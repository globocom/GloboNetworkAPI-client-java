/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.globo.globonetwork.client;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import com.globo.globonetwork.client.api.*;
import com.newrelic.api.agent.NewRelic;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.ErrorMessage;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.google.api.client.util.Types;
import com.google.api.client.xml.GenericXml;
import com.google.api.client.xml.Xml;
import com.google.api.client.xml.Xml.CustomizeParser;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.google.api.client.xml.XmlObjectParser;


public abstract class RequestProcessor {
	
	static final XmlObjectParser parser = new XmlObjectParser(new XmlNamespaceDictionary().set("", ""));
	
	public abstract <T extends GenericXml> GloboNetworkRoot<T> get(String suffixUrl, Class<T> dataClass) throws GloboNetworkException;

	public abstract <T extends GenericXml> GloboNetworkRoot<T> post(String suffixUrl, Object payload, Class<T> dataClass) throws GloboNetworkException;

	public abstract <T extends GenericXml> GloboNetworkRoot<T> put(String suffixUrl, Object payload, Class<T> dataClass) throws GloboNetworkException;

	public abstract <T extends GenericXml> GloboNetworkRoot<T> delete(String suffixUrl, Class<T> dataClass) throws GloboNetworkException;
	
	protected <T extends GenericXml> GloboNetworkRoot<T> readXML(String inputContent, Class<T> dataClass) throws GloboNetworkException {
		Reader in = new StringReader(inputContent);
		
		try {
			XmlPullParser xmlPullParser = Xml.createParser();
			xmlPullParser.setInput(in);
			
			GloboNetworkRoot<T> globoNetworkRoot = new GloboNetworkRoot<T>();
			xmlPullParser.nextTag(); // from null to <networkapi> tag
			xmlPullParser.nextTag(); // from <networkapi> tag to first inner tag
			String tagName = Types.newInstance(dataClass).name;
			while (true) {
				String currentTag = xmlPullParser.getName();
				if ((tagName == null || (tagName != null && tagName.equals(currentTag))) && xmlPullParser.getDepth() == 2) {
					T obj = Types.newInstance(dataClass);
					Xml.parseElement(xmlPullParser, obj, parser.getNamespaceDictionary(), null);
					globoNetworkRoot.getObjectList().add(obj);
				}
				try {
					if (xmlPullParser.nextTag() == 999) {
						break;
					}
				} catch (XmlPullParserException e) {
					if (xmlPullParser.getEventType() == XmlPullParser.END_DOCUMENT) {
						break;
					}
				}
			}
			return globoNetworkRoot;
		} catch (XmlPullParserException e) {
			throw new GloboNetworkException("Error in parsing: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new GloboNetworkException("IOError: " + e.getMessage(), e);
		}
		
	}
	
	/**
	 * Exception treatment for generic calls
	 * @param statusCode
	 * @param responseAsString
	 * @throws IOException
	 * @throws GloboNetworkException
	 */
	protected void handleExceptionIfNeeded(int statusCode, String responseAsString, Long responseTime) throws GloboNetworkException {
		if (statusCode == 200) {
			// Successful return, do nothing
			return;
		} else if (statusCode == 400 || statusCode == 500) {
			// This assumes error is well formed and mappable to class ErrorMessage
			try {
				GloboNetworkRoot<ErrorMessage> response = this.readXML(responseAsString, ErrorMessage.class);
				ErrorMessage errorMsg = response.getFirstObject();
				if (errorMsg != null) {
					throw new GloboNetworkErrorCodeException(errorMsg.getCode(), errorMsg.getDescription());
				} else {
					throw new GloboNetworkException(responseAsString);	
				}
			} catch (GloboNetworkException e) {
				if (e.getCause() instanceof XmlPullParserException) {
					// Fix exception to have response, not parse error.
					throw new GloboNetworkException(responseAsString);
				}
				throw e;
			}
		} else {
			// Unknown error code, return generic exception with description
			throw new GloboNetworkException(responseAsString);
		}
	}

	public abstract String getUsername();

	public abstract String getPassword();

	public VlanAPI getVlanAPI() {
		return new VlanAPI(this);
	}
	
	public NetworkAPI getNetworkAPI() {
		return new NetworkAPI(this);
	}
	
	public EnvironmentAPI getEnvironmentAPI() {
		return new EnvironmentAPI(this);
	}

	public IpAPI getIpAPI() {
		return new IpAPI(this);
	}
	
	public EquipmentAPI getEquipmentAPI() {
		return new EquipmentAPI(this);
	}
	
	public VipAPI getVipAPI() {
		return new VipAPI(this, null);
	}

	public OptionVipAPI getOptionVipAPI() { return new OptionVipAPI(this); }
	
	public VipEnvironmentAPI getVipEnvironmentAPI() {
	    return new VipEnvironmentAPI(this);
	}
}
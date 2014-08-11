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
package com.globo.globonetwork.client.api;

import java.lang.reflect.ParameterizedType;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.google.api.client.xml.GenericXml;

public class BaseAPI<T> {
	
	private final RequestProcessor transport;
	
	public BaseAPI(RequestProcessor transport) {
		this.transport = transport;
	}
	
	protected RequestProcessor getTransport() {
		if (this.transport == null) {
			throw new RuntimeException("No transport configured");
		}
		return this.transport;
	}
	
	protected <T extends GenericXml> Class<T> getBaseClass() {
		return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	protected GloboNetworkRoot<T> get(String suffixUrl) throws GloboNetworkException {
		GloboNetworkRoot<T> answer = (GloboNetworkRoot<T>) this.getTransport().get(suffixUrl, getBaseClass());
		return answer;
	}
	
	protected GloboNetworkRoot<T> post(String suffixUrl, Object payload) throws GloboNetworkException {
		GloboNetworkRoot<T> answer = (GloboNetworkRoot<T>) this.getTransport().post(suffixUrl, payload, getBaseClass());
		return answer;
	}
	
	protected GloboNetworkRoot<T> put(String suffixUrl, Object payload) throws GloboNetworkException {
		GloboNetworkRoot<T> answer = (GloboNetworkRoot<T>) this.getTransport().put(suffixUrl, payload, getBaseClass());
		return answer;
	}
	
	protected GloboNetworkRoot<T> delete(String suffixUrl) throws GloboNetworkException {
		GloboNetworkRoot<T> answer = (GloboNetworkRoot<T>) this.getTransport().delete(suffixUrl, getBaseClass());
		return answer;
	}
	
}
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
package com.globo.globonetwork.client.http;

import java.io.IOException;
import java.security.InvalidParameterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.xml.GenericXml;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.google.api.client.xml.XmlObjectParser;

public class HttpXMLRequestProcessor extends RequestProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessor.class);
	
	private static int DEFAULT_READ_TIMEOUT = 2*60000;
	private static int DEFAULT_CONNECT_TIMEOUT = 1*60000;
	private static int DEFAULT_NUMBER_OF_RETRIES = 0;

	private String baseUrl;
	private String username;
	private String password;
	private int readTimeout = DEFAULT_READ_TIMEOUT;
	private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
	private int numberOfRetries = DEFAULT_NUMBER_OF_RETRIES;
	
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private volatile HttpRequestFactory requestFactory;

	public HttpXMLRequestProcessor(String baseUrl, String username, String password) {
		this.baseUrl = baseUrl;
		this.username = username;
		this.password = password;
	}
	
	private HttpRequestFactory getRequestFactory() {
		if (this.requestFactory == null) {
			synchronized (this) {
				this.requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
					public void initialize(HttpRequest request) throws IOException {
			            request.setParser(new XmlObjectParser(new XmlNamespaceDictionary().set("", "")));
			            request.setReadTimeout(HttpXMLRequestProcessor.this.readTimeout);
			            request.setConnectTimeout(HttpXMLRequestProcessor.this.connectTimeout);
			            request.setNumberOfRetries(HttpXMLRequestProcessor.this.numberOfRetries);
					}
				});
			}
		}
		return this.requestFactory;
	}
	
	protected HttpRequest buildRequest(String method, GenericUrl url, Object payload) throws IOException {
		HttpRequest request;
		
		// Preparing content for POST and PUT
		HttpContent content;
		if (payload != null) {
			content = ByteArrayContent.fromString(null, payload.toString());
		} else {
			content = null;
		}
		
		if ("GET".equalsIgnoreCase(method)) {
			request = this.getRequestFactory().buildGetRequest(url);
		} else if ("POST".equalsIgnoreCase(method)) {
			request = this.getRequestFactory().buildPostRequest(url, content);
		} else if ("PUT".equalsIgnoreCase(method)) {
			request = this.getRequestFactory().buildPutRequest(url, content);
		} else if ("DELETE".equalsIgnoreCase(method)) {
			request = this.getRequestFactory().buildDeleteRequest(url);
		} else {
			throw new InvalidParameterException("Invalid HTTP method.");
		}
		
		request.getHeaders().put("NETWORKAPI_USERNAME", this.username);
		request.getHeaders().put("NETWORKAPI_PASSWORD", this.password);
		request.setLoggingEnabled(true);
		
		return request;
	}
	
	protected HttpResponse performHttpRequest(HttpRequest request) throws IOException {
		LOGGER.debug("Calling GloboNetwork: " + request.getRequestMethod() + " " + request.getUrl() + " " + request.getContent());
		HttpResponse response = request.execute();
		LOGGER.debug("Response from GloboNetwork: " + response.getStatusCode() + " " + response.getStatusMessage());
		return response;
	}
	
	protected String performRequest(HttpRequest request) throws GloboNetworkException, IOException {
		int httpStatusCode;
		String responseAsString;
		try {
			HttpResponse httpResponse = this.performHttpRequest(request);
			httpStatusCode = httpResponse.getStatusCode();
			responseAsString = httpResponse.parseAsString();
		} catch (HttpResponseException e) {
			httpStatusCode = e.getStatusCode();
			responseAsString = e.getContent();
		}
		
		handleExceptionIfNeeded(httpStatusCode, responseAsString);
		return responseAsString;
	}
	
	/**
	 * Build a absolute url
	 *
	 * @param suffixUrl relative url. The {@code baseUrl} attribute will be prepend to this
	 * url.
	 * @return absolue url.
	 */
	protected GenericUrl buildUrl(String suffixUrl) {
		return new GenericUrl(this.baseUrl + suffixUrl);
	}

	public <T extends GenericXml> GloboNetworkRoot<T> get(String suffixUrl, Class<T> dataClass) throws GloboNetworkException {
		try {
			GenericUrl url = buildUrl(suffixUrl);
			HttpRequest request = this.buildRequest("GET", url, null);
			String response = this.performRequest(request);
			return this.readXML(response, dataClass);
		} catch (IOException e) {
			throw new GloboNetworkException("IOError: " + e, e);
		}
	}

	public <T extends GenericXml> GloboNetworkRoot<T> post(String suffixUrl, Object payload, Class<T> dataClass) throws GloboNetworkException {
		try {
			GenericUrl url = buildUrl(suffixUrl);
			HttpRequest request = this.buildRequest("POST", url, payload);
			String response = this.performRequest(request);
			return this.readXML(response, dataClass);
		} catch (IOException e) {
			throw new GloboNetworkException("IOError: " + e, e);
		}
	}

	public <T extends GenericXml> GloboNetworkRoot<T> put(String suffixUrl, Object payload, Class<T> dataClass) throws GloboNetworkException {
		try {
			GenericUrl url = buildUrl(suffixUrl);
			HttpRequest request = this.buildRequest("PUT", url, payload);
			String response = this.performRequest(request);
			return this.readXML(response, dataClass);
		} catch (IOException e) {
			throw new GloboNetworkException("IOError: " + e, e);
		}
	}

	public <T extends GenericXml> GloboNetworkRoot<T> delete(String suffixUrl, Class<T> dataClass) throws GloboNetworkException {
		try {
			GenericUrl url = buildUrl(suffixUrl);
			HttpRequest request = this.buildRequest("DELETE", url, null);
			String response = this.performRequest(request); 
			return this.readXML(response, dataClass);
		} catch (IOException e) {
			throw new GloboNetworkException("IOError: " + e, e);
		}
	}
	
	private void checkIfNotInitialized() {
		if (this.requestFactory != null) {
			throw new IllegalArgumentException("It's not allowed to change connection parameters after initialization.");
		}
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		checkIfNotInitialized();
		this.readTimeout = readTimeout;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		checkIfNotInitialized();
		this.connectTimeout = connectTimeout;
	}

	public Integer getNumberOfRetries() {
		return numberOfRetries;
	}

	public void setNumberOfRetries(Integer numberOfRetries) {
		checkIfNotInitialized();
		this.numberOfRetries = numberOfRetries;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}

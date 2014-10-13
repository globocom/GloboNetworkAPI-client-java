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

import java.util.HashMap;
import java.util.Map;

import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.google.api.client.xml.GenericXml;

public class TestRequestProcessor extends RequestProcessor {
	
	public enum HttpMethod {
		GET, POST, PUT, DELETE
	}
	
	public final Map<String, FakeResponse> urlVsResponse = new HashMap<String, FakeResponse>();

	public void registerFakeRequest(HttpMethod method, String url, String expectedResult) {
		registerFakeRequest(method, url, 200, expectedResult);
	}

	public void registerFakeRequest(HttpMethod method, String url, int statusCode, String expectedResult) {
		String key = method.name() + " " + url;
		if (this.urlVsResponse.containsKey(key)) {
			throw new IllegalArgumentException("Request " + key + " already exists.");
		}
		
		FakeResponse fakeResponse = new FakeResponse(statusCode, expectedResult);
		this.urlVsResponse.put(key, fakeResponse);
	}
	
	private FakeResponse fakeResponse(HttpMethod method, String url) {
		String key = method.name() + " " + url;
		return this.urlVsResponse.get(key);
	}
	
	private <T extends GenericXml> GloboNetworkRoot<T> parseResponse(FakeResponse response, Class<T> dataClass) throws GloboNetworkException {
		if (response == null) {
			throw new RuntimeException("Invalid url");
		}

		int httpStatusCode = response.getStatusCode();
		String content = response.getContent();
		handleExceptionIfNeeded(httpStatusCode, content);
		return this.readXML(content, dataClass);
	}

	public <T extends GenericXml> GloboNetworkRoot<T> get(String baseurl, Class<T> dataClass) throws GloboNetworkException {
		FakeResponse response = this.fakeResponse(HttpMethod.GET, baseurl);
		return this.parseResponse(response, dataClass);
	}

	public <T extends GenericXml> GloboNetworkRoot<T> post(String baseurl, Object payload, Class<T> dataClass) throws GloboNetworkException {
		FakeResponse response = this.fakeResponse(HttpMethod.POST, baseurl);
		return this.parseResponse(response, dataClass);
	}

	public <T extends GenericXml> GloboNetworkRoot<T> put(String baseurl, Object payload, Class<T> dataClass) throws GloboNetworkException {
		FakeResponse response = this.fakeResponse(HttpMethod.PUT, baseurl);
		return this.parseResponse(response, dataClass);
	}

	public <T extends GenericXml> GloboNetworkRoot<T> delete(String baseurl, Class<T> dataClass) throws GloboNetworkException {
		FakeResponse response = this.fakeResponse(HttpMethod.DELETE, baseurl);
		return this.parseResponse(response, dataClass);
	}
	
	private static class FakeResponse {
		
		private final int statusCode;
		
		private final String content;
		
		FakeResponse(int statusCode, String content) {
			this.statusCode = statusCode;
			this.content = content;
		}

		public int getStatusCode() {
			return statusCode;
		}

		public String getContent() {
			return content;
		}
	}

	@Override
	public String getUsername() {
		return "root";
	}

	@Override
	public String getPassword() {
		return "password";
	}

}

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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.Vlan;

@RunWith(JUnit4.class)
public class RequestProcessorTest {
	
	private RequestProcessor rp;

	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
	}

	@Test
	public void handleExceptionDoNothingWhenStatusCodeIs200() throws GloboNetworkException {
		rp.handleExceptionIfNeeded(200, "sdjds");
	}

	@Test
	public void handleExceptionRaisesNetworkAPIErrorCodeExceptionWhenStatusCodeAre500() throws GloboNetworkException {
		String code = "0108";
		String description = "the VLAN name duplicated within an environment informed";
		try {
			rp.handleExceptionIfNeeded(500, "<networkapi versao=\"1.0\"><erro><codigo>" + code + "</codigo><descricao>" + description + "</descricao></erro></networkapi>");
			fail();
		} catch (GloboNetworkErrorCodeException e) {
			assertEquals(Integer.parseInt(code), e.getCode());
			assertEquals(description, e.getDescription());
		}
	}

	@Test
	public void handleExceptionRaisesNetworkAPIExceptionWhenXmlIsInvalid() throws GloboNetworkException {
		String content = "Request error";
		try {
			rp.handleExceptionIfNeeded(500, content);
			fail();
		} catch (GloboNetworkException e) {
			assertEquals(content, e.getMessage());
		}
	}
	
	@Test
	public void innerTagWithRootTagNameDoNotStopXMLProcessing() throws GloboNetworkException {
	    GloboNetworkRoot<Vlan> root = rp.readXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><vlan><nome>vlan name</nome><vlan>10</vlan><descricao>vlan desc</descricao></vlan></networkapi>", Vlan.class);
	    assertNotNull(root);
	    assertNotNull(root.getFirstObject());
	    Vlan vlan = root.getFirstObject();
	    assertEquals("vlan name", vlan.getName());
        assertEquals("vlan desc", vlan.getDescription());
	}

}

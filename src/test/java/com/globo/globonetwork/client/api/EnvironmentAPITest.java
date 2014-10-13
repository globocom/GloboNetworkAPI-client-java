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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Environment;

@RunWith(JUnit4.class)
public class EnvironmentAPITest {
	
	private EnvironmentAPI api;
	private TestRequestProcessor rp;
	
	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
		this.api = this.rp.getEnvironmentAPI();
	}

	@Test
	public void testListAllEnvironmentsReturnsFullList() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/ambiente/list/", "<?xml version='1.0' encoding='utf-8'?><networkapi>" +
				"<ambiente><ipv6_template/><divisao_dc>1</divisao_dc><ipv4_template/><divisao_dc_name>BE</divisao_dc_name><ambiente_logico_name>BALANCEAMENTO INTERNO</ambiente_logico_name><ambiente_logico>21</ambiente_logico><filter/><acl_path>BE</acl_path><grupo_l3_name>CORE/DENSIDADE</grupo_l3_name><id>34</id><grupo_l3>20</grupo_l3></ambiente>" +
				"<ambiente><ipv6_template/><divisao_dc>1</divisao_dc><ipv4_template/><divisao_dc_name>BE</divisao_dc_name><ambiente_logico_name>GCLOUD</ambiente_logico_name><ambiente_logico>91</ambiente_logico><filter/><link/><acl_path/><grupo_l3_name>CITTA BALANCEAMENTO</grupo_l3_name><id>118</id><grupo_l3>52</grupo_l3></ambiente>" + 
				"</networkapi>");
		List<Environment> environmentList = this.api.listAll();
		assertEquals(2, environmentList.size());
		
		Environment environment1 = environmentList.get(0);
		assertEquals(Long.valueOf(34), environment1.getId());
		assertEquals(Long.valueOf(20), environment1.getL3GroupId());
		assertEquals("CORE/DENSIDADE", environment1.getL3GroupName());
		assertEquals(Long.valueOf(21), environment1.getLogicalEnvironmentId());
		assertEquals("BALANCEAMENTO INTERNO", environment1.getLogicalEnvironmentName());
		assertEquals(Long.valueOf(1), environment1.getDcDivisionId());
		assertEquals("BE", environment1.getDcDivisionName());
		
		Environment environment2 = environmentList.get(1);
		assertEquals(Long.valueOf(118), environment2.getId());
		assertEquals(Long.valueOf(52), environment2.getL3GroupId());
		assertEquals("CITTA BALANCEAMENTO", environment2.getL3GroupName());
		assertEquals(Long.valueOf(91), environment2.getLogicalEnvironmentId());
		assertEquals("GCLOUD", environment2.getLogicalEnvironmentName());
		assertEquals(Long.valueOf(1), environment2.getDcDivisionId());
		assertEquals("BE", environment2.getDcDivisionName());
	}
	
	@Test
	public void testListAllEnvironmentsReturnsEmptyList() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/ambiente/list/", "<?xml version='1.0' encoding='utf-8'?><networkapi></networkapi>");
		
		assertTrue(this.api.listAll().isEmpty());
	}
}

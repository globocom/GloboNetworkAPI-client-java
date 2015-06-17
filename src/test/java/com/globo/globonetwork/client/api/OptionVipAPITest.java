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

import com.globo.globonetwork.client.model.OptionVip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Environment;

@RunWith(JUnit4.class)
public class OptionVipAPITest {
	
	private OptionVipAPI api;
	private TestRequestProcessor rp;

	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
		this.api = this.rp.getOptionVipAPI();
	}

	@Test
	public void testListCacheGroupsByEnvironmentVip() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/environment-vip/get/grupo-cache/3/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						"<networkapi versao=\"1.0\">" +
							"<grupocache_opt>" +
							"	<grupocache_opt>CACHE_1</grupocache_opt>" +
							"</grupocache_opt>" +
							"<grupocache_opt>" +
							"	<grupocache_opt>CACHE_2</grupocache_opt>" +
							"</grupocache_opt>" +
							"<grupocache_opt>" +
							"	<grupocache_opt>CACHE_3</grupocache_opt>" +
							"</grupocache_opt>" +
							"<grupocache_opt>" +
							"	<grupocache_opt>CACHE_4</grupocache_opt>" +
							"</grupocache_opt>" +
						"</networkapi>");
		List<OptionVip> optionVips = this.api.listCacheGroups(3l);
		assertEquals(4, optionVips.size());

		OptionVip optVip1 = optionVips.get(0);
		assertEquals("CACHE_1", optVip1.getCacheGroup());

		OptionVip optVip2 = optionVips.get(1);
		assertEquals("CACHE_2", optVip2.getCacheGroup());

		OptionVip optVip3 = optionVips.get(2);
		assertEquals("CACHE_3", optVip3.getCacheGroup());

		OptionVip optVip4 = optionVips.get(3);
		assertEquals("CACHE_4", optVip4.getCacheGroup());

	}

	@Test
	public void testListCacheGroupsByEnvironmentVip_empty() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/environment-vip/get/grupo-cache/3/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						"<networkapi versao=\"1.0\">" +
						"</networkapi>");
		List<OptionVip> optionVips = this.api.listCacheGroups(3l);
		assertEquals(0, optionVips.size());
	}

	@Test
	public void testListCacheGroupsByEnvironmentVip_fail() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/environment-vip/get/grupo-cache/3/",500,
								"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><erro><codigo>0283</codigo><descricao>Environment VIP not registered</descricao></erro></networkapi>");

		List<OptionVip> optionVips = this.api.listCacheGroups(3l);
		assertNull(optionVips);
	}

	@Test(expected = GloboNetworkException.class)
	public void testListCacheGroupsByEnvironmentVip_fail_empty() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/environment-vip/get/grupo-cache/3/",500,
				"");

		List<OptionVip> optionVips = this.api.listCacheGroups(3l);

	}

}

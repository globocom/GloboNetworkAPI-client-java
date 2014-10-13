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
package com.globo.globonetwork.client.flow;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.api.NetworkAPI;
import com.globo.globonetwork.client.api.VlanAPI;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Network;
import com.globo.globonetwork.client.model.Vlan;

@RunWith(JUnit4.class)
public class CloudstackLayer2FlowTest {
	
	private VlanAPI vlanAPI;
	private NetworkAPI netAPI;
	private TestRequestProcessor rp;
	
	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
		this.vlanAPI = this.rp.getVlanAPI();
		this.netAPI = this.rp.getNetworkAPI();
	}

	@Test
	public void testCloudStackLayer2Flow() throws GloboNetworkException {
		
		// Variables
		Long environmentId = 120L;
		Long vlanId = 2544L;
		String vlanName = "TESTE-05";
		String vlanDescription = "DESCRICAO";
		Long networkTypeId = 6L;
		Long networkId = 1693L;
		
		
		// Step 0: Register all fake requests that will be made throughout the test
		this.rp.registerFakeRequest(HttpMethod.POST, "/vlan/no-network/", 
				"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
				"<vlan xmlns=''><acl_file_name>" + vlanName + ".txt</acl_file_name><ativada>0</ativada><acl_valida>0</acl_valida><nome>" + vlanName + "</nome><acl_file_name_v6/><acl_valida_v6>False</acl_valida_v6><ambiente>" + environmentId + "</ambiente><num_vlan>4</num_vlan><id>" + vlanId + "</id><descricao>" + vlanDescription + "</descricao></vlan>" +
				"</networkapi>");
		
		this.rp.registerFakeRequest(HttpMethod.POST, "/network/ipv4/add/", 
				"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
				"<vlan xmlns=''><id_ambiente>" + environmentId + "</id_ambiente><id>" + vlanId + "</id><nome>" + vlanName + "</nome><num_vlan>4</num_vlan><rede_oct2>170</rede_oct2><rede_oct3>0</rede_oct3><rede_oct1>10</rede_oct1><acl_file_name>" + vlanName + ".txt></acl_file_name><ativada>False</ativada><acl_valida>False</acl_valida><acl_file_name_v6 /><acl_valida_v6>False</acl_valida_v6><broadcast>10.170.0.255</broadcast><mascara_oct4>0</mascara_oct4><id_tipo_rede>" + networkTypeId + "</id_tipo_rede><rede_oct4>0</rede_oct4><mascara_oct3>255</mascara_oct3><bloco>24</bloco><mascara_oct1>255</mascara_oct1><descricao/><mascara_oct2>255</mascara_oct2></vlan>" +
				"</networkapi>");
		
		this.rp.registerFakeRequest(HttpMethod.GET, "/vlan/" + vlanId + "/network/", 
				"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
				"<vlan xmlns=''><ambiente>" + environmentId + "</ambiente><id>" + vlanId + "</id><nome>" + vlanName + "</nome><num_vlan>4</num_vlan><acl_file_name>" + vlanName + ".txt</acl_file_name><ativada>False</ativada><acl_valida>False</acl_valida><acl_file_name_v6 /><acl_valida_v6>False</acl_valida_v6><descricao>" + vlanDescription + "</descricao><redeipv4><mask_oct1>255</mask_oct1><mask_oct2>255</mask_oct2><mask_oct3>255</mask_oct3><mask_oct4>0</mask_oct4><oct4>0</oct4><vlan>" + vlanId + "</vlan><oct2>170</oct2><oct3>2</oct3><oct1>10</oct1><broadcast>10.170.2.255</broadcast><ambient_vip/><id>" + networkId + "</id><active>False</active><network_type>" + networkTypeId + "</network_type><block>24</block></redeipv4></vlan>" +
			"</networkapi>");
		
		this.rp.registerFakeRequest(HttpMethod.PUT, "/network/create/", 
				"<?xml version='1.0' encoding='utf-8'?><networkapi/>");
		
		this.rp.registerFakeRequest(HttpMethod.DELETE, "/vlan/" + vlanId + "/remove/",
				"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
				"<sucesso><codigo>0000</codigo><descricao><stderr></stderr><stdout></stdout></descricao></sucesso>" +
			"</networkapi>");
		
		this.rp.registerFakeRequest(HttpMethod.DELETE, "/vlan/" + vlanId + "/deallocate/",
				"<?xml version='1.0' encoding='utf-8'?><networkapi></networkapi>");
		
		
		// Step 1: Allocate VLAN without networks
		Vlan allocatedVlan = this.vlanAPI.allocateWithoutNetwork(environmentId, vlanName, vlanDescription);
		
		assertNotNull(allocatedVlan);
		assertEquals(allocatedVlan.getEnvironment(), environmentId);
		assertEquals(allocatedVlan.getName(), vlanName);
		assertEquals(allocatedVlan.getDescription(), vlanDescription);
		
		// Step 2: Add a network to the allocated VLAN
		Network addedNetwork = this.netAPI.addNetworkIpv4(allocatedVlan.getId(), networkTypeId, environmentId);
		
		assertNotNull(addedNetwork);
		assertEquals(addedNetwork.getNetworkTypeId(), networkTypeId);
		assertNotNull(addedNetwork.getId());
		
		
		// Step 3: Get the allocated network ID
		allocatedVlan = this.vlanAPI.getById(allocatedVlan.getId()); // More complete info of the allocated Vlan
		
		assertNotNull(allocatedVlan);
		
		Long addedNetworkId = allocatedVlan.getIpv4Networks().get(0).getId();
		
		assertNotNull(addedNetworkId);
		
		
		// Step 4: Create the network on the hardware
		this.netAPI.createNetworks(addedNetworkId, allocatedVlan.getId());
		
		
		// Step 5: Remove the VLAN network
		this.vlanAPI.remove(vlanId);
		
		
		// Step 6: Deallocate the VLAN
		this.vlanAPI.deallocate(vlanId);
	}
}

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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Equipment;

@RunWith(JUnit4.class)
public class EquipmentAPITest {
	
	private EquipmentAPI equipmentApi;
	private TestRequestProcessor rp;
	
	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
		this.equipmentApi = this.rp.getEquipmentAPI();
	}
	
	@Test
	public void testListByName() throws GloboNetworkException {
		String equipmentName = "VIRTUALMACHINE1";
		this.rp.registerFakeRequest(HttpMethod.GET, "/equipamento/nome/" + equipmentName + "/", 
			"<?xml version='1.0' encoding='utf-8'?>" +
			"<networkapi versao=\"1.0\"><equipamento><id_modelo>18</id_modelo><nome>" +
			equipmentName + "</nome><nome_marca>GENERICO</nome_marca><nome_modelo>Servidor Virtual</nome_modelo><id_marca>1</id_marca><nome_tipo_equipamento>Servidor Virtual</nome_tipo_equipamento><id_tipo_equipamento>10</id_tipo_equipamento><id>13139</id></equipamento></networkapi>");
		
		Equipment equipment = this.equipmentApi.listByName(equipmentName);
		
		assertEquals(Long.valueOf(18), equipment.getModelId());
		assertEquals(equipmentName, equipment.getName());
		assertEquals(String.valueOf("GENERICO"), equipment.getBrandName());
		assertEquals(String.valueOf("Servidor Virtual"), equipment.getModelName());
		assertEquals(Long.valueOf(1), equipment.getBrandId());
		assertEquals(String.valueOf("Servidor Virtual"), equipment.getEquipmentTypeName());
		assertEquals(Long.valueOf(10), equipment.getEquipmentTypeId());
		assertEquals(Long.valueOf(13139), equipment.getId());

	}

	@Test
	public void testListByNameWithInvalidNameReturnsNull() throws GloboNetworkException {
		String equipmentName = "INEXISTENT";
		this.rp.registerFakeRequest(HttpMethod.GET, "/equipamento/nome/" + equipmentName + "/", 500, 
			"<?xml version='1.0' encoding='utf-8'?>" +
			"<networkapi versao=\"1.0\"><erro><codigo>0117</codigo><descricao>Equipment not registered</descricao></erro></networkapi>");
		
		Equipment equipment = this.equipmentApi.listByName(equipmentName);
		assertNull(equipment);
	}

	@Test
	public void testInsertReturnsNewEquipmentWithId() throws GloboNetworkException {
		String equipmentName = "VIRTUALMACHINE1";
		Long groupId = 23l;
		Long equipmentTypeId = 10l;
		Long modelId = 18l;
		this.rp.registerFakeRequest(HttpMethod.POST, "/equipamento/", 
			"<?xml version='1.0' encoding='utf-8'?>" +
			"<networkapi versao=\"1.0\"><equipamento_grupo><id>13337</id></equipamento_grupo><equipamento><id>13211</id></equipamento></networkapi>");
		
		Equipment equipment = this.equipmentApi.insert(equipmentName, equipmentTypeId, modelId, groupId);
		
		assertEquals(Long.valueOf(13211), equipment.getId());
	}

	@Test
	public void testDeleteEquipment() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.DELETE, "/equipamento/13259/",
			"<?xml version='1.0' encoding='utf-8'?>" +
			"<networkapi versao=\"1.0\"/>");
		
		this.equipmentApi.delete(13259l);
	}
	
	@Test
	public void testRemoveIpv4FromEquipment() throws GloboNetworkException {
	    this.rp.registerFakeRequest(HttpMethod.DELETE, "/ip/1/equipamento/2/",
	            "<?xml version='1.0' encoding='utf-8'?>" +
	            "<networkapi versao=\"1.0\"/>");
	    
	    this.equipmentApi.removeIP(2L, 1L, false);
	}
	
	@Test
	public void testRemoveIpv6FromEquipment() throws GloboNetworkException {
	    this.rp.registerFakeRequest(HttpMethod.DELETE, "/ipv6/1/equipment/2/remove/",
	            "<?xml version='1.0' encoding='utf-8'?>" +
	            "<networkapi versao=\"1.0\"/>");
	    
	    this.equipmentApi.removeIP(2L, 1L, true);
	}
}

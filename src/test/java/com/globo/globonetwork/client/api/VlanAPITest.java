package com.globo.globonetwork.client.api;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.api.VlanAPI;
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Vlan;

@RunWith(JUnit4.class)
public class VlanAPITest {
	
	private VlanAPI api;
	private TestRequestProcessor rp;
	private Long environmentId = 120L;
	
	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
		this.api = this.rp.getVlanAPI();
	}

	@Test
	public void testListByEnvironmentReturnsEmptyList() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/vlan/ambiente/" + this.environmentId + "/", "<?xml version='1.0' encoding='utf-8'?><networkapi></networkapi>");
		
		assertTrue(this.api.listByEnvironment(this.environmentId).isEmpty());
	}

	@Test
	public void testListByEnvironmentIdReturnsFullList() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/vlan/ambiente/" + this.environmentId + "/", 
			"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
			"<vlan xmlns=''><ambiente>82</ambiente><id>1382</id><nome>ORQUESTRA-APLICATION-DEV</nome><num_vlan>2</num_vlan><rede_oct2>248</rede_oct2><rede_oct3>0</rede_oct3><rede_oct1>10</rede_oct1><acl_file_name /><ativada>True</ativada><acl_valida>False</acl_valida><acl_file_name_v6 /><acl_valida_v6>False</acl_valida_v6><broadcast>10.248.0.255</broadcast><mascara_oct4>0</mascara_oct4><id_tipo_rede>6</id_tipo_rede><rede_oct4>0</rede_oct4><mascara_oct3>255</mascara_oct3><bloco>24</bloco><mascara_oct1>255</mascara_oct1><descricao>None</descricao><mascara_oct2>255</mascara_oct2></vlan>" +
			"<vlan xmlns=''><ambiente>82</ambiente><id>1285</id><nome>CACHOS-DEV/QA01-GERENCIA</nome><num_vlan>3</num_vlan><rede_oct2>248</rede_oct2><rede_oct3>1</rede_oct3><rede_oct1>10</rede_oct1><acl_file_name /><ativada>True</ativada><acl_valida>False</acl_valida><acl_file_name_v6 /><acl_valida_v6>False</acl_valida_v6><broadcast>10.248.1.255</broadcast><mascara_oct4>0</mascara_oct4><id_tipo_rede>6</id_tipo_rede><rede_oct4>0</rede_oct4><mascara_oct3>255</mascara_oct3><bloco>24</bloco><mascara_oct1>255</mascara_oct1><descricao>teste3</descricao><mascara_oct2>255</mascara_oct2></vlan>" +
		"</networkapi>");
		
		List<Vlan> vlans = this.api.listByEnvironment(this.environmentId);
		assertEquals(2, vlans.size());
		
		Vlan vlan1 = vlans.get(0);
		assertEquals(Long.valueOf(1382), vlan1.getId());
		assertEquals("ORQUESTRA-APLICATION-DEV", vlan1.getName());
		assertEquals(Long.valueOf(2), vlan1.getVlanNum());
		assertEquals(Long.valueOf(82), vlan1.getEnvironment());

		Vlan vlan2 = vlans.get(1);
		assertEquals(Long.valueOf(1285), vlan2.getId());
		assertEquals("CACHOS-DEV/QA01-GERENCIA", vlan2.getName());
		assertEquals(Long.valueOf(3), vlan2.getVlanNum());
		assertEquals(Long.valueOf(82), vlan2.getEnvironment());
	}
	
	@Test
	public void testAllocateWithoutNetwork() throws GloboNetworkException {
		String name = "TESTE-02";
		String description = "DESCRICAO";
		this.rp.registerFakeRequest(HttpMethod.POST, "/vlan/no-network/", 
				"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
				"<vlan xmlns=''><acl_file_name>" + name + ".txt</acl_file_name><ativada>0</ativada><acl_valida>0</acl_valida><nome>" + name + "</nome><acl_file_name_v6/><acl_valida_v6>False</acl_valida_v6><ambiente>" + this.environmentId + "</ambiente><num_vlan>2</num_vlan><id>2449</id><descricao>" + description + "</descricao></vlan>" +
				"</networkapi>");
		
		Vlan vlan = this.api.allocateWithoutNetwork(this.environmentId, name, description);
		
		assertNotNull(vlan);
		assertEquals(name, vlan.getName());
		assertEquals(Long.valueOf(2449), vlan.getId());
		assertEquals(this.environmentId, vlan.getEnvironment());
	}

	@Test
	public void testAllocateWithoutNetworkWithSameNameThrowsNetworkAPIErrorCodeException() throws GloboNetworkException {
		String code = "0108";
		String description = "the VLAN name duplicated within an environment informed";
		this.rp.registerFakeRequest(HttpMethod.POST, "/vlan/no-network/", 500, "<networkapi versao=\"1.0\"><erro><codigo>" + code + "</codigo><descricao>" + description + "</descricao></erro></networkapi>");
		
		try {
			this.api.allocateWithoutNetwork(this.environmentId, "same", "same");
			fail();
		} catch (GloboNetworkErrorCodeException e) {
			assertEquals(Integer.parseInt(code), e.getCode());
			assertEquals(description, e.getDescription());
		}
	}

	@Test
	public void testGetByIdReturnsEmptyVlan() throws GloboNetworkException {
		Long vlanId = 2452L;
		
		this.rp.registerFakeRequest(HttpMethod.GET, "/vlan/" + vlanId + "/network/", 
				"<?xml version='1.0' encoding='utf-8'?><networkapi></networkapi>");
		
		assertNull(this.api.getById(vlanId));
	}
	
	@Test
	public void testGetByIdReturnsSingleVlan() throws GloboNetworkException {
		Long vlanId = 2452L;
		this.rp.registerFakeRequest(HttpMethod.GET, "/vlan/" + vlanId + "/network/", 
			"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
			"<vlan xmlns=''><ambiente>120</ambiente><id>2452</id><nome>TESTE-04</nome><num_vlan>4</num_vlan><acl_file_name>TESTE-04.txt</acl_file_name><ativada>False</ativada><acl_valida>False</acl_valida><acl_file_name_v6 /><acl_valida_v6>False</acl_valida_v6><descricao>DESCRICAO</descricao><redeipv4><mask_oct1>255</mask_oct1><mask_oct2>255</mask_oct2><mask_oct3>255</mask_oct3><mask_oct4>0</mask_oct4><oct4>0</oct4><vlan>2452</vlan><oct2>170</oct2><oct3>2</oct3><oct1>10</oct1><broadcast>10.170.2.255</broadcast><ambient_vip/><id>1692</id><active>False</active><network_type>6</network_type><block>24</block></redeipv4></vlan>" +
		"</networkapi>");
		
		Vlan vlan = this.api.getById(vlanId);
		
		assertNotNull(vlan);
		assertEquals(vlanId, vlan.getId());
		assertEquals("TESTE-04", vlan.getName());
		assertEquals(Long.valueOf(4), vlan.getVlanNum());
		assertEquals(Long.valueOf(120), vlan.getEnvironment());
	}
	
	@Test
	public void testRemoveVlanReturnsSuccess() throws GloboNetworkException {
		Long vlanId = 393L;
		this.rp.registerFakeRequest(HttpMethod.DELETE, "/vlan/" + vlanId + "/remove/",
			"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
			"<sucesso><codigo>0000</codigo><descricao><stderr></stderr><stdout></stdout></descricao></sucesso>" +
		"</networkapi>");
		
		this.api.remove(vlanId);
	}
	
	@Test
	public void testDeallocateVlanReturnsSuccess() throws GloboNetworkException {
		Long vlanId = 393L;
		this.rp.registerFakeRequest(HttpMethod.DELETE, "/vlan/" + vlanId + "/deallocate/",
			"<?xml version='1.0' encoding='utf-8'?><networkapi></networkapi>");
		
		this.api.deallocate(vlanId);
	}
}




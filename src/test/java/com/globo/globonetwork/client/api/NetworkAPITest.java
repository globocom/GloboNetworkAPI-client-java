package com.globo.globonetwork.client.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.api.NetworkAPI;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.IPv4Network;
import com.globo.globonetwork.client.model.Network;

@RunWith(JUnit4.class)
public class NetworkAPITest {
	
	private NetworkAPI netApi;
	private TestRequestProcessor rp;
	
	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
		this.netApi = this.rp.getNetworkAPI();
	}
	
	@Test
	public void testAddNetworkIpv4() throws GloboNetworkException {
		Long vlanId = 2450L;
		Long networkTypeId = 6L;
		Long vipEnvironmentId = 120L;
		
		this.rp.registerFakeRequest(HttpMethod.POST, "/network/ipv4/add/", 
				"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
				"<vlan xmlns=''><id_ambiente>120</id_ambiente><id>" + vlanId + "</id><nome>TESTE-03</nome><num_vlan>3</num_vlan><rede_oct2>170</rede_oct2><rede_oct3>0</rede_oct3><rede_oct1>10</rede_oct1><acl_file_name>TESTE-03.txt></acl_file_name><ativada>False</ativada><acl_valida>False</acl_valida><acl_file_name_v6 /><acl_valida_v6>False</acl_valida_v6><broadcast>10.170.0.255</broadcast><mascara_oct4>0</mascara_oct4><id_tipo_rede>" + networkTypeId + "</id_tipo_rede><rede_oct4>0</rede_oct4><mascara_oct3>255</mascara_oct3><bloco>24</bloco><mascara_oct1>255</mascara_oct1><descricao/><mascara_oct2>255</mascara_oct2></vlan>" +
				"</networkapi>");

		this.rp.registerFakeRequest(HttpMethod.GET, "/vlan/" + vlanId + "/network/", 
				"<?xml version='1.0' encoding='utf-8'?><networkapi>" +
				"<vlan><acl_file_name>TESTEOKAMA2.txt</acl_file_name><ativada>True</ativada><acl_valida>False</acl_valida><nome>TESTEOKAMA2</nome><acl_file_name_v6></acl_file_name_v6><redeipv6/><acl_valida_v6>False</acl_valida_v6><id>2532</id><num_vlan>24</num_vlan><redeipv4><mask_oct1>255</mask_oct1><mask_oct2>255</mask_oct2><mask_oct3>255</mask_oct3><mask_oct4>0</mask_oct4><oct4>0</oct4><vlan>2532</vlan><oct2>170</oct2><oct3>17</oct3><oct1>10</oct1><broadcast>10.170.17.255</broadcast><ambient_vip/><active>True</active><network_type>6</network_type><id>1766</id><block>24</block></redeipv4><ambiente>120</ambiente><descricao>testeokama2</descricao></vlan></networkapi>");

		Network network = this.netApi.addNetworkIpv4(vlanId, networkTypeId, vipEnvironmentId);
		
		assertNotNull(network);
		assertEquals(networkTypeId, network.getNetworkTypeId());
		assertEquals(Long.valueOf(1766), network.getId());
	}
	
	@Test
	public void testCreateNetworksEmptyResponse() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.PUT, "/network/create/", 
				"<?xml version='1.0' encoding='utf-8'?><networkapi/>");
		
		Long networkId = 1692L;
		Long vlanId = 2452L;
		
		this.netApi.createNetworks(networkId, vlanId);
	}

    @Test
    public void testGetNetworkIpv4() throws GloboNetworkException {
        this.rp.registerFakeRequest(HttpMethod.GET, "/network/ipv4/id/1645/", 
                "<?xml version='1.0' encoding='utf-8'?><networkapi versao=\"1.0\"><network><mask_oct1>255</mask_oct1><mask_oct2>255</mask_oct2><mask_oct3>255</mask_oct3><mask_oct4>0</mask_oct4><oct4>0</oct4><vlan>2399</vlan><oct2>170</oct2><oct3>1</oct3><oct1>200</oct1><broadcast>200.170.1.255</broadcast><ambient_vip>23</ambient_vip><active>True</active><network_type>8</network_type><id>1645</id><block>24</block></network></networkapi>");
        
        Long networkId = 1645L;
        
        IPv4Network network = this.netApi.getNetworkIpv4(networkId);
        assertEquals(networkId, network.getId());
        assertEquals("255.255.255.0", network.getMaskAsString());
        assertEquals("200.170.1.0", network.getNetworkAddressAsString());
        assertEquals(true, network.getActive());
        assertEquals(Long.valueOf(8), network.getNetworkTypeId());
        assertEquals(Integer.valueOf(24), network.getBlock());
        assertEquals("200.170.1.255", network.getBroadcast());
        assertEquals(Long.valueOf(2399), network.getVlanId());
    }

}

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

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Ip;
import com.globo.globonetwork.client.model.Ipv4;
import com.globo.globonetwork.client.model.Ipv6;

@RunWith(JUnit4.class)
public class IpAPITest {
	
	private IpAPI api;
	private TestRequestProcessor rp;
	
	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
		this.api = this.rp.getIpAPI();
	}

	@Test
	public void testGetIpv4() throws GloboNetworkException, InstantiationException, IllegalAccessException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip/get-ipv4/13/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ipv4><oct4>60</oct4><equipamentos>HOST</equipamentos><oct2>16</oct2><oct3>0</oct3><oct1>172</oct1><networkipv4>1</networkipv4><id>13</id><descricao/></ipv4></networkapi>");

		Ip ip = this.api.getIp(13L, false);
		assertEquals(Long.valueOf(13), ip.getId());
		assertEquals("172.16.0.60", ip.getIpString());
		assertEquals(Arrays.asList("HOST"), ip.getEquipments());
	}
	
	@Test
	public void testGetIpv6() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip/get-ipv6/13/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ipv6><id>1</id><equipamentos>HOST</equipamentos><networkipv6>1</networkipv6><block1>2001</block1><block2>0db8</block2><block3>85a3</block3><block4>0042</block4><block5>1000</block5><block6>8a2e</block6><block7>0370</block7><block8>7334</block8><descricao>teste</descricao></ipv6></networkapi>");

		Ipv6 ip = (Ipv6) this.api.getIp(13L, true);
		assertEquals(Long.valueOf(1), ip.getId());
		assertEquals("2001:0db8:85a3:0042:1000:8a2e:0370:7334", ip.getIpString());
		assertEquals(Arrays.asList("HOST"), ip.getEquipments());
	}

	@Test
	public void testFindByIdAndEnvironmentReturnsEmpty() throws GloboNetworkException {
		String ipAddr = "10.170.20.10";
		Long environmentId = 120L;
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip/" + ipAddr + "/ambiente/" + environmentId + "/", 500,
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><erro><codigo>0119</codigo><descricao>IP not registered</descricao></erro></networkapi>");
		
		Ip ip = this.api.findByIpAndEnvironment(ipAddr, environmentId, false);
		assertNull(ip);
	}
	
	@Test
	public void testFindByIdAndEnvironmentReturnsIp() throws GloboNetworkException {
		String ipAddr = "10.170.20.10";
		Long environmentId = 120L;
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip/" + ipAddr + "/ambiente/" + environmentId + "/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ip><oct4>10</oct4><oct2>170</oct2><oct3>20</oct3><oct1>10</oct1><id>44152</id><descricao>eth0</descricao></ip></networkapi>");
		
		Ipv4 ip = (Ipv4) this.api.findByIpAndEnvironment(ipAddr, environmentId, false);
		assertEquals(ipAddr, ip.getOct1() + "." + ip.getOct2() + "." + ip.getOct3() + "." + ip.getOct4());
		assertEquals(Long.valueOf(44152), ip.getId());
	}
	
	@Test
	public void testFindByIdAndEnvironmentReturnsIpv6() throws GloboNetworkException {
		String ipAddr = "2001:0db8:85a3:0042:1000:8a2e:0370:7334";
		Long environmentId = 120L;
		this.rp.registerFakeRequest(HttpMethod.POST, "/ipv6/environment/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ipv6><id>1</id><idvlan>1</idvlan><block1>2001</block1><block2>0db8</block2><block3>85a3</block3><block4>0042</block4><block5>1000</block5><block6>8a2e</block6><block7>0370</block7><block8>7334</block8><descricao>teste</descricao></ipv6></networkapi>");
		
		Ipv6 ip = (Ipv6) this.api.findByIpAndEnvironment(ipAddr, environmentId, true);
		assertEquals(ipAddr, ip.getIpString());
		assertEquals(Long.valueOf(1), ip.getId());
	}
	
	@Test
	public void testSaveIpv4ReturnsIp() throws GloboNetworkException {
		String ipAddr = "10.170.20.10";
		Long equipId = 13204L;
		String nicDescription = "eth0";
		Long networkId = 1722L;
				
		this.rp.registerFakeRequest(HttpMethod.POST, "/ipv4/save/", 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ip><oct4>10</oct4><oct2>170</oct2><oct3>20</oct3><oct1>10</oct1><equipamento><grupos>23</grupos><nome>E834C08665114C68871CB5739B13C0</nome><modelo>18</modelo><id>13204</id><tipo_equipamento>10</tipo_equipamento></equipamento><id>44152</id><descricao>eth0</descricao></ip></networkapi>");		
		
		Ipv4 ip = (Ipv4) this.api.saveIp(ipAddr, equipId, nicDescription, networkId, false);
		assertEquals(ipAddr, ip.getOct1() + "." + ip.getOct2() + "." + ip.getOct3() + "." + ip.getOct4());
		assertEquals(Long.valueOf(44152), ip.getId());
	}
	
	@Test
	public void testSaveIpv6ReturnsIp() throws GloboNetworkException {
		String ipAddr = "2001:0db8:85a3:0042:1000:8a2e:0370:7334";
		Long equipId = 13204L;
		String nicDescription = "eth0";
		Long networkId = 1722L;
				
		this.rp.registerFakeRequest(HttpMethod.POST, "/ipv6/save/", 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ipv6><block1>2001</block1><block2>0db8</block2><block3>85a3</block3><block4>0042</block4><block5>1000</block5><block6>8a2e</block6><block7>0370</block7><block8>7334</block8><equipamento><grupos>23</grupos><nome>E834C08665114C68871CB5739B13C0</nome><modelo>18</modelo><id>13204</id><tipo_equipamento>10</tipo_equipamento></equipamento><id>1</id><descricao>eth0</descricao></ipv6></networkapi>");		
		
		Ipv6 ip = (Ipv6) this.api.saveIp(ipAddr, equipId, nicDescription, networkId, true);
		assertEquals(ipAddr, ip.getIpString());
		assertEquals(Long.valueOf(1), ip.getId());
	}
	
	@Test
	public void testDeleteIpv4() throws GloboNetworkException {
		Long ipv4Id = 44244l;
				
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip4/delete/" + ipv4Id + "/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"/>");
		
		this.api.deleteIp(ipv4Id, false);
	}
	
	@Test
	public void testDeleteIpv6() throws GloboNetworkException {
		Long ipv6Id = 44244l;
		
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip6/delete/" + ipv6Id + "/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"/>");
		
		this.api.deleteIp(ipv6Id, true);
	}

    @Test
    public void testGetAvailableIp4ForVip() throws GloboNetworkException {
        Long environmentVip = 23l;
        String name = "testcloud2";
                
        this.rp.registerFakeRequest(HttpMethod.POST, "/ip/availableip4/vip/" + environmentVip + "/",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ip><oct4>36</oct4><oct2>170</oct2><oct3>1</oct3><oct1>200</oct1><networkipv4>1645</networkipv4><id>50335</id><descricao>testcloud2</descricao></ip></networkapi>");
        
        Ipv4 ip = (Ipv4) this.api.getAvailableIpForVip(environmentVip, name, false);
        assertNotNull(ip);
        assertEquals(Integer.valueOf(36), ip.getOct4());
        assertEquals(Integer.valueOf(1), ip.getOct3());
        assertEquals(Integer.valueOf(170), ip.getOct2());
        assertEquals(Integer.valueOf(200), ip.getOct1());
        assertEquals(Long.valueOf(50335), ip.getId());
        assertEquals(Long.valueOf(1645), ip.getNetworkId());
    }
    
    @Test
    public void testGetAvailableIp6ForVip() throws GloboNetworkException {
    	Long environmentVip = 23l;
    	String name = "testcloud2";
    	String ipAddr = "2001:0db8:85a3:0042:1000:8a2e:0370:7334";
    	
    	this.rp.registerFakeRequest(HttpMethod.POST, "/ip/availableip6/vip/" + environmentVip + "/",
    			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ipv6><id>1</id><idvlan>1</idvlan><block1>2001</block1><block2>0db8</block2><block3>85a3</block3><block4>0042</block4><block5>1000</block5><block6>8a2e</block6><block7>0370</block7><block8>7334</block8><descricao>teste</descricao><networkipv6>1</networkipv6></ipv6></networkapi>");
    	
    	Ip ip = this.api.getAvailableIpForVip(environmentVip, name, true);
    	assertNotNull(ip);
    	assertEquals(ipAddr, ip.getIpString());
   }

   @Test
   public void testCheckVipIpForIpv4() throws GloboNetworkException {
       Long environmentVipId = 23l;
       String ipAddr = "200.12.3.4";
               
       this.rp.registerFakeRequest(HttpMethod.POST, "/ip/checkvipip/",
               "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ip><oct4>4</oct4><oct2>12</oct2><oct3>3</oct3><oct1>200</oct1><networkipv4>1645</networkipv4><id>50438</id><descricao>testcloud2</descricao></ip></networkapi>");
       
       Ip ip = this.api.checkVipIp(ipAddr, environmentVipId, false);
       assertNotNull(ip);
       assertEquals(ipAddr, ip.getIpString());
       assertEquals(Long.valueOf(50438), ip.getId());
       assertEquals(Long.valueOf(1645), ip.getNetworkId());
   }
   
   @Test
   public void testCheckVipIpForIpv6() throws GloboNetworkException {
       Long environmentVipId = 23l;
       String ipAddr = "2001:0db8:85a3:0042:1000:8a2e:0370:7334";
               
       this.rp.registerFakeRequest(HttpMethod.POST, "/ip/checkvipip/",
               "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ipv6><id>1</id><idvlan>1</idvlan><block1>2001</block1><block2>0db8</block2><block3>85a3</block3><block4>0042</block4><block5>1000</block5><block6>8a2e</block6><block7>0370</block7><block8>7334</block8><descricao>teste</descricao><networkipv6>1</networkipv6></ipv6></networkapi>");
       
       Ip ip = this.api.checkVipIp(ipAddr, environmentVipId, true);
       assertNotNull(ip);
       assertEquals(ipAddr, ip.getIpString());
       assertEquals(Long.valueOf(1), ip.getId());
       assertEquals(Long.valueOf(1), ip.getNetworkId());
   }

   @Test
   public void testCheckVipIpWhenIpDoesnotExist() throws GloboNetworkException {
       Long environmentVipId = 23l;
       String ipAddr = "200.12.3.4";
               
       this.rp.registerFakeRequest(HttpMethod.POST, "/ip/checkvipip/", 500,
               "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><erro><codigo>0334</codigo><descricao>Ipv4 não está relacionado ao Ambiente Vip: Homologacao - Usuario Interno - Homologacao BE-TESTE API.</descricao></erro></networkapi>");
       
       Ip ip = this.api.checkVipIp(ipAddr, environmentVipId, false);
       assertNull(ip);
   }

   @Test
   public void testFindIpsByEquipmentReturnsIpv4() throws GloboNetworkException {
	   Long equipId = 1L;
	   this.rp.registerFakeRequest(HttpMethod.GET, "/ip/getbyequip/1/", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ips><ipv4><oct4>36</oct4><oct2>170</oct2><oct3>1</oct3><oct1>200</oct1><id_rede>123</id_rede><id>455</id><descricao>teste</descricao></ipv4><ipv6></ipv6></ips></networkapi>");
	   
	   List<Ip> ips = this.api.findIpsByEquipment(equipId);
	   
	   assertEquals(1, ips.size());
	   assertEquals(Ipv4.class, ips.get(0).getClass());
   }
   
   @Test
   public void testFindIpsByEquipmentReturnsIpv6() throws GloboNetworkException {
	   Long equipId = 1L;
	   this.rp.registerFakeRequest(HttpMethod.GET, "/ip/getbyequip/1/", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ips><ipv4></ipv4><ipv6><id>1</id><idvlan>1</idvlan><block1>2001</block1><block2>0db8</block2><block3>85a3</block3><block4>0042</block4><block5>1000</block5><block6>8a2e</block6><block7>0370</block7><block8>7334</block8><descricao>teste</descricao></ipv6></ips></networkapi>");
	   
	   List<Ip> ips = this.api.findIpsByEquipment(equipId);
	   
	   assertEquals(1, ips.size());
	   assertEquals(Ipv6.class, ips.get(0).getClass());
   }
   
   @Test
   public void testFindIpsByEquipmentReturnsIpv6AndIpv4() throws GloboNetworkException {
	   Long equipId = 1L;
	   this.rp.registerFakeRequest(HttpMethod.GET, "/ip/getbyequip/1/", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ips><ipv4><oct4>36</oct4><oct2>170</oct2><oct3>1</oct3><oct1>200</oct1><id_rede>123</id_rede><id>455</id><descricao>teste</descricao></ipv4><ipv6><id>1</id><idvlan>1</idvlan><block1>2001</block1><block2>0db8</block2><block3>85a3</block3><block4>0042</block4><block5>1000</block5><block6>8a2e</block6><block7>0370</block7><block8>7334</block8><descricao>teste</descricao></ipv6></ips></networkapi>");
	   
	   List<Ip> ips = this.api.findIpsByEquipment(equipId);
	   
	   assertEquals(2, ips.size());
	   assertEquals(Ipv4.class, ips.get(0).getClass());
	   assertEquals(Ipv6.class, ips.get(1).getClass());
   }
}

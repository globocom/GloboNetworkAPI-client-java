package com.globo.globonetwork.client.api;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Ip;

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
	public void testGetIpv4() throws GloboNetworkException {
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip/get-ipv4/13/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ipv4><oct4>60</oct4><equipamentos>HOST</equipamentos><oct2>16</oct2><oct3>0</oct3><oct1>172</oct1><networkipv4>1</networkipv4><id>13</id><descricao/></ipv4></networkapi>");

		Ip ip = this.api.getIpv4(13L);
		assertEquals(Long.valueOf(13), ip.getId());
		assertEquals(Integer.valueOf(172), ip.getOct1());
		assertEquals(Integer.valueOf(16), ip.getOct2());
		assertEquals(Integer.valueOf(0), ip.getOct3());
		assertEquals(Integer.valueOf(60), ip.getOct4());
		assertEquals(Arrays.asList("HOST"), ip.getEquipments());
	}

	@Test
	public void testFindByIdAndEnvironmentReturnsEmpty() throws GloboNetworkException {
		String ipAddr = "10.170.20.10";
		Long environmentId = 120L;
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip/" + ipAddr + "/ambiente/" + environmentId + "/", 500,
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><erro><codigo>0119</codigo><descricao>IP not registered</descricao></erro></networkapi>");
		
		Ip ip = this.api.findByIpAndEnvironment(ipAddr, environmentId);
		assertNull(ip);
	}
	
	@Test
	public void testFindByIdAndEnvironmentReturnsIp() throws GloboNetworkException {
		String ipAddr = "10.170.20.10";
		Long environmentId = 120L;
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip/" + ipAddr + "/ambiente/" + environmentId + "/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ip><oct4>10</oct4><oct2>170</oct2><oct3>20</oct3><oct1>10</oct1><id>44152</id><descricao>eth0</descricao></ip></networkapi>");
		
		Ip ip = this.api.findByIpAndEnvironment(ipAddr, environmentId);
		assertEquals(ipAddr, ip.getOct1() + "." + ip.getOct2() + "." + ip.getOct3() + "." + ip.getOct4());
		assertEquals(Long.valueOf(44152), ip.getId());
	}
	
	@Test
	public void testSaveIpv4ReturnsIp() throws GloboNetworkException {
		String ipAddr = "10.170.20.10";
		Long equipId = 13204L;
		String nicDescription = "eth0";
		Long networkId = 1722L;
				
		this.rp.registerFakeRequest(HttpMethod.POST, "/ipv4/save/", 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ip><oct4>10</oct4><oct2>170</oct2><oct3>20</oct3><oct1>10</oct1><equipamento><grupos>23</grupos><nome>E834C08665114C68871CB5739B13C0</nome><modelo>18</modelo><id>13204</id><tipo_equipamento>10</tipo_equipamento></equipamento><id>44152</id><descricao>eth0</descricao></ip></networkapi>");		
		
		Ip ip = this.api.saveIpv4(ipAddr, equipId, nicDescription, networkId);
		assertEquals(ipAddr, ip.getOct1() + "." + ip.getOct2() + "." + ip.getOct3() + "." + ip.getOct4());
		assertEquals(Long.valueOf(44152), ip.getId());
	}
	
	@Test
	public void testDeleteIpv4() throws GloboNetworkException {
		Long ipv4Id = 44244l;
				
		this.rp.registerFakeRequest(HttpMethod.GET, "/ip4/delete/" + ipv4Id + "/",
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"/>");
		
		this.api.deleteIpv4(ipv4Id);
	}

   @Test
    public void testGetAvailableIp4ForVip() throws GloboNetworkException {
        Long environmentVip = 23l;
        String name = "testcloud2";
                
        this.rp.registerFakeRequest(HttpMethod.POST, "/ip/availableip4/vip/" + environmentVip + "/",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ip><oct4>36</oct4><oct2>170</oct2><oct3>1</oct3><oct1>200</oct1><networkipv4>1645</networkipv4><id>50335</id><descricao>testcloud2</descricao></ip></networkapi>");
        
        Ip ip = this.api.getAvailableIp4ForVip(environmentVip, name);
        assertNotNull(ip);
        assertEquals(Integer.valueOf(36), ip.getOct4());
        assertEquals(Integer.valueOf(1), ip.getOct3());
        assertEquals(Integer.valueOf(170), ip.getOct2());
        assertEquals(Integer.valueOf(200), ip.getOct1());
        assertEquals(Long.valueOf(50335), ip.getId());
        assertEquals(Long.valueOf(1645), ip.getNetworkId());
    }

   @Test
   public void testCheckVipIp() throws GloboNetworkException {
       Long environmentVipId = 23l;
       String ipAddr = "200.12.3.4";
               
       this.rp.registerFakeRequest(HttpMethod.POST, "/ip/checkvipip/",
               "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><ip><oct4>4</oct4><oct2>12</oct2><oct3>3</oct3><oct1>200</oct1><networkipv4>1645</networkipv4><id>50438</id><descricao>testcloud2</descricao></ip></networkapi>");
       
       Ip ip = this.api.checkVipIp(ipAddr, environmentVipId);
       assertNotNull(ip);
       assertEquals(ipAddr, ip.getIpString());
       assertEquals(Long.valueOf(50438), ip.getId());
       assertEquals(Long.valueOf(1645), ip.getNetworkId());
   }

   @Test
   public void testCheckVipIpWhenIpDoesnotExist() throws GloboNetworkException {
       Long environmentVipId = 23l;
       String ipAddr = "200.12.3.4";
               
       this.rp.registerFakeRequest(HttpMethod.POST, "/ip/checkvipip/", 500,
               "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><erro><codigo>0334</codigo><descricao>Ipv4 não está relacionado ao Ambiente Vip: Homologacao - Usuario Interno - Homologacao BE-TESTE API.</descricao></erro></networkapi>");
       
       Ip ip = this.api.checkVipIp(ipAddr, environmentVipId);
       assertNull(ip);
   }

}

package com.globo.globonetwork.client.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Real.RealIP;
import com.globo.globonetwork.client.model.Vip;

@RunWith(JUnit4.class)
public class VipAPITest {
	
	private VipAPI api;
	private TestRequestProcessor rp;
	
	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
		this.api = this.rp.getVipAPI();
	}

	@Test
	public void testGetByIdReturnsNull() throws GloboNetworkException {
		Long vipId = 6374000L;
		
		this.rp.registerFakeRequest(HttpMethod.GET, "/requestvip/getbyid/" + vipId + "/", 500, 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><erro><codigo>0152</codigo><descricao>Request VIP is not registered.</descricao></erro></networkapi>");
		
		assertNull(this.api.getById(vipId));
	}
	
	@Test
	public void testGetByIdReturnsSingleVip() throws GloboNetworkException {
		Long vipId = 6374L;
		this.rp.registerFakeRequest(HttpMethod.GET, "/requestvip/getbyid/" + vipId + "/", 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\">"
			+ "<vip><persistencia>cookie</persistencia><metodo_bal>least-conn</metodo_bal><environments>PROD</environments><ips>192.168.90.130</ips>"
			+ "<expect_string>WORKING</expect_string><match_list>WORKING</match_list><ipv6_description/><id>6374</id><maxcon>300</maxcon>"
			+ "<portas_servicos><porta>80:8180</porta></portas_servicos><ipv4_description>test.networkapi.globoi.com</ipv4_description>"
			+ "<vip_criado>True</vip_criado><id_healthcheck_expect>6</id_healthcheck_expect><reals>"
			+ "<real><id_ip>44401</id_ip><port_real>8080</port_real><real_name>real_name_1</real_name><port_vip>80</port_vip><real_ip>172.10.0.2</real_ip></real>"
			+ "<real><id_ip>44401</id_ip><port_real>8443</port_real><real_name>real_name_2</real_name><port_vip>443</port_vip><real_ip>172.10.0.2</real_ip></real>"
			+ "</reals><healthcheck_type>HTTP</healthcheck_type><healthcheck>GET /healthcheck HTTP/1.0\\r\\nHost:teste.networkapi.globoi.com\\r\\n\\r\\n</healthcheck>"
			+ "<host>test.networkapi.globoi.com</host><validado>True</validado></vip>"
			+ "</networkapi>");
		
		Vip vip = this.api.getById(vipId);
		
		assertNotNull(vip);
		assertEquals(vipId, vip.getId());
		assertEquals(1, vip.getIps().size());
		assertEquals("192.168.90.130", vip.getIps().get(0));
		assertEquals("test.networkapi.globoi.com", vip.getIpv4Description());
		assertEquals(true, vip.getCreated());
		assertEquals("test.networkapi.globoi.com", vip.getHost());
		assertEquals(true, vip.getValidated());
		assertEquals("cookie", vip.getPersistence());
		assertEquals("least-conn", vip.getMethod());
		assertEquals((Integer) 300, vip.getMaxConn());
		assertEquals(1, vip.getServicePorts().size());
		
		assertNotNull(vip.getRealsIp());
		assertEquals(2, vip.getRealsIp().size());
		
		RealIP firstRealIP = vip.getRealsIp().get(0);
		assertEquals(Long.valueOf(44401), firstRealIP.getIpId());
		assertEquals(Integer.valueOf(8080), firstRealIP.getRealPort());
		assertEquals("real_name_1", firstRealIP.getName());
		assertEquals(Integer.valueOf(80), firstRealIP.getVipPort());
		assertEquals("172.10.0.2", firstRealIP.getRealIp());
	}
	
	@Test
	public void testAddVipReturnsVip() throws GloboNetworkException {
	    Long vipId = 9999L;
	    String port = "80:8080";
	    String purpose = "Purpose";
	    String client = "Client";
	    String environment = "Test";
	    String cache = "(none)";
	    String methodBal = "least-conn";
	    String stickness = "(none)";
	    String healthcheckType = "TCP";
	    Integer timeout = 10;
	    String host = "host";
	    Integer maxConn = 5;
	    String businessArea = "viptest";
	    String service = "viptest";
	    
	    this.rp.registerFakeRequest(HttpMethod.POST, "/requestvip/", 
	            "<networkapi versao=\"1.0\"><requisicao_vip><id>" + vipId + "</id></requisicao_vip></networkapi>");
	    this.rp.registerFakeRequest(HttpMethod.GET, "/requestvip/getbyid/" + vipId + "/", 
	            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\">"
                + "<vip><persistencia>" + stickness + "</persistencia><metodo_bal>"
	                    + methodBal + "</metodo_bal><environments>" + environment + "</environments>"
                + "<id>" + vipId + "</id><maxcon>" + maxConn + "</maxcon>"
                + "<portas_servicos><porta>" + port + "</porta></portas_servicos>"
                + "<healthcheck_type>" + healthcheckType + "</healthcheck_type>"
                + "<host>" + host + "</host></vip>"
                + "</networkapi>");
	    
	    List<String> ports = new ArrayList<String>();
	    ports.add(port);
	    Vip vip = this.api.add(9876L, null, null, purpose, client, environment,
                cache, methodBal, stickness, healthcheckType, null,
                timeout, host, maxConn, businessArea, service, null,
                new ArrayList<RealIP>(), null, null, ports, null);
	    assertNotNull(vip);
	    
	    assertEquals(vipId, vip.getId());
        assertEquals(host, vip.getHost());
        assertEquals(stickness, vip.getPersistence());
        assertEquals(methodBal, vip.getMethod());
        assertEquals(maxConn, vip.getMaxConn());
        assertEquals(1, vip.getServicePorts().size());
        assertEquals(port, vip.getServicePorts().get(0));
	}
}

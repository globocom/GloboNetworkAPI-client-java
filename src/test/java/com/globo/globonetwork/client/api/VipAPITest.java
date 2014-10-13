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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
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
    public void testGetByIpReturnsEmptyWhenNotFound() throws GloboNetworkException {
        this.rp.registerFakeRequest(HttpMethod.POST, "/requestvip/get_by_ip_id/", 200, 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><total>0</total><vips></vips></networkapi>");
        
        assertEquals(Collections.emptyList(), this.api.getByIp("200.170.1.199"));
    }

   @Test
   public void testGetByIpReturnsValidList() throws GloboNetworkException {
       this.rp.registerFakeRequest(HttpMethod.POST, "/requestvip/get_by_ip_id/", 200, 
               "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><vips><id>999</id></vips></networkapi>");
       this.rp.registerFakeRequest(HttpMethod.GET, "/requestvip/getbyid/999/", 
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
       
       List<Vip> result = this.api.getByIp("200.170.1.199");
       assertEquals(1, result.size());
       for (Vip vip : result) {
           assertNotNull(vip);
           assertNotNull(vip.getId());
           assertNotNull(vip.getValidated());
           assertNotNull(vip.getCreated());
       }
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
	
	@Test
	public void testBasicVipObjectSerialization() {
	    Vip vip = new Vip();
	    vip.setPersistence("(nenhum)");
	    vip.setMethod("least-conn");
	    vip.setMaxConn(5);
	    vip.setServicePorts(Arrays.asList("80:8080"));
	    vip.setCache("(nenhum)");
	    vip.setEnvironment("QA BE-TESTE API");
	    vip.setHealthcheckType("TCP");
	    vip.setFinality("Homologacao");
	    vip.setClient("Usuario Interno");
	    vip.setIpv4Id(50000l);
	    vip.setServiceName("cloud");
	    vip.setTimeout(10);
	    vip.setBusinessArea("cloud");
	    vip.addReal("vm_xpto", "10.20.30.40");
	    
	    GloboNetworkRoot<Vip> gnroot = new GloboNetworkRoot<Vip>();
	    gnroot.getObjectList().add(vip);
	    gnroot.set("vip", vip);
	    
	    assertEquals("<?xml version=\"1.0\"?><networkapi xmlns=\"http://unknown/\"><vip>"
                + "<ambiente>QA BE-TESTE API</ambiente>"
                + "<areanegocio>cloud</areanegocio>"
                + "<cache>(nenhum)</cache>"
                + "<cliente>Usuario Interno</cliente>"
                + "<finalidade>Homologacao</finalidade>"
                + "<healthcheck_type>TCP</healthcheck_type>"
                + "<id_healthcheck_expect />"
                + "<id_ipv4>50000</id_ipv4>"
                + "<maxcon>5</maxcon>"
                + "<metodo_bal>least-conn</metodo_bal>"
                + "<nome_servico>cloud</nome_servico>"
                + "<persistencia>(nenhum)</persistencia>"
                + "<portas_servicos><porta>80:8080</porta></portas_servicos>"
                + "<reals><real><real_ip>10.20.30.40</real_ip><real_name>vm_xpto</real_name></real></reals>"
                + "<reals_prioritys><reals_priority /></reals_prioritys>"
                + "<reals_weights><reals_weight /></reals_weights>"
                + "<timeout>10</timeout>"
	            + "</vip></networkapi>",
	            gnroot.toString());
	    
	}
}

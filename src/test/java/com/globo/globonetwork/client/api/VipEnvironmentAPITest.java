package com.globo.globonetwork.client.api;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.TestRequestProcessor;
import com.globo.globonetwork.client.TestRequestProcessor.HttpMethod;
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.VipEnvironment;

@RunWith(JUnit4.class)
public class VipEnvironmentAPITest {
	
	private VipEnvironmentAPI api;
	private TestRequestProcessor rp;
	
	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
		this.api = this.rp.getVipEnvironmentAPI();
	}

	@Test
	public void testListAllReturnsEmptyList() throws GloboNetworkException {
		
		this.rp.registerFakeRequest(HttpMethod.GET, "/environmentvip/all/", 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"></networkapi>");
		
		List<VipEnvironment> vipEnvs = this.api.listAll();
		assertNotNull(vipEnvs);
		assertTrue(vipEnvs.isEmpty());
	}
	
	@Test
	public void testListAllReturnsVipEnvironments() throws GloboNetworkException {

		this.rp.registerFakeRequest(HttpMethod.GET, "/environmentvip/all/", 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\">"
			+ "<environment_vip><cliente_txt>User</cliente_txt><id>1</id><finalidade_txt>Production</finalidade_txt><ambiente_p44_txt>Front-end</ambiente_p44_txt></environment_vip>"
			+ "<environment_vip><cliente_txt>Web</cliente_txt><id>2</id><finalidade_txt>QA</finalidade_txt><ambiente_p44_txt>Streaming</ambiente_p44_txt></environment_vip>"
			+ "</networkapi>");
		
		List<VipEnvironment> vipEnvs = this.api.listAll();
		
		assertNotNull(vipEnvs);
		assertEquals(2, vipEnvs.size());
		
		VipEnvironment firstEnv = vipEnvs.get(0);
		assertEquals("User", firstEnv.getClient());
		assertEquals("Production", firstEnv.getFinality());
		assertEquals("Front-end", firstEnv.getEnvironmentName());
		
		VipEnvironment secondEnv = vipEnvs.get(1);
		assertEquals("Web", secondEnv.getClient());
        assertEquals("QA", secondEnv.getFinality());
        assertEquals("Streaming", secondEnv.getEnvironmentName());
	}
	
	@Test
	public void testSearchNoParameters() throws GloboNetworkException {
	    this.rp.registerFakeRequest(HttpMethod.POST, "/environmentvip/search/", 500,
	            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\"><erro><codigo>0287</codigo><descricao>At least one of the parameters have to be informed to query</descricao></erro></networkapi>");
	    
	    try {
	        this.api.search(null, null, null, null);
	        fail();
	    } catch (GloboNetworkErrorCodeException ex) {
	        assertEquals(287, ex.getCode());
	        assertEquals("At least one of the parameters have to be informed to query", ex.getDescription());
	    } catch (GloboNetworkException ex) {
	        // Should've been able to identify the error
	        fail();
	    }
	}
	
    @Test
    public void testSearchById() throws GloboNetworkException {
        this.rp.registerFakeRequest(HttpMethod.POST, "/environmentvip/search/",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><networkapi versao=\"1.0\">"
                + "<environment_vip><cliente_txt>User</cliente_txt><id>9999</id><finalidade_txt>QA</finalidade_txt><ambiente_p44_txt>QA TEST</ambiente_p44_txt></environment_vip>"
                + "</networkapi>");
        
        Long vipEnvId = 9999L;
        
        VipEnvironment vipEnv = this.api.search(vipEnvId, null, null, null);
        assertNotNull(vipEnv);
        assertEquals(vipEnvId, vipEnv.getId());
        assertEquals("User", vipEnv.getClient());
        assertEquals("QA", vipEnv.getFinality());
        assertEquals("QA TEST", vipEnv.getEnvironmentName());
    }	
}

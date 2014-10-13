package com.globo.globonetwork.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;

@RunWith(JUnit4.class)
public class RequestProcessorTest {
	
	private RequestProcessor rp;

	@Before
	public void setUp() {
		this.rp = new TestRequestProcessor();
	}

	@Test
	public void handleExceptionDoNothingWhenStatusCodeIs200() throws GloboNetworkException {
		rp.handleExceptionIfNeeded(200, "sdjds");
	}

	@Test
	public void handleExceptionRaisesNetworkAPIErrorCodeExceptionWhenStatusCodeAre500() throws GloboNetworkException {
		String code = "0108";
		String description = "the VLAN name duplicated within an environment informed";
		try {
			rp.handleExceptionIfNeeded(500, "<networkapi versao=\"1.0\"><erro><codigo>" + code + "</codigo><descricao>" + description + "</descricao></erro></networkapi>");
			fail();
		} catch (GloboNetworkErrorCodeException e) {
			assertEquals(Integer.parseInt(code), e.getCode());
			assertEquals(description, e.getDescription());
		}
	}

	@Test
	public void handleExceptionRaisesNetworkAPIExceptionWhenXmlIsInvalid() throws GloboNetworkException {
		String content = "Request error";
		try {
			rp.handleExceptionIfNeeded(500, content);
			fail();
		} catch (GloboNetworkException e) {
			assertEquals(content, e.getMessage());
		}
	}

}

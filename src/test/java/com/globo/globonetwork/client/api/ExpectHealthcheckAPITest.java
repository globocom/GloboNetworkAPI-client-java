package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpXMLRequestProcessor;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.healthcheck.ExpectHealthcheck;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpectHealthcheckAPITest extends TestCase {


    public void testListHealthcheck() throws GloboNetworkException {
        List<ExpectHealthcheck> expectHealthchecks = new ArrayList<ExpectHealthcheck>();
        expectHealthchecks.add(new ExpectHealthcheck(1l, "OK"));

        HttpXMLRequestProcessor processor = mock(HttpXMLRequestProcessor.class);
        GloboNetworkRoot<ExpectHealthcheck> result = new GloboNetworkRoot<ExpectHealthcheck>();
        result.setObjectList( expectHealthchecks);
        when(processor.get("/healthcheckexpect/distinct/busca/", ExpectHealthcheck.class)).thenReturn(result);

        ExpectHealthcheckAPI api = new ExpectHealthcheckAPI(processor);

        expectHealthchecks = api.listHealthcheck();
        assertNotNull(expectHealthchecks);
        assertEquals(1, expectHealthchecks.size());

        assertEquals((Long)1l, expectHealthchecks.get(0).getId());
        assertEquals("OK", expectHealthchecks.get(0).getExpect());
    }

    public void testListHealthcheck_fail() throws GloboNetworkException {
        HttpXMLRequestProcessor processor = mock(HttpXMLRequestProcessor.class);
        GloboNetworkRoot<ExpectHealthcheck> result = new GloboNetworkRoot<ExpectHealthcheck>();

        when(processor.get("/healthcheckexpect/distinct/busca/", ExpectHealthcheck.class)).thenThrow(new GloboNetworkErrorCodeException(404, "not found"));

        ExpectHealthcheckAPI api = new ExpectHealthcheckAPI(processor);

        try {
            api.listHealthcheck();
            fail();
        } catch (GloboNetworkErrorCodeException e) {
            assertEquals(404, e.getCode());
            assertEquals("not found", e.getDescription());
        }

    }
}
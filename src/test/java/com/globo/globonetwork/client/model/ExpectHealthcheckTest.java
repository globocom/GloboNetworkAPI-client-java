package com.globo.globonetwork.client.model;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.TestUtil;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpXMLRequestProcessor;
import com.globo.globonetwork.client.model.healthcheck.ExpectHealthcheck;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;


public class ExpectHealthcheckTest extends TestCase {

    @Test
    public void testParseXML() throws GloboNetworkException {
        String output = TestUtil.getSample("healthcheck_listExpect.xml");
        GloboNetworkRoot<ExpectHealthcheck> result = new HttpXMLRequestProcessor("", "", "").readXML(output, ExpectHealthcheck.class);

        List<ExpectHealthcheck> objectList = result.getObjectList();

        assertEquals(2, objectList.size());

        ExpectHealthcheck healthcheckExpect = objectList.get(0);
        assertEquals((Long) 1l, healthcheckExpect.getId());
        assertEquals("WORKING", healthcheckExpect.getExpect());

        healthcheckExpect = objectList.get(1);
        assertEquals((Long)2l, healthcheckExpect.getId());
        assertEquals("OK", healthcheckExpect.getExpect());
    }
}
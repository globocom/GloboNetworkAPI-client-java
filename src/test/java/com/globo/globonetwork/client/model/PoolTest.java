package com.globo.globonetwork.client.model;

import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessorTest;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PoolTest extends TestCase {

    @Before
    public void setUp() {

    }

    @Test
    public void testParse() throws IOException {

        InputStream stream = pool_123();
        Pool po = HttpJSONRequestProcessorTest.parse(stream, Pool.class);
        assertNotNull(po);

        Pool.ServerPool serverpool = po.getServerPool();
        assertNotNull(serverpool);
        assertEquals( 123l , serverpool.getId().longValue());
        assertEquals("connection" , serverpool.getLbMethod());
        assertEquals(80 , serverpool.getDefaultPort());
        assertEquals(32 , serverpool.getDefaultLimit());
        assertEquals("VIP_777_pool_111" , serverpool.getIdentifier());
        assertEquals(true , serverpool.isPoolCreated());

        Pool.ServerPool.HealthCheck healthCheck = serverpool.getHealthCheck();
        assertNotNull(healthCheck);
        assertEquals("TCP", healthCheck.getHealthcheckType());
        assertEquals("_*:*_", healthCheck.getDestination());
        assertEquals("h_123", healthCheck.getHealthcheckExpect());
        assertEquals("h_444", healthCheck.getHealthcheckRequest());
        assertEquals("h_iii", healthCheck.getIdentifier());
        assertEquals(155l, healthCheck.getId().longValue());


    }



    public static InputStream pool_123() {

        String pool = null;

        InputStream stream = PoolTest.class.getClassLoader().getResourceAsStream("pool_123.json");

//            pool = IOUtils.toString(stream, "UTF-8");
        return stream;
    }

}

package com.globo.globonetwork.client.model;

import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PoolTest extends TestCase {


    public static final String POOL_LIST_ALL_BY_VIP_JSON = "pool_listAllByReq.json";

    @Before
    public void setUp() {

    }

    @Test
    public void testListByEnvVipId() throws IOException {
        InputStream jsonStream = getSample(POOL_LIST_ALL_BY_VIP_JSON);

        Pool.PoolList pools = HttpJSONRequestProcessor.parse(jsonStream, Pool.PoolList.class);
        assertEquals(2, pools.getPools().size());

        Pool serverPool1 = pools.getPools().get(0);
        assertEquals((Long)11l, serverPool1.getId());
        assertEquals("VIP_pool_80", serverPool1.getIdentifier());
        assertEquals(80, serverPool1.getDefaultPort());
        assertEquals(true, serverPool1.isPoolCreated());
        assertEquals("FE_BE", serverPool1.getEnvironment().getName());

        Pool.Healthcheck healthcheck = serverPool1.getHealthcheck();

        assertEquals("*:*", healthcheck.getDestination());
        assertEquals("GET /healthcheck.html", healthcheck.getHealthcheckRequest());
        assertEquals("HTTP", healthcheck.getHealthcheckType());
        assertEquals("200 OK", healthcheck.getExpectedHealthcheck());

        Pool serverPool2 = pools.getPools().get(1);
        assertEquals((Long)12l, serverPool2.getId());
        assertEquals("VIP_pool_8080", serverPool2.getIdentifier());
        assertEquals(8080, serverPool2.getDefaultPort());
        assertEquals(true, serverPool2.isPoolCreated());
        assertEquals("FE_BALANCER", serverPool2.getEnvironment().getName());

        Pool.Healthcheck healthcheck2 = serverPool2.getHealthcheck();

        assertEquals("*:*", healthcheck2.getDestination());
        assertEquals("", healthcheck2.getHealthcheckRequest());
        assertEquals("TCP", healthcheck2.getHealthcheckType());
        assertEquals(null, healthcheck2.getExpectedHealthcheck());
    }


    public static InputStream getSample(String value) {
        InputStream stream = PoolTest.class.getClassLoader().getResourceAsStream(value);
        return stream;
    }

}

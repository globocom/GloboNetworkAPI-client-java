package com.globo.globonetwork.client.model;

import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PoolTest extends TestCase {


    public static final String POOL_LIST_BY_ENV_VIP_JSON = "pool_listByEnvVip.json";

    @Before
    public void setUp() {

    }

    @Test
    public void testListByEnvVipId() throws IOException {
        InputStream jsonStream = getSample(POOL_LIST_BY_ENV_VIP_JSON);

        Pool.PoolList pools = HttpJSONRequestProcessor.parse(jsonStream, Pool.PoolList.class);
        assertEquals(2, pools.size());

        Pool serverPool1 = pools.get(0);
        assertEquals((Long)11111l, serverPool1.getId());
        assertEquals("DANIEL", serverPool1.getIdentifier());
        assertEquals(80, serverPool1.getDefaultPort());
        assertEquals(false, serverPool1.isPoolCreated());
        assertEquals((Long)121l, serverPool1.getEnvironment());
        assertEquals((Long)33l, serverPool1.getHealthcheck());

        Pool serverPool2 = pools.get(1);
        assertEquals((Long)22222l, serverPool2.getId());
        assertEquals("DANIEL_2", serverPool2.getIdentifier());
        assertEquals(81, serverPool2.getDefaultPort());
        assertEquals(true, serverPool2.isPoolCreated());
        assertEquals((Long)121l, serverPool2.getEnvironment());
        assertEquals((Long)43l, serverPool2.getHealthcheck());
    }


    public static InputStream getSample(String value) {
        InputStream stream = PoolTest.class.getClassLoader().getResourceAsStream(value);
        return stream;
    }

}

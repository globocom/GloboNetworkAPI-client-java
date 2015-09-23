package com.globo.globonetwork.client.model;

import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class PoolTest extends TestCase {


    public static final String POOL_LIST_ALL_BY_VIP_JSON = "pool_listAllByReq.json";
    private static final String POOL_GET_BY_PK_JSON = "pool_getByPK.json";

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
        assertEquals((Integer) 0, serverPool1.getMaxconn());
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
        assertEquals((Integer)3, serverPool2.getMaxconn());
        assertEquals("FE_BALANCER", serverPool2.getEnvironment().getName());

        Pool.Healthcheck healthcheck2 = serverPool2.getHealthcheck();

        assertEquals("*:*", healthcheck2.getDestination());
        assertEquals("", healthcheck2.getHealthcheckRequest());
        assertEquals("TCP", healthcheck2.getHealthcheckType());
        assertEquals(null, healthcheck2.getExpectedHealthcheck());
    }

    @Test
    public void testGetByPK() throws IOException {
        InputStream jsonStream = getSample(POOL_GET_BY_PK_JSON);

        Pool.PoolResponse poolResponse = HttpJSONRequestProcessor.parse(jsonStream, Pool.PoolResponse.class);

        Pool pool = poolResponse.getPool();
        assertEquals("least-conn", pool.getLbMethod());

        //pool
        assertEquals((Long)171l, pool.getId());
        assertEquals("ACS_POOL_1", pool.getIdentifier() );
        assertEquals(8080, pool.getDefaultPort());
        assertEquals((Integer)8, pool.getMaxconn());

        //healthcheck
        Pool.Healthcheck healthcheck = pool.getHealthcheck();
        assertEquals((Long)101l, healthcheck.getId());
        assertEquals("HTTP", healthcheck.getHealthcheckType() );
        assertEquals("GET /heal.html", healthcheck.getHealthcheckRequest() );
        assertEquals("WORKING", healthcheck.getExpectedHealthcheck() );
        assertEquals("*:*", healthcheck.getDestination() );


        assertEquals((Long)2009l, pool.getEnvironment().getId());

        //serviceDownAction
        assertEquals("ServiceDownAction", pool.getServiceDownAction().getType());
        assertEquals((Long)5l, pool.getServiceDownAction().getId());
        assertEquals("none", pool.getServiceDownAction().getName());

        //real
        assertEquals(1, poolResponse.getPoolMembers().size());


        Pool.PoolMember poolMember = poolResponse.getPoolMembers().get(0);
        assertEquals((Long)131313l, poolMember.getId());
        assertEquals("VM_EQP_NAME", poolMember.getEquipmentName());
        assertEquals((Long)159l, poolMember.getEquipmentId());
        assertEquals("10.1.1.1", poolMember.getIp().getIpFormated());
        assertEquals("10.1.1.1", poolMember.getIpFormated());
        assertEquals((Long)1001l, poolMember.getIpId());
        assertEquals((Long)1001l, poolMember.getIp().getId());
        assertEquals((Integer) 8080, poolMember.getPortReal());
        assertEquals((Integer)9, poolMember.getPriority());
        assertEquals((Integer) 23, poolMember.getWeight());
    }


    public static InputStream getSample(String value) {
        InputStream stream = PoolTest.class.getClassLoader().getResourceAsStream(value);
        return stream;
    }

}

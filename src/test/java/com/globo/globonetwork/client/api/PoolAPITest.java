package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.Pool;
import com.google.api.client.json.GenericJson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PoolAPITest extends TestCase {

    public static final String POOL_LIST_ALL_BY_VIP_JSON = "pool_listAllByReq.json";
    @Before
    public void setUp() {

    }


    public void testListByReqVipId() throws Exception {
        String listAllResult = getSample(POOL_LIST_ALL_BY_VIP_JSON);

        GenericJson json = new GenericJson();
        json.set("id_vip", 123l);

        HttpJSONRequestProcessor mock = mock(HttpJSONRequestProcessor.class);
        when(mock.post("/api/pools/pool_list_by_reqvip/", json)).thenReturn(listAllResult);

        PoolAPI api = new PoolAPI(mock);

        List<Pool> pools = api.listAllByReqVip(123l);

        assertEquals(2,pools.size());

        Pool serverPool1 = pools.get(0);
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

        Pool serverPool2 = pools.get(1);
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

    public static String getSample(String value) throws Exception{
        InputStream in = PoolAPITest.class.getClassLoader().getResourceAsStream(value);

        InputStreamReader is = new InputStreamReader(in);
        StringBuilder sb=new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();

        while(read != null) {
            //System.out.println(read);
            sb.append(read);
            read =br.readLine();

        }

        return sb.toString();
    }



}

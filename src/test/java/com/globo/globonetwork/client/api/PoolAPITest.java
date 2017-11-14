package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.Pool;
import com.globo.globonetwork.client.model.pool.PoolV3;
import com.globo.globonetwork.client.model.pool.PoolV3Request;
import com.globo.globonetwork.client.model.pool.PoolV3Response;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PoolAPITest extends TestCase {

    public static final String POOL_LIST_ALL_BY_VIP_JSON = "pool_listAllByReq.json";
    private static final String POOL_GET_BY_PK_JSON = "pool_getByPK.json";
    private static final String POOL_GET_BY_IDS_V3 = "pool_getByIdV3.json";

    @Before
    public void setUp() {

    }

    @Test
    public void testCreateV3() throws GloboNetworkException {
        PoolV3 pool = mockPool("TEST_CREATE");
        PoolV3Request poolRequest = PoolV3Request.newPost(pool);

        HttpJSONRequestProcessor mock = mock(HttpJSONRequestProcessor.class);

        when(mock.post("/api/v3/pool/", poolRequest)).thenReturn("[{\"id\": 123}]");

        PoolAPI api = new PoolAPI(mock);

        pool = api.createV3(pool);
        assertEquals((Long) 123l, pool.getId());
    }

    @Test
    public void testGetByIdV3() throws Exception {
        String sample = getSample(POOL_GET_BY_IDS_V3);
        HttpJSONRequestProcessor mock = mock(HttpJSONRequestProcessor.class);
        when(mock.get("/api/v3/pool/123;456/?include=vips")).thenReturn(sample);

        PoolAPI api = new PoolAPI(mock);

        List<PoolV3> byIdsV3 = api.getByIdsV3(Arrays.asList(123l, 456l));

        PoolV3 pool = byIdsV3.get(0);
        assertEquals("least-conn", pool.getLbMethod());

        //pool
        assertEquals((Long)123l, pool.getId());
        assertEquals("ACS_POOL_teste.com_v3", pool.getIdentifier() );
        assertEquals(8080, pool.getDefaultPort());
        assertEquals((Integer)30, pool.getMaxconn());

        //healthcheck
        PoolV3.Healthcheck healthcheck = pool.getHealthcheck();
        assertEquals("HTTP", healthcheck.getHealthcheckType() );
        assertEquals("GET /ginda.html HTTP/1.0\\r\\nHost: teste.com\\r\\n\\r\\n", healthcheck.getHealthcheckRequest() );
        assertEquals("ALIVE!", healthcheck.getExpectedHealthcheck() );
        assertEquals("*:*", healthcheck.getDestination() );


        assertEquals((Long)111l, pool.getEnvironment());

        //serviceDownAction
//        assertEquals("ServiceDownAction", pool.getServiceDownAction().getType());
        assertEquals((Long)5l, pool.getServiceDownAction().getId());
        assertEquals("none", pool.getServiceDownAction().getName());

        //real
        assertEquals(1, pool.getPoolMembers().size());

        PoolV3.PoolMember poolMember = pool.getPoolMembers().get(0);
        assertEquals((Long)321l, poolMember.getId());
        assertEquals("L-TESTE-2", poolMember.getEquipmentName());
        assertEquals((Long)333l, poolMember.getEquipmentId());
        assertEquals("10.0.0.2", poolMember.getIp().getIpFormated());
        assertEquals("10.0.0.2", poolMember.getIpFormated());
        assertEquals((Long)2l, poolMember.getIpId());
        assertEquals((Long)2l, poolMember.getIp().getId());
        assertEquals((Integer) 8080, poolMember.getPortReal());
        assertEquals((Integer) 50, poolMember.getPriority());
        assertEquals((Integer) 23, poolMember.getWeight());
    }

    @Test
    public void testUpdateV3() throws GloboNetworkException {

        PoolV3 pool = new PoolV3();
        pool.setId(123l);

        HttpJSONRequestProcessor mock = mock(HttpJSONRequestProcessor.class);
        PoolV3Request poolRequest = new PoolV3Request();
        poolRequest.addPoolToPut(pool);
        when(mock.put("/api/v3/pool/" + 123l + "/", poolRequest, GenericJson.class)).thenReturn(new PoolV3Response());

        PoolAPI api = new PoolAPI(mock);

        api.updateAll(Arrays.asList(pool));

        verify(mock, times(1)).put("/api/v3/pool/123/", poolRequest, GenericJson.class);
    }

    @Test
    public void testDeleteV3() throws GloboNetworkException {

        HttpJSONRequestProcessor mock = mock(HttpJSONRequestProcessor.class);
        when(mock.delete("/api/v3/pool/" + 123l + "/", GenericJson.class)).thenReturn(new GenericJson());

        PoolAPI api = new PoolAPI(mock);
        api.deleteV3(123l);

        verify(mock, times(1)).delete("/api/v3/pool/123/", GenericJson.class);
    }

    @Test
    public void testUndeployV3() throws GloboNetworkException {

        HttpJSONRequestProcessor mock = mock(HttpJSONRequestProcessor.class);
        when(mock.delete("/api/v3/pool/deploy/" + 123l + "/", GenericJson.class)).thenReturn(new GenericJson());

        PoolAPI api = new PoolAPI(mock);
        api.undeployV3(123l);

        verify(mock, times(1)).delete("/api/v3/pool/deploy/123/", GenericJson.class);
    }

    @Test
    public void testSeparator(){
        HttpJSONRequestProcessor mock = mock(HttpJSONRequestProcessor.class);
        PoolAPI api = new PoolAPI(mock);

        PoolV3 pool = new PoolV3();
        pool.setId(12l);
        PoolV3 pool2 = new PoolV3();
        pool2.setId(13l);
        PoolV3 pool3 = new PoolV3();
        pool3.setId(11l);

        List<PoolV3> poolV3s = Arrays.asList(pool);
        String s = api.separatorPoolIds(poolV3s);
        assertEquals("12", s);

        poolV3s = Arrays.asList(pool, pool2);
        s = api.separatorPoolIds(poolV3s);
        assertEquals("12;13", s);

        poolV3s = Arrays.asList(pool, pool2, pool3);
        s = api.separatorPoolIds(poolV3s);
        assertEquals("12;13;11", s);

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


    public PoolV3 mockPool(String identifier) {
        PoolV3 firstPool = new PoolV3();
        firstPool.setIdentifier(identifier);
        firstPool.setMaxconn(2);
        firstPool.setLbMethod("least-conn");
        firstPool.setDefaultPort(80);
        firstPool.setPoolCreated(false);
        firstPool.setEnvironment(121l);

        PoolV3.Healthcheck healthcheck = new PoolV3.Healthcheck();
        healthcheck.setHealthcheck("HTTP", "WORKING", "/health.html");
        healthcheck.setDestination("*:*");

        firstPool.setHealthcheck(healthcheck);

        PoolV3.ServiceDownAction serviceDownAction = new PoolV3.ServiceDownAction();
        serviceDownAction.setId(5l);
        firstPool.setServiceDownAction(serviceDownAction);

        PoolV3.PoolMember poolMember = new PoolV3.PoolMember();
        poolMember.setId(Data.NULL_LONG);
        poolMember.setPortReal(8080);
        poolMember.setWeight(0);
        poolMember.setPriority(0);
        poolMember.setEquipmentId(3333l);
        poolMember.setEquipmentName("L-TESTE");

        PoolV3.Ip ipv3 = new PoolV3.Ip();
        ipv3.setIpFormated("10.0.0.1");
        ipv3.setId(111l);
        poolMember.setIp(ipv3);
        poolMember.set("ipv6", Data.nullOf(PoolV3.Ip.class));

        firstPool.getPoolMembers().add(poolMember);

        return firstPool;
    }

}

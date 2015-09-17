package com.globo.globonetwork.client.model;

import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import junit.framework.TestCase;

public class VipJsonTest extends TestCase {


    public void testParseSaveResponse() throws IOException {
        InputStream jsonStream = getSample("vip_save.json");
        Vip vip = HttpJSONRequestProcessor.parse(jsonStream, VipJson.class);

        assertEquals((Long)11111l, vip.getId());
        assertEquals("HMG-123", vip.getEnvironment());
        assertEquals("Homologacao - 123", vip.getFinality());
        assertEquals("daniel.com.br", vip.getHost());
        assertEquals("time-123", vip.getBusinessArea());
        assertEquals("user-321", vip.getClient());
        assertEquals((Long)33333l, vip.getIpv4Id());

        assertEquals("service_name_123", vip.getServiceName());
    }

    public void testParseGetByPk() throws IOException {
        InputStream jsonStream = getSample("vip_getByPk.json");
        Vip vip = HttpJSONRequestProcessor.parse(jsonStream, VipJson.class);

        assertEquals("(nenhum)", vip.getPersistence());
        assertEquals("least-conn", vip.getMethod());
        assertEquals((Long)123l, vip.getId());
        assertEquals((Integer)0, vip.getMaxConn());
        assertEquals("(nenhum)", vip.getCache());

        assertEquals("Hmg API", vip.getEnvironment());
        assertEquals(Boolean.TRUE, vip.getCreated());
        assertEquals("HTTP", vip.getHealthcheckType());
        assertEquals("GET /healthcheck.html HTTP/1.0 Host:teste.globo.com", vip.getHealthcheck());
        assertEquals((Long)2l, vip.getExpectedHealthcheckId());
        assertEquals("teste.globo.com", vip.getHost());

        assertEquals(Boolean.TRUE, vip.getValidated());
        assertEquals("Homologacao", vip.getFinality());
        assertEquals("teste.globo.com", vip.getServiceName());
        assertEquals("Usuario Interno", vip.getClient());
        assertEquals("negocio_123", vip.getBusinessArea());
        assertEquals((Integer)5, vip.getTimeout());

        List<Pool> pools = vip.getPools();
        assertNotNull(pools);
        assertEquals(2, pools.size());

        Pool pool1 = pools.get(0);
        assertEquals((Long)123l, pool1.getId());
        assertEquals("least-conn", pool1.getLbMethod());
        assertEquals((Long)452l, pool1.getHealthcheck().getId());
        assertEquals("VIP-1_pool_80", pool1.getIdentifier());
        assertEquals(true, pool1.isPoolCreated());
        assertEquals((Long)121l, pool1.getEnvironment().getId());
        assertEquals(80, pool1.getDefaultPort());
        assertEquals(0, pool1.getDefaultLimit());


        Pool pool2 = pools.get(1);
        assertEquals((Long)345l, pool2.getId());
        assertEquals("rrrrr", pool2.getLbMethod());
        assertEquals((Long)452l, pool2.getHealthcheck().getId());
        assertEquals("VIP-2_pool_8080", pool2.getIdentifier());
        assertEquals(false, pool2.isPoolCreated());
    }



    public static InputStream getSample(String value) {
        InputStream stream = PoolTest.class.getClassLoader().getResourceAsStream(value);
        return stream;
    }
}

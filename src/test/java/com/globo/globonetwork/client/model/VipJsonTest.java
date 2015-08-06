package com.globo.globonetwork.client.model;

import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import java.io.IOException;
import java.io.InputStream;
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



    public static InputStream getSample(String value) {
        InputStream stream = PoolTest.class.getClassLoader().getResourceAsStream(value);
        return stream;
    }
}

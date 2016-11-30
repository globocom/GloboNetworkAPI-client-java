package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.VipV3;
import com.google.api.client.json.GenericJson;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VipV3APITest extends TestCase {

    private HttpJSONRequestProcessor processor;
    private VipV3API api;

    @Before
    public void setUp(){
        processor = mock(HttpJSONRequestProcessor.class);
        api = new VipV3API(processor);
    }

    public void testCreateNew() throws GloboNetworkException {
        VipV3 vip = buildVip();
        vip.setId(null);
        when(processor.post(eq("/api/v3/vip-request/"), any(VipV3.VipV3Response.class))).thenReturn("[{\"id\": 1}]");
        vip = api.save(vip);

        assertEquals(new Long(1), vip.getId());
        verify(processor).post(eq("/api/v3/vip-request/"), any(VipV3.VipV3Response.class));
    }

    public void testUpdate() throws GloboNetworkException {
        VipV3 vip = buildVip();
        vip.setId(1L);
        vip = api.save(vip);

        assertEquals(new Long(1), vip.getId());
        verify(processor).put(eq("/api/v3/vip-request/1/"), any(VipV3.VipV3Response.class));
    }

    public void testDeleteVipAndIp() throws GloboNetworkException {
        api.delete(1L, false);
        verify(processor).delete(eq("/api/v3/vip-request/1/"), eq(GenericJson.class));
    }

    public void testDeleteVipAndKeepIp() throws GloboNetworkException {
        api.delete(1L, true);
        verify(processor).delete(eq("/api/v3/vip-request/1/?keepip=1"), eq(GenericJson.class));
    }

    public void testDeploy() throws GloboNetworkException {
        api.deploy(1L);
        verify(processor).post(eq("/api/v3/vip-request/deploy/1/"), eq(GenericJson.class));
    }

    public void testUndeploy() throws GloboNetworkException {
        api.undeploy(1L);
        verify(processor).delete(eq("/api/v3/vip-request/deploy/1/"), eq(GenericJson.class));
    }

    public void testUpdatePersistence() throws GloboNetworkException {
        api.updatePersistence(1L, 5L);

        VipV3 expectedVip = new VipV3();
        expectedVip.setId(1L);
        VipV3.VipOptions options = new VipV3.VipOptions();
        options.setPersistenceId(5L);
        expectedVip.setOptions(options);

        verify(processor).patch(eq("/api/v3/vip-request/deploy/1/"), eq(new VipV3.VipV3Response(expectedVip)));
    }

    public void testGetById() throws GloboNetworkException {
        VipV3 mockReturn = buildVip();
        when(processor.get(eq("/api/v3/vip-request/1/"), eq(VipV3.VipV3Response.class))).thenReturn(new VipV3.VipV3Response(mockReturn));
        VipV3 vip = api.getById(1L);
        assertEquals(mockReturn, vip);

        verify(processor).get(eq("/api/v3/vip-request/1/"), eq(VipV3.VipV3Response.class));
    }

    private VipV3 buildVip(){
        VipV3 vip = new VipV3();
        vip.setId(1L);
        vip.setName("dummy");
        vip.setService("dummy");
        vip.setBusiness("dummy");
        vip.setEnvironmentVipId(1L);
        vip.setIpv4Id(1L);
        vip.setOptions(new VipV3.VipOptions(1L, 2L, 3L, 4L));
        List<VipV3.Port> ports = new ArrayList<VipV3.Port>();
        VipV3.Port port = new VipV3.Port();
        port.setPort(80);
        port.setOptions(new VipV3.PortOptions(1L, 5L));
        VipV3.Pool pool = new VipV3.Pool(1L, 60L, null);
        port.setPools(Collections.singletonList(pool));
        ports.add(port);
        vip.setPorts(ports);
        return vip;
    }
}

package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.Pool;
import java.io.IOException;
import junit.framework.TestCase;
import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PoolAPITest extends TestCase {


    @Before
    public void setUp() {

    }

    public void testGetByPk() throws GloboNetworkException, IOException {
        Long id = 123l;

        Pool exPool = new Pool();
        Pool.ServerPool serverPool = new Pool.ServerPool();
        serverPool.setId(123l);
        exPool.setServerPool(serverPool);


        HttpJSONRequestProcessor jsonRP = mock(HttpJSONRequestProcessor.class);
        when(jsonRP.get("/api/pools/getbypk/" +id + "/", Pool.class)).thenReturn(exPool);

        PoolAPI api = new PoolAPI(jsonRP);

        Pool pool = api.getByPk(123l);
        assertNotNull(pool);
        assertEquals(123l, pool.getServerPool().getId().longValue() );


        verify(jsonRP,times(1)).get("/api/pools/getbypk/" + id + "/",Pool.class);

    }



}

package com.globo.globonetwork.client.api;


import com.fasterxml.jackson.core.JsonFactory;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessorTest;
import com.globo.globonetwork.client.model.Pool;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.javafx.tools.doclets.internal.toolkit.util.DocFinder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import junit.framework.TestCase;
import junit.framework.TestResult;
import org.apache.commons.io.IOUtils;
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

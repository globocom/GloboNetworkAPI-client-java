package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.OptionVipV3;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OptionVipV3APITest extends TestCase {

    private HttpJSONRequestProcessor processor;
    private OptionVipV3API api;

    @Before
    public void setUp(){
        processor = mock(HttpJSONRequestProcessor.class);
        api = new OptionVipV3API(processor);
    }

    public void testListOptions() throws GloboNetworkException {
        when(processor.get(eq("/api/v3/option-vip/environment-vip/1/"))).thenReturn(getOptionsString());

        List<OptionVipV3> options = api.listOptions(1L);
        assertEquals(3, options.size());
        verify(processor).get("/api/v3/option-vip/environment-vip/1/");
    }

    public void testFindOptionsByTypeEqualsTimeout() throws GloboNetworkException {
        when(processor.get(eq("/api/v3/option-vip/environment-vip/1/"))).thenReturn(getOptionsString());

        List<OptionVipV3> options = api.findOptionsByType(1L, "timeout");
        assertEquals(2, options.size());
        assertEquals(new Long(2), options.get(0).getId());
        assertEquals(new Long(3), options.get(1).getId());
        assertEquals("5", options.get(0).getName());
        assertEquals("8", options.get(1).getName());
        verify(processor).get("/api/v3/option-vip/environment-vip/1/");
    }

    public void testFindOptionsByTypeEqualsTimeoutAndNameEquals5() throws GloboNetworkException {
        when(processor.get(eq("/api/v3/option-vip/environment-vip/1/"))).thenReturn(getOptionsString());

        List<OptionVipV3> options = api.findOptionsByTypeAndName(1L, "timeout", "5");
        assertEquals(1, options.size());
        assertEquals(new Long(2), options.get(0).getId());
        verify(processor).get("/api/v3/option-vip/environment-vip/1/");
    }

    private String getOptionsString(){
      return
       "[\n" +
       "  {\n" +
       "    \"option\": {\n" +
       "      \"id\": 1,\n" +
       "      \"tipo_opcao\": \"Persistencia\",\n" +
       "      \"nome_opcao_txt\": \"source-ip\"\n" +
       "    }\n" +
       "  },\n" +
       "  {\n" +
       "    \"option\": {\n" +
       "      \"id\": 2,\n" +
       "      \"tipo_opcao\": \"timeout\",\n" +
       "      \"nome_opcao_txt\": \"5\"\n" +
       "    }\n" +
       "  },\n" +
       "  {\n" +
       "    \"option\": {\n" +
       "      \"id\": 3,\n" +
       "      \"tipo_opcao\": \"timeout\",\n" +
       "      \"nome_opcao_txt\": \"8\"\n" +
       "    }\n" +
       "  }\n" +
       "]";
    }
}

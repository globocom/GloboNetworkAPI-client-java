package com.globo.globonetwork.client.http;


import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;




public class HttpJSONRequestProcessorTest extends TestCase {

    HttpJSONRequestProcessor rp;

    @Before
    public void setUp() {
        rp = new HttpJSONRequestProcessor("https://www.url_test.com.br", "username_test", "password_test");
    }

    @Test
    public void testBuildRequest() throws IOException {
        GenericUrl url = new GenericUrl("http://teste.com.br");

        HttpRequest request = rp.buildRequest("GET", url, null);
        assertTrue(request.isLoggingEnabled());
        assertEquals("Basic dXNlcm5hbWVfdGVzdDpwYXNzd29yZF90ZXN0", request.getHeaders().getAuthorization());
    }


    @Test
    public void testPerformRequest_status_200() throws GloboNetworkException {
        createMockFactory("{\"pool\":\"1233\"}", 200);

        try {
            Response response = rp.performRequest(HttpTesting.SIMPLE_GENERIC_URL, HttpMethods.GET, null);

            assertEquals(200, response.statusCode);
            assertTrue(response.content.contains("1233"));
        }catch (GloboNetworkException e ) {
            e.printStackTrace();
            fail("should not fail: " + e.getMessage());
        }
    }

    @Test
    public void testPerformRequest_status_404() throws GloboNetworkException {
        createMockFactory("{\"detail\": \"Pool Does Not Exist.\"}", 404);

        try {
            Response response = rp.performRequest(HttpTesting.SIMPLE_GENERIC_URL, HttpMethods.GET, null );
            fail("should fail GloboNetworkException");
        }catch (GloboNetworkException e ) {
            assertTrue(e.getCause() instanceof HttpResponseException);

            HttpResponseException cause = (HttpResponseException) e.getCause();
            assertEquals(404, cause.getStatusCode());
            assertTrue(cause.getContent().contains("Pool Does Not Exist."));
        } catch (Exception e ) {
            fail("should fail GloboNetworkException: " + e.getClass() + " " + e.getMessage());
        }
    }

    @Test
    public void testPerformRequest_status_500() throws GloboNetworkException {
        createMockFactory("{\"detail\": \"Invalid id for Pool.\"}", 500);

        try {
            Response response = rp.performRequest(HttpTesting.SIMPLE_GENERIC_URL, HttpMethods.GET, null);
            fail("should fail GloboNetworkException");
        }catch (GloboNetworkException e ) {
            assertTrue(e.getCause() instanceof HttpResponseException);

            HttpResponseException cause = (HttpResponseException) e.getCause();
            assertEquals(500, cause.getStatusCode());
            assertTrue(cause.getContent().contains("Invalid id for Pool."));
        } catch (Exception e) {
            fail("should fail GloboNetworkException: " + e.getClass() + " " + e.getMessage());
        }
    }


    public void createMockFactory(final String content, final int statusCode) {
        try {
            MockHttpTransport transport = new MockHttpTransport() {
                @Override
                public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                    LowLevelHttpRequest request = new MockLowLevelHttpRequest() {
                        @Override
                        public LowLevelHttpResponse execute() throws IOException {
                            MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                            response.setStatusCode(statusCode);
                            response.setContent(content);

                            return response;
                        }
                    };
                    return request;
                }
            };
            HttpRequestFactory requestFactory = transport.createRequestFactory();
            rp.setRequestFactory(requestFactory);



        }catch (Exception e ) {
            e.printStackTrace();
            throw new RuntimeException("error creating mock httpRequest: " , e);

        }
    }

    public static <T extends GenericJson> T parse(InputStream inputstream, Class<T> dataType) throws IOException {
        return HttpJSONRequestProcessor.parse(inputstream, dataType);
    }
}

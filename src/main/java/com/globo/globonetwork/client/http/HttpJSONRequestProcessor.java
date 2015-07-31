package com.globo.globonetwork.client.http;

import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpJSONRequestProcessor {


    private static final Logger LOGGER = LoggerFactory.getLogger(HttpJSONRequestProcessor.class);

    //field used to get the message error from response json content: {"detail": "this is a message error"}
    public static final String FIELD_MESSAGE_ERROR = "detail";

    private static int DEFAULT_READ_TIMEOUT = 2*60000;
    private static int DEFAULT_CONNECT_TIMEOUT = 1*60000;
    private static int DEFAULT_NUMBER_OF_RETRIES = 0;

    private String baseUrl;
    private String username;
    private String password;
    private int readTimeout = DEFAULT_READ_TIMEOUT;
    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private int numberOfRetries = DEFAULT_NUMBER_OF_RETRIES;

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private volatile HttpRequestFactory requestFactory;

    public HttpJSONRequestProcessor(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    private HttpRequestFactory getRequestFactory() {
        if (this.requestFactory == null) {
            synchronized (this) {
                this.requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                        request.setParser(new JsonObjectParser(JSON_FACTORY));
                        request.setReadTimeout(HttpJSONRequestProcessor.this.readTimeout);
                        request.setConnectTimeout(HttpJSONRequestProcessor.this.connectTimeout);
                        request.setNumberOfRetries(HttpJSONRequestProcessor.this.numberOfRetries);
                    }
                });
            }
        }
        return this.requestFactory;
    }

    protected HttpRequest buildRequest(String method, GenericUrl url, Object payload) throws IOException {
        HttpRequest request;

        // Preparing content for POST and PUT
        HttpContent content = null;
        if (payload != null) {
//            content = ByteArrayContent.fromString(null, payload.toString());
            content = new JsonHttpContent(new JacksonFactory(), payload);
        }

        if(method == "POST") {
            request = getRequestFactory().buildPostRequest(url, content);
        } else {
            request = createRequest(method, url, content);
        }

        request.getHeaders().setBasicAuthentication(this.username, this.password);
        request.setLoggingEnabled(true);

        return request;
    }
    protected HttpRequest createRequest(String method, GenericUrl url, HttpContent content) throws IOException {
        return this.getRequestFactory().buildRequest(method, url, content);
    }

    protected HttpResponse performHttpRequest(HttpRequest request) throws IOException {
        LOGGER.debug("Calling GloboNetwork: " + request.getRequestMethod() + " " + request.getUrl() + " " + request.getContent());
        Long startTime = new Date().getTime();
        HttpResponse response = request.execute();
        Long responseTime = new Date().getTime() - startTime;
        LOGGER.debug("Response in " + responseTime + " from GloboNetwork: " + response.getStatusCode() + " " + response.getStatusMessage());
        return response;
    }

    protected HttpResponse performRequest(HttpRequest request) throws GloboNetworkException {

        HttpResponse httpResponse;
        try {
            httpResponse = this.performHttpRequest(request);
            return httpResponse;

        } catch (HttpResponseException httpException) {
            int  httpStatusCode = httpException.getStatusCode();
            String description;
            try {
                InputStream stream = new ByteArrayInputStream(httpException.getContent().getBytes(StandardCharsets.UTF_8));
                GenericJson json = new JsonObjectParser(JSON_FACTORY).parseAndClose(stream, StandardCharsets.UTF_8, GenericJson.class);
                description = (String)json.get(FIELD_MESSAGE_ERROR);
            } catch (IOException e1) {
                description = httpException.getContent();
            }
            throw new GloboNetworkErrorCodeException(httpStatusCode, description, httpException);
        } catch (IOException e) {
            throw new GloboNetworkException("IOException: " + e.getMessage(), e );
        }
    }


    /**
     * Build a absolute url
     *
     * @param suffixUrl relative url. The {@code baseUrl} attribute will be prepend to this
     * url.
     * @return absolue url.
     */
    protected GenericUrl buildUrl(String suffixUrl) {
        return new GenericUrl(this.baseUrl + suffixUrl);
    }

    public <T extends GenericJson> GenericJson get(String suffixUrl, Class<T> dataType) throws GloboNetworkException {
        try {
            GenericUrl url = buildUrl(suffixUrl);
            HttpRequest request = this.buildRequest("GET", url, null);
            HttpResponse response = this.performRequest(request);
            return response.parseAs(dataType);
        } catch (IOException e) {
            throw new GloboNetworkException("IOError: " + e, e);
        }
    }

    public <T extends GenericJson> GenericJson post(String suffixUrl, Object payload, Class<T> dataType) throws GloboNetworkException {
        try {
            GenericUrl url = buildUrl(suffixUrl);
            HttpRequest request = this.buildRequest("POST", url, payload);
            HttpResponse response = this.performRequest(request);


            String output = response.parseAsString();

            if ( output.isEmpty()) {
                return new GenericJson();
            }
            return parse(output, dataType);
        } catch (IOException e) {
            throw new GloboNetworkException("IOError: " + e, e);
        }
    }

    public <T extends GenericJson> GenericJson put(String suffixUrl, Object payload, Class<T> dataType) throws GloboNetworkException {
        try {
            GenericUrl url = buildUrl(suffixUrl);
            HttpRequest request = this.buildRequest("PUT", url, payload);
            HttpResponse response = this.performRequest(request);
            return response.parseAs(dataType);
        } catch (IOException e) {
            throw new GloboNetworkException("IOError: " + e, e);
        }
    }

    public <T extends GenericJson> GenericJson delete(String suffixUrl, Class<T> dataType) throws GloboNetworkException {
        try {
            GenericUrl url = buildUrl(suffixUrl);
            HttpRequest request = this.buildRequest("DELETE", url, null);
            HttpResponse response = this.performRequest(request);
            return response.parseAs(dataType);
        } catch (IOException e) {
            throw new GloboNetworkException("IOError: " + e, e);
        }
    }

    private void checkIfNotInitialized() {
        if (this.requestFactory != null) {
            throw new IllegalArgumentException("It's not allowed to change connection parameters after initialization.");
        }
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        checkIfNotInitialized();
        this.readTimeout = readTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        checkIfNotInitialized();
        this.connectTimeout = connectTimeout;
    }

    public Integer getNumberOfRetries() {
        return numberOfRetries;
    }

    public void setNumberOfRetries(Integer numberOfRetries) {
        checkIfNotInitialized();
        this.numberOfRetries = numberOfRetries;
    }

    public static <T extends GenericJson> T parse(InputStream stream, Class<T> dataType) throws IOException {
        com.google.api.client.json.JsonFactory jsonFactory = new JacksonFactory();
        return new JsonObjectParser(jsonFactory).parseAndClose(stream, Charset.defaultCharset(), dataType);
    }

    public static <T extends GenericJson> T parse(String output, Class<T> dataType) throws IOException {

        InputStream stream = new ByteArrayInputStream(output.getBytes(StandardCharsets.UTF_8));

        com.google.api.client.json.JsonFactory jsonFactory = new JacksonFactory();
        return new JsonObjectParser(jsonFactory).parseAndClose(stream, StandardCharsets.UTF_8, dataType);
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

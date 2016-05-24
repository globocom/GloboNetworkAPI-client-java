package com.globo.globonetwork.client.http;

import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Pool;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
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
import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Key;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpJSONRequestProcessor {


    private static final Logger LOGGER = LoggerFactory.getLogger(HttpJSONRequestProcessor.class);

    //field used to get the message error from response json content: {"detail": "this is a message error"}
    public static final String FIELD_MESSAGE_ERROR = "detail";

    private static int DEFAULT_READ_TIMEOUT = 2*60000;
    private static int DEFAULT_CONNECT_TIMEOUT = 1*60000;
    private static int DEFAULT_NUMBER_OF_RETRIES = 0;

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

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

    protected HttpRequestFactory getRequestFactory() {
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
    protected void setRequestFactory(HttpRequestFactory factory) {
        this.requestFactory = factory;
    }
    protected HttpRequest buildRequest(String method, GenericUrl url, Object payload) throws IOException {
        HttpRequest request;

        // Preparing content for POST and PUT
        HttpContent content = null;
        if (payload != null) {
            content = new JsonHttpContent(new JacksonFactory(), payload);
        }

        HttpRequestFactory requestFactory = this.getRequestFactory();
        request = requestFactory.buildRequest(method, url, content);

        request.getHeaders().setBasicAuthentication(this.username, this.password);
        request.setLoggingEnabled(true);

        return request;
    }

    protected Response performRequest(GenericUrl url, String method, Object payload) throws GloboNetworkException {
        Long startTime = new Date().getTime();

        try {
            HttpRequest request = this.buildRequest(method, url, payload);
            HttpUtil.loggingRequest(request);

            HttpResponse response = request.execute();

            Response helper = new Response(response);
            HttpUtil.loggingResponse(startTime, request, helper);

            return helper;
        } catch (HttpResponseException httpException) {
            int  httpStatusCode = httpException.getStatusCode();
            String description = getErrorDescription(url, method, startTime, httpException);

            throw new GloboNetworkErrorCodeException(httpStatusCode, description, httpException);
        } catch (IOException e) {
            throw new GloboNetworkException("IOException: " + e.getMessage(), e );
        }
    }

    private String getErrorDescription(GenericUrl url, String method, Long startTime, HttpResponseException httpException) {
        String description = "";
        try {
            String content = httpException.getContent();
            Long responseTime = new Date().getTime() - startTime;
            LOGGER.debug("[GloboNetworkAPI response] ResponseTime: " + responseTime + "ms " + method +  " URL:" + url + " StatusCode: "+ httpException.getStatusCode() +" Content: " +  content );
            InputStream stream = new ByteArrayInputStream(content.getBytes(DEFAULT_CHARSET));
            GenericJson json = new JsonObjectParser(JSON_FACTORY).parseAndClose(stream, DEFAULT_CHARSET, GenericJson.class);
            Object message = json.get(FIELD_MESSAGE_ERROR);

            if ( message instanceof String ){
                description = (String) message;
            } else if (message != null){
                description = message.toString();
            }

        } catch (IOException e1) {
            description = httpException.getContent();
        }
        return description;
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
        Response response = this.performRequest(buildUrl(suffixUrl), HttpMethods.GET, null);

        return parse(response.content, dataType);
    }

    public String get(String suffixUrl) throws GloboNetworkException {

        Response response = this.performRequest(buildUrl(suffixUrl), HttpMethods.GET, null);
        return response.content;
    }

    public String post(String suffixUrl, Object payload) throws GloboNetworkException {
        Response response = this.performRequest(buildUrl(suffixUrl), HttpMethods.POST, payload);
        return response.content;
    }

    public String put(String suffixUrl, Object payload) throws GloboNetworkException {
        Response response = this.performRequest(buildUrl(suffixUrl), HttpMethods.PUT, payload);
        return response.content;
    }

    public <T extends GenericJson> GenericJson post(String suffixUrl, Object payload, Class<T> dataType) throws GloboNetworkException {
        Response response = this.performRequest(buildUrl(suffixUrl), HttpMethods.POST, payload);

        if (response.content == null || response.content.isEmpty()){
            return new GenericJson();
        }

        return parse(response.content, dataType);
    }

    public <T extends GenericJson> GenericJson put(String suffixUrl, Object payload, Class<T> dataType) throws GloboNetworkException {
        Response response = this.performRequest(buildUrl(suffixUrl), HttpMethods.PUT, payload);

        if (response.content == null || response.content.isEmpty()){
            return new GenericJson();
        }
        return parse(response.content, dataType);
    }

    public <T extends GenericJson> GenericJson delete(String suffixUrl, Class<T> dataType) throws GloboNetworkException {
        Response response = this.performRequest(buildUrl(suffixUrl), HttpMethods.DELETE, null);

        if (response.content == null || response.content.isEmpty()){
            return new GenericJson();
        }
        return parse(response.content, dataType);
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

    public static <T> T parse(InputStream stream, Class<T> dataType) throws IOException {
        com.google.api.client.json.JsonFactory jsonFactory = new JacksonFactory();
        return new JsonObjectParser(jsonFactory).parseAndClose(stream, DEFAULT_CHARSET, dataType);
    }

    public static <T> T parse(String output, Class<T> dataType) throws GloboNetworkException {
        try {
            InputStream stream = new ByteArrayInputStream(output.getBytes(DEFAULT_CHARSET));

            com.google.api.client.json.JsonFactory jsonFactory = new JacksonFactory();
            return new JsonObjectParser(jsonFactory).parseAndClose(stream, DEFAULT_CHARSET, dataType);

        } catch (IOException e) {
            throw new GloboNetworkException("IOError trying to parse : " + output + " to " + dataType + e, e);
        }
    }

    public static <T extends GenericJson> void fillFieldsObject(Class clazz, T obj, ArrayMap json) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                String name = field.getName();
                String keyValue = clazz.getDeclaredField(name).getAnnotation(Key.class).value();
                String setName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
                Method method = clazz.getDeclaredMethod(setName, field.getType());
                Object value = json.get(keyValue);
                if ( value  != null){
                    if ( value instanceof BigDecimal) {
                        value = Long.valueOf(value.toString());
                    } else if ( !(value instanceof String || value instanceof Integer || value instanceof Long) ) {
                        value = null;
                    }
                    method.invoke(obj,  value);
                }
            } catch (Exception e) {
                throw new RuntimeException("error get field= " + field.getName() + " in json: " + json.toString() , e);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

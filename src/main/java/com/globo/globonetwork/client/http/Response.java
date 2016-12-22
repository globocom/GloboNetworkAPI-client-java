package com.globo.globonetwork.client.http;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.Joiner;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Response {

    private static final Logger LOGGER = LoggerFactory.getLogger(Response.class);
    int statusCode;
    String content;

    private HttpHeaders headers;

    public Response(int statusCode, String content, HttpHeaders headers) {
        this.statusCode = statusCode;
        this.content = content;
        this.headers = headers;
    }

    public Response(com.google.api.client.http.HttpResponse response) {
        this.statusCode = response.getStatusCode();
        this.headers = response.getHeaders();
        try {
            this.content = response.parseAsString();

        } catch (Exception e) {
            throw new RuntimeException("Error trying parse response to string: " + e.getMessage(), e);
        }
    }

    public String getHeader(String key) {
        Object value = headers.get(key);

        try {
            if (value instanceof String) {
                return (String) value;
            } else if (value instanceof ArrayList) {
                return Joiner.on(',').join((ArrayList) value);
            } else if (value != null) {
                return value.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.warn("Unable to get header: " + key + " value: " + value + " error: "+ e.getMessage());
            return "HEADER_ERROR";
        }
    }
}

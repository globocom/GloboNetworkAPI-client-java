package com.globo.globonetwork.client.http;

import com.google.api.client.http.HttpResponse;

public class Response {
    int statusCode;
    String content;

    public Response(com.google.api.client.http.HttpResponse response) {
        this.statusCode = response.getStatusCode();
        try {
            this.content = response.parseAsString();

        } catch (Exception e) {
            throw new RuntimeException("Error trying parse response to string: " + e.getMessage(), e);
        }
    }
}

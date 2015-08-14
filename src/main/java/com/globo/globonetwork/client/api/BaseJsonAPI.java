package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.google.api.client.json.GenericJson;
import com.google.api.client.xml.GenericXml;
import java.lang.reflect.ParameterizedType;

public class BaseJsonAPI<T>{
    private final HttpJSONRequestProcessor transport;

    public BaseJsonAPI(HttpJSONRequestProcessor requestProcessor ) {
        this.transport = requestProcessor;
    }

    protected HttpJSONRequestProcessor getTransport() {
        if (this.transport == null) {
            throw new RuntimeException("No transport configured");
        }
        return this.transport;
    }

    protected <T extends GenericJson> Class<T> getBaseClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }




}

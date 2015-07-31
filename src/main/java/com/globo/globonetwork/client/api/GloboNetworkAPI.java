package com.globo.globonetwork.client.api;


import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;

public class GloboNetworkAPI {

    HttpJSONRequestProcessor jsonAPI;

    public GloboNetworkAPI(String baseUrl, String username, String password) {
        this.jsonAPI = new HttpJSONRequestProcessor(baseUrl, username, password);
    }

    public PoolAPI getPoolAPI() {
        return new PoolAPI(jsonAPI);
    }


}

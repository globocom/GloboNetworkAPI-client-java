package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Environment;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.OptionVip;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.ArrayList;
import java.util.List;

public class OptionVipAPI extends BaseAPI<OptionVip> {

    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    static final JsonObjectParser parser = new JsonObjectParser(JSON_FACTORY);

    public OptionVipAPI(RequestProcessor transport) {
        super(transport);
    }

    public List<OptionVip> listCacheGroups(Long envId) throws GloboNetworkException {
        try{
            GloboNetworkRoot<OptionVip> globoNetworkRoot = this.get("/environment-vip/get/grupo-cache/" + envId + "/");
            if (globoNetworkRoot == null) {
                // Problems reading the XML
                throw new GloboNetworkException("Invalid XML response");
            } else if (globoNetworkRoot.getObjectList() == null) {
                return new ArrayList<OptionVip>();
            }
            return globoNetworkRoot.getObjectList();
        } catch (GloboNetworkErrorCodeException e) {
            if (e.getCode() == GloboNetworkErrorCodeException.ENVIRONMENT_VIP_NOT_REGISTERED) {
                return null;
            }
            throw e;
        }
    }
}

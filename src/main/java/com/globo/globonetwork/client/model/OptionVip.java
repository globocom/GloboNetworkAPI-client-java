package com.globo.globonetwork.client.model;


import com.google.api.client.util.Key;
import com.google.api.client.util.Lists;
import com.google.api.client.xml.GenericXml;

import java.util.List;

public class OptionVip extends GenericXml{

    @Key("grupocache_opt")
    private String cacheGroup;


    public String getCacheGroup() {
        return cacheGroup;
    }

    public void setCacheGroup(String cacheGroup) {
        this.cacheGroup = cacheGroup;
    }
}


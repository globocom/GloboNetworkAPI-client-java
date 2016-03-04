package com.globo.globonetwork.client.model.healthcheck;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class ExpectHealthcheck extends GenericXml {

    public ExpectHealthcheck(Long id, String expect) {
        this.id = id;
        this.expect = expect;
    }
    public ExpectHealthcheck() {}

    @Key
    private Long id;

    @Key("expect_string")
    private String expect;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }
}


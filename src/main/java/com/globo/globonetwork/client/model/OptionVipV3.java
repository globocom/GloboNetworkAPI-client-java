package com.globo.globonetwork.client.model;

import com.google.api.client.util.Key;

public class OptionVipV3 {


    @Key("id")
    private Long id;

    @Key("tipo_opcao")
    private String type;

    @Key("nome_opcao_txt")
    private String name;

    public OptionVipV3(Long id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}


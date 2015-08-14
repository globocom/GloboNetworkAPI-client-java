package com.globo.globonetwork.client.model;

public class PoolOption {

    private Long id;

    private String name;

    public PoolOption(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

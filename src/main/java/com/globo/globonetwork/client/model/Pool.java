package com.globo.globonetwork.client.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.ArrayList;

public class Pool extends GenericJson {

    @Key("id")
    private Long id;

    @Key("lb_method")
    private String lbMethod;

    @Key("default_port")
    private int defaultPort;

    @Key("default_limit")
    private int defaultLimit;

    @Key("identifier")
    private String identifier;

    @Key("pool_created")
    private Boolean poolCreated;

    @Key("environment")
    private Long environment;

    @Key("healthcheck")
    private Long healthcheck;

    public Pool(Long id) {
        this.id = id;
    }
    public Pool() {

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLbMethod() {
        return lbMethod;
    }

    public void setLbMethod(String lbMethod) {
        this.lbMethod = lbMethod;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(int defaultPort) {
        this.defaultPort = defaultPort;
    }

    public int getDefaultLimit() {
        return defaultLimit;
    }

    public void setDefaultLimit(int defaultLimit) {
        this.defaultLimit = defaultLimit;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean isPoolCreated() {
        return poolCreated;
    }

    public void setPoolCreated(boolean poolCreated) {
        this.poolCreated = poolCreated;
    }

    public Long getEnvironment() {
        return environment;
    }

    public void setEnvironment(Long environment) {
        this.environment = environment;
    }

    public Long getHealthcheck() {
        return healthcheck;
    }

    public void setHealthcheck(Long healthcheck) {
        this.healthcheck = healthcheck;
    }

    public static class PoolList extends ArrayList<Pool> {

    }

}
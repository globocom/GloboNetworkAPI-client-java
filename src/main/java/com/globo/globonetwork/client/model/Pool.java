package com.globo.globonetwork.client.model;

import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.Key;
import java.math.BigDecimal;
import java.util.List;

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

    private Environment environment;


    private Healthcheck healthcheck;

    @Key("maxcom")
    private Integer maxconn;

    public Pool(Long id) {
        this.id = id;
    }
    public Pool() {

    }

    public Integer getMaxconn() {
        return maxconn;
    }

    public void setMaxconn(Integer maxconn) {
        this.maxconn = maxconn;
    }

    public Boolean getPoolCreated() {
        return poolCreated;
    }

    public void setPoolCreated(Boolean poolCreated) {
        this.poolCreated = poolCreated;
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

    public Environment getEnvironment() {
        if ( environment == null ) {
            this.environment = new Environment();

            Object envValue = this.get("environment");
            if ( envValue instanceof String) {
                this.environment.setName((String) envValue);
            } else if (envValue instanceof BigDecimal){
                this.environment.setId(((BigDecimal)envValue).longValue());
            } else if ( envValue instanceof ArrayMap){
                HttpJSONRequestProcessor.fillFieldsObject(Environment.class, this.environment, (ArrayMap)envValue);
            }

        }

        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Healthcheck getHealthcheck() {
        Object obj = this.get("healthcheck");
        if ( this.healthcheck == null ) {
            this.healthcheck = new Healthcheck();
        }

        if (obj instanceof BigDecimal){
            this.healthcheck.setId(((BigDecimal)obj).longValue());
        } else if (obj instanceof ArrayMap) {
            ArrayMap healthObj = (ArrayMap) obj;

            HttpJSONRequestProcessor.fillFieldsObject(Healthcheck.class, this.healthcheck, healthObj);
        }
        return healthcheck;
    }

    public void setHealthcheck(Healthcheck healthcheck) {
        this.healthcheck = healthcheck;
    }

    public static class PoolList  {
        @Key("pools")
        List<Pool> pools;

        public List<Pool> getPools() {
            return pools;
        }

        public void setPools(List<Pool> pools) {
            this.pools = pools;
        }
    }

    public static class Environment extends GenericJson{
        @Key("id")
        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static class Healthcheck extends GenericJson{

        @Key("id")
        private Long id;

        @Key("healthcheck_type")
        private String healthcheckType;

        @Key("healthcheck_request")
        private String healthcheckRequest;

        @Key("healthcheck_expect")
        private String expectedHealthcheck;

        @Key("destination")
        private String destination;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getHealthcheckType() {
            return healthcheckType;
        }

        public void setHealthcheckType(String healthcheckType) {
            this.healthcheckType = healthcheckType;
        }

        public String getHealthcheckRequest() {
            return healthcheckRequest;
        }

        public void setHealthcheckRequest(String healthcheckRequest) {
            this.healthcheckRequest = healthcheckRequest;
        }

        public String getExpectedHealthcheck() {
            return expectedHealthcheck;
        }

        public void setExpectedHealthcheck(String expectedHealthcheck) {
            this.expectedHealthcheck = expectedHealthcheck;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }
    }

    public static class PoolSave extends GenericJson {
        @Key("server_pool")
        private Pool pool;

        public Pool getPool() {
            return pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }
    }

}
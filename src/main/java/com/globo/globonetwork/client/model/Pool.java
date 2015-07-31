package com.globo.globonetwork.client.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.sun.corba.se.spi.activation.Server;

public class Pool extends GenericJson {

    @Key("server_pool")
    private ServerPool serverPool;

    public ServerPool getServerPool() {
        return serverPool;
    }

    public void setServerPool(ServerPool serverPool) {
        this.serverPool = serverPool;
    }

    public static class ServerPool extends GenericJson {

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
        private boolean poolCreated;


        @Key("healthcheck")
        private HealthCheck healthCheck;

        public HealthCheck getHealthCheck() {
            return healthCheck;
        }

        public void setHealthCheck(HealthCheck healthCheck) {
            this.healthCheck = healthCheck;
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

        public static class HealthCheck {

            @Key("healthcheck_type")
            private String healthcheckType;

            @Key("destination")
            private String destination;

            @Key("healthcheck_expect")
            private String healthcheckExpect;

            @Key("identifier")
            private String identifier;

            @Key("id")
            private Long id;

            @Key("healthcheck_request")
            private String healthcheckRequest;

            public String getHealthcheckType() {
                return healthcheckType;
            }

            public void setHealthcheckType(String healthcheckType) {
                this.healthcheckType = healthcheckType;
            }

            public String getDestination() {
                return destination;
            }

            public void setDestination(String destination) {
                this.destination = destination;
            }

            public String getHealthcheckExpect() {
                return healthcheckExpect;
            }

            public void setHealthcheckExpect(String healthcheckExpect) {
                this.healthcheckExpect = healthcheckExpect;
            }

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getHealthcheckRequest() {
                return healthcheckRequest;
            }

            public void setHealthcheckRequest(String healthcheckRequest) {
                this.healthcheckRequest = healthcheckRequest;
            }
        }
    }
}
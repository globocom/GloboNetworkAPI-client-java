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
    private Integer defaultLimit;

    @Key("identifier")
    private String identifier;

    @Key("pool_created")
    private Boolean poolCreated;

    @Key("servicedownaction")
    private ServiceDownAction serviceDownAction;

    @Key("maxcom")
    private Integer maxconn;

    private Environment environment;
    private Healthcheck healthcheck;

    public Pool() {

    }


    public Pool(Long id) {
        this.id = id;
    }

    public Integer getMaxconn() {
        if ( maxconn == null && defaultLimit != null ){
            return this.defaultLimit;
        }
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

    public void setDefaultLimit(Integer defaultLimit) {
        this.defaultLimit = defaultLimit;
    }

    public ServiceDownAction getServiceDownAction() {
        return serviceDownAction;
    }

    public void setServiceDownAction(ServiceDownAction serviceDownAction) {
        this.serviceDownAction = serviceDownAction;
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

            if (obj instanceof BigDecimal) {
                this.healthcheck.setId(((BigDecimal) obj).longValue());
            } else if (obj instanceof ArrayMap) {
                ArrayMap healthObj = (ArrayMap) obj;

                HttpJSONRequestProcessor.fillFieldsObject(Healthcheck.class, this.healthcheck, healthObj);
            }
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

        @Key("name")
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
    public static class PoolResponse extends GenericJson {
        @Key("server_pool")
        private Pool pool;

        @Key("server_pool_members")
        private List<PoolMember> poolMembers;

        public Pool getPool() {
            return pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }

        public List<PoolMember> getPoolMembers() {
            return poolMembers;
        }

        public void setPoolMembers(List<PoolMember> poolMembers) {
            this.poolMembers = poolMembers;
        }
    }

    public static class PoolMember extends GenericJson {
        @Key("id")
        private Long id;

        @Key("port_real")
        private Integer portReal;

        @Key("weight")
        private Integer weight;

        @Key("priority")
        private Integer priority;

        @Key("equipment_name")
        private String equipmentName;

        @Key("equipment_id")
        private Long equipmentId;

        @Key("ip")
        private Ip ip;


        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Integer getPriority() {
            return priority;
        }

        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public Ip getIp() {
            return ip;
        }

        public void setIp(Ip ip) {
            this.ip = ip;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getPortReal() {
            return portReal;
        }

        public void setPortReal(Integer portReal) {
            this.portReal = portReal;
        }

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
        }

        public Long getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(Long equipmentId) {
            this.equipmentId = equipmentId;
        }

        public String getIpFormated() {
            if (ip != null ) {
                return ip.getIpFormated();
            }
            return null;
        }

        public Long getIpId() {
            if (ip != null ) {
                return ip.getId();
            }
            return null;
        }

    }
    public static class ServiceDownAction extends GenericJson {
        @Key("id")
        private Long id;
        @Key("type")
        private String type;
        @Key("name")
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Ip extends GenericJson {
        @Key("id")
        private Long id;

        @Key("ip_formated")
        private String ipFormated;

        public String getIpFormated() {
            return ipFormated;
        }

        public void setIpFormated(String ipFormated) {
            this.ipFormated = ipFormated;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
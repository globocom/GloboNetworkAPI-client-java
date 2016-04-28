package com.globo.globonetwork.client.model.pool;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import com.google.api.client.util.NullValue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class PoolV3 extends GenericJson {

    static final SimpleDateFormat formatter = new SimpleDateFormat("ddMMYYYY");

    @Key("id")
    @NullValue
    private Long id = null;

    @Key("lb_method")
    private String lbMethod;

    @Key("default_port")
    private Integer defaultPort;

    @Key("default_limit")
    private Integer defaultLimit;

    @Key("identifier")
    private String identifier;

    @Key("pool_created")
    private Boolean poolCreated = false;

    @Key("environment")
    private Long environment;

    @Key("healthcheck")
    private Healthcheck healthcheck = new Healthcheck();

    @Key("servicedownaction")
    private ServiceDownAction serviceDownAction = new ServiceDownAction();

    @Key("server_pool_members")
    private List<PoolMember> poolMembers = new ArrayList<PoolMember>();

    public PoolV3() {}
    public PoolV3(Long id) {
        this.id = id;
    }

    public Integer getMaxconn() {
        return defaultLimit;
    }

    public void setMaxconn(Integer maxconn) {
        this.defaultLimit = maxconn;
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

    public void setDefaultPort(Integer defaultPort) {
        this.defaultPort = defaultPort;
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

    public void setServiceDownAction(ServiceDownAction serviceDownAction) {
        this.serviceDownAction = serviceDownAction;
    }

    public Long getEnvironment() {
        return environment;
    }

    public void setEnvironment(Long environment) {
        this.environment = environment;
    }

    public Healthcheck getHealthcheck() {
        return healthcheck;
    }

    public ServiceDownAction getServiceDownAction() {
        return serviceDownAction;
    }

    public void setHealthcheck(Healthcheck healthcheck) {
        this.healthcheck = healthcheck;
    }

    public List<PoolMember> getPoolMembers() {
        return poolMembers;
    }

    public void setPoolMembers(List<PoolMember> poolMembers) {
        this.poolMembers = poolMembers;
    }

    public static class Environment extends GenericJson {
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

    public static class Healthcheck extends GenericJson {

        @Key("id")
        private Long id;

        @Key("healthcheck_type")
        private String healthcheckType;

        @Key("healthcheck_request")
        private String healthcheckRequest;

        @Key("healthcheck_expect")
        private String expectedHealthcheck;

        @Key("destination")
        private String destination = "*:*";

        @Key("identifier")
        private String identifier;


        public void buildIdentifier() {
            StringBuilder builder = new StringBuilder();

            if ( healthcheckType != null && !healthcheckType.isEmpty() ){
                builder.append(healthcheckType);
            }

            if ( healthcheckRequest != null && !healthcheckRequest.isEmpty() ){
                builder.append("_").append(healthcheckRequest);
            }

            if ( expectedHealthcheck != null && !expectedHealthcheck.isEmpty() ){
                builder.append("_").append(expectedHealthcheck);
            }

            builder.append("_").append(formatter.format(new Date()));
            this.identifier = builder.toString();
        }


        public void setHealthcheck(String type, String request, String expected) {
            this.healthcheckType = type;
            if ( request == null || request.isEmpty() ) {
                this.healthcheckRequest = "";
            } else {
                this.expectedHealthcheck = expected;
            }

            if ( expected == null || expected.isEmpty() ) {
                this.expectedHealthcheck = "";
            } else {
                this.healthcheckRequest = request;
            }
            buildIdentifier();
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

        public String getHealthcheckType() {
            return healthcheckType;
        }

        public String getHealthcheckRequest() {
            return healthcheckRequest;
        }

        public String getExpectedHealthcheck() {
            return expectedHealthcheck;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            if(destination == null || destination.equals("*:*")){
                this.destination = "*:*";
            }else{
                this.destination = "*:" + destination;
            }
        }
    }

    public static class PoolMember extends GenericJson {
        @Key("id")
        private Long id = Data.NULL_LONG;

        @Key("port_real")
        private Integer portReal;

        @Key("weight")
        private Integer weight;

        @Key("priority")
        private Integer priority;

        @Key("ip")
        private Ip ip;

        @Key("ipv6")
        private Ip ipv6;

        @Key("limit")
        private Integer limit = 0;

        @Key("member_status")
        private Integer memberStatus = 0;

        @Key("identifier")
        private String identifier;

        @Key("equipment")
        private Equipment equipment = new Equipment();

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
            this.ipv6 = Data.nullOf(PoolV3.Ip.class);
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

        public Ip getIpv6() {
            return ipv6;
        }

        public void setIpv6(Ip ipv6) {
            this.ipv6 = ipv6;
            this.ip = Data.nullOf(PoolV3.Ip.class);
        }

        public Integer getMemberStatus() {
            return memberStatus;
        }

        public void setMemberStatus(Integer memberStatus) {
            this.memberStatus = memberStatus;
        }

        public String getEquipmentName() {
            if ( equipment != null ) {
                return equipment.name;
            }
            return null;
        }

        public void setEquipmentName(String equipmentName) {
            if ( equipment == null ){
                equipment = new Equipment();
            }
            this.identifier = equipmentName;
            this.equipment.setName(equipmentName);
        }

        public Long getEquipmentId() {
            if ( equipment != null ){
                return equipment.id;
            }
            return null;
        }

        public void setEquipmentId(Long equipmentId) {
            if ( equipment == null ){
                equipment = new Equipment();
            }
            this.equipment.setId(equipmentId);
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public Equipment getEquipment() {
            return equipment;
        }

        public void setEquipment(Equipment equipment) {
            this.equipment = equipment;
        }

        public String getIpFormated() {
            if (ip != null) {
                return ip.getIpFormated();
            }
            return null;
        }

        public Long getIpId() {
            if (ip != null) {
                return ip.getId();
            }
            return null;
        }

    }
    public static class Equipment extends GenericJson{
        @Key("id")
        public Long id;
        @Key("name")
        public String name;

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
            this.set("identifier", name);
            this.name = name;
        }
    }

    public static class ServiceDownAction extends GenericJson {
        @Key("id")
        private Long id;
        @Key("type")
        private String type;
        @Key("name")
        private String name = DEFAULT_ACTION;

        private static final String DEFAULT_ACTION = "none";

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
            if ( name == null || name.isEmpty()) {
                this.name = DEFAULT_ACTION;
            } else {
                this.name = name;
            }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PoolV3 poolV3 = (PoolV3) o;
        return Objects.equals(id, poolV3.id) &&
                Objects.equals(lbMethod, poolV3.lbMethod) &&
                Objects.equals(defaultPort, poolV3.defaultPort) &&
                Objects.equals(defaultLimit, poolV3.defaultLimit) &&
                Objects.equals(identifier, poolV3.identifier) &&
                Objects.equals(poolCreated, poolV3.poolCreated) &&
                Objects.equals(environment, poolV3.environment) &&
                Objects.equals(healthcheck, poolV3.healthcheck) &&
                Objects.equals(serviceDownAction, poolV3.serviceDownAction) &&
                Objects.equals(poolMembers, poolV3.poolMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, lbMethod, defaultPort, defaultLimit, identifier, poolCreated, environment, healthcheck, serviceDownAction, poolMembers);
    }
}
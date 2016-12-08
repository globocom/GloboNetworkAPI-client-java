package com.globo.globonetwork.client.model;

import com.globo.globonetwork.client.model.pool.PoolV3;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.Collections;
import java.util.List;


public class VipV3 extends GenericJson {

    @Key("id")
    private Long id;

    @Key("name")
    private String name;

    @Key("service")
    private String service;

    @Key("business")
    private String business;

    @Key("environmentvip")
    private Long environmentVipId;

    @Key("ipv4")
    private Long ipv4Id = Data.NULL_LONG;

    @Key("ipv6")
    private Long ipv6Id = Data.NULL_LONG;

    @Key("ports")
    private List<Port> ports;

    @Key("options")
    private VipOptions options;

    @Key("created")
    private Boolean created;

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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Long getEnvironmentVipId() {
        return environmentVipId;
    }

    public void setEnvironmentVipId(Long environmentVipId) {
        this.environmentVipId = environmentVipId;
    }

    public Long getIpv4Id() {
        return ipv4Id;
    }

    public void setIpv4Id(Long ipv4Id) {
        this.ipv4Id = ipv4Id;
    }

    public Long getIpv6Id() {
        return ipv6Id;
    }

    public void setIpv6Id(Long ipv6Id) {
        this.ipv6Id = ipv6Id;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public VipOptions getOptions() {
        return options;
    }

    public void setOptions(VipOptions options) {
        this.options = options;
    }

    public Boolean getCreated() {
        return created;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        VipV3 vipV3 = (VipV3) o;

        if (id != null ? !id.equals(vipV3.id) : vipV3.id != null) return false;
        if (name != null ? !name.equals(vipV3.name) : vipV3.name != null) return false;
        if (service != null ? !service.equals(vipV3.service) : vipV3.service != null) return false;
        if (business != null ? !business.equals(vipV3.business) : vipV3.business != null) return false;
        if (environmentVipId != null ? !environmentVipId.equals(vipV3.environmentVipId) : vipV3.environmentVipId != null)
            return false;
        if (ipv4Id != null ? !ipv4Id.equals(vipV3.ipv4Id) : vipV3.ipv4Id != null) return false;
        if (ipv6Id != null ? !ipv6Id.equals(vipV3.ipv6Id) : vipV3.ipv6Id != null) return false;
        if (ports != null ? !ports.equals(vipV3.ports) : vipV3.ports != null) return false;
        if (options != null ? !options.equals(vipV3.options) : vipV3.options != null) return false;
        return created != null ? created.equals(vipV3.created) : vipV3.created == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (business != null ? business.hashCode() : 0);
        result = 31 * result + (environmentVipId != null ? environmentVipId.hashCode() : 0);
        result = 31 * result + (ipv4Id != null ? ipv4Id.hashCode() : 0);
        result = 31 * result + (ipv6Id != null ? ipv6Id.hashCode() : 0);
        result = 31 * result + (ports != null ? ports.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    public static class VipOptions {

        public VipOptions() {
        }

        public VipOptions(Long cacheGroupId, Long trafficReturnId, Long timeoutId, Long persistenceId) {
            this.cacheGroupId = cacheGroupId;
            this.trafficReturnId = trafficReturnId;
            this.timeoutId = timeoutId;
            this.persistenceId = persistenceId;
        }

        @Key("cache_group")
        private Long cacheGroupId;

        @Key("traffic_return")
        private Long trafficReturnId;

        @Key("timeout")
        private Long timeoutId;

        @Key("persistence")
        private Long persistenceId;

        public Long getCacheGroupId() {
            return cacheGroupId;
        }

        public void setCacheGroupId(Long cacheGroupId) {
            this.cacheGroupId = cacheGroupId;
        }

        public Long getTrafficReturnId() {
            return trafficReturnId;
        }

        public void setTrafficReturnId(Long trafficReturnId) {
            this.trafficReturnId = trafficReturnId;
        }

        public Long getTimeoutId() {
            return timeoutId;
        }

        public void setTimeoutId(Long timeoutId) {
            this.timeoutId = timeoutId;
        }

        public Long getPersistenceId() {
            return persistenceId;
        }

        public void setPersistenceId(Long persistenceId) {
            this.persistenceId = persistenceId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VipOptions that = (VipOptions) o;

            if (cacheGroupId != null ? !cacheGroupId.equals(that.cacheGroupId) : that.cacheGroupId != null)
                return false;
            if (trafficReturnId != null ? !trafficReturnId.equals(that.trafficReturnId) : that.trafficReturnId != null)
                return false;
            if (timeoutId != null ? !timeoutId.equals(that.timeoutId) : that.timeoutId != null) return false;
            return persistenceId != null ? persistenceId.equals(that.persistenceId) : that.persistenceId == null;
        }

        @Override
        public int hashCode() {
            int result = cacheGroupId != null ? cacheGroupId.hashCode() : 0;
            result = 31 * result + (trafficReturnId != null ? trafficReturnId.hashCode() : 0);
            result = 31 * result + (timeoutId != null ? timeoutId.hashCode() : 0);
            result = 31 * result + (persistenceId != null ? persistenceId.hashCode() : 0);
            return result;
        }
    }

    public static class Port {

        @Key("id")
        private Long id;

        @Key("port")
        private Integer port;

        @Key("options")
        private PortOptions options;

        @Key("pools")
        private List<Pool> pools;

        public Port() {
        }

        public Port(Long id, Integer port, PortOptions options, List<Pool> pools) {
            this.id = id;
            this.port = port;
            this.options = options;
            this.pools = pools;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public PortOptions getOptions() {
            return options;
        }

        public void setOptions(PortOptions options) {
            this.options = options;
        }

        public List<Pool> getPools() {
            return pools;
        }

        public void setPools(List<Pool> pools) {
            this.pools = pools;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Port port1 = (Port) o;

            if (id != null ? !id.equals(port1.id) : port1.id != null) return false;
            if (port != null ? !port.equals(port1.port) : port1.port != null) return false;
            if (options != null ? !options.equals(port1.options) : port1.options != null) return false;
            return pools != null ? pools.equals(port1.pools) : port1.pools == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (port != null ? port.hashCode() : 0);
            result = 31 * result + (options != null ? options.hashCode() : 0);
            result = 31 * result + (pools != null ? pools.hashCode() : 0);
            return result;
        }
    }

    public static class Pool{

        @Key("id")
        private Long id;

        @Key("server_pool")
        private Long poolId;

        @Key("l7_rule")
        private Long l7RuleId;

        @Key("l7_value")
        private String l7Value;

        @Key("order")
        private Integer order;

        public Pool() {
        }

        public Pool(Long poolId, Long l7RuleId, String l7Value) {
            this.poolId = poolId;
            this.l7RuleId = l7RuleId;
            this.l7Value = l7Value;
            this.order = 0;
        }

        public Pool(Long id, Long poolId, Long l7RuleId, String l7Value, Integer order) {
            this.id = id;
            this.poolId = poolId;
            this.l7RuleId = l7RuleId;
            this.l7Value = l7Value;
            this.order = order;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getPoolId() {
            return poolId;
        }

        public void setPoolId(Long poolId) {
            this.poolId = poolId;
        }

        public Long getL7RuleId() {
            return l7RuleId;
        }

        public void setL7RuleId(Long l7RuleId) {
            this.l7RuleId = l7RuleId;
        }

        public String getL7Value() {
            return l7Value;
        }

        public void setL7Value(String l7Value) {
            this.l7Value = l7Value;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pool pool = (Pool) o;

            if (id != null ? !id.equals(pool.id) : pool.id != null) return false;
            if (poolId != null ? !poolId.equals(pool.poolId) : pool.poolId != null) return false;
            if (l7RuleId != null ? !l7RuleId.equals(pool.l7RuleId) : pool.l7RuleId != null) return false;
            if (l7Value != null ? !l7Value.equals(pool.l7Value) : pool.l7Value != null) return false;
            return order != null ? order.equals(pool.order) : pool.order == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (poolId != null ? poolId.hashCode() : 0);
            result = 31 * result + (l7RuleId != null ? l7RuleId.hashCode() : 0);
            result = 31 * result + (l7Value != null ? l7Value.hashCode() : 0);
            result = 31 * result + (order != null ? order.hashCode() : 0);
            return result;
        }
    }

    public static class PortOptions {

        @Key("l4_protocol")
        private Long l4ProtocolId;

        @Key("l7_protocol")
        private Long l7ProtocolId;

        public PortOptions() {
        }

        public PortOptions(Long l4ProtocolId, Long l7ProtocolId) {
            this.l4ProtocolId = l4ProtocolId;
            this.l7ProtocolId = l7ProtocolId;
        }

        public Long getL4ProtocolId() {
            return l4ProtocolId;
        }

        public void setL4ProtocolId(Long l4ProtocolId) {
            this.l4ProtocolId = l4ProtocolId;
        }

        public Long getL7ProtocolId() {
            return l7ProtocolId;
        }

        public void setL7ProtocolId(Long l7ProtocolId) {
            this.l7ProtocolId = l7ProtocolId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PortOptions that = (PortOptions) o;

            if (l4ProtocolId != null ? !l4ProtocolId.equals(that.l4ProtocolId) : that.l4ProtocolId != null)
                return false;
            return l7ProtocolId != null ? l7ProtocolId.equals(that.l7ProtocolId) : that.l7ProtocolId == null;
        }

        @Override
        public int hashCode() {
            int result = l4ProtocolId != null ? l4ProtocolId.hashCode() : 0;
            result = 31 * result + (l7ProtocolId != null ? l7ProtocolId.hashCode() : 0);
            return result;
        }
    }

    public static class VipV3Response extends GenericJson {

        @Key("vips")
        private List<VipV3> vips;

        public VipV3Response() {
        }

        public VipV3Response(VipV3 vip) {
            this.vips = Collections.singletonList(vip);
        }

        public VipV3 getVip() {
            if (!this.vips.isEmpty()) {
                return this.vips.get(0);
            }
            return null;
        }
    }
}
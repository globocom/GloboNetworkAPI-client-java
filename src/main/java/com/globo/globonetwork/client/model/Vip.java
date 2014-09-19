package com.globo.globonetwork.client.model;

import java.util.ArrayList;
import java.util.List;

import com.globo.globonetwork.client.model.Real.RealIP;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class Vip extends GenericXml {

    @Key
    private Long id;

    @Key
    private List<String> ips;

    @Key("ipv4_description")
    private String ipv4Description;

    @Key("vip_criado")
    private Boolean created;

    @Key("validado")
    private Boolean validated;

    @Key
    private String host;

    @Key
    private String cache;

    @Key("metodo_bal")
    private String method;

    @Key("persistencia")
    private String persistence;

    @Key("id_healthcheck_expect")
    private ObjectWithNullTag<Long> expectedHealthcheckId = new ObjectWithNullTag<Long>();

    @Key("healthcheck_type")
    private String healthcheckType;

    @Key
    private String healthcheck;

    @Key("maxcon")
    private Integer maxConn;

    @Key("portas_servicos")
    private ServicePorts servicePorts;

    @Key
    private Real reals;

    @Key
    private RealsWeights realsWeights;

    @Key
    private RealsPriorities realsPriorities;

    @Key("finalidade")
    private String finality;

    @Key("cliente")
    private String client;

    @Key("ambiente")
    private String environment;

    @Key("l7_filter")
    private String l7Filter;

    @Key("id_ipv4")
    private Long ipv4Id;

    @Key("id_ipv6")
    private Long ipv6Id;

    @Key("nome_servico")
    private String serviceName;

    @Key("areanegocio")
    private String businessArea;

    @Key
    private Integer timeout;

    @Key("rule_id")
    private Long ruleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public String getIpv4Description() {
        return ipv4Description;
    }

    public void setIpv4Description(String ipv4Description) {
        this.ipv4Description = ipv4Description;
    }

    public Boolean getCreated() {
        return created;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    protected Real getReals() {
        return reals;
    }

    protected void setReals(Real reals) {
        this.reals = reals;
    }

    public List<RealIP> getRealsIp() {
        // ensure never returns null.
        if (this.getReals() == null) {
            this.setReals(new Real());
        }
        if (this.getReals().getRealIps() == null) {
            this.getReals().setRealIps(new ArrayList<RealIP>());
        }
        return this.getReals().getRealIps();
    }

    public void setRealsIp(List<RealIP> realsIp) {
        this.getRealsIp().clear();
        this.getRealsIp().addAll(realsIp);
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPersistence() {
        return persistence;
    }

    public void setPersistence(String persistence) {
        this.persistence = persistence;
    }

    public String getHealthcheckType() {
        return healthcheckType;
    }

    public void setHealthcheckType(String healthcheckType) {
        this.healthcheckType = healthcheckType;
    }

    public String getHealthcheck() {
        return healthcheck;
    }

    public void setHealthcheck(String healthcheck) {
        this.healthcheck = healthcheck;
    }

    public Integer getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(Integer maxConn) {
        this.maxConn = maxConn;
    }

    protected ServicePorts getServicePorts() {
        return servicePorts;
    }

    protected void setServicePorts(ServicePorts servicePorts) {
        this.servicePorts = servicePorts;
    }

    public List<String> getPorts() {
        // ensure never returns null.
        if (this.getServicePorts() == null) {
            this.setServicePorts(new ServicePorts());
        }
        if (this.getServicePorts().getPorts() == null) {
            this.getServicePorts().setPorts(new ArrayList<String>());
        }
        return this.getServicePorts().getPorts();
    }

    public void setPorts(List<String> ports) {
        this.getPorts().clear();
        this.getPorts().addAll(ports);
    }

    protected RealsWeights getRealsWeights() {
        return realsWeights;
    }

    protected void setRealsWeights(RealsWeights realsWeights) {
        this.realsWeights = realsWeights;
    }

    public List<Integer> getRealsWeightsValues() {
        // ensure never returns null.
        if (this.getRealsWeights() == null) {
            this.setRealsWeights(new RealsWeights());
        }
        if (this.getRealsWeights().getRealsWeights() == null) {
            this.getRealsWeights().setRealsWeights(new ArrayList<Integer>());
        }
        return this.getRealsWeights().getRealsWeights();
    }

    public void setRealsWeightsValues(List<Integer> weights) {
        this.getRealsWeightsValues().clear();
        if (weights != null) {
            this.getRealsWeightsValues().addAll(weights);
        }
    }

    protected RealsPriorities getRealsPriorities() {
        return realsPriorities;
    }

    protected void setRealsPriorities(RealsPriorities realsPriorities) {
        this.realsPriorities = realsPriorities;
    }

    public List<Integer> getRealsPrioritiesValues() {
        // ensure never returns null.
        if (this.getRealsPriorities() == null) {
            this.setRealsPriorities(new RealsPriorities());
        }
        if (this.getRealsPriorities().getRealsPriorities() == null) {
            this.getRealsPriorities().setRealsPriorities(new ArrayList<Integer>());
        }
        return this.getRealsPriorities().getRealsPriorities();
    }

    public void setRealsPrioritiesValues(List<Integer> priorities) {
        this.getRealsPrioritiesValues().clear();
        if (priorities != null) {
            this.getRealsPrioritiesValues().addAll(priorities);
        }
    }

    public Vip() {
        super.name = "vip";
    }

    public String superName() {
        return super.name;
    }

    public static class ServicePorts extends GenericXml {
        @Key("porta")
        List<String> ports;

        public List<String> getPorts() {
            return ports;
        }

        public void setPorts(List<String> ports) {
            this.ports = ports;
        }

        public ServicePorts() {
            super.name = "portas_servicos";
        }

        public String superName() {
            return super.name;
        }
    }

    public static class RealsWeights extends GenericXml {
        @Key("reals_weight")
        private List<Integer> realsWeights;

        public RealsWeights() {
            super.name = "reals_weights";
        }

        public String superName() {
            return super.name;
        }

        public List<Integer> getRealsWeights() {
            return realsWeights;
        }

        public void setRealsWeights(List<Integer> realsWeights) {
            this.realsWeights = realsWeights;
        }
    }

    public static class RealsPriorities extends GenericXml {
        @Key("reals_priority")
        private List<Integer> realsPriorities;

        public RealsPriorities() {
            super.name = "reals_prioritys";
        }

        public String superName() {
            return super.name;
        }

        public List<Integer> getRealsPriorities() {
            return realsPriorities;
        }

        public void setRealsPriorities(List<Integer> realsPriorities) {
            this.realsPriorities = realsPriorities;
        }
    }

    public Long getExpectedHealthcheckId() {
        return expectedHealthcheckId.getValue();
    }

    public void setExpectedHealthcheckId(Long expectedHealthcheckId) {
        this.expectedHealthcheckId.setValue(expectedHealthcheckId);
    }

    public String getFinality() {
        return finality;
    }

    public void setFinality(String finality) {
        this.finality = finality;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getL7Filter() {
        return l7Filter;
    }

    public void setL7Filter(String l7Filter) {
        this.l7Filter = l7Filter;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }
    
    public static class VipRequest extends Vip {
        public VipRequest() {
            this.name = "requisicao_vip";
        }
    }
    
    public static class ObjectWithNullTag<T> extends GenericData {
        @Key("text()")
        private T value;
        public ObjectWithNullTag(T value) {
            this.value = value;
        }
        public ObjectWithNullTag() {
            this(null);
        }
        public void setValue(T value) {
            this.value = value;
        }
        public T getValue() {
            return this.value;
        }
    }
}

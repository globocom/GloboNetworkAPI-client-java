package com.globo.globonetwork.client.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public class VipJson extends GenericJson implements Vip{

    @Key("id")
    private Long id;

    @Key("ambiente")
    private String environment;

    @Key("finalidade")
    private String finality;

    @Key("ip")
    private Long ipv4Id;

    @Key("id_ipv6")
    private Long ipv6Id;

    @Key("host")
    private String host;

    @Key("areanegocio")
    private String businessArea;

    @Key("cliente")
    private String client;

    @Key("nome_servico")
    private String serviceName;

    @Key("timeout")
    private String timeout;

    @Key("metodo_bal")
    private String method;

    private List<String> ips;

    @Key("vip_criado")
    private Integer created;

    @Key("persistencia")
    private String persistence;

    @Key("cache")
    private String cache;

    @Key("maxcon")
    private String maxconn;

    @Key("healthcheck_type")
    private String healthcheckType;

    @Key("healthcheck")
    private String healthcheck;

    @Key("id_healthcheck_expect")
    private Long healthcheckExpectedId;

    @Key("validado")
    private Integer validated;

    @Key("pools")
    private List<Pool> pools;

    private List<Real.RealIP> realsIp;
    private String ipv4Description;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public List<String> getIps() {
        return this.ips;
    }

    @Override
    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    @Override
    public String getIpv4Description() {
        return ipv4Description;
    }

    @Override
    public void setIpv4Description(String ipv4Description) {
        this.ipv4Description = ipv4Description;
    }

    @Override
    public Boolean getCreated() {
        return created == 1;
    }

    @Override
    public void setCreated(Boolean created) {
        this.created = (created ? 1 : 0);
    }

    @Override
    public Boolean getValidated() {
        return validated != 0;
    }

    @Override
    public void setValidated(Boolean validated) {
        this.validated = (validated ? 1 : 0);
    }

    @Override
    public void setPersistence(String persistence) {
        this.persistence = persistence;
    }

    @Override
    public String getHealthcheckType() {
        return healthcheckType;
    }

    @Override
    public void setHealthcheckType(String healthcheckType) {
        this.healthcheckType = healthcheckType;
    }

    @Override
    public String getHealthcheck() {
        return healthcheck;
    }

    @Override
    public void setHealthcheck(String healthcheck) {
        this.healthcheck = healthcheck;
    }

    @Override
    public Integer getMaxConn() {
        return Integer.valueOf(maxconn);
    }

    @Override
    public void setMaxConn(Integer maxConn) {
        this.maxconn = (maxConn != null ? maxConn.toString() : "0");
    }

    @Override
    public List<String> getServicePorts() {
        return null;
    }

    @Override
    public void setServicePorts(List<String> servicePorts) {

    }

    @Override
    public List<Integer> getRealsPriorities() {
        return null;
    }

    @Override
    public void setRealsPriorities(List<Integer> realsPriorities) {

    }

    @Override
    public List<Integer> getRealsWeights() {
        return null;
    }

    @Override
    public void setRealsWeights(List<Integer> realsWeights) {

    }

    @Override
    public String superName() {
        return null;
    }

    @Override
    public Long getExpectedHealthcheckId() {
        return healthcheckExpectedId;
    }

    @Override
    public void setExpectedHealthcheckId(Long expectedHealthcheckId) {
        this.healthcheckExpectedId = expectedHealthcheckId;
    }

    @Override
    public String getFinality() {
        return finality;
    }

    @Override
    public void setFinality(String finality) {
        this.finality = finality;
    }

    @Override
    public String getClient() {
        return client;
    }

    @Override
    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String getEnvironment() {
        return environment;
    }

    @Override
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public String getL7Filter() {
        return null;
    }

    @Override
    public void setL7Filter(String l7Filter) {

    }

    @Override
    public Long getIpv4Id() {
        return ipv4Id;
    }

    @Override
    public void setIpv4Id(Long ipv4Id) {
        this.ipv4Id = ipv4Id;
    }

    @Override
    public Long getIpv6Id() {
        return ipv6Id;
    }

    @Override
    public void setIpv6Id(Long ipv6Id) {
        this.ipv6Id = ipv6Id;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String getBusinessArea() {
        return businessArea;
    }

    @Override
    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    @Override
    public Integer getTimeout() {
        return Integer.valueOf(timeout);
    }

    @Override
    public void setTimeout(Integer timeout) {
        this.timeout = timeout != null ? timeout.toString() : "0";
    }

    @Override
    public Long getRuleId() {
        return null;
    }

    @Override
    public void setRuleId(Long ruleId) {

    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public List<Real.RealIP> getRealsIp() {
        return realsIp;
    }

    @Override
    public void setRealsIp(List<Real.RealIP> realsIp) {
        this.realsIp = realsIp;
    }

    @Override
    public Vip addReal(String name, String ip) {
        return null;
    }

    @Override
    public String getCache() {
        return cache;
    }

    @Override
    public void setCache(String cache) {
        this.cache = cache;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;

    }

    @Override
    public String getPersistence() {
        return persistence;
    }

    @Override
    public List<Pool> getPools() {
        return pools;
    }

    public void setPools(List<Pool> pools) {
        this.pools = pools;
    }
}

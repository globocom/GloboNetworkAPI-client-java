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

    private Integer timeout;
    private String method;

    private List<String> ips;
    private Boolean created;
    private String persistence;

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
        return null;
    }

    @Override
    public void setIpv4Description(String ipv4Description) {

    }

    @Override
    public Boolean getCreated() {
        return created;
    }

    @Override
    public void setCreated(Boolean created) {
        this.created = created;
    }

    @Override
    public Boolean getValidated() {
        return null;
    }

    @Override
    public void setValidated(Boolean validated) {

    }

    @Override
    public void setPersistence(String persistence) {
        this.persistence = persistence;
    }

    @Override
    public String getHealthcheckType() {
        return null;
    }

    @Override
    public void setHealthcheckType(String healthcheckType) {

    }

    @Override
    public String getHealthcheck() {
        return null;
    }

    @Override
    public void setHealthcheck(String healthcheck) {

    }

    @Override
    public Integer getMaxConn() {
        return null;
    }

    @Override
    public void setMaxConn(Integer maxConn) {

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
        return null;
    }

    @Override
    public void setExpectedHealthcheckId(Long expectedHealthcheckId) {

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
        return timeout;
    }

    @Override
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
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
        return null;
    }

    @Override
    public void setRealsIp(List<Real.RealIP> realsIp) {

    }

    @Override
    public Vip addReal(String name, String ip) {
        return null;
    }

    @Override
    public String getCache() {
        return null;
    }

    @Override
    public void setCache(String cache) {

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
        return null;
    }
}

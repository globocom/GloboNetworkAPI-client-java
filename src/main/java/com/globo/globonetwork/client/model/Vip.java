package com.globo.globonetwork.client.model;

import java.util.ArrayList;
import java.util.List;

public interface Vip {

    public Long getId();

    public void setId(Long id);

    public List<String> getIps();

    public void setIps(List<String> ips) ;

    public String getIpv4Description() ;

    public void setIpv4Description(String ipv4Description) ;

    public Boolean getCreated() ;

    public void setCreated(Boolean created) ;

    public Boolean getValidated() ;

    public void setValidated(Boolean validated) ;

    public void setPersistence(String persistence);
    public String getHealthcheckType();

    public void setHealthcheckType(String healthcheckType);
    public String getHealthcheck();

    public void setHealthcheck(String healthcheck);

    public Integer getMaxConn();

    public void setMaxConn(Integer maxConn);
    public List<String> getServicePorts();
    public void setServicePorts(List<String> servicePorts);
    public List<Integer> getRealsPriorities();
    public void setRealsPriorities(List<Integer> realsPriorities);
    public List<Integer> getRealsWeights();
    public void setRealsWeights(List<Integer> realsWeights);


    public String superName();
    public Long getExpectedHealthcheckId();
    public void setExpectedHealthcheckId(Long expectedHealthcheckId) ;

    public String getFinality();
    public void setFinality(String finality);
    public String getClient();

    public void setClient(String client);
    public String getEnvironment();
    public void setEnvironment(String environment);
    public String getL7Filter();

    public void setL7Filter(String l7Filter);
    public Long getIpv4Id();
    public void setIpv4Id(Long ipv4Id);
    public Long getIpv6Id();
    public void setIpv6Id(Long ipv6Id);
    public String getServiceName();
    public void setServiceName(String serviceName);
    public String getBusinessArea();
    public void setBusinessArea(String businessArea);
    public Integer getTimeout();

    public void setTimeout(Integer timeout);
    public Long getRuleId();
    public void setRuleId(Long ruleId);


    public String getHost();

    public void setHost(String host);

    public List<Real.RealIP> getRealsIp();

    public void setRealsIp(List<Real.RealIP> realsIp);

    public Vip addReal(String name, String ip);

    public String getCache();

    public void setCache(String cache);

    public String getMethod();

    public void setMethod(String method);

    public String getPersistence();

}

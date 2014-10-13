package com.globo.globonetwork.client.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.Real.RealIP;
import com.globo.globonetwork.client.model.Vip;
import com.globo.globonetwork.client.model.Vip.VipRequest;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ArrayMap;
import com.google.api.client.xml.GenericXml;

public class VipAPI extends BaseAPI<Vip> {

    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    static final JsonObjectParser parser = new JsonObjectParser(JSON_FACTORY);

    public VipAPI(RequestProcessor transport) {
        super(transport);
    }

    public Vip getById(Long vipId) throws GloboNetworkException {

        try {
            GloboNetworkRoot<Vip> globoNetworkRoot = this.get("/requestvip/getbyid/" + vipId + "/");
            if (globoNetworkRoot == null) {
                // Problems reading the XML
                throw new GloboNetworkException("Invalid XML response");
            } else if (globoNetworkRoot.getObjectList() == null || globoNetworkRoot.getObjectList().isEmpty()) {
                return null;
            } else if (globoNetworkRoot.getObjectList().size() > 1) {
                // Something is wrong, id should be unique
                throw new GloboNetworkException("Multiple VIPs returned with single ID");
            } else {
                return globoNetworkRoot.getObjectList().get(0);
            }
        } catch (GloboNetworkErrorCodeException ex) {
            if (ex.getCode() == GloboNetworkErrorCodeException.VIP_NOT_REGISTERED) {
                return null;
            }

            throw ex;
        }
    }

    public List<Vip> getByIp(String ip) throws GloboNetworkException {

        GenericXml data = new GenericXml();
        data.set("ip", ip);
        data.set("start_record", 0);
        data.set("end_record", 100);
        data.set("custom_search", "");
        data.set("create", "");
        data.set("asorting_cols", "");
        data.set("searchable_columns", "");
        
        GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
        payload.set("vip", data);

        List<Vip> result = new ArrayList<Vip>();
        GloboNetworkRoot<GenericXml> globoNetworkRoot = this.getTransport().post("/requestvip/get_by_ip_id/", payload, GenericXml.class);
        if (globoNetworkRoot == null) {
            // Problems reading the XML
            throw new GloboNetworkException("Invalid XML response");
        } else if (globoNetworkRoot.getObjectList() != null) {
            for (GenericXml rawVip: globoNetworkRoot.getObjectList()) {
                // because this vip object there is not all information about the vip, 
                // I search vip by id before put in list
                if ("vips".equals(rawVip.name) && rawVip.containsKey("id")) {
                    @SuppressWarnings("unchecked")
                    ArrayMap<String, String> rawId = (ArrayMap<String, String>)  ((List<ArrayMap<String, String>>) rawVip.get("id")).get(0);
                    Vip completeVip = this.getById(Long.valueOf(rawId.getValue(0)));
                    if (completeVip != null) {
                        result.add(completeVip);
                    }
                }
            }
        }
        return result;
    }

    public void validate(Long vipId) throws GloboNetworkException {
        this.get("/vip/validate/" + vipId + "/");
    }

    public Vip add(Long ipv4Id, Long ipv6Id, Long expectedHealthcheckId, String finality, String client, String environment, String cache, String balancingMethod,
            String persistence, String healthcheckType, String healthcheck, Integer timeout, String host, Integer maxConn, String businessArea, String serviceName,
            String l7Filter, List<RealIP> realsIp, List<Integer> realsPriorities, List<Integer> realsWeights, List<String> ports, Long ruleId) throws GloboNetworkException {

        Vip vip = new Vip();
        vip.setIpv4Id(ipv4Id);
        vip.setIpv6Id(ipv6Id);
        vip.setExpectedHealthcheckId(expectedHealthcheckId);
        vip.setFinality(finality);
        vip.setClient(client);
        vip.setEnvironment(environment);
        vip.setCache(cache);
        vip.setMethod(balancingMethod);
        vip.setPersistence(persistence);
        vip.setHealthcheckType(healthcheckType);
        vip.setHealthcheck(healthcheck);
        vip.setTimeout(timeout);
        vip.setHost(host);
        vip.setMaxConn(maxConn);
        vip.setBusinessArea(businessArea);
        vip.setServiceName(serviceName);
        vip.setL7Filter(l7Filter);
        vip.setRealsIp(realsIp);
        vip.setRealsPriorities(realsPriorities);
        vip.setRealsWeights(realsWeights);
        vip.setServicePorts(ports);
        vip.setRuleId(ruleId);

        GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        GloboNetworkRoot<VipRequest> globoNetworkRoot = this.getTransport().post("/requestvip/", payload, VipRequest.class);
        if (globoNetworkRoot == null) {
            // Problems reading the XML
            throw new GloboNetworkException("Invalid XML response");
        }
        
        VipRequest vipRequest = globoNetworkRoot.getFirstObject();
        if (vipRequest == null || vipRequest.getId() == null) {
            throw new GloboNetworkException("Invalid vip request response or ID");
        }
        
        return this.getById(vipRequest.getId());
    }
    
    public void alter(Long vipId, Long ipv4Id, Long ipv6Id, Long expectedHealthcheckId, Boolean validated, Boolean created, String finality, String client, String environment, 
            String cache, String balancingMethod, String persistence, String healthcheckType, String healthcheck, Integer timeout, String host, Integer maxConn, String businessArea, String serviceName,
            String l7Filter, List<RealIP> realsIp, List<Integer> realsPriorities, List<Integer> realsWeights, List<String> ports, Long ruleId) throws GloboNetworkException {

        Vip vip = new Vip();
        vip.setId(vipId);
        vip.setIpv4Id(ipv4Id);
        vip.setIpv6Id(ipv6Id);
        vip.setExpectedHealthcheckId(expectedHealthcheckId);
        vip.setValidated(validated);
        vip.setCreated(created);
        vip.setFinality(finality);
        vip.setClient(client);
        vip.setEnvironment(environment);
        vip.setCache(cache);
        vip.setMethod(balancingMethod);
        vip.setPersistence(persistence);
        vip.setHealthcheckType(healthcheckType);
        vip.setHealthcheck(healthcheck);
        vip.setTimeout(timeout);
        vip.setHost(host);
        vip.setMaxConn(maxConn);
        vip.setBusinessArea(businessArea);
        vip.setServiceName(serviceName);
        vip.setL7Filter(l7Filter);
        vip.setRealsIp(realsIp);
        vip.setRealsPriorities(realsPriorities);
        vip.setRealsWeights(realsWeights);
        vip.setServicePorts(ports);
        vip.setRuleId(ruleId);

        GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.put("/requestvip/" + vipId + "/", payload);
    }

    public void create(Long vipId) throws GloboNetworkException {
        Vip vip = new Vip();
        vip.set("id_vip", vipId);

        GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.post("/vip/create/", payload);
    }

    // remove VIP from NetworkAPI DB
    public void removeVip(Long vipId) throws GloboNetworkException {
        this.delete("/vip/delete/" + vipId + "/");
    }

    public void removeVip(Long vipId, boolean keepIp) throws GloboNetworkException {
        String url = "/vip/delete/" + vipId + "/";
        if (keepIp) {
            url += "?keep_ip=1";
        }
        this.delete(url);
    }

    // remove VIP from network device removeScriptVip
    public void removeScriptVip(Long vipId) throws GloboNetworkException {
        Vip vip = new Vip();
        vip.set("id_vip", vipId);

        GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.post("/vip/remove/", payload);
    }

    public void addReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "add", "v4");
    }

    public void enableReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "ena", "v4");
    }

    public void disableReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "dis", "v4");
    }

    public void checkReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "chk", "v4");
    }

    public void removeReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "del", "v4");
    }

    public void addRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "add", "v6");
    }

    public void enableRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "ena", "v6");
    }

    public void disableRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "dis", "v6");
    }

    public void checkRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "chk", "v6");
    }

    public void removeRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "del", "v6");
    }

    private void performRealOperation(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort, String operation, String networkVersion) throws GloboNetworkException {
        Vip vip = new Vip();
        vip.set("vip_id", vipId);
        vip.set("ip_id", ipId);
        vip.set("equip_id", equipId);
        vip.set("port_vip", vipPort);
        vip.set("port_real", realPort);
        vip.set("operation", operation);
        vip.set("network_version", networkVersion);

        GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.post("/vip/real/", payload);
    }
    
    public void alterHealthcheck(Long vipId, String healthcheckType, String healthcheck, Long expectedHealthcheckId) throws GloboNetworkException {
        Vip vip = new Vip();
        vip.setHealthcheckType(healthcheckType);
        vip.setHealthcheck(healthcheck);
        vip.setExpectedHealthcheckId(expectedHealthcheckId);
        
        GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.put("/vip/" + vipId + "/healthcheck/", payload);
    }

    public void alterPersistence(Long vipId, String persistence) throws GloboNetworkException {
        Vip vip = new Vip();
        vip.setPersistence(persistence);
        
        GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.put("/vip/" + vipId + "/persistence/", payload);
    }

    public String generateVipEditingUrl(Long vipId, String vipServerUrl) throws GloboNetworkException {

        if (vipServerUrl == null || "".equals(vipServerUrl)) {
            throw new GloboNetworkException("Invalid VIP server URL");
        }

        HttpRequestFactory requestFactory = new ApacheHttpTransport.Builder().build().createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
                request.setNumberOfRetries(1);
                request.setThrowExceptionOnExecuteError(false);
                request.setParser(parser);
                request.setLoggingEnabled(true);
                request.getHeaders().setUserAgent("NetworkAPI-inside-cloudstack");
                request.setCurlLoggingEnabled(true);
            }
        });

        try {
            String body = "user=" + this.getTransport().getUsername() + "@" + this.getTransport().getPassword();
            HttpContent content = new ByteArrayContent("application/x-www-form-urlencoded", body.getBytes());
            GenericUrl tokenRequestUrl = new GenericUrl(vipServerUrl);
            tokenRequestUrl.setRawPath("/vip-request/token/");
            HttpRequest request = requestFactory.buildPostRequest(tokenRequestUrl, content);

            HttpResponse response = request.execute();
            GenericJson json = response.parseAs(GenericJson.class);
            String token = (String)json.get("token");
            String path = (String)json.get("url");

            GenericUrl vipEditingUrl = new GenericUrl(vipServerUrl);
            vipEditingUrl.setRawPath(path);
            vipEditingUrl.set("token", token);
            return vipEditingUrl.build();
        } catch (IOException e) {
            throw new GloboNetworkException("Error generating VIP url", e);
        }
    }
}

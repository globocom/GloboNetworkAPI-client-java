package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.Vip;
import com.globo.globonetwork.client.model.VipJson;
import com.globo.globonetwork.client.model.VipPoolMap;
import com.globo.globonetwork.client.model.VipXml;
import com.google.api.client.http.HttpStatusCodes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.Real.RealIP;
import com.globo.globonetwork.client.model.VipXml.VipRequest;
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
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class VipAPI extends BaseXmlAPI<VipXml> {

    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    static final JsonObjectParser parser = new JsonObjectParser(JSON_FACTORY);

    private HttpJSONRequestProcessor jsonRequestProcessor;

    public VipAPI(RequestProcessor transport, HttpJSONRequestProcessor jsonRequestProcessor) {
        super(transport);
        this.jsonRequestProcessor = jsonRequestProcessor;
    }

    /**
        @see VipAPI#getByPk(Long)
     */
    @Trace(dispatcher = true)
    @Deprecated
    public Vip getById(Long vipId) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/getVipById");

        try {
            GloboNetworkRoot<VipXml> globoNetworkRoot = this.get("/requestvip/getbyid/" + vipId + "/");
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

    @Trace(dispatcher = true)
    public Vip getByPk(Long vipId) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/vip/getByPk/");
        try {
            String uri = "/api/vip/request/get/" + vipId.toString() + "/";

            Vip vip = (Vip) jsonRequestProcessor.get(uri, VipJson.class);

            return vip;
        }catch (GloboNetworkErrorCodeException e) {

            if ( e.getCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }


    @Trace(dispatcher = true)
    public Vip save(Long ipv4Id,
                    Long ipv6Id,
                    String finality,
                    String client,
                    String environment,
                    String cache,
                    String persistence,
                    Integer timeout,
                    String host,
                    String businessArea,
                    String serviceName,
                    String l7Filter,
                    List<VipPoolMap> vipPortsToPools,
                    Long ruleId,
                    Long pk) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/vip/save/");

        GenericJson json = new GenericJson();

        json.set("ip", ipv4Id);
        json.set("ipv6", ipv6Id);
        json.set("finalidade",  finality);
        json.set("cliente",  client);
        json.set("ambiente",  environment);
        json.set("cache",  cache);
        json.set("timeout",  timeout);
        json.set("persistencia",  persistence);
        json.set("timeout",  timeout.toString());
        json.set("host",  host);
        json.set("areanegocio",  businessArea);
        json.set("nome_servico",  serviceName);
        json.set("l7_filter",  l7Filter);
        json.set("rule",  ruleId);

        if ( vipPortsToPools == null ){
            vipPortsToPools = new ArrayList<VipPoolMap>();
        }

        json.set("vip_ports_to_pools", vipPortsToPools);


        String uri = "/api/vip/request/save/";

        VipJson vip;
        if (pk != null){
            vip = (VipJson) jsonRequestProcessor.put(uri + pk.toString(), json, VipJson.class);
        } else {
            vip = (VipJson) jsonRequestProcessor.post(uri, json, VipJson.class);
        }

        return vip;
    }


    @Trace(dispatcher = true)
    public List<Vip> getByIp(String ip) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/getVipByIp");

        GenericXml data = new GenericXml();
        data.set("ip", ip);
        data.set("start_record", 0);
        data.set("end_record", 100);
        data.set("custom_search", "");
        data.set("create", "");
        data.set("asorting_cols", "");
        data.set("searchable_columns", "");
        
        GloboNetworkRoot<VipXml> payload = new GloboNetworkRoot<VipXml>();
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

    @Trace(dispatcher = true)
    public void validate(Long vipId) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/validateVip");

        this.get("/vip/validate/" + vipId + "/");
    }

    @Trace(dispatcher = true)
    public Vip add(Long ipv4Id, Long ipv6Id, Long expectedHealthcheckId, String finality, String client, String environment, String cache, String balancingMethod,
            String persistence, String healthcheckType, String healthcheck, Integer timeout, String host, Integer maxConn, String businessArea, String serviceName,
            String l7Filter, List<RealIP> realsIp, List<Integer> realsPriorities, List<Integer> realsWeights, List<String> ports, Long ruleId) throws GloboNetworkException {

        NewRelic.setTransactionName(null, "/globonetwork/addVip");

        VipXml vip = new VipXml();
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

        GloboNetworkRoot<VipXml> payload = new GloboNetworkRoot<VipXml>();
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

    @Trace(dispatcher = true)
    public void alter(Long vipId, Long ipv4Id, Long ipv6Id, Long expectedHealthcheckId, Boolean validated, Boolean created, String finality, String client, String environment, 
            String cache, String balancingMethod, String persistence, String healthcheckType, String healthcheck, Integer timeout, String host, Integer maxConn, String businessArea, String serviceName,
            String l7Filter, List<RealIP> realsIp, List<Integer> realsPriorities, List<Integer> realsWeights, List<String> ports, Long ruleId) throws GloboNetworkException {

        NewRelic.setTransactionName(null, "/globonetwork/alterVip");

        VipXml vip = new VipXml();
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

        GloboNetworkRoot<VipXml> payload = new GloboNetworkRoot<VipXml>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.put("/requestvip/" + vipId + "/", payload);
    }

    @Trace(dispatcher = true)
    public void create(Long vipId) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/createVip");

        VipXml vip = new VipXml();
        vip.set("id_vip", vipId);

        GloboNetworkRoot<VipXml> payload = new GloboNetworkRoot<VipXml>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.post("/vip/create/", payload);
    }

    // remove VIP from NetworkAPI DB
    @Trace(dispatcher = true)
    public void removeVip(Long vipId) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/deleteVip");

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
    @Trace(dispatcher = true)
    public void removeScriptVip(Long vipId) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/removeVip");

        VipXml vip = new VipXml();
        vip.set("id_vip", vipId);

        GloboNetworkRoot<VipXml> payload = new GloboNetworkRoot<VipXml>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.post("/vip/remove/", payload);
    }

    @Trace(dispatcher = true)
    public void addReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/addReal");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "add", "v4");
    }

    @Trace(dispatcher = true)
    public void enableReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/enableReal");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "ena", "v4");
    }

    @Trace(dispatcher = true)
    public void disableReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/disableReal");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "dis", "v4");
    }

    @Trace(dispatcher = true)
    public void checkReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/checkReal");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "chk", "v4");
    }

    @Trace(dispatcher = true)
    public void removeReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/removeReal");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "del", "v4");
    }

    @Trace(dispatcher = true)
    public void addRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/addRealIpv6");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "add", "v6");
    }

    @Trace(dispatcher = true)
    public void enableRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/enableRealIpv6");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "ena", "v6");
    }

    @Trace(dispatcher = true)
    public void disableRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/disableRealIpv6");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "dis", "v6");
    }

    @Trace(dispatcher = true)
    public void checkRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/checkRealIpv6");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "chk", "v6");
    }

    @Trace(dispatcher = true)
    public void removeRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/removeRealIpv6");

        this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "del", "v6");
    }

    private void performRealOperation(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort, String operation, String networkVersion) throws GloboNetworkException {
        VipXml vip = new VipXml();
        vip.set("vip_id", vipId);
        vip.set("ip_id", ipId);
        vip.set("equip_id", equipId);
        vip.set("port_vip", vipPort);
        vip.set("port_real", realPort);
        vip.set("operation", operation);
        vip.set("network_version", networkVersion);

        GloboNetworkRoot<VipXml> payload = new GloboNetworkRoot<VipXml>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.post("/vip/real/", payload);
    }

    @Trace(dispatcher = true)
    public void alterHealthcheck(Long vipId, String healthcheckType, String healthcheck, Long expectedHealthcheckId) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/alterHealthcheck");

        VipXml vip = new VipXml();
        vip.setHealthcheckType(healthcheckType);
        vip.setHealthcheck(healthcheck);
        vip.setExpectedHealthcheckId(expectedHealthcheckId);
        
        GloboNetworkRoot<VipXml> payload = new GloboNetworkRoot<VipXml>();
        payload.getObjectList().add(vip);
        payload.set("vip", vip);

        this.put("/vip/" + vipId + "/healthcheck/", payload);
    }

    @Trace(dispatcher = true)
    public void alterPersistence(Long vipId, String persistence) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/alterPersistence");

        VipXml vip = new VipXml();
        vip.setPersistence(persistence);
        
        GloboNetworkRoot<VipXml> payload = new GloboNetworkRoot<VipXml>();
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

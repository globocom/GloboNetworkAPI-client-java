package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.VipV3;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.ArrayMap;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import java.math.BigDecimal;
import java.util.ArrayList;

public class VipV3API {

    private final HttpJSONRequestProcessor requestProcessor;

    public VipV3API(HttpJSONRequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Trace(dispatcher = true)
    public VipV3 getById(Long id) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/v3/getVipById");
        VipV3.VipV3Response response = (VipV3.VipV3Response) requestProcessor.get("/api/v3/vip-request/" + id + "/", VipV3.VipV3Response.class);
        return response.getVip();
    }

    @Trace(dispatcher = true)
    public VipV3 save(VipV3 vip) throws GloboNetworkException {
        if(vip.getId() != null){
            NewRelic.setTransactionName(null, "/globonetwork/v3/updateVip/");
            requestProcessor.put("/api/v3/vip-request/" + vip.getId() + "/", new VipV3.VipV3Response(vip));
        }else{
            NewRelic.setTransactionName(null, "/globonetwork/v3/createVip/");
            String response = requestProcessor.post("/api/v3/vip-request/", new VipV3.VipV3Response(vip));
            ArrayList<ArrayMap> parse = HttpJSONRequestProcessor.parse(response, ArrayList.class);
            vip.setId(((BigDecimal) parse.get(0).get("id")).longValue());
        }
        return vip;
    }

    @Trace(dispatcher = true)
    public void updatePersistence(Long id, Long persistenceId) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/v3/updatePersistence/");
        VipV3 vip = new VipV3();
        vip.setId(id);
        VipV3.VipOptions options = new VipV3.VipOptions();
        options.setPersistenceId(persistenceId);
        vip.setOptions(options);
        requestProcessor.patch("/api/v3/vip-request/deploy/" + id + "/", new VipV3.VipV3Response(vip));
    }

    @Trace(dispatcher = true)
    public void deploy(Long id) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/v3/deployVip/");
        requestProcessor.post("/api/v3/vip-request/deploy/" + id + "/", GenericJson.class);
    }

    @Trace(dispatcher = true)
    public void deployUpdate(VipV3 vip) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/v3/deployVip/");
        requestProcessor.put("/api/v3/vip-request/deploy/" + vip.getId() + "/", new VipV3.VipV3Response(vip));
    }

    @Trace(dispatcher = true)
    public void undeploy(Long id) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/v3/undeployVip/");
        requestProcessor.delete("/api/v3/vip-request/deploy/" + id + "/", GenericJson.class);
    }

    @Trace(dispatcher = true)
    public void delete(Long id, Boolean keepIp) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/v3/deleteVip/");
        String url = "/api/v3/vip-request/" + id + "/";
        if (keepIp) {
            url += "?keepip=1";
        }
        requestProcessor.delete(url, GenericJson.class);
    }
}



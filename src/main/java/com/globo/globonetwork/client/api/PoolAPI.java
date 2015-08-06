package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.Pool;
import com.globo.globonetwork.client.model.Real;
import com.google.api.client.json.GenericJson;
import com.newrelic.api.agent.NewRelic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PoolAPI extends BaseJsonAPI<Pool>{

    public PoolAPI(HttpJSONRequestProcessor processor) {
        super(processor);
    }

    /**
     *
     * @param lbmethod -  client python is called balancing
     * @param defaultLimit - client python is called maxcom
     * @throws GloboNetworkException
     */
    public Pool save(Long id, String identifier, Integer defaultPort, Long environment, String lbmethod,
                     String healthcheckType, String healthcheckExpect, String healthcheckRequest, Integer defaultLimit,
                     List<Real.RealIP> realIps, List<String> equipNames, List<Long> idEquips, List<Integer> priorities,
                     List <Long> weights,  List<Integer> realPorts, List<Long> idPoolMembers ) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/save");

        GenericJson serverPool = new GenericJson();
        if (id != null ){
            serverPool.set("id", id);
        }
        serverPool.set("environment", environment);
        serverPool.set("identifier", identifier );
        serverPool.set("default_port", defaultPort);
        serverPool.set("balancing", lbmethod);  // field name is different in globoNetworkAPI: balacing
        serverPool.set("maxcom", defaultLimit);  // field name is different in globoNetworkAPI: maxconn


        serverPool.set("healthcheck_type", healthcheckType);
        serverPool.set("healthcheck_expect", healthcheckExpect);
        serverPool.set("healthcheck_request", healthcheckRequest);


        serverPool.set("id_pool_member", idPoolMembers);
        List<GenericJson> realIpsApi = new ArrayList<GenericJson>();
        for (Real.RealIP realIP : realIps) {
            GenericJson json = new GenericJson();
            json.set("id", realIP.getIpId());
            json.set("ip", realIP.getRealIp());
            realIpsApi.add(json);
        }
        serverPool.set("ip_list_full", realIpsApi);
        serverPool.set("priorities", priorities);
        serverPool.set("ports_reals", realPorts);
        serverPool.set("nome_equips", equipNames);
        serverPool.set("id_equips", idEquips);
        serverPool.set("weight", weights);


        GenericJson json = getTransport().post("/api/pools/save/", serverPool, GenericJson.class);

        return new Pool(Long.valueOf(json.get("pool").toString()));
    }

    public List<Pool> listByEnvVip(Long envVipId) throws GloboNetworkException, IOException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/listByEnvVip");

        String uri = "/api/pools/list/by/environment/vip/" + envVipId.toString() + "/";
        String result = getTransport().get(uri);

        Pool.PoolList list = HttpJSONRequestProcessor.parse(result, Pool.PoolList.class);

        return list;
    }



}

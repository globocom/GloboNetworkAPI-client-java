package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.Pool;
import com.globo.globonetwork.client.model.Pool.ServerPool;
import com.globo.globonetwork.client.model.Pool.ServerPool.HealthCheck;
import com.globo.globonetwork.client.model.Real;
import com.google.api.client.json.GenericJson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PoolAPI extends BaseJsonAPI<Pool>{

    public PoolAPI(HttpJSONRequestProcessor processor) {
        super(processor);
    }

    public Pool getByPk(Long pk) throws GloboNetworkException {
        String suffix = "/api/pools/getbypk/" + pk.toString() + "/";
        Pool pool = (Pool) getTransport().get(suffix, Pool.class );
        return pool;
    }


    //id, identifier, default_port, hc, env, balancing, maxconn, id_pool_member


    public void save(Long id, String identifier, Integer defaultPort, Long environment, String lbmethod,
                     String healthcheckType, String healthcheckExpect, String healthcheckRequest, Integer defaultLimit,
                     List<Real.RealIP> realIps, List<String> equipNames, List<Long> idEquips, List<Integer> priorities,
                     List <Long> weights,  List<Integer> realPorts, List<Long> idPoolMembers ) throws GloboNetworkException {


        GenericJson serverPool = new GenericJson();
        serverPool.set("id", "");
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


        getTransport().post("/api/pools/save/", serverPool, GenericJson.class);
    }

}

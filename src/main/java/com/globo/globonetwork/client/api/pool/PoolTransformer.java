package com.globo.globonetwork.client.api.pool;

import com.globo.globonetwork.client.model.Real;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.ArrayList;
import java.util.List;

public class PoolTransformer {


    public static GenericJson saveJsonFrom(Long id, String identifier, Integer defaultPort, Long environment, String lbmethod,
                                           String healthcheckType, String healthcheckExpect, String healthcheckRequest, Integer maxconn,
                                           List<Real.RealIP> realIps, List<String> equipNames, List<Long> idEquips, List<Integer> priorities,
                                           List<Long> weights, List<Integer> realPorts, List<Long> idPoolMembers, String serviceDownAction, String healthcheckDestination) {

        GenericJson serverPool = new GenericJson();
        if (id != null ){
            serverPool.set("id", id);
        }
        serverPool.set("environment", environment);
        serverPool.set("identifier", identifier );
        serverPool.set("default_port", defaultPort);
        serverPool.set("balancing", lbmethod);  // field name is different in globoNetworkAPI: balacing
        serverPool.set("maxcom", maxconn);  // field name is different in globoNetworkAPI: maxconn
        if(serviceDownAction != null) {
            serverPool.set("service-down-action", serviceDownAction);
        }

        serverPool.set("healthcheck_type", healthcheckType);
        serverPool.set("healthcheck_expect", healthcheckExpect);
        serverPool.set("healthcheck_request", healthcheckRequest);
        if(healthcheckDestination != null){
            serverPool.set("healthcheck_destination", "*:" + healthcheckDestination);
        }else{
            serverPool.set("healthcheck_destination", "*:*");
        }

        serverPool.set("id_pool_member", idPoolMembers);
        List<GenericJson> realIpsApi = new ArrayList<GenericJson>();
        if ( realIps != null ) {
            for (Real.RealIP realIP : realIps) {
                GenericJson json = new GenericJson();
                json.set("id", realIP.getIpId());
                json.set("ip", realIP.getRealIp());
                realIpsApi.add(json);
            }
        }

        serverPool.set("ip_list_full", realIpsApi);
        serverPool.set("nome_equips", equipNames);
        serverPool.set("id_equips", idEquips);
        serverPool.set("priorities", priorities);
        serverPool.set("ports_reals", realPorts);
        serverPool.set("weight", weights);

        return serverPool;
    }
}

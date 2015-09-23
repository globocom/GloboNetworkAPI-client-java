package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.api.pool.PoolTransformer;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.Pool;
import com.globo.globonetwork.client.model.PoolOption;
import com.globo.globonetwork.client.model.Real;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.ArrayMap;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.content.text.Generic;

public class PoolAPI extends BaseJsonAPI<Pool>{
    private static final Logger LOGGER = LoggerFactory.getLogger(PoolAPI.class);
    public PoolAPI(HttpJSONRequestProcessor processor) {
        super(processor);
    }

    /**
     *
     * @param lbmethod -  client python is called balancing
     * @throws GloboNetworkException
     */
    @Trace(dispatcher = true)
    public Pool save(Long id, String identifier, Integer defaultPort, Long environment, String lbmethod,
                     String healthcheckType, String healthcheckExpect, String healthcheckRequest, Integer maxconn,
                     List<Real.RealIP> realIps, List<String> equipNames, List<Long> idEquips, List<Integer> priorities,
                     List <Long> weights,  List<Integer> realPorts, List<Long> idPoolMembers, String serviceDownAction, String healthcheckDestination) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/save");


        GenericJson serverPool = PoolTransformer.saveJsonFrom(id, identifier, defaultPort, environment, lbmethod,
                                                              healthcheckType, healthcheckExpect, healthcheckRequest, maxconn,
                                                              realIps, equipNames, idEquips, priorities,
                                                              weights, realPorts, idPoolMembers, serviceDownAction, healthcheckDestination);

        LOGGER.debug("POST POOL: " + serverPool.toString());
        GenericJson json = getTransport().post("/api/pools/save/", serverPool, GenericJson.class);

        return new Pool(Long.valueOf(json.get("pool").toString()));
    }

    @Trace(dispatcher = true)
    public Pool.PoolResponse getByPk(Long id) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/getByPk");

        try {
            String uri = "/api/pools/getbypk/" + id.toString() + "/";
            String result = getTransport().get(uri);

            Pool.PoolResponse poolResponse = HttpJSONRequestProcessor.parse(result, Pool.PoolResponse.class);

            return poolResponse;
        }catch (IOException e) {
            throw new GloboNetworkException("GloboNetworkAPI error parse: " + e.getMessage(), e);
        }
    }


    @Trace(dispatcher = true)
    public GenericJson remove(List<Long> ids) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/listByEnvVip");

        String uri = "/api/pools/remove/";
        GenericJson body = new GenericJson();
        body.set("ids", ids);

        GenericJson output = getTransport().post(uri, body, GenericJson.class);

        return output;
    }

    @Trace(dispatcher = true)
    public GenericJson remove(Long id) throws  GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/removeById");
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        return this.remove(ids);
    }

    @Trace(dispatcher = true)
    public GenericJson delete(List<Long> ids) throws  GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/deleteByIds");

        String uri = "/api/pools/delete/";
        GenericJson body = new GenericJson();
        body.set("ids", ids);

        GenericJson output = getTransport().post(uri, body, GenericJson.class);

        return output;
    }

    @Trace(dispatcher = true)
    public GenericJson delete(Long id) throws  GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/deleteById");
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        return this.delete(ids);
    }

    @Trace(dispatcher = true)
    public List<PoolOption> listPoolOptions(Long networkEnvironmentId, String type) throws GloboNetworkException, IOException{
        NewRelic.setTransactionName(null, "/globonetwork/pools/listPoolOptions");

        String uri = "/api/pools/environment_options/?option_type=" + type + "&environment_id=" + networkEnvironmentId;
        String output = getTransport().get(uri);
        List<GenericJson> result = Arrays.asList(HttpJSONRequestProcessor.parse(output, GenericJson[].class));
        List<PoolOption> poolOptions = new ArrayList<PoolOption>();
        for(GenericJson genericJson : result){
            ArrayMap option = (ArrayMap) genericJson.get("option");
            poolOptions.add(new PoolOption(((BigDecimal)option.get("id")).longValue(), option.get("name").toString()));
        }
        return poolOptions;
    }

    @Trace(dispatcher = true)
    public List<Pool> listAllByReqVip(Long idVip) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/poolListByReqVip");

        try {
            String uri = "/api/pools/pool_list_by_reqvip/";

            GenericJson data = new GenericJson();
            data.set("id_vip", idVip);

            String result = getTransport().post(uri, data);
            Pool.PoolList poolList = HttpJSONRequestProcessor.parse(result, Pool.PoolList.class);

            return poolList.getPools();
        } catch (Exception e) {
            throw new GloboNetworkException("Error trying to list pools in networkApi. cause: " + e.getMessage(), e);
        }
    }
}

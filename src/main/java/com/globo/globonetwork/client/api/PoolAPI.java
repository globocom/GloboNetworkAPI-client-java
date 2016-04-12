package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.api.pool.PoolTransformer;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.Pool;
import com.globo.globonetwork.client.model.PoolOption;
import com.globo.globonetwork.client.model.Real;
import com.globo.globonetwork.client.model.pool.PoolV3;
import com.globo.globonetwork.client.model.pool.PoolV3Request;
import com.globo.globonetwork.client.model.pool.PoolV3Response;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.ArrayValueMap;
import com.google.api.client.util.Joiner;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoolAPI extends BaseJsonAPI<Pool>{
    private static final Logger LOGGER = LoggerFactory.getLogger(PoolAPI.class);
    private static final char IDS_SEPARATOR = ';';
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
        return this.delete(Arrays.asList(id));
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

    @Trace(dispatcher = true)
    public List<PoolV3> getByIdsV3(List<Long> ids) throws GloboNetworkException {
        try{
            NewRelic.setTransactionName(null, "/globonetwork/pools/getByIdV3");

            String idsJoin = Joiner.on(IDS_SEPARATOR).join(ids);
            String uri = "/api/v3/pool/" + idsJoin + "/";
            String result = getTransport().get(uri);

            PoolV3Response poolResponse = HttpJSONRequestProcessor.parse(result, PoolV3Response.class);

            return poolResponse.getPools();
        }catch (IOException e) {
            throw new GloboNetworkException("GloboNetworkAPI error parse: " + e.getMessage(), e);
        }
    }

    /**
     * getByV3 - PoolV3
     */
    @Trace(dispatcher = true)
    public PoolV3 getById(Long id) throws GloboNetworkException {
        if ( id == null ){
            throw new IllegalArgumentException("Pool id can not be null: "+ id);
        }

        Long[] ids = {id};
        List<PoolV3> byIdsV3 = getByIdsV3(Arrays.asList(ids));

        if ( byIdsV3.size() > 0) {
            return byIdsV3.get(0);
        }
        return null;
    }


    /**
     * if id is null, will create with post (with {@link #createV3(PoolV3)}, else will update with put ({@link #updateAll(List)}
     */
    public PoolV3 saveV3(PoolV3 pool) throws GloboNetworkException {
        PoolV3 poolSaved;

        if (pool.getId() == null) {
            poolSaved = createV3(pool);
        } else {
            List<PoolV3> poolV3s = updateAll(Arrays.asList(pool));
            poolSaved = poolV3s.get(0);
        }

        return poolSaved;
    }

    @Trace(dispatcher = true)
    public PoolV3 createV3(PoolV3 pool) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/saveV3");

        PoolV3Request poolRequest;
        PoolV3 poolSaved;

        String uri = "/api/v3/pool/";
        poolRequest = PoolV3Request.newPost(pool);

        String result = getTransport().post(uri, poolRequest);

        try {
            ArrayList<ArrayMap> parse = HttpJSONRequestProcessor.parse(result, ArrayList.class);

            pool.setId(((BigDecimal) parse.get(0).get("id")).longValue());
            poolSaved = pool;

            return poolSaved;
        } catch (IOException e) {
            throw new GloboNetworkException("Error trying parse result: " + result, e);
        }
    }

    /**
     *
     */
    @Trace(dispatcher = true)
    public PoolV3 deployV3(PoolV3 pool) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/deployV3");

        String uri = "/api/v3/pool/deploy/" + pool.getId() + "/";
        String result = getTransport().post(uri, new GenericJson());

        LOGGER.debug("Pool deployed result " + result);

        return pool;
    }

    @Trace(dispatcher = true)
    public void undeployV3(Long id) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/undeployV3");

        if (id == null){
            throw new IllegalArgumentException("Pool id can not be null");
        }

        String uri = "/api/v3/pool/deploy/" +  id.toString() + "/";

        GenericJson result = getTransport().delete(uri, GenericJson.class);
        LOGGER.debug("Pool undeploy result: " + result);
    }

    @Trace(dispatcher = true)
    public void deleteV3(Long id) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/undeployV3");

        if (id == null){
            throw new IllegalArgumentException("Pool id can not be null");
        }

        String uri = "/api/v3/pool/" +  id.toString() + "/";

        GenericJson result = getTransport().delete(uri, GenericJson.class);
        LOGGER.debug("Pool delete result: " + result);
    }

    @Trace(dispatcher = true)
    public List<PoolV3> updateAll(List<PoolV3> poolsV3) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/updateV3");

        String uri = "/api/v3/pool/" + separatorPoolIds(poolsV3) + "/";

        PoolV3Request poolRequest = new PoolV3Request();
        poolRequest.setPools(poolsV3);
        GenericJson updated = getTransport().put(uri, poolRequest, GenericJson.class);

        LOGGER.debug("Pools updated: " + updated.toString());

        return poolsV3;
    }

    @Trace(dispatcher = true)
    public List<PoolV3> updateDeployAll(List<PoolV3> poolsV3) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/pools/updateDeployV3");

        for ( PoolV3 pool : poolsV3) {
            if ( pool.getId() == null) {
                throw new GloboNetworkException("The id can not be empty to update Pool. Pool Identifier: " + pool.getIdentifier());
            }
        }

        String uri = "/api/v3/pool/deploy/" + separatorPoolIds(poolsV3) + "/";

        PoolV3Request poolRequest = new PoolV3Request();
        poolRequest.setPools(poolsV3);
        getTransport().put(uri, poolRequest);

        return poolsV3;
    }

    /**

     */
    public PoolV3 save(PoolV3 poolv3) throws GloboNetworkException {
        if (poolv3.getId() == null){
            //create just in bd, it does not need to create in equipment in first creation
            poolv3 = createV3(poolv3);
            return poolv3;
        }else if (poolv3.isPoolCreated() ) {
            //update bd and equipment
            updateDeployAll(Arrays.asList(poolv3));
            return poolv3;
        } else {
            updateAll(Arrays.asList(poolv3)); //update just bd
            return poolv3;
        }
    }

    protected String separatorPoolIds(List<PoolV3> poolsV3){
        List<String> ids = new ArrayList<String>(poolsV3.size());
        for (int i = 0; i < poolsV3.size(); i++) {
            Long id = poolsV3.get(i).getId();

            if ( id == null){
                throw new IllegalArgumentException("Pool ids can not be null. index: " + i + " pools: " + poolsV3);
            }

            ids.add(id.toString());
        }
        String idsJoin = Joiner.on(IDS_SEPARATOR).join(ids);

        return idsJoin;
    }
}

package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.api.pool.PoolTransformer;
import com.globo.globonetwork.client.model.Real;
import com.google.api.client.json.GenericJson;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 * Created by lucas.castro on 8/13/15.
 */
public class PoolTransformerTest extends TestCase {


    public void testSaveJsonFrom() {

        List<Real.RealIP> realsIp = new ArrayList<Real.RealIP>();
        List<String> equipNames = new ArrayList<String>();
        List<Long> equipIds = new ArrayList<Long>();
        List<Integer> realsPriorities = new ArrayList<Integer>();
        List<Long> realsWeights = new ArrayList<Long>();
        List<Integer> realPorts = new ArrayList<Integer>();
        List<Long> idPoolMembers = new ArrayList<Long>();

        realsIp.add(new Real.RealIP(10l, 8080, "10.0.0.1", 80));
        equipNames.add("eq-10");
        equipIds.add(101l);
        realsPriorities.add(5);
        realsWeights.add(7l);
        realPorts.add(8080);
        idPoolMembers.add(null);

        GenericJson pool = PoolTransformer.saveJsonFrom(12l, "POOL_1" , 80, 121l, "least-conn",
                                                        "HTTP", "WORKING", "/healthcheck", 5,
                                                        realsIp,
                                                        equipNames,
                                                        equipIds,
                                                        realsPriorities,
                                                        realsWeights,
                                                        realPorts,
                                                        idPoolMembers,
                                                        null ,
                                                        null
                );

        assertEquals(12l, pool.get("id"));
        assertEquals("POOL_1", pool.get("identifier"));
        assertEquals(80, pool.get("default_port"));
        assertEquals(121l, pool.get("environment"));
        assertEquals("least-conn", pool.get("balancing"));

        assertEquals("HTTP", pool.get("healthcheck_type"));
        assertEquals("WORKING", pool.get("healthcheck_expect"));
        assertEquals("/healthcheck", pool.get("healthcheck_request"));

        assertEquals(5, pool.get("maxcom"));

        //reals
        assertNotNull(pool.get("ip_list_full"));
        List<GenericJson> genericJsons = (List<GenericJson>)pool.get("ip_list_full");
        assertEquals(1, genericJsons.size());
        GenericJson realIp = genericJsons.get(0);
        assertEquals(10l, realIp.get("id"));
        assertEquals("10.0.0.1", realIp.get("ip"));

        //equipNames
        assertNotNull(pool.get("nome_equips"));
        List<String> eqNames = (List<String>)pool.get("nome_equips");
        assertEquals(1, eqNames.size());
        assertEquals("eq-10", (String)eqNames.get(0));

        //equipId
        assertNotNull(pool.get("id_equips"));
        List<Long> eqId = (List<Long>)pool.get("id_equips");
        assertEquals(1, eqId.size());
        assertEquals((Long)101l, (Long)eqId.get(0));

        //priorities
        assertNotNull(pool.get("priorities"));
        List<Integer> priorities = (List<Integer>)pool.get("priorities");
        assertEquals(1, priorities.size());
        assertEquals((Integer) 5, (Integer) priorities.get(0));

        //weights
        assertNotNull(pool.get("weight"));
        List<Long> weights = (List<Long>)pool.get("weight");
        assertEquals(1, weights.size());
        assertEquals((Long) 7l,  weights.get(0));

        //equipId
        assertNotNull(pool.get("ports_reals"));
        List<Integer> realPorts1 = (List<Integer>)pool.get("ports_reals");
        assertEquals(1, realPorts1.size());
        assertEquals((Integer) 8080, realPorts1.get(0));
    }
}

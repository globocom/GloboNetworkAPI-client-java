/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.IPv6Network;
import com.globo.globonetwork.client.model.IPv4Network;
import com.globo.globonetwork.client.model.Network;
import com.google.api.client.json.GenericJson;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkJsonAPI extends BaseJsonAPI<Network> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkJsonAPI.class);

    public NetworkJsonAPI(HttpJSONRequestProcessor processor) {
        super(processor);
    }


    @Trace(dispatcher = true)
    public List<Network> listVipNetworks(Long vipEnvironmentId, Boolean isIpv6) throws GloboNetworkException, IOException {
        NewRelic.setTransactionName(null, "/globonetwork/listVipNetworks");

        List<Network> networks = new ArrayList<Network>();
        if(!isIpv6) {
            String uri = "/api/networkv4/?environment_vip=" + vipEnvironmentId;
            String output = getTransport().get(uri);
            List<GenericJson> result = Arrays.asList(HttpJSONRequestProcessor.parse(output, GenericJson[].class));
            for(GenericJson genericJson : result){
                IPv4Network network = new IPv4Network();
                network.setId(Long.parseLong(genericJson.get("id").toString()));
                network.setOct1(Integer.parseInt(genericJson.get("oct1").toString()));
                network.setOct2(Integer.parseInt(genericJson.get("oct2").toString()));
                network.setOct3(Integer.parseInt(genericJson.get("oct3").toString()));
                network.setOct4(Integer.parseInt(genericJson.get("oct4").toString()));
                network.setBlock(Integer.parseInt(genericJson.get("block").toString()));
                network.setMaskOct1(Integer.parseInt(genericJson.get("mask_oct1").toString()));
                network.setMaskOct2(Integer.parseInt(genericJson.get("mask_oct2").toString()));
                network.setMaskOct3(Integer.parseInt(genericJson.get("mask_oct3").toString()));
                network.setMaskOct4(Integer.parseInt(genericJson.get("mask_oct4").toString()));
                network.setBroadcast(genericJson.get("broadcast").toString());
                network.setVlanId(Long.parseLong(genericJson.get("vlan").toString()));
                network.setVipEnvironmentId(Long.parseLong(genericJson.get("ambient_vip").toString()));
                network.setNetworkTypeId(Long.parseLong(genericJson.get("network_type").toString()));
                network.setActive(Boolean.parseBoolean(genericJson.get("active").toString()));
                networks.add(network);
            }
        }else{
            throw new NotImplementedException();
        }

        return networks;
    }

    @Trace(dispatcher = true)
    public void createNetworks(Long networkId, boolean isv6) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/createNetwork");
        String uriPrefix = isv6 ? "/api/networkv6/" : "/api/networkv4/";
        getTransport().post(uriPrefix + networkId + "/equipments/", null);
    }

    @Trace
    public void removeNetwork(Long networkId, boolean isv6) throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/removeNetwork");
        String uriPrefix = isv6 ? "/api/networkv6/" : "/api/networkv4/";
        getTransport().delete(uriPrefix + networkId + "/equipments/", GenericJson.class);
    }
}

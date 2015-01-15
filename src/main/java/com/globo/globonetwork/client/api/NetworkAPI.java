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

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.IPv4Network;
import com.globo.globonetwork.client.model.IPv6Network;
import com.globo.globonetwork.client.model.Network;
import com.globo.globonetwork.client.model.Vlan;
import com.google.api.client.xml.GenericXml;

public class NetworkAPI extends BaseAPI<Network> {

    public NetworkAPI(RequestProcessor transport) {
        super(transport);
    }

    public Network addNetwork(Long vlanId, Long networkTypeId, Long vipEnvironmentId, boolean isIpv6) throws GloboNetworkException {
        Network network = Network.initNetwork(isIpv6);
        network.set("id_vlan", vlanId);
        network.set("id_tipo_rede", networkTypeId);
        network.set("id_ambiente_vip", vipEnvironmentId);

        GloboNetworkRoot<Network> globoNetworkRoot = new GloboNetworkRoot<Network>();
        globoNetworkRoot.getObjectList().add(network);

        // Attention here! Not <network>, but <vlan>
        globoNetworkRoot.set("vlan", network);

        this.getTransport().post("/network/" + (isIpv6 ? "ipv6" : "ipv4") + "/add/", globoNetworkRoot, GenericXml.class);

        // FIXME There is no networkId in response of post method, and answer is not standardized.
        // So we fetch vlan data again and the last network is network created
        Vlan vlan = this.getTransport().getVlanAPI().getById(vlanId);
        // Return either IPv6 networks or IPv4 networks
        return (isIpv6 ? vlan.getIpv6Networks().get(vlan.getIpv6Networks().size() - 1) : vlan.getIpv4Networks().get(vlan.getIpv4Networks().size() - 1));
    }

    public void createNetworks(Long networkId, Long vlanId, boolean isv6) throws GloboNetworkException {

        Network network = Network.initNetwork(isv6);
        network.set("ids", networkId + (isv6 ? "-v6" : "-v4"));
        network.set("id_vlan", vlanId);

        GloboNetworkRoot<Network> globoNetworkRoot = new GloboNetworkRoot<Network>();
        globoNetworkRoot.getObjectList().add(network);
        globoNetworkRoot.set("network", network);

        this.getTransport().put("/network/create/", globoNetworkRoot, (isv6 ? IPv6Network.class : IPv4Network.class));
    }
    
    public Network getNetwork(Long networkId, boolean isv6) throws GloboNetworkException {
        GloboNetworkRoot<GenericXml> globoNetworkRoot = (GloboNetworkRoot<GenericXml>)this.getTransport().get("/network/" + (isv6 ? "ipv6" : "ipv4") + "/id/" + networkId + "/", GenericXml.class);

        if (globoNetworkRoot.getFirstObject() == null) {
            throw new GloboNetworkException("Network not found");
        } else if (globoNetworkRoot.size() > 1) {
            throw new GloboNetworkException("Multiple networks returned. This is an error in search by id");
        }

        GenericXml genericObj = globoNetworkRoot.getFirstObject();
        Network network = (isv6 ? new IPv6Network() : new IPv4Network());        
        assignTo(genericObj, network);
        return network;
    }
}

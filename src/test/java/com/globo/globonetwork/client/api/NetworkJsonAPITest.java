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
import com.globo.globonetwork.client.model.Network;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class NetworkJsonAPITest {

    private NetworkJsonAPI networkAPI;

    @Mock
    private HttpJSONRequestProcessor processor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        networkAPI = new NetworkJsonAPI(processor);
    }

    @Test
    public void testGetNetworks() throws GloboNetworkException, IOException {
        when(networkAPI.getTransport().get(anyString())).thenReturn(getNetworkJSON());

        List<Network> networks = networkAPI.listVipNetworks(1L, false);

        assertEquals(2, networks.size());
        assertNotNull(networks.get(0).getVlanId());
    }

    @Test
    public void testGetNetworksGivenEmptyNetworkList() throws GloboNetworkException, IOException {
        when(networkAPI.getTransport().get(anyString())).thenReturn("[]");

        List<Network> networks = networkAPI.listVipNetworks(1L, false);

       assertTrue(networks.isEmpty());
    }

    @Test(expected = NotImplementedException.class)
    public void testGetNetworksGivenEmptyIPv6Networks() throws GloboNetworkException, IOException {
        networkAPI.listVipNetworks(1L, true);
    }

    public String getNetworkJSON(){
        return "[\n" +
                "  {\n" +
                "    \"id\": 1645,\n" +
                "    \"oct1\": 10,\n" +
                "    \"oct2\": 170,\n" +
                "    \"oct3\": 1,\n" +
                "    \"oct4\": 0,\n" +
                "    \"block\": 24,\n" +
                "    \"mask_oct1\": 255,\n" +
                "    \"mask_oct2\": 255,\n" +
                "    \"mask_oct3\": 255,\n" +
                "    \"mask_oct4\": 0,\n" +
                "    \"broadcast\": \"10.170.1.255\",\n" +
                "    \"vlan\": 2399,\n" +
                "    \"network_type\": 8,\n" +
                "    \"ambient_vip\": 23,\n" +
                "    \"active\": false\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 11002,\n" +
                "    \"oct1\": 10,\n" +
                "    \"oct2\": 170,\n" +
                "    \"oct3\": 100,\n" +
                "    \"oct4\": 0,\n" +
                "    \"block\": 24,\n" +
                "    \"mask_oct1\": 255,\n" +
                "    \"mask_oct2\": 255,\n" +
                "    \"mask_oct3\": 255,\n" +
                "    \"mask_oct4\": 0,\n" +
                "    \"broadcast\": \"10.170.100.255\",\n" +
                "    \"vlan\": 2399,\n" +
                "    \"network_type\": 8,\n" +
                "    \"ambient_vip\": 23,\n" +
                "    \"active\": false\n" +
                "  }\n" +
                "]";
    }
}

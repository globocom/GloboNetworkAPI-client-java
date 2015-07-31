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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.IPv4Network;
import com.globo.globonetwork.client.model.Vlan;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class VlanAPI extends BaseXmlAPI<Vlan> {
	
	public VlanAPI(RequestProcessor transport) {
		super(transport);
	}

	@Trace
	public List<Vlan> listByEnvironment(Long environmentId) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/listVlanByEnvironment");

		GloboNetworkRoot<Vlan> globoNetworkRoot = this.get("/vlan/ambiente/" + environmentId + "/");
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return new ArrayList<Vlan>();
		}
		return globoNetworkRoot.getObjectList();
	}

	@Trace
	public Vlan allocateWithoutNetwork(Long environmentId, String name, String description) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/allocateVlanWithoutNetwork");

		Vlan vlan = new Vlan();
		// vlan.setEnvironment(environmentId);
		// vlan.setName(name);
		// vlan.setDescription(description);
		vlan.set("name", name);
		vlan.set("environment_id", environmentId);
		vlan.set("description", description);
		
		GloboNetworkRoot<Vlan> globoNetworkRoot = new GloboNetworkRoot<Vlan>();
		globoNetworkRoot.getObjectList().add(vlan);
		globoNetworkRoot.set(vlan.superName(), vlan);
		
		GloboNetworkRoot<Vlan> globoNetworkRootAnswer = this.post("/vlan/no-network/", globoNetworkRoot);
		if (globoNetworkRootAnswer == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRootAnswer.getObjectList() == null) {
			return null;
		} else {
			// Check if there is only one element?
			return globoNetworkRootAnswer.getObjectList().get(0);
		}
	}

	@Trace
	public Vlan getById(Long vlanId) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/getVlanById");

		GloboNetworkRoot<Vlan> globoNetworkRoot = this.get("/vlan/" + vlanId + "/network/");
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null || globoNetworkRoot.getObjectList().isEmpty()) {
			return null;
		} else if (globoNetworkRoot.getObjectList().size() > 1) {
			// Something is wrong, id should be unique
			throw new RuntimeException();
		} else {
			// Check if there is only one element?
			Vlan vlan = globoNetworkRoot.getObjectList().get(0);
			// Cleaning up empty networks
			for (Iterator<IPv4Network> iter = vlan.getIpv4Networks().iterator(); iter.hasNext(); ) {
				IPv4Network ipv4Network = iter.next();
				if (ipv4Network.getId() == null) {
					iter.remove();
				}
			}
			return vlan;
		}
	}

	@Trace
	public void remove(Long vlanId) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/removeVlan");

		this.delete("/vlan/" + vlanId + "/remove/");
	}

	@Trace
	public void deallocate(Long vlanId) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/deallocateVlan");

		this.delete("/vlan/" + vlanId + "/deallocate/");
	}
}

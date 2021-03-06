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
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Equipment;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class EquipmentAPI extends BaseXmlAPI<Equipment> {
	
	public EquipmentAPI(RequestProcessor transport) {
		super(transport);
	}

	@Trace(dispatcher = true)
	public Equipment listByName(String equipmentName) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/listEquipmentByName");

		try {
			GloboNetworkRoot<Equipment> globoNetworkRoot = this.get("/equipamento/nome/" + equipmentName + "/");
			if (globoNetworkRoot == null) {
				// Problems reading the XML
				throw new GloboNetworkException("Invalid XML response");
			} else if (globoNetworkRoot.getObjectList() == null || globoNetworkRoot.getObjectList().isEmpty()) {
				// not found
				return null;
			}
			return globoNetworkRoot.getFirstObject();
		} catch (GloboNetworkErrorCodeException e) {
			if (e.getCode() == GloboNetworkErrorCodeException.EQUIPMENT_NOT_REGISTERED) {
				return null;
			}
			throw e;
		}
	}

	@Trace(dispatcher = true)
	public Equipment insert(String name, Long equipmentTypeId, Long modelId, Long groupId) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/insertEquipment");

		Equipment equipment = new Equipment();
		equipment.setName(name);
		equipment.setEquipmentTypeId(equipmentTypeId);
		equipment.setModelId(modelId);
		equipment.setGroupId(groupId);
		GloboNetworkRoot<Equipment> globoNetworkRoot = this.post("/equipamento/", new GloboNetworkRoot<Equipment>("equipamento", equipment));
		if (globoNetworkRoot == null || globoNetworkRoot.getObjectList() == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		}
		return globoNetworkRoot.getFirstObject();
	}

	@Trace(dispatcher = true)
	public void delete(Long equipmentId) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/deleteEquipment");

		this.delete("/equipamento/" + equipmentId + "/");
	}

	@Trace(dispatcher = true)
	public void removeIP(Long equipId, Long idIp, boolean isIpv6) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/removeIpFromEquipment");

	    String uri = isIpv6 ? "/ipv6/" + idIp + "/equipment/" + equipId + "/remove/" : "/ip/" + idIp + "/equipamento/" + equipId + "/" ;
		this.delete(uri);
	}
}

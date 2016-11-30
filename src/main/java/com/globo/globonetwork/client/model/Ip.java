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
package com.globo.globonetwork.client.model;

import java.util.List;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public abstract class Ip extends GenericXml {

	@Key("equipamentos")
	private List<String> equipments;
	@Key
	private Long id;

	public Ip() {
		this.name = "ip";
	}

	public List<String> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<String> equipments) {
		this.equipments = equipments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public abstract String getIpString();
	
	public abstract Long getNetworkId();


	public abstract void setDescription(String description);
	public abstract String getDescription();
	
	public abstract void setNetworkId(Long networkId);
	
    public static Ip createIp(Boolean isIpv6){
    	return isIpv6 ? new Ipv6() : new Ipv4();
    }



}
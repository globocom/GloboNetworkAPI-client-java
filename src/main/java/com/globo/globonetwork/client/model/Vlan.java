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

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class Vlan extends GenericXml {
	
	@Key
	private Long id;	

	@Key("num_vlan")
	private Long vlanNum;
	
	@Key("ambiente")
	private Long environment;
	
	@Key("nome")
	private String name;
	
	@Key("descricao")
	private String description;
	
	@Key("redeipv4")
	private final List<IPv4Network> ipv4Networks = new ArrayList<IPv4Network>();

	public Long getVlanNum() {
		return vlanNum;
	}

	public void setVlanNum(Long vlanNum) {
		this.vlanNum = vlanNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEnvironment() {
		return environment;
	}

	public void setEnvironment(Long environment) {
		this.environment = environment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<IPv4Network> getIpv4Networks() {
		return ipv4Networks;
	}

	public void setIpv4Networks(List<IPv4Network> ipv4Networks) {
		this.ipv4Networks.clear();
		if (ipv4Networks != null) {
			this.ipv4Networks.addAll(ipv4Networks);
		}
	}

	public Vlan() {
		super.name = "vlan";
	}
	
	public String superName() {
		return super.name;
	}
	
}

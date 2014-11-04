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

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class Network extends GenericXml {
	
	@Key
	private Long id;
	
	@Key("id_vlan")
	private Long vlanId;
	
	@Key("id_tipo_rede")
	private Long networkTypeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVlanId() {
		return vlanId;
	}

	public void setVlanId(Long vlanId) {
		this.vlanId = vlanId;
	}

	public Long getNetworkTypeId() {
		return networkTypeId;
	}

	public void setNetworkTypeId(Long networkTypeId) {
		this.networkTypeId = networkTypeId;
	}
	
	public Network() {
		super.name = "network";
	}
}
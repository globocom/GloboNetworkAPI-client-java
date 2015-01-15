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

public abstract class Network extends GenericXml {
	
	@Key
	private Long id;
	
	@Key("vlan")
	private Long vlanId;
	
	@Key("network_type")
	private Long networkTypeId;

    @Key("ambient_vip")
    private Long vipEnvironmentId;

    @Key
    private Boolean active;

    @Key
    private Integer block;

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
	
    public Long getVipEnvironmentId() {
        return vipEnvironmentId;
    }

    public void setVipEnvironmentId(Long vipEnvironmentId) {
        this.vipEnvironmentId = vipEnvironmentId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public abstract String getNetworkAddressAsString();

    public abstract String getMaskAsString();
    
    public abstract boolean isv6();
    
    public static Network initNetwork(boolean isv6) {
        if (isv6) {
            return new IPv6Network();
        } else {
            return new IPv4Network();
        }
    }
}

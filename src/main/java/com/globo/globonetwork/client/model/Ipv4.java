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

public class Ipv4 extends Ip{
	
	@Key
	private Integer oct1;
	@Key
	private Integer oct2;
	@Key
	private Integer oct3;
	@Key
	private Integer oct4;
	@Key("networkipv4")
	private Long networkId;

	@Key("descricao")
	private String description;

	public Ipv4() {
		this.name = "ip";
	}
	
	public Integer getOct1() {
		return oct1;
	}
	
	public void setOct1(Integer oct1) {
		this.oct1 = oct1;
	}
	
	public Integer getOct2() {
		return oct2;
	}
	
	public void setOct2(Integer oct2) {
		this.oct2 = oct2;
	}
	
	public Integer getOct3() {
		return oct3;
	}
	
	public void setOct3(Integer oct3) {
		this.oct3 = oct3;
	}
	
	public Integer getOct4() {
		return oct4;
	}
	
	public void setOct4(Integer oct4) {
		this.oct4 = oct4;
	}
	
	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}


	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	public String getIpString() {
	    return String.format("%d.%d.%d.%d", this.getOct1(), this.getOct2(), this.getOct3(), this.getOct4());
	}
}
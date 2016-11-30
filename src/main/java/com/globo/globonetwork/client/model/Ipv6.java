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

public class Ipv6 extends Ip {
	
    @Key
    private String block1;
    @Key
    private String block2;
    @Key
    private String block3;
    @Key
    private String block4;
    @Key
    private String block5;
    @Key
    private String block6;
    @Key
    private String block7;
    @Key
    private String block8;
	@Key
	private Long id;
	@Key("networkipv6")
	private Long networkId;
	@Key("descricao")
	private String description;
	
	public Ipv6() {
		this.name = "ipv6";
	}
	
	public String getIpString() {
	    return String.format("%s:%s:%s:%s:%s:%s:%s:%s", getBlock1(), getBlock2(), getBlock3(), getBlock4(), getBlock5(), getBlock6(), getBlock7(), getBlock8());
	}

	public String getBlock1() {
		return block1;
	}

	public void setBlock1(String block1) {
		this.block1 = block1;
	}

	public String getBlock2() {
		return block2;
	}

	public void setBlock2(String block2) {
		this.block2 = block2;
	}

	public String getBlock3() {
		return block3;
	}

	public void setBlock3(String block3) {
		this.block3 = block3;
	}

	public String getBlock4() {
		return block4;
	}

	public void setBlock4(String block4) {
		this.block4 = block4;
	}

	public String getBlock5() {
		return block5;
	}

	public void setBlock5(String block5) {
		this.block5 = block5;
	}

	public String getBlock6() {
		return block6;
	}

	public void setBlock6(String block6) {
		this.block6 = block6;
	}

	public String getBlock7() {
		return block7;
	}

	public void setBlock7(String block7) {
		this.block7 = block7;
	}

	public String getBlock8() {
		return block8;
	}

	public void setBlock8(String block8) {
		this.block8 = block8;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}

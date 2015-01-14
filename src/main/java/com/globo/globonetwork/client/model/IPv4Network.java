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

public class IPv4Network extends Network {
	
	@Key
	private Integer oct1;
	
	@Key
	private Integer oct2;
	
	@Key
	private Integer oct3;
	
	@Key
	private Integer oct4;
	
	@Key("mask_oct1")
	private Integer maskOct1;
	
	@Key("mask_oct2")
	private Integer maskOct2;
	
	@Key("mask_oct3")
	private Integer maskOct3;
	
	@Key("mask_oct4")
	private Integer maskOct4;
	
	@Key
	private String broadcast;

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
	
	@Override
	public String getNetworkAddressAsString() {
	    return String.format("%d.%d.%d.%d", getOct1(), getOct2(), getOct3(), getOct4());
	}

	public Integer getMaskOct1() {
		return maskOct1;
	}

	public void setMaskOct1(Integer maskOct1) {
		this.maskOct1 = maskOct1;
	}

	public Integer getMaskOct2() {
		return maskOct2;
	}

	public void setMaskOct2(Integer maskOct2) {
		this.maskOct2 = maskOct2;
	}

	public Integer getMaskOct3() {
		return maskOct3;
	}

	public void setMaskOct3(Integer maskOct3) {
		this.maskOct3 = maskOct3;
	}

	public Integer getMaskOct4() {
		return maskOct4;
	}
	
	@Override
	public String getMaskAsString() {
	    return String.format("%d.%d.%d.%d", getMaskOct1(), getMaskOct2(), getMaskOct3(), getMaskOct4());
	}

	public void setMaskOct4(Integer maskOct4) {
		this.maskOct4 = maskOct4;
	}

	public String getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}
	
	public IPv4Network() {
		super.name = "redeipv4";
	}

}

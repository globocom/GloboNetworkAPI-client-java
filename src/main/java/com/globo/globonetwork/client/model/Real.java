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

public class Real extends GenericXml {

	@Key("real")
	private List<RealIP> realIps;
	
	public List<RealIP> getRealIps() {
		return realIps;
	}

	public void setRealIps(List<RealIP> realIps) {
		this.realIps = realIps;
	}
	
	public Real() {
		super.name = "reals";
	}
	
	public String superName() {
		return super.name;
	}
	
	public static class RealIP extends GenericXml {
		
		@Key("id_ip")
		private Long ipId;
		
		@Key("port_real")
		private Integer realPort;
		
		@Key("real_ip")
		private String realIp;
		
		@Key("port_vip")
		private Integer vipPort;
		
		@Key("real_name")
		private String name;

		public Long getIpId() {
			return ipId;
		}

		public void setIpId(Long ipId) {
			this.ipId = ipId;
		}

		public Integer getRealPort() {
			return realPort;
		}

		public void setRealPort(Integer realPort) {
			this.realPort = realPort;
		}

		public String getRealIp() {
			return realIp;
		}

		public void setRealIp(String realIp) {
			this.realIp = realIp;
		}

		public Integer getVipPort() {
			return vipPort;
		}

		public void setVipPort(Integer vipPort) {
			this.vipPort = vipPort;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public RealIP() {
			super.name = "real";
		}
		
		public String superName() {
			return super.name;
		}
	}
}
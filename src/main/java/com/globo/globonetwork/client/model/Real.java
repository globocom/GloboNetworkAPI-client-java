package com.globo.globonetwork.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class Real extends GenericXml {

	@Key("real")
	private List<RealIP> realIps = new ArrayList<RealIP>();
	
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
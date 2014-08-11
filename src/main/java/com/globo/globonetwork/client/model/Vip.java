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

import com.globo.globonetwork.client.model.Real.RealIP;
import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class Vip extends GenericXml {	
	
	@Key
	private Long id;	

	@Key
	private List<String> ips;
	
	@Key("ipv4_description")
	private String ipv4Description;
	
	@Key("vip_criado")
	private Boolean created;
	
	@Key("validado")
	private Boolean validated;
	
	@Key
	private String host;
	
	@Key
	private String cache;
	
	@Key("metodo_bal")
	private String method;
	
	@Key("persistencia")
	private String persistence;

	@Key("healthcheck_type")
	private String healthcheckType;

	@Key
	private String healthcheck;
	
	@Key("maxcon")
	private Integer maxConn;
	
	@Key("portas_servicos")
	private ServicePorts servicePorts;
	
	@Key
	private Real reals;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<String> getIps() {
		return ips;
	}
	
	public void setIps(List<String> ips) {
		this.ips = ips;
	}
	
	public String getIpv4Description() {
		return ipv4Description;
	}
	
	public void setIpv4Description(String ipv4Description) {
		this.ipv4Description = ipv4Description;
	}
	
	public Boolean getCreated() {
		return created;
	}
	
	public void setCreated(Boolean created) {
		this.created = created;
	}
	
	public Boolean getValidated() {
		return validated;
	}
	
	public void setValidated(Boolean validated) {
		this.validated = validated;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	protected Real getReals() {
		return reals;
	}
	
	protected void setReals(Real reals) {
		this.reals = reals;
	}
	
	public List<RealIP> getRealsIp() {
		// ensure never returns null.
		if (this.getReals() == null) {
			this.setReals(new Real());
		}
		if (this.getReals().getRealIps() == null) {
			this.getReals().setRealIps(new ArrayList<RealIP>());
		}
		return this.getReals().getRealIps();
	}
	
	public void setRealsIp(List<RealIP> realsIp) {
		this.getRealsIp().clear();
		this.getRealsIp().addAll(realsIp);
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPersistence() {
		return persistence;
	}

	public void setPersistence(String persistence) {
		this.persistence = persistence;
	}

	public String getHealthcheckType() {
		return healthcheckType;
	}

	public void setHealthcheckType(String healthcheckType) {
		this.healthcheckType = healthcheckType;
	}

	public String getHealthcheck() {
		return healthcheck;
	}

	public void setHealthcheck(String healthcheck) {
		this.healthcheck = healthcheck;
	}

	public Integer getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(Integer maxConn) {
		this.maxConn = maxConn;
	}

	protected ServicePorts getServicePorts() {
		return servicePorts;
	}

	protected void setServicePorts(ServicePorts servicePorts) {
		this.servicePorts = servicePorts;
	}
	
	public List<String> getPorts() {
		// ensure never returns null.
		if (this.getServicePorts() == null) {
			this.setServicePorts(new ServicePorts());
		}
		if (this.getServicePorts().getPorts() == null) {
			this.getServicePorts().setPorts(new ArrayList<String>());
		}
		return this.getServicePorts().getPorts();
	}
	
	public void setPorts(List<String> ports) {
		this.getPorts().clear();
		this.getPorts().addAll(ports);
	}
	
	public Vip() {
		super.name = "vip";
	}
	
	public String superName() {
		return super.name;
	}
	
	public static class ServicePorts extends GenericXml {
		@Key("porta")
		List<String> ports;
		
		public List<String> getPorts() {
			return ports;
		}
		
		public void setPorts(List<String> ports) {
			this.ports = ports;
		}
		
		public ServicePorts() {
			super.name = "portas_servicos";
		}
		
		public String superName() {
			return super.name;
		}
	}
}

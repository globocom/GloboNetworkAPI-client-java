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

package com.globo.globonetwork.client.model;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class EnvironmentVip extends GenericXml {
	
	@Key
	private Long id;	

	@Key("finalidade_txt")
	private String finality;
	
	@Key("cliente_txt")
	private Long client;
	
	@Key("ambiente_p44_txt")
	private String environmentName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFinality() {
		return finality;
	}

	public void setFinality(String finality) {
		this.finality = finality;
	}

	public Long getClient() {
		return client;
	}

	public void setClient(Long client) {
		this.client = client;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public EnvironmentVip() {
		super.name = "environment_vip";
	}
	
}

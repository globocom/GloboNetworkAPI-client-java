package com.globo.globonetwork.client.model;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class Environment extends GenericXml {
	
	@Key
	private Long id;	

	@Key("grupo_l3")
	private Long l3GroupId;
	
	@Key("grupo_l3_name")
	private String l3GroupName;
	
	@Key("ambiente_logico")
	private Long logicalEnvironmentId;
	
	@Key("ambiente_logico_name")
	private String logicalEnvironmentName;
	
	@Key("divisao_dc")
	private Long dcDivisionId;
	
	@Key("divisao_dc_name")
	private String dcDivisionName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getL3GroupId() {
		return l3GroupId;
	}

	public void setL3GroupId(Long l3GroupId) {
		this.l3GroupId = l3GroupId;
	}

	public String getL3GroupName() {
		return l3GroupName;
	}

	public void setL3GroupName(String l3GroupName) {
		this.l3GroupName = l3GroupName;
	}

	public Long getLogicalEnvironmentId() {
		return logicalEnvironmentId;
	}

	public void setLogicalEnvironmentId(Long logicalEnvironmentId) {
		this.logicalEnvironmentId = logicalEnvironmentId;
	}

	public String getLogicalEnvironmentName() {
		return logicalEnvironmentName;
	}

	public void setLogicalEnvironmentName(String logicalEnvironmentName) {
		this.logicalEnvironmentName = logicalEnvironmentName;
	}

	public Long getDcDivisionId() {
		return dcDivisionId;
	}

	public void setDcDivisionId(Long dcDivisionId) {
		this.dcDivisionId = dcDivisionId;
	}

	public String getDcDivisionName() {
		return dcDivisionName;
	}

	public void setDcDivisionName(String dcDivisionName) {
		this.dcDivisionName = dcDivisionName;
	}

	public Environment() {
		super.name = "ambiente";
	}
	
	public String superName() {
		return super.name;
	}
	
}

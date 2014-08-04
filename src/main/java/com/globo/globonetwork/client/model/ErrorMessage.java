package com.globo.globonetwork.client.model;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class ErrorMessage extends GenericXml {
	
	@Key("codigo")
	private Integer code;
	
	@Key("descricao")
	private String description;
	
	public ErrorMessage() {
		super.name = "erro";
	}
	
	public String superName() {
		return super.name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

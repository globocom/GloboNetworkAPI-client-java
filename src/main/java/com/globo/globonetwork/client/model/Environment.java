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

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
package com.globo.globonetwork.client.exception;

public class GloboNetworkErrorCodeException extends GloboNetworkException {

	private static final long serialVersionUID = -7272459136902597384L;

	private int code;
	
	private String description;

	public GloboNetworkErrorCodeException(int code, String description) {
		super(code + ":" + description);
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static final int EQUIPMENT_NOT_REGISTERED = 117;
	public static final int IP_NOT_REGISTERED = 119;
	public static final int VIP_NOT_REGISTERED = 152;
	public static final int NO_PARAMETERS = 287;
	public static final int IPV4_NOT_IN_ENVIRONMENT_VIP = 334;
}

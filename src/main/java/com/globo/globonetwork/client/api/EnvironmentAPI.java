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
package com.globo.globonetwork.client.api;

import java.util.ArrayList;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Environment;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class EnvironmentAPI extends BaseXmlAPI<Environment> {
	
	public EnvironmentAPI(RequestProcessor transport) {
		super(transport);
	}

	@Trace(dispatcher = true)
	public List<Environment> listAll() throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/listEnvironments");

		GloboNetworkRoot<Environment> globoNetworkRoot = this.get("/ambiente/list/");
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return new ArrayList<Environment>();
		}
		return globoNetworkRoot.getObjectList();
	}
}

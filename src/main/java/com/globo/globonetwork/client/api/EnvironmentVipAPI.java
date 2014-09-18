package com.globo.globonetwork.client.api;

import java.util.ArrayList;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Environment;
import com.globo.globonetwork.client.model.GloboNetworkRoot;

public class EnvironmentVipAPI extends BaseAPI<Environment> {
	
	public EnvironmentVipAPI(RequestProcessor transport) {
		super(transport);
	}
	
	public List<Environment> listAll() throws GloboNetworkException {
		GloboNetworkRoot<Environment> globoNetworkRoot = this.get("/environmentvip/all/");
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return new ArrayList<Environment>();
		}
		return globoNetworkRoot.getObjectList();
	}
}

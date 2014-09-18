package com.globo.globonetwork.client.api;

import java.util.ArrayList;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.EnvironmentVip;
import com.globo.globonetwork.client.model.GloboNetworkRoot;

public class EnvironmentVipAPI extends BaseAPI<EnvironmentVip> {
	
	public EnvironmentVipAPI(RequestProcessor transport) {
		super(transport);
	}
	
	public List<EnvironmentVip> listAll() throws GloboNetworkException {
		GloboNetworkRoot<EnvironmentVip> globoNetworkRoot = this.get("/environmentvip/all/");
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return new ArrayList<EnvironmentVip>();
		}
		return globoNetworkRoot.getObjectList();
	}
	
	public EnvironmentVip search(Long environmentVipId, String finality, String client, String environment) throws GloboNetworkException {
	    EnvironmentVip envVip = new EnvironmentVip();
	    envVip.setId(environmentVipId);
	    envVip.setFinality(finality);
	    envVip.setClient(client);
	    envVip.setEnvironmentName(environment);
	    
	    GloboNetworkRoot<EnvironmentVip> payload = new GloboNetworkRoot<EnvironmentVip>();
	    payload.getObjectList().add(envVip);
	    payload.set("environment_vip", envVip);
	    
	    GloboNetworkRoot<EnvironmentVip> globoNetworkRoot = this.post("/environmentvip/search/", payload);
	    if (globoNetworkRoot == null) {
            // Problems reading the XML
            throw new GloboNetworkException("Invalid XML response");
        }
	    
	    return globoNetworkRoot.getFirstObject();
	}
}

package com.globo.globonetwork.client.api;

import java.util.ArrayList;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.VipEnvironment;
import com.globo.globonetwork.client.model.GloboNetworkRoot;

public class VipEnvironmentAPI extends BaseAPI<VipEnvironment> {
	
	public VipEnvironmentAPI(RequestProcessor transport) {
		super(transport);
	}
	
	public List<VipEnvironment> listAll() throws GloboNetworkException {
		GloboNetworkRoot<VipEnvironment> globoNetworkRoot = this.get("/environmentvip/all/");
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return new ArrayList<VipEnvironment>();
		}
		return globoNetworkRoot.getObjectList();
	}
	
	public VipEnvironment search(Long environmentVipId, String finality, String client, String environment) throws GloboNetworkException {
	    VipEnvironment envVip = new VipEnvironment();
	    envVip.setId(environmentVipId);
	    envVip.setFinality(finality);
	    envVip.setClient(client);
	    envVip.setEnvironmentName(environment);
	    
	    GloboNetworkRoot<VipEnvironment> payload = new GloboNetworkRoot<VipEnvironment>();
	    payload.getObjectList().add(envVip);
	    payload.set("environment_vip", envVip);
	    
	    GloboNetworkRoot<VipEnvironment> globoNetworkRoot = this.post("/environmentvip/search/", payload);
	    if (globoNetworkRoot == null) {
            // Problems reading the XML
            throw new GloboNetworkException("Invalid XML response");
        }
	    
	    return globoNetworkRoot.getFirstObject();
	}
}

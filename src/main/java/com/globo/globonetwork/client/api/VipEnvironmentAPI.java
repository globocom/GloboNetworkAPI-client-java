package com.globo.globonetwork.client.api;

import java.util.ArrayList;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.VipEnvironment;
import com.google.api.client.xml.GenericXml;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class VipEnvironmentAPI extends BaseXmlAPI<VipEnvironment> {
	
	public VipEnvironmentAPI(RequestProcessor transport) {
		super(transport);
	}

	@Trace(dispatcher = true)
	public List<VipEnvironment> listAll() throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/listVipEnvironments");

		GloboNetworkRoot<VipEnvironment> globoNetworkRoot = this.get("/environmentvip/all/");
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return new ArrayList<VipEnvironment>();
		}
		return globoNetworkRoot.getObjectList();
	}


	@Trace(dispatcher = true)
	public VipEnvironment search(Long environmentVipId, String finality, String client, String environment) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/searchVipEnvironments");
		
		GenericXml envVip = new GenericXml();
	    envVip.set("id_environment_vip", environmentVipId);
	    envVip.set("finalidade_txt", finality);
	    envVip.set("cliente_txt", client);
	    envVip.set("ambiente_p44_txt", environment);
	    
	    GloboNetworkRoot<GenericXml> payload = new GloboNetworkRoot<GenericXml>();
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

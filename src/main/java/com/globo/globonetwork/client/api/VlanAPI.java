package com.globo.globonetwork.client.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.IPv4Network;
import com.globo.globonetwork.client.model.Vlan;

public class VlanAPI extends BaseAPI<Vlan> {
	
	public VlanAPI(RequestProcessor transport) {
		super(transport);
	}
	
	public List<Vlan> listByEnvironment(Long environmentId) throws GloboNetworkException {
		GloboNetworkRoot<Vlan> globoNetworkRoot = this.get("/vlan/ambiente/" + environmentId + "/");
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return new ArrayList<Vlan>();
		}
		return globoNetworkRoot.getObjectList();
	}
	
	public Vlan allocateWithoutNetwork(Long environmentId, String name, String description) throws GloboNetworkException {
		Vlan vlan = new Vlan();
		// vlan.setEnvironment(environmentId);
		// vlan.setName(name);
		// vlan.setDescription(description);
		vlan.set("name", name);
		vlan.set("environment_id", environmentId);
		vlan.set("description", description);
		
		GloboNetworkRoot<Vlan> globoNetworkRoot = new GloboNetworkRoot<Vlan>();
		globoNetworkRoot.getObjectList().add(vlan);
		globoNetworkRoot.set(vlan.superName(), vlan);
		
		GloboNetworkRoot<Vlan> globoNetworkRootAnswer = this.post("/vlan/no-network/", globoNetworkRoot);
		if (globoNetworkRootAnswer == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRootAnswer.getObjectList() == null) {
			return null;
		} else {
			// Check if there is only one element?
			return globoNetworkRootAnswer.getObjectList().get(0);
		}
	}
	
	public Vlan getById(Long vlanId) throws GloboNetworkException {
		GloboNetworkRoot<Vlan> globoNetworkRoot = this.get("/vlan/" + vlanId + "/network/");
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null || globoNetworkRoot.getObjectList().isEmpty()) {
			return null;
		} else if (globoNetworkRoot.getObjectList().size() > 1) {
			// Something is wrong, id should be unique
			throw new RuntimeException();
		} else {
			// Check if there is only one element?
			Vlan vlan = globoNetworkRoot.getObjectList().get(0);
			// Cleaning up empty networks
			for (Iterator<IPv4Network> iter = vlan.getIpv4Networks().iterator(); iter.hasNext(); ) {
				IPv4Network ipv4Network = iter.next();
				if (ipv4Network.getId() == null) {
					iter.remove();
				}
			}
			return vlan;
		}
	}
	
	public void remove(Long vlanId) throws GloboNetworkException {			
		this.delete("/vlan/" + vlanId + "/remove/");
	}
	
	public void deallocate(Long vlanId) throws GloboNetworkException {
		this.delete("/vlan/" + vlanId + "/deallocate/");
	}
}

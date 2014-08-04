package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.Equipment;
import com.globo.globonetwork.client.model.GloboNetworkRoot;

public class EquipmentAPI extends BaseAPI<Equipment> {
	
	public EquipmentAPI(RequestProcessor transport) {
		super(transport);
	}
	
	public Equipment listByName(String equipmentName) throws GloboNetworkException {
		try {
			GloboNetworkRoot<Equipment> globoNetworkRoot = this.get("/equipamento/nome/" + equipmentName + "/");
			if (globoNetworkRoot == null) {
				// Problems reading the XML
				throw new GloboNetworkException("Invalid XML response");
			} else if (globoNetworkRoot.getObjectList() == null || globoNetworkRoot.getObjectList().isEmpty()) {
				// not found
				return null;
			}
			return globoNetworkRoot.getFirstObject();
		} catch (GloboNetworkErrorCodeException e) {
			if (e.getCode() == GloboNetworkErrorCodeException.EQUIPMENT_NOT_REGISTERED) {
				return null;
			}
			throw e;
		}
	}
	
	public Equipment insert(String name, Long equipmentTypeId, Long modelId, Long groupId) throws GloboNetworkException {
		Equipment equipment = new Equipment();
		equipment.setName(name);
		equipment.setEquipmentTypeId(equipmentTypeId);
		equipment.setModelId(modelId);
		equipment.setGroupId(groupId);
		GloboNetworkRoot<Equipment> globoNetworkRoot = this.post("/equipamento/", new GloboNetworkRoot<Equipment>("equipamento", equipment));
		if (globoNetworkRoot == null || globoNetworkRoot.getObjectList() == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		}
		return globoNetworkRoot.getFirstObject();
	}

	public void delete(Long equipmentId) throws GloboNetworkException {
		this.delete("/equipamento/" + equipmentId + "/");
	}
	
	public void removeIP(Long equipId, Long idIp) throws GloboNetworkException {
		this.delete("/ip/" + idIp + "/equipamento/" + equipId + "/");
	}

}
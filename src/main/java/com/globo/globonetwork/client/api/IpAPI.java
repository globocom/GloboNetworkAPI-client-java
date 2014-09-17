package com.globo.globonetwork.client.api;

import java.util.ArrayList;
import java.util.List;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.Ip;
import com.globo.globonetwork.client.model.Ip.Ipv4;
import com.google.api.client.util.ArrayMap;
import com.google.api.client.xml.GenericXml;

public class IpAPI extends BaseAPI<Ip> {
	
	public IpAPI(RequestProcessor transport) {
		super(transport);
	}
	
	public Ip getIpv4(Long idIp) throws GloboNetworkException {
		
		GloboNetworkRoot<Ipv4> globoNetworkRoot = (GloboNetworkRoot<Ipv4>) this.getTransport().get("/ip/get-ipv4/" + idIp + "/", Ipv4.class);
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return null;
		}
		return globoNetworkRoot.getFirstObject();
	}
	
	public Ip findByIpAndEnvironment(String ip, Long idEnvironment) throws GloboNetworkException {
		try {
			GloboNetworkRoot<Ip> globoNetworkRoot = this.get("/ip/" + ip + "/ambiente/" + idEnvironment + "/");

			if (globoNetworkRoot == null) {
				// Problems reading the XML
				throw new GloboNetworkException("Invalid XML response");
			} else if (globoNetworkRoot.getObjectList() == null) {
				return null;
			}
			return globoNetworkRoot.getFirstObject();
		} catch (GloboNetworkErrorCodeException ex) {
			if (ex.getCode() == GloboNetworkErrorCodeException.IP_NOT_REGISTERED) {
				return null;
			}
			
			throw ex;
		}
	}
	
	public Ip saveIpv4(String ipv4, Long equipId, String nicDescription, Long networkId) throws GloboNetworkException {
		Ip ip = new Ip();
		ip.set("ip4", ipv4);
		ip.set("id_equip", equipId);
		ip.set("descricao", nicDescription);
		ip.set("id_net", networkId);
		
		GloboNetworkRoot<Ip> globoNetworkRootPayload = new GloboNetworkRoot<Ip>();
		globoNetworkRootPayload.getObjectList().add(ip);
		globoNetworkRootPayload.set("ip_map", ip);
		
		GloboNetworkRoot<Ip> globoNetworkRoot = this.post("/ipv4/save/", globoNetworkRootPayload);
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return null;
		}
		return globoNetworkRoot.getFirstObject(); 
	}

	public void deleteIpv4(Long idIpv4) throws GloboNetworkException {
		this.get("/ip4/delete/" + idIpv4 + "/");
	}
	
	public void assocIpv4(Long idIpv4, Long equipId, Long networkId) throws GloboNetworkException {
		Ip ip = new Ip();
		ip.set("id_ip", idIpv4);
		ip.set("id_equip", equipId);
		ip.set("id_net", networkId);
		
		GloboNetworkRoot<Ip> globoNetworkRootPayload = new GloboNetworkRoot<Ip>();
		globoNetworkRootPayload.getObjectList().add(ip);
		globoNetworkRootPayload.set("ip_map", ip);
		
		this.post("/ipv4/assoc/", globoNetworkRootPayload);
	}

    public List<Ip> findIpsByEquipment(Long equipId) throws GloboNetworkException {
		
		GloboNetworkRoot<GenericXml> globoNetworkRoot = this.getTransport().get("/ip/getbyequip/" + equipId + "/", GenericXml.class);

		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return new ArrayList<Ip>();
		}
		// NetworkAPI findIps interface is not compatible with all others objects, so I need to convert
		// data from api to IP object.
		List<Ip> ips = new ArrayList<Ip>();
		@SuppressWarnings("unchecked")
		List<ArrayMap<String, List<ArrayMap<String, String>>>> rawIps = ((List<ArrayMap<String, List<ArrayMap<String, String>>>>) globoNetworkRoot.getFirstObject().get("ipv4"));

		for (ArrayMap<String, List<ArrayMap<String, String>>> genericIp : rawIps) {
			if (genericIp.isEmpty()) {
				break;
			}
			
			Ip ip = new Ip();
			ip.setId(Long.valueOf(genericIp.get("id").get(0).getValue(0)));
			ip.setOct1(Integer.valueOf(genericIp.get("oct1").get(0).getValue(0)));
			ip.setOct2(Integer.valueOf(genericIp.get("oct2").get(0).getValue(0)));
			ip.setOct3(Integer.valueOf(genericIp.get("oct3").get(0).getValue(0)));
			ip.setOct4(Integer.valueOf(genericIp.get("oct4").get(0).getValue(0)));
			ips.add(ip);
		}
		return ips;
	}
    
    public Ip getAvailableIp4ForVip(long environmentVip, String name) throws GloboNetworkException {
        
        GenericXml ip_map = new GenericXml();
        ip_map.put("id_evip", String.valueOf(environmentVip));
        ip_map.put("name", name);
        
        GloboNetworkRoot<GenericXml> globoNetworkRootPayload = new GloboNetworkRoot<GenericXml>();
        globoNetworkRootPayload.getObjectList().add(ip_map);
        globoNetworkRootPayload.set("ip_map", ip_map);
        
        GloboNetworkRoot<Ip> globoNetworkRoot = this.post("/ip/availableip4/vip/" + environmentVip + "/", ip_map);
        if (globoNetworkRoot == null) {
            // Problems reading the XML
            throw new GloboNetworkException("Invalid XML response");
        } else if (globoNetworkRoot.getObjectList() == null) {
            return null;
        }
        return globoNetworkRoot.getFirstObject();
   }
	
}
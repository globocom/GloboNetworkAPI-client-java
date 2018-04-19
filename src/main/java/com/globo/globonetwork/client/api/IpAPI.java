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
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.Ip;
import com.globo.globonetwork.client.model.Ipv4;
import com.globo.globonetwork.client.model.Ipv6;
import com.google.api.client.util.ArrayMap;
import com.google.api.client.xml.GenericXml;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class IpAPI extends BaseXmlAPI<Ip> {
	
	public IpAPI(RequestProcessor transport) {
		super(transport);
	}

	@Trace(dispatcher = true)
	public Ip getIp(Long idIp, Boolean isIpv6)  throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/getIp");

		//Use of GenericXml necessary due to out of standards root xml node ('ipv4')
		String uri = isIpv6 ? "/ip/get-ipv6/" + idIp + "/" : "/ip/get-ipv4/" + idIp + "/";
		GloboNetworkRoot<GenericXml> globoNetworkRoot = (GloboNetworkRoot<GenericXml>) this.getTransport().get(uri, GenericXml.class);
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return null;
		}
		
		GenericXml genericObj = globoNetworkRoot.getFirstObject();
		Ip ip = Ip.createIp(isIpv6);
		assignTo(genericObj, ip);
		return ip;
	}

	@Trace(dispatcher = true)
	public Ip findByIpAndEnvironment(String ip, Long idEnvironment, Boolean isIpv6) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/findByIpAndEnvironment");

		try {
			if(isIpv6){
				return findByIpv6AndEnvironment(ip, idEnvironment);
			}else{
				return findByIpv4AndEnvironment(ip, idEnvironment);
			}
		} catch (GloboNetworkErrorCodeException ex) {
			if (ex.getCode() == GloboNetworkErrorCodeException.IP_NOT_REGISTERED) {
				return null;
			}
			
			throw ex;
		}
	}
	
	private Ipv4 findByIpv4AndEnvironment(String ip, Long idEnvironment) throws GloboNetworkException{
		GloboNetworkRoot<Ipv4> globoNetworkRoot = this.getTransport().get("/ip/" + ip + "/ambiente/" + idEnvironment + "/", Ipv4.class);
		
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return null;
		}
		return globoNetworkRoot.getFirstObject();
	}
	
	private Ipv6 findByIpv6AndEnvironment(String ip, Long idEnvironment) throws GloboNetworkException{
	    GenericXml ipv6 = new GenericXml();
		ipv6.set("id_environment", idEnvironment);
		ipv6.set("ipv6", ip);
		
		GloboNetworkRoot<GenericXml> globoNetworkRootPayload = new GloboNetworkRoot<GenericXml>();
		globoNetworkRootPayload.set("ipv6_map", ipv6);
		
		GloboNetworkRoot<Ipv6> globoNetworkRoot = this.getTransport().post("/ipv6/environment/", globoNetworkRootPayload, Ipv6.class);
		
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return null;
		}
		
		return globoNetworkRoot.getFirstObject();
	}

	@Trace(dispatcher = true)
	public Ip saveIp(String ipAddress, Long equipId, String nicDescription, Long networkId, Boolean isIpv6) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/findByIpAndEnvironment");

		Ip ip = Ip.createIp(isIpv6);
		ip.set(isIpv6 ? "ip6" : "ip4", ipAddress);
		ip.set("id_equip", equipId);
		ip.set("descricao", nicDescription);
		ip.set("id_net", networkId);
		
		GloboNetworkRoot<Ip> globoNetworkRootPayload = new GloboNetworkRoot<Ip>();
		globoNetworkRootPayload.getObjectList().add(ip);
		globoNetworkRootPayload.set("ip_map", ip);
		
		String uri = isIpv6 ? "/ipv6/save/" : "/ipv4/save/";
		@SuppressWarnings("unchecked")
		Class<? extends GenericXml> clazz = getIpClass(isIpv6);
		GloboNetworkRoot<Ip> globoNetworkRoot =  (GloboNetworkRoot<Ip>) this.getTransport().post(uri, globoNetworkRootPayload, clazz);
		if (globoNetworkRoot == null) {
			// Problems reading the XML
			throw new GloboNetworkException("Invalid XML response");
		} else if (globoNetworkRoot.getObjectList() == null) {
			return null;
		}
		return globoNetworkRoot.getFirstObject(); 
	}

	@Trace(dispatcher = true)
	public void deleteIp(Long idIp, Boolean isIpv6) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/deleteIp");

		String uri = isIpv6 ? "/ip6/delete/" + idIp + "/" : "/ip4/delete/" + idIp + "/" ;
		this.getTransport().get(uri, GenericXml.class);
	}

	@Trace(dispatcher = true)
	public void assocIp(Long idIp, Long equipId, Long networkId, Boolean isIpv6, String description) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/assocIp");

		Ip ip = Ip.createIp(isIpv6);
		ip.set("id_ip", idIp);
		ip.set("id_equip", equipId);
		ip.set("id_net", networkId);
		if (description != null) {
			ip.setDescription(description);
		}
		
		GloboNetworkRoot<Ip> globoNetworkRootPayload = new GloboNetworkRoot<Ip>();
		globoNetworkRootPayload.getObjectList().add(ip);
		globoNetworkRootPayload.set("ip_map", ip);
		
		String uri = isIpv6 ? "/ipv6/assoc/" : "/ipv4/assoc/";
		Class<? extends GenericXml> clazz = getIpClass(isIpv6);
		this.getTransport().post(uri, globoNetworkRootPayload, clazz);
	}

	private Class<? extends GenericXml> getIpClass(Boolean isIpv6) {
		return isIpv6 ? Ipv6.class : Ipv4.class;
	}

	@Trace(dispatcher = true)
	public List<Ip> findIpsByEquipment(Long equipId) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/findIpsByEquipment");

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
		List<ArrayMap<String, List<ArrayMap<String, String>>>> rawIpv4s = ((List<ArrayMap<String, List<ArrayMap<String, String>>>>) globoNetworkRoot.getFirstObject().get("ipv4"));
		@SuppressWarnings("unchecked")
		List<ArrayMap<String, List<ArrayMap<String, String>>>> rawIpv6s = ((List<ArrayMap<String, List<ArrayMap<String, String>>>>) globoNetworkRoot.getFirstObject().get("ipv6"));

		ips.addAll(buildIpv4(rawIpv4s));
		ips.addAll(buildIpv6(rawIpv6s));
		return ips;
	}
	
	private List<Ipv4> buildIpv4(List<ArrayMap<String, List<ArrayMap<String, String>>>> rawIpv6s){
		List<Ipv4> ips = new ArrayList<Ipv4>();
		// Interates and converts all IPv4 xml nodes
		for (ArrayMap<String, List<ArrayMap<String, String>>> genericIp : rawIpv6s) {
			if (genericIp.isEmpty()) {
				break;
			}
			
			Ipv4 ip = new Ipv4();
			ip.setId(Long.valueOf(genericIp.get("id").get(0).getValue(0)));
			ip.setOct1(Integer.valueOf(genericIp.get("oct1").get(0).getValue(0)));
			ip.setOct2(Integer.valueOf(genericIp.get("oct2").get(0).getValue(0)));
			ip.setOct3(Integer.valueOf(genericIp.get("oct3").get(0).getValue(0)));
			ip.setOct4(Integer.valueOf(genericIp.get("oct4").get(0).getValue(0)));
			ips.add(ip);
		}
		return ips;
	}
	
	private List<Ipv6> buildIpv6(List<ArrayMap<String, List<ArrayMap<String, String>>>> rawIpv6s){
		List<Ipv6> ips = new ArrayList<Ipv6>();
		// Interates and converts all IPv6 xml nodes
		for (ArrayMap<String, List<ArrayMap<String, String>>> genericIp : rawIpv6s) {
			if (genericIp.isEmpty()) {
				break;
			}
			
			Ipv6 ip = new Ipv6();
			ip.setId(Long.valueOf(genericIp.get("id").get(0).getValue(0)));
			ip.setBlock1(genericIp.get("block1").get(0).getValue(0));
			ip.setBlock2(genericIp.get("block2").get(0).getValue(0));
			ip.setBlock3(genericIp.get("block3").get(0).getValue(0));
			ip.setBlock4(genericIp.get("block4").get(0).getValue(0));
			ip.setBlock5(genericIp.get("block5").get(0).getValue(0));
			ip.setBlock6(genericIp.get("block6").get(0).getValue(0));
			ip.setBlock7(genericIp.get("block7").get(0).getValue(0));
			ip.setBlock8(genericIp.get("block8").get(0).getValue(0));
			ips.add(ip);
		}
		return ips;
	}

    @Trace(dispatcher = true)
    public Ip getAvailableIpForVip(long environmentVip, String name, Boolean isIpv6) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/getAvailableIpForVip");

        GenericXml ip_map = new GenericXml();
        ip_map.set("id_evip", String.valueOf(environmentVip));
        ip_map.set("name", name);
        
        GloboNetworkRoot<GenericXml> globoNetworkRootPayload = new GloboNetworkRoot<GenericXml>();
        globoNetworkRootPayload.getObjectList().add(ip_map);
        globoNetworkRootPayload.set("ip_map", ip_map);
        
        String uri = isIpv6 ? "/ip/availableip6/vip/" : "/ip/availableip4/vip/";
        @SuppressWarnings("unchecked")
		Class<? extends GenericXml> clazz = getIpClass(isIpv6);
		GloboNetworkRoot<Ip> globoNetworkRoot = (GloboNetworkRoot<Ip>) this.getTransport().post(uri + environmentVip + "/", globoNetworkRootPayload, clazz);
        if (globoNetworkRoot == null) {
            // Problems reading the XML
            throw new GloboNetworkException("Invalid XML response");
        } else if (globoNetworkRoot.getObjectList() == null) {
            return null;
        }
        return globoNetworkRoot.getFirstObject();
    }

    /*
     * Check if an Ip address(IpV4 or IpV6) can be used to a new vip.
     */
	@Trace(dispatcher = true)
    public Ip checkVipIp(String ip, long environmentVipId, Boolean isIpv6) throws GloboNetworkException {
		NewRelic.setTransactionName(null, "/globonetwork/checkVipIp");

        try {
            GenericXml ip_map = new GenericXml();
            ip_map.set("id_evip", String.valueOf(environmentVipId));
            ip_map.set("ip", ip);
            
            GloboNetworkRoot<GenericXml> globoNetworkRootPayload = new GloboNetworkRoot<GenericXml>();
            globoNetworkRootPayload.getObjectList().add(ip_map);
            globoNetworkRootPayload.set("ip_map", ip_map);
            
            @SuppressWarnings("unchecked")
			Class<? extends GenericXml> clazz = getIpClass(isIpv6);
			GloboNetworkRoot<Ip> globoNetworkRoot = (GloboNetworkRoot<Ip>) this.getTransport().post("/ip/checkvipip/", globoNetworkRootPayload, clazz);
        	if (globoNetworkRoot == null) {
        		// Problems reading the XML
        		throw new GloboNetworkException("Invalid XML response");
        	} else if (globoNetworkRoot.getObjectList() == null) {
        		return null;
        	}
        	return globoNetworkRoot.getFirstObject();
        } catch (GloboNetworkErrorCodeException ex) {
            if (ex.getCode() == GloboNetworkErrorCodeException.IPV4_NOT_IN_ENVIRONMENT_VIP) {
                return null;
            }
            
            throw ex;
        }
   }
}
package com.globo.globonetwork.client.api;

import java.io.IOException;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.Vip;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class VipAPI extends BaseAPI<Vip> {
	
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	static final JsonObjectParser parser = new JsonObjectParser(JSON_FACTORY);

	public VipAPI(RequestProcessor transport) {
		super(transport);
	}
		
	public Vip getById(Long vipId) throws GloboNetworkException {
		
		try {
			GloboNetworkRoot<Vip> globoNetworkRoot = this.get("/requestvip/getbyid/" + vipId + "/");
			if (globoNetworkRoot == null) {
				// Problems reading the XML
				throw new GloboNetworkException("Invalid XML response");
			} else if (globoNetworkRoot.getObjectList() == null || globoNetworkRoot.getObjectList().isEmpty()) {
				return null;
			} else if (globoNetworkRoot.getObjectList().size() > 1) {
				// Something is wrong, id should be unique
				throw new RuntimeException();
			} else {
				return globoNetworkRoot.getObjectList().get(0);
			}
		} catch (GloboNetworkErrorCodeException ex) {
			if (ex.getCode() == GloboNetworkErrorCodeException.VIP_NOT_REGISTERED) {
				return null;
			}
			
			throw ex;
		}
	}
	
	public void validate(Long vipId) throws GloboNetworkException {
		this.get("/vip/validate/" + vipId + "/");
	}
	
	public void create(Long vipId) throws GloboNetworkException {
		Vip vip = new Vip();
		vip.set("id_vip", vipId);
		
		GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
		payload.getObjectList().add(vip);
		payload.set("vip", vip);
		
		this.post("/vip/create/", payload);
	}
	
	// remove VIP from NetworkAPI DB
	public void removeVip(Long vipId) throws GloboNetworkException {
		this.delete("/vip/delete/" + vipId + "/");
	}
	
	// remove VIP from network device removeScriptVip
	public void removeScriptVip(Long vipId) throws GloboNetworkException {
		Vip vip = new Vip();
		vip.set("id_vip", vipId);
		
		GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
		payload.getObjectList().add(vip);
		payload.set("vip", vip);
		
		this.post("/vip/remove/", payload);
	}
	
	public void addReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "add", "v4");
	}
	
	public void enableReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "ena", "v4");
	}
	
	public void disableReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "dis", "v4");
	}
	
	public void checkReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "chk", "v4");
	}
	
	public void removeReal(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "del", "v4");
	}
	
	public void addRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "add", "v6");
	}
	
	public void enableRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "ena", "v6");
	}
	
	public void disableRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "dis", "v6");
	}
	
	public void checkRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "chk", "v6");
	}
	
	public void removeRealIpv6(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort) throws GloboNetworkException {
		this.performRealOperation(vipId, ipId, equipId, vipPort, realPort, "del", "v6");
	}
	
	private void performRealOperation(Long vipId, Long ipId, Long equipId, Integer vipPort, Integer realPort, String operation, String networkVersion) throws GloboNetworkException {
		Vip vip = new Vip();
		vip.set("vip_id", vipId);
		vip.set("ip_id", ipId);
		vip.set("equip_id", equipId);
		vip.set("port_vip", vipPort);
		vip.set("port_real", realPort);
		vip.set("operation", operation);
		vip.set("network_version", networkVersion);
		
		GloboNetworkRoot<Vip> payload = new GloboNetworkRoot<Vip>();
		payload.getObjectList().add(vip);
		payload.set("vip", vip);
		
		this.post("/vip/real/", payload);
	}
	
	public String generateVipEditingUrl(Long vipId, String vipServerUrl) throws GloboNetworkException {

		if (vipServerUrl == null || "".equals(vipServerUrl)) {
			throw new GloboNetworkException("Invalid VIP server URL");
		}
		
		HttpRequestFactory requestFactory =  new ApacheHttpTransport.Builder().build().createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) throws IOException {
				request.setNumberOfRetries(1);
				request.setThrowExceptionOnExecuteError(false);
				request.setParser(parser);
				request.setLoggingEnabled(true);
				request.getHeaders().setUserAgent("NetworkAPI-inside-cloudstack");
				request.setCurlLoggingEnabled(true);
			}
		});
		
		try {
			String body = "user=" + this.getTransport().getUsername() + "@" + this.getTransport().getPassword();
			HttpContent content = new ByteArrayContent("application/x-www-form-urlencoded", body.getBytes());
			GenericUrl tokenRequestUrl = new GenericUrl(vipServerUrl);
			tokenRequestUrl.setRawPath("/vip-request/token/");
			HttpRequest request = requestFactory.buildPostRequest(tokenRequestUrl, content);
			
			HttpResponse response = request.execute();
			GenericJson json = response.parseAs(GenericJson.class);
			String token = (String) json.get("token");
			String path = (String) json.get("url");
			
			GenericUrl vipEditingUrl = new GenericUrl(vipServerUrl);
			vipEditingUrl.setRawPath(path);
			vipEditingUrl.set("token", token);
			return vipEditingUrl.build();
		} catch (IOException e) {
			throw new GloboNetworkException("Error generating VIP url", e);
		}
	}
}

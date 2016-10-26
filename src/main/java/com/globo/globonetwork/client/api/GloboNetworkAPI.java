package com.globo.globonetwork.client.api;


import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.http.HttpXMLRequestProcessor;

public class GloboNetworkAPI {

    HttpJSONRequestProcessor jsonAPI;
    HttpXMLRequestProcessor xmlAPI;

    public GloboNetworkAPI(String baseUrl, String username, String password) {
        this.jsonAPI = new HttpJSONRequestProcessor(baseUrl, username, password);
        this.xmlAPI = new HttpXMLRequestProcessor(baseUrl, username, password);
    }

    public PoolAPI getPoolAPI() {
        return new PoolAPI(jsonAPI);
    }

    public VipAPI getVipAPI() {
        return new VipAPI(xmlAPI, jsonAPI);
    }

    public VlanAPI getVlanAPI() {
        return new VlanAPI(xmlAPI);
    }

    public NetworkAPI getNetworkAPI() {
        return new NetworkAPI(xmlAPI);
    }

    public NetworkJsonAPI getNetworkJsonAPI() {
        return new NetworkJsonAPI(jsonAPI);
    }

    public EnvironmentAPI getEnvironmentAPI() {
        return new EnvironmentAPI(xmlAPI);
    }

    public IpAPI getIpAPI() {
        return new IpAPI(xmlAPI);
    }

    public EquipmentAPI getEquipmentAPI() {
        return new EquipmentAPI(xmlAPI);
    }

    public OptionVipAPI getOptionVipAPI() { return new OptionVipAPI(xmlAPI); }

    public VipEnvironmentAPI getVipEnvironmentAPI() {
        return new VipEnvironmentAPI(xmlAPI);
    }

    public ExpectHealthcheckAPI getExpectHealthcheckAPI() {
        return new ExpectHealthcheckAPI(xmlAPI);
    }

    public VipV3API getVipV3API(){
        return new VipV3API(jsonAPI);
    }

    public OptionVipV3API getOptionVipV3API(){
        return new OptionVipV3API(jsonAPI);
    }

    public void setConnectTimeout(Integer connectTimeout) {
        jsonAPI.setConnectTimeout(connectTimeout);
        xmlAPI.setConnectTimeout(connectTimeout);
    }

    public void setNumberOfRetries(Integer numberOfRetries) {
        jsonAPI.setNumberOfRetries(numberOfRetries);
        xmlAPI.setNumberOfRetries(numberOfRetries);
    }

    public void setReadTimeout(Integer readTimeout) {
        jsonAPI.setReadTimeout(readTimeout);
        xmlAPI.setReadTimeout(readTimeout);
    }
}

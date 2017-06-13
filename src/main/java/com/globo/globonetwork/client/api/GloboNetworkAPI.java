package com.globo.globonetwork.client.api;


import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.http.HttpXMLRequestProcessor;

public class GloboNetworkAPI {

    private final HttpJSONRequestProcessor jsonAPI;
    private final HttpXMLRequestProcessor xmlAPI;
    private Integer numberOfRetries = 0;
    private Integer connectTimeout = 1*60000;
    private Integer readTimeout = 2*60000;;

    public static final String CONTEXT_HEADER = "X-Request-Context";
    public static final String ID_HEADER = "X-Request-Id";
    public static final String HEADER_REQUEST_PREFIX = "ACS_";


    private String context;

    public GloboNetworkAPI(String baseUrl, String username, String password) {
        this.jsonAPI = buildJsonProcessor(baseUrl, username, password);
        this.xmlAPI = buildXmlProcessor(baseUrl, username, password);
    }

    public PoolAPI getPoolAPI() {
        return new PoolAPI(jsonAPI);
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
        if (connectTimeout != null) {
            this.connectTimeout = connectTimeout;
        }
    }

    public void setNumberOfRetries(Integer numberOfRetries) {
        if (numberOfRetries != null) {
            this.numberOfRetries = numberOfRetries;
        }
    }

    public void setReadTimeout(Integer readTimeout) {
        if (readTimeout != null) {
            this.readTimeout = readTimeout;
        }
    }

    public HttpJSONRequestProcessor buildJsonProcessor(String baseUrl, String username, String password) {
        HttpJSONRequestProcessor processor = new HttpJSONRequestProcessor(baseUrl, username, password);
        processor.setReadTimeout(readTimeout);
        processor.setNumberOfRetries(numberOfRetries);
        processor.setConnectTimeout(connectTimeout);

        return processor;
    }

    public HttpXMLRequestProcessor buildXmlProcessor(String baseUrl, String username, String password) {
        HttpXMLRequestProcessor processor = new HttpXMLRequestProcessor(baseUrl, username, password);
        processor.setReadTimeout(readTimeout);
        processor.setNumberOfRetries(numberOfRetries);
        processor.setConnectTimeout(connectTimeout);



        return processor;
    }


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
        jsonAPI.setHeader(CONTEXT_HEADER, HEADER_REQUEST_PREFIX + context);
        xmlAPI.setHeader(CONTEXT_HEADER, HEADER_REQUEST_PREFIX + context);
    }
}

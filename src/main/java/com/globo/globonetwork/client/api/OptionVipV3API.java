package com.globo.globonetwork.client.api;


import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.http.HttpJSONRequestProcessor;
import com.globo.globonetwork.client.model.OptionVipV3;
import com.google.api.client.json.GenericJson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OptionVipV3API {

    private final HttpJSONRequestProcessor requestProcessor;

    public OptionVipV3API(HttpJSONRequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    public List<OptionVipV3> listOptions(Long vipEnvironmentId) throws GloboNetworkException {
        String response = requestProcessor.get("/api/v3/option-vip/environment-vip/"+ vipEnvironmentId +"/");
        List<GenericJson> result = Arrays.asList(HttpJSONRequestProcessor.parse(response, GenericJson[].class));
        List<OptionVipV3> optionsVip = new ArrayList<OptionVipV3>();
        for(GenericJson genericJson : result){
            Map option = (Map) genericJson.get("option");
            long id = ((BigDecimal) option.get("id")).longValue();
            String type = option.get("tipo_opcao").toString();
            String name = option.get("nome_opcao_txt").toString();
            optionsVip.add(new OptionVipV3(id, type, name));
        }
        return optionsVip;
    }

    public List<OptionVipV3> findOptionsByType(Long vipEnvironmentId, String type) throws GloboNetworkException {
        List<OptionVipV3> optionsVip = this.listOptions(vipEnvironmentId);
        List<OptionVipV3> filteredOptions = new ArrayList<OptionVipV3>();
        for(OptionVipV3 option : optionsVip){
            if(option.getType().equals(type)){
                filteredOptions.add(option);
            }
        }
        return filteredOptions;
    }

    public List<OptionVipV3> findOptionsByTypeAndName(Long vipEnvironmentId, String type, String name) throws GloboNetworkException {
        List<OptionVipV3> optionsVip = this.findOptionsByType(vipEnvironmentId, type);
        List<OptionVipV3> filteredOptions = new ArrayList<OptionVipV3>();
        for(OptionVipV3 option : optionsVip){
            if(option.getName().equals(name)){
                filteredOptions.add(option);
            }
        }
        return filteredOptions;
    }
}

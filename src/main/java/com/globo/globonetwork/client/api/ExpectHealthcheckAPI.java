package com.globo.globonetwork.client.api;

import com.globo.globonetwork.client.RequestProcessor;
import com.globo.globonetwork.client.exception.GloboNetworkErrorCodeException;
import com.globo.globonetwork.client.exception.GloboNetworkException;
import com.globo.globonetwork.client.model.GloboNetworkRoot;
import com.globo.globonetwork.client.model.OptionVip;
import com.globo.globonetwork.client.model.healthcheck.ExpectHealthcheck;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import java.util.ArrayList;
import java.util.List;


public class ExpectHealthcheckAPI extends BaseXmlAPI<ExpectHealthcheck> {

    public ExpectHealthcheckAPI(RequestProcessor transport) {
        super(transport);
    }


    @Trace(dispatcher = true)
    public List<ExpectHealthcheck> listHealthcheck() throws GloboNetworkException {
        NewRelic.setTransactionName(null, "/globonetwork/expectHealthcheck");

        try{
            GloboNetworkRoot<ExpectHealthcheck> globoNetworkRoot = this.get("/healthcheckexpect/distinct/busca/");
            if (globoNetworkRoot == null) {
                // Problems reading the XML
                throw new GloboNetworkException("Invalid XML response");
            } else if (globoNetworkRoot.getObjectList() == null) {
                return new ArrayList<ExpectHealthcheck>();
            }
            return globoNetworkRoot.getObjectList();
        } catch (GloboNetworkErrorCodeException e) {
            throw e;
        }
    }
}

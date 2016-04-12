package com.globo.globonetwork.client.model.pool;


import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.ArrayList;
import java.util.List;

public class PoolV3Response extends GenericJson {

    @Key("server_pools")
    public List<PoolV3> pools;

    public List<PoolV3> getPools() {
        return pools;
    }

    public void setPools(List<PoolV3> pools) {
        this.pools = pools;
    }

    public PoolV3Response() {
        this.pools = new ArrayList<PoolV3>();
    }

    public PoolV3 getFirstPool() {
        if ( pools != null && pools.size() > 0 ) {
            return pools.get(0);
        }
        return null;
    }

}

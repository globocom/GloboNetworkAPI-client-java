package com.globo.globonetwork.client.model.pool;

import com.google.api.client.util.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PoolV3Request {

    @Key("server_pools")
    public List<PoolV3> pools;

    public PoolV3Request() {
        this.pools = new ArrayList<PoolV3>();
    }

    public List<PoolV3> getPools() {
        return pools;
    }

    public void setPools(List<PoolV3> pools) {
        this.pools = pools;
    }



    public void addPoolToPut(PoolV3 pool) {
        if ( pool == null ){
            throw new IllegalArgumentException("Pool can not be null");
        }

        pools.add(pool);
    }

    public void addPoolToPost(PoolV3 pool) {

    }

    public static PoolV3Request newPost(PoolV3 pool) {
        if ( pool == null ){
            throw new IllegalArgumentException("Pool can not be null");
        }

        PoolV3Request poolV3Request = new PoolV3Request();
        poolV3Request.pools.add(pool);
        return poolV3Request;
    }

    public static PoolV3Request newPut(PoolV3 pool) {
        if ( pool == null ){
            throw new IllegalArgumentException("Pool can not be null");
        }

        PoolV3Request poolV3Request = new PoolV3Request();
        poolV3Request.pools.add(pool);
        return poolV3Request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoolV3Request that = (PoolV3Request) o;
        return Objects.equals(pools, that.pools);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pools);
    }
}

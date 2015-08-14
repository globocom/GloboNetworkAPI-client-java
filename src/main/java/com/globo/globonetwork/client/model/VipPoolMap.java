package com.globo.globonetwork.client.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.Objects;

public class VipPoolMap extends GenericJson {

    @Key("id")
    private Long id;

    @Key("server_pool")
    private Long poolId;

    @Key("requisicao_vip")
    private Long vipId;

    @Key("port_vip")
    private Integer port;

    public VipPoolMap(Long id, Long poolId, Long vipId, Integer port) {
        this.id = id;
        this.poolId = poolId;
        this.vipId = vipId;
        this.port = port;
    }

    public VipPoolMap(Long poolId, Integer port) {
        this.poolId = poolId;
        this.port = port;
    }

    public VipPoolMap() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    public Long getVipId() {
        return vipId;
    }

    public void setVipId(Long vipId) {
        this.vipId = vipId;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VipPoolMap that = (VipPoolMap) o;
        return Objects.equals(poolId, that.poolId) &&
                Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poolId, port);
    }
}

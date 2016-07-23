package com.sky.demo.model;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by rg on 2016/7/23.
 */
public class ThriftServerInfo implements Serializable {

    private String ip;
    private int port;
    private String serverTag;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerTag() {
        return serverTag;
    }

    public void setServerTag(String serverTag) {
        this.serverTag = serverTag;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("ip", ip)
                .add("port", port)
                .add("serverTag", serverTag)
                .toString();
    }
}

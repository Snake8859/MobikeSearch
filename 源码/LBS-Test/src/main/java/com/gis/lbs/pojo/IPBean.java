package com.gis.lbs.pojo;

public class IPBean {

    private String ip;
    private int port;
    private String type;
    private String lifeTime;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(String lifeTime) {
        this.lifeTime = lifeTime;
    }

    @Override
    public String toString() {
        return "IPBean{" +
                "ip=" + ip +
                ", port=" + port +
                ", type='" + type + '\'' +
                ", lifeTime='" + lifeTime + '\'' +
                '}';
    }
}

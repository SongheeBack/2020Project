package com.example.a2020project.monitor;

public class MonitorListview {
    private String deviceName;
    private String deviceData;

    public void setDeviceName(String dName){
        deviceName = dName;
    }

    public void setDeviceData(String dData){
        deviceData = dData;
    }

    public String getDeviceName(){
        return this.deviceName;
    }

    public String getDeviceData(){
        return this.deviceData;
    }
}

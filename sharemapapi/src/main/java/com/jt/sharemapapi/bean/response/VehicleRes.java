package com.jt.sharemapapi.bean.response;

import java.io.Serializable;

/**
 * <pre>
 *     @author : @yuegongming
 *     e-mail : yuegongming@chehejia.com
 *     time   : 2017/12/28
 *     desc   :附近车辆数据
 *     version: 1.0
 * </pre>
 */
public class VehicleRes implements Serializable {


    /**
     * 电量
     */
    private int batteryPowerPercentage;
    /**
     * 公里数
     */
    private int extensionMileage;
    /**
     * 纬度
     */
    private double latitude;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 车vin码
     */
    private String vin;

    public int getBatteryPowerPercentage() {
        return batteryPowerPercentage;
    }

    public void setBatteryPowerPercentage(int batteryPowerPercentage) {
        this.batteryPowerPercentage = batteryPowerPercentage;
    }

    public int getExtensionMileage() {
        return extensionMileage;
    }

    public void setExtensionMileage(int extensionMileage) {
        this.extensionMileage = extensionMileage;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

}

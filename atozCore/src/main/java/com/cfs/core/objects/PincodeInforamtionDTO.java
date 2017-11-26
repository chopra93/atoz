package com.cfs.core.objects;

/**
 * Created by Chopra on 26/11/17.
 */
public class PincodeInforamtionDTO {
    private String state;
    private String city;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "PincodeInforamtionDTO{" +
                "state='" + state + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

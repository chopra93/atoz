package com.cfs.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chopra
 * 11/11/17
 */
@Entity
@Table(name = "city_information")
public class CityInformation extends BaseEntity{
    private String cityName;
    private boolean enabled;

    @Column(name = "name", nullable = false)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

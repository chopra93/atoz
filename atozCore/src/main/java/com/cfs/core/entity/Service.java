package com.cfs.core.entity;

import javax.persistence.*;

/**
 * @author chopra
 * 05/12/17
 */
@Entity
@Table(name = "service")
public class Service extends BaseEntity{
    private String serviceType;
    private String limit;
    private Long expiry;
    private boolean isActive;
    private Users serviceUsers;

    @Column(name = "service_type", nullable = false)
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Column(name = "limit", nullable = false)
    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    @Column(name = "expiry", nullable = false)
    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    @Column(name = "is_active", nullable = false)
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getServiceUsers() {
        return serviceUsers;
    }

    public void setServiceUsers(Users serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

}

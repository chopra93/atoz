package com.cfs.core.objects;

import com.cfs.core.enums.ServiceEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

/**
 * @author chopra
 * 06/12/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceDTO {
    @NotNull
    private String token;
    @NotNull
    private String serviceType;
    @NotNull
    private String limit;
    private Long expiry;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "ServiceDTO{" +
                "token='" + token + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", limit='" + limit + '\'' +
                ", expiry='" + expiry + '\'' +
                '}';
    }
}

package com.cfs.core.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author chopra
 * 07/12/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceResponse {
    private String message;
    private Integer statusCode;
    private List<ServiceDTO> serviceList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<ServiceDTO> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceDTO> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "message='" + message + '\'' +
                ", statusCode=" + statusCode +
                ", serviceList=" + serviceList +
                '}';
    }
}

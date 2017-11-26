package com.cfs.core.objects;

import com.cfs.core.enums.YearMonth;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Chopra on 15/11/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EMIResponse {
    private String amount;
    private String rate;
    private String duration;
    private String coumpoundFrequency;
    private String emi;
    private String fd;
    private String rd;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCoumpoundFrequency() {
        return coumpoundFrequency;
    }

    public void setCoumpoundFrequency(String coumpoundFrequency) {
        this.coumpoundFrequency = coumpoundFrequency;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getFd() {
        return fd;
    }

    public void setFd(String fd) {
        this.fd = fd;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    @Override
    public String toString() {
        return "EMIResponse{" +
                "amount='" + amount + '\'' +
                ", rate='" + rate + '\'' +
                ", duration='" + duration + '\'' +
                ", coumpoundFrequency='" + coumpoundFrequency + '\'' +
                ", emi='" + emi + '\'' +
                ", fd='" + fd + '\'' +
                ", rd='" + rd + '\'' +
                '}';
    }
}

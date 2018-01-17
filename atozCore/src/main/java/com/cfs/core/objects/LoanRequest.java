package com.cfs.core.objects;

import com.cfs.core.enums.LoanEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author chopra
 * 17/01/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanRequest {
    private String name;
    private String email;
    private String phone;
    private int loanAmount;
    private LoanEnum type;
    private String preferenceOne;
    private String preferenceTwo;
    private String preferenceThree;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LoanEnum getType() {
        return type;
    }

    public void setType(LoanEnum type) {
        this.type = type;
    }

    public String getPreferenceOne() {
        return preferenceOne;
    }

    public void setPreferenceOne(String preferenceOne) {
        this.preferenceOne = preferenceOne;
    }

    public String getPreferenceTwo() {
        return preferenceTwo;
    }

    public void setPreferenceTwo(String preferenceTwo) {
        this.preferenceTwo = preferenceTwo;
    }

    public String getPreferenceThree() {
        return preferenceThree;
    }

    public void setPreferenceThree(String preferenceThree) {
        this.preferenceThree = preferenceThree;
    }

    @Override
    public String toString() {
        return "LoanRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", loanAmount=" + loanAmount +
                ", type=" + type +
                ", preferenceOne='" + preferenceOne + '\'' +
                ", preferenceTwo='" + preferenceTwo + '\'' +
                ", preferenceThree='" + preferenceThree + '\'' +
                '}';
    }
}

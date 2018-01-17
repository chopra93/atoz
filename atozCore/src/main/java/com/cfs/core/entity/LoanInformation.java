package com.cfs.core.entity;

import javax.persistence.*;

/**
 * @author chopra
 * 17/01/18
 */
@Entity
@Table(name = "loan_information")
public class LoanInformation extends BaseEntity {
    private Integer loanAmount;
    private String type;
    private String preferenceOne;
    private String preferenceTwo;
    private String preferenceThree;
    private boolean active;
    private LoanUsers loanUsers;


    @Column(name = "loan_amount", nullable = false)
    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "preference_one", nullable = false)
    public String getPreferenceOne() {
        return preferenceOne;
    }

    public void setPreferenceOne(String preferenceOne) {
        this.preferenceOne = preferenceOne;
    }

    @Column(name = "preference_two", nullable = true)
    public String getPreferenceTwo() {
        return preferenceTwo;
    }

    public void setPreferenceTwo(String preferenceTwo) {
        this.preferenceTwo = preferenceTwo;
    }

    @Column(name = "preference_three", nullable = true)
    public String getPreferenceThree() {
        return preferenceThree;
    }

    public void setPreferenceThree(String preferenceThree) {
        this.preferenceThree = preferenceThree;
    }

    @Column(name = "is_active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public LoanUsers getLoanUsers() {
        return loanUsers;
    }

    public void setLoanUsers(LoanUsers loanUsers) {
        this.loanUsers = loanUsers;
    }
}

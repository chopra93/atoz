package com.cfs.core.entity;

import javax.persistence.*;

/**
 * @author chopra
 * 11/11/17
 */
@Entity
@Table(name = "bank_information")
public class BankInformation extends BaseEntity{
    private String bankName;
    private boolean enabled;

    @Column(name = "name", nullable = false)
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

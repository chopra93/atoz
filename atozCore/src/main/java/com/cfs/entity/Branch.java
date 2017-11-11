package com.cfs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chopra
 * 11/11/17
 */
@Entity
@Table(name = "branch")
public class Branch extends BaseEntity {
    private String ifsc;
    private String branch;
    private String branchCode;
    private String micrCode;
    private String address;

    @Column(name = "ifsc", nullable = false)
    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    @Column(name = "branch", nullable = false)
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Column(name = "branch_code", nullable = false)
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    @Column(name = "micr_code", nullable = false)
    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

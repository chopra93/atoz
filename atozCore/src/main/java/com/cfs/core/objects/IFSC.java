package com.cfs.core.objects;

import java.util.List;
import java.util.Set;

/**
 * Created by Chopra on 16/11/17.
 */
public class IFSC {
    private String ifsc;
    private String branch;
    private String branchCode;
    private String micrCode;
    private String address;

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "IFSC{" +
                "ifsc='" + ifsc + '\'' +
                ", branch='" + branch + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", micrCode='" + micrCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

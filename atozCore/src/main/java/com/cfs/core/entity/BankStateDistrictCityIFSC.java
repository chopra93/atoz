package com.cfs.core.entity;

/**
 * @author chopra
 * 11/11/17
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bankStateDistrictCityIFSC")
public class BankStateDistrictCityIFSC extends BaseEntity {
    private Integer bid;
    private Integer sid;
    private Integer did;
    private Integer cid;
    private String ifsc;
    private boolean enabled;

    @Column(name = "bid", nullable = false)
    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    @Column(name = "sid", nullable = false)
    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    @Column(name = "did", nullable = false)
    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    @Column(name = "cid", nullable = false)
    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Column(name = "ifsc", nullable = false)
    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

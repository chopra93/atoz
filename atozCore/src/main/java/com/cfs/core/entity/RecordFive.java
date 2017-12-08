package com.cfs.core.entity;

import javax.persistence.*;

/**
 * @author chopra
 * 08/12/17
 */
@Entity
@Table(name = "record_five")
public class RecordFive {
    private Users recordFiveUser;
    private String mobile;
    private Message message;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getRecordFiveUser() {
        return recordFiveUser;
    }

    public void setRecordFiveUser(Users recordFiveUser) {
        this.recordFiveUser = recordFiveUser;
    }

    @Column(name = "mobile", nullable = false)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msg_id", nullable = false)
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Column(name = "is_active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

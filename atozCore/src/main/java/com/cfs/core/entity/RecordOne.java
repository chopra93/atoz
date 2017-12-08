package com.cfs.core.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author chopra
 * 08/12/17
 */
@Entity
@Table(name = "record_one")
public class RecordOne extends BaseEntity{

    private Users recordOneUser;
    private String mobile;
    private Message message;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getRecordOneUser() {
        return recordOneUser;
    }

    public void setRecordOneUser(Users recordOneUser) {
        this.recordOneUser = recordOneUser;
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

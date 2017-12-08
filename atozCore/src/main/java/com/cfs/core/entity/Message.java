package com.cfs.core.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author chopra
 * 08/12/17
 */
@Entity
@Table(name = "message")
public class Message extends BaseEntity {
    private String message;
    private boolean active;
    private Set<RecordOne> recordOne;

    @Column(name = "message", nullable = false)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "is_active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    public Set<RecordOne> getRecordOne() {
        return recordOne;
    }

    public void setRecordOne(Set<RecordOne> recordOne) {
        this.recordOne = recordOne;
    }
}

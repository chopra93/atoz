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
    private Set<RecordTwo> recordTwo;
    private Set<RecordThree> recordThree;
    private Set<RecordFour> recordFour;
    private Set<RecordFive> recordFive;


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

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    public Set<RecordTwo> getRecordTwo() {
        return recordTwo;
    }

    public void setRecordTwo(Set<RecordTwo> recordTwo) {
        this.recordTwo = recordTwo;
    }

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    public Set<RecordThree> getRecordThree() {
        return recordThree;
    }

    public void setRecordThree(Set<RecordThree> recordThree) {
        this.recordThree = recordThree;
    }

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    public Set<RecordFour> getRecordFour() {
        return recordFour;
    }

    public void setRecordFour(Set<RecordFour> recordFour) {
        this.recordFour = recordFour;
    }

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    public Set<RecordFive> getRecordFive() {
        return recordFive;
    }

    public void setRecordFive(Set<RecordFive> recordFive) {
        this.recordFive = recordFive;
    }
}

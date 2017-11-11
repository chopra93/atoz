package com.cfs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chopra
 * 11/11/17
 */
@Entity
@Table(name = "state")
public class state extends BaseEntity{
    private String name;

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

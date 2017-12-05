package com.cfs.core.entity;

import javax.persistence.*;

/**
 * @author chopra
 * 05/12/17
 */
@Entity
@Table(name = "access_token")
public class AccessToken extends BaseEntity{
    private String token;
    private String expiry;
    private boolean isActive;
    private Users users;

    @Column(name = "token", nullable = false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "expiry", nullable = false)
    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    @Column(name = "is_active", nullable = false)
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}

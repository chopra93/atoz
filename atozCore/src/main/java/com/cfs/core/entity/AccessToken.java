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
    private Long expiry;
    private boolean active;
    private Users accessTokenUsers;

    @Column(name = "token", nullable = false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "expiry", nullable = false)
    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    @Column(name = "is_active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getAccessTokenUsers() {
        return accessTokenUsers;
    }

    public void setAccessTokenUsers(Users accessTokenUsers) {
        this.accessTokenUsers = accessTokenUsers;
    }
}

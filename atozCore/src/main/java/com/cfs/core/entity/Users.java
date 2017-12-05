package com.cfs.core.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author chopra
 * 05/12/17
 */
@Entity
@Table(name = "users")
public class Users extends BaseEntity{
    private String name;
    private String username;
    private String phone;
    private String email;
    private AccessToken accessToken;
    private Set<Service> service;

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne(mappedBy = "accessTokenUsers", cascade = CascadeType.ALL)
    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @OneToMany(mappedBy = "serviceUsers", cascade = CascadeType.ALL)
    public Set<Service> getService() {
        return service;
    }

    public void setService(Set<Service> service) {
        this.service = service;
    }
}

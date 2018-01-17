package com.cfs.core.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author chopra
 * 17/01/18
 */
@Entity
@Table(name = "users_loan")
public class LoanUsers extends BaseEntity{
    private String name;
    private String email;
    private String phone;
    private Set<LoanInformation> loanInformations;

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @OneToMany(mappedBy = "loanUsers", cascade = CascadeType.ALL)
    public Set<LoanInformation> getLoanInformations() {
        return loanInformations;
    }

    public void setLoanInformations(Set<LoanInformation> loanInformations) {
        this.loanInformations = loanInformations;
    }
}

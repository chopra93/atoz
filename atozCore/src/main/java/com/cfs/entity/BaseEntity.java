package com.cfs.entity;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public abstract class
BaseEntity implements Serializable {

  private static final long serialVersionUID = 4310332490880086526L;

  private Integer id;

  private boolean enabled;

  private String createdBy;

  private String updatedBy;

  private Date created = DateTime.now().toDate();

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "created_by")
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  @Column(name = "updated_by")
  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created", length = 19, nullable = false)
  public Date getCreated() {

    return created;
  }

  public void setCreated(Date created) {

    this.created = created;
  }

  @Column(name = "enabled")
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public String toString() {
    return "BaseEntity{" +
            "id=" + id +
            ", enabled=" + enabled +
            ", createdBy='" + createdBy + '\'' +
            ", updatedBy='" + updatedBy + '\'' +
            ", created=" + created +
            '}';
  }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author HNT
 */
@Entity
@Table(name = "audit_events")
@NamedQueries({
    @NamedQuery(name = "AuditEvents.findAll", query = "SELECT a FROM AuditEvents a"),
    @NamedQuery(name = "AuditEvents.findById", query = "SELECT a FROM AuditEvents a WHERE a.id = :id"),
    @NamedQuery(name = "AuditEvents.findByEventType", query = "SELECT a FROM AuditEvents a WHERE a.eventType = :eventType"),
    @NamedQuery(name = "AuditEvents.findByEntityType", query = "SELECT a FROM AuditEvents a WHERE a.entityType = :entityType"),
    @NamedQuery(name = "AuditEvents.findByEntityId", query = "SELECT a FROM AuditEvents a WHERE a.entityId = :entityId"),
    @NamedQuery(name = "AuditEvents.findByCreatedAt", query = "SELECT a FROM AuditEvents a WHERE a.createdAt = :createdAt")})
public class AuditEvents implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "event_type")
    private String eventType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "entity_type")
    private String entityType;
    @Column(name = "entity_id")
    private BigInteger entityId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "detail")
    private Object detail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "actor_id", referencedColumnName = "id")
    @ManyToOne
    private Users actorId;

    public AuditEvents() {
    }

    public AuditEvents(Long id) {
        this.id = id;
    }

    public AuditEvents(Long id, String eventType, String entityType, Object detail, Date createdAt) {
        this.id = id;
        this.eventType = eventType;
        this.entityType = entityType;
        this.detail = detail;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public BigInteger getEntityId() {
        return entityId;
    }

    public void setEntityId(BigInteger entityId) {
        this.entityId = entityId;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Users getActorId() {
        return actorId;
    }

    public void setActorId(Users actorId) {
        this.actorId = actorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuditEvents)) {
            return false;
        }
        AuditEvents other = (AuditEvents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.AuditEvents[ id=" + id + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HNT
 */
@Entity
@Table(name = "defense_minutes")
@NamedQueries({
    @NamedQuery(name = "DefenseMinutes.findAll", query = "SELECT d FROM DefenseMinutes d"),
    @NamedQuery(name = "DefenseMinutes.findByDefenseId", query = "SELECT d FROM DefenseMinutes d WHERE d.defenseId = :defenseId"),
    @NamedQuery(name = "DefenseMinutes.findByContent", query = "SELECT d FROM DefenseMinutes d WHERE d.content = :content"),
    @NamedQuery(name = "DefenseMinutes.findBySubmittedAt", query = "SELECT d FROM DefenseMinutes d WHERE d.submittedAt = :submittedAt")})
public class DefenseMinutes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "defense_id")
    private Long defenseId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "submitted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedAt;
    @JoinColumn(name = "defense_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Defenses defenses;
    @JoinColumn(name = "submitted_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users submittedBy;

    public DefenseMinutes() {
    }

    public DefenseMinutes(Long defenseId) {
        this.defenseId = defenseId;
    }

    public DefenseMinutes(Long defenseId, String content, Date submittedAt) {
        this.defenseId = defenseId;
        this.content = content;
        this.submittedAt = submittedAt;
    }

    public Long getDefenseId() {
        return defenseId;
    }

    public void setDefenseId(Long defenseId) {
        this.defenseId = defenseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Defenses getDefenses() {
        return defenses;
    }

    public void setDefenses(Defenses defenses) {
        this.defenses = defenses;
    }

    public Users getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(Users submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (defenseId != null ? defenseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DefenseMinutes)) {
            return false;
        }
        DefenseMinutes other = (DefenseMinutes) object;
        if ((this.defenseId == null && other.defenseId != null) || (this.defenseId != null && !this.defenseId.equals(other.defenseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.DefenseMinutes[ defenseId=" + defenseId + " ]";
    }
    
}

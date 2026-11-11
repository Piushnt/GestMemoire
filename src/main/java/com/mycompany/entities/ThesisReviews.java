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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
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
@Table(name = "thesis_reviews")
@NamedQueries({
    @NamedQuery(name = "ThesisReviews.findAll", query = "SELECT t FROM ThesisReviews t"),
    @NamedQuery(name = "ThesisReviews.findById", query = "SELECT t FROM ThesisReviews t WHERE t.id = :id"),
    @NamedQuery(name = "ThesisReviews.findByComments", query = "SELECT t FROM ThesisReviews t WHERE t.comments = :comments"),
    @NamedQuery(name = "ThesisReviews.findByDecision", query = "SELECT t FROM ThesisReviews t WHERE t.decision = :decision"),
    @NamedQuery(name = "ThesisReviews.findByCreatedAt", query = "SELECT t FROM ThesisReviews t WHERE t.createdAt = :createdAt")})
public class ThesisReviews implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "comments")
    private String comments;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "decision")
    private String decision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "thesis_version_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ThesisVersions thesisVersionId;
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users teacherId;

    public ThesisReviews() {
    }

    public ThesisReviews(Long id) {
        this.id = id;
    }

    public ThesisReviews(Long id, String comments, String decision, Date createdAt) {
        this.id = id;
        this.comments = comments;
        this.decision = decision;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ThesisVersions getThesisVersionId() {
        return thesisVersionId;
    }

    public void setThesisVersionId(ThesisVersions thesisVersionId) {
        this.thesisVersionId = thesisVersionId;
    }

    public Users getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Users teacherId) {
        this.teacherId = teacherId;
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
        if (!(object instanceof ThesisReviews)) {
            return false;
        }
        ThesisReviews other = (ThesisReviews) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.ThesisReviews[ id=" + id + " ]";
    }
    
}

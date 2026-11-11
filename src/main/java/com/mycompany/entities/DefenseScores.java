/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author HNT
 */
@Entity
@Table(name = "defense_scores")
@NamedQueries({
    @NamedQuery(name = "DefenseScores.findAll", query = "SELECT d FROM DefenseScores d"),
    @NamedQuery(name = "DefenseScores.findByDefenseId", query = "SELECT d FROM DefenseScores d WHERE d.defenseScoresPK.defenseId = :defenseId"),
    @NamedQuery(name = "DefenseScores.findByTeacherId", query = "SELECT d FROM DefenseScores d WHERE d.defenseScoresPK.teacherId = :teacherId"),
    @NamedQuery(name = "DefenseScores.findByScore", query = "SELECT d FROM DefenseScores d WHERE d.score = :score"),
    @NamedQuery(name = "DefenseScores.findByComments", query = "SELECT d FROM DefenseScores d WHERE d.comments = :comments"),
    @NamedQuery(name = "DefenseScores.findByMinutes", query = "SELECT d FROM DefenseScores d WHERE d.minutes = :minutes"),
    @NamedQuery(name = "DefenseScores.findBySubmittedAt", query = "SELECT d FROM DefenseScores d WHERE d.submittedAt = :submittedAt")})
public class DefenseScores implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DefenseScoresPK defenseScoresPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private BigDecimal score;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "comments")
    private String comments;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "minutes")
    private String minutes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "submitted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedAt;
    @JoinColumns({
        @JoinColumn(name = "defense_id", referencedColumnName = "defense_id", insertable = false, updatable = false),
        @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private DefenseJury defenseJury;

    public DefenseScores() {
    }

    public DefenseScores(DefenseScoresPK defenseScoresPK) {
        this.defenseScoresPK = defenseScoresPK;
    }

    public DefenseScores(DefenseScoresPK defenseScoresPK, BigDecimal score, String comments, String minutes, Date submittedAt) {
        this.defenseScoresPK = defenseScoresPK;
        this.score = score;
        this.comments = comments;
        this.minutes = minutes;
        this.submittedAt = submittedAt;
    }

    public DefenseScores(long defenseId, long teacherId) {
        this.defenseScoresPK = new DefenseScoresPK(defenseId, teacherId);
    }

    public DefenseScoresPK getDefenseScoresPK() {
        return defenseScoresPK;
    }

    public void setDefenseScoresPK(DefenseScoresPK defenseScoresPK) {
        this.defenseScoresPK = defenseScoresPK;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public DefenseJury getDefenseJury() {
        return defenseJury;
    }

    public void setDefenseJury(DefenseJury defenseJury) {
        this.defenseJury = defenseJury;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (defenseScoresPK != null ? defenseScoresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DefenseScores)) {
            return false;
        }
        DefenseScores other = (DefenseScores) object;
        if ((this.defenseScoresPK == null && other.defenseScoresPK != null) || (this.defenseScoresPK != null && !this.defenseScoresPK.equals(other.defenseScoresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.DefenseScores[ defenseScoresPK=" + defenseScoresPK + " ]";
    }
    
}

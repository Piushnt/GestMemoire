/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author HNT
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByFullName", query = "SELECT u FROM Users u WHERE u.fullName = :fullName"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByPasswordHash", query = "SELECT u FROM Users u WHERE u.passwordHash = :passwordHash"),
    @NamedQuery(name = "Users.findByRole", query = "SELECT u FROM Users u WHERE u.role = :role"),
    @NamedQuery(name = "Users.findByActive", query = "SELECT u FROM Users u WHERE u.active = :active"),
    @NamedQuery(name = "Users.findByFailedLoginCount", query = "SELECT u FROM Users u WHERE u.failedLoginCount = :failedLoginCount"),
    @NamedQuery(name = "Users.findByLockedUntil", query = "SELECT u FROM Users u WHERE u.lockedUntil = :lockedUntil"),
    @NamedQuery(name = "Users.findByCreatedAt", query = "SELECT u FROM Users u WHERE u.createdAt = :createdAt"),
    @NamedQuery(name = "Users.findByUpdatedAt", query = "SELECT u FROM Users u WHERE u.updatedAt = :updatedAt")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 160)
    @Column(name = "full_name")
    private String fullName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "password_hash")
    private String passwordHash;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 24)
    @Column(name = "role")
    private String role;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "failed_login_count")
    private int failedLoginCount;
    @Column(name = "locked_until")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockedUntil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(mappedBy = "updatedBy")
    private Collection<SystemParameters> systemParametersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacherId")
    private Collection<ThesisReviews> thesisReviewsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Collection<DefenseJury> defenseJuryCollection;
    @OneToMany(mappedBy = "actorId")
    private Collection<AuditEvents> auditEventsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submittedBy")
    private Collection<DefenseMinutes> defenseMinutesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private Collection<ThesisVersions> thesisVersionsCollection;
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    @ManyToOne
    private Programs programId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plannedBy")
    private Collection<Defenses> defensesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private Collection<Defenses> defensesCollection1;
    @OneToMany(mappedBy = "assignedStudentId")
    private Collection<Themes> themesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerId")
    private Collection<Themes> themesCollection1;
    @OneToMany(mappedBy = "reviewedBy")
    private Collection<Themes> themesCollection2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedBy")
    private Collection<Supervisions> supervisionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private Collection<Supervisions> supervisionsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacherId")
    private Collection<Supervisions> supervisionsCollection2;
    @OneToMany(mappedBy = "decidedBy")
    private Collection<SupervisionRequests> supervisionRequestsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private Collection<SupervisionRequests> supervisionRequestsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacherId")
    private Collection<SupervisionRequests> supervisionRequestsCollection2;

    public Users() {
    }

    public Users(Long id) {
        this.id = id;
    }

    public Users(Long id, String fullName, String email, String passwordHash, String role, boolean active, int failedLoginCount, Date createdAt, Date updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
        this.failedLoginCount = failedLoginCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(int failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public Date getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(Date lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Collection<SystemParameters> getSystemParametersCollection() {
        return systemParametersCollection;
    }

    public void setSystemParametersCollection(Collection<SystemParameters> systemParametersCollection) {
        this.systemParametersCollection = systemParametersCollection;
    }

    public Collection<ThesisReviews> getThesisReviewsCollection() {
        return thesisReviewsCollection;
    }

    public void setThesisReviewsCollection(Collection<ThesisReviews> thesisReviewsCollection) {
        this.thesisReviewsCollection = thesisReviewsCollection;
    }

    public Collection<DefenseJury> getDefenseJuryCollection() {
        return defenseJuryCollection;
    }

    public void setDefenseJuryCollection(Collection<DefenseJury> defenseJuryCollection) {
        this.defenseJuryCollection = defenseJuryCollection;
    }

    public Collection<AuditEvents> getAuditEventsCollection() {
        return auditEventsCollection;
    }

    public void setAuditEventsCollection(Collection<AuditEvents> auditEventsCollection) {
        this.auditEventsCollection = auditEventsCollection;
    }

    public Collection<DefenseMinutes> getDefenseMinutesCollection() {
        return defenseMinutesCollection;
    }

    public void setDefenseMinutesCollection(Collection<DefenseMinutes> defenseMinutesCollection) {
        this.defenseMinutesCollection = defenseMinutesCollection;
    }

    public Collection<ThesisVersions> getThesisVersionsCollection() {
        return thesisVersionsCollection;
    }

    public void setThesisVersionsCollection(Collection<ThesisVersions> thesisVersionsCollection) {
        this.thesisVersionsCollection = thesisVersionsCollection;
    }

    public Programs getProgramId() {
        return programId;
    }

    public void setProgramId(Programs programId) {
        this.programId = programId;
    }

    public Collection<Defenses> getDefensesCollection() {
        return defensesCollection;
    }

    public void setDefensesCollection(Collection<Defenses> defensesCollection) {
        this.defensesCollection = defensesCollection;
    }

    public Collection<Defenses> getDefensesCollection1() {
        return defensesCollection1;
    }

    public void setDefensesCollection1(Collection<Defenses> defensesCollection1) {
        this.defensesCollection1 = defensesCollection1;
    }

    public Collection<Themes> getThemesCollection() {
        return themesCollection;
    }

    public void setThemesCollection(Collection<Themes> themesCollection) {
        this.themesCollection = themesCollection;
    }

    public Collection<Themes> getThemesCollection1() {
        return themesCollection1;
    }

    public void setThemesCollection1(Collection<Themes> themesCollection1) {
        this.themesCollection1 = themesCollection1;
    }

    public Collection<Themes> getThemesCollection2() {
        return themesCollection2;
    }

    public void setThemesCollection2(Collection<Themes> themesCollection2) {
        this.themesCollection2 = themesCollection2;
    }

    public Collection<Supervisions> getSupervisionsCollection() {
        return supervisionsCollection;
    }

    public void setSupervisionsCollection(Collection<Supervisions> supervisionsCollection) {
        this.supervisionsCollection = supervisionsCollection;
    }

    public Collection<Supervisions> getSupervisionsCollection1() {
        return supervisionsCollection1;
    }

    public void setSupervisionsCollection1(Collection<Supervisions> supervisionsCollection1) {
        this.supervisionsCollection1 = supervisionsCollection1;
    }

    public Collection<Supervisions> getSupervisionsCollection2() {
        return supervisionsCollection2;
    }

    public void setSupervisionsCollection2(Collection<Supervisions> supervisionsCollection2) {
        this.supervisionsCollection2 = supervisionsCollection2;
    }

    public Collection<SupervisionRequests> getSupervisionRequestsCollection() {
        return supervisionRequestsCollection;
    }

    public void setSupervisionRequestsCollection(Collection<SupervisionRequests> supervisionRequestsCollection) {
        this.supervisionRequestsCollection = supervisionRequestsCollection;
    }

    public Collection<SupervisionRequests> getSupervisionRequestsCollection1() {
        return supervisionRequestsCollection1;
    }

    public void setSupervisionRequestsCollection1(Collection<SupervisionRequests> supervisionRequestsCollection1) {
        this.supervisionRequestsCollection1 = supervisionRequestsCollection1;
    }

    public Collection<SupervisionRequests> getSupervisionRequestsCollection2() {
        return supervisionRequestsCollection2;
    }

    public void setSupervisionRequestsCollection2(Collection<SupervisionRequests> supervisionRequestsCollection2) {
        this.supervisionRequestsCollection2 = supervisionRequestsCollection2;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Users[ id=" + id + " ]";
    }
    
}

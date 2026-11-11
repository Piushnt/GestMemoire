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
@Table(name = "supervision_requests")
@NamedQueries({
    @NamedQuery(name = "SupervisionRequests.findAll", query = "SELECT s FROM SupervisionRequests s"),
    @NamedQuery(name = "SupervisionRequests.findById", query = "SELECT s FROM SupervisionRequests s WHERE s.id = :id"),
    @NamedQuery(name = "SupervisionRequests.findByStatus", query = "SELECT s FROM SupervisionRequests s WHERE s.status = :status"),
    @NamedQuery(name = "SupervisionRequests.findByMessage", query = "SELECT s FROM SupervisionRequests s WHERE s.message = :message"),
    @NamedQuery(name = "SupervisionRequests.findByCreatedAt", query = "SELECT s FROM SupervisionRequests s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "SupervisionRequests.findByDecidedAt", query = "SELECT s FROM SupervisionRequests s WHERE s.decidedAt = :decidedAt")})
public class SupervisionRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "status")
    private String status;
    @Size(max = 2147483647)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "decided_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date decidedAt;
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AcademicYears academicYearId;
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    @ManyToOne
    private Themes themeId;
    @JoinColumn(name = "decided_by", referencedColumnName = "id")
    @ManyToOne
    private Users decidedBy;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users studentId;
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users teacherId;

    public SupervisionRequests() {
    }

    public SupervisionRequests(Long id) {
        this.id = id;
    }

    public SupervisionRequests(Long id, String status, Date createdAt) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDecidedAt() {
        return decidedAt;
    }

    public void setDecidedAt(Date decidedAt) {
        this.decidedAt = decidedAt;
    }

    public AcademicYears getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(AcademicYears academicYearId) {
        this.academicYearId = academicYearId;
    }

    public Themes getThemeId() {
        return themeId;
    }

    public void setThemeId(Themes themeId) {
        this.themeId = themeId;
    }

    public Users getDecidedBy() {
        return decidedBy;
    }

    public void setDecidedBy(Users decidedBy) {
        this.decidedBy = decidedBy;
    }

    public Users getStudentId() {
        return studentId;
    }

    public void setStudentId(Users studentId) {
        this.studentId = studentId;
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
        if (!(object instanceof SupervisionRequests)) {
            return false;
        }
        SupervisionRequests other = (SupervisionRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.SupervisionRequests[ id=" + id + " ]";
    }
    
}

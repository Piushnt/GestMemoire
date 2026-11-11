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
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HNT
 */
@Entity
@Table(name = "supervisions")
@NamedQueries({
    @NamedQuery(name = "Supervisions.findAll", query = "SELECT s FROM Supervisions s"),
    @NamedQuery(name = "Supervisions.findById", query = "SELECT s FROM Supervisions s WHERE s.id = :id"),
    @NamedQuery(name = "Supervisions.findByAssignedAt", query = "SELECT s FROM Supervisions s WHERE s.assignedAt = :assignedAt")})
public class Supervisions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "assigned_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignedAt;
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AcademicYears academicYearId;
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Themes themeId;
    @JoinColumn(name = "assigned_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users assignedBy;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users studentId;
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users teacherId;

    public Supervisions() {
    }

    public Supervisions(Long id) {
        this.id = id;
    }

    public Supervisions(Long id, Date assignedAt) {
        this.id = id;
        this.assignedAt = assignedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Date assignedAt) {
        this.assignedAt = assignedAt;
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

    public Users getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Users assignedBy) {
        this.assignedBy = assignedBy;
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
        if (!(object instanceof Supervisions)) {
            return false;
        }
        Supervisions other = (Supervisions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Supervisions[ id=" + id + " ]";
    }
    
}

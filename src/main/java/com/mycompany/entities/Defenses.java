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
import jakarta.persistence.OneToOne;
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
@Table(name = "defenses")
@NamedQueries({
    @NamedQuery(name = "Defenses.findAll", query = "SELECT d FROM Defenses d"),
    @NamedQuery(name = "Defenses.findById", query = "SELECT d FROM Defenses d WHERE d.id = :id"),
    @NamedQuery(name = "Defenses.findByStartsAt", query = "SELECT d FROM Defenses d WHERE d.startsAt = :startsAt"),
    @NamedQuery(name = "Defenses.findByEndsAt", query = "SELECT d FROM Defenses d WHERE d.endsAt = :endsAt"),
    @NamedQuery(name = "Defenses.findByStatus", query = "SELECT d FROM Defenses d WHERE d.status = :status"),
    @NamedQuery(name = "Defenses.findByCreatedAt", query = "SELECT d FROM Defenses d WHERE d.createdAt = :createdAt")})
public class Defenses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "starts_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startsAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ends_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endsAt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "defenses")
    private Collection<DefenseJury> defenseJuryCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "defenses")
    private DefenseMinutes defenseMinutes;
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AcademicYears academicYearId;
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rooms roomId;
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Themes themeId;
    @JoinColumn(name = "planned_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users plannedBy;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users studentId;

    public Defenses() {
    }

    public Defenses(Long id) {
        this.id = id;
    }

    public Defenses(Long id, Date startsAt, Date endsAt, String status, Date createdAt) {
        this.id = id;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Date startsAt) {
        this.startsAt = startsAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Collection<DefenseJury> getDefenseJuryCollection() {
        return defenseJuryCollection;
    }

    public void setDefenseJuryCollection(Collection<DefenseJury> defenseJuryCollection) {
        this.defenseJuryCollection = defenseJuryCollection;
    }

    public DefenseMinutes getDefenseMinutes() {
        return defenseMinutes;
    }

    public void setDefenseMinutes(DefenseMinutes defenseMinutes) {
        this.defenseMinutes = defenseMinutes;
    }

    public AcademicYears getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(AcademicYears academicYearId) {
        this.academicYearId = academicYearId;
    }

    public Rooms getRoomId() {
        return roomId;
    }

    public void setRoomId(Rooms roomId) {
        this.roomId = roomId;
    }

    public Themes getThemeId() {
        return themeId;
    }

    public void setThemeId(Themes themeId) {
        this.themeId = themeId;
    }

    public Users getPlannedBy() {
        return plannedBy;
    }

    public void setPlannedBy(Users plannedBy) {
        this.plannedBy = plannedBy;
    }

    public Users getStudentId() {
        return studentId;
    }

    public void setStudentId(Users studentId) {
        this.studentId = studentId;
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
        if (!(object instanceof Defenses)) {
            return false;
        }
        Defenses other = (Defenses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Defenses[ id=" + id + " ]";
    }
    
}

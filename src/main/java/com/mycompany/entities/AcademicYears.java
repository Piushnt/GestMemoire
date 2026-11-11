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
@Table(name = "academic_years")
@NamedQueries({
    @NamedQuery(name = "AcademicYears.findAll", query = "SELECT a FROM AcademicYears a"),
    @NamedQuery(name = "AcademicYears.findById", query = "SELECT a FROM AcademicYears a WHERE a.id = :id"),
    @NamedQuery(name = "AcademicYears.findByLabel", query = "SELECT a FROM AcademicYears a WHERE a.label = :label"),
    @NamedQuery(name = "AcademicYears.findByStartsOn", query = "SELECT a FROM AcademicYears a WHERE a.startsOn = :startsOn"),
    @NamedQuery(name = "AcademicYears.findByEndsOn", query = "SELECT a FROM AcademicYears a WHERE a.endsOn = :endsOn"),
    @NamedQuery(name = "AcademicYears.findByCurrent", query = "SELECT a FROM AcademicYears a WHERE a.current = :current")})
public class AcademicYears implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "label")
    private String label;
    @Basic(optional = false)
    @NotNull
    @Column(name = "starts_on")
    @Temporal(TemporalType.DATE)
    private Date startsOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ends_on")
    @Temporal(TemporalType.DATE)
    private Date endsOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "current")
    private boolean current;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicYearId")
    private Collection<Defenses> defensesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicYearId")
    private Collection<Themes> themesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicYearId")
    private Collection<Supervisions> supervisionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicYearId")
    private Collection<SupervisionRequests> supervisionRequestsCollection;

    public AcademicYears() {
    }

    public AcademicYears(Long id) {
        this.id = id;
    }

    public AcademicYears(Long id, String label, Date startsOn, Date endsOn, boolean current) {
        this.id = id;
        this.label = label;
        this.startsOn = startsOn;
        this.endsOn = endsOn;
        this.current = current;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getStartsOn() {
        return startsOn;
    }

    public void setStartsOn(Date startsOn) {
        this.startsOn = startsOn;
    }

    public Date getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(Date endsOn) {
        this.endsOn = endsOn;
    }

    public boolean getCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public Collection<Defenses> getDefensesCollection() {
        return defensesCollection;
    }

    public void setDefensesCollection(Collection<Defenses> defensesCollection) {
        this.defensesCollection = defensesCollection;
    }

    public Collection<Themes> getThemesCollection() {
        return themesCollection;
    }

    public void setThemesCollection(Collection<Themes> themesCollection) {
        this.themesCollection = themesCollection;
    }

    public Collection<Supervisions> getSupervisionsCollection() {
        return supervisionsCollection;
    }

    public void setSupervisionsCollection(Collection<Supervisions> supervisionsCollection) {
        this.supervisionsCollection = supervisionsCollection;
    }

    public Collection<SupervisionRequests> getSupervisionRequestsCollection() {
        return supervisionRequestsCollection;
    }

    public void setSupervisionRequestsCollection(Collection<SupervisionRequests> supervisionRequestsCollection) {
        this.supervisionRequestsCollection = supervisionRequestsCollection;
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
        if (!(object instanceof AcademicYears)) {
            return false;
        }
        AcademicYears other = (AcademicYears) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.AcademicYears[ id=" + id + " ]";
    }
    
}

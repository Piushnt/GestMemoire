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
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author HNT
 */
@Entity
@Table(name = "themes")
@NamedQueries({
    @NamedQuery(name = "Themes.findAll", query = "SELECT t FROM Themes t"),
    @NamedQuery(name = "Themes.findById", query = "SELECT t FROM Themes t WHERE t.id = :id"),
    @NamedQuery(name = "Themes.findByTitle", query = "SELECT t FROM Themes t WHERE t.title = :title"),
    @NamedQuery(name = "Themes.findByDescription", query = "SELECT t FROM Themes t WHERE t.description = :description"),
    @NamedQuery(name = "Themes.findByKeywords", query = "SELECT t FROM Themes t WHERE t.keywords = :keywords"),
    @NamedQuery(name = "Themes.findBySimilarityPercent", query = "SELECT t FROM Themes t WHERE t.similarityPercent = :similarityPercent"),
    @NamedQuery(name = "Themes.findByStatus", query = "SELECT t FROM Themes t WHERE t.status = :status"),
    @NamedQuery(name = "Themes.findByLocked", query = "SELECT t FROM Themes t WHERE t.locked = :locked"),
    @NamedQuery(name = "Themes.findByReviewNote", query = "SELECT t FROM Themes t WHERE t.reviewNote = :reviewNote"),
    @NamedQuery(name = "Themes.findBySubmittedAt", query = "SELECT t FROM Themes t WHERE t.submittedAt = :submittedAt"),
    @NamedQuery(name = "Themes.findByReviewedAt", query = "SELECT t FROM Themes t WHERE t.reviewedAt = :reviewedAt"),
    @NamedQuery(name = "Themes.findByVersion", query = "SELECT t FROM Themes t WHERE t.version = :version")})
public class Themes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 180)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "keywords")
    private String keywords;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "similarity_percent")
    private BigDecimal similarityPercent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "locked")
    private boolean locked;
    @Size(max = 2147483647)
    @Column(name = "review_note")
    private String reviewNote;
    @Basic(optional = false)
    @NotNull
    @Column(name = "submitted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedAt;
    @Column(name = "reviewed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewedAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "themes")
    private Collection<ThemeSimilarityMatches> themeSimilarityMatchesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "themes1")
    private Collection<ThemeSimilarityMatches> themeSimilarityMatchesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "themeId")
    private Collection<ThesisVersions> thesisVersionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "themeId")
    private Collection<Defenses> defensesCollection;
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AcademicYears academicYearId;
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Programs programId;
    @JoinColumn(name = "assigned_student_id", referencedColumnName = "id")
    @ManyToOne
    private Users assignedStudentId;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users ownerId;
    @JoinColumn(name = "reviewed_by", referencedColumnName = "id")
    @ManyToOne
    private Users reviewedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "themeId")
    private Collection<Supervisions> supervisionsCollection;
    @OneToMany(mappedBy = "themeId")
    private Collection<SupervisionRequests> supervisionRequestsCollection;

    public Themes() {
    }

    public Themes(Long id) {
        this.id = id;
    }

    public Themes(Long id, String title, String description, String keywords, BigDecimal similarityPercent, String status, boolean locked, Date submittedAt, int version) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.similarityPercent = similarityPercent;
        this.status = status;
        this.locked = locked;
        this.submittedAt = submittedAt;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public BigDecimal getSimilarityPercent() {
        return similarityPercent;
    }

    public void setSimilarityPercent(BigDecimal similarityPercent) {
        this.similarityPercent = similarityPercent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getReviewNote() {
        return reviewNote;
    }

    public void setReviewNote(String reviewNote) {
        this.reviewNote = reviewNote;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Date getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Date reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Collection<ThemeSimilarityMatches> getThemeSimilarityMatchesCollection() {
        return themeSimilarityMatchesCollection;
    }

    public void setThemeSimilarityMatchesCollection(Collection<ThemeSimilarityMatches> themeSimilarityMatchesCollection) {
        this.themeSimilarityMatchesCollection = themeSimilarityMatchesCollection;
    }

    public Collection<ThemeSimilarityMatches> getThemeSimilarityMatchesCollection1() {
        return themeSimilarityMatchesCollection1;
    }

    public void setThemeSimilarityMatchesCollection1(Collection<ThemeSimilarityMatches> themeSimilarityMatchesCollection1) {
        this.themeSimilarityMatchesCollection1 = themeSimilarityMatchesCollection1;
    }

    public Collection<ThesisVersions> getThesisVersionsCollection() {
        return thesisVersionsCollection;
    }

    public void setThesisVersionsCollection(Collection<ThesisVersions> thesisVersionsCollection) {
        this.thesisVersionsCollection = thesisVersionsCollection;
    }

    public Collection<Defenses> getDefensesCollection() {
        return defensesCollection;
    }

    public void setDefensesCollection(Collection<Defenses> defensesCollection) {
        this.defensesCollection = defensesCollection;
    }

    public AcademicYears getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(AcademicYears academicYearId) {
        this.academicYearId = academicYearId;
    }

    public Programs getProgramId() {
        return programId;
    }

    public void setProgramId(Programs programId) {
        this.programId = programId;
    }

    public Users getAssignedStudentId() {
        return assignedStudentId;
    }

    public void setAssignedStudentId(Users assignedStudentId) {
        this.assignedStudentId = assignedStudentId;
    }

    public Users getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Users ownerId) {
        this.ownerId = ownerId;
    }

    public Users getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Users reviewedBy) {
        this.reviewedBy = reviewedBy;
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
        if (!(object instanceof Themes)) {
            return false;
        }
        Themes other = (Themes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Themes[ id=" + id + " ]";
    }
    
}

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
import jakarta.persistence.Lob;
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
@Table(name = "thesis_versions")
@NamedQueries({
    @NamedQuery(name = "ThesisVersions.findAll", query = "SELECT t FROM ThesisVersions t"),
    @NamedQuery(name = "ThesisVersions.findById", query = "SELECT t FROM ThesisVersions t WHERE t.id = :id"),
    @NamedQuery(name = "ThesisVersions.findByVersionNumber", query = "SELECT t FROM ThesisVersions t WHERE t.versionNumber = :versionNumber"),
    @NamedQuery(name = "ThesisVersions.findByOriginalFilename", query = "SELECT t FROM ThesisVersions t WHERE t.originalFilename = :originalFilename"),
    @NamedQuery(name = "ThesisVersions.findBySizeBytes", query = "SELECT t FROM ThesisVersions t WHERE t.sizeBytes = :sizeBytes"),
    @NamedQuery(name = "ThesisVersions.findBySha256", query = "SELECT t FROM ThesisVersions t WHERE t.sha256 = :sha256"),
    @NamedQuery(name = "ThesisVersions.findByStatus", query = "SELECT t FROM ThesisVersions t WHERE t.status = :status"),
    @NamedQuery(name = "ThesisVersions.findByUploadedAt", query = "SELECT t FROM ThesisVersions t WHERE t.uploadedAt = :uploadedAt")})
public class ThesisVersions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version_number")
    private int versionNumber;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "storage_key")
    private Object storageKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "original_filename")
    private String originalFilename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "size_bytes")
    private long sizeBytes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "sha256")
    private String sha256;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "uploaded_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thesisVersionId")
    private Collection<ThesisReviews> thesisReviewsCollection;
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Themes themeId;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users studentId;

    public ThesisVersions() {
    }

    public ThesisVersions(Long id) {
        this.id = id;
    }

    public ThesisVersions(Long id, int versionNumber, Object storageKey, String originalFilename, long sizeBytes, String sha256, String status, Date uploadedAt) {
        this.id = id;
        this.versionNumber = versionNumber;
        this.storageKey = storageKey;
        this.originalFilename = originalFilename;
        this.sizeBytes = sizeBytes;
        this.sha256 = sha256;
        this.status = status;
        this.uploadedAt = uploadedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Object getStorageKey() {
        return storageKey;
    }

    public void setStorageKey(Object storageKey) {
        this.storageKey = storageKey;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Collection<ThesisReviews> getThesisReviewsCollection() {
        return thesisReviewsCollection;
    }

    public void setThesisReviewsCollection(Collection<ThesisReviews> thesisReviewsCollection) {
        this.thesisReviewsCollection = thesisReviewsCollection;
    }

    public Themes getThemeId() {
        return themeId;
    }

    public void setThemeId(Themes themeId) {
        this.themeId = themeId;
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
        if (!(object instanceof ThesisVersions)) {
            return false;
        }
        ThesisVersions other = (ThesisVersions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.ThesisVersions[ id=" + id + " ]";
    }
    
}

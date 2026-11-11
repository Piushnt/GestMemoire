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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author HNT
 */
@Entity
@Table(name = "theme_similarity_matches")
@NamedQueries({
    @NamedQuery(name = "ThemeSimilarityMatches.findAll", query = "SELECT t FROM ThemeSimilarityMatches t"),
    @NamedQuery(name = "ThemeSimilarityMatches.findByThemeId", query = "SELECT t FROM ThemeSimilarityMatches t WHERE t.themeSimilarityMatchesPK.themeId = :themeId"),
    @NamedQuery(name = "ThemeSimilarityMatches.findByComparedThemeId", query = "SELECT t FROM ThemeSimilarityMatches t WHERE t.themeSimilarityMatchesPK.comparedThemeId = :comparedThemeId"),
    @NamedQuery(name = "ThemeSimilarityMatches.findBySimilarityPercent", query = "SELECT t FROM ThemeSimilarityMatches t WHERE t.similarityPercent = :similarityPercent")})
public class ThemeSimilarityMatches implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ThemeSimilarityMatchesPK themeSimilarityMatchesPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "similarity_percent")
    private BigDecimal similarityPercent;
    @JoinColumn(name = "compared_theme_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Themes themes;
    @JoinColumn(name = "theme_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Themes themes1;

    public ThemeSimilarityMatches() {
    }

    public ThemeSimilarityMatches(ThemeSimilarityMatchesPK themeSimilarityMatchesPK) {
        this.themeSimilarityMatchesPK = themeSimilarityMatchesPK;
    }

    public ThemeSimilarityMatches(ThemeSimilarityMatchesPK themeSimilarityMatchesPK, BigDecimal similarityPercent) {
        this.themeSimilarityMatchesPK = themeSimilarityMatchesPK;
        this.similarityPercent = similarityPercent;
    }

    public ThemeSimilarityMatches(long themeId, long comparedThemeId) {
        this.themeSimilarityMatchesPK = new ThemeSimilarityMatchesPK(themeId, comparedThemeId);
    }

    public ThemeSimilarityMatchesPK getThemeSimilarityMatchesPK() {
        return themeSimilarityMatchesPK;
    }

    public void setThemeSimilarityMatchesPK(ThemeSimilarityMatchesPK themeSimilarityMatchesPK) {
        this.themeSimilarityMatchesPK = themeSimilarityMatchesPK;
    }

    public BigDecimal getSimilarityPercent() {
        return similarityPercent;
    }

    public void setSimilarityPercent(BigDecimal similarityPercent) {
        this.similarityPercent = similarityPercent;
    }

    public Themes getThemes() {
        return themes;
    }

    public void setThemes(Themes themes) {
        this.themes = themes;
    }

    public Themes getThemes1() {
        return themes1;
    }

    public void setThemes1(Themes themes1) {
        this.themes1 = themes1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (themeSimilarityMatchesPK != null ? themeSimilarityMatchesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ThemeSimilarityMatches)) {
            return false;
        }
        ThemeSimilarityMatches other = (ThemeSimilarityMatches) object;
        if ((this.themeSimilarityMatchesPK == null && other.themeSimilarityMatchesPK != null) || (this.themeSimilarityMatchesPK != null && !this.themeSimilarityMatchesPK.equals(other.themeSimilarityMatchesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.ThemeSimilarityMatches[ themeSimilarityMatchesPK=" + themeSimilarityMatchesPK + " ]";
    }
    
}

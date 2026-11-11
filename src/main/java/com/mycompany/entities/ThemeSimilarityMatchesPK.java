/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author HNT
 */
@Embeddable
public class ThemeSimilarityMatchesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "theme_id")
    private long themeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "compared_theme_id")
    private long comparedThemeId;

    public ThemeSimilarityMatchesPK() {
    }

    public ThemeSimilarityMatchesPK(long themeId, long comparedThemeId) {
        this.themeId = themeId;
        this.comparedThemeId = comparedThemeId;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public long getComparedThemeId() {
        return comparedThemeId;
    }

    public void setComparedThemeId(long comparedThemeId) {
        this.comparedThemeId = comparedThemeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) themeId;
        hash += (int) comparedThemeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ThemeSimilarityMatchesPK)) {
            return false;
        }
        ThemeSimilarityMatchesPK other = (ThemeSimilarityMatchesPK) object;
        if (this.themeId != other.themeId) {
            return false;
        }
        if (this.comparedThemeId != other.comparedThemeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.ThemeSimilarityMatchesPK[ themeId=" + themeId + ", comparedThemeId=" + comparedThemeId + " ]";
    }
    
}

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
public class DefenseJuryPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "defense_id")
    private long defenseId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "teacher_id")
    private long teacherId;

    public DefenseJuryPK() {
    }

    public DefenseJuryPK(long defenseId, long teacherId) {
        this.defenseId = defenseId;
        this.teacherId = teacherId;
    }

    public long getDefenseId() {
        return defenseId;
    }

    public void setDefenseId(long defenseId) {
        this.defenseId = defenseId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) defenseId;
        hash += (int) teacherId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DefenseJuryPK)) {
            return false;
        }
        DefenseJuryPK other = (DefenseJuryPK) object;
        if (this.defenseId != other.defenseId) {
            return false;
        }
        if (this.teacherId != other.teacherId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.DefenseJuryPK[ defenseId=" + defenseId + ", teacherId=" + teacherId + " ]";
    }
    
}

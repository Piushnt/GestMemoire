/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author HNT
 */
@Entity
@Table(name = "defense_jury")
@NamedQueries({
    @NamedQuery(name = "DefenseJury.findAll", query = "SELECT d FROM DefenseJury d"),
    @NamedQuery(name = "DefenseJury.findByDefenseId", query = "SELECT d FROM DefenseJury d WHERE d.defenseJuryPK.defenseId = :defenseId"),
    @NamedQuery(name = "DefenseJury.findByTeacherId", query = "SELECT d FROM DefenseJury d WHERE d.defenseJuryPK.teacherId = :teacherId"),
    @NamedQuery(name = "DefenseJury.findByMemberRole", query = "SELECT d FROM DefenseJury d WHERE d.memberRole = :memberRole")})
public class DefenseJury implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DefenseJuryPK defenseJuryPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "member_role")
    private String memberRole;
    @JoinColumn(name = "defense_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Defenses defenses;
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "defenseJury")
    private DefenseScores defenseScores;

    public DefenseJury() {
    }

    public DefenseJury(DefenseJuryPK defenseJuryPK) {
        this.defenseJuryPK = defenseJuryPK;
    }

    public DefenseJury(DefenseJuryPK defenseJuryPK, String memberRole) {
        this.defenseJuryPK = defenseJuryPK;
        this.memberRole = memberRole;
    }

    public DefenseJury(long defenseId, long teacherId) {
        this.defenseJuryPK = new DefenseJuryPK(defenseId, teacherId);
    }

    public DefenseJuryPK getDefenseJuryPK() {
        return defenseJuryPK;
    }

    public void setDefenseJuryPK(DefenseJuryPK defenseJuryPK) {
        this.defenseJuryPK = defenseJuryPK;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public Defenses getDefenses() {
        return defenses;
    }

    public void setDefenses(Defenses defenses) {
        this.defenses = defenses;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public DefenseScores getDefenseScores() {
        return defenseScores;
    }

    public void setDefenseScores(DefenseScores defenseScores) {
        this.defenseScores = defenseScores;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (defenseJuryPK != null ? defenseJuryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DefenseJury)) {
            return false;
        }
        DefenseJury other = (DefenseJury) object;
        if ((this.defenseJuryPK == null && other.defenseJuryPK != null) || (this.defenseJuryPK != null && !this.defenseJuryPK.equals(other.defenseJuryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.DefenseJury[ defenseJuryPK=" + defenseJuryPK + " ]";
    }
    
}

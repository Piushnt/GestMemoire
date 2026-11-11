/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "system_parameters")
@NamedQueries({
    @NamedQuery(name = "SystemParameters.findAll", query = "SELECT s FROM SystemParameters s"),
    @NamedQuery(name = "SystemParameters.findByParameterKey", query = "SELECT s FROM SystemParameters s WHERE s.parameterKey = :parameterKey"),
    @NamedQuery(name = "SystemParameters.findByParameterValue", query = "SELECT s FROM SystemParameters s WHERE s.parameterValue = :parameterValue"),
    @NamedQuery(name = "SystemParameters.findByUpdatedAt", query = "SELECT s FROM SystemParameters s WHERE s.updatedAt = :updatedAt")})
public class SystemParameters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "parameter_key")
    private String parameterKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "parameter_value")
    private String parameterValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @ManyToOne
    private Users updatedBy;

    public SystemParameters() {
    }

    public SystemParameters(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public SystemParameters(String parameterKey, String parameterValue, Date updatedAt) {
        this.parameterKey = parameterKey;
        this.parameterValue = parameterValue;
        this.updatedAt = updatedAt;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Users getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Users updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parameterKey != null ? parameterKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemParameters)) {
            return false;
        }
        SystemParameters other = (SystemParameters) object;
        if ((this.parameterKey == null && other.parameterKey != null) || (this.parameterKey != null && !this.parameterKey.equals(other.parameterKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.SystemParameters[ parameterKey=" + parameterKey + " ]";
    }
    
}

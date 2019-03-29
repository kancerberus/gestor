/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author juliano
 */
@Embeddable
public class RelCargosEstablecimiento implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_cargo")
    private Integer codCargo;

    public RelCargosEstablecimiento() {
    }

    public RelCargosEstablecimiento(Integer codigoEstablecimiento, Integer codCargo) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCargo = codCargo;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Integer getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(Integer codCargo) {
        this.codCargo = codCargo;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoEstablecimiento;
        hash += (codCargo != null ? codCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelCargosEstablecimiento)) {
            return false;
        }
        RelCargosEstablecimiento other = (RelCargosEstablecimiento) object;
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if ((this.codCargo == null && other.codCargo != null) || (this.codCargo != null && !this.codCargo.equals(other.codCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.RelCargosEstablecimiento[ codigoEstablecimiento=" + codigoEstablecimiento + ", codCargo=" + codCargo + " ]";
    }
    
}

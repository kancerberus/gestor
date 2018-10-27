/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Julian D Osorio G
 */
@Embeddable
public class PlanTituloPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private int codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_titulo")
    private int codTitulo;

    public PlanTituloPK() {
    }

    public PlanTituloPK(int codigoEstablecimiento, int codTitulo) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codTitulo = codTitulo;
    }

    public int getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(int codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public int getCodTitulo() {             
        return codTitulo;
    }

    public void setCodTitulo(int codTitulo) {
        this.codTitulo = codTitulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoEstablecimiento;
        hash += (int) codTitulo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTituloPK)) {
            return false;
        }
        PlanTituloPK other = (PlanTituloPK) object;
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codTitulo != other.codTitulo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanTituloPK[ codigoEstablecimiento=" + codigoEstablecimiento + ", codTitulo=" + codTitulo + " ]";
    }
    
}

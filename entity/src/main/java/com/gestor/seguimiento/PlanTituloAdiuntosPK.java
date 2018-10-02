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
public class PlanTituloAdiuntosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_titulo_adjunto")
    private int codTituloAdjunto;
    @Basic(optional = false)
    @Column(name = "cod_titulo")
    private int codTitulo;

    public PlanTituloAdiuntosPK() {
    }

    public PlanTituloAdiuntosPK(short codigoEstablecimiento, int codTituloAdjunto, int codTitulo) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codTituloAdjunto = codTituloAdjunto;
        this.codTitulo = codTitulo;
    }

    public short getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(short codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public int getCodTituloAdjunto() {
        return codTituloAdjunto;
    }

    public void setCodTituloAdjunto(int codTituloAdjunto) {
        this.codTituloAdjunto = codTituloAdjunto;
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
        hash += (int) codTituloAdjunto;
        hash += (int) codTitulo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTituloAdiuntosPK)) {
            return false;
        }
        PlanTituloAdiuntosPK other = (PlanTituloAdiuntosPK) object;
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codTituloAdjunto != other.codTituloAdjunto) {
            return false;
        }
        if (this.codTitulo != other.codTitulo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanTituloAdiuntosPK[ codigoEstablecimiento=" + codigoEstablecimiento + ", codTituloAdjunto=" + codTituloAdjunto + ", codTitulo=" + codTitulo + " ]";
    }
    
}

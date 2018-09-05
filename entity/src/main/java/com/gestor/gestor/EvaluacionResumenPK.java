/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author juliano
 */
@Embeddable
public class EvaluacionResumenPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private int codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_resumen")
    private int codResumen;

    public EvaluacionResumenPK() {
    }

    public EvaluacionResumenPK(Long codEvaluacion, int codigoEstablecimiento, int codResumen) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codResumen = codResumen;
    }

    public Long getCodEvaluacion() {
        return codEvaluacion;
    }

    public void setCodEvaluacion(Long codEvaluacion) {
        this.codEvaluacion = codEvaluacion;
    }

    public int getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(int codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public int getCodResumen() {
        return codResumen;
    }

    public void setCodResumen(int codResumen) {
        this.codResumen = codResumen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += (int) codResumen;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionResumenPK)) {
            return false;
        }
        EvaluacionResumenPK other = (EvaluacionResumenPK) object;
        if (this.codEvaluacion != other.codEvaluacion) {
            return false;
        }
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codResumen != other.codResumen) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionResumenPK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codResumen=" + codResumen + " ]";
    }

}

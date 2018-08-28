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
public class EvaluacionCapacitacionDetallePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private int codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_capacitacion")
    private Long codCapacitacion;
    @Basic(optional = false)
    @Column(name = "cod_capacitacion_detalle")
    private int codCapacitacionDetalle;

    public EvaluacionCapacitacionDetallePK() {
    }

    public EvaluacionCapacitacionDetallePK(Long codEvaluacion, int codigoEstablecimiento, Long codCapacitacion, int codCapacitacionDetalle) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCapacitacion = codCapacitacion;
        this.codCapacitacionDetalle = codCapacitacionDetalle;
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

    public Long getCodCapacitacion() {
        return codCapacitacion;
    }

    public void setCodCapacitacion(Long codCapacitacion) {
        this.codCapacitacion = codCapacitacion;
    }

    public int getCodCapacitacionDetalle() {
        return codCapacitacionDetalle;
    }

    public void setCodCapacitacionDetalle(int codCapacitacionDetalle) {
        this.codCapacitacionDetalle = codCapacitacionDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += codCapacitacion;
        hash += (int) codCapacitacionDetalle;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionCapacitacionDetallePK)) {
            return false;
        }
        EvaluacionCapacitacionDetallePK other = (EvaluacionCapacitacionDetallePK) object;
        if (this.codEvaluacion != other.codEvaluacion) {
            return false;
        }
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codCapacitacion != other.codCapacitacion) {
            return false;
        }
        if (this.codCapacitacionDetalle != other.codCapacitacionDetalle) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionCapacitacionDetallePK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codCapacitacion=" + codCapacitacion + ", codCapacitacionDetalle=" + codCapacitacionDetalle + " ]";
    }

}

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
public class EvaluacionPlanAccionDetalleNotasPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private int codEvaluacion;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_capacitacion")
    private int codCapacitacion;
    @Basic(optional = false)
    @Column(name = "cod_capacitacion_detalle")
    private int codCapacitacionDetalle;
    @Basic(optional = false)
    @Column(name = "cod_nota")
    private int codNota;

    public EvaluacionPlanAccionDetalleNotasPK() {
    }

    public EvaluacionPlanAccionDetalleNotasPK(int codEvaluacion, short codigoEstablecimiento, int codCapacitacion, int codCapacitacionDetalle, int codNota) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCapacitacion = codCapacitacion;
        this.codCapacitacionDetalle = codCapacitacionDetalle;
        this.codNota = codNota;
    }

    public int getCodEvaluacion() {
        return codEvaluacion;
    }

    public void setCodEvaluacion(int codEvaluacion) {
        this.codEvaluacion = codEvaluacion;
    }

    public short getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(short codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public int getCodCapacitacion() {
        return codCapacitacion;
    }

    public void setCodCapacitacion(int codCapacitacion) {
        this.codCapacitacion = codCapacitacion;
    }

    public int getCodCapacitacionDetalle() {
        return codCapacitacionDetalle;
    }

    public void setCodCapacitacionDetalle(int codCapacitacionDetalle) {
        this.codCapacitacionDetalle = codCapacitacionDetalle;
    }

    public int getCodNota() {
        return codNota;
    }

    public void setCodNota(int codNota) {
        this.codNota = codNota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += (int) codCapacitacion;
        hash += (int) codCapacitacionDetalle;
        hash += (int) codNota;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPlanAccionDetalleNotasPK)) {
            return false;
        }
        EvaluacionPlanAccionDetalleNotasPK other = (EvaluacionPlanAccionDetalleNotasPK) object;
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
        if (this.codNota != other.codNota) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionPlanAccionDetalleNotasPK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codCapacitacion=" + codCapacitacion + ", codCapacitacionDetalle=" + codCapacitacionDetalle + ", codNota=" + codNota + " ]";
    }
    
}

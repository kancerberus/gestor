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
public class EvaluacionCapacitacionDetalleNotasPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private int codEvaluacion;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_plan")
    private int codPlan;
    @Basic(optional = false)
    @Column(name = "cod_plan_detalle")
    private int codPlanDetalle;
    @Basic(optional = false)
    @Column(name = "cod_nota")
    private int codNota;

    public EvaluacionCapacitacionDetalleNotasPK() {
    }

    public EvaluacionCapacitacionDetalleNotasPK(int codEvaluacion, short codigoEstablecimiento, int codPlan, int codPlanDetalle, int codNota) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codPlan = codPlan;
        this.codPlanDetalle = codPlanDetalle;
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

    public int getCodPlan() {
        return codPlan;
    }

    public void setCodPlan(int codPlan) {
        this.codPlan = codPlan;
    }

    public int getCodPlanDetalle() {
        return codPlanDetalle;
    }

    public void setCodPlanDetalle(int codPlanDetalle) {
        this.codPlanDetalle = codPlanDetalle;
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
        hash += (int) codPlan;
        hash += (int) codPlanDetalle;
        hash += (int) codNota;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionCapacitacionDetalleNotasPK)) {
            return false;
        }
        EvaluacionCapacitacionDetalleNotasPK other = (EvaluacionCapacitacionDetalleNotasPK) object;
        if (this.codEvaluacion != other.codEvaluacion) {
            return false;
        }
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codPlan != other.codPlan) {
            return false;
        }
        if (this.codPlanDetalle != other.codPlanDetalle) {
            return false;
        }
        if (this.codNota != other.codNota) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionCapacitacionDetalleNotasPK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codPlan=" + codPlan + ", codPlanDetalle=" + codPlanDetalle + ", codNota=" + codNota + " ]";
    }
    
}

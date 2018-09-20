/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Julian D Osorio G
 */
@Embeddable
public class EvaluacionPlanAccionDetalleNotasPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private int codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_plan")
    private Long codPlan;
    @Basic(optional = false)
    @Column(name = "cod_plan_detalle")
    private Long codPlanDetalle;
    @Basic(optional = false)
    @Column(name = "cod_nota")
    private int codNota;

    public EvaluacionPlanAccionDetalleNotasPK() {
    }

    public EvaluacionPlanAccionDetalleNotasPK(Long codEvaluacion, int codigoEstablecimiento, Long codPlan, Long codPlanDetalle) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codPlan = codPlan;
        this.codPlanDetalle = codPlanDetalle;
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

    public Long getCodPlan() {
        return codPlan;
    }

    public void setCodPlan(Long codPlan) {
        this.codPlan = codPlan;
    }

    public Long getCodPlanDetalle() {
        return codPlanDetalle;
    }

    public void setCodPlanDetalle(Long codPlanDetalle) {
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
        hash += codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += codPlan;
        hash += codPlanDetalle;
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
        if (!Objects.equals(this.codEvaluacion, other.codEvaluacion)) {
            return false;
        }
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (!Objects.equals(this.codPlan, other.codPlan)) {
            return false;
        }
        if (!Objects.equals(this.codPlanDetalle, other.codPlanDetalle)) {
            return false;
        }
        return this.codNota == other.codNota;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionPlanAccionDetalleNotasPK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codPlan=" + codPlan + ", codPlanDetalle=" + codPlanDetalle + ", codNota=" + codNota + " ]";
    }

}

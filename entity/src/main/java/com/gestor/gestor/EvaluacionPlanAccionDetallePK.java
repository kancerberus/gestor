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
public class EvaluacionPlanAccionDetallePK implements Serializable {

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
    @Column(name = "cod_plan_trabajo")
    private Integer codPlantrabajo;

    public EvaluacionPlanAccionDetallePK() {
    }

    public EvaluacionPlanAccionDetallePK(Long codEvaluacion, int codigoEstablecimiento, Long codPlan, Long codPlanDetalle, int codPlantrabajo) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codPlan = codPlan;        
        this.codPlanDetalle = codPlanDetalle;
        this.codPlantrabajo = codPlantrabajo;
    }

    public EvaluacionPlanAccionDetallePK(Long codEvaluacion, int codigoEstablecimiento, Long codPlan) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codPlan = codPlan;                
    }

    public Integer getCodPlantrabajo() {
        return codPlantrabajo;
    }

    public void setCodPlantrabajo(Integer codPlantrabajo) {
        this.codPlantrabajo = codPlantrabajo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += codPlan;
        hash += codPlanDetalle;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPlanAccionDetallePK)) {
            return false;
        }
        EvaluacionPlanAccionDetallePK other = (EvaluacionPlanAccionDetallePK) object;
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
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionPlanAccionDetallePK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codPlan=" + codPlan + ", codPlanDetalle=" + codPlanDetalle + " ]";
    }

    public void setCodPlanDetalle(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

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
public class RelTipoPlanAccionEvaluacionPlanAccionDetalle implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_tipo_plan_accion")
    private Integer codTipoPlanAccion;        
    @Column(name = "cod_plan_detalle")
    private Integer codPlanDetalle;        
    @Column(name = "cod_plan_accion_detalle")
    private Integer codPlanAccionDetalle;        

    public RelTipoPlanAccionEvaluacionPlanAccionDetalle() {
    }

    public RelTipoPlanAccionEvaluacionPlanAccionDetalle(Integer codigoEstablecimiento, Integer codTipoPlanAccion, Integer codPlanDetalle, Integer codPlanAccionDetalle) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codTipoPlanAccion = codTipoPlanAccion;
        this.codPlanDetalle = codPlanDetalle;
        this.codPlanAccionDetalle = codPlanAccionDetalle;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Integer getCodTipoPlanAccion() {
        return codTipoPlanAccion;
    }

    public void setCodTipoPlanAccion(Integer codTipoPlanAccion) {
        this.codTipoPlanAccion = codTipoPlanAccion;
    }

    public Integer getCodPlanDetalle() {
        return codPlanDetalle;
    }

    public void setCodPlanDetalle(Integer codPlanDetalle) {
        this.codPlanDetalle = codPlanDetalle;
    }

    public Integer getCodPlanAccionDetalle() {
        return codPlanAccionDetalle;
    }

    public void setCodPlanAccionDetalle(Integer codPlanAccionDetalle) {
        this.codPlanAccionDetalle = codPlanAccionDetalle;
    }   
    
}

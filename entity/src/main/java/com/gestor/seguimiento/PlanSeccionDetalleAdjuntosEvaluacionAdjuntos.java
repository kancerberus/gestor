/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Julian D Osorio G
 */
@Entity
@Table(name = "plan_seccion_detalle_adjuntos_evaluacion_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionDetalleAdjuntosEvaluacionAdjuntos.findAll", query = "SELECT p FROM PlanSeccionDetalleAdjuntosEvaluacionAdjuntos p")})
public class PlanSeccionDetalleAdjuntosEvaluacionAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionDetalleAdjuntosEvaluacionAdjuntosPK planSeccionDetalleAdjuntosEvaluacionAdjuntosPK;

    public PlanSeccionDetalleAdjuntosEvaluacionAdjuntos() {
    }

    public PlanSeccionDetalleAdjuntosEvaluacionAdjuntos(PlanSeccionDetalleAdjuntosEvaluacionAdjuntosPK planSeccionDetalleAdjuntosEvaluacionAdjuntosPK) {
        this.planSeccionDetalleAdjuntosEvaluacionAdjuntosPK = planSeccionDetalleAdjuntosEvaluacionAdjuntosPK;
    }

    public PlanSeccionDetalleAdjuntosEvaluacionAdjuntos(int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleAdjuntos, int codEvaluacion, short codigoEstablecimiento, String codCiclo, int codDetalle, int codItem, int codAdjunto) {
        this.planSeccionDetalleAdjuntosEvaluacionAdjuntosPK = new PlanSeccionDetalleAdjuntosEvaluacionAdjuntosPK(codTitulo, codSeccion, codSeccionDetalle, codSeccionDetalleAdjuntos, codEvaluacion, codigoEstablecimiento, codCiclo, codDetalle, codItem, codAdjunto);
    }

    public PlanSeccionDetalleAdjuntosEvaluacionAdjuntosPK getPlanSeccionDetalleAdjuntosEvaluacionAdjuntosPK() {
        return planSeccionDetalleAdjuntosEvaluacionAdjuntosPK;
    }

    public void setPlanSeccionDetalleAdjuntosEvaluacionAdjuntosPK(PlanSeccionDetalleAdjuntosEvaluacionAdjuntosPK planSeccionDetalleAdjuntosEvaluacionAdjuntosPK) {
        this.planSeccionDetalleAdjuntosEvaluacionAdjuntosPK = planSeccionDetalleAdjuntosEvaluacionAdjuntosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionDetalleAdjuntosEvaluacionAdjuntosPK != null ? planSeccionDetalleAdjuntosEvaluacionAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleAdjuntosEvaluacionAdjuntos)) {
            return false;
        }
        PlanSeccionDetalleAdjuntosEvaluacionAdjuntos other = (PlanSeccionDetalleAdjuntosEvaluacionAdjuntos) object;
        if ((this.planSeccionDetalleAdjuntosEvaluacionAdjuntosPK == null && other.planSeccionDetalleAdjuntosEvaluacionAdjuntosPK != null) || (this.planSeccionDetalleAdjuntosEvaluacionAdjuntosPK != null && !this.planSeccionDetalleAdjuntosEvaluacionAdjuntosPK.equals(other.planSeccionDetalleAdjuntosEvaluacionAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleAdjuntosEvaluacionAdjuntos[ planSeccionDetalleAdjuntosEvaluacionAdjuntosPK=" + planSeccionDetalleAdjuntosEvaluacionAdjuntosPK + " ]";
    }
    
}

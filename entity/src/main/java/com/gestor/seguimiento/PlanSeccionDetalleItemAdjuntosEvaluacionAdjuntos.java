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
@Table(name = "plan_seccion_detalle_item_adjuntos_evaluacion_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos.findAll", query = "SELECT p FROM PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos p")})
public class PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK;

    public PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos() {
    }

    public PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos(PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK) {
        this.planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK = planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK;
    }

    public PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos(int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem, int codSeccionDetalleItemAdjuntos, int codEvaluacion, short codigoEstablecimiento, String codCiclo, int codDetalle, int codItem, int codAdjunto) {
        this.planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK = new PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK(codTitulo, codSeccion, codSeccionDetalle, codSeccionDetalleItem, codSeccionDetalleItemAdjuntos, codEvaluacion, codigoEstablecimiento, codCiclo, codDetalle, codItem, codAdjunto);
    }

    public PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK getPlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK() {
        return planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK;
    }

    public void setPlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK(PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK) {
        this.planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK = planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK != null ? planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos)) {
            return false;
        }
        PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos other = (PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos) object;
        if ((this.planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK == null && other.planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK != null) || (this.planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK != null && !this.planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK.equals(other.planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos[ planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK=" + planSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK + " ]";
    }
    
}

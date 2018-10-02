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
@Table(name = "plan_maestro_plan_seccion_detalle_item_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanMaestroPlanSeccionDetalleItemAdjuntos.findAll", query = "SELECT p FROM PlanMaestroPlanSeccionDetalleItemAdjuntos p")})
public class PlanMaestroPlanSeccionDetalleItemAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanMaestroPlanSeccionDetalleItemAdjuntosPK planMaestroPlanSeccionDetalleItemAdjuntosPK;

    public PlanMaestroPlanSeccionDetalleItemAdjuntos() {
    }

    public PlanMaestroPlanSeccionDetalleItemAdjuntos(PlanMaestroPlanSeccionDetalleItemAdjuntosPK planMaestroPlanSeccionDetalleItemAdjuntosPK) {
        this.planMaestroPlanSeccionDetalleItemAdjuntosPK = planMaestroPlanSeccionDetalleItemAdjuntosPK;
    }

    public PlanMaestroPlanSeccionDetalleItemAdjuntos(int codEvaluacion, short codigoEstablecimiento, int codMaestro, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem, int codSeccionDetalleItemAdjuntos) {
        this.planMaestroPlanSeccionDetalleItemAdjuntosPK = new PlanMaestroPlanSeccionDetalleItemAdjuntosPK(codEvaluacion, codigoEstablecimiento, codMaestro, codTitulo, codSeccion, codSeccionDetalle, codSeccionDetalleItem, codSeccionDetalleItemAdjuntos);
    }

    public PlanMaestroPlanSeccionDetalleItemAdjuntosPK getPlanMaestroPlanSeccionDetalleItemAdjuntosPK() {
        return planMaestroPlanSeccionDetalleItemAdjuntosPK;
    }

    public void setPlanMaestroPlanSeccionDetalleItemAdjuntosPK(PlanMaestroPlanSeccionDetalleItemAdjuntosPK planMaestroPlanSeccionDetalleItemAdjuntosPK) {
        this.planMaestroPlanSeccionDetalleItemAdjuntosPK = planMaestroPlanSeccionDetalleItemAdjuntosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planMaestroPlanSeccionDetalleItemAdjuntosPK != null ? planMaestroPlanSeccionDetalleItemAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMaestroPlanSeccionDetalleItemAdjuntos)) {
            return false;
        }
        PlanMaestroPlanSeccionDetalleItemAdjuntos other = (PlanMaestroPlanSeccionDetalleItemAdjuntos) object;
        if ((this.planMaestroPlanSeccionDetalleItemAdjuntosPK == null && other.planMaestroPlanSeccionDetalleItemAdjuntosPK != null) || (this.planMaestroPlanSeccionDetalleItemAdjuntosPK != null && !this.planMaestroPlanSeccionDetalleItemAdjuntosPK.equals(other.planMaestroPlanSeccionDetalleItemAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanMaestroPlanSeccionDetalleItemAdjuntos[ planMaestroPlanSeccionDetalleItemAdjuntosPK=" + planMaestroPlanSeccionDetalleItemAdjuntosPK + " ]";
    }
    
}

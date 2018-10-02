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
@Table(name = "plan_maestro_plan_seccion_detalle_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanMaestroPlanSeccionDetalleAdjuntos.findAll", query = "SELECT p FROM PlanMaestroPlanSeccionDetalleAdjuntos p")})
public class PlanMaestroPlanSeccionDetalleAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanMaestroPlanSeccionDetalleAdjuntosPK planMaestroPlanSeccionDetalleAdjuntosPK;

    public PlanMaestroPlanSeccionDetalleAdjuntos() {
    }

    public PlanMaestroPlanSeccionDetalleAdjuntos(PlanMaestroPlanSeccionDetalleAdjuntosPK planMaestroPlanSeccionDetalleAdjuntosPK) {
        this.planMaestroPlanSeccionDetalleAdjuntosPK = planMaestroPlanSeccionDetalleAdjuntosPK;
    }

    public PlanMaestroPlanSeccionDetalleAdjuntos(int codEvaluacion, short codigoEstablecimiento, int codMaestro, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleAdjuntos) {
        this.planMaestroPlanSeccionDetalleAdjuntosPK = new PlanMaestroPlanSeccionDetalleAdjuntosPK(codEvaluacion, codigoEstablecimiento, codMaestro, codTitulo, codSeccion, codSeccionDetalle, codSeccionDetalleAdjuntos);
    }

    public PlanMaestroPlanSeccionDetalleAdjuntosPK getPlanMaestroPlanSeccionDetalleAdjuntosPK() {
        return planMaestroPlanSeccionDetalleAdjuntosPK;
    }

    public void setPlanMaestroPlanSeccionDetalleAdjuntosPK(PlanMaestroPlanSeccionDetalleAdjuntosPK planMaestroPlanSeccionDetalleAdjuntosPK) {
        this.planMaestroPlanSeccionDetalleAdjuntosPK = planMaestroPlanSeccionDetalleAdjuntosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planMaestroPlanSeccionDetalleAdjuntosPK != null ? planMaestroPlanSeccionDetalleAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMaestroPlanSeccionDetalleAdjuntos)) {
            return false;
        }
        PlanMaestroPlanSeccionDetalleAdjuntos other = (PlanMaestroPlanSeccionDetalleAdjuntos) object;
        if ((this.planMaestroPlanSeccionDetalleAdjuntosPK == null && other.planMaestroPlanSeccionDetalleAdjuntosPK != null) || (this.planMaestroPlanSeccionDetalleAdjuntosPK != null && !this.planMaestroPlanSeccionDetalleAdjuntosPK.equals(other.planMaestroPlanSeccionDetalleAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanMaestroPlanSeccionDetalleAdjuntos[ planMaestroPlanSeccionDetalleAdjuntosPK=" + planMaestroPlanSeccionDetalleAdjuntosPK + " ]";
    }
    
}

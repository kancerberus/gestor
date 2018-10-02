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
@Table(name = "plan_maestro_plan_seccion_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanMaestroPlanSeccionAdjuntos.findAll", query = "SELECT p FROM PlanMaestroPlanSeccionAdjuntos p")})
public class PlanMaestroPlanSeccionAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanMaestroPlanSeccionAdjuntosPK planMaestroPlanSeccionAdjuntosPK;

    public PlanMaestroPlanSeccionAdjuntos() {
    }

    public PlanMaestroPlanSeccionAdjuntos(PlanMaestroPlanSeccionAdjuntosPK planMaestroPlanSeccionAdjuntosPK) {
        this.planMaestroPlanSeccionAdjuntosPK = planMaestroPlanSeccionAdjuntosPK;
    }

    public PlanMaestroPlanSeccionAdjuntos(int codEvaluacion, short codigoEstablecimiento, int codMaestro, int codTitulo, int codSeccion, int codSeccionAdjunto) {
        this.planMaestroPlanSeccionAdjuntosPK = new PlanMaestroPlanSeccionAdjuntosPK(codEvaluacion, codigoEstablecimiento, codMaestro, codTitulo, codSeccion, codSeccionAdjunto);
    }

    public PlanMaestroPlanSeccionAdjuntosPK getPlanMaestroPlanSeccionAdjuntosPK() {
        return planMaestroPlanSeccionAdjuntosPK;
    }

    public void setPlanMaestroPlanSeccionAdjuntosPK(PlanMaestroPlanSeccionAdjuntosPK planMaestroPlanSeccionAdjuntosPK) {
        this.planMaestroPlanSeccionAdjuntosPK = planMaestroPlanSeccionAdjuntosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planMaestroPlanSeccionAdjuntosPK != null ? planMaestroPlanSeccionAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMaestroPlanSeccionAdjuntos)) {
            return false;
        }
        PlanMaestroPlanSeccionAdjuntos other = (PlanMaestroPlanSeccionAdjuntos) object;
        if ((this.planMaestroPlanSeccionAdjuntosPK == null && other.planMaestroPlanSeccionAdjuntosPK != null) || (this.planMaestroPlanSeccionAdjuntosPK != null && !this.planMaestroPlanSeccionAdjuntosPK.equals(other.planMaestroPlanSeccionAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanMaestroPlanSeccionAdjuntos[ planMaestroPlanSeccionAdjuntosPK=" + planMaestroPlanSeccionAdjuntosPK + " ]";
    }
    
}

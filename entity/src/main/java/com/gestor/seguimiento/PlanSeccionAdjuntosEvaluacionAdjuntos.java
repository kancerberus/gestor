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
@Table(name = "plan_seccion_adjuntos_evaluacion_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionAdjuntosEvaluacionAdjuntos.findAll", query = "SELECT p FROM PlanSeccionAdjuntosEvaluacionAdjuntos p")})
public class PlanSeccionAdjuntosEvaluacionAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionAdjuntosEvaluacionAdjuntosPK planSeccionAdjuntosEvaluacionAdjuntosPK;

    public PlanSeccionAdjuntosEvaluacionAdjuntos() {
    }

    public PlanSeccionAdjuntosEvaluacionAdjuntos(PlanSeccionAdjuntosEvaluacionAdjuntosPK planSeccionAdjuntosEvaluacionAdjuntosPK) {
        this.planSeccionAdjuntosEvaluacionAdjuntosPK = planSeccionAdjuntosEvaluacionAdjuntosPK;
    }

    public PlanSeccionAdjuntosEvaluacionAdjuntos(int codTitulo, int codSeccionAdjunto, int codSeccion, int codEvaluacion, short codigoEstablecimiento, String codCiclo, int codDetalle, int codItem, int codAdjunto) {
        this.planSeccionAdjuntosEvaluacionAdjuntosPK = new PlanSeccionAdjuntosEvaluacionAdjuntosPK(codTitulo, codSeccionAdjunto, codSeccion, codEvaluacion, codigoEstablecimiento, codCiclo, codDetalle, codItem, codAdjunto);
    }

    public PlanSeccionAdjuntosEvaluacionAdjuntosPK getPlanSeccionAdjuntosEvaluacionAdjuntosPK() {
        return planSeccionAdjuntosEvaluacionAdjuntosPK;
    }

    public void setPlanSeccionAdjuntosEvaluacionAdjuntosPK(PlanSeccionAdjuntosEvaluacionAdjuntosPK planSeccionAdjuntosEvaluacionAdjuntosPK) {
        this.planSeccionAdjuntosEvaluacionAdjuntosPK = planSeccionAdjuntosEvaluacionAdjuntosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionAdjuntosEvaluacionAdjuntosPK != null ? planSeccionAdjuntosEvaluacionAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionAdjuntosEvaluacionAdjuntos)) {
            return false;
        }
        PlanSeccionAdjuntosEvaluacionAdjuntos other = (PlanSeccionAdjuntosEvaluacionAdjuntos) object;
        if ((this.planSeccionAdjuntosEvaluacionAdjuntosPK == null && other.planSeccionAdjuntosEvaluacionAdjuntosPK != null) || (this.planSeccionAdjuntosEvaluacionAdjuntosPK != null && !this.planSeccionAdjuntosEvaluacionAdjuntosPK.equals(other.planSeccionAdjuntosEvaluacionAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionAdjuntosEvaluacionAdjuntos[ planSeccionAdjuntosEvaluacionAdjuntosPK=" + planSeccionAdjuntosEvaluacionAdjuntosPK + " ]";
    }
    
}

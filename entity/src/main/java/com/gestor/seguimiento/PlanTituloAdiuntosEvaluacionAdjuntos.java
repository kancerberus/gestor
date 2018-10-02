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
@Table(name = "plan_titulo_adiuntos_evaluacion_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanTituloAdiuntosEvaluacionAdjuntos.findAll", query = "SELECT p FROM PlanTituloAdiuntosEvaluacionAdjuntos p")})
public class PlanTituloAdiuntosEvaluacionAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanTituloAdiuntosEvaluacionAdjuntosPK planTituloAdiuntosEvaluacionAdjuntosPK;

    public PlanTituloAdiuntosEvaluacionAdjuntos() {
    }

    public PlanTituloAdiuntosEvaluacionAdjuntos(PlanTituloAdiuntosEvaluacionAdjuntosPK planTituloAdiuntosEvaluacionAdjuntosPK) {
        this.planTituloAdiuntosEvaluacionAdjuntosPK = planTituloAdiuntosEvaluacionAdjuntosPK;
    }

    public PlanTituloAdiuntosEvaluacionAdjuntos(int codTituloAdjunto, int codTitulo, int codEvaluacion, short codigoEstablecimiento, String codCiclo, int codSeccion, int codDetalle, int codItem, int codAdjunto) {
        this.planTituloAdiuntosEvaluacionAdjuntosPK = new PlanTituloAdiuntosEvaluacionAdjuntosPK(codTituloAdjunto, codTitulo, codEvaluacion, codigoEstablecimiento, codCiclo, codSeccion, codDetalle, codItem, codAdjunto);
    }

    public PlanTituloAdiuntosEvaluacionAdjuntosPK getPlanTituloAdiuntosEvaluacionAdjuntosPK() {
        return planTituloAdiuntosEvaluacionAdjuntosPK;
    }

    public void setPlanTituloAdiuntosEvaluacionAdjuntosPK(PlanTituloAdiuntosEvaluacionAdjuntosPK planTituloAdiuntosEvaluacionAdjuntosPK) {
        this.planTituloAdiuntosEvaluacionAdjuntosPK = planTituloAdiuntosEvaluacionAdjuntosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planTituloAdiuntosEvaluacionAdjuntosPK != null ? planTituloAdiuntosEvaluacionAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTituloAdiuntosEvaluacionAdjuntos)) {
            return false;
        }
        PlanTituloAdiuntosEvaluacionAdjuntos other = (PlanTituloAdiuntosEvaluacionAdjuntos) object;
        if ((this.planTituloAdiuntosEvaluacionAdjuntosPK == null && other.planTituloAdiuntosEvaluacionAdjuntosPK != null) || (this.planTituloAdiuntosEvaluacionAdjuntosPK != null && !this.planTituloAdiuntosEvaluacionAdjuntosPK.equals(other.planTituloAdiuntosEvaluacionAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanTituloAdiuntosEvaluacionAdjuntos[ planTituloAdiuntosEvaluacionAdjuntosPK=" + planTituloAdiuntosEvaluacionAdjuntosPK + " ]";
    }
    
}

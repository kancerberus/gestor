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
@Table(name = "plan_maestro_plan_titulo_adiuntos")
@NamedQueries({
    @NamedQuery(name = "PlanMaestroPlanTituloAdiuntos.findAll", query = "SELECT p FROM PlanMaestroPlanTituloAdiuntos p")})
public class PlanMaestroPlanTituloAdiuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanMaestroPlanTituloAdiuntosPK planMaestroPlanTituloAdiuntosPK;

    public PlanMaestroPlanTituloAdiuntos() {
    }

    public PlanMaestroPlanTituloAdiuntos(PlanMaestroPlanTituloAdiuntosPK planMaestroPlanTituloAdiuntosPK) {
        this.planMaestroPlanTituloAdiuntosPK = planMaestroPlanTituloAdiuntosPK;
    }

    public PlanMaestroPlanTituloAdiuntos(int codEvaluacion, short codigoEstablecimiento, int codMaestro, int codTituloAdjunto, int codTitulo) {
        this.planMaestroPlanTituloAdiuntosPK = new PlanMaestroPlanTituloAdiuntosPK(codEvaluacion, codigoEstablecimiento, codMaestro, codTituloAdjunto, codTitulo);
    }

    public PlanMaestroPlanTituloAdiuntosPK getPlanMaestroPlanTituloAdiuntosPK() {
        return planMaestroPlanTituloAdiuntosPK;
    }

    public void setPlanMaestroPlanTituloAdiuntosPK(PlanMaestroPlanTituloAdiuntosPK planMaestroPlanTituloAdiuntosPK) {
        this.planMaestroPlanTituloAdiuntosPK = planMaestroPlanTituloAdiuntosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planMaestroPlanTituloAdiuntosPK != null ? planMaestroPlanTituloAdiuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMaestroPlanTituloAdiuntos)) {
            return false;
        }
        PlanMaestroPlanTituloAdiuntos other = (PlanMaestroPlanTituloAdiuntos) object;
        if ((this.planMaestroPlanTituloAdiuntosPK == null && other.planMaestroPlanTituloAdiuntosPK != null) || (this.planMaestroPlanTituloAdiuntosPK != null && !this.planMaestroPlanTituloAdiuntosPK.equals(other.planMaestroPlanTituloAdiuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanMaestroPlanTituloAdiuntos[ planMaestroPlanTituloAdiuntosPK=" + planMaestroPlanTituloAdiuntosPK + " ]";
    }
    
}

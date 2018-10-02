/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "plan_titulo_texto")
@NamedQueries({
    @NamedQuery(name = "PlanTituloTexto.findAll", query = "SELECT p FROM PlanTituloTexto p")})
public class PlanTituloTexto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanTituloTextoPK planTituloTextoPK;
    @Column(name = "texto")
    private String texto;

    public PlanTituloTexto() {
    }

    public PlanTituloTexto(PlanTituloTextoPK planTituloTextoPK) {
        this.planTituloTextoPK = planTituloTextoPK;
    }

    public PlanTituloTexto(short codigoEstablecimiento, int codTitulo, int codTituloTexto) {
        this.planTituloTextoPK = new PlanTituloTextoPK(codigoEstablecimiento, codTitulo, codTituloTexto);
    }

    public PlanTituloTextoPK getPlanTituloTextoPK() {
        return planTituloTextoPK;
    }

    public void setPlanTituloTextoPK(PlanTituloTextoPK planTituloTextoPK) {
        this.planTituloTextoPK = planTituloTextoPK;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planTituloTextoPK != null ? planTituloTextoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTituloTexto)) {
            return false;
        }
        PlanTituloTexto other = (PlanTituloTexto) object;
        if ((this.planTituloTextoPK == null && other.planTituloTextoPK != null) || (this.planTituloTextoPK != null && !this.planTituloTextoPK.equals(other.planTituloTextoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanTituloTexto[ planTituloTextoPK=" + planTituloTextoPK + " ]";
    }
    
}

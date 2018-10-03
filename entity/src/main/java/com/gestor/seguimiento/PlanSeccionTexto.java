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
@Table(name = "plan_seccion_texto")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionTexto.findAll", query = "SELECT p FROM PlanSeccionTexto p")})
public class PlanSeccionTexto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionTextoPK planSeccionTextoPK;
    @Column(name = "texto")
    private String texto;

    public PlanSeccionTexto() {
    }

    public PlanSeccionTexto(PlanSeccionTextoPK planSeccionTextoPK) {
        this.planSeccionTextoPK = planSeccionTextoPK;
    }

    public PlanSeccionTexto(short codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionTexto) {
        this.planSeccionTextoPK = new PlanSeccionTextoPK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionTexto);
    }

    public PlanSeccionTexto(PlanSeccionTextoPK planSeccionTextoPK, String texto) {
        this.planSeccionTextoPK = planSeccionTextoPK;
        this.texto = texto;
    }
    
    

    public PlanSeccionTextoPK getPlanSeccionTextoPK() {
        return planSeccionTextoPK;
    }

    public void setPlanSeccionTextoPK(PlanSeccionTextoPK planSeccionTextoPK) {
        this.planSeccionTextoPK = planSeccionTextoPK;
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
        hash += (planSeccionTextoPK != null ? planSeccionTextoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionTexto)) {
            return false;
        }
        PlanSeccionTexto other = (PlanSeccionTexto) object;
        if ((this.planSeccionTextoPK == null && other.planSeccionTextoPK != null) || (this.planSeccionTextoPK != null && !this.planSeccionTextoPK.equals(other.planSeccionTextoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionTexto[ planSeccionTextoPK=" + planSeccionTextoPK + " ]";
    }
    
}

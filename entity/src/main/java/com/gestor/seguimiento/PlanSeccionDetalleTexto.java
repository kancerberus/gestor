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
@Table(name = "plan_seccion_detalle_texto")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionDetalleTexto.findAll", query = "SELECT p FROM PlanSeccionDetalleTexto p")})
public class PlanSeccionDetalleTexto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionDetalleTextoPK planSeccionDetalleTextoPK;
    @Column(name = "texto")
    private String texto;

    public PlanSeccionDetalleTexto() {
    }

    public PlanSeccionDetalleTexto(PlanSeccionDetalleTextoPK planSeccionDetalleTextoPK) {
        this.planSeccionDetalleTextoPK = planSeccionDetalleTextoPK;
    }

    public PlanSeccionDetalleTexto(PlanSeccionDetalleTextoPK planSeccionDetalleTextoPK, String texto) {
        this.planSeccionDetalleTextoPK = planSeccionDetalleTextoPK;
        this.texto = texto;
    }
    
    

    public PlanSeccionDetalleTexto(short codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleTexto) {
        this.planSeccionDetalleTextoPK = new PlanSeccionDetalleTextoPK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionDetalle, codSeccionDetalleTexto);
    }

    public PlanSeccionDetalleTextoPK getPlanSeccionDetalleTextoPK() {
        return planSeccionDetalleTextoPK;
    }

    public void setPlanSeccionDetalleTextoPK(PlanSeccionDetalleTextoPK planSeccionDetalleTextoPK) {
        this.planSeccionDetalleTextoPK = planSeccionDetalleTextoPK;
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
        hash += (planSeccionDetalleTextoPK != null ? planSeccionDetalleTextoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleTexto)) {
            return false;
        }
        PlanSeccionDetalleTexto other = (PlanSeccionDetalleTexto) object;
        if ((this.planSeccionDetalleTextoPK == null && other.planSeccionDetalleTextoPK != null) || (this.planSeccionDetalleTextoPK != null && !this.planSeccionDetalleTextoPK.equals(other.planSeccionDetalleTextoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleTexto[ planSeccionDetalleTextoPK=" + planSeccionDetalleTextoPK + " ]";
    }
    
}

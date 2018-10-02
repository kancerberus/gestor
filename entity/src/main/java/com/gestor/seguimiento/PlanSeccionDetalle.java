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
@Table(name = "plan_seccion_detalle")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionDetalle.findAll", query = "SELECT p FROM PlanSeccionDetalle p")})
public class PlanSeccionDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionDetallePK planSeccionDetallePK;
    @Column(name = "numeral")
    private String numeral;
    @Column(name = "nombre")
    private String nombre;

    public PlanSeccionDetalle() {
    }

    public PlanSeccionDetalle(PlanSeccionDetallePK planSeccionDetallePK) {
        this.planSeccionDetallePK = planSeccionDetallePK;
    }

    public PlanSeccionDetalle(short codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle) {
        this.planSeccionDetallePK = new PlanSeccionDetallePK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionDetalle);
    }

    public PlanSeccionDetallePK getPlanSeccionDetallePK() {
        return planSeccionDetallePK;
    }

    public void setPlanSeccionDetallePK(PlanSeccionDetallePK planSeccionDetallePK) {
        this.planSeccionDetallePK = planSeccionDetallePK;
    }

    public String getNumeral() {
        return numeral;
    }

    public void setNumeral(String numeral) {
        this.numeral = numeral;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionDetallePK != null ? planSeccionDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalle)) {
            return false;
        }
        PlanSeccionDetalle other = (PlanSeccionDetalle) object;
        if ((this.planSeccionDetallePK == null && other.planSeccionDetallePK != null) || (this.planSeccionDetallePK != null && !this.planSeccionDetallePK.equals(other.planSeccionDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalle[ planSeccionDetallePK=" + planSeccionDetallePK + " ]";
    }
    
}

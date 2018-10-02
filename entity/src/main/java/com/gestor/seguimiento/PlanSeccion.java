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
@Table(name = "plan_seccion")
@NamedQueries({
    @NamedQuery(name = "PlanSeccion.findAll", query = "SELECT p FROM PlanSeccion p")})
public class PlanSeccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionPK planSeccionPK;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "numeral")
    private String numeral;
    @Column(name = "imagen")
    private String imagen;

    public PlanSeccion() {
    }

    public PlanSeccion(PlanSeccionPK planSeccionPK) {
        this.planSeccionPK = planSeccionPK;
    }

    public PlanSeccion(short codigoEstablecimiento, int codTitulo, int codSeccion) {
        this.planSeccionPK = new PlanSeccionPK(codigoEstablecimiento, codTitulo, codSeccion);
    }

    public PlanSeccionPK getPlanSeccionPK() {
        return planSeccionPK;
    }

    public void setPlanSeccionPK(PlanSeccionPK planSeccionPK) {
        this.planSeccionPK = planSeccionPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeral() {
        return numeral;
    }

    public void setNumeral(String numeral) {
        this.numeral = numeral;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionPK != null ? planSeccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccion)) {
            return false;
        }
        PlanSeccion other = (PlanSeccion) object;
        if ((this.planSeccionPK == null && other.planSeccionPK != null) || (this.planSeccionPK != null && !this.planSeccionPK.equals(other.planSeccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccion[ planSeccionPK=" + planSeccionPK + " ]";
    }
    
}

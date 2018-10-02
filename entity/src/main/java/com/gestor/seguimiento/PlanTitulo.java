/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "plan_titulo")
@NamedQueries({
    @NamedQuery(name = "PlanTitulo.findAll", query = "SELECT p FROM PlanTitulo p")})
public class PlanTitulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanTituloPK planTituloPK;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "numeral")
    private String numeral;
    
    private List<PlanTituloAdiuntos> planTituloAdiuntosList;
    private List<PlanTituloTexto> planTituloTextoList;

    public PlanTitulo() {
    }

    public PlanTitulo(PlanTituloPK planTituloPK) {
        this.planTituloPK = planTituloPK;
    }

    public PlanTitulo(short codigoEstablecimiento, int codTitulo) {
        this.planTituloPK = new PlanTituloPK(codigoEstablecimiento, codTitulo);
    }

    public PlanTituloPK getPlanTituloPK() {
        return planTituloPK;
    }

    public void setPlanTituloPK(PlanTituloPK planTituloPK) {
        this.planTituloPK = planTituloPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planTituloPK != null ? planTituloPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTitulo)) {
            return false;
        }
        PlanTitulo other = (PlanTitulo) object;
        if ((this.planTituloPK == null && other.planTituloPK != null) || (this.planTituloPK != null && !this.planTituloPK.equals(other.planTituloPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanTitulo[ planTituloPK=" + planTituloPK + " ]";
    }

    /**
     * @return the planTituloAdiuntosList
     */
    public List<PlanTituloAdiuntos> getPlanTituloAdiuntosList() {
        return planTituloAdiuntosList;
    }

    /**
     * @param planTituloAdiuntosList the planTituloAdiuntosList to set
     */
    public void setPlanTituloAdiuntosList(List<PlanTituloAdiuntos> planTituloAdiuntosList) {
        this.planTituloAdiuntosList = planTituloAdiuntosList;
    }

    /**
     * @return the planTituloTextoList
     */
    public List<PlanTituloTexto> getPlanTituloTextoList() {
        return planTituloTextoList;
    }

    /**
     * @param planTituloTextoList the planTituloTextoList to set
     */
    public void setPlanTituloTextoList(List<PlanTituloTexto> planTituloTextoList) {
        this.planTituloTextoList = planTituloTextoList;
    }
    
}

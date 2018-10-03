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
@Table(name = "plan_seccion_matriz")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionMatriz.findAll", query = "SELECT p FROM PlanSeccionMatriz p")})
public class PlanSeccionMatriz implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionMatrizPK planSeccionMatrizPK;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    
    private List<PlanSeccionMatrizDetalle> planSeccionMatrizDetalleList;

    public PlanSeccionMatriz() {
    }

    public PlanSeccionMatriz(PlanSeccionMatrizPK planSeccionMatrizPK) {
        this.planSeccionMatrizPK = planSeccionMatrizPK;
    }

    public PlanSeccionMatriz(int codigoEstablecimiento, int codTitulo, int codSeccion, Long codSeccionMatriz) {
        this.planSeccionMatrizPK = new PlanSeccionMatrizPK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionMatriz);
    }

    public PlanSeccionMatrizPK getPlanSeccionMatrizPK() {
        return planSeccionMatrizPK;
    }

    public PlanSeccionMatriz(PlanSeccionMatrizPK planSeccionMatrizPK, String titulo, String descripcion) {
        this.planSeccionMatrizPK = planSeccionMatrizPK;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
    
    

    public void setPlanSeccionMatrizPK(PlanSeccionMatrizPK planSeccionMatrizPK) {
        this.planSeccionMatrizPK = planSeccionMatrizPK;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionMatrizPK != null ? planSeccionMatrizPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionMatriz)) {
            return false;
        }
        PlanSeccionMatriz other = (PlanSeccionMatriz) object;
        if ((this.planSeccionMatrizPK == null && other.planSeccionMatrizPK != null) || (this.planSeccionMatrizPK != null && !this.planSeccionMatrizPK.equals(other.planSeccionMatrizPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionMatriz[ planSeccionMatrizPK=" + planSeccionMatrizPK + " ]";
    }

    /**
     * @return the planSeccionMatrizDetalleList
     */
    public List<PlanSeccionMatrizDetalle> getPlanSeccionMatrizDetalleList() {
        return planSeccionMatrizDetalleList;
    }

    /**
     * @param planSeccionMatrizDetalleList the planSeccionMatrizDetalleList to set
     */
    public void setPlanSeccionMatrizDetalleList(List<PlanSeccionMatrizDetalle> planSeccionMatrizDetalleList) {
        this.planSeccionMatrizDetalleList = planSeccionMatrizDetalleList;
    }
    
}

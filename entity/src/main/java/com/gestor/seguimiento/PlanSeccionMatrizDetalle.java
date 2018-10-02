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
@Table(name = "plan_seccion_matriz_detalle")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionMatrizDetalle.findAll", query = "SELECT p FROM PlanSeccionMatrizDetalle p")})
public class PlanSeccionMatrizDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionMatrizDetallePK planSeccionMatrizDetallePK;
    @Column(name = "directriz")
    private String directriz;
    @Column(name = "objetivos")
    private String objetivos;
    @Column(name = "metas")
    private String metas;
    @Column(name = "indicadores")
    private String indicadores;
    @Column(name = "modalidad")
    private String modalidad;
    @Column(name = "horarios")
    private String horarios;

    public PlanSeccionMatrizDetalle() {
    }

    public PlanSeccionMatrizDetalle(PlanSeccionMatrizDetallePK planSeccionMatrizDetallePK) {
        this.planSeccionMatrizDetallePK = planSeccionMatrizDetallePK;
    }

    public PlanSeccionMatrizDetalle(short codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionMatriz, int codSeccionMatrizDetalle) {
        this.planSeccionMatrizDetallePK = new PlanSeccionMatrizDetallePK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionMatriz, codSeccionMatrizDetalle);
    }

    public PlanSeccionMatrizDetallePK getPlanSeccionMatrizDetallePK() {
        return planSeccionMatrizDetallePK;
    }

    public void setPlanSeccionMatrizDetallePK(PlanSeccionMatrizDetallePK planSeccionMatrizDetallePK) {
        this.planSeccionMatrizDetallePK = planSeccionMatrizDetallePK;
    }

    public String getDirectriz() {
        return directriz;
    }

    public void setDirectriz(String directriz) {
        this.directriz = directriz;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getMetas() {
        return metas;
    }

    public void setMetas(String metas) {
        this.metas = metas;
    }

    public String getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(String indicadores) {
        this.indicadores = indicadores;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionMatrizDetallePK != null ? planSeccionMatrizDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionMatrizDetalle)) {
            return false;
        }
        PlanSeccionMatrizDetalle other = (PlanSeccionMatrizDetalle) object;
        if ((this.planSeccionMatrizDetallePK == null && other.planSeccionMatrizDetallePK != null) || (this.planSeccionMatrizDetallePK != null && !this.planSeccionMatrizDetallePK.equals(other.planSeccionMatrizDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionMatrizDetalle[ planSeccionMatrizDetallePK=" + planSeccionMatrizDetallePK + " ]";
    }
    
}

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
@Table(name = "plan_seccion_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionAdjuntos.findAll", query = "SELECT p FROM PlanSeccionAdjuntos p")})
public class PlanSeccionAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionAdjuntosPK planSeccionAdjuntosPK;
    @Column(name = "cod_categoria")
    private Integer codCategoria;
    @Column(name = "cod_categoria_tipo")
    private Integer codCategoriaTipo;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "documento")
    private String documento;

    public PlanSeccionAdjuntos() {
    }

    public PlanSeccionAdjuntos(PlanSeccionAdjuntosPK planSeccionAdjuntosPK) {
        this.planSeccionAdjuntosPK = planSeccionAdjuntosPK;
    }

    public PlanSeccionAdjuntos(short codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionAdjunto) {
        this.planSeccionAdjuntosPK = new PlanSeccionAdjuntosPK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionAdjunto);
    }

    public PlanSeccionAdjuntosPK getPlanSeccionAdjuntosPK() {
        return planSeccionAdjuntosPK;
    }

    public void setPlanSeccionAdjuntosPK(PlanSeccionAdjuntosPK planSeccionAdjuntosPK) {
        this.planSeccionAdjuntosPK = planSeccionAdjuntosPK;
    }

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Integer getCodCategoriaTipo() {
        return codCategoriaTipo;
    }

    public void setCodCategoriaTipo(Integer codCategoriaTipo) {
        this.codCategoriaTipo = codCategoriaTipo;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionAdjuntosPK != null ? planSeccionAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionAdjuntos)) {
            return false;
        }
        PlanSeccionAdjuntos other = (PlanSeccionAdjuntos) object;
        if ((this.planSeccionAdjuntosPK == null && other.planSeccionAdjuntosPK != null) || (this.planSeccionAdjuntosPK != null && !this.planSeccionAdjuntosPK.equals(other.planSeccionAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionAdjuntos[ planSeccionAdjuntosPK=" + planSeccionAdjuntosPK + " ]";
    }

}

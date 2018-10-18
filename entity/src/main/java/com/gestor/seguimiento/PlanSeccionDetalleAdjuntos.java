/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.publico.EvaluacionAdjuntos;
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
@Table(name = "plan_seccion_detalle_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionDetalleAdjuntos.findAll", query = "SELECT p FROM PlanSeccionDetalleAdjuntos p")})
public class PlanSeccionDetalleAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionDetalleAdjuntosPK planSeccionDetalleAdjuntosPK;
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
    
    private EvaluacionAdjuntos evaluacionAdjuntos;

    public PlanSeccionDetalleAdjuntos() {
    }

    public PlanSeccionDetalleAdjuntos(PlanSeccionDetalleAdjuntosPK planSeccionDetalleAdjuntosPK, Integer codCategoria, Integer codCategoriaTipo, String titulo, String descripcion, String documento) {
        this.planSeccionDetalleAdjuntosPK = planSeccionDetalleAdjuntosPK;
        this.codCategoria = codCategoria;
        this.codCategoriaTipo = codCategoriaTipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.documento = documento;
    }
    
    

    public PlanSeccionDetalleAdjuntos(PlanSeccionDetalleAdjuntosPK planSeccionDetalleAdjuntosPK) {
        this.planSeccionDetalleAdjuntosPK = planSeccionDetalleAdjuntosPK;
    }

    public PlanSeccionDetalleAdjuntos(short codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleAdjuntos) {
        this.planSeccionDetalleAdjuntosPK = new PlanSeccionDetalleAdjuntosPK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionDetalle, codSeccionDetalleAdjuntos);
    }

    public PlanSeccionDetalleAdjuntosPK getPlanSeccionDetalleAdjuntosPK() {
        return planSeccionDetalleAdjuntosPK;
    }

    public void setPlanSeccionDetalleAdjuntosPK(PlanSeccionDetalleAdjuntosPK planSeccionDetalleAdjuntosPK) {
        this.planSeccionDetalleAdjuntosPK = planSeccionDetalleAdjuntosPK;
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
        hash += (planSeccionDetalleAdjuntosPK != null ? planSeccionDetalleAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleAdjuntos)) {
            return false;
        }
        PlanSeccionDetalleAdjuntos other = (PlanSeccionDetalleAdjuntos) object;
        if ((this.planSeccionDetalleAdjuntosPK == null && other.planSeccionDetalleAdjuntosPK != null) || (this.planSeccionDetalleAdjuntosPK != null && !this.planSeccionDetalleAdjuntosPK.equals(other.planSeccionDetalleAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleAdjuntos[ planSeccionDetalleAdjuntosPK=" + planSeccionDetalleAdjuntosPK + " ]";
    }

    /**
     * @return the evaluacionAdjuntos
     */
    public EvaluacionAdjuntos getEvaluacionAdjuntos() {
        return evaluacionAdjuntos;
    }

    /**
     * @param evaluacionAdjuntos the evaluacionAdjuntos to set
     */
    public void setEvaluacionAdjuntos(EvaluacionAdjuntos evaluacionAdjuntos) {
        this.evaluacionAdjuntos = evaluacionAdjuntos;
    }
    
}

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
@Table(name = "plan_seccion_detalle_item_adjuntos")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionDetalleItemAdjuntos.findAll", query = "SELECT p FROM PlanSeccionDetalleItemAdjuntos p")})
public class PlanSeccionDetalleItemAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionDetalleItemAdjuntosPK planSeccionDetalleItemAdjuntosPK;
    @Column(name = "cod_categoria")
    private Integer codCategoria;
    @Column(name = "cod_categoria_tipo")
    private Integer codCategoriaTipo;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "actividad")
    private String actividad;
    @Column(name = "descripcion_general")
    private String descripcionGeneral;
    @Column(name = "documento")
    private String documento;
    
    private EvaluacionAdjuntos evaluacionAdjuntos;

    public PlanSeccionDetalleItemAdjuntos() {
    }
    
    public PlanSeccionDetalleItemAdjuntos(PlanSeccionDetalleItemAdjuntosPK planSeccionDetalleItemAdjuntosPK, Integer codCategoria, Integer codCategoriaTipo, String titulo, String descripcion, String actividad, String descripciongeneral, String documento) {
        this.planSeccionDetalleItemAdjuntosPK = planSeccionDetalleItemAdjuntosPK;
        this.codCategoria = codCategoria;
        this.codCategoriaTipo = codCategoriaTipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.actividad = actividad ;
        this.descripcionGeneral = descripciongeneral ;        
        this.documento = documento;
    }

    public PlanSeccionDetalleItemAdjuntos(PlanSeccionDetalleItemAdjuntosPK planSeccionDetalleItemAdjuntosPK) {
        this.planSeccionDetalleItemAdjuntosPK = planSeccionDetalleItemAdjuntosPK;
    }

    public PlanSeccionDetalleItemAdjuntos(int codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem, int codSeccionDetalleItemAdjuntos) {
        this.planSeccionDetalleItemAdjuntosPK = new PlanSeccionDetalleItemAdjuntosPK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionDetalle, codSeccionDetalleItem, codSeccionDetalleItemAdjuntos);
    }

    public PlanSeccionDetalleItemAdjuntosPK getPlanSeccionDetalleItemAdjuntosPK() {
        return planSeccionDetalleItemAdjuntosPK;
    }
    

    public void setPlanSeccionDetalleItemAdjuntosPK(PlanSeccionDetalleItemAdjuntosPK planSeccionDetalleItemAdjuntosPK) {
        this.planSeccionDetalleItemAdjuntosPK = planSeccionDetalleItemAdjuntosPK;
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

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }

    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
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
        hash += (planSeccionDetalleItemAdjuntosPK != null ? planSeccionDetalleItemAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleItemAdjuntos)) {
            return false;
        }
        PlanSeccionDetalleItemAdjuntos other = (PlanSeccionDetalleItemAdjuntos) object;
        if ((this.planSeccionDetalleItemAdjuntosPK == null && other.planSeccionDetalleItemAdjuntosPK != null) || (this.planSeccionDetalleItemAdjuntosPK != null && !this.planSeccionDetalleItemAdjuntosPK.equals(other.planSeccionDetalleItemAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleItemAdjuntos[ planSeccionDetalleItemAdjuntosPK=" + planSeccionDetalleItemAdjuntosPK + " ]";
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

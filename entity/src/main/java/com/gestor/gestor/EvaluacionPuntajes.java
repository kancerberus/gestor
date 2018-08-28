/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "evaluacion_puntajes")
@NamedQueries({
    @NamedQuery(name = "EvaluacionPuntajes.findAll", query = "SELECT e FROM EvaluacionPuntajes e")})
public class EvaluacionPuntajes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionPuntajesPK evaluacionPuntajesPK;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "plan_accion")
    private Boolean planAccion;
    @Column(name = "capacitacion")
    private Boolean capacitacion;
    @Column(name = "califica")
    private Boolean califica;
    private Integer orden;
    @JoinTable(name = "evaluacion_puntaje_seccion_detalle_combos", joinColumns = {
        @JoinColumn(name = "cod_evaluacion", referencedColumnName = "cod_evaluacion"),
        @JoinColumn(name = "codigo_establecimiento", referencedColumnName = "codigo_establecimiento"),
        @JoinColumn(name = "cod_puntaje", referencedColumnName = "cod_puntaje")}, inverseJoinColumns = {
        @JoinColumn(name = "cod_ciclo", referencedColumnName = "cod_ciclo"),
        @JoinColumn(name = "cod_seccion", referencedColumnName = "cod_seccion"),
        @JoinColumn(name = "cod_detalle", referencedColumnName = "cod_detalle"),
        @JoinColumn(name = "cod_item", referencedColumnName = "cod_item")})
    @ManyToMany
    private List<SeccionDetalleItems> seccionDetalleItemsList;
    @JoinColumns({
        @JoinColumn(name = "cod_evaluacion", referencedColumnName = "cod_evaluacion", insertable = false, updatable = false),
        @JoinColumn(name = "codigo_establecimiento", referencedColumnName = "codigo_establecimiento", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Evaluacion evaluacion;
    @JoinColumns({
        @JoinColumn(name = "cod_puntaje", referencedColumnName = "cod_puntaje", insertable = false, updatable = false),
        @JoinColumn(name = "codigo_establecimiento", referencedColumnName = "codigo_establecimiento", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Puntajes puntajes;

    public EvaluacionPuntajes() {
    }

    public EvaluacionPuntajes(EvaluacionPuntajesPK evaluacionPuntajesPK, String descripcion, Boolean planAccion, Boolean capacitacion, Boolean califica, Integer orden) {
        this.evaluacionPuntajesPK = evaluacionPuntajesPK;
        this.descripcion = descripcion;
        this.planAccion = planAccion;
        this.capacitacion = capacitacion;
        this.califica = califica;
        this.orden = orden;
    }

    public EvaluacionPuntajes(EvaluacionPuntajesPK evaluacionPuntajesPK) {
        this.evaluacionPuntajesPK = evaluacionPuntajesPK;
    }

    public EvaluacionPuntajes(short codigoEstablecimiento, Long codEvaluacion, String codPuntaje) {
        this.evaluacionPuntajesPK = new EvaluacionPuntajesPK(codigoEstablecimiento, codEvaluacion, codPuntaje);
    }

    public EvaluacionPuntajesPK getEvaluacionPuntajesPK() {
        return evaluacionPuntajesPK;
    }

    public void setEvaluacionPuntajesPK(EvaluacionPuntajesPK evaluacionPuntajesPK) {
        this.evaluacionPuntajesPK = evaluacionPuntajesPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getPlanAccion() {
        return planAccion;
    }

    public void setPlanAccion(Boolean planAccion) {
        this.planAccion = planAccion;
    }

    public Boolean getCapacitacion() {
        return capacitacion;
    }

    public void setCapacitacion(Boolean capacitacion) {
        this.capacitacion = capacitacion;
    }

    public Boolean getCalifica() {
        return califica;
    }

    public void setCalifica(Boolean califica) {
        this.califica = califica;
    }

    public List<SeccionDetalleItems> getSeccionDetalleItemsList() {
        return seccionDetalleItemsList;
    }

    public void setSeccionDetalleItemsList(List<SeccionDetalleItems> seccionDetalleItemsList) {
        this.seccionDetalleItemsList = seccionDetalleItemsList;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Puntajes getPuntajes() {
        return puntajes;
    }

    public void setPuntajes(Puntajes puntajes) {
        this.puntajes = puntajes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionPuntajesPK != null ? evaluacionPuntajesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPuntajes)) {
            return false;
        }
        EvaluacionPuntajes other = (EvaluacionPuntajes) object;
        if ((this.evaluacionPuntajesPK == null && other.evaluacionPuntajesPK != null) || (this.evaluacionPuntajesPK != null && !this.evaluacionPuntajesPK.equals(other.evaluacionPuntajesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionPuntajes[ evaluacionPuntajesPK=" + evaluacionPuntajesPK + " ]";
    }

    /**
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

}

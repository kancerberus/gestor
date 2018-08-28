/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "puntajes")
@NamedQueries({
    @NamedQuery(name = "Puntajes.findAll", query = "SELECT p FROM Puntajes p")
})
public class Puntajes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PuntajesPK puntajesPK;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "plan_accion")
    private Boolean planAccion;
    @Column(name = "capacitacion")
    private Boolean capacitacion;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "califica")
    private Boolean califica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puntajes")
    private List<EvaluacionPuntajes> evaluacionPuntajesList;
    private Integer orden;

    public Puntajes() {
    }

    public Puntajes(PuntajesPK puntajesPK) {
        this.puntajesPK = puntajesPK;
    }

    public Puntajes(int codigoEstablecimiento, String codPuntaje) {
        this.puntajesPK = new PuntajesPK(codigoEstablecimiento, codPuntaje);
    }

    public Puntajes(PuntajesPK puntajesPK, String descripcion, Boolean planAccion, Boolean capacitacion, Boolean activo, Boolean califica, Integer orden) {
        this.puntajesPK = puntajesPK;
        this.descripcion = descripcion;
        this.planAccion = planAccion;
        this.capacitacion = capacitacion;
        this.activo = activo;
        this.califica = califica;
        this.orden = orden;
    }

    public PuntajesPK getPuntajesPK() {
        return puntajesPK;
    }

    public void setPuntajesPK(PuntajesPK puntajesPK) {
        this.puntajesPK = puntajesPK;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getCalifica() {
        return califica;
    }

    public void setCalifica(Boolean califica) {
        this.califica = califica;
    }

    public List<EvaluacionPuntajes> getEvaluacionPuntajesList() {
        return evaluacionPuntajesList;
    }

    public void setEvaluacionPuntajesList(List<EvaluacionPuntajes> evaluacionPuntajesList) {
        this.evaluacionPuntajesList = evaluacionPuntajesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (puntajesPK != null ? puntajesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Puntajes)) {
            return false;
        }
        Puntajes other = (Puntajes) object;
        if ((this.puntajesPK == null && other.puntajesPK != null) || (this.puntajesPK != null && !this.puntajesPK.equals(other.puntajesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.Puntajes[ puntajesPK=" + puntajesPK + " ]";
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "evaluacion_plan_accion_detalle_notas")
@NamedQueries({
    @NamedQuery(name = "EvaluacionPlanAccionDetalleNotas.findAll", query = "SELECT e FROM EvaluacionPlanAccionDetalleNotas e")})
public class EvaluacionPlanAccionDetalleNotas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionPlanAccionDetalleNotasPK evaluacionPlanAccionDetalleNotasPK;
    @Column(name = "documento_usuario")
    private String documentoUsuario;
    @Column(name = "estado")
    private String estado;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public EvaluacionPlanAccionDetalleNotas() {
    }

    public EvaluacionPlanAccionDetalleNotas(EvaluacionPlanAccionDetalleNotasPK evaluacionPlanAccionDetalleNotasPK) {
        this.evaluacionPlanAccionDetalleNotasPK = evaluacionPlanAccionDetalleNotasPK;
    }

    public EvaluacionPlanAccionDetalleNotas(int codEvaluacion, short codigoEstablecimiento, int codCapacitacion, int codCapacitacionDetalle, int codNota) {
        this.evaluacionPlanAccionDetalleNotasPK = new EvaluacionPlanAccionDetalleNotasPK(codEvaluacion, codigoEstablecimiento, codCapacitacion, codCapacitacionDetalle, codNota);
    }

    public EvaluacionPlanAccionDetalleNotasPK getEvaluacionPlanAccionDetalleNotasPK() {
        return evaluacionPlanAccionDetalleNotasPK;
    }

    public void setEvaluacionPlanAccionDetalleNotasPK(EvaluacionPlanAccionDetalleNotasPK evaluacionPlanAccionDetalleNotasPK) {
        this.evaluacionPlanAccionDetalleNotasPK = evaluacionPlanAccionDetalleNotasPK;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionPlanAccionDetalleNotasPK != null ? evaluacionPlanAccionDetalleNotasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPlanAccionDetalleNotas)) {
            return false;
        }
        EvaluacionPlanAccionDetalleNotas other = (EvaluacionPlanAccionDetalleNotas) object;
        if ((this.evaluacionPlanAccionDetalleNotasPK == null && other.evaluacionPlanAccionDetalleNotasPK != null) || (this.evaluacionPlanAccionDetalleNotasPK != null && !this.evaluacionPlanAccionDetalleNotasPK.equals(other.evaluacionPlanAccionDetalleNotasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionPlanAccionDetalleNotas[ evaluacionPlanAccionDetalleNotasPK=" + evaluacionPlanAccionDetalleNotasPK + " ]";
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.publico.Usuarios;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
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
 * @author Julian D Osorio G
 */
@Entity
@Table(name = "evaluacion_capacitacion_detalle_notas")
@NamedQueries({
    @NamedQuery(name = "EvaluacionCapacitacionDetalleNotas.findAll", query = "SELECT e FROM EvaluacionCapacitacionDetalleNotas e")})
@ManagedBean
@SessionScoped
public class EvaluacionCapacitacionDetalleNotas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionCapacitacionDetalleNotasPK evaluacionCapacitacionDetalleNotasPK;
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
    private Usuarios usuarios;

    public EvaluacionCapacitacionDetalleNotas() {
    }

    public EvaluacionCapacitacionDetalleNotas(EvaluacionCapacitacionDetalleNotasPK evaluacionCapacitacionDetalleNotasPK) {
        this.evaluacionCapacitacionDetalleNotasPK = evaluacionCapacitacionDetalleNotasPK;
    }

    public EvaluacionCapacitacionDetalleNotas(Long codEvaluacion, int codigoEstablecimiento, Long codCapacitacion, Long codCapacitacionDetalle, Long codNota) {
        this.evaluacionCapacitacionDetalleNotasPK = new EvaluacionCapacitacionDetalleNotasPK(codEvaluacion, codigoEstablecimiento, codCapacitacion, codCapacitacionDetalle, codNota);
    }

    public EvaluacionCapacitacionDetalleNotas(EvaluacionCapacitacionDetalleNotasPK evaluacionCapacitacionDetalleNotasPK, String documentoUsuario, String estado, String nombre, String descripcion, Usuarios usuarios) {
        this.evaluacionCapacitacionDetalleNotasPK = evaluacionCapacitacionDetalleNotasPK;
        this.documentoUsuario = documentoUsuario;
        this.estado = estado;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.usuarios = usuarios;
    }

    public EvaluacionCapacitacionDetalleNotasPK getEvaluacionCapacitacionDetalleNotasPK() {
        return evaluacionCapacitacionDetalleNotasPK;
    }

    public void setEvaluacionCapacitacionDetalleNotasPK(EvaluacionCapacitacionDetalleNotasPK evaluacionCapacitacionDetalleNotasPK) {
        this.evaluacionCapacitacionDetalleNotasPK = evaluacionCapacitacionDetalleNotasPK;
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
        hash += (evaluacionCapacitacionDetalleNotasPK != null ? evaluacionCapacitacionDetalleNotasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionCapacitacionDetalleNotas)) {
            return false;
        }
        EvaluacionCapacitacionDetalleNotas other = (EvaluacionCapacitacionDetalleNotas) object;
        if ((this.evaluacionCapacitacionDetalleNotasPK == null && other.evaluacionCapacitacionDetalleNotasPK != null) || (this.evaluacionCapacitacionDetalleNotasPK != null && !this.evaluacionCapacitacionDetalleNotasPK.equals(other.evaluacionCapacitacionDetalleNotasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionCapacitacionDetalleNotas[ evaluacionCapacitacionDetalleNotasPK=" + evaluacionCapacitacionDetalleNotasPK + " ]";
    }

    /**
     * @return the usuarios
     */
    public Usuarios getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

}

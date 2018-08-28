/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "evaluacion_capacitacion")
@NamedQueries({
    @NamedQuery(name = "EvaluacionCapacitacion.findAll", query = "SELECT e FROM EvaluacionCapacitacion e")})
public class EvaluacionCapacitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionCapacitacionPK evaluacionCapacitacionPK;
    @Basic(optional = false)
    @Column(name = "documento_usuario")
    private String documentoUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "documento_usuario_modifica")
    private String documentoUsuarioModifica;
    @Column(name = "fecha_actualiza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualiza;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluacionCapacitacion")
    private List<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalleList;
    
    private EvaluacionCapacitacionDetalle evaluacionCapacitacionDetalle;

    public EvaluacionCapacitacion() {
    }

    public EvaluacionCapacitacion(EvaluacionCapacitacionPK evaluacionCapacitacionPK) {
        this.evaluacionCapacitacionPK = evaluacionCapacitacionPK;
    }

    public EvaluacionCapacitacion(EvaluacionCapacitacionPK evaluacionCapacitacionPK, String documentoUsuario, String estado) {
        this.evaluacionCapacitacionPK = evaluacionCapacitacionPK;
        this.documentoUsuario = documentoUsuario;
        this.estado = estado;
    }

    public EvaluacionCapacitacion(Long codEvaluacion, short codigoEstablecimiento, Long codCapacitacion) {
        this.evaluacionCapacitacionPK = new EvaluacionCapacitacionPK(codEvaluacion, codigoEstablecimiento, codCapacitacion);
    }

    public EvaluacionCapacitacionPK getEvaluacionCapacitacionPK() {
        return evaluacionCapacitacionPK;
    }

    public void setEvaluacionCapacitacionPK(EvaluacionCapacitacionPK evaluacionCapacitacionPK) {
        this.evaluacionCapacitacionPK = evaluacionCapacitacionPK;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDocumentoUsuarioModifica() {
        return documentoUsuarioModifica;
    }

    public void setDocumentoUsuarioModifica(String documentoUsuarioModifica) {
        this.documentoUsuarioModifica = documentoUsuarioModifica;
    }

    public Date getFechaActualiza() {
        return fechaActualiza;
    }

    public void setFechaActualiza(Date fechaActualiza) {
        this.fechaActualiza = fechaActualiza;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<EvaluacionCapacitacionDetalle> getEvaluacionCapacitacionDetalleList() {
        return evaluacionCapacitacionDetalleList;
    }

    public void setEvaluacionCapacitacionDetalleList(List<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalleList) {
        this.evaluacionCapacitacionDetalleList = evaluacionCapacitacionDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionCapacitacionPK != null ? evaluacionCapacitacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionCapacitacion)) {
            return false;
        }
        EvaluacionCapacitacion other = (EvaluacionCapacitacion) object;
        if ((this.evaluacionCapacitacionPK == null && other.evaluacionCapacitacionPK != null) || (this.evaluacionCapacitacionPK != null && !this.evaluacionCapacitacionPK.equals(other.evaluacionCapacitacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionCapacitacion[ evaluacionCapacitacionPK=" + evaluacionCapacitacionPK + " ]";
    }

    /**
     * @return the evaluacionCapacitacionDetalle
     */
    public EvaluacionCapacitacionDetalle getEvaluacionCapacitacionDetalle() {
        return evaluacionCapacitacionDetalle;
    }

    /**
     * @param evaluacionCapacitacionDetalle the evaluacionCapacitacionDetalle to set
     */
    public void setEvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetalle evaluacionCapacitacionDetalle) {
        this.evaluacionCapacitacionDetalle = evaluacionCapacitacionDetalle;
    }
    
}

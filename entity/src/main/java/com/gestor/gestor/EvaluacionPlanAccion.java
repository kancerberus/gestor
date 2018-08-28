/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
@Table(name = "evaluacion_plan_accion")
@NamedQueries({
    @NamedQuery(name = "EvaluacionPlanAccion.findAll", query = "SELECT e FROM EvaluacionPlanAccion e")
})
@ManagedBean
@SessionScoped
public class EvaluacionPlanAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionPlanAccionPK evaluacionPlanAccionPK;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluacionPlanAccion")
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalleList = new ArrayList<>();
    private EvaluacionPlanAccionDetalle evaluacionPlanAccionDetalle;

    public EvaluacionPlanAccion() {
    }

    public EvaluacionPlanAccion(EvaluacionPlanAccionPK evaluacionPlanAccionPK) {
        this.evaluacionPlanAccionPK = evaluacionPlanAccionPK;
    }

    public EvaluacionPlanAccion(EvaluacionPlanAccionPK evaluacionPlanAccionPK, String estado) {
        this.evaluacionPlanAccionPK = evaluacionPlanAccionPK;
        this.estado = estado;
    }

    public EvaluacionPlanAccion(EvaluacionPlanAccionPK evaluacionPlanAccionPK, String estado, String documentoUsuario, String documentoUsuarioModifica) {
        this.evaluacionPlanAccionPK = evaluacionPlanAccionPK;
        this.estado = estado;
        this.documentoUsuario = documentoUsuario;
        this.documentoUsuarioModifica = documentoUsuarioModifica;
    }

    public EvaluacionPlanAccion(Long codEvaluacion, int codigoEstablecimiento, Long codPlan) {
        this.evaluacionPlanAccionPK = new EvaluacionPlanAccionPK(codEvaluacion, codigoEstablecimiento, codPlan);
    }

    public EvaluacionPlanAccionPK getEvaluacionPlanAccionPK() {
        return evaluacionPlanAccionPK;
    }

    public void setEvaluacionPlanAccionPK(EvaluacionPlanAccionPK evaluacionPlanAccionPK) {
        this.evaluacionPlanAccionPK = evaluacionPlanAccionPK;
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

    public List<EvaluacionPlanAccionDetalle> getEvaluacionPlanAccionDetalleList() {
        return evaluacionPlanAccionDetalleList;
    }

    public void setEvaluacionPlanAccionDetalleList(List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalleList) {
        this.evaluacionPlanAccionDetalleList = evaluacionPlanAccionDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionPlanAccionPK != null ? evaluacionPlanAccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPlanAccion)) {
            return false;
        }
        EvaluacionPlanAccion other = (EvaluacionPlanAccion) object;
        if ((this.evaluacionPlanAccionPK == null && other.evaluacionPlanAccionPK != null) || (this.evaluacionPlanAccionPK != null && !this.evaluacionPlanAccionPK.equals(other.evaluacionPlanAccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionPlanAccion[ evaluacionPlanAccionPK=" + evaluacionPlanAccionPK + " ]";
    }

    /**
     * @return the evaluacionPlanAccionDetalle
     */
    public EvaluacionPlanAccionDetalle getEvaluacionPlanAccionDetalle() {
        return evaluacionPlanAccionDetalle;
    }

    /**
     * @param evaluacionPlanAccionDetalle the evaluacionPlanAccionDetalle to set
     */
    public void setEvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetalle evaluacionPlanAccionDetalle) {
        this.evaluacionPlanAccionDetalle = evaluacionPlanAccionDetalle;
    }

}

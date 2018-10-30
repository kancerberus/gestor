/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import com.gestor.gestor.AdjuntosCategoria;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Basic;
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
@Table(name = "evaluacion_adjuntos")
@NamedQueries({
    @NamedQuery(name = "EvaluacionAdjuntos.findAll", query = "SELECT e FROM EvaluacionAdjuntos e")
})
@ManagedBean
@SessionScoped
public class EvaluacionAdjuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionAdjuntosPK evaluacionAdjuntosPK;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "archivo")
    private String archivo;
    @Column(name = "extension")
    private String extension;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "documento_usuario")
    private String documentoUsuario;
    @Column(name = "estado")
    private String estado;
    
    private AdjuntosCategoria adjuntosCategoria = new AdjuntosCategoria();
    private Responsable responsable = new Responsable();

    private Date fechaActualiza;
    private Date fechaInicioVigencia;
    private Date fechaFinVigencia;
    private Integer mesesVigencia;
    private Integer version;
    private String cedula;

    public EvaluacionAdjuntos() {
        adjuntosCategoria = new AdjuntosCategoria();
    }

    public EvaluacionAdjuntos(EvaluacionAdjuntosPK evaluacionAdjuntosPK) {
        this.evaluacionAdjuntosPK = evaluacionAdjuntosPK;
    }

    public EvaluacionAdjuntos(EvaluacionAdjuntosPK evaluacionAdjuntosPK, String nombre, String archivo) {
        this.evaluacionAdjuntosPK = evaluacionAdjuntosPK;
        this.nombre = nombre;
        this.archivo = archivo;
    }

    public EvaluacionAdjuntos(Long codEvaluacion, int codigoEstablecimiento, String codCiclo, int codSeccion, int codDetalle, int codItem, Long codAdjunto) {
        this.evaluacionAdjuntosPK = new EvaluacionAdjuntosPK(codEvaluacion, codigoEstablecimiento, codCiclo, codSeccion, codDetalle, codItem, codAdjunto);
    }

    public EvaluacionAdjuntos(Long codEvaluacion, int codigoEstablecimiento, String codCiclo, int codSeccion, int codDetalle, int codItem) {
        this.evaluacionAdjuntosPK = new EvaluacionAdjuntosPK(codEvaluacion, codigoEstablecimiento, codCiclo, codSeccion, codDetalle, codItem);
    }

    public EvaluacionAdjuntosPK getEvaluacionAdjuntosPK() {
        return evaluacionAdjuntosPK;
    }

    public void setEvaluacionAdjuntosPK(EvaluacionAdjuntosPK evaluacionAdjuntosPK) {
        this.evaluacionAdjuntosPK = evaluacionAdjuntosPK;
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

    public String getArchivo() {
        return archivo;
    }

    public String getArchivoSimple() {
        if (archivo != null && evaluacionAdjuntosPK != null && evaluacionAdjuntosPK.getCodAdjunto() != null && archivo.length() > 0) {
            return archivo.replace(evaluacionAdjuntosPK.getCodAdjunto() + "-", "");
        }
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionAdjuntosPK != null ? evaluacionAdjuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionAdjuntos)) {
            return false;
        }
        EvaluacionAdjuntos other = (EvaluacionAdjuntos) object;
        if ((this.evaluacionAdjuntosPK == null && other.evaluacionAdjuntosPK != null) || (this.evaluacionAdjuntosPK != null && !this.evaluacionAdjuntosPK.equals(other.evaluacionAdjuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.EvaluacionAdjuntos[ evaluacionAdjuntosPK=" + evaluacionAdjuntosPK + " ]";
    }

    /**
     * @return the fechaActualiza
     */
    public Date getFechaActualiza() {
        return fechaActualiza;
    }

    /**
     * @param fechaActualiza the fechaActualiza to set
     */
    public void setFechaActualiza(Date fechaActualiza) {
        this.fechaActualiza = fechaActualiza;
    }

    /**
     * @return the fechaInicioVigencia
     */
    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    /**
     * @param fechaInicioVigencia the fechaInicioVigencia to set
     */
    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    /**
     * @return the fechaFinVigencia
     */
    public Date getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    /**
     * @param fechaFinVigencia the fechaFinVigencia to set
     */
    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    /**
     * @return the mesesVigencia
     */
    public Integer getMesesVigencia() {
        return mesesVigencia;
    }

    /**
     * @param mesesVigencia the mesesVigencia to set
     */
    public void setMesesVigencia(Integer mesesVigencia) {
        this.mesesVigencia = mesesVigencia;
    }

    /**
     * @return the adjuntosCategoria
     */
    public AdjuntosCategoria getAdjuntosCategoria() {
        return adjuntosCategoria;
    }

    /**
     * @param adjuntosCategoria the adjuntosCategoria to set
     */
    public void setAdjuntosCategoria(AdjuntosCategoria adjuntosCategoria) {
        this.adjuntosCategoria = adjuntosCategoria;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return the responsable
     */
    public Responsable getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.entity.App;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Usuarios;
import java.io.Serializable;
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
@Table(name = "evaluacion")
@NamedQueries({
    @NamedQuery(name = "Evaluacion.findAll", query = "SELECT e FROM Evaluacion e")})
@ManagedBean
@SessionScoped
public class Evaluacion implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    public static String EVALUACION_CONDICION_DOCUMENTO_USUARIO = "E.DOCUMENTO_USUARIO IN (?)";
    public static String EVALUACION_CONDICION_ESTADO = "E.ESTADO IN (?)";
    public static String EVALUACION_CONDICION_COD_ESTABLECIMIENTO = "E.codigo_establecimiento IN (?)";
    public static String EVALUACION_CONDICION_COD_EVALUACION = "E.cod_evaluacion IN (?)";
    public static String EVALUACION_CONDICION_FECHA_REGISTRO_GTE = "E.fecha::DATE >= ?";
    public static String EVALUACION_CONDICION_FECHA_REGISTRO_LTE = "E.fecha::DATE <= ?";

    @EmbeddedId
    protected EvaluacionPK evaluacionPK;
    @Column(name = "documento_usuario")
    private String documentoUsuario;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluacion")
    private List<EvaluacionPuntajes> evaluacionPuntajesList;

    private List<Ciclo> ciclos;
    private Usuarios usuarios;
    private Establecimiento establecimiento;
    private String resumenes;
    private List<Resumenes> resumenesList;

    private Double calificacion;
    private Double peso;
    private Date fechaResumen;

    public Evaluacion(Date fecha) {
        this.fecha = fecha;
    }

    public Evaluacion() {
    }

    public Evaluacion(EvaluacionPK evaluacionPK) {
        this.evaluacionPK = evaluacionPK;
    }

    public Evaluacion(EvaluacionPK evaluacionPK, Date fecha, String estado) {
        this.evaluacionPK = evaluacionPK;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Evaluacion(EvaluacionPK evaluacionPK, String documentoUsuario, Date fecha, Date fechaRegistro, String estado) {
        this.evaluacionPK = evaluacionPK;
        this.documentoUsuario = documentoUsuario;
        this.fecha = fecha;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    public Evaluacion(EvaluacionPK evaluacionPK, String documentoUsuario, Date fecha, Date fechaRegistro, String estado, Double calificacion, Double peso) {
        this.evaluacionPK = evaluacionPK;
        this.documentoUsuario = documentoUsuario;
        this.fecha = fecha;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.calificacion = calificacion;
        this.peso = peso;
    }

    public Evaluacion(Long codEvaluacion, int codigoEstablecimiento) {
        this.evaluacionPK = new EvaluacionPK(codEvaluacion, codigoEstablecimiento);
    }

    public EvaluacionPK getEvaluacionPK() {
        return evaluacionPK;
    }

    public void setEvaluacionPK(EvaluacionPK evaluacionPK) {
        this.evaluacionPK = evaluacionPK;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstadoCompleto() {
        switch (estado) {
            case "A":
                return "ABIERTO";
            case "C":
                return "CERRADO";
        }
        return estado;
    }

    public String getStyleEstado() {
        String style = "padding: 10px;"
                + "opacity: 0.83;"
                + "transition: opacity 0.6s;"
                + "color: white;";
        switch (estado) {
            case App.EVALUACION_ESTADO_CRITICA:
                style += "background-color: #f44336;";
                break;
            case App.EVALUACION_ESTADO_ACEPTABLE:
                style += "background-color: #4CAF50;";
                break;
            case App.EVALUACION_ESTADO_MODERADA_ACEPTABLE:
                style += "background-color: #ff9800;";
                //#2196F3;
                break;
        }
        return style;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        hash += (evaluacionPK != null ? evaluacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.evaluacionPK == null && other.evaluacionPK != null) || (this.evaluacionPK != null && !this.evaluacionPK.equals(other.evaluacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.Evaluacion[ evaluacionPK=" + evaluacionPK + " ]";
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

    /**
     * @return the ciclos
     */
    public List<Ciclo> getCiclos() {
        return ciclos;
    }

    /**
     * @param ciclos the ciclos to set
     */
    public void setCiclos(List<Ciclo> ciclos) {
        this.ciclos = ciclos;
    }

    /**
     * @return the establecimiento
     */
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    /**
     * @param establecimiento the establecimiento to set
     */
    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    /**
     * @return the calificacion
     */
    public Double getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the peso
     */
    public Double getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    /**
     * @return the resumenes
     */
    public String getResumenes() {
        return resumenes;
    }

    /**
     * @param resumenes the resumenes to set
     */
    public void setResumenes(String resumenes) {
        this.resumenes = resumenes;
    }

    /**
     * @return the resumenesList
     */
    public List<Resumenes> getResumenesList() {
        return resumenesList;
    }

    /**
     * @param resumenesList the resumenesList to set
     */
    public void setResumenesList(List<Resumenes> resumenesList) {
        this.resumenesList = resumenesList;
    }

    /**
     * @return the fechaResumen
     */
    public Date getFechaResumen() {
        return fechaResumen;
    }

    /**
     * @param fechaResumen the fechaResumen to set
     */
    public void setFechaResumen(Date fechaResumen) {
        this.fechaResumen = fechaResumen;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

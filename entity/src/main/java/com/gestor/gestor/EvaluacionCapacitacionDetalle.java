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
import javax.faces.bean.SessionScoped;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "evaluacion_capacitacion_detalle")
@NamedQueries({
    @NamedQuery(name = "EvaluacionCapacitacionDetalle.findAll", query = "SELECT e FROM EvaluacionCapacitacionDetalle e")
})
@ManagedBean
@SessionScoped
public class EvaluacionCapacitacionDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK;

    @Column(name = "cod_ciclo")
    private String codCiclo;
    @Column(name = "cod_seccion")
    private Integer codSeccion;
    @Column(name = "cod_detalle")
    private Integer codDetalle;
    @Column(name = "cod_item")
    private Integer codItem;

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "cod_evaluacion", referencedColumnName = "cod_evaluacion", insertable = false, updatable = false),
        @JoinColumn(name = "codigo_establecimiento", referencedColumnName = "codigo_establecimiento", insertable = false, updatable = false),
        @JoinColumn(name = "cod_capacitacion", referencedColumnName = "cod_capacitacion", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private EvaluacionCapacitacion evaluacionCapacitacion;
    
    private String documentoUsuario;

    private Usuarios usuarios = new Usuarios();
    private Date fechaRegistro;

    public EvaluacionCapacitacionDetalle() {
    }

    public EvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK) {
        this.evaluacionCapacitacionDetallePK = evaluacionCapacitacionDetallePK;
    }

    public EvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK, String estado) {
        this.evaluacionCapacitacionDetallePK = evaluacionCapacitacionDetallePK;
        this.estado = estado;
    }

    public EvaluacionCapacitacionDetalle(Long codEvaluacion, int codigoEstablecimiento, Long codCapacitacion, int codCapacitacionDetalle) {
        this.evaluacionCapacitacionDetallePK = new EvaluacionCapacitacionDetallePK(codEvaluacion, codigoEstablecimiento, codCapacitacion, codCapacitacionDetalle);
    }

    public EvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK, String codCiclo, int codSeccion, int codDetalle, int codItem, String nombre, String descripcion,
            String estado, Usuarios usuarios, java.sql.Date fechaRegistro) {
        this.evaluacionCapacitacionDetallePK = evaluacionCapacitacionDetallePK;
        this.codCiclo = codCiclo;
        this.codSeccion = codSeccion;
        this.codDetalle = codDetalle;
        this.codItem = codItem;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.usuarios = usuarios;
        this.fechaRegistro = fechaRegistro;
    }

    public EvaluacionCapacitacionDetallePK getEvaluacionCapacitacionDetallePK() {
        return evaluacionCapacitacionDetallePK;
    }

    public void setEvaluacionCapacitacionDetallePK(EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK) {
        this.evaluacionCapacitacionDetallePK = evaluacionCapacitacionDetallePK;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EvaluacionCapacitacion getEvaluacionCapacitacion() {
        return evaluacionCapacitacion;
    }

    public void setEvaluacionCapacitacion(EvaluacionCapacitacion evaluacionCapacitacion) {
        this.evaluacionCapacitacion = evaluacionCapacitacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionCapacitacionDetallePK != null ? evaluacionCapacitacionDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionCapacitacionDetalle)) {
            return false;
        }
        EvaluacionCapacitacionDetalle other = (EvaluacionCapacitacionDetalle) object;
        if ((this.evaluacionCapacitacionDetallePK == null && other.evaluacionCapacitacionDetallePK != null) || (this.evaluacionCapacitacionDetallePK != null && !this.evaluacionCapacitacionDetallePK.equals(other.evaluacionCapacitacionDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionCapacitacionDetalle[ evaluacionCapacitacionDetallePK=" + evaluacionCapacitacionDetallePK + " ]";
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
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the codCiclo
     */
    public String getCodCiclo() {
        return codCiclo;
    }

    /**
     * @param codCiclo the codCiclo to set
     */
    public void setCodCiclo(String codCiclo) {
        this.codCiclo = codCiclo;
    }

    /**
     * @return the codSeccion
     */
    public Integer getCodSeccion() {
        return codSeccion;
    }

    /**
     * @param codSeccion the codSeccion to set
     */
    public void setCodSeccion(Integer codSeccion) {
        this.codSeccion = codSeccion;
    }

    /**
     * @return the codDetalle
     */
    public Integer getCodDetalle() {
        return codDetalle;
    }

    /**
     * @param codDetalle the codDetalle to set
     */
    public void setCodDetalle(Integer codDetalle) {
        this.codDetalle = codDetalle;
    }

    /**
     * @return the codItem
     */
    public Integer getCodItem() {
        return codItem;
    }

    /**
     * @param codItem the codItem to set
     */
    public void setCodItem(Integer codItem) {
        this.codItem = codItem;
    }

    /**
     * @return the documentoUsuario
     */
    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    /**
     * @param documentoUsuario the documentoUsuario to set
     */
    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

}

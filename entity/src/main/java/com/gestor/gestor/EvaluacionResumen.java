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
@Table(name = "evaluacion_resumen")
@NamedQueries({
    @NamedQuery(name = "EvaluacionResumen.findAll", query = "SELECT e FROM EvaluacionResumen e")})
public class EvaluacionResumen implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionResumenPK evaluacionResumenPK;
    @Column(name = "documento_usuario")
    private String documentoUsuario;
    @Column(name = "cod_ciclo")
    private String codCiclo;
    @Column(name = "ciclo")
    private String ciclo;
    @Column(name = "numeral")
    private String numeral;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ciclo_calificacion")
    private Double cicloCalificacion;
    @Column(name = "seccion")
    private String seccion;
    @Column(name = "seccion_peso")
    private Double seccionPeso;
    @Column(name = "seccion_orden")
    private Integer seccionOrden;
    @Column(name = "seccion_calificacion")
    private Double seccionCalificacion;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "detalle_peso")
    private Double detallePeso;
    @Column(name = "detalle_calificacion")
    private Double detalleCalificacion;
    @Column(name = "detalle_orden")
    private Integer detalleOrden;
    @Column(name = "items")
    private String items;
    @Column(name = "items_detalle")
    private String itemsDetalle;
    @Column(name = "items_peso")
    private Double itemsPeso;
    @Column(name = "items_orden")
    private Integer itemsOrden;
    @Column(name = "cod_puntaje")
    private String codPuntaje;
    @Column(name = "califica")
    private Boolean califica;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    
    private Evaluacion evaluacion;

    public EvaluacionResumen() {
    }

    public EvaluacionResumen(EvaluacionResumenPK evaluacionResumenPK) {
        this.evaluacionResumenPK = evaluacionResumenPK;
    }

    public EvaluacionResumen(Long codEvaluacion, int codigoEstablecimiento, int codResumen) {
        this.evaluacionResumenPK = new EvaluacionResumenPK(codEvaluacion, codigoEstablecimiento, codResumen);
    }

    public EvaluacionResumen(EvaluacionResumenPK evaluacionResumenPK, String documentoUsuario, String codCiclo, String ciclo, String numeral, Double cicloCalificacion, String seccion, Double seccionPeso, Integer seccionOrden, Double seccionCalificacion, String detalle, Double detallePeso, Double detalleCalificacion, Integer detalleOrden, String items, String itemsDetalle, Double itemsPeso, Integer itemsOrden, String codPuntaje, Boolean califica, Date fechaRegistro) {
        this.evaluacionResumenPK = evaluacionResumenPK;
        this.documentoUsuario = documentoUsuario;
        this.codCiclo = codCiclo;
        this.ciclo = ciclo;
        this.numeral = numeral;
        this.cicloCalificacion = cicloCalificacion;
        this.seccion = seccion;
        this.seccionPeso = seccionPeso;
        this.seccionOrden = seccionOrden;
        this.seccionCalificacion = seccionCalificacion;
        this.detalle = detalle;
        this.detallePeso = detallePeso;
        this.detalleCalificacion = detalleCalificacion;
        this.detalleOrden = detalleOrden;
        this.items = items;
        this.itemsDetalle = itemsDetalle;
        this.itemsPeso = itemsPeso;
        this.itemsOrden = itemsOrden;
        this.codPuntaje = codPuntaje;
        this.califica = califica;
        this.fechaRegistro = fechaRegistro;
    }
    
    

    public EvaluacionResumenPK getEvaluacionResumenPK() {
        return evaluacionResumenPK;
    }

    public void setEvaluacionResumenPK(EvaluacionResumenPK evaluacionResumenPK) {
        this.evaluacionResumenPK = evaluacionResumenPK;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public String getCodCiclo() {
        return codCiclo;
    }

    public void setCodCiclo(String codCiclo) {
        this.codCiclo = codCiclo;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getNumeral() {
        return numeral;
    }

    public void setNumeral(String numeral) {
        this.numeral = numeral;
    }

    public Double getCicloCalificacion() {
        return cicloCalificacion;
    }

    public void setCicloCalificacion(Double cicloCalificacion) {
        this.cicloCalificacion = cicloCalificacion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Double getSeccionPeso() {
        return seccionPeso;
    }

    public void setSeccionPeso(Double seccionPeso) {
        this.seccionPeso = seccionPeso;
    }

    public Integer getSeccionOrden() {
        return seccionOrden;
    }

    public void setSeccionOrden(Integer seccionOrden) {
        this.seccionOrden = seccionOrden;
    }

    public Double getSeccionCalificacion() {
        return seccionCalificacion;
    }

    public void setSeccionCalificacion(Double seccionCalificacion) {
        this.seccionCalificacion = seccionCalificacion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getDetallePeso() {
        return detallePeso;
    }

    public void setDetallePeso(Double detallePeso) {
        this.detallePeso = detallePeso;
    }

    public Double getDetalleCalificacion() {
        return detalleCalificacion;
    }

    public void setDetalleCalificacion(Double detalleCalificacion) {
        this.detalleCalificacion = detalleCalificacion;
    }

    public Integer getDetalleOrden() {
        return detalleOrden;
    }

    public void setDetalleOrden(Integer detalleOrden) {
        this.detalleOrden = detalleOrden;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getItemsDetalle() {
        return itemsDetalle;
    }

    public void setItemsDetalle(String itemsDetalle) {
        this.itemsDetalle = itemsDetalle;
    }

    public Double getItemsPeso() {
        return itemsPeso;
    }

    public void setItemsPeso(Double itemsPeso) {
        this.itemsPeso = itemsPeso;
    }

    public Integer getItemsOrden() {
        return itemsOrden;
    }

    public void setItemsOrden(Integer itemsOrden) {
        this.itemsOrden = itemsOrden;
    }

    public String getCodPuntaje() {
        return codPuntaje;
    }

    public void setCodPuntaje(String codPuntaje) {
        this.codPuntaje = codPuntaje;
    }

    public Boolean getCalifica() {
        return califica;
    }

    public void setCalifica(Boolean califica) {
        this.califica = califica;
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
        hash += (evaluacionResumenPK != null ? evaluacionResumenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionResumen)) {
            return false;
        }
        EvaluacionResumen other = (EvaluacionResumen) object;
        if ((this.evaluacionResumenPK == null && other.evaluacionResumenPK != null) || (this.evaluacionResumenPK != null && !this.evaluacionResumenPK.equals(other.evaluacionResumenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionResumen[ evaluacionResumenPK=" + evaluacionResumenPK + " ]";
    }

    /**
     * @return the evaluacion
     */
    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    /**
     * @param evaluacion the evaluacion to set
     */
    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }
    
}

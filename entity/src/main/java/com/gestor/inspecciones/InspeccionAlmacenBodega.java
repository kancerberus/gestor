/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.inspecciones;

import com.gestor.publico.CentroTrabajo;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;



/**
 *
 * @author franj
 */
@Entity
@Table(name = "inspeccion_almacen_bodega")
@NamedQueries({
    @NamedQuery(name = "InspeccionAlmacenBodega.findAll", query = "SELECT iab FROM InspeccionAlmacenBodega iab")})

public class InspeccionAlmacenBodega implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Basic(optional = false)    
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    @Basic(optional = false)    
    @Column(name = "cod_centrotrabajo")
    private Integer codCentrotrabajo;
    @Basic(optional = false)    
    @Column(name = "cod_inspeccion_almacen_bodega")
    private Integer codInspeccionAlmacenBodega;    
    @Column(name = "cod_sgs")
    private String codSgs;
    @Column(name = "fecha_inspeccion")    
    private Date fechaInspeccion;    
    @Column(name = "version")
    private String version;
    @Column(name = "fecha_registro")    
    private Date fechaRegistro;    
    @Column(name = "responsableuno")
    private String responsableuno;    
    @Column(name = "responsabledos")
    private String responsabledos;
    @Column(name = "cod_metrica")
    private Integer codMetrica;
    @Column(name = "cumple")
    private Boolean cumple;
    @Column(name = "valoracion")
    private String valoracion;    
    @Column(name = "observacion")
    private String observacion;  
    
    
    private CentroTrabajo centroTrabajo;
    private AlmacenBodegaMetricas almBodegaMetricas=new AlmacenBodegaMetricas();
    
    public InspeccionAlmacenBodega(){
        
    }    

    public InspeccionAlmacenBodega(Integer codigoEstablecimiento, Integer codCentrotrabajo, Integer codInspeccionAlmacenBodega, String codSgs, Date fechaInspeccion, String version, Date fechaRegistro, String responsableuno, String responsabledos, Integer codMetrica, Boolean cumple, String valoracion, String observacion) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCentrotrabajo = codCentrotrabajo;
        this.codInspeccionAlmacenBodega = codInspeccionAlmacenBodega;
        this.codSgs = codSgs;
        this.fechaInspeccion = fechaInspeccion;
        this.version = version;
        this.fechaRegistro = fechaRegistro;
        this.responsableuno = responsableuno;
        this.responsabledos = responsabledos;
        this.codMetrica = codMetrica;
        this.cumple = cumple;
        this.valoracion = valoracion;
        this.observacion = observacion;        
    }
    
    

    public AlmacenBodegaMetricas getAlmBodegaMetricas() {
        return almBodegaMetricas;
    }

    public void setAlmBodegaMetricas(AlmacenBodegaMetricas almBodegaMetricas) {
        this.almBodegaMetricas = almBodegaMetricas;
    }
    
    public CentroTrabajo getCentroTrabajo() {
        return centroTrabajo;
    }

    public void setCentroTrabajo(CentroTrabajo centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }
    

    public Integer getCodCentrotrabajo() {
        return codCentrotrabajo;
    }

    public void setCodCentrotrabajo(Integer codCentrotrabajo) {
        this.codCentrotrabajo = codCentrotrabajo;
    }

    public Integer getCodInspeccionAlmacenBodega() {
        return codInspeccionAlmacenBodega;
    }

    public void setCodInspeccionAlmacenBodega(Integer codInspeccionAlmacenBodega) {
        this.codInspeccionAlmacenBodega = codInspeccionAlmacenBodega;
    }

    public Integer getCodMetrica() {
        return codMetrica;
    }

    public void setCodMetrica(Integer codMetrica) {
        this.codMetrica = codMetrica;
    }

    public Boolean getCumple() {
        return cumple;
    }

    public void setCumple(Boolean cumple) {
        this.cumple = cumple;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }   

    public Date getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(Date fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }    

    public String getCodSgs() {
        return codSgs;
    }

    public void setCodSgs(String codSgs) {
        this.codSgs = codSgs;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }    

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }   

    public String getResponsableuno() {
        return responsableuno;
    }

    public void setResponsableuno(String responsableuno) {
        this.responsableuno = responsableuno;
    }

    public String getResponsabledos() {
        return responsabledos;
    }

    public void setResponsabledos(String responsabledos) {
        this.responsabledos = responsabledos;
    }
    
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InspeccionAlmacenBodega)) {
            return false;
        }
        InspeccionAlmacenBodega other = (InspeccionAlmacenBodega) object;
        if ((this.codInspeccionAlmacenBodega == null && other.codInspeccionAlmacenBodega != null) || (this.codInspeccionAlmacenBodega != null && !this.codInspeccionAlmacenBodega.equals(other.codInspeccionAlmacenBodega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.Inspecciones.InspeccionAlmacenBodega[ cod_inspecciones_almacen_bodega=" + codInspeccionAlmacenBodega + " ]";
    }
    
    
}

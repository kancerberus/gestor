/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.inspecciones;

import com.gestor.publico.CentroTrabajo;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author franj
 */
@Entity
@Table(name = "insapeccion_extintores")
@NamedQueries({
    @NamedQuery(name = "InspeccionExtintor.findAll", query = "SELECT ie FROM InspeccionExtintor ie")})

public class InspeccionExtintor implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Basic(optional = false)    
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)    
    @Column(name = "cod_inspeccion_extintor")
    private Integer codInspeccionExtintor;
    @Basic(optional = false)    
    @Column(name = "cod_centrotrabajo")
    private Integer codCentrotrabajo;
    @Column(name = "cod_sgs")
    private String codSgs;
    @Column(name = "fecha_vence")    
    private Date fechaVence;    
    @Column(name = "version")
    private String version;
    @Column(name = "fecha_registro")    
    private Date fechaRegistro;    
    @Column(name = "responsableuno")
    private String responsableuno;    
    @Column(name = "responsabledos")
    private String responsabledos;
    @Column(name = "no_extintor")
    private Integer noExtintor;    
    @Column(name = "area")
    private String area;    
    @Column(name = "tipo")
    private String tipo;    
    @Column(name = "capacidad")
    private Integer capacidad;
    @Column(name = "cilindro")
    private Boolean cilindro;
    @Column(name = "boquilla")
    private Boolean boquilla;
    @Column(name = "manguera")
    private Boolean manguera;
    @Column(name = "manometro")
    private Boolean manometro;
    @Column(name = "senalizacion")
    private Boolean senalizacion;
    @Column(name = "fecha_recarga")    
    private Date fechaRecarga;    
    @Column(name = "observacion")
    private String observacion;        
    
    private CentroTrabajo centroTrabajo;

    public InspeccionExtintor(short codigoEstablecimiento, Integer codInspeccionExtintor, Integer codCentrotrabajo, String codSgs, Date fechaVence, String version, Date fechaRegistro, String responsableuno, String responsabledos, Integer noExtintor, String area, String tipo, Integer capacidad, Boolean cilindro, Boolean boquilla, Boolean manguera, Boolean manometro, Boolean senalizacion, Date fechaRecarga, String observacion) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codInspeccionExtintor = codInspeccionExtintor;
        this.codCentrotrabajo = codCentrotrabajo;
        this.codSgs = codSgs;
        this.fechaVence = fechaVence;
        this.version = version;
        this.fechaRegistro = fechaRegistro;
        this.responsableuno = responsableuno;
        this.responsabledos = responsabledos;
        this.noExtintor = noExtintor;
        this.area = area;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.cilindro = cilindro;
        this.boquilla = boquilla;
        this.manguera = manguera;
        this.manometro = manometro;
        this.senalizacion = senalizacion;
        this.fechaRecarga = fechaRecarga;
        this.observacion = observacion;
    }



    
    
    public InspeccionExtintor(){
        
    }    

    public CentroTrabajo getCentroTrabajo() {
        return centroTrabajo;
    }

    public void setCentroTrabajo(CentroTrabajo centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }
    
    public short getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(short codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Integer getCodInspeccionExtintor() {
        return codInspeccionExtintor;
    }

    public void setCodInspeccionExtintor(Integer codInspeccionExtintor) {
        this.codInspeccionExtintor = codInspeccionExtintor;
    }

    public Integer getCodCentrotrabajo() {
        return codCentrotrabajo;
    }

    public void setCodCentrotrabajo(Integer codCentrotrabajo) {
        this.codCentrotrabajo = codCentrotrabajo;
    }

    public String getCodSgs() {
        return codSgs;
    }

    public void setCodSgs(String codSgs) {
        this.codSgs = codSgs;
    }

    public Date getFechaVence() {
        return fechaVence;
    }

    public void setFechaVence(Date fechaVence) {
        this.fechaVence = fechaVence;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaRecarga() {        
        return fechaRecarga;
    }

    public void setFechaRecarga(Date fechaRecarga) {
        this.fechaRecarga = fechaRecarga;
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

    public Integer getNoExtintor() {
        return noExtintor;
    }

    public void setNoExtintor(Integer noExtintor) {
        this.noExtintor = noExtintor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Boolean getCilindro() {
        return cilindro;
    }

    public void setCilindro(Boolean cilindro) {
        this.cilindro = cilindro;
    }

    public Boolean getBoquilla() {
        return boquilla;
    }

    public void setBoquilla(Boolean boquilla) {
        this.boquilla = boquilla;
    }

    public Boolean getManguera() {
        return manguera;
    }

    public void setManguera(Boolean manguera) {
        this.manguera = manguera;
    }

    public Boolean getManometro() {
        return manometro;
    }

    public void setManometro(Boolean manometro) {
        this.manometro = manometro;
    }

    public Boolean getSenalizacion() {
        return senalizacion;
    }

    public void setSenalizacion(Boolean senalizacion) {
        this.senalizacion = senalizacion;
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
        if (!(object instanceof InspeccionExtintor)) {
            return false;
        }
        InspeccionExtintor other = (InspeccionExtintor) object;
        if ((this.codInspeccionExtintor == null && other.codInspeccionExtintor != null) || (this.codInspeccionExtintor != null && !this.codInspeccionExtintor.equals(other.codInspeccionExtintor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.Inspecciones.InspeccionExtintor[ cod_inspecciones_extintor=" + codInspeccionExtintor + " ]";
    }
    
    
}

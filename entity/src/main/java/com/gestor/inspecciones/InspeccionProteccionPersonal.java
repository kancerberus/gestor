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
@Table(name = "inspeccion_proteccion_personal")
@NamedQueries({
    @NamedQuery(name = "InspeccionProteccionPersonal.findAll", query = "SELECT ipp FROM InspeccionProteccionPersonal ipp")})

public class InspeccionProteccionPersonal implements Serializable{
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
    @Column(name = "cod_ins_proteccion_personal")
    private Integer codInsProteccionPersonal;    
    @Column(name = "cod_sgs")
    private String codSgs;
    @Column(name = "vigencia")    
    private Date vigencia;    
    @Column(name = "fecha_inspeccion")    
    private Date fechaInspeccion;    
    @Column(name = "hora_inspeccion")    
    private Date hora_inspeccion;    
    @Column(name = "nom_empleado")    
    private String nomEmpleado;        
    @Column(name = "actividad")    
    private String actividad;        
    @Column(name = "botas")    
    private Boolean botas;        
    @Column(name = "guantes")    
    private Boolean guantes;        
    @Column(name = "tapabocas")    
    private Boolean tapabocas;        
    @Column(name = "gorras")    
    private Boolean gorras;        
    @Column(name = "Zapatos")    
    private Boolean zapatos;        
    @Column(name = "delantal")        
    private Boolean delantal;     
    @Column(name = "ele_pp")    
    private String elePP;        
    @Column(name = "motivo")    
    private String motivo;                
    @Column(name = "version")
    private String version;
    @Column(name = "fecha_registro")    
    private Date fechaRegistro;    
    @Column(name = "responsableuno")
    private String responsableuno;    
    @Column(name = "responsabledos")
    private String responsabledos;
   
    @Column(name = "observacion")
    private String observacion;  
    
    
    private CentroTrabajo centroTrabajo;
    private MotivoNoUso motivoNoUso;
    
    public InspeccionProteccionPersonal(){
        
    }

    public InspeccionProteccionPersonal(Integer codigoEstablecimiento, Integer codCentrotrabajo, Integer codInsProteccionPersonal, String codSgs, Date vigencia, Date fechaInspeccion, Date hora_inspeccion, String nomEmpleado, String actividad, Boolean botas, Boolean guantes, Boolean tapabocas, Boolean gorras, Boolean zapatos, Boolean delantal, String elePP, String motivo, String version, Date fechaRegistro, String responsableuno, String responsabledos, String observacion) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCentrotrabajo = codCentrotrabajo;
        this.codInsProteccionPersonal = codInsProteccionPersonal;
        this.codSgs = codSgs;
        this.vigencia = vigencia;
        this.fechaInspeccion = fechaInspeccion;
        this.hora_inspeccion = hora_inspeccion;
        this.nomEmpleado = nomEmpleado;
        this.actividad = actividad;
        this.botas = botas;
        this.guantes = guantes;
        this.tapabocas = tapabocas;
        this.gorras = gorras;
        this.zapatos = zapatos;
        this.delantal = delantal;
        this.elePP = elePP;
        this.motivo = motivo;
        this.version = version;
        this.fechaRegistro = fechaRegistro;
        this.responsableuno = responsableuno;
        this.responsabledos = responsabledos;
        this.observacion = observacion;        
    }
    
    

    public Date getHora_inspeccion() {
        return hora_inspeccion;
    }

    public void setHora_inspeccion(Date hora_inspeccion) {
        this.hora_inspeccion = hora_inspeccion;
    }

    public String getNomEmpleado() {
        return nomEmpleado;
    }

    public void setNomEmpleado(String nomEmpleado) {
        this.nomEmpleado = nomEmpleado;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Boolean getBotas() {
        return botas;
    }

    public void setBotas(Boolean botas) {
        this.botas = botas;
    }

    public Boolean getGuantes() {
        return guantes;
    }

    public void setGuantes(Boolean guantes) {
        this.guantes = guantes;
    }

    public Boolean getTapabocas() {
        return tapabocas;
    }

    public void setTapabocas(Boolean tapabocas) {
        this.tapabocas = tapabocas;
    }

    public Boolean getGorras() {
        return gorras;
    }

    public void setGorras(Boolean gorras) {
        this.gorras = gorras;
    }

    public Boolean getZapatos() {
        return zapatos;
    }

    public void setZapatos(Boolean zapatos) {
        this.zapatos = zapatos;
    }

    public Boolean getDelantal() {
        return delantal;
    }

    public void setDelantal(Boolean delantal) {
        this.delantal = delantal;
    }

    public String getElePP() {
        return elePP;
    }

    public void setElePP(String elePP) {
        this.elePP = elePP;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public MotivoNoUso getMotivoNoUso() {
        return motivoNoUso;
    }

    public void setMotivoNoUso(MotivoNoUso motivoNoUso) {
        this.motivoNoUso = motivoNoUso;
    }

    
    public Integer getCodInsProteccionPersonal() {
        return codInsProteccionPersonal;
    }

    public void setCodInsProteccionPersonal(Integer codInsProteccionPersonal) {
        this.codInsProteccionPersonal = codInsProteccionPersonal;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
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
        if (!(object instanceof InspeccionProteccionPersonal)) {
            return false;
        }
        InspeccionProteccionPersonal other = (InspeccionProteccionPersonal) object;
        if ((this.codInsProteccionPersonal == null && other.codInsProteccionPersonal != null) || (this.codInsProteccionPersonal != null && !this.codInsProteccionPersonal.equals(other.codInsProteccionPersonal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.Inspecciones.InspeccionProteccionPersonal[ cod_ins_proteccion_personal=" + codInsProteccionPersonal + " ]";
    }
    
    
}

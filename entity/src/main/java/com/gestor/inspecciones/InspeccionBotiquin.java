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
@Table(name = "insapeccion_botiquines")
@NamedQueries({
    @NamedQuery(name = "InspeccionBotiquin.findAll", query = "SELECT bot FROM Botiquin bot")})

public class InspeccionBotiquin implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Basic(optional = false)    
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)    
    @Column(name = "cod_centrotrabajo")
    private Integer codCentrotrabajo;
    @Basic(optional = false)    
    @Column(name = "cod_inspeccion_botiquin")
    private Integer codInspeccionBotiquin;    
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
    @Column(name = "bot_portatil")
    private Boolean botPortatil;
    @Column(name = "cod_elemento")
    private Integer codElemento;    
    @Column(name = "cantidad_req")
    private Integer cantidadReq;    
    @Column(name = "cantidad_exis")
    private Integer cantidadExis;    
    @Column(name = "fvence_ele")    
    private Date fvenceEle;    
    @Column(name = "observacion")
    private String observacion;  
    
    
    private CentroTrabajo centroTrabajo;
    private ElementosBotiquin elementosBotiquin=new ElementosBotiquin();
    
    public InspeccionBotiquin(){
        
    }    

    public InspeccionBotiquin(short codigoEstablecimiento, Integer codCentrotrabajo, Integer codInspeccionBotiquin, String codSgs, Date fechaInspeccion, String version, Date fechaRegistro, String responsableuno, String responsabledos, Boolean botPortatil, Integer codElemento, Integer cantidadReq, Integer cantidadExis, Date fvenceEle, String observacion) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCentrotrabajo = codCentrotrabajo;
        this.codInspeccionBotiquin = codInspeccionBotiquin;
        this.codSgs = codSgs;
        this.fechaInspeccion = fechaInspeccion;
        this.version = version;
        this.fechaRegistro = fechaRegistro;
        this.responsableuno = responsableuno;
        this.responsabledos = responsabledos;
        this.botPortatil = botPortatil;
        this.codElemento = codElemento;
        this.cantidadReq = cantidadReq;
        this.cantidadExis = cantidadExis;
        this.fvenceEle = fvenceEle;
        this.observacion = observacion;
    }
    
    
    public ElementosBotiquin getElementosBotiquin() {
        return elementosBotiquin;
    }

    public void setElementosBotiquin(ElementosBotiquin elementosBotiquin) {
        this.elementosBotiquin = elementosBotiquin;
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

    public Integer getCodCentrotrabajo() {
        return codCentrotrabajo;
    }

    public void setCodCentrotrabajo(Integer codCentrotrabajo) {
        this.codCentrotrabajo = codCentrotrabajo;
    }

    public Integer getCodInspeccionBotiquin() {
        return codInspeccionBotiquin;
    }

    public void setCodInspeccionBotiquin(Integer codInspeccionBotiquin) {
        this.codInspeccionBotiquin = codInspeccionBotiquin;
    }

    public Date getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(Date fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public Boolean getBotPortatil() {
        return botPortatil;
    }

    public void setBotPortatil(Boolean botPortatil) {
        this.botPortatil = botPortatil;
    }

    public Integer getCodElemento() {
        return codElemento;
    }

    public void setCodElemento(Integer codElemento) {
        this.codElemento = codElemento;
    }

    public Integer getCantidadReq() {
        return cantidadReq;
    }

    public void setCantidadReq(Integer cantidadReq) {
        this.cantidadReq = cantidadReq;
    }

    public Integer getCantidadExis() {
        return cantidadExis;
    }

    public void setCantidadExis(Integer cantidadExis) {
        this.cantidadExis = cantidadExis;
    }

    public Date getFvenceEle() {
        return fvenceEle;
    }

    public void setFvenceEle(Date fvenceEle) {
        this.fvenceEle = fvenceEle;
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
        if (!(object instanceof InspeccionBotiquin)) {
            return false;
        }
        InspeccionBotiquin other = (InspeccionBotiquin) object;
        if ((this.codInspeccionBotiquin == null && other.codInspeccionBotiquin != null) || (this.codInspeccionBotiquin != null && !this.codInspeccionBotiquin.equals(other.codInspeccionBotiquin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.Inspecciones.InspeccionBotiquin[ cod_inspecciones_botiquin=" + codInspeccionBotiquin + " ]";
    }
    
    
}

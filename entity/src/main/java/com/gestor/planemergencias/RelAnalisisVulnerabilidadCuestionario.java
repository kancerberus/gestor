/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author franj
 */
@Entity
@Table(name = "rel_analisis_vulnerabilidad_cuestionario")
@NamedQueries({
    @NamedQuery(name = "rel_analisis_vulnerabilidad_cuestionario.findAll", query = "SELECT relavc FROM rel_analisis_vulnerabilidad_cuestionario relavc")})
public class RelAnalisisVulnerabilidadCuestionario implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_rel_analisis_vul_cuestionario")
    private Integer codRelAnalisisVulCuestionario;
    @Column(name = "cod_analisis_vulnerabilidad")
    private Integer codAnalisisVulnerabilidad;
    @Column(name = "cod_vulnerabilidad")
    private Integer codVulnerabilidad;
    @Column(name = "cod_vulnerabilidad_tipo")
    private Integer codVulnerabilidadTipo;
    @Column(name = "cod_cuestionario")
    private Integer codCuestionario;
    @Column(name = "calificacion")
    private Float calificacion;
    @Column(name = "cod_origen")
    private Integer codOrigen;
    @Column(name = "cod_origen_tipo")
    private Integer codOrigenTipo;    
    
    private RelOriegenTipo relOrigenTipo;
    private TipoOrigen tipoOrigen;
    private CuestionarioVulnerabilidad cuestionarioVulnerabilidad;
    
    private List<Float> calificaciones= new ArrayList<>();
    
    
    public RelAnalisisVulnerabilidadCuestionario() {
    }   

    public RelAnalisisVulnerabilidadCuestionario(Integer codAnalisisVulnerabilidad, Integer codVulnerabilidad, Integer codVulnerabilidadTipo, Integer codCuestionario, Integer codRelAnalisisVulCuestionario) {
        this.codAnalisisVulnerabilidad = codAnalisisVulnerabilidad;
        this.codVulnerabilidad = codVulnerabilidad;
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
        this.codCuestionario = codCuestionario;   
        this.codRelAnalisisVulCuestionario = codRelAnalisisVulCuestionario;
    }    

    public List<Float> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Float> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public Integer getCodRelAnalisisVulCuestionario() {
        return codRelAnalisisVulCuestionario;
    }

    public void setCodRelAnalisisVulCuestionario(Integer codRelAnalisisVulCuestionario) {
        this.codRelAnalisisVulCuestionario = codRelAnalisisVulCuestionario;
    }

    public Integer getCodVulnerabilidad() {
        return codVulnerabilidad;
    }

    public void setCodVulnerabilidad(Integer codVulnerabilidad) {
        this.codVulnerabilidad = codVulnerabilidad;
    }

    public Integer getCodVulnerabilidadTipo() {
        return codVulnerabilidadTipo;
    }

    public void setCodVulnerabilidadTipo(Integer codVulnerabilidadTipo) {
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
    }

    public Integer getCodCuestionario() {
        return codCuestionario;
    }

    public void setCodCuestionario(Integer codCuestionario) {
        this.codCuestionario = codCuestionario;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getCodOrigen() {
        return codOrigen;
    }

    public void setCodOrigen(Integer codOrigen) {
        this.codOrigen = codOrigen;
    }

    public Integer getCodOrigenTipo() {
        return codOrigenTipo;
    }

    public void setCodOrigenTipo(Integer codOrigenTipo) {
        this.codOrigenTipo = codOrigenTipo;
    }

    public Integer getCodAnalisisVulnerabilidad() {
        return codAnalisisVulnerabilidad;
    }

    public void setCodAnalisisVulnerabilidad(Integer codAnalisisVulnerabilidad) {
        this.codAnalisisVulnerabilidad = codAnalisisVulnerabilidad;
    }

    public RelOriegenTipo getRelOrigenTipo() {
        return relOrigenTipo;
    }

    public void setRelOrigenTipo(RelOriegenTipo relOrigenTipo) {
        this.relOrigenTipo = relOrigenTipo;
    }

    public TipoOrigen getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(TipoOrigen tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }

    public CuestionarioVulnerabilidad getCuestionarioVulnerabilidad() {
        return cuestionarioVulnerabilidad;
    }

    public void setCuestionarioVulnerabilidad(CuestionarioVulnerabilidad cuestionarioVulnerabilidad) {
        this.cuestionarioVulnerabilidad = cuestionarioVulnerabilidad;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelAnalisisVulnerabilidadCuestionario)) {
            return false;
        }
        RelAnalisisVulnerabilidadCuestionario other = (RelAnalisisVulnerabilidadCuestionario) object;
        if ((this.codAnalisisVulnerabilidad == null && other.codAnalisisVulnerabilidad != null) || (this.codAnalisisVulnerabilidad != null && !this.codAnalisisVulnerabilidad.equals(other.codAnalisisVulnerabilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.RelAnalisisVulnerabilidadCuestionario[ cod_Analisis_vulnerabilida=" + codAnalisisVulnerabilidad + " ]";
    }
    
}

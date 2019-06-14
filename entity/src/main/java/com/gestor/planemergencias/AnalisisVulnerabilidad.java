/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias;

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
/**
 *
 * @author franj
 */
@Entity
@Table(name = "gravedad")
@NamedQueries({
    @NamedQuery(name = "gravedad.findAll", query = "SELECT g FROM gravedad g")})
public class AnalisisVulnerabilidad implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    @Column(name = "cod_centro_trabajo")
    private Integer codCentroTrabajo;
    @Column(name = "cod_plan_emergencia")
    private Integer codPlanEmergencia;
    @Column(name = "cod_analisis_vulnerabilidad")
    private Integer codAnalisisVulnerabilidad;

    @Column(name = "cod_vulnerabilida_tipo")
    private Integer codVulnerabilidadTipo;
    @Column(name = "cod_cuestionario")
    private Integer codCuestionario;
    @Column(name = "cod_vulnerabilidad")
    private Integer codVulnerabilidad;
    @Column(name = "cod_tipo_origen")
    private Integer codTipoOrigen;
    @Column(name = "cod_origen")
    private Integer codOrigen;
    
    private Vulnerabilidad vulnerabilidad;
    private RelVulnerabilidadTipo vulnerabilidadTipo;
    private CuestionarioVulnerabilidad cuestionarioVulnerabilidad;
    private RelAnalisisVulnerabilidadCuestionario relCuestionarioVulnerabilidad;
    
    
    public AnalisisVulnerabilidad(){
    }

    public AnalisisVulnerabilidad(Integer codigoEstablecimiento, Integer codCentroTrabajo, Integer codPlanEmergencia, Integer codAnalisisVulnerabilidad, Integer codVulnerabilidadTipo, Integer codCuestionario, Integer codVulnerabilidad) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCentroTrabajo = codCentroTrabajo;
        this.codPlanEmergencia = codPlanEmergencia;
        this.codAnalisisVulnerabilidad = codAnalisisVulnerabilidad;
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
        this.codCuestionario = codCuestionario;
        this.codVulnerabilidad = codVulnerabilidad;        
    }

    public RelAnalisisVulnerabilidadCuestionario getRelCuestionarioVulnerabilidad() {
        return relCuestionarioVulnerabilidad;
    }

    public void setRelCuestionarioVulnerabilidad(RelAnalisisVulnerabilidadCuestionario relCuestionarioVulnerabilidad) {
        this.relCuestionarioVulnerabilidad = relCuestionarioVulnerabilidad;
    }

    public RelVulnerabilidadTipo getVulnerabilidadTipo() {
        return vulnerabilidadTipo;
    }

    public void setVulnerabilidadTipo(RelVulnerabilidadTipo vulnerabilidadTipo) {
        this.vulnerabilidadTipo = vulnerabilidadTipo;
    }

    public Vulnerabilidad getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(Vulnerabilidad vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    public CuestionarioVulnerabilidad getCuestionarioVulnerabilidad() {
        return cuestionarioVulnerabilidad;
    }

    public void setCuestionarioVulnerabilidad(CuestionarioVulnerabilidad cuestionarioVulnerabilidad) {
        this.cuestionarioVulnerabilidad = cuestionarioVulnerabilidad;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Integer getCodCentroTrabajo() {
        return codCentroTrabajo;
    }

    public void setCodCentroTrabajo(Integer codCentroTrabajo) {
        this.codCentroTrabajo = codCentroTrabajo;
    }

    public Integer getCodPlanEmergencia() {
        return codPlanEmergencia;
    }

    public void setCodPlanEmergencia(Integer codPlanEmergencia) {
        this.codPlanEmergencia = codPlanEmergencia;
    }

    public Integer getCodAnalisisVulnerabilidad() {
        return codAnalisisVulnerabilidad;
    }

    public void setCodAnalisisVulnerabilidad(Integer codAnalisisVulnerabilidad) {
        this.codAnalisisVulnerabilidad = codAnalisisVulnerabilidad;
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

    public Integer getCodVulnerabilidad() {
        return codVulnerabilidad;
    }

    public void setCodVulnerabilidad(Integer codVulnerabilidad) {
        this.codVulnerabilidad = codVulnerabilidad;
    }

    public Integer getCodTipoOrigen() {
        return codTipoOrigen;
    }

    public void setCodTipoOrigen(Integer codTipoOrigen) {
        this.codTipoOrigen = codTipoOrigen;
    }

    public Integer getCodOrigen() {
        return codOrigen;
    }

    public void setCodOrigen(Integer codOrigen) {
        this.codOrigen = codOrigen;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalisisVulnerabilidad)) {
            return false;
        }
        AnalisisVulnerabilidad other = (AnalisisVulnerabilidad) object;
        if ((this.codAnalisisVulnerabilidad == null && other.codAnalisisVulnerabilidad != null) || (this.codAnalisisVulnerabilidad != null && !this.codAnalisisVulnerabilidad.equals(other.codAnalisisVulnerabilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.AnalisisVulnerabilidad[ cod_analisis_vulnerabilidad=" + codAnalisisVulnerabilidad + " ]";
    }
}
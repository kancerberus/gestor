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
@Table(name = "determinacion_nivel_riesgo")
@NamedQueries({
    @NamedQuery(name = "determinacion_nivel_riesgo.findAll", query = "SELECT detnr FROM determinacion_nivel_riesgo detnr")})
public class DeterminacionNivelRiesgo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_plan_emergencia")
    private Integer codPlanEmergencia;
    @Column(name = "cod_det_nivel_riesgo")
    private Integer codDetNivelRiesgo;
    @Column(name = "cod_origen")
    private Integer codOrigen;
    @Column(name = "cod_tipo_origen")
    private Integer codTipoOrigen;
    @Column(name = "promedio")
    private Float promedio;
    @Column(name = "nivel_riesgo")
    private String nivelRiesgo;
    
    public DeterminacionNivelRiesgo(){
    }

    public DeterminacionNivelRiesgo(Integer codPlanEmergencia, Integer codDetNivelRiesgo, Integer codOrigen, Integer codTipoOrigen, Float promedio, String nivelRiesgo) {
        this.codPlanEmergencia = codPlanEmergencia;
        this.codDetNivelRiesgo = codDetNivelRiesgo;
        this.codOrigen = codOrigen;
        this.codTipoOrigen = codTipoOrigen;
        this.promedio = promedio;
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }


    public Integer getCodOrigen() {
        return codOrigen;
    }

    public void setCodOrigen(Integer codOrigen) {
        this.codOrigen = codOrigen;
    }

    public Integer getCodTipoOrigen() {
        return codTipoOrigen;
    }

    public void setCodTipoOrigen(Integer codTipoOrigen) {
        this.codTipoOrigen = codTipoOrigen;
    }

    public Float getPromedio() {
        return promedio;
    }

    public void setPromedio(Float promedio) {
        this.promedio = promedio;
    }


    public Integer getCodDetNivelRiesgo() {
        return codDetNivelRiesgo;
    }

    public void setCodDetNivelRiesgo(Integer codDetNivelRiesgo) {
        this.codDetNivelRiesgo = codDetNivelRiesgo;
    }    
      
    public Integer getCodPlanEmergencia() {
        return codPlanEmergencia;
    }

    public void setCodPlanEmergencia(Integer codPlanEmergencia) {
        this.codPlanEmergencia = codPlanEmergencia;
    }    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeterminacionNivelRiesgo)) {
            return false;
        }
        DeterminacionNivelRiesgo other = (DeterminacionNivelRiesgo) object;
        if ((this.codDetNivelRiesgo == null && other.codDetNivelRiesgo != null) || (this.codDetNivelRiesgo != null && !this.codDetNivelRiesgo.equals(other.codDetNivelRiesgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.DeterminacionNivelRiesgo[ cod_det_nivel_riesgo=" + codDetNivelRiesgo + " ]";
    }
}
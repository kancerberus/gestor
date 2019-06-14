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
@Table(name = "rel_determinacion_nivel_riesgo")
@NamedQueries({
    @NamedQuery(name = "rel_determinacion_nivel_riesgo.findAll", query = "SELECT reldetnr FROM rel_determinacion_nivel_riesgo reldetnr")})
public class RelDeterminacionNivelRiesgo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)    
    @Column(name = "cod_det_nivel_riesgo")
    private Integer codDetNivelRiesgo;
    @Column(name = "vulnerabilidad")
    private Integer codVulnerabilidad;
    @Column(name = "cod_vulnerabilidad_tipo")
    private Integer codVulnerabilidadTipo;
    @Column(name = "bajo")
    private Float bajo=0.0f;
    @Column(name = "medio")
    private Float medio=0.0f;
    @Column(name = "alto")
    private Float alto=0.0f;
    @Column(name = "subtotal")
    private Float subtotal;
    @Column(name = "color")
    private String color;
    
    private DeterminacionNivelRiesgo determinacionNivelRiesgo;
    private Vulnerabilidad vulnerabilidad;
    private RelVulnerabilidadTipo relVulnerabilidadTipo;    
    
    public RelDeterminacionNivelRiesgo(){
    }

    public RelDeterminacionNivelRiesgo(Integer codDetNivelRiesgo, Integer codVulnerabilidad, Integer codVulnerabilidadTipo, Float bajo, Float medio, Float alto, Float subtotal, String color) {
        this.codDetNivelRiesgo = codDetNivelRiesgo;
        this.codVulnerabilidad = codVulnerabilidad;
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
        this.bajo = bajo;
        this.medio = medio;
        this.alto = alto;
        this.subtotal = subtotal;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Vulnerabilidad getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(Vulnerabilidad vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    public RelVulnerabilidadTipo getRelVulnerabilidadTipo() {
        return relVulnerabilidadTipo;
    }

    public void setRelVulnerabilidadTipo(RelVulnerabilidadTipo relVulnerabilidadTipo) {
        this.relVulnerabilidadTipo = relVulnerabilidadTipo;
    }

    public DeterminacionNivelRiesgo getDeterminacionNivelRiesgo() {
        return determinacionNivelRiesgo;
    }

    public void setDeterminacionNivelRiesgo(DeterminacionNivelRiesgo determinacionNivelRiesgo) {
        this.determinacionNivelRiesgo = determinacionNivelRiesgo;
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

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Float getBajo() {
        return bajo;
    }

    public void setBajo(Float bajo) {
        this.bajo = bajo;
    }

    public Float getMedio() {
        return medio;
    }

    public void setMedio(Float medio) {
        this.medio = medio;
    }

    public Float getAlto() {
        return alto;
    }

    public void setAlto(Float alto) {
        this.alto = alto;
    }    

    public Integer getCodDetNivelRiesgo() {
        return codDetNivelRiesgo;
    }

    public void setCodDetNivelRiesgo(Integer codDetNivelRiesgo) {
        this.codDetNivelRiesgo = codDetNivelRiesgo;
    }         
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelDeterminacionNivelRiesgo)) {
            return false;
        }
        RelDeterminacionNivelRiesgo other = (RelDeterminacionNivelRiesgo) object;
        if ((this.codDetNivelRiesgo == null && other.codDetNivelRiesgo != null) || (this.codDetNivelRiesgo != null && !this.codDetNivelRiesgo.equals(other.codDetNivelRiesgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.RelDeterminacionNivelRiesgo[ cod_det_nivel_riesgo=" + codDetNivelRiesgo + " ]";
    }
}
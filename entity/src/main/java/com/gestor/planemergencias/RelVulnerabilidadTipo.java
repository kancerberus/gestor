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
@Table(name = "rel_vulnerabilidad_tipo")
@NamedQueries({
    @NamedQuery(name = "rel_vulnerabilidad_tipo.findAll", query = "SELECT relvt FROM rel_vulnerabilidad_tipo relvt")})
public class RelVulnerabilidadTipo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_vulnerabilidad_tipo")
    private Integer codVulnerabilidadTipo;
    @Basic(optional = false)
    @Column(name = "cod_vulnerabilidad")
    private Integer codVulnerabilidad;    
    @Column(name = "nombre")
    private String nombre;
    
    Vulnerabilidad vulnerabilidad;
    
        
    public RelVulnerabilidadTipo() {
    }

    public RelVulnerabilidadTipo(Integer codVulnerabilidadTipo, Integer codVulnerabilidad, String nombre) {
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
        this.codVulnerabilidad = codVulnerabilidad;
        this.nombre = nombre;
    }

    public Integer getCodVulnerabilidadTipo() {
        return codVulnerabilidadTipo;
    }

    public void setCodVulnerabilidadTipo(Integer codVulnerabilidadTipo) {
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
    }

    public Integer getCodVulnerabilidad() {
        return codVulnerabilidad;
    }

    public void setCodVulnerabilidad(Integer codVulnerabilidad) {
        this.codVulnerabilidad = codVulnerabilidad;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Vulnerabilidad getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(Vulnerabilidad vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelVulnerabilidadTipo)) {
            return false;
        }
        RelVulnerabilidadTipo other = (RelVulnerabilidadTipo) object;
        if ((this.codVulnerabilidadTipo == null && other.codVulnerabilidadTipo != null) || (this.codVulnerabilidadTipo != null && !this.codVulnerabilidadTipo.equals(other.codVulnerabilidadTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.RelVulnerabilidadTipo[ cod_vulnerabilida_tipo=" + codVulnerabilidadTipo + " ]";
    }
    
}

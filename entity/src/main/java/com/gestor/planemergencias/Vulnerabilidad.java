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
@Table(name = "vulnerabilidad")
@NamedQueries({
    @NamedQuery(name = "vulnerabilidad.findAll", query = "SELECT v FROM vulnerabilidad v")})
public class Vulnerabilidad implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_vulnerabilidad")
    private Integer codVulnerabilidad;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public Vulnerabilidad() {
    }

    public Vulnerabilidad(Integer codVulnerabilidad, String nombre) {
        this.codVulnerabilidad = codVulnerabilidad;
        this.nombre = nombre;
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

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vulnerabilidad)) {
            return false;
        }
        Vulnerabilidad other = (Vulnerabilidad) object;
        if ((this.codVulnerabilidad == null && other.codVulnerabilidad != null) || (this.codVulnerabilidad != null && !this.codVulnerabilidad.equals(other.codVulnerabilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.Vulnerabilidad[ cod_vulnerabilidad=" + codVulnerabilidad + " ]";
    }
    
}

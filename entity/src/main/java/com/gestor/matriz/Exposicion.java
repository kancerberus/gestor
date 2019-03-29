/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.matriz;

import com.gestor.publico.*;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.Table;

/**
 *
 * @author Franciso
 */
@Entity
@Table(name = "Exposicion")
@NamedQueries({
    @NamedQuery(name = "Exposicion.findAll", query = "SELECT ex FROM Exposicion ex")})
public class Exposicion implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_riesgo")
    private Integer codRiesgo;
    @Column(name = "cod_exposicion")
    private Integer codExposicion;
    @Column(name = "nombre")
    private String nombre;   
       
    public Exposicion() {        
    }

    public Exposicion(Integer codExposicion, String nombre, Integer codRiesgo) {
        this.codExposicion = codExposicion;
        this.nombre = nombre;
        this.codRiesgo = codRiesgo;
    }

    public Integer getCodExposicion() {
        return codExposicion;
    }

    public void setCodExposicion(Integer codExposicion) {
        this.codExposicion = codExposicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    

    public Integer getCodRiesgo() {
        return codRiesgo;
    }

    public void setCodRiesgo(Integer codRiesgo) {
        this.codRiesgo = codRiesgo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codExposicion != null ? codExposicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exposicion)) {
            return false;
        }
        Exposicion other = (Exposicion) object;
        if ((this.codExposicion == null && other.codExposicion != null) || (this.codExposicion != null && !this.codExposicion.equals(other.codExposicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz.Exposicion[ codExposcion=" + codExposicion + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

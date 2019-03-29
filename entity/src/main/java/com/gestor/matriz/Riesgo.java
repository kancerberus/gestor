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
@Table(name = "Riesgo")
@NamedQueries({
    @NamedQuery(name = "Riesgo.findAll", query = "SELECT ri FROM Riesgo ri")})
public class Riesgo implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_riesgo")
    private Integer codRiesgo;
    @Column(name = "nombre")
    private String nombre;   
       
    public Riesgo() {        
    }

    public Riesgo(Integer codRiesgo, String nombre) {
        this.codRiesgo = codRiesgo;
        this.nombre = nombre;
    }

    public Integer getCodRiesgo() {
        return codRiesgo;
    }

    public void setCodRiesgo(Integer codRiesgo) {
        this.codRiesgo = codRiesgo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codRiesgo != null ? codRiesgo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Riesgo)) {
            return false;
        }
        Riesgo other = (Riesgo) object;
        if ((this.codRiesgo == null && other.codRiesgo != null) || (this.codRiesgo != null && !this.codRiesgo.equals(other.codRiesgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz.Riesgo[ codRiesgo=" + codRiesgo + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

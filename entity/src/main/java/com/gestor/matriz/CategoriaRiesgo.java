/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.matriz;

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
@Table(name = "CategoriaRiesgo")
@NamedQueries({
    @NamedQuery(name = "CategoriaRiesgo.findAll", query = "SELECT cri FROM CategoriaRiesgo cri")})
public class CategoriaRiesgo implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_categoria_riesgo")
    private Integer codCategoriaRiesgo;    
    @Column(name = "nombre")
    private String nombre;   
       
    public CategoriaRiesgo() {        
    }

    public CategoriaRiesgo(Integer codCategoriaRiesgo, String nombre) {
        this.codCategoriaRiesgo = codCategoriaRiesgo;
        this.nombre = nombre;
    }

    public Integer getCodCategoriaRiesgo() {
        return codCategoriaRiesgo;
    }

    public void setCodCategoriaRiesgo(Integer codCategoriaRiesgo) {
        this.codCategoriaRiesgo = codCategoriaRiesgo;
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
        hash += (codCategoriaRiesgo != null ? codCategoriaRiesgo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaRiesgo)) {
            return false;
        }
        CategoriaRiesgo other = (CategoriaRiesgo) object;
        if ((this.codCategoriaRiesgo == null && other.codCategoriaRiesgo != null) || (this.codCategoriaRiesgo != null && !this.codCategoriaRiesgo.equals(other.codCategoriaRiesgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz.CategoriaRiesgo[ codCategoriaRiesgo=" + codCategoriaRiesgo + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

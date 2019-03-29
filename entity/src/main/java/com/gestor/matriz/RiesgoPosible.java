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
@Table(name = "RiesgoPosible")
@NamedQueries({
    @NamedQuery(name = "RiesgoPosible.findAll", query = "SELECT rpo FROM RiesgoPosible rpo")})
public class RiesgoPosible implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_categoria_riesgo")
    private Integer codCategoriaRiesgo;    
    @Column(name = "cod_riesgo_posible")
    private Integer codRiesgoPosible;    
    @Column(name = "nombre")
    private String nombre;   
    @Column(name = "descripcion")
    private String descripcion;   
       
    public RiesgoPosible() {        
    }

    public RiesgoPosible(Integer codCategoriaRiesgo, Integer codRiesgoPosible, String nombre, String descripcion) {
        this.codCategoriaRiesgo = codCategoriaRiesgo;
        this.codRiesgoPosible = codRiesgoPosible;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {        
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodRiesgoPosible() {
        return codRiesgoPosible;
    }

    public void setCodRiesgoPosible(Integer codRiesgoPosible) {
        this.codRiesgoPosible = codRiesgoPosible;
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
        if (!(object instanceof RiesgoPosible)) {
            return false;
        }
        RiesgoPosible other = (RiesgoPosible) object;
        if ((this.codRiesgoPosible == null && other.codRiesgoPosible != null) || (this.codRiesgoPosible != null && !this.codRiesgoPosible.equals(other.codRiesgoPosible))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz.RiesgoPosible[ codRiesgoPosible=" + codRiesgoPosible + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

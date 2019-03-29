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
@Table(name = "ElementosProteccion")
@NamedQueries({
    @NamedQuery(name = "ElementosProteccion.findAll", query = "SELECT ep FROM ElementosProteccion ep")})
public class ElementosProteccion implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_elemento")
    private Integer codElemento;
    @Column(name = "nombre")
    private String nombre;   
       
    public ElementosProteccion() {        
    }

    public ElementosProteccion(Integer codElemento, String nombre) {
        this.codElemento = codElemento;
        this.nombre = nombre;
    }

    public Integer getCodElemento() {
        return codElemento;
    }

    public void setCodElemento(Integer codElemento) {
        this.codElemento = codElemento;
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
        hash += (codElemento != null ? codElemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElementosProteccion)) {
            return false;
        }
        ElementosProteccion other = (ElementosProteccion) object;
        if ((this.codElemento == null && other.codElemento != null) || (this.codElemento != null && !this.codElemento.equals(other.codElemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz.ElementoProteccion[ codElemento= "+ codElemento + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

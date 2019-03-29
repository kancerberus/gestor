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
@Table(name = "NivelExposicion")
@NamedQueries({
    @NamedQuery(name = "NivelExposicion.findAll", query = "SELECT ne FROM NivelExposicion ne")})
public class NivelExposicion implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_nviel_exp")
    private Integer codNivelExp;           
    @Column(name = "nombre")
    private String nombre;   
    @Column(name = "valor")
    private Integer valor; 
    @Column(name = "descripcion")
    private String descripcion;   
       
    public NivelExposicion() {        
    }

    public NivelExposicion(Integer codNivelExp, String nombre, Integer valor, String descripcion) {
        this.codNivelExp = codNivelExp;
        this.nombre = nombre;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public Integer getCodNivelExp() {
        return codNivelExp;
    }

    public void setCodNivelExp(Integer codNivelExp) {
        this.codNivelExp = codNivelExp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codNivelExp != null ? codNivelExp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelExposicion)) {
            return false;
        }
        NivelExposicion other = (NivelExposicion) object;
        if ((this.codNivelExp == null && other.codNivelExp != null) || (this.codNivelExp != null && !this.codNivelExp.equals(other.codNivelExp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz.NivelExposicion[ codNivelExp=" + codNivelExp + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

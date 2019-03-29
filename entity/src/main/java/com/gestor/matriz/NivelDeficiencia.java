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
@Table(name = "NivelDeficiencia")
@NamedQueries({
    @NamedQuery(name = "NivelDeficiencia.findAll", query = "SELECT nd FROM NivelDeficiencia nd")})
public class NivelDeficiencia implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_nviel_def")
    private Integer codNivelDef;    
    @Column(name = "valor")
    private Integer valor;    
    @Column(name = "nombre")
    private String nombre;   
    @Column(name = "descripcion")
    private String descripcion;   
       
    public NivelDeficiencia() {        
    }

    public NivelDeficiencia(Integer codNivelDef, Integer valor, String nombre, String descripcion) {
        this.codNivelDef = codNivelDef;
        this.valor = valor;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    
    public Integer getCodNivelDef() {
        return codNivelDef;
    }

    public void setCodNivelDef(Integer codNivelDef) {
        this.codNivelDef = codNivelDef;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codNivelDef != null ? codNivelDef.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelDeficiencia)) {
            return false;
        }
        NivelDeficiencia other = (NivelDeficiencia) object;
        if ((this.codNivelDef == null && other.codNivelDef != null) || (this.codNivelDef != null && !this.codNivelDef.equals(other.codNivelDef))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz.NivelDeficiencia[ codNivelDef=" + codNivelDef + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

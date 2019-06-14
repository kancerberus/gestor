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
@Table(name = "gravedad")
@NamedQueries({
    @NamedQuery(name = "gravedad.findAll", query = "SELECT g FROM gravedad g")})
public class Gravedad implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_gravedad")
    private Integer codGravedad;
    @Column(name = "nombre")
    private String nombre;
    
    public Gravedad(){
    }

    public Gravedad(Integer codGravedad, String nombre) {
        this.codGravedad = codGravedad;
        this.nombre = nombre;
    }

    public Integer getCodGravedad() {
        return codGravedad;
    }

    public void setCodGravedad(Integer codGravedad) {
        this.codGravedad = codGravedad;
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
        if (!(object instanceof Gravedad)) {
            return false;
        }
        Gravedad other = (Gravedad) object;
        if ((this.codGravedad == null && other.codGravedad != null) || (this.codGravedad != null && !this.codGravedad.equals(other.codGravedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.Gravedad[ cod_gravedad=" + codGravedad + " ]";
    }
}
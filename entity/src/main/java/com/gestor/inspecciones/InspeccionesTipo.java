/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.inspecciones;

import com.gestor.gestor.*;
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
@Table(name = "inspecciones_tipo")
@NamedQueries({
    @NamedQuery(name = "InspeccionesTipo.findAll", query = "SELECT it FROM InspeccionesTipo it")})
public class InspeccionesTipo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_inspeccion")
    private Integer codInspeccion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public InspeccionesTipo() {
    }

    
    public InspeccionesTipo(Integer codInspeccion, String nombre) {
        this.codInspeccion= codInspeccion;
        this.nombre = nombre;
        
    }

    public Integer getCodInspeccion() {
        return codInspeccion;
    }

    public void setCodInspeccion(Integer codInspeccion) {
        this.codInspeccion = codInspeccion;
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
        if (!(object instanceof InspeccionesTipo)) {
            return false;
        }
        InspeccionesTipo other = (InspeccionesTipo) object;
        if ((this.codInspeccion == null && other.codInspeccion != null) || (this.codInspeccion != null && !this.codInspeccion.equals(other.codInspeccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.InspeccionesTipo[ cod_inspeccion=" + codInspeccion + " ]";
    }
    
}

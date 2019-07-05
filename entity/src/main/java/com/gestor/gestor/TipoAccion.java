/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

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
@Table(name = "tipo_accion")
@NamedQueries({
    @NamedQuery(name = "TipoAccion.findAll", query = "SELECT ta FROM TipoAccion ta")})
public class TipoAccion implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tipo_accion")
    private Integer codtipoaccion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "distribucion")
    private Float distribucion;

    public TipoAccion() {
    }

    
    public TipoAccion(int codtipoaccion, String nombre) {
        this.codtipoaccion= codtipoaccion;
        this.nombre = nombre;
        
    }

    public TipoAccion(Integer codtipoaccion, String nombre, Integer cantidad, Float distribucion) {
        this.codtipoaccion = codtipoaccion;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.distribucion = distribucion;
    }

    public Integer getCodtipoaccion() {
        return codtipoaccion;
    }

    public void setCodtipoaccion(Integer codtipoaccion) {
        this.codtipoaccion = codtipoaccion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(Float distribucion) {
        this.distribucion = distribucion;
    }

    public Integer getCodTipoaccion() {
        return codtipoaccion;
    }

    public void setCodTipoaccion(Integer codtipoaccion) {
        this.codtipoaccion = codtipoaccion;
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
        if (!(object instanceof TipoAccion)) {
            return false;
        }
        TipoAccion other = (TipoAccion) object;
        if ((this.codtipoaccion == null && other.codtipoaccion != null) || (this.codtipoaccion != null && !this.codtipoaccion.equals(other.codtipoaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.TipoAccion[ cod_tipo_accion=" + codtipoaccion + " ]";
    }
    
}

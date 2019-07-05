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
@Table(name = "clase_hallazgo")
@NamedQueries({
    @NamedQuery(name = "ClaseHallazgo.findAll", query = "SELECT ch FROM ClaseHallazgo ch")})
public class ClaseHallazgo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_clase_hallazgo")
    private Integer codclasehallazgo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cantidad")
    private Integer cantidad;    
    private Float distribucion;

    public ClaseHallazgo() {
    }

    
    public ClaseHallazgo(int codclasehallazgo, String nombre) {
        this.codclasehallazgo= codclasehallazgo;
        this.nombre = nombre;
        
    }

    public ClaseHallazgo(Integer codclasehallazgo, String nombre, Integer cantidad, Float distribucion) {
        this.codclasehallazgo = codclasehallazgo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.distribucion = distribucion;
    }

    
    public Integer getCodclasehallazgo() {
        return codclasehallazgo;
    }

    public void setCodclasehallazgo(Integer codclasehallazgo) {
        this.codclasehallazgo = codclasehallazgo;
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

    public Integer getCodClasehallazgo() {
        return codclasehallazgo;
    }

    public void setCodClasehallazgo(Integer codclasehallazgo) {
        this.codclasehallazgo = codclasehallazgo;
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
        if (!(object instanceof ClaseHallazgo)) {
            return false;
        }
        ClaseHallazgo other = (ClaseHallazgo) object;
        if ((this.codclasehallazgo == null && other.codclasehallazgo != null) || (this.codclasehallazgo != null && !this.codclasehallazgo.equals(other.codclasehallazgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.ClaseHallazgo[ cod_clase_hallazgo=" + codclasehallazgo + " ]";
    }
    
}

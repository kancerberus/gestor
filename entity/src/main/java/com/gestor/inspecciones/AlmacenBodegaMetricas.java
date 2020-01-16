/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.inspecciones;

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
@Table(name = "almacen_bodega_metricas")
@NamedQueries({
    @NamedQuery(name = "AlmacenBodegaMetricas.findAll", query = "SELECT abm FROM AlmacenBodegaMetricas abm")})
public class AlmacenBodegaMetricas implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_metrica")
    private Integer codMetrica;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    

    public AlmacenBodegaMetricas() {
    }

    public AlmacenBodegaMetricas(Integer codMetrica, String nombre) {
        this.codMetrica = codMetrica;
        this.nombre = nombre;        
    }

    public Integer getCodMetrica() {
        return codMetrica;
    }

    public void setCodMetrica(Integer codMetrica) {
        this.codMetrica = codMetrica;
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
        if (!(object instanceof AlmacenBodegaMetricas)) {
            return false;
        }
        AlmacenBodegaMetricas other = (AlmacenBodegaMetricas) object;
        if ((this.codMetrica == null && other.codMetrica != null) || (this.codMetrica != null && !this.codMetrica.equals(other.codMetrica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.inspecciones.AlmacenBodegaMetricas[ cod_metrica=" + codMetrica + " ]";
    }
    
}

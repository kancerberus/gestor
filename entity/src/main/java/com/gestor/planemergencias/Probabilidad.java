/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias;

import com.gestor.publico.CentroTrabajo;
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
@Table(name = "probabilidad")
@NamedQueries({
    @NamedQuery(name = "probabilidad.findAll", query = "SELECT p FROM probabilidad p")})
public class Probabilidad implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_probabilidad")
    private Integer codProbabilidad;
    @Column(name = "nombre")
    private String nombre;
    
    public Probabilidad(){
    }

    public Probabilidad(Integer codProbabilidad, String nombre) {
        this.codProbabilidad = codProbabilidad;
        this.nombre = nombre;
    }

    public Integer getCodProbabilidad() {
        return codProbabilidad;
    }

    public void setCodProbabilidad(Integer codProbabilidad) {
        this.codProbabilidad = codProbabilidad;
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
        if (!(object instanceof Probabilidad)) {
            return false;
        }
        Probabilidad other = (Probabilidad) object;
        if ((this.codProbabilidad == null && other.codProbabilidad != null) || (this.codProbabilidad != null && !this.codProbabilidad.equals(other.codProbabilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.AnalisisAmenazas[ cod_probabilidad=" + codProbabilidad + " ]";
    }
    
}
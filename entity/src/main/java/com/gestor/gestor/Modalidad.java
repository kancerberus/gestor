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
@Table(name = "modalidad")
@NamedQueries({
    @NamedQuery(name = "Modalidad.findAll", query = "SELECT mod FROM Modalidad mod")})
public class Modalidad implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_modalidad")
    private Integer codmodalidad;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public Modalidad() {
    }

    
    public Modalidad(int codmodalidad, String nombre) {
        this.codmodalidad= codmodalidad;
        this.nombre = nombre;
        
    }

    public Integer getCodModalidad() {
        return codmodalidad;
    }

    public void setCodModalidad(Integer codmodalidad) {
        this.codmodalidad = codmodalidad;
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
        if (!(object instanceof Modalidad)) {
            return false;
        }
        Modalidad other = (Modalidad) object;
        if ((this.codmodalidad == null && other.codmodalidad != null) || (this.codmodalidad != null && !this.codmodalidad.equals(other.codmodalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.Modalidad[ cod_modalidad=" + codmodalidad + " ]";
    }
    
}

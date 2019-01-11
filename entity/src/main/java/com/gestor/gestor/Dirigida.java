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
@Table(name = "dirigida")
@NamedQueries({
    @NamedQuery(name = "ClaseHallazgo.findAll", query = "SELECT d FROM Dirigida d")})
public class Dirigida implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_dirigida")
    private Integer coddirigida;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public Dirigida() {
    }

    
    public Dirigida(int coddirigida, String nombre) {
        this.coddirigida= coddirigida;
        this.nombre = nombre;
        
    }

    public Integer getCodDirigida() {
        return coddirigida;
    }

    public void setCodDirigida(Integer coddirigida) {
        this.coddirigida = coddirigida;
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
        if (!(object instanceof Dirigida)) {
            return false;
        }
        Dirigida other = (Dirigida) object;
        if ((this.coddirigida == null && other.coddirigida != null) || (this.coddirigida != null && !this.coddirigida.equals(other.coddirigida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.Dirigida[ cod_dirigida=" + coddirigida + " ]";
    }
    
}

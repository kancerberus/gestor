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
@Table(name = "recursos")
@NamedQueries({
    @NamedQuery(name = "Recursos.findAll", query = "SELECT r FROM Recursos r")})
public class Recursos implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_recursos")
    private Integer codrecursos;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public Recursos() {
    }

    
    public Recursos(int codrecursos, String nombre) {
        this.codrecursos= codrecursos;
        this.nombre = nombre;
        
    }

    public Integer getCodRecursos() {
        return codrecursos;
    }

    public void setCodRecursos(Integer codrecursos) {
        this.codrecursos = codrecursos;
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
        if (!(object instanceof Recursos)) {
            return false;
        }
        Recursos other = (Recursos) object;
        if ((this.codrecursos == null && other.codrecursos != null) || (this.codrecursos != null && !this.codrecursos.equals(other.codrecursos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.Recursos[ cod_recursos=" + codrecursos + " ]";
    }
    
}

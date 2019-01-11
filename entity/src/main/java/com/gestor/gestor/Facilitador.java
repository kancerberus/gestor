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
@Table(name = "facilitador")
@NamedQueries({
    @NamedQuery(name = "Facilitador.findAll", query = "SELECT f FROM Facilitador f")})
public class Facilitador implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "facilitador")
    private Integer codfacilitador;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public Facilitador() {
    }

    
    public Facilitador(int codfacilitador, String nombre) {
        this.codfacilitador= codfacilitador;
        this.nombre = nombre;
        
    }

    public Integer getCodFacilitador() {
        return codfacilitador;
    }

    public void setCodFacilitador(Integer codfacilitador) {
        this.codfacilitador = codfacilitador;
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
        if (!(object instanceof Facilitador)) {
            return false;
        }
        Facilitador other = (Facilitador) object;
        if ((this.codfacilitador == null && other.codfacilitador != null) || (this.codfacilitador != null && !this.codfacilitador.equals(other.codfacilitador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.Facilitador[ cod_facilitador=" + codfacilitador + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.matriz;

import com.gestor.publico.*;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.Table;

/**
 *
 * @author Franciso
 */
@Entity
@Table(name = "MedidasIntervencion")
@NamedQueries({
    @NamedQuery(name = "MedidasIntervencion.findAll", query = "SELECT mi FROM MedidasIntervencion mi")})
public class MedidasIntervencion implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_medida")
    private Integer codMedida;
    @Column(name = "nombre")
    private String nombre;   
       
    public MedidasIntervencion() {        
    }

    public MedidasIntervencion(Integer codMedida, String nombre) {
        this.codMedida = codMedida;
        this.nombre = nombre;
    }

    public Integer getCodMedida() {
        return codMedida;
    }

    public void setCodMedida(Integer codMedida) {
        this.codMedida = codMedida;
    }  

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMedida != null ? codMedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedidasIntervencion)) {
            return false;
        }
        MedidasIntervencion other = (MedidasIntervencion) object;
        if ((this.codMedida == null && other.codMedida != null) || (this.codMedida != null && !this.codMedida.equals(other.codMedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz.MedidaIntervencion[ codMedida=" + codMedida + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

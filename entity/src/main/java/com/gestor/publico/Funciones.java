/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

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
 * @author juliano
 */
@Entity
@Table(name = "funciones")
@NamedQueries({
    @NamedQuery(name = "Funciones.findAll", query = "SELECT fun FROM Funciones fun")})
public class Funciones implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_cargo")
    private Integer codCargo;
    @Column(name = "cod_funcion")
    private Integer codFuncion;
    @Column(name = "nombre")
    private String nombre;    
    
    
    private Cargos cargos= new Cargos();
       
    public Funciones() {        
    }

    public Funciones(Integer codCargo, Integer codFuncion, String nombre) {
        this.codCargo = codCargo;
        this.codFuncion = codFuncion;
        this.nombre = nombre;     
    }

    public Cargos getCargos() {
        return cargos;
    }

    public void setCargos(Cargos cargos) {
        this.cargos = cargos;
    }

    public Integer getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(Integer codCargo) {
        this.codCargo = codCargo;
    }

    public Integer getCodFuncion() {
        return codFuncion;
    }

    public void setCodFuncion(Integer codFuncion) {
        this.codFuncion = codFuncion;
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
        hash += (codFuncion != null ? codFuncion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funciones)) {
            return false;
        }
        Funciones other = (Funciones) object;
        if ((this.codFuncion == null && other.codFuncion != null) || (this.codFuncion != null && !this.codFuncion.equals(other.codFuncion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.Funciones[ codFuncion=" + codFuncion + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

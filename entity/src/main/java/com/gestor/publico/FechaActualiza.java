/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.Table;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "fecha_actualiza")
@NamedQueries({
    @NamedQuery(name = "FechaActualiza.findAll", query = "SELECT usuario FROM FechaActualiza fac")})
public class FechaActualiza implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha_actualizo")
    private Date fechaActualizo;
       
    public FechaActualiza() {        
    }

    public FechaActualiza(String usuario, String nombre, Date fechaActualizo) {        
        this.usuario = usuario;
        this.nombre = nombre;
        this.fechaActualizo = fechaActualizo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaActualizo() {
        return fechaActualizo;
    }

    public void setFechaActualizo(Date fechaActualizo) {
        this.fechaActualizo = fechaActualizo;
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
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FechaActualiza)) {
            return false;
        }
        FechaActualiza other = (FechaActualiza) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.FechaActualiza[ usuario=" + usuario + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

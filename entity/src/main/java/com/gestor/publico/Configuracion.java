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
 * @author franj
 */

@Entity
@Table(name = "configuracion")
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c")})
public class Configuracion implements Serializable, Cloneable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_configuracion")
    private Integer codConfiguracion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor")
    private String valor;
    @Column(name = "email_admin")
    private String email;
    
    public Configuracion() {
    }

    public Configuracion(Integer codConfiguracion, String nombre, String valor, String email) {
        this.codConfiguracion = codConfiguracion;
        this.nombre = nombre;
        this.valor = valor;
        this.email = email;
    }

    public Integer getCodConfiguracion() {
        return codConfiguracion;
    }

    public void setCodConfiguracion(Integer codConfiguracion) {
        this.codConfiguracion = codConfiguracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codConfiguracion != null ? codConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.codConfiguracion == null && other.codConfiguracion != null) || (this.codConfiguracion != null && !this.codConfiguracion.equals(other.codConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.Configuracion[ cod_configuracion=" + codConfiguracion + " ]";
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
}

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
@Table(name = "tipo_origen")
@NamedQueries({
    @NamedQuery(name = "tipo_origen.findAll", query = "SELECT to FROM tipo_origen to")})
public class TipoOrigen implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tipo_origen")
    private Integer codTipoOrigen;
    @Column(name = "nombre")
    private String nombre;

    public TipoOrigen(){
    }

    public TipoOrigen(Integer codTipoOrigen, String nombre) {
        this.codTipoOrigen = codTipoOrigen;
        this.nombre = nombre;
    }

    public Integer getCodTipoOrigen() {
        return codTipoOrigen;
    }

    public void setCodTipoOrigen(Integer codTipoOrigen) {
        this.codTipoOrigen = codTipoOrigen;
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
        if (!(object instanceof TipoOrigen)) {
            return false;
        }
        TipoOrigen other = (TipoOrigen) object;
        if ((this.codTipoOrigen == null && other.codTipoOrigen != null) || (this.codTipoOrigen != null && !this.codTipoOrigen.equals(other.codTipoOrigen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.TipoOrigen[ cod_tipo_origen=" + codTipoOrigen + " ]";
    }
    
}
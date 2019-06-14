/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias;

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
@Table(name = "rel_origen_tipo")
@NamedQueries({
    @NamedQuery(name = "rel_origen_tipo.findAll", query = "SELECT relot FROM rel_origen_tipo relot")})
public class RelOriegenTipo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tipo_origen")
    private Integer codTipoOrigen;
    @Basic(optional = false)
    @Column(name = "cod_origen")
    private Integer codOrigen;    
    @Column(name = "nombre")
    private String nombre;
    
    private TipoOrigen tipoOrigen;

    public RelOriegenTipo() {
    }

    public RelOriegenTipo(Integer codTipoOrigen, Integer codOrigen, String nombre) {
        this.codTipoOrigen = codTipoOrigen;
        this.codOrigen = codOrigen;
        this.nombre = nombre;
    }

    public TipoOrigen getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(TipoOrigen tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }

    public Integer getCodTipoOrigen() {
        return codTipoOrigen;
    }

    public void setCodTipoOrigen(Integer codTipoOrigen) {
        this.codTipoOrigen = codTipoOrigen;
    }

    public Integer getCodOrigen() {
        return codOrigen;
    }

    public void setCodOrigen(Integer codOrigen) {
        this.codOrigen = codOrigen;
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
        if (!(object instanceof RelOriegenTipo)) {
            return false;
        }
        RelOriegenTipo other = (RelOriegenTipo) object;
        if ((this.codOrigen == null && other.codOrigen != null) || (this.codOrigen != null && !this.codOrigen.equals(other.codOrigen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.RelOrigenTipo[ cod_origen=" + codOrigen + " ]";
    }
    
}

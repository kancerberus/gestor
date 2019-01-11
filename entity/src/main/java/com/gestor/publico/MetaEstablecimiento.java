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
@Table(name = "meta_etablecimiento")
@NamedQueries({
    @NamedQuery(name = "MetaEstablecimiento.findAll", query = "SELECT me FROM MetaEstablecimiento me")})
public class MetaEstablecimiento implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_meta")
    private Integer codMeta;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;    
    @Basic(optional = false)
    @Column(name = "cod_detalle")
    private Integer codDetalle;    
    @Column(name = "meta")
    private Integer meta;
        
    private ListaDetalle listaDetalle= new ListaDetalle();
    
    
    public MetaEstablecimiento() {        
    }

    public MetaEstablecimiento(Integer codMeta) {
        this.codMeta = codMeta;        
    }

    public MetaEstablecimiento(Integer codMeta, Integer meta, Integer codDetalle) {
        this.codMeta = codMeta;
        this.meta = meta;   
        this.codDetalle = codDetalle;
    }

    public MetaEstablecimiento(Integer codigoEstablecimiento, Integer codMeta, Integer meta, Integer codDetalle ) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codMeta = codMeta;
        this.meta = meta;
        this.codDetalle = codDetalle;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Integer getCodMeta() {
        return codMeta;
    }

    public void setCodMeta(Integer codMeta) {
        this.codMeta = codMeta;
    }

    public Integer getCodDetalle() {
        return codDetalle;
    }

    public void setCodDetalle(Integer codDetalle) {
        this.codDetalle = codDetalle;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }

    public ListaDetalle getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(ListaDetalle listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMeta != null ? codMeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetaEstablecimiento)) {
            return false;
        }
        MetaEstablecimiento other = (MetaEstablecimiento) object;
        if ((this.codMeta == null && other.codMeta != null) || (this.codMeta != null && !this.codMeta.equals(other.codMeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.MetaEstablecimiento[ codMeta=" + codMeta + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

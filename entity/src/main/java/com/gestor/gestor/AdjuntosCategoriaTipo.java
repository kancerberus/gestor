/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Julian D Osorio G
 */
@Entity
@Table(name = "adjuntos_categoria_tipo")
@NamedQueries({
    @NamedQuery(name = "AdjuntosCategoriaTipo.findAll", query = "SELECT a FROM AdjuntosCategoriaTipo a")})
public class AdjuntosCategoriaTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AdjuntosCategoriaTipoPK adjuntosCategoriaTipoPK = new AdjuntosCategoriaTipoPK();
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "cod_categoria", referencedColumnName = "cod_categoria", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AdjuntosCategoria adjuntosCategoria;

    public AdjuntosCategoriaTipo() {
        adjuntosCategoriaTipoPK = new AdjuntosCategoriaTipoPK();    
    }

    public AdjuntosCategoriaTipo(AdjuntosCategoriaTipoPK adjuntosCategoriaTipoPK) {
        this.adjuntosCategoriaTipoPK = adjuntosCategoriaTipoPK;
    }

    public AdjuntosCategoriaTipo(AdjuntosCategoriaTipoPK adjuntosCategoriaTipoPK, String nombre) {
        this.adjuntosCategoriaTipoPK = adjuntosCategoriaTipoPK;
        this.nombre = nombre;
    }
    
    public AdjuntosCategoriaTipo(AdjuntosCategoriaTipoPK adjuntosCategoriaTipoPK, String nombre, String descripcion) {
        this.adjuntosCategoriaTipoPK = adjuntosCategoriaTipoPK;
        this.nombre = nombre;
        this.descripcion= descripcion;
    }

    public AdjuntosCategoriaTipo(int codCategoria, int codCategoriaTipo) {
        this.adjuntosCategoriaTipoPK = new AdjuntosCategoriaTipoPK(codCategoria, codCategoriaTipo);
    }

    public AdjuntosCategoriaTipoPK getAdjuntosCategoriaTipoPK() {
        return adjuntosCategoriaTipoPK;
    }

    public void setAdjuntosCategoriaTipoPK(AdjuntosCategoriaTipoPK adjuntosCategoriaTipoPK) {
        this.adjuntosCategoriaTipoPK = adjuntosCategoriaTipoPK;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public AdjuntosCategoria getAdjuntosCategoria() {
        return adjuntosCategoria;
    }

    public void setAdjuntosCategoria(AdjuntosCategoria adjuntosCategoria) {
        this.adjuntosCategoria = adjuntosCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adjuntosCategoriaTipoPK != null ? adjuntosCategoriaTipoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdjuntosCategoriaTipo)) {
            return false;
        }
        AdjuntosCategoriaTipo other = (AdjuntosCategoriaTipo) object;
        if ((this.adjuntosCategoriaTipoPK == null && other.adjuntosCategoriaTipoPK != null) || (this.adjuntosCategoriaTipoPK != null && !this.adjuntosCategoriaTipoPK.equals(other.adjuntosCategoriaTipoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.AdjuntosCategoriaTipo[ adjuntosCategoriaTipoPK=" + adjuntosCategoriaTipoPK + " ]";
    }
    
}

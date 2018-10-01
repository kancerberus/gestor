/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Julian D Osorio G
 */
@Embeddable
public class AdjuntosCategoriaTipoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_categoria")
    private int codCategoria;
    @Basic(optional = false)
    @Column(name = "cod_categoria_tipo")
    private Integer codCategoriaTipo;

    public AdjuntosCategoriaTipoPK() {
    }

    public AdjuntosCategoriaTipoPK(int codCategoria, Integer codCategoriaTipo) {
        this.codCategoria = codCategoria;
        this.codCategoriaTipo = codCategoriaTipo;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Integer getCodCategoriaTipo() {
        return codCategoriaTipo;
    }

    public void setCodCategoriaTipo(Integer codCategoriaTipo) {
        this.codCategoriaTipo = codCategoriaTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codCategoria;
        hash += (int) codCategoriaTipo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdjuntosCategoriaTipoPK)) {
            return false;
        }
        AdjuntosCategoriaTipoPK other = (AdjuntosCategoriaTipoPK) object;
        if (this.codCategoria != other.codCategoria) {
            return false;
        }
        if (this.codCategoriaTipo != other.codCategoriaTipo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.AdjuntosCategoriaTipoPK[ codCategoria=" + codCategoria + ", codCategoriaTipo=" + codCategoriaTipo + " ]";
    }
    
}

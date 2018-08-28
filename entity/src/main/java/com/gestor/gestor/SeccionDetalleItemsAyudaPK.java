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
 * @author juliano
 */
@Embeddable
public class SeccionDetalleItemsAyudaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_ciclo")
    private String codCiclo;
    @Basic(optional = false)
    @Column(name = "cod_seccion")
    private int codSeccion;
    @Basic(optional = false)
    @Column(name = "cod_detalle")
    private int codDetalle;
    @Basic(optional = false)
    @Column(name = "cod_item")
    private int codItem;
    @Basic(optional = false)
    @Column(name = "cod_ayuda")
    private int codAyuda;

    public SeccionDetalleItemsAyudaPK() {
    }
    
    public SeccionDetalleItemsAyudaPK(String codCiclo, int codSeccion, int codDetalle, int codItem) {
        this.codCiclo = codCiclo;
        this.codSeccion = codSeccion;
        this.codDetalle = codDetalle;
        this.codItem = codItem;
    }

    public SeccionDetalleItemsAyudaPK(String codCiclo, int codSeccion, int codDetalle, int codItem, int codAyuda) {
        this.codCiclo = codCiclo;
        this.codSeccion = codSeccion;
        this.codDetalle = codDetalle;
        this.codItem = codItem;
        this.codAyuda = codAyuda;
    }

    public String getCodCiclo() {
        return codCiclo;
    }

    public void setCodCiclo(String codCiclo) {
        this.codCiclo = codCiclo;
    }

    public int getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(int codSeccion) {
        this.codSeccion = codSeccion;
    }

    public int getCodDetalle() {
        return codDetalle;
    }

    public void setCodDetalle(int codDetalle) {
        this.codDetalle = codDetalle;
    }

    public int getCodItem() {
        return codItem;
    }

    public void setCodItem(int codItem) {
        this.codItem = codItem;
    }

    public int getCodAyuda() {
        return codAyuda;
    }

    public void setCodAyuda(int codAyuda) {
        this.codAyuda = codAyuda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCiclo != null ? codCiclo.hashCode() : 0);
        hash += (int) codSeccion;
        hash += (int) codDetalle;
        hash += (int) codItem;
        hash += (int) codAyuda;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeccionDetalleItemsAyudaPK)) {
            return false;
        }
        SeccionDetalleItemsAyudaPK other = (SeccionDetalleItemsAyudaPK) object;
        if ((this.codCiclo == null && other.codCiclo != null) || (this.codCiclo != null && !this.codCiclo.equals(other.codCiclo))) {
            return false;
        }
        if (this.codSeccion != other.codSeccion) {
            return false;
        }
        if (this.codDetalle != other.codDetalle) {
            return false;
        }
        if (this.codItem != other.codItem) {
            return false;
        }
        if (this.codAyuda != other.codAyuda) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.SeccionDetalleItemsAyudaPK[ codCiclo=" + codCiclo + ", codSeccion=" + codSeccion + ", codDetalle=" + codDetalle + ", codItem=" + codItem + ", codAyuda=" + codAyuda + " ]";
    }
    
}

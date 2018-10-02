/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Julian D Osorio G
 */
@Embeddable
public class PlanSeccionDetalleItemTextoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_titulo")
    private int codTitulo;
    @Basic(optional = false)
    @Column(name = "cod_seccion")
    private int codSeccion;
    @Basic(optional = false)
    @Column(name = "cod_seccion_detalle")
    private int codSeccionDetalle;
    @Basic(optional = false)
    @Column(name = "cod_seccion_detalle_item")
    private int codSeccionDetalleItem;
    @Basic(optional = false)
    @Column(name = "cod_seccion_detalle_item_texto")
    private int codSeccionDetalleItemTexto;

    public PlanSeccionDetalleItemTextoPK() {
    }

    public PlanSeccionDetalleItemTextoPK(short codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem, int codSeccionDetalleItemTexto) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codTitulo = codTitulo;
        this.codSeccion = codSeccion;
        this.codSeccionDetalle = codSeccionDetalle;
        this.codSeccionDetalleItem = codSeccionDetalleItem;
        this.codSeccionDetalleItemTexto = codSeccionDetalleItemTexto;
    }

    public short getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(short codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public int getCodTitulo() {
        return codTitulo;
    }

    public void setCodTitulo(int codTitulo) {
        this.codTitulo = codTitulo;
    }

    public int getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(int codSeccion) {
        this.codSeccion = codSeccion;
    }

    public int getCodSeccionDetalle() {
        return codSeccionDetalle;
    }

    public void setCodSeccionDetalle(int codSeccionDetalle) {
        this.codSeccionDetalle = codSeccionDetalle;
    }

    public int getCodSeccionDetalleItem() {
        return codSeccionDetalleItem;
    }

    public void setCodSeccionDetalleItem(int codSeccionDetalleItem) {
        this.codSeccionDetalleItem = codSeccionDetalleItem;
    }

    public int getCodSeccionDetalleItemTexto() {
        return codSeccionDetalleItemTexto;
    }

    public void setCodSeccionDetalleItemTexto(int codSeccionDetalleItemTexto) {
        this.codSeccionDetalleItemTexto = codSeccionDetalleItemTexto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoEstablecimiento;
        hash += (int) codTitulo;
        hash += (int) codSeccion;
        hash += (int) codSeccionDetalle;
        hash += (int) codSeccionDetalleItem;
        hash += (int) codSeccionDetalleItemTexto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleItemTextoPK)) {
            return false;
        }
        PlanSeccionDetalleItemTextoPK other = (PlanSeccionDetalleItemTextoPK) object;
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codTitulo != other.codTitulo) {
            return false;
        }
        if (this.codSeccion != other.codSeccion) {
            return false;
        }
        if (this.codSeccionDetalle != other.codSeccionDetalle) {
            return false;
        }
        if (this.codSeccionDetalleItem != other.codSeccionDetalleItem) {
            return false;
        }
        if (this.codSeccionDetalleItemTexto != other.codSeccionDetalleItemTexto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleItemTextoPK[ codigoEstablecimiento=" + codigoEstablecimiento + ", codTitulo=" + codTitulo + ", codSeccion=" + codSeccion + ", codSeccionDetalle=" + codSeccionDetalle + ", codSeccionDetalleItem=" + codSeccionDetalleItem + ", codSeccionDetalleItemTexto=" + codSeccionDetalleItemTexto + " ]";
    }
    
}

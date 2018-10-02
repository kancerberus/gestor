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
public class PlanMaestroPlanSeccionDetalleItemAdjuntosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private int codEvaluacion;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_maestro")
    private int codMaestro;
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
    @Column(name = "cod_seccion_detalle_item_adjuntos")
    private int codSeccionDetalleItemAdjuntos;

    public PlanMaestroPlanSeccionDetalleItemAdjuntosPK() {
    }

    public PlanMaestroPlanSeccionDetalleItemAdjuntosPK(int codEvaluacion, short codigoEstablecimiento, int codMaestro, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem, int codSeccionDetalleItemAdjuntos) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codMaestro = codMaestro;
        this.codTitulo = codTitulo;
        this.codSeccion = codSeccion;
        this.codSeccionDetalle = codSeccionDetalle;
        this.codSeccionDetalleItem = codSeccionDetalleItem;
        this.codSeccionDetalleItemAdjuntos = codSeccionDetalleItemAdjuntos;
    }

    public int getCodEvaluacion() {
        return codEvaluacion;
    }

    public void setCodEvaluacion(int codEvaluacion) {
        this.codEvaluacion = codEvaluacion;
    }

    public short getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(short codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public int getCodMaestro() {
        return codMaestro;
    }

    public void setCodMaestro(int codMaestro) {
        this.codMaestro = codMaestro;
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

    public int getCodSeccionDetalleItemAdjuntos() {
        return codSeccionDetalleItemAdjuntos;
    }

    public void setCodSeccionDetalleItemAdjuntos(int codSeccionDetalleItemAdjuntos) {
        this.codSeccionDetalleItemAdjuntos = codSeccionDetalleItemAdjuntos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += (int) codMaestro;
        hash += (int) codTitulo;
        hash += (int) codSeccion;
        hash += (int) codSeccionDetalle;
        hash += (int) codSeccionDetalleItem;
        hash += (int) codSeccionDetalleItemAdjuntos;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMaestroPlanSeccionDetalleItemAdjuntosPK)) {
            return false;
        }
        PlanMaestroPlanSeccionDetalleItemAdjuntosPK other = (PlanMaestroPlanSeccionDetalleItemAdjuntosPK) object;
        if (this.codEvaluacion != other.codEvaluacion) {
            return false;
        }
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codMaestro != other.codMaestro) {
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
        if (this.codSeccionDetalleItemAdjuntos != other.codSeccionDetalleItemAdjuntos) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanMaestroPlanSeccionDetalleItemAdjuntosPK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codMaestro=" + codMaestro + ", codTitulo=" + codTitulo + ", codSeccion=" + codSeccion + ", codSeccionDetalle=" + codSeccionDetalle + ", codSeccionDetalleItem=" + codSeccionDetalleItem + ", codSeccionDetalleItemAdjuntos=" + codSeccionDetalleItemAdjuntos + " ]";
    }
    
}

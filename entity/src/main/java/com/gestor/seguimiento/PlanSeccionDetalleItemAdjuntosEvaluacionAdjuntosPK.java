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
public class PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK implements Serializable {

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
    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private int codEvaluacion;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_ciclo")
    private String codCiclo;
    @Basic(optional = false)
    @Column(name = "cod_detalle")
    private int codDetalle;
    @Basic(optional = false)
    @Column(name = "cod_item")
    private int codItem;
    @Basic(optional = false)
    @Column(name = "cod_adjunto")
    private int codAdjunto;

    public PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK() {
    }

    public PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK(int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem, int codSeccionDetalleItemAdjuntos, int codEvaluacion, short codigoEstablecimiento, String codCiclo, int codDetalle, int codItem, int codAdjunto) {
        this.codTitulo = codTitulo;
        this.codSeccion = codSeccion;
        this.codSeccionDetalle = codSeccionDetalle;
        this.codSeccionDetalleItem = codSeccionDetalleItem;
        this.codSeccionDetalleItemAdjuntos = codSeccionDetalleItemAdjuntos;
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCiclo = codCiclo;
        this.codDetalle = codDetalle;
        this.codItem = codItem;
        this.codAdjunto = codAdjunto;
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

    public String getCodCiclo() {
        return codCiclo;
    }

    public void setCodCiclo(String codCiclo) {
        this.codCiclo = codCiclo;
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

    public int getCodAdjunto() {
        return codAdjunto;
    }

    public void setCodAdjunto(int codAdjunto) {
        this.codAdjunto = codAdjunto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codTitulo;
        hash += (int) codSeccion;
        hash += (int) codSeccionDetalle;
        hash += (int) codSeccionDetalleItem;
        hash += (int) codSeccionDetalleItemAdjuntos;
        hash += (int) codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += (codCiclo != null ? codCiclo.hashCode() : 0);
        hash += (int) codDetalle;
        hash += (int) codItem;
        hash += (int) codAdjunto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK)) {
            return false;
        }
        PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK other = (PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK) object;
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
        if (this.codEvaluacion != other.codEvaluacion) {
            return false;
        }
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if ((this.codCiclo == null && other.codCiclo != null) || (this.codCiclo != null && !this.codCiclo.equals(other.codCiclo))) {
            return false;
        }
        if (this.codDetalle != other.codDetalle) {
            return false;
        }
        if (this.codItem != other.codItem) {
            return false;
        }
        if (this.codAdjunto != other.codAdjunto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleItemAdjuntosEvaluacionAdjuntosPK[ codTitulo=" + codTitulo + ", codSeccion=" + codSeccion + ", codSeccionDetalle=" + codSeccionDetalle + ", codSeccionDetalleItem=" + codSeccionDetalleItem + ", codSeccionDetalleItemAdjuntos=" + codSeccionDetalleItemAdjuntos + ", codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codCiclo=" + codCiclo + ", codDetalle=" + codDetalle + ", codItem=" + codItem + ", codAdjunto=" + codAdjunto + " ]";
    }
    
}

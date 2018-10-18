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
public class PlanSeccionDetalleAdjuntosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private int codigoEstablecimiento;
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
    @Column(name = "cod_seccion_detalle_adjuntos")
    private int codSeccionDetalleAdjuntos;

    public PlanSeccionDetalleAdjuntosPK() {
    }

    public PlanSeccionDetalleAdjuntosPK(int codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleAdjuntos) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codTitulo = codTitulo;
        this.codSeccion = codSeccion;
        this.codSeccionDetalle = codSeccionDetalle;
        this.codSeccionDetalleAdjuntos = codSeccionDetalleAdjuntos;
    }

    public int getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(int codigoEstablecimiento) {
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

    public int getCodSeccionDetalleAdjuntos() {
        return codSeccionDetalleAdjuntos;
    }

    public void setCodSeccionDetalleAdjuntos(int codSeccionDetalleAdjuntos) {
        this.codSeccionDetalleAdjuntos = codSeccionDetalleAdjuntos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoEstablecimiento;
        hash += (int) codTitulo;
        hash += (int) codSeccion;
        hash += (int) codSeccionDetalle;
        hash += (int) codSeccionDetalleAdjuntos;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleAdjuntosPK)) {
            return false;
        }
        PlanSeccionDetalleAdjuntosPK other = (PlanSeccionDetalleAdjuntosPK) object;
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
        if (this.codSeccionDetalleAdjuntos != other.codSeccionDetalleAdjuntos) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleAdjuntosPK[ codigoEstablecimiento=" + codigoEstablecimiento + ", codTitulo=" + codTitulo + ", codSeccion=" + codSeccion + ", codSeccionDetalle=" + codSeccionDetalle + ", codSeccionDetalleAdjuntos=" + codSeccionDetalleAdjuntos + " ]";
    }
    
}

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
public class PlanTituloAdiuntosEvaluacionAdjuntosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_titulo_adjunto")
    private int codTituloAdjunto;
    @Basic(optional = false)
    @Column(name = "cod_titulo")
    private int codTitulo;
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
    @Column(name = "cod_seccion")
    private int codSeccion;
    @Basic(optional = false)
    @Column(name = "cod_detalle")
    private int codDetalle;
    @Basic(optional = false)
    @Column(name = "cod_item")
    private int codItem;
    @Basic(optional = false)
    @Column(name = "cod_adjunto")
    private int codAdjunto;

    public PlanTituloAdiuntosEvaluacionAdjuntosPK() {
    }

    public PlanTituloAdiuntosEvaluacionAdjuntosPK(int codTituloAdjunto, int codTitulo, int codEvaluacion, short codigoEstablecimiento, String codCiclo, int codSeccion, int codDetalle, int codItem, int codAdjunto) {
        this.codTituloAdjunto = codTituloAdjunto;
        this.codTitulo = codTitulo;
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCiclo = codCiclo;
        this.codSeccion = codSeccion;
        this.codDetalle = codDetalle;
        this.codItem = codItem;
        this.codAdjunto = codAdjunto;
    }

    public int getCodTituloAdjunto() {
        return codTituloAdjunto;
    }

    public void setCodTituloAdjunto(int codTituloAdjunto) {
        this.codTituloAdjunto = codTituloAdjunto;
    }

    public int getCodTitulo() {
        return codTitulo;
    }

    public void setCodTitulo(int codTitulo) {
        this.codTitulo = codTitulo;
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

    public int getCodAdjunto() {
        return codAdjunto;
    }

    public void setCodAdjunto(int codAdjunto) {
        this.codAdjunto = codAdjunto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codTituloAdjunto;
        hash += (int) codTitulo;
        hash += (int) codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += (codCiclo != null ? codCiclo.hashCode() : 0);
        hash += (int) codSeccion;
        hash += (int) codDetalle;
        hash += (int) codItem;
        hash += (int) codAdjunto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTituloAdiuntosEvaluacionAdjuntosPK)) {
            return false;
        }
        PlanTituloAdiuntosEvaluacionAdjuntosPK other = (PlanTituloAdiuntosEvaluacionAdjuntosPK) object;
        if (this.codTituloAdjunto != other.codTituloAdjunto) {
            return false;
        }
        if (this.codTitulo != other.codTitulo) {
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
        if (this.codSeccion != other.codSeccion) {
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
        return "com.gestor.seguimiento.PlanTituloAdiuntosEvaluacionAdjuntosPK[ codTituloAdjunto=" + codTituloAdjunto + ", codTitulo=" + codTitulo + ", codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codCiclo=" + codCiclo + ", codSeccion=" + codSeccion + ", codDetalle=" + codDetalle + ", codItem=" + codItem + ", codAdjunto=" + codAdjunto + " ]";
    }
    
}

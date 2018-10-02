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
public class PlanMaestroPlanSeccionAdjuntosPK implements Serializable {

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
    @Column(name = "cod_seccion_adjunto")
    private int codSeccionAdjunto;

    public PlanMaestroPlanSeccionAdjuntosPK() {
    }

    public PlanMaestroPlanSeccionAdjuntosPK(int codEvaluacion, short codigoEstablecimiento, int codMaestro, int codTitulo, int codSeccion, int codSeccionAdjunto) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codMaestro = codMaestro;
        this.codTitulo = codTitulo;
        this.codSeccion = codSeccion;
        this.codSeccionAdjunto = codSeccionAdjunto;
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

    public int getCodSeccionAdjunto() {
        return codSeccionAdjunto;
    }

    public void setCodSeccionAdjunto(int codSeccionAdjunto) {
        this.codSeccionAdjunto = codSeccionAdjunto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += (int) codMaestro;
        hash += (int) codTitulo;
        hash += (int) codSeccion;
        hash += (int) codSeccionAdjunto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMaestroPlanSeccionAdjuntosPK)) {
            return false;
        }
        PlanMaestroPlanSeccionAdjuntosPK other = (PlanMaestroPlanSeccionAdjuntosPK) object;
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
        if (this.codSeccionAdjunto != other.codSeccionAdjunto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanMaestroPlanSeccionAdjuntosPK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codMaestro=" + codMaestro + ", codTitulo=" + codTitulo + ", codSeccion=" + codSeccion + ", codSeccionAdjunto=" + codSeccionAdjunto + " ]";
    }
    
}

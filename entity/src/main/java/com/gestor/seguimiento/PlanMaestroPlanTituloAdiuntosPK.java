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
public class PlanMaestroPlanTituloAdiuntosPK implements Serializable {

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
    @Column(name = "cod_titulo_adjunto")
    private int codTituloAdjunto;
    @Basic(optional = false)
    @Column(name = "cod_titulo")
    private int codTitulo;

    public PlanMaestroPlanTituloAdiuntosPK() {
    }

    public PlanMaestroPlanTituloAdiuntosPK(int codEvaluacion, short codigoEstablecimiento, int codMaestro, int codTituloAdjunto, int codTitulo) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codMaestro = codMaestro;
        this.codTituloAdjunto = codTituloAdjunto;
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

    public int getCodMaestro() {
        return codMaestro;
    }

    public void setCodMaestro(int codMaestro) {
        this.codMaestro = codMaestro;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += (int) codMaestro;
        hash += (int) codTituloAdjunto;
        hash += (int) codTitulo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMaestroPlanTituloAdiuntosPK)) {
            return false;
        }
        PlanMaestroPlanTituloAdiuntosPK other = (PlanMaestroPlanTituloAdiuntosPK) object;
        if (this.codEvaluacion != other.codEvaluacion) {
            return false;
        }
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codMaestro != other.codMaestro) {
            return false;
        }
        if (this.codTituloAdjunto != other.codTituloAdjunto) {
            return false;
        }
        if (this.codTitulo != other.codTitulo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanMaestroPlanTituloAdiuntosPK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codMaestro=" + codMaestro + ", codTituloAdjunto=" + codTituloAdjunto + ", codTitulo=" + codTitulo + " ]";
    }
    
}

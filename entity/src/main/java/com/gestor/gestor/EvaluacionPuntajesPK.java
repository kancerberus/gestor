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
public class EvaluacionPuntajesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private int codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;
    @Basic(optional = false)
    @Column(name = "cod_puntaje")
    private String codPuntaje;

    public EvaluacionPuntajesPK() {
    }

    public EvaluacionPuntajesPK(int codigoEstablecimiento, Long codEvaluacion, String codPuntaje) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codEvaluacion = codEvaluacion;
        this.codPuntaje = codPuntaje;
    }

    public int getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(int codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Long getCodEvaluacion() {
        return codEvaluacion;
    }

    public void setCodEvaluacion(Long codEvaluacion) {
        this.codEvaluacion = codEvaluacion;
    }

    public String getCodPuntaje() {
        return codPuntaje;
    }

    public void setCodPuntaje(String codPuntaje) {
        this.codPuntaje = codPuntaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoEstablecimiento;
        hash += codEvaluacion;
        hash += (codPuntaje != null ? codPuntaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPuntajesPK)) {
            return false;
        }
        EvaluacionPuntajesPK other = (EvaluacionPuntajesPK) object;
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codEvaluacion != other.codEvaluacion) {
            return false;
        }
        if ((this.codPuntaje == null && other.codPuntaje != null) || (this.codPuntaje != null && !this.codPuntaje.equals(other.codPuntaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionPuntajesPK[ codigoEstablecimiento=" + codigoEstablecimiento + ", codEvaluacion=" + codEvaluacion + ", codPuntaje=" + codPuntaje + " ]";
    }

}

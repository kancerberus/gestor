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
public class EvaluacionCapacitacionDetalleNotasPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private int codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_capacitacion")
    private Long codCapacitacion;
    @Basic(optional = false)
    @Column(name = "cod_capacitacion_detalle")
    private Long codCapacitacionDetalle;
    @Basic(optional = false)
    @Column(name = "cod_nota")
    private int codNota;

    public EvaluacionCapacitacionDetalleNotasPK() {
    }

    public EvaluacionCapacitacionDetalleNotasPK(Long codEvaluacion, int codigoEstablecimiento, Long codCapacitacion, Long codCapacitacionDetalle) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCapacitacion = codCapacitacion;
        this.codCapacitacionDetalle = codCapacitacionDetalle;
    }

    public EvaluacionCapacitacionDetalleNotasPK(Long codEvaluacion, int codigoEstablecimiento, Long codCapacitacion, Long codCapacitacionDetalle, int codNota) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCapacitacion = codCapacitacion;
        this.codCapacitacionDetalle = codCapacitacionDetalle;
        this.codNota = codNota;
    }

    public Long getCodEvaluacion() {
        return codEvaluacion;
    }

    public void setCodEvaluacion(Long codEvaluacion) {
        this.codEvaluacion = codEvaluacion;
    }

    public int getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(int codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Long getCodCapacitacion() {
        return codCapacitacion;
    }

    public void setCodCapacitacion(Long codCapacitacion) {
        this.codCapacitacion = codCapacitacion;
    }

    public Long getCodCapacitacionDetalle() {
        return codCapacitacionDetalle;
    }

    public void setCodCapacitacionDetalle(Long codCapacitacionDetalle) {
        this.codCapacitacionDetalle = codCapacitacionDetalle;
    }

    public int getCodNota() {
        return codNota;
    }

    public void setCodNota(int codNota) {
        this.codNota = codNota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += codCapacitacion;
        hash += codCapacitacionDetalle;
        hash += (int) codNota;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionCapacitacionDetalleNotasPK)) {
            return false;
        }
        EvaluacionCapacitacionDetalleNotasPK other = (EvaluacionCapacitacionDetalleNotasPK) object;
        if (this.codEvaluacion != other.codEvaluacion) {
            return false;
        }
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codCapacitacion != other.codCapacitacion) {
            return false;
        }
        if (this.codCapacitacionDetalle != other.codCapacitacionDetalle) {
            return false;
        }
        if (this.codNota != other.codNota) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionCapacitacionDetalleNotasPK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codCapacitacion=" + codCapacitacion + ", codCapacitacionDetalle=" + codCapacitacionDetalle + ", codNota=" + codNota + " ]";
    }

}

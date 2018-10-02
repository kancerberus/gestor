/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Julian D Osorio G
 */
@Embeddable
public class PlanMaestroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_evaluacion")
    private Long codEvaluacion;
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private int codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_maestro")
    private Long codMaestro;

    public PlanMaestroPK() {
    }

    public PlanMaestroPK(Long codEvaluacion, int codigoEstablecimiento, Long codMaestro) {
        this.codEvaluacion = codEvaluacion;
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codMaestro = codMaestro;
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

    public Long getCodMaestro() {
        return codMaestro;
    }

    public void setCodMaestro(Long codMaestro) {
        this.codMaestro = codMaestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += codEvaluacion;
        hash += (int) codigoEstablecimiento;
        hash += codMaestro;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMaestroPK)) {
            return false;
        }
        PlanMaestroPK other = (PlanMaestroPK) object;
        if (!Objects.equals(this.codEvaluacion, other.codEvaluacion)) {
            return false;
        }
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        return Objects.equals(this.codMaestro, other.codMaestro);
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanMaestroPK[ codEvaluacion=" + codEvaluacion + ", codigoEstablecimiento=" + codigoEstablecimiento + ", codMaestro=" + codMaestro + " ]";
    }

}

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
public class PlanSeccionTextoPK implements Serializable {

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
    @Column(name = "cod_seccion_texto")
    private int codSeccionTexto;

    public PlanSeccionTextoPK() {
    }

    public PlanSeccionTextoPK(short codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionTexto) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codTitulo = codTitulo;
        this.codSeccion = codSeccion;
        this.codSeccionTexto = codSeccionTexto;
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

    public int getCodSeccionTexto() {
        return codSeccionTexto;
    }

    public void setCodSeccionTexto(int codSeccionTexto) {
        this.codSeccionTexto = codSeccionTexto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoEstablecimiento;
        hash += (int) codTitulo;
        hash += (int) codSeccion;
        hash += (int) codSeccionTexto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionTextoPK)) {
            return false;
        }
        PlanSeccionTextoPK other = (PlanSeccionTextoPK) object;
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codTitulo != other.codTitulo) {
            return false;
        }
        if (this.codSeccion != other.codSeccion) {
            return false;
        }
        if (this.codSeccionTexto != other.codSeccionTexto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionTextoPK[ codigoEstablecimiento=" + codigoEstablecimiento + ", codTitulo=" + codTitulo + ", codSeccion=" + codSeccion + ", codSeccionTexto=" + codSeccionTexto + " ]";
    }
    
}

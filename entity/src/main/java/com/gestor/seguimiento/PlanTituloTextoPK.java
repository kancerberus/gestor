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
public class PlanTituloTextoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_titulo")
    private int codTitulo;
    @Basic(optional = false)
    @Column(name = "cod_titulo_texto")
    private int codTituloTexto;

    public PlanTituloTextoPK() {
    }

    public PlanTituloTextoPK(short codigoEstablecimiento, int codTitulo, int codTituloTexto) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codTitulo = codTitulo;
        this.codTituloTexto = codTituloTexto;
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

    public int getCodTituloTexto() {
        return codTituloTexto;
    }

    public void setCodTituloTexto(int codTituloTexto) {
        this.codTituloTexto = codTituloTexto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoEstablecimiento;
        hash += (int) codTitulo;
        hash += (int) codTituloTexto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTituloTextoPK)) {
            return false;
        }
        PlanTituloTextoPK other = (PlanTituloTextoPK) object;
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if (this.codTitulo != other.codTitulo) {
            return false;
        }
        if (this.codTituloTexto != other.codTituloTexto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanTituloTextoPK[ codigoEstablecimiento=" + codigoEstablecimiento + ", codTitulo=" + codTitulo + ", codTituloTexto=" + codTituloTexto + " ]";
    }
    
}

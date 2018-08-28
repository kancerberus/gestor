/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author juliano
 */
@Embeddable
public class RelUsuariosEstablecimientoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private short codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "documento_usuario")
    private String documentoUsuario;

    public RelUsuariosEstablecimientoPK() {
    }

    public RelUsuariosEstablecimientoPK(short codigoEstablecimiento, String documentoUsuario) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.documentoUsuario = documentoUsuario;
    }

    public short getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(short codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoEstablecimiento;
        hash += (documentoUsuario != null ? documentoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelUsuariosEstablecimientoPK)) {
            return false;
        }
        RelUsuariosEstablecimientoPK other = (RelUsuariosEstablecimientoPK) object;
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if ((this.documentoUsuario == null && other.documentoUsuario != null) || (this.documentoUsuario != null && !this.documentoUsuario.equals(other.documentoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.RelUsuariosEstablecimientoPK[ codigoEstablecimiento=" + codigoEstablecimiento + ", documentoUsuario=" + documentoUsuario + " ]";
    }
    
}

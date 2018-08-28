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
public class PrivilegiosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_transaccion")
    private long codTransaccion;
    @Basic(optional = false)
    @Column(name = "cod_operacion")
    private int codOperacion;
    @Basic(optional = false)
    @Column(name = "cod_rol")
    private long codRol;

    public PrivilegiosPK() {
    }

    public PrivilegiosPK(long codTransaccion, int codOperacion, long codRol) {
        this.codTransaccion = codTransaccion;
        this.codOperacion = codOperacion;
        this.codRol = codRol;
    }

    public long getCodTransaccion() {
        return codTransaccion;
    }

    public void setCodTransaccion(long codTransaccion) {
        this.codTransaccion = codTransaccion;
    }

    public int getCodOperacion() {
        return codOperacion;
    }

    public void setCodOperacion(int codOperacion) {
        this.codOperacion = codOperacion;
    }

    public long getCodRol() {
        return codRol;
    }

    public void setCodRol(long codRol) {
        this.codRol = codRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codTransaccion;
        hash += (int) codOperacion;
        hash += (int) codRol;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrivilegiosPK)) {
            return false;
        }
        PrivilegiosPK other = (PrivilegiosPK) object;
        if (this.codTransaccion != other.codTransaccion) {
            return false;
        }
        if (this.codOperacion != other.codOperacion) {
            return false;
        }
        if (this.codRol != other.codRol) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.PrivilegiosPK[ codTransaccion=" + codTransaccion + ", codOperacion=" + codOperacion + ", codRol=" + codRol + " ]";
    }
    
}

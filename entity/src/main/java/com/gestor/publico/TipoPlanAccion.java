/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "tipo_plan_accion")
@NamedQueries({
    @NamedQuery(name = "TipoPlanAccion.findAll", query = "SELECT tpaccion FROM TipoPlanAccion tpaccion")})
public class TipoPlanAccion implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_tipo_plan_accion")
    private Integer codTipoPlanAccion;
    @Column(name = "nombre")
    private String nombre;   

    public TipoPlanAccion() {        
    }

    public TipoPlanAccion(Integer codTipoPlanAccion, String nombre) {
        this.codTipoPlanAccion = codTipoPlanAccion;
        this.nombre = nombre;
    }

    public Integer getCodTipoPlanAccion() {
        return codTipoPlanAccion;
    }

    public void setCodTipoPlanAccion(Integer codTipoPlanAccion) {
        this.codTipoPlanAccion = codTipoPlanAccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTipoPlanAccion != null ? codTipoPlanAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPlanAccion)) {
            return false;
        }
        TipoPlanAccion other = (TipoPlanAccion) object;
        if ((this.codTipoPlanAccion == null && other.codTipoPlanAccion != null) || (this.codTipoPlanAccion != null && !this.codTipoPlanAccion.equals(other.codTipoPlanAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.TipoPlanAccion[ codTipoPlanAccion=" + codTipoPlanAccion + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

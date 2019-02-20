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
@Table(name = "centro_trabajo")
@NamedQueries({
    @NamedQuery(name = "CentroTrabajo.findAll", query = "SELECT ct FROM CentroTrabajo ct")})
public class CentroTrabajo implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    @Column(name = "cod_centrotrabajo")
    private Integer codCentrotrabajo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "nit")
    private String nit;
    @Column(name="estado")
    private Boolean estado;
    
       
    public CentroTrabajo() {        
    }

    public CentroTrabajo(Integer codCentrotrabajo) {
        this.codCentrotrabajo = codCentrotrabajo;
    }

    public CentroTrabajo(Integer codCentrotrabajo, String nombre) {
        this.codCentrotrabajo = codCentrotrabajo;
        this.nombre = nombre;        
    }

    public CentroTrabajo(Integer codigoEstablecimiento, Integer codCentrotrabajo, String nombre, String nit, Boolean estado) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCentrotrabajo = codCentrotrabajo;
        this.nombre = nombre;  
        this.nit = nit;
        this.estado = estado;
    }

    public Integer getCodCentrotrabajo() {
        return codCentrotrabajo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public void setCodCentrotrabajo(Integer codCentrotrabajo) {
        this.codCentrotrabajo = codCentrotrabajo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
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
        hash += (codCentrotrabajo != null ? codCentrotrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CentroTrabajo)) {
            return false;
        }
        CentroTrabajo other = (CentroTrabajo) object;
        if ((this.codCentrotrabajo == null && other.codCentrotrabajo != null) || (this.codCentrotrabajo != null && !this.codCentrotrabajo.equals(other.codCentrotrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.CentroTrabajo[ codCentrotrabajo=" + codCentrotrabajo + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}

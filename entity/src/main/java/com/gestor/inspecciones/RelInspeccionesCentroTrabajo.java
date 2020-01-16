/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.inspecciones;

import com.gestor.gestor.*;
import com.gestor.publico.CentroTrabajo;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author franj
 */
@Entity
@Table(name = "rel_inspecciones_centro_trabajo")
@NamedQueries({
    @NamedQuery(name = "RelInspeccionesCentroTrabajo.findAll", query = "SELECT rel_i_ct FROM RelInspeccionesCentroTrabajo rel_i_ct")})
public class RelInspeccionesCentroTrabajo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_inspeccion")
    private Integer codInspeccion;
    @Basic(optional = false)
    @Column(name = "cod_centrotrabajo")
    private Integer codCentroTrabajo;    
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    private CentroTrabajo centroTrabajo;
    private InspeccionesTipo inspeccionesTipo;

    public RelInspeccionesCentroTrabajo() {
    }

    
    public RelInspeccionesCentroTrabajo(Integer codInspeccion,Integer codCentroTrabajo, Integer codigoEstablecimiento) {
        this.codInspeccion= codInspeccion;
        this.codCentroTrabajo= codCentroTrabajo;
        this.codigoEstablecimiento=codigoEstablecimiento;
        
    }

    public CentroTrabajo getCentroTrabajo() {
        return centroTrabajo;
    }

    public void setCentroTrabajo(CentroTrabajo centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }

    public InspeccionesTipo getInspeccionesTipo() {
        return inspeccionesTipo;
    }

    public void setInspeccionesTipo(InspeccionesTipo inspeccionesTipo) {
        this.inspeccionesTipo = inspeccionesTipo;
    }

    public Integer getCodInspeccion() {
        return codInspeccion;
    }

    public void setCodInspeccion(Integer codInspeccion) {
        this.codInspeccion = codInspeccion;
    }

    public Integer getCodCentroTrabajo() {
        return codCentroTrabajo;
    }

    public void setCodCentroTrabajo(Integer codCentroTrabajo) {
        this.codCentroTrabajo = codCentroTrabajo;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelInspeccionesCentroTrabajo)) {
            return false;
        }
        RelInspeccionesCentroTrabajo other = (RelInspeccionesCentroTrabajo) object;
        if ((this.codInspeccion == null && other.codInspeccion != null) || (this.codInspeccion != null && !this.codInspeccion.equals(other.codInspeccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.RelInspeccionesCentroTrabajo[ cod_inspeccion=" + codInspeccion + " ]";
    }
    
}

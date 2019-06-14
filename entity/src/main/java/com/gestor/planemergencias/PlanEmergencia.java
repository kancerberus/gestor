/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias;

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
@Table(name = "plan_emergencia")
@NamedQueries({
    @NamedQuery(name = "plan_emergencia.findAll", query = "SELECT pe FROM plan_emergencia pe")})
public class PlanEmergencia implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_plan_emergencia")
    private Integer codPlanEmergencia;
    @Basic(optional = false)
    @Column(name = "cod_centro_trabajo")
    private Integer codCentroTrabajo;
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    
    private CentroTrabajo centrotrabajo;

    public PlanEmergencia(){
    }

    public PlanEmergencia(Integer codPlanEmergencia, Integer codCentroTrabajo, Integer codigoEstablecimiento) {
        this.codPlanEmergencia = codPlanEmergencia;
        this.codCentroTrabajo = codCentroTrabajo;
        this.codigoEstablecimiento = codigoEstablecimiento;        
    }


    public CentroTrabajo getCentrotrabajo() {
        return centrotrabajo;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public void setCentrotrabajo(CentroTrabajo centrotrabajo) {
        this.centrotrabajo = centrotrabajo;
    }

    public Integer getCodPlanEmergencia() {
        return codPlanEmergencia;
    }

    public void setCodPlanEmergencia(Integer codPlanEmergencia) {
        this.codPlanEmergencia = codPlanEmergencia;
    }

    public Integer getCodCentroTrabajo() {
        return codCentroTrabajo;
    }

    public void setCodCentroTrabajo(Integer codCentroTrabajo) {
        this.codCentroTrabajo = codCentroTrabajo;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanEmergencia)) {
            return false;
        }
        PlanEmergencia other = (PlanEmergencia) object;
        if ((this.codPlanEmergencia == null && other.codPlanEmergencia != null) || (this.codPlanEmergencia != null && !this.codPlanEmergencia.equals(other.codPlanEmergencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.PlanEmergencia[ cod_plan_emergencia=" + codPlanEmergencia + " ]";
    }    
}
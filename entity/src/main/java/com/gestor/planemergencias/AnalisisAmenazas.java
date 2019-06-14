/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias;

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
@Table(name = "analisis_amenazas")
@NamedQueries({
    @NamedQuery(name = "analisis_amenazas.findAll", query = "SELECT aam FROM analisis_amenazas aam")})
public class AnalisisAmenazas implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_centro_trabajo")
    private Integer codCentroTrabajo;
    @Column(name = "cod_plan_emergencia")
    private Integer codPlanEmergencia;
    @Column(name = "cod_analisis_amenaza")
    private Integer codAnalisisAmenaza;
    @Column(name = "cod_tipo_origen")
    private Integer codTipoOrigen;
    @Column(name = "cod_origen")
    private Integer codOrigen;
    
    private TipoOrigen tipoOrigen;    
    private RelOriegenTipo relOrigenTipo=new RelOriegenTipo();
    private Probabilidad probabilidad;
    private Gravedad gravedad;
    
    public AnalisisAmenazas(){
    }

    public AnalisisAmenazas(Integer codigoEstablecimiento, Integer codCentroTrabajo, Integer codPlanEmergencia, Integer codAnalisisAmenaza, Integer codTipoOrigen, Integer codOrigen) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCentroTrabajo = codCentroTrabajo;
        this.codPlanEmergencia = codPlanEmergencia;
        this.codAnalisisAmenaza = codAnalisisAmenaza;
        this.codTipoOrigen = codTipoOrigen;
        this.codOrigen = codOrigen;           
    }

    public AnalisisAmenazas(Integer codigoEstablecimiento, Integer codCentroTrabajo, Integer codPlanEmergencia, Integer codAnalisisAmenaza) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCentroTrabajo = codCentroTrabajo;
        this.codPlanEmergencia = codPlanEmergencia;
        this.codAnalisisAmenaza = codAnalisisAmenaza;
    }    

    public Probabilidad getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Probabilidad probabilidad) {
        this.probabilidad = probabilidad;
    }

    public Gravedad getGravedad() {
        return gravedad;
    }

    public void setGravedad(Gravedad gravedad) {
        this.gravedad = gravedad;
    }

    public TipoOrigen getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(TipoOrigen tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }

    public RelOriegenTipo getRelOrigenTipo() {
        return relOrigenTipo;
    }

    public void setRelOrigenTipo(RelOriegenTipo relOrigenTipo) {
        this.relOrigenTipo = relOrigenTipo;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Integer getCodCentroTrabajo() {
        return codCentroTrabajo;
    }

    public void setCodCentroTrabajo(Integer codCentroTrabajo) {
        this.codCentroTrabajo = codCentroTrabajo;
    }

    public Integer getCodPlanEmergencia() {
        return codPlanEmergencia;
    }

    public void setCodPlanEmergencia(Integer codPlanEmergencia) {
        this.codPlanEmergencia = codPlanEmergencia;
    }

    public Integer getCodAnalisisAmenaza() {
        return codAnalisisAmenaza;
    }

    public void setCodAnalisisAmenaza(Integer codAnalisisAmenaza) {
        this.codAnalisisAmenaza = codAnalisisAmenaza;
    }

    public Integer getCodTipoOrigen() {
        return codTipoOrigen;
    }

    public void setCodTipoOrigen(Integer codTipoOrigen) {
        this.codTipoOrigen = codTipoOrigen;
    }

    public Integer getCodOrigen() {
        return codOrigen;
    }

    public void setCodOrigen(Integer codOrigen) {
        this.codOrigen = codOrigen;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalisisAmenazas)) {
            return false;
        }
        AnalisisAmenazas other = (AnalisisAmenazas) object;
        if ((this.codAnalisisAmenaza == null && other.codAnalisisAmenaza != null) || (this.codAnalisisAmenaza != null && !this.codAnalisisAmenaza.equals(other.codAnalisisAmenaza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.AnalisisAmenazas[ cod_analisis_amenaza=" + codAnalisisAmenaza + " ]";
    }    

    String getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
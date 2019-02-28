/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author fjvc
 */
@Entity
@Table(name = "plan_capacitacion")
@NamedQueries({
    @NamedQuery(name = "PlanCapacitacion.findAll", query = "SELECT pc FROM PlanCapacitacion pc")
})
@ManagedBean
@SessionScoped
public class PlanCapacitacion implements Serializable {   
    
    public static String PLAN_CAPACITACION_CONDICION_COD_PlAN_CAPACITACION = "PC.cod_plan_capacitacion IN (?)";

    private static final long serialVersionUID = 1L;
    @EmbeddedId     
    @Column(name = "codigo_establecimiento")
    private Integer codEstablecimiento;    
    @Column(name = "cod_plan_capacitacion")
    private Integer codPlancapacitacion;    
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_venc")
    private Date fechaVenc;                  
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado; 
    @Column(name = "meta")
    private Integer meta; 
    

    private Date fechaRegistro;       

    public PlanCapacitacion() {
    }

    public PlanCapacitacion(Integer codEstablecimiento, Integer codPlancapacitacion, String descripcion, Date fechaVenc, String estado, Date fechaRegistro, Integer meta) {
        this.codEstablecimiento = codEstablecimiento;
        this.codPlancapacitacion = codPlancapacitacion;
        this.descripcion = descripcion;
        this.fechaVenc = fechaVenc;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.meta = meta;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }    

    public Integer getCodPlancapacitacion() {
        return codPlancapacitacion;
    }

    public void setCodPlancapacitacion(Integer codPlancapacitacion) {
        this.codPlancapacitacion = codPlancapacitacion;
    }
    
    public Integer getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(Integer codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanTrabajoCapacitacion[ " + codPlancapacitacion + " ]";
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }    

}
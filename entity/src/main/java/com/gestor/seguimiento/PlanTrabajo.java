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
@Table(name = "plan_trabajo")
@NamedQueries({
    @NamedQuery(name = "PlanTrabajo.findAll", query = "SELECT pt FROM PlanTrabajo pt")
})
@ManagedBean
@SessionScoped
public class PlanTrabajo implements Serializable {   

    private static final long serialVersionUID = 1L;
    @EmbeddedId     
    @Column(name = "codigo_establecimiento")
    private Integer codEstablecimiento;    
    @Column(name = "cod_plan_trabajo")
    private Integer codPlantrabajo;    
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_venc")
    private Date fechaVenc;      
    @Column(name = "peso")
    private Integer peso;     
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;            

    private Date fechaRegistro;       

    public PlanTrabajo() {
    }

    public PlanTrabajo( Integer codEstablecimiento, Integer codPlantrabajo, String descripcion, Date fechaVenc, Integer peso, String estado, Date fechaRegistro) {          
        this.codEstablecimiento = codEstablecimiento;
        this.codPlantrabajo = codPlantrabajo;
        this.descripcion = descripcion;
        this.fechaVenc = fechaVenc;
        this.peso = peso;        
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }       

    public Integer getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(Integer codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }
    
    public Integer getCodPlantrabajo() {
        return codPlantrabajo;
    }

    public void setCodPlantrabajo(Integer codPlantrabajo) {
        this.codPlantrabajo = codPlantrabajo;
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

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }   

    @Override
    public String toString() {
        return "com.gestor.gestor.PlanTrabajo[ " + codPlantrabajo + " ]";
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

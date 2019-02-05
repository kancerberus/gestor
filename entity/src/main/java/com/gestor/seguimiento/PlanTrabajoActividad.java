/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.publico.Establecimiento;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.PlanTrabajoPrograma;
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
@Table(name = "plan_trabajo_actividad")
@NamedQueries({
    @NamedQuery(name = "PlanTrabajoActividad.findAll", query = "SELECT pta FROM PlanTrabajoActividad pta")
})
@ManagedBean
@SessionScoped
public class PlanTrabajoActividad implements Serializable {   

    private static final long serialVersionUID = 1L;
    @EmbeddedId     
    @Column(name = "codigo_establecimiento")
    private Integer codEstablecimiento;    
    @Column(name = "cod_plan_trabajo")
    private Integer codPlantrabajo;
    @Column(name = "cod_actividad")
    private Integer codActividad;    
    @Column(name = "cod_objetivo")
    private Integer codObjetivo;    
    @Column(name = "cod_programa")
    private Integer codPrograma;        
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_venc")
    private Date fechaVenc;              
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado; 

    private Date fechaRegistro;       
    
    private Establecimiento establecimiento = new Establecimiento();
    private PlanTrabajoObjetivo objetivo = new PlanTrabajoObjetivo();
    private PlanTrabajoPrograma programa= new PlanTrabajoPrograma();
    
    
    public PlanTrabajoActividad() {
    }

    public PlanTrabajoActividad(Integer codEstablecimiento, Integer codPlantrabajo, Integer codActividad, Integer codObjetivo, Integer codPrograma, String descripcion, Date fechaVenc, String estado, Date fechaRegistro) {
        this.codEstablecimiento = codEstablecimiento;
        this.codPlantrabajo = codPlantrabajo;
        this.codActividad = codActividad;
        this.codObjetivo = codObjetivo;
        this.codPrograma = codPrograma;        
        this.descripcion = descripcion;
        this.fechaVenc = fechaVenc;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;        
    }   

    public Integer getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(Integer codActividad) {
        this.codActividad = codActividad;
    }

    public PlanTrabajoObjetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(PlanTrabajoObjetivo objetivo) {
        this.objetivo = objetivo;
    }

    public PlanTrabajoPrograma getPrograma() {
        return programa;
    }

    public void setPrograma(PlanTrabajoPrograma programa) {
        this.programa = programa;
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

    public Integer getCodObjetivo() {
        return codObjetivo;
    }

    public void setCodObjetivo(Integer codObjetivo) {
        this.codObjetivo = codObjetivo;
    }

    public Integer getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(Integer codPrograma) {
        this.codPrograma = codPrograma;
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

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
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

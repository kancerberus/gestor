/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.gestor.FuenteHallazgo;
import com.gestor.gestor.Recursos;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.Responsable;
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
@Table(name = "plan_trabajo_actividad_nota")
@NamedQueries({
    @NamedQuery(name = "PlanTrabajoActividadNota.findAll", query = "SELECT ptanota FROM PlanTrabajoActividadNota ptanota")
})
@ManagedBean
@SessionScoped
public class PlanTrabajoActividadNota implements Serializable {   

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
    @Basic(optional = false)
    @Column(name = "cod_nota")
    private Integer codNota;
    @Column(name = "nombre")
    private String nombre;                
    @Column(name = "descripcion")
    private String descripcion;                
    @Column(name = "estado")
    private String estado; 

    private Date fechaRegistro;       
    
    private Establecimiento establecimiento = new Establecimiento();
    private PlanTrabajo plantrabajo = new PlanTrabajo();
    private PlanTrabajoObjetivo objetivo = new PlanTrabajoObjetivo();
    private PlanTrabajoPrograma programa= new PlanTrabajoPrograma();
    private FuenteHallazgo fuenteHallazgo= new FuenteHallazgo();
    private Responsable responsable= new Responsable();
    private Recursos recursos = new Recursos();
    
    
    public PlanTrabajoActividadNota() {
    }

    public PlanTrabajoActividadNota(Integer codEstablecimiento, Integer codPlantrabajo, Integer codActividad, Integer codObjetivo, Integer codPrograma, Integer codNota, String nombre, String descripcion, String estado, Date fechaRegistro) {
        this.codEstablecimiento = codEstablecimiento;
        this.codPlantrabajo = codPlantrabajo;
        this.codActividad = codActividad;
        this.codObjetivo = codObjetivo;
        this.codPrograma = codPrograma;
        this.codNota = codNota;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PlanTrabajo getPlantrabajo() {
        return plantrabajo;
    }

    public void setPlantrabajo(PlanTrabajo plantrabajo) {
        this.plantrabajo = plantrabajo;
    }

    public FuenteHallazgo getFuenteHallazgo() {
        return fuenteHallazgo;
    }

    public void setFuenteHallazgo(FuenteHallazgo fuenteHallazgo) {
        this.fuenteHallazgo = fuenteHallazgo;
    }    

    public Recursos getRecursos() {
        return recursos;
    }
    public void setRecursos(Recursos recursos) {
        this.recursos = recursos;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
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

    public Integer getCodNota() {
        return codNota;
    }

    public void setCodNota(Integer codNota) {
        this.codNota = codNota;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.PlanTrabajoActividadNota[ " + codNota + " ]";
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

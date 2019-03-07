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
import java.util.List;
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
    
    public static String PLAN_TRABAJO_ACTIVIDAD_CONDICION_DOCUMENTO_USUARIO = "U.DOCUMENTO_USUARIO IN (?)";
    public static String PLAN_TRABAJO_ACTIVIDAD_CONDICION_COD_ESTABLECIMIENTO = "PTA.codigo_establecimiento IN (?)";
    public static String PLAN_TRABAJO_ACTIVIDAD_CONDICION_COD_PlAN_TRABAJO = "PTA.cod_plan_trabajo IN (?)";
    public static String PLAN_TRABAJO_ACTIVIDAD_CONDICION_RESPONSABLE = "PTA.responsable ILIKE (?)";
    public static String PLAN_TRABAJO_ACTIVIDAD_CONDICION_ESTADO = "PTA.ESTADO IN (?)";
    public static String PLAN_TRABAJO_ACTIVIDAD_CONDICION_FECHA_REGISTRO_GTE = "PTA.fecha_reg::DATE >= ?";
    public static String PLAN_TRABAJO_ACTIVIDAD_CONDICION_FECHA_FINALIZADO_LTE = "PTA.fecha_finalizado::DATE <= ?";
    
    public static String PLAN_TRABAJO_ACTIVIDAD_ESTADO_CERRADO = "C";

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
    @Column(name = "cod_fuente_hallazgo")
    private Integer codFuentehallazgo;      
    @Column(name = "cedula")
    private String cedula;
    @Column(name = "cod_recursos")
    private Integer codRecursos;  
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_venc")
    private Date fechaVenc;     
    @Column(name = "fecha_finalizado")
    private Date fechaFinalizado;  
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    private Integer diasRestantes;

    private Date fechaRegistro;       
    
    private Establecimiento establecimiento = new Establecimiento();
    private PlanTrabajo plantrabajo = new PlanTrabajo();
    private PlanTrabajoObjetivo objetivo = new PlanTrabajoObjetivo();
    private PlanTrabajoPrograma programa= new PlanTrabajoPrograma();
    private FuenteHallazgo fuenteHallazgo= new FuenteHallazgo();
    private Responsable responsable= new Responsable();
    private Recursos recursos = new Recursos();
    
    private List<PlanTrabajoActividadNota> planTrabajoactividadNotasList;
    private PlanTrabajoActividadNota planTrabajoActividadNota;
    
    public PlanTrabajoActividad() {
    }

    public PlanTrabajoActividad(Integer codEstablecimiento, Integer codPlantrabajo, Integer codActividad, Integer codObjetivo, Integer codPrograma, Integer codFuentehallazgo, String cedula, Integer codRecurso, String descripcion, Date fechaVenc, String estado, Date fechaRegistro, Date fechaFinalizado) {
        this.codEstablecimiento = codEstablecimiento;
        this.codPlantrabajo = codPlantrabajo;
        this.codActividad = codActividad;
        this.codObjetivo = codObjetivo;
        this.codPrograma = codPrograma;  
        this.codFuentehallazgo = codFuentehallazgo;
        this.codRecursos = codRecurso;
        this.cedula = cedula;
        this.descripcion = descripcion;
        this.fechaVenc = fechaVenc;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;     
        this.fechaFinalizado = fechaFinalizado;
    }   

    public Date getFechaFinalizado() {
        return fechaFinalizado;
    }

    public void setFechaFinalizado(Date fechaFinalizado) {
        this.fechaFinalizado = fechaFinalizado;
    }

    public Integer getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(Integer diasRestantes) {
        this.diasRestantes = diasRestantes;
    }


    public PlanTrabajoActividadNota getPlanTrabajoActividadNota() {
        return planTrabajoActividadNota;
    }

    public void setPlanTrabajoActividadNota(PlanTrabajoActividadNota planTrabajoActividadNota) {
        this.planTrabajoActividadNota = planTrabajoActividadNota;
    }

    public List<PlanTrabajoActividadNota> getPlanTrabajoactividadNotasList() {
        return planTrabajoactividadNotasList;
    }

    public void setPlanTrabajoactividadNotasList(List<PlanTrabajoActividadNota> planTrabajoactividadNotasList) {
        this.planTrabajoactividadNotasList = planTrabajoactividadNotasList;
    }


    public PlanTrabajo getPlantrabajo() {
        return plantrabajo;
    }

    public void setPlantrabajo(PlanTrabajo plantrabajo) {
        this.plantrabajo = plantrabajo;
    }

    public Integer getCodFuentehallazgo() {
        return codFuentehallazgo;
    }

    public void setCodFuentehallazgo(Integer codFuentehallazgo) {
        this.codFuentehallazgo = codFuentehallazgo;
    }

    public FuenteHallazgo getFuenteHallazgo() {
        return fuenteHallazgo;
    }

    public void setFuenteHallazgo(FuenteHallazgo fuenteHallazgo) {
        this.fuenteHallazgo = fuenteHallazgo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Integer getCodRecursos() {
        return codRecursos;
    }

    public void setCodRecursos(Integer codRecursos) {
        this.codRecursos = codRecursos;
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

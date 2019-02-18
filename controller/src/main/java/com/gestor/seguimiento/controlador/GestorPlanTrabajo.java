/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.seguimiento.PlanTrabajoActividadNota;
import com.gestor.seguimiento.PlanTrabajo;
import com.gestor.seguimiento.PlanTrabajoActividad;
import com.gestor.seguimiento.dao.PlanTrabajoDAO;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 *
 * @author fjvc
 */
public class GestorPlanTrabajo extends Gestor implements Serializable{

    public GestorPlanTrabajo() throws Exception {
        super();
    }
    
    public List<PlanTrabajo> cargarPlantrabajoList(int codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            PlanTrabajoDAO plantrabajoDAO = new PlanTrabajoDAO(conexion);            
            return plantrabajoDAO.cargarPlanTrabajoList(codEstablecimiento);                        
        }finally {
            this.cerrarConexion();
        }
    
    }
    
    public List<PlanTrabajo> cargarPlantrabajoAbiertosList(int codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            PlanTrabajoDAO plantrabajoDAO = new PlanTrabajoDAO(conexion);            
            return plantrabajoDAO.cargarPlanTrabajoAbirtosList(codEstablecimiento);
        }finally {
            this.cerrarConexion();
        }
    
    }
    
    public List<PlanTrabajoActividad> cargarPlantrabajoactividadList(PlanTrabajo plantrabajo)throws Exception {
        try {
            this.abrirConexion();
            PlanTrabajoDAO plantrabajoDAO = new PlanTrabajoDAO(conexion);            
            return plantrabajoDAO.cargarPlanTrabajoactividadList(plantrabajo);
        }finally {
            this.cerrarConexion();
        }
    
    }
    
    public void almacenarPlantrabajo(PlanTrabajo plantrabajo) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanTrabajoDAO plantrabajoDAO = new PlanTrabajoDAO(conexion);
            plantrabajoDAO.insertarPlantrabajo(plantrabajo);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarPlantrabajoactividad(PlanTrabajoActividad plantrabajoactividad) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanTrabajoDAO plantrabajoDAO = new PlanTrabajoDAO(conexion);
            plantrabajoDAO.insertarPlantrabajoactividad(plantrabajoactividad);
            if(plantrabajoactividad.getPlanTrabajoActividadNota() !=null){
            plantrabajoDAO.insertaEvaluacionPlanTrabajoActividadNota(plantrabajoactividad.getPlanTrabajoActividadNota());
            }
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }  

    
    public Integer valorMeta(int codEstablecimiento, int codPlantrabajo) throws Exception {
        try {
            this.abrirConexion();
            PlanTrabajoDAO planTrabajoDAO = new PlanTrabajoDAO(conexion);
            return planTrabajoDAO.valorMeta(codEstablecimiento, codPlantrabajo);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanTrabajoActividad> cargarListaEvaluacionPlanAccion(String condicion) throws Exception {
        try {
            this.abrirConexion();
            PlanTrabajoDAO planTrabajoDAO = new PlanTrabajoDAO(conexion);
            return planTrabajoDAO.cargarListaPlanTrabajo(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanTrabajoActividad> cargarListaEvaluacionPlanAccionpt(String condicion) throws Exception {
        try {
            this.abrirConexion();
            PlanTrabajoDAO planTrabajoDAO = new PlanTrabajoDAO(conexion);
            return planTrabajoDAO.cargarListaPlanTrabajopt(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void cerrarPlanAccionDetalle(PlanTrabajoActividad pta) throws Exception {
        try {
            this.abrirConexion();
            PlanTrabajoDAO planTrabajoDAO = new PlanTrabajoDAO(conexion);
            planTrabajoDAO.insertarPlantrabajoactividad(pta);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<PlanTrabajoActividadNota> cargarEvaluacionPlanTrabajoNotasList(PlanTrabajoActividad pta) throws Exception {
        try {
            this.abrirConexion();
            PlanTrabajoDAO planTrabajoDAO = new PlanTrabajoDAO(conexion);
            return planTrabajoDAO.cargarEvaluacionPlanTrabajoActividadNotasList(pta);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public PlanTrabajoActividadNota validarPlanTrabajoActividadNota(PlanTrabajoActividadNota ptan) throws Exception {
        if (ptan == null || ptan.getDescripcion() == null || ptan.getDescripcion().equalsIgnoreCase("")) {
            throw new Exception("Ingrese la descripción del seguimiento", UtilLog.TW_VALIDACION);
        }
        if (ptan.getNombre() == null || ptan.getNombre().equalsIgnoreCase("")) {
            ptan.setNombre("SEGUIMIENTO");
        }        
        ptan.setDescripcion(ptan.getDescripcion().trim());
        return ptan;
    }
    
    public void procesarPlanTrabajoActividadNota(PlanTrabajoActividadNota ptan) throws Exception {
        try {
            this.abrirConexion();
            PlanTrabajoDAO planTrabajoDAO = new PlanTrabajoDAO(conexion);
            planTrabajoDAO.upsertPlanTrabajoActividadNota(ptan);
        } finally {
            this.cerrarConexion();
        }
    }
}

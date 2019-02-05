/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.controlador;

import com.gestor.controller.Gestor;
import com.gestor.seguimiento.PlanTrabajo;
import com.gestor.seguimiento.PlanTrabajoActividad;
import com.gestor.seguimiento.dao.PlanTrabajoDAO;
import java.io.Serializable;
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
}

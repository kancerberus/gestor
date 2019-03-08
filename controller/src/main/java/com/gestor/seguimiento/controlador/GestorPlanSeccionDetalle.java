/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.controlador;

import com.gestor.entity.UtilLog;
import java.io.Serializable;
import com.gestor.seguimiento.PlanSeccionDetalle;
import com.gestor.seguimiento.dao.PlanSeccionDetalleDAO;
import com.gestor.controller.Gestor;

/**
 *
 * @author franj
 */
public class GestorPlanSeccionDetalle extends Gestor implements Serializable{
    
    public GestorPlanSeccionDetalle() throws Exception {
        super();
    }
    
    public void validarPlansecciondetalle(PlanSeccionDetalle plansecciondetalle) throws Exception {
        if(plansecciondetalle.getNombre()==null){
            throw new Exception("Ingresa el subtitulo.", UtilLog.TW_VALIDACION);
        }
        if (plansecciondetalle.getPlanSeccionDetallePK().getCodTitulo()==0 || plansecciondetalle.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el numeral", UtilLog.TW_VALIDACION);
        }  
        plansecciondetalle.setNombre(plansecciondetalle.getNombre().trim().toUpperCase());        

    }
    
    public void almacenarSecciondetalle(PlanSeccionDetalle plansecciondetalle) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDetalleDAO plansecciondetalleDAO = new PlanSeccionDetalleDAO(conexion);
            plansecciondetalleDAO.insertarPlansecciondetalle(plansecciondetalle);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarPlanSeccionDetalle(PlanSeccionDetalle plansecciondetalle) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDetalleDAO plansecciondetalleDAO = new PlanSeccionDetalleDAO(conexion);
            plansecciondetalleDAO.eliminarPlanSeccionDetalle(plansecciondetalle);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
}

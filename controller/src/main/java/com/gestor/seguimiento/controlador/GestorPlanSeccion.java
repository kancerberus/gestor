/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.seguimiento.PlanSeccion;
import com.gestor.seguimiento.PlanSeccionTexto;
import com.gestor.seguimiento.dao.PlanSeccionTextoDAO;
import com.gestor.seguimiento.dao.PlanSeccionDAO;
import com.gestor.seguimiento.PlanTitulo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
/**
 *
 * @author Julian D Osorio G
 */
public class GestorPlanSeccion extends Gestor implements Serializable{

    public GestorPlanSeccion() throws Exception {
        super();
    }
    
    public void validarPlanseccion(PlanSeccion planseccion) throws Exception {
        if(planseccion.getNombre()==null){
            throw new Exception("Ingresa el titulo de la seccion.", UtilLog.TW_VALIDACION);
        }
        if (planseccion.getPlanSeccionPK().getCodTitulo()==0 || planseccion.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el numeral", UtilLog.TW_VALIDACION);
        }  
        planseccion.setNombre(planseccion.getNombre().trim().toUpperCase());        

    }
    
    public void almacenarSeccion(PlanSeccion planseccion) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDAO planseccionDAO = new PlanSeccionDAO(conexion);
            planseccionDAO.insertarPlanseccion(planseccion);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarSecciontexto(PlanSeccionTexto plansecciontexto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionTextoDAO plansecciontextoDAO = new PlanSeccionTextoDAO(conexion);
            plansecciontextoDAO.insertarPlanseccionTexto(plansecciontexto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanSeccion> cargarListaSeccion(PlanTitulo pt) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionDAO planSeccionDAO = new PlanSeccionDAO(conexion);
            Collection<PlanSeccion> planseccionList = new ArrayList<>();
            planseccionList.addAll(planSeccionDAO.cargarPlanSeccionList(pt));
            return planseccionList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    
    public Collection<? extends PlanSeccionTexto> cargarPlanSecciontextoList(PlanSeccion planseccion) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionTextoDAO planSecciontextoDAO = new PlanSeccionTextoDAO(conexion);
            Collection<PlanSeccionTexto> plansecciontextoList = new ArrayList<>();
            plansecciontextoList.addAll(planSecciontextoDAO.cargarPlanSecciontextoList(planseccion));
            return plansecciontextoList;
        } finally {
            this.cerrarConexion();
        }
    }
    public void modificarSeccion(PlanSeccion planseccion) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDAO planseccionDAO = new PlanSeccionDAO(conexion);
            planseccionDAO.modificarplanseccion(planseccion);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    

}

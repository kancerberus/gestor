/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.indicadores.controlador;


import com.gestor.controller.Gestor;
import com.gestor.gestor.EvaluacionPlanAccionDetalle;
import com.gestor.indicadores.dao.IndicadoresDAO;
import java.io.Serializable;
import java.util.Collection;


/**
 *
 * @author Francisco
 */
public class GestorIndicadores extends Gestor implements Serializable{

    public GestorIndicadores() throws Exception {
        super();
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleClaseHallazgo(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarListaEvaluacionPlanAccionDetalleClaseHallazgo(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleTipoAccion(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarListaEvaluacionPlanAccionDetalleTipoAccion(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleFuenteHallazgo(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarListaEvaluacionPlanAccionDetalleFuenteHallazgo(condicion);
        } finally {
            this.cerrarConexion();
        }
    }    
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleEstado(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarListaEvaluacionPlanAccionDetalleEstado(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleFuenteHallazgoEstados(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarListaEvaluacionPlanAccionDetalleFuenteHallazgoEstados(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    
    public Integer cargarCantReqLegalesCerrados(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarCantReqLegalesCerrados(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    public Integer cargarCantIdPeligrosCerrados(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarCantIdPeligrosCerrados(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
        
    public Integer cargarCantAccionesCerradas(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarCantAccionesCerradas(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Integer cargarCantAcciones(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarCantAcciones(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Integer cargarCantAccionesEficacia(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarCantAccionesEficacia(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Integer cargarCantIdPeligros(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarCantIdPeligros(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Integer cargarCantIdPeligrosEficacia(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarCantIdPeligrosEficacia(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Integer cargarCantReqLegales(String condicion) throws Exception {
        try {
            this.abrirConexion();
            IndicadoresDAO indicadoresDAO =new IndicadoresDAO(conexion);
            return indicadoresDAO.cargarCantReqLegales(condicion);
        } finally {
            this.cerrarConexion();
        }
    }
            
    
}

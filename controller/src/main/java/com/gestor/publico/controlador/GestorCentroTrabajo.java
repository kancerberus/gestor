/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.controlador;


import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.dao.CentroTrabajoDAO;
import com.gestor.publico.dao.EstablecimientoDAO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Julian
 */
public class GestorCentroTrabajo extends Gestor implements Serializable{

    public GestorCentroTrabajo() throws Exception {
        super();
    }
    
    public List<?> cargarListaCentrosTrabajo(int codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            CentroTrabajoDAO centrotrabajoDAO = new CentroTrabajoDAO(conexion);
            return centrotrabajoDAO.cargarListaCentrostrabajo(codEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaCentrosTrabajoactivos(int codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            CentroTrabajoDAO centrotrabajoDAO = new CentroTrabajoDAO(conexion);
            return centrotrabajoDAO.cargarListaCentrostrabajoactivos(codEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }
    

    
    public void validarCentro(CentroTrabajo centro) throws Exception {        
        if(centro.getNombre() == null ){
            throw new Exception("Ingresa el nombre.", UtilLog.TW_VALIDACION);
        }
        if (centro.getNit() == null || centro.getNit().equalsIgnoreCase("")) {
            throw new Exception("Ingresa un Nit.", UtilLog.TW_VALIDACION);
        }            
        centro.setNombre(centro.getNombre().trim().toUpperCase());        
    }
    
    public void almacenarCentro(CentroTrabajo centro) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            CentroTrabajoDAO centroTrabajoDAO = new CentroTrabajoDAO(conexion);
            centroTrabajoDAO.insertarCentro(centro);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarCentro(CentroTrabajo centro) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            CentroTrabajoDAO centroTrabajoDAO = new CentroTrabajoDAO(conexion);
            centroTrabajoDAO.eliminarCentro(centro);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
}

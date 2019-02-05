/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.controlador;
import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.dao.ObjetivoDAO;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author franj
 */
public class GestorObjetivo extends Gestor implements Serializable{
    
    public GestorObjetivo() throws Exception {
        super();
    }
    
    public void validarObjetivo(PlanTrabajoObjetivo objetivo) throws Exception {
        if (objetivo.getNombre() == null || objetivo.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el Objetivo.", UtilLog.TW_VALIDACION);
        }
        
        objetivo.setNombre(objetivo.getNombre().trim().toUpperCase());
    }
    
    public void almacenarObjetivo(PlanTrabajoObjetivo obj) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            ObjetivoDAO objetivoDAO = new ObjetivoDAO(conexion);
            objetivoDAO.insertarObjetivo(obj);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarObjetivo(PlanTrabajoObjetivo objetivo) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            ObjetivoDAO objetivoDAO= new ObjetivoDAO(conexion);
            objetivoDAO.eliminarObjetivo(objetivo);            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaObjetivo(int codEstablecimiento , int codPlantrabajo) throws Exception {
        try {
            this.abrirConexion();
            ObjetivoDAO objetivoDAO= new ObjetivoDAO(conexion);
            return objetivoDAO.cargarListaObjetivo(codEstablecimiento, codPlantrabajo);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaObjetivoplanaccion(int codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            ObjetivoDAO objetivoDAO= new ObjetivoDAO(conexion);
            return objetivoDAO.cargarListaObjetivoplanaccion(codEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.controlador;
import com.gestor.publico.Responsable;
import com.gestor.publico.dao.ResponsableDAO;
import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author franj
 */
public class GestorResponsable extends Gestor implements Serializable{
    
    public GestorResponsable() throws Exception {
        super();
    }
    
    public Responsable cargarResponsable(String cedula) throws Exception {
        try {
            this.abrirConexion();
            Responsable responsable;
            ResponsableDAO responsableDAO = new ResponsableDAO(conexion);
            responsable = responsableDAO.cargarResponsable(cedula);
//            establecimiento.setPrefijo(establecimientoDAO.cargarPrefijo(codigo));
            return responsable;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaResponsable() throws Exception {
        try {
            this.abrirConexion();
            ResponsableDAO responsableDAO = new ResponsableDAO(conexion);
            return responsableDAO.cargarListaResponsable();
        } finally {
            this.cerrarConexion();
        }
    }
    public void validarResponsable(Responsable responsable) throws Exception {
        if(responsable.getCedula()==null || responsable.getCedula()== ""){
            throw new Exception("Ingresa la cedula.", UtilLog.TW_VALIDACION);
        }
        if (responsable.getNombres() == null || responsable.getNombres().equalsIgnoreCase("")) {
            throw new Exception("Ingresa los nombres.", UtilLog.TW_VALIDACION);
        }
        if ( responsable.getApellidos()== null || responsable.getApellidos().equalsIgnoreCase("")) {
            throw new Exception("Ingresa los apellidos.", UtilLog.TW_VALIDACION);
        }
        if (responsable.getTelefono() == null || responsable.getTelefono().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el telefono.", UtilLog.TW_VALIDACION);
        }
        if (responsable.getCorreo() == null || responsable.getCorreo().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el correo.", UtilLog.TW_VALIDACION);
        }
        responsable.setCedula(responsable.getCedula().trim());
        responsable.setNombres(responsable.getNombres().trim().toUpperCase());
        responsable.setApellidos(responsable.getApellidos().trim().toUpperCase());
        responsable.setCorreo(responsable.getCorreo().trim());
        responsable.setTelefono(responsable.getTelefono().trim());
    }
    
    public void almacenarResponsable(Responsable responsable) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            ResponsableDAO responsableDAO = new ResponsableDAO(conexion);
            responsableDAO.insertarResponsable(responsable);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
}

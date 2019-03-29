/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.controlador;


import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.publico.Cargos;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Funciones;
import com.gestor.publico.dao.EstablecimientoDAO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Julian
 */
public class GestorEstablecimiento extends Gestor implements Serializable{

    public GestorEstablecimiento() throws Exception {
        super();
    }

    public Establecimiento cargarEstablecimiento(int codigo) throws Exception {
        try {
            this.abrirConexion();
            Establecimiento establecimiento;
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            establecimiento = establecimientoDAO.cargarEstablecimiento(codigo);
//            establecimiento.setPrefijo(establecimientoDAO.cargarPrefijo(codigo));
            return establecimiento;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Integer buscarSisgapp() throws Exception {
        try {
            this.abrirConexion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            return establecimientoDAO.buscarSisgapp();
        } finally {
            this.cerrarConexion();
        }
    }
    


    public List<?> cargarListaEstablecimientos() throws Exception {
        try {
            this.abrirConexion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            return establecimientoDAO.cargarListaEstablecimientos();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaCargos() throws Exception {
        try {
            this.abrirConexion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            return establecimientoDAO.cargarListaCargos();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaFunciones(Integer codCargo) throws Exception {
        try {
            this.abrirConexion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            return establecimientoDAO.cargarListaFunciones(codCargo);
        } finally {
            this.cerrarConexion();
        }
    }
       
    public List<?> cargarListaCargosEstablecimiento(Integer codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            return establecimientoDAO.cargarListaCargosEstablecimiento(codEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }

    public List<?> cargarListaEstablecimientosUsuario(String documentoUsuario) throws Exception {
        try {
            this.abrirConexion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            return establecimientoDAO.cargarListaEstablecimientosUsuario(documentoUsuario);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarCargosEstablecimiento(Establecimiento establecimiento) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);            
            establecimientoDAO.eliminarCargosEstablecimiento(establecimiento);
            for (Cargos car : establecimiento.getListaCargosEstablecimientos()) {
                establecimientoDAO.agregarCargosEstablecimiento(car, establecimiento);
            }
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarCargos(Cargos cargos) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);                                    
            establecimientoDAO.insertarCargos(cargos);
            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarFuncion(Funciones funcion) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);                                    
            establecimientoDAO.insertarFuncion(funcion);
            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void validarCargo(Cargos cargo) throws Exception {
        if (cargo == null || cargo.getNombre() == null || cargo.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el nombre del Cargo.", UtilLog.TW_VALIDACION);
        }        
        cargo.setNombre(cargo.getNombre().trim().toUpperCase());
       
    }
    
    public void validarFuncion(Funciones funcion) throws Exception {
        if (funcion == null || funcion.getNombre() == null || funcion.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el nombre de la actividad.", UtilLog.TW_VALIDACION);
        }        
        funcion.setNombre(funcion.getNombre().trim().toUpperCase());
       
    }

    public void validarEstablecimiento(Establecimiento establecimiento) throws Exception {
        if (establecimiento == null || establecimiento.getNombre() == null || establecimiento.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el nombre del establecimiento.", UtilLog.TW_VALIDACION);
        }
        if (establecimiento.getNit() == null || establecimiento.getNit().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el nit del establecimiento.", UtilLog.TW_VALIDACION);
        }
        if (establecimiento.getDireccion() == null || establecimiento.getDireccion().equalsIgnoreCase("")) {
            throw new Exception("Ingresa la dirección del establecimiento.", UtilLog.TW_VALIDACION);
        }
        if (establecimiento.getTelefono() == null || establecimiento.getTelefono().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el telefono del establecimiento.", UtilLog.TW_VALIDACION);
        }
        if (establecimiento.getCorreo() == null || establecimiento.getCorreo().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el correo del establecimiento.", UtilLog.TW_VALIDACION);
        }
        establecimiento.setCorreo(establecimiento.getCorreo().trim());
        establecimiento.setDireccion(establecimiento.getDireccion().trim().toUpperCase());
        establecimiento.setNit(establecimiento.getNit().trim().toUpperCase());
        establecimiento.setNombre(establecimiento.getNombre().trim().toUpperCase());
        establecimiento.setTelefono(establecimiento.getTelefono().trim());
    }

    public void almacenarEstablecimiento(Establecimiento establecimiento) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            establecimientoDAO.insertarEstablecimiento(establecimiento);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<Establecimiento> establecimientosPermitidos() throws Exception{
        try {
            this.abrirConexion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            return establecimientoDAO.establecimientoPermitido();
        } finally {
            this.cerrarConexion();
        }        
    }
    
    
    
    public List<Establecimiento> establecimientosPlanMaestroPermitidos() throws Exception{
        try {
            this.abrirConexion();
            EstablecimientoDAO establecimientoDAO = new EstablecimientoDAO(conexion);
            return establecimientoDAO.establecimientosPlanMaestroPermitido();
        } finally {
            this.cerrarConexion();
        }        
    }
    


}

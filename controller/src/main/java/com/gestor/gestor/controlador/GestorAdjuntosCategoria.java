/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.SeccionDetalleItemsAdjuntosCategorias;
import com.gestor.gestor.SeccionDetalleItemsPK;
import com.gestor.gestor.dao.AdjuntosCategoriaDAO;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Julian D Osorio G
 */
public class GestorAdjuntosCategoria extends Gestor implements Serializable{

    public GestorAdjuntosCategoria() throws Exception {
        super();
    }
    
    public void validarCategoria(AdjuntosCategoria categoria) throws Exception {        
        if(categoria.getNombre() == null ){
            throw new Exception("Ingresa el nombre.", UtilLog.TW_VALIDACION);
        }
        if (categoria.getDescripcion() == null || categoria.getDescripcion().equalsIgnoreCase("")) {
            throw new Exception("Ingresa una descripcion.", UtilLog.TW_VALIDACION);
        }    
        if (categoria.getMesesVigencia() == 0 ) {
            throw new Exception("Ingresa los meses de vigencia.", UtilLog.TW_VALIDACION);
        }      
        categoria.setNombre(categoria.getNombre().trim().toUpperCase());
        categoria.setDescripcion(categoria.getDescripcion().trim());
        categoria.setMesesVigencia(categoria.getMesesVigencia());
    }
    
    public void validarTipo(AdjuntosCategoriaTipo tipo) throws Exception {        
        if(tipo.getNombre() == null ){
            throw new Exception("Ingresa el nombre.", UtilLog.TW_VALIDACION);
        }
        if (tipo.getDescripcion() == null || tipo.getDescripcion().equalsIgnoreCase("")) {
            throw new Exception("Ingresa una descripcion.", UtilLog.TW_VALIDACION);
        }            
        tipo.setNombre(tipo.getNombre().trim().toUpperCase());
        tipo.setDescripcion(tipo.getDescripcion().trim());        
    }
    
    public void almacenarCategoria(AdjuntosCategoria categoria, SeccionDetalleItemsAdjuntosCategorias sdiac) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            adjuntosCategoriaDAO.insertarCategoria(categoria, sdiac);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarCategoria(Integer codCategoria) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            adjuntosCategoriaDAO.eliminarCategoria(codCategoria);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarTipo(AdjuntosCategoriaTipo tipo) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            adjuntosCategoriaDAO.insertarTipo(tipo);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarTipo(AdjuntosCategoriaTipo tipo) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            adjuntosCategoriaDAO.eliminarTipo(tipo);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }


    public Collection<? extends AdjuntosCategoria> cargarListaAdjuntosCategoria(SeccionDetalleItemsPK seccionDetalleItemsPK) throws Exception {
        try {
            this.abrirConexion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            return adjuntosCategoriaDAO.cargarListaAdjuntosCategoria(seccionDetalleItemsPK);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends AdjuntosCategoria> cargarListaAdjuntosCategoriapm() throws Exception {
        try {
            this.abrirConexion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            return adjuntosCategoriaDAO.cargarListaAdjuntosCategoriapm();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends AdjuntosCategoria> cargarListaAdjuntosCategoria() throws Exception {
        try {
            this.abrirConexion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            return adjuntosCategoriaDAO.cargarListaAdjuntosCategoria();
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends AdjuntosCategoriaTipo> cargarListaAdjuntosCategoriaTipo(Integer codCategoria) throws Exception {
        try {
            this.abrirConexion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            return adjuntosCategoriaDAO.cargarListaAdjuntosCategoriaTipo(codCategoria);
        } finally {
            this.cerrarConexion();
        }
    }
}

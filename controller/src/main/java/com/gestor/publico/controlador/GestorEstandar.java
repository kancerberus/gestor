/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.controlador;
import com.gestor.gestor.dao.CicloDAO;
import com.gestor.gestor.dao.SeccionDAO;
import com.gestor.gestor.dao.SeccionDetalleDAO;
import com.gestor.gestor.dao.SeccionDetalleItemsDAO;
import com.gestor.gestor.dao.SeccionDetalleItemsAyudaDAO;
import com.gestor.controller.Gestor;
import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.SeccionDetalleItems;
import com.gestor.gestor.dao.AdjuntosCategoriaDAO;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author franj
 */
public class GestorEstandar extends Gestor implements Serializable{    
    
    public GestorEstandar() throws Exception {
        super();
    }
    
    public List<?> cargarListaCiclo() throws Exception {
        try {
            this.abrirConexion();
            CicloDAO cicloDAO = new CicloDAO(conexion);
            return cicloDAO.cargarListaCiclos();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaSeccion(String codCiclo) throws Exception {   
        
        try {
            this.abrirConexion();
            SeccionDAO seccionDAO = new SeccionDAO(conexion);                                                 
            return seccionDAO.cargarListaSeccion(codCiclo);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaSecciondetalle(String codCiclo, Integer codSeccion) throws Exception {   
        
        try {
            this.abrirConexion();
            SeccionDetalleDAO secciondetalleDAO = new SeccionDetalleDAO(conexion);                                                 
            return secciondetalleDAO.cargarListaSeccionDetalle(codCiclo, codSeccion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaSecciondetalleitems(String codCiclo, Integer codSeccion, Integer codDetalle) throws Exception {   
        
        try {
            this.abrirConexion();
            SeccionDetalleItemsDAO secciondetalleitemsDAO = new SeccionDetalleItemsDAO(conexion);                                                 
            return secciondetalleitemsDAO.cargarListaSeccionDetalleItems(codCiclo, codSeccion, codDetalle);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaSecciondetalleitemsnumeral() throws Exception {   
        
        try {
            this.abrirConexion();
            SeccionDetalleItemsDAO secciondetalleitemsDAO = new SeccionDetalleItemsDAO(conexion);                                                 
            return secciondetalleitemsDAO.cargarListaSeccionDetalleItems();
        } finally {
            this.cerrarConexion();
        }
    }  
       
    
    
    public List<?> cargarSecciondetalleitemsayudaList(String nomc,String nomsec,String nomsdetalle,String nomsditem) throws Exception {   
        
        try {
            this.abrirConexion();
            SeccionDetalleItemsAyudaDAO secciondetalleitemsayudaDAO = new SeccionDetalleItemsAyudaDAO(conexion);                                                 
            return secciondetalleitemsayudaDAO.cargarListaSeccionDetalleItemsayuda(nomc,nomsec,nomsdetalle,nomsditem);
        } finally {
            this.cerrarConexion();
        }
    }  

    public List<?> cargarCicloList(String numeral) throws Exception {   
        
        try {
            this.abrirConexion();
            CicloDAO cicloDAO = new CicloDAO(conexion);
            return cicloDAO.cargarListaCiclosnombre(numeral);            
        } finally {
            this.cerrarConexion();
        }
    }   
    
    public List<?> cargarSeccionList(String numeral) throws Exception {   
        
        try {
            this.abrirConexion();
            SeccionDAO seccionDAO = new SeccionDAO(conexion);
            return seccionDAO.cargarListaSeccionnombre(numeral);            
        } finally {
            this.cerrarConexion();
        }
    }   
    
    public List<?> cargarSecciondetalleList(String numeral) throws Exception {   
        
        try {
            this.abrirConexion();
            SeccionDetalleDAO secciondetalleDAO = new SeccionDetalleDAO(conexion);
            return secciondetalleDAO.cargarListaSecciondetallenombre(numeral);            
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarSecciondetalleitemsList(String numeral) throws Exception {
        try {
            this.abrirConexion();
            SeccionDetalleItemsDAO secciondetalleitemsDAO = new SeccionDetalleItemsDAO(conexion);
            return secciondetalleitemsDAO.cargarListaSecciondetalleitemsnombre(numeral);            
        } finally {
            this.cerrarConexion();
        }
    }

    
    public void almacenaralmacenarSeccionDetalleItemsAyuda(String numSecc, int codseccion, int coddetalle, int coditem, String cod_ciclo, String numeral1,String numeral2, String numeral3,String criterio,String mlegal, String mdeverif) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            SeccionDetalleItemsAyudaDAO seccionDetalleItemsAyudaDAO = new SeccionDetalleItemsAyudaDAO(conexion);
            seccionDetalleItemsAyudaDAO.insertarEstandar(numSecc, codseccion, coddetalle, coditem, cod_ciclo, numeral1, numeral2, numeral3, criterio, mlegal, mdeverif);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    
    
}

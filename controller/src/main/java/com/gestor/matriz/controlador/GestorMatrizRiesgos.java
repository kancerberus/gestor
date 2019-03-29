/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.matriz.controlador;


import com.gestor.controller.Gestor;
import com.gestor.matriz.MatrizRiesgos;
import com.gestor.matriz.dao.MatrizRiesgosDAO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Francisco
 */
public class GestorMatrizRiesgos extends Gestor implements Serializable{

    public GestorMatrizRiesgos() throws Exception {
        super();
    }
    
    
    public List<?> cargarListaFunciones(int codCargo) throws Exception {
        try {
            this.abrirConexion();
            MatrizRiesgosDAO matrizRiesgosDAO= new MatrizRiesgosDAO(conexion);
            return  matrizRiesgosDAO.cargarListaFunciones(codCargo);            
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaNivelDeficiencia() throws Exception {
        try {
            this.abrirConexion();
            MatrizRiesgosDAO matrizRiesgosDAO= new MatrizRiesgosDAO(conexion);
            return  matrizRiesgosDAO.cargarListaNivelDeficiencia();            
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaNivelExposicion() throws Exception {
        try {
            this.abrirConexion();
            MatrizRiesgosDAO matrizRiesgosDAO= new MatrizRiesgosDAO(conexion);
            return  matrizRiesgosDAO.cargarListaNivelExposicion();            
        } finally {
            this.cerrarConexion();
        }
    }
    
    public MatrizRiesgos cargarMatrizRiesgosCargoActividad(int codCargo, int codFuncion, int codigoEstablecimiento) throws Exception{
        try {
            this.abrirConexion();
            MatrizRiesgosDAO matrizRiesgosDAO = new MatrizRiesgosDAO(conexion);
            return matrizRiesgosDAO.cargarMatrizRiesgosCargoActividad(codCargo, codFuncion, codigoEstablecimiento);
        } finally {
            this.cerrarConexion();
        }        
    }
    
    public List<?> cargarListaMatrizCargosEstablecimiento(Integer codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            MatrizRiesgosDAO matrizRiesgosDAO = new MatrizRiesgosDAO(conexion);
            return matrizRiesgosDAO.cargarListaMatrizCargosEstablecimiento(codEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }
    
    
    
    public List<?> cargarListaCargosFuncionesEstablecimiento(Integer codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            MatrizRiesgosDAO matrizRiesgosDAO = new MatrizRiesgosDAO(conexion);
            return matrizRiesgosDAO.cargarListaCargosFuncionesEstablecimiento(codEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }
    public List<?> cargarListaNivelConsecuencia() throws Exception {
        try {
            this.abrirConexion();
            MatrizRiesgosDAO matrizRiesgosDAO= new MatrizRiesgosDAO(conexion);
            return  matrizRiesgosDAO.cargarListaNivelConsecuencia();            
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaRiesgos() throws Exception {
        try {
            this.abrirConexion();
            return new MatrizRiesgosDAO(this.conexion).cargarListaRiesgos();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaCategoriaRiesgos() throws Exception {
        try {
            this.abrirConexion();
            return new MatrizRiesgosDAO(this.conexion).cargarListaCategoriaRiesgos();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaMedidasIntervecion() throws Exception {
        try {
            this.abrirConexion();
            return new MatrizRiesgosDAO(this.conexion).cargarListaMedidasIntervecion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaElementosProteccion() throws Exception {
        try {
            this.abrirConexion();
            return new MatrizRiesgosDAO(this.conexion).cargarListaElementosProteccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaExposiciones(int codRiesgo) throws Exception {
        try {
            this.abrirConexion();
            return new MatrizRiesgosDAO(this.conexion).cargarListaExposiciones(codRiesgo);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaRiesgoPosibles(int codCategoriaRiesgo) throws Exception {
        try {
            this.abrirConexion();
            return new MatrizRiesgosDAO(this.conexion).cargarListaRiesgoPosibles(codCategoriaRiesgo);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarMatrizRiesgo(MatrizRiesgos matrizRiesgos) throws Exception {  
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            MatrizRiesgosDAO matrizRiesgosDAO= new MatrizRiesgosDAO(conexion);
            matrizRiesgosDAO.insertarMatrizRiesgos(matrizRiesgos);
            matrizRiesgosDAO.updiligenciado(matrizRiesgos);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
        
    } 
    
    public void validarMatrizRiesgos(){
                
    }
    
    public String cargarNombreAdjunto(int codigo_establecimiento, long codEvaluacion, int codCategoria, int codCategoriaTipo) throws Exception{
        try {
            this.abrirConexion();
            return new MatrizRiesgosDAO(this.conexion).cargarNombreAdjunto(codigo_establecimiento, codEvaluacion, codCategoria, codCategoriaTipo);
        } finally {
            this.cerrarConexion();
        }
    }
    
    
    
}

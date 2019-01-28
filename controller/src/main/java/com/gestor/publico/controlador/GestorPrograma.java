/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.controlador;
import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.dao.ProgramaDAO;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author franj
 */
public class GestorPrograma extends Gestor implements Serializable{
    
    public GestorPrograma() throws Exception {
        super();
    }
    
    public void validarPrograma(PlanTrabajoPrograma programa) throws Exception {
        if (programa.getNombre() == null || programa.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el Programa.", UtilLog.TW_VALIDACION);
        }
        
        programa.setNombre(programa.getNombre().trim().toUpperCase());
    }
    
    public void almacenarPrograma(PlanTrabajoPrograma pro) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            ProgramaDAO programaDAO = new ProgramaDAO(conexion);
            programaDAO.insertarPrograma(pro);
            programaDAO.actualizarPeso(pro);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarPrograma(PlanTrabajoPrograma programa) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            ProgramaDAO programaDAO= new ProgramaDAO(conexion);
            programaDAO.eliminarPrograma(programa);            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> cargarListaPrograma(int codEstablecimiento, int codObjetivo, int codPlantrabajo) throws Exception {
        try {
            this.abrirConexion();
            ProgramaDAO programaDAO= new ProgramaDAO(conexion);
            return programaDAO.cargarListaPrograma(codEstablecimiento, codObjetivo, codPlantrabajo);
        } finally {
            this.cerrarConexion();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;
import com.gestor.gestor.dao.MotivoCorreccionDAO;
import com.gestor.controller.Gestor;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author franj
 */
public class GestorMotivoCorreccion extends Gestor implements Serializable{
    
    public GestorMotivoCorreccion() throws Exception {
        super();
    }
    
    public List<?> cargarListaMotivocorreccion() throws Exception {
        try {
            this.abrirConexion();
            MotivoCorreccionDAO motivocorreccionDAO = new MotivoCorreccionDAO(conexion);
            return motivocorreccionDAO.cargarListaMotivocorreccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
}

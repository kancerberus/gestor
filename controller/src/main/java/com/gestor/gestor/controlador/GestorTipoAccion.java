/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;
import com.gestor.gestor.dao.TipoAccionDAO;
import com.gestor.controller.Gestor;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author franj
 */
public class GestorTipoAccion extends Gestor implements Serializable{
    
    public GestorTipoAccion() throws Exception {
        super();
    }
    
    public List<?> cargarListaTipoaccion() throws Exception {
        try {
            this.abrirConexion();
            TipoAccionDAO tipoaccionDAO = new TipoAccionDAO(conexion);
            return tipoaccionDAO.cargarListaTipoaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;
import com.gestor.gestor.dao.ClaseHallazgoDAO;
import com.gestor.controller.Gestor;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author franj
 */
public class GestorClaseHallazgo extends Gestor implements Serializable{
    
    public GestorClaseHallazgo() throws Exception {
        super();
    }
    
    public List<?> cargarListaClasehallazgo() throws Exception {
        try {
            this.abrirConexion();
            ClaseHallazgoDAO clasehallazgoDAO = new ClaseHallazgoDAO(conexion);
            return clasehallazgoDAO.cargarListaClasehallazgo();
        } finally {
            this.cerrarConexion();
        }
    }
    
}

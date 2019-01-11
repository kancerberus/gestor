/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;
import com.gestor.gestor.dao.FuenteHallazgoDAO;
import com.gestor.controller.Gestor;
import com.gestor.gestor.FuenteHallazgo;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author franj
 */
public class GestorFuenteHallazgo extends Gestor implements Serializable{
    
    public GestorFuenteHallazgo() throws Exception {
        super();
    }
    
    public List<?> cargarListaFuentehallazgo() throws Exception {
        try {
            this.abrirConexion();
            FuenteHallazgoDAO fuentehallazgoDAO = new FuenteHallazgoDAO(conexion);
            return fuentehallazgoDAO.cargarListaFuentehallazgo();
        } finally {
            this.cerrarConexion();
        }
    }
    
}

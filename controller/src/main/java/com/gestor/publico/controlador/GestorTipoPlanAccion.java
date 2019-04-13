/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.controlador;


import com.gestor.controller.Gestor;
import com.gestor.publico.TipoPlanAccion;
import com.gestor.publico.dao.TipoPlanAccionDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julian
 */
public class GestorTipoPlanAccion extends Gestor implements Serializable{

    public GestorTipoPlanAccion() throws Exception {
        super();
    }    
        
    public ArrayList<TipoPlanAccion> cargarListaTipoaccion() throws Exception {
        try {
            this.abrirConexion();
            TipoPlanAccionDAO tipoplanaccionDAO = new TipoPlanAccionDAO(conexion);
            return tipoplanaccionDAO.cargarListaTipoPlanAccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    
}

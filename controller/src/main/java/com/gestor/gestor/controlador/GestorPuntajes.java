/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.gestor.Puntajes;
import com.gestor.gestor.dao.PuntajesDAO;
import java.util.List;

/**
 *
 * @author juliano
 */
public class GestorPuntajes extends Gestor {

    public GestorPuntajes() throws Exception {
        super();
    }

    public List<Puntajes> cargarPuntajes(Integer codigoEstablecimiento) throws Exception {
        try {
            this.abrirConexion();

            return new PuntajesDAO(conexion).cargarPuntajes(codigoEstablecimiento);

        } finally {
            this.cerrarConexion();
        }
    }

}

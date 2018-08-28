/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.gestor.Ciclo;
import com.gestor.gestor.dao.CicloDAO;
import java.util.List;

/**
 *
 * @author juliano
 */
public class GestorCiclo extends Gestor {

    public GestorCiclo() throws Exception {
        super();
    }

    public List<Ciclo> cargarListaCiclos() throws Exception {
        try {
            this.abrirConexion();
            return new CicloDAO(conexion).cargarListaCiclos();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<Ciclo> cargarArbolCiclos() throws Exception {
        try {
            this.abrirConexion();
            return new CicloDAO(conexion).cargarListaCiclos();
        } finally {
            this.cerrarConexion();
        }
    }
}


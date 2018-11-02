/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.SeccionDetalleItemsPK;
import com.gestor.gestor.dao.AdjuntosCategoriaDAO;
import java.util.Collection;

/**
 *
 * @author Julian D Osorio G
 */
public class GestorAdjuntosCategoria extends Gestor {

    public GestorAdjuntosCategoria() throws Exception {
        super();
    }


    public Collection<? extends AdjuntosCategoria> cargarListaAdjuntosCategoria(SeccionDetalleItemsPK seccionDetalleItemsPK) throws Exception {
        try {
            this.abrirConexion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            return adjuntosCategoriaDAO.cargarListaAdjuntosCategoria(seccionDetalleItemsPK);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends AdjuntosCategoria> cargarListaAdjuntosCategoriapm() throws Exception {
        try {
            this.abrirConexion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            return adjuntosCategoriaDAO.cargarListaAdjuntosCategoriapm();
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends AdjuntosCategoriaTipo> cargarListaAdjuntosCategoriaTipo(Integer codCategoria) throws Exception {
        try {
            this.abrirConexion();
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            return adjuntosCategoriaDAO.cargarListaAdjuntosCategoriaTipo(codCategoria);
        } finally {
            this.cerrarConexion();
        }
    }
}

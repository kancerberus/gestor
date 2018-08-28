/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.gestor.SeccionDetalleItemsAyuda;
import com.gestor.gestor.SeccionDetalleItemsAyudaPK;
import com.gestor.gestor.dao.SeccionDetalleItemsAyudaDAO;
import java.util.Collection;

/**
 *
 * @author juliano
 */
public class GestorSeccionDetalleItemsAyuda extends Gestor {

    public GestorSeccionDetalleItemsAyuda() throws Exception {
        super();
    }

    public Collection<? extends SeccionDetalleItemsAyuda> cargarSeccionDetalleItemsAyudas(SeccionDetalleItemsAyudaPK seccionDetalleItemsAyudaPK) throws Exception {
        try {
            this.abrirConexion();
            return new SeccionDetalleItemsAyudaDAO(conexion).cargarSeccionDetalleItemsAyudas(seccionDetalleItemsAyudaPK);
        } finally {
            this.cerrarConexion();
        }
    }
}

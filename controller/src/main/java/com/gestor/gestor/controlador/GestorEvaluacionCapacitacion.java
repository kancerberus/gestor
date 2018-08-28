/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.gestor.EvaluacionCapacitacion;
import com.gestor.gestor.EvaluacionCapacitacionDetalle;
import com.gestor.gestor.dao.EvaluacionCapacitacionDAO;
import java.sql.SQLException;
import java.util.Collection;

/**
 *
 * @author juliano
 */
public class GestorEvaluacionCapacitacion extends Gestor {

    public GestorEvaluacionCapacitacion() throws Exception {
        super();
    }

    public Collection<? extends EvaluacionCapacitacionDetalle> cargarListaEvaluacionCapacitacionDetalle(Long codEvaluacion, int codigoEstablecimiento, String codCiclo, int codSeccion, int codDetalle, int codItem) throws SQLException, Exception {

        try {
            this.abrirConexion();
            EvaluacionCapacitacionDAO evaluacionCapacitacionDAO = new EvaluacionCapacitacionDAO(conexion);
            return evaluacionCapacitacionDAO.cargarListaEvaluacionCapacitacionDetalle(codEvaluacion, codigoEstablecimiento, codCiclo, codSeccion, codDetalle, codItem);
        } finally {
            this.cerrarConexion();
        }
    }

    public EvaluacionCapacitacionDetalle validarEvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetalle ecd) throws Exception {
        if (ecd.getNombre() == null || ecd.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingrese el nombre del plan de acción.", UtilLog.TW_VALIDACION);
        }
        if (ecd.getDescripcion() == null || ecd.getDescripcion().equalsIgnoreCase("")) {
            throw new Exception("Ingrese el nombre del plan de acción.", UtilLog.TW_VALIDACION);
        }
        ecd.setNombre(ecd.getNombre().toUpperCase().trim());
        ecd.setDescripcion(ecd.getDescripcion().toUpperCase().trim());

        return ecd;
    }

    public Long consultarEvaluacionCapacitacion(Long codEvaluacion, int codigoEstablecimiento, String estado) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionCapacitacionDAO evaluacionCapacitacionDAO = new EvaluacionCapacitacionDAO(conexion);
            return evaluacionCapacitacionDAO.consultarEvaluacionCapacitacion(codEvaluacion, codigoEstablecimiento, estado);
        } finally {
            this.cerrarConexion();
        }
    }

    public void procesarCapacitacion(EvaluacionCapacitacion ec) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            EvaluacionCapacitacionDAO evaluacionCapacitacionDAO = new EvaluacionCapacitacionDAO(conexion);
            evaluacionCapacitacionDAO.upsertEvaluacionCapacitacion(ec);
            evaluacionCapacitacionDAO.insertaEvaluacionCapacitacionDetalle(ec.getEvaluacionCapacitacionDetalle());

            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public int actualizarEstadoEvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetalle ecd) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionCapacitacionDAO evaluacionCapacitacionDAO = new EvaluacionCapacitacionDAO(conexion);
            return evaluacionCapacitacionDAO.actualizarEstadoEvaluacionCapacitacionDetalle(ecd);
        } finally {
            this.cerrarConexion();
        }
    }

    public void actualizarEvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetalle ecd) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionCapacitacionDAO evaluacionCapacitacionDAO = new EvaluacionCapacitacionDAO(conexion);
            evaluacionCapacitacionDAO.actualizarEvaluacionCapacitacionDetalle(ecd);
        } finally {
            this.cerrarConexion();
        }
    }
}

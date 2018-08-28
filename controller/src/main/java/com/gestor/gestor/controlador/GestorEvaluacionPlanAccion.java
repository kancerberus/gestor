/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.gestor.EvaluacionPlanAccion;
import com.gestor.gestor.EvaluacionPlanAccionDetalle;
import com.gestor.gestor.dao.EvaluacionPlanAccionDAO;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author juliano
 */
public class GestorEvaluacionPlanAccion extends Gestor {

    public GestorEvaluacionPlanAccion() throws Exception {
        super();
    }

    public EvaluacionPlanAccionDetalle validarEvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetalle epd) throws Exception {
        if (epd.getNombre() == null || epd.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingrese el nombre del plan de acción.", UtilLog.TW_VALIDACION);
        }
        if (epd.getDescripcion() == null || epd.getDescripcion().equalsIgnoreCase("")) {
            throw new Exception("Ingrese el nombre del plan de acción.", UtilLog.TW_VALIDACION);
        }
        epd.setNombre(epd.getNombre().toUpperCase().trim());
        epd.setDescripcion(epd.getDescripcion().toUpperCase().trim());
        return epd;
    }

    public void procesarPlanAccion(EvaluacionPlanAccion ep) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            evaluacionPlanAccionDAO.upsertEvaluacionPlanAccion(ep);
            evaluacionPlanAccionDAO.insertaEvaluacionPlanAccionDetalle(ep.getEvaluacionPlanAccionDetalle());

            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccion(Long codEvaluacion, int codigoEstablecimiento, String codCiclo, int codSeccion, int codDetalle, int codItem) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            return evaluacionPlanAccionDAO.cargarListaEvaluacionPlanAccion(codEvaluacion, codigoEstablecimiento, codCiclo, codSeccion, codDetalle, codItem);
        } finally {
            this.cerrarConexion();
        }
    }

    public Long consultarEvaluacionPlanAccion(Long codEvaluacion, int codigoEstablecimiento, String estado) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            return evaluacionPlanAccionDAO.consultarEvaluacionPlanAccion(codEvaluacion, codigoEstablecimiento, estado);
        } finally {
            this.cerrarConexion();
        }
    }

    public int actualizarEstadoEvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetalle epad) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            return evaluacionPlanAccionDAO.actualizarEstadoEvaluacionPlanAccionDetalle(epad);
        } finally {
            this.cerrarConexion();
        }
    }

    public void actualizarEvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetalle epd) throws Exception {
        try {
            this.abrirConexion();

            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            evaluacionPlanAccionDAO.actualizarEvaluacionPlanAccionDetalle(epd);

        } finally {
            this.cerrarConexion();
        }
    }
}

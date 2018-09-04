/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.App;
import com.gestor.entity.UtilLog;
import com.gestor.gestor.dao.EvaluacionAdjuntosDAO;
import com.gestor.publico.EvaluacionAdjuntos;
import com.gestor.publico.EvaluacionAdjuntosPK;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

/**
 *
 * @author juliano
 */
public class GestorEvaluacionAdjuntos extends Gestor {

    public GestorEvaluacionAdjuntos() throws Exception {
        super();
    }

    public EvaluacionAdjuntos validarEvaluacionAdjuntos(EvaluacionAdjuntos evaluacionAdjuntos) throws Exception {
        if (evaluacionAdjuntos.getEvaluacionAdjuntosPK() == null) {
            throw new Exception("No se pudo cargar la información de la evaluación.", UtilLog.TW_VALIDACION);
        }
        if (evaluacionAdjuntos.getNombre() == null || evaluacionAdjuntos.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el nombre del adjunto.", UtilLog.TW_VALIDACION);
        }
        if (evaluacionAdjuntos.getDescripcion() == null || evaluacionAdjuntos.getDescripcion().equalsIgnoreCase("")) {
            throw new Exception("Ingresa la descripción del adjunto.", UtilLog.TW_VALIDACION);
        }
        if (evaluacionAdjuntos.getMesesVigencia() == null || evaluacionAdjuntos.getMesesVigencia() == 0) {
            evaluacionAdjuntos.setMesesVigencia(App.EVALUACION_ADJUNTOS_MESES_VIGENCIA);
        }
        if (evaluacionAdjuntos.getFechaInicioVigencia() == null) {
            throw new Exception("No se pudo determinar la fecha inicio de vigencia del archivo, intente realizar el proceso de nuevo.", UtilLog.TW_VALIDACION);
        }
        evaluacionAdjuntos.setNombre(evaluacionAdjuntos.getNombre().toUpperCase().trim());
        evaluacionAdjuntos.setDescripcion(evaluacionAdjuntos.getDescripcion().trim());

        return evaluacionAdjuntos;
    }

    public void procesarEvaluacionAdjuntos(EvaluacionAdjuntos evaluacionAdjuntos) throws Exception {
        try {
            this.abrirConexion();

            EvaluacionAdjuntosDAO evaluacionAdjuntosDAO = new EvaluacionAdjuntosDAO(conexion);
            evaluacionAdjuntosDAO.insertaEvaluacionAdjuntos(evaluacionAdjuntos);
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends EvaluacionAdjuntos> cargarEvaluacionAdjuntos(EvaluacionAdjuntosPK evaluacionAdjuntosPK) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionAdjuntosDAO evaluacionAdjuntosDAO = new EvaluacionAdjuntosDAO(conexion);
            return evaluacionAdjuntosDAO.cargarEvaluacionAdjuntos(evaluacionAdjuntosPK);
        } finally {
            this.cerrarConexion();
        }
    }

    public void actualizarEstadoEvaluacionAdjuntos(EvaluacionAdjuntos ea) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionAdjuntosDAO evaluacionAdjuntosDAO = new EvaluacionAdjuntosDAO(conexion);
            evaluacionAdjuntosDAO.actualizarEstadoEvaluacionAdjuntos(ea);
        } finally {
            this.cerrarConexion();
        }
    }

    public EvaluacionAdjuntos calcularVigenciaAdjunto(EvaluacionAdjuntos evaluacionAdjuntos) throws Exception {
        evaluacionAdjuntos.setFechaFinVigencia(evaluacionAdjuntos.getFechaInicioVigencia());

        if (evaluacionAdjuntos.getMesesVigencia() != null && evaluacionAdjuntos.getMesesVigencia() > 0) {
            GregorianCalendar fechaFinVigencia = new GregorianCalendar();
            fechaFinVigencia.setTime(evaluacionAdjuntos.getFechaInicioVigencia());
            fechaFinVigencia.add(Calendar.DAY_OF_MONTH, evaluacionAdjuntos.getMesesVigencia() * 30);
            evaluacionAdjuntos.setFechaFinVigencia(fechaFinVigencia.getTime());
        } else {
            GregorianCalendar fechaFinVigencia = new GregorianCalendar();
            fechaFinVigencia.setTime(evaluacionAdjuntos.getFechaInicioVigencia());
            fechaFinVigencia.add(Calendar.DAY_OF_MONTH, App.EVALUACION_ADJUNTOS_MESES_VIGENCIA_CALCULO * 30);
            evaluacionAdjuntos.setFechaFinVigencia(fechaFinVigencia.getTime());
        }

        return evaluacionAdjuntos;
    }
}

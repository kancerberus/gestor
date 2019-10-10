/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.App;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.dao.AdjuntosCategoriaDAO;
import com.gestor.gestor.dao.EvaluacionAdjuntosDAO;
import com.gestor.publico.EvaluacionAdjuntos;
import com.gestor.publico.EvaluacionAdjuntosPK;
import com.gestor.publico.Responsable;
import com.gestor.publico.dao.ResponsableDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

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
        if (evaluacionAdjuntos.getAdjuntosCategoria() == null || evaluacionAdjuntos.getAdjuntosCategoria().getCodCategoria() == null
                || evaluacionAdjuntos.getAdjuntosCategoria().getCodCategoria() == 0) {
            throw new Exception("Selecciona la categoria del adjunto.", UtilLog.TW_VALIDACION);
        }
        
        if (evaluacionAdjuntos.getResponsable() == null || evaluacionAdjuntos.getResponsable().getCedula() == null
                || evaluacionAdjuntos.getResponsable().getCedula().equalsIgnoreCase("")) {
            throw new Exception("Selecciona el responsable del adjunto.", UtilLog.TW_VALIDACION);
        }
        
        if (evaluacionAdjuntos.getAdjuntosCategoria().getAdjuntosCategoriaTipo() == null
                || evaluacionAdjuntos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK() == null
                || evaluacionAdjuntos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo() == null
                || evaluacionAdjuntos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo() == 0) {
            throw new Exception("Selecciona el tipo del adjunto.", UtilLog.TW_VALIDACION);
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
    
    public String cargarDireccionAdjunto(String archivo) throws Exception{
        try{            
            this.abrirConexion();
            EvaluacionAdjuntosDAO evaluacionAdjuntosDAO = new EvaluacionAdjuntosDAO(conexion);
            String direccionArchivo=evaluacionAdjuntosDAO.cargarDireccionArchivo(archivo);
            return direccionArchivo;
            } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends EvaluacionAdjuntos> cargarEvaluacionAdjuntos(EvaluacionAdjuntosPK evaluacionAdjuntosPK) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionAdjuntosDAO evaluacionAdjuntosDAO = new EvaluacionAdjuntosDAO(conexion);
            AdjuntosCategoriaDAO adjuntosCategoriaDAO = new AdjuntosCategoriaDAO(conexion);
            Collection<? extends EvaluacionAdjuntos> evaluacionAdjuntosList = evaluacionAdjuntosDAO.cargarEvaluacionAdjuntos(evaluacionAdjuntosPK);
            for (EvaluacionAdjuntos ea : evaluacionAdjuntosList) {
                ea.getAdjuntosCategoria().setAdjuntosCategoriaTipoList((List<AdjuntosCategoriaTipo>) adjuntosCategoriaDAO.cargarListaAdjuntosCategoriaTipo(ea.getAdjuntosCategoria().getCodCategoria()));
            }
            return evaluacionAdjuntosList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends EvaluacionAdjuntos> cargarListaAdjuntosCategoriaTipo(Integer codEstablecimiento,Integer codEvaluacion,String codCiclo,Integer codSeccion,Integer codDetalle, Integer codItem,Integer codCategoria,Integer codCatTipo) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionAdjuntosDAO evaluacionAdjuntosDAO = new EvaluacionAdjuntosDAO(conexion);            
            return evaluacionAdjuntosDAO.cargarListaAdjuntosCategoriaTipo(codEstablecimiento, codEvaluacion, codCiclo, codSeccion, codDetalle, codItem, codCategoria, codCatTipo);                                                
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

    public Integer siguienteVersionCategoriaTipo(EvaluacionAdjuntosPK evaluacionAdjuntosPK, int codCategoria, int codCategoriaTipo) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionAdjuntosDAO evaluacionAdjuntosDAO = new EvaluacionAdjuntosDAO(conexion);
            return evaluacionAdjuntosDAO.siguienteVersionCategoriaTipo(evaluacionAdjuntosPK,codCategoria, codCategoriaTipo);
        } finally {
            this.cerrarConexion();
        }
    }
}

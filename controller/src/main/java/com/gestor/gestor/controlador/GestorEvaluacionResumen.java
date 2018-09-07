/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.dao.GeneralDAO;
import com.gestor.entity.App;
import com.gestor.entity.UtilFecha;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionResumen;
import com.gestor.gestor.dao.EvaluacionDAO;
import com.gestor.gestor.dao.EvaluacionResumenDAO;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author juliano
 */
public class GestorEvaluacionResumen extends Gestor {

    public GestorEvaluacionResumen() throws Exception {
        super();
    }

    public void procesarEvaluacionResumen(Evaluacion evaluacion, List<EvaluacionResumen> evaluacionResumenList) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionResumenDAO evaluacionResumenDAO = new EvaluacionResumenDAO(conexion);
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO(conexion);
            GeneralDAO generalDAO = new GeneralDAO(conexion);

            for (EvaluacionResumen er : evaluacionResumenList) {
                evaluacionResumenDAO.insertarEvaluacionResumen(er);
            }

            JsonObject o = new JsonObject();
            o.addProperty("codigoEstablecimiento", String.valueOf(evaluacion.getEvaluacionPK().getCodigoEstablecimiento()));
            o.addProperty("codEvaluacion", String.valueOf(evaluacion.getEvaluacionPK().getCodEvaluacion()));
            o.addProperty("fechaResumen", UtilFecha.formatoFecha(generalDAO.now(), null, UtilFecha.PATRON_FECHA_YYYYMMDD));
            o.addProperty("calificacion", evaluacion.getCalificacion());
            o.addProperty("peso", evaluacion.getPeso());
            o.addProperty("estado", evaluacion.getEstado());

            evaluacionDAO.insertarResumenes(evaluacion.getEvaluacionPK().getCodEvaluacion(), evaluacion.getEvaluacionPK().getCodigoEstablecimiento(),
                    evaluacion.getEvaluacionPK().getCodigoEstablecimiento() + "-" + evaluacion.getEvaluacionPK().getCodEvaluacion()
                    + "-" + UtilFecha.formatoFecha(generalDAO.now(), null, UtilFecha.PATRON_FECHA_YYYYMMDD),
                    o);
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends EvaluacionResumen> cargarEvaluacionResumen(Long codEvaluacion, int codigoEstablecimiento, Date fechaResumen) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionResumenDAO evaluacionResumenDAO = new EvaluacionResumenDAO(conexion);
            return evaluacionResumenDAO.cargarEvaluacionResumen(codEvaluacion, codigoEstablecimiento, fechaResumen);
        } finally {
            this.cerrarConexion();
        }
    }

    public String calcularEstadoEvaluacion(Double calificacion, Double peso) {
        Double c = calificacion / peso;
        if (c <= 60) {
            return App.EVALUACION_ESTADO_CRITICA;
        } else if (c > 85) {
            return App.EVALUACION_ESTADO_ACEPTABLE;
        } else {
            return App.EVALUACION_ESTADO_MODERADA_ACEPTABLE;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.App;
import com.gestor.entity.ArrayAdapterFactory;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilRest;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionPK;
import com.gestor.gestor.EvaluacionPuntajes;
import com.gestor.gestor.EvaluacionPuntajesPK;
import com.gestor.gestor.Puntajes;
import com.gestor.gestor.Resumenes;
import com.gestor.gestor.dao.EvaluacionDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author juliano
 */
public class GestorEvaluacion extends Gestor {

    public GestorEvaluacion() throws Exception {
        super();
    }

    public Evaluacion validarEvaluacion(Evaluacion e) throws Exception {
        if (e.getEvaluacionPK() == null || e.getEvaluacionPK().getCodEvaluacion() == null) {
            throw new Exception("No fue posible generar el consecutivo de la evaluación.", UtilLog.TW_VALIDACION);
        }
        if (e.getEvaluacionPK().getCodigoEstablecimiento() == 0) {
            throw new Exception("No se determino una empresa seleccioanda.", UtilLog.TW_VALIDACION);
        }
        if (e.getDocumentoUsuario() == null || e.getDocumentoUsuario().equalsIgnoreCase("")) {
            throw new Exception("Error en la sesión, por favor cierre sesión e ingresa de nuevo.", UtilLog.TW_VALIDACION);
        }
        return e;
    }

    public void procesarEvaluacion(Evaluacion e) throws Exception {
        try {
            this.abrirConexion();

            EvaluacionDAO evaluacionDAO = new EvaluacionDAO(conexion);
            evaluacionDAO.upsertEvaluacion(e);
            e.getEvaluacionPuntajesList().forEach((ep) -> {
                try {
                    evaluacionDAO.insertEvaluacionPuntajes(ep);
                } catch (SQLException ex) {
                    Logger.getLogger(GestorEvaluacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (Exception ex) {
            throw ex;
        } finally {
            this.cerrarConexion();
        }
    }

    public List<Evaluacion> cargarEvaluacionList(Integer codigoEstablecimiento, String mostrarEvaluaciones) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO(conexion);
            if (mostrarEvaluaciones != null && mostrarEvaluaciones.equalsIgnoreCase("S")) {
                return evaluacionDAO.cargarEvaluacionList();
            } else {
                return evaluacionDAO.cargarEvaluacionList(codigoEstablecimiento);
            }
        } finally {
            this.cerrarConexion();
        }
    }

    public List<EvaluacionPuntajes> generarEvaluacionPuntajes(int codigoEstablecimiento, Long codEvaluacion, List<Puntajes> puntajesList) {
        List<EvaluacionPuntajes> evaluacionPuntajeses = new ArrayList<>();
        puntajesList.forEach((p) -> {
            EvaluacionPuntajes evaluacionPuntajes = new EvaluacionPuntajes(new EvaluacionPuntajesPK(codigoEstablecimiento, codEvaluacion, p.getPuntajesPK().getCodPuntaje()), p.getDescripcion(),
                    p.getPlanAccion(), p.getCapacitacion(), p.getCalifica(), p.getOrden());
            evaluacionPuntajeses.add(evaluacionPuntajes);
        });
        return evaluacionPuntajeses;
    }

    public List<EvaluacionPuntajes> cargarEvaluacionPuntajes(int codigoEstablecimiento, Long codEvaluacion) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO(conexion);
            return evaluacionDAO.cargarEvaluacionPuntajes(codigoEstablecimiento, codEvaluacion);
        } finally {
            this.cerrarConexion();
        }

    }

    public EvaluacionPuntajes cargarEvaluacionPuntajes(int codigoEstablecimiento, Long codEvaluacion, String descripcion) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO(conexion);
            return evaluacionDAO.cargarEvaluacionPuntajes(codigoEstablecimiento, codEvaluacion, descripcion);

        } finally {
            this.cerrarConexion();
        }
    }

    public Integer avanceEvaluacion(int codigoEstablecimiento, Long codEvaluacion) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO(conexion);
            return evaluacionDAO.avanceEvaluacion(codigoEstablecimiento, codEvaluacion);

        } finally {
            this.cerrarConexion();
        }
    }

    public Integer avanceEvaluacionCiclo(int codigoEstablecimiento, Long codEvaluacion, String codCiclo) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO(conexion);
            return evaluacionDAO.avanceEvaluacionCiclo(codigoEstablecimiento, codEvaluacion, codCiclo);

        } finally {
            this.cerrarConexion();
        }
    }

    public void finalizarEvaluacion(Evaluacion evaluacion) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO(conexion);
            evaluacion.setEstado(App.EVALUACION_ESTADO_CERRADO);
            evaluacionDAO.actualizarEstadoEvaluacion(evaluacion.getEvaluacionPK(), evaluacion.getEstado());
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public List<Resumenes> getResumenesFromJson(String resumenes) throws ParseException {
        List<Resumenes> resumenesList = new ArrayList<>();
        JsonArray array = (JsonArray) new JsonParser().parse(resumenes);
        for (JsonElement je : array) {
            je.getAsJsonObject().keySet().forEach((t) -> {
                List<Resumenes> r2;
                Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
                r2 = gson.fromJson(je.getAsJsonObject().get(t), new TypeToken<List<Resumenes>>() {
                }.getType());
                if (r2 != null) {
                    r2.forEach((r) -> {
                        if (r.getFechaResumen() != null) {
                            resumenesList.add(r);
                        }
                    });
                }

            });

        }

        Collections.sort(resumenesList, new Comparator<Resumenes>() {
            @Override
            public int compare(final Resumenes object1, final Resumenes object2) {
                System.out.println("o1,02" + object1.getFechaResumen().compareTo(object2.getFechaResumen()));
                System.out.println("o2,01" + object2.getFechaResumen().compareTo(object1.getFechaResumen()));
                return object2.getFechaResumen().compareTo(object1.getFechaResumen());
            }
        });
//        Collections.sort(resumenesList, (final Resumenes object1, final Resumenes object2) -> object1.getFechaResumen().compareTo(object2.getFechaResumen()));

        return resumenesList;
    }

    public String cargarResumenesEvaluacion(EvaluacionPK evaluacionPK) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO(conexion);
            return evaluacionDAO.cargarResumenesEvaluacion(evaluacionPK);
        } finally {
            this.cerrarConexion();
        }
    }
}

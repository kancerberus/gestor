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
import com.gestor.gestor.EvaluacionPlanAccionDetalleNotas;
import com.gestor.gestor.EvaluacionPlanAccionDetalleNotasPK;
import com.gestor.gestor.dao.EvaluacionPlanAccionDAO;
import com.gestor.publico.Establecimiento;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.ViewExpiredException;

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
        if (epd.getResponsable() == null || epd.getResponsable().getCedula() == null || epd.getResponsable().getCedula().equalsIgnoreCase("")) {
            throw new Exception("Ingrese el responsable del plan de acción.", UtilLog.TW_VALIDACION);
        }
        epd.setNombre(epd.getNombre().toUpperCase().trim());
       
//        epd.setResponsable(epd.getResponsable().toUpperCase().trim());
        return epd;
    }

    public void procesarPlanAccion(EvaluacionPlanAccion ep) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            evaluacionPlanAccionDAO.upsertEvaluacionPlanAccion(ep);
            evaluacionPlanAccionDAO.insertaEvaluacionPlanAccionDetalle(ep.getEvaluacionPlanAccionDetalle());
            evaluacionPlanAccionDAO.insertaEvaluacionPlanAccionDetalleNotas(ep.getEvaluacionPlanAccionDetalle().getEvaluacionPlanAccionDetalleNotas());
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void procesarTareaRiesgo(EvaluacionPlanAccion ep) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            evaluacionPlanAccionDAO.upsertEvaluacionPlanAccion(ep);
            evaluacionPlanAccionDAO.insertaEvaluacionPlanAccionDetalle(ep.getEvaluacionPlanAccionDetalle());
            evaluacionPlanAccionDAO.insertaEvaluacionPlanAccionDetalleNotas(ep.getEvaluacionPlanAccionDetalle().getEvaluacionPlanAccionDetalleNotas());
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
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaMatrizTareaRiesgo(int codigoEstablecimiento, int codMatriz) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            return evaluacionPlanAccionDAO.cargarListaMatrizTareaRiesgo(codigoEstablecimiento, codMatriz);
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
    
    public void actualizarEvaluacionPlanAccionDetalleRiesgoMatriz(EvaluacionPlanAccionDetalle epd) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            evaluacionPlanAccionDAO.actualizarEvaluacionPlanAccionDetalleRiesgoMatriz(epd);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccion(String condicion) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            return evaluacionPlanAccionDAO.cargarListaEvaluacionPlanAccion(condicion);
        } finally {
            this.cerrarConexion();
        }
    }

    public List<EvaluacionPlanAccionDetalleNotas> cargarEvaluacionPlanAccionDetalleNotasList(EvaluacionPlanAccionDetalleNotasPK evaluacionPlanAccionDetalleNotasPK) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            return evaluacionPlanAccionDAO.cargarEvaluacionPlanAccionDetalleNotasList(evaluacionPlanAccionDetalleNotasPK);
        } finally {
            this.cerrarConexion();
        }
    }

    public EvaluacionPlanAccionDetalleNotas validarEvaluacionPlanAccionDetalleNotas(EvaluacionPlanAccionDetalleNotas epadn) throws Exception {
        if (epadn == null || epadn.getDescripcion() == null || epadn.getDescripcion().equalsIgnoreCase("")) {
            throw new Exception("Ingrese la descripción del seguimiento", UtilLog.TW_VALIDACION);
        }
        if (epadn.getNombre() == null || epadn.getNombre().equalsIgnoreCase("")) {
            epadn.setNombre("SEGUIMIENTO");
        }
        if (epadn.getDocumentoUsuario() == null || epadn.getDocumentoUsuario().equalsIgnoreCase("")) {
            throw new ViewExpiredException();
        }
        epadn.setDescripcion(epadn.getDescripcion().trim());
        return epadn;
    }

    public void procesarPlanAccionDetalleNotas(EvaluacionPlanAccionDetalleNotas epadn) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            evaluacionPlanAccionDAO.upsertEvaluacionPlanAccionDetalleNotas(epadn);
        } finally {
            this.cerrarConexion();
        }
    }

    public void cerrarPlanAccionDetalle(EvaluacionPlanAccionDetalle epad) throws Exception {
        try {
            this.abrirConexion();
            EvaluacionPlanAccionDAO evaluacionPlanAccionDAO = new EvaluacionPlanAccionDAO(conexion);
            evaluacionPlanAccionDAO.actualizarEstadoEvaluacionPlanAccionDetalle(epad);
        } finally {
            this.cerrarConexion();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.entity.App;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.controlador.GestorEvaluacionResumen;
import com.gestor.modelo.Sesion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author juliano
 */
@ManagedBean(name = "uiEvaluacionResumen")
@SessionScoped
public class UIEvaluacionResumen {

    private List<EvaluacionResumen> evaluacionResumenList;
    private List<String> evaluacionPuntajesItems;
    private Evaluacion evaluacion;
    private String estadoEvaluacion;

    public static final String COMPONENTES_REFRESCAR = "";

    private boolean guardarActivo = Boolean.TRUE;
    private boolean nuevoActivo = Boolean.FALSE;
    private boolean consultarActivo = Boolean.TRUE;
    private boolean cancelarActivo = Boolean.FALSE;
    private boolean eliminarActivo = Boolean.FALSE;

    public void nuevo() {
    }

    public void consultar() {
    }

    public void guardar() {
        try {
            GestorEvaluacionResumen gestorEvaluacionResumen = new GestorEvaluacionResumen();
            gestorEvaluacionResumen.procesarEvaluacionResumen(evaluacion, evaluacionResumenList);
            UtilMSG.addSuccessMsg("Resumen Generado", "Resumen Almacenado Satisfactoriamente");
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }

    }

    public void cancelar() {
    }

    public void eliminar() {
    }

    public String getComponentesRefrescar() {
        return COMPONENTES_REFRESCAR;
    }

    /**
     * @return the guardarActivo
     */
    public boolean isGuardarActivo() {
        return guardarActivo;
    }

    /**
     * @param guardarActivo the guardarActivo to set
     */
    public void setGuardarActivo(boolean guardarActivo) {
        this.guardarActivo = guardarActivo;
    }

    /**
     * @return the nuevoActivo
     */
    public boolean isNuevoActivo() {
        return nuevoActivo;
    }

    /**
     * @param nuevoActivo the nuevoActivo to set
     */
    public void setNuevoActivo(boolean nuevoActivo) {
        this.nuevoActivo = nuevoActivo;
    }

    /**
     * @return the eliminarActivo
     */
    public boolean isEliminarActivo() {
        return eliminarActivo;
    }

    /**
     * @param eliminarActivo the eliminarActivo to set
     */
    public void setEliminarActivo(boolean eliminarActivo) {
        this.eliminarActivo = eliminarActivo;
    }

    /**
     * @return the consultarActivo
     */
    public boolean isConsultarActivo() {
        return consultarActivo;
    }

    /**
     * @param consultarActivo the consultarActivo to set
     */
    public void setConsultarActivo(boolean consultarActivo) {
        this.consultarActivo = consultarActivo;
    }

    /**
     * @return the cancelarActivo
     */
    public boolean isCancelarActivo() {
        return cancelarActivo;
    }

    /**
     * @param cancelarActivo the cancelarActivo to set
     */
    public void setCancelarActivo(boolean cancelarActivo) {
        this.cancelarActivo = cancelarActivo;
    }

    public UIEvaluacionResumen() {
        try {
            evaluacionPuntajesItems = new ArrayList<>();
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            for (Puntajes p : s.getPuntajesList()) {
                evaluacionPuntajesItems.add(p.getPuntajesPK().getCodPuntaje());
            }

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    /**
     * @return the evaluacionResumenList
     */
    public List<EvaluacionResumen> getEvaluacionResumenList() {
        return evaluacionResumenList;
    }

    /**
     * @param evaluacionResumenList the evaluacionResumenList to set
     */
    public void setEvaluacionResumenList(List<EvaluacionResumen> evaluacionResumenList) {
        this.evaluacionResumenList = evaluacionResumenList;
    }

    /**
     * @return the evaluacionPuntajesItems
     */
    public List<String> getEvaluacionPuntajesItems() {
        return evaluacionPuntajesItems;
    }

    /**
     * @param evaluacionPuntajesItems the evaluacionPuntajesItems to set
     */
    public void setEvaluacionPuntajesItems(List<String> evaluacionPuntajesItems) {
        this.evaluacionPuntajesItems = evaluacionPuntajesItems;
    }

    /**
     * @return the evaluacion
     */
    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    /**
     * @param evaluacion the evaluacion to set
     */
    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getStyleEstado() {
        String style = "padding: 20px;"
                + "opacity: 0.83;"
                + "transition: opacity 0.6s;"
                + "color: white;";
        switch (getEstadoEvaluacion()) {
            case App.EVALUACION_ESTADO_CRITICA:
                style += "background-color: #f44336;";
                break;
            case App.EVALUACION_ESTADO_ACEPTABLE:
                style += "background-color: #4CAF50;";
                break;
            case App.EVALUACION_ESTADO_MODERADA_ACEPTABLE:
                style += "background-color: #ff9800;";
                //#2196F3;
                break;
        }
        return style;
    }

    public List<String> getEstadoEvaluacionLista() {
        List<String> texto = new ArrayList<>();
        switch (getEstadoEvaluacion()) {
            case App.EVALUACION_ESTADO_CRITICA:
                texto.add("Plan de Mejoramiento de inmediato a disposición de MinTrabajo");
                texto.add("Enviar a la ARL reporte de avances ( máx a los tres meses)");
                texto.add("Seguimiento anual y Plan de Visita  la empresa");
                break;
            case App.EVALUACION_ESTADO_ACEPTABLE:
                texto.add("Mantener la calificación y evidencias a disposición de MinTrabajo");
                texto.add("Incluir en el Plan de Anual de Trabajo las mejoras detectadas");
                break;
            case App.EVALUACION_ESTADO_MODERADA_ACEPTABLE:
                texto.add("Plan de Mejoramiento a disposición de MinTrabajo");
                texto.add("Enviar a la ARL reporte de avances (max a los seis meses)");
                texto.add("Plan de visita MinTrabajo");
                break;
        }
        return texto;
    }

    /**
     * @return the estadoEvaluacion
     */
    public String getEstadoEvaluacion() {
        Double calificacion = evaluacion.getCalificacion() / evaluacion.getPeso();
        if (calificacion <= 60) {
            estadoEvaluacion = App.EVALUACION_ESTADO_CRITICA;
        } else if (calificacion > 85) {
            estadoEvaluacion = App.EVALUACION_ESTADO_ACEPTABLE;
        } else {
            estadoEvaluacion = App.EVALUACION_ESTADO_MODERADA_ACEPTABLE;
        }
        return estadoEvaluacion;
    }

}

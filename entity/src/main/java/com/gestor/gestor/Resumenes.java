/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.entity.App;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author juliano
 */
public class Resumenes implements Serializable {

    private String codigoEstablecimiento;
    private Long codEvaluacion;
    private Date fechaResumen;
    private Double calificacion;
    private Double peso;

    private String estado;

    /**
     * @return the codigoEstablecimiento
     */
    public String getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    /**
     * @param codigoEstablecimiento the codigoEstablecimiento to set
     */
    public void setCodigoEstablecimiento(String codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    /**
     * @return the fechaResumen
     */
    public Date getFechaResumen() {
        return fechaResumen;
    }

    /**
     * @param fechaResumen the fechaResumen to set
     */
    public void setFechaResumen(Date fechaResumen) {
        this.fechaResumen = fechaResumen;
    }

    /**
     * @return the calificacion
     */
    public Double getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the peso
     */
    public Double getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    /**
     * @return the codEvaluacion
     */
    public Long getCodEvaluacion() {
        return codEvaluacion;
    }

    /**
     * @param codEvaluacion the codEvaluacion to set
     */
    public void setCodEvaluacion(Long codEvaluacion) {
        this.codEvaluacion = codEvaluacion;
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
        String estadoEvaluacion;
        if (calificacion <= 60) {
            estadoEvaluacion = App.EVALUACION_ESTADO_CRITICA;
        } else if (calificacion > 85) {
            estadoEvaluacion = App.EVALUACION_ESTADO_ACEPTABLE;
        } else {
            estadoEvaluacion = App.EVALUACION_ESTADO_MODERADA_ACEPTABLE;
        }
        return estadoEvaluacion;
    }

    /**
     * @return the styleEstado
     */
    public String getStyleEstado() {
        String style = "padding: -1px;"
                + "opacity: 0.83;"
                + "transition: opacity 0.6s;"
                + "color: white;";
        switch (estado) {
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

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

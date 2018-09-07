/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.entity.App;
import com.gestor.entity.UtilFecha;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.gestor.controlador.GestorEvaluacionResumen;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Usuarios;
import com.gestor.publico.controlador.GestorUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private List<Evaluacion> evaluacionList;

    public static final String COMPONENTES_REFRESCAR = "";

    private boolean guardarActivo = Boolean.TRUE;
    private boolean nuevoActivo = Boolean.FALSE;
    private boolean consultarActivo = Boolean.FALSE;
    private boolean cancelarActivo = Boolean.TRUE;
    private boolean eliminarActivo = Boolean.FALSE;

    //filtros
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();

    private List<Usuarios> usuariosList = new ArrayList<>();
    private Usuarios usuariosSeleccionado;

    private Map<String, String> evaluacionEstado = new HashMap<>();
    private List<String> evaluacionEstadoSeleccionado = new ArrayList<>();

    private Long codEvaluacion;
    private Date fechaEvaluacionInicio;
    private Date fechaEvaluacionFin;

    public UIEvaluacionResumen() {
        try {
            evaluacionPuntajesItems = new ArrayList<>();
            Sesion s = (Sesion) UtilJSF.getBean("sesion");

            GestorUsuario gestorUsuario = new GestorUsuario();

            for (Puntajes p : s.getPuntajesList()) {
                evaluacionPuntajesItems.add(p.getPuntajesPK().getCodPuntaje());
            }

            establecimientoList = new ArrayList<>();
            establecimientoList.addAll(s.getEstablecimientoList());

            evaluacionEstado = new HashMap<>();
            evaluacionEstado.put(App.EVALUACION_ESTADO_CRITICA_TEXTO, App.EVALUACION_ESTADO_CRITICA);
            evaluacionEstado.put(App.EVALUACION_ESTADO_MODERADA_ACEPTABLE_TEXTO, App.EVALUACION_ESTADO_MODERADA_ACEPTABLE);
            evaluacionEstado.put(App.EVALUACION_ESTADO_ACEPTABLE_TEXTO, App.EVALUACION_ESTADO_ACEPTABLE);

            usuariosList = new ArrayList<>();
            usuariosList.addAll(gestorUsuario.cargarListaUsuarios());

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public String consultarResumenEvaluacion() {
        try {
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            evaluacion = (Evaluacion) UtilJSF.getBean("varEvaluacionResumen");
            evaluacionResumenList = new ArrayList<>();
            GestorEvaluacionResumen gestorEvaluacionResumen = new GestorEvaluacionResumen();
            evaluacionResumenList.addAll(gestorEvaluacionResumen.cargarEvaluacionResumen(evaluacion.getEvaluacionPK().getCodEvaluacion(), evaluacion.getEvaluacionPK().getCodigoEstablecimiento(), evaluacion.getFechaResumen()));
            evaluacion.setResumenesList(gestorEvaluacion.getResumenesFromJson(evaluacion.getResumenes()));
            if (evaluacionResumenList == null || evaluacionResumenList.isEmpty()) {
                guardarActivo = Boolean.FALSE;
            }
            return ("/seguimiento/evaluacion-resumen.xhtml?faces-redirect=true");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        UtilMSG.addSupportMsg();
        return null;
    }

    private List<String> filtrarOpcionesSeleccionadas() {
        List<String> condicionesConsulta = new ArrayList<>();
        condicionesConsulta.add(App.CONDICION_WHERE);

        if (establecimientoListSeleccionado != null && !establecimientoListSeleccionado.isEmpty()) {
            String cadena = "0";
            for (Establecimiento e : establecimientoListSeleccionado) {
                cadena += "," + e.getCodigoEstablecimiento();
            }
            condicionesConsulta.add(Evaluacion.EVALUACION_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
        } else {
            condicionesConsulta.add(Boolean.TRUE.toString());
        }

        if (usuariosSeleccionado != null && usuariosSeleccionado.getUsuariosPK() != null
                && usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() != null && !usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario().equalsIgnoreCase("")) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(Evaluacion.EVALUACION_CONDICION_DOCUMENTO_USUARIO.replace("?", UtilTexto.CARACTER_COMILLA + usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() + UtilTexto.CARACTER_COMILLA));
        }

        if (codEvaluacion != null && codEvaluacion >= 0) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(Evaluacion.EVALUACION_CONDICION_COD_EVALUACION.replace("?", codEvaluacion.toString()));
        }

        if (evaluacionEstadoSeleccionado != null && !evaluacionEstadoSeleccionado.isEmpty()) {
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for (String s : evaluacionEstadoSeleccionado) {
                cadena += "," + UtilTexto.CARACTER_COMILLA + s + UtilTexto.CARACTER_COMILLA;
            }
            condicionesConsulta.add(Evaluacion.EVALUACION_CONDICION_ESTADO.replace("?", cadena));
        }

        if (fechaEvaluacionInicio != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(Evaluacion.EVALUACION_CONDICION_FECHA_REGISTRO_GTE.replace("?", UtilFecha.formatoFecha(fechaEvaluacionInicio, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        if (fechaEvaluacionFin != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(Evaluacion.EVALUACION_CONDICION_FECHA_REGISTRO_LTE.replace("?", UtilFecha.formatoFecha(fechaEvaluacionFin, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        return condicionesConsulta;
    }

    public void consultarEvaluaciones() {
        try {
            evaluacionList = new ArrayList<>();
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();
            evaluacionList.addAll(gestorEvaluacion.cargarListaEvaluacion(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            ));
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void nuevo() {
    }

    public void consultar() {
    }

    public void guardar() {
        try {
            GestorEvaluacionResumen gestorEvaluacionResumen = new GestorEvaluacionResumen();
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            evaluacion.setEstado(gestorEvaluacionResumen.calcularEstadoEvaluacion(evaluacion.getCalificacion(), evaluacion.getPeso()));

            gestorEvaluacionResumen.procesarEvaluacionResumen(evaluacion, evaluacionResumenList);
            evaluacion.setResumenes(gestorEvaluacion.cargarResumenesEvaluacion(evaluacion.getEvaluacionPK()));
            evaluacion.setResumenesList(gestorEvaluacion.getResumenesFromJson(evaluacion.getResumenes()));
            UtilMSG.addSuccessMsg("Resumen Generado", "Resumen Almacenado Satisfactoriamente");
            UtilJSF.update("formGuardarEvaluacion");
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    public String cancelar() {
        guardarActivo = Boolean.TRUE;
        return ("/seguimiento/evaluacion-resumen-consultar.xhtml?faces-redirect=true");
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
            case App.EVALUACION_ESTADO_CRITICA_TEXTO:
                style += "background-color: #f44336;";
                break;
            case App.EVALUACION_ESTADO_ACEPTABLE_TEXTO:
                style += "background-color: #4CAF50;";
                break;
            case App.EVALUACION_ESTADO_MODERADA_ACEPTABLE_TEXTO:
                style += "background-color: #ff9800;";
                //#2196F3;
                break;
        }
        return style;
    }

    public List<String> getEstadoEvaluacionLista() {
        List<String> texto = new ArrayList<>();
        switch (getEstadoEvaluacion()) {
            case App.EVALUACION_ESTADO_CRITICA_TEXTO:
                texto.add("Plan de Mejoramiento de inmediato a disposición de MinTrabajo");
                texto.add("Enviar a la ARL reporte de avances ( máx a los tres meses)");
                texto.add("Seguimiento anual y Plan de Visita  la empresa");
                break;
            case App.EVALUACION_ESTADO_ACEPTABLE_TEXTO:
                texto.add("Mantener la calificación y evidencias a disposición de MinTrabajo");
                texto.add("Incluir en el Plan de Anual de Trabajo las mejoras detectadas");
                break;
            case App.EVALUACION_ESTADO_MODERADA_ACEPTABLE_TEXTO:
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
        String estadoEvaluacion = "";
        try {
            GestorEvaluacionResumen gestorEvaluacionResumen = new GestorEvaluacionResumen();
            switch (gestorEvaluacionResumen.calcularEstadoEvaluacion(evaluacion.getCalificacion(), evaluacion.getPeso())) {
                case App.EVALUACION_ESTADO_CRITICA:
                    estadoEvaluacion = App.EVALUACION_ESTADO_CRITICA_TEXTO;
                    break;
                case App.EVALUACION_ESTADO_ACEPTABLE:
                    estadoEvaluacion = App.EVALUACION_ESTADO_ACEPTABLE_TEXTO;
                    break;
                case App.EVALUACION_ESTADO_MODERADA_ACEPTABLE:
                    estadoEvaluacion = App.EVALUACION_ESTADO_MODERADA_ACEPTABLE_TEXTO;
                    break;
            }
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return estadoEvaluacion;
    }

    /**
     * @return the evaluacionList
     */
    public List<Evaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    /**
     * @param evaluacionList the evaluacionList to set
     */
    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    /**
     * @return the establecimientoList
     */
    public List<Establecimiento> getEstablecimientoList() {
        return establecimientoList;
    }

    /**
     * @param establecimientoList the establecimientoList to set
     */
    public void setEstablecimientoList(List<Establecimiento> establecimientoList) {
        this.establecimientoList = establecimientoList;
    }

    /**
     * @return the establecimientoListSeleccionado
     */
    public List<Establecimiento> getEstablecimientoListSeleccionado() {
        return establecimientoListSeleccionado;
    }

    /**
     * @param establecimientoListSeleccionado the
     * establecimientoListSeleccionado to set
     */
    public void setEstablecimientoListSeleccionado(List<Establecimiento> establecimientoListSeleccionado) {
        this.establecimientoListSeleccionado = establecimientoListSeleccionado;
    }

    /**
     * @return the usuariosList
     */
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    /**
     * @param usuariosList the usuariosList to set
     */
    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    /**
     * @return the usuariosSeleccionado
     */
    public Usuarios getUsuariosSeleccionado() {
        return usuariosSeleccionado;
    }

    /**
     * @param usuariosSeleccionado the usuariosSeleccionado to set
     */
    public void setUsuariosSeleccionado(Usuarios usuariosSeleccionado) {
        this.usuariosSeleccionado = usuariosSeleccionado;
    }

    /**
     * @return the evaluacionEstado
     */
    public Map<String, String> getEvaluacionEstado() {
        return evaluacionEstado;
    }

    /**
     * @param evaluacionEstado the evaluacionEstado to set
     */
    public void setEvaluacionEstado(Map<String, String> evaluacionEstado) {
        this.evaluacionEstado = evaluacionEstado;
    }

    /**
     * @return the evaluacionEstadoSeleccionado
     */
    public List<String> getEvaluacionEstadoSeleccionado() {
        return evaluacionEstadoSeleccionado;
    }

    /**
     * @param evaluacionEstadoSeleccionado the evaluacionEstadoSeleccionado to
     * set
     */
    public void setEvaluacionEstadoSeleccionado(List<String> evaluacionEstadoSeleccionado) {
        this.evaluacionEstadoSeleccionado = evaluacionEstadoSeleccionado;
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

    /**
     * @return the fechaEvaluacionInicio
     */
    public Date getFechaEvaluacionInicio() {
        return fechaEvaluacionInicio;
    }

    /**
     * @param fechaEvaluacionInicio the fechaEvaluacionInicio to set
     */
    public void setFechaEvaluacionInicio(Date fechaEvaluacionInicio) {
        this.fechaEvaluacionInicio = fechaEvaluacionInicio;
    }

    /**
     * @return the fechaEvaluacionFin
     */
    public Date getFechaEvaluacionFin() {
        return fechaEvaluacionFin;
    }

    /**
     * @param fechaEvaluacionFin the fechaEvaluacionFin to set
     */
    public void setFechaEvaluacionFin(Date fechaEvaluacionFin) {
        this.fechaEvaluacionFin = fechaEvaluacionFin;
    }

}

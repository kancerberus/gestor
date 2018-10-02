/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.controller.GestorGeneral;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilFecha;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionCapacitacionDetalle;
import com.gestor.gestor.EvaluacionPlanAccionDetalle;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.gestor.controlador.GestorEvaluacionPlanAccion;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Usuarios;
import com.gestor.publico.controlador.GestorUsuario;
import com.gestor.seguimiento.controlador.GestorPlanMaestro;
import com.gestor.seguimiento.controlador.GestorPlanTitulo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Julian D Osorio G
 */
@ManagedBean(name = "uiPlanMaestro")
@SessionScoped
public class UIPlanMaestro {

    public static final String COMPONENTES_REFRESCAR = "";

    private boolean guardarActivo = false;
    private boolean nuevoActivo = true;
    private boolean consultarActivo = false;
    private boolean cancelarActivo = false;
    private boolean eliminarActivo = false;

    private List<PlanMaestro> planMaestroList;
    private Establecimiento establecimiento;

    private List<Evaluacion> evaluacionList = new ArrayList<>();
    private Evaluacion evaluacion = new Evaluacion();

    private Boolean filtroActivo = Boolean.TRUE;

    //filtros
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();

//    private List<Usuarios> usuariosList = new ArrayList<>();
//    private Usuarios usuariosSeleccionado;
//
//    private List<String> ciclosString = new ArrayList<>();
//    private List<String> ciclosStringSeleccionado = new ArrayList<>();
//
//    private Map<String, String> capacitacionEstado = new HashMap<>();
//    private List<String> capacitacionEstadoSeleccionado = new ArrayList<>();
//    private Long codEvaluacion;
//    private String responsable;
    private Date fechaActualizaInicio;
    private Date fechaActualizaFin;

    @PostConstruct
    public void init() {
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");

            establecimientoList = new ArrayList<>();
            establecimientoList.addAll(s.getEstablecimientoList());
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public String consultarPlanMaestro() {
        try {
            GestorPlanTitulo gestorPlanTitulo = new GestorPlanTitulo();
            PlanMaestro pm = (PlanMaestro) UtilJSF.getBean("varPlanMaestro");
            pm.getPlanTituloList().addAll(gestorPlanTitulo.cargarPlanTituloList( pm.getPlanMaestroPK().getCodigoEstablecimiento(), pm.getPlanMaestroPK().getCodEvaluacion()));
            UtilJSF.setBean("planMaestro", pm, UtilJSF.SESSION_SCOPE);
            return ("/seguimiento/plan-maestro.xhtml?faces-redirect=true");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }

    public void cancelarProcesarPlanMaestro() {
        establecimiento = new Establecimiento();
        evaluacionList = new ArrayList<>();
    }

    public void procesarPlanMaestro() {
        try {
            GestorPlanMaestro gestorPlanMaestro = new GestorPlanMaestro();
            GestorGeneral gestorGeneral = new GestorGeneral();
            GestorPlanTitulo gestorPlanTitulo = new GestorPlanTitulo();
            PlanMaestro pm = new PlanMaestro(new PlanMaestroPK(
                    evaluacion.getEvaluacionPK().getCodEvaluacion(),
                    evaluacion.getEvaluacionPK().getCodigoEstablecimiento(),
                    gestorGeneral.nextval(GestorGeneral.SEGUIMIENTO_PLAN_MAESTRO_COD_MAESTRO_SEQ)
            ), gestorGeneral.now(), gestorGeneral.now());

//            List<PlanTitulo> pt = new ArrayList<>();
//            pt.addAll(gestorPlanTitulo.cargarPlanTituloList(establecimiento));
            gestorPlanMaestro.procesarPlanMaestro(pm);

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void seleccionarEstablecimiento() {
        try {
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            this.establecimiento = (Establecimiento) UtilJSF.getBean("varEstablecimientoPm");
            this.evaluacionList = new ArrayList<>();

            List<String> condicionesConsulta = new ArrayList<>();
            condicionesConsulta.add(App.CONDICION_WHERE);
            condicionesConsulta.add(Evaluacion.EVALUACION_CONDICION_COD_ESTABLECIMIENTO.replace("?", String.valueOf(establecimiento.getCodigoEstablecimiento())));

            this.evaluacionList.addAll(gestorEvaluacion.cargarListaEvaluacion(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            ));
        } catch (Exception e) {
        }

    }

    private List<String> filtrarOpcionesSeleccionadas() {
        List<String> condicionesConsulta = new ArrayList<>();
        condicionesConsulta.add(App.CONDICION_WHERE);

        if (establecimientoListSeleccionado != null && !establecimientoListSeleccionado.isEmpty()) {
            String cadena = "0";
            for (Establecimiento e : establecimientoListSeleccionado) {
                cadena += "," + e.getCodigoEstablecimiento();
            }
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
        } else {
            condicionesConsulta.add(Boolean.TRUE.toString());
        }

        if (fechaActualizaInicio != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_FECHA_ACTUALIZA_GTE.replace("?", UtilFecha.formatoFecha(fechaActualizaInicio, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        if (fechaActualizaFin != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_FECHA_ACTUALIZA_LTE.replace("?", UtilFecha.formatoFecha(fechaActualizaFin, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        return condicionesConsulta;
    }

    public void consultarPlanMaestroLista() {
        try {
            planMaestroList = new ArrayList<>();
            GestorPlanMaestro gestorPlanMaestro = new GestorPlanMaestro();
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();
            planMaestroList.addAll(gestorPlanMaestro.cargarListaPlanMaestro(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            )
            );
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }

    public String cargarPlanMaestro() {
        try {
            UtilJSF.setBean("dialogo", new Dialogo(), UtilJSF.SESSION_SCOPE);
//            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();
            planMaestroList = new ArrayList<>();
            GestorPlanMaestro gestorPlanMaestro = new GestorPlanMaestro();
            GestorGeneral gestorGeneral = new GestorGeneral();

            List<String> condicionesConsulta = new ArrayList<>();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(gestorGeneral.now());
            gc.add(Calendar.DAY_OF_MONTH, -30);
            this.fechaActualizaInicio = gc.getTime();

            condicionesConsulta.add(App.CONDICION_WHERE);
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_FECHA_ACTUALIZA_GTE.replace("?", UtilFecha.formatoFecha(fechaActualizaInicio, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));

            planMaestroList.addAll(gestorPlanMaestro.cargarListaPlanMaestro(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            )
            );
            return ("/seguimiento/planes-maestros.xhtml?faces-redirect=true");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }

    public String nuevo() {
        return ("/seguimiento/plan-maestro-nuevo.xhtml?faces-redirect=true");
    }

    public void consultar() {
    }

    public void guardar() {
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

    /**
     * @return the filtroActivo
     */
    public Boolean getFiltroActivo() {
        return filtroActivo;
    }

    /**
     * @param filtroActivo the filtroActivo to set
     */
    public void setFiltroActivo(Boolean filtroActivo) {
        this.filtroActivo = filtroActivo;
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
     * @return the fechaActualizaInicio
     */
    public Date getFechaActualizaInicio() {
        return fechaActualizaInicio;
    }

    /**
     * @param fechaActualizaInicio the fechaActualizaInicio to set
     */
    public void setFechaActualizaInicio(Date fechaActualizaInicio) {
        this.fechaActualizaInicio = fechaActualizaInicio;
    }

    /**
     * @return the fechaActualizaFin
     */
    public Date getFechaActualizaFin() {
        return fechaActualizaFin;
    }

    /**
     * @param fechaActualizaFin the fechaActualizaFin to set
     */
    public void setFechaActualizaFin(Date fechaActualizaFin) {
        this.fechaActualizaFin = fechaActualizaFin;
    }

    /**
     * @return the planMaestroList
     */
    public List<PlanMaestro> getPlanMaestroList() {
        return planMaestroList;
    }

    /**
     * @param planMaestroList the planMaestroList to set
     */
    public void setPlanMaestroList(List<PlanMaestro> planMaestroList) {
        this.planMaestroList = planMaestroList;
    }

    /**
     * @return the establecimiento
     */
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    /**
     * @param establecimiento the establecimiento to set
     */
    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
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
}

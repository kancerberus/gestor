/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.controller.GestorGeneral;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilFecha;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.controlador.GestorEvaluacionPlanAccion;
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
@ManagedBean(name = "uiPlanAccion")
@SessionScoped

public class UIPlanAccion {

    private SeccionDetalleItems sdiSeleccionado;
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles = new ArrayList<>();
    private Boolean modificarActivo = Boolean.FALSE;
    private Boolean filtroActivo = Boolean.TRUE;

    //filtros
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();

    private List<Usuarios> usuariosList = new ArrayList<>();
    private Usuarios usuariosSeleccionado;

    private List<String> ciclosString = new ArrayList<>();
    private List<String> ciclosStringSeleccionado = new ArrayList<>();

    private Map<String, String> capacitacionEstado = new HashMap<>();
    private List<String> capacitacionEstadoSeleccionado = new ArrayList<>();

    private Long codEvaluacion;
    private String responsable;
    private Date fechaPlanInicio;
    private Date fechaPlanFin;

    public UIPlanAccion() {
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            GestorUsuario gestorUsuario = new GestorUsuario();

            establecimientoList = new ArrayList<>();
            establecimientoList.addAll(s.getEstablecimientoList());

            ciclosString = new ArrayList<>();
            for (Ciclo c : s.getCiclos()) {
                ciclosString.add(c.getCodCiclo());
                ciclosStringSeleccionado.add(c.getCodCiclo());
            }

            capacitacionEstado = new HashMap<>();
            capacitacionEstado.put(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO_TEXTO, App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO);
            capacitacionEstado.put(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_CERRADO_TEXTO, App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_CERRADO);
            capacitacionEstado.put(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO_TEXTO, App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO);
            capacitacionEstadoSeleccionado.add(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO);

            usuariosList = new ArrayList<>();
            usuariosList.addAll(gestorUsuario.cargarListaUsuarios());

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
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
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
        } else {
            condicionesConsulta.add(Boolean.TRUE.toString());
        }

        if (usuariosSeleccionado != null && usuariosSeleccionado.getUsuariosPK() != null
                && usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() != null && !usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario().equalsIgnoreCase("")) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_DOCUMENTO_USUARIO.replace("?", UtilTexto.CARACTER_COMILLA + usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() + UtilTexto.CARACTER_COMILLA));
        }

        if (codEvaluacion != null && codEvaluacion >= 0) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_EVALUACION.replace("?", codEvaluacion.toString()));
        }

        if (ciclosStringSeleccionado != null && !ciclosStringSeleccionado.isEmpty()) {
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for (String s : ciclosStringSeleccionado) {
                cadena += "," + UtilTexto.CARACTER_COMILLA + s + UtilTexto.CARACTER_COMILLA;
            }
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_CICLO.replace("?", cadena));
        }

        if (responsable != null && responsable.length() > 0) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_RESPONSABLE.replace("?", UtilTexto.CARACTER_COMILLA + UtilTexto.CARACTER_PORCENTAJE + responsable + UtilTexto.CARACTER_PORCENTAJE + UtilTexto.CARACTER_COMILLA));
        }

        if (capacitacionEstadoSeleccionado != null && !capacitacionEstadoSeleccionado.isEmpty()) {
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for (String s : capacitacionEstadoSeleccionado) {
                cadena += "," + UtilTexto.CARACTER_COMILLA + s + UtilTexto.CARACTER_COMILLA;
            }
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_ESTADO.replace("?", cadena));
        }

        if (fechaPlanInicio != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_FECHA_REGISTRO_GTE.replace("?", UtilFecha.formatoFecha(fechaPlanInicio, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        if (fechaPlanFin != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_FECHA_REGISTRO_LTE.replace("?", UtilFecha.formatoFecha(fechaPlanFin, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        return condicionesConsulta;
    }

    public void consultarSeguimientoPlanAccion() {
        try {
            evaluacionPlanAccionDetalles = new ArrayList<>();
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            )
            );
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }

    public String cargarPlanAccionGeneral() {
        try {
            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();
            evaluacionPlanAccionDetalles = new ArrayList<>();
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();

            List<String> condicionesConsulta = new ArrayList<>();
            condicionesConsulta.add(App.CONDICION_WHERE);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_DOCUMENTO_USUARIO.replace("?", UtilTexto.CARACTER_COMILLA + usuarios.getUsuariosPK().getDocumentoUsuario() + UtilTexto.CARACTER_COMILLA));
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_ESTADO.replace("?", UtilTexto.CARACTER_COMILLA + App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO + UtilTexto.CARACTER_COMILLA));

            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            )
            );
            return ("/seguimiento/planes-accion.xhtml?faces-redirect=true");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }

    public void limpiar() {
        try {
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);
            modificarActivo = Boolean.FALSE;

            evaluacionPlanAccionDetalles = new ArrayList<>();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

        } catch (Exception e) {
        }

    }

    public void modificarPlanAccion() {
        EvaluacionPlanAccionDetalle epad = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");
        evaluacionPlanAccionDetalles.remove(epad);
        modificarActivo = Boolean.TRUE;
        UtilJSF.setBean("evaluacionPlanAccionDetalle", epad, UtilJSF.SESSION_SCOPE);
//        System.out.println("obj" + (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle"));
    }

    public void eliminarPlanAccion() {
        try {
            String documentoUsuario = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios().getUsuariosPK().getDocumentoUsuario();
            EvaluacionPlanAccionDetalle epad = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");
            epad.setDocumentoUsuario(documentoUsuario);
            epad.setEstado(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO);

            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            if (gestorEvaluacionPlanAccion.actualizarEstadoEvaluacionPlanAccionDetalle(epad) <= 0) {
                UtilMSG.addErrorMsg("Eliminar Plan Acción", "No se pudo eliminar el plan de acción, por favor intente de nuevo.");
            } else {
                evaluacionPlanAccionDetalles.remove(epad);
            }

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilLog.generarLog(this.getClass(), e);
                UtilMSG.addSupportMsg();
            }
        }

    }

    public void procesarPlanAccionModificar() {
        try {
            EvaluacionPlanAccionDetalle epd = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle");
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            epd = gestorEvaluacionPlanAccion.validarEvaluacionPlanAccionDetalle(epd);
            gestorEvaluacionPlanAccion.actualizarEvaluacionPlanAccionDetalle(epd);

            evaluacionPlanAccionDetalles = new ArrayList<>();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    public void procesarPlanAccion() {
        try {
            EvaluacionPlanAccionDetalle epd = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle");
            String documentoUsuario = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios().getUsuariosPK().getDocumentoUsuario();

            GestorGeneral gestorGeneral = new GestorGeneral();
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();

            epd = gestorEvaluacionPlanAccion.validarEvaluacionPlanAccionDetalle(epd);

            Long codPlan = gestorEvaluacionPlanAccion.consultarEvaluacionPlanAccion(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(), App.EVALUACION_PLAN_ACCION_ESTADO_ABIERTO);

            if (codPlan == null) {
                codPlan = gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_PLAN_ACCION_COD_PLAN_SEQ);
            }

            EvaluacionPlanAccion ep = new EvaluacionPlanAccion(
                    new EvaluacionPlanAccionPK(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                            sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                            codPlan),
                    App.EVALUACION_PLAN_ACCION_ESTADO_ABIERTO, documentoUsuario, documentoUsuario
            );
            epd.setEvaluacionPlanAccionDetallePK(new EvaluacionPlanAccionDetallePK(ep.getEvaluacionPlanAccionPK().getCodEvaluacion(), ep.getEvaluacionPlanAccionPK().getCodigoEstablecimiento(),
                    ep.getEvaluacionPlanAccionPK().getCodPlan()));
            epd.setEstado(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO);

            epd.setCodCiclo(sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo());
            epd.setCodSeccion(sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion());
            epd.setCodDetalle(sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle());
            epd.setCodItem(sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem());
            epd.setDocumentoUsuario(documentoUsuario);

            ep.setEvaluacionPlanAccionDetalle(epd);
            gestorEvaluacionPlanAccion.procesarPlanAccion(ep);
            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);
            UtilMSG.addWarningMsg("Plan Acción Guardado", "Se almaceno el plan de acción satisfactoriamente.");
            evaluacionPlanAccionDetalles = new ArrayList<>();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

        } catch (Exception ex) {
            if (UtilLog.causaControlada(ex)) {
                UtilMSG.addWarningMsg("Validación", ex.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), ex);
            }
        }

    }

    public void mostrarDialogoPlanAccion() {
        try {
            this.sdiSeleccionado = (SeccionDetalleItems) UtilJSF.getBean("varSeccionDetalleItems");
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            evaluacionPlanAccionDetalles = new ArrayList<>();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

            Dialogo dialogo = new Dialogo("dialogos/plan-accion.xhtml", "Plan Acción", "clip", Dialogo.WIDTH_80);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    /**
     * @return the evaluacionPlanAccionDetalles
     */
    public List<EvaluacionPlanAccionDetalle> getEvaluacionPlanAccionDetalles() {
        return evaluacionPlanAccionDetalles;
    }

    /**
     * @param evaluacionPlanAccionDetalles the evaluacionPlanAccionDetalles to
     * set
     */
    public void setEvaluacionPlanAccionDetalles(List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles) {
        this.evaluacionPlanAccionDetalles = evaluacionPlanAccionDetalles;
    }

    /**
     * @return the sdiSeleccionado
     */
    public SeccionDetalleItems getSdiSeleccionado() {
        return sdiSeleccionado;
    }

    /**
     * @param sdiSeleccionado the sdiSeleccionado to set
     */
    public void setSdiSeleccionado(SeccionDetalleItems sdiSeleccionado) {
        this.sdiSeleccionado = sdiSeleccionado;
    }

    /**
     * @return the modificarActivo
     */
    public Boolean getModificarActivo() {
        return modificarActivo;
    }

    /**
     * @param modificarActivo the modificarActivo to set
     */
    public void setModificarActivo(Boolean modificarActivo) {
        this.modificarActivo = modificarActivo;
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
     * @return the ciclosString
     */
    public List<String> getCiclosString() {
        return ciclosString;
    }

    /**
     * @param ciclosString the ciclosString to set
     */
    public void setCiclosString(List<String> ciclosString) {
        this.ciclosString = ciclosString;
    }

    /**
     * @return the ciclosStringSeleccionado
     */
    public List<String> getCiclosStringSeleccionado() {
        return ciclosStringSeleccionado;
    }

    /**
     * @param ciclosStringSeleccionado the ciclosStringSeleccionado to set
     */
    public void setCiclosStringSeleccionado(List<String> ciclosStringSeleccionado) {
        this.ciclosStringSeleccionado = ciclosStringSeleccionado;
    }

    /**
     * @return the capacitacionEstado
     */
    public Map<String, String> getCapacitacionEstado() {
        return capacitacionEstado;
    }

    /**
     * @param capacitacionEstado the capacitacionEstado to set
     */
    public void setCapacitacionEstado(Map<String, String> capacitacionEstado) {
        this.capacitacionEstado = capacitacionEstado;
    }

    /**
     * @return the capacitacionEstadoSeleccionado
     */
    public List<String> getCapacitacionEstadoSeleccionado() {
        return capacitacionEstadoSeleccionado;
    }

    /**
     * @param capacitacionEstadoSeleccionado the capacitacionEstadoSeleccionado
     * to set
     */
    public void setCapacitacionEstadoSeleccionado(List<String> capacitacionEstadoSeleccionado) {
        this.capacitacionEstadoSeleccionado = capacitacionEstadoSeleccionado;
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
     * @return the responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    /**
     * @return the fechaPlanInicio
     */
    public Date getFechaPlanInicio() {
        return fechaPlanInicio;
    }

    /**
     * @param fechaPlanInicio the fechaPlanInicio to set
     */
    public void setFechaPlanInicio(Date fechaPlanInicio) {
        this.fechaPlanInicio = fechaPlanInicio;
    }

    /**
     * @return the fechaPlanFin
     */
    public Date getFechaPlanFin() {
        return fechaPlanFin;
    }

    /**
     * @param fechaPlanFin the fechaPlanFin to set
     */
    public void setFechaPlanFin(Date fechaPlanFin) {
        this.fechaPlanFin = fechaPlanFin;
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

}

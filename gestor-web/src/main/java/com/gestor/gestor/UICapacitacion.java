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
import com.gestor.gestor.controlador.GestorEvaluacionCapacitacion;
import com.gestor.modelo.Sesion;
import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.Responsable;
import com.gestor.publico.Usuarios;
import com.gestor.publico.controlador.GestorCentroTrabajo;
import com.gestor.publico.controlador.GestorConfiguracion;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorResponsable;
import com.gestor.publico.controlador.GestorUsuario;
import com.gestor.seguimiento.PlanCapacitacion;
import com.gestor.seguimiento.controlador.GestorPlanCapacitacion;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.ViewExpiredException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author juliano
 */
@ManagedBean(name = "uiCapacitacion")
@SessionScoped
public class UICapacitacion {

    private SeccionDetalleItems sdiSeleccionado;
    private List<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles = new ArrayList<>();
    private EvaluacionCapacitacionDetalle evaluacionCapacitacionDetalle = new EvaluacionCapacitacionDetalle();

    private Boolean modificarActivo = Boolean.FALSE;
    private Boolean filtroActivo = Boolean.TRUE;

    //filtros
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();    

    private List<Usuarios> usuariosList = new ArrayList<>();
    private List<PlanCapacitacion> planCapacitaciones = new ArrayList<>();
    private List<PlanCapacitacion> planCapacitacionList = new ArrayList<>();
    private Usuarios usuariosSeleccionado;
    private Establecimiento establecimiento = new Establecimiento();
    

    private List<String> ciclosString = new ArrayList<>();
    private List<String> ciclosStringSeleccionado = new ArrayList<>();

    private Map<String, String> capacitacionEstado = new HashMap<>();
    private List<String> capacitacionEstadoSeleccionado = new ArrayList<>();

    private Long codEvaluacion;
    private String responsable;
    private List<Modalidad> modalidades = new ArrayList<>();
    private List<TecnicaCapacitacion> tecnicas = new ArrayList<>();
    private List<Facilitador> facilitadores = new ArrayList<>();
    private List<Dirigida> dirigidas = new ArrayList<>();
    private List<Recursos> recursos = new ArrayList<>();
    private List<CentroTrabajo> centrostrabajo = new ArrayList<>();
    private Date fechaCapacitacionInicio;
    private Date fechaCapacitacionFin;
    
    private List<Responsable> responsables = new ArrayList<>();
    private List<PlanTrabajoPrograma> programas = new ArrayList<>();
    private List<ClaseHallazgo> clasehallazgos = new ArrayList<>();
    private List<TipoAccion> tipoacciones = new ArrayList<>();
    private List<MotivoCorreccion> motivocorrecciones  = new ArrayList<>();
    
    private PlanCapacitacion planCapacitaiconSeleccionado;
    private Integer cons=0;

    public UICapacitacion() {
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
            capacitacionEstado.put(App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ELIMINADO_TEXTO, App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ELIMINADO);
            capacitacionEstado.put(App.EVALUACION_CAPACITACION_DETALLE_ESTADO_CERRADO_TEXTO, App.EVALUACION_CAPACITACION_DETALLE_ESTADO_CERRADO);
            capacitacionEstado.put(App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ABIERTO_TEXTO, App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ABIERTO);
            capacitacionEstadoSeleccionado.add(App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ABIERTO);

            usuariosList = new ArrayList<>();
            usuariosList.addAll(gestorUsuario.cargarListaUsuarios());

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void limpiarNota(){
        try {
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            EvaluacionCapacitacionDetalleNotasPK pk = new EvaluacionCapacitacionDetalleNotasPK(
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodEvaluacion(),
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodigoEstablecimiento(),
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodCapacitacion(),
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle()
            );
            evaluacionCapacitacionDetalle.setEvaluacionCapacitacionDetalleNotasList(gestorEvaluacionCapacitacion.cargarCapacitacionDetalleNotasList(pk));
            UtilJSF.setBean("evaluacionCapacitacionDetalleNotas", new EvaluacionCapacitacionDetalleNotas(), UtilJSF.SESSION_SCOPE);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void modificarNotaSeguimiento() {
        EvaluacionCapacitacionDetalleNotas ecdn = (EvaluacionCapacitacionDetalleNotas) UtilJSF.getBean("varCapacitacionNota");
        evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetalleNotasList().remove(ecdn);
        UtilJSF.setBean("evaluacionCapacitacionDetalleNotas", ecdn, UtilJSF.SESSION_SCOPE);
    }

    public void procesarCapacitacionDetalleNota() {
        try {
            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            GestorGeneral gestorGeneral = new GestorGeneral();
            EvaluacionCapacitacionDetalleNotas ecdn = (EvaluacionCapacitacionDetalleNotas) UtilJSF.getBean("evaluacionCapacitacionDetalleNotas");
            ecdn.setDocumentoUsuario(usuarios.getUsuariosPK().getDocumentoUsuario());
            ecdn.setEstado(evaluacionCapacitacionDetalle.getEstado());

            if (ecdn.getEvaluacionCapacitacionDetalleNotasPK() == null || ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodNota() == null
                    || ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodNota() == 0) {
                EvaluacionCapacitacionDetallePK pk = evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK();
                ecdn.setEvaluacionCapacitacionDetalleNotasPK(new EvaluacionCapacitacionDetalleNotasPK(
                        pk.getCodEvaluacion(),
                        pk.getCodigoEstablecimiento(),
                        pk.getCodCapacitacion(),
                        pk.getCodCapacitacionDetalle(),
                        gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_CAPACITACION_DETALLE_NOTAS_COD_NOTA_SEQ)
                ));
            }

            ecdn = gestorEvaluacionCapacitacion.validarEvaluacionCapacitacionDetalleNotas(ecdn);
            gestorEvaluacionCapacitacion.procesarCapacitacionDetalleNotas(ecdn);
            UtilMSG.addSuccessMsg("Seguimiento Guardado", "Se almaceno el seguimiento a la capacitación satisfactoriamente.");
            UtilJSF.setBean("evaluacionCapacitacionDetalleNotas", new EvaluacionCapacitacionDetalleNotas(), UtilJSF.SESSION_SCOPE);
            
            EvaluacionCapacitacionDetalleNotasPK pk = new EvaluacionCapacitacionDetalleNotasPK(
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodEvaluacion(),
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodigoEstablecimiento(),
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodCapacitacion(),
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle()
            );
            evaluacionCapacitacionDetalle.setEvaluacionCapacitacionDetalleNotasList(gestorEvaluacionCapacitacion.cargarCapacitacionDetalleNotasList(pk));
            

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    public void mostrarNotaSeguimiento() {
        try {
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            evaluacionCapacitacionDetalle = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("varCapacitacionDetalle");

            EvaluacionCapacitacionDetalleNotasPK pk = new EvaluacionCapacitacionDetalleNotasPK(
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodEvaluacion(),
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodigoEstablecimiento(),
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodCapacitacion(),
                    evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle()
            );
            evaluacionCapacitacionDetalle.setEvaluacionCapacitacionDetalleNotasList(gestorEvaluacionCapacitacion.cargarCapacitacionDetalleNotasList(pk));

            Dialogo dialogo = new Dialogo("dialogos/capacitacion-notas.xhtml", "Seguimiento Capacitación", "clip", Dialogo.WIDTH_80);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            UtilJSF.setBean("evaluacionCapacitacionDetalleNotas", new EvaluacionCapacitacionDetalleNotas(), UtilJSF.SESSION_SCOPE);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }
    
    public void cerrarEvaluacionCapacitacionDetalle() {
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            evaluacionCapacitacionDetalle = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("varCapacitacionDetalle");
            evaluacionCapacitacionDetalle.setDocumentoUsuario(s.getUsuarios().getUsuariosPK().getDocumentoUsuario());
            evaluacionCapacitacionDetalle.setEstado(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_ESTADO_CERRADO);
            
            gestorEvaluacionCapacitacion.cerrarEvaluacionCapacitacionDetalle(evaluacionCapacitacionDetalle);
            this.cargarCapacitacionGeneral();
            UtilJSF.update("formCapacitaciones");
            UtilMSG.addSuccessMsg("Capacitación Finalizada", "Se finalizo la capacitación # " + evaluacionCapacitacionDetalle.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle());
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    private List<String> filtrarOpcionesSeleccionadas() {
        List<String> condicionesConsulta = new ArrayList<>();
        condicionesConsulta.add(App.CONDICION_WHERE);

        if(establecimiento == null){   
            String cadena;         
            cadena = Integer.toString(establecimiento.getCodigoEstablecimiento());
            
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));            
        }
        
        if(establecimiento!=null){            
            String cadena="";
            cadena = Integer.toString(establecimiento.getCodigoEstablecimiento());
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena )); 
            establecimiento= new Establecimiento();
        }
        
        if(planCapacitaiconSeleccionado != null){
            UtilJSF.setBean("varPlanCapacitacion", planCapacitaiconSeleccionado, UtilJSF.SESSION_SCOPE);            
            String codpc= Integer.toString(planCapacitaiconSeleccionado.getCodPlancapacitacion());
            condicionesConsulta.add(App.CONDICION_AND);            
            condicionesConsulta.add(PlanCapacitacion.PLAN_CAPACITACION_CONDICION_COD_PlAN_CAPACITACION.replace("?", codpc ));
        }

        if (usuariosSeleccionado != null && usuariosSeleccionado.getUsuariosPK() != null
                && usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() != null && !usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario().equalsIgnoreCase("")) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_DOCUMENTO_USUARIO.replace("?", UtilTexto.CARACTER_COMILLA + usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() + UtilTexto.CARACTER_COMILLA));
        }

        if (codEvaluacion != null && codEvaluacion >= 0) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_COD_EVALUACION.replace("?", codEvaluacion.toString()));
        }

        if (ciclosStringSeleccionado != null && !ciclosStringSeleccionado.isEmpty()) {
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for (String s : ciclosStringSeleccionado) {
                cadena += "," + UtilTexto.CARACTER_COMILLA + s + UtilTexto.CARACTER_COMILLA;
            }
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_COD_CICLO.replace("?", cadena));
        }

        if (responsable != null && responsable.length() > 0) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_RESPONSABLE.replace("?", UtilTexto.CARACTER_COMILLA + UtilTexto.CARACTER_PORCENTAJE + responsable + UtilTexto.CARACTER_PORCENTAJE + UtilTexto.CARACTER_COMILLA));
        }

        if (capacitacionEstadoSeleccionado != null && !capacitacionEstadoSeleccionado.isEmpty()) {
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for (String s : capacitacionEstadoSeleccionado) {
                cadena += "," + UtilTexto.CARACTER_COMILLA + s + UtilTexto.CARACTER_COMILLA;
            }
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_ESTADO.replace("?", cadena));
        }

        if (fechaCapacitacionInicio != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_FECHA_REGISTRO_GTE.replace("?", UtilFecha.formatoFecha(fechaCapacitacionInicio, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        if (fechaCapacitacionFin != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_FECHA_FINALIZADO_LTE.replace("?", UtilFecha.formatoFecha(fechaCapacitacionFin, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        return condicionesConsulta;
    }
    
    public void cargarPlanesCapacitacionList(){

        try {            
            if(establecimiento!=null){
                GestorPlanCapacitacion gestorPlanCapacitacion= new GestorPlanCapacitacion();
                planCapacitacionList= new ArrayList<>();
                planCapacitacionList.addAll(gestorPlanCapacitacion.cargarPlancapacitacionAbiertosList(establecimiento.getCodigoEstablecimiento()));
            }
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void consultarSeguimientoCapacitaciones() {
        try {
            evaluacionCapacitacionDetalles = new ArrayList<>();
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();
            
            
            if(planCapacitaiconSeleccionado!=null){
                evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetallepc(
                UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)                
                )); 
            }else{
                evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetalle(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
                )
                );
            }
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }

    public String cargarCapacitacionGeneral() {
        try {
            UtilJSF.setBean("dialogo", new Dialogo(), UtilJSF.SESSION_SCOPE);
            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();
            evaluacionCapacitacionDetalles = new ArrayList<>();
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();

            List<String> condicionesConsulta = new ArrayList<>();
            condicionesConsulta.add(App.CONDICION_WHERE);
            //condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_DOCUMENTO_USUARIO.replace("?", UtilTexto.CARACTER_COMILLA + usuarios.getUsuariosPK().getDocumentoUsuario() + UtilTexto.CARACTER_COMILLA));
            //condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionCapacitacionDetalle.EVALUACION_CAPACITACION_DETALLE_CONDICION_ESTADO.replace("?", UtilTexto.CARACTER_COMILLA + App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO + UtilTexto.CARACTER_COMILLA));

            evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetalle(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            )
            );
            return ("/seguimiento/capacitaciones.xhtml?faces-redirect=true");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }

    public void limpiar() {
        try {
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            modificarActivo = Boolean.FALSE;
            UtilJSF.setBean("evaluacionCapacitacionDetalle", new EvaluacionCapacitacionDetalle(), UtilJSF.SESSION_SCOPE);

            evaluacionCapacitacionDetalles = new ArrayList<>();
            evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetalle(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }

    public void modificarCapacitacion() {
        EvaluacionCapacitacionDetalle epad = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("varCapacitacionDetalle");
        evaluacionCapacitacionDetalles.remove(epad);
        modificarActivo = Boolean.TRUE;
        UtilJSF.setBean("evaluacionCapacitacionDetalle", epad, UtilJSF.SESSION_SCOPE);
    }

    public void eliminarCapacitacion() {
        try {
            String documentoUsuario = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios().getUsuariosPK().getDocumentoUsuario();
            EvaluacionCapacitacionDetalle ecd = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("varCapacitacionDetalle");
            ecd.setDocumentoUsuario(documentoUsuario);
            ecd.setEstado(App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ELIMINADO);

            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            if (gestorEvaluacionCapacitacion.actualizarEstadoEvaluacionCapacitacionDetalle(ecd) <= 0) {
                UtilMSG.addErrorMsg("Eliminar Capacitación", "No se pudo eliminar la capacitación, por favor intente de nuevo.");
            } else {
                evaluacionCapacitacionDetalles.remove(ecd);
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

    public void procesarCapacitacionModificar() {
        try {
            EvaluacionCapacitacionDetalle ecd = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("evaluacionCapacitacionDetalle");
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            ecd = gestorEvaluacionCapacitacion.validarEvaluacionCapacitacionDetalle(ecd);
            gestorEvaluacionCapacitacion.actualizarEvaluacionCapacitacionDetalle(ecd);
            this.enviarCorreo();
            this.modificarActivo = Boolean.FALSE;

            evaluacionCapacitacionDetalles = new ArrayList<>();
            evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetalle(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

            UtilJSF.setBean("evaluacionCapacitacionDetalle", new EvaluacionCapacitacionDetalle(), UtilJSF.SESSION_SCOPE);

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    public void procesarCapacitacion() {
        try {
            EvaluacionCapacitacionDetalle ecd = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("evaluacionCapacitacionDetalle");
            String documentoUsuario = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios().getUsuariosPK().getDocumentoUsuario();

            GestorGeneral gestorGeneral = new GestorGeneral();
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            ecd = gestorEvaluacionCapacitacion.validarEvaluacionCapacitacionDetalle(ecd);

            Long codCapacitacion = gestorEvaluacionCapacitacion.consultarEvaluacionCapacitacion(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(), App.EVALUACION_CAPACITACION_ESTADO_ABIERTO);

            if (codCapacitacion == null) {
                codCapacitacion = gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_CAPACITACION_COD_CAPACITACION_SEQ);
            }

            EvaluacionCapacitacion ec = new EvaluacionCapacitacion(new EvaluacionCapacitacionPK(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(), codCapacitacion
            ), documentoUsuario, App.EVALUACION_CAPACITACION_ESTADO_ABIERTO);

            ecd.setEvaluacionCapacitacionDetallePK(new EvaluacionCapacitacionDetallePK(ec.getEvaluacionCapacitacionPK().getCodEvaluacion(),
                    ec.getEvaluacionCapacitacionPK().getCodigoEstablecimiento(), ec.getEvaluacionCapacitacionPK().getCodCapacitacion()));
            ecd.setEstado(App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ABIERTO);

            ecd.setCodCiclo(sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo());
            ecd.setCodSeccion(sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion());
            ecd.setCodDetalle(sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle());
            ecd.setCodItem(sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem());
            ecd.setDocumentoUsuario(documentoUsuario);
            ecd.getEvaluacionCapacitacionDetallePK().setCodCapacitacionDetalle(gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_CAPACITACION_DETALLE_COD_CAPACITACION_DETALLE_SEQ));

            //EvaluacionCapacitacionDetalleNotasPK(Long codEvaluacion, int codigoEstablecimiento, Long codCapacitacion, int codCapacitacionDetalle, int codNota
            //EvaluacionCapacitacionDetalleNotasPK evaluacionCapacitacionDetalleNotasPK, 
            //String documentoUsuario, String estado, String nombre, String descripcion
            EvaluacionCapacitacionDetalleNotas ecdn = new EvaluacionCapacitacionDetalleNotas(
                    new EvaluacionCapacitacionDetalleNotasPK(
                            ec.getEvaluacionCapacitacionPK().getCodEvaluacion(),
                            ec.getEvaluacionCapacitacionPK().getCodigoEstablecimiento(),
                            codCapacitacion, ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle()
                    ),
                    documentoUsuario, ecd.getEstado(), "REGISTRO INICIAL", "Inicia registro de capacitación, responsable: " + UtilTexto.capitalizarCadena(responsable), usuariosSeleccionado);
            ecd.setEvaluacionCapacitacionDetalleNotas(ecdn);

            ec.setEvaluacionCapacitacionDetalle(ecd);

            gestorEvaluacionCapacitacion.procesarCapacitacion(ec);
            this.enviarCorreo();
            UtilJSF.setBean("evaluacionCapacitacionDetalle", new EvaluacionCapacitacionDetalle(), UtilJSF.SESSION_SCOPE);
            UtilMSG.addSuccessMsg("Capacitación Guardada", "Se almaceno la capacitación satisfactoriamente.");
            evaluacionCapacitacionDetalles = new ArrayList<>();
            evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetalle(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addErrorMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addErrorMsg(e.getCause().getMessage(), e.getMessage());
            }
        }
    }

    public void mostrarDialogoCapacitacion() {
        try {
            Sesion sesion = (Sesion) UtilJSF.getBean("sesion");
            this.sdiSeleccionado = (SeccionDetalleItems) UtilJSF.getBean("varSeccionDetalleItems");
            
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            
            GestorResponsable gestorResponsable = new GestorResponsable();
            
            if(modalidades.isEmpty()){
            modalidades.addAll((Collection<? extends Modalidad>) gestorEvaluacionCapacitacion.cargarListaModalidad());
            tecnicas.addAll((Collection<? extends TecnicaCapacitacion>) gestorEvaluacionCapacitacion.cargarListaTecnicas());
            facilitadores.addAll((Collection<? extends Facilitador>) gestorEvaluacionCapacitacion.cargarListaFacilitadores());
            dirigidas.addAll((Collection<? extends Dirigida>) gestorEvaluacionCapacitacion.cargarListaDirigidas());
            recursos.addAll((Collection<? extends Recursos>) gestorEvaluacionCapacitacion.cargarListaRecursos());
            
            }
            
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorCentroTrabajo gestorCentrotrabajo = new GestorCentroTrabajo();
            centrostrabajo= new ArrayList<>();
            centrostrabajo.addAll((Collection<? extends CentroTrabajo>) gestorCentrotrabajo.cargarListaCentrosTrabajoactivos(e.getEstablecimiento().getCodigoEstablecimiento()));
            
            GestorPlanCapacitacion gestorPlanCapacitacion= new GestorPlanCapacitacion();
            planCapacitaciones= new ArrayList<>();
            planCapacitaciones.addAll((Collection<? extends PlanCapacitacion>) gestorPlanCapacitacion.cargarPlancapacitacionList(e.getEstablecimiento().getCodigoEstablecimiento()));
            
            evaluacionCapacitacionDetalles = new ArrayList<>();
            evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetalle(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));
            
            GestorEstablecimiento gestorEstablecimiento= new GestorEstablecimiento();
            Integer sisgapp = gestorEstablecimiento.buscarSisgapp();

            responsables = new ArrayList<>();
            List<String> condicionesConsulta = new ArrayList<>();
            condicionesConsulta.add(App.CONDICION_WHERE);
            condicionesConsulta.add(Responsable.RESPONSABLE_CONDICION_CODIGO_ESTABLECIMIENTO.replace("?", String.valueOf(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento())+","+sisgapp));
            responsables.addAll(gestorResponsable.cargarListaResponsable(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            ));
            
            List<Responsable> responsablesSisgapp = new ArrayList<>();
            for (Responsable rs : sesion.getResponsables()) {
                for (Responsable r : responsables) {
                    if (!rs.equals(r)) {
                        responsablesSisgapp.add(rs);
                    }
                }
            }
            if(!responsablesSisgapp.isEmpty()){
                responsables.addAll(responsablesSisgapp);
            }

            
            Dialogo dialogo = new Dialogo("dialogos/capacitacion.xhtml", "Capactiación", "clip", Dialogo.WIDTH_100);
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
    
    public void enviarCorreo(){
        final String username = "gestorapp@sisgappcolombia.com";
        final String password = "4prF$nsL3";
        final String smtp = "sisgappcolombia.com";
        final String port = "587";
        final String ssltrust = "true";
        final String smtpauth = "true";

        Properties props = new Properties();
        props.put("mail.smtp.auth", smtpauth);
        props.put("mail.smtp.starttls.enable", ssltrust);
        props.put("mail.smtp.ssl.trust", smtp);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
              }
          });

        try {
            EvaluacionCapacitacionDetalle ecd = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("evaluacionCapacitacionDetalle");
            GestorConfiguracion gestorConfiguracion= new GestorConfiguracion();
            String correoadmin = gestorConfiguracion.cargarConfiguracioncorreo();
            // Define message            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            
            //Extrae el codigo del plan de acción para llevarlo al asunto del mensaje
            //Long plandetalle  = epd.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle();            
            // plandetalleS = Long.toString(plandetalle);
            
            message.setSubject("Se ha generado una nueva CAPACITACION "+ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle());            
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(ecd.getResponsable().getCorreo()));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(correoadmin));
            
            
            Date fechareg = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechar = sdf.format(fechareg);
            String fechap = sdf.format(ecd.getFechaPlazo());
            
            String msg=("<html>\n" +
            "<head>\n" +
            "<style>\n" +
            "table, th, td {\n" +
            "    border: 1px solid black;\n" +
            "}\n" +
            "th, td {\n" +
            "    padding: 5px;\n" +
            "    text-align: left;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<table style=\"width:80%\">\n" +
            "  <tr>\n" +
            "    <th>Nombre Capacitacion</th>\n" +
            "    <th>Descripcion</th>\n" +
            "    <th>Fecha Creacion</th>\n" +
            "    <th>Responsable</th>\n" +
            "    <th>Fecha de Vencimiento</th>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>"+ecd.getNombre()+"</td>\n" +            
            "    <td>"+ecd.getDescripcion()+"</td>\n" +            
            "    <td>"+fechar+"</td>\n" +            
            "    <td>"+ecd.getResponsable().getNombresApellidos()+"</td>\n" +
            "    <td>"+fechap+"</td>\n" +
            "  </tr>\n" +            
            "</table>\n" +
            "\n" +
            "</body>\n" +
            "</html>");
            message.setText(msg,"utf-8", "html");
            
            // Envia el mensaje
            Transport transport = session.getTransport("smtp");


            transport.connect(smtp, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch(Exception e){
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {
                UtilMSG.addSupportMsg();                
                UtilMSG.addWarningMsg("No se puedo enviar el correo "+e.getCause()+e.getMessage());
                UtilLog.generarLog(this.getClass(), e);
            }            
        }
    }
    
    public String getStyleDias() {
        evaluacionCapacitacionDetalle = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("varCapacitacionDetalle");
        String style = "";          
        if(evaluacionCapacitacionDetalle.getDiasRestantes() != null && evaluacionCapacitacionDetalle.getDiasRestantes() <= 0 ){
            style += "color: #FF0000";
        }
        return style;
    }

    public List<PlanCapacitacion> getPlanCapacitacionList() {
        return planCapacitacionList;
    }

    public void setPlanCapacitacionList(List<PlanCapacitacion> planCapacitacionList) {
        this.planCapacitacionList = planCapacitacionList;
    }

    public Integer getCons() {
        return cons;
    }

    public void setCons(Integer cons) {
        this.cons = cons;
    }

    public PlanCapacitacion getPlanCapacitaiconSeleccionado() {
        return planCapacitaiconSeleccionado;
    }

    public void setPlanCapacitaiconSeleccionado(PlanCapacitacion planCapacitaiconSeleccionado) {
        this.planCapacitaiconSeleccionado = planCapacitaiconSeleccionado;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public List<PlanCapacitacion> getPlanCapacitaciones() {
        return planCapacitaciones;
    }

    public void setPlanCapacitaciones(List<PlanCapacitacion> planCapacitaciones) {
        this.planCapacitaciones = planCapacitaciones;
    }

    public List<CentroTrabajo> getCentrostrabajo() {
        return centrostrabajo;
    }

    public void setCentrostrabajo(List<CentroTrabajo> centrostrabajo) {
        this.centrostrabajo = centrostrabajo;
    }

    public List<Modalidad> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<Modalidad> modalidades) {
        this.modalidades = modalidades;
    }

    public List<TecnicaCapacitacion> getTecnicas() {
        return tecnicas;
    }

    public void setTecnicas(List<TecnicaCapacitacion> tecnicas) {
        this.tecnicas = tecnicas;
    }

    public List<Facilitador> getFacilitadores() {
        return facilitadores;
    }

    public void setFacilitadores(List<Facilitador> facilitadores) {
        this.facilitadores = facilitadores;
    }

    public List<Dirigida> getDirigidas() {
        return dirigidas;
    }

    public void setDirigidas(List<Dirigida> dirigidas) {
        this.dirigidas = dirigidas;
    }

    public List<Recursos> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recursos> recursos) {
        this.recursos = recursos;
    }

    public List<PlanTrabajoPrograma> getProgramas() {
        return programas;
    }

    public void setProgramas(List<PlanTrabajoPrograma> programas) {
        this.programas = programas;
    }
    

    public List<ClaseHallazgo> getClasehallazgos() {
        return clasehallazgos;
    }

    public void setClasehallazgos(List<ClaseHallazgo> clasehallazgos) {
        this.clasehallazgos = clasehallazgos;
    }

    public List<TipoAccion> getTipoacciones() {
        return tipoacciones;
    }

    public void setTipoacciones(List<TipoAccion> tipoacciones) {
        this.tipoacciones = tipoacciones;
    }

    public List<MotivoCorreccion> getMotivocorrecciones() {
        return motivocorrecciones;
    }

    public void setMotivocorrecciones(List<MotivoCorreccion> motivocorrecciones) {
        this.motivocorrecciones = motivocorrecciones;
    }

    /**
     * @return the evaluacionCapacitacionDetalles
     */
    public List<EvaluacionCapacitacionDetalle> getEvaluacionCapacitacionDetalles() {
        return evaluacionCapacitacionDetalles;
    }

    /**
     * @param evaluacionCapacitacionDetalles the evaluacionCapacitacionDetalles
     * to set
     */
    public void setEvaluacionCapacitacionDetalles(List<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles) {
        this.evaluacionCapacitacionDetalles = evaluacionCapacitacionDetalles;
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
     * @return the fechaCapacitacionInicio
     */
    public Date getFechaCapacitacionInicio() {
        return fechaCapacitacionInicio;
    }

    /**
     * @param fechaCapacitacionInicio the fechaCapacitacionInicio to set
     */
    public void setFechaCapacitacionInicio(Date fechaCapacitacionInicio) {
        this.fechaCapacitacionInicio = fechaCapacitacionInicio;
    }

    /**
     * @return the fechaCapacitacionFin
     */
    public Date getFechaCapacitacionFin() {
        return fechaCapacitacionFin;
    }

    /**
     * @param fechaCapacitacionFin the fechaCapacitacionFin to set
     */
    public void setFechaCapacitacionFin(Date fechaCapacitacionFin) {
        this.fechaCapacitacionFin = fechaCapacitacionFin;
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
     * @return the evaluacionCapacitacionDetalle
     */
    public EvaluacionCapacitacionDetalle getEvaluacionCapacitacionDetalle() {
        return evaluacionCapacitacionDetalle;
    }

    /**
     * @param evaluacionCapacitacionDetalle the evaluacionCapacitacionDetalle to
     * set
     */
    public void setEvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetalle evaluacionCapacitacionDetalle) {
        this.evaluacionCapacitacionDetalle = evaluacionCapacitacionDetalle;
    }

    /**
     * @return the responsables
     */
    public List<Responsable> getResponsables() {
        return responsables;
    }

    /**
     * @param responsables the responsables to set
     */
    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }

}

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
import com.gestor.publico.Responsable;
import com.gestor.publico.Usuarios;
import com.gestor.publico.controlador.GestorConfiguracion;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorResponsable;
import com.gestor.gestor.controlador.GestorClaseHallazgo;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.gestor.controlador.GestorFuenteHallazgo;
import com.gestor.gestor.controlador.GestorMotivoCorreccion;
import com.gestor.gestor.controlador.GestorTipoAccion;
import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.TipoPlanAccion;
import com.gestor.publico.controlador.GestorCentroTrabajo;
import com.gestor.publico.controlador.GestorTipoPlanAccion;
import com.gestor.publico.controlador.GestorUsuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
@ManagedBean(name = "uiPlanAccion")
@SessionScoped

public class UIPlanAccion {

    private SeccionDetalleItems sdiSeleccionado;
    private PlanTrabajoObjetivo objetivoSeleccionado;
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles = new ArrayList<>();
    private EvaluacionPlanAccionDetalle evaluacionPlanAccionDetalle = new EvaluacionPlanAccionDetalle();
    private EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK = new EvaluacionPlanAccionDetallePK();
    private Boolean modificarActivo = Boolean.FALSE;
    private Boolean filtroActivo = Boolean.TRUE;
    private Establecimiento establecimiento = new Establecimiento();
    private List<String> selectedOptions;
    //filtros
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<TipoPlanAccion> tipoPlanAccionList=new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();    

    private List<Usuarios> usuariosList = new ArrayList<>();
    private Usuarios usuariosSeleccionado;

    private List<String> ciclosString = new ArrayList<>();
    private List<Integer> tiposPlanAccion=new ArrayList<>();
    private List<String> ciclosStringSeleccionado = new ArrayList<>();
    private List<TipoPlanAccion> tiposPlanAccionIntegerSeleccionado = new ArrayList<>();
    private TipoPlanAccion tipoPlanAccion=new TipoPlanAccion();
    
    

    private Map<String, String> capacitacionEstado = new HashMap<>();
    private List<String> capacitacionEstadoSeleccionado = new ArrayList<>();    
    
    private Long codEvaluacion;
    private String responsable;
    private Date fechaPlanInicio;
    private Date fechaPlanFin;
    int dias=0;
    
    private List<Responsable> responsables = new ArrayList<>();
    private List<PlanTrabajoPrograma> programas = new ArrayList<>();
    private List<PlanTrabajoObjetivo> objetivos = new ArrayList<>();
    private List<ClaseHallazgo> clasehallazgos = new ArrayList<>();
    private List<FuenteHallazgo> fuentehallazgos = new ArrayList<>();
    private List<TipoAccion> tipoacciones = new ArrayList<>();    
    private List<MotivoCorreccion> motivocorrecciones  = new ArrayList<>();
    private List<CentroTrabajo> centrostrabajo = new ArrayList<>();    

    public UIPlanAccion() {
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            GestorUsuario gestorUsuario = new GestorUsuario();
            
            establecimiento=new Establecimiento();
            establecimientoList = new ArrayList<>();
            establecimientoList.addAll(s.getEstablecimientoList());
            
            GestorTipoPlanAccion gestorTipoPlanAccion=new GestorTipoPlanAccion();
            tipoPlanAccionList=new ArrayList<>();
            tipoPlanAccionList.addAll(gestorTipoPlanAccion.cargarListaTipoaccion());
            
                        
            tiposPlanAccion=new ArrayList<>();
            for(TipoPlanAccion tp:tipoPlanAccionList){
                tiposPlanAccion.add(tp.getCodTipoPlanAccion());
                tiposPlanAccionIntegerSeleccionado.add(tp);
            }
            
            ciclosString = new ArrayList<>();
            for (Ciclo c : s.getCiclos()) {
                ciclosString.add(c.getCodCiclo());
                ciclosStringSeleccionado.add(c.getCodCiclo());
            }

            capacitacionEstado = new HashMap<>();
            capacitacionEstado.put(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO_TEXTO, App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO);
            capacitacionEstado.put(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_CERRADO_TEXTO, App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_CERRADO);
            capacitacionEstado.put(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO_TEXTO, App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO);            
            
            usuariosList = new ArrayList<>();
            usuariosList.addAll(gestorUsuario.cargarListaUsuarios());
            
            

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void limpiarNota() {
        try {
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();

            EvaluacionPlanAccionDetalleNotasPK pk = new EvaluacionPlanAccionDetalleNotasPK(
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodEvaluacion(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodPlan(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle()                   
            );
            evaluacionPlanAccionDetalle.setEvaluacionPlanAccionDetalleNotasList(gestorEvaluacionPlanAccion.cargarEvaluacionPlanAccionDetalleNotasList(pk));

            UtilJSF.setBean("evaluacionPlanAccionDetalleNotas", new EvaluacionPlanAccionDetalleNotas(), UtilJSF.SESSION_SCOPE);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void modificarNotaSeguimiento() {
        EvaluacionPlanAccionDetalleNotas epadn = (EvaluacionPlanAccionDetalleNotas) UtilJSF.getBean("varPlanAccionNota");
        evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetalleNotasList().remove(epadn);
        UtilJSF.setBean("evaluacionPlanAccionDetalleNotas", epadn, UtilJSF.SESSION_SCOPE);
    }

    public void procesarPlanAccionDetalleNota() {
        try {
            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            GestorGeneral gestorGeneral = new GestorGeneral();
            EvaluacionPlanAccionDetalleNotas epadn = (EvaluacionPlanAccionDetalleNotas) UtilJSF.getBean("evaluacionPlanAccionDetalleNotas");
            epadn.setDocumentoUsuario(usuarios.getUsuariosPK().getDocumentoUsuario());
            epadn.setEstado(evaluacionPlanAccionDetalle.getEstado());

            if (epadn.getEvaluacionPlanAccionDetalleNotasPK() == null || epadn.getEvaluacionPlanAccionDetalleNotasPK().getCodNota() == null
                    || epadn.getEvaluacionPlanAccionDetalleNotasPK().getCodNota() == 0) {
                EvaluacionPlanAccionDetallePK pk = evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK();
                epadn.setEvaluacionPlanAccionDetalleNotasPK(new EvaluacionPlanAccionDetalleNotasPK(
                        pk.getCodEvaluacion(),
                        pk.getCodigoEstablecimiento(),
                        pk.getCodPlan(),
                        pk.getCodPlanDetalle()                                                
                ));
            }

            epadn = gestorEvaluacionPlanAccion.validarEvaluacionPlanAccionDetalleNotas(epadn);
            gestorEvaluacionPlanAccion.procesarPlanAccionDetalleNotas(epadn);
            UtilMSG.addSuccessMsg("Seguimiento Guardado", "Se almaceno el seguimiento al plan de acción satisfactoriamente.");
            UtilJSF.setBean("evaluacionPlanAccionDetalleNotas", new EvaluacionCapacitacionDetalleNotas(), UtilJSF.SESSION_SCOPE);

            EvaluacionPlanAccionDetalleNotasPK pk = new EvaluacionPlanAccionDetalleNotasPK(
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodEvaluacion(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodPlan(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle()                    
                    
            );
            evaluacionPlanAccionDetalle.setEvaluacionPlanAccionDetalleNotasList(new ArrayList<EvaluacionPlanAccionDetalleNotas>());
            evaluacionPlanAccionDetalle.setEvaluacionPlanAccionDetalleNotasList(gestorEvaluacionPlanAccion.cargarEvaluacionPlanAccionDetalleNotasList(pk));

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
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            evaluacionPlanAccionDetalle = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");

            EvaluacionPlanAccionDetalleNotasPK pk = new EvaluacionPlanAccionDetalleNotasPK(
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodEvaluacion(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodPlan(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle()
                    
            );
            evaluacionPlanAccionDetalle.setEvaluacionPlanAccionDetalleNotasList(gestorEvaluacionPlanAccion.cargarEvaluacionPlanAccionDetalleNotasList(pk));

            Dialogo dialogo = new Dialogo("dialogos/plan-accion-notas.xhtml", "Seguimiento Plan Acción", "clip", Dialogo.WIDTH_80);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            UtilJSF.setBean("evaluacionPlanAccionDetalleNotas", new EvaluacionPlanAccionDetalleNotas(), UtilJSF.SESSION_SCOPE);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }
    
    
    public void dialogoCerrarPlanAccion() {
        try {                        
            Dialogo dialogo = new Dialogo("dialogos/cerrar-plan-accion.xhtml", "Finalizar Plan Accion", "clip", Dialogo.WIDTH_AUTO);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();"); 
            evaluacionPlanAccionDetalle= (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public void cerrarPlanAccionDetalle() {
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();           
            
            EvaluacionPlanAccionDetallePK pk=new EvaluacionPlanAccionDetallePK(
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodEvaluacion(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodPlan(),
                    evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle()
            );
            
            evaluacionPlanAccionDetalle = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");
            evaluacionPlanAccionDetalle.setDocumentoUsuario(s.getUsuarios().getUsuariosPK().getDocumentoUsuario());
            evaluacionPlanAccionDetalle.setEstado(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_CERRADO);             
                       
            evaluacionPlanAccionDetalle.setEvaluacionPlanAccionDetallePK(pk);
            gestorEvaluacionPlanAccion.cerrarPlanAccionDetalle(evaluacionPlanAccionDetalle);            
            this.cargarPlanAccionActual();
            UtilJSF.update("formPlanesAccion");
            UtilMSG.addSuccessMsg("Plan Acción Finalizado", "Se finalizo el Plan Acción # " + evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle());
            
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    private List<String> filtrarOpcionesSeleccionadas() throws Exception {
        List<String> condicionesConsulta = new ArrayList<>();
        condicionesConsulta.add(App.CONDICION_WHERE);

        /*if (establecimientoListSeleccionado != null && !establecimientoListSeleccionado.isEmpty()) {
            String cadena = "0";
            for (Establecimiento e : establecimientoListSeleccionado) {
                cadena += "," + e.getCodigoEstablecimiento();
            }
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
        } else {
            condicionesConsulta.add(Boolean.TRUE.toString());
        }*/
        
        
        
        if(establecimiento == null){   
            String cadena;         
            cadena = Integer.toString(establecimiento.getCodigoEstablecimiento());
            
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
            
        }
        
        if(establecimiento!=null){            
            String cadena="";
            cadena = Integer.toString(establecimiento.getCodigoEstablecimiento());
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena ));             
        }
        
        if(tiposPlanAccionIntegerSeleccionado!=null && !tiposPlanAccionIntegerSeleccionado.isEmpty()){            
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for(int i=0; i<tiposPlanAccionIntegerSeleccionado.size();i++){                
                cadena += "," + UtilTexto.CARACTER_COMILLA + Integer.toString(tiposPlanAccionIntegerSeleccionado.get(i).getCodTipoPlanAccion()) + UtilTexto.CARACTER_COMILLA;                                        
            }
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_TIPO_PLAN_ACCION.replace("?", cadena));
        }

        if (usuariosSeleccionado != null && usuariosSeleccionado.getUsuariosPK() != null && usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() != null && !usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario().equalsIgnoreCase("")) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_DOCUMENTO_USUARIO.replace("?", UtilTexto.CARACTER_COMILLA + usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() + UtilTexto.CARACTER_COMILLA));
        }

        if (codEvaluacion != null && codEvaluacion >= 0) {
            this.getAvancePlanAccion();
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_EVALUACION.replace("?", codEvaluacion.toString()));                        
            
        }
        
        if (ciclosStringSeleccionado != null && !ciclosStringSeleccionado.isEmpty()) {                        
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for (String s : ciclosStringSeleccionado) {
                cadena += "," + UtilTexto.CARACTER_COMILLA + s + UtilTexto.CARACTER_COMILLA;                  
            }
            cadena+=","+UtilTexto.CARACTER_COMILLA+"X"+UtilTexto.CARACTER_COMILLA;
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
            ));
            
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public String cargarPlanAccionGeneral() {
        try {            
            UtilJSF.setBean("dialogo", new Dialogo(), UtilJSF.SESSION_SCOPE);
            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();
            evaluacionPlanAccionDetalles = new ArrayList<>();
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            
                List<String> condicionesConsulta = new ArrayList<>();
                condicionesConsulta.add(App.CONDICION_WHERE);
                condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", "3" ));
                establecimiento.setCodigoEstablecimiento(3);
                evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
                )
                );
                
                if(!evaluacionPlanAccionDetalles.isEmpty()){
                UtilJSF.setBean("varPlanAccionDetalle", evaluacionPlanAccionDetalles.get(0), UtilJSF.SESSION_SCOPE);                
                }
                this.getAvancePlanAccion();
            UtilJSF.setBean("varPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);                        
            return ("/seguimiento/planes-accion.xhtml?faces-redirect=true");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }
    
    public String cargarPlanAccionActual() {
        try {            
            UtilJSF.setBean("dialogo", new Dialogo(), UtilJSF.SESSION_SCOPE);
            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();            
            evaluacionPlanAccionDetalles = new ArrayList<>();
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
                
                List<String> condicionesConsulta = new ArrayList<>();
                String codE=Integer.toString(evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento());
                establecimiento=new Establecimiento();                
                condicionesConsulta.add(App.CONDICION_WHERE);
                condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", codE ));
                establecimiento.setCodigoEstablecimiento(evaluacionPlanAccionDetalle.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento());
                evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
                )
                );
                if(!evaluacionPlanAccionDetalles.isEmpty()){
                UtilJSF.setBean("varPlanAccionDetalle", evaluacionPlanAccionDetalles.get(0), UtilJSF.SESSION_SCOPE);
                }
                this.getAvancePlanAccion();
            
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
            this.enviarCorreo();
            this.modificarActivo = Boolean.FALSE;

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
            epd.getEvaluacionPlanAccionDetallePK().setCodPlanDetalle(codPlan);
            epd.getEvaluacionPlanAccionDetallePK().setCodPlanDetalle(gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_PLAN_ACCION_DETALLE_COD_PLAN_DETALLE_SEQ));            

            EvaluacionPlanAccionDetalleNotas epadn = new EvaluacionPlanAccionDetalleNotas(
                    new EvaluacionPlanAccionDetalleNotasPK(ep.getEvaluacionPlanAccionPK().getCodEvaluacion(), ep.getEvaluacionPlanAccionPK().getCodigoEstablecimiento(),
                            codPlan, epd.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle()),
                    documentoUsuario, epd.getEstado(), "REGISTRO INICIAL", "Inicia registro de plan acción, responsable: " + UtilTexto.capitalizarCadena(responsable), usuariosSeleccionado);
            epd.setEvaluacionPlanAccionDetalleNotas(epadn);
            
            
            ep.setEvaluacionPlanAccionDetalle(epd);
            gestorEvaluacionPlanAccion.procesarPlanAccion(ep);
            this.enviarCorreo();
            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);            
            UtilMSG.addSuccessMsg("Plan Acción Guardado", "Se almaceno el plan de acción satisfactoriamente.");
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
        
    public Integer getAvancePlanAccion() {
        try {                          
            long codEv=0;
            if(!evaluacionPlanAccionDetalles.isEmpty()){
            codEv=evaluacionPlanAccionDetalles.get(0).getEvaluacion().getEvaluacionPK().getCodEvaluacion();
            }
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            return gestorEvaluacion.avancePlanaccion(codEv);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }        
        return null;
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
            EvaluacionPlanAccionDetalle epd = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle");
            GestorConfiguracion gestorConfiguracion= new GestorConfiguracion();
            String correoadmin = gestorConfiguracion.cargarConfiguracioncorreo();
            
            
            // Define message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            
            //Extrae el codigo del plan de acción para llevarlo al asunto del mensaje
            //Long plandetalle  = epd.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle();            
            // plandetalleS = Long.toString(plandetalle);
            
            message.setSubject("Se ha generado un nuevo PLAN DE ACCION "+epd.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle());            
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(epd.getResponsable().getCorreo()));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(correoadmin));            
            
            
            Date fechareg = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechar = sdf.format(fechareg);
            String fechap = sdf.format(epd.getFechaPlazo());
            
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
            "    <th>Nombre Plan Accion</th>\n" +
            "    <th>Descripcion</th>\n" +
            "    <th>Fecha Creacion</th>\n" +
            "    <th>Responsable</th>\n" +
            "    <th>Fecha de Vencimiento</th>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td>"+epd.getNombre()+"</td>\n" +            
            "    <td>"+epd.getDescripcion()+"</td>\n" +            
            "    <td>"+fechar+"</td>\n" +            
            "    <td>"+epd.getResponsable().getNombresApellidos()+"</td>\n" +
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
                UtilMSG.addWarningMsg("No se puedo enviar el correo "+e.getCause()+e.getMessage());
            } else {                                
                UtilMSG.addSupportMsg();                
                UtilMSG.addWarningMsg("No se puedo enviar el correo "+e.getCause()+e.getMessage());
                UtilLog.generarLog(this.getClass(), e);
            }            
        }
    }
    
    public void mostrarDialogoPlanAccion() {
        try {                     
            Sesion sesion = (Sesion) UtilJSF.getBean("sesion");
            this.sdiSeleccionado = (SeccionDetalleItems) UtilJSF.getBean("varSeccionDetalleItems");
                    
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            GestorResponsable gestorResponsable = new GestorResponsable();

            evaluacionPlanAccionDetalles = new ArrayList<>();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
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
            
            if(clasehallazgos.isEmpty()){
                
                GestorFuenteHallazgo gestorFuentehallazgo = new GestorFuenteHallazgo();
                fuentehallazgos.addAll((Collection<? extends FuenteHallazgo>) gestorFuentehallazgo.cargarListaFuentehallazgo());
                
                GestorClaseHallazgo gestorClasehallazgo = new GestorClaseHallazgo();            
                clasehallazgos.addAll((Collection<? extends ClaseHallazgo>) gestorClasehallazgo.cargarListaClasehallazgo());

                GestorTipoAccion gestorTipoaccion = new GestorTipoAccion();            
                tipoacciones.addAll((Collection<? extends TipoAccion>) gestorTipoaccion.cargarListaTipoaccion());

                GestorMotivoCorreccion gestorMotivocorreccion = new GestorMotivoCorreccion();
                motivocorrecciones.addAll((Collection<? extends MotivoCorreccion>) gestorMotivocorreccion.cargarListaMotivocorreccion());            
                
            
            }
            
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            
            GestorCentroTrabajo gestorCentrotrabajo = new GestorCentroTrabajo();
            centrostrabajo= new ArrayList<>();
            centrostrabajo.addAll((Collection<? extends CentroTrabajo>) gestorCentrotrabajo.cargarListaCentrosTrabajoactivos(e.getEstablecimiento().getCodigoEstablecimiento()));
            

            List<Responsable> responsablesSisgapp = new ArrayList<>();
            for (Responsable rs : sesion.getResponsables()) {
                for (Responsable r : responsables) {
                    if (!rs.equals(r)) {
                        responsablesSisgapp.add(rs);
                    }
                }
            }
            if (!responsablesSisgapp.isEmpty()) {
                responsables.addAll(responsablesSisgapp);
            }

            Dialogo dialogo = new Dialogo("dialogos/plan-accion.xhtml", "Plan Acción", "clip", Dialogo.WIDTH_100);
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
    
    public String getStyleDias() {
        evaluacionPlanAccionDetalle = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");
        String style = "";          
        if(evaluacionPlanAccionDetalle.getDiasRestantes() != null && evaluacionPlanAccionDetalle.getDiasRestantes() <= 0 ){
            style += "color: #FF0000";
        }
        return style;
    }

    public TipoPlanAccion getTipoPlanAccion() {
        return tipoPlanAccion;
    }

    public void setTipoPlanAccion(TipoPlanAccion tipoPlanAccion) {
        this.tipoPlanAccion = tipoPlanAccion;
    }

    public List<Integer> getTiposPlanAccion() {
        return tiposPlanAccion;
    }

    public void setTiposPlanAccion(List<Integer> tiposPlanAccion) {
        this.tiposPlanAccion = tiposPlanAccion;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public EvaluacionPlanAccionDetallePK getEvaluacionPlanAccionDetallePK() {
        return evaluacionPlanAccionDetallePK;
    }

    public void setEvaluacionPlanAccionDetallePK(EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK) {
        this.evaluacionPlanAccionDetallePK = evaluacionPlanAccionDetallePK;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
    
    public List<FuenteHallazgo> getFuentehallazgos() {
        return fuentehallazgos;
    }

    public void setFuentehallazgos(List<FuenteHallazgo> fuentehallazgos) {
        this.fuentehallazgos = fuentehallazgos;
    }

    public PlanTrabajoObjetivo getObjetivoSeleccionado() {
        return objetivoSeleccionado;
    }

    public void setObjetivoSeleccionado(PlanTrabajoObjetivo objetivoSeleccionado) {
        this.objetivoSeleccionado = objetivoSeleccionado;
    }

    public List<PlanTrabajoObjetivo> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(List<PlanTrabajoObjetivo> objetivos) {
        this.objetivos = objetivos;
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

    public List<CentroTrabajo> getCentrostrabajo() {
        return centrostrabajo;
    }

    public void setCentrostrabajo(List<CentroTrabajo> centrostrabajo) {
        this.centrostrabajo = centrostrabajo;
    }    

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
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

    public List<TipoPlanAccion> getTipoPlanAccionList() {
        return tipoPlanAccionList;
    }

    public void setTipoPlanAccionList(List<TipoPlanAccion> tipoPlanAccionList) {
        this.tipoPlanAccionList = tipoPlanAccionList;
    }    
    
    /**
     * @return the ciclosStringSeleccionado
     */
    public List<String> getCiclosStringSeleccionado() {
        return ciclosStringSeleccionado;
    }

    public List<TipoPlanAccion> getTiposPlanAccionIntegerSeleccionado() {
        return tiposPlanAccionIntegerSeleccionado;
    }

    public void setTiposPlanAccionIntegerSeleccionado(List<TipoPlanAccion> tiposPlanAccionIntegerSeleccionado) {
        this.tiposPlanAccionIntegerSeleccionado = tiposPlanAccionIntegerSeleccionado;
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

    /**
     * @return the evaluacionPlanAccionDetalle
     */
    public EvaluacionPlanAccionDetalle getEvaluacionPlanAccionDetalle() {
        return evaluacionPlanAccionDetalle;
    }

    /**
     * @param evaluacionPlanAccionDetalle the evaluacionPlanAccionDetalle to set
     */
    public void setEvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetalle evaluacionPlanAccionDetalle) {
        this.evaluacionPlanAccionDetalle = evaluacionPlanAccionDetalle;
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

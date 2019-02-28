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
import com.gestor.entity.UtilMSG;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.FuenteHallazgo;
import com.gestor.gestor.Recursos;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.gestor.controlador.GestorEvaluacionCapacitacion;
import com.gestor.gestor.controlador.GestorFuenteHallazgo;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.ListaDetalle;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.Responsable;
import com.gestor.publico.Usuarios;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorObjetivo;
import com.gestor.publico.controlador.GestorPrograma;
import com.gestor.publico.controlador.GestorResponsable;
import com.gestor.publico.controlador.GestorUsuario;
import com.gestor.seguimiento.controlador.GestorPlanTrabajo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author fjvc
 */
@ManagedBean(name = "uiPlantrabajo")
@SessionScoped
public class UIPlanTrabajo implements Serializable{

    private List<PlanTrabajo> plantrabajoList = new ArrayList<>();    
    private List<PlanTrabajoActividad> plantrabajoActividadList = new ArrayList<>();
    private List<Responsable> responsables = new ArrayList<>();
    private List<FuenteHallazgo> fuenteHallazgos = new ArrayList<>();
    private List<Recursos> recursos = new ArrayList<>();
    private List<String> actividadEstadoSeleccionado = new ArrayList<>();    
    
    private Date fechaPlanInicio;
    private Date fechaPlanFin;
    
    private PlanTrabajo plantrabajo;     
    private PlanTrabajoActividad plantrabajoActividad;     
    private PlanTrabajoActividadNota plantrabajoActividadnota;
    private PlanTrabajoObjetivo objetivo;
    private PlanTrabajoPrograma programa;  
    private Establecimiento establecimiento = new Establecimiento();
    private Usuarios usuariosSeleccionado;
    private PlanTrabajo planTrabajoSeleccionado;
    private String responsable;
    
    private List<Usuarios> usuariosList = new ArrayList<>();
    private List<PlanTrabajo> planesTrabajoList = new ArrayList<>();
    private ListaDetalle listadetalle= new ListaDetalle();
    
    
    private List<PlanTrabajoActividad> planTrabajoactividadmetaList= new ArrayList<>();    
    
    private GestorObjetivo gestorObjetivo;
    private GestorPrograma gestorPrograma;
    private GestorPlanTrabajo gestorPlanTrabajo;    
        
    private List<PlanTrabajoObjetivo> objetivos = new ArrayList<>();    
    private List<PlanTrabajoObjetivo> objetivoList= new ArrayList<>();    
    private List<PlanTrabajoPrograma> programaList= new ArrayList<>();            
    private List<ListaDetalle> listadetalles= new ArrayList<>();
    
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();
    
    private Map<String, String> actividadEstado = new HashMap<>();
    
    private boolean guardarActivo = false;
    private boolean nuevoActivo = true;
    private boolean consultarActivo = false;
    private boolean cancelarActivo = false;
    private boolean eliminarActivo = false;
    private Boolean filtroActivo = Boolean.TRUE;
    
    private Evaluacion evaluacion = new Evaluacion();
    private Integer cons=0;
    
    private List<Evaluacion> evaluacionList = new ArrayList<>();   
    
    public UIPlanTrabajo(){ 
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            GestorUsuario gestorUsuario = new GestorUsuario();

            establecimientoList = new ArrayList<>();
            establecimientoList.addAll(s.getEstablecimientoList());

            actividadEstado = new HashMap<>();
            actividadEstado.put(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO_TEXTO, App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO);
            actividadEstado.put(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_CERRADO_TEXTO, App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_CERRADO);
            actividadEstado.put(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO_TEXTO, App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO);            

            usuariosList = new ArrayList<>();
            usuariosList.addAll(gestorUsuario.cargarListaUsuarios());
            cons=0;
            

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }    
    
    @PostConstruct
    public void init() {                   
        plantrabajo = new PlanTrabajo();        
        plantrabajoActividad = new PlanTrabajoActividad();        
        plantrabajoActividadnota = new PlanTrabajoActividadNota();
    }
    
    private void cargarPlantrabajo() {
        try {        
            Evaluacion ev= (Evaluacion) UtilJSF.getBean("evaluacion");             
            this.plantrabajoList = new ArrayList<>();
            GestorPlanTrabajo gestorPlantrabajo= new GestorPlanTrabajo();
            this.plantrabajoList.addAll((Collection<? extends PlanTrabajo>) gestorPlantrabajo.cargarPlantrabajoList(ev.getEstablecimiento().getCodigoEstablecimiento()));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void consultarEvaluacionesLista() {
        try {
            evaluacionList = new ArrayList<>();
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            Sesion s = (Sesion) UtilJSF.getBean("sesion");            
            evaluacionList.addAll(gestorEvaluacion.cargarEvaluacionList(s.getEstablecimiento().getCodigoEstablecimiento(), s.getConfiguracion().get(App.CONFIGURACION_MOSTRAR_EVALUACIONES).toString())                                
            );          
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }
    
    
    
    public void dialogoObjetivo() {
        try {
            objetivo = new PlanTrabajoObjetivo();
            Dialogo dialogo = new Dialogo ("dialogos/objetivo.xhtml", "Crear Objetivo", "clip", Dialogo.WIDTH_80);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            this.cargarObjetivo();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void guardarObjetivo() {
        try {                                
            PlanTrabajo pt = (PlanTrabajo) UtilJSF.getBean("varPlantrabajo");     
            UtilJSF.setBean("varPlantrabajo", pt, UtilJSF.SESSION_SCOPE);
            
            if(objetivoList.isEmpty()){
                objetivo.setCodObjetivo(1);
            }if(objetivo.getCodObjetivo()==null){
                objetivo.setCodObjetivo(objetivoList.size()+1);
            }          
            
            PlanTrabajoObjetivo obj= new PlanTrabajoObjetivo(pt.getCodEstablecimiento(), pt.getCodPlantrabajo(), objetivo.getCodObjetivo(), objetivo.getNombre());
            gestorObjetivo.validarObjetivo(obj);
            gestorObjetivo.almacenarObjetivo(obj);                        
            this.cargarObjetivo();
            UtilMSG.addSuccessMsg("Objetivo almacenado correctamente.");                 
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
    public void cargarObjetivo() {
        try {            
            PlanTrabajo pt= (PlanTrabajo) UtilJSF.getBean("varPlantrabajo");                                           
            UtilJSF.setBean("varPlantrabajo", pt, UtilJSF.SESSION_SCOPE);
            this.objetivoList = new ArrayList<>();
            gestorObjetivo = new GestorObjetivo();
            this.objetivoList.addAll((Collection<? extends PlanTrabajoObjetivo>) gestorObjetivo.cargarListaObjetivo(pt.getCodEstablecimiento(),pt.getCodPlantrabajo()));            
            
            if(this.objetivo.getCodObjetivo() != null){
                this.cargarPrograma();
            }
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarObjetivoactividad() {
        try {
            plantrabajo = (PlanTrabajo) UtilJSF.getBean("varPlantrabajo");                        
            UtilJSF.setBean("varPlantrabajo", plantrabajo, UtilJSF.SESSION_SCOPE); 
            if(plantrabajo.getCodPlantrabajo() == null){
                plantrabajo = (PlanTrabajo) UtilJSF.getBean("planTrabajo");                
            }
            this.objetivoList = new ArrayList<>();
            gestorObjetivo = new GestorObjetivo();            
            this.objetivoList.addAll((Collection<? extends PlanTrabajoObjetivo>) gestorObjetivo.cargarListaObjetivo(plantrabajo.getCodEstablecimiento(), plantrabajo.getCodPlantrabajo()));                                    
            this.cargarPrograma();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    
    public void limpiarObjetivo(){        
        objetivo=new PlanTrabajoObjetivo();
        
    }
    
    public void subirItemObjetivo() {        
        objetivo = (PlanTrabajoObjetivo) UtilJSF.getBean("varObjetivo");        
    }
    
    public void subirItemPlantrabajo() {
        plantrabajo = (PlanTrabajo) UtilJSF.getBean("varPlantrabajo");
        UtilJSF.setBean("planTrabajo", plantrabajo, UtilJSF.SESSION_SCOPE);         
    }
    
    public void subirItemPlantrabajoactividad() {        
        plantrabajoActividad = (PlanTrabajoActividad) UtilJSF.getBean("varPlantrabajoactividad");   
        this.cargarPrograma();
        this.cargarObjetivoactividad();
        UtilJSF.setBean("planTrabajoactividad", plantrabajoActividad, UtilJSF.SESSION_SCOPE);         
    }

    
    
    
    public void eliminarObjetivo(){
        try {
            
            objetivo = (PlanTrabajoObjetivo) UtilJSF.getBean("varObjetivo");
            gestorObjetivo.eliminarObjetivo(objetivo);
            this.cargarObjetivo();
            UtilMSG.addSuccessMsg("Objetivo eliminado.");
        } catch (Exception ex) {
            UtilMSG.addSuccessMsg("Objetivo en uso.");
        }
    }
    
     public void guardarPlantrabajo(){
        try {
            evaluacion = (Evaluacion) UtilJSF.getBean("evaluacion");     
            UtilJSF.setBean("varEvaluacion", evaluacion, UtilJSF.APPLICATION_SCOPE);                                     
            if(plantrabajoList.isEmpty()){
                plantrabajo.setCodPlantrabajo(1);
            }if(plantrabajo.getCodPlantrabajo()==null){
                plantrabajo.setCodPlantrabajo(plantrabajoList.size()+1);
            }                                   
            plantrabajo.setEstado("A");
            
            PlanTrabajo pltrabajo = new PlanTrabajo( evaluacion.getEvaluacionPK().getCodigoEstablecimiento(), plantrabajo.getCodPlantrabajo(), plantrabajo.getDescripcion(), plantrabajo.getFechaVenc(), plantrabajo.getMeta(), 
                    plantrabajo.getEstado(), null
            );            
            
            GestorPlanTrabajo gestorPlantrabajo= new GestorPlanTrabajo();
            gestorPlantrabajo.almacenarPlantrabajo(pltrabajo);            
            this.cargarPlantrabajo();
            UtilMSG.addSuccessMsg("Plan Trabajo almacenado correctamente.");
            UtilJSF.setBean("varPlantrabajo", plantrabajo, UtilJSF.SESSION_SCOPE);
            this.limpiarPlantrabajo();
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }        
    }
    
    
    public String subirItemevaluacion() {
        try{  
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("varEvaluacion");      
            UtilJSF.setBean("evaluacion", ev , UtilJSF.SESSION_SCOPE);                            
            this.cargarPlantrabajo();            
            return ("/seguimiento/plan-trabajo.xhtml?faces-redirect=true");  
            
        }catch(Exception e){
        return ("/seguimiento/plan-trabajo.xhtml?faces-redirect=true");                    
        }        
    }
    
    public void subirItemPrograma() {                        
        programa = (PlanTrabajoPrograma)  UtilJSF.getBean("varPrograma");                          
    }
    
    public void cargarPrograma() {
        try {                                      
            objetivo = (PlanTrabajoObjetivo)  UtilJSF.getBean("varObjetivo");   
            UtilJSF.setBean("objetivo", this.objetivo , UtilJSF.SESSION_SCOPE);                            
            if (objetivo==null){
                objetivo = new PlanTrabajoObjetivo();
                objetivo.setCodEstablecimiento(plantrabajoActividad.getObjetivo().getCodEstablecimiento());
                objetivo.setCodPlantrabajo(plantrabajoActividad.getObjetivo().getCodPlantrabajo());
                objetivo.setCodObjetivo(plantrabajoActividad.getObjetivo().getCodObjetivo());
            }
            this.programaList = new ArrayList<>();
            gestorPrograma = new GestorPrograma();
            this.programaList.addAll((Collection<? extends PlanTrabajoPrograma>) gestorPrograma.cargarListaProgramas(objetivo.getCodEstablecimiento(),objetivo.getCodObjetivo(), objetivo.getCodPlantrabajo()));                                                
        } catch (Exception ex) {            
        }
    }
    
    public void cargarProgramaList() {
        try {                          
            programa = (PlanTrabajoPrograma) UtilJSF.getBean("varPrograma");                      
            this.programaList = new ArrayList<>();
            gestorPrograma = new GestorPrograma();
            this.programaList.addAll((Collection<? extends PlanTrabajoPrograma>) gestorPrograma.cargarListaProgramas(programa.getCodEstablecimiento(),programa.getCodObjetivo(), programa.getCodPlantrabajo()));                                    
        } catch (Exception ex) {            
        }
    }
    
    private void cargarPlantrabajoactividad() {
        try {                    
            //UtilJSF.setBean("varPlantrabajo", plantrabajo, UtilJSF.SESSION_SCOPE);
            plantrabajo = (PlanTrabajo)  UtilJSF.getBean("varPlantrabajo");                       
            this.plantrabajoActividadList = new ArrayList<>();
            GestorPlanTrabajo gestorPlantrabajo = new GestorPlanTrabajo();
            this.plantrabajoActividadList.addAll((Collection<? extends PlanTrabajoActividad>) gestorPlantrabajo.cargarPlantrabajoactividadList(plantrabajo));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
        
    public void eliminarPrograma(){
        try {            
            UtilJSF.setBean("programa", programa, UtilJSF.SESSION_SCOPE);
            programa = (PlanTrabajoPrograma)  UtilJSF.getBean("varPrograma");
            gestorPrograma.eliminarPrograma(programa);                        
            this.cargarPrograma();
            this.limpiarPrograma();
            UtilMSG.addSuccessMsg("Programa eliminado.");
            
        } catch (Exception ex) {
            UtilMSG.addSuccessMsg("Programa en uso.");
        }
    }
    
    public void guardarPrograma() {
        try {                        
            GestorPrograma gestorPrograma= new GestorPrograma();                     
            
            if(programaList.isEmpty()){
                programa.setCodPrograma(1);
                programa.setPeso(100);
            }if(programa.getCodPrograma()==null){
                programa.setCodPrograma(programaList.size()+1);
                Integer pr= programaList.size()+1;
                programa.setPeso(100/pr);
            }
            
            PlanTrabajoPrograma pro= new PlanTrabajoPrograma(objetivo.getCodEstablecimiento(), objetivo.getCodPlantrabajo(), objetivo.getCodObjetivo(), programa.getCodPrograma(), programa.getPeso(), programa.getNombre());            
            gestorPrograma.validarPrograma(pro);
            gestorPrograma.almacenarPrograma(pro);
            
            
            UtilMSG.addSuccessMsg("Programa almacenado correctamente.");
            UtilJSF.setBean("varPrograma", pro, UtilJSF.SESSION_SCOPE);            
            this.cargarProgramaList();
            this.limpiarPrograma();

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
     public void dialogoPrograma() {
        try {        
            programa = new PlanTrabajoPrograma();
            Dialogo dialogo = new Dialogo("dialogos/programa.xhtml", "Crear Programa", "60%", "top: 0px;");
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");            
            this.cargarPrograma();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void limpiarPrograma() {
        UtilJSF.setBean("varPrograma", programa=null, UtilJSF.SESSION_SCOPE);                    
        programa=new PlanTrabajoPrograma();
        objetivo = (PlanTrabajoObjetivo) UtilJSF.getBean("objetivo");                                             
    }
        
    public void dialogoActividad() {
        try {
            
            PlanTrabajo p= (PlanTrabajo) UtilJSF.getBean("varPlantrabajo");            

            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion= new GestorEvaluacionCapacitacion();
            GestorResponsable gestorResponsable= new GestorResponsable();
            
            recursos= new ArrayList<>();
            recursos.addAll((Collection<? extends Recursos>) gestorEvaluacionCapacitacion.cargarListaRecursos());
                                   
            GestorEstablecimiento gestorEstablecimiento= new GestorEstablecimiento();
            Integer sisgapp = gestorEstablecimiento.buscarSisgapp();
            
            responsables = new ArrayList<>();
            List<String> condicionesConsulta = new ArrayList<>();
            condicionesConsulta.add(App.CONDICION_WHERE);
            condicionesConsulta.add(Responsable.RESPONSABLE_CONDICION_CODIGO_ESTABLECIMIENTO.replace("?", String.valueOf(p.getCodEstablecimiento())+","+sisgapp));
            responsables.addAll(gestorResponsable.cargarListaResponsable(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO) 
            ));
            
            GestorFuenteHallazgo gestorFuentehallazgo =new GestorFuenteHallazgo();
            fuenteHallazgos = new ArrayList<>();
            fuenteHallazgos.addAll((Collection<? extends FuenteHallazgo>) gestorFuentehallazgo.cargarListaFuentehallazgo());
            
            Dialogo dialogo = new Dialogo ("dialogos/plan-trabajo-actividad.xhtml", "Crear Actividad", "clip", Dialogo.WIDTH_80);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            
            UtilJSF.execute("PF('dialog').show();");                        
            plantrabajoActividad = new PlanTrabajoActividad();            
            this.cargarObjetivoactividad();            
            this.cargarPlantrabajoactividad();
            this.limpiarPlantrabajo();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cerrarPlantrabajoactividad() {
        try {            
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            gestorPlanTrabajo= new GestorPlanTrabajo();           
            plantrabajoActividad = (PlanTrabajoActividad) UtilJSF.getBean("varPlanTrabajoactividad");                       
            plantrabajoActividad.setEstado(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_ESTADO_CERRADO);
            gestorPlanTrabajo.almacenarPlantrabajoactividad(plantrabajoActividad);            
            UtilJSF.update("formPlanesTrabajo");
            UtilMSG.addSuccessMsg("Actividad Finalizada", "Se finalizo Actividad # " + plantrabajoActividad.getCodActividad());
            this.cargarPlanTrabajoactividadEstablecimiento();
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void guardarPlantrabajoactividad() {
        try { 
            plantrabajo= (PlanTrabajo) UtilJSF.getBean("varPlantrabajo");
            
            if(plantrabajoActividadList.isEmpty()){
                plantrabajoActividad.setCodActividad(1);
            }if(plantrabajoActividad.getCodActividad()==null){
                plantrabajoActividad.setCodActividad(plantrabajoActividadList.size()+1);
            }
            
            plantrabajoActividad.setEstado("A");
            PlanTrabajoActividad pta= new PlanTrabajoActividad(plantrabajo.getCodEstablecimiento(), plantrabajo.getCodPlantrabajo(), plantrabajoActividad.getCodActividad(),
                objetivo.getCodObjetivo(), plantrabajoActividad.getPrograma().getCodPrograma(), plantrabajoActividad.getFuenteHallazgo().getCodFuentehallazgo(), plantrabajoActividad.getResponsable().getCedula(), plantrabajoActividad.getRecursos().getCodRecursos(), plantrabajoActividad.getDescripcion(), plantrabajoActividad.getFechaVenc(),
                plantrabajoActividad.getEstado(), null);
            
            PlanTrabajoActividadNota ptan= new PlanTrabajoActividadNota(plantrabajo.getCodEstablecimiento(),  plantrabajo.getCodPlantrabajo(),  plantrabajoActividad.getCodActividad(),
                objetivo.getCodObjetivo(), plantrabajoActividad.getPrograma().getCodPrograma(), 0, "REGISTRO INICIAL", "Inicia registro de actividad ", plantrabajoActividad.getEstado(), null);
            
            pta.setPlanTrabajoActividadNota(ptan);
            
            GestorPlanTrabajo gestorPlantrabajo= new GestorPlanTrabajo();
            gestorPlantrabajo.almacenarPlantrabajoactividad(pta);
            
            UtilMSG.addSuccessMsg("Actividad almacenada correctamente.");
            UtilJSF.setBean("varPlantrabajoactividad", plantrabajoActividad, UtilJSF.SESSION_SCOPE);            
            this.cargarPlantrabajoactividad();            
            this.limpiarPlantrabajoactividad();
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }

    } 
    
    public void cargarPlanesTrabajoList(){

        try {            
            if(establecimiento!=null){
                gestorPlanTrabajo= new GestorPlanTrabajo();
                planesTrabajoList= new ArrayList<>();
                planesTrabajoList.addAll(gestorPlanTrabajo.cargarPlantrabajoAbiertosList(establecimiento.getCodigoEstablecimiento()));
            }
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    private List<String> filtrarOpcionesSeleccionadas() throws Exception {
        List<String> condicionesConsulta = new ArrayList<>();
        condicionesConsulta.add(App.CONDICION_WHERE);
        
        if(establecimiento == null){   
            String cadena;         
            cadena = Integer.toString(establecimiento.getCodigoEstablecimiento());
            
            condicionesConsulta.add(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
            
        }
        
        if(establecimiento!=null){            
            String cadena="";
            cadena = Integer.toString(establecimiento.getCodigoEstablecimiento());
            condicionesConsulta.add(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena ));             
        }
        
        if(planTrabajoSeleccionado != null){
            UtilJSF.setBean("varPlantrabajo", planTrabajoSeleccionado, UtilJSF.SESSION_SCOPE);            
            String codpt= Integer.toString(planTrabajoSeleccionado.getCodPlantrabajo());
            condicionesConsulta.add(App.CONDICION_AND);            
            condicionesConsulta.add(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_CONDICION_COD_PlAN_TRABAJO.replace("?", codpt ));
        }
        

        if (usuariosSeleccionado != null && usuariosSeleccionado.getUsuariosPK() != null
                && usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() != null && !usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario().equalsIgnoreCase("")) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_CONDICION_DOCUMENTO_USUARIO.replace("?", UtilTexto.CARACTER_COMILLA + usuariosSeleccionado.getUsuariosPK().getDocumentoUsuario() + UtilTexto.CARACTER_COMILLA));
        }        

        if (actividadEstadoSeleccionado != null && !actividadEstadoSeleccionado.isEmpty()) {
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for (String s : actividadEstadoSeleccionado) {
                cadena += "," + UtilTexto.CARACTER_COMILLA + s + UtilTexto.CARACTER_COMILLA;
            }
            condicionesConsulta.add(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_CONDICION_ESTADO.replace("?", cadena));
        }

        if (fechaPlanInicio != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_CONDICION_FECHA_REGISTRO_GTE.replace("?", UtilFecha.formatoFecha(fechaPlanInicio, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        if (fechaPlanFin != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_CONDICION_FECHA_REGISTRO_LTE.replace("?", UtilFecha.formatoFecha(fechaPlanFin, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }            
        return condicionesConsulta;
        
    }
    
    public String cargarPlanTrabajoactividadGeneral() {
        try {            
            UtilJSF.setBean("dialogo", new Dialogo(), UtilJSF.SESSION_SCOPE);                        
            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();
            plantrabajoActividadList = new ArrayList<>();
            GestorPlanTrabajo gestorPlanTrabajo = new GestorPlanTrabajo();
                    
            
                List<String> condicionesConsulta = new ArrayList<>();
                condicionesConsulta.add(App.CONDICION_WHERE);
                condicionesConsulta.add(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_CONDICION_COD_ESTABLECIMIENTO.replace("?", "3" ));
                establecimiento.setCodigoEstablecimiento(3);           
                this.cargarPlanesTrabajoList();
                plantrabajoActividadList.addAll(gestorPlanTrabajo.cargarListaEvaluacionPlanAccion(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
                )
                );
                if(!plantrabajoActividadList.isEmpty()){
                UtilJSF.setBean("varPlantrabajoactividad", plantrabajoActividadList.get(0), UtilJSF.SESSION_SCOPE);                
                }
                //this.getAvancePlanAccion();
            UtilJSF.setBean("varPlantrabajoactividad", new PlanTrabajoActividad(), UtilJSF.SESSION_SCOPE);
            return ("/seguimiento/planes-trabajo.xhtml?faces-redirect=true");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }
    
    public String cargarPlanTrabajoactividadEstablecimiento() {
        try {            
            UtilJSF.setBean("dialogo", new Dialogo(), UtilJSF.SESSION_SCOPE);                        
            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();
            plantrabajoActividadList = new ArrayList<>();
            GestorPlanTrabajo gestorPlanTrabajo = new GestorPlanTrabajo();
                    
            establecimiento=new Establecimiento();
                String codE = Integer.toString(plantrabajoActividad.getCodEstablecimiento());
                List<String> condicionesConsulta = new ArrayList<>();
                condicionesConsulta.add(App.CONDICION_WHERE);
                condicionesConsulta.add(PlanTrabajoActividad.PLAN_TRABAJO_ACTIVIDAD_CONDICION_COD_ESTABLECIMIENTO.replace("?", codE ));
                establecimiento.setCodigoEstablecimiento(plantrabajoActividad.getCodEstablecimiento());
                plantrabajoActividadList.addAll(gestorPlanTrabajo.cargarListaEvaluacionPlanAccion(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
                ));
                
                if(!plantrabajoActividadList.isEmpty()){
                UtilJSF.setBean("varPlantrabajoactividad", plantrabajoActividadList.get(0), UtilJSF.SESSION_SCOPE);                
                }
                //this.getAvancePlanAccion();
            UtilJSF.setBean("varPlantrabajoactividad", new PlanTrabajoActividad(), UtilJSF.SESSION_SCOPE);
            return ("/seguimiento/planes-trabajo.xhtml?faces-redirect=true");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }
    
    /*public Integer getAvancePlanTrabajo() {
        try {                          
            long codEv=0;
            if(!plantrabajoActividadList.isEmpty()){
            codEv=plantrabajoActividadList.get(0).getEvaluacion().getEvaluacionPK().getCodEvaluacion();
            }
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            return gestorEvaluacion.avancePlanaccion(codEv);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }        
        return null;
    }*/
    
    public void consultarSeguimientoPlanTrabajo() {
        try {
            plantrabajoActividadList = new ArrayList<>();            
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();
            gestorPlanTrabajo = new GestorPlanTrabajo();
            
            
            if(planTrabajoSeleccionado!=null){
                plantrabajoActividadList.addAll(gestorPlanTrabajo.cargarListaEvaluacionPlanAccionpt(
                UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)                
                )); 
            }else{
                plantrabajoActividadList.addAll(gestorPlanTrabajo.cargarListaEvaluacionPlanAccion(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)                
                ));            
            }
            int cont=0;
            int acum=0;
            int peso=0;
            cons=0;
            for(int i=0;i<plantrabajoActividadList.size();i++){
                if(plantrabajoActividadList.get(i).getEstado().equals("A")){
                    cont++;
                    peso = plantrabajoActividadList.get(i).getPrograma().getPeso();                    
                    acum+=peso;
                }
                if(cont==0){
                    cons=100;
                }else{
                    cons=(100-(acum/cont));
                }
            }
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void mostrarNotaSeguimiento() {
        try {
            
            plantrabajoActividadnota= new PlanTrabajoActividadNota();
            gestorPlanTrabajo = new GestorPlanTrabajo(); 
            if(plantrabajoActividad.getCodActividad()==null){
            plantrabajoActividad= (PlanTrabajoActividad) UtilJSF.getBean("varPlanTrabajoactividad");                
            }
            plantrabajoActividad.setPlanTrabajoactividadNotasList(gestorPlanTrabajo.cargarEvaluacionPlanTrabajoNotasList(plantrabajoActividad));
            Dialogo dialogo = new Dialogo("dialogos/plan-trabajo-notas.xhtml", "Seguimiento Plan Trabajo", "clip", Dialogo.WIDTH_80);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            UtilJSF.setBean("varPlanTrabajoActividadNota", new PlanTrabajoActividadNota(), UtilJSF.SESSION_SCOPE);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }    
    
    
    public void procesarPlanTrabajoActividadNota() {
        try {            
            gestorPlanTrabajo = new GestorPlanTrabajo();   
            UtilJSF.setBean("varPlanTrabajoActividad", plantrabajoActividad, UtilJSF.SESSION_SCOPE);
            
            
            PlanTrabajoActividadNota ptan= (PlanTrabajoActividadNota) UtilJSF.getBean("planTrabajoActividadNota");
            
            if( ptan==null ){
                ptan=(PlanTrabajoActividadNota) UtilJSF.getBean("varPlanTrabajoActividadNota");
            }
            
            ptan.setEstado(plantrabajoActividad.getEstado());            
            ptan.setCodEstablecimiento(plantrabajoActividad.getCodEstablecimiento());
            ptan.setCodPlantrabajo(plantrabajoActividad.getCodPlantrabajo());
            ptan.setCodActividad(plantrabajoActividad.getCodActividad());
            ptan.setCodObjetivo(plantrabajoActividad.getCodObjetivo());
            ptan.setCodPrograma(plantrabajoActividad.getCodPrograma());
            if(ptan.getCodNota()==null || ptan.getCodNota()==0){
                ptan.setDescripcion(plantrabajoActividadnota.getDescripcion());
                ptan.setEstado(plantrabajoActividad.getEstado());            
                ptan.setNombre(plantrabajoActividadnota.getNombre());
                ptan.setCodNota(0);
            }          
            
            plantrabajoActividadnota = gestorPlanTrabajo.validarPlanTrabajoActividadNota(ptan);
            gestorPlanTrabajo.procesarPlanTrabajoActividadNota(ptan);            
            
            UtilMSG.addSuccessMsg("Seguimiento Guardado", "Se almaceno el seguimiento a la actividad satisfactoriamente.");
            UtilJSF.setBean("varPlanTrabajoActividadNota", new PlanTrabajoActividadNota(), UtilJSF.SESSION_SCOPE);
            this.mostrarNotaSeguimiento();
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
    public void modificarNotaSeguimiento() {
        plantrabajoActividadnota = (PlanTrabajoActividadNota) UtilJSF.getBean("varPlanTrabajoActividadNota");        
        UtilJSF.setBean("varPlanTrabajoActividadNota", plantrabajoActividadnota, UtilJSF.SESSION_SCOPE);
    }
    
    public String getStylePorcentaje() {
        String style = "padding: 6px;"
                + "opacity: 0.83;"
                + "transition: opacity 0.6s;";          
        if(cons >= 0 && cons < 50  ){
            style += "background-color: #f44336;";
        }if(cons >= 50 && cons<=71){
                style += "background-color: #fbaa36;";
        }if(cons>71){
            style += "background-color: #008000;";
        }
        return style;
    }
    
    public String getStyleDias() {
        plantrabajoActividad = (PlanTrabajoActividad) UtilJSF.getBean("varPlanTrabajoactividad");
        String style = "";          
        if(plantrabajoActividad.getDiasRestantes() != null && plantrabajoActividad.getDiasRestantes() <= 0 ){
            style += "color: #FF0000";
        }
        return style;
    }

    public Integer getCons() {
        return cons;
    }

    public void setCons(Integer cons) {
        this.cons = cons;
    }

    public List<PlanTrabajo> getPlanesTrabajoList() {
        return planesTrabajoList;
    }

    public void setPlanesTrabajoList(List<PlanTrabajo> planesTrabajoList) {
        this.planesTrabajoList = planesTrabajoList;
    }

    public PlanTrabajo getPlanTrabajoSeleccionado() {
        return planTrabajoSeleccionado;
    }

    public void setPlanTrabajoSeleccionado(PlanTrabajo planTrabajoSeleccionado) {
        this.planTrabajoSeleccionado = planTrabajoSeleccionado;
    }
    
    public PlanTrabajoActividadNota getPlantrabajoActividadnota() {
        return plantrabajoActividadnota;
    }

    public void setPlantrabajoActividadnota(PlanTrabajoActividadNota plantrabajoActividadnota) {
        this.plantrabajoActividadnota = plantrabajoActividadnota;
    }

    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public List<Establecimiento> getEstablecimientoList() {
        return establecimientoList;
    }

    public void setEstablecimientoList(List<Establecimiento> establecimientoList) {
        this.establecimientoList = establecimientoList;
    }

    public List<Establecimiento> getEstablecimientoListSeleccionado() {
        return establecimientoListSeleccionado;
    }

    public void setEstablecimientoListSeleccionado(List<Establecimiento> establecimientoListSeleccionado) {
        this.establecimientoListSeleccionado = establecimientoListSeleccionado;
    }

    public Map<String, String> getActividadEstado() {
        return actividadEstado;
    }

    public void setActividadEstado(Map<String, String> actividadEstado) {
        this.actividadEstado = actividadEstado;
    }

    public Date getFechaPlanInicio() {
        return fechaPlanInicio;
    }

    public void setFechaPlanInicio(Date fechaPlanInicio) {
        this.fechaPlanInicio = fechaPlanInicio;
    }

    public Date getFechaPlanFin() {
        return fechaPlanFin;
    }

    public void setFechaPlanFin(Date fechaPlanFin) {
        this.fechaPlanFin = fechaPlanFin;
    }

    public List<String> getActividadEstadoSeleccionado() {
        return actividadEstadoSeleccionado;
    }

    public void setActividadEstadoSeleccionado(List<String> actividadEstadoSeleccionado) {
        this.actividadEstadoSeleccionado = actividadEstadoSeleccionado;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Usuarios getUsuariosSeleccionado() {
        return usuariosSeleccionado;
    }

    public void setUsuariosSeleccionado(Usuarios usuariosSeleccionado) {
        this.usuariosSeleccionado = usuariosSeleccionado;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public List<Responsable> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }

    public List<Recursos> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recursos> recursos) {
        this.recursos = recursos;
    }

    public List<PlanTrabajoActividad> getPlantrabajoActividadList() {
        return plantrabajoActividadList;
    }

    public void setPlantrabajoActividadList(List<PlanTrabajoActividad> plantrabajoActividadList) {
        this.plantrabajoActividadList = plantrabajoActividadList;
    }

    public PlanTrabajoActividad getPlantrabajoActividad() {
        return plantrabajoActividad;
    }

    public void setPlantrabajoActividad(PlanTrabajoActividad plantrabajoActividad) {
        this.plantrabajoActividad = plantrabajoActividad;
    }
        
    public void limpiarPlantrabajo() throws Exception {
        cargarPlantrabajo();
        plantrabajo= new PlanTrabajo();                        
    }
    
    public void limpiarPlantrabajoactividad() throws Exception {
        this.cargarPlantrabajoactividad();
        plantrabajoActividad= new PlanTrabajoActividad();
        
    }

    public List<PlanTrabajoActividad> getPlanTrabajoactividadmetaList() {
        return planTrabajoactividadmetaList;
    }

    public void setPlanTrabajoactividadmetaList(List<PlanTrabajoActividad> planTrabajoactividadmetaList) {
        this.planTrabajoactividadmetaList = planTrabajoactividadmetaList;
    }

    public ListaDetalle getListadetalle() {
        return listadetalle;
    }

    public void setListadetalle(ListaDetalle listadetalle) {
        this.listadetalle = listadetalle;
    }

    public List<ListaDetalle> getListadetalles() {
        return listadetalles;
    }

    public void setListadetalles(List<ListaDetalle> listadetalles) {
        this.listadetalles = listadetalles;
    }

    public List<FuenteHallazgo> getFuenteHallazgos() {
        return fuenteHallazgos;
    }

    public void setFuenteHallazgos(List<FuenteHallazgo> fuenteHallazgos) {
        this.fuenteHallazgos = fuenteHallazgos;
    }

    public List<PlanTrabajoPrograma> getProgramaList() {
        return programaList;
    }

    public void setProgramaList(List<PlanTrabajoPrograma> programaList) {
        this.programaList = programaList;
    }

    public List<PlanTrabajoObjetivo> getObjetivoList() {
        return objetivoList;
    }

    public void setObjetivoList(List<PlanTrabajoObjetivo> objetivoList) {
        this.objetivoList = objetivoList;
    }

    public PlanTrabajoObjetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(PlanTrabajoObjetivo objetivo) {
        this.objetivo = objetivo;
    }

    public PlanTrabajoPrograma getPrograma() {
        return programa;
    }

    public void setPrograma(PlanTrabajoPrograma programa) {
        this.programa = programa;
    }

    public boolean isGuardarActivo() {
        return guardarActivo;
    }

    public void setGuardarActivo(boolean guardarActivo) {
        this.guardarActivo = guardarActivo;
    }

    public boolean isNuevoActivo() {
        return nuevoActivo;
    }

    public void setNuevoActivo(boolean nuevoActivo) {
        this.nuevoActivo = nuevoActivo;
    }

    public boolean isConsultarActivo() {
        return consultarActivo;
    }

    public void setConsultarActivo(boolean consultarActivo) {
        this.consultarActivo = consultarActivo;
    }

    public boolean isCancelarActivo() {
        return cancelarActivo;
    }

    public void setCancelarActivo(boolean cancelarActivo) {
        this.cancelarActivo = cancelarActivo;
    }

    public boolean isEliminarActivo() {
        return eliminarActivo;
    }

    public void setEliminarActivo(boolean eliminarActivo) {
        this.eliminarActivo = eliminarActivo;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public List<Evaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    public Boolean getFiltroActivo() {
        return filtroActivo;
    }

    public void setFiltroActivo(Boolean filtroActivo) {
        this.filtroActivo = filtroActivo;
    }

    public List<PlanTrabajoObjetivo> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(List<PlanTrabajoObjetivo> objetivos) {
        this.objetivos = objetivos;
    }

    public PlanTrabajo getPlantrabajo() {
        return plantrabajo;
    }

    public void setPlantrabajo(PlanTrabajo plantrabajo) {
        this.plantrabajo = plantrabajo;
    }

    public List<PlanTrabajo> getPlantrabajoList() {
        return plantrabajoList;
    }

    public void setPlantrabajoList(List<PlanTrabajo> plantrabajoList) {
        this.plantrabajoList = plantrabajoList;
    }   
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.gestor.controller.GestorGeneral;
import com.gestor.controller.Propiedades;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilFecha;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionCapacitacionDetalle;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.EvaluacionAdjuntos;
import com.gestor.publico.ListaDetalle;
import com.gestor.publico.Usuarios;
import com.gestor.publico.controlador.GestorUsuario;
import com.gestor.seguimiento.controlador.GestorPlanCapacitacion;
import com.gestor.seguimiento.controlador.GestorPlanMaestro;
import com.gestor.seguimiento.controlador.GestorPlanTitulo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author franj
 */
@ManagedBean(name = "uiPlanCapacitacion")
@SessionScoped
public class UIPlanCapacitacion implements Serializable {
    
    private List<PlanCapacitacion> planCapacitacionList = new ArrayList<>();            
    
    private Date fechaPlanInicio;
    private Date fechaPlanFin;
    
    private PlanCapacitacion planCapacitacion;    
    
    private Establecimiento establecimiento = new Establecimiento();
    private Usuarios usuariosSeleccionado;
    
    private String responsable;
    
    private List<Usuarios> usuariosList = new ArrayList<>();    
    private ListaDetalle listadetalle= new ListaDetalle();        
    
    private List<ListaDetalle> listadetalles= new ArrayList<>();
    
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();
    
    private Map<String, String> actividadEstado = new HashMap<>();
    
    private boolean guardarActivo = false;
    private boolean nuevoActivo = true;
    private boolean consultarActivo = false;
    private boolean cancelarActivo = false;
    private boolean eliminarActivo = false;
    private boolean volverActivo = false;
    private boolean filtroActivo = Boolean.TRUE;
    
    private Evaluacion evaluacion = new Evaluacion();
    
    private List<Evaluacion> evaluacionList = new ArrayList<>(); 
    
    private List<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles = new ArrayList<>();

    public UIPlanCapacitacion() {
        
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

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }
    
    @PostConstruct
    public void init() {                   
        planCapacitacion = new PlanCapacitacion();        
        establecimiento= new Establecimiento();
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
    
    public String subirItemevaluacion() {
        try{  
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("varEvaluacion");      
            UtilJSF.setBean("evaluacion", ev , UtilJSF.SESSION_SCOPE);                            
            this.cargarPlancapacitacion();
            return ("/seguimiento/crear-plan-capacitacion.xhtml?faces-redirect=true");                    
            
        }catch(Exception e){
        return ("/seguimiento/crear-plan-capacitacion.xhtml?faces-redirect=true");                    
        }        
    }
    
    public void guardarPlancapacitacion(){
        try {
            evaluacion = (Evaluacion) UtilJSF.getBean("evaluacion");     
            UtilJSF.setBean("varEvaluacion", evaluacion, UtilJSF.APPLICATION_SCOPE);                                     
            if(planCapacitacionList.isEmpty()){
                planCapacitacion.setCodPlancapacitacion(1);
            }if(planCapacitacion.getCodPlancapacitacion()==null){
                planCapacitacion.setCodPlancapacitacion(planCapacitacionList.size()+1);
            }                                   
            planCapacitacion.setEstado("A");
            
            PlanCapacitacion plcapacitacion = new PlanCapacitacion(evaluacion.getEvaluacionPK().getCodigoEstablecimiento(), planCapacitacion.getCodPlancapacitacion(), planCapacitacion.getDescripcion(), planCapacitacion.getFechaVenc(), 
                    planCapacitacion.getEstado(), null, planCapacitacion.getMeta()
            );            
            
            GestorPlanCapacitacion gestorPlancapacitacion= new GestorPlanCapacitacion();
            gestorPlancapacitacion.almacenarPlancapacitacion(plcapacitacion);            
            this.cargarPlancapacitacion();
            UtilMSG.addSuccessMsg("Plan Capacitacion almacenado correctamente.");
            UtilJSF.setBean("varPlanCapacitacion", plcapacitacion, UtilJSF.SESSION_SCOPE);
            this.limpiarPlanCapacitacion();
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }        
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
    
    public void cargarPlancapacitacion() {
        try {        
            Evaluacion ev= (Evaluacion) UtilJSF.getBean("evaluacion");             
            this.planCapacitacionList = new ArrayList<>();
            GestorPlanCapacitacion gestorPlanCapacitacion= new GestorPlanCapacitacion();
            this.planCapacitacionList.addAll((Collection<? extends PlanCapacitacion>) gestorPlanCapacitacion.cargarPlancapacitacionList(ev.getEstablecimiento().getCodigoEstablecimiento()));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public List<EvaluacionCapacitacionDetalle> getEvaluacionCapacitacionDetalles() {
        return evaluacionCapacitacionDetalles;
    }

    public void setEvaluacionCapacitacionDetalles(List<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles) {
        this.evaluacionCapacitacionDetalles = evaluacionCapacitacionDetalles;
    }
    
    public void subirItemPlancapacitacion() {
        planCapacitacion = (PlanCapacitacion) UtilJSF.getBean("varPlanCapacitacion");
        UtilJSF.setBean("planCapacitacion", planCapacitacion, UtilJSF.SESSION_SCOPE);         
    }
    
    public void limpiarPlanCapacitacion() throws Exception {
        cargarPlancapacitacion();
        planCapacitacion= new PlanCapacitacion();
    }    

    public List<PlanCapacitacion> getPlanCapacitacionList() {
        return planCapacitacionList;
    }

    public void setPlanCapacitacionList(List<PlanCapacitacion> planCapacitacionList) {
        this.planCapacitacionList = planCapacitacionList;
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

    public PlanCapacitacion getPlanCapacitacion() {
        return planCapacitacion;
    }

    public void setPlanCapacitacion(PlanCapacitacion planCapacitacion) {
        this.planCapacitacion = planCapacitacion;
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

    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
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

    public List<Establecimiento> getEstablecimientoList() {
        return establecimientoList;
    }

    public void setEstablecimientoList(List<Establecimiento> establecimientoList) {
        this.establecimientoList = establecimientoList;
    }

    public List<Establecimiento> getEstablecimientoListSeleccionado() {
        return establecimientoListSeleccionado;
    }

    public boolean isVolverActivo() {
        return volverActivo;
    }

    public void setVolverActivo(boolean volverActivo) {
        this.volverActivo = volverActivo;
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

    public boolean isFiltroActivo() {
        return filtroActivo;
    }

    public void setFiltroActivo(boolean filtroActivo) {
        this.filtroActivo = filtroActivo;
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
    
    
    
    
    
    
}

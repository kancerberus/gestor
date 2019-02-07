/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;


import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.Recursos;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.gestor.controlador.GestorEvaluacionCapacitacion;
import com.gestor.modelo.Sesion;
import com.gestor.publico.ListaDetalle;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.Responsable;
import com.gestor.publico.controlador.GestorLista;
import com.gestor.publico.controlador.GestorObjetivo;
import com.gestor.publico.controlador.GestorPrograma;
import com.gestor.publico.controlador.GestorResponsable;
import com.gestor.seguimiento.controlador.GestorPlanTrabajo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private PlanTrabajo plantrabajo;     
    private PlanTrabajoActividad plantrabajoActividad;     
    private PlanTrabajoObjetivo objetivo;
    private PlanTrabajoPrograma programa;  
    private List<Responsable> responsables = new ArrayList<>();
    private List<Recursos> recursos = new ArrayList<>();
    private ListaDetalle listadetalle= new ListaDetalle();
    
    
    private List<PlanTrabajoActividad> planTrabajoactividadmetaList= new ArrayList<>();    
    
    private GestorObjetivo gestorObjetivo;
    private GestorPrograma gestorPrograma;
    private GestorPlanTrabajo gestorPlanTrabajo;    
    private GestorLista gestorLista;
        
    private List<PlanTrabajoObjetivo> objetivos = new ArrayList<>();    
    private List<PlanTrabajoObjetivo> objetivoList= new ArrayList<>();    
    private List<PlanTrabajoPrograma> programaList= new ArrayList<>();            
    private List<ListaDetalle> listadetalles= new ArrayList<>();
    
    private boolean guardarActivo = false;
    private boolean nuevoActivo = true;
    private boolean consultarActivo = false;
    private boolean cancelarActivo = false;
    private boolean eliminarActivo = false;
    private Boolean filtroActivo = Boolean.TRUE;
    
    private Evaluacion evaluacion = new Evaluacion();
    
    
    private List<Evaluacion> evaluacionList = new ArrayList<>();   
    
    public UIPlanTrabajo(){  
        
    }    
    
    @PostConstruct
    public void init() {                   
        plantrabajo = new PlanTrabajo();        
        plantrabajoActividad = new PlanTrabajoActividad();        
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
            plantrabajo.setPeso(100);
            plantrabajo.setEstado("A");
            
            PlanTrabajo pltrabajo = new PlanTrabajo( evaluacion.getEvaluacionPK().getCodigoEstablecimiento(), plantrabajo.getCodPlantrabajo(), plantrabajo.getDescripcion(), plantrabajo.getFechaVenc(), plantrabajo.getPeso(), plantrabajo.getMeta(), 
                    plantrabajo.getEstado(), null
            );            
            
            GestorPlanTrabajo gestorPlantrabajo= new GestorPlanTrabajo();
            gestorPlantrabajo.almacenarPlantrabajo(pltrabajo);            
            this.cargarPlantrabajo();
            UtilMSG.addSuccessMsg("Plan Trabajo almacenado correctamente.");
            UtilJSF.setBean("varPlantrabajo", plantrabajo, UtilJSF.SESSION_SCOPE);
            
            
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
            objetivo = (PlanTrabajoObjetivo) UtilJSF.getBean("objetivo");          
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
            
            objetivo = (PlanTrabajoObjetivo) UtilJSF.getBean("objetivo");                
            UtilJSF.setBean("varObjetivo", objetivo, UtilJSF.SESSION_SCOPE);  
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
            
            this.cargarPrograma();  
            this.limpiarPrograma();
            UtilMSG.addSuccessMsg("Programa almacenado correctamente.");
            UtilJSF.setBean("programa", new PlanTrabajoPrograma(), UtilJSF.SESSION_SCOPE);            

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
            objetivo = (PlanTrabajoObjetivo) UtilJSF.getBean("varObjetivo");                
            UtilJSF.setBean("objetivo", objetivo, UtilJSF.SESSION_SCOPE);                        
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
                                   
            responsables = new ArrayList<>();
            List<String> condicionesConsulta = new ArrayList<>();
            condicionesConsulta.add(App.CONDICION_WHERE);
            condicionesConsulta.add(Responsable.RESPONSABLE_CONDICION_CODIGO_ESTABLECIMIENTO.replace("?", String.valueOf(p.getCodEstablecimiento())));
            responsables.addAll(gestorResponsable.cargarListaResponsable(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO) 
            ));
            
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
            plantrabajoActividad = (PlanTrabajoActividad) UtilJSF.getBean("varPlantrabajoactividad");
            plantrabajoActividad.setEstado("C");
            
            PlanTrabajoActividad pta = new PlanTrabajoActividad(plantrabajoActividad.getCodEstablecimiento(),
                    plantrabajoActividad.getCodPlantrabajo(), plantrabajoActividad.getCodActividad(), plantrabajoActividad.getCodObjetivo(), 
                    plantrabajoActividad.getCodPrograma(), plantrabajoActividad.getCedula(), plantrabajoActividad.getCodRecursos(),
                    plantrabajoActividad.getDescripcion(), plantrabajoActividad.getFechaVenc(), plantrabajoActividad.getEstado(), 
                    null
            );
            gestorPlanTrabajo=new GestorPlanTrabajo();
            gestorPlanTrabajo.almacenarPlantrabajoactividad(pta);
            UtilMSG.addSuccessMsg("Actividad cerrada correctamente.");
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
                    objetivo.getCodObjetivo(), plantrabajoActividad.getPrograma().getCodPrograma(), plantrabajoActividad.getResponsable().getCedula(), plantrabajoActividad.getRecursos().getCodRecursos(), plantrabajoActividad.getDescripcion(), plantrabajoActividad.getFechaVenc(),
                    plantrabajoActividad.getEstado(), null);
            
            GestorPlanTrabajo gestorPlantrabajo= new GestorPlanTrabajo();
            gestorPlantrabajo.almacenarPlantrabajoactividad(pta);
            
            UtilMSG.addSuccessMsg("Actividad almacenada correctamente.");
            UtilJSF.setBean("varPlantrabajoactividad", plantrabajoActividad, UtilJSF.SESSION_SCOPE);            
            this.cargarPlantrabajoactividad();            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }

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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias;


import com.gestor.controller.GestorGeneral;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.modelo.Sesion;
import com.gestor.planemergencias.conrolador.GestorPlanEmergencias;
import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Usuarios;
import com.gestor.publico.controlador.GestorCentroTrabajo;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.chart.PieChartModel;


/**
 *
 * @author fjvc
 */
@ManagedBean(name = "uiPlanEmergencias")
@SessionScoped
public class UIPlanEmergencias implements Serializable{
    
    private Establecimiento establecimiento = new Establecimiento();    
    public static final String COMPONENTES_REFRESCAR = "";
    
    private boolean guardarActivo = true;
    private boolean nuevoActivo = false;
    private boolean consultarActivo = false;
    private boolean cancelarActivo = true;
    private boolean eliminarActivo = false;
    private boolean volverActivo = false;
    private Boolean modificarActivo = Boolean.FALSE;
    private Boolean filtroActivo = Boolean.TRUE;    
    
    private List<Establecimiento> establecimientoList = new ArrayList<>();    
    private List<Usuarios> usuariosList = new ArrayList<>();    
    private List<Evaluacion> evaluacionList = new ArrayList<>();   
    private List<PlanEmergencia> planEmergenciasList = new ArrayList<>();   
    private List<Vulnerabilidad> vulnerabilidadList = new ArrayList<>();  
    private List<RelVulnerabilidadTipo> relvulnerabilidadTipoList = new ArrayList<>();  
    private List<RelOriegenTipo> relOrigenTiposList = new ArrayList<>();  
    private List<CuestionarioVulnerabilidad> cuestionarioVulnerabilidadList = new ArrayList<>();
    private List<CuestionarioVulnerabilidad> cuestionarioVulnerabilidadAnalisisVulnerabilidadList = new ArrayList<>();
    private List<CentroTrabajo> centrostrabajo = new ArrayList<>();    
    private List<Probabilidad> probabilidades = new ArrayList<>();    
    private List<TipoOrigen> tiposOrigen = new ArrayList<>();
    private List<RelOriegenTipo> relOrigenesTipo= new ArrayList<>();
    private List<Gravedad> gravedades= new ArrayList<>();
    private List<Float> calificaciones= new ArrayList<>();
    private List<Amenaza> amenazasList=new ArrayList<>();
    
    private AnalisisAmenazas selectedAnalisisAmenasa;
    private AnalisisVulnerabilidad selectedAnalisisVulnerabilidadPersonas;
    private AnalisisVulnerabilidad selectedAnalisisVulnerabilidadRecursos;
    private AnalisisVulnerabilidad selectedAnalisisVulnerabilidadSistemas;
    private RelOriegenTipo selectedOrigenTipo;
    private Amenaza amenaza;
    private PieChartModel pieDeterminacionNivelRiesgo;
    
    private ArrayList<AnalisisAmenazas> analisisAmenasasList=new ArrayList<>();
    private ArrayList<AnalisisVulnerabilidad> analisisVulnerabilidadPersonasList=new ArrayList<>();
    private ArrayList<AnalisisVulnerabilidad> analisisVulnerabilidadRecursosList=new ArrayList<>();
    private ArrayList<AnalisisVulnerabilidad> analisisVulnerabilidadSistemasList=new ArrayList<>();
    private ArrayList<AnalisisVulnerabilidadResultados> resultadoAnalisisVulnerabilidadList=new ArrayList<>();
    private ArrayList<DeterminacionNivelRiesgo> determinacionNivelRiesgoList=new ArrayList<>();
    private ArrayList<AnalisisVulnerabilidad> analisisVulnerabilidadList=new ArrayList<>();
    private ArrayList<AnalisisVulnerabilidadResultados> calculoAnalisisVulnerabilidadResultadosList=new ArrayList<>();
    private ArrayList<RelDeterminacionNivelRiesgo> relDetNivelRiesgoList=new ArrayList<>();
    
    private GestorEstablecimiento gestorEstablecimiento;
    private GestorPlanEmergencias gestorPlEmergencias;            
    private GestorGeneral gestorGeneral;
    
    
    private Evaluacion evaluacion = new Evaluacion();
    private Vulnerabilidad vulnerabilidad =new Vulnerabilidad();
    private CuestionarioVulnerabilidad cuestionarioVulnerabilidad= new CuestionarioVulnerabilidad();
    private RelVulnerabilidadTipo relVulnerabilidadTipo=new RelVulnerabilidadTipo();
    private CentroTrabajo centrotrabajo = new CentroTrabajo();    
    private PlanEmergencia planEmergencia= new PlanEmergencia();
    private AnalisisAmenazas analisisAmenazas=new AnalisisAmenazas();
    private AnalisisVulnerabilidad analisisVulnerabilidad=new AnalisisVulnerabilidad();
    private AnalisisVulnerabilidadResultados analisisVulnerabilildadResultados=new AnalisisVulnerabilidadResultados();
    private DeterminacionNivelRiesgo detNivelRiesgo=new DeterminacionNivelRiesgo();
    private RelDeterminacionNivelRiesgo relDetNivelRiesgo=new RelDeterminacionNivelRiesgo();
    private AnalisisVulnerabilidadResultados analisisVulnerabilildadResultadosTotal=new AnalisisVulnerabilidadResultados();
    private RelOriegenTipo origen=new RelOriegenTipo();
    private Probabilidad probabilidad=new Probabilidad();
    
    @PostConstruct
    public void init() {
        this.cargarCuestionarioVulnerabilidades();             
    }
    
    public UIPlanEmergencias(){ 
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            GestorUsuario gestorUsuario = new GestorUsuario();            
            
            establecimientoList = new ArrayList<>();
            establecimientoList.addAll(s.getEstablecimientoList());
    
            usuariosList = new ArrayList<>();
            usuariosList.addAll(gestorUsuario.cargarListaUsuarios());
            
            gestorPlEmergencias=new GestorPlanEmergencias();
            vulnerabilidadList.addAll(gestorPlEmergencias.cargarVulnerabilidades());            
            pieDeterminacionNivelRiesgo=new PieChartModel();            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    } 
    
    public void cargarRelVulneravilidadTipo() {
        try {                       
            gestorPlEmergencias=new GestorPlanEmergencias();  
            relvulnerabilidadTipoList=new ArrayList<>();
            relvulnerabilidadTipoList.addAll(gestorPlEmergencias.cargarVulnerabilidadTipos(cuestionarioVulnerabilidad.getVulnerabilidad().getCodVulnerabilidad()));                                    
        } catch (Exception e) {            
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarRelVulneravilidadTipoAnalisisVulnerabilidad() {
        try {           
            gestorPlEmergencias=new GestorPlanEmergencias();  
            relvulnerabilidadTipoList=new ArrayList<>();
            relvulnerabilidadTipoList.addAll(gestorPlEmergencias.cargarVulnerabilidadTipos(analisisVulnerabilidad.getVulnerabilidad().getCodVulnerabilidad()));                        
            
        } catch (Exception e) {            
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarProbabilidades() {
        try {                        
            gestorPlEmergencias=new GestorPlanEmergencias();  
            probabilidades=new ArrayList<>();
            probabilidades.addAll(gestorPlEmergencias.cargarProbabilidades());                        
        } catch (Exception e) {            
            UtilLog.generarLog(this.getClass(), e);
        }
    }    
    
    public void cargarTiposOrigen() {
        try {                        
            gestorPlEmergencias=new GestorPlanEmergencias();  
            tiposOrigen=new ArrayList<>();
            tiposOrigen.addAll(gestorPlEmergencias.cargarTiposOrigen());  
            
            if(analisisAmenazas.getTipoOrigen() != null){
                relOrigenesTipo=new ArrayList<>();
                relOrigenesTipo.addAll(gestorPlEmergencias.cargarOrigenes(analisisAmenazas.getTipoOrigen().getCodTipoOrigen()));                                        
            }
        } catch (Exception e) {            
            UtilLog.generarLog(this.getClass(), e);
        }
    } 
    
    public void cargarRelOrigenTiposList() {
        try {                        
            gestorPlEmergencias=new GestorPlanEmergencias();  
            relOrigenTiposList=new ArrayList<>();
            relOrigenTiposList.addAll(gestorPlEmergencias.cargarOrigenesTipoList());
        } catch (Exception e) {            
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarGravedades() {
        try {                        
            gestorPlEmergencias=new GestorPlanEmergencias();  
            gravedades=new ArrayList<>();
            gravedades.addAll(gestorPlEmergencias.cargarGravedades());                        
        } catch (Exception e) {            
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarCuestionarioVulnerabilidades(){
        try {
            cuestionarioVulnerabilidadList=new ArrayList<>();
            gestorPlEmergencias=new GestorPlanEmergencias();            
            cuestionarioVulnerabilidadList.addAll(gestorPlEmergencias.cargarCuestionarioVulnerabilidades());            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarCuestionarioVulnerabilidadesTipo(){
        try {            
            cuestionarioVulnerabilidadAnalisisVulnerabilidadList=new ArrayList<>();
            gestorPlEmergencias=new GestorPlanEmergencias();
            cuestionarioVulnerabilidadAnalisisVulnerabilidadList.addAll(gestorPlEmergencias.cargarCuestionarioVulnerabilidadTipo(analisisVulnerabilidad.getVulnerabilidad().getCodVulnerabilidad(), analisisVulnerabilidad.getVulnerabilidadTipo().getCodVulnerabilidadTipo()));
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarPlanesEmergencias(){
        try {
            planEmergenciasList=new ArrayList<>();
            gestorPlEmergencias=new GestorPlanEmergencias();
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("evaluacion");                  
            planEmergenciasList.addAll(gestorPlEmergencias.cargarPlanesEmergencias(ev.getEvaluacionPK().getCodigoEstablecimiento()));
            GestorCentroTrabajo gestorCentrotrabajo = new GestorCentroTrabajo();
            centrostrabajo= new ArrayList<>();
            centrostrabajo.addAll((Collection<? extends CentroTrabajo>) gestorCentrotrabajo.cargarListaCentrosTrabajoVulnerabilidades(ev.getEstablecimiento().getCodigoEstablecimiento()));
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarAnalisisVulnerabilidades(){
        try {                                   
            analisisVulnerabilidadPersonasList=new ArrayList<>();
            analisisVulnerabilidadRecursosList=new ArrayList<>();
            analisisVulnerabilidadSistemasList=new ArrayList<>();
            gestorPlEmergencias=new GestorPlanEmergencias();
            analisisVulnerabilidadPersonasList.addAll((Collection<? extends AnalisisVulnerabilidad>) gestorPlEmergencias.cargarAnalisisVulnerabilidadesPersonasCentroTrabajo(planEmergencia));
            analisisVulnerabilidadRecursosList.addAll((Collection<? extends AnalisisVulnerabilidad>) gestorPlEmergencias.cargarAnalisisVulnerabilidadesRecursosCentroTrabajo(planEmergencia));
            analisisVulnerabilidadSistemasList.addAll((Collection<? extends AnalisisVulnerabilidad>) gestorPlEmergencias.cargarAnalisisVulnerabilidadesSistemasCentroTrabajo(planEmergencia));            
            analisisVulnerabilidadList.addAll(analisisVulnerabilidadPersonasList);
            analisisVulnerabilidadList.addAll(analisisVulnerabilidadRecursosList);
            analisisVulnerabilidadList.addAll(analisisVulnerabilidadSistemasList);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void seleccionarAmenazaNivelRiesgo(){
        try {
            
            planEmergencia= (PlanEmergencia) UtilJSF.getBean("planEmergencia");
            gestorPlEmergencias=new GestorPlanEmergencias();            
            relDetNivelRiesgoList=new ArrayList<>();            
            relDetNivelRiesgoList.addAll((Collection<? extends RelDeterminacionNivelRiesgo>) gestorPlEmergencias.cargarRelDeterminacionNivelRiesgo(planEmergencia.getCodPlanEmergencia(),selectedOrigenTipo));
            
            detNivelRiesgo=new DeterminacionNivelRiesgo();
            detNivelRiesgo.setPromedio(relDetNivelRiesgoList.get(0).getDeterminacionNivelRiesgo().getPromedio());
            detNivelRiesgo.setNivelRiesgo(relDetNivelRiesgoList.get(0).getDeterminacionNivelRiesgo().getNivelRiesgo());
            
            
            pieDeterminacionNivelRiesgo.clear();
            pieDeterminacionNivelRiesgo.set("Personas", 25);
            pieDeterminacionNivelRiesgo.set("Recursos", 25);
            pieDeterminacionNivelRiesgo.set("Sistemas", 25);
            pieDeterminacionNivelRiesgo.set("Amenazas", 25);
            
            String color="";
            String cAmenaza="";
            for(int i=0;i<=relDetNivelRiesgoList.size()-1;i++){
                if(relDetNivelRiesgoList.get(i).getCodVulnerabilidadTipo()==99){                    
                   color+=relDetNivelRiesgoList.get(i-1).getColor().replace("#", "")+",";                                                                             
                   
                   if(relDetNivelRiesgoList.get(i).getDeterminacionNivelRiesgo().getPromedio()<1.5){
                       cAmenaza="669900";
                   }
                   if(relDetNivelRiesgoList.get(i).getDeterminacionNivelRiesgo().getPromedio()>=1.5 && relDetNivelRiesgoList.get(i).getDeterminacionNivelRiesgo().getPromedio()<3){
                       cAmenaza="f9f900";
                   }
                   if(relDetNivelRiesgoList.get(i).getDeterminacionNivelRiesgo().getPromedio()>=3){
                       cAmenaza="DF0101";
                   }
                }
            }
            
            pieDeterminacionNivelRiesgo.setSeriesColors(color+cAmenaza);                                    
            pieDeterminacionNivelRiesgo.setDiameter(200);
            pieDeterminacionNivelRiesgo.setLegendEscapeHtml(true);
            pieDeterminacionNivelRiesgo.setDataLabelFormatString("%s");            
            pieDeterminacionNivelRiesgo.setLegendPosition("w");
            pieDeterminacionNivelRiesgo.setLegendCols(1);    
            pieDeterminacionNivelRiesgo.setShadow(true);
            pieDeterminacionNivelRiesgo.setSliceMargin(20);
            //pieDeterminacionNivelRiesgo = gestorPlEmergencias.pieDeterminacionNivelRiesgo(planEmergencia.getCodPlanEmergencia(),selectedOrigenTipo);            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void guardarAnalisisAmenazasList(){
        try {          
            planEmergencia= (PlanEmergencia) UtilJSF.getBean("planEmergencia");            
            analisisAmenazas.setRelOrigenTipo(origen);
            gestorPlEmergencias=new GestorPlanEmergencias();
            gestorGeneral=new GestorGeneral();
            
            Long codAnalisisAmenaza=gestorGeneral.nextval(GestorGeneral.ANALISIS_AMENAZAS_COD_ANAILIS_AMENAZA_SEQ);
            analisisAmenazas.setCodAnalisisAmenaza(codAnalisisAmenaza.intValue());
            analisisAmenazas.setCodigoEstablecimiento(planEmergencia.getCodigoEstablecimiento());
            analisisAmenazas.setCodCentroTrabajo(planEmergencia.getCodCentroTrabajo());
            analisisAmenazas.setCodPlanEmergencia(planEmergencia.getCodPlanEmergencia());            
            
            analisisAmenasasList.add(analisisAmenazas);
            analisisAmenazas=new AnalisisAmenazas();
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }        
    }
    
    public void guardarAnalisisVulnerabilidadesList(){
        try {
            planEmergencia= (PlanEmergencia) UtilJSF.getBean("planEmergencia");
            gestorGeneral=new GestorGeneral();
            
            analisisVulnerabilidad.getCuestionarioVulnerabilidad().setVulnerabilidad(analisisVulnerabilidad.getVulnerabilidad());            
            analisisVulnerabilidad.setCodigoEstablecimiento(planEmergencia.getCodigoEstablecimiento());
            analisisVulnerabilidad.setCodCentroTrabajo(planEmergencia.getCodCentroTrabajo());
            analisisVulnerabilidad.setCodPlanEmergencia(planEmergencia.getCodPlanEmergencia());            
                    
            Long codAnalisisVulnerabilidad=gestorGeneral.nextval(GestorGeneral.ANALISIS_VULNERABILIDAD_COD_ANALISIS_VULNERABILIDAD_SEQ);
            analisisVulnerabilidad.setCodAnalisisVulnerabilidad(codAnalisisVulnerabilidad.intValue());
            
            analisisVulnerabilidadList.add(analisisVulnerabilidad);
            
            if(analisisVulnerabilidad.getVulnerabilidad().getCodVulnerabilidad()==1){
                this.analisisVulnerabilidadPersonasList.add(analisisVulnerabilidad);                
            }
            if(analisisVulnerabilidad.getVulnerabilidad().getCodVulnerabilidad()==2){
                this.analisisVulnerabilidadRecursosList.add(analisisVulnerabilidad);             
            }
            if(analisisVulnerabilidad.getVulnerabilidad().getCodVulnerabilidad()==3){
                this.analisisVulnerabilidadSistemasList.add(analisisVulnerabilidad);             
            }            
            analisisVulnerabilidad=new AnalisisVulnerabilidad();            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }        
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);            
        }
        
    }
    
    public void onRowUnselect(UnselectEvent event) {        
            selectedAnalisisVulnerabilidadPersonas=new AnalisisVulnerabilidad();
            selectedAnalisisVulnerabilidadRecursos=new AnalisisVulnerabilidad();
            selectedAnalisisVulnerabilidadSistemas=new AnalisisVulnerabilidad();
    }
    
    
    public void deleteAnalisisAmenaza() throws Exception {
        try{
            gestorPlEmergencias = new GestorPlanEmergencias();
            
            analisisAmenasasList.remove(selectedAnalisisAmenasa);
            gestorPlEmergencias.eliminarAnalisisAmenaza(selectedAnalisisAmenasa);
            selectedAnalisisAmenasa=null;            
        }
        catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }         
    }
    
    public void deleteAnalisisVulnerabilidad() throws Exception {
        try{            
            
            gestorPlEmergencias = new GestorPlanEmergencias();
            if(selectedAnalisisVulnerabilidadPersonas != null){
                analisisVulnerabilidadPersonasList.remove(selectedAnalisisVulnerabilidadPersonas);            
                gestorPlEmergencias.eliminarAnalisisVulnerabilidad(selectedAnalisisVulnerabilidadPersonas);
            }
            
            if(selectedAnalisisVulnerabilidadRecursos != null){
                analisisVulnerabilidadRecursosList.remove(selectedAnalisisVulnerabilidadRecursos);            
                gestorPlEmergencias.eliminarAnalisisVulnerabilidad(selectedAnalisisVulnerabilidadRecursos);
            }
            
            if(selectedAnalisisVulnerabilidadSistemas != null){
                analisisVulnerabilidadSistemasList.remove(selectedAnalisisVulnerabilidadSistemas);            
                gestorPlEmergencias.eliminarAnalisisVulnerabilidad(selectedAnalisisVulnerabilidadSistemas);
            }
            
            selectedAnalisisVulnerabilidadPersonas=new AnalisisVulnerabilidad();
            selectedAnalisisVulnerabilidadRecursos=new AnalisisVulnerabilidad();
            selectedAnalisisVulnerabilidadSistemas=new AnalisisVulnerabilidad();
        }
        catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
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

    public String subirItemevaluacion() {
        try{              
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("varEvaluacion");      
            UtilJSF.setBean("evaluacion", ev , UtilJSF.SESSION_SCOPE);
            GestorCentroTrabajo gestorCentrotrabajo = new GestorCentroTrabajo();
            centrostrabajo= new ArrayList<>();
            centrostrabajo.addAll((Collection<? extends CentroTrabajo>) gestorCentrotrabajo.cargarListaCentrosTrabajoVulnerabilidades(ev.getEstablecimiento().getCodigoEstablecimiento()));
            this.cargarPlanesEmergencias();            
            return ("/plan-emergencias/crear-plan-emergencias.xhtml?faces-redirect=true");
        }catch(Exception e){
            return ("/plan-emergencias/crear-plan-emergencias.xhtml?faces-redirect=true");
        }
    }
    
    public void crearPlanEmergencia(){
        try {
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("evaluacion");      
            gestorPlEmergencias=new GestorPlanEmergencias();
            gestorGeneral=new GestorGeneral();
            
            Long codPlanEmergencias=gestorGeneral.nextval(GestorGeneral.PLAN_EMERGENCIA_COD_PLAN_EMERGENCIA_SEQ);
            planEmergencia.setCodPlanEmergencia(codPlanEmergencias.intValue());
            
            PlanEmergencia pe= new PlanEmergencia(planEmergencia.getCodPlanEmergencia(), planEmergencia.getCentrotrabajo().getCodCentrotrabajo(), ev.getEvaluacionPK().getCodigoEstablecimiento());
            gestorPlEmergencias.almacenarPlanEmergencia(pe);
            this.cargarPlanesEmergencias();
            UtilMSG.addSuccessMsg("Plan Emergencia almacenado correctamente.");
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }        
    } 

    
    public void cargarAnalisisAmenazaCentroTrabajo(){
        try {            
            gestorPlEmergencias = new GestorPlanEmergencias();
            resultadoAnalisisVulnerabilidadList=new ArrayList<>();            
            analisisAmenasasList.addAll(gestorPlEmergencias.cargarAnalisisAmenazaCentroTrabajo(planEmergencia));            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void dialogoAnalisisAmenazas() {
        try {
            
            analisisAmenazas=new AnalisisAmenazas();
            analisisAmenasasList=new ArrayList<>();            
            planEmergencia= (PlanEmergencia) UtilJSF.getBean("varPlanEmergencias");
            UtilJSF.setBean("planEmergencia", planEmergencia, UtilJSF.SESSION_SCOPE);    
            this.cargarAnalisisAmenazaCentroTrabajo();
            this.cargarTiposOrigen();            
            this.cargarProbabilidades();
            this.cargarGravedades();            
            Dialogo dialogo = new Dialogo("dialogos/analisis-amenazas.xhtml", "Crear Analisis Amenazas", "clip", Dialogo.WIDTH_80," width:80%;height=100;");
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");            
            this.cargarPlanesEmergencias();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void dialogoAnalisisVulnerabilidades() {
        try {
            cuestionarioVulnerabilidad=new CuestionarioVulnerabilidad();
            analisisVulnerabilidad = new AnalisisVulnerabilidad();            
            analisisVulnerabilidadList = new ArrayList<>();
            selectedAnalisisVulnerabilidadPersonas=new AnalisisVulnerabilidad();
            selectedAnalisisVulnerabilidadRecursos=new AnalisisVulnerabilidad();
            selectedAnalisisVulnerabilidadSistemas=new AnalisisVulnerabilidad();
            analisisVulnerabilidadPersonasList=new ArrayList<>();
            analisisVulnerabilidadRecursosList=new ArrayList<>();
            analisisVulnerabilidadSistemasList=new ArrayList<>();
            
            calificaciones=new ArrayList<>();
            calificaciones.add(0.0f);
            calificaciones.add(0.5f);
            calificaciones.add(1.0f);            
            
            planEmergencia= (PlanEmergencia) UtilJSF.getBean("varPlanEmergencias");
            UtilJSF.setBean("planEmergencia", planEmergencia, UtilJSF.SESSION_SCOPE);
            this.cargarAnalisisVulnerabilidades();            
            Dialogo dialogo = new Dialogo("dialogos/analisis-vulnerabilidades.xhtml", "Crear Analisis Vulnerabilidad", "clip", Dialogo.WIDTH_100," width:80%;height=100;");
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            this.cargarPlanesEmergencias();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void dialogoResultadoAnalisisVulnerabilidades() {
        try {
            planEmergencia= (PlanEmergencia) UtilJSF.getBean("varPlanEmergencias");
            UtilJSF.setBean("planEmergencia", planEmergencia, UtilJSF.SESSION_SCOPE);  
            resultadoAnalisisVulnerabilidadList=new ArrayList<>();
            this.cargaResultadoAnalisisVulnerabilidadList();
            Dialogo dialogo = new Dialogo("dialogos/resultado-vulnerabilidades.xhtml", "Resultados Analisis Vulnerabilidad", "clip", Dialogo.WIDTH_100," width:80%;height=100;");
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            this.cargarPlanesEmergencias(); 
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void dialogoDeterminacionNivelRiesgo() {
        try {            
            planEmergencia= (PlanEmergencia) UtilJSF.getBean("varPlanEmergencias");
            UtilJSF.setBean("planEmergencia", planEmergencia, UtilJSF.SESSION_SCOPE);            
            this.cargarRelOrigenTiposList();
            Dialogo dialogo = new Dialogo("dialogos/determinacion-nivel-riesgo.xhtml", "Determinacion Nivel De Riesgo", "clip", Dialogo.WIDTH_100," width:80%;height=100;");
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");               
            this.cargarPlanesEmergencias();            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    
    public void cargaResultadoAnalisisVulnerabilidadList(){
        try {
            planEmergencia= (PlanEmergencia) UtilJSF.getBean("varPlanEmergencias");
            UtilJSF.setBean("planEmergencia", planEmergencia, UtilJSF.SESSION_SCOPE);  
            resultadoAnalisisVulnerabilidadList=new ArrayList<>();
            gestorPlEmergencias=new GestorPlanEmergencias();
            resultadoAnalisisVulnerabilidadList.addAll(gestorPlEmergencias.cargarAnalisisVulnerabilidadesResultadosList(planEmergencia));            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void subirItemPregunta(){                   
        cuestionarioVulnerabilidad=(CuestionarioVulnerabilidad) UtilJSF.getBean("varCuestionario"); 
        this.cargarRelVulneravilidadTipo();
    }
    
    public void guardarPregunta(){
        try {            
            gestorPlEmergencias=new GestorPlanEmergencias();            
            gestorGeneral=new GestorGeneral();
            
            if(cuestionarioVulnerabilidad.getCodCuestionario()==null){
                Long codCuestionario=gestorGeneral.nextval(GestorGeneral.CUESTIONARIO_VULNERABILIDAD_COD_CUESTIONARIO_SEQ);
                cuestionarioVulnerabilidad.setCodCuestionario(codCuestionario.intValue());
            }
            
            CuestionarioVulnerabilidad cv= new CuestionarioVulnerabilidad( cuestionarioVulnerabilidad.getVulnerabilidad().getCodVulnerabilidad(),cuestionarioVulnerabilidad.getRelVulnerabilidadTipo().getCodVulnerabilidadTipo(), cuestionarioVulnerabilidad.getCodCuestionario(),cuestionarioVulnerabilidad.getNombre());
            gestorPlEmergencias.almacenarPregunta(cv);
            this.cargarCuestionarioVulnerabilidades();
            UtilMSG.addSuccessMsg("Cuestionario almacenado correctamente.");
            cuestionarioVulnerabilidad=new CuestionarioVulnerabilidad();
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
    public void guardarAnalisisAmenaza(){
        try {                                    
            gestorPlEmergencias=new GestorPlanEmergencias();
            gestorGeneral=new GestorGeneral();            
            
            gestorPlEmergencias.almacenarAnalisisAmenazas(analisisAmenasasList);
            UtilMSG.addSuccessMsg("Analisis de amenazas almacenado correctamente.");            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
    public void guardarAnalisisVulnerabilidad(){
        try {                                    
            planEmergencia= (PlanEmergencia) UtilJSF.getBean("planEmergencia");                        
            gestorPlEmergencias=new GestorPlanEmergencias();
            gestorGeneral=new GestorGeneral();
            
            analisisVulnerabilildadResultados=new AnalisisVulnerabilidadResultados();
            resultadoAnalisisVulnerabilidadList=new ArrayList<>();

            gestorPlEmergencias.almacenarAnalisisVulnerabilidades(analisisVulnerabilidadList);
            analisisVulnerabilidadList=new ArrayList<>();
            float prom1=0, prom2=0,prom3=0, prom4=0,prom5=0, prom6=0, prom7=0, prom8=0, prom9=0, prom10=0, prom11=0, prom12=0, prom13=0, prom14=0,prom15=0, prom16=0, prom17=0;            
            float tot1=0,tot2=0,tot3=0,tot4=0,tot5=0,tot6=0,tot7=0,tot8=0,tot9=0,tot10=0,tot11=0,tot12=0,tot13=0,tot14=0,tot15=0,tot16=0,tot17=0;
            Integer vultipo1,vultipo2=0;
            Integer acum=1;
                        
            Integer tamVulnerabilidad;
            
            tamVulnerabilidad = gestorPlEmergencias.cargarvulnerabildadSize(planEmergencia);            
            
            for(int u=0; u<=tamVulnerabilidad-1;u++){   
                int codVul=u+1;                
                Integer tamVulnerabilidadtipo = gestorPlEmergencias.cargarvulnerabildadtipoSize(planEmergencia, codVul);
               for(int k=0; k<=tamVulnerabilidadtipo-1;k++){
                    int codVultipo=k+1;                    
                    analisisVulnerabilidadList.addAll((Collection<? extends AnalisisVulnerabilidad>) gestorPlEmergencias.cargarAnalisisVulnerabilidadesCentroTrabajo(planEmergencia, codVul, codVultipo));            
                    prom1=0; prom2=0;prom3=0; prom4=0;prom5=0; prom6=0; prom7=0; prom8=0; prom9=0; prom10=0; prom11=0; prom12=0; prom13=0; prom14=0;prom15=0; prom16=0; prom17=0;
                    for(int i=0; i<=analisisVulnerabilidadList.size()-1;i++) {
                        analisisVulnerabilildadResultados.setCodPlanEmergencia(analisisVulnerabilidadList.get(i).getCodPlanEmergencia());
                        analisisVulnerabilildadResultados.setCodVulnerabilidad(analisisVulnerabilidadList.get(i).getVulnerabilidad().getCodVulnerabilidad());
                        analisisVulnerabilildadResultados.setCodVulnerabilidadTipo(analisisVulnerabilidadList.get(i).getVulnerabilidadTipo().getCodVulnerabilidadTipo());
                        prom1=((prom1+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif1())/acum);
                        prom2=((prom2+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif2())/acum);
                        prom3=((prom3+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif3())/acum);
                        prom4=((prom4+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif4())/acum);
                        prom5=((prom5+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif5())/acum);
                        prom6=((prom6+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif6())/acum);
                        prom7=((prom7+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif7())/acum);
                        prom8=((prom8+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif8())/acum);
                        prom9=((prom9+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif9())/acum);
                        prom10=((prom10+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif10())/acum);
                        prom11=((prom11+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif11())/acum);
                        prom12=((prom12+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif12())/acum);
                        prom13=((prom13+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif13())/acum);
                        prom14=((prom14+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif14())/acum);
                        prom15=((prom15+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif15())/acum);
                        prom16=((prom16+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif16())/acum);
                        prom17=((prom17+=analisisVulnerabilidadList.get(i).getCuestionarioVulnerabilidad().getCalif17())/acum);                        
                        acum++;
                    }
                    acum=1;
                    if(prom1<3){analisisVulnerabilildadResultados.setProm1(0.0f);}
                    if(prom2<3){analisisVulnerabilildadResultados.setProm2(0.0f);}
                    if(prom3<3){analisisVulnerabilildadResultados.setProm3(0.0f);}
                    if(prom4<3){analisisVulnerabilildadResultados.setProm4(0.0f);}
                    if(prom5<3){analisisVulnerabilildadResultados.setProm5(0.0f);}
                    if(prom6<3){analisisVulnerabilildadResultados.setProm6(0.0f);}
                    if(prom7<3){analisisVulnerabilildadResultados.setProm7(0.0f);}                       
                    if(prom8<3){analisisVulnerabilildadResultados.setProm8(0.0f);}
                    if(prom9<3){analisisVulnerabilildadResultados.setProm9(0.0f);}
                    if(prom10<3){analisisVulnerabilildadResultados.setProm10(0.0f);}
                    if(prom11<3){analisisVulnerabilildadResultados.setProm11(0.0f);}
                    if(prom12<3){analisisVulnerabilildadResultados.setProm12(0.0f);}
                    if(prom13<3){analisisVulnerabilildadResultados.setProm13(0.0f);}
                    if(prom14<3){analisisVulnerabilildadResultados.setProm14(0.0f);}
                    if(prom15<3){analisisVulnerabilildadResultados.setProm15(0.0f);}
                    if(prom16<3){analisisVulnerabilildadResultados.setProm16(0.0f);}
                    if(prom17<3){analisisVulnerabilildadResultados.setProm17(0.0f);}

                    if(prom1 >= 0.3 && prom1 <= 0.5 ){analisisVulnerabilildadResultados.setProm1(0.5f);}else{analisisVulnerabilildadResultados.setProm1(1f);}
                    if(prom2 >= 0.3 && prom2 <= 0.5 ){analisisVulnerabilildadResultados.setProm2(0.5f);}else{analisisVulnerabilildadResultados.setProm2(1f);}
                    if(prom3 >= 0.3 && prom3 <= 0.5 ){analisisVulnerabilildadResultados.setProm3(0.5f);}else{analisisVulnerabilildadResultados.setProm3(1f);}
                    if(prom4 >= 0.3 && prom4 <= 0.5 ){analisisVulnerabilildadResultados.setProm4(0.5f);}else{analisisVulnerabilildadResultados.setProm4(1f);}
                    if(prom5 >= 0.3 && prom5 <= 0.5 ){analisisVulnerabilildadResultados.setProm5(0.5f);}else{analisisVulnerabilildadResultados.setProm5(1f);}
                    if(prom6 >= 0.3 && prom6 <= 0.5 ){analisisVulnerabilildadResultados.setProm6(0.5f);}else{analisisVulnerabilildadResultados.setProm6(1f);}
                    if(prom7 >= 0.3 && prom7 <= 0.5 ){analisisVulnerabilildadResultados.setProm7(0.5f);}else{analisisVulnerabilildadResultados.setProm7(1f);}
                    if(prom8 >= 0.3 && prom8 <= 0.5 ){analisisVulnerabilildadResultados.setProm8(0.5f);}else{analisisVulnerabilildadResultados.setProm8(1f);}
                    if(prom9 >= 0.3 && prom9 <= 0.5 ){analisisVulnerabilildadResultados.setProm9(0.5f);}else{analisisVulnerabilildadResultados.setProm9(1f);}
                    if(prom10 >= 0.3 && prom10 <= 0.5 ){analisisVulnerabilildadResultados.setProm10(0.5f);}else{analisisVulnerabilildadResultados.setProm10(1f);}
                    if(prom11 >= 0.3 && prom11 <= 0.5 ){analisisVulnerabilildadResultados.setProm11(0.5f);}else{analisisVulnerabilildadResultados.setProm11(1f);}
                    if(prom12 >= 0.3 && prom12 <= 0.5 ){analisisVulnerabilildadResultados.setProm12(0.5f);}else{analisisVulnerabilildadResultados.setProm12(1f);}
                    if(prom13 >= 0.3 && prom13 <= 0.5 ){analisisVulnerabilildadResultados.setProm13(0.5f);}else{analisisVulnerabilildadResultados.setProm13(1f);}
                    if(prom14 >= 0.3 && prom14 <= 0.5 ){analisisVulnerabilildadResultados.setProm14(0.5f);}else{analisisVulnerabilildadResultados.setProm14(1f);}
                    if(prom15 >= 0.3 && prom15 <= 0.5 ){analisisVulnerabilildadResultados.setProm15(0.5f);}else{analisisVulnerabilildadResultados.setProm15(1f);}
                    if(prom16 >= 0.3 && prom16 <= 0.5 ){analisisVulnerabilildadResultados.setProm16(0.5f);}else{analisisVulnerabilildadResultados.setProm16(1f);}
                    if(prom17 >= 0.3 && prom17 <= 0.5 ){analisisVulnerabilildadResultados.setProm17(0.5f);}else{analisisVulnerabilildadResultados.setProm17(1f);}
                        
                    if(analisisVulnerabilidadList.size()>1){
                        resultadoAnalisisVulnerabilidadList.add(analisisVulnerabilildadResultados);
                        analisisVulnerabilildadResultados = new AnalisisVulnerabilidadResultados();
                        analisisVulnerabilidadList.clear();
                    }
                    if(analisisVulnerabilidadList.size()==1){
                        resultadoAnalisisVulnerabilidadList.add(analisisVulnerabilildadResultados);
                        analisisVulnerabilildadResultados = new AnalisisVulnerabilidadResultados();
                        analisisVulnerabilidadList.clear();
                    }
                }
            }
            
            int tam=resultadoAnalisisVulnerabilidadList.size()-1;
            analisisVulnerabilildadResultados=new AnalisisVulnerabilidadResultados();
            detNivelRiesgo=new DeterminacionNivelRiesgo();
            int vul1,vul2=1;
            for(int j=0; j<=tam;j++){
                vul1=resultadoAnalisisVulnerabilidadList.get(j).getCodVulnerabilidad();
                if(vul1 != vul2){
                    tot1=0;tot2=0;tot3=0;tot4=0;tot5=0;tot6=0;tot7=0;tot8=0;tot9=0;tot10=0;tot11=0;tot12=0;tot13=0;tot14=0;tot15=0;tot16=0;tot17=0;                    
                    resultadoAnalisisVulnerabilidadList.add(analisisVulnerabilildadResultados);                    
                    analisisVulnerabilildadResultados = new AnalisisVulnerabilidadResultados();
                }
                analisisVulnerabilildadResultados.setCodPlanEmergencia(resultadoAnalisisVulnerabilidadList.get(j).getCodPlanEmergencia());
                analisisVulnerabilildadResultados.setCodVulnerabilidad(resultadoAnalisisVulnerabilidadList.get(j).getCodVulnerabilidad());
                analisisVulnerabilildadResultados.setCodVulnerabilidadTipo(99);
                tot1+=resultadoAnalisisVulnerabilidadList.get(j).getProm1();
                analisisVulnerabilildadResultados.setProm1(tot1);                
                tot2+=resultadoAnalisisVulnerabilidadList.get(j).getProm2();
                analisisVulnerabilildadResultados.setProm2(tot2);                
                tot3+=resultadoAnalisisVulnerabilidadList.get(j).getProm3();
                analisisVulnerabilildadResultados.setProm3(tot3);
                tot4+=resultadoAnalisisVulnerabilidadList.get(j).getProm4();
                analisisVulnerabilildadResultados.setProm4(tot4);
                tot5+=resultadoAnalisisVulnerabilidadList.get(j).getProm5();
                analisisVulnerabilildadResultados.setProm5(tot5);
                tot6+=resultadoAnalisisVulnerabilidadList.get(j).getProm6();
                analisisVulnerabilildadResultados.setProm6(tot6);
                tot7+=resultadoAnalisisVulnerabilidadList.get(j).getProm7();
                analisisVulnerabilildadResultados.setProm7(tot7);
                tot8+=resultadoAnalisisVulnerabilidadList.get(j).getProm8();
                analisisVulnerabilildadResultados.setProm8(tot8);
                tot9+=resultadoAnalisisVulnerabilidadList.get(j).getProm9();
                analisisVulnerabilildadResultados.setProm9(tot9);
                tot10+=resultadoAnalisisVulnerabilidadList.get(j).getProm10();
                analisisVulnerabilildadResultados.setProm10(tot10);
                tot11+=resultadoAnalisisVulnerabilidadList.get(j).getProm11();
                analisisVulnerabilildadResultados.setProm11(tot11);
                tot12+=resultadoAnalisisVulnerabilidadList.get(j).getProm12();
                analisisVulnerabilildadResultados.setProm12(tot12);
                tot13+=resultadoAnalisisVulnerabilidadList.get(j).getProm13();
                analisisVulnerabilildadResultados.setProm13(tot13);
                tot14+=resultadoAnalisisVulnerabilidadList.get(j).getProm14();
                analisisVulnerabilildadResultados.setProm14(tot14);
                tot15+=resultadoAnalisisVulnerabilidadList.get(j).getProm15();
                analisisVulnerabilildadResultados.setProm15(tot15);
                tot16+=resultadoAnalisisVulnerabilidadList.get(j).getProm16();
                analisisVulnerabilildadResultados.setProm16(tot16);
                tot17+=resultadoAnalisisVulnerabilidadList.get(j).getProm17();
                analisisVulnerabilildadResultados.setProm17(tot17); 
                vul2=vul1;
            }
            
            resultadoAnalisisVulnerabilidadList.add(analisisVulnerabilildadResultados);                                    
            analisisVulnerabilildadResultados = new AnalisisVulnerabilidadResultados();                        
            gestorPlEmergencias.almacenarAnalisisVulnerabilidadesResultado(resultadoAnalisisVulnerabilidadList);            
            
            String cadena="prom";            
            Integer tipoOrigen=1;    
            Integer tipoOrigen2=1;
            
            gestorPlEmergencias.eliminarDeterminacionNivelRiesgo(planEmergencia.getCodPlanEmergencia());

            for(int i = 1; i <= 17; i++){
                StringBuilder sb = new StringBuilder(i);
                sb.append(cadena);
                sb.append(i);
                sb.toString();
                detNivelRiesgo=new DeterminacionNivelRiesgo();
                detNivelRiesgo.setCodPlanEmergencia(planEmergencia.getCodPlanEmergencia());
                if(i<=7){
                       detNivelRiesgo.setCodOrigen(1);
                       detNivelRiesgo.setCodTipoOrigen(i);
                }
                if(i>=8&&i<=12){
                    detNivelRiesgo.setCodOrigen(2);
                    detNivelRiesgo.setCodTipoOrigen(tipoOrigen);
                    tipoOrigen++;
                }
                if(i>=13){
                    detNivelRiesgo.setCodOrigen(3);
                    detNivelRiesgo.setCodTipoOrigen(tipoOrigen2);
                    tipoOrigen2++;
                }                    

                Long codDetNivelRiesgo=gestorGeneral.nextval(GestorGeneral.DETERMINACION_NIVEL_RIESGO_COD_DET_NIVEL_RIESGO_SEQ);
                detNivelRiesgo.setCodDetNivelRiesgo(codDetNivelRiesgo.intValue());

                gestorPlEmergencias.almacenarDeterminacionNivelRiesgo(detNivelRiesgo);
                    
                amenazasList=new ArrayList<>();
                amenazasList.addAll(gestorPlEmergencias.cargarPromedioAmenaza(sb,planEmergencia.getCodPlanEmergencia()));
                
                Float totalBajo=0.0f;
                Float totalMedio=0.0f;
                Float totalAlto=0.0f;
                Float total;
                Float prom=0.0f;                
                String nivel="";
                for(int j=0; j <= amenazasList.size()-1;j++){
                    relDetNivelRiesgo = new RelDeterminacionNivelRiesgo();
                    relDetNivelRiesgo.setCodDetNivelRiesgo(detNivelRiesgo.getCodDetNivelRiesgo());
                    relDetNivelRiesgo.setCodVulnerabilidad(amenazasList.get(j).getCodVulnerabilidad());
                    relDetNivelRiesgo.setCodVulnerabilidadTipo(amenazasList.get(j).getCodVulnerabilidadTipo());
                    
                    if(amenazasList.get(j).getProm1() == 0.0){
                        relDetNivelRiesgo.setBajo(0.0f);                        
                    }
                    if(amenazasList.get(j).getProm1() == 0.5){
                        relDetNivelRiesgo.setMedio(0.5f);                        
                    }
                    if(amenazasList.get(j).getProm1() == 1.0){
                        relDetNivelRiesgo.setAlto(1.0f);                        
                    }
                    
                    if(relDetNivelRiesgo.getCodVulnerabilidadTipo()!=99){
                        totalBajo += relDetNivelRiesgo.getBajo();
                        totalMedio += relDetNivelRiesgo.getMedio();
                        totalAlto += relDetNivelRiesgo.getAlto();                        
                    }                    
                    
                    if(relDetNivelRiesgo.getCodVulnerabilidadTipo()==99){
                        relDetNivelRiesgo.setBajo(totalBajo);
                        relDetNivelRiesgo.setMedio(totalMedio);
                        relDetNivelRiesgo.setAlto(totalAlto);
                        total=totalBajo+totalMedio+totalAlto;
                        totalBajo=0.0f;
                        totalMedio=0.0f;
                        totalAlto=0.0f;  
                        relDetNivelRiesgo.setSubtotal(total);
                        
                        relDetNivelRiesgoList=new ArrayList<>();
                        relDetNivelRiesgoList.addAll(gestorPlEmergencias.cargarTiposVulnerabilidad(relDetNivelRiesgo));
                        
                        if( total < 1.5 ){
                            for(int m=0; m<=relDetNivelRiesgoList.size()-1;m++){
                                if(relDetNivelRiesgoList.get(m).getCodVulnerabilidadTipo()==99){
                                    relDetNivelRiesgoList.get(m).setSubtotal(total);
                                }
                                relDetNivelRiesgoList.get(m).setColor("#669900");
                            }
                        }
                        if( total >=1.5 && total<3 ){
                            for(int m=0; m<=relDetNivelRiesgoList.size()-1;m++){
                                if(relDetNivelRiesgoList.get(m).getCodVulnerabilidadTipo()==99){
                                    relDetNivelRiesgoList.get(m).setSubtotal(total);                
                                }
                                
                                relDetNivelRiesgoList.get(m).setColor("#f9f900");
                            }                            
                        }
                        if( total >=3 ){
                            for(int m=0; m<=relDetNivelRiesgoList.size()-1;m++){
                                if(relDetNivelRiesgo.getCodVulnerabilidadTipo()==99){
                                    relDetNivelRiesgoList.get(m).setSubtotal(total);     
                                }
                                
                                relDetNivelRiesgoList.get(m).setColor("#DF0101");                                
                            }
                        }
                        gestorPlEmergencias.updateRelDeterminacionNivelRiesgo(relDetNivelRiesgoList);
                        prom+=total;                        
                    }                    
                        if(prom < 1.5){
                            nivel="Bajo";
                        }
                        if(prom >= 1.5 && prom<3){
                            nivel="Medio";
                        }
                        if(prom >= 3){
                            nivel="Alto";
                        }                        
                    gestorPlEmergencias.updateDetNivelRiesgo(relDetNivelRiesgo.getCodDetNivelRiesgo(), prom, nivel);
                    gestorPlEmergencias.almacenarRelDeterminacionNivelRiesgo(relDetNivelRiesgo);
                }
            }
            
            
            
            UtilMSG.addSuccessMsg("Analisis de Vulnerabilidades almacenado correctamente.");
            selectedAnalisisVulnerabilidadPersonas = new AnalisisVulnerabilidad();
            selectedAnalisisVulnerabilidadRecursos = new AnalisisVulnerabilidad();
            selectedAnalisisVulnerabilidadSistemas = new AnalisisVulnerabilidad();
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }  

    public PieChartModel getPieDeterminacionNivelRiesgo() {
        return pieDeterminacionNivelRiesgo;
    }

    public void setPieDeterminacionNivelRiesgo(PieChartModel pieDeterminacionNivelRiesgo) {
        this.pieDeterminacionNivelRiesgo = pieDeterminacionNivelRiesgo;
    }

    public List<Amenaza> getAmenazasList() {
        return amenazasList;
    }

    public void setAmenazasList(List<Amenaza> amenazasList) {
        this.amenazasList = amenazasList;
    }

    public Amenaza getAmenaza() {
        return amenaza;
    }

    public void setAmenaza(Amenaza amenaza) {
        this.amenaza = amenaza;
    }

    public ArrayList<RelDeterminacionNivelRiesgo> getRelDetNivelRiesgoList() {
        return relDetNivelRiesgoList;
    }

    public void setRelDetNivelRiesgoList(ArrayList<RelDeterminacionNivelRiesgo> relDetNivelRiesgoList) {
        this.relDetNivelRiesgoList = relDetNivelRiesgoList;
    }

    public RelDeterminacionNivelRiesgo getRelDetNivelRiesgo() {
        return relDetNivelRiesgo;
    }

    public void setRelDetNivelRiesgo(RelDeterminacionNivelRiesgo relDetNivelRiesgo) {
        this.relDetNivelRiesgo = relDetNivelRiesgo;
    }

    public DeterminacionNivelRiesgo getDetNivelRiesgo() {
        return detNivelRiesgo;
    }

    public void setDetNivelRiesgo(DeterminacionNivelRiesgo detNivelRiesgo) {
        this.detNivelRiesgo = detNivelRiesgo;
    }

    public ArrayList<DeterminacionNivelRiesgo> getDeterminacionNivelRiesgoList() {
        return determinacionNivelRiesgoList;
    }

    public void setDeterminacionNivelRiesgoList(ArrayList<DeterminacionNivelRiesgo> determinacionNivelRiesgoList) {
        this.determinacionNivelRiesgoList = determinacionNivelRiesgoList;
    }

    public List<RelOriegenTipo> getRelOrigenTiposList() {
        return relOrigenTiposList;
    }

    public void setRelOrigenTiposList(List<RelOriegenTipo> relOrigenTiposList) {
        this.relOrigenTiposList = relOrigenTiposList;
    }

    public AnalisisVulnerabilidadResultados getAnalisisVulnerabilildadResultadosTotal() {
        return analisisVulnerabilildadResultadosTotal;
    }

    public void setAnalisisVulnerabilildadResultadosTotal(AnalisisVulnerabilidadResultados analisisVulnerabilildadResultadosTotal) {
        this.analisisVulnerabilildadResultadosTotal = analisisVulnerabilildadResultadosTotal;
    }

    public ArrayList<AnalisisVulnerabilidadResultados> getCalculoAnalisisVulnerabilidadResultadosList() {
        return calculoAnalisisVulnerabilidadResultadosList;
    }

    public void setCalculoAnalisisVulnerabilidadResultadosList(ArrayList<AnalisisVulnerabilidadResultados> calculoAnalisisVulnerabilidadResultadosList) {
        this.calculoAnalisisVulnerabilidadResultadosList = calculoAnalisisVulnerabilidadResultadosList;
    }

    public ArrayList<AnalisisVulnerabilidadResultados> getResultadoAnalisisVulnerabilidadList() {
        return resultadoAnalisisVulnerabilidadList;
    }

    public void setResultadoAnalisisVulnerabilidadList(ArrayList<AnalisisVulnerabilidadResultados> resultadoAnalisisVulnerabilidadList) {
        this.resultadoAnalisisVulnerabilidadList = resultadoAnalisisVulnerabilidadList;
    }


    public List<CuestionarioVulnerabilidad> getCuestionarioVulnerabilidadAnalisisVulnerabilidadList() {
        return cuestionarioVulnerabilidadAnalisisVulnerabilidadList;
    }

    public void setCuestionarioVulnerabilidadAnalisisVulnerabilidadList(List<CuestionarioVulnerabilidad> cuestionarioVulnerabilidadAnalisisVulnerabilidadList) {
        this.cuestionarioVulnerabilidadAnalisisVulnerabilidadList = cuestionarioVulnerabilidadAnalisisVulnerabilidadList;
    }

    public List<Float> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Float> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public ArrayList<AnalisisVulnerabilidad> getAnalisisVulnerabilidadPersonasList() {
        return analisisVulnerabilidadPersonasList;
    }

    public void setAnalisisVulnerabilidadPersonasList(ArrayList<AnalisisVulnerabilidad> analisisVulnerabilidadPersonasList) {
        this.analisisVulnerabilidadPersonasList = analisisVulnerabilidadPersonasList;
    }

    public ArrayList<AnalisisVulnerabilidad> getAnalisisVulnerabilidadRecursosList() {
        return analisisVulnerabilidadRecursosList;
    }

    public void setAnalisisVulnerabilidadRecursosList(ArrayList<AnalisisVulnerabilidad> analisisVulnerabilidadRecursosList) {
        this.analisisVulnerabilidadRecursosList = analisisVulnerabilidadRecursosList;
    }

    public ArrayList<AnalisisVulnerabilidad> getAnalisisVulnerabilidadSistemasList() {
        return analisisVulnerabilidadSistemasList;
    }

    public void setAnalisisVulnerabilidadSistemasList(ArrayList<AnalisisVulnerabilidad> analisisVulnerabilidadSistemasList) {
        this.analisisVulnerabilidadSistemasList = analisisVulnerabilidadSistemasList;
    }

    public ArrayList<AnalisisVulnerabilidad> getAnalisisVulnerabilidadList() {
        return analisisVulnerabilidadList;
    }

    public void setAnalisisVulnerabilidadList(ArrayList<AnalisisVulnerabilidad> analisisVulnerabilidadList) {
        this.analisisVulnerabilidadList = analisisVulnerabilidadList;
    }

    public AnalisisVulnerabilidadResultados getAnalisisVulnerabilildadResultados() {
        return analisisVulnerabilildadResultados;
    }

    public void setAnalisisVulnerabilildadResultados(AnalisisVulnerabilidadResultados analisisVulnerabilildadResultados) {
        this.analisisVulnerabilildadResultados = analisisVulnerabilildadResultados;
    }

    public AnalisisVulnerabilidad getAnalisisVulnerabilidad() {
        return analisisVulnerabilidad;
    }

    public void setAnalisisVulnerabilidad(AnalisisVulnerabilidad analisisVulnerabilidad) {
        this.analisisVulnerabilidad = analisisVulnerabilidad;
    }

    public AnalisisVulnerabilidad getSelectedAnalisisVulnerabilidadPersonas() {
        return selectedAnalisisVulnerabilidadPersonas;
    }

    public void setSelectedAnalisisVulnerabilidadPersonas(AnalisisVulnerabilidad selectedAnalisisVulnerabilidadPersonas) {
        this.selectedAnalisisVulnerabilidadPersonas = selectedAnalisisVulnerabilidadPersonas;
    }

    public AnalisisVulnerabilidad getSelectedAnalisisVulnerabilidadRecursos() {
        return selectedAnalisisVulnerabilidadRecursos;
    }

    public void setSelectedAnalisisVulnerabilidadRecursos(AnalisisVulnerabilidad selectedAnalisisVulnerabilidadRecursos) {
        this.selectedAnalisisVulnerabilidadRecursos = selectedAnalisisVulnerabilidadRecursos;
    }

    public RelOriegenTipo getSelectedOrigenTipo() {
        return selectedOrigenTipo;
    }

    public void setSelectedOrigenTipo(RelOriegenTipo selectedOrigenTipo) {
        this.selectedOrigenTipo = selectedOrigenTipo;
    }

    public AnalisisVulnerabilidad getSelectedAnalisisVulnerabilidadSistemas() {
        return selectedAnalisisVulnerabilidadSistemas;
    }

    public void setSelectedAnalisisVulnerabilidadSistemas(AnalisisVulnerabilidad selectedAnalisisVulnerabilidadSistemas) {
        this.selectedAnalisisVulnerabilidadSistemas = selectedAnalisisVulnerabilidadSistemas;
    }

    public AnalisisAmenazas getSelectedAnalisisAmenasa() {
        return selectedAnalisisAmenasa;
    }

    public void setSelectedAnalisisAmenasa(AnalisisAmenazas selectedAnalisisAmenasa) {
        this.selectedAnalisisAmenasa = selectedAnalisisAmenasa;
    }

    public List<TipoOrigen> getTiposOrigen() {
        return tiposOrigen;
    }

    public void setTiposOrigen(List<TipoOrigen> tiposOrigen) {
        this.tiposOrigen = tiposOrigen;
    }

    public List<RelOriegenTipo> getRelOrigenesTipo() {
        return relOrigenesTipo;
    }

    public void setRelOrigenesTipo(List<RelOriegenTipo> relOrigenesTipo) {
        this.relOrigenesTipo = relOrigenesTipo;
    }

    public ArrayList<AnalisisAmenazas> getAnalisisAmenasasList() {
        return analisisAmenasasList;
    }

    public void setAnalisisAmenasasList(ArrayList<AnalisisAmenazas> analisisAmenasasList) {
        this.analisisAmenasasList = analisisAmenasasList;
    }

    public List<Gravedad> getGravedades() {
        return gravedades;
    }

    public void setGravedades(List<Gravedad> gravedades) {
        this.gravedades = gravedades;
    }

    public AnalisisAmenazas getAnalisisAmenazas() {
        return analisisAmenazas;
    }

    public void setAnalisisAmenazas(AnalisisAmenazas analisisAmenazas) {
        this.analisisAmenazas = analisisAmenazas;
    }

    public List<Probabilidad> getProbabilidades() {
        return probabilidades;
    }

    public void setProbabilidades(List<Probabilidad> probabilidades) {
        this.probabilidades = probabilidades;
    }

    public GestorPlanEmergencias getGestorPlEmergencias() {
        return gestorPlEmergencias;
    }

    public void setGestorPlEmergencias(GestorPlanEmergencias gestorPlEmergencias) {
        this.gestorPlEmergencias = gestorPlEmergencias;
    }

    public GestorGeneral getGestorGeneral() {
        return gestorGeneral;
    }

    public void setGestorGeneral(GestorGeneral gestorGeneral) {
        this.gestorGeneral = gestorGeneral;
    }

    public Probabilidad getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Probabilidad probabilidad) {
        this.probabilidad = probabilidad;
    }

    public RelOriegenTipo getOrigen() {
        return origen;
    }

    public void setOrigen(RelOriegenTipo origen) {
        this.origen = origen;
    }

    public List<PlanEmergencia> getPlanEmergenciasList() {
        return planEmergenciasList;
    }

    public void setPlanEmergenciasList(List<PlanEmergencia> planEmergenciasList) {
        this.planEmergenciasList = planEmergenciasList;
    }

    public PlanEmergencia getPlanEmergencia() {
        return planEmergencia;
    }

    public void setPlanEmergencia(PlanEmergencia planEmergencia) {
        this.planEmergencia = planEmergencia;
    }


    public void limpiar(){        
        cuestionarioVulnerabilidad=new CuestionarioVulnerabilidad();
    }

    public CuestionarioVulnerabilidad getCuestionarioVulnerabilidad() {
        return cuestionarioVulnerabilidad;
    }

    public void setCuestionarioVulnerabilidad(CuestionarioVulnerabilidad cuestionarioVulnerabilidad) {
        this.cuestionarioVulnerabilidad = cuestionarioVulnerabilidad;
    }

    public List<Vulnerabilidad> getVulnerabilidadList() {
        return vulnerabilidadList;
    }

    public void setVulnerabilidadList(List<Vulnerabilidad> vulnerabilidadList) {
        this.vulnerabilidadList = vulnerabilidadList;
    }

    public List<RelVulnerabilidadTipo> getRelvulnerabilidadTipoList() {
        return relvulnerabilidadTipoList;
    }

    public void setRelvulnerabilidadTipoList(List<RelVulnerabilidadTipo> relvulnerabilidadTipoList) {
        this.relvulnerabilidadTipoList = relvulnerabilidadTipoList;
    }


    public RelVulnerabilidadTipo getRelVulnerabilidadTipo() {
        return relVulnerabilidadTipo;
    }

    public void setRelVulnerabilidadTipo(RelVulnerabilidadTipo relVulnerabilidadTipo) {
        this.relVulnerabilidadTipo = relVulnerabilidadTipo;
    }

    public CentroTrabajo getCentrotrabajo() {
        return centrotrabajo;
    }

    public void setCentrotrabajo(CentroTrabajo centrotrabajo) {
        this.centrotrabajo = centrotrabajo;
    }

    public Vulnerabilidad getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(Vulnerabilidad vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    public List<CuestionarioVulnerabilidad> getCuestionarioVulnerabilidadList() {
        return cuestionarioVulnerabilidadList;
    }

    public void setCuestionarioVulnerabilidadList(List<CuestionarioVulnerabilidad> cuestionarioVulnerabilidadList) {
        this.cuestionarioVulnerabilidadList = cuestionarioVulnerabilidadList;
    }

    public List<CentroTrabajo> getCentrostrabajo() {
        return centrostrabajo;
    }

    public void setCentrostrabajo(List<CentroTrabajo> centrostrabajo) {
        this.centrostrabajo = centrostrabajo;
    }

    public Boolean getModificarActivo() {
        return modificarActivo;
    }

    public void setModificarActivo(Boolean modificarActivo) {
        this.modificarActivo = modificarActivo;
    }
       
    public String getComponentesRefrescar() {
        return COMPONENTES_REFRESCAR;
    }

    public boolean isVolverActivo() {
        return volverActivo;
    }

    public void setVolverActivo(boolean volverActivo) {
        this.volverActivo = volverActivo;
    }

    public GestorEstablecimiento getGestorEstablecimiento() {
        return gestorEstablecimiento;
    }

    public void setGestorEstablecimiento(GestorEstablecimiento gestorEstablecimiento) {
        this.gestorEstablecimiento = gestorEstablecimiento;
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
    
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
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
    
}

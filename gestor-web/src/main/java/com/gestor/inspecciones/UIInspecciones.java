/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.inspecciones;

import com.gestor.controller.GestorGeneral;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionPlanAccionDetalle;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.inspecciones.controlador.GestorInspecciones;
import com.gestor.modelo.Sesion;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author fjvc
 */
@ManagedBean(name = "uiInspecciones")
@SessionScoped
public class UIInspecciones implements Serializable{
    
    private Establecimiento establecimiento = new Establecimiento();    
    public static final String COMPONENTES_REFRESCAR = "";
    
    private boolean guardarActivo = true;
    private boolean nuevoActivo = false;
    private boolean consultarActivo = false;
    private boolean cancelarActivo = true;
    private boolean eliminarActivo = false;
    private boolean volverActivo = false;
    private String responsable;
    private Usuarios usuariosSeleccionado;
    private CentroTrabajo centroTrabajo=new CentroTrabajo();
    private InspeccionesTipo inspeccionTipo=new InspeccionesTipo();
           
        
    private Boolean modificarActivo = Boolean.FALSE;
    private Boolean filtroActivo = Boolean.TRUE;
                
    private GestorEstablecimiento gestorEstablecimiento;
    private GestorInspecciones gestorInspecciones;
    private InspeccionExtintor selectedExtinguidor=new InspeccionExtintor();
    private InspeccionBotiquin selectedBotiquin=new InspeccionBotiquin();
    private InspeccionProteccionPersonal selectedProteccionPersonal=new InspeccionProteccionPersonal();
    private InspeccionAlmacenBodega selectedAlmacenBodega=new InspeccionAlmacenBodega();
            
    
    private Evaluacion evaluacion = new Evaluacion();
    private InspeccionExtintor inspeccionExtintor=new InspeccionExtintor();    
    private InspeccionBotiquin inspeccionBotiquin=new InspeccionBotiquin();
    private InspeccionAlmacenBodega inspeccionAlmacenBodega=new InspeccionAlmacenBodega();
    private InspeccionProteccionPersonal inspeccionProteccionPersonal=new InspeccionProteccionPersonal();
    
    private ArrayList<InspeccionExtintor> extintoresList=new ArrayList<>();        
    private ArrayList<InspeccionBotiquin> elementosBotiquinList=new ArrayList<>();    
    private ArrayList<ElementosBotiquin> elementosBotiquinListCombo=new ArrayList<>();
    private ArrayList<AlmacenBodegaMetricas> almacenBodegaMetricas=new ArrayList<>();
    private ArrayList<InspeccionAlmacenBodega> inspeccionAlmacenBodegaList=new ArrayList<>();
    private ArrayList<InspeccionProteccionPersonal> inspeccionProteccionPersonalList=new ArrayList<>();
    private ArrayList<MotivoNoUso> motivosNoUsoList=new ArrayList<>();
    
    private List<Evaluacion> evaluacionList = new ArrayList<>();   
    private List<CentroTrabajo> centrosTrabajo = new ArrayList<>();    
    private List<InspeccionesTipo> inspeccionesTipo = new ArrayList<>();    
    private List<RelInspeccionesCentroTrabajo> relInspeccionesCentroTrabajoList=new ArrayList<>();
    
    
    
    @PostConstruct
    public void init() {
        this.cargarElementosBotiquinCombo();
        this.cargarMetricasAlmacenBodega();
    }
    
    public UIInspecciones(){ 
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            GestorUsuario gestorUsuario = new GestorUsuario();      
            selectedExtinguidor=new InspeccionExtintor();   
            this.cargarElementosBotiquinCombo();
        } catch (Exception e) {
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
    
    public String crearInspeccion(){
        try {                  
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            gestorInspecciones.guardarInspeccionCentroTrabajo(e.getEstablecimiento().getCodigoEstablecimiento(), centroTrabajo.getCodCentrotrabajo(),inspeccionTipo.getCodInspeccion());
            
            switch(inspeccionTipo.getCodInspeccion()){
                case 1:
                    inspeccionExtintor.setCentroTrabajo(new CentroTrabajo());
                    inspeccionExtintor.getCentroTrabajo().setNombre(centroTrabajo.getNombre());
                    inspeccionExtintor.getCentroTrabajo().setCodCentrotrabajo(centroTrabajo.getCodCentrotrabajo());
                    inspeccionExtintor.setCodigoEstablecimiento(centroTrabajo.getCodigoEstablecimiento().shortValue());
                    return ("/inspecciones/inspeccion-extintores.xhtml?faces-redirect=true");
                    
                case 2:
                    inspeccionBotiquin.setCentroTrabajo(new CentroTrabajo());
                    inspeccionBotiquin.getCentroTrabajo().setNombre(centroTrabajo.getNombre());
                    inspeccionBotiquin.getCentroTrabajo().setCodCentrotrabajo(centroTrabajo.getCodCentrotrabajo());
                    inspeccionBotiquin.setCodigoEstablecimiento(centroTrabajo.getCodigoEstablecimiento().shortValue());
                    this.cargarElementosBotiquinCombo();
                    return ("/inspecciones/inspeccion-botiquines.xhtml?faces-redirect=true");
                    
                case 3:
                    inspeccionAlmacenBodega.setCentroTrabajo(new CentroTrabajo());
                    inspeccionAlmacenBodega.getCentroTrabajo().setNombre(centroTrabajo.getNombre());
                    inspeccionAlmacenBodega.getCentroTrabajo().setCodCentrotrabajo(centroTrabajo.getCodCentrotrabajo());
                    inspeccionAlmacenBodega.setCodigoEstablecimiento(centroTrabajo.getCodigoEstablecimiento());
                    this.cargarMetricasAlmacenBodega();
                    return ("/inspecciones/inspeccion-almacenamieto-bodega.xhtml?faces-redirect=true");
                    
                case 4:
                    inspeccionProteccionPersonal.setCentroTrabajo(new CentroTrabajo());
                    inspeccionProteccionPersonal.getCentroTrabajo().setNombre(centroTrabajo.getNombre());
                    inspeccionProteccionPersonal.getCentroTrabajo().setCodCentrotrabajo(centroTrabajo.getCodCentrotrabajo());
                    inspeccionProteccionPersonal.setCodigoEstablecimiento(centroTrabajo.getCodigoEstablecimiento());
                    this.cargarMotivoNoUso();
                    return ("/inspecciones/inspeccion-proteccion-personal.xhtml?faces-redirect=true");
                    
                    
                    
            }
            
            
            
            
        } catch (Exception ex) {            
            UtilLog.generarLog(this.getClass(), ex);
        }  
        return null;
    }
    
    public void cargarInspeccionesTipo(){
        try {
            
            gestorInspecciones=new GestorInspecciones();
            inspeccionesTipo=new ArrayList<>();
            inspeccionesTipo.addAll((Collection<? extends InspeccionesTipo>) gestorInspecciones.cargarInspeccionesTipo());
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        
    }
    
    public void cargarCentroTrabajos(){
        try {
            
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorCentroTrabajo gestorCentrotrabajo = new GestorCentroTrabajo();
            centrosTrabajo= new ArrayList<>();
            centrosTrabajo.addAll((Collection<? extends CentroTrabajo>) gestorCentrotrabajo.cargarListaCentrosTrabajoactivos(e.getEstablecimiento().getCodigoEstablecimiento()));            
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    
    
    public void agregarExtinguidor(){
        try {            
            GestorGeneral gestorGeneral=new GestorGeneral();
            
            inspeccionExtintor.setCodSgs("SGSST-F03");
            inspeccionExtintor.setVersion("03");
            inspeccionExtintor.setCodInspeccionExtintor(gestorGeneral.nextval(GestorGeneral.INSPECCION_EXTINTORES_COD_INSPECCION_EXTINTOR_SEQ).intValue());            
            gestorInspecciones.validarInspeccionExtintores(inspeccionExtintor);
            extintoresList.add(inspeccionExtintor);
            inspeccionExtintor=new InspeccionExtintor();
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    
    public void dialogoCrearInspeccion(){
        try {            
            centroTrabajo=new CentroTrabajo();
            inspeccionTipo=new InspeccionesTipo();
            this.cargarCentroTrabajos();
            this.cargarInspeccionesTipo();
            Dialogo dialogo = new Dialogo("dialogos/inspeccion-nueva.xhtml", "Crear Inspeccion", "clip", "30%", Dialogo.EFECTO);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");           
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
        
    }

    public String subirItemevaluacion() {
        try{  
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("varEvaluacion");      
            UtilJSF.setBean("evaluacion", ev , UtilJSF.SESSION_SCOPE);            
            
            this.cargarInspeccionesEstablecimiento();
            return ("/inspecciones/inspecciones-establecimiento.xhtml?faces-redirect=true");
        }catch(Exception e){
        return ("/inspecciones/inspecciones-establecimiento.xhtml?faces-redirect=true");  
        }
    }
    
    public String subirItemInspeccion(){
        try {
            
            extintoresList=new ArrayList<>();       
            elementosBotiquinListCombo=new ArrayList<>();
            inspeccionAlmacenBodegaList=new ArrayList<>();
            
            
            selectedExtinguidor=new InspeccionExtintor();
            gestorInspecciones=new GestorInspecciones();
            RelInspeccionesCentroTrabajo relICt= (RelInspeccionesCentroTrabajo) UtilJSF.getBean("varInspeccionesEstablecimiento");
            UtilJSF.setBean("inspeccionesEstablecimiento", relICt, UtilJSF.SESSION_SCOPE);
            
            switch(relICt.getInspeccionesTipo().getCodInspeccion()){
                case 1:
                    inspeccionExtintor.setCentroTrabajo(new CentroTrabajo());
                    inspeccionExtintor.getCentroTrabajo().setNombre(relICt.getCentroTrabajo().getNombre());
                    inspeccionExtintor.getCentroTrabajo().setCodCentrotrabajo(relICt.getCentroTrabajo().getCodCentrotrabajo());                    
                    inspeccionExtintor.setCodigoEstablecimiento(relICt.getCodigoEstablecimiento().shortValue());
                    extintoresList.addAll(gestorInspecciones.cargarInspeccionExtintoresCentroTrabajo(relICt.getCentroTrabajo().getCodCentrotrabajo().intValue(), relICt.getCodInspeccion(), relICt.getCodigoEstablecimiento().intValue()));
                    if(!extintoresList.isEmpty()){
                        inspeccionExtintor.setCodSgs(extintoresList.get(0).getCodSgs());
                        inspeccionExtintor.setVersion(extintoresList.get(0).getVersion());
                        inspeccionExtintor.setResponsableuno(extintoresList.get(0).getResponsableuno());
                        inspeccionExtintor.setResponsabledos(extintoresList.get(0).getResponsabledos());
                        inspeccionExtintor.setFechaVence(extintoresList.get(0).getFechaVence());                                        
                    }
                    
                    return ("/inspecciones/inspeccion-extintores.xhtml?faces-redirect=true");
                    
                case 2:
                    elementosBotiquinList=new ArrayList<>();
                    inspeccionBotiquin.setCentroTrabajo(new CentroTrabajo());
                    inspeccionBotiquin.getCentroTrabajo().setNombre(relICt.getCentroTrabajo().getNombre());
                    inspeccionBotiquin.getCentroTrabajo().setCodCentrotrabajo(relICt.getCentroTrabajo().getCodCentrotrabajo());                    
                    inspeccionBotiquin.setCodigoEstablecimiento(relICt.getCodigoEstablecimiento().shortValue()); 
                    elementosBotiquinList.addAll(gestorInspecciones.cargarInspeccionBotiquinesCentroTrabajo(relICt.getCentroTrabajo().getCodCentrotrabajo().intValue(),relICt.getCodInspeccion(), relICt.getCodigoEstablecimiento().intValue()));
                    if(!elementosBotiquinList.isEmpty()){
                        inspeccionBotiquin.setCodSgs(elementosBotiquinList.get(0).getCodSgs());
                        inspeccionBotiquin.setVersion(elementosBotiquinList.get(0).getVersion());
                        inspeccionBotiquin.setResponsableuno(elementosBotiquinList.get(0).getResponsableuno());
                        inspeccionBotiquin.setResponsabledos(elementosBotiquinList.get(0).getResponsabledos());
                        inspeccionBotiquin.setFechaInspeccion(elementosBotiquinList.get(0).getFechaInspeccion());                                        
                    }                    
                    this.cargarElementosBotiquinCombo();                    
                    return ("/inspecciones/inspeccion-botiquines.xhtml?faces-redirect=true");
                
                case 3:
                    inspeccionAlmacenBodega.setCentroTrabajo(new CentroTrabajo());
                    inspeccionAlmacenBodega.getCentroTrabajo().setNombre(relICt.getCentroTrabajo().getNombre());
                    inspeccionAlmacenBodega.getCentroTrabajo().setCodCentrotrabajo(relICt.getCentroTrabajo().getCodCentrotrabajo());
                    inspeccionAlmacenBodega.setCodigoEstablecimiento(relICt.getCodigoEstablecimiento());                    
                    inspeccionAlmacenBodega.setVersion("02");
                    inspeccionAlmacenBodegaList.addAll(gestorInspecciones.cargarInspeccionesAlmacenBodega(relICt.getCentroTrabajo().getCodCentrotrabajo(),relICt.getCodInspeccion(), relICt.getCodigoEstablecimiento()));
                    if(!inspeccionAlmacenBodegaList.isEmpty()){
                        inspeccionAlmacenBodega.setVersion("02");
                        inspeccionAlmacenBodega.setCodSgs(inspeccionAlmacenBodegaList.get(0).getCodSgs());
                        inspeccionAlmacenBodega.setVersion(inspeccionAlmacenBodegaList.get(0).getVersion());                        
                        inspeccionAlmacenBodega.setResponsableuno(inspeccionAlmacenBodegaList.get(0).getResponsableuno());
                        inspeccionAlmacenBodega.setResponsabledos(inspeccionAlmacenBodegaList.get(0).getResponsabledos());                        
                    }
                    this.cargarMetricasAlmacenBodega();
                    return ("/inspecciones/inspeccion-almacenamieto-bodega.xhtml?faces-redirect=true");
                    
                case 4:
                    inspeccionProteccionPersonal.setCentroTrabajo(new CentroTrabajo());
                    inspeccionProteccionPersonal.getCentroTrabajo().setNombre(relICt.getCentroTrabajo().getNombre());
                    inspeccionProteccionPersonal.getCentroTrabajo().setCodCentrotrabajo(relICt.getCentroTrabajo().getCodCentrotrabajo());
                    inspeccionProteccionPersonal.setCodigoEstablecimiento(relICt.getCodigoEstablecimiento());                    
                    inspeccionProteccionPersonal.setVersion("02");
                    inspeccionProteccionPersonalList.addAll(gestorInspecciones.cargarInspeccionesProteccionPersonalList(relICt.getCentroTrabajo().getCodCentrotrabajo(),relICt.getCodInspeccion(), relICt.getCodigoEstablecimiento()));
                    if(!inspeccionProteccionPersonalList.isEmpty()){
                        inspeccionProteccionPersonal.setVersion("01");
                        inspeccionProteccionPersonal.setCodSgs(inspeccionProteccionPersonalList.get(0).getCodSgs());
                        inspeccionProteccionPersonal.setVersion(inspeccionProteccionPersonalList.get(0).getVersion());                        
                        inspeccionProteccionPersonal.setResponsableuno(inspeccionProteccionPersonalList.get(0).getResponsableuno());
                        inspeccionProteccionPersonal.setResponsabledos(inspeccionProteccionPersonalList.get(0).getResponsabledos());                        
                    }
                    this.cargarMotivoNoUso();
                    return ("/inspecciones/inspeccion-proteccion-personal.xhtml?faces-redirect=true");
                    
            }
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }   
    
    public void deleteExtinguidor() throws Exception {
        try {
            gestorInspecciones=new GestorInspecciones();
               
        
            extintoresList.remove(selectedExtinguidor);
            gestorInspecciones.eliminarExtinguidor(selectedExtinguidor);
            selectedExtinguidor=new InspeccionExtintor();
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        
        
    }
    
    public void deleteElementoProteccionPersonal(){
        try {
            
            gestorInspecciones=new GestorInspecciones();
            inspeccionProteccionPersonalList.remove(selectedProteccionPersonal);
            gestorInspecciones.eliminarInspeccionProteccionPersonal(selectedProteccionPersonal);
            selectedProteccionPersonal=new InspeccionProteccionPersonal();
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void deleteElemBotiquin() throws Exception {
        try {
            gestorInspecciones=new GestorInspecciones();
               
        
            elementosBotiquinList.remove(selectedBotiquin);
            gestorInspecciones.eliminarBotiquin(selectedBotiquin);
            selectedBotiquin=new InspeccionBotiquin();
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        
        
    }

    public void deleteMetrica() throws Exception {
        try {
            gestorInspecciones=new GestorInspecciones();               
        
            inspeccionAlmacenBodegaList.remove(selectedAlmacenBodega);
            gestorInspecciones.eliminarAlmacenBodegaMetrica(selectedAlmacenBodega);
            selectedAlmacenBodega=new InspeccionAlmacenBodega();
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        
        
    }
    
    public void cargarElementosBotiquinCombo(){
        try {
            elementosBotiquinListCombo=new ArrayList<>();
            gestorInspecciones=new GestorInspecciones();
            //this.cicloList.addAll((Collection<? extends Ciclo>) gestorEstandar.cargarCicloList(numeral));
            this.elementosBotiquinListCombo.addAll((Collection<? extends ElementosBotiquin>) gestorInspecciones.cargarElementosBotiquin());
            UtilJSF.update("formInspeccionBotiquines");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarMetricasAlmacenBodega(){
        try {
            almacenBodegaMetricas=new ArrayList<>();
            gestorInspecciones=new GestorInspecciones();            
            this.almacenBodegaMetricas.addAll((Collection<? extends AlmacenBodegaMetricas>) gestorInspecciones.cargarAlmacenBodegaMetricas());
            UtilJSF.update("formInspeccionAlmacenBodega");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarMotivoNoUso(){
        try {
            motivosNoUsoList=new ArrayList<>();
            gestorInspecciones=new GestorInspecciones();            
            this.motivosNoUsoList.addAll((Collection<? extends MotivoNoUso>) gestorInspecciones.cargarMotivoNoUsoList());
            UtilJSF.update("formInspeccionProteccionPersonal");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarInspeccionesEstablecimiento(){
        try {
            relInspeccionesCentroTrabajoList=new ArrayList<>();
            gestorInspecciones=new GestorInspecciones();
            Evaluacion ev=new Evaluacion();
            ev = (Evaluacion) UtilJSF.getBean("varEvaluacion");   
            if(ev==null){
                ev = (Evaluacion) UtilJSF.getBean("evaluacion");   
            }
            relInspeccionesCentroTrabajoList.addAll(gestorInspecciones.cargarRelInspeccionesEstablecimientoList(ev.getEvaluacionPK().getCodigoEstablecimiento()));
            this.cargarElementosBotiquinCombo();
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    
    
    
    public String cancelar(){
        
        return ("/inspecciones/inspecciones-establecimiento.xhtml?faces-redirect=true");
        
    }
    
    public String guardar() {
        
        try {
            RelInspeccionesCentroTrabajo relICt= (RelInspeccionesCentroTrabajo) UtilJSF.getBean("inspeccionesEstablecimiento");
            if(relICt==null){
                relICt=new RelInspeccionesCentroTrabajo();
                relICt.setInspeccionesTipo(new InspeccionesTipo(inspeccionTipo.getCodInspeccion(),""));
            }
            
       
            switch(relICt.getInspeccionesTipo().getCodInspeccion()){
            
                case 1:                    
                    gestorInspecciones.guardarInspeccionExtintores(extintoresList);
                    extintoresList.clear();                                       
                    this.cargarInspeccionesEstablecimiento();
                    return ("/inspecciones/inspecciones-establecimiento.xhtml?faces-redirect=true");
                    
                case 2:
                    gestorInspecciones.guardarInspeccionBotiquin(elementosBotiquinList);
                    elementosBotiquinList.clear();
                    this.cargarInspeccionesEstablecimiento();
                    return ("/inspecciones/inspecciones-establecimiento.xhtml?faces-redirect=true");
                    
                case 3:
                    gestorInspecciones.guardarInspeccionAlmacenBodega(inspeccionAlmacenBodegaList);
                    inspeccionAlmacenBodegaList.clear();
                    this.cargarInspeccionesEstablecimiento();
                    return ("/inspecciones/inspecciones-establecimiento.xhtml?faces-redirect=true");
                    
                case 4:
                    gestorInspecciones.guardarInspeccionProteccionPersonal(inspeccionProteccionPersonalList);                    
                    inspeccionProteccionPersonalList.clear();
                    this.cargarInspeccionesEstablecimiento();
                    return ("/inspecciones/inspecciones-establecimiento.xhtml?faces-redirect=true");
                    
            }
        
        
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
            UtilMSG.addWarningMsg("Registro No Guardado");
        }
        return null;
    }
    
    public void onRowEditBotiquin() {
        
    }
     
    public void onRowCancelBotiquin() {
        
    }
    
    public void agregarInpseccionProteccionPersonal(){
        try {
            
            gestorInspecciones=new GestorInspecciones();
            GestorGeneral gestorGeneral=new GestorGeneral();            
            
            inspeccionProteccionPersonal.setCodInsProteccionPersonal(gestorGeneral.nextval(GestorGeneral.INSPECCION_PROTECCION_PERSONAL_COD_INSPECCION_PROTECCION_PERSONAL_SEQ).intValue());
            gestorInspecciones.validarInspeccionProteccionPersonal(inspeccionProteccionPersonal);            
            
            
            if(inspeccionProteccionPersonal.getCodInsProteccionPersonal()!=null){
                inspeccionProteccionPersonalList.add(inspeccionProteccionPersonal);
            }
            
            inspeccionProteccionPersonal=new InspeccionProteccionPersonal();
            inspeccionProteccionPersonal.setCodSgs("SGSST-F05");
            inspeccionProteccionPersonal.setVersion("01");
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void agregarElementoBotiquin(){
        try {
            gestorInspecciones=new GestorInspecciones();
            GestorGeneral gestorGeneral=new GestorGeneral();            
            
            inspeccionBotiquin.setCodInspeccionBotiquin(gestorGeneral.nextval(GestorGeneral.INSPECCION_BOTIQUINES_COD_INSPECCION_BOTIQUIN_SEQ).intValue());
            gestorInspecciones.validarInspeccionBotiquin(inspeccionBotiquin);            
            if(inspeccionBotiquin.getCodInspeccionBotiquin()!=null){
                elementosBotiquinList.add(inspeccionBotiquin);
            }
            
            inspeccionBotiquin=new InspeccionBotiquin();
            inspeccionBotiquin.setCodSgs("SGSST-F04");
            inspeccionBotiquin.setVersion("04");
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    
    public void calificarAlmacenBodega(){
        try {
            gestorInspecciones=new GestorInspecciones();
            GestorGeneral gestorGeneral=new GestorGeneral();  
            
            inspeccionAlmacenBodega.setCodInspeccionAlmacenBodega(gestorGeneral.nextval(GestorGeneral.INSPECCION_ALMACEN_BODEGA_COD_INSPECCION_ALMACEN_BODEGA_SEQ).intValue());
            inspeccionAlmacenBodega.setCodSgs("SGSST-F06");
            inspeccionBotiquin.setVersion("02");
            gestorInspecciones.validarInspeccionAlmacenBodega(inspeccionAlmacenBodega);
            
            if(inspeccionAlmacenBodega.getCodInspeccionAlmacenBodega()!=null){
                
                inspeccionAlmacenBodegaList.add(inspeccionAlmacenBodega);                
            }
            
            inspeccionAlmacenBodega=new InspeccionAlmacenBodega();
            inspeccionAlmacenBodega.setCodSgs("SGSST-F06");
            inspeccionBotiquin.setVersion("02");
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public InspeccionProteccionPersonal getSelectedProteccionPersonal() {
        return selectedProteccionPersonal;
    }

    public void setSelectedProteccionPersonal(InspeccionProteccionPersonal selectedProteccionPersonal) {
        this.selectedProteccionPersonal = selectedProteccionPersonal;
    }

    public ArrayList<InspeccionProteccionPersonal> getInspeccionProteccionPersonalList() {
        return inspeccionProteccionPersonalList;
    }

    public void setInspeccionProteccionPersonalList(ArrayList<InspeccionProteccionPersonal> inspeccionProteccionPersonalList) {
        this.inspeccionProteccionPersonalList = inspeccionProteccionPersonalList;
    }

    public ArrayList<MotivoNoUso> getMotivosNoUsoList() {
        return motivosNoUsoList;
    }

    public void setMotivosNoUsoList(ArrayList<MotivoNoUso> motivosNoUsoList) {
        this.motivosNoUsoList = motivosNoUsoList;
    }

    public InspeccionProteccionPersonal getInspeccionProteccionPersonal() {
        return inspeccionProteccionPersonal;
    }

    public void setInspeccionProteccionPersonal(InspeccionProteccionPersonal inspeccionProteccionPersonal) {
        this.inspeccionProteccionPersonal = inspeccionProteccionPersonal;
    }

    public InspeccionAlmacenBodega getSelectedAlmacenBodega() {
        return selectedAlmacenBodega;
    }

    public void setSelectedAlmacenBodega(InspeccionAlmacenBodega selectedAlmacenBodega) {
        this.selectedAlmacenBodega = selectedAlmacenBodega;
    }

    public ArrayList<InspeccionAlmacenBodega> getInspeccionAlmacenBodegaList() {
        return inspeccionAlmacenBodegaList;
    }

    public void setInspeccionAlmacenBodegaList(ArrayList<InspeccionAlmacenBodega> inspeccionAlmacenBodegaList) {
        this.inspeccionAlmacenBodegaList = inspeccionAlmacenBodegaList;
    }

    public ArrayList<AlmacenBodegaMetricas> getAlmacenBodegaMetricas() {
        return almacenBodegaMetricas;
    }

    public void setAlmacenBodegaMetricas(ArrayList<AlmacenBodegaMetricas> almacenBodegaMetricas) {
        this.almacenBodegaMetricas = almacenBodegaMetricas;
    }    
    
    public InspeccionAlmacenBodega getInspeccionAlmacenBodega() {
        return inspeccionAlmacenBodega;
    }

    public void setInspeccionAlmacenBodega(InspeccionAlmacenBodega inspeccionAlmacenBodega) {
        this.inspeccionAlmacenBodega = inspeccionAlmacenBodega;
    }

    public InspeccionBotiquin getSelectedBotiquin() {
        return selectedBotiquin;
    }

    public void setSelectedBotiquin(InspeccionBotiquin selectedBotiquin) {
        this.selectedBotiquin = selectedBotiquin;
    }

    public ArrayList<ElementosBotiquin> getElementosBotiquinListCombo() {
        return elementosBotiquinListCombo;
    }

    public void setElementosBotiquinListCombo(ArrayList<ElementosBotiquin> elementosBotiquinListCombo) {
        this.elementosBotiquinListCombo = elementosBotiquinListCombo;
    }
    
    public InspeccionBotiquin getInspeccionBotiquin() {
        return inspeccionBotiquin;
    }

    public void setInspeccionBotiquin(InspeccionBotiquin inspeccionBotiquin) {
        this.inspeccionBotiquin = inspeccionBotiquin;
    }
    
    public InspeccionExtintor getSelectedExtinguidor() {
        return selectedExtinguidor;
    }

    public void setSelectedExtinguidor(InspeccionExtintor selectedExtinguidor) {
        this.selectedExtinguidor = selectedExtinguidor;
    }

    public ArrayList<InspeccionBotiquin> getElementosBotiquinList() {
        return elementosBotiquinList;
    }

    public void setElementosBotiquinList(ArrayList<InspeccionBotiquin> elementosBotiquinList) {
        this.elementosBotiquinList = elementosBotiquinList;
    }

    public List<RelInspeccionesCentroTrabajo> getRelInspeccionesCentroTrabajoList() {
        return relInspeccionesCentroTrabajoList;
    }

    public void setRelInspeccionesCentroTrabajoList(List<RelInspeccionesCentroTrabajo> relInspeccionesCentroTrabajoList) {
        this.relInspeccionesCentroTrabajoList = relInspeccionesCentroTrabajoList;
    }

    public CentroTrabajo getCentroTrabajo() {
        return centroTrabajo;
    }

    public void setCentroTrabajo(CentroTrabajo centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }

    public InspeccionesTipo getInspeccionTipo() {
        return inspeccionTipo;
    }

    public void setInspeccionTipo(InspeccionesTipo inspeccionTipo) {
        this.inspeccionTipo = inspeccionTipo;
    }

    public List<InspeccionesTipo> getInspeccionesTipo() {
        return inspeccionesTipo;
    }

    public void setInspeccionesTipo(List<InspeccionesTipo> inspeccionesTipo) {
        this.inspeccionesTipo = inspeccionesTipo;
    }

    public GestorInspecciones getGestorInspecciones() {
        return gestorInspecciones;
    }

    public void setGestorInspecciones(GestorInspecciones gestorInspecciones) {
        this.gestorInspecciones = gestorInspecciones;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public ArrayList<InspeccionExtintor> getExtintoresList() {
        return extintoresList;
    }

    public void setExtintoresList(ArrayList<InspeccionExtintor> extintoresList) {
        this.extintoresList = extintoresList;
    }

    public InspeccionExtintor getInspeccionExtintor() {
        return inspeccionExtintor;
    }

    public void setInspeccionExtintor(InspeccionExtintor inspeccionExtintor) {
        this.inspeccionExtintor = inspeccionExtintor;
    }
    
    public void modificarPlanAccion() {
        EvaluacionPlanAccionDetalle epad = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");        
        modificarActivo = Boolean.TRUE;
        UtilJSF.setBean("evaluacionPlanAccionDetalle", epad, UtilJSF.SESSION_SCOPE);   
    }

    public Boolean getModificarActivo() {
        return modificarActivo;
    }

    public void setModificarActivo(Boolean modificarActivo) {
        this.modificarActivo = modificarActivo;
    }

    public List<CentroTrabajo> getCentrosTrabajo() {
        return centrosTrabajo;
    }

    public void setCentrosTrabajo(List<CentroTrabajo> centrosTrabajo) {
        this.centrosTrabajo = centrosTrabajo;
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
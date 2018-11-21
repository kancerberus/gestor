/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.controlador.GestorAdjuntosCategoria;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import com.gestor.seguimiento.controlador.GestorPlanTitulo;
import com.gestor.modelo.Sesion;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.Evaluacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author franj
 */

@ManagedBean(name = "uiPlantitulo")
@SessionScoped
public class UIPlantitulo implements Serializable{     
    private GestorPlanTitulo gestorPlanTitulo;       
    private PlanTitulo plantitulo = new PlanTitulo();       
    private PlanTituloPK plantitulopk = new PlanTituloPK();    
    private PlanTituloTexto plantitulotexto= new PlanTituloTexto(); 
    private PlanTituloAdiuntos plantituloadjunto= new PlanTituloAdiuntos(); 
    private PlanTituloTextoPK plantitulotextopk= new PlanTituloTextoPK(); 
    private PlanTituloAdiuntosPK plantituloadjuntopk= new PlanTituloAdiuntosPK();     
    private List<PlanTitulo> plantituloList = new ArrayList<>();
    private List<PlanTituloTexto> plantitulotextoList = new ArrayList<>();
    private List<PlanTituloAdiuntos> plantituloadjuntoList = new ArrayList<>(); 
    private List<AdjuntosCategoria> adjuntosCategorias = new ArrayList<>();    
    
    
    Sesion s = (Sesion) UtilJSF.getBean("sesion");
    
    
    public UIPlantitulo(){  
        
    }        
    
    @PostConstruct
    public void init() {           
        this.cargarPlantitulo(); 
        this.cargarAdjuntosCategoriaTipo();
    }            
    
    public void cargarAdjuntosCategoriaTipo() {
        try {
            GestorAdjuntosCategoria gestorAdjuntosCategoria = new GestorAdjuntosCategoria();    
                        
            adjuntosCategorias= new ArrayList<>();
            adjuntosCategorias.addAll(gestorAdjuntosCategoria.cargarListaAdjuntosCategoriapm());            
            
            if(plantituloadjunto.getAdjuntosCategoria().getCodCategoria() != null){
                plantituloadjunto.getAdjuntosCategoria().setAdjuntosCategoriaTipoList((List<AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(plantituloadjunto.getAdjuntosCategoria().getCodCategoria()));                                                
            }            
            
        } catch (Exception e) {  
            
        }        
    }
     
    public void subirItemPlantitulo() {        
        plantitulo = (PlanTitulo) UtilJSF.getBean("varPlantitulo");
        UtilJSF.setBean("planTitulo", plantitulo, UtilJSF.SESSION_SCOPE);  
        this.cargarPlantitulotexto();
        this.cargarPlantituloadjunto();
    }     
    
    public void subirItemPlantituloadjunto() {           
        this.cargarAdjuntosCategoriaTipo();
        plantituloadjunto = (PlanTituloAdiuntos) UtilJSF.getBean("varPlantituloadjunto");
        UtilJSF.setBean("planTituloadjunto", plantituloadjunto, UtilJSF.SESSION_SCOPE);  
        plantituloadjuntopk.setCodTituloAdjunto(plantituloadjunto.getPlanTituloAdiuntosPK().getCodTituloAdjunto());                          
    }
    
    public void cargarPlantitulo() {        
        try {                  
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            plantituloList = new ArrayList<>();
            gestorPlanTitulo = new GestorPlanTitulo();
            this.plantituloList.addAll((Collection<? extends PlanTitulo>) gestorPlanTitulo.cargarPlanTituloList(e.getEstablecimiento().getCodigoEstablecimiento()));                                                                     
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarPlantitulolista() {        
        try {                   
            this.plantitulo= new PlanTitulo();
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");            
            this.plantituloList = new ArrayList<>();
            gestorPlanTitulo = new GestorPlanTitulo();
            this.plantituloList.addAll((Collection<? extends PlanTitulo>) gestorPlanTitulo.cargarPlanTituloList(e.getEstablecimiento().getCodigoEstablecimiento()));                        
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarPlantituloadjuntolista() {        
        try {            
            this.plantituloadjuntoList = new ArrayList<>();
            gestorPlanTitulo = new GestorPlanTitulo();
            this.plantituloadjuntoList.addAll((Collection<? extends PlanTituloAdiuntos>) gestorPlanTitulo.cargarPlanTituloadjuntoList(plantitulo));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }    
 
    
    public void cargarPlantitulotexto() {        
        try {
            plantitulotexto.setTexto("");
            this.plantitulotextoList = new ArrayList<>();
            gestorPlanTitulo = new GestorPlanTitulo();
            this.plantitulotextoList.addAll((Collection<? extends PlanTituloTexto>) gestorPlanTitulo.cargarPlanTitulotextoList(plantitulo));                                 
            plantitulotexto.setTexto(plantitulotextoList.get(0).getTexto());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    public void cargarPlantituloadjunto() {        
        try {                    
            this.plantituloadjuntoList = new ArrayList<>();
            gestorPlanTitulo = new GestorPlanTitulo();            
            this.plantituloadjuntoList.addAll((Collection<? extends PlanTituloAdiuntos>) gestorPlanTitulo.cargarPlanTituloadjuntoList(plantitulo));                                                                                                                     
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }    
 
        
    public void guardarTitulo(){     
        try {                
            
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorPlanTitulo gestorPlantitulo = new GestorPlanTitulo();           
            plantitulopk.setCodTitulo(Integer.parseInt(plantitulo.getNumeral()));            
            
            PlanTitulo pltitulo = new PlanTitulo(new PlanTituloPK(
            e.getEstablecimiento().getCodigoEstablecimiento(), plantitulopk.getCodTitulo()),plantitulo.getNombre(),plantitulo.getNumeral()
            );           
                                    
            gestorPlantitulo.validarPlantitulo(pltitulo);
            gestorPlantitulo.almacenarTitulo(pltitulo);

            UtilMSG.addSuccessMsg("Titulo almacenado correctamente.");
            UtilJSF.setBean("planTitulo", new PlanTitulo(), UtilJSF.SESSION_SCOPE);                                                
            this.cargarPlantitulolista();
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }    

    public void guardarTituloadjunto(){
        try {            
            if(plantituloadjuntopk.getCodTituloAdjunto()==0){
                UtilMSG.addSuccessMsg("Ingrese un indice para el adjunto");
            }            
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorPlanTitulo gestorPlantitulo = new GestorPlanTitulo();
                        

            PlanTituloAdiuntos pltituloadjunto = new PlanTituloAdiuntos(new PlanTituloAdiuntosPK(e.getEstablecimiento().getCodigoEstablecimiento(), 
                plantituloadjuntopk.getCodTituloAdjunto(), plantitulo.getPlanTituloPK().getCodTitulo()) , plantituloadjunto.getAdjuntosCategoria().getCodCategoria(),
                plantituloadjunto.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo(), plantituloadjunto.getTitulo(), plantituloadjunto.getDescripcion(), plantituloadjunto.getDocumento()                    
            );                        
            gestorPlantitulo.almacenarTituloadjunto(pltituloadjunto);
            
            UtilMSG.addSuccessMsg("Adjunto almacenado correctamente.");
            UtilJSF.setBean("planTituloadjunto", new PlanTituloAdiuntos(), UtilJSF.SESSION_SCOPE);            
            this.cargarPlantituloadjuntolista();
            this.plantituloadjunto=new PlanTituloAdiuntos();
            this.plantituloadjuntopk=new PlanTituloAdiuntosPK();                        
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }        
    }
    
    public String regresar(){        
        try{
        plantitulo=new PlanTitulo();
        plantitulotexto=new PlanTituloTexto();
        plantituloadjunto=new PlanTituloAdiuntos();
        plantituloadjuntoList=new ArrayList<>();
        
        
        UIPlanSeccion ps = (UIPlanSeccion) UtilJSF.getBean("uiPlanseccion");
        ps.setPlanseccion(new PlanSeccion());        
        ps.setPlanseccionList(new ArrayList<PlanSeccion>());
        ps.setPlansecciontexto(new PlanSeccionTexto());        
        ps.setPlanseccionadjuntos(new PlanSeccionAdjuntos());
        ps.getPlanseccionList().clear();
        ps.getPlanseccionadjuntosList().clear();
        
        UIPlanDetalle pd = (UIPlanDetalle) UtilJSF.getBean("uiPlandetalle");
        pd.setPlansecciondetalle(new PlanSeccionDetalle());
        pd.setPlansecciondetalleList(new ArrayList<PlanSeccionDetalle>());
        pd.setPlansecciondetalletexto(new PlanSeccionDetalleTexto());
        pd.setPlansecciondetalleadjuntos(new PlanSeccionDetalleAdjuntos());
        pd.getPlansecciondetalleList().clear();
        pd.getPlansecciondetalleadjuntosList().clear();
                
        UIPlanItem pi = (UIPlanItem) UtilJSF.getBean("uiPlanitem");
        pi.setPlansecciondetalleitem(new PlanSeccionDetalleItem());
        pi.setPlansecciondetalleitemList(new ArrayList<PlanSeccionDetalleItem>());
        pi.setPlansecciondetalleitemtexto(new PlanSeccionDetalleItemTexto());
        pi.setPlansecciondetalleitemadjuntos(new PlanSeccionDetalleItemAdjuntos());
        pi.getPlansecciondetalleitemList().clear();
        pi.getPlansecciondetalleitemadjuntosList().clear();
            
        }catch(Exception e){
        return ("/seguimiento/administrar-plan-maestro-evaluaciones.xhtml?faces-redirect=true");                    
        }        
       return ("/seguimiento/administrar-plan-maestro-evaluaciones.xhtml?faces-redirect=true");                           
    }
    
    public void guardarTitulotexto(){                
         
        try {          
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorPlanTitulo gestorPlantitulo = new GestorPlanTitulo();                                    
            plantitulotextopk.setCodTituloTexto(1);               
            
            PlanTituloTexto pltitulotexto = new PlanTituloTexto(new PlanTituloTextoPK(
            e.getEstablecimiento().getCodigoEstablecimiento(), plantitulo.getPlanTituloPK().getCodTitulo(),plantitulotextopk.getCodTituloTexto()), plantitulotexto.getTexto());                                   
            gestorPlantitulo.almacenarTitulotexto(pltitulotexto);            
            
            UtilMSG.addSuccessMsg("Texto almacenado correctamente.");
            UtilJSF.setBean("planTitulo", new PlanTitulo(), UtilJSF.SESSION_SCOPE); 
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
        
    }    

    public List<AdjuntosCategoria> getAdjuntosCategorias() {
        return adjuntosCategorias;
    }

    public void setAdjuntosCategorias(List<AdjuntosCategoria> adjuntosCategorias) {
        this.adjuntosCategorias = adjuntosCategorias;
    }
   
    public String administrarPlanmaestro() throws IOException{        
        return ("/seguimiento/administrar-plan-maestro-evaluaciones.xhtml?faces-redirect=true");
    }    

    public PlanTituloTextoPK getPlantitulotextopk() {
        return plantitulotextopk;
    }

    public void setPlantitulotextopk(PlanTituloTextoPK plantitulotextopk) {
        this.plantitulotextopk = plantitulotextopk;
    }

    public List<PlanTituloAdiuntos> getPlantituloadjuntoList() {
        return plantituloadjuntoList;
    }

    public void setPlantituloadjuntoList(List<PlanTituloAdiuntos> plantituloadjuntoList) {
        this.plantituloadjuntoList = plantituloadjuntoList;
    }

    public PlanTituloAdiuntos getPlantituloadjunto() {
        return plantituloadjunto;
    }

    public void setPlantituloadjunto(PlanTituloAdiuntos plantituloadjunto) {
        this.plantituloadjunto = plantituloadjunto;
    }

    public PlanTituloAdiuntosPK getPlantituloadjuntopk() {
        return plantituloadjuntopk;
    }

    public void setPlantituloadjuntopk(PlanTituloAdiuntosPK plantituloadjuntopk) {
        this.plantituloadjuntopk = plantituloadjuntopk;
    }

    public PlanTituloTexto getPlantitulotexto() {
        return plantitulotexto;
    }

    public void setPlantitulotexto(PlanTituloTexto plantitulotexto) {
        this.plantitulotexto = plantitulotexto;
    }

    public List<PlanTituloTexto> getPlantitulotextoList() {
        return plantitulotextoList;
    }

    public void setPlantitulotextoList(List<PlanTituloTexto> plantitulotextoList) {
        this.plantitulotextoList = plantitulotextoList;
    }
    
    public List<PlanTitulo> getPlantituloList() {
        return plantituloList;
    }

    public void setPlantituloList(List<PlanTitulo> plantituloList) {
        this.plantituloList = plantituloList;
    }    
    
    public PlanTitulo getPlantitulo() {
        return plantitulo;
    }

    public void setPlantitulo(PlanTitulo plantitulo) {
        this.plantitulo = plantitulo;
    }

    public PlanTituloPK getPlantitulopk() {        
        return plantitulopk;
    }

    public void setPlantitulopk(PlanTituloPK plantitulopk) {
        this.plantitulopk = plantitulopk;
    }
}

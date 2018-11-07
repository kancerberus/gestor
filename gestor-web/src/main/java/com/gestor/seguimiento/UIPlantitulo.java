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
    private GestorAdjuntosCategoria gestorAdjuntosCategoria;   
    private PlanTitulo plantitulo = new PlanTitulo();       
    private PlanTituloPK plantitulopk = new PlanTituloPK();    
    private PlanTituloTexto plantitulotexto= new PlanTituloTexto(); 
    private PlanTituloAdiuntos plantituloadjunto= new PlanTituloAdiuntos(); 
    private PlanTituloTextoPK plantitulotextopk= new PlanTituloTextoPK(); 
    private PlanTituloAdiuntosPK plantituloadjuntopk= new PlanTituloAdiuntosPK();     
    private List<PlanTitulo> plantituloList = new ArrayList<>();
    private List<PlanTituloTexto> plantitulotextoList = new ArrayList<>();
    private List<PlanTituloAdiuntos> plantituloadjuntoList = new ArrayList<>();   
    private AdjuntosCategoria adjuntoscategoria = new AdjuntosCategoria();            
    private AdjuntosCategoriaTipo adjuntosCategoriaTipo= new AdjuntosCategoriaTipo();
    private List<AdjuntosCategoria> adjuntosCategoriaitems = new ArrayList<>();    
    
    Sesion s = (Sesion) UtilJSF.getBean("sesion");
    
    
    public UIPlantitulo(){  
        
    }        
    
    @PostConstruct
    public void init() {
        adjuntoscategoria= new AdjuntosCategoria();
        adjuntoscategoria.setAdjuntosCategoriaTipo( adjuntosCategoriaTipo);
        adjuntosCategoriaTipo = new AdjuntosCategoriaTipo();
        adjuntosCategoriaTipo.setAdjuntosCategoria(new AdjuntosCategoria());       
        
        this.cargarPlantitulo();
        this.cargarCategoriaitems();
        this.cargarAdjuntosCategoriaTipo();
    }
    
    public void cargarCategoriaitems() {
        try {
            this.adjuntosCategoriaitems = new ArrayList<>();
            gestorAdjuntosCategoria = new GestorAdjuntosCategoria();
            this.adjuntosCategoriaitems.addAll((Collection<? extends AdjuntosCategoria>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriapm());                        
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
        
    public void cargarAdjuntosCategoriaTipo() {
        try {                                   
            if(getAdjuntoscategoria() != null){                                
                this.adjuntoscategoria.setAdjuntosCategoriaTipoList((List<AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(adjuntoscategoria.getCodCategoria()));                                
            }            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
            UtilMSG.addSupportMsg();
        }        
    }
     
    public void subirItemPlantitulo() {                     
        plantitulo = (PlanTitulo) UtilJSF.getBean("varPlantitulo");
        UtilJSF.setBean("planTitulo", plantitulo, UtilJSF.SESSION_SCOPE);                  
        this.cargarPlantitulotexto();
        this.cargarPlantituloadjunto();
    }     
    
    public void subirItemPlantituloadjunto() {   
        this.adjuntoscategoria= new AdjuntosCategoria();
        plantituloadjunto = (PlanTituloAdiuntos) UtilJSF.getBean("varPlantituloadjunto");
        UtilJSF.setBean("planTituloadjunto", plantituloadjunto, UtilJSF.SESSION_SCOPE);                          
        adjuntoscategoria.setCodCategoria(plantituloadjunto.getCodCategoria());
        this.cargarAdjuntosCategoriaTipo();
        adjuntoscategoria.getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().setCodCategoriaTipo(plantituloadjunto.getCodCategoriaTipo());        
        for(int i=0;i<=plantituloadjuntoList.size();i++){
            if(plantituloadjuntoList.get(i).getCodCategoriaTipo()==plantituloadjunto.getCodCategoriaTipo()){
                adjuntosCategoriaitems.set(i, adjuntoscategoria);
            }
        }
    }
    
    public void cargarPlantitulo() {        
        try {            
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            this.plantituloList = new ArrayList<>();
            gestorPlanTitulo = new GestorPlanTitulo();
            this.plantituloList.addAll((Collection<? extends PlanTitulo>) gestorPlanTitulo.cargarPlanTituloList(e.getEstablecimiento().getCodigoEstablecimiento()));                        
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarPlantitulolista() {        
        try {            
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            this.plantituloList = new ArrayList<>();
            gestorPlanTitulo = new GestorPlanTitulo();
            this.plantituloList.addAll((Collection<? extends PlanTitulo>) gestorPlanTitulo.cargarPlanTituloList(e.getEstablecimiento().getCodigoEstablecimiento()));                        
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
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorPlanTitulo gestorPlantitulo = new GestorPlanTitulo();                                    
            if(plantituloadjuntopk.getCodTituloAdjunto()==0){
                plantituloadjuntopk.setCodTituloAdjunto(1);
            }
            
            plantituloadjuntopk.setCodTituloAdjunto(plantituloadjuntoList.size()+1);             
            
            
            PlanTituloAdiuntos pltituloadjunto = new PlanTituloAdiuntos(new PlanTituloAdiuntosPK(e.getEstablecimiento().getCodigoEstablecimiento(), 
                plantituloadjuntopk.getCodTituloAdjunto(), plantitulo.getPlanTituloPK().getCodTitulo()) , adjuntoscategoria.getCodCategoria(),
                adjuntoscategoria.getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo(), plantituloadjunto.getTitulo(), plantituloadjunto.getDescripcion(), plantituloadjunto.getDocumento()                    
            );
           gestorPlantitulo.almacenarTituloadjunto(pltituloadjunto);
            
            UtilMSG.addSuccessMsg("Adjunto almacenado correctamente.");
            UtilJSF.setBean("planTitulo", new PlanTitulo(), UtilJSF.SESSION_SCOPE);
            this.plantituloadjunto= new PlanTituloAdiuntos();            
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }        
    }
    
    public void limpiar(){
        this.plantitulo=new PlanTitulo();        
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

    public AdjuntosCategoriaTipo getAdjuntosCategoriaTipo() {
        return adjuntosCategoriaTipo;
    }

    public void setAdjuntosCategoriaTipo(AdjuntosCategoriaTipo adjuntosCategoriaTipo) {
        this.adjuntosCategoriaTipo = adjuntosCategoriaTipo;
    }

    public AdjuntosCategoria getAdjuntoscategoria() {
        return adjuntoscategoria;
    }

    public void setAdjuntoscategoria(AdjuntosCategoria adjuntoscategoria) {
        this.adjuntoscategoria = adjuntoscategoria;
    }

    public List<AdjuntosCategoria> getAdjuntosCategoriaitems() {
        return adjuntosCategoriaitems;
    }

    public void setAdjuntosCategoriaitems(List<AdjuntosCategoria> adjuntosCategoriaitems) {
        this.adjuntosCategoriaitems = adjuntosCategoriaitems;
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

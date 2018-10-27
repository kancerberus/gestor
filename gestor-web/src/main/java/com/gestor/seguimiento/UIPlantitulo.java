/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import com.gestor.seguimiento.controlador.GestorPlanTitulo;
import com.gestor.modelo.Sesion;
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
    Sesion s = (Sesion) UtilJSF.getBean("sesion"); 
    
    
    public UIPlantitulo(){        
    }    
    
    @PostConstruct
    public void init() {       
        this.cargarPlantitulo();
        this.cargarPlantitulotexto();        
    }
     
    public void subirItemPlantitulo() {
        plantitulo = (PlanTitulo) UtilJSF.getBean("varPlantitulo");        
        UtilJSF.setBean("Plantitulo", plantitulo, UtilJSF.SESSION_SCOPE);                
        this.cargarPlantitulotexto();
    }     
      
    public void cargarPlantitulo() {        
        try {
            this.plantituloList = new ArrayList<>();
            gestorPlanTitulo = new GestorPlanTitulo();
            this.plantituloList.addAll((Collection<? extends PlanTitulo>) gestorPlanTitulo.cargarPlanTituloList(s.getEstablecimiento().getCodigoEstablecimiento()));
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
    
    /*public void cargarPlantituloadjunto() {                
        try {
            this.plantituloadjuntoList = new ArrayList<>();
            gestorPlanMaestro = new GestorPlanMaestro();
            this.plantituloadjuntoList.addAll((Collection<? extends PlanTituloAdiuntos>) gestorPlanMaestro.cargarListaTituloadjunto(plantitulo));                        
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }*/
    
        
    public void guardarTitulo(){                        
        
        try {                                                    
            GestorPlanTitulo gestorPlantitulo = new GestorPlanTitulo();

            plantitulopk.setCodTitulo(Integer.parseInt(plantitulo.getNumeral()));
            
            
            PlanTitulo pltitulo = new PlanTitulo(new PlanTituloPK(
            s.getEstablecimiento().getCodigoEstablecimiento(), plantitulopk.getCodTitulo()),plantitulo.getNombre(),plantitulo.getNumeral()
            );           
                                    
            gestorPlantitulo.validarPlantitulo(pltitulo);
            gestorPlantitulo.almacenarTitulo(pltitulo);

            UtilMSG.addSuccessMsg("Titulo almacenado correctamente.");
            UtilJSF.setBean("Plantitulo", new PlanTitulo(), UtilJSF.SESSION_SCOPE);
            this.limpiar();
            

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
    /*public void guardarTituloadjunto(){
        Integer codE=1;        
        try {                                    
            
            plantitulopk.setCodTitulo(codTit); 
            GestorPlanTitulo gestorPlantitulo = new GestorPlanTitulo();                                    
            plantituloadjuntopk.setCodTituloAdjunto(plantituloadjuntoList.size()+1);    
            
            PlanTituloAdiuntos pltituloadjunto = new PlanTituloAdiuntos(new PlanTituloAdiuntosPK(codE, plantituloadjuntopk.getCodTituloAdjunto(), plantitulopk.getCodTitulo()) , plantituloadjunto.getCodCategoria(),
                    plantituloadjunto.getCodCategoriaTipo(), plantituloadjunto.getTitulo(), plantituloadjunto.getDescripcion(), plantituloadjunto.getDocumento()                    
            );
//           gestorPlantitulo.almacenarTituloadjunto(pltituloadjunto);
            
            UtilMSG.addSuccessMsg("Adjunto almacenado correctamente.");
            UtilJSF.setBean("Plantitulo", new PlanTitulo(), UtilJSF.SESSION_SCOPE);
            this.plantitulotextopk.setCodTituloTexto(0);
            this.plantitulotextoList.clear();
            this.codTit=null;
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
        
    }*/
    
    public void guardarTitulotexto(){                
         
        try {               
            GestorPlanTitulo gestorPlantitulo = new GestorPlanTitulo();                                    
            plantitulotextopk.setCodTituloTexto(1);               
            
            PlanTituloTexto pltitulotexto = new PlanTituloTexto(new PlanTituloTextoPK(
            s.getEstablecimiento().getCodigoEstablecimiento(), plantitulo.getPlanTituloPK().getCodTitulo(),plantitulotextopk.getCodTituloTexto()), plantitulotexto.getTexto());                                   
            gestorPlantitulo.almacenarTitulotexto(pltitulotexto);            
            
            UtilMSG.addSuccessMsg("Texto almacenado correctamente.");
            UtilJSF.setBean("Plantitulo", new PlanTitulo(), UtilJSF.SESSION_SCOPE); 
            this.cargarPlantitulotexto();
            
            
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
        
        this.plantitulo.setNumeral("");
        this.plantitulo.setNombre("");        
        this.cargarPlantitulo();
        this.plantitulotextoList.clear();
        this.plantitulotexto.setTexto("");
    }    
    
   
    public String administrarPlanmaestro(){
        return ("/seguimiento/admin-plan-maestro.xhtml?faces-redirect=true");
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

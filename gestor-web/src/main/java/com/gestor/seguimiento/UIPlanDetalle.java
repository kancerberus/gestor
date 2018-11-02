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
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.controlador.GestorAdjuntosCategoria;
import com.gestor.seguimiento.controlador.GestorPlanSeccion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author franj
 */
@ManagedBean(name = "uiPlandetalle")
@SessionScoped
public class UIPlanDetalle implements Serializable{
    private GestorPlanSeccion gestorPlanSeccion;
    private GestorAdjuntosCategoria gestorAdjuntosCategoria;   
    private PlanSeccionDetalle plansecciondetalle = new PlanSeccionDetalle();    
    private PlanSeccionDetalleTexto plansecciondetalletexto = new PlanSeccionDetalleTexto();
    private PlanSeccionDetalleTextoPK plansecciondetalletextopk = new PlanSeccionDetalleTextoPK();
    private PlanSeccionDetallePK plansecciondetallepk = new PlanSeccionDetallePK();
    private List<PlanSeccionDetalle> plansecciondetalleList = new ArrayList<>();    
    private List<PlanSeccionDetalleTexto> plansecciondetalletextoList = new ArrayList<>();    
    private List<PlanSeccionAdjuntos> planseccionadjuntosList = new ArrayList<>();   
    private List<AdjuntosCategoria> adjuntosCategoriaitems = new ArrayList<>();
    private AdjuntosCategoria adjuntoscategoria = new AdjuntosCategoria();    
    
    
    public UIPlanDetalle(){  
        
    }   
    
    @PostConstruct
    public void init() {         
        this.cargarPlansecciondetalle();
        this.cargarPlansecciondetalletexto();
    }
    
    public void subirItemPlansecciondetalle() {    
        plansecciondetalle = (PlanSeccionDetalle) UtilJSF.getBean("varPlandetalle");                     
        Integer coddetalle=Integer.parseInt(plansecciondetalle.getNumeral().substring(4, 5));
        plansecciondetallepk.setCodSeccionDetalle(coddetalle);   
        plansecciondetalle = (PlanSeccionDetalle) UtilJSF.getBean("varPlandetalle");
        UtilJSF.setBean("planDetalle", plansecciondetalle, UtilJSF.SESSION_SCOPE);      
        this.cargarPlansecciondetalletexto();           
    }
    
    public void cargarPlansecciondetalle() {
        try {
            PlanSeccion ps=(PlanSeccion) UtilJSF.getBean("varPlanseccion");
            this.plansecciondetalleList=new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();            
            this.plansecciondetalleList.addAll((Collection<? extends PlanSeccionDetalle>) gestorPlanSeccion.cargarListaSecciondetalle(ps));                                                
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarAdjuntosCategoriaTipo() {
        try {                                   
            if(getAdjuntoscategoria() != null){                
                adjuntoscategoria.setAdjuntosCategoriaTipoList((List<AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(adjuntoscategoria.getCodCategoria()));                                
            }            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
            UtilMSG.addSupportMsg();
        }        
    }
    
    public void cargarPlansecciondetalletexto() {        
        try {
            plansecciondetalletexto.setTexto("");
            this.plansecciondetalletextoList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.plansecciondetalletextoList.addAll((Collection<? extends PlanSeccionDetalleTexto>) gestorPlanSeccion.cargarPlanSecciondetalletextoList(plansecciondetalle));                                 
            plansecciondetalletexto.setTexto(plansecciondetalletextoList.get(0).getTexto());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void guardarSeccionDetalle(){              
        try {              
            GestorPlanSeccion gestorPlanseccion= new GestorPlanSeccion();           
            PlanSeccion ps=(PlanSeccion) UtilJSF.getBean("planSeccion");            
            
            if(plansecciondetallepk.getCodSeccionDetalle()==0){
                plansecciondetallepk.setCodSeccion(1);
            }
            
            plansecciondetalle.setNumeral(ps.getNumeral()+"."+Integer.toString(plansecciondetallepk.getCodSeccionDetalle()));
            
            PlanSeccionDetalle plsecciondetalle = new PlanSeccionDetalle(new PlanSeccionDetallePK(ps.getPlanSeccionPK().getCodigoEstablecimiento(), ps.getPlanSeccionPK().getCodTitulo()
                    , ps.getPlanSeccionPK().getCodSeccion() , plansecciondetallepk.getCodSeccionDetalle()) , plansecciondetalle.getNumeral(), plansecciondetalle.getNombre()
            );            
            
            gestorPlanseccion.validarPlansecciondetalle(plsecciondetalle);
            gestorPlanseccion.almacenarSecciondetalle(plsecciondetalle);                    
            
            UtilMSG.addSuccessMsg("Titulo Detalle almacenado correctamente.");                                 
            UtilJSF.setBean("planDetalle", new PlanSeccionDetalle(), UtilJSF.SESSION_SCOPE);                        
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
    public void guardarSecciondetalletexto(){                
         
        try {                        
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                                    
            plansecciondetalletextopk.setCodSeccionDetalleTexto(1);    
            
            PlanSeccionDetalleTexto plsecciondetalletexto = new PlanSeccionDetalleTexto(new PlanSeccionDetalleTextoPK(plansecciondetalle.getPlanSeccionDetallePK().getCodigoEstablecimiento(),
            plansecciondetalle.getPlanSeccionDetallePK().getCodTitulo(), plansecciondetalle.getPlanSeccionDetallePK().getCodSeccion(), plansecciondetalle.getPlanSeccionDetallePK().getCodSeccionDetalle(),
            plansecciondetalletextopk.getCodSeccionDetalleTexto()), plansecciondetalletexto.getTexto()
            );
            gestorPlanseccion.almacenarSecciondetalletexto(plsecciondetalletexto);            
            
            UtilMSG.addSuccessMsg("Texto almacenado correctamente.");
            UtilJSF.setBean("planDetalle", new PlanSeccionDetalle(), UtilJSF.SESSION_SCOPE); 
            this.cargarPlansecciondetalletexto();
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
        
    }

    public List<AdjuntosCategoria> getAdjuntosCategoriaitems() {
        return adjuntosCategoriaitems;
    }

    public void setAdjuntosCategoriaitems(List<AdjuntosCategoria> adjuntosCategoriaitems) {
        this.adjuntosCategoriaitems = adjuntosCategoriaitems;
    }

    public AdjuntosCategoria getAdjuntoscategoria() {
        return adjuntoscategoria;
    }

    public void setAdjuntoscategoria(AdjuntosCategoria adjuntoscategoria) {
        this.adjuntoscategoria = adjuntoscategoria;
    }

    public PlanSeccionDetalle getPlansecciondetalle() {
        return plansecciondetalle;
    }

    public void setPlansecciondetalle(PlanSeccionDetalle plansecciondetalle) {
        this.plansecciondetalle = plansecciondetalle;
    }

    public PlanSeccionDetalleTexto getPlansecciondetalletexto() {
        return plansecciondetalletexto;
    }

    public void setPlansecciondetalletexto(PlanSeccionDetalleTexto plansecciondetalletexto) {
        this.plansecciondetalletexto = plansecciondetalletexto;
    }

    public PlanSeccionDetalleTextoPK getPlansecciondetalletextopk() {
        return plansecciondetalletextopk;
    }

    public void setPlansecciondetalletextopk(PlanSeccionDetalleTextoPK plansecciondetalletextopk) {
        this.plansecciondetalletextopk = plansecciondetalletextopk;
    }

    public PlanSeccionDetallePK getPlansecciondetallepk() {
        return plansecciondetallepk;
    }

    public void setPlansecciondetallepk(PlanSeccionDetallePK plansecciondetallepk) {
        this.plansecciondetallepk = plansecciondetallepk;
    }

    public List<PlanSeccionDetalle> getPlansecciondetalleList() {
        return plansecciondetalleList;
    }

    public void setPlansecciondetalleList(List<PlanSeccionDetalle> plansecciondetalleList) {
        this.plansecciondetalleList = plansecciondetalleList;
    }
    
    
}

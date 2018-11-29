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
    private PlanSeccionDetalleAdjuntos plansecciondetalleadjuntos = new PlanSeccionDetalleAdjuntos();
    private PlanSeccionDetallePK plansecciondetallepk = new PlanSeccionDetallePK();
    private PlanSeccionDetalleAdjuntosPK plansecciondetalleadjuntospk = new PlanSeccionDetalleAdjuntosPK();
    private List<PlanSeccionDetalle> plansecciondetalleList = new ArrayList<>();    
    private List<PlanSeccionDetalleTexto> plansecciondetalletextoList = new ArrayList<>();    
    private List<PlanSeccionDetalleAdjuntos> plansecciondetalleadjuntosList = new ArrayList<>();   
    private List<AdjuntosCategoria> adjuntosCategorias = new ArrayList<>();
    
    
    @PostConstruct
    public void init() {           
        this.cargarPlansecciondetalle();
        this.cargarAdjuntosCategoriaTipo();
        
    }      
    
    public UIPlanDetalle(){  
        
    }   
    

    public void cargarAdjuntosCategoriaTipo() {
        try {            
            GestorAdjuntosCategoria gestorAdjuntosCategoria = new GestorAdjuntosCategoria();    
            adjuntosCategorias = new ArrayList<>();
            adjuntosCategorias.addAll(gestorAdjuntosCategoria.cargarListaAdjuntosCategoriapm());            
            
            if(plansecciondetalleadjuntos.getAdjuntosCategoria().getCodCategoria() != null){
                plansecciondetalleadjuntos.getAdjuntosCategoria().setAdjuntosCategoriaTipoList((List<AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(plansecciondetalleadjuntos.getAdjuntosCategoria().getCodCategoria()));                                                
            }            
            
        } catch (Exception e) {            
        }        
    }    

    
    public void subirItemPlansecciondetalle() {    
        plansecciondetalle = (PlanSeccionDetalle) UtilJSF.getBean("varPlandetalle");          
        Integer coddetalle=Integer.parseInt(plansecciondetalle.getNumeral().substring(4, 5));
        plansecciondetallepk.setCodSeccionDetalle(coddetalle);           
        UtilJSF.setBean("planDetalle", plansecciondetalle, UtilJSF.SESSION_SCOPE);        
        this.cargarPlansecciondetalletexto();
        this.cargarPlansecciondetalleadjuntoList();        
                   
    }
    
    public void subirItemPlansecciondetalleadjunto() {  
        this.cargarAdjuntosCategoriaTipo();
        plansecciondetalleadjuntos = (PlanSeccionDetalleAdjuntos) UtilJSF.getBean("varPlandetalleadjunto");
        UtilJSF.setBean("planSecciondetalleadjunto", plansecciondetalleadjuntos, UtilJSF.SESSION_SCOPE);         
        plansecciondetalleadjuntospk.setCodSeccionDetalleAdjuntos(plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodSeccionDetalleAdjuntos());
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
    
    public void cargarPlansecciondetalleadjunto() {        
        try {            
            this.plansecciondetalleadjuntosList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.plansecciondetalleadjuntosList.addAll((Collection<? extends PlanSeccionDetalleAdjuntos>) gestorPlanSeccion.cargarPlanSecciondetalleadjuntoList(plansecciondetalle));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }    
    
    
    public void cargarPlansecciondetalletexto() {        
        try {            
            this.plansecciondetalletextoList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.plansecciondetalletextoList.addAll((Collection<? extends PlanSeccionDetalleTexto>) gestorPlanSeccion.cargarPlanSecciondetalletextoList(plansecciondetalle));                                 
            plansecciondetalletexto.setTexto(plansecciondetalletextoList.get(0).getTexto());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarPlansecciondetalleadjuntoList() {        
        try {            
            this.plansecciondetalleadjuntosList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.plansecciondetalleadjuntosList.addAll((Collection<? extends PlanSeccionDetalleAdjuntos>) gestorPlanSeccion.cargarPlanSecciondetalleadjuntoList(plansecciondetalle));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }    
    
    public void cargarPlansecciondetalleList() {
        
        try {                        
            PlanSeccion ps=(PlanSeccion) UtilJSF.getBean("planSeccion");            
            this.plansecciondetalleList=new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();                   
            this.plansecciondetalleList.addAll((Collection<? extends PlanSeccionDetalle>) gestorPlanSeccion.cargarListaSecciondetalle(ps));                     
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
            
            UtilJSF.setBean("varPlandetalle", plsecciondetalle, UtilJSF.SESSION_SCOPE);
            UtilJSF.setBean("planDetalle", plsecciondetalle, UtilJSF.SESSION_SCOPE);
            this.cargarPlansecciondetalleList();            
            UtilMSG.addSuccessMsg("Titulo Detalle almacenado correctamente.");                                             
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
            PlanSeccion ps=(PlanSeccion) UtilJSF.getBean("planSeccion");                        
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                                    
            plansecciondetalletextopk.setCodSeccionDetalleTexto(1);    
            
            PlanSeccionDetalleTexto plsecciondetalletexto = new PlanSeccionDetalleTexto(new PlanSeccionDetalleTextoPK(ps.getPlanSeccionPK().getCodigoEstablecimiento(),
            ps.getPlanSeccionPK().getCodTitulo(), ps.getPlanSeccionPK().getCodSeccion(), plansecciondetallepk.getCodSeccionDetalle(),
            plansecciondetalletextopk.getCodSeccionDetalleTexto()), plansecciondetalletexto.getTexto()
            );
            gestorPlanseccion.almacenarSecciondetalletexto(plsecciondetalletexto);            
            
            UtilMSG.addSuccessMsg("Texto almacenado correctamente.");            
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
    
    public void guardarSecciondetalleadjunto(){              
        try {                            
            PlanSeccionDetalleAdjuntos psda = (PlanSeccionDetalleAdjuntos) UtilJSF.getBean("planSecciondetalleadjunto");
                        
            if(psda==null || plansecciondetalleadjuntospk.getCodSeccionDetalleAdjuntos()==0){
                plansecciondetalleadjuntospk.setCodSeccionDetalleAdjuntos(plansecciondetalleadjuntosList.size()+1);
            }            
            
            PlanSeccion ps=(PlanSeccion) UtilJSF.getBean("planSeccion");            
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                                    
            
            PlanSeccionDetalleAdjuntos plsecciondetalleadjunto = new PlanSeccionDetalleAdjuntos(new PlanSeccionDetalleAdjuntosPK(ps.getPlanSeccionPK().getCodigoEstablecimiento(),
                ps.getPlanSeccionPK().getCodTitulo(), ps.getPlanSeccionPK().getCodSeccion(), plansecciondetallepk.getCodSeccionDetalle(), plansecciondetalleadjuntospk.getCodSeccionDetalleAdjuntos()) , plansecciondetalleadjuntos.getAdjuntosCategoria().getCodCategoria(),
                plansecciondetalleadjuntos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo(), plansecciondetalleadjuntos.getTitulo(), plansecciondetalleadjuntos.getDescripcion(), plansecciondetalleadjuntos.getDocumento()                    
            );
           gestorPlanseccion.almacenarSecciondetalleadjunto(plsecciondetalleadjunto);
            
            UtilMSG.addSuccessMsg("Adjunto almacenado correctamente.");
            UtilJSF.setBean("planDetalle", new PlanSeccionDetalle(), UtilJSF.SESSION_SCOPE);
            this.cargarPlansecciondetalleadjuntoList();
            this.plansecciondetalleadjuntos=new PlanSeccionDetalleAdjuntos();
            this.plansecciondetalleadjuntospk=new PlanSeccionDetalleAdjuntosPK();
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }        
    }
    
    public void eliminarPlansecciondetalleadjunto(){
        try{
            plansecciondetalleadjuntos = (PlanSeccionDetalleAdjuntos) UtilJSF.getBean("varPlandetalleadjunto");     
            
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();
            
            gestorPlanseccion.eliminarPlansecciondetalleadjunto(plansecciondetalleadjuntos);
            
            UtilMSG.addSuccessMsg("Adjunto eliminado correctamente.");            
            this.cargarPlansecciondetalleadjuntoList();
        }catch (Exception e) {
            UtilMSG.addSuccessMsg("Adjunto en uso.");
        }
    }
    
    public List<AdjuntosCategoria> getAdjuntosCategorias() {
        return adjuntosCategorias;
    }

    public void setAdjuntosCategorias(List<AdjuntosCategoria> adjuntosCategorias) {
        this.adjuntosCategorias = adjuntosCategorias;
    }

    public PlanSeccionDetalleAdjuntosPK getPlansecciondetalleadjuntospk() {
        return plansecciondetalleadjuntospk;
    }

    public void setPlansecciondetalleadjuntospk(PlanSeccionDetalleAdjuntosPK plansecciondetalleadjuntospk) {
        this.plansecciondetalleadjuntospk = plansecciondetalleadjuntospk;
    }

    public PlanSeccionDetalleAdjuntos getPlansecciondetalleadjuntos() {
        return plansecciondetalleadjuntos;
    }

    public void setPlansecciondetalleadjuntos(PlanSeccionDetalleAdjuntos plansecciondetalleadjuntos) {
        this.plansecciondetalleadjuntos = plansecciondetalleadjuntos;
    }

    public List<PlanSeccionDetalleTexto> getPlansecciondetalletextoList() {
        return plansecciondetalletextoList;
    }

    public void setPlansecciondetalletextoList(List<PlanSeccionDetalleTexto> plansecciondetalletextoList) {
        this.plansecciondetalletextoList = plansecciondetalletextoList;
    }

    public List<PlanSeccionDetalleAdjuntos> getPlansecciondetalleadjuntosList() {
        return plansecciondetalleadjuntosList;
    }

    public void setPlansecciondetalleadjuntosList(List<PlanSeccionDetalleAdjuntos> plansecciondetalleadjuntosList) {
        this.plansecciondetalleadjuntosList = plansecciondetalleadjuntosList;
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

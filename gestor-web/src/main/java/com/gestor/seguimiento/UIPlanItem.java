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

@ManagedBean(name = "uiPlanitem")
@SessionScoped
public class UIPlanItem implements Serializable{
    
    private GestorPlanSeccion gestorPlanSeccion;
    private PlanSeccionDetalleItem plansecciondetalleitem = new PlanSeccionDetalleItem();    
    private PlanSeccionDetalleItemTexto plansecciondetalleitemtexto = new PlanSeccionDetalleItemTexto();
    private PlanSeccionDetalleItemTextoPK plansecciondetalleitemtextopk = new PlanSeccionDetalleItemTextoPK();
    private PlanSeccionDetalleItemPK plansecciondetalleitempk = new PlanSeccionDetalleItemPK();
    private PlanSeccionDetalleItemAdjuntos plansecciondetalleitemadjuntos = new PlanSeccionDetalleItemAdjuntos();
    private PlanSeccionDetalleItemAdjuntosPK plansecciondetalleitemadjuntospk = new PlanSeccionDetalleItemAdjuntosPK();
    private List<PlanSeccionDetalleItemAdjuntos> plansecciondetalleitemadjuntosList = new ArrayList<>();   
    private List<PlanSeccionDetalleItem> plansecciondetalleitemList = new ArrayList<>();    
    private List<PlanSeccionDetalleItemTexto> plansecciondetalleitemtextoList = new ArrayList<>();       
    private List<AdjuntosCategoria> adjuntosCategorias = new ArrayList<>();
    
    
    public UIPlanItem(){
    
    }
    @PostConstruct
    public void init() {  
        this.cargarPlansecciondetalleitem();
        this.cargarAdjuntosCategoriaTipo();
        
    }      
    
    public void cargarAdjuntosCategoriaTipo() {
        try {            
            GestorAdjuntosCategoria gestorAdjuntosCategoria = new GestorAdjuntosCategoria();    
            adjuntosCategorias = new ArrayList<>();
            adjuntosCategorias.addAll(gestorAdjuntosCategoria.cargarListaAdjuntosCategoriapm());            
            
            if(plansecciondetalleitemadjuntos.getAdjuntosCategoria().getCodCategoria() != null){
                plansecciondetalleitemadjuntos.getAdjuntosCategoria().setAdjuntosCategoriaTipoList((List<AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(plansecciondetalleitemadjuntos.getAdjuntosCategoria().getCodCategoria()));                                                
            }            
            
        } catch (Exception e) {
            
        }        
    }    

    public void subirItemPlansecciondetalleitem() {  
        plansecciondetalleitem = (PlanSeccionDetalleItem) UtilJSF.getBean("varPlanitem");                     
        Integer coditem=Integer.parseInt(plansecciondetalleitem.getNumeral().substring(6, 7));
        plansecciondetalleitempk.setCodSeccionDetalleItem(coditem);           
        UtilJSF.setBean("planItem", plansecciondetalleitem, UtilJSF.SESSION_SCOPE);      
        this.cargarPlansecciondetalleitemtexto();     
        this.cargarPlansecciondetalleitemadjuntoList();        
    }
    
    public void subirItemPlanitemadjunto() {   
        
        
        plansecciondetalleitemadjuntos = (PlanSeccionDetalleItemAdjuntos) UtilJSF.getBean("varPlanitemadjunto");
        UtilJSF.setBean("planSecciondetalleitemadjunto", plansecciondetalleitemadjuntos, UtilJSF.SESSION_SCOPE);                          
        plansecciondetalleitemadjuntospk.setCodSeccionDetalleItemAdjuntos(plansecciondetalleitemadjuntos.getPlanSeccionDetalleItemAdjuntosPK().getCodSeccionDetalleItemAdjuntos());        
        
    }
    
    public void cargarPlansecciondetalleitem() {        
        try {               
            PlanSeccionDetalle psd=(PlanSeccionDetalle) UtilJSF.getBean("varPlandetalle");
            this.plansecciondetalleitemList=new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();            
            this.plansecciondetalleitemList.addAll((Collection<? extends PlanSeccionDetalleItem>) gestorPlanSeccion.cargarListaSecciondetalleitem(psd));                                                
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarPlansecciondetalleitemadjuntoList() {        
        try {            
            this.plansecciondetalleitemadjuntosList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.plansecciondetalleitemadjuntosList.addAll((Collection<? extends PlanSeccionDetalleItemAdjuntos>) gestorPlanSeccion.cargarPlanSecciondetalleitemadjuntoList(plansecciondetalleitem));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }   
    
    public void cargarPlansecciondetalleitemtexto() {        
        try {
            PlanSeccionDetalle psd=(PlanSeccionDetalle) UtilJSF.getBean("planDetalle");
            plansecciondetalleitem.getPlanSeccionDetalleItemPK().setCodigoEstablecimiento(psd.getPlanSeccionDetallePK().getCodigoEstablecimiento());
            plansecciondetalleitem.getPlanSeccionDetalleItemPK().setCodTitulo(psd.getPlanSeccionDetallePK().getCodTitulo());
            plansecciondetalleitemtexto.setTexto("");
            this.plansecciondetalleitemtextoList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.plansecciondetalleitemtextoList.addAll((Collection<? extends PlanSeccionDetalleItemTexto>) gestorPlanSeccion.cargarPlanSecciondetalletextoitemList(plansecciondetalleitem));                                 
            plansecciondetalleitemtexto.setTexto(plansecciondetalleitemtextoList.get(0).getTexto());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarPlansecciondetalleitemadjunto() {        
        try {            
            this.plansecciondetalleitemadjuntosList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.plansecciondetalleitemadjuntosList.addAll((Collection<? extends PlanSeccionDetalleItemAdjuntos>) gestorPlanSeccion.cargarPlanSecciondetalleitemadjuntoList(plansecciondetalleitem));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }   
    
    public void cargarPlansecciondetalleitemList() {
        
        try {                        
            PlanSeccionDetalle psd=(PlanSeccionDetalle) UtilJSF.getBean("planDetalle");            
            this.plansecciondetalleitemList=new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();                   
            this.plansecciondetalleitemList.addAll((Collection<? extends PlanSeccionDetalleItem >) gestorPlanSeccion.cargarListaSecciondetalleitem(psd));                     
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
        
    public void guardarSeccionDetalleItem(){              
        try {              
            GestorPlanSeccion gestorPlanseccion= new GestorPlanSeccion();           
            PlanSeccionDetalle psd=(PlanSeccionDetalle) UtilJSF.getBean("planDetalle");
            
            if(plansecciondetalleitempk.getCodSeccionDetalleItem()==0){
                plansecciondetalleitempk.setCodSeccionDetalle(1);
            }
            
            plansecciondetalleitem.setNumeral(psd.getNumeral()+"."+Integer.toString(plansecciondetalleitempk.getCodSeccionDetalleItem()));
            
            PlanSeccionDetalleItem plsecciondetalleitem = new PlanSeccionDetalleItem(new PlanSeccionDetalleItemPK(psd.getPlanSeccionDetallePK().getCodTitulo(), psd.getPlanSeccionDetallePK().getCodigoEstablecimiento(),
            psd.getPlanSeccionDetallePK().getCodSeccion() , psd.getPlanSeccionDetallePK().getCodSeccionDetalle() , plansecciondetalleitempk.getCodSeccionDetalleItem()) , plansecciondetalleitem.getNumeral(), plansecciondetalleitem.getNombre()
            );            
            
            gestorPlanseccion.validarPlansecciondetalleitem(plsecciondetalleitem);
            gestorPlanseccion.almacenarSecciondetalleitem(plsecciondetalleitem);                    
            
            UtilMSG.addSuccessMsg("Titulo Item almacenado correctamente.");                                 
            UtilJSF.setBean("planItem", new PlanSeccionDetalle(), UtilJSF.SESSION_SCOPE);                        
            this.cargarPlansecciondetalleitemList();
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
    public void guardarSecciondetalleitemtexto(){                
         
        try {        
            PlanSeccionDetalle psd=(PlanSeccionDetalle) UtilJSF.getBean("planDetalle");                        
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                                    
            plansecciondetalleitemtextopk.setCodSeccionDetalleItemTexto(1); 
            
            plansecciondetalleitem.getPlanSeccionDetalleItemPK().setCodTitulo(psd.getPlanSeccionDetallePK().getCodTitulo());
            
            PlanSeccionDetalleItemTexto plsecciondetalleitemtexto = new PlanSeccionDetalleItemTexto(new PlanSeccionDetalleItemTextoPK(psd.getPlanSeccionDetallePK().getCodigoEstablecimiento(),
            psd.getPlanSeccionDetallePK().getCodTitulo(), psd.getPlanSeccionDetallePK().getCodSeccion(), psd.getPlanSeccionDetallePK().getCodSeccionDetalle(),
            plansecciondetalleitem.getPlanSeccionDetalleItemPK().getCodSeccionDetalleItem(),plansecciondetalleitemtextopk.getCodSeccionDetalleItemTexto()), plansecciondetalleitemtexto.getTexto()
            );
            gestorPlanseccion.almacenarSecciondetalleitemtexto(plsecciondetalleitemtexto);            
            
            UtilMSG.addSuccessMsg("Texto almacenado correctamente.");
            UtilJSF.setBean("planItem", new PlanSeccionDetalleItem(), UtilJSF.SESSION_SCOPE); 
            this.cargarPlansecciondetalleitemtexto();
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
        
    }
    
    public void guardarSecciondetalleitemadjunto(){              
        try {                   
            PlanSeccionDetalle psd=(PlanSeccionDetalle) UtilJSF.getBean("planDetalle");            
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                                    
            plansecciondetalleitemadjuntospk.setCodSeccionDetalleItemAdjuntos((plansecciondetalleitemadjuntosList.size()+1));             
            
            
            PlanSeccionDetalleItemAdjuntos plsecciondetalleitemadjunto = new PlanSeccionDetalleItemAdjuntos(new PlanSeccionDetalleItemAdjuntosPK(psd.getPlanSeccionDetallePK().getCodigoEstablecimiento(),
                psd.getPlanSeccionDetallePK().getCodTitulo(), psd.getPlanSeccionDetallePK().getCodSeccion(), psd.getPlanSeccionDetallePK().getCodSeccionDetalle(), plansecciondetalleitem.getPlanSeccionDetalleItemPK().getCodSeccionDetalleItem(), plansecciondetalleitemadjuntospk.getCodSeccionDetalleItemAdjuntos()), plansecciondetalleitemadjuntos.getCodCategoria(),
                plansecciondetalleitemadjuntos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo(), plansecciondetalleitemadjuntos.getTitulo(), plansecciondetalleitemadjuntos.getDescripcion(), plansecciondetalleitemadjuntos.getActividad(), plansecciondetalleitemadjuntos.getDescripcionGeneral(), plansecciondetalleitemadjuntos.getDocumento()                    
            );
           gestorPlanseccion.almacenarSecciondetalleitemadjunto(plsecciondetalleitemadjunto);
            
            UtilMSG.addSuccessMsg("Adjunto almacenado correctamente.");
            UtilJSF.setBean("planItem", new PlanSeccion(), UtilJSF.SESSION_SCOPE);
            this.cargarPlansecciondetalleitemadjuntoList();
            this.plansecciondetalleitemadjuntos=new PlanSeccionDetalleItemAdjuntos();
            this.plansecciondetalleitemadjuntospk=new PlanSeccionDetalleItemAdjuntosPK();            
            
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

    public PlanSeccionDetalleItemAdjuntos getPlansecciondetalleitemadjuntos() {
        return plansecciondetalleitemadjuntos;
    }

    public void setPlansecciondetalleitemadjuntos(PlanSeccionDetalleItemAdjuntos plansecciondetalleitemadjuntos) {
        this.plansecciondetalleitemadjuntos = plansecciondetalleitemadjuntos;
    }

    public PlanSeccionDetalleItemAdjuntosPK getPlansecciondetalleitemadjuntospk() {
        return plansecciondetalleitemadjuntospk;
    }

    public void setPlansecciondetalleitemadjuntospk(PlanSeccionDetalleItemAdjuntosPK plansecciondetalleitemadjuntospk) {
        this.plansecciondetalleitemadjuntospk = plansecciondetalleitemadjuntospk;
    }

    public List<PlanSeccionDetalleItemAdjuntos> getPlansecciondetalleitemadjuntosList() {
        return plansecciondetalleitemadjuntosList;
    }

    public void setPlansecciondetalleitemadjuntosList(List<PlanSeccionDetalleItemAdjuntos> plansecciondetalleitemadjuntosList) {
        this.plansecciondetalleitemadjuntosList = plansecciondetalleitemadjuntosList;
    }

    public PlanSeccionDetalleItem getPlansecciondetalleitem() {
        return plansecciondetalleitem;
    }

    public void setPlansecciondetalleitem(PlanSeccionDetalleItem plansecciondetalleitem) {
        this.plansecciondetalleitem = plansecciondetalleitem;
    }

    public PlanSeccionDetalleItemTexto getPlansecciondetalleitemtexto() {
        return plansecciondetalleitemtexto;
    }

    public void setPlansecciondetalleitemtexto(PlanSeccionDetalleItemTexto plansecciondetalleitemtexto) {
        this.plansecciondetalleitemtexto = plansecciondetalleitemtexto;
    }

    public PlanSeccionDetalleItemTextoPK getPlansecciondetalleitemtextopk() {
        return plansecciondetalleitemtextopk;
    }

    public void setPlansecciondetalleitemtextopk(PlanSeccionDetalleItemTextoPK plansecciondetalleitemtextopk) {
        this.plansecciondetalleitemtextopk = plansecciondetalleitemtextopk;
    }

    public PlanSeccionDetalleItemPK getPlansecciondetalleitempk() {
        return plansecciondetalleitempk;
    }

    public void setPlansecciondetalleitempk(PlanSeccionDetalleItemPK plansecciondetalleitempk) {
        this.plansecciondetalleitempk = plansecciondetalleitempk;
    }

    public List<PlanSeccionDetalleItem> getPlansecciondetalleitemList() {
        return plansecciondetalleitemList;
    }

    public void setPlansecciondetalleitemList(List<PlanSeccionDetalleItem> plansecciondetalleitemList) {
        this.plansecciondetalleitemList = plansecciondetalleitemList;
    }

    public List<PlanSeccionDetalleItemTexto> getPlansecciondetalleitemtextoList() {
        return plansecciondetalleitemtextoList;
    }

    public void setPlansecciondetalleitemtextoList(List<PlanSeccionDetalleItemTexto> plansecciondetalleitemtextoList) {
        this.plansecciondetalleitemtextoList = plansecciondetalleitemtextoList;
    }
    
    
}

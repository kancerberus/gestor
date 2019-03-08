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
import com.gestor.gestor.controlador.GestorSeccionDetalleItems;
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
        this.cargarAdjuntosCategoriaTipo();
        this.cargarPlansecciondetalleitem();
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
        PlanTitulo pt=(PlanTitulo) UtilJSF.getBean("planTitulo");
        plansecciondetalleitem = (PlanSeccionDetalleItem) UtilJSF.getBean("varPlanitem");           
        
        Integer codItem;
        
        Integer pos=0;
        Integer tam=0;
        int punto;
        for(int i=0; i<=plansecciondetalleitem.getNumeral().length();i++){            
            punto = plansecciondetalleitem.getNumeral().indexOf(".",pos)+1;
            tam = plansecciondetalleitem.getNumeral().length();
            if(punto>0){
                pos=punto;
            }
        }
        
        codItem=Integer.parseInt(plansecciondetalleitem.getNumeral().substring(pos, tam));       
        plansecciondetalleitempk.setCodSeccionDetalleItem(codItem);
        
                
        plansecciondetalleitem.getPlanSeccionDetalleItemPK().setCodTitulo(pt.getPlanTituloPK().getCodTitulo());
        plansecciondetalleitem.getPlanSeccionDetalleItemPK().setCodigoEstablecimiento(pt.getPlanTituloPK().getCodigoEstablecimiento());
        UtilJSF.setBean("planItem", plansecciondetalleitem, UtilJSF.SESSION_SCOPE);                
        this.cargarAdjuntosCategoriaTipo();
        this.cargarPlansecciondetalleitemtexto();
        this.cargarPlansecciondetalleitemadjunto();
    }
    
    public void subirItemPlanitemadjunto() {  
        this.cargarAdjuntosCategoriaTipo();        
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
        plansecciondetalleitem = (PlanSeccionDetalleItem) UtilJSF.getBean("varPlanitem");           
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
    
    public void eliminarPlanitem(){
        try{
            plansecciondetalleitem = (PlanSeccionDetalleItem) UtilJSF.getBean("varPlanitem");            
            
            GestorSeccionDetalleItems gestorSeccionDetalleItem= new GestorSeccionDetalleItems();
            gestorSeccionDetalleItem.eliminarPlanSeccionDetalleItem(plansecciondetalleitem);
            
            UtilMSG.addSuccessMsg("Item eliminado correctamente.");            
            this.cargarPlansecciondetalleitemList();
        }catch (Exception e) {
            UtilMSG.addSuccessMsg("Item en uso.");
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
            
            UtilJSF.setBean("varPlanitem", plsecciondetalleitem, UtilJSF.SESSION_SCOPE);
            UtilJSF.setBean("planItem", plsecciondetalleitem, UtilJSF.SESSION_SCOPE);
            UtilMSG.addSuccessMsg("Titulo Item almacenado correctamente.");                                             
            this.cargarPlansecciondetalleitemList();
            this.plansecciondetalleitemtexto=new PlanSeccionDetalleItemTexto();
            this.plansecciondetalleitemadjuntos=new PlanSeccionDetalleItemAdjuntos();
            this.plansecciondetalleitemadjuntosList.clear();
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
                        
            
            PlanSeccionDetalleItemTexto plsecciondetalleitemtexto = new PlanSeccionDetalleItemTexto(new PlanSeccionDetalleItemTextoPK(psd.getPlanSeccionDetallePK().getCodigoEstablecimiento(),
            psd.getPlanSeccionDetallePK().getCodTitulo(), psd.getPlanSeccionDetallePK().getCodSeccion(), psd.getPlanSeccionDetallePK().getCodSeccionDetalle(),
            plansecciondetalleitempk.getCodSeccionDetalleItem(),plansecciondetalleitemtextopk.getCodSeccionDetalleItemTexto()), plansecciondetalleitemtexto.getTexto()
            );
            gestorPlanseccion.almacenarSecciondetalleitemtexto(plsecciondetalleitemtexto);            
            
            UtilMSG.addSuccessMsg("Texto almacenado correctamente.");            
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
            PlanSeccionDetalleItemAdjuntos psdia = (PlanSeccionDetalleItemAdjuntos) UtilJSF.getBean("planSecciondetalleitemadjunto");
                        
            if(psdia==null || plansecciondetalleitemadjuntospk.getCodSeccionDetalleItemAdjuntos()==0){
                plansecciondetalleitemadjuntospk.setCodSeccionDetalleItemAdjuntos(plansecciondetalleitemadjuntosList.size()+1);
            }
            
            PlanSeccionDetalle psd=(PlanSeccionDetalle) UtilJSF.getBean("planDetalle");            
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                                    
            
            PlanSeccionDetalleItemAdjuntos plsecciondetalleitemadjunto = new PlanSeccionDetalleItemAdjuntos(new PlanSeccionDetalleItemAdjuntosPK(psd.getPlanSeccionDetallePK().getCodigoEstablecimiento(),
                psd.getPlanSeccionDetallePK().getCodTitulo(), psd.getPlanSeccionDetallePK().getCodSeccion(), psd.getPlanSeccionDetallePK().getCodSeccionDetalle(), plansecciondetalleitempk.getCodSeccionDetalleItem(), plansecciondetalleitemadjuntospk.getCodSeccionDetalleItemAdjuntos()), plansecciondetalleitemadjuntos.getAdjuntosCategoria().getCodCategoria(),
                plansecciondetalleitemadjuntos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo(), plansecciondetalleitemadjuntos.getTitulo(), plansecciondetalleitemadjuntos.getDescripcion(), plansecciondetalleitemadjuntos.getActividad(), plansecciondetalleitemadjuntos.getDescripcionGeneral(), plansecciondetalleitemadjuntos.getDocumento()                    
            );
            gestorPlanseccion.almacenarSecciondetalleitemadjunto(plsecciondetalleitemadjunto);
            
            UtilMSG.addSuccessMsg("Adjunto almacenado correctamente.");
            UtilJSF.setBean("planItemadjunto", new PlanSeccionDetalleItemAdjuntos(), UtilJSF.SESSION_SCOPE);
            
            this.plansecciondetalleitemadjuntos=new PlanSeccionDetalleItemAdjuntos();
            this.plansecciondetalleitemadjuntospk=new PlanSeccionDetalleItemAdjuntosPK();            
            this.cargarPlansecciondetalleitemadjuntoList();
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }        
    }
    
    public void eliminarPlansecciondetalleitemadjunto(){
        try{
            plansecciondetalleitemadjuntos = (PlanSeccionDetalleItemAdjuntos) UtilJSF.getBean("varPlanitemadjunto");     
            
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();
            
            gestorPlanseccion.eliminarPlansecciondetalleitemadjunto(plansecciondetalleitemadjuntos);
            
            UtilMSG.addSuccessMsg("Adjunto eliminado correctamente.");            
            this.cargarPlansecciondetalleitemadjuntoList();
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

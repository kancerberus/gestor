/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.modelo.Sesion;
import com.gestor.seguimiento.controlador.GestorPlanSeccionDetalle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author franj
 */
@ManagedBean(name = "uiPlandetalle")
@SessionScoped
public class UIPlanDetalle implements Serializable{
    private GestorPlanSeccionDetalle gestorPlanSeccionDetalle;
    private PlanSeccionDetalle plansecciondetalle = new PlanSeccionDetalle();    
    private PlanSeccionDetalleTexto plansecciondetalletexto = new PlanSeccionDetalleTexto();
    private PlanSeccionDetalleTextoPK plansecciondetalletextopk = new PlanSeccionDetalleTextoPK();
    private PlanSeccionDetallePK plansecciondetallepk = new PlanSeccionDetallePK();
    private List<PlanSeccionDetalle> plansecciondetalleList = new ArrayList<>();    
    
    
    public UIPlanDetalle(){  
        
    }   
    
    public void guardarSeccionDetalle(){              
        try {              
            GestorPlanSeccionDetalle gestorPlansecciondetalle= new GestorPlanSeccionDetalle();           
            PlanSeccion ps=(PlanSeccion) UtilJSF.getBean("Planseccion");            
            
            if(plansecciondetallepk.getCodSeccionDetalle()==0){
                plansecciondetallepk.setCodSeccion(1);
            }
            plansecciondetallepk.setCodSeccionDetalle(Integer.parseInt(plansecciondetalle.getNumeral()));
            
            PlanSeccionDetalle plsecciondetalle = new PlanSeccionDetalle(new PlanSeccionDetallePK(ps.getPlanSeccionPK().getCodigoEstablecimiento(), ps.getPlanSeccionPK().getCodTitulo()
                    , ps.getPlanSeccionPK().getCodSeccion() , plansecciondetallepk.getCodSeccionDetalle()) , plansecciondetalle.getNumeral(), plansecciondetalle.getNombre()
            );            
            
            gestorPlansecciondetalle.validarPlansecciondetalle(plsecciondetalle);
            gestorPlansecciondetalle.almacenarSecciondetalle(plsecciondetalle); 
            
            this.plansecciondetalleList.add(plansecciondetalleList.size(), plsecciondetalle);            
                        
            UtilMSG.addSuccessMsg("Seccion almacenado correctamente.");                                                
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    public GestorPlanSeccionDetalle getGestorPlanSeccionDetalle() {
        return gestorPlanSeccionDetalle;
    }

    public void setGestorPlanSeccionDetalle(GestorPlanSeccionDetalle gestorPlanSeccionDetalle) {
        this.gestorPlanSeccionDetalle = gestorPlanSeccionDetalle;
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

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
import com.gestor.seguimiento.controlador.GestorPlanSeccion;
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
@ManagedBean(name = "uiPlanseccion")
@SessionScoped
public class UIPlanSeccion implements Serializable{    
    private GestorPlanSeccion gestorPlanSeccion;
    private PlanSeccion planseccion = new PlanSeccion();
    private PlanSeccionTexto plansecciontexto = new PlanSeccionTexto();
    private PlanSeccionPK planseccionpk = new PlanSeccionPK();
    private PlanSeccionTextoPK plansecciontextopk = new PlanSeccionTextoPK();
    private List<PlanSeccion> planseccionList = new ArrayList<>();    
    private List<PlanSeccionTexto> plansecciontextoList = new ArrayList<>();    
    
    Sesion s = (Sesion) UtilJSF.getBean("sesion");    

    public UIPlanSeccion(){        
    }   
    
    
    public void subirItemPlanseccion() {                               
        planseccion = (PlanSeccion) UtilJSF.getBean("varPlanseccion");
        UtilJSF.setBean("Planseccion", planseccion, UtilJSF.SESSION_SCOPE);      
        this.cargarPlansecciontexto();
           
    }
    
    public void cargarPlanseccion() {
        
        try {               
            PlanTitulo pt=(PlanTitulo) UtilJSF.getBean("varPlantitulo");
            this.planseccionList=new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();            
            this.planseccionList.addAll((Collection<? extends PlanSeccion>) gestorPlanSeccion.cargarListaSeccion(pt));                        
            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarPlansecciontexto() {        
        try {
            plansecciontexto.setTexto("");
            this.plansecciontextoList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.plansecciontextoList.addAll((Collection<? extends PlanSeccionTexto>) gestorPlanSeccion.cargarPlanSecciontextoList(planseccion));                                 
            plansecciontexto.setTexto(plansecciontextoList.get(0).getTexto());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }


    public void guardarSeccion(){              
        try {              
            GestorPlanSeccion gestorPlanseccion= new GestorPlanSeccion();           
            PlanTitulo pt=(PlanTitulo) UtilJSF.getBean("Plantitulo");            
            planseccionpk.setCodTitulo(pt.getPlanTituloPK().getCodTitulo());
            
            planseccionpk.setCodSeccion(Integer.parseInt(planseccion.getNumeral()));
            if(planseccionpk.getCodSeccion()==0){
                planseccionpk.setCodSeccion(1);
            }
            
            planseccion.setNumeral(Integer.toString(planseccionpk.getCodSeccion()));            
            
            PlanSeccion plseccion = new PlanSeccion(new PlanSeccionPK(
            s.getEstablecimiento().getCodigoEstablecimiento(), planseccionpk.getCodTitulo(), planseccionpk.getCodSeccion()),planseccion.getNombre(),planseccion.getNumeral(), planseccion.getImagen()
            );
            
            gestorPlanseccion.validarPlanseccion(plseccion);
            gestorPlanseccion.almacenarSeccion(plseccion); 
            
            this.planseccionList.add(planseccionList.size(), plseccion);            
                        
            UtilMSG.addSuccessMsg("Seccion almacenado correctamente."); 
            UtilJSF.setBean("Planseccion", new PlanSeccion(), UtilJSF.SESSION_SCOPE);

            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
    
    public void modificarSeccion(){   
        
        try {                        
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                         
            PlanTitulo pt=(PlanTitulo) UtilJSF.getBean("Plantitulo");     

            
            PlanSeccion plseccion = new PlanSeccion(new PlanSeccionPK(s.getEstablecimiento().getCodigoEstablecimiento(), pt.getPlanTituloPK().getCodTitulo() ,
                    planseccion.getPlanSeccionPK().getCodSeccion()) , planseccion.getNombre(), planseccion.getNumeral(), planseccion.getImagen()
            );
            gestorPlanseccion.modificarSeccion(plseccion);
            
            UtilMSG.addSuccessMsg("Titulo Seccion modificado correctamente.");
            UtilJSF.setBean("Planseccion", new PlanSeccion(), UtilJSF.SESSION_SCOPE);  
            
            
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
        
    }    
    
    public void guardarSecciontexto(){                
         
        try {                        
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                                    
            plansecciontextopk.setCodSeccionTexto(1);    
            
            PlanSeccionTexto plsecciontexto = new PlanSeccionTexto(new PlanSeccionTextoPK(planseccion.getPlanSeccionPK().getCodigoEstablecimiento(),
            planseccion.getPlanSeccionPK().getCodTitulo(), planseccion.getPlanSeccionPK().getCodSeccion(), plansecciontextopk.getCodSeccionTexto()), plansecciontexto.getTexto()
            );
            gestorPlanseccion.almacenarSecciontexto(plsecciontexto);            
            
            UtilMSG.addSuccessMsg("Texto almacenado correctamente.");
            UtilJSF.setBean("Planseccion", new PlanSeccion(), UtilJSF.SESSION_SCOPE); 
            this.cargarPlansecciontexto();
            
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
        
    }

    public PlanSeccionTexto getPlansecciontexto() {
        return plansecciontexto;
    }

    public void setPlansecciontexto(PlanSeccionTexto plansecciontexto) {
        this.plansecciontexto = plansecciontexto;
    }

    public PlanSeccionTextoPK getPlansecciontextopk() {
        return plansecciontextopk;
    }

    public void setPlansecciontextopk(PlanSeccionTextoPK plansecciontextopk) {
        this.plansecciontextopk = plansecciontextopk;
    }

    public List<PlanSeccionTexto> getPlansecciontextoList() {
        return plansecciontextoList;
    }

    public void setPlansecciontextoList(List<PlanSeccionTexto> plansecciontextoList) {
        this.plansecciontextoList = plansecciontextoList;
    }
        
    public String administrarPlanmaestro(){
        return ("/seguimiento/admin-plan-maestro.xhtml?faces-redirect=true");
    }    

    public List<PlanSeccion> getPlanseccionList() {
        return planseccionList;
    }

    public void setPlanseccionList(List<PlanSeccion> planseccionList) {
        this.planseccionList = planseccionList;
    }

    public PlanSeccion getPlanseccion() {
        return planseccion;
    }

    public void setPlanseccion(PlanSeccion planseccion) {
        this.planseccion = planseccion;
    }

    public PlanSeccionPK getPlanseccionpk() {
        return planseccionpk;
    }

    public void setPlanseccionpk(PlanSeccionPK planseccionpk) {
        this.planseccionpk = planseccionpk;
    }
    
}
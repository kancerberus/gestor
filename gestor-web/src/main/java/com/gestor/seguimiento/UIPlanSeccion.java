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
import com.gestor.modelo.Sesion;
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
@ManagedBean(name = "uiPlanseccion")
@SessionScoped
public class UIPlanSeccion implements Serializable{    
    private GestorPlanSeccion gestorPlanSeccion;    
    private PlanSeccion planseccion = new PlanSeccion();
    private PlanSeccionTexto plansecciontexto = new PlanSeccionTexto();
    private PlanSeccionPK planseccionpk = new PlanSeccionPK();
    private PlanSeccionTextoPK plansecciontextopk = new PlanSeccionTextoPK();
    private PlanSeccionAdjuntos planseccionadjuntos= new PlanSeccionAdjuntos();
    private PlanSeccionAdjuntosPK planseccionadjuntospk= new PlanSeccionAdjuntosPK();
    private AdjuntosCategoria adjuntoscategoria = new AdjuntosCategoria();    
    private AdjuntosCategoriaTipo adjuntosCategoriaTipo = new AdjuntosCategoriaTipo();    
    private List<PlanSeccion> planseccionList = new ArrayList<>();    
    private List<PlanSeccionTexto> plansecciontextoList = new ArrayList<>();    
    private List<PlanSeccionAdjuntos> planseccionadjuntosList = new ArrayList<>();   
    private List<AdjuntosCategoria> adjuntosCategorias = new ArrayList<>();    
    
    Sesion s = (Sesion) UtilJSF.getBean("sesion");    

    @PostConstruct
    public void init() {           
        this.cargarPlanseccion();          
    }            
    
    public UIPlanSeccion(){        
    }   
    
    public void cargarAdjuntosCategoriaTipo() {
        try {
            GestorAdjuntosCategoria gestorAdjuntosCategoria = new GestorAdjuntosCategoria();    
                        
            adjuntosCategorias = new ArrayList<>();
            adjuntosCategorias.addAll(gestorAdjuntosCategoria.cargarListaAdjuntosCategoriapm());            
            
            if(planseccionadjuntos.getAdjuntosCategoria().getCodCategoria() != null){
                planseccionadjuntos.getAdjuntosCategoria().setAdjuntosCategoriaTipoList((List<AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(planseccionadjuntos.getAdjuntosCategoria().getCodCategoria()));                                                
            }            
            
        } catch (Exception e) {            
        }        
    }
    
    public void subirItemPlanseccion() {       
        
        planseccion = (PlanSeccion) UtilJSF.getBean("varPlanseccion");                     
        Integer codSeccion=Integer.parseInt(planseccion.getNumeral().substring(2, 3));
        planseccionpk.setCodSeccion(codSeccion);   
        UtilJSF.setBean("planSeccion", planseccion, UtilJSF.SESSION_SCOPE);                
        this.cargarAdjuntosCategoriaTipo();
        this.cargarPlansecciontexto();
        this.cargarPlanseccionadjuntosList();        
    }
    
    public void subirItemPlanseccionadjunto() {   
        this.cargarAdjuntosCategoriaTipo();
        planseccionadjuntos = (PlanSeccionAdjuntos) UtilJSF.getBean("varPlanseccionadjunto");
        UtilJSF.setBean("planSeccionadjunto", planseccionadjuntos, UtilJSF.SESSION_SCOPE);  
        planseccionadjuntospk.setCodSeccionAdjunto(planseccionadjuntos.getPlanSeccionAdjuntosPK().getCodSeccionAdjunto());
        adjuntoscategoria.setCodCategoria(planseccionadjuntos.getCodCategoria());
        
        
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
    
    public void cargarPlanseccionList() {
        
        try {                        
            PlanTitulo pt=(PlanTitulo) UtilJSF.getBean("planTitulo");            
            this.planseccionList=new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();                   
            this.planseccionList.addAll((Collection<? extends PlanSeccion>) gestorPlanSeccion.cargarListaSeccion(pt));                     
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    
    public void cargarPlansecciontexto() {        
        try {            
            this.plansecciontextoList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.plansecciontextoList.addAll((Collection<? extends PlanSeccionTexto>) gestorPlanSeccion.cargarPlanSecciontextoList(planseccion));                                 
            plansecciontexto.setTexto(plansecciontextoList.get(0).getTexto());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarPlanseccionadjunto() {        
        try {            
            this.planseccionadjuntosList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.planseccionadjuntosList.addAll((Collection<? extends PlanSeccionAdjuntos>) gestorPlanSeccion.cargarPlanSeccionadjuntoList(planseccion));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }    
    
    public void cargarPlanseccionadjuntosList() {        
        try {            
            this.planseccionadjuntosList = new ArrayList<>();
            gestorPlanSeccion = new GestorPlanSeccion();
            this.planseccionadjuntosList.addAll((Collection<? extends PlanSeccionAdjuntos>) gestorPlanSeccion.cargarPlanSeccionadjuntoList(planseccion));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }  

    public void guardarSeccion(){              
        try {                          
            GestorPlanSeccion gestorPlanseccion= new GestorPlanSeccion();           
            PlanTitulo pt=(PlanTitulo) UtilJSF.getBean("planTitulo");            
            planseccionpk.setCodTitulo(pt.getPlanTituloPK().getCodTitulo());
            
            if(planseccionpk.getCodSeccion()==0){
                planseccionpk.setCodSeccion(1);
            }                        

            planseccion.setNumeral(pt.getNumeral()+"."+Integer.toString(planseccionpk.getCodSeccion()));
            PlanSeccion plseccion = new PlanSeccion(new PlanSeccionPK(
            pt.getPlanTituloPK().getCodigoEstablecimiento(), planseccionpk.getCodTitulo(), planseccionpk.getCodSeccion()),planseccion.getNombre(),planseccion.getNumeral(), planseccion.getImagen()
            );            
                       
            
            gestorPlanseccion.validarPlanseccion(plseccion);
            gestorPlanseccion.almacenarSeccion(plseccion); 
            
            UtilJSF.setBean("varPlanseccion", plseccion, UtilJSF.SESSION_SCOPE);
            UtilJSF.setBean("planSeccion", plseccion, UtilJSF.SESSION_SCOPE);
            UtilMSG.addSuccessMsg("Seccion almacenado correctamente.");             
            this.cargarPlanseccionList();
            
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
            PlanTitulo pt=(PlanTitulo) UtilJSF.getBean("planTitulo");                        
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                                    
            plansecciontextopk.setCodSeccionTexto(1);    
            
            PlanSeccionTexto plsecciontexto = new PlanSeccionTexto(new PlanSeccionTextoPK(pt.getPlanTituloPK().getCodigoEstablecimiento(),
            pt.getPlanTituloPK().getCodTitulo(), planseccionpk.getCodSeccion(), plansecciontextopk.getCodSeccionTexto()), plansecciontexto.getTexto()
            );
            gestorPlanseccion.almacenarSecciontexto(plsecciontexto);            
            
            UtilMSG.addSuccessMsg("Texto almacenado correctamente.");            
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
    
    public void eliminarPlanseccionadjunto(){
        try{
            planseccionadjuntos = (PlanSeccionAdjuntos) UtilJSF.getBean("varPlanseccionadjunto");     
            
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();
            
            gestorPlanseccion.eliminarPlanseccionadjunto(planseccionadjuntos);
            
            UtilMSG.addSuccessMsg("Adjunto eliminado correctamente.");            
            this.cargarPlanseccionadjuntosList();
        }catch (Exception e) {
            UtilMSG.addSuccessMsg("Adjunto en uso.");
        }
    }
    
    public void guardarSeccionadjunto(){              
        try {     
            PlanSeccionAdjuntos psa = (PlanSeccionAdjuntos) UtilJSF.getBean("planSeccionadjunto");
                        
            if(psa==null || planseccionadjuntospk.getCodSeccionAdjunto()==0){
                planseccionadjuntospk.setCodSeccionAdjunto(planseccionadjuntosList.size()+1);
            }
            
            PlanTitulo pt=(PlanTitulo) UtilJSF.getBean("planTitulo");                        
            GestorPlanSeccion gestorPlanseccion = new GestorPlanSeccion();                                    
            
            PlanSeccionAdjuntos plseccionadjunto = new PlanSeccionAdjuntos(new PlanSeccionAdjuntosPK(pt.getPlanTituloPK().getCodigoEstablecimiento(), 
                planseccion.getPlanSeccionPK().getCodTitulo(), planseccion.getPlanSeccionPK().getCodSeccion() ,planseccionadjuntospk.getCodSeccionAdjunto()),  planseccionadjuntos.getAdjuntosCategoria().getCodCategoria(),
                planseccionadjuntos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo(), planseccionadjuntos.getTitulo(), planseccionadjuntos.getDescripcion(), planseccionadjuntos.getDocumento()                    
            );
           gestorPlanseccion.almacenarSeccionadjunto(plseccionadjunto);
            
            UtilMSG.addSuccessMsg("Adjunto almacenado correctamente.");
            UtilJSF.setBean("planSeccion", new PlanSeccion(), UtilJSF.SESSION_SCOPE);
            this.cargarPlanseccionadjuntosList();            
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

    public AdjuntosCategoriaTipo getAdjuntosCategoriaTipo() {
        return adjuntosCategoriaTipo;
    }

    public void setAdjuntosCategoriaTipo(AdjuntosCategoriaTipo adjuntosCategoriaTipo) {
        this.adjuntosCategoriaTipo = adjuntosCategoriaTipo;
    }

    public PlanSeccionAdjuntos getPlanseccionadjuntos() {
        return planseccionadjuntos;
    }

    public void setPlanseccionadjuntos(PlanSeccionAdjuntos planseccionadjuntos) {
        this.planseccionadjuntos = planseccionadjuntos;
    }

    public PlanSeccionAdjuntosPK getPlanseccionadjuntospk() {
        return planseccionadjuntospk;
    }

    public void setPlanseccionadjuntospk(PlanSeccionAdjuntosPK planseccionadjuntospk) {
        this.planseccionadjuntospk = planseccionadjuntospk;
    }

    public AdjuntosCategoria getAdjuntoscategoria() {
        return adjuntoscategoria;
    }

    public void setAdjuntoscategoria(AdjuntosCategoria adjuntoscategoria) {
        this.adjuntoscategoria = adjuntoscategoria;
    }

    public List<PlanSeccionAdjuntos> getPlanseccionadjuntosList() {
        return planseccionadjuntosList;
    }

    public void setPlanseccionadjuntosList(List<PlanSeccionAdjuntos> planseccionadjuntosList) {
        this.planseccionadjuntosList = planseccionadjuntosList;
    }
    
    public void limpiarBean(){
      planseccion= new PlanSeccion();
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.Ciclo;
import com.gestor.gestor.Seccion;
import com.gestor.gestor.SeccionDetalle;
import com.gestor.gestor.SeccionDetalleItems;
import com.gestor.gestor.SeccionDetalleItemsAyuda;
import com.gestor.publico.controlador.GestorEstandar;
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
@ManagedBean(name = "uiEstandar")
@SessionScoped
public class UIEstandar implements Serializable {

    private GestorEstandar gestorEstandar;
    
    private Ciclo ciclo = new Ciclo();
    private Seccion seccion = new Seccion();
    private SeccionDetalle secciondetalle = new SeccionDetalle();
    private SeccionDetalleItems secciondetalleitems = new SeccionDetalleItems();
    private SeccionDetalleItemsAyuda secciondetalleitemsayuda = new SeccionDetalleItemsAyuda();  
    
    private List<Ciclo> cicloList = new ArrayList<>();
    private List<Seccion> seccionList = new ArrayList<>();
    private List<SeccionDetalle> secciondetalleList = new ArrayList<>();
    private List<SeccionDetalleItems> secciondetalleitemsList = new ArrayList<>();
    private List<SeccionDetalleItems> secciondetalleitemsnombreList = new ArrayList<>();
    private List<SeccionDetalleItemsAyuda> secciondetalleitemsayudaList = new ArrayList<>();
        
    public String nomc=null;
    public String nomsec=null;
    public String nomsdetalle=null;
    public String nomsditem=null;
    public String mlegal;
    public String criterio;
    public String mdeverif;
    public String coday;
    public String sub=null;
    public String numeral=null;

    @PostConstruct
    public void init() {             
        this.cargarSecciondetalleitems();
     
    }
    
    public void limpiar() {
        this.secciondetalleitems = new SeccionDetalleItems();      
        this.secciondetalleitemsList.clear();
        this.nomc= null;
        this.nomsec=null;
        this.nomsdetalle=null;
        this.nomsditem=null;
        this.criterio=null;
        this.mlegal=null;
        this.mdeverif=null;
        this.cargarSecciondetalleitems();
    }
    
    public void cargarSecciondetalleitems() {
        try {
            this.secciondetalleitemsList = new ArrayList<>();
            gestorEstandar = new GestorEstandar();            
            this.secciondetalleitemsList.addAll((Collection<? extends SeccionDetalleItems>) gestorEstandar.cargarListaSecciondetalleitemsnumeral());            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarCicloList() {   
        sub = secciondetalleitems.getNumeral();
        try {                        
            this.cicloList = new ArrayList<>();
            gestorEstandar = new GestorEstandar();              
            numeral = sub.substring(0, 1);            
            this.cicloList.addAll((Collection<? extends Ciclo>) gestorEstandar.cargarCicloList(numeral));
            nomc = cicloList.get(0).getNombre();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarSeccionList() {        
        sub = secciondetalleitems.getNumeral();
        try {            
            
            this.seccionList = new ArrayList<>();
            gestorEstandar = new GestorEstandar();
            numeral = sub.substring(0, 3);
            this.seccionList.addAll((Collection<? extends Seccion>) gestorEstandar.cargarSeccionList(numeral));
            nomsec = seccionList.get(0).getNombre();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarSecciondetalleList() {
            sub = secciondetalleitems.getNumeral();         
        try {            
            
            this.secciondetalleList = new ArrayList<>();
            gestorEstandar = new GestorEstandar();  
            if(sub.length()==7){
            numeral = sub.substring(0, 5);            
            }
            if(sub.length()==8){
                numeral=sub.substring(0, 6);
            }
            this.secciondetalleList.addAll((Collection<? extends SeccionDetalle>) gestorEstandar.cargarSecciondetalleList(numeral));            
            nomsdetalle = secciondetalleList.get(0).getNombre();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    public void cargarSecciondetalleitemnombreList() {        
        sub = secciondetalleitems.getNumeral();
        try {            
            
            this.secciondetalleitemsnombreList = new ArrayList<>();
            gestorEstandar = new GestorEstandar();  
            if(sub.length()==7){
            numeral = sub.substring(0, 7);            
            }
            if(sub.length()==8){
                numeral=sub.substring(0, 8);
            }
            this.secciondetalleitemsnombreList.addAll((Collection<? extends SeccionDetalleItems>) gestorEstandar.cargarSecciondetalleitemsList(numeral));            
            nomsditem = secciondetalleitemsnombreList.get(0).getNombre();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarSecciondetalleitemayudaList() {           
        mlegal=null;criterio=null;mdeverif=null;
        try {                                    
            this.secciondetalleitemsayudaList = new ArrayList<>();        
            gestorEstandar = new GestorEstandar();  
            this.secciondetalleitemsayudaList.addAll((Collection<? extends SeccionDetalleItemsAyuda>) gestorEstandar.cargarSecciondetalleitemsayudaList(nomc,nomsec,nomsdetalle,nomsditem));                  
            
            for(int i=0;i<secciondetalleitemsayudaList.size();i++){
                if(secciondetalleitemsayudaList.get(i).getNombre().equals("CRITERIO")){
                criterio = secciondetalleitemsayudaList.get(i).getDefinicion();                    
                }
                if(secciondetalleitemsayudaList.get(i).getNombre().equals("MARCO LEGAL")){
                mlegal = secciondetalleitemsayudaList.get(i).getDefinicion();
                }
                if(secciondetalleitemsayudaList.get(i).getNombre().equals("MODO DE VERIFICACIÓN")){
                mdeverif = secciondetalleitemsayudaList.get(i).getDefinicion();    
                }   
            }
            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }   
        

    }
    
    public void guardarEstandar() {
        String numeral1=null;
        String numeral2=null;
        String numeral3=null;
        
        for(int i=0;i<secciondetalleitemsayudaList.size();i++){
                 
                if(secciondetalleitemsayudaList.get(i).getNumeral().substring(8, 9).equals("1")){                
                numeral1=secciondetalleitemsayudaList.get(i).getNumeral();
                }
                if(secciondetalleitemsayudaList.get(i).getNumeral().substring(8, 9).equals("2")){                
                numeral2=secciondetalleitemsayudaList.get(i).getNumeral();
                }
                if(secciondetalleitemsayudaList.get(i).getNumeral().substring(8, 9).equals("3")){                
                numeral3=secciondetalleitemsayudaList.get(i).getNumeral();
                }   
            }
        
        try {                  
            GestorEstandar gestorEstandar = new GestorEstandar();                         
            gestorEstandar.almacenaralmacenarSeccionDetalleItemsAyuda(secciondetalleitems.getNumeral(),secciondetalleitems.getSeccionDetalleItemsPK().getCodSeccion(), secciondetalleitems.getSeccionDetalleItemsPK().getCodDetalle(),secciondetalleitems.getSeccionDetalleItemsPK().getCodItem(), secciondetalleitems.getSeccionDetalleItemsPK().getCodCiclo(), numeral1, numeral2, numeral3,criterio,mlegal,mdeverif );
            UtilMSG.addSuccessMsg("Estandar almacenado correctamente.");
            UtilJSF.setBean("secciondetalleitemsayuda", new SeccionDetalleItemsAyuda(), UtilJSF.SESSION_SCOPE);            

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }

    }

    public List<SeccionDetalleItemsAyuda> getSecciondetalleitemsayudaList() {
        return secciondetalleitemsayudaList;
    }

    public void setSecciondetalleitemsayudaList(List<SeccionDetalleItemsAyuda> secciondetalleitemsayudaList) {
        this.secciondetalleitemsayudaList = secciondetalleitemsayudaList;
    }

    public String getMlegal() {
        return mlegal;
    }

    public void setMlegal(String mlegal) {
        this.mlegal = mlegal;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getMdeverif() {
        return mdeverif;
    }

    public void setMdeverif(String mdeverif) {
        this.mdeverif = mdeverif;
    }
    

    public String getNomsec() {
        return nomsec;
    }

    public void setNomsec(String nomsec) {
        this.nomsec = nomsec;
    }

    public String getNomc() {
        return nomc;
    }

    public void setNomc(String nomc) {
        this.nomc = nomc;
    }

    public String getNomsdetalle() {
        return nomsdetalle;
    }

    public void setNomsdetalle(String nomsdetalle) {
        this.nomsdetalle = nomsdetalle;
    }

    public String getNomsditem() {
        return nomsditem;
    }

    public void setNomsditem(String nomsditem) {
        this.nomsditem = nomsditem;
    }
 
    public SeccionDetalleItems getSecciondetalleitems() {
        return secciondetalleitems;
    }

    public void setSecciondetalleitems(SeccionDetalleItems secciondetalleitems) {
        this.secciondetalleitems = secciondetalleitems;
    }
    
    public SeccionDetalle getSecciondetalle() {
        return secciondetalle;
    }

    public void setSecciondetalle(SeccionDetalle secciondetalle) {
        this.secciondetalle = secciondetalle;
    }        

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public GestorEstandar getGestorEstandar() {
        return gestorEstandar;
    }

    public void setGestorEstandar(GestorEstandar gestorEstandar) {
        this.gestorEstandar = gestorEstandar;
    }

    public List<Ciclo> getCicloList() {
        return cicloList;
    }

    public void setCicloList(List<Ciclo> cicloList) {
        this.cicloList = cicloList;
    }

    public SeccionDetalleItemsAyuda getSecciondetalleitemsayuda() {
        return secciondetalleitemsayuda;
    }

    public void setSecciondetalleitemsayuda(SeccionDetalleItemsAyuda secciondetalleitemsayuda) {
        this.secciondetalleitemsayuda = secciondetalleitemsayuda;
    }

    public List<Seccion> getSeccionList() {
        return seccionList;
    }

    public void setSeccionList(List<Seccion> seccionList) {
        this.seccionList = seccionList;
    }

    public List<SeccionDetalle> getSecciondetalleList() {
        return secciondetalleList;
    }

    public void setSecciondetalleList(List<SeccionDetalle> secciondetalleList) {
        this.secciondetalleList = secciondetalleList;
    }

    public List<SeccionDetalleItems> getSecciondetalleitemsList() {
        return secciondetalleitemsList;
    }

    public void setSecciondetalleitemsList(List<SeccionDetalleItems> secciondetalleitemsList) {
        this.secciondetalleitemsList = secciondetalleitemsList;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.controlador.GestorAdjuntosCategoria;
import com.gestor.gestor.controlador.GestorSeccionDetalleItems;
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
@ManagedBean(name = "uiCategoria")
@SessionScoped
public class UICategoria implements Serializable{
    private AdjuntosCategoria categoria = new AdjuntosCategoria();
    private AdjuntosCategoriaTipo tipo= new AdjuntosCategoriaTipo();    
    private SeccionDetalleItemsAdjuntosCategorias sdiaca= new SeccionDetalleItemsAdjuntosCategorias();
    private List<AdjuntosCategoria> adjuntosCategorias = new ArrayList<>();   
    private List<SeccionDetalleItems> sdipk= new ArrayList<>();
    private List<AdjuntosCategoriaTipo> adjuntosCategoriaTipos= new ArrayList<>();
    private GestorAdjuntosCategoria gestorAdjuntosCategoria;   
    private GestorSeccionDetalleItems gestorSeccionDetalleItems;    
    private List<SeccionDetalleItems> secciondetalleitemsList = new ArrayList<>();
    private GestorEstandar gestorEstandar;
    
    @PostConstruct
    public void init() {
        this.cargarCategoria();         
        this.cargarSecciondetalleitems();
    }
    
    private void cargarCategoria() {
        try {
            this.adjuntosCategorias = new ArrayList<>();
            gestorAdjuntosCategoria = new GestorAdjuntosCategoria();
            this.adjuntosCategorias.addAll((Collection<? extends AdjuntosCategoria>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoria());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarTipo() {
        try {
            categoria = (AdjuntosCategoria) UtilJSF.getBean("categoria");                
            this.adjuntosCategoriaTipos = new ArrayList<>();
            gestorAdjuntosCategoria = new GestorAdjuntosCategoria();
            this.adjuntosCategoriaTipos.addAll((Collection<? extends AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(categoria.getCodCategoria()));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarTipoList() {
        try {
            categoria = (AdjuntosCategoria) UtilJSF.getBean("categoria");                
            this.adjuntosCategoriaTipos = new ArrayList<>();
            gestorAdjuntosCategoria = new GestorAdjuntosCategoria();
            this.adjuntosCategoriaTipos.addAll((Collection<? extends AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(categoria.getCodCategoria()));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
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
    
    public void subirItemTipo() {
        tipo = (AdjuntosCategoriaTipo) UtilJSF.getBean("varTipo");                
        UtilJSF.setBean("tipo", tipo, UtilJSF.SESSION_SCOPE);        
    }    
    
    
    public void subirItemCategoria() {        
        categoria = (AdjuntosCategoria) UtilJSF.getBean("varCategoria");
        UtilJSF.setBean("varCategoria", categoria, UtilJSF.SESSION_SCOPE); 
        UtilJSF.setBean("categoria", categoria, UtilJSF.SESSION_SCOPE);         
        
        this.cargarCategoriaList();        
        this.cargarSecciondetalleitems();
    }
    
    public void cargarSecciondetalleitemsList() {
        try {
            this.secciondetalleitemsList = new ArrayList<>();
            gestorEstandar = new GestorEstandar();            
            this.secciondetalleitemsList.addAll((Collection<? extends SeccionDetalleItems>) gestorEstandar.cargarListaSecciondetalleitemsnumeral());            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        categoria=new AdjuntosCategoria();        
    }
    
    public void limpiartipo() {
        tipo= new AdjuntosCategoriaTipo();        
    }
    
    
    public void dialogoTipo() {
        try {
            
            tipo=new AdjuntosCategoriaTipo();
            Dialogo dialogo = new Dialogo("dialogos/categoriaTipo.xhtml", "Crear Tipo", "clip", Dialogo.WIDTH_AUTO);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            categoria = (AdjuntosCategoria) UtilJSF.getBean("varCategoria");                
            UtilJSF.setBean("categoria", categoria, UtilJSF.SESSION_SCOPE);            
            this.cargarTipo();         
            this.cargarCategoriaList();
            this.cargarSecciondetalleitemsList();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void guardarTipo(){    
        try {
            categoria = (AdjuntosCategoria) UtilJSF.getBean("categoria");                            

            if(tipo.getAdjuntosCategoriaTipoPK().getCodCategoriaTipo()==null){
                tipo.getAdjuntosCategoriaTipoPK().setCodCategoriaTipo(adjuntosCategoriaTipos.size()+1);
            }
            
            /*for(int i=0;i<adjuntosCategoriaTipos.size();i++){
                if(tipo.getAdjuntosCategoriaTipoPK().getCodCategoriaTipo() != adjuntosCategoriaTipos.get(i).getAdjuntosCategoriaTipoPK().getCodCategoriaTipo()){
                    tipo.getAdjuntosCategoriaTipoPK().setCodCategoriaTipo(adjuntosCategoriaTipos.size()+1);                    
                }
            }*/
            
            AdjuntosCategoriaTipo tip= new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(categoria.getCodCategoria(), tipo.getAdjuntosCategoriaTipoPK().getCodCategoriaTipo())  , tipo.getNombre(), tipo.getDescripcion());            
            gestorAdjuntosCategoria.validarTipo(tip); 
            gestorAdjuntosCategoria.almacenarTipo(tip); 
            
            UtilMSG.addSuccessMsg("Tipo almacenado correctamente.");
            UtilJSF.setBean("tipo", new AdjuntosCategoriaTipo(), UtilJSF.SESSION_SCOPE);            
            this.cargarTipoList();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    private void cargarCategoriaList() {
        try {
            this.adjuntosCategorias = new ArrayList<>();
            gestorAdjuntosCategoria = new GestorAdjuntosCategoria();
            this.adjuntosCategorias.addAll((Collection<? extends AdjuntosCategoria>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoria());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }   

    
    public void guardarCategoria() {
        try {              
            if(categoria==null){
            categoria=new AdjuntosCategoria();
            }
                        
            if(categoria.getCodCategoria()==null){                
                categoria.setCodCategoria(1);
            }
            
            for(int i=0;i<adjuntosCategorias.size();i++){
                if(categoria.getCodCategoria() != adjuntosCategorias.get(i).getCodCategoria()){
                    categoria.setCodCategoria(adjuntosCategorias.size()+1);
                }
            }        
            this.sdipk = new ArrayList<>();
            gestorSeccionDetalleItems= new GestorSeccionDetalleItems();
            this.sdipk.addAll((Collection<? extends SeccionDetalleItems>) gestorSeccionDetalleItems.buscarNumeral(categoria.getSecciondetalleitems().getNumeral()));
            
                        
            AdjuntosCategoria cat = new AdjuntosCategoria(categoria.getCodCategoria(), categoria.getNombre(),
            categoria.getDescripcion(), categoria.getMesesVigencia());                        
            
            
            
            SeccionDetalleItemsAdjuntosCategorias sdiac= new SeccionDetalleItemsAdjuntosCategorias(sdipk.get(0).getSeccionDetalleItemsPK().getCodCiclo(), 
            sdipk.get(0).getSeccionDetalleItemsPK().getCodSeccion(), sdipk.get(0).getSeccionDetalleItemsPK().getCodDetalle(), sdipk.get(0).getSeccionDetalleItemsPK().getCodItem(), sdiaca.getCodCategoria());
            
            
            gestorAdjuntosCategoria.validarCategoria(cat); 
            gestorAdjuntosCategoria.almacenarCategoria(cat, sdiac);

            UtilMSG.addSuccessMsg("Categoria almacenada correctamente.");
            UtilJSF.setBean("categoria", new AdjuntosCategoria(), UtilJSF.SESSION_SCOPE);            
            this.cargarCategoriaList();
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }

    }
    
    public void eliminarCategoria(){
        try {
            categoria = (AdjuntosCategoria) UtilJSF.getBean("varCategoria");
            gestorAdjuntosCategoria.eliminarCategoria(categoria.getCodCategoria());
            this.cargarCategoriaList();
            this.cargarSecciondetalleitemsList();
            
            UtilMSG.addSuccessMsg("Categoria eliminada.");
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void eliminarTipo(){
        try {
            tipo= (AdjuntosCategoriaTipo) UtilJSF.getBean("varTipo");            
            gestorAdjuntosCategoria.eliminarTipo(tipo);
            
            
            UtilMSG.addSuccessMsg("Tipo eliminado.");
            this.cargarCategoriaList();
            this.cargarTipoList();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public AdjuntosCategoriaTipo getTipo() {
        return tipo;
    }

    public void setTipo(AdjuntosCategoriaTipo tipo) {
        this.tipo = tipo;
    }

    public List<AdjuntosCategoriaTipo> getAdjuntosCategoriaTipos() {
        return adjuntosCategoriaTipos;
    }

    public void setAdjuntosCategoriaTipos(List<AdjuntosCategoriaTipo> adjuntosCategoriaTipos) {
        this.adjuntosCategoriaTipos = adjuntosCategoriaTipos;
    }

    

    public AdjuntosCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(AdjuntosCategoria categoria) {
        this.categoria = categoria;
    }

    public List<AdjuntosCategoria> getAdjuntosCategorias() {
        return adjuntosCategorias;
    }

    public void setAdjuntosCategorias(List<AdjuntosCategoria> adjuntosCategorias) {
        this.adjuntosCategorias = adjuntosCategorias;
    }

    public List<SeccionDetalleItems> getSecciondetalleitemsList() {
        return secciondetalleitemsList;
    }

    public void setSecciondetalleitemsList(List<SeccionDetalleItems> secciondetalleitemsList) {
        this.secciondetalleitemsList = secciondetalleitemsList;
    }
    
}

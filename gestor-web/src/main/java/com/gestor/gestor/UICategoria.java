/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.controlador.GestorAdjuntosCategoria;
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
    private List<AdjuntosCategoria> adjuntosCategorias = new ArrayList<>();    
    private GestorAdjuntosCategoria gestorAdjuntosCategoria;       
    
    @PostConstruct
    public void init() {
        this.cargarCategoria();
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
    
    public void subirItemCategoria() {
        categoria = (AdjuntosCategoria) UtilJSF.getBean("varCategoria");
        adjuntosCategorias.remove(categoria);
    }
    
    public void codigoCategoria(){
        try {
            if(categoria.getCodCategoria()==0){
                categoria.setCodCategoria(1);
            }else{
                for(int i=0;i<adjuntosCategorias.size();i++){                
                    if(categoria.getCodCategoria() == adjuntosCategorias.get(i).getCodCategoria()){
                        categoria.setCodCategoria(categoria.getCodCategoria());
                    }else{
                        categoria.setCodCategoria(categoria.getCodCategoria()+1);
                    }                
                }
            }                      
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }        
    }
    
    public void guardarCategoria() {
        try {
            this.codigoCategoria();
            AdjuntosCategoria cat = this.getCategoria();
            
            
            
            
            gestorAdjuntosCategoria.validarCategoria(cat);
            gestorAdjuntosCategoria.almacenarCategoria(cat);

            UtilMSG.addSuccessMsg("Categoria almacenada correctamente.");
            UtilJSF.setBean("categoria", new AdjuntosCategoria(), UtilJSF.SESSION_SCOPE);            

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }

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
    
    

    
}

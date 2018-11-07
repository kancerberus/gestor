/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;
import com.gestor.controller.GestorGeneral;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.publico.controlador.GestorResponsable;
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

@ManagedBean(name = "uiResponsable")
@SessionScoped
public class UIResponsable implements Serializable{
    private GestorResponsable gestorResponsable;
    private GestorGeneral gestorGeneral;
    private Responsable responsable = new Responsable();
    
    private List<Responsable> responsableList = new ArrayList<>();    
    
    @PostConstruct
    public void init() {
        this.cargarResponsable();        
    }

    public void subirItemResponsable() {
        responsable = (Responsable) UtilJSF.getBean("varResponsable");
        responsableList.remove(responsable);
    }
    
    private void cargarResponsable() {
        try {
            this.responsableList = new ArrayList<>();
            gestorResponsable = new GestorResponsable();
            this.responsableList.addAll((Collection<? extends Responsable>) gestorResponsable.cargarListaResponsable());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    
    public void guardarResponsable() {
        try {
            Responsable res = this.getResponsable();
            GestorResponsable gestorResponsable = new GestorResponsable();            

            gestorResponsable.validarResponsable(res);            
            gestorResponsable.almacenarResponsable(res);

            UtilMSG.addSuccessMsg("Responsable almacenado correctamente.");
            UtilJSF.setBean("responsable", new Responsable(), UtilJSF.SESSION_SCOPE);
            this.limpiar();

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }

    }
    
    public void limpiar() {
        this.responsable = new Responsable();
        this.cargarResponsable();

    }

    public GestorResponsable getGestorResponsable() {
        return gestorResponsable;
    }

    public void setGestorResponsable(GestorResponsable gestorResponsable) {
        this.gestorResponsable = gestorResponsable;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public GestorGeneral getGestorGeneral() {
        return gestorGeneral;
    }

    public void setGestorGeneral(GestorGeneral gestorGeneral) {
        this.gestorGeneral = gestorGeneral;
    }

    public List<Responsable> getResponsableList() {
        return responsableList;
    }

    public void setResponsableList(List<Responsable> responsableList) {
        this.responsableList = responsableList;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import com.gestor.controller.GestorGeneral;
import com.gestor.controller.Propiedades;
import com.gestor.entity.UtilArchivo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorMunicipios;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author juliano
 */
@ManagedBean(name = "uiEstablecimiento")
@SessionScoped

public class UIEstablecimiento implements Serializable {   
    private UploadedFile file;
    private GestorEstablecimiento gestorEstablecimiento;
    private GestorGeneral gestorGeneral;
    private GestorMunicipios gestorMunicipios;
    private Establecimiento establecimiento = new Establecimiento();
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Municipios> municipiosList = new ArrayList<>();    
    

    @PostConstruct
    public void init() {
        this.cargarEstablecimientosInstitucion();
        this.cargarMunicipios();             
    }

    public void subirItemEstablecimiento() {
        establecimiento = (Establecimiento) UtilJSF.getBean("varEstablecimiento");
        establecimientoList.remove(establecimiento);
    }
    
    public void guardarEstablecimiento() {
        try {
            Establecimiento e = this.getEstablecimiento();
            GestorEstablecimiento gestorEstablecimiento = new GestorEstablecimiento();
            GestorGeneral gestorGeneral = new GestorGeneral();
            
            String path2="/resources/imagenes/establecimientos/";
            e.setLogo(path2+file.getFileName());
            gestorEstablecimiento.validarEstablecimiento(e);
            if (e.getCodigoEstablecimiento() == null || e.getCodigoEstablecimiento() == 0) {
                e.setCodigoEstablecimiento(gestorGeneral.siguienteCodigoEntidad("codigo_establecimiento", "establecimiento"));
            }                                   
            
            gestorEstablecimiento.almacenarEstablecimiento(e);

            UtilMSG.addSuccessMsg("Empresa almacenada correctamente.");
            UtilJSF.setBean("establecimiento", new Establecimiento(), UtilJSF.SESSION_SCOPE);
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
        this.cargarEstablecimientosInstitucion();
        this.cargarMunicipios();
        this.establecimiento = new Establecimiento();
        this.file=null;
    }
    private void cargarEstablecimientosInstitucion() {        
        try {
            this.establecimientoList = new ArrayList<>();
            gestorEstablecimiento = new GestorEstablecimiento();            
            this.establecimientoList.addAll((Collection<? extends Establecimiento>) gestorEstablecimiento.cargarListaEstablecimientos());                                                                                                
            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }    
    
    public void cargarLogo(FileUploadEvent event) {
        try {
            String ruta = Propiedades.getInstancia().getPropiedades().getProperty("guardarLogos");
            File carpeta = new File(ruta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            UtilArchivo.guardarStream(ruta + File.separator + event.getFile().getFileName(), event.getFile().getInputstream());
            this.file = event.getFile();           
        } catch (IOException ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }

    }
    
    private void cargarMunicipios() {
        try {
            gestorMunicipios = new GestorMunicipios();
            this.getMunicipiosList().addAll(gestorMunicipios.cargarMunicipios());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    /**
     * @return the establecimiento
     */
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    /**
     * @param establecimiento the establecimiento to set
     */
    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }
    /**
     * @return the establecimientoList
     */
    public List<Establecimiento> getEstablecimientoList() {
        return establecimientoList;
    }

    /**
     * @param establecimientoList the establecimientoList to set
     */
    public void setEstablecimientoList(List<Establecimiento> establecimientoList) {
        this.establecimientoList = establecimientoList;
    }

    /**
     * @return the gestorEstablecimiento
     */
    public GestorEstablecimiento getGestorEstablecimiento() {
        return gestorEstablecimiento;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * @param gestorEstablecimiento the gestorEstablecimiento to set
     */
    public void setGestorEstablecimiento(GestorEstablecimiento gestorEstablecimiento) {
        this.gestorEstablecimiento = gestorEstablecimiento;
    }

    /**
     * @return the gestorGeneral
     */
    public GestorGeneral getGestorGeneral() {
        return gestorGeneral;
    }

    /**
     * @param gestorGeneral the gestorGeneral to set
     */
    public void setGestorGeneral(GestorGeneral gestorGeneral) {
        this.gestorGeneral = gestorGeneral;
    }

    /**
     * @return the gestorMunicipios
     */
    public GestorMunicipios getGestorMunicipios() {
        return gestorMunicipios;
    }

    /**
     * @param gestorMunicipios the gestorMunicipios to set
     */
    public void setGestorMunicipios(GestorMunicipios gestorMunicipios) {
        this.gestorMunicipios = gestorMunicipios;
    }

    /**
     * @return the municipiosList
     */
    public List<Municipios> getMunicipiosList() {
        return municipiosList;
    }

    /**
     * @param municipiosList the municipiosList to set
     */
    public void setMunicipiosList(List<Municipios> municipiosList) {
        this.municipiosList = municipiosList;
    }

}

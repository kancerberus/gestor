/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import com.gestor.controller.GestorGeneral;
import com.gestor.controller.Propiedades;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilArchivo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.Evaluacion;
import com.gestor.publico.controlador.GestorCentroTrabajo;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorLista;
import com.gestor.publico.controlador.GestorMunicipios;
import com.gestor.publico.controlador.GestorObjetivo;
import com.gestor.publico.controlador.GestorPrograma;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
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
    private GestorLista gestorLista;
    private GestorMunicipios gestorMunicipios;
    private Establecimiento establecimiento = new Establecimiento();
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Municipios> municipiosList = new ArrayList<>();    
    
    private GestorCentroTrabajo gestorCentrotrabajo;
    
    private GestorPrograma gestorPrograma;
    private List<CentroTrabajo> centrostrabajo = new ArrayList<>();
    private List<ListaDetalle> listadetalles= new ArrayList<>();    
    private List<MetaEstablecimiento> metas= new ArrayList<>();    
    
    
    private CentroTrabajo centro= new CentroTrabajo();
    private ListaDetalle listadetalle= new ListaDetalle();
    private MetaEstablecimiento metaestablecimiento= new MetaEstablecimiento();    
    private PlanTrabajoPrograma programa = new PlanTrabajoPrograma();
    

    @PostConstruct
    public void init() {
        this.cargarEstablecimientosInstitucion();
        this.cargarMunicipios();      
    }
    
    private void cargarListadetalles() {
        try {
            gestorLista = new GestorLista();
            if(listadetalles.isEmpty()){
            listadetalles.addAll((Collection<? extends ListaDetalle>) gestorLista.cargarListadetalles());
            }
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
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
            
            
            if(file!=null){
            String path2="/resources/imagenes/establecimientos/";
            e.setLogo(path2+file.getFileName());
            }
            
            gestorEstablecimiento.validarEstablecimiento(e);
            if (e.getCodigoEstablecimiento() == null || e.getCodigoEstablecimiento() == 0) {
                e.setCodigoEstablecimiento(gestorGeneral.siguienteCodigoEntidad("codigo_establecimiento", "establecimiento"));
            }                                   
            
            gestorEstablecimiento.almacenarEstablecimiento(e);

            UtilMSG.addSuccessMsg("Empresa almacenada correctamente.");
            UtilJSF.setBean("establecimiento", new Establecimiento(), UtilJSF.SESSION_SCOPE);
            this.limpiarEstablecimiento();

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {    
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }

    }    
    
    public void limpiarEstablecimiento() {
        this.establecimiento= new Establecimiento();
        this.cargarEstablecimientosInstitucion();
        this.cargarMunicipios();              
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
    
        
    public void limpiarcentro() {
        centro= new CentroTrabajo();  
        metaestablecimiento = new MetaEstablecimiento();
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
    
    public void dialogoCentro() {
        try {            
            centro=new CentroTrabajo();
            Dialogo dialogo = new Dialogo("dialogos/centros.xhtml", "Crear Centro De Trabajo", "clip", Dialogo.WIDTH_80);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            establecimiento = (Establecimiento) UtilJSF.getBean("varEstablecimiento");
            UtilJSF.setBean("establecimiento", establecimiento, UtilJSF.APPLICATION_SCOPE);
            this.cargarCentroList();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void dialogoMeta() {
        try {
            metaestablecimiento= new MetaEstablecimiento();
            Dialogo dialogo = new Dialogo ("dialogos/meta.xhtml", "Crear Metas", "clip", Dialogo.WIDTH_80);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            establecimiento = (Establecimiento) UtilJSF.getBean("varEstablecimiento");                
            UtilJSF.setBean("establecimiento", establecimiento, UtilJSF.SESSION_SCOPE);
            this.cargarListadetalles();
            this.cargarMetaList();            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void guardarCentro(){    
        try {
            establecimiento = (Establecimiento) UtilJSF.getBean("establecimiento");                            

            if(centro.getCodCentrotrabajo()==null){
                centro.setCodCentrotrabajo(centrostrabajo.size()+1); 
            }
            
            CentroTrabajo ct= new CentroTrabajo(establecimiento.getCodigoEstablecimiento(), centro.getCodCentrotrabajo(), centro.getNombre(), centro.getNit());
            gestorCentrotrabajo.validarCentro(ct);
            gestorCentrotrabajo.almacenarCentro(ct);   
            
            UtilMSG.addSuccessMsg("Centro De trabajo almacenado correctamente.");
            UtilJSF.setBean("centro", new CentroTrabajo(), UtilJSF.SESSION_SCOPE);            
            this.cargarCentroList();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void guardarMeta(){    
        try {
            establecimiento = (Establecimiento) UtilJSF.getBean("establecimiento");                            

            if(metaestablecimiento.getCodMeta() ==null){
                metaestablecimiento.setCodMeta(metas.size()+1);                
            }
            if(metaestablecimiento.getCodMeta()>2){
                UtilMSG.addSuccessMsg("Modifique meta correctamente.");
                metaestablecimiento.setCodMeta(null);
            }
            
            MetaEstablecimiento me= new MetaEstablecimiento(establecimiento.getCodigoEstablecimiento(), metaestablecimiento.getCodMeta(), metaestablecimiento.getMeta(), metaestablecimiento.getListaDetalle().getListaDetallePK().getCodDetalle());                        
            gestorEstablecimiento.almacenarMeta(me);   
            
            UtilMSG.addSuccessMsg("Meta almacenado correctamente.");
            UtilJSF.setBean("meta", new CentroTrabajo(), UtilJSF.SESSION_SCOPE);            
            this.cargarMetaList();
            this.limpiarcentro();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarCentroList() {
        try {
            establecimiento= (Establecimiento) UtilJSF.getBean("establecimiento");
            this.centrostrabajo= new ArrayList<>();
            gestorCentrotrabajo= new GestorCentroTrabajo();
            this.centrostrabajo.addAll((Collection<? extends CentroTrabajo>) gestorCentrotrabajo.cargarListaCentrosTrabajo(establecimiento.getCodigoEstablecimiento()));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarMetaList() {
        try {
            establecimiento= (Establecimiento) UtilJSF.getBean("establecimiento");
            this.metas= new ArrayList<>();
            gestorEstablecimiento= new GestorEstablecimiento();
            metas.addAll((Collection<? extends MetaEstablecimiento>) gestorEstablecimiento.cargarListaMetas(establecimiento.getCodigoEstablecimiento()));
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void eliminarCentro(){
        try {
            centro= (CentroTrabajo) UtilJSF.getBean("varCentro");            
            gestorCentrotrabajo.eliminarCentro(centro);
            UtilMSG.addSuccessMsg("Centro eliminado.");
            this.cargarCentroList();  
            this.limpiarcentro();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void subirItemCentro() {
        centro = (CentroTrabajo) UtilJSF.getBean("varCentro");                
        UtilJSF.setBean("centro", centro, UtilJSF.SESSION_SCOPE);        
    }   
    
    public void subirItemMeta() {        
        metaestablecimiento = (MetaEstablecimiento) UtilJSF.getBean("varMeta");         
        UtilJSF.setBean("meta", metaestablecimiento, UtilJSF.SESSION_SCOPE);           
        this.cargarMetaList();
    }
    
    private void cargarMunicipios() {
        try {
            gestorMunicipios = new GestorMunicipios();
            this.getMunicipiosList().addAll(gestorMunicipios.cargarMunicipios());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public PlanTrabajoPrograma getPrograma() {
        return programa;
    }

    public void setPrograma(PlanTrabajoPrograma programa) {
        this.programa = programa;
    }

    public ListaDetalle getListadetalle() {
        return listadetalle;
    }

    public void setListadetalle(ListaDetalle listadetalle) {
        this.listadetalle = listadetalle;
    }

    public List<MetaEstablecimiento> getMetas() {
        return metas;
    }

    public void setMetas(List<MetaEstablecimiento> metas) {
        this.metas = metas;
    }

    public List<ListaDetalle> getListadetalles() {
        return listadetalles;
    }

    public void setListadetalles(List<ListaDetalle> listadetalles) {
        this.listadetalles = listadetalles;
    }


    public MetaEstablecimiento getMetaestablecimiento() {
        return metaestablecimiento;
    }

    public void setMetaestablecimiento(MetaEstablecimiento metaestablecimiento) {
        this.metaestablecimiento = metaestablecimiento;
    }


    public GestorCentroTrabajo getGestorCentrotrabajo() {
        return gestorCentrotrabajo;
    }

    public void setGestorCentrotrabajo(GestorCentroTrabajo gestorCentrotrabajo) {
        this.gestorCentrotrabajo = gestorCentrotrabajo;
    }

    public List<CentroTrabajo> getCentrostrabajo() {
        return centrostrabajo;
    }

    public void setCentrostrabajo(List<CentroTrabajo> centrostrabajo) {
        this.centrostrabajo = centrostrabajo;
    }

    public CentroTrabajo getCentro() {
        return centro;
    }

    public void setCentro(CentroTrabajo centro) {
        this.centro = centro;
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

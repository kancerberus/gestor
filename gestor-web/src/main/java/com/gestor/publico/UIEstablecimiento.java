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
import com.gestor.modelo.Sesion;
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
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
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
    
    private List<String> cargosDisponibles = new ArrayList<>();
    private List<String> cargosAsignados = new ArrayList<>();
    private DualListModel<String> itemsDualCargos = new DualListModel<>(cargosDisponibles, cargosAsignados);
    
    private GestorCentroTrabajo gestorCentrotrabajo;
    
    
    private List<CentroTrabajo> centrostrabajo = new ArrayList<>();    
    private List<Cargos> cargosList = new ArrayList<>();
    private List<Funciones> funcionesList= new ArrayList<>();
    
    
    private CentroTrabajo centro= new CentroTrabajo();
    private Cargos cargos=new Cargos();
    private Funciones funciones= new Funciones();
    private PlanTrabajoPrograma programa = new PlanTrabajoPrograma();
    

    @PostConstruct
    public void init() {
        this.cargarEstablecimientosInstitucion();
        this.cargarMunicipios();  
        this.cargarCargosList();
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
    
    public void guardarCargos(){
        try {
            gestorEstablecimiento= new GestorEstablecimiento();
            if(cargos.getCodCargo()==null){
                cargos.setCodCargo(cargosList.size()+1);
            }   
                
            Cargos carg=new Cargos(cargos.getCodCargo(), cargos.getNombre());
            gestorEstablecimiento.validarCargo(carg);
            gestorEstablecimiento.almacenarCargos(carg);
            UtilMSG.addSuccessMsg("Cargo almacenado correctamente.");
            UtilJSF.setBean("cargos", new Cargos(), UtilJSF.SESSION_SCOPE);            
            this.cargarCargosList();
            this.limpiarCargos();
        }catch (Exception e) {
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
    }
    
    public void limpiarFunciones() {
        funciones = new Funciones();        
    }

    public void limpiarCargos() {
        cargos=new Cargos();          
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
            Dialogo dialogo = new Dialogo("dialogos/centros.xhtml", "Crear Centro De Trabajo", "clip", Dialogo.WIDTH_AUTO);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            establecimiento = (Establecimiento) UtilJSF.getBean("varEstablecimiento");
            UtilJSF.setBean("establecimiento", establecimiento, UtilJSF.APPLICATION_SCOPE);
            this.cargarCentroList();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void dialogoFunciones() {
        try {            
            funciones=new Funciones();
            Dialogo dialogo = new Dialogo("dialogos/funciones.xhtml", "Crear Actividad", "clip", Dialogo.WIDTH_AUTO);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
            cargos = (Cargos) UtilJSF.getBean("varCargos");
            UtilJSF.setBean("cargos", cargos, UtilJSF.SESSION_SCOPE);            
            this.cargarFuncionesList();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void dialogoCargos() {
        try {        
            cargos= new Cargos();
            Dialogo dialogo = new Dialogo("dialogos/cargos-establecimiento.xhtml", "Crear Cargos", "clip", Dialogo.WIDTH_AUTO);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarCargosEstablecimiento() {

        try {                        
            establecimiento = (Establecimiento) UtilJSF.getBean("varEstablecimiento");
            gestorEstablecimiento = new GestorEstablecimiento();
            
            this.cargosDisponibles = this.transformarLista(gestorEstablecimiento.cargarListaCargos());
            this.cargosAsignados = this.transformarLista(gestorEstablecimiento.cargarListaCargosEstablecimiento(establecimiento.getCodigoEstablecimiento()));
            this.itemsDualCargos = new DualListModel<>((List<String>) this.removerElementosAsignados(cargosDisponibles, cargosAsignados), cargosAsignados);

            UtilJSF.setBean("establecimiento", establecimiento, UtilJSF.SESSION_SCOPE);            
        } catch (Exception ex) {
            UtilMSG.addErrorMsg("Error al cargar el usuario");
            UtilLog.generarLog(this.getClass(), ex);
        }

    }
    
    public List<String> transformarLista(final List<?> objects) {
        List<String> lista = new ArrayList<>();
        for (Object ob : objects) {
            Cargos car = (Cargos) ob;
            lista.add(car.getNombre());
        }
        return lista;
    }
    
    public List<?> removerElementosAsignados(List<?> disponibles, List<?> asignados) {
        CopyOnWriteArrayList origen = new CopyOnWriteArrayList(disponibles);
        CopyOnWriteArrayList destino = new CopyOnWriteArrayList(asignados);
        for (Object obj1 : origen) {
            for (Object obj2 : destino) {
                if (obj1.equals(obj2)) {
                    origen.remove(obj2);
                }
            }
        }
        return new ArrayList(origen);
    }
    
    public void guardarCargosEstablecimiento(){
        try {
        
            gestorEstablecimiento= new GestorEstablecimiento();
            establecimiento.setListaCargosEstablecimientos(this.cargarCargosEstablecimientosAsignados());
            
            gestorEstablecimiento.almacenarCargosEstablecimiento(establecimiento);
            UtilMSG.addSuccessMsg("Cargos modificados correctamente");
        } catch (Exception ex) {
            if (UtilLog.causaControlada(ex)) {
                UtilMSG.addWarningMsg(ex.getMessage());
            } else {
                UtilMSG.addErrorMsg("Ocurrio una excepción");
                UtilLog.generarLog(this.getClass(), ex);
            }
        }
    }
     

    
    public void guardarCentro(){    
        try {
            establecimiento = (Establecimiento) UtilJSF.getBean("establecimiento");                            

            if(centro.getCodCentrotrabajo()==null){
                centro.setCodCentrotrabajo(centrostrabajo.size()+1); 
            }
            
            CentroTrabajo ct= new CentroTrabajo(establecimiento.getCodigoEstablecimiento(), centro.getCodCentrotrabajo(), centro.getNombre(), centro.getNit(), centro.getEstado());
            gestorCentrotrabajo.validarCentro(ct);
            gestorCentrotrabajo.almacenarCentro(ct);   
            
            UtilMSG.addSuccessMsg("Centro De trabajo almacenado correctamente.");
            UtilJSF.setBean("centro", new CentroTrabajo(), UtilJSF.SESSION_SCOPE);            
            this.cargarCentroList();
            this.limpiarcentro();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void guardarFunciones(){    
        try {
            cargos = (Cargos) UtilJSF.getBean("cargos");                            
            gestorEstablecimiento= new GestorEstablecimiento();
            if(funciones.getCodFuncion()==null){
                funciones.setCodFuncion(funcionesList.size()+1); 
            }
            
            
            Funciones fun= new Funciones(cargos.getCodCargo(), funciones.getCodFuncion(), funciones.getNombre());
            gestorEstablecimiento.validarFuncion(fun);
            gestorEstablecimiento.almacenarFuncion(fun);
            
            
            
            UtilMSG.addSuccessMsg("Actividad almacenada correctamente.");
            UtilJSF.setBean("funcion", new Funciones(), UtilJSF.SESSION_SCOPE);            
            this.cargarFuncionesList();            
            this.limpiarFunciones();
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    private List<Cargos> cargarCargosEstablecimientosAsignados() throws Exception {
        gestorEstablecimiento = new GestorEstablecimiento();
        List<Cargos> listaCargosEstablecimientosAsignados = new ArrayList<>();
        List<Cargos> listaCargos = (List<Cargos>) gestorEstablecimiento.cargarListaCargos();
        List<String> asignados = this.itemsDualCargos.getTarget();

        for (Cargos obe : listaCargos) {
            for (String e : asignados) {
                if (obe.getNombre().equalsIgnoreCase(e)) {
                    listaCargosEstablecimientosAsignados.add(obe);
                }
            }
        }
        return listaCargosEstablecimientosAsignados;
    }
    
    public void cargarCargosList() {
        try {
            cargosList= new ArrayList<>();
            gestorEstablecimiento= new GestorEstablecimiento();
            this.cargosList.addAll((Collection<? extends Cargos>) gestorEstablecimiento.cargarListaCargos());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void subirItemCargo() {
        cargos = (Cargos) UtilJSF.getBean("varCargos");                
        UtilJSF.setBean("cargos", cargos, UtilJSF.SESSION_SCOPE);
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
    
    public void cargarFuncionesList() {
        try {
            cargos = (Cargos) UtilJSF.getBean("varCargos");
            if(cargos==null){
                cargos = (Cargos) UtilJSF.getBean("cargos");
            }
            
            this.funcionesList= new ArrayList<>();
            gestorEstablecimiento= new GestorEstablecimiento();
            this.funcionesList.addAll((Collection<? extends Funciones>) gestorEstablecimiento.cargarListaFunciones(cargos.getCodCargo()));
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
    
    public void subirItemFunciones() {
        funciones = (Funciones) UtilJSF.getBean("varFunciones");
        UtilJSF.setBean("funciones", funciones, UtilJSF.SESSION_SCOPE);        
    }   
    
    private void cargarMunicipios() {
        try {
            gestorMunicipios = new GestorMunicipios();
            this.getMunicipiosList().addAll(gestorMunicipios.cargarMunicipios());
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public List<Funciones> getFuncionesList() {
        return funcionesList;
    }

    public void setFuncionesList(List<Funciones> funcionesList) {
        this.funcionesList = funcionesList;
    }

    public List<Cargos> getCargosList() {
        return cargosList;
    }

    public void setCargosList(List<Cargos> cargosList) {
        this.cargosList = cargosList;
    }

    public List<String> getCargosDisponibles() {
        return cargosDisponibles;
    }

    public void setCargosDisponibles(List<String> cargosDisponibles) {
        this.cargosDisponibles = cargosDisponibles;
    }

    public List<String> getCargosAsignados() {
        return cargosAsignados;
    }

    public void setCargosAsignados(List<String> cargosAsignados) {
        this.cargosAsignados = cargosAsignados;
    }

    public DualListModel<String> getItemsDualCargos() {
        return itemsDualCargos;
    }

    public void setItemsDualCargos(DualListModel<String> itemsDualCargos) {
        this.itemsDualCargos = itemsDualCargos;
    }

    public Cargos getCargos() {
        return cargos;
    }

    public void setCargos(Cargos cargos) {
        this.cargos = cargos;
    }

    public PlanTrabajoPrograma getPrograma() {
        return programa;
    }

    public void setPrograma(PlanTrabajoPrograma programa) {
        this.programa = programa;
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

    public Funciones getFunciones() {
        return funciones;
    }

    public void setFunciones(Funciones funciones) {
        this.funciones = funciones;
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.controller.GestorGeneral;
import com.gestor.controller.Propiedades;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilFecha;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.EvaluacionAdjuntos;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.seguimiento.controlador.GestorPlanMaestro;
import com.gestor.seguimiento.controlador.GestorPlanTitulo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Julian D Osorio G
 */
@ManagedBean(name = "uiPlanMaestro")
@SessionScoped
public class UIPlanMaestro {

    public static final String COMPONENTES_REFRESCAR = "";

    private boolean guardarActivo = false;
    private boolean nuevoActivo = true;
    private boolean consultarActivo = false;
    private boolean cancelarActivo = false;    
    private boolean eliminarActivo = false;
    private boolean volverActivo = false;


    private List<PlanMaestro> planMaestroList;
    private Establecimiento establecimiento;

    private List<Evaluacion> evaluacionList = new ArrayList<>();
    private Evaluacion evaluacion = new Evaluacion();

    private Boolean filtroActivo = Boolean.TRUE;

    private StreamedContent fileDownload;

    //filtros
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientosPermitidosList= new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();

//    private List<Usuarios> usuariosList = new ArrayList<>();
//    private Usuarios usuariosSeleccionado;
//
//    private List<String> ciclosString = new ArrayList<>();
//    private List<String> ciclosStringSeleccionado = new ArrayList<>();
//
//    private Map<String, String> capacitacionEstado = new HashMap<>();
//    private List<String> capacitacionEstadoSeleccionado = new ArrayList<>();
//    private Long codEvaluacion;
//    private String responsable;
    private Date fechaActualizaInicio;
    private Date fechaActualizaFin;    

    @PostConstruct
    public void init() {
        try {            
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            establecimientoList = new ArrayList<>();
            establecimientoList.addAll(s.getEstablecimientoList());            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarEstablecimientos() {
        try {                              
            establecimientosPermitidosList = new ArrayList<>();
            GestorEstablecimiento gestorEstablecimiento= new GestorEstablecimiento();
            establecimientosPermitidosList.addAll((Collection<? extends Establecimiento>)gestorEstablecimiento.establecimientosPlanMaestroPermitidos());            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public String consultarPlanMaestro() {
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");            
            GestorPlanTitulo gestorPlanTitulo = new GestorPlanTitulo();
            PlanMaestro pm = (PlanMaestro) UtilJSF.getBean("varPlanMaestro");
            pm.setPlanTituloList((List<PlanTitulo>) gestorPlanTitulo.cargarPlanTituloList(pm.getPlanMaestroPK().getCodigoEstablecimiento(), pm.getPlanMaestroPK().getCodEvaluacion()));
            UtilJSF.setBean("planMaestro", pm, UtilJSF.SESSION_SCOPE);
            if(s.getUsuarios().getRoles().getCodigoRol()==4){
                this.procesarPlanMaestro();
            return ("/seguimiento/pm/vuwopux.xhtml?faces-redirect=true");            
            }else{
                this.procesarPlanMaestro();
                return ("/seguimiento/plan-maestro.xhtml?faces-redirect=true");
            }
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }
    
    public String administrarPlanmaestro(){
        return ("/seguimiento/titulo.xhtml?faces-redirect=true");
    }
    
    public void cancelarProcesarPlanMaestro() {
        establecimiento = new Establecimiento();
        evaluacionList = new ArrayList<>();
    }

    public String procesarPlanMaestro() {
        try {           
            
            GestorPlanMaestro gestorPlanMaestro = new GestorPlanMaestro();
            GestorGeneral gestorGeneral = new GestorGeneral();
            GestorPlanTitulo gestorPlanTitulo = new GestorPlanTitulo();           
            
            if(evaluacion.getEvaluacionPK()==null){
                PlanMaestro pm=(PlanMaestro) UtilJSF.getBean("varPlanMaestro");
                evaluacion.setEvaluacionPK(pm.getEvaluacion().getEvaluacionPK());                
            }
            
                
            PlanMaestro pm = (PlanMaestro) new PlanMaestro(new PlanMaestroPK(                    
                    evaluacion.getEvaluacionPK().getCodEvaluacion(),
                    evaluacion.getEvaluacionPK().getCodigoEstablecimiento(),
                    null
            ), gestorGeneral.now(), gestorGeneral.now()).clone();

            pm.getPlanMaestroPK().setCodMaestro(gestorPlanMaestro.consultarCodMaestroPlanMaestro(pm.getPlanMaestroPK().getCodigoEstablecimiento(), pm.getPlanMaestroPK().getCodEvaluacion()));
            if (pm.getPlanMaestroPK().getCodMaestro() == null
                    || pm.getPlanMaestroPK().getCodMaestro() == 0) {
                pm.getPlanMaestroPK().setCodMaestro(gestorGeneral.nextval(GestorGeneral.SEGUIMIENTO_PLAN_MAESTRO_COD_MAESTRO_SEQ));
            }
            
            gestorPlanMaestro.procesarPlanMaestro(pm);
            
            pm.setPlanTituloList((List<PlanTitulo>) gestorPlanTitulo.cargarPlanTituloList(pm.getPlanMaestroPK().getCodigoEstablecimiento(), pm.getPlanMaestroPK().getCodEvaluacion()));
            UtilJSF.setBean("planMaestro", pm, UtilJSF.SESSION_SCOPE);
            
            
            return ("/seguimiento/plan-maestro.xhtml?faces-redirect=true");

        } catch (Exception e) {
            UtilMSG.addSupportMsg();
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }

    public void seleccionarEstablecimiento() {
        try {
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            this.establecimiento = (Establecimiento) UtilJSF.getBean("varEstablecimientoPm");
            this.evaluacionList = new ArrayList<>();            
            List<String> condicionesConsulta = new ArrayList<>();
            condicionesConsulta.add(App.CONDICION_WHERE);
            condicionesConsulta.add(Evaluacion.EVALUACION_CONDICION_COD_ESTABLECIMIENTO.replace("?", String.valueOf(establecimiento.getCodigoEstablecimiento())));

            this.evaluacionList.addAll(gestorEvaluacion.cargarListaEvaluacion(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            ));
        } catch (Exception e) {
        }

    }

    private List<String> filtrarOpcionesSeleccionadas() {
        List<String> condicionesConsulta = new ArrayList<>();
        condicionesConsulta.add(App.CONDICION_WHERE);

        if (establecimientoListSeleccionado != null && !establecimientoListSeleccionado.isEmpty()) {
            String cadena = "0";
            for (Establecimiento e : establecimientoListSeleccionado) {
                cadena += "," + e.getCodigoEstablecimiento();
            }
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
        } else {
            condicionesConsulta.add(Boolean.TRUE.toString());
        }

        if (fechaActualizaInicio != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_FECHA_ACTUALIZA_GTE.replace("?", UtilFecha.formatoFecha(fechaActualizaInicio, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        if (fechaActualizaFin != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_FECHA_ACTUALIZA_LTE.replace("?", UtilFecha.formatoFecha(fechaActualizaFin, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        return condicionesConsulta;
    }

    public void consultarPlanMaestroLista() {
        try {
            planMaestroList = new ArrayList<>();
            GestorPlanMaestro gestorPlanMaestro = new GestorPlanMaestro();
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();            
            
            String cadena="0";            
            for (Establecimiento e : s.getUsuarios().getListaEstablecimientos()) {
                cadena += "," + e.getCodigoEstablecimiento();
            }            
            condicionesConsulta.add("AND");
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
            
            
            if(s.getUsuarios().getRoles().getCodigoRol()==4){
                planMaestroList.addAll(gestorPlanMaestro.cargarListaPlanMaestrogerente(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
                    )
                );
            }if(s.getUsuarios().getRoles().getCodigoRol() != 4){
                planMaestroList.addAll(gestorPlanMaestro.cargarListaPlanMaestro(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
                )
                );
            }
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }
    
    public Integer getAvanceEvaluacion() {
        try {
            PlanMaestro pm = (PlanMaestro) UtilJSF.getBean("planMaestro");
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            return gestorEvaluacion.avanceEvaluacion(pm.getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(), pm.getEvaluacion().getEvaluacionPK().getCodEvaluacion());
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return Integer.MIN_VALUE;
    }
    
    public void consultarEvaluacionesLista() {
        try {
            evaluacionList = new ArrayList<>();
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            Sesion s = (Sesion) UtilJSF.getBean("sesion");            
            evaluacionList.addAll(gestorEvaluacion.cargarEvaluacionList(s.getEstablecimiento().getCodigoEstablecimiento(), s.getConfiguracion().get(App.CONFIGURACION_MOSTRAR_EVALUACIONES).toString())                                
            );
          
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }

    }
    
    public String subirItemevaluacion() {
        try{     
            evaluacion=new Evaluacion();
            evaluacion = (Evaluacion) UtilJSF.getBean("varEvaluacion"); 
            UIPlantitulo pt = (UIPlantitulo) UtilJSF.getBean("uiPlantitulo");            
            UtilJSF.setBean("evaluacion", evaluacion, UtilJSF.SESSION_SCOPE);                                                                                  
            pt.cargarPlantitulo();     
            return ("/seguimiento/titulo.xhtml?faces-redirect=true");
        }catch(Exception e){
        return ("/seguimiento/titulo.xhtml?faces-redirect=true");                    
        }        
    }     

    public String cargarPlanMaestro() {
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");            
            UtilJSF.setBean("dialogo", new Dialogo(), UtilJSF.SESSION_SCOPE);
            
//            Usuarios usuarios = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios();
            planMaestroList = new ArrayList<>();            
            GestorPlanMaestro gestorPlanMaestro = new GestorPlanMaestro();
            GestorGeneral gestorGeneral = new GestorGeneral();

            List<String> condicionesConsulta = new ArrayList<>();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(gestorGeneral.now());
            gc.add(Calendar.DAY_OF_MONTH, -30);
            this.fechaActualizaInicio = gc.getTime();
            String cadena="0";            
            for (Establecimiento e : s.getUsuarios().getListaEstablecimientos()) {
                cadena += "," + e.getCodigoEstablecimiento();
            }
            
            condicionesConsulta.add(App.CONDICION_WHERE);
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_FECHA_ACTUALIZA_GTE.replace("?", UtilFecha.formatoFecha(fechaActualizaInicio, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));            
            condicionesConsulta.add("AND");
            condicionesConsulta.add(PlanMaestro.PLAN_MAESTRO_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
            
            
            if(s.getUsuarios().getRoles().getCodigoRol()==4){
                planMaestroList.addAll(gestorPlanMaestro.cargarListaPlanMaestrogerente(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
                    )
                );
            return ("/seguimiento/pm/xvzhuehs.xhtml?faces-redirect=true");
            }else{
                planMaestroList.addAll(gestorPlanMaestro.cargarListaPlanMaestro(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
                    )
                );
                return ("/seguimiento/planes-maestros.xhtml?faces-redirect=true");
            }
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return null;
    }
    public String administrar(){        
        return("/seguimiento/titulo.xhtml?faces-redirect=true");
    }
    
    public String crearTitulo(){
        return("/seguimiento/titulo.xhtml?faces-redirect=true");
    }
    
    public String crearSeccion(){
        return("/seguimiento/seccion.xhtml?faces-redirect=true");
    }
    
    public String crearDetalle(){
        return("/seguimiento/detalle.xhtml?faces-redirect=true");
    }
    public String crearItem(){
        return("/seguimiento/item.xhtml?faces-redirect=true");
    }
        
    public String nuevo() {
        this.cargarEstablecimientos();
        return ("/seguimiento/plan-maestro-nuevo.xhtml?faces-redirect=true");
    }

    public void consultar() {
    }

    public void guardar() {
    }

    public void cancelar() {
    }

    public void eliminar() {
    }

    public String getComponentesRefrescar() {
        return COMPONENTES_REFRESCAR;
    }

    public List<Establecimiento> getEstablecimientosPermitidosList() {
        return establecimientosPermitidosList;
    }

    public void setEstablecimientosPermitidosList(List<Establecimiento> establecimientosPermitidosList) {
        this.establecimientosPermitidosList = establecimientosPermitidosList;
    }

    /**
     * @return the guardarActivo
     */
    public boolean isGuardarActivo() {
        return guardarActivo;
    }

    /**
     * @param guardarActivo the guardarActivo to set
     */
    public void setGuardarActivo(boolean guardarActivo) {
        this.guardarActivo = guardarActivo;
    }

    /**
     * @return the nuevoActivo
     */
    public boolean isNuevoActivo() {
        return nuevoActivo;
    }

    /**
     * @param nuevoActivo the nuevoActivo to set
     */
    public void setNuevoActivo(boolean nuevoActivo) {
        this.nuevoActivo = nuevoActivo;
    }

    /**
     * @return the eliminarActivo
     */
    public boolean isEliminarActivo() {
        return eliminarActivo;
    }

    /**
     * @param eliminarActivo the eliminarActivo to set
     */
    public void setEliminarActivo(boolean eliminarActivo) {
        this.eliminarActivo = eliminarActivo;
    }

    /**
     * @return the consultarActivo
     */
    public boolean isConsultarActivo() {
        return consultarActivo;
    }

    /**
     * @param consultarActivo the consultarActivo to set
     */
    public void setConsultarActivo(boolean consultarActivo) {
        this.consultarActivo = consultarActivo;
    }

    /**
     * @return the cancelarActivo
     */
    public boolean isCancelarActivo() {
        return cancelarActivo;
    }

    /**
     * @param cancelarActivo the cancelarActivo to set
     */
    public void setCancelarActivo(boolean cancelarActivo) {
        this.cancelarActivo = cancelarActivo;
    }

    /**
     * @return the filtroActivo
     */
    public Boolean getFiltroActivo() {
        return filtroActivo;
    }

    /**
     * @param filtroActivo the filtroActivo to set
     */
    public void setFiltroActivo(Boolean filtroActivo) {
        this.filtroActivo = filtroActivo;
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
     * @return the establecimientoListSeleccionado
     */
    public List<Establecimiento> getEstablecimientoListSeleccionado() {
        return establecimientoListSeleccionado;
    }

    /**
     * @param establecimientoListSeleccionado the
     * establecimientoListSeleccionado to set
     */
    public void setEstablecimientoListSeleccionado(List<Establecimiento> establecimientoListSeleccionado) {
        this.establecimientoListSeleccionado = establecimientoListSeleccionado;
    }

    /**
     * @return the fechaActualizaInicio
     */
    public Date getFechaActualizaInicio() {
        return fechaActualizaInicio;
    }

    /**
     * @param fechaActualizaInicio the fechaActualizaInicio to set
     */
    public void setFechaActualizaInicio(Date fechaActualizaInicio) {
        this.fechaActualizaInicio = fechaActualizaInicio;
    }

    /**
     * @return the fechaActualizaFin
     */
    public Date getFechaActualizaFin() {
        return fechaActualizaFin;
    }

    /**
     * @param fechaActualizaFin the fechaActualizaFin to set
     */
    public void setFechaActualizaFin(Date fechaActualizaFin) {
        this.fechaActualizaFin = fechaActualizaFin;
    }

    /**
     * @return the planMaestroList
     */
    public List<PlanMaestro> getPlanMaestroList() {
        return planMaestroList;
    }

    /**
     * @param planMaestroList the planMaestroList to set
     */
    public void setPlanMaestroList(List<PlanMaestro> planMaestroList) {
        this.planMaestroList = planMaestroList;
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
     * @return the evaluacion
     */
    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    /**
     * @param evaluacion the evaluacion to set
     */
    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public boolean isVolverActivo() {
        return volverActivo;
    }

    public void setVolverActivo(boolean volverActivo) {
        this.volverActivo = volverActivo;
    }

    /**
     * @return the evaluacionList
     */
    public List<Evaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    /**
     * @param evaluacionList the evaluacionList to set
     */
    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    /**
     * @return the fileDownload
     */
    public StreamedContent getFileDownload() {
        String nombreAdjunto = "";
        try {
            PlanTituloAdiuntos pta = (PlanTituloAdiuntos) UtilJSF.getBean("varPlanTituloAdjuntos");
            EvaluacionAdjuntos ea = pta.getEvaluacionAdjuntos();
            if (ea == null) {
                UtilMSG.addWarningMsg("Adjunto No Encontrado", "No se encontro el adjunto, intente nuevamente o contáctenos para asistirle.");
                return null;
            }
            Properties p = Propiedades.getInstancia().getPropiedades();
            String rutaAdjunto = p.getProperty("rutaAdjunto") + File.separator + App.ADJUNTO_PREFIJO + ea.getEvaluacionAdjuntosPK().getCodEvaluacion();            
            nombreAdjunto = ea.getArchivo();
            InputStream stream = new FileInputStream(rutaAdjunto + File.separator + ea.getArchivo());
            fileDownload = new DefaultStreamedContent(stream, null, ea.getArchivoSimple());
        } catch (FileNotFoundException ex) {
            UtilMSG.addErrorMsg("Archivo No Existe", "El adjunto " + nombreAdjunto + ", no fue encontrado. Si el problema persiste contactenos para asistirle.");
            UtilLog.generarLog(this.getClass(), ex);
        }
        return fileDownload;
    }
    
    /**
     * @return the fileDownload
     */
    public StreamedContent getFileDownloadSeccion() {
        String nombreAdjunto = "";
        try {
            PlanSeccionAdjuntos psa =  (PlanSeccionAdjuntos) UtilJSF.getBean("varPlanSeccionAdjuntos");
            EvaluacionAdjuntos ea = psa.getEvaluacionAdjuntos();
            if (ea == null) {
                UtilMSG.addWarningMsg("Adjunto No Encontrado", "No se encontro el adjunto, intente nuevamente o contáctenos para asistirle.");
                return null;
            }
            Properties p = Propiedades.getInstancia().getPropiedades();
            String rutaAdjunto = p.getProperty("rutaAdjunto") + File.separator + App.ADJUNTO_PREFIJO + ea.getEvaluacionAdjuntosPK().getCodEvaluacion();
            nombreAdjunto = ea.getArchivo();
            InputStream stream = new FileInputStream(rutaAdjunto + File.separator + ea.getArchivo());
            fileDownload = new DefaultStreamedContent(stream, null, ea.getArchivoSimple());
        } catch (FileNotFoundException ex) {
            UtilMSG.addErrorMsg("Archivo No Existe", "El adjunto " + nombreAdjunto + ", no fue encontrado. Si el problema persiste contactenos para asistirle.");
            UtilLog.generarLog(this.getClass(), ex);
        }
        return fileDownload;
    }
    
    
    
    /**
     * @return the fileDownload
     */
    public StreamedContent getFileDownloadSeccionDetalle() {
        String nombreAdjunto = "";
        try {
            PlanSeccionDetalleAdjuntos psda =  (PlanSeccionDetalleAdjuntos) UtilJSF.getBean("varPlanSeccionDetalleAdjuntos");
            EvaluacionAdjuntos ea = psda.getEvaluacionAdjuntos();
            if (ea == null) {
                UtilMSG.addWarningMsg("Adjunto No Encontrado", "No se encontro el adjunto, intente nuevamente o contáctenos para asistirle.");
                return null;
            }
            Properties p = Propiedades.getInstancia().getPropiedades();
            String rutaAdjunto = p.getProperty("rutaAdjunto") + File.separator + App.ADJUNTO_PREFIJO + ea.getEvaluacionAdjuntosPK().getCodEvaluacion();
            nombreAdjunto = ea.getArchivo();
            InputStream stream = new FileInputStream(rutaAdjunto + File.separator + ea.getArchivo());
            fileDownload = new DefaultStreamedContent(stream, null, ea.getArchivoSimple());
        } catch (FileNotFoundException ex) {
            UtilMSG.addErrorMsg("Archivo No Existe", "El adjunto " + nombreAdjunto + ", no fue encontrado. Si el problema persiste contactenos para asistirle.");
            UtilLog.generarLog(this.getClass(), ex);
        }
        return fileDownload;
    }
    
    /**
     * @return the fileDownload
     */
    public StreamedContent getFileDownloadSeccionDetalleItem() {
        String nombreAdjunto = "";
        try {
            PlanSeccionDetalleItemAdjuntos psdia =   (PlanSeccionDetalleItemAdjuntos) UtilJSF.getBean("varPlanSeccionDetalleItemAdjuntos");
            EvaluacionAdjuntos ea = psdia.getEvaluacionAdjuntos();
            if (ea == null) {
                UtilMSG.addWarningMsg("Adjunto No Encontrado", "No se encontro el adjunto, intente nuevamente o contáctenos para asistirle.");
                return null;
            }
            Properties p = Propiedades.getInstancia().getPropiedades();
            String rutaAdjunto = p.getProperty("rutaAdjunto") + File.separator + App.ADJUNTO_PREFIJO + ea.getEvaluacionAdjuntosPK().getCodEvaluacion();
            nombreAdjunto = ea.getArchivo();
            InputStream stream = new FileInputStream(rutaAdjunto + File.separator + ea.getArchivo());
            fileDownload = new DefaultStreamedContent(stream, null, ea.getArchivoSimple());
        } catch (FileNotFoundException ex) {
            UtilMSG.addErrorMsg("Archivo No Existe", "El adjunto " + nombreAdjunto + ", no fue encontrado. Si el problema persiste contactenos para asistirle.");
            UtilLog.generarLog(this.getClass(), ex);
        }
        return fileDownload;
    }

    /**
     * @param fileDownload the fileDownload to set
     */
    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }
}

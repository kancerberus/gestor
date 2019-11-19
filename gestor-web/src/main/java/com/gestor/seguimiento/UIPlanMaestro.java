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
import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.Ciclo;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.Seccion;
import com.gestor.gestor.SeccionDetalle;
import com.gestor.gestor.SeccionDetalleItems;
import com.gestor.gestor.controlador.GestorAdjuntosCategoria;
import com.gestor.gestor.controlador.GestorCiclo;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.gestor.controlador.GestorEvaluacionAdjuntos;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.EvaluacionAdjuntos;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorEstandar;
import com.gestor.seguimiento.controlador.GestorPlanMaestro;
import com.gestor.seguimiento.controlador.GestorPlanTitulo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

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
    private PlanMaestro planMaestro;
    private Establecimiento establecimiento;

    private List<Evaluacion> evaluacionList = new ArrayList<>();
    private Evaluacion evaluacion = new Evaluacion();

    private Boolean filtroActivo = Boolean.TRUE;
    private TreeNode raizPlanear;
    private TreeNode raizHacer;
    private TreeNode raizVerificar;
    private TreeNode raizActuar;
    
    private StreamedContent fileDownload;
    private Boolean siAdjunto;
    
    private TreeNode selectedNode;    
    private String node;
    private File destination;
    Date hoy=new Date();

    //filtros
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientosPermitidosList= new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();
    private List<String> nombresSeccion=new ArrayList<>();
    private List<String> nombresDetalle=new ArrayList<>();
    private List<String> nombresItem=new ArrayList<>();
    private Ciclo ciclo;    
    private StreamedContent fileDownloadTree;
    private EvaluacionAdjuntos eadj=new EvaluacionAdjuntos();    
    
    

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
            planMaestro=new PlanMaestro();
            establecimientoList.addAll(s.getEstablecimientoList());    
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void rootCiclos() throws Exception{
        
        planMaestro= (PlanMaestro) UtilJSF.getBean("varPlanMaestro");
        
        GestorCiclo gestorCiclo=new GestorCiclo();
        GestorEstandar gestorEstandar=new GestorEstandar(); 
        GestorEvaluacionAdjuntos gestorEvaluacionAdjuntos=new GestorEvaluacionAdjuntos();
        GestorAdjuntosCategoria gestorAdjuntosCategoria=new GestorAdjuntosCategoria();
        List<Seccion> secciones=new ArrayList<>();
        List<SeccionDetalle> sDetalles=new ArrayList<>();
        List<SeccionDetalleItems> sdItems=new ArrayList<>();
        List<AdjuntosCategoria> sdiCategorias=new ArrayList<>();
        List<AdjuntosCategoriaTipo> acTipos=new ArrayList<>();
        List<EvaluacionAdjuntos> nombresAdjuntos=new ArrayList<>();
        
                
                
        raizPlanear= new DefaultTreeNode("PLANEAR", null);               
            secciones = gestorEstandar.cargarListaSecciones("P");
            for (Seccion seccion : secciones) {
                if (seccion != null ) {
                    TreeNode s = new DefaultTreeNode(seccion.getNombre(),raizPlanear);
                    sDetalles= gestorEstandar.cargarListaSecciondetalles("P", seccion.getSeccionPK().getCodSeccion());
                    for(SeccionDetalle seccionDetalle:sDetalles){
                        TreeNode d=new DefaultTreeNode(seccionDetalle.getNombre(), s);
                        if(seccionDetalle!=null){
                            sdItems=gestorEstandar.cargarListaSecciondetalleitemss("P", seccion.getSeccionPK().getCodSeccion(), seccionDetalle.getSeccionDetallePK().getCodDetalle());
                            for(SeccionDetalleItems seccionDetalleItems: sdItems){
                                TreeNode i=new DefaultTreeNode(seccionDetalleItems.getNombre(), d);
                                if(seccionDetalleItems != null){                                                
                                    sdiCategorias=new ArrayList<>();
                                    sdiCategorias.addAll((Collection<? extends AdjuntosCategoria>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoria(seccionDetalleItems.getSeccionDetalleItemsPK()));                                                                                                
                                    for(AdjuntosCategoria aCategoria: sdiCategorias){
                                        TreeNode c=new DefaultTreeNode(aCategoria.getNombre(), i);
                                        if(aCategoria!=null){
                                            acTipos=new ArrayList<>();
                                            acTipos.addAll((Collection<? extends AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(aCategoria.getCodCategoria()));
                                            for(AdjuntosCategoriaTipo aCategoriaTipo: acTipos){
                                                TreeNode t=new DefaultTreeNode(aCategoriaTipo.getNombre(), c);
                                                if( aCategoriaTipo!=null ){
                                                    siAdjunto=true;
                                                    nombresAdjuntos=new ArrayList<>();
                                                    nombresAdjuntos.addAll(gestorEvaluacionAdjuntos.cargarListaAdjuntosCategoriaTipo(planMaestro.getEstablecimiento().getCodigoEstablecimiento(),
                                                            planMaestro.getEvaluacion().getEvaluacionPK().getCodEvaluacion().intValue(),"P",seccion.getSeccionPK().getCodSeccion(),
                                                            seccionDetalle.getSeccionDetallePK().getCodDetalle(),seccionDetalleItems.getSeccionDetalleItemsPK().getCodItem(),aCategoria.getCodCategoria(),aCategoriaTipo.getAdjuntosCategoriaTipoPK().getCodCategoriaTipo()));
                                                    Integer n=0;
                                                    List<EvaluacionAdjuntos> nombresAdjuntoss=new ArrayList<>();                                                    
                                                    EvaluacionAdjuntos ea=new EvaluacionAdjuntos();                                                       
                                                    
                                                    if(nombresAdjuntos.size()!=0){                                                                                                                
                                                        n=nombresAdjuntos.size()-1;                                                                                                        
                                                        ea.setArchivo(nombresAdjuntos.get(n).getArchivo());
                                                        nombresAdjuntoss.add(ea);
                                                    }
                                                    for(EvaluacionAdjuntos eAdjuntos:nombresAdjuntoss){
                                                        
                                                        TreeNode ad=new DefaultTreeNode(eAdjuntos.getArchivo(), t);
                                                    }
                                                    
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            
            raizHacer= new DefaultTreeNode("HACER", null);                                
            secciones = gestorEstandar.cargarListaSecciones("H");
            for (Seccion seccion : secciones) {
                if (seccion != null ) {
                    TreeNode s = new DefaultTreeNode(seccion.getNombre(),raizHacer);
                    sDetalles= gestorEstandar.cargarListaSecciondetalles("H", seccion.getSeccionPK().getCodSeccion());
                    for(SeccionDetalle seccionDetalle:sDetalles){
                        TreeNode d=new DefaultTreeNode(seccionDetalle.getNombre(), s);
                        if(seccionDetalle!=null){
                            sdItems=gestorEstandar.cargarListaSecciondetalleitemss("H", seccion.getSeccionPK().getCodSeccion(), seccionDetalle.getSeccionDetallePK().getCodDetalle());
                            for(SeccionDetalleItems seccionDetalleItems: sdItems){
                                TreeNode i=new DefaultTreeNode(seccionDetalleItems.getNombre(), d);
                                if(seccionDetalleItems != null){                                                
                                    sdiCategorias=new ArrayList<>();
                                    sdiCategorias.addAll((Collection<? extends AdjuntosCategoria>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoria(seccionDetalleItems.getSeccionDetalleItemsPK()));                                                                                                
                                    for(AdjuntosCategoria aCategoria: sdiCategorias){
                                        TreeNode c=new DefaultTreeNode(aCategoria.getNombre(), i);
                                        if(aCategoria!=null){
                                            acTipos=new ArrayList<>();
                                            acTipos.addAll((Collection<? extends AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(aCategoria.getCodCategoria()));
                                            for(AdjuntosCategoriaTipo aCategoriaTipo: acTipos){
                                                TreeNode t=new DefaultTreeNode(aCategoriaTipo.getNombre(), c);
                                                if( aCategoriaTipo!=null ){
                                                    nombresAdjuntos=new ArrayList<>();
                                                    nombresAdjuntos.addAll(gestorEvaluacionAdjuntos.cargarListaAdjuntosCategoriaTipo(planMaestro.getEstablecimiento().getCodigoEstablecimiento(),
                                                            planMaestro.getEvaluacion().getEvaluacionPK().getCodEvaluacion().intValue(),"H",seccion.getSeccionPK().getCodSeccion(),
                                                            seccionDetalle.getSeccionDetallePK().getCodDetalle(),seccionDetalleItems.getSeccionDetalleItemsPK().getCodItem(),aCategoria.getCodCategoria(),aCategoriaTipo.getAdjuntosCategoriaTipoPK().getCodCategoriaTipo()));
                                                    Integer n=0;
                                                    List<EvaluacionAdjuntos> nombresAdjuntoss=new ArrayList<>();                                                    
                                                    EvaluacionAdjuntos ea=new EvaluacionAdjuntos();                                                    
                                                    if(nombresAdjuntos.size()!=0){
                                                        n=nombresAdjuntos.size()-1;                                                                                                        
                                                        ea.setArchivo(nombresAdjuntos.get(n).getArchivo());
                                                        nombresAdjuntoss.add(ea);
                                                    }
                                                    for(EvaluacionAdjuntos eAdjuntos:nombresAdjuntoss){
                                                        TreeNode ad=new DefaultTreeNode(eAdjuntos.getArchivo(), t);
                                                    }
                                                    
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            raizVerificar= new DefaultTreeNode("VERIFICAR", null);                                
            secciones = gestorEstandar.cargarListaSecciones("V");
            for (Seccion seccion : secciones) {
                if (seccion != null ) {
                    TreeNode s = new DefaultTreeNode(seccion.getNombre(),raizVerificar);
                    sDetalles= gestorEstandar.cargarListaSecciondetalles("V", seccion.getSeccionPK().getCodSeccion());
                    for(SeccionDetalle seccionDetalle:sDetalles){
                        TreeNode d=new DefaultTreeNode(seccionDetalle.getNombre(), s);
                        if(seccionDetalle!=null){
                            sdItems=gestorEstandar.cargarListaSecciondetalleitemss("V", seccion.getSeccionPK().getCodSeccion(), seccionDetalle.getSeccionDetallePK().getCodDetalle());
                            for(SeccionDetalleItems seccionDetalleItems: sdItems){
                                TreeNode i=new DefaultTreeNode(seccionDetalleItems.getNombre(), d);
                                if(seccionDetalleItems != null){                                                
                                    sdiCategorias=new ArrayList<>();
                                    sdiCategorias.addAll((Collection<? extends AdjuntosCategoria>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoria(seccionDetalleItems.getSeccionDetalleItemsPK()));                                                                                                
                                    for(AdjuntosCategoria aCategoria: sdiCategorias){
                                        TreeNode c=new DefaultTreeNode(aCategoria.getNombre(), i);
                                        if(aCategoria!=null){
                                            acTipos=new ArrayList<>();
                                            acTipos.addAll((Collection<? extends AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(aCategoria.getCodCategoria()));
                                            for(AdjuntosCategoriaTipo aCategoriaTipo: acTipos){
                                                TreeNode t=new DefaultTreeNode(aCategoriaTipo.getNombre(), c);
                                                if( aCategoriaTipo!=null ){
                                                    nombresAdjuntos=new ArrayList<>();
                                                    nombresAdjuntos.addAll(gestorEvaluacionAdjuntos.cargarListaAdjuntosCategoriaTipo(planMaestro.getEstablecimiento().getCodigoEstablecimiento(),
                                                            planMaestro.getEvaluacion().getEvaluacionPK().getCodEvaluacion().intValue(),"V",seccion.getSeccionPK().getCodSeccion(),
                                                            seccionDetalle.getSeccionDetallePK().getCodDetalle(),seccionDetalleItems.getSeccionDetalleItemsPK().getCodItem(),aCategoria.getCodCategoria(),aCategoriaTipo.getAdjuntosCategoriaTipoPK().getCodCategoriaTipo()));
                                                    Integer n=0;
                                                    List<EvaluacionAdjuntos> nombresAdjuntoss=new ArrayList<>();                                                    
                                                    EvaluacionAdjuntos ea=new EvaluacionAdjuntos();                                                    
                                                    if(nombresAdjuntos.size()!=0){
                                                        n=nombresAdjuntos.size()-1;                                                                                                        
                                                        ea.setArchivo(nombresAdjuntos.get(n).getArchivo());
                                                        nombresAdjuntoss.add(ea);
                                                    }
                                                    for(EvaluacionAdjuntos eAdjuntos:nombresAdjuntoss){
                                                        TreeNode ad=new DefaultTreeNode(eAdjuntos.getArchivo(), t);
                                                    }
                                                    
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            raizActuar= new DefaultTreeNode("ACTUAR", null);                                
            secciones = gestorEstandar.cargarListaSecciones("A");
            for (Seccion seccion : secciones) {
                if (seccion != null ) {
                    TreeNode s = new DefaultTreeNode(seccion.getNombre(),raizActuar);
                    sDetalles= gestorEstandar.cargarListaSecciondetalles("A", seccion.getSeccionPK().getCodSeccion());
                    for(SeccionDetalle seccionDetalle:sDetalles){
                        TreeNode d=new DefaultTreeNode(seccionDetalle.getNombre(), s);
                        if(seccionDetalle!=null){
                            sdItems=gestorEstandar.cargarListaSecciondetalleitemss("A", seccion.getSeccionPK().getCodSeccion(), seccionDetalle.getSeccionDetallePK().getCodDetalle());
                            for(SeccionDetalleItems seccionDetalleItems: sdItems){
                                TreeNode i=new DefaultTreeNode(seccionDetalleItems.getNombre(), d);
                                if(seccionDetalleItems != null){                                                
                                    sdiCategorias=new ArrayList<>();
                                    sdiCategorias.addAll((Collection<? extends AdjuntosCategoria>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoria(seccionDetalleItems.getSeccionDetalleItemsPK()));                                                                                                
                                    for(AdjuntosCategoria aCategoria: sdiCategorias){
                                        TreeNode c=new DefaultTreeNode(aCategoria.getNombre(), i);
                                        if(aCategoria!=null){
                                            acTipos=new ArrayList<>();
                                            acTipos.addAll((Collection<? extends AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(aCategoria.getCodCategoria()));
                                            for(AdjuntosCategoriaTipo aCategoriaTipo: acTipos){
                                                TreeNode t=new DefaultTreeNode(aCategoriaTipo.getNombre(), c);
                                                if( aCategoriaTipo!=null ){
                                                    nombresAdjuntos=new ArrayList<>();
                                                    nombresAdjuntos.addAll(gestorEvaluacionAdjuntos.cargarListaAdjuntosCategoriaTipo(planMaestro.getEstablecimiento().getCodigoEstablecimiento(),
                                                            planMaestro.getEvaluacion().getEvaluacionPK().getCodEvaluacion().intValue(),"A",seccion.getSeccionPK().getCodSeccion(),
                                                            seccionDetalle.getSeccionDetallePK().getCodDetalle(),seccionDetalleItems.getSeccionDetalleItemsPK().getCodItem(),aCategoria.getCodCategoria(),aCategoriaTipo.getAdjuntosCategoriaTipoPK().getCodCategoriaTipo()));
                                                    Integer n=0;
                                                    List<EvaluacionAdjuntos> nombresAdjuntoss=new ArrayList<>();                                                    
                                                    EvaluacionAdjuntos ea=new EvaluacionAdjuntos();                                                    
                                                    if(nombresAdjuntos.size()!=0){
                                                        n=nombresAdjuntos.size()-1;                                                                                                        
                                                        ea.setArchivo(nombresAdjuntos.get(n).getArchivo());
                                                        nombresAdjuntoss.add(ea);
                                                    }
                                                    for(EvaluacionAdjuntos eAdjuntos:nombresAdjuntoss){
                                                        TreeNode ad=new DefaultTreeNode(eAdjuntos.getArchivo(), t);
                                                    }
                                                    
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        
        /*GestorEstandar gestorEstandar=new GestorEstandar();
        List<Seccion> seccionList=gestorEstandar.cargarListaSecciones("P");
        List<SeccionDetalle> seccionDetalleList=gestorEstandar.cargarListaSecciondetalle(COMPONENTES_REFRESCAR, Integer.SIZE)
        
        
        for(int i=0;i<seccionList.size();i++){
            nombresSeccion.add(seccionList.get(i).getNombre());
        }
        
        adicionarSeccion(nombresSeccion, raizPlanear);
        adicionarDetalle(nombresDetalle, raizHacer);*/
    }
    
    
    
    
    public void onNodeSelect(NodeSelectEvent event) throws FileNotFoundException, Exception {        
        
        GestorEvaluacionAdjuntos gestorEvaluacionAdjuntos=new GestorEvaluacionAdjuntos();
        
        String nodes = event.getTreeNode().getData().toString();                                
        
        String direccion= gestorEvaluacionAdjuntos.cargarDireccionAdjunto(nodes); 
        
        int ini=direccion.length()-3;
        
        
        String fmt=direccion.substring(ini, direccion.length());
        
        
        if(!fmt.equals("pdf")){
            
            InputStream inputStream = new FileInputStream(direccion);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                baos = new ByteArrayOutputStream();

                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }

            fileDownloadTree=new DefaultStreamedContent(new ByteArrayInputStream(baos.toByteArray()));
            
        }else{
            if(direccion!=""){
            
            InputStream inputStream = new FileInputStream(direccion);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                baos = new ByteArrayOutputStream();

                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }

            fileDownloadTree=new DefaultStreamedContent(new ByteArrayInputStream(baos.toByteArray()));

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

            session.setAttribute("pdfBytesArray", baos.toByteArray());        
            }else{
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("pdfBytesArray", node);
            }
        }
        
       
        
    }

    public StreamedContent getFileDownloadTree(String name) throws Exception {
        
        try {
            GestorEvaluacionAdjuntos gestorEvaluacionAdjuntos=new GestorEvaluacionAdjuntos();

            String direccion= gestorEvaluacionAdjuntos.cargarDireccionAdjunto(name); 
            
            if(!direccion.equals("")){
                InputStream inputStream = new FileInputStream(direccion);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    byte[] buffer = new byte[1024];
                    baos = new ByteArrayOutputStream();

                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesRead);
                    }
                    
                fileDownloadTree=new DefaultStreamedContent(new ByteArrayInputStream(baos.toByteArray()),"", name);
                return fileDownloadTree;
            }
            
        } catch (Exception e) {
            UtilMSG.addErrorMsg("Archivo No Existe", "El adjunto " + node+ ", no fue encontrado. Si el problema persiste contactenos para asistirle.");
            UtilLog.generarLog(this.getClass(), e);
        }
        fileDownloadTree=null;
        return fileDownloadTree;
    }

    public void setFileDownloadTree(StreamedContent fileDownloadTree) {
        this.fileDownloadTree = fileDownloadTree;
    }

    public File getDestination() {
        return destination;
    }

    public void setDestination(File destination) {
        this.destination = destination;
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
            List<EvaluacionAdjuntos> evaluacionAdjuntosList=new ArrayList<>();
            GestorEvaluacionAdjuntos gestorEvaluacionAdjuntos=new GestorEvaluacionAdjuntos();
            PlanMaestro pm = (PlanMaestro) UtilJSF.getBean("varPlanMaestro");
            pm.setPlanTituloList((List<PlanTitulo>) gestorPlanTitulo.cargarPlanTituloList(pm.getPlanMaestroPK().getCodigoEstablecimiento(), pm.getPlanMaestroPK().getCodEvaluacion()));
            UtilJSF.setBean("planMaestro", pm, UtilJSF.SESSION_SCOPE);            
            evaluacion=pm.getEvaluacion();
            establecimiento=pm.getEstablecimiento();
            evaluacionAdjuntosList.addAll(gestorEvaluacionAdjuntos.cargarArchivosMaxVersion(pm.getEvaluacion().getEvaluacionPK().getCodEvaluacion()));           
            Date fecha_vigencia=null;          
           
            Integer num=0;
            for(int k=0; k<evaluacionAdjuntosList.size();k++){
                //fecha vigencia de archivo de evaluacion
                evaluacionAdjuntosList.get(k).getEvaluacionAdjuntosPK().setCodigoEstablecimiento(pm.getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento());
                fecha_vigencia= gestorEvaluacionAdjuntos.cargarFechaVigencia(evaluacionAdjuntosList.get(k));
                if(hoy.after(fecha_vigencia)){                                        
                    num++;
                    pm.setNumArchivosVencidos(num);
                }

            }            
            if(s.getUsuarios().getRoles().getCodigoRol()==4){
                this.procesarPlanMaestro();
                rootCiclos();
            return ("/seguimiento/pm/vuwopux_1.xhtml?faces-redirect=true");            
            }else{
                this.procesarPlanMaestro();
                rootCiclos();                
                return ("/seguimiento/plan-maestro_1.xhtml?faces-redirect=true");
                
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
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            PlanMaestro pm = (PlanMaestro) UtilJSF.getBean("planMaestro");            
            return gestorEvaluacion.avanceEvaluacion(pm.getPlanMaestroPK().getCodigoEstablecimiento(), pm.getPlanMaestroPK().getCodEvaluacion());            
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
    
    public String getStyleEstado() {
        String style = "";
                
        if(siAdjunto==true){
            style=" background: #4cae4c;";
        }else{
            style="";
        }
        
        return style;
        
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Date getHoy() {
        SimpleDateFormat f=new SimpleDateFormat("dd/MM/yyyy");
        f.format(hoy);
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
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

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Boolean getSiAdjunto() {
        return siAdjunto;
    }

    public void setSiAdjunto(Boolean siAdjunto) {
        this.siAdjunto = siAdjunto;
    }


    public List<String> getNombresSeccion() {
        return nombresSeccion;
    }

    public void setNombresSeccion(List<String> nombresSeccion) {
        this.nombresSeccion = nombresSeccion;
    }

    public TreeNode getRaizPlanear() {
        return raizPlanear;
    }

    public void setRaizPlanear(TreeNode raizPlanear) {
        this.raizPlanear = raizPlanear;
    }

    public TreeNode getRaizHacer() {
        return raizHacer;
    }

    public void setRaizHacer(TreeNode raizHacer) {
        this.raizHacer = raizHacer;
    }

    public TreeNode getRaizVerificar() {
        return raizVerificar;
    }

    public void setRaizVerificar(TreeNode raizVerificar) {
        this.raizVerificar = raizVerificar;
    }

    public TreeNode getRaizActuar() {
        return raizActuar;
    }

    public void setRaizActuar(TreeNode raizActuar) {
        this.raizActuar = raizActuar;
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

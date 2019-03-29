/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.matriz;

import com.gestor.controller.Propiedades;
import com.gestor.entity.App;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.controlador.GestorAdjuntosCategoria;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.matriz.controlador.GestorMatrizRiesgos;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Cargos;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Funciones;
import com.gestor.publico.Usuarios;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorUsuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


/**
 *
 * @author fjvc
 */
@ManagedBean(name = "uiMatrizRiesgos")
@SessionScoped
public class UIMatrizRiesgos implements Serializable{
    
    private Establecimiento establecimiento = new Establecimiento();
    private MatrizRiesgos matrizRiesgos= new MatrizRiesgos();
    public static final String COMPONENTES_REFRESCAR = "";
    
    private boolean guardarActivo = true;
    private boolean nuevoActivo = false;
    private boolean consultarActivo = false;
    private boolean cancelarActivo = false;
    private boolean eliminarActivo = false;
    private boolean volverActivo = true;
    
    private StreamedContent fileDownloadMatriz;
    private StreamedContent fileDownloadMatriz2;
    
    private GestorMatrizRiesgos gestorMatrizRiesgos;
    private GestorEstablecimiento gestorEstablecimiento;
    
    private List<Usuarios> usuariosList = new ArrayList<>();
    private List<Cargos> cargosList= new ArrayList<>();
    private List<Riesgo> riesgosList= new ArrayList<>();
    private List<Exposicion> exposicionList= new ArrayList<>();
    private List<Funciones> funcionesList= new ArrayList<>(); 
    private List<CategoriaRiesgo> categoriaRiesgosList= new ArrayList<>();
    private List<RiesgoPosible> riesgoPosibleList = new ArrayList<>();
    private List<NivelDeficiencia> nivelDeficienciaList= new ArrayList<>();
    private List<NivelExposicion> nvielExposcionList=new ArrayList<>();
    private List<NivelConsecuencia> nivelConsecuenciaList= new ArrayList<>();
    private List<MedidasIntervencion> medidasIntervecionList= new ArrayList<>();
    private List<ElementosProteccion> elementosProteccionList= new ArrayList<>();
    private List<AdjuntosCategoria> adjuntosCategorias = new ArrayList<>();    
    private List<AdjuntosCategoria> adjuntosCategorias2 = new ArrayList<>();    
    
    private List<Funciones> cargosActividadesEstablecimientoList= new ArrayList<>();
    
    
    private List<MatrizRiesgos> matricesRiesgoEstablecimientoList = new ArrayList<>();
            
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();

    private Boolean filtroActivo = Boolean.TRUE;
    
    private Evaluacion evaluacion = new Evaluacion();
    private String nom=" ";
    private String nom2=" ";
    
    private List<Evaluacion> evaluacionList = new ArrayList<>();   
    
    public UIMatrizRiesgos(){ 
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            GestorUsuario gestorUsuario = new GestorUsuario();
            
            establecimientoList = new ArrayList<>();
            establecimientoList.addAll(s.getEstablecimientoList());
    
            usuariosList = new ArrayList<>();
            usuariosList.addAll(gestorUsuario.cargarListaUsuarios());            
            

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
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
    
    public String subirItemMatriz(){
        try {                      
            gestorMatrizRiesgos=new GestorMatrizRiesgos();
            Funciones f=(Funciones) UtilJSF.getBean("varCargosEstablecimiento");
            evaluacion= (Evaluacion) UtilJSF.getBean("evaluacion");
            
            matrizRiesgos = gestorMatrizRiesgos.cargarMatrizRiesgosCargoActividad(f.getCargos().getCodCargo(), f.getCodFuncion(), evaluacion.getEvaluacionPK().getCodigoEstablecimiento());
            
            if(matrizRiesgos == null){
                matrizRiesgos=new MatrizRiesgos();
                matrizRiesgos.getCargos().setCodCargo(f.getCargos().getCodCargo());
                matrizRiesgos.getCargos().setNombre(f.getCargos().getNombre());
                matrizRiesgos.getFunciones().setCodFuncion(f.getCodFuncion());
                matrizRiesgos.getFunciones().setNombre(f.getNombre());                 
            }
                this.cargarRiesgos();
                this.cargarMedidasIntervecion();
                this.cargarElementosProteccion();
                this.cargarNivelDeficiencia();
                this.cargarNivelConsecuencia();
                this.cargarNivelExposicion();
                this.cargarAdjuntosCategoriaTipo();
                this.cargarAdjuntosCategoriaTipo2();
                this.cargarRiesgoPosibles();
                this.cargarExposiciones();
            
            
            return ("/matriz/crear-matriz-riesgos.xhtml?faces-redirect=true");
        } catch (Exception ex) {
            return ("/matriz/crear-matriz-riesgos.xhtml?faces-redirect=true");
        }         
    }
    
    public String crearMatrizNueva(){
        try {                      
            this.cargarCargosEstablecimiento();
            this.cargarFunciones();
            this.cargarRiesgos();
            this.cargarRiesgoPosibles();
            
            this.cargarMedidasIntervecion();
            this.cargarElementosProteccion();
            this.cargarNivelDeficiencia();
            this.cargarNivelConsecuencia();
            this.cargarNivelExposicion();
            this.cargarAdjuntosCategoriaTipo();
            this.cargarAdjuntosCategoriaTipo2();
            this.cargarExposiciones();
            return ("/matriz/crear-matriz-riesgos.xhtml?faces-redirect=true");
        } catch (Exception e) {
            return ("/matriz/crear-matriz-riesgos.xhtml?faces-redirect=true");          
        }
        
    }
    
    public String getStyleAceptabilidad() {
        String style = "padding: 20px;width: 50%;"
                + "opacity: 0.83;background-color: #539aa0;"
                + "transition: opacity 0.6s; font-weight: bold;";          
        if(matrizRiesgos.getAceptabilidadRiesgo() == null){
            return style="";
        }
        if(matrizRiesgos.getAceptabilidadRiesgo().equals("ACEPTABLE") ){
            style += "color: #33ff33;";
        }if(matrizRiesgos.getAceptabilidadRiesgo().equals("ACEPTABLE CON CONTROL")){
                style += "color: #ffff66;";
        }if(matrizRiesgos.getAceptabilidadRiesgo().equals("NO ACEPTABLE")){
            style += "color: #ff3333;";
        }
        return style;
    }
    
    public String getStyleDiligenciado() {
        Funciones f=(Funciones) UtilJSF.getBean("varCargosEstablecimiento");
        String style = "padding: 10px;width 50%;"
                + "opacity: 0.83;background-color: #006699;"
                + "transition: opacity 0.6s; font-weight: bold;";          
        if(f.getDiligenciado()==true){
            style+= "color: #99ff99;";
        }
        if(f.getDiligenciado()==false){
            style+= "color: #ff6666;";
        }
        return style;
    }
    

    public String subirItemevaluacion() {
        try{  
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("varEvaluacion");      
            UtilJSF.setBean("evaluacion", ev , UtilJSF.SESSION_SCOPE);
            
            //
            this.cargarCargosFuncionesEstablecimiento();            
            return ("/matriz/administrar-matriz-cargos-establecimiento.xhtml?faces-redirect=true");
        }catch(Exception e){
        return ("/matriz/matriz-cargos-establecimiento.xhtml?faces-redirect=true");  
        }
    }
    
    public void cargarCargosEstablecimiento(){
        try {
            evaluacion= (Evaluacion) UtilJSF.getBean("evaluacion");
            cargosList = new ArrayList<>();
            gestorEstablecimiento= new GestorEstablecimiento();
            cargosList.addAll((Collection<? extends Cargos>) gestorEstablecimiento.cargarListaCargosEstablecimiento(evaluacion.getEstablecimiento().getCodigoEstablecimiento()));            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarMatrizCargoEstablecimiento(){
        try {
            evaluacion= (Evaluacion) UtilJSF.getBean("evaluacion");
            matricesRiesgoEstablecimientoList = new ArrayList<>();
            gestorMatrizRiesgos= new GestorMatrizRiesgos();
            matricesRiesgoEstablecimientoList.addAll((Collection<? extends MatrizRiesgos>) gestorMatrizRiesgos.cargarListaMatrizCargosEstablecimiento(evaluacion.getEstablecimiento().getCodigoEstablecimiento()));            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarCargosFuncionesEstablecimiento(){
        try {
            evaluacion= (Evaluacion) UtilJSF.getBean("evaluacion");
            cargosActividadesEstablecimientoList = new ArrayList<>();
            gestorMatrizRiesgos= new GestorMatrizRiesgos();
            cargosActividadesEstablecimientoList.addAll((Collection<? extends Funciones>) gestorMatrizRiesgos.cargarListaCargosFuncionesEstablecimiento(evaluacion.getEstablecimiento().getCodigoEstablecimiento()));            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public void cargarRiesgos() throws Exception{
            gestorMatrizRiesgos= new GestorMatrizRiesgos();
            riesgosList = new ArrayList<>();
            riesgosList.addAll((Collection<? extends Riesgo>) gestorMatrizRiesgos.cargarListaRiesgos());
            
            categoriaRiesgosList= new ArrayList<>();
            categoriaRiesgosList.addAll((Collection<? extends CategoriaRiesgo>) gestorMatrizRiesgos.cargarListaCategoriaRiesgos());
            
    }
    
    public void cargarExposiciones() throws Exception{
            gestorMatrizRiesgos= new GestorMatrizRiesgos();
            exposicionList = new ArrayList<>();
            exposicionList.addAll((Collection<? extends Exposicion>) gestorMatrizRiesgos.cargarListaExposiciones(matrizRiesgos.getRiesgo().getCodRiesgo()));
    }
    
    public void cargarRiesgoPosibles() throws Exception{
            gestorMatrizRiesgos= new GestorMatrizRiesgos();
            riesgoPosibleList = new ArrayList<>();
            riesgoPosibleList.addAll((Collection<? extends RiesgoPosible>) gestorMatrizRiesgos.cargarListaRiesgoPosibles(matrizRiesgos.getCategoriaRiesgo().getCodCategoriaRiesgo()));
    }
    
    public void cargarFunciones() {
        try {            
            funcionesList = new ArrayList<>();
            gestorMatrizRiesgos = new GestorMatrizRiesgos();
            this.funcionesList.addAll((Collection<? extends Funciones>) gestorMatrizRiesgos.cargarListaFunciones(matrizRiesgos.getCargos().getCodCargo()));            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarNivelDeficiencia() {
        try {            
            nivelDeficienciaList = new ArrayList<>();
            gestorMatrizRiesgos = new GestorMatrizRiesgos();
            this.nivelDeficienciaList.addAll((Collection<? extends NivelDeficiencia>) gestorMatrizRiesgos.cargarListaNivelDeficiencia());            
            
            if(this.matrizRiesgos.getNivelExposcion().getValor() != 0){
                this.matrizRiesgos.setNivelProbabilidad(matrizRiesgos.getNivelExposcion().getValor()*matrizRiesgos.getNivelDeficiencia().getValor());
                if(matrizRiesgos.getNivelProbabilidad()<=4){
                   this. matrizRiesgos.setInterpretacionProb("BAJO");
                }if(matrizRiesgos.getNivelProbabilidad()<9){
                    this.matrizRiesgos.setInterpretacionProb("MEDIO");
                }if(matrizRiesgos.getNivelProbabilidad()>=10){
                    this.matrizRiesgos.setInterpretacionProb("ALTO");
                }          
                this.cargarNivelExposicion();
                this.cargarNivelConsecuencia();
            }
            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarNivelExposicion() {
        try {   
            
            nvielExposcionList = new ArrayList<>();
            gestorMatrizRiesgos = new GestorMatrizRiesgos();
            this.nvielExposcionList.addAll((Collection<? extends NivelExposicion>) gestorMatrizRiesgos.cargarListaNivelExposicion()); 
            
            if(this.matrizRiesgos.getNivelDeficiencia().getValor() != 0){
                this.matrizRiesgos.setNivelProbabilidad(matrizRiesgos.getNivelExposcion().getValor()*matrizRiesgos.getNivelDeficiencia().getValor());
                if(matrizRiesgos.getNivelProbabilidad()<=4){
                   this.matrizRiesgos.setInterpretacionProb("BAJO");
                }if(matrizRiesgos.getNivelProbabilidad()>4 && matrizRiesgos.getNivelProbabilidad()<9){
                    this.matrizRiesgos.setInterpretacionProb("MEDIO");
                }if(matrizRiesgos.getNivelProbabilidad()>=10){
                    this.matrizRiesgos.setInterpretacionProb("ALTO");
                }
            this.cargarNivelConsecuencia();
            }
            
            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarNivelConsecuencia() {
        try {            
            nivelConsecuenciaList = new ArrayList<>();
            gestorMatrizRiesgos = new GestorMatrizRiesgos();
            this.nivelConsecuenciaList.addAll((Collection<? extends NivelConsecuencia>) gestorMatrizRiesgos.cargarListaNivelConsecuencia());
            
            this.matrizRiesgos.setNivelRiesgo(matrizRiesgos.getNivelProbabilidad()*matrizRiesgos.getNivelConsecuencia().getValor());
            if(matrizRiesgos.getNivelRiesgo()!=0){
                if(matrizRiesgos.getNivelRiesgo()>=600){
                    matrizRiesgos.setInterpretacionNr("I");
                }
                if(matrizRiesgos.getNivelRiesgo()<500){
                    matrizRiesgos.setInterpretacionNr("II");
                }
                if(matrizRiesgos.getNivelRiesgo()<120){
                    matrizRiesgos.setInterpretacionNr("III");
                }
                if(matrizRiesgos.getNivelRiesgo()<20){
                    matrizRiesgos.setInterpretacionNr("IV");
                }
                
                if(!matrizRiesgos.getInterpretacionNr().equals("")){
                    if(matrizRiesgos.getInterpretacionNr().equals("I")){
                        matrizRiesgos.setAceptabilidadRiesgo("NO ACEPTABLE");
                    }
                    if(matrizRiesgos.getInterpretacionNr().equals("II")){
                        matrizRiesgos.setAceptabilidadRiesgo("ACEPTABLE CON CONTROL");
                    }
                    if(matrizRiesgos.getInterpretacionNr().equals("III")){
                        matrizRiesgos.setAceptabilidadRiesgo("ACEPTABLE");
                    }
                    if(matrizRiesgos.getInterpretacionNr().equals("IV")){
                        matrizRiesgos.setAceptabilidadRiesgo("ACEPTABLE");
                }
            }
            }
            
            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }    

    
    
    public void cargarMedidasIntervecion() {
        try {                                     
            medidasIntervecionList = new ArrayList<>();
            gestorMatrizRiesgos = new GestorMatrizRiesgos();
            this.medidasIntervecionList.addAll((Collection<? extends MedidasIntervencion>) gestorMatrizRiesgos.cargarListaMedidasIntervecion());                                    
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarElementosProteccion() {
        try {                                     
            elementosProteccionList = new ArrayList<>();
            gestorMatrizRiesgos = new GestorMatrizRiesgos();
            this.elementosProteccionList.addAll((Collection<? extends ElementosProteccion>) gestorMatrizRiesgos.cargarListaElementosProteccion());                                    
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void cargarAdjuntosCategoriaTipo() {
        try {
            GestorAdjuntosCategoria gestorAdjuntosCategoria = new GestorAdjuntosCategoria();    
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("evaluacion");      
            
            gestorMatrizRiesgos= new GestorMatrizRiesgos();
            adjuntosCategorias= new ArrayList<>();
            adjuntosCategorias.addAll(gestorAdjuntosCategoria.cargarListaAdjuntosCategoriapm());            
            
            if(matrizRiesgos.getAdjuntosCategoria().getCodCategoria() != null){
                matrizRiesgos.getAdjuntosCategoria().setAdjuntosCategoriaTipoList((List<AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(matrizRiesgos.getAdjuntosCategoria().getCodCategoria()));                                                
            }    
            
            if(matrizRiesgos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo()!=null){
                nom = gestorMatrizRiesgos.cargarNombreAdjunto(ev.getEvaluacionPK().getCodigoEstablecimiento(),  ev.getEvaluacionPK().getCodEvaluacion(), matrizRiesgos.getAdjuntosCategoria().getCodCategoria(), matrizRiesgos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo());                 
            }
            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
     public void cargarAdjuntosCategoriaTipo2() {
        try {
            GestorAdjuntosCategoria gestorAdjuntosCategoria = new GestorAdjuntosCategoria();    
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("evaluacion");      
            
            gestorMatrizRiesgos= new GestorMatrizRiesgos();
            adjuntosCategorias2= new ArrayList<>();
            adjuntosCategorias2.addAll(gestorAdjuntosCategoria.cargarListaAdjuntosCategoriapm());                        
            
            if(matrizRiesgos.getAdjuntosCategoria2().getCodCategoria()!= null){
                matrizRiesgos.getAdjuntosCategoria2().setAdjuntosCategoriaTipoList((List<AdjuntosCategoriaTipo>) gestorAdjuntosCategoria.cargarListaAdjuntosCategoriaTipo(matrizRiesgos.getAdjuntosCategoria2().getCodCategoria()));
            }
            if(matrizRiesgos.getAdjuntosCategoria2().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo() !=null){
                nom2 = gestorMatrizRiesgos.cargarNombreAdjunto(ev.getEvaluacionPK().getCodigoEstablecimiento(),  ev.getEvaluacionPK().getCodEvaluacion(), matrizRiesgos.getAdjuntosCategoria2().getCodCategoria(), matrizRiesgos.getAdjuntosCategoria2().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo());                 
            }
            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public String guardar() {
        try {
            gestorMatrizRiesgos= new GestorMatrizRiesgos();            
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            if(matricesRiesgoEstablecimientoList.isEmpty()){
                matrizRiesgos.setCodMatriz(1);
            }
            if(matrizRiesgos.getCodMatriz()==null){
            matrizRiesgos.setCodMatriz(matricesRiesgoEstablecimientoList.size()+1);
            }
            
            
            if(matrizRiesgos.getDescripcionMedida2().equals("") || matrizRiesgos.getAdjuntosCategoria2()==null){
                matrizRiesgos.setDescripcionMedida2("");                                
                matrizRiesgos.setAdjuntosCategoria2(new AdjuntosCategoria(0));    
                matrizRiesgos.getAdjuntosCategoria2().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().setCodCategoriaTipo(0);
            }
            
            MatrizRiesgos mr= new MatrizRiesgos(e.getEvaluacionPK().getCodigoEstablecimiento(), matrizRiesgos.getCargos().getCodCargo(), matrizRiesgos.getCodMatriz(), 
                    matrizRiesgos.getFunciones().getCodFuncion(), matrizRiesgos.getTarea(), matrizRiesgos.isRutinaria(), matrizRiesgos.getRiesgo().getCodRiesgo(), matrizRiesgos.getRiesgoPosible().getCodRiesgoPosible(),
                    matrizRiesgos.getExposicion().getCodExposicion(), matrizRiesgos.getCategoriaRiesgo().getCodCategoriaRiesgo(), matrizRiesgos.isFuente(), matrizRiesgos.isMedio(), matrizRiesgos.isIndividuo(),
                    matrizRiesgos.getNivelDeficiencia().getCodNivelDef(), matrizRiesgos.getNivelExposcion().getCodNivelExp(), matrizRiesgos.getNivelConsecuencia().getCodNivelConsec(), matrizRiesgos.getNivelRiesgo(), 
                    matrizRiesgos.getInterpretacionNr(), matrizRiesgos.getAceptabilidadRiesgo(),matrizRiesgos.getNivelProbabilidad(),matrizRiesgos.getInterpretacionProb(), matrizRiesgos.getNumExpuestos(), 
                    matrizRiesgos.getPeorConsecuencia(), matrizRiesgos.isReqLegal(), matrizRiesgos.getMedidasIntervencion().getCodMedida(), matrizRiesgos.getDescripcionMedida(), 
                    matrizRiesgos.getDescripcionMedida2(), matrizRiesgos.getElementoProteccion().getCodElemento(), matrizRiesgos.getAdjuntosCategoria().getCodCategoria(), matrizRiesgos.getAdjuntosCategoria2().getCodCategoria(),
                    matrizRiesgos.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo(), matrizRiesgos.getAdjuntosCategoria2().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo(),
                    matrizRiesgos.getObservaciones());
            //gestorMatrizRiesgos.validarMatrizRiesgos(mr);            
            gestorMatrizRiesgos.almacenarMatrizRiesgo(mr);
            
            UtilMSG.addSuccessMsg("Matriz Creada correctamente.");            
        } catch (Exception ex) {
            
        }       
        return "";
    }
    
    public String volver(){ 
        adjuntosCategorias=new ArrayList<>();
        adjuntosCategorias2=new ArrayList<>();
        this.cargarCargosFuncionesEstablecimiento();
        return ("/matriz/administrar-matriz-cargos-establecimiento.xhtml?faces-redirect=true");        
    }
    
    public StreamedContent getFileDownloadMatriz() {        
        try {             
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("evaluacion");      
            
            Properties p = Propiedades.getInstancia().getPropiedades();
            String rutaAdjunto = p.getProperty("rutaAdjunto") + File.separator + App.ADJUNTO_PREFIJO + ev.getEvaluacionPK().getCodEvaluacion();
            if(matrizRiesgos.getAdjuntosCategoria() == null){
                return null;
            }
            InputStream stream = new FileInputStream(rutaAdjunto+ File.separator + nom);
            fileDownloadMatriz = new DefaultStreamedContent(stream, null, rutaAdjunto);
        } catch (FileNotFoundException ex) {
            UtilMSG.addErrorMsg("Archivo No Existe", "El adjunto " + nom + ", no fue encontrado. Si el problema persiste contactenos para asistirle.");
            UtilLog.generarLog(this.getClass(), ex);
        }
        return fileDownloadMatriz;
    }

    public void setFileDownloadMatriz(StreamedContent fileDownloadMatriz) {
        this.fileDownloadMatriz = fileDownloadMatriz;
    }

    public StreamedContent getFileDownloadMatriz2() {        
        try {             
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("evaluacion");      
            
            Properties p = Propiedades.getInstancia().getPropiedades();
            String rutaAdjunto2 = p.getProperty("rutaAdjunto") + File.separator + App.ADJUNTO_PREFIJO + ev.getEvaluacionPK().getCodEvaluacion();
            if(matrizRiesgos.getAdjuntosCategoria2() == null){
                return null;
            }
            InputStream stream2 = new FileInputStream(rutaAdjunto2+ File.separator + nom2);
            fileDownloadMatriz2 = new DefaultStreamedContent(stream2, null, rutaAdjunto2);
        } catch (FileNotFoundException ex) {
            UtilMSG.addErrorMsg("Archivo No Existe", "El adjunto " + nom2 + ", no fue encontrado. Si el problema persiste contactenos para asistirle.");
            UtilLog.generarLog(this.getClass(), ex);
        }
        return fileDownloadMatriz2;
    }

    public List<Funciones> getCargosActividadesEstablecimientoList() {
        return cargosActividadesEstablecimientoList;
    }

    public void setCargosActividadesEstablecimientoList(List<Funciones> cargosActividadesEstablecimientoList) {
        this.cargosActividadesEstablecimientoList = cargosActividadesEstablecimientoList;
    }


    public void setFileDownloadMatriz2(StreamedContent fileDownloadMatriz2) {
        this.fileDownloadMatriz2 = fileDownloadMatriz2;
    }

    public String getNom2() {
        return nom2;
    }

    public void setNom2(String nom2) {
        this.nom2 = nom2;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
       
    public String getComponentesRefrescar() {
        return COMPONENTES_REFRESCAR;
    }


    public List<MatrizRiesgos> getMatricesRiesgoEstablecimientoList() {
        return matricesRiesgoEstablecimientoList;
    }

    public void setMatricesRiesgoEstablecimientoList(List<MatrizRiesgos> matricesRiesgoEstablecimientoList) {
        this.matricesRiesgoEstablecimientoList = matricesRiesgoEstablecimientoList;
    }

    public List<AdjuntosCategoria> getAdjuntosCategorias2() {
        return adjuntosCategorias2;
    }

    public void setAdjuntosCategorias2(List<AdjuntosCategoria> adjuntosCategorias2) {
        this.adjuntosCategorias2 = adjuntosCategorias2;
    }

    public List<AdjuntosCategoria> getAdjuntosCategorias() {
        return adjuntosCategorias;
    }

    public void setAdjuntosCategorias(List<AdjuntosCategoria> adjuntosCategorias) {
        this.adjuntosCategorias = adjuntosCategorias;
    }

    public List<ElementosProteccion> getElementosProteccionList() {
        return elementosProteccionList;
    }

    public void setElementosProteccionList(List<ElementosProteccion> elementosProteccionList) {
        this.elementosProteccionList = elementosProteccionList;
    }

    public List<MedidasIntervencion> getMedidasIntervecionList() {
        return medidasIntervecionList;
    }

    public void setMedidasIntervecionList(List<MedidasIntervencion> medidasIntervecionList) {
        this.medidasIntervecionList = medidasIntervecionList;
    }

    public List<NivelConsecuencia> getNivelConsecuenciaList() {
        return nivelConsecuenciaList;
    }

    public void setNivelConsecuenciaList(List<NivelConsecuencia> nivelConsecuenciaList) {
        this.nivelConsecuenciaList = nivelConsecuenciaList;
    }

    public List<NivelExposicion> getNvielExposcionList() {
        return nvielExposcionList;
    }

    public void setNvielExposcionList(List<NivelExposicion> nvielExposcionList) {
        this.nvielExposcionList = nvielExposcionList;
    }

    public List<NivelDeficiencia> getNivelDeficienciaList() {
        return nivelDeficienciaList;
    }

    public void setNivelDeficienciaList(List<NivelDeficiencia> nivelDeficienciaList) {
        this.nivelDeficienciaList = nivelDeficienciaList;
    }

    public List<RiesgoPosible> getRiesgoPosibleList() {
        return riesgoPosibleList;
    }

    public void setRiesgoPosibleList(List<RiesgoPosible> riesgoPosibleList) {
        this.riesgoPosibleList = riesgoPosibleList;
    }   

    public List<CategoriaRiesgo> getCategoriaRiesgosList() {
        return categoriaRiesgosList;
    }

    public void setCategoriaRiesgosList(List<CategoriaRiesgo> categoriaRiesgosList) {
        this.categoriaRiesgosList = categoriaRiesgosList;
    }

    public List<Exposicion> getExposicionList() {
        return exposicionList;
    }

    public void setExposicionList(List<Exposicion> exposicionList) {
        this.exposicionList = exposicionList;
    }
    
    public List<Riesgo> getRiesgosList() {
        return riesgosList;
    }

    public boolean isVolverActivo() {
        return volverActivo;
    }

    public void setVolverActivo(boolean volverActivo) {
        this.volverActivo = volverActivo;
    }

    public void setRiesgosList(List<Riesgo> riesgosList) {
        this.riesgosList = riesgosList;
    }

    public GestorEstablecimiento getGestorEstablecimiento() {
        return gestorEstablecimiento;
    }

    public void setGestorEstablecimiento(GestorEstablecimiento gestorEstablecimiento) {
        this.gestorEstablecimiento = gestorEstablecimiento;
    }

    public List<Cargos> getCargosList() {
        return cargosList;
    }

    public void setCargosList(List<Cargos> cargosList) {
        this.cargosList = cargosList;
    }

    public List<Funciones> getFuncionesList() {
        return funcionesList;
    }

    public void setFuncionesList(List<Funciones> funcionesList) {
        this.funcionesList = funcionesList;
    }

    public GestorMatrizRiesgos getGestorMatrizRiesgos() {
        return gestorMatrizRiesgos;
    }

    public void setGestorMatrizRiesgos(GestorMatrizRiesgos gestorMatrizRiesgos) {
        this.gestorMatrizRiesgos = gestorMatrizRiesgos;
    }    

    public MatrizRiesgos getMatrizRiesgos() {
        return matrizRiesgos;
    }

    public void setMatrizRiesgos(MatrizRiesgos matrizRiesgos) {
        this.matrizRiesgos = matrizRiesgos;
    }

    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public List<Establecimiento> getEstablecimientoList() {
        return establecimientoList;
    }

    public void setEstablecimientoList(List<Establecimiento> establecimientoList) {
        this.establecimientoList = establecimientoList;
    }

    public List<Establecimiento> getEstablecimientoListSeleccionado() {
        return establecimientoListSeleccionado;
    }

    public void setEstablecimientoListSeleccionado(List<Establecimiento> establecimientoListSeleccionado) {
        this.establecimientoListSeleccionado = establecimientoListSeleccionado;
    }
    
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public boolean isGuardarActivo() {
        return guardarActivo;
    }

    public void setGuardarActivo(boolean guardarActivo) {
        this.guardarActivo = guardarActivo;
    }

    public boolean isNuevoActivo() {
        return nuevoActivo;
    }

    public void setNuevoActivo(boolean nuevoActivo) {
        this.nuevoActivo = nuevoActivo;
    }

    public boolean isConsultarActivo() {
        return consultarActivo;
    }

    public void setConsultarActivo(boolean consultarActivo) {
        this.consultarActivo = consultarActivo;
    }

    public boolean isCancelarActivo() {
        return cancelarActivo;
    }

    public void setCancelarActivo(boolean cancelarActivo) {
        this.cancelarActivo = cancelarActivo;
    }

    public boolean isEliminarActivo() {
        return eliminarActivo;
    }

    public void setEliminarActivo(boolean eliminarActivo) {
        this.eliminarActivo = eliminarActivo;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public List<Evaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    public Boolean getFiltroActivo() {
        return filtroActivo;
    }

    public void setFiltroActivo(Boolean filtroActivo) {
        this.filtroActivo = filtroActivo;
    }
    
}
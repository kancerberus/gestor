/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.matriz;

import com.gestor.controller.GestorGeneral;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.ClaseHallazgo;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionPlanAccion;
import com.gestor.gestor.EvaluacionPlanAccionDetalle;
import com.gestor.gestor.EvaluacionPlanAccionDetalleNotas;
import com.gestor.gestor.EvaluacionPlanAccionDetalleNotasPK;
import com.gestor.gestor.EvaluacionPlanAccionDetallePK;
import com.gestor.gestor.EvaluacionPlanAccionPK;
import com.gestor.gestor.FuenteHallazgo;
import com.gestor.gestor.MotivoCorreccion;
import com.gestor.gestor.TipoAccion;
import com.gestor.gestor.controlador.GestorClaseHallazgo;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.gestor.controlador.GestorEvaluacionPlanAccion;
import com.gestor.gestor.controlador.GestorFuenteHallazgo;
import com.gestor.gestor.controlador.GestorMotivoCorreccion;
import com.gestor.gestor.controlador.GestorTipoAccion;
import com.gestor.matriz.controlador.GestorMatrizRiesgos;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Cargos;
import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Funciones;
import com.gestor.publico.RelCargosEstablecimiento;
import com.gestor.publico.Responsable;
import com.gestor.publico.TipoPlanAccion;
import com.gestor.publico.Usuarios;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorResponsable;
import com.gestor.publico.controlador.GestorUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    private boolean cancelarActivo = true;
    private boolean eliminarActivo = false;
    private boolean volverActivo = false;
    private String mrPdf="";
    private String responsable;
    private Usuarios usuariosSeleccionado;
    
    
    private Boolean modificarActivo = Boolean.FALSE;
    private Boolean filtroActivo = Boolean.TRUE;
    
    private EvaluacionPlanAccionDetalle evaluacionPlanAccionDetalle = new EvaluacionPlanAccionDetalle();
    private EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK = new EvaluacionPlanAccionDetallePK();
    private List<Responsable> responsables = new ArrayList<>();
    private List<ClaseHallazgo> clasehallazgos = new ArrayList<>();
    private List<FuenteHallazgo> fuentehallazgos = new ArrayList<>();
    private List<TipoAccion> tipoacciones = new ArrayList<>();
    private List<TipoPlanAccion> tiposTarea=new ArrayList<>();
    private List<MotivoCorreccion> motivocorrecciones  = new ArrayList<>();
    private List<CentroTrabajo> centrostrabajo = new ArrayList<>();
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles = new ArrayList<>();    
    
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
    
    private List<RelCargosEstablecimiento> cargosActividadesEstablecimientoList= new ArrayList<>();
    
    
    private List<MatrizRiesgos> matricesRiesgoEstablecimientoList = new ArrayList<>();
            
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();
    
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
            this.cargarCargosEstablecimiento();
            this.cargarFunciones();
            this.cargarRiesgos();
            this.cargarExposiciones();
            this.cargarRiesgoPosibles();
            this.cargarNivelDeficiencia();
            this.cargarNivelExposicion();
            this.cargarNivelConsecuencia();
            this.cargarMedidasIntervecion();
            this.cargarElementosProteccion();           
            this.cargarFunciones();
            this.cargarRiesgoPosibles();
            this.cargarExposiciones();
            this.cargarListaMatrizTareaRiesgo();
            matrizRiesgos = (MatrizRiesgos) UtilJSF.getBean("varRiesgoEstablecimiento");                                               
            return ("/matriz/crear-matriz-riesgos.xhtml?faces-redirect=true");
        } catch (Exception ex) {
            
        }  
        return ("/matriz/crear-matriz-riesgos.xhtml?faces-redirect=true");
    }
    
    public String crearMatrizNueva(){
        try {                   
            this.cargarCargosEstablecimiento();            
            this.cargarRiesgos();                   
            this.cargarMedidasIntervecion();
            this.cargarElementosProteccion();
            this.cargarNivelDeficiencia();
            this.cargarNivelConsecuencia();
            this.cargarNivelExposicion();                
            this.evaluacionPlanAccionDetalles.clear();
            matrizRiesgos=new MatrizRiesgos();
            return ("/matriz/crear-matriz-riesgos.xhtml?faces-redirect=true");
        } catch (Exception e) {
        return ("/matriz/crear-matriz-riesgos.xhtml?faces-redirect=true");
        }
        
    }
    
    public String getStyleAceptabilidad() {
        String style = "padding: 10px;width: 50%;"
                + "opacity: 0.83;background-color: #539aa0;"
                + "transition: opacity 0.6s; font-weight: bold;"; 
        if(matrizRiesgos!=null){
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
        }
        
        return style;
    }

    public String subirItemevaluacion() {
        try{  
            Evaluacion ev = (Evaluacion) UtilJSF.getBean("varEvaluacion");      
            UtilJSF.setBean("evaluacion", ev , UtilJSF.SESSION_SCOPE);            
            
            this.cargarMatrizCargoEstablecimiento();            
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
            cargosActividadesEstablecimientoList.addAll((Collection<? extends RelCargosEstablecimiento>) gestorMatrizRiesgos.cargarListaCargosFuncionesEstablecimiento(evaluacion.getEstablecimiento().getCodigoEstablecimiento()));            
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
            
            if(matrizRiesgos==null){
            matrizRiesgos = (MatrizRiesgos) UtilJSF.getBean("varRiesgoEstablecimiento");                           
            }
            gestorMatrizRiesgos= new GestorMatrizRiesgos();
            exposicionList = new ArrayList<>();
            exposicionList.addAll((Collection<? extends Exposicion>) gestorMatrizRiesgos.cargarListaExposiciones(matrizRiesgos.getRiesgo().getCodRiesgo()));
    }
    
    public void cargarRiesgoPosibles() {
        try {
                if(matrizRiesgos.getCategoriaRiesgo().getCodCategoriaRiesgo() != null && matrizRiesgos!=null){  
                    if(matrizRiesgos.getCategoriaRiesgo().getCodCategoriaRiesgo() !=  matrizRiesgos.getRiesgoPosible().getCodCategoriaRiesgo()){
                        matrizRiesgos.setRiesgoPosible(new RiesgoPosible());
                    }
                gestorMatrizRiesgos= new GestorMatrizRiesgos();
                riesgoPosibleList = new ArrayList<>();                
                riesgoPosibleList.addAll((Collection<? extends RiesgoPosible>) gestorMatrizRiesgos.cargarListaRiesgoPosibles(matrizRiesgos.getCategoriaRiesgo().getCodCategoriaRiesgo()));                
            }
        } catch (Exception e) {
        }        
    }
    
    public void cargarFunciones() {
        try {                 
            if(matrizRiesgos.getCargos()==null || matrizRiesgos.getCargos().getCodCargo()==null){
                matrizRiesgos = (MatrizRiesgos) UtilJSF.getBean("varRiesgoEstablecimiento");                           
            }
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
            
        }
    }
    
    public void cargarNivelExposicion() {
        try {   
            
            nvielExposcionList = new ArrayList<>();
            gestorMatrizRiesgos = new GestorMatrizRiesgos();
            this.nvielExposcionList.addAll((Collection<? extends NivelExposicion>) gestorMatrizRiesgos.cargarListaNivelExposicion()); 
            
            if(this.matrizRiesgos.getNivelDeficiencia().getValor() != null){
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
            if(matrizRiesgos.getNivelRiesgo()!=null){
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
    
    public String guardar() {
        try {
            gestorMatrizRiesgos= new GestorMatrizRiesgos();            
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorGeneral gestorGeneral=new GestorGeneral();
            
            if(matrizRiesgos.getCodRiesgoMatriz()==null){
                Long codMatriz=gestorGeneral.nextval(GestorGeneral.MATRIZ_MATRIZ_RIESGOS_COD_RIESGO_MATRIZ_SEQ);
                matrizRiesgos.setCodRiesgoMatriz(codMatriz.intValue());
            }
            
            MatrizRiesgos mr= new MatrizRiesgos(e.getEvaluacionPK().getCodigoEstablecimiento(), matrizRiesgos.getCargos().getCodCargo(), matrizRiesgos.getCodRiesgoMatriz(), 
                    matrizRiesgos.getFunciones().getCodFuncion(), matrizRiesgos.isRutinaria(), matrizRiesgos.getRiesgo().getCodRiesgo(), matrizRiesgos.getRiesgoPosible().getCodRiesgoPosible(),
                    matrizRiesgos.getExposicion().getCodExposicion(), matrizRiesgos.getCategoriaRiesgo().getCodCategoriaRiesgo(), matrizRiesgos.getFuente(), matrizRiesgos.getMedio(), matrizRiesgos.getIndividuo(),
                    matrizRiesgos.getNivelDeficiencia().getCodNivelDef(), matrizRiesgos.getNivelExposcion().getCodNivelExp(), matrizRiesgos.getNivelConsecuencia().getCodNivelConsec(), matrizRiesgos.getNivelRiesgo(), 
                    matrizRiesgos.getInterpretacionNr(), matrizRiesgos.getAceptabilidadRiesgo(),matrizRiesgos.getNivelProbabilidad(),matrizRiesgos.getInterpretacionProb(), matrizRiesgos.getNumExpuestos(), 
                    matrizRiesgos.getPeorConsecuencia(), matrizRiesgos.isReqLegal(), matrizRiesgos.getMedidasIntervencion().getCodMedida(), matrizRiesgos.getElementoProteccion().getCodElemento(),                    
                    matrizRiesgos.getObservaciones());
            //gestorMatrizRiesgos.validarMatrizRiesgos(mr);            
            gestorMatrizRiesgos.almacenarMatrizRiesgo(mr);
            
            UtilMSG.addSuccessMsg("Matriz Creada correctamente.");            
            this.cargarMatrizCargoEstablecimiento();
            this.cargarListaMatrizTareaRiesgo();
            return ("/matriz/administrar-matriz-cargos-establecimiento.xhtml?faces-redirect=true");        
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
            UtilMSG.addSuccessMsg("Complete los Campos");            
        }       
    return "";
    }
    
    public String cancelar(){           
        this.cargarCargosFuncionesEstablecimiento();                
        return ("/matriz/administrar-matriz-cargos-establecimiento.xhtml?faces-redirect=true");        
    }
    
    public void dialogoGenerarpdf() {
        try {            
            Dialogo dialogo = new Dialogo("dialogos/generarPDF.xhtml", "Generar Matriz de Riesgos", "clip", "30%", Dialogo.EFECTO);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");           
            this.cargarCargosEstablecimiento();
            UtilJSF.setBean("establecimiento", establecimiento, UtilJSF.APPLICATION_SCOPE);            
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    
    public void cargarListaMatrizTareaRiesgo() {
        try {                                     
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");            
            UtilJSF.setBean("varRiesgoEstablecimiento", matrizRiesgos, UtilJSF.SESSION_SCOPE);
            GestorGeneral gestorGeneral=new GestorGeneral();
            
            MatrizRiesgos mr=(MatrizRiesgos) UtilJSF.getBean("varRiesgoEstablecimiento");
            if(mr.getCodRiesgoMatriz()==null){
                Long codMatriz=gestorGeneral.nextval(GestorGeneral.MATRIZ_MATRIZ_RIESGOS_COD_RIESGO_MATRIZ_SEQ);
                mr.setCodRiesgoMatriz(codMatriz.intValue());
            }
            
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            evaluacionPlanAccionDetalles = new ArrayList<>();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaMatrizTareaRiesgo( e.getEvaluacionPK().getCodigoEstablecimiento(),mr.getCodRiesgoMatriz()));
    
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }
    
    public void modificarPlanAccion() {
        EvaluacionPlanAccionDetalle epad = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");        
        modificarActivo = Boolean.TRUE;
        UtilJSF.setBean("evaluacionPlanAccionDetalle", epad, UtilJSF.SESSION_SCOPE);
//        System.out.println("obj" + (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle"));
    }
    
    
    public void procesarPlanAccionModificar() {
        try {            
            EvaluacionPlanAccionDetalle epd = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle");
            epd.getMatrizRiesgos().setCodRiesgoMatriz(matrizRiesgos.getCodRiesgoMatriz());
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            epd = gestorEvaluacionPlanAccion.validarEvaluacionPlanAccionDetalle(epd);
            gestorEvaluacionPlanAccion.actualizarEvaluacionPlanAccionDetalleRiesgoMatriz(epd);
            //this.enviarCorreo();
            this.modificarActivo = Boolean.FALSE;

            evaluacionPlanAccionDetalles = new ArrayList<>();
            
            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);
            this.cargarListaMatrizTareaRiesgo();

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
    
    public void mostrarDialogoPlanAccion() {
        try {                                         
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            Sesion sesion = (Sesion) UtilJSF.getBean("sesion");            
            
            this.cargarListaMatrizTareaRiesgo();
            GestorResponsable gestorResponsable = new GestorResponsable();            
            GestorEstablecimiento gestorEstablecimiento= new GestorEstablecimiento();
            Integer sisgapp = gestorEstablecimiento.buscarSisgapp();
            
            responsables = new ArrayList<>();
            List<String> condicionesConsulta = new ArrayList<>();
            condicionesConsulta.add(App.CONDICION_WHERE);
            condicionesConsulta.add(Responsable.RESPONSABLE_CONDICION_CODIGO_ESTABLECIMIENTO.replace("?", String.valueOf(e.getEstablecimiento().getCodigoEstablecimiento())+","+sisgapp));
            responsables.addAll(gestorResponsable.cargarListaResponsable(
                    UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)
            ));
                
            GestorFuenteHallazgo gestorFuentehallazgo = new GestorFuenteHallazgo();
            fuentehallazgos.addAll((Collection<? extends FuenteHallazgo>) gestorFuentehallazgo.cargarListaFuentehallazgo());

            GestorClaseHallazgo gestorClasehallazgo = new GestorClaseHallazgo();            
            clasehallazgos.addAll((Collection<? extends ClaseHallazgo>) gestorClasehallazgo.cargarListaClasehallazgo());

            GestorTipoAccion gestorTipoaccion = new GestorTipoAccion();            
            tipoacciones.addAll((Collection<? extends TipoAccion>) gestorTipoaccion.cargarListaTipoaccion());

            GestorMotivoCorreccion gestorMotivocorreccion = new GestorMotivoCorreccion();
            motivocorrecciones.addAll((Collection<? extends MotivoCorreccion>) gestorMotivocorreccion.cargarListaMotivocorreccion());            

            List<Responsable> responsablesSisgapp = new ArrayList<>();
            for (Responsable rs : sesion.getResponsables()) {
                for (Responsable r : responsables) {
                    if (!rs.equals(r)) {
                        responsablesSisgapp.add(rs);
                    }
                }
            }
            if (!responsablesSisgapp.isEmpty()) {
                responsables.addAll(responsablesSisgapp);
            }
            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);
            Dialogo dialogo = new Dialogo("dialogos/tarea-riesgo.xhtml", "Tarea Riesgo", "clip", Dialogo.WIDTH_50);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }
    
     public void procesarPlanAccion() {
        try {
            
            EvaluacionPlanAccionDetalle epd = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle");
            String documentoUsuario = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios().getUsuariosPK().getDocumentoUsuario();
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            matrizRiesgos = (MatrizRiesgos) UtilJSF.getBean("varRiesgoEstablecimiento");
            
            GestorGeneral gestorGeneral = new GestorGeneral();
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();

            epd = gestorEvaluacionPlanAccion.validarEvaluacionPlanAccionDetalle(epd);

            Long codPlan = gestorEvaluacionPlanAccion.consultarEvaluacionPlanAccion(e.getEvaluacionPK().getCodEvaluacion(),
                    e.getEvaluacionPK().getCodigoEstablecimiento(), App.EVALUACION_PLAN_ACCION_ESTADO_ABIERTO);

            if (codPlan == null) {
                codPlan = gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_PLAN_ACCION_COD_PLAN_SEQ);
            }

            EvaluacionPlanAccion ep = new EvaluacionPlanAccion(
                    new EvaluacionPlanAccionPK(e.getEvaluacionPK().getCodEvaluacion(),
                            e.getEvaluacionPK().getCodigoEstablecimiento(),
                            codPlan),
                    App.EVALUACION_PLAN_ACCION_ESTADO_ABIERTO, documentoUsuario, documentoUsuario
            );
            epd.setEvaluacionPlanAccionDetallePK(new EvaluacionPlanAccionDetallePK(ep.getEvaluacionPlanAccionPK().getCodEvaluacion(), ep.getEvaluacionPlanAccionPK().getCodigoEstablecimiento(),
                    ep.getEvaluacionPlanAccionPK().getCodPlan()));
            epd.setEstado(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO);
            
            epd.setDocumentoUsuario(documentoUsuario);            
            epd.getEvaluacionPlanAccionDetallePK().setCodPlanDetalle(codPlan);
            epd.getEvaluacionPlanAccionDetallePK().setCodPlanDetalle(gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_PLAN_ACCION_DETALLE_COD_PLAN_DETALLE_SEQ));            

            EvaluacionPlanAccionDetalleNotas epadn = new EvaluacionPlanAccionDetalleNotas(
                    new EvaluacionPlanAccionDetalleNotasPK(ep.getEvaluacionPlanAccionPK().getCodEvaluacion(), ep.getEvaluacionPlanAccionPK().getCodigoEstablecimiento(),
                            codPlan, epd.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle()),
                    documentoUsuario, epd.getEstado(), "REGISTRO INICIAL", "Inicia registro de plan acción, responsable: " + UtilTexto.capitalizarCadena(responsable), usuariosSeleccionado);
            epd.setEvaluacionPlanAccionDetalleNotas(epadn);
            epd.getMatrizRiesgos().setCodRiesgoMatriz(matrizRiesgos.getCodRiesgoMatriz());
            
            ep.setEvaluacionPlanAccionDetalle(epd);
            gestorEvaluacionPlanAccion.procesarTareaRiesgo(ep);
            //this.enviarCorreo();
            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);            
            UtilMSG.addSuccessMsg("Tarea Riesgo Guardada", "La Tarea se almaceno satisfactoriamente.");            
            this.cargarListaMatrizTareaRiesgo();
            
        } catch (Exception ex) {
            if (UtilLog.causaControlada(ex)) {
                UtilMSG.addWarningMsg("Validación", ex.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), ex);
            }
        }
    }

    public EvaluacionPlanAccionDetalle getEvaluacionPlanAccionDetalle() {
        return evaluacionPlanAccionDetalle;
    }

    public void setEvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetalle evaluacionPlanAccionDetalle) {
        this.evaluacionPlanAccionDetalle = evaluacionPlanAccionDetalle;
    }

    public EvaluacionPlanAccionDetallePK getEvaluacionPlanAccionDetallePK() {
        return evaluacionPlanAccionDetallePK;
    }

    public void setEvaluacionPlanAccionDetallePK(EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK) {
        this.evaluacionPlanAccionDetallePK = evaluacionPlanAccionDetallePK;
    }

    public List<EvaluacionPlanAccionDetalle> getEvaluacionPlanAccionDetalles() {
        return evaluacionPlanAccionDetalles;
    }

    public void setEvaluacionPlanAccionDetalles(List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles) {
        this.evaluacionPlanAccionDetalles = evaluacionPlanAccionDetalles;
    }

    public List<ClaseHallazgo> getClasehallazgos() {
        return clasehallazgos;
    }

    public void setClasehallazgos(List<ClaseHallazgo> clasehallazgos) {
        this.clasehallazgos = clasehallazgos;
    }

    public List<FuenteHallazgo> getFuentehallazgos() {
        return fuentehallazgos;
    }

    public void setFuentehallazgos(List<FuenteHallazgo> fuentehallazgos) {
        this.fuentehallazgos = fuentehallazgos;
    }

    public List<TipoAccion> getTipoacciones() {
        return tipoacciones;
    }

    public void setTipoacciones(List<TipoAccion> tipoacciones) {
        this.tipoacciones = tipoacciones;
    }

    public List<TipoPlanAccion> getTiposTarea() {
        return tiposTarea;
    }

    public void setTiposTarea(List<TipoPlanAccion> tiposTarea) {
        this.tiposTarea = tiposTarea;
    }

    public List<MotivoCorreccion> getMotivocorrecciones() {
        return motivocorrecciones;
    }

    public void setMotivocorrecciones(List<MotivoCorreccion> motivocorrecciones) {
        this.motivocorrecciones = motivocorrecciones;
    }

    public List<CentroTrabajo> getCentrostrabajo() {
        return centrostrabajo;
    }

    public void setCentrostrabajo(List<CentroTrabajo> centrostrabajo) {
        this.centrostrabajo = centrostrabajo;
    }

    public List<Responsable> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }
    
    public void matrizRiesgosPDF(){
        Integer codEstablecimiento=evaluacion.getEstablecimiento().getCodigoEstablecimiento();
        Integer codCargo=matrizRiesgos.getCargos().getCodCargo();
        mrPdf=("window.open('.././exportar?nomReporte=matrizRiesgos&parametros=codigo_establecimiento,cod_cargo&valores=" + codEstablecimiento +","+codCargo+ "&tipos=I,I');");        
                
    }

    public String getMrPdf() {
        return mrPdf;
    }

    public void setMrPdf(String mrPdf) {
        this.mrPdf = mrPdf;
    }

    public List<RelCargosEstablecimiento> getCargosActividadesEstablecimientoList() {
        return cargosActividadesEstablecimientoList;
    }

    public void setCargosActividadesEstablecimientoList(List<RelCargosEstablecimiento> cargosActividadesEstablecimientoList) {
        this.cargosActividadesEstablecimientoList = cargosActividadesEstablecimientoList;
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

    public Boolean getModificarActivo() {
        return modificarActivo;
    }

    public void setModificarActivo(Boolean modificarActivo) {
        this.modificarActivo = modificarActivo;
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
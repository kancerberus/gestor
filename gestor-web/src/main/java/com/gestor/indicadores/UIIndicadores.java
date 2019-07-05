/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.indicadores;

import com.gestor.gestor.*;
import com.gestor.entity.App;
import com.gestor.entity.UtilFecha;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilTexto;
import com.gestor.indicadores.controlador.GestorIndicadores;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Responsable;
import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.TipoPlanAccion;
import com.gestor.publico.controlador.GestorCentroTrabajo;
import com.gestor.publico.controlador.GestorUsuario;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;


/**
 *
 * @author juliano
 */
@ManagedBean(name = "uiIndicadores")
@SessionScoped

public class UIIndicadores {

    
    
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetallesClaseHallazgo = new ArrayList<>();
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetallesTipoAccion = new ArrayList<>();
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetallesFuenteHallazgo=new ArrayList<>();    
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetallesEstado=new ArrayList<>();    
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalleFuenteHallazgoEstados=new ArrayList<>();    
    
    
    private Boolean modificarActivo = Boolean.FALSE;
    private Boolean filtroActivo = Boolean.TRUE;
    private Establecimiento establecimiento = new Establecimiento();
    private CentroTrabajo centroTrabajo = new CentroTrabajo();
    
    private List<String> selectedOptions;
    //filtros
    private List<Establecimiento> establecimientoList = new ArrayList<>();
    private List<TipoPlanAccion> tipoPlanAccionList=new ArrayList<>();
    private List<Establecimiento> establecimientoListSeleccionado = new ArrayList<>();    
    
    
    
    private GestorCentroTrabajo gestorCentroTrabajo;
    private GestorIndicadores gestorIndicadores;

    
    private List<Integer> tiposPlanAccion=new ArrayList<>();
    private List<String> ciclosStringSeleccionado = new ArrayList<>();
    private List<TipoPlanAccion> tiposPlanAccionIntegerSeleccionado = new ArrayList<>();
    
    private TipoPlanAccion tipoPlanAccion=new TipoPlanAccion();

    
    
    
    private  HorizontalBarChartModel graficoBarrasClaseHallazgo;
    private  HorizontalBarChartModel graficoBarrasTipoAccion;
    private PieChartModel pastelEstados;
    
    private Date fechaPlanInicio;
    private Date fechaPlanFin;
    float cantReqLegalesCerrados;
    float cantIdPeligrosCerrados;    
    float cantAccionesCerrados;
    float cantAcciones;
    float cantReqLegales;
    float errorReqLegales;
    float errorIdPeligros;
    float errorIdpeligrosEficacia;
    float errorAccionesCumplidas;
    float errorAccionesEficacia;
    float cantIdPeligros;
    float cantIdePeligrosEficacia;    
    float cantAccionesEficacia;    
    
    
    private List<Responsable> responsables = new ArrayList<>();
    private List<PlanTrabajoPrograma> programas = new ArrayList<>();
    private List<PlanTrabajoObjetivo> objetivos = new ArrayList<>();
    private List<ClaseHallazgo> clasehallazgos = new ArrayList<>();
    private List<FuenteHallazgo> fuentehallazgos = new ArrayList<>();
    private List<TipoAccion> tipoacciones = new ArrayList<>();    
    private List<MotivoCorreccion> motivocorrecciones  = new ArrayList<>();
    private List<CentroTrabajo> centrosTrabajoList = new ArrayList<>();    
    
    @PostConstruct
    public void init() {
        establecimiento=new Establecimiento();
        centroTrabajo=new CentroTrabajo();
        graficoBarrasClaseHallazgo=new HorizontalBarChartModel();
        graficoBarrasTipoAccion=new HorizontalBarChartModel();
        centrosTrabajoList=new ArrayList<>();
        pastelEstados=new PieChartModel();
    }

    public UIIndicadores() {
        try {
            Sesion s = (Sesion) UtilJSF.getBean("sesion");
            GestorUsuario gestorUsuario = new GestorUsuario();
            
            establecimiento=new Establecimiento();
            establecimientoList = new ArrayList<>();
            establecimientoList.addAll(s.getEstablecimientoList());               

        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void cargarCentrosTrabajo(){
        try {
            if(establecimiento.getCodigoEstablecimiento() == null){
                establecimiento=new Establecimiento();
                establecimiento.setCodigoEstablecimiento(12);                
            }
            gestorCentroTrabajo=new GestorCentroTrabajo();
            centrosTrabajoList= new ArrayList<>();
            centrosTrabajoList.addAll((Collection<? extends CentroTrabajo>) gestorCentroTrabajo.cargarListaCentrosTrabajo(establecimiento.getCodigoEstablecimiento()));            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }         
    }

    private List<String> filtrarOpcionesSeleccionadas() throws Exception {
        List<String> condicionesConsulta = new ArrayList<>();
        condicionesConsulta.add(App.CONDICION_WHERE);
        
        if(establecimiento == null){   
            String cadena;         
            cadena = Integer.toString(establecimiento.getCodigoEstablecimiento());
            
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena));
            
        }
        
        
        
        if(establecimiento!=null){            
            String cadena="";
            cadena = Integer.toString(establecimiento.getCodigoEstablecimiento());
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_ESTABLECIMIENTO.replace("?", cadena ));             
        }
        
        if(centroTrabajo!=null){   
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena="";
            cadena = Integer.toString(centroTrabajo.getCodCentrotrabajo());
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_CENTROTRABAJO.replace("?", cadena ));             
        }
        
        if(tiposPlanAccionIntegerSeleccionado!=null && !tiposPlanAccionIntegerSeleccionado.isEmpty()){            
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for(int i=0; i<tiposPlanAccionIntegerSeleccionado.size();i++){                
                cadena += "," + UtilTexto.CARACTER_COMILLA + Integer.toString(tiposPlanAccionIntegerSeleccionado.get(i).getCodTipoPlanAccion()) + UtilTexto.CARACTER_COMILLA;                                        
            }
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_TIPO_PLAN_ACCION.replace("?", cadena));
        }
        
        if (ciclosStringSeleccionado != null && !ciclosStringSeleccionado.isEmpty()) {                        
            condicionesConsulta.add(App.CONDICION_AND);
            String cadena = UtilTexto.CARACTER_COMILLA + "0" + UtilTexto.CARACTER_COMILLA;
            for (String s : ciclosStringSeleccionado) {
                cadena += "," + UtilTexto.CARACTER_COMILLA + s + UtilTexto.CARACTER_COMILLA;                  
            }
            cadena+=","+UtilTexto.CARACTER_COMILLA+"X"+UtilTexto.CARACTER_COMILLA;
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_CICLO.replace("?", cadena));
        }

        if (fechaPlanInicio != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_FECHA_REGISTRO_GTE.replace("?", UtilFecha.formatoFecha(fechaPlanInicio, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }

        if (fechaPlanFin != null) {
            condicionesConsulta.add(App.CONDICION_AND);
            condicionesConsulta.add(EvaluacionPlanAccionDetalle.EVALUACION_PLAN_ACCION_DETALLE_CONDICION_FECHA_REGISTRO_LTE.replace("?", UtilFecha.formatoFecha(fechaPlanFin, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)));
        }            
        return condicionesConsulta;
        
    }
    
    private void distribucionPorHallazgo(){
        try {
            gestorIndicadores=new GestorIndicadores();
            evaluacionPlanAccionDetallesClaseHallazgo=new ArrayList<>();
            graficoBarrasClaseHallazgo=new HorizontalBarChartModel();
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();            
            
            
            evaluacionPlanAccionDetallesClaseHallazgo.addAll(gestorIndicadores.cargarListaEvaluacionPlanAccionDetalleClaseHallazgo(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)));
            
            for(int i=0;i<=evaluacionPlanAccionDetallesClaseHallazgo.size()-1;i++){
                ChartSeries serie=new ChartSeries();                    
                serie.setLabel(evaluacionPlanAccionDetallesClaseHallazgo.get(i).getClasehallazgo().getNombre());
                serie.set(evaluacionPlanAccionDetallesClaseHallazgo.get(i).getClasehallazgo().getNombre(), evaluacionPlanAccionDetallesClaseHallazgo.get(i).getClasehallazgo().getCantidad());
                graficoBarrasClaseHallazgo.addSeries(serie);                
            }            
            graficoBarrasClaseHallazgo.setTitle("Distribucion Por Clase De Hallazgo");
            graficoBarrasClaseHallazgo.setLegendPosition("ne");
            graficoBarrasClaseHallazgo.setAnimate(true);    
            graficoBarrasClaseHallazgo.setDatatipFormat("%.0f");
            graficoBarrasClaseHallazgo.setBarWidth(40);
            
            Axis xAxis = graficoBarrasClaseHallazgo.getAxis(AxisType.X);
            xAxis.setLabel("Cantidad");
            xAxis.setMin(0);
            xAxis.setMax(50);
            xAxis.setTickInterval("10");

            Axis yAxis = graficoBarrasClaseHallazgo.getAxis(AxisType.Y);
            yAxis.setLabel("Clase Hallazgo");
            
            
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    private void distribucionPorTipoAccion(){
        try {
            gestorIndicadores=new GestorIndicadores();
            evaluacionPlanAccionDetallesTipoAccion=new ArrayList<>();
            graficoBarrasTipoAccion=new HorizontalBarChartModel();
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();            

            
            evaluacionPlanAccionDetallesTipoAccion.addAll(gestorIndicadores.cargarListaEvaluacionPlanAccionDetalleTipoAccion(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)));
            
            for(int i=0;i<=evaluacionPlanAccionDetallesTipoAccion.size()-1;i++){
                ChartSeries serie=new ChartSeries();                                
                serie.setLabel(evaluacionPlanAccionDetallesTipoAccion.get(i).getTipoaccion().getNombre());
                serie.set(evaluacionPlanAccionDetallesTipoAccion.get(i).getTipoaccion().getNombre(), evaluacionPlanAccionDetallesTipoAccion.get(i).getTipoaccion().getCantidad());                
                graficoBarrasTipoAccion.addSeries(serie);
            }
            
            graficoBarrasTipoAccion.setTitle("Distribucion Por Tipo Accion");
            graficoBarrasTipoAccion.setLegendPosition("ne");
            graficoBarrasTipoAccion.setAnimate(true);
            graficoBarrasTipoAccion.setDatatipFormat("%.0f");            
            graficoBarrasTipoAccion.setBarWidth(40);
            
            Axis xAxis = graficoBarrasTipoAccion.getAxis(AxisType.X);
            xAxis.setLabel("Cantidad");
            xAxis.setMin(0);
            xAxis.setMax(50);
            xAxis.setTickInterval("10");

            Axis yAxis = graficoBarrasTipoAccion.getAxis(AxisType.Y);
            yAxis.setLabel("Tipo Accion");
            
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void distribucionFuente(){
        try {            
            gestorIndicadores=new GestorIndicadores();
            evaluacionPlanAccionDetallesFuenteHallazgo=new ArrayList<>();            
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();            
            
            evaluacionPlanAccionDetallesFuenteHallazgo.addAll(gestorIndicadores.cargarListaEvaluacionPlanAccionDetalleFuenteHallazgo(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)));
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void distribucionEstado(){
        try {            
            gestorIndicadores=new GestorIndicadores();
            evaluacionPlanAccionDetallesEstado=new ArrayList<>();            
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();            
            pastelEstados=new PieChartModel();
            
            evaluacionPlanAccionDetallesEstado.addAll(gestorIndicadores.cargarListaEvaluacionPlanAccionDetalleEstado(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)));
            
            for(int i=0;i<=evaluacionPlanAccionDetallesEstado.size()-1;i++){
                pastelEstados.set(evaluacionPlanAccionDetallesEstado.get(i).getEstado(), evaluacionPlanAccionDetallesEstado.get(i).getCantidad());
            }
            
            pastelEstados.setTitle("Distribucion Por Estado");
            pastelEstados.setLegendPosition("w");
            pastelEstados.setShowDataLabels(true);            
            pastelEstados.setSliceMargin(5);            
            pastelEstados.setDiameter(230);
            pastelEstados.setShadow(false);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    

    
    public void distribucionFuenteHallazgoEstados(){
        try {            
            gestorIndicadores=new GestorIndicadores();
            evaluacionPlanAccionDetalleFuenteHallazgoEstados=new ArrayList<>();
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();            
            
            
            evaluacionPlanAccionDetalleFuenteHallazgoEstados.addAll(gestorIndicadores.cargarListaEvaluacionPlanAccionDetalleFuenteHallazgoEstados(
                        UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO)));
            
           
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void indicadoresMejora(){
        try {
            gestorIndicadores=new GestorIndicadores();
            List<String> condicionesConsulta = this.filtrarOpcionesSeleccionadas();            
            errorReqLegales=0;
            
            cantReqLegalesCerrados = gestorIndicadores.cargarCantReqLegalesCerrados(UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO));
            cantIdPeligrosCerrados = gestorIndicadores.cargarCantIdPeligrosCerrados(UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO));
            cantReqLegales = gestorIndicadores.cargarCantReqLegales(UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO));
            
            cantIdPeligros = gestorIndicadores.cargarCantIdPeligros(UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO));
            cantIdePeligrosEficacia = gestorIndicadores.cargarCantIdPeligrosEficacia(UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO));
            cantAccionesCerrados = gestorIndicadores.cargarCantAccionesCerradas(UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO));
            cantAcciones = gestorIndicadores.cargarCantAcciones(UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO));
            cantAccionesEficacia = gestorIndicadores.cargarCantAccionesEficacia(UtilTexto.listToString(condicionesConsulta, UtilTexto.SEPARADOR_ESPACIO));
            
            errorReqLegales = ((cantReqLegalesCerrados/cantReqLegales)*100);
            errorIdPeligros = (cantIdPeligrosCerrados/cantIdPeligros)*100;
            errorIdpeligrosEficacia = (cantIdePeligrosEficacia/cantIdPeligrosCerrados)*100;
            errorAccionesCumplidas = (cantAccionesCerrados/cantAcciones)*100;
            errorAccionesEficacia = (cantAccionesEficacia/cantAccionesCerrados)*100;
            
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void consultarGraficas() {
        try {            
            this.indicadoresMejora();
            this.distribucionPorHallazgo();
            this.distribucionPorTipoAccion();
            this.distribucionFuente();
            this.distribucionEstado();
            this.distribucionFuenteHallazgoEstados();
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    
    public String getStyleErrReqLegales() {
        String styleErrReqLegales = "padding: 6px; padding-left: 200px;"
                + "opacity: 0.83;"
                + "transition: opacity 0.6s;";          
        if(errorReqLegales >= 0 && errorReqLegales < 50  ){
            styleErrReqLegales += "background-color: #f44336;";
        }if(errorReqLegales >= 50 && errorReqLegales<=71){
                styleErrReqLegales += "background-color: #fbaa36;";
        }if(errorReqLegales>71){
            styleErrReqLegales += "background-color: #008000;";
        }
        return styleErrReqLegales;
    }
    
    public String getStyleErrorIdPeligros() {
        String styleErrorIdPeligros = "padding: 6px;padding-left: 200px;"
                + "opacity: 0.83;"
                + "transition: opacity 0.6s;";          
        if(errorIdPeligros >= 0 && errorIdPeligros < 50  ){
            styleErrorIdPeligros += "background-color: #f44336;";
        }if(errorIdPeligros >= 50 && errorIdPeligros<=71){
                styleErrorIdPeligros += "background-color: #fbaa36;";
        }if(errorIdPeligros>71){
            styleErrorIdPeligros += "background-color: #008000;";
        }
        return styleErrorIdPeligros;
    }
    
    public String getStyleErrorIdpeligrosEficacia() {
        String styleErrorIdpeligrosEficacia = "padding: 6px;padding-left: 200px;"
                + "opacity: 0.83;"
                + "transition: opacity 0.6s;";          
        if(errorIdpeligrosEficacia >= 0 && errorIdpeligrosEficacia < 50  ){
            styleErrorIdpeligrosEficacia += "background-color: #f44336;";
        }if(errorIdpeligrosEficacia >= 50 && errorIdpeligrosEficacia<=71){
                styleErrorIdpeligrosEficacia += "background-color: #fbaa36;";
        }if(errorIdpeligrosEficacia>71){
            styleErrorIdpeligrosEficacia += "background-color: #008000;";
        }
        return styleErrorIdpeligrosEficacia;
    }
        
    public String getStyleErrorAccionesCumplidas() {
        String styleErrorAccionesCumplidas = "padding: 6px;padding-left: 200px;"
                + "opacity: 0.83;"
                + "transition: opacity 0.6s;";          
        if(errorAccionesCumplidas >= 0 && errorAccionesCumplidas < 50  ){
            styleErrorAccionesCumplidas += "background-color: #f44336;";
        }if(errorAccionesCumplidas >= 50 && errorAccionesCumplidas<=71){
                styleErrorAccionesCumplidas += "background-color: #fbaa36;";
        }if(errorAccionesCumplidas>71){
            styleErrorAccionesCumplidas += "background-color: #008000;";
        }
        return styleErrorAccionesCumplidas;
    }
            
    public String getStyleErrorAccionesEficacia() {
        String styleErrorAccionesEficacia = "padding: 6px;padding-left: 200px;"
                + "opacity: 0.83;"
                + "transition: opacity 0.6s;";          
        if(errorAccionesEficacia >= 0 && errorAccionesEficacia < 50  ){
            styleErrorAccionesEficacia += "background-color: #f44336;";
        }if(errorAccionesEficacia >= 50 && errorAccionesEficacia<=71){
                styleErrorAccionesEficacia += "background-color: #fbaa36;";
        }if(errorAccionesEficacia>71){
            styleErrorAccionesEficacia += "background-color: #008000;";
        }
        return styleErrorAccionesEficacia;
    }
    
    public float getCantReqLegalesCerrados() {
        return cantReqLegalesCerrados;
    }

    public void setCantReqLegalesCerrados(float cantReqLegalesCerrados) {
        this.cantReqLegalesCerrados = cantReqLegalesCerrados;
    }

    public float getCantIdPeligrosCerrados() {
        return cantIdPeligrosCerrados;
    }

    public void setCantIdPeligrosCerrados(float cantIdPeligrosCerrados) {
        this.cantIdPeligrosCerrados = cantIdPeligrosCerrados;
    }

    public float getCantAccionesCerrados() {
        return cantAccionesCerrados;
    }

    public void setCantAccionesCerrados(float cantAccionesCerrados) {
        this.cantAccionesCerrados = cantAccionesCerrados;
    }

    public float getCantAcciones() {
        return cantAcciones;
    }

    public void setCantAcciones(float cantAcciones) {
        this.cantAcciones = cantAcciones;
    }

    public float getCantReqLegales() {
        return cantReqLegales;
    }

    public void setCantReqLegales(float cantReqLegales) {
        this.cantReqLegales = cantReqLegales;
    }

    public float getErrorReqLegales() {
        return errorReqLegales;
    }

    public void setErrorReqLegales(float errorReqLegales) {
        this.errorReqLegales = errorReqLegales;
    }

    public float getErrorIdPeligros() {
        return errorIdPeligros;
    }

    public void setErrorIdPeligros(float errorIdPeligros) {
        this.errorIdPeligros = errorIdPeligros;
    }

    public float getErrorIdpeligrosEficacia() {
        return errorIdpeligrosEficacia;
    }

    public void setErrorIdpeligrosEficacia(float errorIdpeligrosEficacia) {
        this.errorIdpeligrosEficacia = errorIdpeligrosEficacia;
    }

    public float getErrorAccionesCumplidas() {
        return errorAccionesCumplidas;
    }

    public void setErrorAccionesCumplidas(float errorAccionesCumplidas) {
        this.errorAccionesCumplidas = errorAccionesCumplidas;
    }

    public float getErrorAccionesEficacia() {
        return errorAccionesEficacia;
    }

    public void setErrorAccionesEficacia(float errorAccionesEficacia) {
        this.errorAccionesEficacia = errorAccionesEficacia;
    }

    public float getCantIdPeligros() {
        return cantIdPeligros;
    }

    public void setCantIdPeligros(float cantIdPeligros) {
        this.cantIdPeligros = cantIdPeligros;
    }

    public float getCantIdePeligrosEficacia() {
        return cantIdePeligrosEficacia;
    }

    public void setCantIdePeligrosEficacia(float cantIdePeligrosEficacia) {
        this.cantIdePeligrosEficacia = cantIdePeligrosEficacia;
    }

    public float getCantAccionesEficacia() {
        return cantAccionesEficacia;
    }

    public void setCantAccionesEficacia(float cantAccionesEficacia) {
        this.cantAccionesEficacia = cantAccionesEficacia;
    }
    

    public List<EvaluacionPlanAccionDetalle> getEvaluacionPlanAccionDetalleFuenteHallazgoEstados() {
        return evaluacionPlanAccionDetalleFuenteHallazgoEstados;
    }

    public void setEvaluacionPlanAccionDetalleFuenteHallazgoEstados(List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalleFuenteHallazgoEstados) {
        this.evaluacionPlanAccionDetalleFuenteHallazgoEstados = evaluacionPlanAccionDetalleFuenteHallazgoEstados;
    }

    public List<EvaluacionPlanAccionDetalle> getEvaluacionPlanAccionDetallesEstado() {
        return evaluacionPlanAccionDetallesEstado;
    }

    public void setEvaluacionPlanAccionDetallesEstado(List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetallesEstado) {
        this.evaluacionPlanAccionDetallesEstado = evaluacionPlanAccionDetallesEstado;
    }

    public PieChartModel getPastelEstados() {
        return pastelEstados;
    }

    public void setPastelEstados(PieChartModel pastelEstados) {
        this.pastelEstados = pastelEstados;
    }

    public List<EvaluacionPlanAccionDetalle> getEvaluacionPlanAccionDetallesFuenteHallazgo() {
        return evaluacionPlanAccionDetallesFuenteHallazgo;
    }

    public void setEvaluacionPlanAccionDetallesFuenteHallazgo(List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetallesFuenteHallazgo) {
        this.evaluacionPlanAccionDetallesFuenteHallazgo = evaluacionPlanAccionDetallesFuenteHallazgo;
    }

    public HorizontalBarChartModel getGraficoBarrasTipoAccion() {
        return graficoBarrasTipoAccion;
    }

    public void setGraficoBarrasTipoAccion(HorizontalBarChartModel graficoBarrasTipoAccion) {
        this.graficoBarrasTipoAccion = graficoBarrasTipoAccion;
    }
    
    public GestorCentroTrabajo getGestorCentroTrabajo() {
        return gestorCentroTrabajo;
    }

    public void setGestorCentroTrabajo(GestorCentroTrabajo gestorCentroTrabajo) {
        this.gestorCentroTrabajo = gestorCentroTrabajo;
    }

    public HorizontalBarChartModel getGraficoBarrasClaseHallazgo() {
        return graficoBarrasClaseHallazgo;
    }

    public void setGraficoBarrasClaseHallazgo(HorizontalBarChartModel graficoBarrasClaseHallazgo) {
        this.graficoBarrasClaseHallazgo = graficoBarrasClaseHallazgo;
    }

    public TipoPlanAccion getTipoPlanAccion() {
        return tipoPlanAccion;
    }

    public void setTipoPlanAccion(TipoPlanAccion tipoPlanAccion) {
        this.tipoPlanAccion = tipoPlanAccion;
    }

    public List<Integer> getTiposPlanAccion() {
        return tiposPlanAccion;
    }

    public void setTiposPlanAccion(List<Integer> tiposPlanAccion) {
        this.tiposPlanAccion = tiposPlanAccion;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
    
    public List<FuenteHallazgo> getFuentehallazgos() {
        return fuentehallazgos;
    }

    public void setFuentehallazgos(List<FuenteHallazgo> fuentehallazgos) {
        this.fuentehallazgos = fuentehallazgos;
    }

    public List<PlanTrabajoObjetivo> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(List<PlanTrabajoObjetivo> objetivos) {
        this.objetivos = objetivos;
    }

    public List<PlanTrabajoPrograma> getProgramas() {
        return programas;
    }

    public void setProgramas(List<PlanTrabajoPrograma> programas) {
        this.programas = programas;
    }
    
    public List<ClaseHallazgo> getClasehallazgos() {
        return clasehallazgos;
    }

    public void setClasehallazgos(List<ClaseHallazgo> clasehallazgos) {
        this.clasehallazgos = clasehallazgos;
    }

    public List<TipoAccion> getTipoacciones() {
        return tipoacciones;
    }

    public void setTipoacciones(List<TipoAccion> tipoacciones) {
        this.tipoacciones = tipoacciones;
    }

    public List<MotivoCorreccion> getMotivocorrecciones() {
        return motivocorrecciones;
    }

    public void setMotivocorrecciones(List<MotivoCorreccion> motivocorrecciones) {
        this.motivocorrecciones = motivocorrecciones;
    }

    public List<CentroTrabajo> getCentrosTrabajoList() {
        return centrosTrabajoList;
    }

    public void setCentrosTrabajoList(List<CentroTrabajo> centrosTrabajoList) {
        this.centrosTrabajoList = centrosTrabajoList;
    }

    
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public List<EvaluacionPlanAccionDetalle> getEvaluacionPlanAccionDetallesClaseHallazgo() {
        return evaluacionPlanAccionDetallesClaseHallazgo;
    }

    public void setEvaluacionPlanAccionDetallesClaseHallazgo(List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetallesClaseHallazgo) {
        this.evaluacionPlanAccionDetallesClaseHallazgo = evaluacionPlanAccionDetallesClaseHallazgo;
    }

    public List<EvaluacionPlanAccionDetalle> getEvaluacionPlanAccionDetallesTipoAccion() {
        return evaluacionPlanAccionDetallesTipoAccion;
    }

    /**
     * @return the modificarActivo
     */
    public Boolean getModificarActivo() {
        return modificarActivo;
    }

    /**
     * @param modificarActivo the modificarActivo to set
     */
    public void setModificarActivo(Boolean modificarActivo) {
        this.modificarActivo = modificarActivo;
    }

    public CentroTrabajo getCentroTrabajo() {
        return centroTrabajo;
    }

    public void setCentroTrabajo(CentroTrabajo centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
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

    public GestorIndicadores getGestorIndicadores() {
        return gestorIndicadores;
    }

    public void setGestorIndicadores(GestorIndicadores gestorIndicadores) {
        this.gestorIndicadores = gestorIndicadores;
    }

    /**
     * @param establecimientoListSeleccionado the
     * establecimientoListSeleccionado to set
     */
    public void setEstablecimientoListSeleccionado(List<Establecimiento> establecimientoListSeleccionado) {
        this.establecimientoListSeleccionado = establecimientoListSeleccionado;
    }

    public List<TipoPlanAccion> getTipoPlanAccionList() {
        return tipoPlanAccionList;
    }

    public void setTipoPlanAccionList(List<TipoPlanAccion> tipoPlanAccionList) {
        this.tipoPlanAccionList = tipoPlanAccionList;
    }    
    
    /**
     * @return the ciclosStringSeleccionado
     */
    public List<String> getCiclosStringSeleccionado() {
        return ciclosStringSeleccionado;
    }

    public List<TipoPlanAccion> getTiposPlanAccionIntegerSeleccionado() {
        return tiposPlanAccionIntegerSeleccionado;
    }

    public void setTiposPlanAccionIntegerSeleccionado(List<TipoPlanAccion> tiposPlanAccionIntegerSeleccionado) {
        this.tiposPlanAccionIntegerSeleccionado = tiposPlanAccionIntegerSeleccionado;
    }

    

    /**
     * @param ciclosStringSeleccionado the ciclosStringSeleccionado to set
     */
    public void setCiclosStringSeleccionado(List<String> ciclosStringSeleccionado) {
        this.ciclosStringSeleccionado = ciclosStringSeleccionado;
    }
    /**
     * @return the fechaPlanInicio
     */
    public Date getFechaPlanInicio() {
        return fechaPlanInicio;
    }

    /**
     * @param fechaPlanInicio the fechaPlanInicio to set
     */
    public void setFechaPlanInicio(Date fechaPlanInicio) {
        this.fechaPlanInicio = fechaPlanInicio;
    }

    /**
     * @return the fechaPlanFin
     */
    public Date getFechaPlanFin() {
        return fechaPlanFin;
    }

    /**
     * @param fechaPlanFin the fechaPlanFin to set
     */
    public void setFechaPlanFin(Date fechaPlanFin) {
        this.fechaPlanFin = fechaPlanFin;
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
     * @return the responsables
     */
    public List<Responsable> getResponsables() {
        return responsables;
    }

    /**
     * @param responsables the responsables to set
     */
    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }

}

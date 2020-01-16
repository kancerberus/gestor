/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.inspecciones.controlador;



import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.inspecciones.AlmacenBodegaMetricas;
import com.gestor.inspecciones.ElementosBotiquin;
import com.gestor.inspecciones.InspeccionAlmacenBodega;
import com.gestor.inspecciones.InspeccionBotiquin;
import com.gestor.inspecciones.InspeccionExtintor;
import com.gestor.inspecciones.InspeccionProteccionPersonal;
import com.gestor.inspecciones.InspeccionesTipo;
import com.gestor.inspecciones.MotivoNoUso;
import com.gestor.inspecciones.RelInspeccionesCentroTrabajo;
import com.gestor.inspecciones.dao.InspeccionesDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author Francisco
 */
public class GestorInspecciones extends Gestor implements Serializable{

    public GestorInspecciones() throws Exception {
        super();
    }

    public void guardarInspeccionCentroTrabajo(Integer codigoEstablecimiento, Integer codCentrotrabajo, Integer codInspeccion) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            InspeccionesDAO inspeccionesDAO= new InspeccionesDAO(conexion);
            inspeccionesDAO.guardarInspeccionesCentroTrabajo(codigoEstablecimiento, codCentrotrabajo, codInspeccion);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    

    
    public Collection<InspeccionesTipo> cargarInspeccionesTipo() throws Exception{
        try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarInspeccionesTipo();
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<RelInspeccionesCentroTrabajo> cargarRelInspeccionesEstablecimientoList(int codigoEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarRelInspeccionesEstablecimientoList(codigoEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends InspeccionExtintor> cargarInspeccionCentroTrabajo(Integer codigoEstablecimiento, Integer codCentroTrabajo, Integer codInspeccion) throws Exception {
        try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarInspeccionCentroTrabajo(codigoEstablecimiento,codCentroTrabajo, codInspeccion);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void validarInspeccionExtintores(InspeccionExtintor inspeccionExtintores) {
        try {            
            
            if(inspeccionExtintores.getResponsableuno()!=null){
                inspeccionExtintores.getResponsableuno().toUpperCase();
            }
            if(inspeccionExtintores.getResponsabledos()!=null){
                inspeccionExtintores.getResponsabledos().toUpperCase();
            }
            if(inspeccionExtintores.getArea()!=null){
                inspeccionExtintores.getArea().toUpperCase();
            }
            if(inspeccionExtintores.getTipo()!=null){
                inspeccionExtintores.getTipo().toUpperCase();
            }else{
                UtilMSG.addErrorMsg("Hay Campos Vacios");
            }
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void guardarInspeccionExtintores(ArrayList<InspeccionExtintor> inspeccionExtintoresList) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            InspeccionesDAO inspeccionesDAO= new InspeccionesDAO(conexion);
            inspeccionesDAO.guardarInspeccionExtintores(inspeccionExtintoresList);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends InspeccionExtintor> cargarInspeccionExtintoresCentroTrabajo(Integer codCentrotrabajo, Integer codInspeccion, Integer codigoEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarInspeccionExtintoresCentroTrabajo(codCentrotrabajo, codInspeccion,codigoEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }

    public void eliminarExtinguidor(InspeccionExtintor extinguidor) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            InspeccionesDAO inspeccionesDAO= new InspeccionesDAO(conexion);
            inspeccionesDAO.eliminarExtintor(extinguidor);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
        
    }

    public Collection<? extends ElementosBotiquin> cargarElementosBotiquin() throws Exception {
        try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarElementosBotiquin();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends AlmacenBodegaMetricas> cargarAlmacenBodegaMetricas() throws Exception {
        try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarAlmacenBodegaMetricas();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends MotivoNoUso> cargarMotivoNoUsoList() throws Exception {
        try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarMotivoNoUsoList();
        } finally {
            this.cerrarConexion();
        }
    }
    
    

    public void validarInspeccionBotiquin(InspeccionBotiquin inspeccionBotiquin) {
         try {            
            
            if(inspeccionBotiquin.getResponsableuno()!=null){
                inspeccionBotiquin.getResponsableuno().toUpperCase();
            }
            if(inspeccionBotiquin.getResponsableuno()==null || inspeccionBotiquin.getResponsableuno().equals("")){
                UtilMSG.addErrorMsg("Ingrese Responsable");
                inspeccionBotiquin.setCodInspeccionBotiquin(null);
            }
            if(inspeccionBotiquin.getResponsabledos()!=null){
                inspeccionBotiquin.getResponsabledos().toUpperCase();
            }
            if(inspeccionBotiquin.getResponsabledos()==null || inspeccionBotiquin.getResponsabledos().equals("")){
                UtilMSG.addErrorMsg("Ingrese Responsable");
                inspeccionBotiquin.setCodInspeccionBotiquin(null);
            }
            if(inspeccionBotiquin.getBotPortatil()==null){
                UtilMSG.addErrorMsg("Ingrese Tipo Botiquin");
                inspeccionBotiquin.setCodInspeccionBotiquin(null);
            }
            if(inspeccionBotiquin.getFechaInspeccion()==null){
                UtilMSG.addErrorMsg("Ingrese Fecha Inspeccion");
                inspeccionBotiquin.setCodInspeccionBotiquin(null);
            }
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }
    
    public void validarInspeccionAlmacenBodega(InspeccionAlmacenBodega inspeccionAlmacenBodega) {
         try {            
            
            if(inspeccionAlmacenBodega.getResponsableuno()!=null){
                inspeccionAlmacenBodega.getResponsableuno().toUpperCase();
            }
            if(inspeccionAlmacenBodega.getResponsableuno()==null || inspeccionAlmacenBodega.getResponsableuno().equals("")){
                UtilMSG.addErrorMsg("Ingrese Responsable");
                inspeccionAlmacenBodega.setCodInspeccionAlmacenBodega(null);
            }
            if(inspeccionAlmacenBodega.getResponsabledos()!=null){
                inspeccionAlmacenBodega.getResponsabledos().toUpperCase();
            }
            if(inspeccionAlmacenBodega.getResponsabledos()==null || inspeccionAlmacenBodega.getResponsabledos().equals("")){
                UtilMSG.addErrorMsg("Ingrese Responsable");
                inspeccionAlmacenBodega.setCodInspeccionAlmacenBodega(null);
            }            
            if(inspeccionAlmacenBodega.getFechaInspeccion()==null){
                UtilMSG.addErrorMsg("Ingrese Fecha Inspeccion");
                inspeccionAlmacenBodega.setCodInspeccionAlmacenBodega(null);
            }
            if(inspeccionAlmacenBodega.getAlmBodegaMetricas()==null){
                UtilMSG.addErrorMsg("Seleccione Metrica");
                inspeccionAlmacenBodega.setCodInspeccionAlmacenBodega(null);
            }
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void eliminarBotiquin(InspeccionBotiquin selectedBotiquin) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            InspeccionesDAO inspeccionesDAO= new InspeccionesDAO(conexion);
            inspeccionesDAO.eliminarElemBotiquin(selectedBotiquin);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public void guardarInspeccionBotiquin(ArrayList<InspeccionBotiquin> elementosBotiquinList) throws Exception {        
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            InspeccionesDAO inspeccionesDAO= new InspeccionesDAO(conexion);
            inspeccionesDAO.guardarInspeccionBotiquines(elementosBotiquinList);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends InspeccionBotiquin> cargarInspeccionBotiquinesCentroTrabajo(Integer codCt, Integer codInspeccion, Integer codEstablecmiento) throws Exception {
         try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarInspeccionBotiquinCentroTrabajo(codCt, codInspeccion, codEstablecmiento);
        } finally {
            this.cerrarConexion();
        }
    }

    public void eliminarAlmacenBodegaMetrica(InspeccionAlmacenBodega selectedAlmacenBodega) throws Exception {
         try {
            this.abrirConexion();
            this.inicioTransaccion();
            InspeccionesDAO inspeccionesDAO= new InspeccionesDAO(conexion);
            inspeccionesDAO.eliminarAlmacenBodegaMetrica(selectedAlmacenBodega);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public void guardarInspeccionAlmacenBodega(ArrayList<InspeccionAlmacenBodega> inspeccionAlmacenBodegaList) throws Exception {
         try {
            this.abrirConexion();
            this.inicioTransaccion();
            InspeccionesDAO inspeccionesDAO= new InspeccionesDAO(conexion);
            inspeccionesDAO.guardarInspeccionAlmacenBodega(inspeccionAlmacenBodegaList);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends InspeccionAlmacenBodega> cargarInspeccionesAlmacenBodega(Integer codCt, Integer codInspeccion, Integer codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarInspeccionAlmacenBodega(codCt, codInspeccion, codEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }

    public void eliminarInspeccionProteccionPersonal(InspeccionProteccionPersonal selectedProteccionPersonal) throws Exception {
    try {
           this.abrirConexion();
           this.inicioTransaccion();
           InspeccionesDAO inspeccionesDAO= new InspeccionesDAO(conexion);
           inspeccionesDAO.eliminarInspeccionProteccionPersonal(selectedProteccionPersonal);
           this.finTransaccion();
        } finally {
           this.cerrarConexion();
        }
    }

    public void validarInspeccionProteccionPersonal(InspeccionProteccionPersonal inspeccionProteccionPersonal) {
         try {            
            
            if(inspeccionProteccionPersonal.getResponsableuno()!=null){
                inspeccionProteccionPersonal.getResponsableuno().toUpperCase();
            }
            if(inspeccionProteccionPersonal.getResponsableuno()==null || inspeccionProteccionPersonal.getResponsableuno().equals("")){
                UtilMSG.addErrorMsg("Ingrese Responsable");
                inspeccionProteccionPersonal.setCodInsProteccionPersonal(null);
            }
            if(inspeccionProteccionPersonal.getResponsabledos()!=null){
                inspeccionProteccionPersonal.getResponsabledos().toUpperCase();
            }
            if(inspeccionProteccionPersonal.getResponsabledos()==null || inspeccionProteccionPersonal.getResponsabledos().equals("")){
                UtilMSG.addErrorMsg("Ingrese Responsable");
                inspeccionProteccionPersonal.setCodInsProteccionPersonal(null);
            }
            if(inspeccionProteccionPersonal.getVigencia()==null){
                UtilMSG.addErrorMsg("Ingrese vigencia");
                inspeccionProteccionPersonal.setCodInsProteccionPersonal(null);
            }
            if(inspeccionProteccionPersonal.getFechaInspeccion()==null){
                UtilMSG.addErrorMsg("Ingrese fecha Inspeccion");
                inspeccionProteccionPersonal.setCodInsProteccionPersonal(null);
            }
            if(inspeccionProteccionPersonal.getHora_inspeccion()==null){
                UtilMSG.addErrorMsg("Ingrese hora Inspeccion");
                inspeccionProteccionPersonal.setCodInsProteccionPersonal(null);                
            }
            if(inspeccionProteccionPersonal.getNomEmpleado().equals("") || inspeccionProteccionPersonal.getNomEmpleado()==null){
                UtilMSG.addErrorMsg("Ingrese Nombre Empleado");
                inspeccionProteccionPersonal.setCodInsProteccionPersonal(null);
            }
            if(inspeccionProteccionPersonal.getActividad().equals("") || inspeccionProteccionPersonal.getActividad()==null){
                UtilMSG.addErrorMsg("Ingrese Actividad");
                inspeccionProteccionPersonal.setCodInsProteccionPersonal(null);                
            }            
            
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public Collection<? extends InspeccionProteccionPersonal> cargarInspeccionesProteccionPersonalList(Integer codigoEstablecimiento,Integer codTipoInspeccion, Integer codCT) throws Exception {
        try {
            this.abrirConexion();
            InspeccionesDAO inspeccionesDAO = new InspeccionesDAO(conexion);
            return inspeccionesDAO.cargarInspeccionesProteccionPersonalList(codigoEstablecimiento, codTipoInspeccion,codCT);
        } finally {
            this.cerrarConexion();
        }
    }

    public void guardarInspeccionProteccionPersonal(ArrayList<InspeccionProteccionPersonal> inspeccionProteccionPersonalList) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            InspeccionesDAO inspeccionesDAO= new InspeccionesDAO(conexion);
            inspeccionesDAO.guardarInspeccionProteccionPersonal(inspeccionProteccionPersonalList);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    
    
}

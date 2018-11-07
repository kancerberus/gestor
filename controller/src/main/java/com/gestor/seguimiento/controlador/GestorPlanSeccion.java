/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.seguimiento.PlanSeccion;
import com.gestor.seguimiento.PlanSeccionAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalle;
import com.gestor.seguimiento.PlanSeccionDetalleItem;
import com.gestor.seguimiento.PlanSeccionDetalleTexto;
import com.gestor.seguimiento.PlanSeccionDetalleItemTexto;
import com.gestor.seguimiento.PlanSeccionDetalleAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalleItemAdjuntos;
import com.gestor.seguimiento.dao.PlanSeccionDetalleTextoDAO;
import com.gestor.seguimiento.dao.PlanSeccionDetalleItemTextoDAO;
import com.gestor.seguimiento.PlanSeccionTexto;
import com.gestor.seguimiento.dao.PlanSeccionTextoDAO;
import com.gestor.seguimiento.dao.PlanSeccionDAO;
import com.gestor.seguimiento.dao.PlanSeccionAdjuntosDAO;
import com.gestor.seguimiento.dao.PlanSeccionDetalleDAO;
import com.gestor.seguimiento.dao.PlanSeccionDetalleAdjuntosDAO;
import com.gestor.seguimiento.dao.PlanSeccionDetalleItemAdjuntosDAO;
import com.gestor.seguimiento.dao.PlanSeccionDetalleItemDAO;
import com.gestor.seguimiento.PlanTitulo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
/**
 *
 * @author Julian D Osorio G
 */
public class GestorPlanSeccion extends Gestor implements Serializable{

    public GestorPlanSeccion() throws Exception {
        super();
    }
    
    public void validarPlanseccion(PlanSeccion planseccion) throws Exception {
        if(planseccion.getNombre()==null){
            throw new Exception("Ingresa el titulo de la seccion.", UtilLog.TW_VALIDACION);
        }
        if (planseccion.getPlanSeccionPK().getCodTitulo()==0 ) {
            throw new Exception("Ingresa el numeral", UtilLog.TW_VALIDACION);
        }  
        planseccion.setNombre(planseccion.getNombre().trim().toUpperCase());        

    }
    
    public Collection<? extends PlanSeccionAdjuntos> cargarPlanSeccionadjuntoList(PlanSeccion planseccion) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionAdjuntosDAO planSeccionadjuntosDAO = new PlanSeccionAdjuntosDAO(conexion);
            Collection<PlanSeccionAdjuntos> planSeccionadjuntosList = new ArrayList<>();
            planSeccionadjuntosList.addAll(planSeccionadjuntosDAO.cargarPlanSeccionadjuntosList(planseccion));
            return planSeccionadjuntosList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanSeccionDetalleAdjuntos> cargarPlanSecciondetalleadjuntoList(PlanSeccionDetalle plansecciondetalle) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionDetalleAdjuntosDAO planSecciondetalleadjuntosDAO = new PlanSeccionDetalleAdjuntosDAO(conexion);
            Collection<PlanSeccionDetalleAdjuntos> planSecciondetalleadjuntosList = new ArrayList<>();
            planSecciondetalleadjuntosList.addAll(planSecciondetalleadjuntosDAO.cargarPlanSecciondetalleadjuntosList(plansecciondetalle));
            return planSecciondetalleadjuntosList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarSeccion(PlanSeccion planseccion) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDAO planseccionDAO = new PlanSeccionDAO(conexion);
            planseccionDAO.insertarPlanseccion(planseccion);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarSecciontexto(PlanSeccionTexto plansecciontexto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionTextoDAO plansecciontextoDAO = new PlanSeccionTextoDAO(conexion);
            plansecciontextoDAO.insertarPlanseccionTexto(plansecciontexto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanSeccionDetalleItemAdjuntos> cargarPlanSecciondetalleitemadjuntoList(PlanSeccionDetalleItem plansecciondetalleitem) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionDetalleItemAdjuntosDAO planSecciondetalleitemadjuntosDAO = new PlanSeccionDetalleItemAdjuntosDAO(conexion);
            Collection<PlanSeccionDetalleItemAdjuntos> planSecciondetalleitemadjuntosList = new ArrayList<>();
            planSecciondetalleitemadjuntosList.addAll(planSecciondetalleitemadjuntosDAO.cargarPlanSecciondetalleitemadjuntosList(plansecciondetalleitem));
            return planSecciondetalleitemadjuntosList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarSeccionadjunto(PlanSeccionAdjuntos planseccionadjunto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionAdjuntosDAO planseccionadjuntoDAO = new PlanSeccionAdjuntosDAO(conexion);
            planseccionadjuntoDAO.insertarPlanseccionadjunto(planseccionadjunto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarSecciondetalleadjunto(PlanSeccionDetalleAdjuntos plansecciondetalleadjunto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDetalleAdjuntosDAO plansecciondetalleadjuntoDAO = new PlanSeccionDetalleAdjuntosDAO(conexion);
            plansecciondetalleadjuntoDAO.insertarPlansecciondetalleadjunto(plansecciondetalleadjunto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarSecciondetalleitemadjunto(PlanSeccionDetalleItemAdjuntos plansecciondetalleitemadjunto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDetalleItemAdjuntosDAO plansecciondetalleitemadjuntoDAO = new PlanSeccionDetalleItemAdjuntosDAO(conexion);
            plansecciondetalleitemadjuntoDAO.insertarPlansecciondetalleitemadjunto(plansecciondetalleitemadjunto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarSecciondetalletexto(PlanSeccionDetalleTexto plansecciondetalletexto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDetalleTextoDAO plansecciondetalletextoDAO = new PlanSeccionDetalleTextoDAO(conexion);
            plansecciondetalletextoDAO.insertarPlanseccionDetalleTexto(plansecciondetalletexto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarSecciondetalleitemtexto(PlanSeccionDetalleItemTexto plansecciondetalleitemtexto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDetalleItemTextoDAO plansecciondetalleitemtextoDAO = new PlanSeccionDetalleItemTextoDAO(conexion);
            plansecciondetalleitemtextoDAO.insertarPlanseccionDetalleItemTexto(plansecciondetalleitemtexto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanSeccion> cargarListaSeccion(PlanTitulo pt) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionDAO planSeccionDAO = new PlanSeccionDAO(conexion);
            Collection<PlanSeccion> planseccionList = new ArrayList<>();
            planseccionList.addAll(planSeccionDAO.cargarPlanSeccionList(pt));            
            return planseccionList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanSeccionDetalle> cargarListaSecciondetalle(PlanSeccion ps) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionDetalleDAO planSecciondetalleDAO = new PlanSeccionDetalleDAO(conexion);
            Collection<PlanSeccionDetalle> plansecciondetalleList = new ArrayList<>();
            plansecciondetalleList.addAll(planSecciondetalleDAO.cargarPlanSecciondetalleList(ps));
            return plansecciondetalleList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanSeccionDetalleItem> cargarListaSecciondetalleitem(PlanSeccionDetalle psd) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionDetalleItemDAO planSecciondetalleitemDAO = new PlanSeccionDetalleItemDAO(conexion);
            Collection<PlanSeccionDetalleItem> plansecciondetalleitemList = new ArrayList<>();
            plansecciondetalleitemList.addAll(planSecciondetalleitemDAO.cargarPlanSecciondetalleitemList(psd));
            return plansecciondetalleitemList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    
    public Collection<? extends PlanSeccionTexto> cargarPlanSecciontextoList(PlanSeccion planseccion) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionTextoDAO planSecciontextoDAO = new PlanSeccionTextoDAO(conexion);
            Collection<PlanSeccionTexto> plansecciontextoList = new ArrayList<>();
            plansecciontextoList.addAll(planSecciontextoDAO.cargarPlanSecciontextoList(planseccion));
            return plansecciontextoList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanSeccionDetalleTexto> cargarPlanSecciondetalletextoList(PlanSeccionDetalle plansecciondetalle) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionDetalleTextoDAO planSecciondetalletextoDAO = new PlanSeccionDetalleTextoDAO(conexion);
            Collection<PlanSeccionDetalleTexto> plansecciondetalletextoList = new ArrayList<>();
            plansecciondetalletextoList.addAll(planSecciondetalletextoDAO.cargarPlanSecciondetalletextoList(plansecciondetalle));
            return plansecciondetalletextoList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanSeccionDetalleItemTexto> cargarPlanSecciondetalletextoitemList(PlanSeccionDetalleItem plansecciondetalleitem) throws Exception {
        try {
            this.abrirConexion();
            PlanSeccionDetalleItemTextoDAO planSecciondetalleitemtextoDAO = new PlanSeccionDetalleItemTextoDAO(conexion);
            Collection<PlanSeccionDetalleItemTexto> plansecciondetalleitemtextoList = new ArrayList<>();
            plansecciondetalleitemtextoList.addAll(planSecciondetalleitemtextoDAO.cargarPlanSecciondetalleitemtextoList(plansecciondetalleitem));
            return plansecciondetalleitemtextoList;
        } finally {
            this.cerrarConexion();
        }
    }
    
  
    public void validarPlansecciondetalle(PlanSeccionDetalle plansecciondetalle) throws Exception {
        if(plansecciondetalle.getNombre()==null){
            throw new Exception("Ingresa el subtitulo.", UtilLog.TW_VALIDACION);
        }
        if (plansecciondetalle.getPlanSeccionDetallePK().getCodTitulo()==0 || plansecciondetalle.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el numeral", UtilLog.TW_VALIDACION);
        }  
        plansecciondetalle.setNombre(plansecciondetalle.getNombre().trim().toUpperCase());        

    }
    
    public void validarPlansecciondetalleitem(PlanSeccionDetalleItem plansecciondetalleitem) throws Exception {
        if(plansecciondetalleitem.getNombre()==null){
            throw new Exception("Ingresa el subtitulo.", UtilLog.TW_VALIDACION);
        }
        if (plansecciondetalleitem.getPlanSeccionDetalleItemPK().getCodSeccionDetalleItem()==0) {
            throw new Exception("Ingresa el numeral", UtilLog.TW_VALIDACION);
        }  
        plansecciondetalleitem.setNombre(plansecciondetalleitem.getNombre().trim().toUpperCase());        

    }
    
    public void almacenarSecciondetalleitem(PlanSeccionDetalleItem plansecciondetalleitem) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDetalleItemDAO plansecciondetalleitemDAO = new PlanSeccionDetalleItemDAO(conexion);
            plansecciondetalleitemDAO.insertarPlansecciondetalleitem(plansecciondetalleitem);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarSecciondetalle(PlanSeccionDetalle plansecciondetalle) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDetalleDAO plansecciondetalleDAO = new PlanSeccionDetalleDAO(conexion);
            plansecciondetalleDAO.insertarPlansecciondetalle(plansecciondetalle);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.UtilLog;
import com.gestor.publico.Establecimiento;
import com.gestor.seguimiento.PlanSeccion;
import com.gestor.seguimiento.PlanSeccionAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalleAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalleTexto;
import com.gestor.seguimiento.PlanSeccionMatrizDetalle;
import com.gestor.seguimiento.PlanSeccionTexto;
import com.gestor.seguimiento.PlanTitulo;
import com.gestor.seguimiento.PlanTituloTexto;
import com.gestor.seguimiento.dao.PlanSeccionDAO;
import com.gestor.seguimiento.dao.PlanSeccionDetalleDAO;
import com.gestor.seguimiento.dao.PlanTituloAdiuntosDAO;
import com.gestor.seguimiento.PlanTituloAdiuntos;
import com.gestor.seguimiento.dao.PlanTituloDAO;
import com.gestor.seguimiento.dao.PlanTituloMatrizDAO;
import com.gestor.seguimiento.dao.PlanTituloTextoDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 *
 * @author Julian D Osorio G
 */
public class GestorPlanTitulo extends Gestor implements Serializable{

    public GestorPlanTitulo() throws Exception {
        super();
    }

    public Collection<? extends PlanTitulo> cargarPlanTituloList(Establecimiento establecimiento) throws Exception {
        try {
            this.abrirConexion();

            PlanTituloDAO planTituloDAO = new PlanTituloDAO(conexion);
            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            planTituloList.addAll(planTituloDAO.cargarPlanTituloList(establecimiento));
            return planTituloList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanTitulo> cargarPlanTituloList(Integer codEstablecimiento) throws Exception {
        try {
            this.abrirConexion();

            PlanTituloDAO planTituloDAO = new PlanTituloDAO(conexion);
            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            planTituloList.addAll(planTituloDAO.cargarPlanTituloList(codEstablecimiento));
            return planTituloList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanTituloAdiuntos> cargarPlanTituloadjuntoList(PlanTitulo plantitulo) throws Exception {
        try {
            this.abrirConexion();
            PlanTituloAdiuntosDAO planTituloadjuntosDAO = new PlanTituloAdiuntosDAO(conexion);
            Collection<PlanTituloAdiuntos> planTituloadjuntosList = new ArrayList<>();
            planTituloadjuntosList.addAll(planTituloadjuntosDAO.cargarPlanTituloadjuntosList(plantitulo));
            return planTituloadjuntosList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanTituloTexto> cargarPlanTitulotextoList(PlanTitulo plantitulo) throws Exception {
        try {
            this.abrirConexion();

            PlanTituloTextoDAO planTitulotextoDAO = new PlanTituloTextoDAO(conexion);
            Collection<PlanTituloTexto> planTitulotextoList = new ArrayList<>();
            planTitulotextoList.addAll(planTitulotextoDAO.cargarPlanTitulotextoList(plantitulo));
            return planTitulotextoList;
        } finally {
            this.cerrarConexion();
        }
    }
    
    
    
    public void validarPlantitulo(PlanTitulo plantitulo) throws Exception {
        if(plantitulo.getNombre()==null){
            throw new Exception("Ingresa el titulo.", UtilLog.TW_VALIDACION);
        }
        if (plantitulo.getPlanTituloPK().getCodTitulo()==0 || plantitulo.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el numeral", UtilLog.TW_VALIDACION);
        }  
        plantitulo.setNombre(plantitulo.getNombre().trim().toUpperCase());        

    }
    
    public void almacenarTitulotexto(PlanTituloTexto plantitulotexto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanTituloTextoDAO plantitulotextoDAO = new PlanTituloTextoDAO(conexion);
            plantitulotextoDAO.insertarPlantituloTexto(plantitulotexto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }   
    
    public void modificarTitulotexto(PlanTituloTexto plantitulotexto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanTituloTextoDAO plantitulotextoDAO = new PlanTituloTextoDAO(conexion);
            plantitulotextoDAO.modificarplantitulotexto(plantitulotexto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarTitulo(PlanTitulo plantitulo) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanTituloDAO plantituloDAO = new PlanTituloDAO(conexion);
            plantituloDAO.insertarPlantitulo(plantitulo);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarTituloadjunto(PlanTituloAdiuntos plantituloadjunto) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanTituloAdiuntosDAO plantituloadjuntosDAO = new PlanTituloAdiuntosDAO(conexion);
            plantituloadjuntosDAO.insertarPlantituloadjunto(plantituloadjunto);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends PlanTitulo> cargarPlanTituloList(int codigoEstablecimiento, Long codEvaluacion) throws Exception {
        try {
            this.abrirConexion();

            PlanTituloDAO planTituloDAO = new PlanTituloDAO(conexion);
            PlanTituloAdiuntosDAO planTituloAdiuntosDAO = new PlanTituloAdiuntosDAO(conexion);
            PlanTituloTextoDAO planTituloTextoDAO = new PlanTituloTextoDAO(conexion);
            PlanSeccionDAO planSeccionDAO = new PlanSeccionDAO(conexion);
            PlanSeccionDetalleDAO planSeccionDetalleDAO = new PlanSeccionDetalleDAO(conexion);
            PlanTituloMatrizDAO planTituloMatrizDAO = new PlanTituloMatrizDAO(conexion);

            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            planTituloList.addAll(planTituloDAO.cargarPlanTituloList(codigoEstablecimiento, codEvaluacion));

            planTituloList.forEach((pt) -> {
                try {
                    pt.setPlanTituloAdiuntosList((List<PlanTituloAdiuntos>) planTituloAdiuntosDAO.cargarPlanTituloAdiuntosList(pt.getPlanTituloPK(), codEvaluacion));
                    pt.setPlanTituloTextoList((List<PlanTituloTexto>) planTituloTextoDAO.cargarPlanTituloTextoList(pt.getPlanTituloPK()));
                } catch (SQLException ex) {
                    UtilLog.generarLog(this.getClass(), ex);
                }
            });

            //seccion de cada titulo
            planTituloList.forEach((pt) -> {
                try {
                    pt.setPlanSeccionList(planSeccionDAO.cargarPlanSeccion(pt.getPlanTituloPK()));
                    for (PlanSeccion ps : pt.getPlanSeccionList()) {
                        ps.setPlanSeccionAdjuntosList((List<PlanSeccionAdjuntos>) planTituloAdiuntosDAO.cargarPlanSeccionAdjuntos(ps.getPlanSeccionPK(), codEvaluacion));
                        ps.setPlanSeccionTextoList((List<PlanSeccionTexto>) planTituloTextoDAO.cargarPlanSeccionTexto(ps.getPlanSeccionPK()));
                        ps.setPlanSeccionMatriz(planTituloMatrizDAO.cargarPlanSeccionMatriz(ps.getPlanSeccionPK()));
                        if (ps.getPlanSeccionMatriz() != null && ps.getPlanSeccionMatriz().getPlanSeccionMatrizPK() != null
                                && ps.getPlanSeccionMatriz().getPlanSeccionMatrizPK().getCodSeccionMatriz() != null
                                && ps.getPlanSeccionMatriz().getPlanSeccionMatrizPK().getCodSeccionMatriz() != 0) {
                            ps.getPlanSeccionMatriz().setPlanSeccionMatrizDetalleList((List<PlanSeccionMatrizDetalle>) planTituloMatrizDAO.cargarPlanSeccionMatrizDetalle(ps.getPlanSeccionMatriz().getPlanSeccionMatrizPK()));
                        }
                        ps.setPlanSeccionDetalleList(planSeccionDetalleDAO.cargarPlanSeccionDetalle(ps.getPlanSeccionPK()));
                    }
                } catch (SQLException ex) {
                    UtilLog.generarLog(this.getClass(), ex);
                }
            });

            //seccion detalle
            planTituloList.forEach((pt) -> {
                pt.getPlanSeccionList().forEach((ps) -> {
                    ps.getPlanSeccionDetalleList().forEach((psd) -> {
                        try {
                            psd.setPlanSeccionDetalleTextoList((List<PlanSeccionDetalleTexto>) planTituloTextoDAO.cargarPlanSeccionDetalleTexto(psd.getPlanSeccionDetallePK()));
                            psd.setPlanSeccionDetalleAdjuntosList((List<PlanSeccionDetalleAdjuntos>) planTituloAdiuntosDAO.cargarPlanSeccionDetalleAdjuntos(psd.getPlanSeccionDetallePK(), codEvaluacion));
                        } catch (SQLException ex) {
                            UtilLog.generarLog(this.getClass(), ex);
                        }
                    });
                });
            });

            return planTituloList;
        } finally {
            this.cerrarConexion();
        }
    }
}

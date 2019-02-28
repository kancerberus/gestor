/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.controlador;

import com.gestor.controller.Gestor;
import com.gestor.seguimiento.PlanMaestro;
import com.gestor.seguimiento.PlanTitulo;
import com.gestor.seguimiento.dao.PlanMaestroDAO;
import com.gestor.seguimiento.dao.PlanTituloDAO;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Julian D Osorio G
 */
public class GestorPlanMaestro extends Gestor {

    public GestorPlanMaestro() throws Exception {
        super();
    }

    public Collection<? extends PlanMaestro> cargarListaPlanMaestro(String condicion) throws Exception {
        try {
            this.abrirConexion();

            PlanMaestroDAO planMaestroDAO = new PlanMaestroDAO(conexion);
            return planMaestroDAO.cargarListaPlanMaestro(condicion);

        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanMaestro> cargarListaPlanMaestrogerente(String condicion) throws Exception {
        try {
            this.abrirConexion();

            PlanMaestroDAO planMaestroDAO = new PlanMaestroDAO(conexion);
            return planMaestroDAO.cargarListaPlanMaestro(condicion);

        } finally {
            this.cerrarConexion();
        }
    }

    public void procesarPlanMaestro(PlanMaestro pm) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanMaestroDAO planMaestroDAO = new PlanMaestroDAO(conexion);
            planMaestroDAO.upsertPlanMaestro(pm);

            Long codEvaluacion = pm.getPlanMaestroPK().getCodEvaluacion();
            int codigoEstablecimiento = pm.getPlanMaestroPK().getCodigoEstablecimiento();
            Long codMaestro = pm.getPlanMaestroPK().getCodMaestro();

            //titulo
            planMaestroDAO.eliminarPlanMaestroPlanTituloAdiuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            planMaestroDAO.eliminarPlanTituloAdiuntosEvaluacionAdjuntos(codEvaluacion, codigoEstablecimiento);
            planMaestroDAO.procesarPlanMaestroPlanTituloAdiuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            planMaestroDAO.procesarPlanTituloAdiuntosEvaluacionAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);

            //seccion
            planMaestroDAO.eliminarPlanMaestroPlanSeccionAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            planMaestroDAO.eliminarPlanSeccionAdjuntosEvaluacionAdjuntos(codEvaluacion, codigoEstablecimiento);
            planMaestroDAO.procesarPlanMaestroPlanSeccionAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            planMaestroDAO.procesarPlanSeccionAdjuntosEvaluacionAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);

            //seccion detalle
            planMaestroDAO.eliminarPlanMaestroPlanSeccionDetalleAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            planMaestroDAO.eliminarPlanSeccionDetalleAdjuntosEvaluacionAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            planMaestroDAO.procesarPlanMaestroPlanSeccionDetalleAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            planMaestroDAO.procesarPlanSeccionDetalleAdjuntosEvaluacionAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            
            //seccion detalle item
            planMaestroDAO.eliminarPlanMaestroPlanSeccionDetalleItemAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            planMaestroDAO.eliminarPlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos(codEvaluacion, codigoEstablecimiento);
            planMaestroDAO.procesarPlanMaestroPlanSeccionDetalleItemAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);
            planMaestroDAO.procesarPlanSeccionDetalleItemAdjuntosEvaluacionAdjuntos(codEvaluacion, codigoEstablecimiento, codMaestro);

            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public Long consultarCodMaestroPlanMaestro(int codigoEstablecimiento, Long codEvaluacion) throws Exception {
        try {
            this.abrirConexion();

            PlanMaestroDAO planMaestroDAO = new PlanMaestroDAO(conexion);
            return planMaestroDAO.consultarCodMaestroPlanMaestro(codigoEstablecimiento, codEvaluacion);

        } finally {
            this.cerrarConexion();
        }
    }
}

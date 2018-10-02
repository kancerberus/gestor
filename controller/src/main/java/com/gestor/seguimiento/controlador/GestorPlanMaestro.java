/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.controlador;

import com.gestor.controller.Gestor;
import com.gestor.seguimiento.PlanMaestro;
import com.gestor.seguimiento.dao.PlanMaestroDAO;
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

    public void procesarPlanMaestro(PlanMaestro pm) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanMaestroDAO planMaestroDAO = new PlanMaestroDAO(conexion);
            planMaestroDAO.upsertPlanMaestro(pm);
            planMaestroDAO.procesarPlanMaestroPlanTituloAdiuntos(pm.getPlanMaestroPK().getCodEvaluacion(), pm.getPlanMaestroPK().getCodigoEstablecimiento(), pm.getPlanMaestroPK().getCodMaestro());
            planMaestroDAO.procesarPlanTituloAdiuntosEvaluacionAdjuntos(pm.getPlanMaestroPK().getCodEvaluacion(), pm.getPlanMaestroPK().getCodigoEstablecimiento(), pm.getPlanMaestroPK().getCodMaestro());
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
}

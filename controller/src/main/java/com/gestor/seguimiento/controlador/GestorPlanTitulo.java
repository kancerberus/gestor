/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.controlador;

import com.gestor.controller.Gestor;
import com.gestor.publico.Establecimiento;
import com.gestor.seguimiento.PlanTitulo;
import com.gestor.seguimiento.dao.PlanTituloAdiuntosDAO;
import com.gestor.seguimiento.dao.PlanTituloDAO;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Julian D Osorio G
 */
public class GestorPlanTitulo extends Gestor {

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

    public Collection<? extends PlanTitulo> cargarPlanTituloList(int codigoEstablecimiento, Long codEvaluacion) throws Exception {
        try {
            this.abrirConexion();

            PlanTituloDAO planTituloDAO = new PlanTituloDAO(conexion);
            PlanTituloAdiuntosDAO planTituloAdiuntosDAO = new PlanTituloAdiuntosDAO(conexion);
            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            planTituloList.addAll(planTituloDAO.cargarPlanTituloList(codigoEstablecimiento, codEvaluacion));
            planTituloList.forEach((pt) -> {
                pt.getPlanTituloAdiuntosList().addAll(planTituloAdiuntosDAO.cargarPlanTituloAdiuntosList(pt.getPlanTituloPK()));
                
            });
            return planTituloList;
        } finally {
            this.cerrarConexion();
        }
    }
}

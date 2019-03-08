/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.controlador;

import com.gestor.controller.Gestor;
import com.gestor.gestor.EvaluacionPuntajeSeccionDetalleCombos;
import com.gestor.gestor.EvaluacionPuntajeSeccionDetalleCombosPK;
import com.gestor.gestor.dao.SeccionDetalleItemsDAO;
import com.gestor.gestor.SeccionDetalleItems;
import com.gestor.seguimiento.PlanSeccionDetalleItem;
import com.gestor.seguimiento.dao.PlanSeccionDetalleItemDAO;
import java.util.List;

/**
 *
 * @author juliano
 */
public class GestorSeccionDetalleItems extends Gestor {

    public GestorSeccionDetalleItems() throws Exception {
        super();
    }

    public void upsertEvaluacionPuntajeSeccionDetalleCombos(EvaluacionPuntajeSeccionDetalleCombos epsc) throws Exception {
        try {
            this.abrirConexion();

            SeccionDetalleItemsDAO seccionDetalleItemsDAO = new SeccionDetalleItemsDAO(conexion);
            seccionDetalleItemsDAO.upsertEvaluacionPuntajeSeccionDetalleCombos(epsc);

        } finally {
            this.cerrarConexion();
        }
    }

    public String cargarDescripcionEvaluacionPuntajes(EvaluacionPuntajeSeccionDetalleCombosPK evaluacionPuntajeSeccionDetalleCombosPK) throws Exception {
        try {
            this.abrirConexion();
            SeccionDetalleItemsDAO seccionDetalleItemsDAO = new SeccionDetalleItemsDAO(conexion);
            return seccionDetalleItemsDAO.cargarDescripcionEvaluacionPuntajes(evaluacionPuntajeSeccionDetalleCombosPK);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<?> buscarNumeral(String numeral) throws Exception {   
        
        try {
            this.abrirConexion();
            SeccionDetalleItemsDAO secciondetalleitemsDAO = new SeccionDetalleItemsDAO(conexion);                                                 
            return secciondetalleitemsDAO.buscarNumeral(numeral);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarPlanSeccionDetalleItem(PlanSeccionDetalleItem psti) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanSeccionDetalleItemDAO plansecciondetalleitemDAO = new PlanSeccionDetalleItemDAO(conexion);
            plansecciondetalleitemDAO.eliminarPlansecciondetalleitem(psti);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
}

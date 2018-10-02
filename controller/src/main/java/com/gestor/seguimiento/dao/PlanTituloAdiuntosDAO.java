/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.seguimiento.PlanTituloAdiuntos;
import com.gestor.seguimiento.PlanTituloPK;
import java.sql.Connection;
import java.util.Collection;

/**
 *
 * @author Julian D Osorio G
 */
public class PlanTituloAdiuntosDAO {

    private Connection conexion;

    public PlanTituloAdiuntosDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<? extends PlanTituloAdiuntos> cargarPlanTituloAdiuntosList(PlanTituloPK planTituloPK) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

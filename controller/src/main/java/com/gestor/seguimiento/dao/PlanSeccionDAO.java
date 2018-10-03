/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.seguimiento.PlanSeccion;
import com.gestor.seguimiento.PlanTituloPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julian D Osorio G
 */
public class PlanSeccionDAO {

    private Connection conexion;

    public PlanSeccionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<PlanSeccion> cargarPlanSeccion(PlanTituloPK planTituloPK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder();
            rs = consulta.ejecutar(sql);
            List<PlanSeccion> planSeccionList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccion ps = new PlanSeccion();
                planSeccionList.add(ps);
            }
            return planSeccionList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

}

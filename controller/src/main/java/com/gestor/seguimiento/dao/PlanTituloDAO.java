/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.publico.Establecimiento;
import com.gestor.seguimiento.PlanTitulo;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Julian D Osorio G
 */
public class PlanTituloDAO {

    private Connection conexion;

    public PlanTituloDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<? extends PlanTitulo> cargarPlanTituloList(Establecimiento establecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            rs = consulta.ejecutar(sql);
            return planTituloList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends PlanTitulo> cargarPlanTituloList(int codigoEstablecimiento, Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            rs = consulta.ejecutar(sql);
            return planTituloList;
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

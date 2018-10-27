/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.seguimiento.PlanSeccion;
import com.gestor.seguimiento.PlanSeccionDetalle;
import com.gestor.seguimiento.PlanSeccionDetallePK;
import com.gestor.seguimiento.PlanSeccionPK;
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
public class PlanSeccionDetalleDAO {

    private Connection conexion;

    public PlanSeccionDetalleDAO(Connection connection) {
        this.conexion = connection;
    }

    public List<PlanSeccionDetalle> cargarPlanSeccionDetalle(PlanSeccionPK planSeccionPK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle,"
                    + " numeral, nombre"
                    + " FROM seguimiento.plan_seccion_detalle"
                    + " WHERE codigo_establecimiento=" + planSeccionPK.getCodigoEstablecimiento() + " AND cod_titulo=" + planSeccionPK.getCodTitulo()
                    + " AND cod_seccion=" + planSeccionPK.getCodSeccion()
            );
            rs = consulta.ejecutar(sql);
            List<PlanSeccionDetalle> planSeccionDetalleList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionDetalle ps = new PlanSeccionDetalle(new PlanSeccionDetallePK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle"))
                        , rs.getString("numeral"), rs.getString("nombre"));
                planSeccionDetalleList.add(ps);
            }
            return planSeccionDetalleList;
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
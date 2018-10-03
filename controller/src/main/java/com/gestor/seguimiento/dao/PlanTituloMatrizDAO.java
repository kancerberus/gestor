/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.seguimiento.PlanSeccionMatriz;
import com.gestor.seguimiento.PlanSeccionMatrizDetalle;
import com.gestor.seguimiento.PlanSeccionMatrizDetallePK;
import com.gestor.seguimiento.PlanSeccionMatrizPK;
import com.gestor.seguimiento.PlanSeccionPK;
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
public class PlanTituloMatrizDAO {

    private Connection conexion;

    public PlanTituloMatrizDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public PlanSeccionMatriz cargarPlanSeccionMatriz(PlanSeccionPK planSeccionPK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_matriz,"
                    + " titulo, descripcion"
                    + " FROM seguimiento.plan_seccion_matriz"
                    + " WHERE codigo_establecimiento=" + planSeccionPK.getCodigoEstablecimiento() + " AND cod_titulo=" + planSeccionPK.getCodTitulo() + " AND cod_seccion" + planSeccionPK.getCodTitulo()
            );
            rs = consulta.ejecutar(sql);
            PlanSeccionMatriz psm = new PlanSeccionMatriz(
                    new PlanSeccionMatrizPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getLong("cod_seccion_matriz")),
                    rs.getString("titulo"), rs.getString("descripcion")
            );
            return psm;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends PlanSeccionMatrizDetalle> cargarPlanSeccionMatrizDetalle(PlanSeccionMatrizPK planSeccionMatrizPK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_matriz,"
                    + " cod_seccion_matriz_detalle, directriz, objetivos, metas, indicadores,"
                    + " modalidad, horarios"
                    + " FROM seguimiento.plan_seccion_matriz_detalle"
                    + " WHERE codigo_establecimiento=" + planSeccionMatrizPK.getCodigoEstablecimiento()
                    + " AND cod_titulo=" + planSeccionMatrizPK.getCodTitulo() + " AND cod_seccion=" + planSeccionMatrizPK.getCodSeccion()
                    + " AND cod_seccion_matriz=" + planSeccionMatrizPK.getCodSeccionMatriz()
            );
            rs = consulta.ejecutar(sql);
            Collection<PlanSeccionMatrizDetalle> planSeccionMatrizDetalleList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionMatrizDetalle pmd = new PlanSeccionMatrizDetalle(
                        new PlanSeccionMatrizDetallePK(
                                rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"),
                                rs.getInt("cod_seccion"), rs.getLong("cod_seccion_matriz"), rs.getInt("cod_seccion_matriz_detalle")),
                        rs.getString("directriz"), rs.getString("objetivos"), rs.getString("metas"), rs.getString("indicadores"),
                        rs.getString("modalidad"), rs.getString("horarios")
                );
                planSeccionMatrizDetalleList.add(pmd);
            }
            return planSeccionMatrizDetalleList;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.controller.Gestor;
import com.gestor.seguimiento.PlanSeccionDetallePK;
import com.gestor.seguimiento.PlanSeccionDetalleTexto;
import com.gestor.seguimiento.PlanSeccionDetalleTextoPK;
import com.gestor.seguimiento.PlanSeccionPK;
import com.gestor.seguimiento.PlanSeccionTexto;
import com.gestor.seguimiento.PlanSeccionTextoPK;
import com.gestor.seguimiento.PlanTituloPK;
import com.gestor.seguimiento.PlanTituloTexto;
import com.gestor.seguimiento.PlanTituloTextoPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Julian D Osorio G
 */
public class PlanTituloTextoDAO extends Gestor {

    private Connection conexion;

    public PlanTituloTextoDAO(Connection conexion) throws Exception {
        this.conexion = conexion;
    }

    public Collection<? extends PlanTituloTexto> cargarPlanTituloTextoList(PlanTituloPK planTituloPK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_titulo_texto, texto"
                    + " FROM seguimiento.plan_titulo_texto"
                    + " WHERE codigo_establecimiento=" + planTituloPK.getCodigoEstablecimiento() + " AND cod_titulo=" + planTituloPK.getCodTitulo()
            );

            rs = consulta.ejecutar(sql);
            Collection<PlanTituloTexto> planTituloTextoList = new ArrayList<>();
            while (rs.next()) {
                PlanTituloTexto ptt = new PlanTituloTexto(
                        new PlanTituloTextoPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_titulo_texto")
                        ), rs.getString("texto")
                );
                planTituloTextoList.add(ptt);
            }
            return planTituloTextoList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends PlanSeccionTexto> cargarPlanSeccionTexto(PlanSeccionPK planSeccionPK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_texto,"
                    + " texto"
                    + " FROM seguimiento.plan_seccion_texto"
                    + " WHERE codigo_establecimiento=" + planSeccionPK.getCodigoEstablecimiento()
                    + " AND cod_titulo=" + planSeccionPK.getCodTitulo() + " AND cod_seccion=" + planSeccionPK.getCodSeccion()
            );

            rs = consulta.ejecutar(sql);
            Collection<PlanSeccionTexto> planSeccionTextoList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionTexto pst = new PlanSeccionTexto(
                        new PlanSeccionTextoPK(
                                rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"),
                                rs.getInt("cod_seccion_texto")
                        ),
                        rs.getString("texto")
                );
                planSeccionTextoList.add(pst);
            }
            return planSeccionTextoList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<PlanSeccionDetalleTexto> cargarPlanSeccionDetalleTexto(PlanSeccionDetallePK planSeccionDetallePK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle,"
                    + " cod_seccion_detalle_texto, texto"
                    + " FROM seguimiento.plan_seccion_detalle_texto"
                    + " WHERE codigo_establecimiento=" + planSeccionDetallePK.getCodigoEstablecimiento()
                    + " AND cod_titulo=" + planSeccionDetallePK.getCodTitulo() + " AND cod_seccion=" + planSeccionDetallePK.getCodSeccion()
                    + " AND cod_seccion_detalle=" + planSeccionDetallePK.getCodSeccionDetalle()
            );

            rs = consulta.ejecutar(sql);
            Collection<PlanSeccionDetalleTexto> planSeccionDetalleTextoList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionDetalleTexto psdt = new PlanSeccionDetalleTexto(new PlanSeccionDetalleTextoPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo")
                        , rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle"), rs.getInt("cod_seccion_detalle_texto")), rs.getString("texto"));
                planSeccionDetalleTextoList.add(psdt);
            }
            return planSeccionDetalleTextoList;
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

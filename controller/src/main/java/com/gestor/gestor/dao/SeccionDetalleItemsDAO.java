/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.EvaluacionPuntajeSeccionDetalleCombos;
import com.gestor.gestor.EvaluacionPuntajeSeccionDetalleCombosPK;
import com.gestor.gestor.SeccionDetalleItems;
import com.gestor.gestor.SeccionDetalleItemsPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juliano
 */
public class SeccionDetalleItemsDAO {

    private Connection conexion;

    public SeccionDetalleItemsDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<SeccionDetalleItems> cargarListaSeccionDetalleItems(String codCiclo, int codSeccion, int codDetalle) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_ciclo, cod_seccion, cod_detalle, cod_item, nombre, detalle, peso, activo, imagen, orden, numeral"
                    + " FROM gestor.seccion_detalle_items"
                    + " WHERE cod_ciclo='" + codCiclo + "' AND cod_seccion=" + codSeccion + " AND cod_detalle=" + codDetalle
                    + " ORDER BY orden"
            );

            rs = consulta.ejecutar(sql);
            List<SeccionDetalleItems> seccionDetalleItemses = new ArrayList<>();
            while (rs.next()) {
                SeccionDetalleItems sdi = new SeccionDetalleItems(new SeccionDetalleItemsPK(rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item")),
                        rs.getString("nombre"), rs.getString("detalle"), rs.getDouble("peso"), rs.getBoolean("activo"), rs.getString("imagen"), rs.getInt("orden"));
                sdi.setNumeral(rs.getString("numeral"));
                seccionDetalleItemses.add(sdi);
            }
            return seccionDetalleItemses;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void upsertEvaluacionPuntajeSeccionDetalleCombos(EvaluacionPuntajeSeccionDetalleCombos epsc) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            EvaluacionPuntajeSeccionDetalleCombosPK pk = epsc.getEvaluacionPuntajeSeccionDetalleCombosPK();
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_puntaje_seccion_detalle_combos("
                    + " cod_evaluacion, codigo_establecimiento, cod_puntaje, cod_ciclo, "
                    + " cod_seccion, cod_detalle, cod_item)"
                    + " VALUES (" + pk.getCodEvaluacion() + ", " + pk.getCodigoEstablecimiento() + ", '" + pk.getCodPuntaje() + "'"
                    + " , '" + pk.getCodCiclo() + "', " + pk.getCodSeccion() + ", " + pk.getCodDetalle() + ", " + pk.getCodItem() + ")"
                    + " ON CONFLICT (cod_evaluacion, codigo_establecimiento, cod_ciclo, cod_seccion, cod_detalle, cod_item) DO UPDATE SET "
                    + " cod_puntaje=EXCLUDED.cod_puntaje"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public String cargarDescripcionEvaluacionPuntajes(EvaluacionPuntajeSeccionDetalleCombosPK pk) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT EP.descripcion"
                    + " FROM gestor.evaluacion_puntaje_seccion_detalle_combos"
                    + " JOIN gestor.evaluacion_puntajes EP USING (codigo_establecimiento, cod_evaluacion, cod_puntaje)"
                    + " WHERE cod_evaluacion=" + pk.getCodEvaluacion() + " AND codigo_establecimiento=" + pk.getCodigoEstablecimiento() + " AND cod_ciclo='" + pk.getCodCiclo() + "'"
                    + " AND cod_seccion=" + pk.getCodSeccion() + " AND cod_detalle=" + pk.getCodDetalle() + " AND cod_item=" + pk.getCodItem()
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getString("descripcion");
            } else {
                return null;
            }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.seguimiento.PlanSeccionDetalle;
import com.gestor.seguimiento.PlanSeccionDetalleItem;
import com.gestor.seguimiento.PlanSeccionDetalleItemPK;
import com.gestor.seguimiento.PlanSeccionDetallePK;
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
public class PlanSeccionDetalleItemDAO {

    private Connection conexion;

    public PlanSeccionDetalleItemDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<PlanSeccionDetalleItem> cargarPlanSeccionDetalleItem(PlanSeccionDetallePK planSeccionDetallePK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT PSDI.cod_titulo, PSDI.codigo_establecimiento, PSDI.cod_seccion, PSDI.cod_seccion_detalle,"
                    + " PSDI.cod_seccion_detalle_item, PSDI.numeral, PSDI.nombre"
                    + " FROM seguimiento.plan_seccion_detalle_item PSDI"
                    + " WHERE cod_titulo=" + planSeccionDetallePK.getCodTitulo()
                    + " AND codigo_establecimiento=" + planSeccionDetallePK.getCodigoEstablecimiento()
                    + " AND cod_seccion=" + planSeccionDetallePK.getCodSeccion() + " AND cod_seccion_detalle=" + planSeccionDetallePK.getCodSeccionDetalle()
            );
            rs = consulta.ejecutar(sql);
            List<PlanSeccionDetalleItem> planSeccionDetalleItemList = new ArrayList<>();
            while (rs.next()) {
                //int codTitulo, int codigoEstablecimiento, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem
                PlanSeccionDetalleItem ps = new PlanSeccionDetalleItem(
                        new PlanSeccionDetalleItemPK(
                                rs.getInt("cod_titulo"), rs.getInt("codigo_establecimiento"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle"), rs.getInt("cod_seccion_detalle_item")
                        ),
                        rs.getString("numeral"), rs.getString("nombre"));
                planSeccionDetalleItemList.add(ps);
            }
            return planSeccionDetalleItemList;
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

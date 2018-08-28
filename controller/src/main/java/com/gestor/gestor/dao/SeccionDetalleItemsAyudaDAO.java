/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.SeccionDetalleItemsAyuda;
import com.gestor.gestor.SeccionDetalleItemsAyudaPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author juliano
 */
public class SeccionDetalleItemsAyudaDAO {

    private Connection conexion;

    public SeccionDetalleItemsAyudaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<? extends SeccionDetalleItemsAyuda> cargarSeccionDetalleItemsAyudas(SeccionDetalleItemsAyudaPK pk) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_ayuda, nombre,"
                    + " definicion"
                    + " FROM gestor.seccion_detalle_items_ayuda"
                    + " WHERE cod_ciclo='" + pk.getCodCiclo() + "' AND cod_seccion=" + pk.getCodSeccion() + " AND cod_detalle=" + pk.getCodDetalle() + " AND cod_item=" + pk.getCodItem()
            );
            rs = consulta.ejecutar(sql);
            Collection<SeccionDetalleItemsAyuda> seccionDetalleItemsAyudas = new ArrayList<>();
            while (rs.next()) {
                SeccionDetalleItemsAyuda sdia = new SeccionDetalleItemsAyuda(rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getInt("cod_ayuda"), rs.getString("nombre"), rs.getString("definicion"));
                seccionDetalleItemsAyudas.add(sdia);
            }
            return seccionDetalleItemsAyudas;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.SeccionDetalle;
import com.gestor.gestor.SeccionDetallePK;
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
public class SeccionDetalleDAO {

    private Connection conexion;

    public SeccionDetalleDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<SeccionDetalle> cargarListaSeccionDetalle(String codCiclo, int codSeccion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_ciclo, cod_seccion, cod_detalle, nombre, detalle, orden, peso, imagen, activo, numeral"
                    + " FROM gestor.seccion_detalle"
                    + " WHERE cod_ciclo='" + codCiclo + "' AND cod_seccion=" + codSeccion
                    + " ORDER BY orden"
            );

            rs = consulta.ejecutar(sql);
            List<SeccionDetalle> seccionDetalles = new ArrayList<>();
            while (rs.next()) {
                SeccionDetalle sd = new SeccionDetalle(new SeccionDetallePK(rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle")),
                        rs.getString("nombre"), rs.getString("detalle"), rs.getInt("orden"), rs.getDouble("peso"), rs.getString("imagen"), rs.getBoolean("activo"));
                sd.setNumeral(rs.getString("numeral"));
                seccionDetalles.add(sd);
            }
            return seccionDetalles;
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

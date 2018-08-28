/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.Ciclo;
import com.gestor.gestor.Seccion;
import com.gestor.gestor.SeccionPK;
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
public class SeccionDAO {

    private Connection conexion;

    public SeccionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Seccion> cargarListaSeccion(String codCiclo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_ciclo, cod_seccion, nombre, activo, peso, imagen, orden, numeral"
                    + " FROM gestor.seccion"
                    + " WHERE cod_ciclo='" + codCiclo + "'"
                    + " ORDER BY orden"
            );

            rs = consulta.ejecutar(sql);
            List<Seccion> seccions = new ArrayList<>();
            while (rs.next()) {
                Seccion s = new Seccion(new SeccionPK(rs.getString("cod_ciclo"), rs.getInt("cod_seccion")), rs.getString("nombre"), rs.getBoolean("activo"), rs.getDouble("peso"),
                        rs.getString("imagen"), rs.getInt("orden")
                );
                s.setNumeral(rs.getString("numeral"));
                seccions.add(s);
            }
            return seccions;
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

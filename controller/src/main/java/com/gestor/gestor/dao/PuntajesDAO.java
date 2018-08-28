/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.Puntajes;
import com.gestor.gestor.PuntajesPK;
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
public class PuntajesDAO {

    private Connection conexion;

    public PuntajesDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Puntajes> cargarPuntajes(Integer codigoEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_puntaje, descripcion, plan_accion,"
                    + " capacitacion, activo, califica, orden"
                    + " FROM gestor.puntajes"
                    + " WHERE codigo_establecimiento=" + codigoEstablecimiento
                    + " ORDER BY orden"
            );
            rs = consulta.ejecutar(sql);
            List<Puntajes> puntajesList = new ArrayList<>();
            while (rs.next()) {
                Puntajes p = new Puntajes(new PuntajesPK(rs.getInt("codigo_establecimiento"), rs.getString("cod_puntaje")), rs.getString("descripcion"),
                        rs.getBoolean("plan_accion"), rs.getBoolean("capacitacion"), rs.getBoolean("activo"), rs.getBoolean("califica"), rs.getInt("orden"));
                puntajesList.add(p);
            }
            return puntajesList;
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

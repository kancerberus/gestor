/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.dao;

import com.gestor.publico.Municipios;
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
public class MunicipiosDAO {

    private Connection conexion;

    public MunicipiosDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<? extends Municipios> cargarMunicipios() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_municipio, codigo_departamento, nombre"
                    + " FROM public.municipios"
                    + " ORDER BY nombre"
            );
            rs = consulta.ejecutar(sql);
            ArrayList<Municipios> municipioses = new ArrayList<>();
            while (rs.next()) {
                Municipios m = new Municipios(rs.getString("codigo_municipio"), rs.getString("nombre"));
                m.setCodigoDepartamento(rs.getString("codigo_departamento"));
                municipioses.add(m);
            }
            return municipioses;
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

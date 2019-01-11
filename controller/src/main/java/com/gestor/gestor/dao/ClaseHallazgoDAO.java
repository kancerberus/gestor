/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.ClaseHallazgo;
import com.gestor.publico.dao.*;
import com.gestor.publico.Responsable;
import com.gestor.publico.Establecimiento;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author franj
 */
public class ClaseHallazgoDAO {

    private Connection conexion;

    public ClaseHallazgoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public List<?> cargarListaClasehallazgo() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<ClaseHallazgo> listaClasehallazgo = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_clase_hallazgo, nombre "                    
                    + "FROM gestor.clase_hallazgo"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                ClaseHallazgo fh= new ClaseHallazgo(rs.getInt("cod_clase_hallazgo"), rs.getString("nombre"));                                
                listaClasehallazgo.add(fh);
            }
            return listaClasehallazgo;
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

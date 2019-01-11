/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.FuenteHallazgo;
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
public class FuenteHallazgoDAO {

    private Connection conexion;

    public FuenteHallazgoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public List<?> cargarListaFuentehallazgo() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<FuenteHallazgo> listaFuentehallazgo = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_fuente_hallazgo, nombre "                    
                    + "FROM gestor.fuente_hallazgo"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                FuenteHallazgo fh= new FuenteHallazgo(rs.getInt("cod_fuente_hallazgo"), rs.getString("nombre"));                                
                listaFuentehallazgo.add(fh);
            }
            return listaFuentehallazgo;
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

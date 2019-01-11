/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.MotivoCorreccion;
import com.gestor.gestor.TipoAccion;
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
public class MotivoCorreccionDAO {

    private Connection conexion;

    public MotivoCorreccionDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public List<?> cargarListaMotivocorreccion() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<MotivoCorreccion> listaMotivocorreccion = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_motivo_correccion, nombre "                    
                    + "FROM gestor.motivo_correccion"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                MotivoCorreccion mc= new MotivoCorreccion(rs.getInt("cod_motivo_correccion"), rs.getString("nombre"));                                
                listaMotivocorreccion.add(mc);
            }
            return listaMotivocorreccion;
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

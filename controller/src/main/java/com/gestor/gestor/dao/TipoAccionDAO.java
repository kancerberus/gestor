/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.TipoAccion;
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
public class TipoAccionDAO {

    private Connection conexion;

    public TipoAccionDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public List<?> cargarListaTipoaccion() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<TipoAccion> listaTipoaccion = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_tipo_accion, nombre "                    
                    + "FROM gestor.tipo_accion"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                TipoAccion fh= new TipoAccion(rs.getInt("cod_tipo_accion"), rs.getString("nombre"));                                
                listaTipoaccion.add(fh);
            }
            return listaTipoaccion;
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

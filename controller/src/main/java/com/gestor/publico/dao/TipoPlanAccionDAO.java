/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.dao;

import com.gestor.publico.TipoPlanAccion;
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
public class TipoPlanAccionDAO {
    
    private Connection conexion;

    public TipoPlanAccionDAO(Connection conexion) {
        this.conexion = conexion;
    }  
    
    
    public ArrayList<TipoPlanAccion> cargarListaTipoPlanAccion() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<TipoPlanAccion> listaTipoPlanAccion = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_tipo_plan_accion, nombre "                    
                    + "FROM tipo_plan_accion"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                TipoPlanAccion tpa= new TipoPlanAccion(rs.getInt("cod_tipo_plan_accion"), rs.getString("nombre"));                                
                listaTipoPlanAccion.add(tpa);
            }
            return listaTipoPlanAccion;
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

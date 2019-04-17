/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.Ciclo;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author juliano
 */
public class CicloDAO {

    private Connection conexion;

    public CicloDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Ciclo> cargarListaCiclos() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_ciclo, nombre, numeral"
                    + " FROM gestor.ciclo WHERE cod_ciclo<>'X' ORDER BY numeral;"
            );

            rs = consulta.ejecutar(sql);
            List<Ciclo> ciclos = new ArrayList<>();
            while (rs.next()) {
                Ciclo c = new Ciclo(rs.getString("cod_ciclo"), rs.getString("nombre"));
                c.setNumeral(rs.getString("numeral"));
                ciclos.add(c);
            }
            return ciclos;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }   
    
    public String traerCiclo(String numeral) throws SQLException{
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_ciclo, nombre, numeral"
                    + " FROM gestor.ciclo "
                    + " where numeral = '"+numeral+"' and cod_ciclo<>'X' "
                            
            );

            rs = consulta.ejecutar(sql);     
            String c=null;
            while (rs.next()) {
                c=(rs.getString("cod_ciclo"));
            }
            return c;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    
    public List<Ciclo> cargarListaCiclosnombre(String numeral) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_ciclo, nombre, numeral"
                    + " FROM gestor.ciclo "
                    + " where numeral = '"+numeral+"' "
                            
            );

            rs = consulta.ejecutar(sql);
            List<Ciclo> ciclos = new ArrayList<>();
            while (rs.next()) {
                Ciclo c = new Ciclo(rs.getString("nombre"), rs.getString("nombre"));
                ciclos.add(c);
            }
            return ciclos;
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

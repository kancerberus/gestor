/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.publico.Establecimiento;
import com.gestor.seguimiento.PlanTitulo;
import com.gestor.seguimiento.PlanTituloTexto;
import com.gestor.seguimiento.PlanTituloPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Julian D Osorio G
 */
public class PlanTituloDAO {

    private Connection conexion;

    public PlanTituloDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<? extends PlanTitulo> cargarPlanTituloList(Establecimiento establecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            rs = consulta.ejecutar(sql);
            return planTituloList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    
    public Collection<? extends PlanTitulo> cargarPlanTituloList(Integer codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, nombre, numeral"
                    + " FROM seguimiento.plan_titulo"
                    + " WHERE codigo_establecimiento='"+codEstablecimiento+"'"
                    + " ORDER BY cod_titulo"  
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTitulo pt = new PlanTitulo(new PlanTituloPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo")), rs.getString("nombre"), rs.getString("numeral"));
                planTituloList.add(pt);
            }
            return planTituloList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    
    
    

    public Collection<? extends PlanTitulo> cargarPlanTituloList(int codigoEstablecimiento, Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, nombre, numeral"
                    + " FROM seguimiento.plan_titulo"
                    + " WHERE codigo_establecimiento=" + codigoEstablecimiento+" "
                    + " ORDER BY cod_titulo"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTitulo pt = new PlanTitulo(new PlanTituloPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo")), rs.getString("nombre"), rs.getString("numeral"));
                planTituloList.add(pt);
            }
            return planTituloList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarPlantitulo(PlanTitulo plantitulo) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_titulo "
                    + " ( codigo_establecimiento, cod_titulo, nombre, numeral )"
                    + " VALUES ('"+plantitulo.getPlanTituloPK().getCodigoEstablecimiento()+"', '"+plantitulo.getPlanTituloPK().getCodTitulo()+"', '"+plantitulo.getNombre()+"','"+plantitulo.getNumeral()+"') "                                        
                    + " ON CONFLICT ( codigo_establecimiento, cod_titulo ) DO UPDATE"
                    + " SET nombre=EXCLUDED.nombre, numeral=EXCLUDED.numeral "
                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarPlantitulo(PlanTitulo plantitulo) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM seguimiento.plan_titulo "
                    + " WHERE codigo_establecimiento='"+plantitulo.getPlanTituloPK().getCodigoEstablecimiento()+"' AND cod_titulo='"+plantitulo.getPlanTituloPK().getCodTitulo()+"'"                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public Collection<? extends PlanTitulo> cargarPlanTituloList() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanTitulo> planTituloList = new ArrayList<>();
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, nombre, numeral"
                    + " FROM seguimiento.plan_titulo"
                    + " ORDER BY numeral"  
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTitulo pt = new PlanTitulo(new PlanTituloPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo")), rs.getString("nombre"), rs.getString("numeral"));
                planTituloList.add(pt);
            }
            return planTituloList;
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


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.seguimiento.PlanSeccion;
import com.gestor.seguimiento.PlanSeccionDetalle;
import com.gestor.seguimiento.PlanSeccionDetallePK;
import com.gestor.seguimiento.PlanSeccionPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julian D Osorio G
 */
public class PlanSeccionDetalleDAO {

    private Connection conexion;

    public PlanSeccionDetalleDAO(Connection connection) {
        this.conexion = connection;
    }
    
    public List<PlanSeccionDetalle> cargarPlanSeccionDetalle(PlanSeccionPK planSeccionPK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle,"
                    + " numeral, nombre"
                    + " FROM seguimiento.plan_seccion_detalle"
                    + " WHERE codigo_establecimiento=" + planSeccionPK.getCodigoEstablecimiento() + " AND cod_titulo=" + planSeccionPK.getCodTitulo()
                    + " AND cod_seccion=" + planSeccionPK.getCodSeccion()
            );
            rs = consulta.ejecutar(sql);
            List<PlanSeccionDetalle> planSeccionDetalleList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionDetalle ps = new PlanSeccionDetalle(new PlanSeccionDetallePK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle"))
                        , rs.getString("numeral"), rs.getString("nombre"));
                planSeccionDetalleList.add(ps);
            }
            return planSeccionDetalleList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarPlansecciondetalle(PlanSeccionDetalle plansecciondetalle) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_seccion_detalle "
                    + " ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, nombre, numeral)"
                    +" VALUES ('"+plansecciondetalle.getPlanSeccionDetallePK().getCodigoEstablecimiento()+"', "
                            + " '"+plansecciondetalle.getPlanSeccionDetallePK().getCodTitulo()+"', "
                            +" '"+plansecciondetalle.getPlanSeccionDetallePK().getCodSeccion()+"', "
                            + " '"+plansecciondetalle.getPlanSeccionDetallePK().getCodSeccionDetalle()+"',  "
                            + " '"+plansecciondetalle.getNombre()+"','"+plansecciondetalle.getNumeral()+"') "                                        
                            + " ON CONFLICT (cod_titulo, codigo_establecimiento, cod_seccion, cod_seccion_detalle) DO UPDATE"
                            + " SET nombre=EXCLUDED.nombre, numeral=EXCLUDED.numeral "
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<PlanSeccionDetalle> cargarPlanSecciondetalleList(PlanSeccion ps) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle,  nombre, numeral"                    
                    + " FROM seguimiento.plan_seccion_detalle"
                    + " WHERE codigo_establecimiento= '"+ ps.getPlanSeccionPK().getCodigoEstablecimiento() +"' AND cod_titulo='" + ps.getPlanSeccionPK().getCodTitulo()+"' "
                    + " AND cod_seccion= '"+ps.getPlanSeccionPK().getCodSeccion()+"'"
                    + " ORDER BY cod_seccion_detalle"
            );
            rs = consulta.ejecutar(sql);
            List<PlanSeccionDetalle> planSecciondetalleList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionDetalle psd = new PlanSeccionDetalle(new PlanSeccionDetallePK(rs.getInt("codigo_establecimiento")
                        , rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle") ), rs.getString("numeral"), rs.getString("nombre")
                );
                planSecciondetalleList.add(psd);
            }
            return planSecciondetalleList;
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

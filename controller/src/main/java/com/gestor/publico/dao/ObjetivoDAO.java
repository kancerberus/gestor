/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.dao;

import com.gestor.publico.Responsable;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.PlanTrabajoObjetivo;
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
public class ObjetivoDAO {

    private Connection conexion;

    public ObjetivoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<?> cargarListaObjetivo(int codEstablecimiento, int codPlantrabajo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<PlanTrabajoObjetivo> listaObjetivo = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_plan_trabajo, cod_objetivo,  nombre "                    
                    + " FROM plan_trabajo_objetivo "             
                    + " WHERE codigo_establecimiento='"+codEstablecimiento+"' and cod_plan_trabajo='"+codPlantrabajo+"'"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTrabajoObjetivo obj = new PlanTrabajoObjetivo(rs.getInt("codigo_establecimiento"),rs.getInt("cod_plan_trabajo"),rs.getInt("cod_objetivo"), rs.getString("nombre"));                
                obj.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                obj.setCodPlantrabajo(rs.getInt("cod_plan_trabajo"));
                obj.setCodObjetivo(rs.getInt("cod_objetivo"));                
                obj.setNombre(rs.getString("nombre"));
                listaObjetivo.add(obj);                
            }
            return listaObjetivo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaObjetivoplanaccion(int codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<PlanTrabajoObjetivo> listaObjetivo = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_plan_trabajo, cod_objetivo,  nombre "                    
                    + " FROM plan_trabajo_objetivo "             
                    + " WHERE codigo_establecimiento='"+codEstablecimiento+"' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTrabajoObjetivo obj = new PlanTrabajoObjetivo(rs.getInt("codigo_establecimiento"),rs.getInt("cod_plan_trabajo"),rs.getInt("cod_objetivo"), rs.getString("nombre"));                
                obj.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                obj.setCodPlantrabajo(rs.getInt("cod_plan_trabajo"));
                obj.setCodObjetivo(rs.getInt("cod_objetivo"));                
                obj.setNombre(rs.getString("nombre"));
                listaObjetivo.add(obj);                
            }
            return listaObjetivo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void insertarObjetivo(PlanTrabajoObjetivo objetivo) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO plan_trabajo_objetivo "
                    + " (codigo_establecimiento, cod_plan_trabajo, cod_objetivo, nombre )"
                    + " VALUES (" + objetivo.getCodEstablecimiento()+ ", "+objetivo.getCodPlantrabajo()+", " + objetivo.getCodObjetivo()+ ", '" + objetivo.getNombre() + "') "
                    + " ON CONFLICT (codigo_establecimiento, cod_plan_trabajo,cod_objetivo) DO UPDATE "
                    + " SET nombre = EXCLUDED.nombre "
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarObjetivo(PlanTrabajoObjetivo objetivo) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM public.plan_trabajo_objetivo"
                    + " WHERE codigo_establecimiento='"+objetivo.getCodEstablecimiento()+"', cod_plan_trabajo='"+objetivo.getCodPlantrabajo()+"', cod_objetivo='"+objetivo.getCodObjetivo()+"'"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

}

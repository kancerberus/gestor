/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.dao;

import com.gestor.publico.PlanTrabajoPrograma;
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
public class ProgramaDAO {

    private Connection conexion;

    public ProgramaDAO(Connection conexion) {
        this.conexion = conexion;
    }   

    public List<?> cargarListaPrograma(int codEstablecimiento, int codObjetivo, int codPlantrabajo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<PlanTrabajoPrograma> listaPrograma = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_plan_trabajo, cod_programa, cod_objetivo, nombre, peso "                    
                    + " FROM plan_trabajo_programa "    
                    + " WHERE codigo_establecimiento='"+codEstablecimiento+"' AND cod_objetivo = '"+codObjetivo+"' AND cod_plan_trabajo='"+codPlantrabajo+"' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTrabajoPrograma pro = new PlanTrabajoPrograma(rs.getInt("codigo_establecimiento"),rs.getInt("cod_plan_trabajo"),rs.getInt("cod_programa"), rs.getInt("cod_objetivo"), rs.getInt("peso"), rs.getString("nombre"));                
                pro.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                pro.setCodPlantrabajo(rs.getInt("cod_plan_trabajo"));
                pro.setCodPrograma(rs.getInt("cod_programa"));
                pro.setCodObjetivo(rs.getInt("cod_objetivo"));
                pro.setPeso(rs.getInt("peso"));
                pro.setNombre(rs.getString("nombre"));
                listaPrograma.add(pro);                
            }
            return listaPrograma;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaProgramasplanaccion(int codEstablecimiento, int codObjetivo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<PlanTrabajoPrograma> listaPrograma = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_plan_trabajo, cod_programa, cod_objetivo, nombre, peso "                    
                    + " FROM plan_trabajo_programa "    
                    + " WHERE codigo_establecimiento='"+codEstablecimiento+"' AND cod_objetivo = '"+codObjetivo+"' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTrabajoPrograma pro = new PlanTrabajoPrograma(rs.getInt("codigo_establecimiento"),rs.getInt("cod_plan_trabajo"),rs.getInt("cod_programa"), rs.getInt("cod_objetivo"), rs.getInt("peso"), rs.getString("nombre"));                
                pro.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                pro.setCodPlantrabajo(rs.getInt("cod_plan_trabajo"));
                pro.setCodPrograma(rs.getInt("cod_programa"));
                pro.setCodObjetivo(rs.getInt("cod_objetivo"));
                pro.setPeso(rs.getInt("peso"));
                pro.setNombre(rs.getString("nombre"));
                listaPrograma.add(pro);                
            }
            return listaPrograma;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaPrograma(int codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<PlanTrabajoPrograma> listaPrograma = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_plan_trabajo, cod_programa, cod_objetivo, nombre, peso "                    
                    + " FROM public.plan_trabajo_programa "    
                    + " WHERE codigo_establecimiento='"+codEstablecimiento+"' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTrabajoPrograma pro = new PlanTrabajoPrograma(rs.getInt("codigo_establecimiento"),rs.getInt("cod_plan_trabajo"),rs.getInt("cod_programa"), rs.getInt("cod_objetivo"), rs.getInt("peso"), rs.getString("nombre"));                
                pro.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                pro.setCodPlantrabajo(rs.getInt("cod_plan_trabajo"));
                pro.setCodPrograma(rs.getInt("cod_programa"));
                pro.setCodObjetivo(rs.getInt("cod_objetivo"));
                pro.setPeso(rs.getInt("peso"));
                pro.setNombre(rs.getString("nombre"));
                listaPrograma.add(pro);                
            }
            return listaPrograma;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void insertarPrograma(PlanTrabajoPrograma programa) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO plan_trabajo_programa "
                    + " (codigo_establecimiento, cod_plan_trabajo, cod_objetivo, cod_programa, nombre, peso )"
                    + " VALUES ("+programa.getCodEstablecimiento()+","+programa.getCodPlantrabajo()+", " + programa.getCodObjetivo()+ ", " + programa.getCodPrograma()+ ", '" + programa.getNombre()+ "', '"+programa.getPeso()+"') "
                    + " ON CONFLICT (codigo_establecimiento, cod_plan_trabajo, cod_objetivo, cod_programa) DO UPDATE "
                    + " SET nombre = EXCLUDED.nombre , peso=EXCLUDED.peso"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void actualizarPeso(PlanTrabajoPrograma programa) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "UPDATE plan_trabajo_programa " 
                    +"SET peso='"+programa.getPeso()+"' "           
                    +"WHERE codigo_establecimiento="+programa.getCodEstablecimiento()+" AND cod_objetivo="+programa.getCodObjetivo()+" "
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarPrograma(PlanTrabajoPrograma programa) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM public.plan_trabajo_programa"
                    + " WHERE codigo_establecimiento= "+programa.getCodEstablecimiento()+" AND cod_plan_trabajo="+programa.getCodPlantrabajo()+" and cod_objetivo='"+programa.getCodObjetivo()+"' and cod_programa='"+programa.getCodPrograma()+"' "
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

}

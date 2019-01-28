/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.entity.UtilLog;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionPK;
import com.gestor.gestor.EvaluacionPuntajes;
import com.gestor.gestor.EvaluacionPuntajesPK;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.Usuarios;
import com.gestor.publico.UsuariosPK;
import com.gestor.seguimiento.PlanTrabajo;
import com.gestor.seguimiento.PlanTrabajoActividad;
import com.google.gson.JsonObject;
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
public class PlanTrabajoDAO {

    private Connection conexion;

    public PlanTrabajoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<PlanTrabajo> cargarPlanTrabajoList(int codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT pt.codigo_establecimiento,pt.cod_plan_trabajo codpt, pt.peso pesopt, descripcion descpt, "
                    + " estado espt, fecha_venc fvpt, fecha_reg frpt "                    
                    + " FROM seguimiento.plan_trabajo pt "
                    + " WHERE codigo_establecimiento= '"+codEstablecimiento+"'"
                    
            );
            rs = consulta.ejecutar(sql);
            List<PlanTrabajo> planestrabajo = new ArrayList<>();
            while (rs.next()) {
                PlanTrabajo pt= new PlanTrabajo( rs.getInt("codigo_establecimiento"),rs.getInt("codpt"), rs.getString("descpt"),
                        rs.getDate("fvpt"), rs.getInt("pesopt"), rs.getString("espt"), rs.getDate("frpt"));                
                pt.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                pt.setCodPlantrabajo(rs.getInt("codpt"));                
                pt.setDescripcion(rs.getString("descpt"));
                pt.setFechaVenc(rs.getDate("fvpt"));
                pt.setPeso(rs.getInt("pesopt"));
                pt.setEstado(rs.getString("espt"));
                pt.setFechaRegistro(rs.getDate("frpt"));
                planestrabajo.add(pt);
            }
            return planestrabajo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<PlanTrabajoActividad> cargarPlanTrabajoactividadList(PlanTrabajo plantrabajo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT pta.cod_plan_trabajo, pta.cod_actividad, pta.actividad, pta.fecha_reg, pta.fecha_venc, pta.estado, "
                        + " e.codigo_establecimiento code, e.nombre nome, "
                        + " obj.codigo_establecimiento codeobj, obj.cod_plan_trabajo codptobj, obj.cod_objetivo codobj, obj.nombre nomobj,  "    
                        + " pr.cod_programa codpr, pr.nombre nompr "
                        + " FROM seguimiento.plan_trabajo_actividad pta"                        
                        + " JOIN public.plan_trabajo_objetivo obj USING (cod_objetivo)"
                        + " JOIN public.plan_trabajo_programa pr USING (cod_programa)"
                        + " INNER JOIN public.establecimiento e on (e.codigo_establecimiento=pta.codigo_establecimiento)"                                                   
                        + " WHERE pta.codigo_establecimiento='"+plantrabajo.getCodEstablecimiento()+"' AND pta.cod_plan_trabajo='"+plantrabajo.getCodPlantrabajo()+"' "
                        + " ORDER BY pta.cod_actividad"                        
                    
                    
            );
            rs = consulta.ejecutar(sql);
            List<PlanTrabajoActividad> planestrabajoactividad = new ArrayList<>();
            while (rs.next()) {
                PlanTrabajoActividad pta= new PlanTrabajoActividad(rs.getInt("code"), rs.getInt("cod_plan_trabajo"), rs.getInt("codobj"),
                        rs.getInt("codpr"), rs.getInt("cod_actividad"), rs.getString("actividad"), rs.getDate("fecha_venc"), rs.getString("estado"), rs.getDate("fecha_reg"));
                pta.setCodEstablecimiento(rs.getInt("code"));
                pta.setCodPlantrabajo(rs.getInt("cod_plan_trabajo"));
                pta.setCodObjetivo(rs.getInt("codobj"));
                pta.setCodPrograma(rs.getInt("codpr"));
                pta.setCodActividad(rs.getInt("cod_actividad"));
                pta.setActividad(rs.getString("actividad"));
                pta.setFechaRegistro(rs.getDate("fecha_reg"));
                pta.setEstado(rs.getString("estado"));
                pta.setFechaVenc(rs.getDate("fecha_venc"));
                pta.setEstablecimiento(new Establecimiento(rs.getInt("code"), rs.getString("nome")));
                pta.setObjetivo(new PlanTrabajoObjetivo(rs.getInt("codeobj"), rs.getInt("codptobj"), rs.getInt("codobj"), rs.getString("nomobj")));
                pta.setPrograma(new PlanTrabajoPrograma(0, 0, 0, rs.getInt("codpr"), 0, rs.getString("nompr")));
                planestrabajoactividad.add(pta);                
            }
            return planestrabajoactividad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public List<Evaluacion> cargarEvaluacionList(Integer codigoEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_evaluacion, codigo_establecimiento, documento_usuario, fecha,"
                    + " fecha_registro, estado, resumenes, calificacion, peso, fecha_resumen,"
                    + " U.documento_usuario, U.nombre, U.apellido, E.codigo_establecimiento, E.nombre AS nombre_establecimiento"
                    + " FROM gestor.evaluacion"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " JOIN public.establecimiento E USING (codigo_establecimiento)"
                    + " WHERE codigo_establecimiento=" + codigoEstablecimiento
            );
            rs = consulta.ejecutar(sql);
            List<Evaluacion> evaluacions = new ArrayList<>();
            while (rs.next()) {
                Evaluacion e = new Evaluacion(new EvaluacionPK(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento")), rs.getString("documento_usuario"),
                        rs.getDate("fecha"), rs.getDate("fecha_registro"), rs.getString("estado"));
                e.setUsuarios(new Usuarios(new UsuariosPK(rs.getString("documento_usuario")), rs.getString("nombre"), rs.getString("apellido")));
                e.setEstablecimiento(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("nombre_establecimiento")));
                e.setResumenes(rs.getString("resumenes"));
                e.setCalificacion(rs.getDouble("calificacion"));
                e.setPeso(rs.getDouble("peso"));
                e.setFechaResumen(rs.getDate("fecha_resumen"));
                evaluacions.add(e);
            }
            return evaluacions;

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void insertarPlantrabajo(PlanTrabajo plantrabajo) throws SQLException {
        Consulta consulta = null;           
        
        try {                                      
        
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_trabajo "
                    + " ( codigo_establecimiento, cod_plan_trabajo, descripcion, fecha_venc, peso, estado, fecha_reg )"
                    + " VALUES ( "+plantrabajo.getCodEstablecimiento()+", '"+plantrabajo.getCodPlantrabajo()+"', '"+plantrabajo.getDescripcion()+"', '"+plantrabajo.getFechaVenc()+"', '"+plantrabajo.getPeso()+"', "
                    + " '"+plantrabajo.getEstado()+"', NOW() ) "
                    + " ON CONFLICT ( codigo_establecimiento, cod_plan_trabajo  ) DO UPDATE "
                    + " SET descripcion=EXCLUDED.descripcion, fecha_venc=EXCLUDED.fecha_venc, peso=EXCLUDED.peso, estado=EXCLUDED.estado "
                    
            );
            consulta.actualizar(sql);            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarPlantrabajoactividad(PlanTrabajoActividad plantrabajoactividad) throws SQLException {
        Consulta consulta = null;           
        
        try {                                      
        
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_trabajo_actividad "
                    + " ( codigo_establecimiento, cod_plan_trabajo, cod_objetivo, cod_programa, cod_actividad, actividad, fecha_venc, estado, fecha_reg )"
                    + " VALUES ( "+plantrabajoactividad.getCodEstablecimiento()+", '"+plantrabajoactividad.getCodPlantrabajo()+"', '"+plantrabajoactividad.getCodObjetivo()+"', '"+plantrabajoactividad.getCodPrograma()+"',"
                    + " '"+plantrabajoactividad.getCodActividad()+"', '"+plantrabajoactividad.getActividad()+"', '"+plantrabajoactividad.getFechaVenc()+"', '"+plantrabajoactividad.getEstado()+"', NOW() ) "
                    + " ON CONFLICT ( codigo_establecimiento, cod_plan_trabajo, cod_actividad ) DO UPDATE "
                    + " SET cod_plan_trabajo=EXCLUDED.cod_plan_trabajo, cod_objetivo=EXCLUDED.cod_objetivo, cod_programa=EXCLUDED.cod_programa, actividad=EXCLUDED.actividad, fecha_venc=EXCLUDED.fecha_venc, estado=EXCLUDED.estado"                    
            );
            consulta.actualizar(sql);            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

}

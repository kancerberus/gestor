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
import com.gestor.publico.ListaDetalle;
import com.gestor.publico.ListaDetallePK;
import com.gestor.publico.PlanTrabajoMeta;
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
                    "SELECT pta.cod_plan_trabajo , pta.cod_actividad , pta.actividad , pta.fecha_reg, pta.fecha_venc, pta.estado, pta.peso,  e.codigo_establecimiento code, "
                        + "e.nombre nome, obj.cod_objetivo codobj, obj.nombre nomobj, pr.cod_programa codpr, pr.nombre nompr "                        
                        + " FROM seguimiento.plan_trabajo_actividad pta "                        
                        + " INNER JOIN public.plan_trabajo_objetivo obj on (obj.cod_objetivo=pta.cod_objetivo)   "
                        + " INNER JOIN public.plan_trabajo_programa pr on (pr.cod_programa=pta.cod_programa)  "
                        + " INNER JOIN public.establecimiento e on (e.codigo_establecimiento=pta.codigo_establecimiento)   "
                        + " WHERE pta.codigo_establecimiento='"+plantrabajo.getCodEstablecimiento()+"' AND pta.cod_plan_trabajo='"+plantrabajo.getCodPlantrabajo()+"' "
                        + " GROUP BY pta.cod_plan_trabajo, pta.cod_actividad, pta.actividad, pta.fecha_reg, pta.fecha_venc, pta.estado, pta.peso,  e.codigo_establecimiento, " 
                        + " e.nombre, obj.cod_objetivo, obj.nombre, pr.cod_programa, pr.nombre"
                        + " ORDER BY pta.cod_actividad"
                    
            );
            rs = consulta.ejecutar(sql);
            List<PlanTrabajoActividad> planestrabajoactividad = new ArrayList<>();
            while (rs.next()) {
                PlanTrabajoActividad pta= new PlanTrabajoActividad(rs.getInt("code"), rs.getInt("cod_plan_trabajo"), rs.getInt("codobj"),
                        rs.getInt("codpr"), rs.getInt("cod_actividad"), rs.getString("actividad"), rs.getDate("fecha_venc"), rs.getString("estado"), rs.getDate("fecha_reg"), rs.getInt("peso"));
                pta.setCodEstablecimiento(rs.getInt("code"));
                pta.setCodPlantrabajo(rs.getInt("cod_plan_trabajo"));
                pta.setCodObjetivo(rs.getInt("codobj"));
                pta.setCodPrograma(rs.getInt("codpr"));
                pta.setCodActividad(rs.getInt("cod_actividad"));
                pta.setActividad(rs.getString("actividad"));
                pta.setFechaRegistro(rs.getDate("fecha_reg"));
                pta.setEstado(rs.getString("estado"));
                pta.setFechaVenc(rs.getDate("fecha_venc"));
                pta.setPeso(rs.getInt("peso"));
                pta.setEstablecimiento(new Establecimiento(rs.getInt("code"), rs.getString("nome")));
                pta.setObjetivo(new PlanTrabajoObjetivo(rs.getInt("code"), rs.getInt("cod_plan_trabajo"), rs.getInt("codobj"), rs.getString("nomobj")));
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
    
    public List<PlanTrabajoActividad> cargarPlanTrabajoactividadmetaList(PlanTrabajoPrograma programa) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_plan_trabajo, cod_actividad, estado "
                        + " FROM seguimiento.plan_trabajo_actividad "                         
                        + " WHERE codigo_establecimiento='"+programa.getCodEstablecimiento()+"' AND cod_plan_trabajo='"+programa.getCodPlantrabajo()+"' "
                        + " and cod_objetivo='"+programa.getCodObjetivo()+"' AND cod_programa='"+programa.getCodPrograma()+"'"
                        + " ORDER BY cod_actividad"
                    
            );
            rs = consulta.ejecutar(sql);
            List<PlanTrabajoActividad> planestrabajoactividadmeta = new ArrayList<>();
            while (rs.next()) {
                PlanTrabajoActividad ptam= new PlanTrabajoActividad(rs.getInt("codigo_establecimiento"), rs.getInt("cod_plan_trabajo"), rs.getInt("cod_actividad"),
                        0, 0, "", null, rs.getString("estado"), null, 0);
                ptam.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                ptam.setCodPlantrabajo(rs.getInt("cod_plan_trabajo"));
                ptam.setCodActividad(rs.getInt("cod_actividad"));
                planestrabajoactividadmeta.add(ptam);                
            }
            return planestrabajoactividadmeta;
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
                    + " ( codigo_establecimiento, cod_plan_trabajo, cod_objetivo, cod_programa, cod_actividad, actividad, fecha_venc, estado, fecha_reg, peso )"
                    + " VALUES ( "+plantrabajoactividad.getCodEstablecimiento()+", '"+plantrabajoactividad.getCodPlantrabajo()+"', '"+plantrabajoactividad.getCodObjetivo()+"', '"+plantrabajoactividad.getCodPrograma()+"',"
                    + " '"+plantrabajoactividad.getCodActividad()+"', '"+plantrabajoactividad.getActividad()+"', '"+plantrabajoactividad.getFechaVenc()+"', '"+plantrabajoactividad.getEstado()+"', NOW(), "+plantrabajoactividad.getPeso()+" ) "
                    + " ON CONFLICT ( codigo_establecimiento, cod_plan_trabajo, cod_actividad ) DO UPDATE "
                    + " SET cod_objetivo=EXCLUDED.cod_objetivo, cod_programa=EXCLUDED.cod_programa, actividad=EXCLUDED.actividad, estado=EXCLUDED.estado"                    
            );
            consulta.actualizar(sql);            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    public void insertarMeta(PlanTrabajoMeta meta) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO plan_trabajo_meta("
                    + " codigo_establecimiento, cod_plan_trabajo, cod_meta, cod_detalle, meta)"                    
                    + " VALUES (" + meta.getCodigoEstablecimiento() + ", "+meta.getCodPlantrabajo()+" , " + meta.getCodMeta() + " ,"
                    + "  " + meta.getCodDetalle() + ", " + meta.getMeta() + ")"                    
                    + " ON CONFLICT (codigo_establecimiento, cod_plan_trabajo, cod_meta, cod_detalle) DO UPDATE"
                    + " SET cod_detalle=EXCLUDED.cod_detalle, meta=EXCLUDED.meta"                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaMetas(Integer codEstablecimiento, Integer codPlantrabajo) throws SQLException {        
        ResultSet rs = null;
        
        Consulta consulta = null;        
        List<PlanTrabajoMeta> listaMetaestablecimiento = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_plan_trabajo, cod_meta, cod_lista, cod_detalle, meta , ld.nombre"                    
                    + " FROM plan_trabajo_meta "
                    + " JOIN public.lista_detalle ld USING (cod_detalle)"                    
                    + " WHERE codigo_establecimiento="+codEstablecimiento+" and cod_plan_trabajo="+codPlantrabajo+""
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTrabajoMeta me= new PlanTrabajoMeta(rs.getInt("codigo_establecimiento"), rs.getInt("cod_plan_trabajo"), rs.getInt("cod_meta"), rs.getInt("cod_detalle"), rs.getInt("meta"));
                me.setListaDetalle(new ListaDetalle(new ListaDetallePK(rs.getInt("cod_lista"), rs.getInt("cod_detalle")), rs.getString("nombre"), null, null));
                me.setCodigoEstablecimiento(rs.getInt("codigo_establecimiento"));
                me.setCodMeta(rs.getInt("cod_meta"));
                me.setCodDetalle(rs.getInt("cod_detalle"));
                me.setMeta(rs.getInt("meta"));
                listaMetaestablecimiento.add(me);             
            }
            
            return listaMetaestablecimiento;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void actualizarPlantrabajoactividadmeta(PlanTrabajoActividad actividad) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "UPDATE seguimiento.plan_trabajo_actividad " 
                    +" SET peso = '"+actividad.getPeso()+"' "           
                    +" WHERE codigo_establecimiento = "+actividad.getCodEstablecimiento()+" AND cod_plan_trabajo = "+actividad.getCodPlantrabajo()+""
                    +" AND cod_objetivo = "+actividad.getCodObjetivo()+" AND cod_programa = "+actividad.getCodPrograma()+" "
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

}

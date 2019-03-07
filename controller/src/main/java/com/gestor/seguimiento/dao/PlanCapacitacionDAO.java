/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.entity.UtilLog;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionPK;
import com.gestor.gestor.EvaluacionPlanAccionDetalleNotasPK;
import com.gestor.gestor.EvaluacionPuntajes;
import com.gestor.gestor.EvaluacionPuntajesPK;
import com.gestor.gestor.FuenteHallazgo;
import com.gestor.gestor.Recursos;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.ListaDetalle;
import com.gestor.publico.ListaDetallePK;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.Responsable;
import com.gestor.publico.Usuarios;
import com.gestor.publico.UsuariosPK;
import com.gestor.seguimiento.PlanCapacitacion;
import com.gestor.seguimiento.PlanTrabajo;
import com.gestor.seguimiento.PlanTrabajoActividad;
import com.gestor.seguimiento.PlanTrabajoActividadNota;
import com.google.gson.JsonObject;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author juliano
 */
public class PlanCapacitacionDAO {

    private Connection conexion;

    public PlanCapacitacionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<PlanCapacitacion> cargarPlanCapacitacionList(int codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT pc.codigo_establecimiento,pc.cod_plan_capacitacion codpc, descripcion descpc, meta metapc,"
                    + " estado espc, fecha_venc fvpc, fecha_reg frpc "                    
                    + " FROM seguimiento.plan_capacitacion pc "
                    + " WHERE codigo_establecimiento= '"+codEstablecimiento+"'"
                    + " ORDER BY pc.cod_plan_capacitacion"
                    
            );
            rs = consulta.ejecutar(sql);
            List<PlanCapacitacion> planescapacitacion = new ArrayList<>();
            while (rs.next()) {
                PlanCapacitacion pc= new PlanCapacitacion( rs.getInt("codigo_establecimiento"),rs.getInt("codpc"), rs.getString("descpc"),
                        rs.getDate("fvpc"), rs.getString("espc"), rs.getDate("frpc"), rs.getInt("metapc"));                
                pc.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                pc.setCodPlancapacitacion(rs.getInt("codpc"));                
                pc.setDescripcion(rs.getString("descpc"));
                pc.setFechaVenc(rs.getDate("fvpc"));                                        
                pc.setEstado(rs.getString("espc"));
                pc.setFechaRegistro(rs.getDate("frpc"));
                pc.setMeta(rs.getInt("metapc"));
                planescapacitacion.add(pc);
            }
            return planescapacitacion;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<PlanTrabajo> cargarPlanTrabajoAbirtosList(int codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT pt.codigo_establecimiento,pt.cod_plan_trabajo codpt, pt.meta metapt, descripcion descpt, "
                    + " estado espt, fecha_venc fvpt, fecha_reg frpt "                    
                    + " FROM seguimiento.plan_trabajo pt "
                    + " WHERE codigo_establecimiento= '"+codEstablecimiento+"' AND estado='A'"
                    + " ORDER BY pt.cod_plan_trabajo"
                    
            );
            rs = consulta.ejecutar(sql);
            List<PlanTrabajo> planestrabajo = new ArrayList<>();
            while (rs.next()) {
                PlanTrabajo pt= new PlanTrabajo( rs.getInt("codigo_establecimiento"),rs.getInt("codpt"), rs.getString("descpt"),
                        rs.getDate("fvpt"), rs.getInt("metapt"), rs.getString("espt"), rs.getDate("frpt"));                
                pt.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                pt.setCodPlantrabajo(rs.getInt("codpt"));                
                pt.setDescripcion(rs.getString("descpt"));
                pt.setFechaVenc(rs.getDate("fvpt"));
                pt.setMeta(rs.getInt("metapt"));                              
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
    
    
    public List<PlanCapacitacion> cargarPlanCapacitacionAbirtosList(int codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT pc.codigo_establecimiento,pc.cod_plan_capacitacion codpc, pc.meta metapc, descripcion descpc, "
                    + " estado espc, fecha_venc fvpc, fecha_reg frpc "                    
                    + " FROM seguimiento.plan_capacitacion pc "
                    + " WHERE codigo_establecimiento= '"+codEstablecimiento+"' AND estado='A'"
                    + " ORDER BY pc.cod_plan_capacitacion"
                    
            );
            rs = consulta.ejecutar(sql);
            List<PlanCapacitacion> planescapacitacion = new ArrayList<>();
            while (rs.next()) {
                PlanCapacitacion pc= new PlanCapacitacion(rs.getInt("codigo_establecimiento"),rs.getInt("codpc"), rs.getString("descpc"),
                        rs.getDate("fvpc"), rs.getString("espc"), rs.getDate("frpc"), rs.getInt("metapc"));                
                pc.setCodEstablecimiento(rs.getInt("codigo_establecimiento"));
                pc.setCodPlancapacitacion(rs.getInt("codpc"));                
                pc.setDescripcion(rs.getString("descpc"));
                pc.setFechaVenc(rs.getDate("fvpc"));
                pc.setMeta(rs.getInt("metapc"));                              
                pc.setEstado(rs.getString("espc"));
                pc.setFechaRegistro(rs.getDate("frpc"));
                planescapacitacion.add(pc);
            }
            return planescapacitacion;
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

    public void insertarPlancapacitacion(PlanCapacitacion plancapacitacion) throws SQLException {
        Consulta consulta = null;    
        try {                                      
        
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_capacitacion "
                    + " ( codigo_establecimiento, cod_plan_capacitacion, descripcion, fecha_venc,estado, fecha_reg, meta )"
                    + " VALUES ( "+plancapacitacion.getCodEstablecimiento()+", '"+plancapacitacion.getCodPlancapacitacion()+"', '"+plancapacitacion.getDescripcion()+"', '"+plancapacitacion.getFechaVenc()+"', "
                    + " '"+plancapacitacion.getEstado()+"', NOW(), '"+plancapacitacion.getMeta()+"' ) "
                    + " ON CONFLICT ( codigo_establecimiento, cod_plan_capacitacion  ) DO UPDATE "
                    + " SET descripcion=EXCLUDED.descripcion, fecha_venc=EXCLUDED.fecha_venc, estado=EXCLUDED.estado , meta=EXCLUDED.meta"
                    
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
                    + " ( codigo_establecimiento, cod_plan_trabajo, cod_actividad, cod_objetivo, cod_programa, cod_fuente_hallazgo, cedula, cod_recursos, actividad, fecha_venc, estado, fecha_reg )"
                    + " VALUES ( "+plantrabajoactividad.getCodEstablecimiento()+", '"+plantrabajoactividad.getCodPlantrabajo()+"', '"+plantrabajoactividad.getCodActividad()+"', '"+plantrabajoactividad.getCodObjetivo()+"', '"+plantrabajoactividad.getCodPrograma()+"',"
                    + " '"+plantrabajoactividad.getCodFuentehallazgo()+"', '"+plantrabajoactividad.getCedula()+"', '"+plantrabajoactividad.getCodRecursos()+"', '"+plantrabajoactividad.getDescripcion()+"', '"+plantrabajoactividad.getFechaVenc()+"', '"+plantrabajoactividad.getEstado()+"', NOW() ) "
                    + " ON CONFLICT ( codigo_establecimiento, cod_plan_trabajo, cod_actividad ) DO UPDATE "
                    + " SET cod_objetivo=EXCLUDED.cod_objetivo, cod_programa=EXCLUDED.cod_programa, cod_fuente_hallazgo=EXCLUDED.cod_fuente_hallazgo, cedula= EXCLUDED.cedula, cod_recursos=EXCLUDED.cod_recursos, actividad=EXCLUDED.actividad, estado=EXCLUDED.estado"                    
            );
            consulta.actualizar(sql);            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }   
    
    public Integer valorMeta(Integer codEstablecimiento, Integer codPlantrabajo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT meta "
                    + " FROM public.plan_trabajo_meta "
                    + " WHERE codigo_establecimiento='"+codEstablecimiento+"' cod_plan_trabajo='"+codPlantrabajo+"' AND cod_detalle=3 "
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getInt("meta");                
            }
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    
    
    
    
    public List<PlanTrabajoActividadNota> cargarEvaluacionPlanTrabajoActividadNotasList(PlanTrabajoActividad pta) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_plan_trabajo, cod_objetivo, cod_programa, cod_actividad, "
                    + " cod_nota, estado, nombre, descripcion, fecha_registro "
                    + " FROM seguimiento.plan_trabajo_actividad_nota "   
                    + " WHERE codigo_establecimiento=" + pta.getCodEstablecimiento()+" AND cod_plan_trabajo=" +pta.getCodPlantrabajo()+" "
                    + " AND cod_objetivo=" + pta.getCodObjetivo() + " AND cod_programa=" + pta.getCodPrograma()+" AND cod_actividad="+pta.getCodActividad()+""
                    + " ORDER BY cod_nota"
                    
            );
            rs = consulta.ejecutar(sql);
            List<PlanTrabajoActividadNota> plantrabajoactividadNotaList = new ArrayList<>();

            while (rs.next()) {                
                PlanTrabajoActividadNota ptan= new PlanTrabajoActividadNota(pta.getCodEstablecimiento(), pta.getCodPlantrabajo(), pta.getCodActividad(),
                    pta.getCodObjetivo(), pta.getCodPrograma(), rs.getInt("cod_nota"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("estado"),
                    rs.getDate("fecha_registro"));                
                
                plantrabajoactividadNotaList.add(ptan);
            }
            return plantrabajoactividadNotaList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void upsertPlanTrabajoActividadNota(PlanTrabajoActividadNota ptan) throws SQLException {
        Consulta consulta = null;
        try {
            if(ptan.getCodNota()==0){
            consulta = new Consulta(this.conexion);            
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_trabajo_actividad_nota("
                    + " codigo_establecimiento, cod_plan_trabajo, cod_actividad, cod_objetivo,  cod_programa, "
                    + " cod_nota, estado, nombre, descripcion, fecha_registro)"     
                    + " VALUES (" + ptan.getCodEstablecimiento() + ", " + ptan.getCodPlantrabajo() + ", " + ptan.getCodActividad() + ", " + ptan.getCodObjetivo()+",  "
                    + " "+ptan.getCodPrograma()+", DEFAULT, '" + ptan.getEstado() + "', '" + ptan.getNombre() + "', '" + ptan.getDescripcion() + "', NOW())"
                    
            );
            consulta.actualizar(sql);
            }if(ptan.getCodNota() != 0){
                consulta = new Consulta(this.conexion);            
                StringBuilder sql = new StringBuilder(
                    " UPDATE seguimiento.plan_trabajo_actividad_nota "
                        + "SET estado='"+ptan.getEstado()+"', descripcion='"+ptan.getDescripcion()+"', nombre='"+ptan.getNombre()+"', "
                        + "fecha_registro='"+ptan.getFechaRegistro()+"'"
                        +"WHERE codigo_establecimiento="+ptan.getCodEstablecimiento()+" AND cod_plan_trabajo="+ptan.getCodPlantrabajo()+" "
                        + "AND cod_actividad="+ptan.getCodActividad()+" AND cod_objetivo="+ptan.getCodObjetivo()+"AND cod_programa="+ptan.getCodPrograma()+" "
                        + "AND cod_nota="+ptan.getCodNota()+""
                        
            );
            consulta.actualizar(sql);
            }
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void insertaEvaluacionPlanTrabajoActividadNota(PlanTrabajoActividadNota ptan) throws SQLException {        
        Consulta consulta = null;
        try {            
            
            consulta = new Consulta(this.conexion);            
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_trabajo_actividad_nota("
                    + " codigo_establecimiento, cod_plan_trabajo, cod_actividad, "
                    + " cod_objetivo, cod_programa, cod_nota, nombre, descripcion, estado, fecha_registro)"
                    + " VALUES (" + ptan.getCodEstablecimiento()+ ", " + ptan.getCodPlantrabajo() + ", " + ptan.getCodActividad() + ", " + ptan.getCodObjetivo()+", "+ptan.getCodPrograma()+", "
                    + " DEFAULT, '" + ptan.getNombre() + "', '" + ptan.getDescripcion() + "', '" + ptan.getEstado() + "', NOW());"
            );
            consulta.actualizar(sql);            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    
    }

}

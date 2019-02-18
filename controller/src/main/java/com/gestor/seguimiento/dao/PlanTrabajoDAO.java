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
import com.gestor.seguimiento.PlanTrabajo;
import com.gestor.seguimiento.PlanTrabajoActividad;
import com.gestor.seguimiento.PlanTrabajoActividadNota;
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
                    "SELECT pt.codigo_establecimiento,pt.cod_plan_trabajo codpt, pt.meta metapt, descripcion descpt, "
                    + " estado espt, fecha_venc fvpt, fecha_reg frpt "                    
                    + " FROM seguimiento.plan_trabajo pt "
                    + " WHERE codigo_establecimiento= '"+codEstablecimiento+"'"
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
    
    public List<PlanTrabajoActividad> cargarPlanTrabajoactividadList(PlanTrabajo plantrabajo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT pta.cod_plan_trabajo , pta.cod_actividad, pta.actividad , pta.fecha_reg, pta.fecha_venc, pta.estado, e.codigo_establecimiento code, "
                        + "e.nombre nome, obj.cod_objetivo codobj, obj.nombre nomobj, pr.cod_programa codpr, pr.nombre nompr, "                        
                        + " rec.cod_recursos cod_rec, rec.nombre nom_rec,"
                        + " fh.cod_fuente_hallazgo cod_fh, fh.nombre nom_fh,"
                        + " res.cedula cedula, res.nombres nom_resp"
                        + " FROM seguimiento.plan_trabajo_actividad pta "                        
                        + " INNER JOIN gestor.fuente_hallazgo fh USING (cod_fuente_hallazgo)"
                        + " INNER JOIN gestor.recursos rec USING (cod_recursos)"
                        + " INNER JOIN public.responsable res USING (codigo_establecimiento, cedula)"
                        + " INNER JOIN public.plan_trabajo_objetivo obj USING (codigo_establecimiento, cod_objetivo, cod_plan_trabajo)"
                        + " INNER JOIN public.plan_trabajo_programa pr using (codigo_establecimiento, cod_plan_trabajo, cod_objetivo, cod_programa)  "
                        + " INNER JOIN public.establecimiento e using (codigo_establecimiento)    "
                        + " WHERE pta.codigo_establecimiento='"+plantrabajo.getCodEstablecimiento()+"' AND pta.cod_plan_trabajo='"+plantrabajo.getCodPlantrabajo()+"' "
                        + " GROUP BY pta.cod_plan_trabajo, pta.cod_actividad, pta.actividad, pta.fecha_reg, pta.fecha_venc, pta.estado, pta.peso,  e.codigo_establecimiento, " 
                        + " e.nombre, obj.cod_objetivo, obj.nombre, pr.cod_programa, pr.nombre, rec.cod_recursos, res.cedula, fh.cod_fuente_hallazgo"
                        + " ORDER BY pta.cod_actividad"
                    
            );
            rs = consulta.ejecutar(sql);
            List<PlanTrabajoActividad> planestrabajoactividad = new ArrayList<>();
            while (rs.next()) {
                PlanTrabajoActividad pta= new PlanTrabajoActividad(rs.getInt("code"), rs.getInt("cod_plan_trabajo"), rs.getInt("cod_actividad"),rs.getInt("codobj"),
                        rs.getInt("codpr"), rs.getInt("cod_fh"),rs.getString("cedula"), rs.getInt("cod_rec"), rs.getString("actividad"), rs.getDate("fecha_venc"), rs.getString("estado"), rs.getDate("fecha_reg"));
                pta.setCodEstablecimiento(rs.getInt("code"));
                pta.setCodPlantrabajo(rs.getInt("cod_plan_trabajo"));
                pta.setCodObjetivo(rs.getInt("codobj"));                
                pta.setCodPrograma(rs.getInt("codpr"));                
                pta.setDescripcion(rs.getString("actividad"));
                pta.setFechaRegistro(rs.getDate("fecha_reg"));
                pta.setEstado(rs.getString("estado"));
                pta.setFechaVenc(rs.getDate("fecha_venc"));                
                pta.setEstablecimiento(new Establecimiento(rs.getInt("code"), rs.getString("nome")));
                pta.setFuenteHallazgo(new FuenteHallazgo(rs.getInt("cod_fh"), rs.getString("nom_fh")));
                pta.setRecursos(new Recursos(rs.getInt("cod_rec"), rs.getString("nom_rec")));
                pta.setResponsable(new Responsable(rs.getString("cedula"), rs.getString("nom_resp")));
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
                    + " ( codigo_establecimiento, cod_plan_trabajo, descripcion, fecha_venc, meta, estado, fecha_reg )"
                    + " VALUES ( "+plantrabajo.getCodEstablecimiento()+", '"+plantrabajo.getCodPlantrabajo()+"', '"+plantrabajo.getDescripcion()+"', '"+plantrabajo.getFechaVenc()+"', '"+plantrabajo.getMeta()+"', "
                    + " '"+plantrabajo.getEstado()+"', NOW() ) "
                    + " ON CONFLICT ( codigo_establecimiento, cod_plan_trabajo  ) DO UPDATE "
                    + " SET descripcion=EXCLUDED.descripcion, fecha_venc=EXCLUDED.fecha_venc, meta=EXCLUDED.meta, estado=EXCLUDED.estado "
                    
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

    public Collection<? extends PlanTrabajoActividad> cargarListaPlanTrabajo(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT pta.codigo_establecimiento code, pta.actividad act, pta.cod_actividad cod_act, pta.fecha_reg fr, pta.fecha_venc fv, pta.estado edo"+
                    ", pt.cod_plan_trabajo codpt, pt.descripcion descpt, " +
                    "obj.cod_objetivo codobj, obj.nombre nomobj," +
                    "pr.cod_programa codpr, pr.nombre nompr, " +
                    "rec.cod_recursos codrec, rec.nombre nomrec, "+
                    "res.cedula ced, res.nombres noms, res.apellidos apes, "+
                    "fh.cod_fuente_hallazgo codfh, fh.nombre nomfh "+                            
                    "FROM seguimiento.plan_trabajo_actividad pta " +
                    "JOIN seguimiento.plan_trabajo pt USING (cod_plan_trabajo, codigo_establecimiento) " +
                    "JOIN public.responsable res USING (cedula) " +
                    "JOIN gestor.recursos rec USING (cod_recursos) " +
                    "JOIN gestor.fuente_hallazgo fh USING (cod_fuente_hallazgo) " +
                    "JOIN public.plan_trabajo_programa pr USING (cod_programa, cod_objetivo, cod_plan_trabajo) " +
                    "JOIN public.plan_trabajo_objetivo obj USING (cod_objetivo, cod_plan_trabajo) " +
                    condicion+
                    "GROUP BY pta.codigo_establecimiento, pta.actividad, pta.cod_actividad, pt.cod_plan_trabajo, pta.fecha_reg, pta.fecha_venc, pta.estado, pt.descripcion, " +
                    "obj.cod_objetivo, obj.nombre, pr.nombre, pr.cod_programa, res.cedula, res.nombres, res.apellidos, fh.cod_fuente_hallazgo, fh.nombre, "+
                    "rec.cod_recursos, rec.nombre "+
                    "ORDER BY pt.cod_plan_trabajo"
            );
            System.out.println("condicion => " + condicion);
            System.out.println("sql => " + sql);
            rs = consulta.ejecutar(sql);
            Collection<PlanTrabajoActividad> planesTrabajoactividad = new ArrayList<PlanTrabajoActividad>();            
            
            while (rs.next()) {
               PlanTrabajoActividad pta = new PlanTrabajoActividad(rs.getInt("code"), rs.getInt("codpt"), rs.getInt("cod_act"),
                    rs.getInt("codobj"), rs.getInt("codpr"), rs.getInt("codfh"), rs.getString("ced"), rs.getInt("codrec"),
                    rs.getString("act"), rs.getDate("fv"), rs.getString("edo"), rs.getDate("fr"));
               pta.setPlantrabajo(new PlanTrabajo(0, rs.getInt("codpt"), rs.getString("descpt"), null, 0, "", null));
               pta.setObjetivo(new PlanTrabajoObjetivo(0, 0, rs.getInt("codobj"), rs.getString("nomobj")));
               pta.setPrograma(new PlanTrabajoPrograma(0, 0, 0, rs.getInt("codpr"), 0, rs.getString("nompr")));
               pta.setRecursos(new Recursos(rs.getInt("codrec"), rs.getString("nomrec")));
               pta.setResponsable(new Responsable(rs.getString("ced"), rs.getString("noms"), rs.getString("apes"), "", ""));
               pta.setFuenteHallazgo(new FuenteHallazgo(rs.getInt("codfh"), rs.getString("nomfh")));
               planesTrabajoactividad.add(pta);
            }            
            return planesTrabajoactividad;
            
            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends PlanTrabajoActividad> cargarListaPlanTrabajopt(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT pta.codigo_establecimiento code, pta.actividad act, pta.cod_actividad cod_act, pta.fecha_reg fr, pta.fecha_venc fv, pta.estado edo"+
                    ", pt.cod_plan_trabajo codpt, pt.descripcion descpt, " +
                    "obj.cod_objetivo codobj, obj.nombre nomobj," +
                    "pr.cod_programa codpr, pr.nombre nompr, pr.peso pesopr, " +
                    "rec.cod_recursos codrec, rec.nombre nomrec, "+
                    "res.cedula ced, res.nombres noms, res.apellidos apes, "+
                    "fh.cod_fuente_hallazgo codfh, fh.nombre nomfh "+                            
                    "FROM seguimiento.plan_trabajo_actividad pta " +
                    "JOIN seguimiento.plan_trabajo pt USING (cod_plan_trabajo, codigo_establecimiento) " +
                    "JOIN public.responsable res USING (cedula) " +
                    "JOIN gestor.recursos rec USING (cod_recursos) " +
                    "JOIN gestor.fuente_hallazgo fh USING (cod_fuente_hallazgo) " +
                    "JOIN public.plan_trabajo_programa pr USING (cod_programa, cod_objetivo, cod_plan_trabajo) " +
                    "JOIN public.plan_trabajo_objetivo obj USING (cod_objetivo, cod_plan_trabajo) " +
                    condicion+
                    "GROUP BY pta.codigo_establecimiento, pta.actividad, pta.cod_actividad, pt.cod_plan_trabajo, pta.fecha_reg, pta.fecha_venc, pta.estado, pt.descripcion, " +
                    "obj.cod_objetivo, obj.nombre, pr.nombre, pr.cod_programa, res.cedula, res.nombres, res.apellidos, fh.cod_fuente_hallazgo, fh.nombre, "+
                    "rec.cod_recursos, rec.nombre, pr.peso "+
                    "ORDER BY pt.cod_plan_trabajo"
            );
            System.out.println("condicion => " + condicion);
            System.out.println("sql => " + sql);
            rs = consulta.ejecutar(sql);
            Collection<PlanTrabajoActividad> planesTrabajoactividad = new ArrayList<PlanTrabajoActividad>();            
            
            while (rs.next()) {
               PlanTrabajoActividad pta = new PlanTrabajoActividad(rs.getInt("code"), rs.getInt("codpt"), rs.getInt("cod_act"),
                    rs.getInt("codobj"), rs.getInt("codpr"), rs.getInt("codfh"), rs.getString("ced"), rs.getInt("codrec"),
                    rs.getString("act"), rs.getDate("fv"), rs.getString("edo"), rs.getDate("fr"));
               pta.setPlantrabajo(new PlanTrabajo(0, rs.getInt("codpt"), rs.getString("descpt"), null, 0, "", null));
               pta.setObjetivo(new PlanTrabajoObjetivo(0, 0, rs.getInt("codobj"), rs.getString("nomobj")));
               pta.setPrograma(new PlanTrabajoPrograma(0, 0, 0, rs.getInt("codpr"), rs.getInt("pesopr"), rs.getString("nompr")));
               pta.setRecursos(new Recursos(rs.getInt("codrec"), rs.getString("nomrec")));
               pta.setResponsable(new Responsable(rs.getString("ced"), rs.getString("noms"), rs.getString("apes"), "", ""));
               pta.setFuenteHallazgo(new FuenteHallazgo(rs.getInt("codfh"), rs.getString("nomfh")));
               planesTrabajoactividad.add(pta);
            }            
            return planesTrabajoactividad;
            
            
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

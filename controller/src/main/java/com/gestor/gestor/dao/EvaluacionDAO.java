/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.entity.UtilFecha;
import com.gestor.entity.UtilLog;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionPK;
import com.gestor.gestor.EvaluacionPuntajes;
import com.gestor.gestor.EvaluacionPuntajesPK;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Usuarios;
import com.gestor.publico.UsuariosPK;
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
public class EvaluacionDAO {

    private Connection conexion;

    public EvaluacionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void upsertEvaluacion(Evaluacion e) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion("
                    + " cod_evaluacion, codigo_establecimiento, documento_usuario, fecha, "
                    + " fecha_registro, estado)"
                    + " VALUES (" + e.getEvaluacionPK().getCodEvaluacion() + ", " + e.getEvaluacionPK().getCodigoEstablecimiento() + ", '" + e.getDocumentoUsuario() + "',"
                    + UtilFecha.formatoFecha(e.getFecha(), null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA) + " , NOW(), '" + e.getEstado() + "')"
                    + " ON CONFLICT (cod_evaluacion, codigo_establecimiento)"
                    + " DO UPDATE SET documento_usuario=excluded.documento_usuario, fecha=excluded.fecha, estado=excluded.estado"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public List<Evaluacion> cargarEvaluacionList() throws SQLException {
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

    public void insertEvaluacionPuntajes(EvaluacionPuntajes ep) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_puntajes("
                    + " codigo_establecimiento, cod_evaluacion, cod_puntaje, descripcion, "
                    + " plan_accion, capacitacion, califica, orden)"
                    + " VALUES (" + ep.getEvaluacionPuntajesPK().getCodigoEstablecimiento() + ", " + ep.getEvaluacionPuntajesPK().getCodEvaluacion()
                    + " , '" + ep.getEvaluacionPuntajesPK().getCodPuntaje() + "', '" + ep.getDescripcion() + "'"
                    + " , " + Boolean.FALSE + ", " + Boolean.FALSE + ", " + ep.getCalifica() + "," + ep.getOrden() + ");"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public List<EvaluacionPuntajes> cargarEvaluacionPuntajes(int codigoEstablecimiento, Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_evaluacion, cod_puntaje, descripcion,"
                    + " plan_accion, capacitacion, califica, orden"
                    + " FROM gestor.evaluacion_puntajes"
                    + " WHERE codigo_establecimiento=" + codigoEstablecimiento + " AND cod_evaluacion=" + codEvaluacion
                    + " ORDER BY orden"
            );
            rs = consulta.ejecutar(sql);
            List<EvaluacionPuntajes> evaluacionPuntajeses = new ArrayList<>();
            while (rs.next()) {
                EvaluacionPuntajes evaluacionPuntajes = new EvaluacionPuntajes(new EvaluacionPuntajesPK(rs.getInt("codigo_establecimiento"), rs.getLong("cod_evaluacion"), rs.getString("cod_puntaje")),
                        rs.getString("descripcion"), rs.getBoolean("plan_accion"), rs.getBoolean("capacitacion"), rs.getBoolean("califica"), rs.getInt("orden"));
                evaluacionPuntajeses.add(evaluacionPuntajes);
            }
            return evaluacionPuntajeses;

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public EvaluacionPuntajes cargarEvaluacionPuntajes(int codigoEstablecimiento, Long codEvaluacion, String descripcion) throws Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_evaluacion, cod_puntaje, descripcion,"
                    + " plan_accion, capacitacion, califica, orden"
                    + " FROM gestor.evaluacion_puntajes"
                    + " WHERE codigo_establecimiento=" + codigoEstablecimiento
                    + " AND cod_evaluacion=" + codEvaluacion + " AND descripcion='" + descripcion + "'"
                    + " ORDER BY orden"
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                EvaluacionPuntajes evaluacionPuntajes = new EvaluacionPuntajes(new EvaluacionPuntajesPK(rs.getInt("codigo_establecimiento"), rs.getLong("cod_evaluacion"), rs.getString("cod_puntaje")),
                        rs.getString("descripcion"), rs.getBoolean("plan_accion"), rs.getBoolean("capacitacion"), rs.getBoolean("califica"), rs.getInt("orden"));
                return evaluacionPuntajes;
            } else {
                throw new Exception("No se pudo cargar el puntaje con descripción, " + descripcion, UtilLog.TW_RESTRICCION);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Integer avanceEvaluacion(int codigoEstablecimiento, Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(SDI), count(EPSD), ((count(EPSD)::float/count(SDI)::float)*100)::INT AS avance"
                    + " FROM gestor.seccion_detalle_items SDI"
                    + " LEFT JOIN gestor.evaluacion_puntaje_seccion_detalle_combos EPSD on (EPSD.cod_ciclo=SDI.cod_ciclo AND EPSD.cod_seccion=SDI.cod_seccion"
                    + " AND EPSD.cod_detalle=SDI.cod_detalle AND EPSD.cod_item=SDI.cod_item"
                    + " AND codigo_establecimiento=" + codigoEstablecimiento + " AND cod_evaluacion=" + codEvaluacion + ")"
                    + " WHERE SDI.activo"
            );
            rs = consulta.ejecutar(sql);
            rs.next();
            return rs.getInt("avance");

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Integer avanceEvaluacionCiclo(int codigoEstablecimiento, Long codEvaluacion, String codCiclo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    //"SELECT count(SDI), count(EPSD), ((count(EPSD)::float/count(SDI)::float)*100)::INT AS avance"
                    "SELECT count(SDI), count(EPSD), (CASE WHEN count(SDI) = 0 THEN 0 ELSE ((count(EPSD)::float/count(SDI)::float)*100)::INT END) AS avance"
                    + " FROM gestor.seccion_detalle_items SDI"
                    + " LEFT JOIN gestor.evaluacion_puntaje_seccion_detalle_combos EPSD on (EPSD.cod_ciclo=SDI.cod_ciclo AND EPSD.cod_seccion=SDI.cod_seccion"
                    + " AND EPSD.cod_detalle=SDI.cod_detalle AND EPSD.cod_item=SDI.cod_item"
                    + " AND codigo_establecimiento=" + codigoEstablecimiento + " AND cod_evaluacion=" + codEvaluacion
                    + " AND EPSD.cod_ciclo='" + codCiclo + "')"
                    + " WHERE SDI.activo"
                    + " AND SDI.cod_ciclo='" + codCiclo + "'"
            );
            rs = consulta.ejecutar(sql);
            rs.next();
            return rs.getInt("avance");

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public Integer avancePlanaccion(Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        try {            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(                    
                    "SELECT count(EPD) as total"
                    +" FROM gestor.evaluacion_plan_accion_detalle EPD "
                    +" WHERE cod_evaluacion="+codEvaluacion+"  "
            );
            rs = consulta.ejecutar(sql);
            rs.next();            
            Integer total= rs.getInt("total");
            
            consulta = new Consulta(conexion);
            StringBuilder sql1= new StringBuilder(
                    "SELECT count(EPD),(CASE WHEN count(EPD) = 0 THEN 0 ELSE ((count(EPD)::float*100::float)/"+total+")::INT END) AS avance "
                    +" from gestor.evaluacion_plan_accion_detalle EPD "
                    +" WHERE cod_evaluacion="+codEvaluacion+" AND estado='C'"                                        
            );
            rs = consulta.ejecutar(sql1);
            rs.next();   
            
            return rs.getInt("avance");            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void actualizarEstadoEvaluacion(EvaluacionPK evaluacionPK, String estado) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "UPDATE gestor.evaluacion"
                    + " SET estado='" + estado + "'"
                    + " WHERE cod_evaluacion=" + evaluacionPK.getCodEvaluacion() + " AND codigo_establecimiento=" + evaluacionPK.getCodigoEstablecimiento()
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void insertarResumenes(Long codEvaluacion, Integer codigoEstablecimiento, String key, JsonObject o) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
//            System.out.println("json" + o.toString());
            StringBuilder sql = new StringBuilder(
                    "UPDATE gestor.evaluacion set estado='" + o.get("estado").getAsString() + "', calificacion=" + o.get("calificacion").getAsDouble() + ", peso=" + o.get("peso").getAsDouble()
                    + " ,fecha_resumen='" + o.get("fechaResumen").getAsString() + "', resumenes=jsonb_set("
                    + "resumenes,'{0," + key + "}','["
                    + o.toString()
                    + "]', " + Boolean.TRUE.toString()
                    + ")"
                    + " WHERE cod_evaluacion=" + codEvaluacion + " AND codigo_establecimiento=" + codigoEstablecimiento
            );
//            System.out.println("sql=>" + sql);
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public String cargarResumenesEvaluacion(EvaluacionPK evaluacionPK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT resumenes"
                    + " FROM gestor.evaluacion"
                    + " WHERE cod_evaluacion=" + evaluacionPK.getCodEvaluacion() + " AND codigo_establecimiento=" + evaluacionPK.getCodigoEstablecimiento()
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getString("resumenes");
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

    public Collection<? extends Evaluacion> cargarListaEvaluacion(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_evaluacion, codigo_establecimiento, documento_usuario, fecha,"
                    + " fecha_registro, estado, resumenes, calificacion, peso, fecha_resumen,"
                    + " U.documento_usuario, U.nombre, U.apellido, ES.codigo_establecimiento, ES.nombre AS nombre_establecimiento"
                    + " FROM gestor.evaluacion E"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " JOIN public.establecimiento ES USING (codigo_establecimiento)"
                    + condicion
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
    
    public Collection<? extends Evaluacion> cargarListaEvaluacionesPlanMaestros() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT e.cod_evaluacion cod_evaluacion, e.codigo_establecimiento codigo_establecimiento, e.documento_usuario documento_usuario, e.fecha fecha, " +
                    " e.fecha_registro fecha_registro, e.estado estado, e.resumenes resumenes, e.calificacion calificacion, e.peso peso, e.fecha_resumen fecha_resumen, " +
                    " U.documento_usuario, U.nombre, U.apellido, ES.codigo_establecimiento, ES.nombre AS nombre_establecimiento " +
                    " FROM gestor.evaluacion E " +
                    " JOIN public.usuarios U USING (documento_usuario) " +
                    " JOIN public.establecimiento ES USING (codigo_establecimiento) " +
                    " where E.cod_evaluacion not in(select cod_evaluacion from seguimiento.plan_maestro) " +
                    " order by cod_evaluacion"
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

}

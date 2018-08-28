/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.EvaluacionCapacitacion;
import com.gestor.gestor.EvaluacionCapacitacionDetalle;
import com.gestor.gestor.EvaluacionCapacitacionDetallePK;
import com.gestor.publico.Usuarios;
import com.gestor.publico.UsuariosPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author juliano
 */
public class EvaluacionCapacitacionDAO {

    private Connection conexion;

    public EvaluacionCapacitacionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<? extends EvaluacionCapacitacionDetalle> cargarListaEvaluacionCapacitacionDetalle(Long codEvaluacion, int codigoEstablecimiento, String codCiclo, int codSeccion, int codDetalle, int codItem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_evaluacion, codigo_establecimiento, cod_capacitacion, cod_capacitacion_detalle,"
                    + " cod_ciclo, cod_seccion, cod_detalle, cod_item, ECD.nombre, descripcion,"
                    + " estado,ECD.fecha_registro,"
                    + " U.documento_usuario, U.nombre AS nombre_usuario, U.apellido, U.usuario"
                    + " FROM gestor.evaluacion_capacitacion_detalle ECD"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " WHERE cod_evaluacion=" + codEvaluacion + " AND codigo_establecimiento=" + codigoEstablecimiento
                    + " AND cod_ciclo='" + codCiclo + "' AND cod_seccion=" + codSeccion + " AND cod_detalle=" + codDetalle + " AND cod_item=" + codItem
            );
            rs = consulta.ejecutar(sql);

            Collection<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles = new ArrayList<>();
            while (rs.next()) {

                EvaluacionCapacitacionDetalle ecd = new EvaluacionCapacitacionDetalle(new EvaluacionCapacitacionDetallePK(codEvaluacion, codigoEstablecimiento, rs.getLong("cod_capacitacion"), rs.getInt("cod_capacitacion_detalle")),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("estado"),
                        new Usuarios(
                                new UsuariosPK(rs.getString("documento_usuario")), rs.getString("nombre_usuario"), rs.getString("apellido"), rs.getString("usuario")
                        ), rs.getDate("fecha_registro")
                );
                evaluacionCapacitacionDetalles.add(ecd);
            }
            return evaluacionCapacitacionDetalles;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Long consultarEvaluacionCapacitacion(Long codEvaluacion, int codigoEstablecimiento, String estado) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_evaluacion, codigo_establecimiento, cod_capacitacion, documento_usuario,"
                    + " fecha_registro, documento_usuario_modifica, fecha_actualiza,"
                    + " estado"
                    + " FROM gestor.evaluacion_capacitacion"
                    + " WHERE cod_evaluacion=" + codEvaluacion + " AND codigo_establecimiento=" + codigoEstablecimiento + " AND"
                    + " estado = '" + estado + "'"
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getLong("cod_capacitacion");
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

    public void upsertEvaluacionCapacitacion(EvaluacionCapacitacion ec) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_capacitacion(cod_evaluacion, codigo_establecimiento, cod_capacitacion, documento_usuario,"
                    + " fecha_registro, documento_usuario_modifica, fecha_actualiza,"
                    + " estado)"
                    + " VALUES (" + ec.getEvaluacionCapacitacionPK().getCodEvaluacion() + ", " + ec.getEvaluacionCapacitacionPK().getCodigoEstablecimiento() + ", " + ec.getEvaluacionCapacitacionPK().getCodCapacitacion()
                    + " ,'" + ec.getDocumentoUsuario() + "', NOW(), '" + ec.getDocumentoUsuario() + "', NOW(), '" + ec.getEstado() + "')"
                    + " ON CONFLICT (cod_evaluacion, codigo_establecimiento, cod_capacitacion)"
                    + " DO UPDATE SET documento_usuario_modifica=excluded.documento_usuario_modifica, fecha_actualiza=excluded.fecha_actualiza, estado=excluded.estado"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void insertaEvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetalle ecd) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_capacitacion_detalle("
                    + " cod_evaluacion, codigo_establecimiento, cod_capacitacion, cod_capacitacion_detalle,"
                    + " cod_ciclo, cod_seccion, cod_detalle, cod_item, nombre, descripcion,"
                    + " estado, documento_usuario)"
                    + " VALUES (" + ecd.getEvaluacionCapacitacionDetallePK().getCodEvaluacion() + ", " + ecd.getEvaluacionCapacitacionDetallePK().getCodigoEstablecimiento()
                    + " , " + ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacion() + " , DEFAULT,"
                    + " '" + ecd.getCodCiclo() + "', " + ecd.getCodSeccion() + ", " + ecd.getCodDetalle() + ", " + ecd.getCodItem() + ", '" + ecd.getNombre() + "', '" + ecd.getDescripcion() + "'"
                    + " , '" + ecd.getEstado() + "','" + ecd.getDocumentoUsuario() + "');"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public int actualizarEstadoEvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetalle ecd) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "UPDATE gestor.evaluacion_capacitacion_detalle"
                    + " SET estado='" + ecd.getEstado() + "', documento_usuario='" + ecd.getDocumentoUsuario() + "', fecha_actualiza=NOW()"
                    + " WHERE cod_evaluacion=" + ecd.getEvaluacionCapacitacionDetallePK().getCodEvaluacion() + " AND codigo_establecimiento=" + ecd.getEvaluacionCapacitacionDetallePK().getCodigoEstablecimiento()
                    + " AND cod_capacitacion=" + ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacion() + " AND cod_capacitacion_detalle=" + ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle()
            );
            return consulta.actualizar(sql);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void actualizarEvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetalle ecd) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "UPDATE gestor.evaluacion_capacitacion_detalle"
                    + " SET nombre='" + ecd.getNombre() + "', descripcion='" + ecd.getDescripcion() + "', fecha_actualiza=NOW()"
                    + " WHERE cod_evaluacion=" + ecd.getEvaluacionCapacitacionDetallePK().getCodEvaluacion() + " AND codigo_establecimiento=" + ecd.getEvaluacionCapacitacionDetallePK().getCodigoEstablecimiento()
                    + " AND cod_capacitacion=" + ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacion() + " AND cod_capacitacion_detalle=" + ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle()
            );
            consulta.actualizar(sql);
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

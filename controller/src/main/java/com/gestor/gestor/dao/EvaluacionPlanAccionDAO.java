/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.entity.App;
import com.gestor.gestor.EvaluacionPlanAccion;
import com.gestor.gestor.EvaluacionPlanAccionDetalle;
import com.gestor.gestor.EvaluacionPlanAccionDetallePK;
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
public class EvaluacionPlanAccionDAO {

    private Connection conexion;

    public EvaluacionPlanAccionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void upsertEvaluacionPlanAccion(EvaluacionPlanAccion ep) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_plan_accion("
                    + " cod_evaluacion, codigo_establecimiento, cod_plan, documento_usuario,"
                    + " fecha_registro, documento_usuario_modifica, fecha_actualiza, estado)"
                    + " VALUES (" + ep.getEvaluacionPlanAccionPK().getCodEvaluacion() + ", " + ep.getEvaluacionPlanAccionPK().getCodigoEstablecimiento() + ", " + ep.getEvaluacionPlanAccionPK().getCodPlan() + ","
                    + " '" + ep.getDocumentoUsuario() + "', NOW(), '" + ep.getDocumentoUsuarioModifica() + "', NOW(), '" + ep.getEstado() + "')"
                    + " ON CONFLICT (cod_evaluacion, codigo_establecimiento, cod_plan)"
                    + " DO UPDATE SET documento_usuario_modifica=excluded.documento_usuario_modifica, fecha_actualiza=excluded.fecha_actualiza, estado=excluded.estado"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void insertaEvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetalle epd) throws SQLException {

        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_plan_accion_detalle("
                    + " cod_evaluacion, codigo_establecimiento, cod_plan, cod_plan_detalle,"
                    + " cod_ciclo, cod_seccion, cod_detalle, cod_item, nombre, descripcion,"
                    + " estado, documento_usuario)"
                    + " VALUES (" + epd.getEvaluacionPlanAccionDetallePK().getCodEvaluacion() + ", " + epd.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento()
                    + " ," + epd.getEvaluacionPlanAccionDetallePK().getCodPlan() + ","
                    + " DEFAULT, '" + epd.getCodCiclo() + "', " + epd.getCodSeccion() + ", " + epd.getCodDetalle()
                    + " ," + epd.getCodItem() + ", '" + epd.getNombre() + "', '" + epd.getDescripcion() + "', '" + epd.getEstado() + "','" + epd.getDocumentoUsuario() + "');"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccion(Long codEvaluacion, int codigoEstablecimiento, String codCiclo, int codSeccion, int codDetalle, int codItem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_evaluacion, codigo_establecimiento, cod_plan, cod_plan_detalle,"
                    + " cod_ciclo, cod_seccion, cod_detalle, cod_item, EPAD.nombre, descripcion,"
                    + " estado, EPAD.fecha_registro,"
                    + " U.documento_usuario, U.nombre AS nombre_usuario, U.apellido, U.usuario"
                    + " FROM gestor.evaluacion_plan_accion_detalle EPAD"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " WHERE cod_evaluacion=" + codEvaluacion + " AND codigo_establecimiento=" + codigoEstablecimiento
                    + " AND cod_ciclo='" + codCiclo + "' AND cod_seccion=" + codSeccion + " AND cod_detalle=" + codDetalle + " AND cod_item=" + codItem
                    + " AND estado<>'" + App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO + "'"
            );
            rs = consulta.ejecutar(sql);
            Collection<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles = new ArrayList<EvaluacionPlanAccionDetalle>();
            while (rs.next()) {
                EvaluacionPlanAccionDetalle epad = new EvaluacionPlanAccionDetalle(new EvaluacionPlanAccionDetallePK(codEvaluacion, codigoEstablecimiento, rs.getLong("cod_plan"), rs.getInt("cod_plan_detalle")),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("estado"),
                        new Usuarios(
                                new UsuariosPK(rs.getString("documento_usuario")), rs.getString("nombre_usuario"), rs.getString("apellido"), rs.getString("usuario")
                        ), rs.getDate("fecha_registro"
                        )
                );

                evaluacionPlanAccionDetalles.add(epad);

            }
            return evaluacionPlanAccionDetalles;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Long consultarEvaluacionPlanAccion(Long codEvaluacion, int codigoEstablecimiento, String estado) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_evaluacion, codigo_establecimiento, cod_plan, documento_usuario,"
                    + " fecha_registro, documento_usuario_modifica, fecha_actualiza, "
                    + " estado"
                    + " FROM gestor.evaluacion_plan_accion"
                    + " WHERE cod_evaluacion=" + codEvaluacion + " AND codigo_establecimiento=" + codigoEstablecimiento + " AND estado='" + estado + "'"
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getLong("cod_plan");
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

    public int actualizarEstadoEvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetalle epad) throws SQLException {

        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "UPDATE gestor.evaluacion_plan_accion_detalle"
                    + " SET estado='" + epad.getEstado() + "', documento_usuario='" + epad.getDocumentoUsuario() + "', fecha_actualiza=NOW()"
                    + " WHERE cod_evaluacion=" + epad.getEvaluacionPlanAccionDetallePK().getCodEvaluacion() + " AND codigo_establecimiento=" + epad.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento()
                    + " AND cod_plan=" + epad.getEvaluacionPlanAccionDetallePK().getCodPlan() + " AND cod_plan_detalle=" + epad.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle()
            );
            return consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void actualizarEvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetalle epd) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "UPDATE gestor.evaluacion_plan_accion_detalle"
                    + " SET nombre='" + epd.getNombre() + "', descripcion='" + epd.getDescripcion() + "', fecha_actualiza=NOW()"
                    + " WHERE cod_evaluacion=" + epd.getEvaluacionPlanAccionDetallePK().getCodEvaluacion() + " AND codigo_establecimiento=" + epd.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento()
                    + " AND cod_plan=" + epd.getEvaluacionPlanAccionDetallePK().getCodPlan() + " AND cod_plan_detalle=" + epd.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle()
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

}

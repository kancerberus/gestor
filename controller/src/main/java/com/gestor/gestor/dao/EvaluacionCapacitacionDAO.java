/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.Ciclo;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionCapacitacion;
import com.gestor.gestor.EvaluacionCapacitacionDetalle;
import com.gestor.gestor.EvaluacionCapacitacionDetalleNotas;
import com.gestor.gestor.EvaluacionCapacitacionDetalleNotasPK;
import com.gestor.gestor.EvaluacionCapacitacionDetallePK;
import com.gestor.gestor.EvaluacionPK;
import com.gestor.gestor.Seccion;
import com.gestor.gestor.SeccionDetalle;
import com.gestor.gestor.SeccionDetalleItems;
import com.gestor.gestor.SeccionDetalleItemsPK;
import com.gestor.gestor.SeccionDetallePK;
import com.gestor.gestor.SeccionPK;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Usuarios;
import com.gestor.publico.UsuariosPK;
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
                    + " estado,ECD.fecha_registro, responsable,"
                    + " U.documento_usuario, U.nombre AS nombre_usuario, U.apellido, U.usuario"
                    + " FROM gestor.evaluacion_capacitacion_detalle ECD"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " WHERE cod_evaluacion=" + codEvaluacion + " AND codigo_establecimiento=" + codigoEstablecimiento
                    + " AND cod_ciclo='" + codCiclo + "' AND cod_seccion=" + codSeccion + " AND cod_detalle=" + codDetalle + " AND cod_item=" + codItem
            );
            rs = consulta.ejecutar(sql);

            Collection<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles = new ArrayList<>();
            while (rs.next()) {

                EvaluacionCapacitacionDetalle ecd = new EvaluacionCapacitacionDetalle(new EvaluacionCapacitacionDetallePK(codEvaluacion, codigoEstablecimiento, rs.getLong("cod_capacitacion"), rs.getLong("cod_capacitacion_detalle")),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("estado"),
                        new Usuarios(
                                new UsuariosPK(rs.getString("documento_usuario")), rs.getString("nombre_usuario"), rs.getString("apellido"), rs.getString("usuario")
                        ), rs.getDate("fecha_registro")
                );
                ecd.setResponsable(rs.getString("responsable"));
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
                    + " estado, documento_usuario, responsable)"
                    + " VALUES (" + ecd.getEvaluacionCapacitacionDetallePK().getCodEvaluacion() + ", " + ecd.getEvaluacionCapacitacionDetallePK().getCodigoEstablecimiento()
                    + " , " + ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacion() + " , " + ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle() + ","
                    + " '" + ecd.getCodCiclo() + "', " + ecd.getCodSeccion() + ", " + ecd.getCodDetalle() + ", " + ecd.getCodItem() + ", '" + ecd.getNombre() + "', '" + ecd.getDescripcion() + "'"
                    + " , '" + ecd.getEstado() + "','" + ecd.getDocumentoUsuario() + "','" + ecd.getResponsable() + "');"
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
                    + " SET nombre='" + ecd.getNombre() + "', descripcion='" + ecd.getDescripcion() + "', fecha_actualiza=NOW(), responsable='" + ecd.getResponsable() + "'"
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

    public Collection<? extends EvaluacionCapacitacionDetalle> cargarListaEvaluacionCapacitacionDetalle(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT ECD.cod_evaluacion, ECD.codigo_establecimiento, ECD.cod_capacitacion, ECD.cod_capacitacion_detalle,"
                    + " ECD.cod_ciclo, ECD.cod_seccion, ECD.cod_detalle, ECD.cod_item, ECD.nombre AS ecd_nombre, ECD.descripcion,"
                    + " ECD.estado,ECD.fecha_registro, ECD.responsable,"
                    + " U.documento_usuario, U.nombre AS nombre_usuario, U.apellido, U.usuario,"
                    + " SDI.cod_item AS sdi_cod_item, SDI.nombre AS sdi_nombre, SDI.detalle AS sdi_detalle, SDI.peso AS sdi_peso, SDI.activo AS sdi_activo, SDI.imagen AS sdi_imagen, SDI.orden AS sdi_orden, SDI.numeral AS sdi_numeral,"
                    + " SD.cod_detalle AS sd_cod_detalle, SD.nombre AS sd_nombre, SD.detalle AS sd_detalle, SD.orden AS sd_orden, SD.peso AS sd_peso, SD.imagen AS sd_imagen, SD.activo AS sd_activo, SD.numeral AS sd_numeral,"
                    + " S.cod_seccion AS s_cod_seccion, S.nombre AS s_nombre, S.activo AS s_activo, S.peso AS s_peso, S.imagen AS s_imagen, S.orden AS s_orden, S.numeral AS s_numeral,"
                    + " E.cod_evaluacion AS e_cod_evaluacion, E.documento_usuario AS e_documento_usuario, E.fecha AS e_fecha, E.fecha_registro AS e_fecha_registro, E.estado AS e_estado,"
                    + " ES.nombre AS es_nombre,"
                    + " C.cod_ciclo AS c_cod_ciclo, C.nombre AS c_nombre, C.numeral AS c_numeral"
                    + " FROM gestor.evaluacion_capacitacion_detalle ECD"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " JOIN gestor.evaluacion_capacitacion EC USING (cod_evaluacion, codigo_establecimiento, cod_capacitacion)"
                    + " JOIN gestor.evaluacion E USING (cod_evaluacion, codigo_establecimiento)"
                    + " JOIN public.establecimiento ES USING (codigo_establecimiento)"
                    + " JOIN gestor.seccion_detalle_items SDI USING (cod_seccion, cod_detalle, cod_ciclo, cod_item)"
                    + " JOIN gestor.seccion_detalle SD USING (cod_seccion, cod_ciclo, cod_detalle)"
                    + " JOIN gestor.seccion S USING (cod_seccion, cod_ciclo)"
                    + " JOIN gestor.ciclo C USING (cod_ciclo)"
                    + condicion
                    + " ORDER BY C.numeral, S.numeral, SD.numeral, SDI.numeral"
            );
            rs = consulta.ejecutar(sql);

            Collection<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles = new ArrayList<>();
            while (rs.next()) {

                EvaluacionCapacitacionDetalle ecd = new EvaluacionCapacitacionDetalle(new EvaluacionCapacitacionDetallePK(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento"), rs.getLong("cod_capacitacion"), rs.getLong("cod_capacitacion_detalle")),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getString("ecd_nombre"), rs.getString("descripcion"), rs.getString("estado"),
                        new Usuarios(
                                new UsuariosPK(rs.getString("documento_usuario")), rs.getString("nombre_usuario"), rs.getString("apellido"), rs.getString("usuario")
                        ), rs.getDate("fecha_registro")
                );
                ecd.setResponsable(rs.getString("responsable"));

                //evaluacion
                Evaluacion e = new Evaluacion(new EvaluacionPK(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento")), rs.getString("documento_usuario"),
                        rs.getDate("e_fecha"), rs.getDate("e_fecha_registro"), rs.getString("e_estado"));
                e.setUsuarios(new Usuarios(new UsuariosPK(rs.getString("e_documento_usuario"))));
                e.setEstablecimiento(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("es_nombre")));
                ecd.setEvaluacion(e);

                ecd.setEstablecimiento(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("es_nombre")));

                SeccionDetalleItems sdi = new SeccionDetalleItems(new SeccionDetalleItemsPK(rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("sdi_cod_item")),
                        rs.getString("sdi_nombre"), rs.getString("sdi_detalle"), rs.getDouble("sdi_peso"), rs.getBoolean("sdi_activo"), rs.getString("sdi_imagen"), rs.getInt("sdi_orden"));
                sdi.setNumeral(rs.getString("sdi_numeral"));

                SeccionDetalle sd = new SeccionDetalle(new SeccionDetallePK(rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("sd_cod_detalle")),
                        rs.getString("sd_nombre"), rs.getString("sd_detalle"), rs.getInt("sd_orden"), rs.getDouble("sd_peso"), rs.getString("sd_imagen"), rs.getBoolean("sd_activo"));
                sd.setNumeral(rs.getString("sd_numeral"));

                Seccion s = new Seccion(new SeccionPK(rs.getString("cod_ciclo"), rs.getInt("s_cod_seccion")), rs.getString("s_nombre"), rs.getBoolean("s_activo"), rs.getDouble("s_peso"),
                        rs.getString("s_imagen"), rs.getInt("s_orden")
                );
                s.setNumeral(rs.getString("s_numeral"));

                Ciclo c = new Ciclo(rs.getString("c_cod_ciclo"), rs.getString("c_nombre"));
                c.setNumeral(rs.getString("c_numeral"));

                c.setEvaluacion(e);
                s.setCiclo(c);
                sd.setSeccion(s);
                sdi.setSeccionDetalle(sd);
                ecd.setSeccionDetalleItems(sdi);

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

    public void insertaEvaluacionCapacitacionDetalleNotas(EvaluacionCapacitacionDetalleNotas ecdn) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_capacitacion_detalle_notas("
                    + " cod_evaluacion, codigo_establecimiento,"
                    + " cod_capacitacion, cod_capacitacion_detalle, "
                    + " cod_nota, documento_usuario, estado, nombre, descripcion, fecha_registro)"
                    + " VALUES (" + ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodEvaluacion() + ", " + ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodigoEstablecimiento()
                    + " , " + ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodCapacitacion() + ", " + ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodCapacitacionDetalle()
                    + " , DEFAULT, '" + ecdn.getDocumentoUsuario() + "', '" + ecdn.getEstado() + "', '" + ecdn.getNombre() + "', '" + ecdn.getDescripcion() + "', NOW());"
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

    public List<EvaluacionCapacitacionDetalleNotas> cargarListaEvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetalleNotasPK pk) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_evaluacion, codigo_establecimiento, cod_capacitacion, cod_capacitacion_detalle,"
                    + " cod_nota, documento_usuario, estado, nombre, descripcion, fecha_registro"
                    + " FROM gestor.evaluacion_capacitacion_detalle_notas"
                    + " WHERE cod_evaluacion=" + pk.getCodEvaluacion() + " AND codigo_establecimiento=" + pk.getCodigoEstablecimiento()
                    + " AND cod_capacitacion=" + pk.getCodCapacitacion() + " AND cod_capacitacion_detalle=" + pk.getCodCapacitacionDetalle()
            );
            rs = consulta.ejecutar(sql);
            List<EvaluacionCapacitacionDetalleNotas> evaluacionCapacitacionDetalleNotasList = new ArrayList<>();
            while (rs.next()) {
                EvaluacionCapacitacionDetalleNotas ecdn = new EvaluacionCapacitacionDetalleNotas(
                        new EvaluacionCapacitacionDetalleNotasPK(pk.getCodEvaluacion(), pk.getCodigoEstablecimiento(), pk.getCodCapacitacion(),
                                pk.getCodCapacitacionDetalle(), rs.getLong("cod_nota")),
                         rs.getString("documento_usuario"), rs.getString("estado"), rs.getString("nombre"), rs.getString("descripcion"));
                ecdn.setFechaRegistro(rs.getDate("fecha_registro"));
                evaluacionCapacitacionDetalleNotasList.add(ecdn);
            }
            return evaluacionCapacitacionDetalleNotasList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void upsertEvaluacionCapacitacionDetalleNotas(EvaluacionCapacitacionDetalleNotas ecdn) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_capacitacion_detalle_notas("
                    + " cod_evaluacion, codigo_establecimiento,"
                    + " cod_capacitacion, cod_capacitacion_detalle, "
                    + " cod_nota, documento_usuario, estado, nombre, descripcion, fecha_registro)"
                    + " VALUES (" + ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodEvaluacion() + ", " + ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodigoEstablecimiento()
                    + " , " + ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodCapacitacion() + ", " + ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodCapacitacionDetalle()
                    + " , " + ecdn.getEvaluacionCapacitacionDetalleNotasPK().getCodNota() + ", '" + ecdn.getDocumentoUsuario() + "', '" + ecdn.getEstado() + "', '" + ecdn.getNombre() + "', '" + ecdn.getDescripcion() + "', NOW())"
                    + " ON CONFLICT (cod_evaluacion, codigo_establecimiento, cod_capacitacion, cod_capacitacion_detalle, cod_nota)"
                    + " DO UPDATE SET documento_usuario=excluded.documento_usuario, descripcion=excluded.descripcion"
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

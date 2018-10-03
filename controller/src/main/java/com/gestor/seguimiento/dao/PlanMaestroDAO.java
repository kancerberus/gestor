/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionPK;
import com.gestor.publico.Establecimiento;
import com.gestor.seguimiento.PlanMaestro;
import com.gestor.seguimiento.PlanMaestroPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Julian D Osorio G
 */
public class PlanMaestroDAO {

    private Connection conexion;

    public PlanMaestroDAO(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * @return the conexion
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * @param conexion the conexion to set
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<? extends PlanMaestro> cargarListaPlanMaestro(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT PM.cod_evaluacion, PM.cod_maestro, PM.fecha_registro,"
                    + " PM.fecha_actualiza,"
                    + " E.cod_evaluacion AS cod_evaluacion_e, E.documento_usuario, E.fecha,"
                    + " E.fecha_registro AS fecha_registro_e, E.estado, E.fecha_resumen, E.calificacion, E.peso, E.resumenes,"
                    + " ES.codigo_establecimiento, ES.codigo_municipio, ES.nombre AS nombre_es, ES.nit, ES.direccion,"
                    + " ES.telefono, ES.correo, ES.fecha_cierre_diario, ES.tipo_establecimiento"
                    + " FROM seguimiento.plan_maestro PM"
                    + " JOIN gestor.evaluacion E USING (cod_evaluacion, codigo_establecimiento)"
                    + " JOIN public.establecimiento ES USING (codigo_establecimiento)"
                    + condicion
            );
            rs = consulta.ejecutar(sql);

            Collection<PlanMaestro> planMaestroList = new ArrayList<>();
            while (rs.next()) {
                PlanMaestro pm = new PlanMaestro(new PlanMaestroPK(
                        rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento"), rs.getLong("cod_maestro")
                ), rs.getDate("fecha_registro"), rs.getDate("fecha_actualiza"));
                pm.setEvaluacion(new Evaluacion(new EvaluacionPK(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento")), rs.getString("documento_usuario"),
                        rs.getDate("fecha"), rs.getDate("fecha_registro_e"), rs.getString("estado"), rs.getDouble("calificacion"), rs.getDouble("peso")));
                pm.setEstablecimiento(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("nombre_es"), rs.getString("nit"), rs.getString("direccion"), rs.getString("telefono"), rs.getString("correo")));
                planMaestroList.add(pm);
            }
            return planMaestroList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void upsertPlanMaestro(PlanMaestro pm) throws SQLException {

        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_maestro("
                    + " cod_evaluacion, codigo_establecimiento, cod_maestro, fecha_registro,"
                    + " fecha_actualiza)"
                    + " VALUES (" + pm.getPlanMaestroPK().getCodEvaluacion() + ", " + pm.getPlanMaestroPK().getCodigoEstablecimiento()
                    + " , " + pm.getPlanMaestroPK().getCodMaestro() + ", "
                    + " NOW(), NOW())"
                    + " ON CONFLICT (cod_evaluacion, codigo_establecimiento, cod_maestro)"
                    + " DO UPDATE SET fecha_actualiza=excluded.fecha_actualiza"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void procesarPlanMaestroPlanTituloAdiuntos(Long codEvaluacion, int codigoEstablecimiento, Long codMaestro) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " INSERT INTO seguimiento.plan_maestro_plan_titulo_adiuntos"
                    + " SELECT cod_evaluacion, codigo_establecimiento, cod_maestro, cod_titulo_adjunto, "
                    + " cod_titulo"
                    + " FROM "
                    + " (SELECT EA.cod_evaluacion, EA.codigo_establecimiento, cod_maestro, cod_titulo_adjunto, "
                    + " cod_titulo,MAX(EA.version)"
                    + " FROM gestor.evaluacion_adjuntos EA"
                    + " JOIN seguimiento.plan_maestro PM ON (PM.cod_evaluacion=EA.cod_evaluacion AND PM.codigo_establecimiento=EA.codigo_establecimiento)"
                    + " JOIN seguimiento.plan_titulo_adiuntos PTA ON (PTA.codigo_establecimiento=EA.codigo_establecimiento AND PTA.cod_categoria=EA.cod_categoria AND PTA.cod_categoria_tipo=EA.cod_categoria_tipo)"
                    + " WHERE EA.cod_evaluacion=" + codEvaluacion + " AND EA.codigo_establecimiento=" + codigoEstablecimiento + " AND PM.cod_maestro=" + codMaestro
                    + " GROUP BY 1,2,3,4,5"
                    + " ) FOO"
                    + " ON CONFLICT DO NOTHING"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void procesarPlanTituloAdiuntosEvaluacionAdjuntos(Long codEvaluacion, int codigoEstablecimiento, Long codMaestro) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " INSERT INTO seguimiento.plan_titulo_adiuntos_evaluacion_adjuntos("
                    + "             cod_titulo_adjunto, cod_titulo, cod_evaluacion, codigo_establecimiento,"
                    + "             cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_adjunto)"
                    + " WITH tmp_ea AS ("
                    + " 	SELECT cod_evaluacion, codigo_establecimiento, cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_categoria, cod_categoria_tipo, MAX(version) AS version"
                    + " 	FROM gestor.evaluacion_adjuntos"
                    + " 	WHERE cod_evaluacion=" + codEvaluacion + " AND codigo_establecimiento=" + codigoEstablecimiento
                    + " 	GROUP BY 1,2,3,4,5,6,7,8"
                    + " )"
                    + " SELECT cod_titulo_adjunto, cod_titulo, EA.cod_evaluacion, EA.codigo_establecimiento,"
                    + "             EA.cod_ciclo, EA.cod_seccion, EA.cod_detalle, EA.cod_item, EA.cod_adjunto"
                    + " FROM gestor.evaluacion_adjuntos EA"
                    + " JOIN tmp_ea TEA ON (EA.cod_evaluacion=TEA.cod_evaluacion AND EA.codigo_establecimiento=TEA.codigo_establecimiento"
                    + " AND TEA.cod_ciclo=EA.cod_ciclo AND TEA.cod_seccion=EA.cod_seccion AND TEA.cod_detalle=EA.cod_detalle "
                    + " AND TEA.cod_item=EA.cod_item AND TEA.cod_categoria=EA.cod_categoria AND TEA.cod_categoria_tipo=EA.cod_categoria_tipo AND TEA.version=EA.version)"
                    + " JOIN seguimiento.plan_maestro PM ON (PM.cod_evaluacion=EA.cod_evaluacion AND PM.codigo_establecimiento=EA.codigo_establecimiento)"
                    + " JOIN seguimiento.plan_titulo_adiuntos PTA ON (PTA.codigo_establecimiento=EA.codigo_establecimiento AND PTA.cod_categoria=EA.cod_categoria AND PTA.cod_categoria_tipo=EA.cod_categoria_tipo)"
                    + " WHERE PM.cod_maestro=" + codMaestro
                    + " ON CONFLICT DO NOTHING"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Long consultarCodMaestroPlanMaestro(int codigoEstablecimiento, Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_maestro"
                    + " FROM seguimiento.plan_maestro"
                    + " WHERE cod_evaluacion=" + codEvaluacion + " AND codigo_establecimiento=" + codigoEstablecimiento
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getLong("cod_maestro");
            } else {
                return null;
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

    public void procesarPlanMaestroPlanSeccionAdjuntos(Long codEvaluacion, int codigoEstablecimiento, Long codMaestro) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_maestro_plan_seccion_adjuntos("
                    + "             cod_evaluacion, codigo_establecimiento, cod_maestro, cod_titulo,"
                    + "             cod_seccion, cod_seccion_adjunto)"
                    + "  WITH tmp_ea AS ("
                    + "  	SELECT cod_evaluacion, codigo_establecimiento, cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_categoria, cod_categoria_tipo, MAX(version) AS version"
                    + "  	FROM gestor.evaluacion_adjuntos"
                    + "  	WHERE cod_evaluacion=" + codEvaluacion + "  AND codigo_establecimiento=" + codigoEstablecimiento
                    + "  	GROUP BY 1,2,3,4,5,6,7,8"
                    + "  )"
                    + "  SELECT EA.cod_evaluacion, EA.codigo_establecimiento, cod_maestro, cod_titulo,"
                    + "             PSA.cod_seccion, PSA.cod_seccion_adjunto"
                    + "  FROM gestor.evaluacion_adjuntos EA"
                    + "  JOIN tmp_ea TEA ON (EA.cod_evaluacion=TEA.cod_evaluacion AND EA.codigo_establecimiento=TEA.codigo_establecimiento"
                    + "  AND TEA.cod_ciclo=EA.cod_ciclo AND TEA.cod_seccion=EA.cod_seccion AND TEA.cod_detalle=EA.cod_detalle"
                    + "  AND TEA.cod_item=EA.cod_item AND TEA.cod_categoria=EA.cod_categoria AND TEA.cod_categoria_tipo=EA.cod_categoria_tipo AND TEA.version=EA.version)"
                    + "  JOIN seguimiento.plan_maestro PM ON (PM.cod_evaluacion=EA.cod_evaluacion AND PM.codigo_establecimiento=EA.codigo_establecimiento)"
                    + "  JOIN seguimiento.plan_seccion_adjuntos PSA ON (PSA.codigo_establecimiento=EA.codigo_establecimiento AND PSA.cod_categoria=EA.cod_categoria AND PSA.cod_categoria_tipo=EA.cod_categoria_tipo)"
                    + "  WHERE PM.cod_maestro=" + codMaestro
                    + "  ON CONFLICT DO NOTHING"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void procesarPlanSeccionAdjuntosEvaluacionAdjuntos(Long codEvaluacion, int codigoEstablecimiento, Long codMaestro) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " INSERT INTO seguimiento.plan_seccion_adjuntos_evaluacion_adjuntos("
                    + "             cod_titulo, cod_seccion_adjunto, cod_seccion, cod_evaluacion,"
                    + "             codigo_establecimiento, cod_ciclo, cod_seccion_ea, cod_detalle,"
                    + "             cod_item, cod_adjunto)"
                    + "  WITH tmp_ea AS ("
                    + "  	SELECT cod_evaluacion, codigo_establecimiento, cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_categoria, cod_categoria_tipo, MAX(version) AS version"
                    + "  	FROM gestor.evaluacion_adjuntos"
                    + "   	WHERE cod_evaluacion=" + codEvaluacion + "  AND codigo_establecimiento=" + codigoEstablecimiento
                    + "  	GROUP BY 1,2,3,4,5,6,7,8"
                    + "  )"
                    + "  SELECT PSA.cod_titulo, PSA.cod_seccion_adjunto, PSA.cod_seccion, EA.cod_evaluacion,"
                    + "             EA.codigo_establecimiento, EA.cod_ciclo, EA.cod_seccion, EA.cod_detalle,"
                    + "             EA.cod_item, EA.cod_adjunto"
                    + "  FROM gestor.evaluacion_adjuntos EA"
                    + "  JOIN tmp_ea TEA ON (EA.cod_evaluacion=TEA.cod_evaluacion AND EA.codigo_establecimiento=TEA.codigo_establecimiento"
                    + "  AND TEA.cod_ciclo=EA.cod_ciclo AND TEA.cod_seccion=EA.cod_seccion AND TEA.cod_detalle=EA.cod_detalle"
                    + "  AND TEA.cod_item=EA.cod_item AND TEA.cod_categoria=EA.cod_categoria AND TEA.cod_categoria_tipo=EA.cod_categoria_tipo AND TEA.version=EA.version)"
                    + "  JOIN seguimiento.plan_maestro PM ON (PM.cod_evaluacion=EA.cod_evaluacion AND PM.codigo_establecimiento=EA.codigo_establecimiento)"
                    + "  JOIN seguimiento.plan_seccion_adjuntos PSA ON (PSA.codigo_establecimiento=EA.codigo_establecimiento AND PSA.cod_categoria=EA.cod_categoria AND PSA.cod_categoria_tipo=EA.cod_categoria_tipo)"
                    + "  WHERE PM.cod_maestro=" + codMaestro
                    + "  ON CONFLICT DO NOTHING"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
}

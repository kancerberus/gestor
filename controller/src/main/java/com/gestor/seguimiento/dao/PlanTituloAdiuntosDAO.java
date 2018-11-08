/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.publico.EvaluacionAdjuntos;
import com.gestor.seguimiento.PlanSeccionAdjuntos;
import com.gestor.seguimiento.PlanSeccionAdjuntosPK;
import com.gestor.seguimiento.PlanSeccionDetalleAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalleAdjuntosPK;
import com.gestor.seguimiento.PlanSeccionDetalleItemAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalleItemAdjuntosPK;
import com.gestor.seguimiento.PlanSeccionDetalleItemPK;
import com.gestor.seguimiento.PlanSeccionDetallePK;
import com.gestor.seguimiento.PlanSeccionPK;
import com.gestor.seguimiento.PlanTituloAdiuntos;
import com.gestor.seguimiento.PlanTituloAdiuntosPK;
import com.gestor.seguimiento.PlanTituloPK;
import com.gestor.seguimiento.PlanTitulo;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Julian D Osorio G
 */
public class PlanTituloAdiuntosDAO {

    private Connection conexion;

    public PlanTituloAdiuntosDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    
    public Collection<? extends PlanTituloAdiuntos> cargarPlanTituloadjuntosList(PlanTitulo plantitulo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanTituloAdiuntos> planTituloadjuntosList = new ArrayList<>();
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_titulo_adjunto, cod_categoria, cod_categoria_tipo, titulo, descripcion, documento"
                    + " FROM seguimiento.plan_titulo_adiuntos"
                    + " WHERE codigo_establecimiento='"+plantitulo.getPlanTituloPK().getCodigoEstablecimiento()+"' AND cod_titulo='"+plantitulo.getPlanTituloPK().getCodTitulo()+"' "
                    + " ORDER BY cod_titulo_adjunto"  
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanTituloAdiuntos pta = new PlanTituloAdiuntos(new PlanTituloAdiuntosPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo_adjunto"),
                        rs.getInt("cod_titulo")), rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo"), rs.getString("titulo"), rs.getString("descripcion"), rs.getString("documento")
                );
                planTituloadjuntosList.add(pta);
            }
            return planTituloadjuntosList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarPlantituloadjunto(PlanTituloAdiuntos plantituloadjuntos) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_titulo_adiuntos "
                    + " ( codigo_establecimiento, cod_titulo_adjunto, cod_titulo, cod_categoria, cod_categoria_tipo, titulo, descripcion, documento )"
                    + " VALUES ('"+plantituloadjuntos.getPlanTituloAdiuntosPK().getCodigoEstablecimiento()+"', '"+plantituloadjuntos.getPlanTituloAdiuntosPK().getCodTituloAdjunto()+"', "
                    + " '"+plantituloadjuntos.getPlanTituloAdiuntosPK().getCodTitulo()+"', '"+plantituloadjuntos.getCodCategoria()+"', "
                    + " '"+plantituloadjuntos.getCodCategoriaTipo()+"', '"+plantituloadjuntos.getTitulo()+"', '"+plantituloadjuntos.getDescripcion()+"', '"+plantituloadjuntos.getDocumento()+"') "
                    + " ON CONFLICT ( codigo_establecimiento, cod_titulo_adjunto, cod_titulo ) DO UPDATE "
                    + " SET titulo=EXCLUDED.titulo, descripcion=EXCLUDED.descripcion, documento=EXCLUDED.documento "
                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
        
    public Collection<? extends PlanTituloAdiuntos> cargarPlanTituloAdiuntosList(PlanTituloPK planTituloPK, Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT PTA.codigo_establecimiento, PTA.cod_titulo_adjunto, PTA.cod_titulo, PTA.cod_categoria, "
                    + " PTA.cod_categoria_tipo, PTA.titulo, PTA.descripcion, PTA.documento,"
                    + " EA.cod_evaluacion, EA.cod_ciclo, EA.cod_seccion, "
                    + " EA.cod_detalle, EA.cod_item, EA.cod_adjunto, EA.nombre AS nombre_ea, EA.descripcion AS descripcion_ea, EA.archivo, "
                    + " EA.extension, EA.fecha AS fecha_ea, EA.documento_usuario, EA.estado AS estado_ea, EA.fecha_actualiza, "
                    + " EA.fecha_inicio_vigencia, EA.fecha_fin_vigencia, EA.meses_vigencia, EA.cod_categoria, "
                    + " EA.cod_categoria_tipo, EA.version"
                    + " FROM seguimiento.plan_titulo_adiuntos PTA"
                    + " JOIN seguimiento.plan_titulo_adiuntos_evaluacion_adjuntos USING (cod_titulo_adjunto, cod_titulo, codigo_establecimiento)"
                    + " JOIN gestor.evaluacion_adjuntos EA USING (cod_evaluacion, codigo_establecimiento, cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_adjunto)"
                    + " WHERE PTA.codigo_establecimiento=" + planTituloPK.getCodigoEstablecimiento()
                    + " AND PTA.cod_titulo=" + planTituloPK.getCodTitulo() + "AND EA.cod_evaluacion=" + codEvaluacion
            );
            rs = consulta.ejecutar(sql);

            Collection<PlanTituloAdiuntos> planTituloAdiuntosList = new ArrayList<>();
            while (rs.next()) {
                PlanTituloAdiuntos pta = new PlanTituloAdiuntos(
                        new PlanTituloAdiuntosPK(
                                rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo_adjunto"), rs.getInt("cod_titulo")
                        ), rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo"), rs.getString("titulo"),
                        rs.getString("descripcion"), rs.getString("documento")
                );

                EvaluacionAdjuntos ea = new EvaluacionAdjuntos(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento"),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getLong("cod_adjunto")
                );
                ea.setNombre(rs.getString("nombre_ea"));
                ea.setDescripcion(rs.getString("descripcion_ea"));
                ea.setArchivo(rs.getString("archivo"));
                ea.setExtension(rs.getString("extension"));
                ea.setFecha(rs.getDate("fecha_ea"));
                ea.setDocumentoUsuario(rs.getString("documento_usuario"));
                ea.setEstado(rs.getString("estado_ea"));
                ea.setMesesVigencia(rs.getInt("meses_vigencia"));
                ea.setVersion(rs.getInt("version"));
                ea.setFechaInicioVigencia(rs.getDate("fecha_inicio_vigencia"));
                ea.setFechaFinVigencia(rs.getDate("fecha_fin_vigencia"));
                pta.setEvaluacionAdjuntos(ea);
                planTituloAdiuntosList.add(pta);
            }
            return planTituloAdiuntosList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends PlanSeccionAdjuntos> cargarPlanSeccionAdjuntos(PlanSeccionPK planSeccionPK, Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT PSA.codigo_establecimiento, PSA.cod_titulo, PSA.cod_seccion, PSA.cod_seccion_adjunto,"
                    + " PSA.cod_categoria, PSA.cod_categoria_tipo, PSA.titulo, PSA.descripcion, PSA.documento,"
                    + " EA.cod_evaluacion, EA.cod_ciclo, EA.cod_seccion,"
                    + " EA.cod_detalle, EA.cod_item, EA.cod_adjunto, EA.nombre AS nombre_ea, EA.descripcion AS descripcion_ea, EA.archivo,"
                    + " EA.extension, EA.fecha AS fecha_ea, EA.documento_usuario, EA.estado AS estado_ea, EA.fecha_actualiza,"
                    + " EA.fecha_inicio_vigencia, EA.fecha_fin_vigencia, EA.meses_vigencia, EA.cod_categoria,"
                    + " EA.cod_categoria_tipo, EA.version"
                    + " FROM seguimiento.plan_seccion_adjuntos PSA"
                    + " JOIN seguimiento.plan_seccion_adjuntos_evaluacion_adjuntos PSAEA USING (cod_titulo, cod_seccion_adjunto, cod_seccion, codigo_establecimiento)"
                    + " JOIN gestor.evaluacion_adjuntos EA ON (EA.cod_evaluacion=PSAEA.cod_evaluacion AND EA.codigo_establecimiento=PSAEA.codigo_establecimiento"
                    + " AND EA.cod_ciclo=PSAEA.cod_ciclo AND EA.cod_seccion=PSAEA.cod_seccion_ea AND EA.cod_detalle=PSAEA.cod_detalle AND EA.cod_item=PSAEA.cod_item AND EA.cod_adjunto=PSAEA.cod_adjunto)"
                    + " WHERE PSA.codigo_establecimiento=" + planSeccionPK.getCodigoEstablecimiento()
                    + " AND PSA.cod_titulo=" + planSeccionPK.getCodTitulo() + " AND PSA.cod_seccion=" + planSeccionPK.getCodSeccion()
                    + " AND EA.cod_evaluacion=" + codEvaluacion
            );
            rs = consulta.ejecutar(sql);

            Collection<PlanSeccionAdjuntos> planSeccionAdjuntosList = new ArrayList<>();
            while (rs.next()) {

                PlanSeccionAdjuntos psa = new PlanSeccionAdjuntos(
                        new PlanSeccionAdjuntosPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_adjunto")),
                        rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo"), rs.getString("titulo"),
                        rs.getString("descripcion"), rs.getString("documento")
                );

                EvaluacionAdjuntos ea = new EvaluacionAdjuntos(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento"),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getLong("cod_adjunto")
                );
                ea.setNombre(rs.getString("nombre_ea"));
                ea.setDescripcion(rs.getString("descripcion_ea"));
                ea.setArchivo(rs.getString("archivo"));
                ea.setExtension(rs.getString("extension"));
                ea.setFecha(rs.getDate("fecha_ea"));
                ea.setDocumentoUsuario(rs.getString("documento_usuario"));
                ea.setEstado(rs.getString("estado_ea"));
                ea.setMesesVigencia(rs.getInt("meses_vigencia"));
                ea.setVersion(rs.getInt("version"));
                ea.setFechaInicioVigencia(rs.getDate("fecha_inicio_vigencia"));
                ea.setFechaFinVigencia(rs.getDate("fecha_fin_vigencia"));
                psa.setEvaluacionAdjuntos(ea);
                planSeccionAdjuntosList.add(psa);
            }
            return planSeccionAdjuntosList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<PlanSeccionDetalleAdjuntos> cargarPlanSeccionDetalleAdjuntos(PlanSeccionDetallePK planSeccionDetallePK, Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT PSDA.codigo_establecimiento, PSDA.cod_titulo, PSDA.cod_seccion, PSDA.cod_seccion_detalle, "
                    + " PSDA.cod_seccion_detalle_adjuntos, PSDA.cod_categoria, PSDA.cod_categoria_tipo, "
                    + " PSDA.titulo, PSDA.descripcion, PSDA.documento,"
                    + " EA.cod_evaluacion, EA.codigo_establecimiento, EA.cod_ciclo, EA.cod_seccion, "
                    + " EA.cod_detalle, EA.cod_item, EA.cod_adjunto, EA.nombre nombre_ea, EA.descripcion descripcion_ea, EA.archivo, "
                    + " EA.extension, EA.fecha fecha_ea, EA.documento_usuario, EA.estado estado_ea, EA.fecha_actualiza, "
                    + " EA.fecha_inicio_vigencia, EA.fecha_fin_vigencia, EA.meses_vigencia, EA.cod_categoria, "
                    + " EA.cod_categoria_tipo, EA.version"
                    + " FROM seguimiento.plan_seccion_detalle_adjuntos PSDA"
                    + " JOIN seguimiento.plan_seccion_detalle_adjuntos_evaluacion_adjuntos PSDAEA USING (cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_adjuntos, codigo_establecimiento)"
                    + " JOIN gestor.evaluacion_adjuntos EA ON (EA.cod_evaluacion=PSDAEA.cod_evaluacion AND EA.codigo_establecimiento=PSDAEA.codigo_establecimiento AND EA.cod_ciclo=PSDAEA.cod_ciclo "
                    + " AND EA.cod_detalle=PSDAEA.cod_detalle AND EA.cod_item=PSDAEA.cod_item AND EA.cod_adjunto=PSDAEA.cod_adjunto AND EA.cod_seccion=PSDAEA.cod_seccion_ea)"
                    + " WHERE PSDA.codigo_establecimiento=" + planSeccionDetallePK.getCodigoEstablecimiento() + " AND PSDA.cod_titulo=" + planSeccionDetallePK.getCodTitulo()
                    + " AND PSDA.cod_seccion=" + planSeccionDetallePK.getCodSeccion() + " AND PSDA.cod_seccion_detalle=" + planSeccionDetallePK.getCodSeccionDetalle()
                    + " AND EA.cod_evaluacion=" + codEvaluacion
            );
            rs = consulta.ejecutar(sql);

            Collection<PlanSeccionDetalleAdjuntos> planSeccionDetalleAdjuntosList = new ArrayList<>();
            while (rs.next()) {

                //int codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleAdjuntos
                //PlanSeccionDetalleAdjuntosPK planSeccionDetalleAdjuntosPK, 
                //Integer codCategoria, Integer codCategoriaTipo, String titulo, String descripcion, String documento
                PlanSeccionDetalleAdjuntos psda = new PlanSeccionDetalleAdjuntos(
                        new PlanSeccionDetalleAdjuntosPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle"), rs.getInt("cod_seccion_detalle_adjuntos")),
                        rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo"), rs.getString("titulo"), rs.getString("descripcion"), rs.getString("documento")
                );

                EvaluacionAdjuntos ea = new EvaluacionAdjuntos(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento"),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getLong("cod_adjunto")
                );
                ea.setNombre(rs.getString("nombre_ea"));
                ea.setDescripcion(rs.getString("descripcion_ea"));
                ea.setArchivo(rs.getString("archivo"));
                ea.setExtension(rs.getString("extension"));
                ea.setFecha(rs.getDate("fecha_ea"));
                ea.setDocumentoUsuario(rs.getString("documento_usuario"));
                ea.setEstado(rs.getString("estado_ea"));
                ea.setMesesVigencia(rs.getInt("meses_vigencia"));
                ea.setVersion(rs.getInt("version"));
                ea.setFechaInicioVigencia(rs.getDate("fecha_inicio_vigencia"));
                ea.setFechaFinVigencia(rs.getDate("fecha_fin_vigencia"));
                psda.setEvaluacionAdjuntos(ea);

                planSeccionDetalleAdjuntosList.add(psda);
            }
            return planSeccionDetalleAdjuntosList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public List<PlanSeccionDetalleItemAdjuntos> cargarPlanSeccionDetalleItemAdjuntos(PlanSeccionDetalleItemPK planSeccionDetalleItemPK, Long codEvaluacion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT PSDIA.codigo_establecimiento, PSDIA.cod_titulo, PSDIA.cod_seccion, PSDIA.cod_seccion_detalle, PSDIA.cod_seccion_detalle_item,"
                    + " PSDIA.cod_seccion_detalle_item_adjuntos, PSDIA.cod_categoria, PSDIA.cod_categoria_tipo,"
                    + " PSDIA.titulo, PSDIA.descripcion, PSDIA.documento, PSDIA.actividad, PSDIA.descripcion_general,"
                    + " EA.cod_evaluacion, EA.codigo_establecimiento, EA.cod_ciclo, EA.cod_seccion,"
                    + " EA.cod_detalle, EA.cod_item, EA.cod_adjunto, EA.nombre nombre_ea, EA.descripcion descripcion_ea, EA.archivo,"
                    + " EA.extension, EA.fecha fecha_ea, EA.documento_usuario, EA.estado estado_ea, EA.fecha_actualiza,"
                    + " EA.fecha_inicio_vigencia, EA.fecha_fin_vigencia, EA.meses_vigencia, EA.cod_categoria,"
                    + " EA.cod_categoria_tipo, EA.version"
                    + " FROM seguimiento.plan_seccion_detalle_item_adjuntos PSDIA"
                    + " JOIN seguimiento.plan_seccion_detalle_item_adjuntos_evaluacion_adjuntos PSDIAEA USING (cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item, cod_seccion_detalle_item_adjuntos, codigo_establecimiento)"
                    + " JOIN gestor.evaluacion_adjuntos EA ON (EA.cod_evaluacion=PSDIAEA.cod_evaluacion AND EA.codigo_establecimiento=PSDIAEA.codigo_establecimiento AND EA.cod_ciclo=PSDIAEA.cod_ciclo"
                    + " AND EA.cod_detalle=PSDIAEA.cod_detalle AND EA.cod_item=PSDIAEA.cod_item AND EA.cod_adjunto=PSDIAEA.cod_adjunto AND EA.cod_seccion=PSDIAEA.cod_seccion_ea)"
                    + " WHERE PSDIA.codigo_establecimiento=" + planSeccionDetalleItemPK.getCodigoEstablecimiento() + " AND PSDIA.cod_titulo=" + planSeccionDetalleItemPK.getCodTitulo()
                    + " AND PSDIA.cod_seccion= " + planSeccionDetalleItemPK.getCodSeccion() + "AND PSDIA.cod_seccion_detalle=" + planSeccionDetalleItemPK.getCodSeccionDetalle()
                    + " AND PSDIA.cod_seccion_detalle_item=" + planSeccionDetalleItemPK.getCodSeccionDetalleItem()
                    + " AND EA.cod_evaluacion=" + codEvaluacion
            );
            rs = consulta.ejecutar(sql);

            List<PlanSeccionDetalleItemAdjuntos> planSeccionDetalleAdjuntosList = new ArrayList<>();
            while (rs.next()) {
//codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem, int codSeccionDetalleItemAdjuntos
                PlanSeccionDetalleItemAdjuntos psdia = new PlanSeccionDetalleItemAdjuntos(
                        new PlanSeccionDetalleItemAdjuntosPK(
                                rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), 
                                rs.getInt("cod_seccion_detalle"), rs.getInt("cod_seccion_detalle_item"), rs.getInt("cod_seccion_detalle_item_adjuntos")
                        ), rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo"), rs.getString("titulo"), rs.getString("descripcion"), rs.getString("actividad"), rs.getString("descripcion_general"), rs.getString("documento"));

                EvaluacionAdjuntos ea = new EvaluacionAdjuntos(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento"),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getLong("cod_adjunto")
                );
                ea.setNombre(rs.getString("nombre_ea"));
                ea.setDescripcion(rs.getString("descripcion_ea"));
                ea.setArchivo(rs.getString("archivo"));
                ea.setExtension(rs.getString("extension"));
                ea.setFecha(rs.getDate("fecha_ea"));
                ea.setDocumentoUsuario(rs.getString("documento_usuario"));
                ea.setEstado(rs.getString("estado_ea"));
                ea.setMesesVigencia(rs.getInt("meses_vigencia"));
                ea.setVersion(rs.getInt("version"));
                ea.setFechaInicioVigencia(rs.getDate("fecha_inicio_vigencia"));
                ea.setFechaFinVigencia(rs.getDate("fecha_fin_vigencia"));
                psdia.setEvaluacionAdjuntos(ea);

                planSeccionDetalleAdjuntosList.add(psdia);
            }
            return planSeccionDetalleAdjuntosList;
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

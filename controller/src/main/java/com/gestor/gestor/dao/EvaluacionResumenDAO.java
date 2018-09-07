/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.entity.UtilFecha;
import com.gestor.entity.UtilTexto;
import com.gestor.gestor.EvaluacionResumen;
import com.gestor.gestor.EvaluacionResumenPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author juliano
 */
public class EvaluacionResumenDAO {

    private Connection conexion;

    public EvaluacionResumenDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarEvaluacionResumen(EvaluacionResumen er) throws SQLException {

        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " INSERT INTO gestor.evaluacion_resumen("
                    + " cod_evaluacion, codigo_establecimiento, cod_resumen, documento_usuario,"
                    + " cod_ciclo, ciclo, numeral, ciclo_calificacion, seccion, seccion_peso,"
                    + " seccion_orden, seccion_calificacion, detalle, detalle_peso, detalle_calificacion,"
                    + " detalle_orden, items, items_detalle, items_peso, items_orden,"
                    + " cod_puntaje, califica, fecha_registro)"
                    + " VALUES (" + er.getEvaluacionResumenPK().getCodEvaluacion() + ", " + er.getEvaluacionResumenPK().getCodigoEstablecimiento() + ", DEFAULT, '" + er.getDocumentoUsuario() + "',"
                    + " '" + er.getCodCiclo() + "', '" + er.getCiclo() + "', '" + er.getNumeral() + "', " + er.getCicloCalificacion() + ", '" + er.getSeccion() + "', " + er.getSeccionPeso() + ","
                    + " " + er.getSeccionOrden() + ", " + er.getSeccionCalificacion() + ", '" + er.getDetalle() + "', " + er.getDetallePeso() + ", " + er.getDetalleCalificacion() + ","
                    + " " + er.getDetalleOrden() + ", '" + er.getItems() + "', '" + er.getItemsDetalle() + "', " + er.getItemsPeso() + ", " + er.getItemsOrden() + ","
                    + UtilTexto.cadenaDefecto(er.getCodPuntaje(), UtilTexto.CARACTER_COMILLA) + ", " + er.getCalifica() + ", NOW());"
            );
            System.out.println("sql=>" + sql);
            consulta.actualizar(sql);
        } finally {

            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends EvaluacionResumen> cargarEvaluacionResumen(Long codEvaluacion, int codigoEstablecimiento, Date fechaResumen) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_evaluacion, codigo_establecimiento, cod_resumen, documento_usuario,"
                    + " cod_ciclo, ciclo, numeral, ciclo_calificacion, seccion, seccion_peso,"
                    + " seccion_orden, seccion_calificacion, detalle, detalle_peso, detalle_calificacion,"
                    + " detalle_orden, items, items_detalle, items_peso, items_orden,"
                    + " cod_puntaje, califica, fecha_registro"
                    + " FROM gestor.evaluacion_resumen"
                    + " WHERE cod_evaluacion=" + codEvaluacion + " AND codigo_establecimiento=" + codigoEstablecimiento
                    + " AND fecha_registro::DATE=" + UtilFecha.formatoFecha(fechaResumen, null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)
            );
            rs = consulta.ejecutar(sql);
            List<EvaluacionResumen> evaluacionResumens = new ArrayList<>();
            //EvaluacionResumenPK evaluacionResumenPK, String documentoUsuario, String codCiclo, String ciclo, String numeral, Double cicloCalificacion, 
            //String seccion, Double seccionPeso, Integer seccionOrden, Double seccionCalificacion, 
            //String detalle, Double detallePeso, Double detalleCalificacion, Integer detalleOrden, 
            //String items, String itemsDetalle, Double itemsPeso, Integer itemsOrden, 
            //String codPuntaje, Boolean califica, Date fechaRegistro
            while (rs.next()) {
                EvaluacionResumen er = new EvaluacionResumen(new EvaluacionResumenPK(codEvaluacion, codigoEstablecimiento, rs.getInt("cod_resumen")),
                        rs.getString("documento_usuario"), rs.getString("cod_ciclo"), rs.getString("ciclo"), rs.getString("numeral"), rs.getDouble("ciclo_calificacion"),
                        rs.getString("seccion"), rs.getDouble("seccion_peso"), rs.getInt("seccion_orden"), rs.getDouble("seccion_calificacion"),
                        rs.getString("detalle"), rs.getDouble("detalle_peso"), rs.getDouble("detalle_calificacion"), rs.getInt("detalle_orden"),
                        rs.getString("items"), rs.getString("items_detalle"), rs.getDouble("items_peso"), rs.getInt("items_orden"), rs.getString("cod_puntaje"), rs.getBoolean("califica"), rs.getDate("fecha_registro"));
                evaluacionResumens.add(er);
            }
            return evaluacionResumens;

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

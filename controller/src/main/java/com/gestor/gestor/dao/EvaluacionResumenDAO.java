/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.entity.UtilTexto;
import com.gestor.gestor.EvaluacionResumen;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            System.out.println("sql=>"+sql);
            consulta.actualizar(sql);
        } finally {

            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

}

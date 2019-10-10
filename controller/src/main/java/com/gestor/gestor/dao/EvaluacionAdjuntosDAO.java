/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.entity.App;
import com.gestor.entity.UtilFecha;
import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.AdjuntosCategoriaTipoPK;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.EvaluacionAdjuntos;
import com.gestor.publico.EvaluacionAdjuntosPK;
import com.gestor.publico.Responsable;
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
public class EvaluacionAdjuntosDAO {

    private Connection conexion;

    public EvaluacionAdjuntosDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertaEvaluacionAdjuntos(EvaluacionAdjuntos ea) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_adjuntos("
                    + " cod_evaluacion, codigo_establecimiento, cod_ciclo, cod_seccion,"
                    + " cod_detalle, cod_item, cod_adjunto, nombre, descripcion, archivo,"
                    + " extension, fecha, documento_usuario, cedula, estado, fecha_inicio_vigencia, fecha_fin_vigencia, meses_vigencia"
                    + " ,cod_categoria,cod_categoria_tipo, version)"
                    + " VALUES (" + ea.getEvaluacionAdjuntosPK().getCodEvaluacion() + ", " + ea.getEvaluacionAdjuntosPK().getCodigoEstablecimiento() + ","
                    + " '" + ea.getEvaluacionAdjuntosPK().getCodCiclo() + "', " + ea.getEvaluacionAdjuntosPK().getCodSeccion()
                    + " , " + ea.getEvaluacionAdjuntosPK().getCodDetalle() + ", " + ea.getEvaluacionAdjuntosPK().getCodItem() + ", " + ea.getEvaluacionAdjuntosPK().getCodAdjunto() + ","
                    + " '" + ea.getNombre() + "', '" + ea.getDescripcion() + "', '" + ea.getArchivo() + "', '" + ea.getExtension() + "',"
                    + " NOW(), '" + ea.getDocumentoUsuario() + "','" + ea.getResponsable().getCedula() + "', '" + ea.getEstado() + "'"
                    + " ," + UtilFecha.formatoFecha(ea.getFechaInicioVigencia(), null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)
                    + " ," + UtilFecha.formatoFecha(ea.getFechaFinVigencia(), null, UtilFecha.PATRON_FECHA_YYYYMMDD, UtilFecha.CARACTER_COMILLA)
                    + " ," + ea.getMesesVigencia() + "," + ea.getAdjuntosCategoria().getCodCategoria() + "," + ea.getAdjuntosCategoria().getAdjuntosCategoriaTipo().getAdjuntosCategoriaTipoPK().getCodCategoriaTipo() + "," + ea.getVersion() + ")"
                    + " ON CONFLICT (cod_evaluacion, codigo_establecimiento, cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_adjunto)"
                    + " DO UPDATE SET nombre=excluded.nombre, descripcion=excluded.descripcion, archivo=excluded.archivo,"
                    + " extension=excluded.extension, fecha_inicio_vigencia=excluded.fecha_inicio_vigencia,"
                    + " fecha_fin_vigencia=excluded.fecha_fin_vigencia, meses_vigencia=excluded.meses_vigencia"
                    + " ,cod_categoria=excluded.cod_categoria, cod_categoria_tipo=excluded.cod_categoria_tipo, cedula=excluded.cedula"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public String cargarDireccionArchivo(String archivo) throws SQLException{
        ResultSet rs = null;
        Consulta consulta = null;
        String direccion="";
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT nombre "
                    + " FROM gestor.evaluacion_adjuntos "
                    + " WHERE archivo='"+archivo+"'"
            );
            
            rs = consulta.ejecutar(sql);
            
            while (rs.next()) {
                direccion=rs.getString("nombre");
            }
            
            return direccion;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
            
    }

    public Collection<? extends EvaluacionAdjuntos> cargarEvaluacionAdjuntos(EvaluacionAdjuntosPK pk) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT EA.cod_evaluacion, EA.codigo_establecimiento, EA.cod_ciclo, EA.cod_seccion,"
                    + " EA.cod_detalle, EA.cod_item, EA.cod_adjunto, EA.nombre, EA.descripcion, EA.archivo,"
                    + " EA.extension, EA.fecha, EA.documento_usuario, EA.estado, EA.cod_categoria, EA.cod_categoria_tipo, EA.version,"
                    + " EA.fecha_inicio_vigencia, EA.fecha_fin_vigencia,"
                    + " ACT.cod_categoria_tipo, ACT.nombre AS nombre_act, ACT.descripcion descripcion_act,"
                    + " AC.cod_categoria, AC.nombre AS nombre_ac, AC.descripcion AS descripcion_ac, AC.meses_vigencia,"
                    + " R.cedula cedula_r, R.nombres nombres_r, R.apellidos apellidos_r, R.telefono telefono_r, R.correo correo_r, R.estado estado_r, R.codigo_establecimiento codigo_establecimiento_r"
                    + " FROM gestor.evaluacion_adjuntos EA"
                    + " JOIN gestor.adjuntos_categoria_tipo ACT USING (cod_categoria, cod_categoria_tipo)"
                    + " JOIN gestor.adjuntos_categoria AC USING (cod_categoria)"
                    + " JOIN public.responsable R USING (cedula)"
                    + " WHERE cod_evaluacion=" + pk.getCodEvaluacion() + " AND EA.codigo_establecimiento=" + pk.getCodigoEstablecimiento() + " AND cod_ciclo='" + pk.getCodCiclo() + "'"
                    + " AND cod_seccion=" + pk.getCodSeccion() + " AND cod_detalle=" + pk.getCodDetalle() + " AND cod_item=" + pk.getCodItem()
                    + " AND EA.estado='" + App.EVALUACION_ADJUNTOS_ESTADO_ACTIVO + "'"
            );
            rs = consulta.ejecutar(sql);
            Collection<EvaluacionAdjuntos> evaluacionAdjuntosList = new ArrayList<>();
            while (rs.next()) {
                EvaluacionAdjuntos ea = new EvaluacionAdjuntos(pk.getCodEvaluacion(), pk.getCodigoEstablecimiento(), pk.getCodCiclo(), pk.getCodSeccion(), pk.getCodDetalle(), pk.getCodItem(), rs.getLong("cod_adjunto"));
                ea.setNombre(rs.getString("nombre"));
                ea.setDescripcion(rs.getString("descripcion"));
                ea.setArchivo(rs.getString("archivo"));
                ea.setExtension(rs.getString("extension"));
                ea.setFecha(rs.getDate("fecha"));
                ea.setDocumentoUsuario(rs.getString("documento_usuario"));
                ea.setEstado(rs.getString("estado"));
                ea.setMesesVigencia(rs.getInt("meses_vigencia"));
                ea.setVersion(rs.getInt("version"));
                ea.setFechaInicioVigencia(rs.getDate("fecha_inicio_vigencia"));
                ea.setFechaFinVigencia(rs.getDate("fecha_fin_vigencia"));

                ea.setAdjuntosCategoria(new AdjuntosCategoria(rs.getInt("cod_categoria"), rs.getString("nombre_ac"), rs.getString("descripcion"), rs.getInt("meses_vigencia")));
                ea.getAdjuntosCategoria().setAdjuntosCategoriaTipo(new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo")), rs.getString("nombre_act")));

                Responsable r = new Responsable(rs.getString("cedula_r"), rs.getString("nombres_r"));
                r.setApellidos(rs.getString("apellidos_r"));
                r.setTelefono(rs.getString("telefono_r"));
                r.setCorreo(rs.getString("correo_r"));
                r.setEstado(rs.getBoolean("estado_r"));

                ea.setResponsable(r);

                evaluacionAdjuntosList.add(ea);
            }
            return evaluacionAdjuntosList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends EvaluacionAdjuntos> cargarListaAdjuntosCategoriaTipo(Integer codEstablecimiento,Integer codEvaluacion,String codCiclo,Integer codSeccion,Integer codDetalle, Integer codItem,Integer codCategoria,Integer codCatTipo) throws SQLException {    
        ResultSet rs = null;
        Consulta consulta = null;                
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT ea.archivo archivo, ea.version, ea.nombre " +
                    " from gestor.evaluacion_adjuntos ea " +
                    " where cod_evaluacion='"+codEvaluacion+"' and codigo_establecimiento='"+codEstablecimiento+"'  " +
                    " and cod_ciclo='"+codCiclo+"' and cod_seccion='"+codSeccion+"' " +
                    " and cod_detalle='"+codDetalle+"' and cod_item='"+codItem+"' " +
                    " and cod_categoria='"+codCategoria+"' and cod_categoria_tipo='"+codCatTipo+"' "
            );
            
            rs = consulta.ejecutar(sql);
            
            Collection<EvaluacionAdjuntos> evaluacionAdjuntos = new ArrayList<>();
            
            while (rs.next()) {
                EvaluacionAdjuntos ev=new EvaluacionAdjuntos(null, "", rs.getString("archivo"));
                evaluacionAdjuntos.add(ev);
            }                                    
            return evaluacionAdjuntos;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
           
                    

    public void actualizarEstadoEvaluacionAdjuntos(EvaluacionAdjuntos ea) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "UPDATE gestor.evaluacion_adjuntos"
                    + " SET estado='" + ea.getEstado() + "'"
                    + " WHERE cod_evaluacion=" + ea.getEvaluacionAdjuntosPK().getCodEvaluacion() + " AND codigo_establecimiento=" + ea.getEvaluacionAdjuntosPK().getCodigoEstablecimiento()
                    + " AND cod_ciclo='" + ea.getEvaluacionAdjuntosPK().getCodCiclo() + "' AND cod_seccion=" + ea.getEvaluacionAdjuntosPK().getCodSeccion() + " AND cod_detalle=" + ea.getEvaluacionAdjuntosPK().getCodDetalle()
                    + " AND cod_item=" + ea.getEvaluacionAdjuntosPK().getCodItem() + " AND cod_adjunto=" + ea.getEvaluacionAdjuntosPK().getCodAdjunto()
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Integer siguienteVersionCategoriaTipo(EvaluacionAdjuntosPK pk, int codCategoria, int codCategoriaTipo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT COALESCE(MAX(version)+1,1) AS next"
                    + " FROM gestor.evaluacion_adjuntos"
                    + " WHERE cod_evaluacion=" + pk.getCodEvaluacion() + " AND codigo_establecimiento=" + pk.getCodigoEstablecimiento()
                    + " AND cod_ciclo='" + pk.getCodCiclo() + "' AND cod_seccion=" + pk.getCodSeccion() + " AND cod_detalle=" + pk.getCodDetalle() + " AND cod_item=" + pk.getCodItem()
                    + " AND cod_categoria=" + codCategoria + " AND cod_categoria_tipo=" + codCategoriaTipo
            );
            rs = consulta.ejecutar(sql);
            rs.next();
            return rs.getInt("next");
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

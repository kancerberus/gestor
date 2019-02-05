/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.entity.App;
import com.gestor.gestor.Ciclo;
import com.gestor.gestor.ClaseHallazgo;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionPK;
import com.gestor.gestor.EvaluacionPlanAccion;
import com.gestor.gestor.EvaluacionPlanAccionDetalle;
import com.gestor.gestor.EvaluacionPlanAccionDetalleNotas;
import com.gestor.gestor.EvaluacionPlanAccionDetalleNotasPK;
import com.gestor.gestor.EvaluacionPlanAccionDetallePK;
import com.gestor.gestor.MotivoCorreccion;
import com.gestor.gestor.Seccion;
import com.gestor.gestor.SeccionDetalle;
import com.gestor.gestor.SeccionDetalleItems;
import com.gestor.gestor.SeccionDetalleItemsPK;
import com.gestor.gestor.SeccionDetallePK;
import com.gestor.gestor.SeccionPK;
import com.gestor.gestor.TipoAccion;
import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.PlanTrabajoObjetivo;
import com.gestor.publico.PlanTrabajoPrograma;
import com.gestor.publico.Responsable;
import com.gestor.publico.Usuarios;
import com.gestor.publico.UsuariosPK;
import com.gestor.publico.dao.ObjetivoDAO;
import com.gestor.publico.dao.ProgramaDAO;
import com.gestor.seguimiento.PlanTrabajoActividad;
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
                    + " cod_evaluacion, codigo_establecimiento, cod_plan, cod_plan_detalle, cod_plan_trabajo,"
                    + " cod_objetivo, cod_programa, cod_ciclo, cod_seccion, cod_detalle, cod_item, nombre, descripcion,"
                    + " estado, documento_usuario, fecha_registro, cedula, fecha_plazo,"
                    + " cod_clase_hallazgo, cod_tipo_accion, cod_motivo_correccion, descripcion_hallazgo, "
                    + " observaciones, cod_centrotrabajo, registro, eficacia, tipo_plan_accion)"
                    + " VALUES (" + epd.getEvaluacionPlanAccionDetallePK().getCodEvaluacion() + ", " + epd.getEvaluacionPlanAccionDetallePK().getCodigoEstablecimiento()
                    + " ," + epd.getEvaluacionPlanAccionDetallePK().getCodPlan()
                    + " ," + epd.getEvaluacionPlanAccionDetallePK().getCodPlanDetalle() + ", '"+epd.getEvaluacionPlanAccionDetallePK().getCodPlantrabajo()+"',"+epd.getPrograma().getCodPrograma()+", "+epd.getPrograma().getCodObjetivo()+", '" + epd.getCodCiclo() + "', " + epd.getCodSeccion() + ", " + epd.getCodDetalle()
                    + " ," + epd.getCodItem() + ", '" + epd.getNombre() + "', '" + epd.getDescripcion() + "', '" + epd.getEstado() + "','" + epd.getDocumentoUsuario()+"', " 
                    + " NOW(), '" + epd.getResponsable().getCedula() + "','" + epd.getFechaPlazo() + "', "+epd.getClasehallazgo().getCodClasehallazgo()
                    + " ," + epd.getTipoaccion().getCodTipoaccion()+", "+epd.getMotivocorreccion().getCodMotivocorreccion()+" "
                    + ",'" + epd.getDescripcionhallazgo()+"','"+epd.getObservaciones()+"', "+epd.getCentrotrabajo().getCodCentrotrabajo()+", "+epd.getRegistro()+", "+epd.getEficacia()+", '"+epd.getTipo()+"' )" 
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
                    "SELECT cod_evaluacion, EPAD.codigo_establecimiento, EPAD.cod_plan, EPAD.cod_plan_detalle, EPAD.cod_plan_trabajo,"
                    + " cod_ciclo, cod_seccion, cod_detalle, cod_item, EPAD.nombre, descripcion,"
                    + " EPAD.estado, EPAD.fecha_registro, EPAD.fecha_plazo, EPAD.fecha_actualiza,"
                    + " EPAD.descripcion_hallazgo, EPAD.observaciones, EPAD.registro, EPAD.eficacia, EPAD.tipo_plan_accion, "
                    + " U.documento_usuario, U.nombre AS nombre_usuario, U.apellido, U.usuario,"
                    + " R.cedula r_cedula, R.nombres r_nombres, R.apellidos r_apellidos, R.telefono r_telefono, R.correo r_correo, R.estado r_estado, R.codigo_establecimiento r_codigo_establecimiento,"
                    + " obj.cod_objetivo cod_o, obj.nombre nom_obj,"
                    + " pr.cod_programa cod_p, pr.nombre nom_p,"                    
                    + " ch.cod_clase_hallazgo cod_ch, ch.nombre nom_ch,"
                    + " ta.cod_tipo_accion cod_ta, ta.nombre nom_ta,"
                    + " mc.cod_motivo_correccion cod_mc, mc.nombre nom_mc,"
                    + " ct.cod_centrotrabajo cod_ct, ct.nombre nom_ct"
                    + " FROM gestor.evaluacion_plan_accion_detalle EPAD"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " JOIN public.plan_trabajo_objetivo obj USING (codigo_establecimiento, cod_plan_trabajo, cod_objetivo)"
                    + " JOIN public.plan_trabajo_programa pr USING (codigo_establecimiento, cod_plan_trabajo, cod_objetivo, cod_programa)"                            
                    + " JOIN gestor.clase_hallazgo ch USING (cod_clase_hallazgo)"                            
                    + " JOIN gestor.tipo_accion ta USING (cod_tipo_accion)"
                    + " JOIN gestor.motivo_correccion mc USING (cod_motivo_correccion)"
                    + " JOIN public.centro_trabajo ct USING (cod_centrotrabajo)"
                    + " JOIN public.responsable R USING (cedula)"
                    + " WHERE cod_evaluacion=" + codEvaluacion + " AND EPAD.codigo_establecimiento=" + codigoEstablecimiento 
                    + " AND cod_ciclo='" + codCiclo + "' AND cod_seccion=" + codSeccion + " AND cod_detalle=" + codDetalle + " AND cod_item=" + codItem
                    + " AND EPAD.estado<>'" + App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO + "'"
            );
            rs = consulta.ejecutar(sql);
            Collection<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles = new ArrayList<EvaluacionPlanAccionDetalle>();
            while (rs.next()) {
                EvaluacionPlanAccionDetalle epad = new EvaluacionPlanAccionDetalle(new EvaluacionPlanAccionDetallePK(codEvaluacion, codigoEstablecimiento, rs.getLong("cod_plan"), rs.getLong("cod_plan_detalle"), rs.getInt("cod_plan_trabajo")),
                        rs.getInt("cod_o"), rs.getInt("cod_p"), rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("estado"),
                        new Usuarios(
                                new UsuariosPK(rs.getString("documento_usuario")), rs.getString("nombre_usuario"), rs.getString("apellido"), rs.getString("usuario")
                        ), rs.getDate("fecha_registro"), rs.getDate("fecha_plazo"), rs.getDate("fecha_actualiza"), rs.getString("descripcion_hallazgo"),
                        rs.getString("observaciones"), rs.getBoolean("registro"), rs.getBoolean("eficacia"), rs.getString("tipo_plan_accion")
                );
                epad.setResponsable(new Responsable(rs.getString("r_cedula"), rs.getString("r_nombres"), rs.getString("r_apellidos"), rs.getString("r_correo"), rs.getString("r_telefono")));
                epad.setObjetivo(new PlanTrabajoObjetivo(0, 0, rs.getInt("cod_o"), rs.getString("nom_obj")));
                epad.setPrograma(new PlanTrabajoPrograma(0,0,0,rs.getInt("cod_p"),0, rs.getString("nom_p")));                
                epad.setClasehallazgo(new ClaseHallazgo(rs.getInt("cod_ch"), rs.getString("nom_ch")));
                epad.setTipoaccion(new TipoAccion(rs.getInt("cod_ta"), rs.getString("nom_ta")));
                epad.setMotivocorreccion(new MotivoCorreccion(rs.getInt("cod_mc"), rs.getString("nom_mc")));
                epad.setCentrotrabajo(new CentroTrabajo(rs.getInt("cod_ct"), rs.getString("nom_ct")));

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
                    + " SET estado='" + epad.getEstado() + "', documento_usuario='" + epad.getDocumentoUsuario() + "', fecha_actualiza=NOW(), fecha_finalizado=NOW()"
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
                    + " SET nombre='" + epd.getNombre() + "', descripcion='" + epd.getDescripcion() + "', fecha_actualiza=NOW(), cedula='" + epd.getResponsable().getCedula() + "',"
                    + " cod_programa="+epd.getPrograma().getCodPrograma()+",cod_clase_hallazgo="+epd.getClasehallazgo().getCodClasehallazgo()+","
                    + " cod_tipo_accion="+epd.getTipoaccion().getCodTipoaccion()+", cod_motivo_correccion="+epd.getMotivocorreccion().getCodMotivocorreccion()+","
                    + " fecha_plazo='"+epd.getFechaPlazo()+"', descripcion_hallazgo='"+epd.getDescripcionhallazgo()+"',"
                    + " observaciones='"+epd.getObservaciones()+"', cod_centrotrabajo='"+epd.getCentrotrabajo().getCodCentrotrabajo()+"', registro="+epd.getRegistro()+", eficacia="+epd.getEficacia()+""                                                                            
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

    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccion(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT EPAD.cod_evaluacion, EPAD.codigo_establecimiento, EPAD.cod_plan, EPAD.cod_plan_detalle, EPAD.cod_plan_trabajo,"
                    + " EPAD.cod_ciclo, EPAD.cod_seccion, EPAD.cod_detalle, EPAD.cod_item, EPAD.nombre AS epad_nombre, EPAD.descripcion,"
                    + " EPAD.estado, EPAD.fecha_registro, EPAD.fecha_plazo, EPAD.fecha_actualiza, EPAD.fecha_finalizado, EPAD.descripcion_hallazgo,"
                    + " EPAD.observaciones, EPAD.registro, EPAD.eficacia,"                               
                    + " U.documento_usuario, U.nombre AS nombre_usuario, U.apellido, U.usuario,"
                    + " SDI.cod_item AS sdi_cod_item, SDI.nombre AS sdi_nombre, SDI.detalle AS sdi_detalle, SDI.peso AS sdi_peso, SDI.activo AS sdi_activo, SDI.imagen AS sdi_imagen, SDI.orden AS sdi_orden, SDI.numeral AS sdi_numeral,"
                    + " SD.cod_detalle AS sd_cod_detalle, SD.nombre AS sd_nombre, SD.detalle AS sd_detalle, SD.orden AS sd_orden, SD.peso AS sd_peso, SD.imagen AS sd_imagen, SD.activo AS sd_activo, SD.numeral AS sd_numeral,"
                    + " S.cod_seccion AS s_cod_seccion, S.nombre AS s_nombre, S.activo AS s_activo, S.peso AS s_peso, S.imagen AS s_imagen, S.orden AS s_orden, S.numeral AS s_numeral,"
                    + " E.cod_evaluacion AS e_cod_evaluacion, E.documento_usuario AS e_documento_usuario, E.fecha AS e_fecha, E.fecha_registro AS e_fecha_registro, E.estado AS e_estado,"
                    + " ES.nombre AS es_nombre,"
                    + " C.cod_ciclo AS c_cod_ciclo, C.nombre AS c_nombre, C.numeral AS c_numeral,"
                    + " p.cod_programa cod_p, p.nombre nom_p,"
                    + " ch.cod_clase_hallazgo cod_ch, ch.nombre nom_ch,"
                    + " ta.cod_tipo_accion cod_ta, ta.nombre nom_ta,"
                    + " mc.cod_motivo_correccion cod_mc, mc.nombre nom_mc,"
                    + " ct.cod_centrotrabajo cod_ct, ct.nombre nom_ct,"                    
                    + " R.cedula r_cedula, R.nombres r_nombres, R.apellidos r_apellidos, R.telefono r_telefono, R.correo r_correo, R.estado r_estado, R.codigo_establecimiento r_codigo_establecimiento"
                    + " FROM gestor.evaluacion_plan_accion_detalle EPAD"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " JOIN gestor.evaluacion_plan_accion EPA USING (cod_evaluacion, codigo_establecimiento, cod_plan)"
                    + " JOIN gestor.evaluacion E USING (cod_evaluacion, codigo_establecimiento)"
                    + " JOIN public.establecimiento ES USING (codigo_establecimiento)"
                    + " JOIN gestor.seccion_detalle_items SDI USING (cod_seccion, cod_detalle, cod_ciclo, cod_item)"
                    + " JOIN gestor.seccion_detalle SD USING (cod_seccion, cod_ciclo, cod_detalle)"
                    + " JOIN gestor.seccion S USING (cod_seccion, cod_ciclo)"
                    + " JOIN gestor.ciclo C USING (cod_ciclo)"
                    + " JOIN public.plan_trabajo_programa p USING (cod_programa)"
                    + " JOIN gestor.clase_hallazgo ch USING (cod_clase_hallazgo)"                            
                    + " JOIN gestor.tipo_accion ta USING (cod_tipo_accion)"
                    + " JOIN gestor.motivo_correccion mc USING (cod_motivo_correccion)"
                    + " JOIN public.centro_trabajo ct USING (cod_centrotrabajo)"
                    + " JOIN public.responsable R ON (R.cedula=EPAD.cedula)"                    
                    + condicion
                    + " ORDER BY C.numeral, S.numeral, SD.numeral, SDI.numeral"
            );
            System.out.println("condicion => " + condicion);
            System.out.println("sql => " + sql);
            rs = consulta.ejecutar(sql);
            Collection<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles = new ArrayList<EvaluacionPlanAccionDetalle>();            
            
            while (rs.next()) {
                EvaluacionPlanAccionDetalle epad = new EvaluacionPlanAccionDetalle(new EvaluacionPlanAccionDetallePK(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento"), rs.getLong("cod_plan"), rs.getLong("cod_plan_detalle"), rs.getInt("cod_plan_detalle")),
                        rs.getInt("cod_objetivo"), rs.getInt("cod_programa"),rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getString("epad_nombre"), rs.getString("descripcion"), rs.getString("estado"),
                        new Usuarios(
                                new UsuariosPK(rs.getString("documento_usuario")), rs.getString("nombre_usuario"), rs.getString("apellido"), rs.getString("usuario")
                        ), rs.getDate("fecha_registro"
                        ), rs.getDate("fecha_plazo"),rs.getDate("fecha_finalizado"), rs.getString("descripcion_hallazgo"),
                        rs.getString("observaciones"), rs.getBoolean("registro"), rs.getBoolean("eficacia"), rs.getString("tipo")
                );
                epad.setResponsable(new Responsable(rs.getString("r_cedula"), rs.getString("r_nombres"), rs.getString("r_apellidos"), rs.getString("r_correo"), rs.getString("r_telefono")));
                epad.setPrograma(new PlanTrabajoPrograma(0,0,0,rs.getInt("cod_p"),0, rs.getString("nom_p")));
                epad.setClasehallazgo(new ClaseHallazgo(rs.getInt("cod_ch"), rs.getString("nom_ch")));
                epad.setTipoaccion(new TipoAccion(rs.getInt("cod_ta"), rs.getString("nom_ta")));
                epad.setMotivocorreccion(new MotivoCorreccion(rs.getInt("cod_mc"), rs.getString("nom_mc")));
                epad.setCentrotrabajo(new CentroTrabajo(rs.getInt("cod_ct"), rs.getString("nom_ct")));                
                //evaluacion
                Evaluacion e = new Evaluacion(new EvaluacionPK(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento")), rs.getString("documento_usuario"),
                        rs.getDate("e_fecha"), rs.getDate("e_fecha_registro"), rs.getString("e_estado"));
                e.setUsuarios(new Usuarios(new UsuariosPK(rs.getString("e_documento_usuario"))));
                e.setEstablecimiento(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("es_nombre")));
                epad.setEvaluacion(e);

                epad.setEstablecimiento(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("es_nombre")));

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
                epad.setSeccionDetalleItems(sdi);

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

    public void insertaEvaluacionPlanAccionDetalleNotas(EvaluacionPlanAccionDetalleNotas epadn) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            EvaluacionPlanAccionDetalleNotasPK pk = epadn.getEvaluacionPlanAccionDetalleNotasPK();
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_plan_accion_detalle_notas("
                    + " cod_evaluacion, codigo_establecimiento, cod_plan, cod_plan_detalle, "
                    + " cod_nota, cod_plan_trabajo, documento_usuario, estado, nombre, descripcion, fecha_registro)"
                    + " VALUES (" + pk.getCodEvaluacion() + ", " + pk.getCodigoEstablecimiento() + ", " + pk.getCodPlan() + ", " + pk.getCodPlanDetalle()+",  "
                    + " DEFAULT, "+pk.getCodPlantrabajo()+", '" + epadn.getDocumentoUsuario() + "', '" + epadn.getEstado() + "', '" + epadn.getNombre() + "', '" + epadn.getDescripcion() + "', NOW());"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public List<EvaluacionPlanAccionDetalleNotas> cargarEvaluacionPlanAccionDetalleNotasList(EvaluacionPlanAccionDetalleNotasPK pk) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_evaluacion, codigo_establecimiento, cod_plan, cod_plan_detalle, cod_plan_trabajo,"
                    + " cod_nota, documento_usuario, U.nombre as nombreus, U.apellido as apellidous, estado, epadn.nombre as nombrepa, descripcion, fecha_registro"
                    + " FROM gestor.evaluacion_plan_accion_detalle_notas epadn"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " WHERE cod_evaluacion=" + pk.getCodEvaluacion() + " AND codigo_establecimiento=" + pk.getCodigoEstablecimiento()
                    + " AND cod_plan=" + pk.getCodPlan() + " AND cod_plan_detalle=" + pk.getCodPlanDetalle()
            );
            rs = consulta.ejecutar(sql);
            List<EvaluacionPlanAccionDetalleNotas> evaluacionPlanAccionDetalleNotasList = new ArrayList<>();

            while (rs.next()) {
                pk.setCodNota(rs.getLong("cod_nota"));
                EvaluacionPlanAccionDetalleNotas epadn = new EvaluacionPlanAccionDetalleNotas(
                        new EvaluacionPlanAccionDetalleNotasPK(pk.getCodEvaluacion(), pk.getCodigoEstablecimiento(), pk.getCodPlan(), pk.getCodPlanDetalle(), rs.getLong("cod_nota"), rs.getInt("cod_plan_trabajo")),
                        rs.getString("documento_usuario"), rs.getString("estado"), rs.getString("nombrepa"), rs.getString("descripcion"),
                        new Usuarios(null, rs.getString("nombreus"), rs.getString("apellidous"))
                );
                epadn.setFechaRegistro(rs.getDate("fecha_registro"));
                evaluacionPlanAccionDetalleNotasList.add(epadn);
            }
            return evaluacionPlanAccionDetalleNotasList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void upsertEvaluacionPlanAccionDetalleNotas(EvaluacionPlanAccionDetalleNotas epadn) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            EvaluacionPlanAccionDetalleNotasPK pk = epadn.getEvaluacionPlanAccionDetalleNotasPK();
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.evaluacion_plan_accion_detalle_notas("
                    + " cod_evaluacion, codigo_establecimiento, cod_plan,  cod_plan_detalle, "
                    + " cod_nota, documento_usuario, estado, nombre, descripcion, fecha_registro)"
                    + " VALUES (" + pk.getCodEvaluacion() + ", " + pk.getCodigoEstablecimiento() + ", " + pk.getCodPlan() + ", " + pk.getCodPlanDetalle()
                    + " , " + pk.getCodNota() + ", '" + epadn.getDocumentoUsuario() + "', '" + epadn.getEstado() + "', '" + epadn.getNombre() + "', '" + epadn.getDescripcion() + "', NOW())"
                    + " ON CONFLICT (cod_evaluacion, codigo_establecimiento, cod_plan, cod_plan_detalle, cod_nota)"
                    + " DO UPDATE SET documento_usuario=excluded.documento_usuario, descripcion=excluded.descripcion"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

}

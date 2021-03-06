/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.entity.App;
import com.gestor.gestor.Ciclo;
import com.gestor.gestor.Dirigida;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.EvaluacionCapacitacion;
import com.gestor.gestor.EvaluacionCapacitacionDetalle;
import com.gestor.gestor.EvaluacionCapacitacionDetalleNotas;
import com.gestor.gestor.EvaluacionCapacitacionDetalleNotasPK;
import com.gestor.gestor.EvaluacionCapacitacionDetallePK;
import com.gestor.gestor.EvaluacionPK;
import com.gestor.gestor.Facilitador;
import com.gestor.gestor.Modalidad;
import com.gestor.gestor.Recursos;
import com.gestor.gestor.Seccion;
import com.gestor.gestor.SeccionDetalle;
import com.gestor.gestor.SeccionDetalleItems;
import com.gestor.gestor.SeccionDetalleItemsPK;
import com.gestor.gestor.SeccionDetallePK;
import com.gestor.gestor.SeccionPK;
import com.gestor.gestor.TecnicaCapacitacion;
import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Responsable;
import com.gestor.publico.Usuarios;
import com.gestor.publico.UsuariosPK;
import com.gestor.seguimiento.PlanCapacitacion;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
                    "SELECT ECD.cod_evaluacion, ECD.codigo_establecimiento, ECD.cod_capacitacion, ECD.cod_capacitacion_detalle,"
                    + " ECD.cod_ciclo, ECD.cod_seccion, ECD.cod_detalle, ECD.cod_item, ECD.nombre, ECD.descripcion,"
                    + " ECD.estado,ECD.fecha_registro,ECD.fecha_plazo, ECD.fecha_actualiza,"                            
                    + " U.documento_usuario, U.nombre AS nombre_usuario, U.apellido, U.usuario,"
                    + " R.cedula r_cedula, R.nombres r_nombres, R.apellidos r_apellidos, R.telefono r_telefono, R.correo r_correo, R.estado r_estado, R.codigo_establecimiento r_codigo_establecimiento,"
                    + " ct.cod_centrotrabajo cod_ct, ct.nombre nom_ct,"
                    + " mo.cod_modalidad cod_mo, mo.nombre nom_mo,"
                    + " tec.cod_tecnica cod_tec, tec.nombre nom_tec,"
                    + " f.cod_facilitador cod_f, f.nombre nom_f,"                    
                    + " da.cod_dirigida cod_da, da.nombre nom_da,"
                    + " re.cod_recursos cod_re, re.nombre nom_re,"
                    + " pc.cod_plan_capacitacion cod_pc, pc.descripcion desc_pc"                            
                    + " FROM gestor.evaluacion_capacitacion_detalle ECD"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " INNER JOIN public.responsable R on (R.cedula=ECD.cedula) "
                    + " INNER JOIN public.centro_trabajo ct ON (ct.cod_centrotrabajo=ECD.cod_centrotrabajo and ct.codigo_establecimiento=ECD.codigo_establecimiento)"
                    + " JOIN gestor.modalidad mo USING (cod_modalidad)"
                    + " JOIN gestor.tecnica_capacitacion tec USING (cod_tecnica)"
                    + " JOIN gestor.facilitador f USING (cod_facilitador)"
                    + " JOIN gestor.dirigida da USING (cod_dirigida)"
                    + " JOIN gestor.recursos re USING (cod_recursos)"
                    + " INNER JOIN seguimiento.plan_capacitacion pc ON (pc.cod_plan_capacitacion=ECD.cod_plan_capacitacion and pc.codigo_establecimiento=ECD.codigo_establecimiento)"                            
                    + " WHERE ECD.cod_evaluacion=" + codEvaluacion + " AND ECD.codigo_establecimiento=" + codigoEstablecimiento
                    + " AND ECD.cod_ciclo='" + codCiclo + "' AND ECD.cod_seccion=" + codSeccion + " AND ECD.cod_detalle=" + codDetalle + " AND ECD.cod_item=" + codItem
                    + " AND ECD.estado<>'" + App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ELIMINADO + "'"
            );
            rs = consulta.ejecutar(sql);

            Collection<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles = new ArrayList<>();
            while (rs.next()) {

                EvaluacionCapacitacionDetalle ecd = new EvaluacionCapacitacionDetalle(new EvaluacionCapacitacionDetallePK(codEvaluacion, codigoEstablecimiento, rs.getLong("cod_capacitacion"), rs.getLong("cod_capacitacion_detalle")),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("estado"),
                        new Usuarios(
                                new UsuariosPK(rs.getString("documento_usuario")), rs.getString("nombre_usuario"), rs.getString("apellido"), rs.getString("usuario")
                        ), rs.getDate("fecha_registro"), rs.getDate("fecha_plazo"), rs.getDate("fecha_actualiza")
                );
                ecd.setCentrotrabajo(new CentroTrabajo(rs.getInt("cod_ct"), rs.getString("nom_ct")));
                ecd.setModalidad(new Modalidad(rs.getInt("cod_mo"), rs.getString("nom_mo")));
                ecd.setTecnica(new TecnicaCapacitacion(rs.getInt("cod_tec"), rs.getString("nom_tec")));
                ecd.setFacilitador(new Facilitador(rs.getInt("cod_f"), rs.getString("nom_f")));
                ecd.setDirigida(new Dirigida(rs.getInt("cod_da"), rs.getString("nom_da")));
                ecd.setRecursos(new Recursos(rs.getInt("cod_re"), rs.getString("nom_re")));
                ecd.setResponsable(new Responsable(rs.getString("r_cedula"), rs.getString("r_nombres"), rs.getString("r_apellidos"), rs.getString("r_correo"), rs.getString("r_telefono")));
                ecd.setPlancapacitacion(new PlanCapacitacion(null, rs.getInt("cod_pc"), rs.getString("desc_pc"), null, null, null, null));
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
                    + " estado, documento_usuario, cedula, fecha_plazo, cod_centrotrabajo, cod_modalidad, cod_tecnica, cod_facilitador,"
                    + " cod_dirigida, cod_recursos, cod_plan_capacitacion )"
                    + " VALUES (" + ecd.getEvaluacionCapacitacionDetallePK().getCodEvaluacion() + ", " + ecd.getEvaluacionCapacitacionDetallePK().getCodigoEstablecimiento()
                    + " , " + ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacion() + " , " + ecd.getEvaluacionCapacitacionDetallePK().getCodCapacitacionDetalle() + ","
                    + " '" + ecd.getCodCiclo() + "', " + ecd.getCodSeccion() + ", " + ecd.getCodDetalle() + ", " + ecd.getCodItem() + ", '" + ecd.getNombre() + "', '" + ecd.getDescripcion() + "'"
                    + " , '" + ecd.getEstado() + "','" + ecd.getDocumentoUsuario() + "','" + ecd.getResponsable().getCedula() + "','"+ecd.getFechaPlazo()+"', '"+ecd.getCentrotrabajo().getCodCentrotrabajo()+"'"
                    + ", '"+ecd.getModalidad().getCodModalidad()+"', '"+ecd.getTecnica().getCodTecnica()+"', '"+ecd.getFacilitador().getCodFacilitador()+"'"
                    + ", '"+ecd.getDirigida().getCodDirigida()+"', '"+ecd.getRecursos().getCodRecursos()+"', '"+ecd.getPlancapacitacion().getCodPlancapacitacion()+"');"
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
                    + " SET estado='" + ecd.getEstado() + "', documento_usuario='" + ecd.getDocumentoUsuario() + "', fecha_actualiza=NOW(), fecha_finalizado=NOW(),"
                    + " cod_centrotrabajo="+ecd.getCentrotrabajo().getCodCentrotrabajo()+", cod_modalidad="+ecd.getModalidad().getCodModalidad()+","
                    + " cod_tecnica="+ecd.getTecnica().getCodTecnica()+", cod_facilitador="+ecd.getFacilitador().getCodFacilitador()+","
                    + " cod_dirigida= "+ecd.getDirigida().getCodDirigida()+", cod_recursos="+ecd.getRecursos().getCodRecursos()+""                            
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
                    + " SET nombre='" + ecd.getNombre() + "', descripcion='" + ecd.getDescripcion() + "', fecha_actualiza=NOW(), cedula='" + ecd.getResponsable().getCedula() + "' ,"                                                                                              
                    + " cod_centrotrabajo="+ecd.getCentrotrabajo().getCodCentrotrabajo()+", cod_modalidad= "+ecd.getModalidad().getCodModalidad()+","
                    + " cod_tecnica="+ecd.getTecnica().getCodTecnica()+", cod_facilitador="+ecd.getFacilitador().getCodFacilitador()+","
                    + " cod_dirigida= "+ecd.getDirigida().getCodDirigida()+", cod_recursos="+ecd.getRecursos().getCodRecursos()+""
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
                    + " ECD.estado, ECD.fecha_registro, ECD.fecha_plazo, ECD.fecha_actualiza, ECD.fecha_finalizado, "                                  
                    + " U.documento_usuario, U.nombre AS nombre_usuario, U.apellido, U.usuario,"
                    + " SDI.cod_item AS sdi_cod_item, SDI.nombre AS sdi_nombre, SDI.detalle AS sdi_detalle, SDI.peso AS sdi_peso, SDI.activo AS sdi_activo, SDI.imagen AS sdi_imagen, SDI.orden AS sdi_orden, SDI.numeral AS sdi_numeral,"
                    + " SD.cod_detalle AS sd_cod_detalle, SD.nombre AS sd_nombre, SD.detalle AS sd_detalle, SD.orden AS sd_orden, SD.peso AS sd_peso, SD.imagen AS sd_imagen, SD.activo AS sd_activo, SD.numeral AS sd_numeral,"
                    + " S.cod_seccion AS s_cod_seccion, S.nombre AS s_nombre, S.activo AS s_activo, S.peso AS s_peso, S.imagen AS s_imagen, S.orden AS s_orden, S.numeral AS s_numeral,"
                    + " E.cod_evaluacion AS e_cod_evaluacion, E.documento_usuario AS e_documento_usuario, E.fecha AS e_fecha, E.fecha_registro AS e_fecha_registro, E.estado AS e_estado,"
                    + " ES.nombre AS es_nombre,"
                    + " C.cod_ciclo AS c_cod_ciclo, C.nombre AS c_nombre, C.numeral AS c_numeral,"
                    + " R.cedula r_cedula, R.nombres r_nombres, R.apellidos r_apellidos, R.telefono r_telefono, R.correo r_correo, R.estado r_estado, R.codigo_establecimiento r_codigo_establecimiento,"
                    + " ct.cod_centrotrabajo cod_ct, ct.nombre nom_ct,"
                    + " mo.cod_modalidad cod_mo, mo.nombre nom_mo,"
                    + " tec.cod_tecnica cod_tec, tec.nombre nom_tec,"
                    + " f.cod_facilitador cod_f, f.nombre nom_f,"                    
                    + " da.cod_dirigida cod_da, da.nombre nom_da,"
                    + " re.cod_recursos cod_re, re.nombre nom_re,"
                    + " pc.cod_plan_capacitacion codpc, pc.descripcion descpc "
                    + " FROM gestor.evaluacion_capacitacion_detalle ECD"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " JOIN gestor.evaluacion_capacitacion EC USING (cod_evaluacion, codigo_establecimiento, cod_capacitacion)"
                    + " JOIN gestor.evaluacion E USING (cod_evaluacion, codigo_establecimiento)"
                    + " JOIN public.establecimiento ES USING (codigo_establecimiento)"
                    + " JOIN gestor.seccion_detalle_items SDI USING (cod_seccion, cod_detalle, cod_ciclo, cod_item)"
                    + " JOIN gestor.seccion_detalle SD USING (cod_seccion, cod_ciclo, cod_detalle)"
                    + " JOIN gestor.seccion S USING (cod_seccion, cod_ciclo)"
                    + " JOIN gestor.ciclo C USING (cod_ciclo)"
                    + " INNER JOIN public.responsable R on (R.cedula=ECD.cedula)    "
                    + " INNER JOIN public.centro_trabajo ct ON (ct.cod_centrotrabajo=ECD.cod_centrotrabajo and ct.codigo_establecimiento=ECD.codigo_establecimiento )"
                    + " JOIN gestor.modalidad mo USING (cod_modalidad)"
                    + " JOIN gestor.tecnica_capacitacion tec USING (cod_tecnica)"
                    + " JOIN gestor.facilitador f USING (cod_facilitador)"
                    + " JOIN gestor.dirigida da USING (cod_dirigida)"
                    + " JOIN gestor.recursos re USING (cod_recursos)"
                    + " INNER JOIN seguimiento.plan_capacitacion pc ON (pc.cod_plan_capacitacion=ECD.cod_plan_capacitacion and pc.codigo_establecimiento=ECD.codigo_establecimiento)"                            
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
                        ), rs.getDate("fecha_registro"), rs.getDate("fecha_plazo"), rs.getDate("fecha_finalizado")
                );
                ecd.setCentrotrabajo(new CentroTrabajo(rs.getInt("cod_ct"), rs.getString("nom_ct")));
                ecd.setModalidad(new Modalidad(rs.getInt("cod_mo"), rs.getString("nom_mo")));
                ecd.setTecnica(new TecnicaCapacitacion(rs.getInt("cod_tec"), rs.getString("nom_tec")));
                ecd.setFacilitador(new Facilitador(rs.getInt("cod_f"), rs.getString("nom_f")));
                ecd.setDirigida(new Dirigida(rs.getInt("cod_da"), rs.getString("nom_da")));
                ecd.setRecursos(new Recursos(rs.getInt("cod_re"), rs.getString("nom_re")));
                ecd.setResponsable(new Responsable(rs.getString("r_cedula"), rs.getString("r_nombres"), rs.getString("r_apellidos"), rs.getString("r_correo"), rs.getString("r_telefono")));
                ecd.setPlancapacitacion(new PlanCapacitacion(0, rs.getInt("codpc"), rs.getString("descpc"), null, "", null, 0));
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
                
                SimpleDateFormat dd = new SimpleDateFormat("dd/MM/yyyy");       
                int dias = 0; boolean activo = false;
                Calendar calendar; Date aux;
                
                Date fac= new Date();
                long fp=ecd.getFechaPlazo().getTime();
                long fa=fac.getTime();                 
                if(fa>fp && ecd.getEstado().equals("A")){
                    dias=(int) ((fp-fa)/86400000);
                }
                if(fa<fp){                    
                    do{       
                        calendar = Calendar.getInstance();           
                        calendar.add(Calendar.DAY_OF_YEAR, dias);
                        aux = calendar.getTime();

                        if(dd.format(aux).equals(dd.format(ecd.getFechaPlazo())))
                            activo = true; 
                        else
                            dias++;
                    }while(activo != true);
                    
                }
                ecd.setDiasRestantes(dias);

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
    
    
    public Collection<? extends EvaluacionCapacitacionDetalle> cargarListaEvaluacionCapacitacionDetallepc(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT ECD.cod_evaluacion, ECD.codigo_establecimiento, ECD.cod_capacitacion, ECD.cod_capacitacion_detalle,"
                    + " ECD.cod_ciclo, ECD.cod_seccion, ECD.cod_detalle, ECD.cod_item, ECD.nombre AS ecd_nombre, ECD.descripcion,"
                    + " ECD.estado, ECD.fecha_registro, ECD.fecha_plazo, ECD.fecha_actualiza, ECD.fecha_finalizado, ECD.cod_plan_capacitacion,"                                  
                    + " U.documento_usuario, U.nombre AS nombre_usuario, U.apellido, U.usuario,"
                    + " SDI.cod_item AS sdi_cod_item, SDI.nombre AS sdi_nombre, SDI.detalle AS sdi_detalle, SDI.peso AS sdi_peso, SDI.activo AS sdi_activo, SDI.imagen AS sdi_imagen, SDI.orden AS sdi_orden, SDI.numeral AS sdi_numeral,"
                    + " SD.cod_detalle AS sd_cod_detalle, SD.nombre AS sd_nombre, SD.detalle AS sd_detalle, SD.orden AS sd_orden, SD.peso AS sd_peso, SD.imagen AS sd_imagen, SD.activo AS sd_activo, SD.numeral AS sd_numeral,"
                    + " S.cod_seccion AS s_cod_seccion, S.nombre AS s_nombre, S.activo AS s_activo, S.peso AS s_peso, S.imagen AS s_imagen, S.orden AS s_orden, S.numeral AS s_numeral,"
                    + " E.cod_evaluacion AS e_cod_evaluacion, E.documento_usuario AS e_documento_usuario, E.fecha AS e_fecha, E.fecha_registro AS e_fecha_registro, E.estado AS e_estado,"
                    + " ES.nombre AS es_nombre,"
                    + " C.cod_ciclo AS c_cod_ciclo, C.nombre AS c_nombre, C.numeral AS c_numeral,"
                    + " R.cedula r_cedula, R.nombres r_nombres, R.apellidos r_apellidos, R.telefono r_telefono, R.correo r_correo, R.estado r_estado, R.codigo_establecimiento r_codigo_establecimiento,"
                    + " ct.cod_centrotrabajo cod_ct, ct.nombre nom_ct,"
                    + " mo.cod_modalidad cod_mo, mo.nombre nom_mo,"
                    + " tec.cod_tecnica cod_tec, tec.nombre nom_tec,"
                    + " f.cod_facilitador cod_f, f.nombre nom_f,"                    
                    + " da.cod_dirigida cod_da, da.nombre nom_da,"
                    + " re.cod_recursos cod_re, re.nombre nom_re,"
                    + " pc.cod_plan_capacitacion cod_pc, pc.descripcion nom_pc, pc.meta meta_pc"
                    + " FROM gestor.evaluacion_capacitacion_detalle ECD"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " JOIN gestor.evaluacion_capacitacion EC USING (cod_evaluacion, codigo_establecimiento, cod_capacitacion)"
                    + " JOIN gestor.evaluacion E USING (cod_evaluacion, codigo_establecimiento)"
                    + " JOIN public.establecimiento ES USING (codigo_establecimiento)"
                    + " JOIN gestor.seccion_detalle_items SDI USING (cod_seccion, cod_detalle, cod_ciclo, cod_item)"
                    + " JOIN gestor.seccion_detalle SD USING (cod_seccion, cod_ciclo, cod_detalle)"
                    + " JOIN gestor.seccion S USING (cod_seccion, cod_ciclo)"
                    + " JOIN gestor.ciclo C USING (cod_ciclo)"
                    + " INNER JOIN public.responsable R on (R.cedula=ECD.cedula) "
                    + " INNER JOIN public.centro_trabajo ct ON (ct.cod_centrotrabajo=ECD.cod_centrotrabajo and ct.codigo_establecimiento=ECD.codigo_establecimiento)"
                    + " JOIN gestor.modalidad mo USING (cod_modalidad)"
                    + " JOIN gestor.tecnica_capacitacion tec USING (cod_tecnica)"
                    + " JOIN gestor.facilitador f USING (cod_facilitador)"
                    + " JOIN gestor.dirigida da USING (cod_dirigida)"
                    + " JOIN gestor.recursos re USING (cod_recursos)"
                    + " INNER JOIN seguimiento.plan_capacitacion pc ON (pc.cod_plan_capacitacion=ECD.cod_plan_capacitacion and pc.codigo_establecimiento=ECD.codigo_establecimiento)"                            
                    + condicion
                    + " GROUP BY ECD.cod_evaluacion, ECD.codigo_establecimiento, ECD.cod_capacitacion, ECD.cod_capacitacion_detalle, ECD.cod_ciclo, ECD.cod_seccion, ECD.cod_detalle, ECD.cod_item, ECD.nombre , ECD.descripcion, ECD.estado, ECD.fecha_registro, ECD.fecha_plazo, ECD.fecha_actualiza, ECD.fecha_finalizado, ECD.cod_plan_capacitacion, U.documento_usuario, U.nombre , U.apellido, U.usuario, SDI.cod_item , SDI.nombre , SDI.detalle , SDI.peso , SDI.activo , SDI.imagen , SDI.orden , SDI.numeral , SD.cod_detalle , SD.nombre , SD.detalle , SD.orden , SD.peso , SD.imagen , SD.activo , SD.numeral , S.cod_seccion , S.nombre , S.activo , S.peso , S.imagen , S.orden , S.numeral , E.cod_evaluacion , E.documento_usuario , E.fecha , E.fecha_registro , E.estado , ES.nombre , C.cod_ciclo , C.nombre , C.numeral , R.cedula , R.nombres , R.apellidos , R.telefono , R.correo , R.estado , R.codigo_establecimiento , ct.cod_centrotrabajo , ct.nombre , mo.cod_modalidad , mo.nombre , tec.cod_tecnica , tec.nombre , f.cod_facilitador , f.nombre , da.cod_dirigida , da.nombre , re.cod_recursos , re.nombre , pc.cod_plan_capacitacion , pc.descripcion , pc.meta  "
                    + " ORDER BY C.numeral, S.numeral, SD.numeral, SDI.numeral"
            );
            rs = consulta.ejecutar(sql);

            Collection<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles = new ArrayList<>();
            while (rs.next()) {

                EvaluacionCapacitacionDetalle ecd = new EvaluacionCapacitacionDetalle(new EvaluacionCapacitacionDetallePK(rs.getLong("cod_evaluacion"), rs.getInt("codigo_establecimiento"), rs.getLong("cod_capacitacion"), rs.getLong("cod_capacitacion_detalle")),
                        rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getString("ecd_nombre"), rs.getString("descripcion"), rs.getString("estado"),
                        new Usuarios(
                                new UsuariosPK(rs.getString("documento_usuario")), rs.getString("nombre_usuario"), rs.getString("apellido"), rs.getString("usuario")
                        ), rs.getDate("fecha_registro"), rs.getDate("fecha_plazo"), rs.getDate("fecha_finalizado")
                );
                ecd.setCentrotrabajo(new CentroTrabajo(rs.getInt("cod_ct"), rs.getString("nom_ct")));
                ecd.setModalidad(new Modalidad(rs.getInt("cod_mo"), rs.getString("nom_mo")));
                ecd.setTecnica(new TecnicaCapacitacion(rs.getInt("cod_tec"), rs.getString("nom_tec")));
                ecd.setFacilitador(new Facilitador(rs.getInt("cod_f"), rs.getString("nom_f")));
                ecd.setDirigida(new Dirigida(rs.getInt("cod_da"), rs.getString("nom_da")));
                ecd.setRecursos(new Recursos(rs.getInt("cod_re"), rs.getString("nom_re")));
                ecd.setResponsable(new Responsable(rs.getString("r_cedula"), rs.getString("r_nombres"), rs.getString("r_apellidos"), rs.getString("r_correo"), rs.getString("r_telefono")));
                ecd.setPlancapacitacion(new PlanCapacitacion(0, rs.getInt("cod_pc"), rs.getString("nom_pc"), null, "", null, rs.getInt("meta_pc")));

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
                
                SimpleDateFormat dd = new SimpleDateFormat("dd/MM/yyyy");       
                int dias = 0; boolean activo = false;
                Calendar calendar; Date aux;
                
                Date fac= new Date();
                long fp=ecd.getFechaPlazo().getTime();
                long fa=fac.getTime(); 
                if(fa<fp){                    
                    do{       
                        calendar = Calendar.getInstance();           
                        calendar.add(Calendar.DAY_OF_YEAR, dias);
                        aux = calendar.getTime();

                        if(dd.format(aux).equals(dd.format(ecd.getFechaPlazo())))
                            activo = true; 
                        else
                            dias++;
                    }while(activo != true);
                    
                }
                ecd.setDiasRestantes(dias);
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
                    + " cod_nota, documento_usuario, U.nombre as nombreus, U.apellido as apellidous, estado, ecdn.nombre as nombrec, descripcion, fecha_registro"
                    + " FROM gestor.evaluacion_capacitacion_detalle_notas ecdn"
                    + " JOIN public.usuarios U USING (documento_usuario)"
                    + " WHERE cod_evaluacion=" + pk.getCodEvaluacion() + " AND codigo_establecimiento=" + pk.getCodigoEstablecimiento()
                    + " AND cod_capacitacion=" + pk.getCodCapacitacion() + " AND cod_capacitacion_detalle=" + pk.getCodCapacitacionDetalle()
            );
            rs = consulta.ejecutar(sql);
            List<EvaluacionCapacitacionDetalleNotas> evaluacionCapacitacionDetalleNotasList = new ArrayList<>();
            while (rs.next()) {
                EvaluacionCapacitacionDetalleNotas ecdn = new EvaluacionCapacitacionDetalleNotas(
                        new EvaluacionCapacitacionDetalleNotasPK(pk.getCodEvaluacion(), pk.getCodigoEstablecimiento(), pk.getCodCapacitacion(),
                                pk.getCodCapacitacionDetalle(), rs.getLong("cod_nota")),
                        rs.getString("documento_usuario"), rs.getString("estado"), rs.getString("nombrec"), rs.getString("descripcion"), 
                        new Usuarios(null, rs.getString("nombreus"), rs.getString("apellidous"))
                );
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
    
    public List<?> cargarListaModalidad() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Modalidad> listaModalidad = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_modalidad, nombre "                    
                    + "FROM gestor.modalidad"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Modalidad m= new Modalidad(rs.getInt("cod_modalidad"), rs.getString("nombre"));                                
                listaModalidad.add(m);
            }
            return listaModalidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaTecnicas() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<TecnicaCapacitacion> listaTecnicas = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_tecnica, nombre "                    
                    + "FROM gestor.tecnica_capacitacion"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                TecnicaCapacitacion t= new TecnicaCapacitacion(rs.getInt("cod_tecnica"), rs.getString("nombre"));                                
                listaTecnicas.add(t);
            }
            return listaTecnicas;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaFacilitadores() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Facilitador> listaFacilitadores = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_facilitador, nombre "                    
                    + "FROM gestor.facilitador"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Facilitador f= new Facilitador(rs.getInt("cod_facilitador"), rs.getString("nombre"));                                
                listaFacilitadores.add(f);
            }
            return listaFacilitadores;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaDirigidas() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Dirigida> listaDirigidas = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_dirigida, nombre "                    
                    + "FROM gestor.dirigida"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Dirigida d= new Dirigida(rs.getInt("cod_dirigida"), rs.getString("nombre"));                                
                listaDirigidas.add(d);
            }
            return listaDirigidas;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaRecursos() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Recursos> listaRecursos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_recursos, nombre "                    
                    + "FROM gestor.recursos"
                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Recursos r= new Recursos(rs.getInt("cod_recursos"), rs.getString("nombre"));                                
                listaRecursos.add(r);
            }
            return listaRecursos;
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

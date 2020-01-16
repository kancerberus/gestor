/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.controller;

import com.gestor.dao.GeneralDAO;
import com.gestor.gestor.Ciclo;
import com.gestor.gestor.Seccion;
import com.gestor.gestor.SeccionDetalle;
import com.gestor.gestor.dao.CicloDAO;
import com.gestor.gestor.dao.SeccionDAO;
import com.gestor.gestor.dao.SeccionDetalleDAO;
import com.gestor.gestor.dao.SeccionDetalleItemsDAO;
import com.gestor.publico.Establecimiento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author juliano
 */
public class GestorGeneral extends Gestor implements Serializable {

//    public static String USUARIO_MENUS_COD_MENU_SEQ = "usuario.menus_cod_menu_seq";
    public static String GESTOR_EVALUACION_COD_EVALUACION_SEQ = "gestor.evaluacion_cod_evaluacion_seq";
    public static String GESTOR_EVALUACION_PLAN_ACCION_COD_PLAN_SEQ = "gestor.evaluacion_cod_evaluacion_seq";
    public static String GESTOR_EVALUACION_ADJUNTOS_COD_ADJUNTO_SEQ = "gestor.evaluacion_adjuntos_cod_adjunto_seq";
    public static String GESTOR_EVALUACION_CAPACITACION_COD_CAPACITACION_SEQ = "gestor.evaluacion_capacitacion_cod_capacitacion_seq";
    public static String GESTOR_EVALUACION_CAPACITACION_DETALLE_COD_CAPACITACION_DETALLE_SEQ = "gestor.evaluacion_capacitacion_detalle_cod_capacitacion_detalle_seq";
    public static String GESTOR_EVALUACION_PLAN_ACCION_DETALLE_COD_PLAN_DETALLE_SEQ = "gestor.evaluacion_plan_accion_detalle_cod_plan_detalle_seq";
    public static String MATRIZ_MATRIZ_RIESGOS_COD_RIESGO_MATRIZ_SEQ = "matriz.matriz_matriz_riesgos_cod_riesgo_matriz_seq";            
    public static String CUESTIONARIO_VULNERABILIDAD_COD_CUESTIONARIO_SEQ = "plemergencias.cuestionario_vulnerabilidad_cod_cuestionario_seq";
    public static String PLAN_EMERGENCIA_COD_PLAN_EMERGENCIA_SEQ = "plemergencias.plan_emergencia_cod_plan_emergencia_seq";
    public static String ANALISIS_AMENAZAS_COD_ANAILIS_AMENAZA_SEQ = "plemergencias.analisis_amenazas_cod_analisis_amenaza_seq";
    public static String ANALISIS_VULNERABILIDAD_COD_ANALISIS_VULNERABILIDAD_SEQ = "plemergencias.analisis_vulnerabilidad_cod_analisis_vulnerabilidad_seq";
    public static String ANALISIS_VULNERABILIDAD_COD_REL_ANALISIS_VUL_CUESTIONARIO_SEQ= "plemergencias.cod_rel_analisis_vul_cuestionario_seq";
    public static String ANALISIS_VULNERABILIDAD_RESULTADOS_COD_ANALISIS_VUL_RESULTADOS_SEQ= "plemergencias.cod_analisis_vul_resultado_seq";
    public static String DETERMINACION_NIVEL_RIESGO_COD_DET_NIVEL_RIESGO_SEQ = "plemergencias.determinacion_nivel_riesgo_cod_det_nivel_riesgo_seq";
    public static String INSPECCION_EXTINTORES_COD_INSPECCION_EXTINTOR_SEQ = "inspecciones.inspeccion_extintor_cod_inspeccion_extintor_seq";
    public static String INSPECCION_BOTIQUINES_COD_INSPECCION_BOTIQUIN_SEQ = "inspecciones.inspeccion_botiquines_cod_inspeccion_botiquin_seq";
    public static String INSPECCION_ALMACEN_BODEGA_COD_INSPECCION_ALMACEN_BODEGA_SEQ = "inspecciones.inspeccion_almacen_bodega_cod_inspeccion_almacen_bodega_seq";
    public static String INSPECCION_PROTECCION_PERSONAL_COD_INSPECCION_PROTECCION_PERSONAL_SEQ = "inspecciones.inspeccion_proteccion_personal_cod_inspeccion_proteccion_personal_seq";
    
    public static boolean VALIDA_PK = true;
    public static boolean NO_VALIDA_PK = false;
    public static String GESTOR_EVALUACION_CAPACITACION_DETALLE_NOTAS_COD_NOTA_SEQ = "gestor.evaluacion_capacitacion_detalle_notas_cod_nota_seq";
    public static String GESTOR_EVALUACION_PLAN_ACCION_DETALLE_NOTAS_COD_NOTA_SEQ = "gestor.evaluacion_plan_accion_detalle_notas_cod_nota_seq";
    public static String SEGUIMIENTO_PLAN_MAESTRO_COD_MAESTRO_SEQ = "seguimiento.plan_maestro_cod_maestro_seq";

    public GestorGeneral() throws Exception {
        super();
    }

    public Long nextval(String secuencia) throws Exception {
        try {
            this.abrirConexion();
            return new GeneralDAO(this.conexion).nextval(secuencia);
        } finally {
            this.cerrarConexion();
        }
    }

    public int siguienteCodigoEntidad(String campo, String entidad) throws Exception {
        try {
            this.abrirConexion();
            GeneralDAO generalDAO = new GeneralDAO(this.conexion);
            return generalDAO.siguienteCodigoEntidad(campo, entidad);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Date cargarFechaActualizo(String usuario) throws Exception{
        try {
            this.abrirConexion();
            GeneralDAO generalDAO = new GeneralDAO(this.conexion);
            return generalDAO.cargarFechaActualizo(usuario);
        } finally {
            this.cerrarConexion();
        }
    }
    


    public List<Ciclo> cargarCiclosEvaluacion() throws Exception {
        try {
            this.abrirConexion();
            List<Ciclo> ciclos = new ArrayList<>();

            CicloDAO cicloDAO = new CicloDAO(conexion);
            SeccionDAO seccionDAO = new SeccionDAO(conexion);
            SeccionDetalleDAO seccionDetalleDAO = new SeccionDetalleDAO(conexion);
            SeccionDetalleItemsDAO seccionDetalleItemsDAO = new SeccionDetalleItemsDAO(conexion);

            ciclos.addAll(cicloDAO.cargarListaCiclos());
            for (Ciclo c : ciclos) {
                c.setSeccionList(seccionDAO.cargarListaSeccion(c.getCodCiclo()));
                for (Seccion s : c.getSeccionList()) {
                    s.setSeccionDetalleList(
                            seccionDetalleDAO.cargarListaSeccionDetalle(s.getSeccionPK().getCodCiclo(), s.getSeccionPK().getCodSeccion())
                    );
                    for (SeccionDetalle sd : s.getSeccionDetalleList()) {
                        sd.setSeccionDetalleItemsList(
                                seccionDetalleItemsDAO.cargarListaSeccionDetalleItems(sd.getSeccionDetallePK().getCodCiclo(), sd.getSeccionDetallePK().getCodSeccion(), sd.getSeccionDetallePK().getCodDetalle())
                        );
                    }
                }
            }

            return ciclos;
        } finally {
            this.cerrarConexion();
        }
    }

    public Date now() throws Exception {
        try {
            this.abrirConexion();
            GeneralDAO generalDAO = new GeneralDAO(this.conexion);
            return generalDAO.now();
        } finally {
            this.cerrarConexion();
        }
    }

}

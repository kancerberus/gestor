/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias.dao;

import com.gestor.controller.GestorGeneral;
import com.gestor.planemergencias.Amenaza;
import com.gestor.planemergencias.AnalisisAmenazas;
import com.gestor.planemergencias.AnalisisVulnerabilidad;
import com.gestor.planemergencias.AnalisisVulnerabilidadResultados;
import com.gestor.planemergencias.PlanEmergencia;
import com.gestor.planemergencias.CuestionarioVulnerabilidad;
import com.gestor.planemergencias.DeterminacionNivelRiesgo;
import com.gestor.planemergencias.Gravedad;
import com.gestor.planemergencias.Probabilidad;
import com.gestor.planemergencias.RelAnalisisVulnerabilidadCuestionario;
import com.gestor.planemergencias.RelDeterminacionNivelRiesgo;
import com.gestor.planemergencias.RelOriegenTipo;
import com.gestor.planemergencias.RelVulnerabilidadTipo;
import com.gestor.planemergencias.TipoOrigen;
import com.gestor.planemergencias.Vulnerabilidad;
import com.gestor.publico.CentroTrabajo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.Consulta;
import java.text.DecimalFormat;
import java.util.Collection;

/**
 *
 * @author Julian
 */
public class PlanEmergenciasDAO {

    private Connection conexion;

    public PlanEmergenciasDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public Collection<? extends Vulnerabilidad> cargarListaVulnerabilidades() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Vulnerabilidad> listaVulnerabilidades = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_vulnerabilidad, nombre "                    
                    + " FROM plemergencias.vulnerabilidad"                                 
                    + " ORDER BY cod_vulnerabilidad"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Vulnerabilidad v= new Vulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getString("nombre"));
                v.setCodVulnerabilidad(rs.getInt("cod_vulnerabilidad"));
                v.setNombre(rs.getString("nombre"));                
                listaVulnerabilidades.add(v);                               
            }
            return listaVulnerabilidades;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends Vulnerabilidad> cargarListaVulnerabilidadesNivelRiesgo(Integer codPlanEmergencia) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Vulnerabilidad> listaVulnerabilidad = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT anvulres.cod_vulnerabilidad, vul.nombre nomvul, anvulres.cod_vulnerabilidad_tipo, vult.nombre nomvult " +
                    " FROM plemergencias.analisis_vulnerabilidad_resultados anvulres " +
                    " JOIN plemergencias.vulnerabilidad vul using (cod_vulnerabilidad) " +
                    " JOIN plemergencias.rel_vulnerabilidad_tipo vult using (cod_vulnerabilidad_tipo, cod_vulnerabilidad) " +
                    " WHERE cod_plan_emergencia='"+codPlanEmergencia+"' " +
                    " GROUP by anvulres.cod_vulnerabilidad, vul.nombre, anvulres.cod_vulnerabilidad_tipo, vult.nombre "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Vulnerabilidad v= new Vulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getString("nomvul"));
                v.setCodVulnerabilidad(rs.getInt("cod_vulnerabilidad"));                
                v.setNombre(rs.getString("nomvul"));
                listaVulnerabilidad.add(v);
            }
            return listaVulnerabilidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends Amenaza> cargarListaPromedioAmenaza(StringBuilder sb, Integer codPlanEmergencia) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Amenaza> listaAmenaza = new ArrayList<>();                
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT "+sb+", cod_plan_emergencia, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_analisis_vul_resultado, " +
                    " vul.nombre nomvul, vult.nombre nomrelvul " +
                    " FROM plemergencias.analisis_vulnerabilidad_resultados anvulres " +
                    " JOIN plemergencias.vulnerabilidad vul using (cod_vulnerabilidad) " +
                    " JOIN plemergencias.rel_vulnerabilidad_tipo vult using (cod_vulnerabilidad_tipo, cod_vulnerabilidad) " +
                    " WHERE cod_plan_emergencia='"+codPlanEmergencia+"' " +
                    " GROUP by prom1,cod_plan_emergencia, cod_vulnerabilidad, cod_vulnerabilidad_tipo,cod_analisis_vul_resultado, anvulres.cod_vulnerabilidad, vul.nombre, anvulres.cod_vulnerabilidad_tipo, vult.nombre "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Amenaza am=new Amenaza(rs.getInt("cod_plan_emergencia"), rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"),
                        rs.getInt("cod_analisis_vul_resultado"), rs.getFloat(sb.toString()));
                listaAmenaza.add(am);
            }
            return listaAmenaza;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends RelDeterminacionNivelRiesgo> cargarListaTiposVulnerabilidad(RelDeterminacionNivelRiesgo reldnr) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<RelDeterminacionNivelRiesgo> listaRelDetNivelRiesgo = new ArrayList<>();                
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT cod_det_nivel_riesgo, cod_vulnerabilidad, cod_vulnerabilidad_tipo " +
                    " FROM plemergencias.rel_determinacion_nivel_riesgo " +
                    " WHERE cod_det_nivel_riesgo='"+reldnr.getCodDetNivelRiesgo()+"' and cod_vulnerabilidad='"+reldnr.getCodVulnerabilidad()+"' and cod_vulnerabilidad_tipo<>99 "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                RelDeterminacionNivelRiesgo reldetnr=new RelDeterminacionNivelRiesgo(reldnr.getCodDetNivelRiesgo(), reldnr.getCodVulnerabilidad(), rs.getInt("cod_vulnerabilidad_tipo"), 0.0f, 0.0f, 0.0f, 0f,"");
                listaRelDetNivelRiesgo.add(reldetnr);                
            }
            return listaRelDetNivelRiesgo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    
    
    
    public Integer cargarvulnerabildadSize(PlanEmergencia pe) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                "SELECT  MAX(cod_vulnerabilidad) " +
                "FROM plemergencias.analisis_vulnerabilidad " +
                "WHERE cod_plan_emergencia='"+pe.getCodPlanEmergencia()+"' "                
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getInt("max");
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
    
    public Integer cargarvulnerabildadtipoSize(PlanEmergencia pe,Integer i) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                "SELECT  MAX(cod_vulnerabilidad_tipo) " +
                "FROM plemergencias.analisis_vulnerabilidad " +
                "WHERE cod_plan_emergencia='"+pe.getCodPlanEmergencia()+"' AND cod_vulnerabilidad='"+i+"'"                
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getInt("max");
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
    
    public Collection<? extends AnalisisVulnerabilidadResultados> cargarAnalisisVulnerabilidadesResultadosList(PlanEmergencia pem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<AnalisisVulnerabilidadResultados> listaAnalisisVulnerabilidadResultados = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_plan_emergencia, cod_vulnerabilidad, vul.nombre nomvul, cod_vulnerabilidad_tipo, vultipo.nombre nomvultipo, cod_analisis_vul_resultado, " +
                    " prom1,prom2,prom3,prom4, prom5,prom6, prom7,prom8,prom9,prom10,prom11,prom12,prom13,prom14,prom15,prom16,prom17,prom18,prom19,prom20,prom21,prom22,prom23,prom24,prom25, "
                    + " prom26,prom27,prom28,prom29,prom30,prom31,prom32,prom33 " +
                    " FROM plemergencias.analisis_vulnerabilidad_resultados " +
                    " JOIN plemergencias.vulnerabilidad vul using(cod_vulnerabilidad) " +
                    " JOIN plemergencias.rel_vulnerabilidad_tipo vultipo using (cod_vulnerabilidad, cod_vulnerabilidad_tipo) " +
                    " WHERE cod_plan_emergencia='"+pem.getCodPlanEmergencia()+"' "+
                    " ORDER BY cod_analisis_vul_resultado"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                AnalisisVulnerabilidadResultados anvulres=new AnalisisVulnerabilidadResultados(pem.getCodPlanEmergencia(), rs.getInt("cod_vulnerabilidad_tipo"), 
                        rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_analisis_vul_resultado"), rs.getFloat("prom1"), rs.getFloat("prom2"), rs.getFloat("prom3"), rs.getFloat("prom4"),
                        rs.getFloat("prom5"), rs.getFloat("prom6"), rs.getFloat("prom7"), rs.getFloat("prom8"), rs.getFloat("prom9"), rs.getFloat("prom10"), rs.getFloat("prom11"), rs.getFloat("prom12"),
                        rs.getFloat("prom13"), rs.getFloat("prom14"), rs.getFloat("prom15"), rs.getFloat("prom16"), rs.getFloat("prom17"),rs.getFloat("prom18"), rs.getFloat("prom19"), rs.getFloat("prom20"), rs.getFloat("prom21"), rs.getFloat("prom22"),
                        rs.getFloat("prom23"), rs.getFloat("prom24"), rs.getFloat("prom25"), rs.getFloat("prom26"), rs.getFloat("prom27"),rs.getFloat("prom28"), rs.getFloat("prom29"), rs.getFloat("prom30"), rs.getFloat("prom31"), rs.getFloat("prom32"), rs.getFloat("prom33")
                );
                
                anvulres.setVulnerabilidad(new Vulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getString("nomvul")));
                anvulres.setVulnerabilidadTipo(new RelVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_vulnerabilidad"), rs.getString("nomvultipo")));
                
                listaAnalisisVulnerabilidadResultados.add(anvulres);
            }
            return listaAnalisisVulnerabilidadResultados;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
            
            
            
    public Collection<? extends AnalisisAmenazas> cargarListaAnalisisAmenazasCentroTrabajo(PlanEmergencia pem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<AnalisisAmenazas> listaAnalisisAmenazas = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_analisis_amenaza, " +
                    " tor.cod_tipo_origen codto, tor.nombre nomto, " +
                    " relto.cod_origen codor, relto.nombre nomrelto, " +
                    " pro.cod_probabilidad codpro, pro.nombre nompro, " +
                    " gra.cod_gravedad codgra, gra.nombre nomgra " +
                    " FROM plemergencias.analisis_amenazas aam " +
                    " JOIN plemergencias.tipo_origen tor USING (cod_tipo_origen) " +
                    " JOIN plemergencias.rel_origen_tipo relto ON (relto.cod_origen = aam.cod_origen AND relto.cod_tipo_origen=aam.cod_tipo_origen) " +
                    " JOIN plemergencias.probabilidad pro USING (cod_probabilidad) " +
                    " JOIN plemergencias.gravedad gra USING (cod_gravedad) " +
                    " WHERE codigo_establecimiento ='"+pem.getCodigoEstablecimiento()+"'  AND cod_centro_trabajo='"+pem.getCodCentroTrabajo()+"' AND cod_plan_emergencia='"+pem.getCodPlanEmergencia()+"' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                AnalisisAmenazas aam= new AnalisisAmenazas(pem.getCodigoEstablecimiento(), pem.getCodCentroTrabajo(), pem.getCodPlanEmergencia()
                        , rs.getInt("cod_analisis_amenaza")                       
                );
                aam.setTipoOrigen(new TipoOrigen(rs.getInt("codto"), rs.getString("nomto")));
                aam.setRelOrigenTipo(new RelOriegenTipo(rs.getInt("codto"), rs.getInt("codor"), rs.getString("nomrelto")));
                aam.setProbabilidad(new Probabilidad(rs.getInt("codpro"), rs.getString("nompro")));
                aam.setGravedad(new Gravedad(rs.getInt("codgra"), rs.getString("nomgra")));
                listaAnalisisAmenazas.add(aam);
            }
            return listaAnalisisAmenazas;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends AnalisisVulnerabilidad> cargarListaAnalisisVulnerabilidadesPersonasCentroTrabajo(PlanEmergencia pem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<AnalisisVulnerabilidad> listaAnalisisVulnerabilidades = new ArrayList<>();
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_analisis_vulnerabilidad, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario, relvulcu.c1 c1, relvulcu.c2 c2, "+
                    " relvulcu.c3 c3,relvulcu.c4 c4,relvulcu.c5 c5,relvulcu.c6 c6,relvulcu.c7 c7,relvulcu.c8 c8,relvulcu.c9 c9,relvulcu.c10 c10,relvulcu.c11 c11, "+
                    " relvulcu.c12 c12,relvulcu.c13 c13,relvulcu.c14 c14,relvulcu.c15 c15,relvulcu.c16 c16,relvulcu.c17 c17, " +
                    " vul.nombre nomvul, vult.nombre nomvult, cvul.nombre nomcu, relvulcu.cod_rel_analisis_vul_cuestionario codrelvulcu" +
                    " FROM plemergencias.analisis_vulnerabilidad " +
                    " JOIN plemergencias.rel_analisis_vulnerabilidad_cuestionario relvulcu using (cod_analisis_vulnerabilidad, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario) " +
                    " JOIN plemergencias.vulnerabilidad vul using(cod_vulnerabilidad) " +
                    " JOIN plemergencias.rel_vulnerabilidad_tipo vult using (cod_vulnerabilidad,cod_vulnerabilidad_tipo) " +
                    " JOIN plemergencias.cuestionario_vulnerabilidad cvul using (cod_vulnerabilidad,cod_vulnerabilidad_tipo, cod_cuestionario) " +
                    " WHERE codigo_establecimiento='"+pem.getCodigoEstablecimiento()+"' and cod_centro_trabajo='"+pem.getCodCentroTrabajo()+"' and cod_plan_emergencia='"+pem.getCodPlanEmergencia()+"' AND cod_vulnerabilidad=1 "+
                    " ORDER BY cod_vulnerabilidad_tipo "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                AnalisisVulnerabilidad av= new AnalisisVulnerabilidad(pem.getCodigoEstablecimiento(), pem.getCodCentroTrabajo(), pem.getCodPlanEmergencia(), rs.getInt("cod_analisis_vulnerabilidad"),
                        rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getInt("cod_vulnerabilidad"));
                av.setRelCuestionarioVulnerabilidad(new RelAnalisisVulnerabilidadCuestionario(rs.getInt("cod_analisis_vulnerabilidad"), rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getInt("codrelvulcu")));                                               
                av.setVulnerabilidad(new Vulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getString("nomvul")));
                av.setVulnerabilidadTipo(new RelVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_vulnerabilidad"), rs.getString("nomvult")));
                av.setCuestionarioVulnerabilidad(new CuestionarioVulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getString("nomcu")));
                av.getCuestionarioVulnerabilidad().setCalif1(rs.getFloat("c1"));
                av.getCuestionarioVulnerabilidad().setCalif2(rs.getFloat("c2"));
                av.getCuestionarioVulnerabilidad().setCalif3(rs.getFloat("c3"));
                av.getCuestionarioVulnerabilidad().setCalif4(rs.getFloat("c4"));
                av.getCuestionarioVulnerabilidad().setCalif5(rs.getFloat("c5"));
                av.getCuestionarioVulnerabilidad().setCalif6(rs.getFloat("c6"));
                av.getCuestionarioVulnerabilidad().setCalif7(rs.getFloat("c7"));
                av.getCuestionarioVulnerabilidad().setCalif8(rs.getFloat("c8"));
                av.getCuestionarioVulnerabilidad().setCalif9(rs.getFloat("c9"));
                av.getCuestionarioVulnerabilidad().setCalif10(rs.getFloat("c10"));
                av.getCuestionarioVulnerabilidad().setCalif11(rs.getFloat("c11"));
                av.getCuestionarioVulnerabilidad().setCalif12(rs.getFloat("c12"));
                av.getCuestionarioVulnerabilidad().setCalif13(rs.getFloat("c13"));
                av.getCuestionarioVulnerabilidad().setCalif14(rs.getFloat("c14"));
                av.getCuestionarioVulnerabilidad().setCalif15(rs.getFloat("c15"));
                av.getCuestionarioVulnerabilidad().setCalif16(rs.getFloat("c16"));
                av.getCuestionarioVulnerabilidad().setCalif17(rs.getFloat("c17"));                
                listaAnalisisVulnerabilidades.add(av);                
            }

            return listaAnalisisVulnerabilidades;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends AnalisisVulnerabilidad> cargarListaAnalisisVulnerabilidadesRecursosCentroTrabajo(PlanEmergencia pem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<AnalisisVulnerabilidad> listaAnalisisVulnerabilidades = new ArrayList<>();
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_analisis_vulnerabilidad, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario, relvulcu.c1 c1, relvulcu.c2 c2, "+
                    " relvulcu.c3 c3,relvulcu.c4 c4,relvulcu.c5 c5,relvulcu.c6 c6,relvulcu.c7 c7,relvulcu.c8 c8,relvulcu.c9 c9,relvulcu.c10 c10,relvulcu.c11 c11, "+
                    " relvulcu.c12 c12,relvulcu.c13 c13,relvulcu.c14 c14,relvulcu.c15 c15,relvulcu.c16 c16,relvulcu.c17 c17, " +
                    " vul.nombre nomvul, vult.nombre nomvult, cvul.nombre nomcu, relvulcu.cod_rel_analisis_vul_cuestionario codrelvulcu" +
                    " FROM plemergencias.analisis_vulnerabilidad " +
                    " JOIN plemergencias.rel_analisis_vulnerabilidad_cuestionario relvulcu using (cod_analisis_vulnerabilidad, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario) " +
                    " JOIN plemergencias.vulnerabilidad vul using(cod_vulnerabilidad) " +
                    " JOIN plemergencias.rel_vulnerabilidad_tipo vult using (cod_vulnerabilidad,cod_vulnerabilidad_tipo) " +
                    " JOIN plemergencias.cuestionario_vulnerabilidad cvul using (cod_vulnerabilidad,cod_vulnerabilidad_tipo, cod_cuestionario) " +
                    " WHERE codigo_establecimiento='"+pem.getCodigoEstablecimiento()+"' and cod_centro_trabajo='"+pem.getCodCentroTrabajo()+"' and cod_plan_emergencia='"+pem.getCodPlanEmergencia()+"' and cod_vulnerabilidad=2 "
                    + " ORDER BY cod_vulnerabilidad_tipo "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                AnalisisVulnerabilidad av= new AnalisisVulnerabilidad(pem.getCodigoEstablecimiento(), pem.getCodCentroTrabajo(), pem.getCodPlanEmergencia(), rs.getInt("cod_analisis_vulnerabilidad"),
                        rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getInt("cod_vulnerabilidad"));
                av.setRelCuestionarioVulnerabilidad(new RelAnalisisVulnerabilidadCuestionario(rs.getInt("cod_analisis_vulnerabilidad"), rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getInt("codrelvulcu")));                                               
                av.setVulnerabilidad(new Vulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getString("nomvul")));
                av.setVulnerabilidadTipo(new RelVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_vulnerabilidad"), rs.getString("nomvult")));
                av.setCuestionarioVulnerabilidad(new CuestionarioVulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getString("nomcu")));
                av.getCuestionarioVulnerabilidad().setCalif1(rs.getFloat("c1"));
                av.getCuestionarioVulnerabilidad().setCalif2(rs.getFloat("c2"));
                av.getCuestionarioVulnerabilidad().setCalif3(rs.getFloat("c3"));
                av.getCuestionarioVulnerabilidad().setCalif4(rs.getFloat("c4"));
                av.getCuestionarioVulnerabilidad().setCalif5(rs.getFloat("c5"));
                av.getCuestionarioVulnerabilidad().setCalif6(rs.getFloat("c6"));
                av.getCuestionarioVulnerabilidad().setCalif7(rs.getFloat("c7"));
                av.getCuestionarioVulnerabilidad().setCalif8(rs.getFloat("c8"));
                av.getCuestionarioVulnerabilidad().setCalif9(rs.getFloat("c9"));
                av.getCuestionarioVulnerabilidad().setCalif10(rs.getFloat("c10"));
                av.getCuestionarioVulnerabilidad().setCalif11(rs.getFloat("c11"));
                av.getCuestionarioVulnerabilidad().setCalif12(rs.getFloat("c12"));
                av.getCuestionarioVulnerabilidad().setCalif13(rs.getFloat("c13"));
                av.getCuestionarioVulnerabilidad().setCalif14(rs.getFloat("c14"));
                av.getCuestionarioVulnerabilidad().setCalif15(rs.getFloat("c15"));
                av.getCuestionarioVulnerabilidad().setCalif16(rs.getFloat("c16"));
                av.getCuestionarioVulnerabilidad().setCalif17(rs.getFloat("c17"));                
                listaAnalisisVulnerabilidades.add(av);                
            }

            return listaAnalisisVulnerabilidades;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends AnalisisVulnerabilidad> cargarListaAnalisisVulnerabilidadesSistemasCentroTrabajo(PlanEmergencia pem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<AnalisisVulnerabilidad> listaAnalisisVulnerabilidades = new ArrayList<>();
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_analisis_vulnerabilidad, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario, relvulcu.c1 c1, relvulcu.c2 c2, "+
                    " relvulcu.c3 c3,relvulcu.c4 c4,relvulcu.c5 c5,relvulcu.c6 c6,relvulcu.c7 c7,relvulcu.c8 c8,relvulcu.c9 c9,relvulcu.c10 c10,relvulcu.c11 c11, "+
                    " relvulcu.c12 c12,relvulcu.c13 c13,relvulcu.c14 c14,relvulcu.c15 c15,relvulcu.c16 c16,relvulcu.c17 c17, " +
                    " vul.nombre nomvul, vult.nombre nomvult, cvul.nombre nomcu, relvulcu.cod_rel_analisis_vul_cuestionario codrelvulcu" +
                    " FROM plemergencias.analisis_vulnerabilidad " +
                    " JOIN plemergencias.rel_analisis_vulnerabilidad_cuestionario relvulcu using (cod_analisis_vulnerabilidad, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario) " +
                    " JOIN plemergencias.vulnerabilidad vul using(cod_vulnerabilidad) " +
                    " JOIN plemergencias.rel_vulnerabilidad_tipo vult using (cod_vulnerabilidad,cod_vulnerabilidad_tipo) " +
                    " JOIN plemergencias.cuestionario_vulnerabilidad cvul using (cod_vulnerabilidad,cod_vulnerabilidad_tipo, cod_cuestionario) " +
                    " WHERE codigo_establecimiento='"+pem.getCodigoEstablecimiento()+"' and cod_centro_trabajo='"+pem.getCodCentroTrabajo()+"' and cod_plan_emergencia='"+pem.getCodPlanEmergencia()+"' and cod_vulnerabilidad=3 "
                    + " ORDER BY cod_vulnerabilidad_tipo "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                AnalisisVulnerabilidad av= new AnalisisVulnerabilidad(pem.getCodigoEstablecimiento(), pem.getCodCentroTrabajo(), pem.getCodPlanEmergencia(), rs.getInt("cod_analisis_vulnerabilidad"),
                        rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getInt("cod_vulnerabilidad"));
                av.setRelCuestionarioVulnerabilidad(new RelAnalisisVulnerabilidadCuestionario(rs.getInt("cod_analisis_vulnerabilidad"), rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getInt("codrelvulcu")));                                               
                av.setVulnerabilidad(new Vulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getString("nomvul")));
                av.setVulnerabilidadTipo(new RelVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_vulnerabilidad"), rs.getString("nomvult")));
                av.setCuestionarioVulnerabilidad(new CuestionarioVulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getString("nomcu")));
                av.getCuestionarioVulnerabilidad().setCalif1(rs.getFloat("c1"));
                av.getCuestionarioVulnerabilidad().setCalif2(rs.getFloat("c2"));
                av.getCuestionarioVulnerabilidad().setCalif3(rs.getFloat("c3"));
                av.getCuestionarioVulnerabilidad().setCalif4(rs.getFloat("c4"));
                av.getCuestionarioVulnerabilidad().setCalif5(rs.getFloat("c5"));
                av.getCuestionarioVulnerabilidad().setCalif6(rs.getFloat("c6"));
                av.getCuestionarioVulnerabilidad().setCalif7(rs.getFloat("c7"));
                av.getCuestionarioVulnerabilidad().setCalif8(rs.getFloat("c8"));
                av.getCuestionarioVulnerabilidad().setCalif9(rs.getFloat("c9"));
                av.getCuestionarioVulnerabilidad().setCalif10(rs.getFloat("c10"));
                av.getCuestionarioVulnerabilidad().setCalif11(rs.getFloat("c11"));
                av.getCuestionarioVulnerabilidad().setCalif12(rs.getFloat("c12"));
                av.getCuestionarioVulnerabilidad().setCalif13(rs.getFloat("c13"));
                av.getCuestionarioVulnerabilidad().setCalif14(rs.getFloat("c14"));
                av.getCuestionarioVulnerabilidad().setCalif15(rs.getFloat("c15"));
                av.getCuestionarioVulnerabilidad().setCalif16(rs.getFloat("c16"));
                av.getCuestionarioVulnerabilidad().setCalif17(rs.getFloat("c17"));                
                listaAnalisisVulnerabilidades.add(av);                
            }

            return listaAnalisisVulnerabilidades;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends AnalisisVulnerabilidad> cargarListaAnalisisVulnerabilidadesCentroTrabajo(PlanEmergencia pem, Integer codVul, Integer codVultipo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<AnalisisVulnerabilidad> listaAnalisisVulnerabilidades = new ArrayList<>();
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_analisis_vulnerabilidad, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario, relvulcu.c1 c1, relvulcu.c2 c2, "+
                    " relvulcu.c3 c3,relvulcu.c4 c4,relvulcu.c5 c5,relvulcu.c6 c6,relvulcu.c7 c7,relvulcu.c8 c8,relvulcu.c9 c9,relvulcu.c10 c10,relvulcu.c11 c11, "+
                    " relvulcu.c12 c12,relvulcu.c13 c13,relvulcu.c14 c14,relvulcu.c15 c15,relvulcu.c16 c16,relvulcu.c17 c17, " +
                    " vul.nombre nomvul, vult.nombre nomvult, cvul.nombre nomcu, relvulcu.cod_rel_analisis_vul_cuestionario codrelvulcu" +
                    " FROM plemergencias.analisis_vulnerabilidad " +
                    " JOIN plemergencias.rel_analisis_vulnerabilidad_cuestionario relvulcu using (cod_analisis_vulnerabilidad, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario) " +
                    " JOIN plemergencias.vulnerabilidad vul using(cod_vulnerabilidad) " +
                    " JOIN plemergencias.rel_vulnerabilidad_tipo vult using (cod_vulnerabilidad,cod_vulnerabilidad_tipo) " +
                    " JOIN plemergencias.cuestionario_vulnerabilidad cvul using (cod_vulnerabilidad,cod_vulnerabilidad_tipo, cod_cuestionario) " +
                    " WHERE codigo_establecimiento='"+pem.getCodigoEstablecimiento()+"' AND cod_centro_trabajo='"+pem.getCodCentroTrabajo()+"' AND cod_plan_emergencia='"+pem.getCodPlanEmergencia()+"' AND cod_vulnerabilidad='"+codVul+"' AND cod_vulnerabilidad_tipo='"+codVultipo+"' "
                    + " ORDER BY cod_vulnerabilidad_tipo "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                AnalisisVulnerabilidad av= new AnalisisVulnerabilidad(pem.getCodigoEstablecimiento(), pem.getCodCentroTrabajo(), pem.getCodPlanEmergencia(), rs.getInt("cod_analisis_vulnerabilidad"),
                        rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getInt("cod_vulnerabilidad"));
                av.setRelCuestionarioVulnerabilidad(new RelAnalisisVulnerabilidadCuestionario(rs.getInt("cod_analisis_vulnerabilidad"), rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getInt("codrelvulcu")));                                               
                av.setVulnerabilidad(new Vulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getString("nomvul")));
                av.setVulnerabilidadTipo(new RelVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_vulnerabilidad"), rs.getString("nomvult")));
                av.setCuestionarioVulnerabilidad(new CuestionarioVulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"), rs.getString("nomcu")));
                av.getCuestionarioVulnerabilidad().setCalif1(rs.getFloat("c1"));
                av.getCuestionarioVulnerabilidad().setCalif2(rs.getFloat("c2"));
                av.getCuestionarioVulnerabilidad().setCalif3(rs.getFloat("c3"));
                av.getCuestionarioVulnerabilidad().setCalif4(rs.getFloat("c4"));
                av.getCuestionarioVulnerabilidad().setCalif5(rs.getFloat("c5"));
                av.getCuestionarioVulnerabilidad().setCalif6(rs.getFloat("c6"));
                av.getCuestionarioVulnerabilidad().setCalif7(rs.getFloat("c7"));
                av.getCuestionarioVulnerabilidad().setCalif8(rs.getFloat("c8"));
                av.getCuestionarioVulnerabilidad().setCalif9(rs.getFloat("c9"));
                av.getCuestionarioVulnerabilidad().setCalif10(rs.getFloat("c10"));
                av.getCuestionarioVulnerabilidad().setCalif11(rs.getFloat("c11"));
                av.getCuestionarioVulnerabilidad().setCalif12(rs.getFloat("c12"));
                av.getCuestionarioVulnerabilidad().setCalif13(rs.getFloat("c13"));
                av.getCuestionarioVulnerabilidad().setCalif14(rs.getFloat("c14"));
                av.getCuestionarioVulnerabilidad().setCalif15(rs.getFloat("c15"));
                av.getCuestionarioVulnerabilidad().setCalif16(rs.getFloat("c16"));
                av.getCuestionarioVulnerabilidad().setCalif17(rs.getFloat("c17"));                
                listaAnalisisVulnerabilidades.add(av);                
            }

            return listaAnalisisVulnerabilidades;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends Probabilidad> cargarListaProbabilidades() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Probabilidad> listaProbabilidades = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_probabilidad, nombre "                    
                    + " FROM plemergencias.probabilidad "                                 
                    + " ORDER BY cod_probabilidad"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Probabilidad p=new Probabilidad(rs.getInt("cod_probabilidad"), rs.getString("nombre"));
                p.setCodProbabilidad(rs.getInt("cod_probabilidad"));
                p.setNombre(rs.getString("nombre"));
                listaProbabilidades.add(p);                
            }
            return listaProbabilidades;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public Collection<? extends CuestionarioVulnerabilidad> cargarListaCuestionarioTipo(Integer codVulnerabilidad, Integer codVulnerabilidadTipo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<CuestionarioVulnerabilidad> listaCuestionarioVulnerabilidad = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_cuestionario, nombre "                    
                    + " FROM plemergencias.cuestionario_vulnerabilidad "
                    + " WHERE cod_vulnerabilidad='"+codVulnerabilidad+"' and cod_vulnerabilidad_tipo='"+codVulnerabilidadTipo+"' "                                 
                    + " ORDER BY cod_cuestionario "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                CuestionarioVulnerabilidad cv=new CuestionarioVulnerabilidad(codVulnerabilidad, codVulnerabilidadTipo, rs.getInt("cod_cuestionario"), rs.getString("nombre")
                );
                cv.setCodVulnerabilidad(codVulnerabilidad);
                cv.setCodVulnerabilidadTipo(codVulnerabilidadTipo);
                cv.setCodCuestionario(rs.getInt("cod_cuestionario"));
                cv.setNombre(rs.getString("nombre"));
                listaCuestionarioVulnerabilidad.add(cv);
            }
            return listaCuestionarioVulnerabilidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends TipoOrigen> cargarListaTiposOrigen() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<TipoOrigen> listaTiposOrigen = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_tipo_origen, nombre "                    
                    + " FROM plemergencias.tipo_origen  "                    
                    + " ORDER BY cod_tipo_origen"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                TipoOrigen to=new TipoOrigen(rs.getInt("cod_tipo_origen"), rs.getString("nombre"));
                to.setCodTipoOrigen(rs.getInt("cod_tipo_origen"));
                to.setNombre(rs.getString("nombre"));
                listaTiposOrigen.add(to);                
            }
            return listaTiposOrigen;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends RelOriegenTipo> cargarListaOrigenes(Integer codTipoOrigen) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<RelOriegenTipo> listaOrigenes = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_tipo_origen, cod_origen, relot.nombre nomrelot "                    
                    + " FROM plemergencias.rel_origen_tipo relot "
                    + " JOIN plemergencias.tipo_origen tor USING(cod_tipo_origen) "
                    + " WHERE cod_tipo_origen='"+codTipoOrigen+"' "                                                             
                    + " ORDER BY cod_origen"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                RelOriegenTipo rot=new RelOriegenTipo(rs.getInt("cod_tipo_origen"), rs.getInt("cod_origen"), rs.getString("nomrelot"));
                rot.setCodOrigen(rs.getInt("cod_tipo_origen"));
                rot.setCodOrigen(rs.getInt("cod_origen"));
                rot.setNombre(rs.getString("nomrelot"));                
                listaOrigenes.add(rot);
            }
            return listaOrigenes;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends RelOriegenTipo> cargarListaRelOrigenTiposList() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<RelOriegenTipo> listaOrigenes = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_tipo_origen, cod_origen, relot.nombre nomrelot, tor.nombre nomtor "                    
                    + " FROM plemergencias.rel_origen_tipo relot "
                    + " JOIN plemergencias.tipo_origen tor USING(cod_tipo_origen) "                    
                    + " ORDER BY relot.cod_tipo_origen"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                RelOriegenTipo rot=new RelOriegenTipo(rs.getInt("cod_tipo_origen"), rs.getInt("cod_origen"), rs.getString("nomrelot"));
                rot.setCodOrigen(rs.getInt("cod_tipo_origen"));
                rot.setCodOrigen(rs.getInt("cod_origen"));
                rot.setNombre(rs.getString("nomrelot"));                
                rot.setTipoOrigen(new TipoOrigen(rs.getInt("cod_tipo_origen"), rs.getString("nomtor")));
                listaOrigenes.add(rot);
            }
            return listaOrigenes;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends Gravedad> cargarListaGravedades() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Gravedad> listaGravedades = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_gravedad, nombre "                    
                    + " FROM plemergencias.gravedad "                                 
                    + " ORDER BY cod_gravedad"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Gravedad g=new Gravedad(rs.getInt("cod_gravedad"), rs.getString("nombre"));
                g.setCodGravedad(rs.getInt("cod_gravedad"));
                g.setNombre(rs.getString("nombre"));
                listaGravedades.add(g);
            }
            return listaGravedades;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends RelVulnerabilidadTipo> cargarListaVulnerabilidadTipos(Integer codVulnerabilidad) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<RelVulnerabilidadTipo> listaRelVulnerabilidadesTipo = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_vulnerabilidad, cod_vulnerabilidad_tipo, nombre "                    
                    + " FROM plemergencias.rel_vulnerabilidad_tipo"
                    + " WHERE cod_vulnerabilidad='"+codVulnerabilidad+"' AND cod_vulnerabilidad_tipo<>'99'"                                 
                    + " ORDER BY cod_vulnerabilidad_tipo "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                RelVulnerabilidadTipo relvt= new RelVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_vulnerabilidad"), rs.getString("nombre"));
                relvt.setCodVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"));
                relvt.setCodVulnerabilidad(rs.getInt("cod_vulnerabilidad"));
                relvt.setNombre(rs.getString("nombre"));
                listaRelVulnerabilidadesTipo.add(relvt);                               
            }
            return listaRelVulnerabilidadesTipo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarPregunta(CuestionarioVulnerabilidad cv) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO plemergencias.cuestionario_vulnerabilidad("
                    + " cod_cuestionario, cod_vulnerabilidad, cod_vulnerabilidad_tipo, nombre )"
                    + " VALUES ( '"+cv.getCodCuestionario()+"',"+cv.getCodVulnerabilidad()+","+cv.getCodVulnerabilidadTipo()+",'"+cv.getNombre()+"')"
                    + " ON CONFLICT (cod_vulnerabilidad, cod_cuestionario, cod_vulnerabilidad_tipo) DO UPDATE"
                    + " SET nombre=EXCLUDED.nombre"                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarPlanEmergencia(PlanEmergencia pe) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO plemergencias.plan_emergencia("
                    + " cod_plan_emergencia, cod_centro_trabajo, codigo_establecimiento)"
                    + " VALUES ( '"+pe.getCodPlanEmergencia()+"','"+pe.getCodCentroTrabajo()+"','"+pe.getCodigoEstablecimiento()+"')"                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarAnalisisAmenaza(AnalisisAmenazas aam) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM plemergencias.analisis_amenazas"
                    + " WHERE cod_analisis_amenaza='"+aam.getCodAnalisisAmenaza()+"'"                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarAnalisisAmenaza(AnalisisVulnerabilidad av) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM plemergencias.analisis_vulnerabilidad"
                    + " WHERE cod_analisis_vulnerabilidad='"+av.getCodAnalisisVulnerabilidad()+"'"                    
            );
            consulta.actualizar(sql);
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                    "DELETE "
                    + " FROM plemergencias.rel_analisis_vulnerabilidad_cuestionario"
                    + " WHERE cod_rel_analisis_vul_cuestionario='"+av.getRelCuestionarioVulnerabilidad().getCodRelAnalisisVulCuestionario()+"'"                    
            );
            consulta.actualizar(sql1);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public void insertarAnalisisAmenazas(ArrayList<AnalisisAmenazas> al) throws SQLException {
        Consulta consulta = null;
        try {          
            
            for(int i=0; i<al.size(); i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    "INSERT INTO plemergencias.analisis_amenazas("
                    + " codigo_establecimiento, cod_centro_trabajo, cod_plan_emergencia, cod_analisis_amenaza, cod_tipo_origen,"
                    + " cod_origen, cod_probabilidad, cod_gravedad)"
                    + " VALUES ( '"+al.get(i).getCodigoEstablecimiento()+"','"+al.get(i).getCodCentroTrabajo()+"','"+al.get(i).getCodPlanEmergencia()+"', "
                    + " '"+al.get(i).getCodAnalisisAmenaza()+"', '"+al.get(i).getTipoOrigen().getCodTipoOrigen()+"', '"+al.get(i).getRelOrigenTipo().getCodOrigen()+"','"+al.get(i).getProbabilidad().getCodProbabilidad()+"', "
                    + " '"+al.get(i).getGravedad().getCodGravedad()+"')"
                    + " ON CONFLICT ( codigo_establecimiento, cod_centro_trabajo, cod_plan_emergencia, cod_analisis_amenaza) DO UPDATE "
                    + " SET cod_tipo_origen=EXCLUDED.cod_tipo_origen, cod_origen=EXCLUDED.cod_origen, cod_probabilidad=EXCLUDED.cod_probabilidad, cod_gravedad=EXCLUDED.cod_gravedad "
                        
                );
            consulta.actualizar(sql);                
            }
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarAnalisisVulnerabilidades(ArrayList<AnalisisVulnerabilidad> av) throws SQLException, Exception {
        Consulta consulta = null;
        try {
            for(int i=0; i<av.size(); i++){
            if(av.get(i).getRelCuestionarioVulnerabilidad()==null){
                consulta = new Consulta(this.conexion);
                    StringBuilder sql = new StringBuilder(
                        "INSERT INTO plemergencias.analisis_vulnerabilidad("
                        + " codigo_establecimiento, cod_centro_trabajo, cod_plan_emergencia, cod_analisis_vulnerabilidad, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario )"
                        + " VALUES ( '"+av.get(i).getCodigoEstablecimiento()+"','"+av.get(i).getCodCentroTrabajo()+"','"+av.get(i).getCodPlanEmergencia()+"', "
                        + " '"+av.get(i).getCodAnalisisVulnerabilidad()+"','"+av.get(i).getVulnerabilidad().getCodVulnerabilidad()+"', '"+av.get(i).getVulnerabilidadTipo().getCodVulnerabilidadTipo()+"', "
                        + " '"+av.get(i).getCuestionarioVulnerabilidad().getCodCuestionario()+"')"
                    );
                consulta.actualizar(sql);

            }
            if(av.get(i).getRelCuestionarioVulnerabilidad()==null){
                GestorGeneral gestorGeneral = new GestorGeneral();
                av.get(i).setRelCuestionarioVulnerabilidad(new RelAnalisisVulnerabilidadCuestionario());
                Long codRelAnalisisVulCuestionario=gestorGeneral.nextval(GestorGeneral.ANALISIS_VULNERABILIDAD_COD_REL_ANALISIS_VUL_CUESTIONARIO_SEQ);
                av.get(i).getRelCuestionarioVulnerabilidad().setCodRelAnalisisVulCuestionario(codRelAnalisisVulCuestionario.intValue());
                
            }    
            consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    "INSERT INTO plemergencias.rel_analisis_vulnerabilidad_cuestionario("
                    + " cod_analisis_vulnerabilidad, cod_rel_analisis_vul_cuestionario,  cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario, c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22,c23,c24,c25,c26,c27,c28,c29,c30,c31,c32,c33 )"
                    + " VALUES ('"+av.get(i).getCodAnalisisVulnerabilidad()+"', '"+av.get(i).getRelCuestionarioVulnerabilidad().getCodRelAnalisisVulCuestionario()+"', '"+av.get(i).getVulnerabilidad().getCodVulnerabilidad()+"', '"+av.get(i).getVulnerabilidadTipo().getCodVulnerabilidadTipo()+"', "
                    + " '"+av.get(i).getCuestionarioVulnerabilidad().getCodCuestionario()+"', '"+av.get(i).getCuestionarioVulnerabilidad().getCalif1()+"', '"+av.get(i).getCuestionarioVulnerabilidad().getCalif2()+"', "
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif3()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif4()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif5()+"', "
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif6()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif7()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif8()+"', "
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif9()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif10()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif11()+"', "
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif12()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif13()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif14()+"', "
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif15()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif16()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif17()+"', "
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif18()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif19()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif20()+"', "
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif21()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif22()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif23()+"',"
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif24()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif25()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif26()+"',"
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif27()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif28()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif29()+"',"
                    + "'"+av.get(i).getCuestionarioVulnerabilidad().getCalif30()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif31()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif32()+"','"+av.get(i).getCuestionarioVulnerabilidad().getCalif33()+"')"
                    + " ON CONFLICT ( cod_analisis_vulnerabilidad,cod_rel_analisis_vul_cuestionario, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_cuestionario) DO UPDATE "
                    + " SET c1=EXCLUDED.c1, c2=EXCLUDED.c2, c3=EXCLUDED.c3, c4=EXCLUDED.c4,c5=EXCLUDED.c5, c6=EXCLUDED.c6,c7=EXCLUDED.c7, c8=EXCLUDED.c8,"
                    + " c9=EXCLUDED.c9, c10=EXCLUDED.c10,c11=EXCLUDED.c11, c12=EXCLUDED.c12,c13=EXCLUDED.c13, c14=EXCLUDED.c14,c15=EXCLUDED.c15, c16=EXCLUDED.c16,c17=EXCLUDED.c17,"
                    + " c18=EXCLUDED.c18,c19=EXCLUDED.c19, c20=EXCLUDED.c20,c21=EXCLUDED.c21, c22=EXCLUDED.c22,c23=EXCLUDED.c23, c24=EXCLUDED.c24,c25=EXCLUDED.c25, c26=EXCLUDED.c26,c27=EXCLUDED.c27, "
                    + " c28=EXCLUDED.c28,c29=EXCLUDED.c29, c30=EXCLUDED.c30,c31=EXCLUDED.c31,c32=EXCLUDED.c32,c33=EXCLUDED.c33"
                    
                );
            consulta.actualizar(sql1);
            }
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarAnalisisVulnerabilidadesResultado(ArrayList<AnalisisVulnerabilidadResultados> avr) throws SQLException, Exception {
        Consulta consulta = null;
        try {
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                    " DELETE " +
                        "FROM plemergencias.analisis_vulnerabilidad_resultados " +
                        "WHERE cod_plan_emergencia='"+avr.get(0).getCodPlanEmergencia()+"'"
                );
            consulta.actualizar(sql1);
            
            
        for(int i=0; i<avr.size(); i++){
                    GestorGeneral gestorGeneral = new GestorGeneral();                
                    Long codAnalisisVulResultado=gestorGeneral.nextval(GestorGeneral.ANALISIS_VULNERABILIDAD_COD_REL_ANALISIS_VUL_CUESTIONARIO_SEQ);
                    avr.get(i).setCodAnalisisVulResultado(codAnalisisVulResultado.intValue());
                
                    consulta = new Consulta(this.conexion);
                        StringBuilder sql = new StringBuilder(
                            " INSERT INTO plemergencias.analisis_vulnerabilidad_resultados ( "
                            + " cod_plan_emergencia, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_analisis_vul_resultado, prom1, prom2, prom3, prom4, prom5, prom6, prom7, prom8, prom9,prom10, prom11, prom12, prom13, prom14, prom15, prom16, prom17,prom18, prom19, prom20, prom21, prom22, prom23, prom24, prom25, prom26,prom27, prom28, prom29, prom30, prom31, prom32, prom33 )"
                            + " VALUES ( '"+avr.get(i).getCodPlanEmergencia()+"', '"+avr.get(i).getCodVulnerabilidad()+"', '"+avr.get(i).getCodVulnerabilidadTipo()+"', "
                            + " '"+avr.get(i).getCodAnalisisVulResultado()+"', '"+avr.get(i).getProm1()+"','"+avr.get(i).getProm2()+"','"+avr.get(i).getProm3()+"','"+avr.get(i).getProm4()+"','"+avr.get(i).getProm5()+"','"+avr.get(i).getProm6()+"','"+avr.get(i).getProm7()+"','"+avr.get(i).getProm8()+"', "
                            + " '"+avr.get(i).getProm9()+"','"+avr.get(i).getProm10()+"','"+avr.get(i).getProm11()+"','"+avr.get(i).getProm12()+"','"+avr.get(i).getProm13()+"','"+avr.get(i).getProm14()+"','"+avr.get(i).getProm15()+"','"+avr.get(i).getProm16()+"','"+avr.get(i).getProm17()+"','"+avr.get(i).getProm18()+"','"+avr.get(i).getProm19()+"' "
                            + " ,'"+avr.get(i).getProm20()+"','"+avr.get(i).getProm21()+"','"+avr.get(i).getProm22()+"','"+avr.get(i).getProm23()+"','"+avr.get(i).getProm24()+"','"+avr.get(i).getProm25()+"','"+avr.get(i).getProm26()+"','"+avr.get(i).getProm27()+"','"+avr.get(i).getProm28()+"','"+avr.get(i).getProm29()+"','"+avr.get(i).getProm30()+"' "
                            + ",'"+avr.get(i).getProm31()+"','"+avr.get(i).getProm32()+"','"+avr.get(i).getProm33()+"') "
                            + " ON CONFLICT ( cod_plan_emergencia, cod_vulnerabilidad, cod_vulnerabilidad_tipo, cod_analisis_vul_resultado) DO UPDATE "
                            + " SET prom1=EXCLUDED.prom1, prom2=EXCLUDED.prom2, prom3=EXCLUDED.prom3, prom4=EXCLUDED.prom4,prom5=EXCLUDED.prom5, prom6=EXCLUDED.prom6, prom7=EXCLUDED.prom7, prom8=EXCLUDED.prom8,"
                            + " prom9=EXCLUDED.prom9, prom10=EXCLUDED.prom10,prom11=EXCLUDED.prom11, prom12=EXCLUDED.prom12,prom13=EXCLUDED.prom13, prom14=EXCLUDED.prom14,prom15=EXCLUDED.prom15, prom16=EXCLUDED.prom16, prom17=EXCLUDED.prom17,"
                            + " prom18=EXCLUDED.prom18,prom19=EXCLUDED.prom19,prom20=EXCLUDED.prom20,prom21=EXCLUDED.prom21,prom22=EXCLUDED.prom22,prom23=EXCLUDED.prom23,prom24=EXCLUDED.prom24,prom25=EXCLUDED.prom25,prom26=EXCLUDED.prom26,prom27=EXCLUDED.prom27,"
                            + " prom28=EXCLUDED.prom28,prom29=EXCLUDED.prom29,prom30=EXCLUDED.prom30,prom31=EXCLUDED.prom31,prom32=EXCLUDED.prom32,prom33=EXCLUDED.prom33"
                        );
                    consulta.actualizar(sql);
            }            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }    
    
    public void insertarDeterminacionNivelRiesgo(DeterminacionNivelRiesgo dnr) throws SQLException, Exception {
        Consulta consulta = null;
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql2 = new StringBuilder(
                    " INSERT INTO plemergencias.determinacion_nivel_riesgo ( "+
                        " cod_plan_emergencia, cod_det_nivel_riesgo, cod_origen, cod_tipo_origen )"
                        + " VALUES ('"+dnr.getCodPlanEmergencia()+"', '"+dnr.getCodDetNivelRiesgo()+"', '"+dnr.getCodOrigen()+"', '"+dnr.getCodTipoOrigen()+"' )"                        
                );
            consulta.actualizar(sql2);            
            consulta = new Consulta(this.conexion);
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarDeterminacionNivelRiesgo(Integer codPlanEmergencia) throws SQLException, Exception {
        Consulta consulta = null;
        ResultSet rs = null;
        try {
            Integer codDetNivelRiesgo=0;
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT cod_det_nivel_riesgo "
                        + " FROM plemergencias.determinacion_nivel_riesgo "+
                        " WHERE cod_plan_emergencia='"+codPlanEmergencia+"'"                        
                );            
            rs = consulta.ejecutar(sql);
            
            while (rs.next()) {
                codDetNivelRiesgo= rs.getInt("cod_det_nivel_riesgo");
            }
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                    " DELETE FROM plemergencias.determinacion_nivel_riesgo "+
                        " WHERE cod_plan_emergencia='"+codPlanEmergencia+"'"                        
                );
            consulta.actualizar(sql1);                        
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql3 = new StringBuilder(
                    " DELETE FROM plemergencias.rel_determinacion_nivel_riesgo "+
                        " WHERE cod_det_nivel_riesgo='"+codDetNivelRiesgo+"'"                        
                );
            consulta.actualizar(sql3);                        
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    

    public Collection<? extends RelDeterminacionNivelRiesgo> cargarRelDeterminacionNivelRiesgo(Integer codPlanEmergencia, RelOriegenTipo rot) throws SQLException, Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        List<RelDeterminacionNivelRiesgo> listaRelDeterminacionNivelRiesgo = new ArrayList<>();
        try {             
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_plan_emergencia, cod_det_nivel_riesgo, cod_origen, cod_tipo_origen, cod_vulnerabilidad, cod_vulnerabilidad_tipo, bajo, medio, " +
                    " alto, subtotal, color, vul.nombre nomvul, vult.nombre nomvult, det.promedio promedio, det.nivel_riesgo nivelriesgo " +
                    " FROM plemergencias.rel_determinacion_nivel_riesgo reldet " +
                    " JOIN plemergencias.determinacion_nivel_riesgo det using (cod_det_nivel_riesgo) " +
                    " JOIN plemergencias.vulnerabilidad vul using(cod_vulnerabilidad) " +
                    " JOIN plemergencias.rel_vulnerabilidad_tipo vult using (cod_vulnerabilidad_tipo, cod_vulnerabilidad) " +
                    " WHERE cod_plan_emergencia='"+codPlanEmergencia+"' and cod_origen='"+rot.getCodOrigen()+"' and cod_tipo_origen='"+rot.getCodTipoOrigen()+"' "+
                    " ORDER BY cod_vulnerabilidad ASC, cod_vulnerabilidad_tipo ASC"
                );            
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                
                RelDeterminacionNivelRiesgo reldnr=new RelDeterminacionNivelRiesgo(rs.getInt("cod_det_nivel_riesgo"), rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"),
                        rs.getFloat("alto"), rs.getFloat("medio"), rs.getFloat("bajo"), rs.getFloat("subtotal"), rs.getString("color"));                
                reldnr.setDeterminacionNivelRiesgo(new DeterminacionNivelRiesgo(codPlanEmergencia, rs.getInt("cod_det_nivel_riesgo"), rs.getInt("cod_origen"), rs.getInt("cod_tipo_origen"), rs.getFloat("promedio"), rs.getString("nivelriesgo")));
                reldnr.setVulnerabilidad(new Vulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getString("nomvul")));
                reldnr.setRelVulnerabilidadTipo(new RelVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_vulnerabilidad"), rs.getString("nomvult")));
                if(reldnr.getSubtotal()==0.0f){
                    reldnr.setSubtotal(null);
                }
                DecimalFormat d=new DecimalFormat("#.#f");
                d.format(reldnr.getDeterminacionNivelRiesgo().getPromedio());
                listaRelDeterminacionNivelRiesgo.add(reldnr);
            }
            return listaRelDeterminacionNivelRiesgo;
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }        
    }
    
    
     public void insertarRelDeterminacionNivelRiesgo(RelDeterminacionNivelRiesgo reldnr) throws SQLException, Exception {  
        Consulta consulta = null;
        try {            
            consulta = new Consulta(this.conexion);
            StringBuilder sql2 = new StringBuilder(
                    " INSERT INTO plemergencias.rel_determinacion_nivel_riesgo ( "+
                        " cod_det_nivel_riesgo, cod_vulnerabilidad, cod_vulnerabilidad_tipo, bajo, medio, alto, subtotal,color )"
                        + " VALUES ('"+reldnr.getCodDetNivelRiesgo()+"', '"+reldnr.getCodVulnerabilidad()+"', '"+reldnr.getCodVulnerabilidadTipo()+"' ,"
                        + " "+reldnr.getBajo()+","+reldnr.getMedio()+","+reldnr.getAlto()+", "+reldnr.getSubtotal()+","+reldnr.getColor()+")"                        
                );
            consulta.actualizar(sql2);
            consulta = new Consulta(this.conexion);
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void updateRelDeterminacionNivelRiesgo(ArrayList<RelDeterminacionNivelRiesgo> reldnr) throws SQLException, Exception {  
        Consulta consulta = null;
        try {            
            for(int i=0;i<=reldnr.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " UPDATE plemergencias.rel_determinacion_nivel_riesgo "
                        + " SET subtotal="+reldnr.get(i).getSubtotal()+", color='"+reldnr.get(i).getColor()+"' "                        
                        + " WHERE cod_det_nivel_riesgo='"+reldnr.get(i).getCodDetNivelRiesgo()+"' AND cod_vulnerabilidad='"+reldnr.get(i).getCodVulnerabilidad()+"' "
                        + " AND cod_vulnerabilidad_tipo='"+reldnr.get(i).getCodVulnerabilidadTipo()+"' "
                );
                consulta.actualizar(sql2);                
            }
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void updateRelDeterminacionNivelRiesgo(Integer codDetNivelRiesgo, Float prom, String nivel) throws SQLException, Exception {  
        Consulta consulta = null;
        try {            
            prom=prom/3;
            DecimalFormat formato1 = new DecimalFormat("#.#");
            formato1.format(prom);
            consulta = new Consulta(this.conexion);
            StringBuilder sql2 = new StringBuilder(
            " UPDATE plemergencias.determinacion_nivel_riesgo "
                + " SET promedio="+prom+", nivel_riesgo='"+nivel+"' "                        
                + " WHERE cod_det_nivel_riesgo='"+codDetNivelRiesgo+"' "                
            );
            consulta.actualizar(sql2);                
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public Collection<? extends CuestionarioVulnerabilidad> cargarListaCuestionarioVulnerabilidad() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<CuestionarioVulnerabilidad> listaCuestionarioVulnerabilidades = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_cuestionario, cv.cod_vulnerabilidad, cv.cod_vulnerabilidad_tipo, cv.nombre,  v.nombre nomv, relvt.nombre nomrelvt "                    
                    +"FROM plemergencias.cuestionario_vulnerabilidad cv " +
                    "JOIN plemergencias.vulnerabilidad v on(v.cod_vulnerabilidad=cv.cod_vulnerabilidad) " +
                    "JOIN plemergencias.rel_vulnerabilidad_tipo relvt on (relvt.cod_vulnerabilidad=v.cod_vulnerabilidad and relvt.cod_vulnerabilidad_tipo=cv.cod_vulnerabilidad_tipo) " +
                    "group by cod_cuestionario, cv.cod_vulnerabilidad, cv.cod_vulnerabilidad_tipo, cv.nombre,  v.nombre, relvt.nombre " +
                    "ORDER BY cod_cuestionario"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                CuestionarioVulnerabilidad cv=new CuestionarioVulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_cuestionario"),rs.getString("nombre"));                
                cv.setCodCuestionario(rs.getInt("cod_cuestionario"));
                cv.setCodVulnerabilidad(rs.getInt("cod_vulnerabilidad"));
                cv.setCodVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"));
                cv.setNombre(rs.getString("nombre"));
                cv.setVulnerabilidad(new Vulnerabilidad(rs.getInt("cod_vulnerabilidad"), rs.getString("nomv")));
                cv.setRelVulnerabilidadTipo(new RelVulnerabilidadTipo(rs.getInt("cod_vulnerabilidad_tipo"), rs.getInt("cod_vulnerabilidad"), rs.getString("nomrelvt")));                
                listaCuestionarioVulnerabilidades.add(cv);
            }
            return listaCuestionarioVulnerabilidades;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends PlanEmergencia> cargarPlanesEmergencias(Integer codigoEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<PlanEmergencia> listaPlanesEmergencias = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT cod_centro_trabajo, cod_plan_emergencia, ct.nombre nomct, pe.codigo_establecimiento" +
                    " FROM plemergencias.plan_emergencia pe " +
                    " JOIN centro_trabajo ct on(ct.codigo_establecimiento=pe.codigo_establecimiento and ct.cod_centrotrabajo=pe.cod_centro_trabajo) "+
                    " WHERE pe.codigo_establecimiento='"+codigoEstablecimiento+"'" +                            
                    " ORDER by cod_plan_emergencia"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()){
                PlanEmergencia pe=new PlanEmergencia(rs.getInt("cod_plan_emergencia"),rs.getInt("cod_centro_trabajo"), rs.getInt("codigo_establecimiento"));
                pe.setCodigoEstablecimiento(rs.getInt("codigo_establecimiento"));
                pe.setCodPlanEmergencia(rs.getInt("cod_plan_emergencia"));
                pe.setCodCentroTrabajo(rs.getInt("cod_centro_trabajo"));
                pe.setCentrotrabajo(new CentroTrabajo(rs.getInt("cod_centro_trabajo"), rs.getString("nomct")));                
                listaPlanesEmergencias.add(pe);
            }
            return listaPlanesEmergencias;
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

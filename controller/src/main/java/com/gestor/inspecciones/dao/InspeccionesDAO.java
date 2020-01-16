/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.inspecciones.dao;


import com.gestor.indicadores.dao.*;
import com.gestor.gestor.ClaseHallazgo;
import com.gestor.gestor.EvaluacionPlanAccionDetalle;
import com.gestor.gestor.FuenteHallazgo;
import com.gestor.gestor.TipoAccion;
import com.gestor.inspecciones.AlmacenBodegaMetricas;
import com.gestor.inspecciones.ElementosBotiquin;
import com.gestor.inspecciones.InspeccionAlmacenBodega;
import com.gestor.inspecciones.InspeccionBotiquin;
import com.gestor.inspecciones.InspeccionExtintor;
import com.gestor.inspecciones.InspeccionProteccionPersonal;
import com.gestor.inspecciones.InspeccionesTipo;
import com.gestor.inspecciones.MotivoNoUso;
import com.gestor.inspecciones.RelInspeccionesCentroTrabajo;
import com.gestor.planemergencias.Vulnerabilidad;
import com.gestor.publico.CentroTrabajo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.Consulta;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Julian
 */
public class InspeccionesDAO {

    private Connection conexion;

    public InspeccionesDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void guardarInspeccionesCentroTrabajo(Integer codE,Integer codCT,Integer codInspeccion) throws SQLException{
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO inspecciones.rel_inspecciones_centro_trabajo "
                    + " ( codigo_establecimiento, cod_centrotrabajo, cod_inspeccion )"
                    + " VALUES ('" + codE + "', '" + codCT + "', '" + codInspeccion + "') "                                        
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<InspeccionesTipo> cargarInspeccionesTipo() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<InspeccionesTipo> inspeccionesTipoList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_inspeccion, nombre "                    
                    + " FROM inspecciones.combo_inspecciones "                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                InspeccionesTipo insTipo=new InspeccionesTipo();
                insTipo.setCodInspeccion(rs.getInt("cod_inspeccion"));
                insTipo.setNombre(rs.getString("nombre"));
                inspeccionesTipoList.add(insTipo);
            }
            
            return inspeccionesTipoList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<RelInspeccionesCentroTrabajo> cargarRelInspeccionesEstablecimientoList(int codigoEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<RelInspeccionesCentroTrabajo> relInspeccionesCentroTrabajoList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " select ct.nombre centrotrabajo, ci.nombre inspeccion, relct.cod_centrotrabajo cCT, relct.cod_inspeccion cI " +
                    " from inspecciones.rel_inspecciones_centro_trabajo relct " +
                    " join public.centro_trabajo ct on(ct.cod_centrotrabajo=relct.cod_centrotrabajo and ct.codigo_establecimiento=relct.codigo_establecimiento) " +
                    " join inspecciones.combo_inspecciones ci on(ci.cod_inspeccion=relct.cod_inspeccion) " +
                    " where relct.codigo_establecimiento='"+codigoEstablecimiento+"' "                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                RelInspeccionesCentroTrabajo relInspeccionCentroTrabajo=new RelInspeccionesCentroTrabajo();
                relInspeccionCentroTrabajo.setCentroTrabajo(new CentroTrabajo(rs.getInt("cCT"), rs.getString("centrotrabajo")));
                relInspeccionCentroTrabajo.setInspeccionesTipo(new InspeccionesTipo(rs.getInt("cI"), rs.getString("inspeccion")));
                relInspeccionCentroTrabajo.setCodigoEstablecimiento(codigoEstablecimiento);
                relInspeccionesCentroTrabajoList.add(relInspeccionCentroTrabajo);
            }
            
            return relInspeccionesCentroTrabajoList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends InspeccionExtintor> cargarInspeccionCentroTrabajo(Integer codigoEstablecimiento, Integer codCentroTrabajo, Integer codInspeccion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<InspeccionExtintor> inspeccionExtintoresList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "  "                    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                InspeccionExtintor inspeccionExtintores=new InspeccionExtintor();                
                inspeccionExtintoresList.add(inspeccionExtintores);
            }
            
            return inspeccionExtintoresList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void guardarInspeccionExtintores(ArrayList<InspeccionExtintor> ieList) throws SQLException {
        Consulta consulta = null;        
        
        try {
            for(int i=0;i<ieList.size();i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " INSERT INTO inspecciones.inspeccion_extintores " +
                        "( codigo_establecimiento, cod_inspeccion_extintor, cod_sgs, fecha_vence, " +
                        "version, fecha_registro, cod_centrotrabajo, responsableuno, responsabledos, " +
                        "no_extintor, area, tipo, capacidad, cilindro, boquilla, manguera, manometro, " +
                        "senalizacion, fecha_recarga, observacion )"
                        + " VALUES ('" + ieList.get(0).getCodigoEstablecimiento() + "', '"+ieList.get(i).getCodInspeccionExtintor()+"', '" + ieList.get(i).getCodSgs() + "', "
                        + "'"+ieList.get(i).getFechaVence()+"','"+ieList.get(i).getVersion()+"',NOW() ,"
                        + "'"+ieList.get(0).getCentroTrabajo().getCodCentrotrabajo()+"','"+ieList.get(i).getResponsableuno()+"', "
                        + "'"+ieList.get(i).getResponsabledos()+"', '"+ieList.get(i).getNoExtintor()+"','"+ieList.get(i).getArea()+"', "
                        + "'"+ieList.get(i).getTipo()+"','"+ieList.get(i).getCapacidad()+"','"+ieList.get(i).getCilindro()+"', "
                        + "'"+ieList.get(i).getBoquilla()+"', '"+ieList.get(i).getManguera()+"','"+ieList.get(i).getManometro()+"', "
                        + "'"+ieList.get(i).getSenalizacion()+"','"+ieList.get(i).getFechaRecarga()+"','"+ieList.get(i).getObservacion()+"') "
                        + " ON CONFLICT (codigo_establecimiento, cod_inspeccion_extintor, cod_centrotrabajo) DO UPDATE "
                        + " SET cod_sgs=EXCLUDED.cod_sgs, fecha_vence=EXCLUDED.fecha_vence, version=EXCLUDED.version "                    
                        + " , cod_centrotrabajo=EXCLUDED.cod_centrotrabajo, responsableuno=EXCLUDED.responsableuno, responsabledos=EXCLUDED.responsabledos "                    
                        + " , no_extintor=EXCLUDED.no_extintor, area=EXCLUDED.area, tipo=EXCLUDED.tipo "                    
                        + " , capacidad=EXCLUDED.capacidad, cilindro=EXCLUDED.cilindro, boquilla=EXCLUDED.boquilla "                    
                        + " , manguera=EXCLUDED.manguera, manometro=EXCLUDED.manometro, senalizacion=EXCLUDED.senalizacion "                    
                        + " , fecha_recarga=EXCLUDED.fecha_recarga, observacion=EXCLUDED.observacion "                    
                );
                consulta.actualizar(sql);
            }
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends InspeccionExtintor> cargarInspeccionExtintoresCentroTrabajo(Integer codCentrotrabajo, Integer codInspeccion, Integer codigoEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<InspeccionExtintor> extintoresList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " select cod_inspeccion_extintor, cod_sgs, fecha_vence, version, fecha_registro, responsableuno, responsabledos, "+
                    " no_extintor, area, tipo, capacidad, cilindro, boquilla, manguera, manometro, senalizacion, fecha_recarga, observacion "+
                    " from inspecciones.inspeccion_extintores " +
                    " where codigo_establecimiento='"+codigoEstablecimiento+"' and cod_centrotrabajo='"+codCentrotrabajo+"' " 
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                InspeccionExtintor iE=new InspeccionExtintor(codigoEstablecimiento.shortValue(), rs.getInt("cod_inspeccion_extintor"), codCentrotrabajo, rs.getString("cod_sgs"), rs.getDate("fecha_vence"),
                rs.getString("version"), rs.getDate("fecha_registro"), rs.getString("responsableuno"), rs.getString("responsabledos"), rs.getInt("no_extintor"), rs.getString("area"), rs.getString("tipo"), rs.getInt("capacidad"), 
                rs.getBoolean("cilindro"), rs.getBoolean("boquilla"), rs.getBoolean("manguera"), rs.getBoolean("manometro"), rs.getBoolean("senalizacion"), rs.getDate("fecha_recarga"), rs.getString("observacion"));                                
                extintoresList.add(iE);
                iE.setCentroTrabajo(new CentroTrabajo(codCentrotrabajo));
            }
            
            return extintoresList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void eliminarExtintor(InspeccionExtintor extinguidor) throws SQLException {
        Consulta consulta = null;        
        
        try {
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " DELETE FROM inspecciones.inspeccion_extintores "+
                    " WHERE cod_inspeccion_extintor='"+extinguidor.getCodInspeccionExtintor()+"'" 
                    
            );
            consulta.actualizar(sql);
            
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarElemBotiquin(InspeccionBotiquin elemBotiquin) throws SQLException {
        Consulta consulta = null;        
        
        try {
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " DELETE FROM inspecciones.inspeccion_botiquines "+
                    " WHERE cod_inspeccion_botiquin='"+elemBotiquin.getCodInspeccionBotiquin()+"'" 
                    
            );
            consulta.actualizar(sql);
            
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarAlmacenBodegaMetrica(InspeccionAlmacenBodega eleAlmBodega) throws SQLException {
        Consulta consulta = null;        
        
        try {
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " DELETE FROM inspecciones.inspeccion_almacen_bodega "+
                    " WHERE cod_inspeccion_almacen_bodega='"+eleAlmBodega.getCodInspeccionAlmacenBodega()+"'" 
                    
            );
            consulta.actualizar(sql);
            
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends ElementosBotiquin> cargarElementosBotiquin() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<ElementosBotiquin> elementosBotiquinList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT cod_elemento, nombre, unidades "+
                    " FROM inspecciones.elementos_botiquin " +
                    " ORDER BY cod_elemento ASC " 
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                ElementosBotiquin eB=new ElementosBotiquin(rs.getInt("cod_elemento"), rs.getString("nombre"), rs.getString("unidades"));
                elementosBotiquinList.add(eB);                
            }
            
            return elementosBotiquinList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void guardarInspeccionBotiquines(ArrayList<InspeccionBotiquin> eleBotList) throws SQLException {        
        Consulta consulta = null;        
        
        try {
            for(int i=0;i<eleBotList.size();i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " INSERT INTO inspecciones.inspeccion_botiquines " +
                        "( codigo_establecimiento, cod_inspeccion_botiquin, cod_sgs, fecha_inspeccion, " +
                        "version, fecha_registro, cod_centrotrabajo, responsableuno, responsabledos, " +
                        "bot_portatil, cod_elemento, cantidad_req, cantidad_exis, fvence_ele, observacion)"
                        + " VALUES ('" + eleBotList.get(0).getCodigoEstablecimiento() + "', '"+eleBotList.get(i).getCodInspeccionBotiquin()+"', '" + eleBotList.get(i).getCodSgs() + "', "
                        + "'"+eleBotList.get(i).getFechaInspeccion()+"','"+eleBotList.get(i).getVersion()+"',NOW() ,"
                        + "'"+eleBotList.get(0).getCentroTrabajo().getCodCentrotrabajo()+"','"+eleBotList.get(i).getResponsableuno()+"', "
                        + "'"+eleBotList.get(i).getResponsabledos()+"', '"+eleBotList.get(i).getBotPortatil()+"', "                        
                        + "'"+eleBotList.get(i).getElementosBotiquin().getCodElemento()+"','"+eleBotList.get(i).getCantidadReq()+"', "
                        + "'"+eleBotList.get(i).getCantidadExis()+"','"+eleBotList.get(i).getFvenceEle()+"', "
                        + "'"+eleBotList.get(i).getObservacion()+"') "
                        + " ON CONFLICT (codigo_establecimiento, cod_inspeccion_botiquin, cod_centrotrabajo) DO UPDATE "
                        + " SET cod_sgs=EXCLUDED.cod_sgs, fecha_inspeccion=EXCLUDED.fecha_inspeccion, version=EXCLUDED.version "                    
                        + " , cod_centrotrabajo=EXCLUDED.cod_centrotrabajo, responsableuno=EXCLUDED.responsableuno, responsabledos=EXCLUDED.responsabledos "                    
                        + " , bot_portatil=EXCLUDED.bot_portatil, cod_elemento=EXCLUDED.cod_elemento,cantidad_req=EXCLUDED.cantidad_req "
                        + " , cantidad_exis=EXCLUDED.cantidad_exis, fvence_ele=EXCLUDED.fvence_ele, observacion=EXCLUDED.observacion"
                );
                consulta.actualizar(sql);
            }
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    
    }

    public Collection<? extends InspeccionBotiquin> cargarInspeccionBotiquinCentroTrabajo(Integer codCt, Integer codInspeccion, Integer codEstablecmiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<InspeccionBotiquin> elemBotiquinList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " select  cod_inspeccion_botiquin,cod_sgs, fecha_inspeccion, version, fecha_registro, responsableuno, responsabledos, " +
                    "eb.cod_elemento cod_elemento, cantidad_req, cantidad_exis, observacion,fvence_ele, bot_portatil, eb.nombre nome, eb.unidades unide " +
                    "from inspecciones.inspeccion_botiquines " +
                    "join inspecciones.elementos_botiquin eb using(cod_elemento) " +
                    "where codigo_establecimiento='"+codEstablecmiento+"' and cod_centrotrabajo='"+codCt+"'" 
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                InspeccionBotiquin iB=new InspeccionBotiquin(codEstablecmiento.shortValue(), codCt, rs.getInt("cod_inspeccion_botiquin"),
                rs.getString("cod_sgs"), rs.getDate("fecha_inspeccion"), rs.getString("version"), rs.getDate("fecha_registro"), 
                rs.getString("responsableuno"), rs.getString("responsabledos"),rs.getBoolean("bot_portatil"), rs.getInt("cod_elemento"),
                rs.getInt("cantidad_req"), rs.getInt("cantidad_exis"),rs.getDate("fvence_ele"),rs.getString("observacion"));
                iB.setElementosBotiquin(new ElementosBotiquin(rs.getInt("cod_elemento"), rs.getString("nome"), rs.getString("unide")));
                elemBotiquinList.add(iB);
            }
            
            return elemBotiquinList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<?extends  AlmacenBodegaMetricas> cargarAlmacenBodegaMetricas() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<AlmacenBodegaMetricas> metricasList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT cod_metrica, nombre "+
                    " FROM inspecciones.almacen_bodega_metricas " +
                    " ORDER BY cod_metrica ASC " 
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                AlmacenBodegaMetricas aM=new AlmacenBodegaMetricas(rs.getInt("cod_metrica"), rs.getString("nombre"));
                metricasList.add(aM);                
            }
            
            return metricasList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void guardarInspeccionAlmacenBodega(ArrayList<InspeccionAlmacenBodega> AlmacenBodegaList) throws SQLException {
        Consulta consulta = null;        
        
        try {
            for(int i=0;i<AlmacenBodegaList.size();i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " INSERT INTO inspecciones.inspeccion_almacen_bodega " +
                        "( codigo_establecimiento, cod_inspeccion_almacen_bodega, cod_sgs, fecha_inspeccion, " +
                        "version, fecha_registro, cod_centro_trabajo, responsableuno, responsabledos, " +
                        "cod_metrica, cumple, valoracion, observacion)"
                        + " VALUES ('" + AlmacenBodegaList.get(0).getCodigoEstablecimiento() + "', '"+AlmacenBodegaList.get(i).getCodInspeccionAlmacenBodega()+"', '" + AlmacenBodegaList.get(i).getCodSgs() + "', "
                        + "'"+AlmacenBodegaList.get(i).getFechaInspeccion()+"','"+AlmacenBodegaList.get(i).getVersion()+"',NOW() ,"
                        + "'"+AlmacenBodegaList.get(0).getCentroTrabajo().getCodCentrotrabajo()+"','"+AlmacenBodegaList.get(i).getResponsableuno()+"', "
                        + "'"+AlmacenBodegaList.get(i).getResponsabledos()+"', '"+AlmacenBodegaList.get(i).getAlmBodegaMetricas().getCodMetrica()+"', "                        
                        + "'"+AlmacenBodegaList.get(i).getCumple()+"','"+AlmacenBodegaList.get(i).getValoracion()+"', "
                        + "'"+AlmacenBodegaList.get(i).getObservacion()+"') "
                        + " ON CONFLICT (codigo_establecimiento, cod_inspeccion_almacen_bodega, cod_centro_trabajo) DO UPDATE "
                        + " SET cod_sgs=EXCLUDED.cod_sgs, fecha_inspeccion=EXCLUDED.fecha_inspeccion, version=EXCLUDED.version "                    
                        + " , cod_centro_trabajo=EXCLUDED.cod_centro_trabajo, responsableuno=EXCLUDED.responsableuno, responsabledos=EXCLUDED.responsabledos "                    
                        + " , cod_metrica=EXCLUDED.cod_metrica, cumple=EXCLUDED.cumple,valoracion=EXCLUDED.valoracion "
                        + " , observacion=EXCLUDED.observacion"
                );
                consulta.actualizar(sql);
            }
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends InspeccionAlmacenBodega> cargarInspeccionAlmacenBodega(int codCt, Integer codInspeccion, int codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<InspeccionAlmacenBodega> almacenBodegaList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " select  cod_inspeccion_almacen_bodega,cod_sgs, fecha_inspeccion, version, fecha_registro, responsableuno, responsabledos, " +
                    " abm.cod_metrica cod_metrica,valoracion,cumple, observacion, abm.nombre nome " +
                    " from inspecciones.inspeccion_almacen_bodega " +
                    " join inspecciones.almacen_bodega_metricas abm using(cod_metrica) " +
                    " where codigo_establecimiento='"+codEstablecimiento+"' and cod_centro_trabajo='"+codCt+"'" 
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                InspeccionAlmacenBodega iAB=new InspeccionAlmacenBodega(codEstablecimiento, codCt, rs.getInt("cod_inspeccion_almacen_bodega"),
                        rs.getString("cod_sgs"), rs.getDate("fecha_inspeccion"), rs.getString("version"), rs.getDate("fecha_registro"),
                        rs.getString("responsableuno"), rs.getString("responsabledos"), rs.getInt("cod_metrica"),rs.getBoolean("cumple"),
                        rs.getString("valoracion"),rs.getString("observacion"));
                iAB.setAlmBodegaMetricas(new AlmacenBodegaMetricas(rs.getInt("cod_metrica"), rs.getString("nome")));
                
                almacenBodegaList.add(iAB);
            }
            
            return almacenBodegaList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends MotivoNoUso> cargarMotivoNoUsoList() throws SQLException {        
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<MotivoNoUso> motivoNoUsoList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT cod_mot_no_uso, nombre "+
                    " FROM inspecciones.motivo_no_uso " +
                    " ORDER BY cod_mot_no_uso ASC " 
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                MotivoNoUso mNU=new MotivoNoUso(rs.getInt("cod_mot_no_uso"), rs.getString("nombre"));
                motivoNoUsoList.add(mNU);                
            }
            
            return motivoNoUsoList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }    

    public void eliminarInspeccionProteccionPersonal(InspeccionProteccionPersonal selectedProteccionPersonal) throws SQLException {
        Consulta consulta = null;        
        
        try {
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " DELETE FROM inspecciones.inspeccion_proteccion_personal "+
                    " WHERE cod_ins_proteccion_personal='"+selectedProteccionPersonal.getCodInsProteccionPersonal()+"'" 
                    
            );
            consulta.actualizar(sql);
            
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends InspeccionProteccionPersonal> cargarInspeccionesProteccionPersonalList(Integer codigoEstablecimiento,Integer codTipoInspeccion, Integer codCt) throws SQLException {        
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<InspeccionProteccionPersonal> inspeccionProteccionPersonalList=new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " select cod_ins_proteccion_personal, codigo_sgs, vigencia, version, fecha_inspeccion, responsableuno, responsabledos, " +
                    " hora_inspeccion, nom_empleado, actividad, botas, guantes, tapabocas, gorros, zapatos, delantal, ele_pp, motivo, " +
                    " mnu.cod_mot_no_uso codmnu, mnu.nombre nommnu, observaciones, fecha_registro " +
                    " from inspecciones.inspeccion_proteccion_personal " +
                    " join inspecciones.motivo_no_uso mnu using(cod_mot_no_uso) " +
                    " where codigo_establecimiento='"+codigoEstablecimiento+"' and cod_centro_trabajo='"+codCt+"'" 
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                InspeccionProteccionPersonal ipp=new InspeccionProteccionPersonal(codigoEstablecimiento, codCt, rs.getInt("cod_ins_proteccion_personal"), rs.getString("cod_sgs"),
                        rs.getDate("vigencia"), rs.getDate("fecha_inspeccion"), rs.getDate("fecha_inspeccion"), rs.getString("nom_empleado"), rs.getString("actividad"), rs.getBoolean("botas"), rs.getBoolean("guantes"), rs.getBoolean("tapabocas"), 
                        rs.getBoolean("gorros"), rs.getBoolean("zapatos"), rs.getBoolean("delantal"), rs.getString("ele_pp"), rs.getString("motivo"), rs.getString("version"), rs.getDate("fecha_registro"), rs.getString("responsableuno"), 
                        rs.getString("responsabledos"), rs.getString("observaciones"));
                ipp.setMotivoNoUso(new MotivoNoUso(rs.getInt("codmnu"), rs.getString("nommnu")));
                inspeccionProteccionPersonalList.add(ipp);                
            }
            
            return inspeccionProteccionPersonalList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public void guardarInspeccionProteccionPersonal(ArrayList<InspeccionProteccionPersonal> iPPList) throws SQLException {
        Consulta consulta = null;        
        
        try {
            for(int i=0;i<iPPList.size();i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " INSERT INTO inspecciones.inspeccion_proteccion_personal " +
                        "( codigo_establecimiento, cod_ins_proteccion_personal, codigo_sgs, fecha_inspeccion, " +
                        "version, fecha_registro, cod_centro_trabajo, responsableuno, responsabledos, " +
                        "hora_inspeccion, nom_empleado, actividad, botas, guantes, tapabocas, gorros, zapatos, delantal, ele_pp, motivo, cod_mot_no_uso, observaciones)"
                        + " VALUES ('" + iPPList.get(0).getCodigoEstablecimiento() + "', '"+iPPList.get(i).getCodInsProteccionPersonal()+"', '" + iPPList.get(i).getCodSgs() + "', "
                        + "'"+iPPList.get(i).getFechaInspeccion()+"','"+iPPList.get(i).getVersion()+"',NOW() ,"
                        + "'"+iPPList.get(0).getCentroTrabajo().getCodCentrotrabajo()+"','"+iPPList.get(i).getResponsableuno()+"', "
                        + "'"+iPPList.get(i).getResponsabledos()+"','"+iPPList.get(i).getHora_inspeccion()+"', '"+iPPList.get(i).getBotas()+"','"+iPPList.get(i).getGuantes()+"', '"+iPPList.get(i).getTapabocas()+"',"
                        + "'"+iPPList.get(i).getGorras()+"', '"+iPPList.get(i).getZapatos()+"','"+iPPList.get(i).getDelantal()+"',"
                        + "'"+iPPList.get(i).getElePP()+"','"+iPPList.get(i).getMotivo()+"',  '"+iPPList.get(i).getMotivoNoUso().getCodMotNoUso()+"','"+iPPList.get(i).getObservacion()+"') "
                        + " ON CONFLICT (codigo_establecimiento, cod_ins_proteccion_personal, cod_centro_trabajo) DO UPDATE "
                        + " SET codigo_sgs=EXCLUDED.codigo_sgs, fecha_inspeccion=EXCLUDED.fecha_inspeccion, version=EXCLUDED.version, fecha_registro=EXCLUDED.fecha_registro "                    
                        + " , cod_centro_trabajo=EXCLUDED.cod_centro_trabajo, responsableuno=EXCLUDED.responsableuno, responsabledos=EXCLUDED.responsabledos "                    
                        + " , hora_inspeccion=EXCLUDED.hora_inspeccion, nom_empleado=EXCLUDED.nom_empleado,actividad=EXCLUDED.actividad "
                        + " , botas=EXCLUDED.botas, guantes=EXCLUDED.guantes, tapabocas=EXCLUDED.tapabocas, gorros=EXCLUDED.gorros "
                        + " , zapatos=EXCLUDED.zapatos, delantal=EXCLUDED.delantal, ele_pp=EXCLUDED.ele_pp, motivo=EXCLUDED.motivo "
                        + " , cod_mot_no_uso=EXCLUDED.cod_mot_no_uso , observacion=EXCLUDED.observacion"
                );
                consulta.actualizar(sql);
            }
            
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
}

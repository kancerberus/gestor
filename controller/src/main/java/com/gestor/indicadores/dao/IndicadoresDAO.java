/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.indicadores.dao;


import com.gestor.gestor.ClaseHallazgo;
import com.gestor.gestor.EvaluacionPlanAccionDetalle;
import com.gestor.gestor.FuenteHallazgo;
import com.gestor.gestor.TipoAccion;
import com.gestor.planemergencias.Vulnerabilidad;
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
public class IndicadoresDAO {

    private Connection conexion;

    public IndicadoresDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleClaseHallazgo(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<EvaluacionPlanAccionDetalle> listaEvaluacionPlanAccionDetalleClaseHallazgo = new ArrayList<>();
        Float total=0.0f;
        Float distribucion=0.0f;
        
        try {
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                    "SELECT count (cod_plan_detalle) total "+
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD  " +
                    " JOIN gestor.clase_hallazgo ch using(cod_clase_hallazgo)    " +
                    condicion+ " AND ch.cod_clase_hallazgo<>0  "                     
            );
            rs = consulta.ejecutar(sql1);
            while (rs.next()) {
                total=rs.getFloat("total");
            }
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count (cod_plan_detalle) cantidad, ch.cod_clase_hallazgo codch, ch.nombre nomch " +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD  " +
                    " JOIN gestor.clase_hallazgo ch using(cod_clase_hallazgo)    " +
                    condicion+ " AND ch.cod_clase_hallazgo<>0  " +
                    " GROUP BY ch.nombre, ch.cod_clase_hallazgo"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                EvaluacionPlanAccionDetalle epach=new EvaluacionPlanAccionDetalle();
                distribucion=rs.getInt("cantidad")/total;
                epach.setClasehallazgo(new ClaseHallazgo(rs.getInt("codch"), rs.getString("nomch"), rs.getInt("cantidad"), distribucion));
                listaEvaluacionPlanAccionDetalleClaseHallazgo.add(epach);                               
            }
            return listaEvaluacionPlanAccionDetalleClaseHallazgo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleTipoAccion(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<EvaluacionPlanAccionDetalle> listaEvaluacionPlanAccionDetalleTipoAccion = new ArrayList<>();
        Float total=0.0f;
        Float distribucion=0.0f;
        
        try {
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                    "SELECT count (cod_plan_detalle) total "+
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD  " +
                    " JOIN gestor.tipo_accion ta using(cod_tipo_accion) " +
                    condicion+ " AND ta.cod_tipo_accion<>0  "                     
            );
            rs = consulta.ejecutar(sql1);
            while (rs.next()) {
                total=rs.getFloat("total");
            }
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count (cod_plan_detalle) cantidad, ta.cod_tipo_accion codta, ta.nombre nomta  " +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD  " +
                    " JOIN gestor.tipo_accion ta using(cod_tipo_accion) " 
                    +condicion+ " AND ta.cod_tipo_accion<>0 " +
                    " GROUP BY ta.nombre, ta.cod_tipo_accion "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                EvaluacionPlanAccionDetalle epata=new EvaluacionPlanAccionDetalle();
                distribucion=rs.getInt("cantidad")/total;
                epata.setTipoaccion(new TipoAccion(rs.getInt("codta"), rs.getString("nomta"), rs.getInt("cantidad"), distribucion));
                listaEvaluacionPlanAccionDetalleTipoAccion.add(epata);                               
            }
            return listaEvaluacionPlanAccionDetalleTipoAccion;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleFuenteHallazgo(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<EvaluacionPlanAccionDetalle> listaEvaluacionPlanAccionDetalleFuenteHallazgo = new ArrayList<>();
        Float total=0.0f;
        Float distribucion=0.0f;
        
        try {
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                    "SELECT count (cod_plan_detalle) total "+
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD  " +
                    " JOIN gestor.fuente_hallazgo fh using(cod_fuente_hallazgo) " +
                    condicion+ " AND fh.cod_fuente_hallazgo<>0  "                     
            );
            rs = consulta.ejecutar(sql1);
            while (rs.next()) {
                total=rs.getFloat("total");
            }
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count (cod_plan_detalle) cantidad, fh.cod_fuente_hallazgo codfh, fh.nombre nomfh  " +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD  " +
                    " JOIN gestor.fuente_hallazgo fh using(cod_fuente_hallazgo) " 
                    +condicion+ " AND fh.cod_fuente_hallazgo<>0 " +
                    " GROUP BY fh.nombre, fh.cod_fuente_hallazgo "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                EvaluacionPlanAccionDetalle epata=new EvaluacionPlanAccionDetalle();
                distribucion=rs.getInt("cantidad")/total;
                epata.setFuentehallazgo(new FuenteHallazgo(rs.getInt("codfh"), rs.getString("nomfh"), rs.getInt("cantidad"), distribucion));
                listaEvaluacionPlanAccionDetalleFuenteHallazgo.add(epata);                               
            }
            return listaEvaluacionPlanAccionDetalleFuenteHallazgo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleEstado(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<EvaluacionPlanAccionDetalle> listaEvaluacionPlanAccionDetalleEstado = new ArrayList<>();
        List<EvaluacionPlanAccionDetalle> listaEvaluacionPlanAccionDetalleEstadoRelacion = new ArrayList<>();
        Float total=0.0f;
        Float distribucion=0.0f;        
        
        try {
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                    "SELECT count (cod_plan_detalle) total " +
                    "FROM gestor.evaluacion_plan_accion_detalle EPAD  " +
                    condicion+" AND estado<>'E' " 
                    
            );
            rs = consulta.ejecutar(sql1);
            while (rs.next()) {
                total=rs.getFloat("total");
            }
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count (cod_plan_detalle) cantidad, estado, fecha_plazo " +
                    "FROM gestor.evaluacion_plan_accion_detalle EPAD " +
                    condicion+" AND estado<>'E' " +
                    "GROUP BY estado,fecha_plazo "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                distribucion=rs.getInt("cantidad")/total;   
                EvaluacionPlanAccionDetalle epata=new EvaluacionPlanAccionDetalle(rs.getString("estado"), rs.getInt("cantidad"), distribucion, rs.getDate("fecha_plazo"));
                
                SimpleDateFormat dd = new SimpleDateFormat("dd/MM/yyyy");       
                int dias = 0; boolean activo = false;
                Calendar calendar; Date aux;
                
                Date fac= new Date();
                long fp=epata.getFechaPlazo().getTime();
                long fa=fac.getTime(); 
                if(fa>fp && epata.getEstado().equals("A")){
                    
                    dias=(int) ((fp-fa)/86400000);
                }
                if(fa<fp){                    
                    do{       
                        calendar = Calendar.getInstance();           
                        calendar.add(Calendar.DAY_OF_YEAR, dias);
                        aux = calendar.getTime();

                        if(dd.format(aux).equals(dd.format(epata.getFechaPlazo())))
                            activo = true; 
                        else
                            dias++;
                    }while(activo != true);
                    
                }
                
                if(epata.getEstado().equals("A") && dias>0){
                    epata.setEstado("Abierto");                    
                }
                if(dias<0 && epata.getEstado().equals("A")){
                    epata.setEstado("Vencido");                                                            
                }
                if(epata.getEstado().equals("C")){
                    epata.setEstado("Cerrado");
                }
                
                listaEvaluacionPlanAccionDetalleEstado.add(epata);                               
            }
            
                Integer cantidadab=0;
                Integer cantidadve=0;
                Integer cantidadce=0;                                    
                Float dist;                
                listaEvaluacionPlanAccionDetalleEstadoRelacion.add(0, new EvaluacionPlanAccionDetalle("Abierto", 0, 0.0f, null));
                listaEvaluacionPlanAccionDetalleEstadoRelacion.add(1, new EvaluacionPlanAccionDetalle("Cerrado", 0, 0.0f, null));
                listaEvaluacionPlanAccionDetalleEstadoRelacion.add(2, new EvaluacionPlanAccionDetalle("Vencida", 0, 0.0f, null));
                for(int i=0; i<listaEvaluacionPlanAccionDetalleEstado.size();i++){
                    
                    if(listaEvaluacionPlanAccionDetalleEstado.get(i).getEstado().equals("Abierto")){
                        cantidadab=cantidadab+listaEvaluacionPlanAccionDetalleEstado.get(i).getCantidad();
                        dist=cantidadab/total;            
                        EvaluacionPlanAccionDetalle evab=new EvaluacionPlanAccionDetalle(listaEvaluacionPlanAccionDetalleEstado.get(i).getEstado(), cantidadab, dist, null);
                        listaEvaluacionPlanAccionDetalleEstadoRelacion.set(0, evab);
                    }                    
                    if(listaEvaluacionPlanAccionDetalleEstado.get(i).getEstado().equals("Cerrado")){                        
                        cantidadce=cantidadce+listaEvaluacionPlanAccionDetalleEstado.get(i).getCantidad();
                        dist=cantidadce/total;                        
                        EvaluacionPlanAccionDetalle evce=new EvaluacionPlanAccionDetalle(listaEvaluacionPlanAccionDetalleEstado.get(i).getEstado(), cantidadce, dist, null);
                        listaEvaluacionPlanAccionDetalleEstadoRelacion.set(1, evce);
                    }
                    if(listaEvaluacionPlanAccionDetalleEstado.get(i).getEstado().equals("Vencido")){                        
                        cantidadve=cantidadve+listaEvaluacionPlanAccionDetalleEstado.get(i).getCantidad();
                        dist=cantidadve/total;      
                        EvaluacionPlanAccionDetalle evve=new EvaluacionPlanAccionDetalle(listaEvaluacionPlanAccionDetalleEstado.get(i).getEstado(), cantidadve, dist, null);                                                
                        listaEvaluacionPlanAccionDetalleEstadoRelacion.set(2, evve);
                    }                                        
                }
                
            
            return listaEvaluacionPlanAccionDetalleEstadoRelacion;
            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Collection<? extends EvaluacionPlanAccionDetalle> cargarListaEvaluacionPlanAccionDetalleFuenteHallazgoEstados(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<EvaluacionPlanAccionDetalle> listaEvaluacionPlanAccionDetalleFuenteHallazgoEstados = new ArrayList<>();
        List<EvaluacionPlanAccionDetalle> listaEvaluacionPlanAccionDetalleEstados = new ArrayList<>();
        try {                        
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT fh.cod_fuente_hallazgo codfh, fh.nombre nomfh " +
                    "FROM gestor.evaluacion_plan_accion_detalle EPAD  JOIN gestor.fuente_hallazgo fh using(cod_fuente_hallazgo) " +
                    "WHERE codigo_establecimiento=9  and estado<>'E'" +
                    "GROUP BY fh.nombre, fh.cod_fuente_hallazgo "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                EvaluacionPlanAccionDetalle epata=new EvaluacionPlanAccionDetalle();
                epata.setFuentehallazgo(new FuenteHallazgo(rs.getInt("codfh"), rs.getString("nomfh")));
                listaEvaluacionPlanAccionDetalleFuenteHallazgoEstados.add(epata);             
            }       
            
            Integer total=0;
            Integer abierto=0;
            Integer cerrado=0;
            Integer vencido=0;
            for(int i=0; i<=listaEvaluacionPlanAccionDetalleFuenteHallazgoEstados.size()-1;i++){                
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                        "SELECT count (cod_plan_detalle) cantidad " +
                        "FROM gestor.evaluacion_plan_accion_detalle EPAD  JOIN gestor.fuente_hallazgo fh using(cod_fuente_hallazgo) " +
                        "WHERE codigo_establecimiento=9 AND fh.cod_fuente_hallazgo='"+listaEvaluacionPlanAccionDetalleFuenteHallazgoEstados.get(i).getFuentehallazgo().getCodFuentehallazgo()+"'  and estado<>'E' "
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) { 
                    total=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2= new StringBuilder(
                        "SELECT count (cod_plan_detalle) abierto " +
                        "FROM gestor.evaluacion_plan_accion_detalle EPAD  JOIN gestor.fuente_hallazgo fh using(cod_fuente_hallazgo) " +
                        "WHERE codigo_establecimiento=9 AND fh.cod_fuente_hallazgo='"+listaEvaluacionPlanAccionDetalleFuenteHallazgoEstados.get(i).getFuentehallazgo().getCodFuentehallazgo()+"' AND estado='A' AND (CURRENT_DATE)<fecha_plazo AND estado='A'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    abierto=rs.getInt("abierto");
                } 
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3= new StringBuilder(
                        "SELECT count (cod_plan_detalle) cerrado " +
                        "FROM gestor.evaluacion_plan_accion_detalle EPAD  JOIN gestor.fuente_hallazgo fh using(cod_fuente_hallazgo) " +
                        "WHERE codigo_establecimiento=9 AND fh.cod_fuente_hallazgo='"+listaEvaluacionPlanAccionDetalleFuenteHallazgoEstados.get(i).getFuentehallazgo().getCodFuentehallazgo()+"' AND estado='C' "
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    cerrado=rs.getInt("cerrado");
                }
                
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql4= new StringBuilder(
                        "SELECT count (cod_plan_detalle) vencido " +
                        "FROM gestor.evaluacion_plan_accion_detalle EPAD  JOIN gestor.fuente_hallazgo fh using(cod_fuente_hallazgo) " +
                        "WHERE codigo_establecimiento=9 AND fh.cod_fuente_hallazgo='"+listaEvaluacionPlanAccionDetalleFuenteHallazgoEstados.get(i).getFuentehallazgo().getCodFuentehallazgo()+"' AND (CURRENT_DATE)>fecha_plazo AND estado='A' "
                );
                rs = consulta.ejecutar(sql4);
                while (rs.next()) {
                    vencido=rs.getInt("vencido");
                }
                
                listaEvaluacionPlanAccionDetalleEstados.add(new EvaluacionPlanAccionDetalle(listaEvaluacionPlanAccionDetalleFuenteHallazgoEstados.get(i).getFuentehallazgo().getCodFuentehallazgo(),
                listaEvaluacionPlanAccionDetalleFuenteHallazgoEstados.get(i).getFuentehallazgo().getNombre(), total, abierto, cerrado, vencido));
            }
            
            
            
            return listaEvaluacionPlanAccionDetalleEstados;
            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantidadClaseHallazgo(Integer codClaseHallazgo, String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(epad.cod_plan_detalle) cant,ch.cod_clase_hallazgo codch, ch.nombre nomch " +
                        " FROM gestor.evaluacion_plan_accion_detalle EPAD " +
                        " JOIN gestor.clase_hallazgo ch using(cod_clase_hallazgo) " +
                        condicion+" AND cod_clase_hallazgo='"+codClaseHallazgo+"' " +
                        " GROUP BY cod_clase_hallazgo, ch.nombre, ch.cod_clase_hallazgo "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantReqLegalesCerrados(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(cod_plan_detalle) AS cant" +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD " +
                    condicion+"AND cod_fuente_hallazgo='9' AND estado='C' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantIdPeligrosCerrados(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(cod_plan_detalle) AS cant" +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD " +
                    condicion+"AND cod_fuente_hallazgo='10' AND estado='C' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantAccionesCerradas(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(cod_plan_detalle) AS cant" +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD" +
                    condicion+" AND estado='C' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantAcciones(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(cod_plan_detalle) AS cant" +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD " +
                    condicion
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantAccionesEficacia(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(cod_plan_detalle) AS cant" +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD" +
                    condicion+" AND eficacia='true'"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantIdPeligros(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(cod_plan_detalle) AS cant" +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD" +
                    condicion+"AND cod_fuente_hallazgo='10' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantIdPeligrosEficacia(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(cod_plan_detalle) AS cant" +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD" +
                    condicion+"AND cod_fuente_hallazgo='10' AND eficacia='true'"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantReqLegales(String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(cod_plan_detalle) AS cant" +
                    " FROM gestor.evaluacion_plan_accion_detalle EPAD" +
                    condicion+"AND cod_fuente_hallazgo='9' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer cargarCantidadTipoAccion(Integer codTipoAccion, String condicion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        Integer cantidad=0;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT count(epad.cod_plan_detalle) cant,ch.cod_tipo_accion codta, ta.nombre nomta " +
                        " FROM gestor.evaluacion_plan_accion_detalle EPAD " +
                        " JOIN gestor.tipo_accion ta using(cod_tipo_accion) " +
                        condicion+" AND cod_tipo_accion='"+codTipoAccion+"' " +
                        " GROUP BY cod_tipo_accion, ta.nombre, ta.cod_tipo_accion"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cantidad=rs.getInt("cant");                
            }
            return cantidad;
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
    
    
    
    
    
   
    
}

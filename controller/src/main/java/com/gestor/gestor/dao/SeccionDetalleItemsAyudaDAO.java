/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.SeccionDetalleItemsAyuda;
import com.gestor.gestor.SeccionDetalleItemsAyudaPK;
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
public class SeccionDetalleItemsAyudaDAO {

    private Connection conexion;

    public SeccionDetalleItemsAyudaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<? extends SeccionDetalleItemsAyuda> cargarSeccionDetalleItemsAyudas(SeccionDetalleItemsAyudaPK pk) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_ayuda, nombre,"
                    + " definicion"
                    + " FROM gestor.seccion_detalle_items_ayuda"
                    + " WHERE cod_ciclo='" + pk.getCodCiclo() + "' AND cod_seccion=" + pk.getCodSeccion() + " AND cod_detalle=" + pk.getCodDetalle() + " AND cod_item=" + pk.getCodItem()
            );
            rs = consulta.ejecutar(sql);
            Collection<SeccionDetalleItemsAyuda> seccionDetalleItemsAyudas = new ArrayList<>();
            while (rs.next()) {
                SeccionDetalleItemsAyuda sdia = new SeccionDetalleItemsAyuda(rs.getString("cod_ciclo"), rs.getInt("cod_seccion"), rs.getInt("cod_detalle"), rs.getInt("cod_item"), rs.getInt("cod_ayuda"), rs.getString("nombre"), rs.getString("definicion"));
                seccionDetalleItemsAyudas.add(sdia);
            }
            return seccionDetalleItemsAyudas;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<SeccionDetalleItemsAyuda> cargarListaSeccionDetalleItemsayuda(String nomc,String nomsec,String nomsdetalle,String nomsditem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "select ayuda.numeral, ayuda.definicion, ayuda.nombre "
                    + " from gestor.seccion_detalle_items_ayuda ayuda "
                    + " inner join gestor.ciclo ci on(ci.cod_ciclo=ayuda.cod_ciclo) "
                    + " inner join gestor.seccion s on(s.cod_seccion=ayuda.cod_seccion) "
                    + " inner join gestor.seccion_detalle d on(d.cod_detalle=ayuda.cod_detalle) "
                    + " inner join gestor.seccion_detalle_items i on(i.cod_item=ayuda.cod_item) "
                    + " where ci.nombre='"+nomc+"' and s.nombre='"+nomsec+"' and d.nombre='"+nomsdetalle+"' and i.nombre='"+nomsditem+"' "

            );
            rs = consulta.ejecutar(sql);
            List<SeccionDetalleItemsAyuda> secciondetalleitemsayudas = new ArrayList<>();
            while (rs.next()) {
                SeccionDetalleItemsAyuda sdi = new SeccionDetalleItemsAyuda(new SeccionDetalleItemsAyudaPK(null, 0, 0, 0), rs.getString("nombre"),rs.getString("definicion"),rs.getString("numeral"));
                secciondetalleitemsayudas.add(sdi);
            }                              
            return secciondetalleitemsayudas;
            } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
        
    public void insertarEstandar(String numSecc, int codseccion, int coddetalle, int coditem, String cod_ciclo, String numeral1,String numeral2, String numeral3,String criterio,String mlegal, String mdeverif) throws SQLException {
        ResultSet rs ;
        Consulta consulta = null;
        Consulta consulta1 ;
        Consulta consulta2 ;
        String mlegaldef=null;
        String cridef=null;
        String mdverifdef=null;
        try {
            
                consulta = new Consulta(this.conexion);
                StringBuilder sqls1 = new StringBuilder(
                        "SELECT definicion "
                        + " FROM gestor.seccion_detalle_items_ayuda "                        
                        + " where numeral = '"+numeral1+"' "
                );                
                rs = consulta.ejecutar(sqls1);
                if(rs.next()){
                mlegaldef=rs.getString("definicion");
                }
            
                if(mlegaldef==null){
                    consulta = new Consulta(this.conexion);
                    StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.seccion_detalle_items_ayuda "
                    + " ( cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_ayuda, nombre, definicion, numeral ) "                    
                    + " VALUES ('"+cod_ciclo+"', '"+codseccion+"','"+coddetalle+"', '"+coditem+"', 1, 'MARCO LEGAL', '"+mlegal+"', '"+numSecc+".1')"  
                            
                    );
                consulta.actualizar(sql);
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sqls2 = new StringBuilder(
                        "SELECT definicion "
                        + " FROM gestor.seccion_detalle_items_ayuda "                        
                        + " where numeral = '"+numeral2+"' "
                );                
                rs = consulta.ejecutar(sqls2);
                if(rs.next()){
                cridef=rs.getString("definicion");
                }
            
                if(cridef==null){
                    consulta = new Consulta(this.conexion);
                    StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.seccion_detalle_items_ayuda "
                    + " ( cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_ayuda, nombre, definicion, numeral ) "                    
                    + " VALUES ('"+cod_ciclo+"', '"+codseccion+"','"+coddetalle+"', '"+coditem+"', 2, 'CRITERIO', '"+criterio+"', '"+numSecc+".2')"                    
                     );
                consulta.actualizar(sql);
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                        "SELECT definicion "
                        + " FROM gestor.seccion_detalle_items_ayuda "                        
                        + " where numeral = '"+numeral3+"' "
                );                
                rs = consulta.ejecutar(sql3);
                if(rs.next()){
                    mdverifdef=rs.getString("definicion");
                }


            
                if(mdverifdef==null){
                    consulta = new Consulta(this.conexion);
                    StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.seccion_detalle_items_ayuda "
                    + " ( cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_ayuda, nombre, definicion, numeral ) "                    
                    + " VALUES ('"+cod_ciclo+"', '"+codseccion+"','"+coddetalle+"', '"+coditem+"', 3, 'MODO DE VERIFICACIÓN', '"+mdeverif+"', '"+numSecc+".3')"                    
                     );
                consulta.actualizar(sql);
                }                
                
                
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                        "UPDATE gestor.seccion_detalle_items_ayuda"
                        + " set definicion= '"+mlegal+"' "
                        + " where numeral = '"+numeral1+"' "
                );
                consulta.actualizar(sql1);
                
                
                consulta1 = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        "UPDATE gestor.seccion_detalle_items_ayuda"
                        + " set definicion= '"+criterio+"' "
                        + " where numeral = '"+numeral2+"' "
                );
                consulta1.actualizar(sql);
                
                
                consulta2 = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                        "UPDATE gestor.seccion_detalle_items_ayuda"
                        + " set definicion= '"+mdeverif+"' "
                        + " where numeral = '"+numeral3+"' "
                );
                consulta2.actualizar(sql2);
                
            } finally {
                if (consulta != null) {
                    consulta.desconectar();
                }
            }
        }

}

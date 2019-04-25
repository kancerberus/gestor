/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import java.sql.Connection;
import java.sql.SQLException;
import com.gestor.seguimiento.PlanSeccionDetalleItemTexto;
import com.gestor.seguimiento.PlanSeccionDetalleItem;
import com.gestor.seguimiento.PlanSeccionDetalleItemTextoPK;
import conexion.Consulta;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author franj
 */
public class PlanSeccionDetalleItemTextoDAO {
    
    private Connection conexion;
        
    public PlanSeccionDetalleItemTextoDAO(Connection conexion) throws Exception {
    this.conexion = conexion;
    }
    
    public void insertarPlanseccionDetalleItemTexto(PlanSeccionDetalleItemTexto plansecciondetalleitemtexto) throws SQLException {
        Consulta consulta = null;
        if(!plansecciondetalleitemtexto.getTexto().isEmpty()){
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_seccion_detalle_item_texto "
                    + " ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item, cod_seccion_detalle_item_texto, texto )"
                    + " VALUES ( '"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodigoEstablecimiento()+"', '"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodTitulo()+"',  "
                    + " '"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodSeccion()+"', '"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodSeccionDetalle()+"', '"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodSeccionDetalleItem()+"', "
                    + " '"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodSeccionDetalleItemTexto()+"', '"+plansecciondetalleitemtexto.getTexto()+"') "
                    + " ON CONFLICT ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item, cod_seccion_detalle_item_texto) DO UPDATE"
                    + " SET texto=EXCLUDED.texto "
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
        }else{
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + "FROM seguimiento.plan_seccion_detalle_item_texto "
                    + "WHERE codigo_establecimiento='"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodigoEstablecimiento()+"' AND cod_titulo='"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodTitulo()+"'"
                    + " AND cod_seccion='"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodSeccion()+"' AND cod_seccion_detalle='"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodSeccionDetalle()+"' AND cod_seccion_detalle_item='"+plansecciondetalleitemtexto.getPlanSeccionDetalleItemTextoPK().getCodSeccionDetalleItem()+"' AND cod_seccion_detalle_item_texto='1' "
            );
            consulta.actualizar(sql);            
        }
    }
    
    public Collection<? extends PlanSeccionDetalleItemTexto> cargarPlanSecciondetalleitemtextoList(PlanSeccionDetalleItem planSecciondetalleitem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item, cod_seccion_detalle_item_texto, texto"
                    + " FROM seguimiento.plan_seccion_detalle_item_texto"
                    + " WHERE  codigo_establecimiento="+ planSecciondetalleitem.getPlanSeccionDetalleItemPK().getCodigoEstablecimiento()+" AND cod_titulo=" + planSecciondetalleitem.getPlanSeccionDetalleItemPK().getCodTitulo()+ " "
                    + " AND  cod_seccion="+ planSecciondetalleitem.getPlanSeccionDetalleItemPK().getCodSeccion()+" AND cod_seccion_detalle="+planSecciondetalleitem.getPlanSeccionDetalleItemPK().getCodSeccionDetalle()+" "
                    + " AND  cod_seccion_detalle_item="+planSecciondetalleitem.getPlanSeccionDetalleItemPK().getCodSeccionDetalleItem()+" "
                    
            );

            rs = consulta.ejecutar(sql);
            Collection<PlanSeccionDetalleItemTexto> planSeccionDetalleItemTextoList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionDetalleItemTexto ptit = new PlanSeccionDetalleItemTexto(
                        new PlanSeccionDetalleItemTextoPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion") ,rs.getInt("cod_seccion_detalle"), rs.getInt("cod_seccion_detalle_item"),rs.getInt("cod_seccion_detalle_item_texto")                        
                        ), rs.getString("texto")
                );
                planSeccionDetalleItemTextoList.add(ptit);
            }
            return planSeccionDetalleItemTextoList;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.seguimiento.PlanSeccionDetalle;
import com.gestor.seguimiento.PlanSeccionDetalleItem;
import com.gestor.seguimiento.PlanSeccionDetalleItemPK;
import com.gestor.seguimiento.PlanSeccionDetallePK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julian D Osorio G
 */
public class PlanSeccionDetalleItemDAO {

    private Connection conexion;

    public PlanSeccionDetalleItemDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<PlanSeccionDetalleItem> cargarPlanSeccionDetalleItem(PlanSeccionDetallePK planSeccionDetallePK) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT PSDI.cod_titulo, PSDI.codigo_establecimiento, PSDI.cod_seccion, PSDI.cod_seccion_detalle,"
                    + " PSDI.cod_seccion_detalle_item, PSDI.numeral, PSDI.nombre"
                    + " FROM seguimiento.plan_seccion_detalle_item PSDI"
                    + " WHERE cod_titulo=" + planSeccionDetallePK.getCodTitulo()
                    + " AND codigo_establecimiento=" + planSeccionDetallePK.getCodigoEstablecimiento()
                    + " AND cod_seccion=" + planSeccionDetallePK.getCodSeccion() + " AND cod_seccion_detalle=" + planSeccionDetallePK.getCodSeccionDetalle()
            );
            rs = consulta.ejecutar(sql);
            List<PlanSeccionDetalleItem> planSeccionDetalleItemList = new ArrayList<>();
            while (rs.next()) {
                //int codTitulo, int codigoEstablecimiento, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem
                PlanSeccionDetalleItem ps = new PlanSeccionDetalleItem(
                        new PlanSeccionDetalleItemPK(
                                rs.getInt("cod_titulo"), rs.getInt("codigo_establecimiento"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle"), rs.getInt("cod_seccion_detalle_item")
                        ),
                        rs.getString("numeral"), rs.getString("nombre"));
                planSeccionDetalleItemList.add(ps);
            }
            return planSeccionDetalleItemList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
        public void insertarPlansecciondetalleitem(PlanSeccionDetalleItem plansecciondetalleitem) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_seccion_detalle_item "
                    + " ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item, nombre, numeral)"
                    +" VALUES ('"+plansecciondetalleitem.getPlanSeccionDetalleItemPK().getCodigoEstablecimiento()+"', "
                            + " '"+plansecciondetalleitem.getPlanSeccionDetalleItemPK().getCodTitulo()+"', "
                            + " '"+plansecciondetalleitem.getPlanSeccionDetalleItemPK().getCodSeccion()+"', "
                            + " '"+plansecciondetalleitem.getPlanSeccionDetalleItemPK().getCodSeccionDetalle()+"',  "
                            + " '"+plansecciondetalleitem.getPlanSeccionDetalleItemPK().getCodSeccionDetalleItem() +"', "
                            + " '"+plansecciondetalleitem.getNombre()+"','"+plansecciondetalleitem.getNumeral()+"') "
                            + " ON CONFLICT (cod_titulo, codigo_establecimiento, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item) DO UPDATE"
                            + " SET nombre=EXCLUDED.nombre, numeral=EXCLUDED.numeral "
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
        
    public void eliminarPlansecciondetalleitem(PlanSeccionDetalleItem psdi) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM seguimiento.plan_seccion_detalle_item "
                    + " WHERE codigo_establecimiento='"+psdi.getPlanSeccionDetalleItemPK().getCodTitulo()+"' AND cod_titulo='"+psdi.getPlanSeccionDetalleItemPK().getCodigoEstablecimiento()+"'"
                    + " AND cod_seccion='"+psdi.getPlanSeccionDetalleItemPK().getCodSeccion()+"' AND cod_seccion_detalle='"+psdi.getPlanSeccionDetalleItemPK().getCodSeccionDetalle()+"' "
                    + " AND cod_seccion_detalle_item='"+psdi.getPlanSeccionDetalleItemPK().getCodSeccionDetalleItem()+"'"
                    
                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<PlanSeccionDetalleItem> cargarPlanSecciondetalleitemList(PlanSeccionDetalle psd) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item,  nombre, numeral"                    
                    + " FROM seguimiento.plan_seccion_detalle_item"
                    + " WHERE codigo_establecimiento= '"+ psd.getPlanSeccionDetallePK().getCodigoEstablecimiento() +"' AND cod_titulo='" + psd.getPlanSeccionDetallePK().getCodTitulo()+"' "
                    + " AND cod_seccion= '"+psd.getPlanSeccionDetallePK().getCodSeccion()+"' AND cod_seccion_detalle='"+psd.getPlanSeccionDetallePK().getCodSeccionDetalle()+"' "
                    + " ORDER BY cod_seccion_detalle_item"
            );
            rs = consulta.ejecutar(sql);
            List<PlanSeccionDetalleItem> planSecciondetalleitemList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionDetalleItem psdi = new PlanSeccionDetalleItem(new PlanSeccionDetalleItemPK(rs.getInt("codigo_establecimiento")
                , rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle"), rs.getInt("cod_seccion_detalle_item") ), rs.getString("numeral"), rs.getString("nombre")
                );
                planSecciondetalleitemList.add(psdi);
            }
            return planSecciondetalleitemList;
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

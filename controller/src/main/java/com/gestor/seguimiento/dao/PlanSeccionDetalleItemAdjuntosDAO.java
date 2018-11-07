/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.seguimiento.PlanSeccionAdjuntos;
import com.gestor.seguimiento.PlanSeccionAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalleItemAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalleItemAdjuntosPK;
import com.gestor.seguimiento.PlanSeccionAdjuntosPK;
import com.gestor.seguimiento.PlanSeccionDetalleItem;
import com.gestor.seguimiento.PlanSeccionDetalleItemPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author Julian D Osorio G
 */
public class PlanSeccionDetalleItemAdjuntosDAO {

    private Connection conexion;

    public PlanSeccionDetalleItemAdjuntosDAO(Connection conexion) {
        this.conexion = conexion;
    }        
            
    public Collection<? extends PlanSeccionDetalleItemAdjuntos> cargarPlanSecciondetalleitemadjuntosList(PlanSeccionDetalleItem planitem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanSeccionDetalleItemAdjuntos> planSecciondetalleitemadjuntosList = new ArrayList<>();
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item, cod_seccion_detalle_item_adjuntos, cod_categoria, cod_categoria_tipo, titulo, descripcion, actividad, descripcion_general, documento"
                    + " FROM seguimiento.plan_seccion_detalle_item_adjuntos"
                    + " WHERE codigo_establecimiento='"+planitem.getPlanSeccionDetalleItemPK().getCodigoEstablecimiento()+"' AND cod_titulo='"+planitem.getPlanSeccionDetalleItemPK().getCodTitulo()+"' "                    
                    + " AND cod_seccion= '"+planitem.getPlanSeccionDetalleItemPK().getCodSeccion()+"' AND cod_seccion_detalle='"+planitem.getPlanSeccionDetalleItemPK().getCodSeccionDetalle()+"' "
                    + " AND cod_seccion_detalle_item='"+planitem.getPlanSeccionDetalleItemPK().getCodSeccionDetalleItem()+"' " 
                    + " ORDER BY cod_seccion_detalle_item_adjuntos"  
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanSeccionDetalleItemAdjuntos psdia = new PlanSeccionDetalleItemAdjuntos(new PlanSeccionDetalleItemAdjuntosPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle"), rs.getInt("cod_seccion_detalle_item"), rs.getInt("cod_seccion_detalle_item_adjuntos")),
                        rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo"), rs.getString("titulo"), rs.getString("descripcion"), rs.getString("actividad"), rs.getString("descripcion_general"), rs.getString("documento")
                );
                planSecciondetalleitemadjuntosList.add(psdia);
            }
            return planSecciondetalleitemadjuntosList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarPlansecciondetalleitemadjunto(PlanSeccionDetalleItemAdjuntos plansecciondetalleitemadjuntos) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_seccion_detalle_item_adjuntos "
                    + " ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item, cod_seccion_detalle_item_adjuntos, cod_categoria, cod_categoria_tipo, titulo, descripcion, actividad, descripcion_general, documento )"
                    + " VALUES ('"+plansecciondetalleitemadjuntos.getPlanSeccionDetalleItemAdjuntosPK().getCodigoEstablecimiento()+"', '"+plansecciondetalleitemadjuntos.getPlanSeccionDetalleItemAdjuntosPK().getCodTitulo()+"', '"+plansecciondetalleitemadjuntos.getPlanSeccionDetalleItemAdjuntosPK().getCodSeccion()+"', "
                    + " '"+plansecciondetalleitemadjuntos.getPlanSeccionDetalleItemAdjuntosPK().getCodSeccionDetalle()+"', '"+plansecciondetalleitemadjuntos.getPlanSeccionDetalleItemAdjuntosPK().getCodSeccionDetalleItem()+"', '"+plansecciondetalleitemadjuntos.getPlanSeccionDetalleItemAdjuntosPK().getCodSeccionDetalleItemAdjuntos()+"', "                    
                    + " '"+plansecciondetalleitemadjuntos.getCodCategoria()+"', '"+plansecciondetalleitemadjuntos.getCodCategoriaTipo()+"',  '"+plansecciondetalleitemadjuntos.getTitulo()+"', '"+plansecciondetalleitemadjuntos.getDescripcion()+"', '"+ plansecciondetalleitemadjuntos.getActividad()+"', '"+plansecciondetalleitemadjuntos.getDescripcionGeneral()+"', '"+plansecciondetalleitemadjuntos.getDocumento()+"') "
                    + " ON CONFLICT ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_item, cod_seccion_detalle_item_adjuntos ) DO UPDATE "
                    + " SET titulo=EXCLUDED.titulo, descripcion=EXCLUDED.descripcion, actividad=EXCLUDED.actividad, descripcion_general=EXCLUDED.descripcion_general, documento=EXCLUDED.documento "
                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    

}
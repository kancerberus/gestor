/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.AdjuntosCategoriaTipoPK;
import com.gestor.seguimiento.PlanSeccionAdjuntos;
import com.gestor.seguimiento.PlanSeccionAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalleAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalle;
import com.gestor.seguimiento.PlanSeccionDetalleAdjuntosPK;
import com.gestor.seguimiento.PlanSeccionAdjuntosPK;
import com.gestor.seguimiento.PlanSeccion;
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
public class PlanSeccionDetalleAdjuntosDAO {

    private Connection conexion;

    public PlanSeccionDetalleAdjuntosDAO(Connection conexion) {
        this.conexion = conexion;
    }        
            
    public Collection<? extends PlanSeccionDetalleAdjuntos> cargarPlanSecciondetalleadjuntosList(PlanSeccionDetalle plandetalle) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanSeccionDetalleAdjuntos> planSecciondetalleadjuntosList = new ArrayList<>();
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_adjuntos, cod_categoria, cod_categoria_tipo, titulo, pda.descripcion as descripcionpda, documento, "
                    + " cod_categoria, ct.nombre as nombrect, "
                    + " cod_categoria_tipo, ctt.nombre as nombrectt "
                    + " FROM seguimiento.plan_seccion_detalle_adjuntos pda"
                    + " JOIN gestor.adjuntos_categoria ct USING (cod_categoria)"
                    + " JOIN gestor.adjuntos_categoria_tipo ctt USING (cod_categoria, cod_categoria_tipo)"
                    + " WHERE codigo_establecimiento='"+plandetalle.getPlanSeccionDetallePK().getCodigoEstablecimiento()+"' AND cod_titulo='"+plandetalle.getPlanSeccionDetallePK().getCodTitulo()+"' "                    
                    + " AND cod_seccion= '"+plandetalle.getPlanSeccionDetallePK().getCodSeccion()+"' AND cod_seccion_detalle='"+plandetalle.getPlanSeccionDetallePK().getCodSeccionDetalle()+"' "                    
                    + " ORDER BY cod_seccion_detalle_adjuntos"  
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanSeccionDetalleAdjuntos psda = new PlanSeccionDetalleAdjuntos(new PlanSeccionDetalleAdjuntosPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_detalle"), rs.getInt("cod_seccion_detalle_adjuntos")),
                        rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo"), rs.getString("titulo"), rs.getString("descripcionpda"), rs.getString("documento")
                );
                psda.setAdjuntosCategoria(new AdjuntosCategoria(rs.getInt("cod_categoria"), rs.getString("nombrect"), 0));
                psda.getAdjuntosCategoria().setAdjuntosCategoriaTipo(new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo")), rs.getString("nombrectt")));
                planSecciondetalleadjuntosList.add(psda);
            }
            return planSecciondetalleadjuntosList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarPlansecciondetalleadjunto(PlanSeccionDetalleAdjuntos plansecciondetalleadjuntos) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_seccion_detalle_adjuntos "
                    + " ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_adjuntos, cod_categoria, cod_categoria_tipo, titulo, descripcion, documento )"
                    + " VALUES ('"+plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodigoEstablecimiento()+"', '"+plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodTitulo()+"', '"+plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodSeccion()+"', "
                    + " '"+plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodSeccionDetalle()+"', '"+plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodSeccionDetalleAdjuntos()+"', '"+plansecciondetalleadjuntos.getCodCategoria()+"', "
                    + " '"+plansecciondetalleadjuntos.getCodCategoriaTipo()+"', '"+plansecciondetalleadjuntos.getTitulo()+"', '"+plansecciondetalleadjuntos.getDescripcion()+"', '"+plansecciondetalleadjuntos.getDocumento()+"') "
                    + " ON CONFLICT ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_adjuntos ) DO UPDATE "
                    + " SET titulo=EXCLUDED.titulo, descripcion=EXCLUDED.descripcion, documento=EXCLUDED.documento "
                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    

}

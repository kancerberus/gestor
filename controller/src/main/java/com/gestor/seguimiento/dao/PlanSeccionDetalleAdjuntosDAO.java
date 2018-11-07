/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import com.gestor.seguimiento.PlanSeccionAdjuntos;
import com.gestor.seguimiento.PlanSeccionAdjuntos;
import com.gestor.seguimiento.PlanSeccionDetalleAdjuntos;
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
            
    /*public Collection<? extends PlanSeccionAdjuntos> cargarPlanSeccionadjuntosList(PlanSeccion plantitulo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanSeccionAdjuntos> planSeccionadjuntosList = new ArrayList<>();
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_titulo_adjunto, cod_categoria, cod_categoria_tipo, titulo, descripcion, documento"
                    + " FROM seguimiento.plan_titulo_adiuntos"
                    + " WHERE codigo_establecimiento='"+plantitulo.getPlanSeccionPK().getCodigoEstablecimiento()+"' AND cod_titulo='"+plantitulo.getPlanSeccionPK().getCodSeccion()+"' "
                    + " ORDER BY cod_titulo_adjunto"  
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanSeccionAdiuntos pta = new PlanSeccionAdiuntos(new PlanSeccionAdiuntosPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo_adjunto"),
                        rs.getInt("cod_titulo")), rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo"), rs.getString("titulo"), rs.getString("descripcion"), rs.getString("documento")
                );
                planSeccionadjuntosList.add(pta);
            }
            return planSeccionadjuntosList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }*/
    
    public void insertarPlansecciondetalleadjunto(PlanSeccionDetalleAdjuntos plansecciondetalleadjuntos) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_seccion_detalle_adjuntos "
                    + " ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle_adjuntos, cod_categoria, cod_categoria_tipo, titulo, descripcion, documento )"
                    + " VALUES ('"+plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodigoEstablecimiento()+"', '"+plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodTitulo()+"', '"+plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodSeccion()+"', "
                    + " "+plansecciondetalleadjuntos.getPlanSeccionDetalleAdjuntosPK().getCodSeccionDetalleAdjuntos()+"', '"+plansecciondetalleadjuntos.getCodCategoria()+"', "
                    + " '"+plansecciondetalleadjuntos.getCodCategoriaTipo()+"', '"+plansecciondetalleadjuntos.getTitulo()+"', '"+plansecciondetalleadjuntos.getDescripcion()+"', '"+plansecciondetalleadjuntos.getDocumento()+"') "
                    + " ON CONFLICT ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_adjuntos ) DO UPDATE "
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

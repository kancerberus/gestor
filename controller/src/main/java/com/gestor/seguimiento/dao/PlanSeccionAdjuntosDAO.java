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
public class PlanSeccionAdjuntosDAO {

    private Connection conexion;

    public PlanSeccionAdjuntosDAO(Connection conexion) {
        this.conexion = conexion;
    }        
            
    public Collection<? extends PlanSeccionAdjuntos> cargarPlanSeccionadjuntosList(PlanSeccion planseccion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            Collection<PlanSeccionAdjuntos> planSeccionadjuntosList = new ArrayList<>();
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_adjunto, cod_categoria, cod_categoria_tipo, titulo, psa.descripcion as descripcionpsa, documento, "
                    + " cod_categoria, ct.nombre as nombrect, "
                    + " cod_categoria_tipo, ctt.nombre as nombrectt "
                    + " FROM seguimiento.plan_seccion_adjuntos psa"
                    + " JOIN gestor.adjuntos_categoria ct USING (cod_categoria)"
                    + " JOIN gestor.adjuntos_categoria_tipo ctt USING (cod_categoria, cod_categoria_tipo)"
                    + " WHERE codigo_establecimiento='"+planseccion.getPlanSeccionPK().getCodigoEstablecimiento()+"' AND cod_titulo='"+planseccion.getPlanSeccionPK().getCodTitulo()+"' "                     
                    + " ORDER BY cod_seccion_adjunto"  
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                PlanSeccionAdjuntos psa = new PlanSeccionAdjuntos(new PlanSeccionAdjuntosPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion"), rs.getInt("cod_seccion_adjunto")),
                        rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo"), rs.getString("titulo"), rs.getString("descripcionpsa"), rs.getString("documento")
                );                
                psa.setAdjuntosCategoria(new AdjuntosCategoria(rs.getInt("cod_categoria"), rs.getString("nombrect"), 0));
                psa.getAdjuntosCategoria().setAdjuntosCategoriaTipo(new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo")), rs.getString("nombrectt")));
                planSeccionadjuntosList.add(psa);
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
    }
    
    public void insertarPlanseccionadjunto(PlanSeccionAdjuntos planseccionadjuntos) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_seccion_adjuntos "
                    + " ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_adjunto, cod_categoria, cod_categoria_tipo, titulo, descripcion, documento )"
                    + " VALUES ('"+planseccionadjuntos.getPlanSeccionAdjuntosPK().getCodigoEstablecimiento()+"', '"+planseccionadjuntos.getPlanSeccionAdjuntosPK().getCodTitulo()+"', '"+planseccionadjuntos.getPlanSeccionAdjuntosPK().getCodSeccion()+"', "
                    + "'"+planseccionadjuntos.getPlanSeccionAdjuntosPK().getCodSeccionAdjunto()+"', '"+planseccionadjuntos.getCodCategoria()+"', "
                    + " '"+planseccionadjuntos.getCodCategoriaTipo()+"', '"+planseccionadjuntos.getTitulo()+"', '"+planseccionadjuntos.getDescripcion()+"', '"+planseccionadjuntos.getDocumento()+"') "
                    + " ON CONFLICT ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_adjunto ) DO UPDATE "
                    + " SET titulo=EXCLUDED.titulo, descripcion=EXCLUDED.descripcion, documento=EXCLUDED.documento "
                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarPlanseccionadjunto(PlanSeccionAdjuntos planseccionadjuntos) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM seguimiento.plan_seccion_adjuntos "
                    + " WHERE codigo_establecimiento='"+planseccionadjuntos.getPlanSeccionAdjuntosPK().getCodigoEstablecimiento()+"' AND "
                    + "cod_titulo='"+planseccionadjuntos.getPlanSeccionAdjuntosPK().getCodTitulo()+"' AND "
                    + "cod_seccion='"+planseccionadjuntos.getPlanSeccionAdjuntosPK().getCodSeccion()+"' AND "
                    + "cod_seccion_adjunto='"+planseccionadjuntos.getPlanSeccionAdjuntosPK().getCodSeccionAdjunto()+"' "
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    

    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import conexion.Consulta;
import java.sql.Connection;
import java.sql.SQLException;
import com.gestor.seguimiento.PlanSeccionDetalleTexto;
import com.gestor.seguimiento.PlanSeccionDetalle;
import com.gestor.seguimiento.PlanSeccionDetalleTextoPK;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author franj
 */
public class PlanSeccionDetalleTextoDAO {
    
    private Connection conexion;
        
    public PlanSeccionDetalleTextoDAO(Connection conexion) throws Exception {
    this.conexion = conexion;
    }
        
    public void insertarPlanseccionDetalleTexto(PlanSeccionDetalleTexto plansecciondetalletexto) throws SQLException {
        Consulta consulta = null;
        if(!plansecciondetalletexto.getTexto().isEmpty()){
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_seccion_detalle_texto "
                    + " ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_texto, texto )"
                    + " VALUES ('"+plansecciondetalletexto.getPlanSeccionDetalleTextoPK().getCodigoEstablecimiento()+"', '"+plansecciondetalletexto.getPlanSeccionDetalleTextoPK().getCodTitulo()+"', "
                    + " '"+plansecciondetalletexto.getPlanSeccionDetalleTextoPK().getCodSeccion()+"', '"+plansecciondetalletexto.getPlanSeccionDetalleTextoPK().getCodSeccionDetalle()+"', "
                    + " '"+plansecciondetalletexto.getPlanSeccionDetalleTextoPK().getCodSeccionDetalleTexto()+"', '"+plansecciondetalletexto.getTexto()+"') "
                    + " ON CONFLICT (cod_titulo, codigo_establecimiento, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_texto) DO UPDATE"
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
                    + "FROM seguimiento.plan_seccion_detalle_texto "
                    + "WHERE codigo_establecimiento='"+plansecciondetalletexto.getPlanSeccionDetalleTextoPK().getCodigoEstablecimiento()+"' AND cod_titulo='"+plansecciondetalletexto.getPlanSeccionDetalleTextoPK().getCodTitulo()+"'"
                    + " AND cod_seccion='"+plansecciondetalletexto.getPlanSeccionDetalleTextoPK().getCodSeccion()+"' AND cod_seccion_detalle='"+plansecciondetalletexto.getPlanSeccionDetalleTextoPK().getCodSeccionDetalle()+"' AND cod_seccion_detalle_texto='1' "
            );
            consulta.actualizar(sql);            
        }
    }
    
    public Collection<? extends PlanSeccionDetalleTexto> cargarPlanSecciondetalletextoList(PlanSeccionDetalle planSecciondetalle) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_detalle, cod_seccion_detalle_texto, texto"
                    + " FROM seguimiento.plan_seccion_detalle_texto"
                    + " WHERE codigo_establecimiento=" + planSecciondetalle.getPlanSeccionDetallePK().getCodigoEstablecimiento() + " AND cod_titulo=" + planSecciondetalle.getPlanSeccionDetallePK().getCodTitulo()
                    + " AND  cod_seccion="+ planSecciondetalle.getPlanSeccionDetallePK().getCodSeccion()+" AND cod_seccion_detalle="+planSecciondetalle.getPlanSeccionDetallePK().getCodSeccionDetalle()+" "
            );

            rs = consulta.ejecutar(sql);
            Collection<PlanSeccionDetalleTexto> planSeccionDetalleTextoList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionDetalleTexto ptt = new PlanSeccionDetalleTexto(
                        new PlanSeccionDetalleTextoPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion") ,rs.getInt("cod_seccion_detalle"),rs.getInt("cod_seccion_detalle_texto")                        
                        ), rs.getString("texto")
                );
                planSeccionDetalleTextoList.add(ptt);
            }
            return planSeccionDetalleTextoList;
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

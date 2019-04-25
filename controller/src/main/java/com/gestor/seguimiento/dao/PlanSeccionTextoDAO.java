/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento.dao;

import conexion.Consulta;
import java.sql.Connection;
import java.sql.SQLException;
import com.gestor.seguimiento.PlanSeccion;
import com.gestor.seguimiento.PlanSeccionTexto;
import com.gestor.seguimiento.PlanSeccionTextoPK;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author franj
 */
public class PlanSeccionTextoDAO {
    
    private Connection conexion;

    public PlanSeccionTextoDAO(Connection conexion) throws Exception {
        this.conexion = conexion;
    }
    
    public void insertarPlanseccionTexto(PlanSeccionTexto plansecciontexto) throws SQLException {
        Consulta consulta = null;                
        if(!plansecciontexto.getTexto().isEmpty()){
            try {
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    "INSERT INTO seguimiento.plan_seccion_texto "
                    + " ( codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_texto, texto )"
                    + " VALUES ('"+plansecciontexto.getPlanSeccionTextoPK().getCodigoEstablecimiento()+"', '"+plansecciontexto.getPlanSeccionTextoPK().getCodTitulo()+"', "
                    + " '"+plansecciontexto.getPlanSeccionTextoPK().getCodSeccion()+"', '"+plansecciontexto.getPlanSeccionTextoPK().getCodSeccionTexto()+"', '"+plansecciontexto.getTexto()+"') "
                    + " ON CONFLICT (cod_titulo, codigo_establecimiento, cod_seccion, cod_seccion_texto) DO UPDATE"
                    + " SET texto=EXCLUDED.texto "
                );
                consulta.actualizar(sql);
             
            }finally {
                if (consulta != null) {
                    consulta.desconectar();
                }
            }
        }else{
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + "FROM seguimiento.plan_seccion_texto "
                    + "WHERE codigo_establecimiento='"+plansecciontexto.getPlanSeccionTextoPK().getCodigoEstablecimiento()+"' AND cod_titulo='"+plansecciontexto.getPlanSeccionTextoPK().getCodTitulo()+"'"
                    + " AND cod_seccion='"+plansecciontexto.getPlanSeccionTextoPK().getCodSeccion()+"' AND cod_seccion_texto='1' "
            );
            consulta.actualizar(sql);            
        }
    }
    
    public Collection<? extends PlanSeccionTexto> cargarPlanSecciontextoList(PlanSeccion planSeccion) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_titulo, cod_seccion, cod_seccion_texto, texto"
                    + " FROM seguimiento.plan_seccion_texto"
                    + " WHERE codigo_establecimiento=" + planSeccion.getPlanSeccionPK().getCodigoEstablecimiento() + " AND cod_titulo=" + planSeccion.getPlanSeccionPK().getCodTitulo()
                    + " AND  cod_seccion="+ planSeccion.getPlanSeccionPK().getCodSeccion()+" "
            );

            rs = consulta.ejecutar(sql);
            Collection<PlanSeccionTexto> planSeccionTextoList = new ArrayList<>();
            while (rs.next()) {
                PlanSeccionTexto ptt = new PlanSeccionTexto(
                        new PlanSeccionTextoPK(rs.getInt("codigo_establecimiento"), rs.getInt("cod_titulo"), rs.getInt("cod_seccion") ,rs.getInt("cod_seccion_texto")
                        ), rs.getString("texto")
                );
                planSeccionTextoList.add(ptt);
            }
            return planSeccionTextoList;
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

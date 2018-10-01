/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.AdjuntosCategoriaTipoPK;
import com.gestor.gestor.SeccionDetalleItemsPK;
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
public class AdjuntosCategoriaDAO {

    private Connection conexion;

    public AdjuntosCategoriaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<? extends AdjuntosCategoria> cargarListaAdjuntosCategoria(SeccionDetalleItemsPK pk) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT AC.cod_categoria, AC.nombre, AC.descripcion, AC.meses_vigencia"
                    + " FROM gestor.seccion_detalle_items_adjuntos_categoria SDIAC"
                    + " JOIN gestor.adjuntos_categoria AC USING (cod_categoria)"
                    + " WHERE SDIAC.cod_ciclo='" + pk.getCodCiclo() + "' AND SDIAC.cod_seccion=" + pk.getCodSeccion()
                    + " AND SDIAC.cod_detalle=" + pk.getCodDetalle() + " AND SDIAC.cod_item=" + pk.getCodItem()
            );
            rs = consulta.ejecutar(sql);
            Collection<AdjuntosCategoria> adjuntosCategorias = new ArrayList<>();
            while (rs.next()) {
                AdjuntosCategoria ac = new AdjuntosCategoria(rs.getInt("cod_categoria"), rs.getString("nombre"), rs.getString("descripcion"), rs.getInt("meses_vigencia"));
                adjuntosCategorias.add(ac);
            }
            return adjuntosCategorias;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public Collection<? extends AdjuntosCategoriaTipo> cargarListaAdjuntosCategoriaTipo(Integer codCategoria) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_categoria, cod_categoria_tipo, nombre, descripcion"
                    + " FROM gestor.adjuntos_categoria_tipo"
                    + " WHERE cod_categoria=" + codCategoria
            );
            rs = consulta.ejecutar(sql);
            Collection<AdjuntosCategoriaTipo> adjuntosCategoriaTipos = new ArrayList<>();
            while (rs.next()) {
                AdjuntosCategoriaTipo act = new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo")), rs.getString("nombre"));
                adjuntosCategoriaTipos.add(act);
            }
            return adjuntosCategoriaTipos;
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

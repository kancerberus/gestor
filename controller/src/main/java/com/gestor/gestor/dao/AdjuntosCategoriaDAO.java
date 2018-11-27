/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor.dao;

import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.AdjuntosCategoriaTipoPK;
import com.gestor.gestor.SeccionDetalleItems;
import com.gestor.gestor.SeccionDetalleItemsAdjuntosCategorias;
import com.gestor.gestor.SeccionDetalleItemsPK;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.crypto.SealedObject;

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
    
    public Collection<? extends AdjuntosCategoria> cargarListaAdjuntosCategoriapm() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_categoria, nombre, descripcion, meses_vigencia "
                    + " FROM gestor.adjuntos_categoria "                    
                    
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
    
    public Collection<? extends AdjuntosCategoria> cargarListaAdjuntosCategoria() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT sdi.numeral, ac.cod_categoria, ac.nombre, ac.descripcion, ac.meses_vigencia "
                    + " FROM gestor.seccion_detalle_items_adjuntos_categoria "
                    + " JOIN gestor.adjuntos_categoria ac using (cod_categoria) "
                    + " JOIN gestor.seccion_detalle_items sdi using (cod_seccion, cod_item, cod_ciclo,cod_detalle) "                    
                    + " ORDER BY ac.cod_categoria"
            );
            rs = consulta.ejecutar(sql);
            Collection<AdjuntosCategoria> adjuntosCategorias = new ArrayList<>();
            while (rs.next()) {
                AdjuntosCategoria ac = new AdjuntosCategoria(rs.getInt("cod_categoria"), rs.getString("nombre"), rs.getString("descripcion"), rs.getInt("meses_vigencia"));
                ac.setSecciondetalleitems(new SeccionDetalleItems(rs.getString("numeral")));
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
                AdjuntosCategoriaTipo act = new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(rs.getInt("cod_categoria"), rs.getInt("cod_categoria_tipo")), rs.getString("nombre"), rs.getString("descripcion"));
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
    
    public void insertarCategoria(AdjuntosCategoria categoria, SeccionDetalleItemsAdjuntosCategorias sdiac) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.adjuntos_categoria "
                    + " ( cod_categoria, nombre, descripcion, meses_vigencia )"
                    + " VALUES ('" + categoria.getCodCategoria() + "', '" + categoria.getNombre() + "', '" + categoria.getDescripcion() + "', "
                    + " '" + categoria.getMesesVigencia() + "') "                    
                    + " ON CONFLICT (cod_categoria) DO UPDATE "
                    + " SET nombre=EXCLUDED.nombre, descripcion=EXCLUDED.descripcion, meses_vigencia=EXCLUDED.meses_vigencia "                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.seccion_detalle_items_adjuntos_categoria "
                    + " ( cod_ciclo, cod_seccion, cod_detalle, cod_item, cod_categoria )"
                    + " VALUES ('" + sdiac.getCodCiclo() + "', '" + sdiac.getCodSeccion() + "', '" + sdiac.getCodDetalle() + "', "
                    + " '" + sdiac.getCodItem() + "', '"+categoria.getCodCategoria()+"') ")                    
                    ;
            
            consulta.actualizar(sql);
        } finally {
            if(consulta!=null){
                consulta.desconectar();
            }
                
           
        }
    }
    
    public void eliminarCategoria(Integer codCategoria) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM gestor.seccion_detalle_items_adjuntos_categoria"
                    + " WHERE cod_categoria='"+codCategoria+"'"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM gestor.adjuntos_categoria"
                    + " WHERE cod_categoria='"+codCategoria+"'"
            );
            
            consulta.actualizar(sql);
        } finally {
            if(consulta!=null){
                consulta.desconectar();
            }
                
           
        }
    }
    
    public void eliminarTipo(AdjuntosCategoriaTipo tipo) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM gestor.adjuntos_categoria_tipo"
                    + " WHERE cod_categoria='"+tipo.getAdjuntosCategoriaTipoPK().getCodCategoria()+"' AND cod_categoria_tipo='"+tipo.getAdjuntosCategoriaTipoPK().getCodCategoriaTipo()+"'"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }       
       
    }
    
    public void insertarTipo(AdjuntosCategoriaTipo tipo) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO gestor.adjuntos_categoria_tipo "
                    + " ( cod_categoria, cod_categoria_tipo, nombre, descripcion )"
                    + " VALUES ('" + tipo.getAdjuntosCategoriaTipoPK().getCodCategoria() + "', '" + tipo.getAdjuntosCategoriaTipoPK().getCodCategoriaTipo() + "', '"+tipo.getNombre()+"', '" + tipo.getDescripcion() + "') "                    
                    + " ON CONFLICT (cod_categoria, cod_categoria_tipo) DO UPDATE "
                    + " SET nombre=EXCLUDED.nombre, descripcion=EXCLUDED.descripcion"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
        
        
    }

}

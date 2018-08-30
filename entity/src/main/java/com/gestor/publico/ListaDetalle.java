/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "lista_detalle")
@NamedQueries({
    @NamedQuery(name = "ListaDetalle.findAll", query = "SELECT l FROM ListaDetalle l")})
public class ListaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListaDetallePK listaDetallePK;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @Lob
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "cod_lista", referencedColumnName = "cod_lista", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lista lista;

    public ListaDetalle() {
    }

    public ListaDetalle(ListaDetallePK listaDetallePK) {
        this.listaDetallePK = listaDetallePK;
    }

    public ListaDetalle(ListaDetallePK listaDetallePK, String nombre, Boolean activo, String valor) {
        this.listaDetallePK = listaDetallePK;
        this.nombre = nombre;
        this.activo = activo;
        this.valor = valor;
    }

    public ListaDetalle(int codLista, int codDetalle) {
        this.listaDetallePK = new ListaDetallePK(codLista, codDetalle);
    }

    public ListaDetallePK getListaDetallePK() {
        return listaDetallePK;
    }

    public void setListaDetallePK(ListaDetallePK listaDetallePK) {
        this.listaDetallePK = listaDetallePK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listaDetallePK != null ? listaDetallePK.hashCode() : 0);
        return hash;
    }

    public String getPropiedad(String p) {
        JsonObject parametrosTimeOutJson = (JsonObject) new JsonParser().parse(this.valor);
        return parametrosTimeOutJson.get(p).getAsString();
    }
    
    public JsonArray getPropiedadArray(String p) {
        JsonObject parametrosTimeOutJson = (JsonObject) new JsonParser().parse(this.valor);
        return parametrosTimeOutJson.get(p).getAsJsonArray();
    }
    
    public Integer getPropiedadInt(String p) {
        JsonObject parametrosTimeOutJson = (JsonObject) new JsonParser().parse(this.valor);
        return parametrosTimeOutJson.get(p).getAsInt();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaDetalle)) {
            return false;
        }
        ListaDetalle other = (ListaDetalle) object;
        if ((this.listaDetallePK == null && other.listaDetallePK != null) || (this.listaDetallePK != null && !this.listaDetallePK.equals(other.listaDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.ListaDetalle[ listaDetallePK=" + listaDetallePK + " ]";
    }

}

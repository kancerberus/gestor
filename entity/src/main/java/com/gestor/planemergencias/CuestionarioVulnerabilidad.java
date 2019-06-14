/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author franj
 */
@Entity
@Table(name = "cuestionario_vulnerabilidad")
@NamedQueries({
    @NamedQuery(name = "cuestionario_vulnerabilidad.findAll", query = "SELECT cv FROM cuestionario_vulnerabilidad cv")})
public class CuestionarioVulnerabilidad implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_vulnerabilidad")
    private Integer codVulnerabilidad;
    @Basic(optional = false)
    @Column(name = "cod_vulnerabilidad_tipo")
    private Integer codVulnerabilidadTipo;
    @Basic(optional = false)
    @Column(name = "cod_cuestionario")
    private Integer codCuestionario;    
    @Column(name = "nombre")
    private String nombre;
    
    private Float calif1=0.0f;
    private Float calif2=0.0f;
    private Float calif3=0.0f;
    private Float calif4=0.0f;
    private Float calif5=0.0f;
    private Float calif6=0.0f;
    private Float calif7=0.0f;
    private Float calif8=0.0f;
    private Float calif9=0.0f;
    private Float calif10=0.0f;
    private Float calif11=0.0f;
    private Float calif12=0.0f;
    private Float calif13=0.0f;
    private Float calif14=0.0f;
    private Float calif15=0.0f;
    private Float calif16=0.0f;
    private Float calif17=0.0f;
    
    private Vulnerabilidad vulnerabilidad;
    private RelVulnerabilidadTipo relVulnerabilidadTipo;
    
    

    public CuestionarioVulnerabilidad() {
    }

    public CuestionarioVulnerabilidad(Integer codVulnerabilidad, Integer codVulnerabilidadTipo,  String nombre) {
        this.codVulnerabilidad = codVulnerabilidad;
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;        
        this.nombre = nombre;
    }

    public CuestionarioVulnerabilidad(Integer codVulnerabilidad, Integer codVulnerabilidadTipo, Integer codCuestionario, String nombre) {
        this.codVulnerabilidad = codVulnerabilidad;
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
        this.codCuestionario = codCuestionario;
        this.nombre = nombre;        
    }

    public Float getCalif1() {
        return calif1;
    }

    public void setCalif1(Float calif1) {
        this.calif1 = calif1;
    }

    public Float getCalif2() {
        return calif2;
    }

    public void setCalif2(Float calif2) {
        this.calif2 = calif2;
    }

    public Float getCalif3() {
        return calif3;
    }

    public void setCalif3(Float calif3) {
        this.calif3 = calif3;
    }

    public Float getCalif4() {
        return calif4;
    }

    public void setCalif4(Float calif4) {
        this.calif4 = calif4;
    }

    public Float getCalif5() {
        return calif5;
    }

    public void setCalif5(Float calif5) {
        this.calif5 = calif5;
    }

    public Float getCalif6() {
        return calif6;
    }

    public void setCalif6(Float calif6) {
        this.calif6 = calif6;
    }

    public Float getCalif7() {
        return calif7;
    }

    public void setCalif7(Float calif7) {
        this.calif7 = calif7;
    }

    public Float getCalif8() {
        return calif8;
    }

    public void setCalif8(Float calif8) {
        this.calif8 = calif8;
    }

    public Float getCalif9() {
        return calif9;
    }

    public void setCalif9(Float calif9) {
        this.calif9 = calif9;
    }

    public Float getCalif10() {
        return calif10;
    }

    public void setCalif10(Float calif10) {
        this.calif10 = calif10;
    }

    public Float getCalif11() {
        return calif11;
    }

    public void setCalif11(Float calif11) {
        this.calif11 = calif11;
    }

    public Float getCalif12() {
        return calif12;
    }

    public void setCalif12(Float calif12) {
        this.calif12 = calif12;
    }

    public Float getCalif13() {
        return calif13;
    }

    public void setCalif13(Float calif13) {
        this.calif13 = calif13;
    }

    public Float getCalif14() {
        return calif14;
    }

    public void setCalif14(Float calif14) {
        this.calif14 = calif14;
    }

    public Float getCalif15() {
        return calif15;
    }

    public void setCalif15(Float calif15) {
        this.calif15 = calif15;
    }

    public Float getCalif16() {
        return calif16;
    }

    public void setCalif16(Float calif16) {
        this.calif16 = calif16;
    }

    public Float getCalif17() {
        return calif17;
    }

    public void setCalif17(Float calif17) {
        this.calif17 = calif17;
    }

    public Vulnerabilidad getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(Vulnerabilidad vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    public RelVulnerabilidadTipo getRelVulnerabilidadTipo() {
        return relVulnerabilidadTipo;
    }

    public void setRelVulnerabilidadTipo(RelVulnerabilidadTipo relVulnerabilidadTipo) {
        this.relVulnerabilidadTipo = relVulnerabilidadTipo;
    }

    public Integer getCodVulnerabilidad() {
        return codVulnerabilidad;
    }

    public void setCodVulnerabilidad(Integer codVulnerabilidad) {
        this.codVulnerabilidad = codVulnerabilidad;
    }

    public Integer getCodVulnerabilidadTipo() {
        return codVulnerabilidadTipo;
    }

    public void setCodVulnerabilidadTipo(Integer codVulnerabilidadTipo) {
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
    }

    public Integer getCodCuestionario() {
        return codCuestionario;
    }

    public void setCodCuestionario(Integer codCuestionario) {
        this.codCuestionario = codCuestionario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuestionarioVulnerabilidad)) {
            return false;
        }
        CuestionarioVulnerabilidad other = (CuestionarioVulnerabilidad) object;
        if ((this.codCuestionario == null && other.codCuestionario != null) || (this.codCuestionario != null && !this.codCuestionario.equals(other.codCuestionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.CuestionarioVulnerabilidad[ cod_cuestionario=" + codCuestionario + " ]";
    }
    
}

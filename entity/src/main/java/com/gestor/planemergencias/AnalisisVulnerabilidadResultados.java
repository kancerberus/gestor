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
@Table(name = "analisis_vulnerabilidad_resultados")
@NamedQueries({
    @NamedQuery(name = "analisis_vulnerabilidad_resultados.findAll", query = "SELECT anvulres FROM analisis_vulnerabilidad_resultados anvulres")})
public class AnalisisVulnerabilidadResultados implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)    
    @Column(name = "cod_plan_emergencia")
    private Integer codPlanEmergencia;    
    @Column(name = "cod_vulnerabilida_tipo")
    private Integer codVulnerabilidadTipo;    
    @Column(name = "cod_vulnerabilidad")
    private Integer codVulnerabilidad;    
    @Column(name = "cod_analisis_vul_resultado")
    private Integer codAnalisisVulResultado;         
    @Column(name = "prom1")
    private Float prom1=0.0f;
    @Column(name = "prom2")
    private Float prom2=0.0f;
    @Column(name = "prom3")
    private Float prom3=0.0f;
    @Column(name = "prom4")
    private Float prom4=0.0f;
    @Column(name = "prom5")
    private Float prom5=0.0f;
    @Column(name = "prom6")
    private Float prom6=0.0f;
    @Column(name = "prom7")
    private Float prom7=0.0f;
    @Column(name = "prom8")
    private Float prom8=0.0f;
    @Column(name = "prom9")
    private Float prom9=0.0f;
    @Column(name = "prom10")
    private Float prom10=0.0f;
    @Column(name = "prom11")
    private Float prom11=0.0f;
    @Column(name = "prom12")
    private Float prom12=0.0f;
    @Column(name = "prom13")
    private Float prom13=0.0f;
    @Column(name = "prom14")
    private Float prom14=0.0f;
    @Column(name = "prom15")
    private Float prom15=0.0f;
    @Column(name = "prom16")
    private Float prom16=0.0f;
    @Column(name = "prom17")
    private Float prom17=0.0f;

    private Vulnerabilidad vulnerabilidad;
    private RelVulnerabilidadTipo vulnerabilidadTipo;    
    private RelAnalisisVulnerabilidadCuestionario relCuestionarioVulnerabilidad;       
    
    public AnalisisVulnerabilidadResultados(){
    }

    public AnalisisVulnerabilidadResultados(Integer codPlanEmergencia, Integer codVulnerabilidadTipo, Integer codVulnerabilidad, Integer codAnalisisVulResultado,
        Float prom1, Float prom2, Float prom3, Float prom4,Float prom5,Float prom6, Float prom7, Float prom8, Float prom9, Float prom10, Float prom11, Float prom12, Float prom13,
        Float prom14,Float prom15,Float prom16,Float prom17) {
        this.codPlanEmergencia = codPlanEmergencia;
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
        this.codVulnerabilidad = codVulnerabilidad;
        this.codAnalisisVulResultado = codAnalisisVulResultado;        
        this.prom1= prom1;
        this.prom2= prom2;
        this.prom3= prom3;
        this.prom4= prom4;
        this.prom5= prom5;
        this.prom6= prom6;
        this.prom7= prom7;
        this.prom8= prom8;
        this.prom9= prom9;
        this.prom10= prom10;
        this.prom11= prom11;
        this.prom12= prom12;
        this.prom13= prom13;
        this.prom14= prom14;
        this.prom15= prom15;
        this.prom16= prom16;
        this.prom17= prom17;        
    }    

    public Float getProm1() {
        return prom1;
    }

    public void setProm1(Float prom1) {
        this.prom1 = prom1;
    }

    public Float getProm2() {
        return prom2;
    }

    public void setProm2(Float prom2) {
        this.prom2 = prom2;
    }

    public Float getProm3() {
        return prom3;
    }

    public void setProm3(Float prom3) {
        this.prom3 = prom3;
    }

    public Float getProm4() {
        return prom4;
    }

    public void setProm4(Float prom4) {
        this.prom4 = prom4;
    }

    public Float getProm5() {
        return prom5;
    }

    public void setProm5(Float prom5) {
        this.prom5 = prom5;
    }

    public Float getProm6() {
        return prom6;
    }

    public void setProm6(Float prom6) {
        this.prom6 = prom6;
    }

    public Float getProm7() {
        return prom7;
    }

    public void setProm7(Float prom7) {
        this.prom7 = prom7;
    }

    public Float getProm8() {
        return prom8;
    }

    public void setProm8(Float prom8) {
        this.prom8 = prom8;
    }

    public Float getProm9() {
        return prom9;
    }

    public void setProm9(Float prom9) {
        this.prom9 = prom9;
    }

    public Float getProm10() {
        return prom10;
    }

    public void setProm10(Float prom10) {
        this.prom10 = prom10;
    }

    public Float getProm11() {
        return prom11;
    }

    public void setProm11(Float prom11) {
        this.prom11 = prom11;
    }

    public Float getProm12() {
        return prom12;
    }

    public void setProm12(Float prom12) {
        this.prom12 = prom12;
    }

    public Float getProm13() {
        return prom13;
    }

    public void setProm13(Float prom13) {
        this.prom13 = prom13;
    }

    public Float getProm14() {
        return prom14;
    }

    public void setProm14(Float prom14) {
        this.prom14 = prom14;
    }

    public Float getProm15() {
        return prom15;
    }

    public void setProm15(Float prom15) {
        this.prom15 = prom15;
    }

    public Float getProm16() {
        return prom16;
    }

    public void setProm16(Float prom16) {
        this.prom16 = prom16;
    }

    public Float getProm17() {
        return prom17;
    }

    public void setProm17(Float prom17) {
        this.prom17 = prom17;
    }
   
    

    public Integer getCodPlanEmergencia() {
        return codPlanEmergencia;
    }

    public void setCodPlanEmergencia(Integer codPlanEmergencia) {
        this.codPlanEmergencia = codPlanEmergencia;
    }

    public Integer getCodVulnerabilidadTipo() {
        return codVulnerabilidadTipo;
    }

    public void setCodVulnerabilidadTipo(Integer codVulnerabilidadTipo) {
        this.codVulnerabilidadTipo = codVulnerabilidadTipo;
    }

    public Integer getCodVulnerabilidad() {
        return codVulnerabilidad;
    }

    public void setCodVulnerabilidad(Integer codVulnerabilidad) {
        this.codVulnerabilidad = codVulnerabilidad;
    }

    public Integer getCodAnalisisVulResultado() {
        return codAnalisisVulResultado;
    }

    public void setCodAnalisisVulResultado(Integer codAnalisisVulResultado) {
        this.codAnalisisVulResultado = codAnalisisVulResultado;
    }

    public Vulnerabilidad getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(Vulnerabilidad vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    public RelVulnerabilidadTipo getVulnerabilidadTipo() {
        return vulnerabilidadTipo;
    }

    public void setVulnerabilidadTipo(RelVulnerabilidadTipo vulnerabilidadTipo) {
        this.vulnerabilidadTipo = vulnerabilidadTipo;
    }

    public RelAnalisisVulnerabilidadCuestionario getRelCuestionarioVulnerabilidad() {
        return relCuestionarioVulnerabilidad;
    }

    public void setRelCuestionarioVulnerabilidad(RelAnalisisVulnerabilidadCuestionario relCuestionarioVulnerabilidad) {
        this.relCuestionarioVulnerabilidad = relCuestionarioVulnerabilidad;
    }    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalisisVulnerabilidadResultados)) {
            return false;
        }
        AnalisisVulnerabilidadResultados other = (AnalisisVulnerabilidadResultados) object;
        if ((this.codAnalisisVulResultado == null && other.codAnalisisVulResultado != null) || (this.codAnalisisVulResultado != null && !this.codAnalisisVulResultado.equals(other.codAnalisisVulResultado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.planemergencias.AnalisisVulnerabilidadResultados[ cod_analisis_vul_resultado=" + codAnalisisVulResultado + " ]";
    }
}
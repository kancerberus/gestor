/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.matriz;

import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.publico.Cargos;
import com.gestor.publico.Funciones;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "matriz_riesgos")
@NamedQueries({
    @NamedQuery(name = "MatrizRiesgos.findAll", query = "SELECT mr FROM MatrizRiesgos mr")
})
@ManagedBean
@SessionScoped
public class MatrizRiesgos implements Serializable {

    private static final long serialVersionUID = 1L; 
    
    @EmbeddedId    

    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    @Column(name = "cod_cargo")
    private Integer codCargo;
    @Column(name = "cod_riesgo_matriz")
    private Integer codRiesgoMatriz;
    @Column(name = "cod_funcion")
    private Integer codFuncion;    
    @Column(name = "rutinaria")
    private boolean rutinaria;
    @Column(name = "cod_riesgo")
    private Integer codRiesgo;
    @Column(name = "cod_riesgo_posible")
    private Integer codRiesgoPosible;
    @Column(name = "cod_exposicion")
    private Integer codExposicion;
    @Column(name = "cod_categoria_riesgo")
    private Integer codCategoriaRiesgo;
    @Column(name = "fuente")
    private String fuente;
    @Column(name = "medio")
    private String medio;
    @Column(name = "individuo")
    private String individuo;
    @Column(name = "cod_nivel_def")
    private Integer codNivelDef;
    @Column(name = "cod_nivel_exp")
    private Integer codNivelExp;
    @Column(name = "cod_nivel_cons")
    private Integer codNivelCons;    
    @Column(name = "nivel_riesgo")
    private Integer nivelRiesgo;
    @Column(name = "interpretacion_nr")
    private String interpretacionNr;
    @Column(name = "aceptabilidad_riesgo")
    private String aceptabilidadRiesgo;
    @Column(name = "nivel_probabilidad")
    private Integer nivelProbabilidad;
    @Column(name = "interpretacion_prob")
    private String interpretacionProb;
    @Column(name = "num_expuestos")
    private Integer numExpuestos;
    @Column(name = "peor_consecuencia")
    private String peorConsecuencia;
    @Column(name = "requisito_legal")    
    private boolean reqLegal;
    @Column(name = "cod_medida")
    private Integer codMedida;
    @Column(name = "cod_elemento")
    private Integer codElemento;
    @Column(name = "observaciones")
    private String observaciones;        
    
    private NivelDeficiencia nivelDeficiencia=new NivelDeficiencia();
    private Cargos cargos= new Cargos();
    private Riesgo riesgo= new Riesgo();
    private Exposicion exposicion= new Exposicion();
    private CategoriaRiesgo categoriaRiesgo= new CategoriaRiesgo();
    private RiesgoPosible riesgoPosible= new RiesgoPosible();
    private Funciones funciones= new Funciones();    
    private NivelExposicion nivelExposcion= new NivelExposicion();
    private NivelConsecuencia nivelConsecuencia= new NivelConsecuencia();
    private MedidasIntervencion medidasIntervencion= new MedidasIntervencion();
    private ElementosProteccion elementoProteccion= new ElementosProteccion();    
    
    public MatrizRiesgos() {
    }

    public Integer getCodNivelCons() {
        return codNivelCons;
    }

    public MatrizRiesgos(Integer codigoEstablecimiento, Integer codCargo, Integer codRiesgoMatriz, Integer codFuncion, boolean rutinaria, Integer codRiesgo, Integer codRiesgoPosible, Integer codExposicion, Integer codCategoriaRiesgo, String fuente, String medio, String individuo, Integer codNivelDef, Integer codNivelExp, Integer codNivelCons, Integer nivelRiesgo, String interpretacionNr, String aceptabilidadRiesgo, Integer nivelProbabilidad, String interpretacionProb, Integer numExpuestos, String peorConsecuencia, boolean reqLegal, Integer codMedida, Integer codElemento, String observaciones) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCargo = codCargo;
        this.codRiesgoMatriz = codRiesgoMatriz;
        this.codFuncion = codFuncion;
        this.rutinaria = rutinaria;
        this.codRiesgo = codRiesgo;
        this.codRiesgoPosible = codRiesgoPosible;
        this.codExposicion = codExposicion;
        this.codCategoriaRiesgo = codCategoriaRiesgo;
        this.fuente = fuente;
        this.medio = medio;
        this.individuo = individuo;
        this.codNivelDef = codNivelDef;
        this.codNivelExp = codNivelExp;
        this.codNivelCons = codNivelCons;
        this.nivelRiesgo = nivelRiesgo;
        this.interpretacionNr = interpretacionNr;
        this.aceptabilidadRiesgo = aceptabilidadRiesgo;
        this.nivelProbabilidad = nivelProbabilidad;
        this.interpretacionProb = interpretacionProb;
        this.numExpuestos = numExpuestos;
        this.peorConsecuencia = peorConsecuencia;
        this.reqLegal = reqLegal;
        this.codMedida = codMedida;        
        this.codElemento = codElemento;        
        this.observaciones = observaciones;
    }


    public String getStyleAceptabilidadfila() {
        String style = "padding: 8px;width: 95%;"
                + "opacity: 0.83;background-color: #539aa0;"
                + "transition: opacity 0.6s; font-weight: bold;";          
        if( aceptabilidadRiesgo == null){
            return style="";
        }
        if(aceptabilidadRiesgo.equals("ACEPTABLE") ){
            style += "color: #33ff33;";
        }if(aceptabilidadRiesgo.equals("ACEPTABLE CON CONTROL")){
                style += "color: #ffff66;";
        }if(aceptabilidadRiesgo.equals("NO ACEPTABLE")){
            style += "color: #ff3333;";
        }
        return style;
    }

    public Integer getCodRiesgoMatriz() {
        return codRiesgoMatriz;
    }

    public void setCodRiesgoMatriz(Integer codRiesgoMatriz) {
        this.codRiesgoMatriz = codRiesgoMatriz;
    }

    public Integer getCodRiesgoPosible() {
        return codRiesgoPosible;
    }

    public void setCodRiesgoPosible(Integer codRiesgoPosible) {
        this.codRiesgoPosible = codRiesgoPosible;
    }

    public String getAceptabilidadRiesgo() {
        return aceptabilidadRiesgo;
    }

    public void setAceptabilidadRiesgo(String aceptabilidadRiesgo) {
        this.aceptabilidadRiesgo = aceptabilidadRiesgo;
    }

    public String getInterpretacionNr() {
        return interpretacionNr;
    }

    public void setInterpretacionNr(String interpretacionNr) {
        this.interpretacionNr = interpretacionNr;
    }

    public Integer getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(Integer nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getInterpretacionProb() {
        return interpretacionProb;
    }

    public void setInterpretacionProb(String interpretacionProb) {
        this.interpretacionProb = interpretacionProb;
    }

    public Integer getCodFuncion() {
        return codFuncion;
    }

    public void setCodFuncion(Integer codFuncion) {
        this.codFuncion = codFuncion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getCodElemento() {
        return codElemento;
    }

    public void setCodElemento(Integer codElemento) {
        this.codElemento = codElemento;
    }

    public ElementosProteccion getElementoProteccion() {
        return elementoProteccion;
    }

    public void setElementoProteccion(ElementosProteccion elementoProteccion) {
        this.elementoProteccion = elementoProteccion;
    }

    public MedidasIntervencion getMedidasIntervencion() {
        return medidasIntervencion;
    }

    public void setMedidasIntervencion(MedidasIntervencion medidasIntervencion) {
        this.medidasIntervencion = medidasIntervencion;
    }

    public Integer getCodMedida() {
        return codMedida;
    }

    public void setCodMedida(Integer codMedida) {
        this.codMedida = codMedida;
    }


    public boolean isReqLegal() {
        return reqLegal;
    }

    public void setReqLegal(boolean reqLegal) {
        this.reqLegal = reqLegal;
    }

    public String getPeorConsecuencia() {
        return peorConsecuencia;
    }

    public void setPeorConsecuencia(String peorConsecuencia) {
        this.peorConsecuencia = peorConsecuencia;
    }

    public Integer getNumExpuestos() {
        return numExpuestos;
    }

    public void setNumExpuestos(Integer numExpuestos) {
        this.numExpuestos = numExpuestos;
    }
    
    public void setCodNivelCons(Integer codNivelCons) {
        this.codNivelCons = codNivelCons;
    }

    public NivelConsecuencia getNivelConsecuencia() {
        return nivelConsecuencia;
    }

    public void setNivelConsecuencia(NivelConsecuencia nivelConsecuencia) {
        this.nivelConsecuencia = nivelConsecuencia;
    }

    public Cargos getCargos() {
        return cargos;
    }

    public void setCargos(Cargos cargos) {
        this.cargos = cargos;
    }

    public Riesgo getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(Riesgo riesgo) {
        this.riesgo = riesgo;
    }

    public Exposicion getExposicion() {
        return exposicion;
    }

    public void setExposicion(Exposicion exposicion) {
        this.exposicion = exposicion;
    }

    public CategoriaRiesgo getCategoriaRiesgo() {
        return categoriaRiesgo;
    }

    public void setCategoriaRiesgo(CategoriaRiesgo categoriaRiesgo) {
        this.categoriaRiesgo = categoriaRiesgo;
    }

    public RiesgoPosible getRiesgoPosible() {
        return riesgoPosible;
    }

    public void setRiesgoPosible(RiesgoPosible riesgoPosible) {
        this.riesgoPosible = riesgoPosible;
    }

    public Funciones getFunciones() {
        return funciones;
    }

    public void setFunciones(Funciones funciones) {
        this.funciones = funciones;
    }

    public NivelExposicion getNivelExposcion() {
        return nivelExposcion;
    }

    public void setNivelExposcion(NivelExposicion nivelExposcion) {
        this.nivelExposcion = nivelExposcion;
    }

    public Integer getCodNivelExp() {
        return codNivelExp;
    }

    public void setCodNivelExp(Integer codNivelExp) {
        this.codNivelExp = codNivelExp;
    }

    public Integer getNivelProbabilidad() {         
        return nivelProbabilidad;
    }

    public void setNivelProbabilidad(Integer nivelProbabilidad) {
        this.nivelProbabilidad = nivelProbabilidad;
    }

    public Integer getCodNivelDef() {
        return codNivelDef;
    }

    public void setCodNivelDef(Integer codNivelDef) {
        this.codNivelDef = codNivelDef;
    }

    public NivelDeficiencia getNivelDeficiencia() {
        return nivelDeficiencia;
    }

    public void setNivelDeficiencia(NivelDeficiencia nivelDeficiencia) {
        this.nivelDeficiencia = nivelDeficiencia;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getIndividuo() {
        return individuo;
    }

    public void setIndividuo(String individuo) {
        this.individuo = individuo;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Integer getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(Integer codCargo) {
        this.codCargo = codCargo;
    }

    public boolean isRutinaria() {
        return rutinaria;
    }

    public void setRutinaria(boolean rutinaria) {
            this.rutinaria = rutinaria;
    }

    public Integer getCodRiesgo() {
        return codRiesgo;
    }

    public void setCodRiesgo(Integer codRiesgo) {
        this.codRiesgo = codRiesgo;
    }

    public Integer getCodExposicion() {
        return codExposicion;
    }

    public void setCodExposicion(Integer codExposicion) {
        this.codExposicion = codExposicion;
    }

    public Integer getCodCategoriaRiesgo() {
        return codCategoriaRiesgo;
    }

    public void setCodCategoriaRiesgo(Integer codCategoriaRiesgo) {
        this.codCategoriaRiesgo = codCategoriaRiesgo;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codRiesgoMatriz != null ? codRiesgoMatriz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatrizRiesgos)) {
            return false;
        }
        MatrizRiesgos other = (MatrizRiesgos) object;
        if ((this.codRiesgoMatriz == null && other.codRiesgoMatriz != null) || (this.codRiesgoMatriz != null && !this.codRiesgoMatriz.equals(other.codRiesgoMatriz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz[ cod_riesgo_matriz=" + codRiesgoMatriz + " ]";
    }

    void getAdjuntosCategoria2(AdjuntosCategoria adjuntosCategoria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

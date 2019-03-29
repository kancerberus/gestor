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
    @Column(name = "cod_matriz")
    private Integer codMatriz;
    @Column(name = "cod_funcion")
    private Integer codFuncion;    
    @Column(name = "tarea")
    private String tarea;
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
    private boolean fuente;
    @Column(name = "medio")
    private boolean medio;
    @Column(name = "individuo")
    private boolean individuo;
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
    @Column(name = "descripcion_medida")
    private String descripcionMedida;
    @Column(name = "descripcion_medida2")
    private String descripcionMedida2;
    @Column(name = "cod_elemento")
    private Integer codElemento;
    @Column(name = "cod_categoria")
    private Integer codCategoria;
    @Column(name = "cod_categoria2")
    private Integer codCategoria2;
    @Column(name = "cod_categoria_tipo")
    private Integer codCategoriaTipo;
    @Column(name = "cod_categoria_tipo2")
    private Integer codCategoriaTipo2;
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
    private AdjuntosCategoria adjuntosCategoria = new AdjuntosCategoria();
    private AdjuntosCategoria adjuntosCategoria2 = new AdjuntosCategoria();
    
    public MatrizRiesgos() {
    }

    public Integer getCodNivelCons() {
        return codNivelCons;
    }

    public MatrizRiesgos(Integer codigoEstablecimiento, Integer codCargo, Integer codMatriz, Integer codFuncion, String tarea, boolean rutinaria, Integer codRiesgo, Integer codRiesgoPosible, Integer codExposicion, Integer codCategoriaRiesgo, boolean fuente, boolean medio, boolean individuo, Integer codNivelDef, Integer codNivelExp, Integer codNivelCons, Integer nivelRiesgo, String interpretacionNr, String aceptabilidadRiesgo, Integer nivelProbabilidad, String interpretacionProb, Integer numExpuestos, String peorConsecuencia, boolean reqLegal, Integer codMedida, String descripcionMedida, String descripcionMedida2, Integer codElemento, Integer codCategoria, Integer codCategoria2, Integer codCategoriaTipo, Integer codCategoriaTipo2, String observaciones) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCargo = codCargo;
        this.codMatriz = codMatriz;
        this.codFuncion = codFuncion;
        this.tarea = tarea;
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
        this.descripcionMedida = descripcionMedida;
        this.descripcionMedida2 = descripcionMedida2;
        this.codElemento = codElemento;
        this.codCategoria = codCategoria;
        this.codCategoria2 = codCategoria2;
        this.codCategoriaTipo = codCategoriaTipo;
        this.codCategoriaTipo2 = codCategoriaTipo2;
        this.observaciones = observaciones;
    }
    
    

    public Integer getCodRiesgoPosible() {
        return codRiesgoPosible;
    }

    public void setCodRiesgoPosible(Integer codRiesgoPosible) {
        this.codRiesgoPosible = codRiesgoPosible;
    }

    public Integer getCodCategoria2() {
        return codCategoria2;
    }

    public void setCodCategoria2(Integer codCategoria2) {
        this.codCategoria2 = codCategoria2;
    }

    public Integer getCodCategoriaTipo2() {
        return codCategoriaTipo2;
    }

    public void setCodCategoriaTipo2(Integer codCategoriaTipo2) {
        this.codCategoriaTipo2 = codCategoriaTipo2;
    }

    public AdjuntosCategoria getAdjuntosCategoria2() {
        return adjuntosCategoria2;
    }

    public void setAdjuntosCategoria2(AdjuntosCategoria adjuntosCategoria2) {
        this.adjuntosCategoria2 = adjuntosCategoria2;
    }

    public String getDescripcionMedida2() {
        return descripcionMedida2;
    }

    public void setDescripcionMedida2(String descripcionMedida2) {
        this.descripcionMedida2 = descripcionMedida2;
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


    public String getDescripcionMedida() {
        return descripcionMedida;
    }

    public void setDescripcionMedida(String descripcionMedida) {
        this.descripcionMedida = descripcionMedida;
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

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Integer getCodCategoriaTipo() {
        return codCategoriaTipo;
    }

    public void setCodCategoriaTipo(Integer codCategoriaTipo) {
        this.codCategoriaTipo = codCategoriaTipo;
    }

    public AdjuntosCategoria getAdjuntosCategoria() {
        return adjuntosCategoria;
    }

    public void setAdjuntosCategoria(AdjuntosCategoria adjuntosCategoria) {
        this.adjuntosCategoria = adjuntosCategoria;
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

    public boolean isFuente() {
        return fuente;
    }

    public void setFuente(boolean fuente) {
        this.fuente = fuente;
    }

    public boolean isMedio() {
        return medio;
    }

    public void setMedio(boolean medio) {
        this.medio = medio;
    }

    public boolean isIndividuo() {
        return individuo;
    }

    public void setIndividuo(boolean individuo) {
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

    public Integer getCodMatriz() {
        return codMatriz;
    }

    public void setCodMatriz(Integer codMatriz) {
        this.codMatriz = codMatriz;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
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
        hash += (codMatriz != null ? codMatriz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatrizRiesgos)) {
            return false;
        }
        MatrizRiesgos other = (MatrizRiesgos) object;
        if ((this.codMatriz == null && other.codMatriz != null) || (this.codMatriz != null && !this.codMatriz.equals(other.codMatriz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz[ cod_matriz=" + codMatriz + " ]";
    }

    void getAdjuntosCategoria2(AdjuntosCategoria adjuntosCategoria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

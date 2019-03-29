/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.matriz.dao;

import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.gestor.AdjuntosCategoriaTipo;
import com.gestor.gestor.AdjuntosCategoriaTipoPK;
import com.gestor.matriz.CategoriaRiesgo;
import com.gestor.matriz.ElementosProteccion;
import com.gestor.matriz.Exposicion;
import com.gestor.matriz.MatrizRiesgos;
import com.gestor.matriz.MedidasIntervencion;
import com.gestor.matriz.NivelConsecuencia;
import com.gestor.matriz.NivelDeficiencia;
import com.gestor.matriz.NivelExposicion;
import com.gestor.matriz.Riesgo;
import com.gestor.matriz.RiesgoPosible;
import com.gestor.publico.Cargos;
import com.gestor.publico.Funciones;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.Consulta;

/**
 *
 * @author Julian
 */
public class MatrizRiesgosDAO {

    private Connection conexion;

    public MatrizRiesgosDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public List<?> cargarListaFunciones(int codCargo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Funciones> listaFunciones = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_cargo, cod_funcion, nombre, diligenciado "                    
                    + " FROM funciones "             
                    + " WHERE cod_cargo='"+codCargo+"' "
                    + " ORDER BY cod_funcion"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Funciones fun = new Funciones(rs.getInt("cod_cargo"), rs.getInt("cod_funcion"), rs.getString("nombre"), rs.getBoolean("diligenciado"));
                fun.setCodCargo(rs.getInt("cod_cargo"));
                fun.setCodFuncion(rs.getInt("cod_funcion"));
                fun.setNombre(rs.getString("nombre"));
                fun.setDiligenciado(rs.getBoolean("diligenciado"));
                listaFunciones.add(fun);                               
            }
            return listaFunciones;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaExposiciones(int codRiesgo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Exposicion> listaExposiciones = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_riesgo, cod_exposicion, nombre "                    
                    + " FROM matriz.exposicion "             
                    + " WHERE cod_riesgo='"+codRiesgo+"' "
                    + " ORDER BY cod_exposicion"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Exposicion ex= new Exposicion(rs.getInt("cod_exposicion"), rs.getString("nombre"), rs.getInt("cod_riesgo"));
                ex.setCodRiesgo(rs.getInt("cod_riesgo"));
                ex.setCodExposicion(rs.getInt("cod_exposicion"));
                ex.setNombre(rs.getString("nombre"));
                listaExposiciones.add(ex);
                                             
            }
            return listaExposiciones;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaRiesgoPosibles(int codCategoriaRiesgo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<RiesgoPosible> listaRiesgoPosibles = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_categoria_riesgo, cod_riesgo_posible, nombre, descripcion "                    
                    + " FROM matriz.riesgo_posible "             
                    + " WHERE cod_categoria_riesgo='"+codCategoriaRiesgo+"' "
                    + " ORDER BY cod_riesgo_posible"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                RiesgoPosible rpo= new RiesgoPosible(rs.getInt("cod_categoria_riesgo"), rs.getInt("cod_riesgo_posible"), rs.getString("nombre"), rs.getString("descripcion"));
                rpo.setCodCategoriaRiesgo(rs.getInt("cod_categoria_riesgo"));
                rpo.setCodRiesgoPosible(rs.getInt("cod_riesgo_posible"));
                rpo.setNombre(rs.getString("nombre"));
                rpo.setDescripcion(rs.getString("descripcion"));                                
                listaRiesgoPosibles.add(rpo);
            }
            return listaRiesgoPosibles;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public String cargarNombreAdjunto(int codigo_establecimiento, long codEvaluacion, int codCategoria, int codCategoriaTipo) throws SQLException{
        ResultSet rs = null;
        Consulta consulta = null;
        String nombre="Sin Evidencia";        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT archivo "                    
                    + " FROM gestor.evaluacion_adjuntos"
                    + " WHERE cod_evaluacion='"+codEvaluacion+"' and codigo_establecimiento='"+codigo_establecimiento+"' and cod_categoria='"+codCategoria+"' and cod_categoria_tipo='"+codCategoriaTipo+"'"                                                     
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                nombre=rs.getString("archivo");
            }
            return nombre;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public ArrayList<Riesgo> cargarListaRiesgos() throws Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<Riesgo> listaRiesgos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_riesgo, nombre "                    
                    + " FROM matriz.riesgo "                                                     
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Riesgo ri= new Riesgo(rs.getInt("cod_riesgo"), rs.getString("nombre"));
                ri.setCodRiesgo(rs.getInt("cod_riesgo"));
                ri.setNombre(rs.getString("nombre"));
                listaRiesgos.add(ri);                
            }
            return listaRiesgos;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarMatrizRiesgos(MatrizRiesgos mr) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO matriz.matriz_riesgos "
                    + " ( codigo_establecimiento, cod_cargo, cod_matriz, tarea, rutinaria, cod_funcion, cod_riesgo, cod_riesgo_posible, cod_exposicion, cod_categoria_riesgo, "
                    + "fuente, medio, individuo, cod_nivel_def, cod_nivel_exp, nivel_probabilidad, interpretacion_prob, cod_nivel_consec, nivel_riesgo, "
                    + "interpretacion_nr, aceptabilidad_riesgo, nro_expuestos, peor_consecuencia, requisito_legal, observaciones, cod_medida,"
                    + " descripcion_medida, cod_elemento, cod_categoria, cod_categoria_tipo, descripcion_medida2, cod_categoria2, cod_categoria_tipo2)"
                    + " VALUES ('" + mr.getCodigoEstablecimiento() + "', '" + mr.getCodCargo() + "', '" + mr.getCodMatriz() + "','" + mr.getTarea() + "', " 
                    + " "+mr.isRutinaria()+", '"+mr.getCodFuncion()+"', '"+mr.getCodRiesgo()+"', '"+mr.getCodRiesgoPosible()+"', '"+mr.getCodExposicion()+"', '"+mr.getCodCategoriaRiesgo()+"', "
                    + " "+mr.isFuente()+","+mr.isMedio()+","+mr.isIndividuo()+",'"+mr.getCodNivelDef()+"', '"+mr.getCodNivelExp()+"', '"+mr.getNivelProbabilidad()+"', "
                    + " '"+mr.getInterpretacionProb()+"', '"+mr.getCodNivelCons()+"', '"+mr.getNivelRiesgo()+"','"+mr.getInterpretacionNr()+"','"+mr.getAceptabilidadRiesgo()+"', "
                    + "'"+mr.getNumExpuestos()+"','"+mr.getPeorConsecuencia()+"','"+mr.isReqLegal()+"', '"+mr.getObservaciones()+"','"+mr.getCodMedida()+"', "
                    + "'"+mr.getDescripcionMedida()+"', '"+mr.getCodElemento()+"', '"+mr.getCodCategoria()+"', '"+mr.getCodCategoriaTipo()+"', '"+mr.getDescripcionMedida2()+"', "
                    + "'"+mr.getCodCategoria2()+"', '"+mr.getCodCategoriaTipo2()+"') "
                    + " ON CONFLICT (codigo_establecimiento, cod_cargo , cod_matriz) DO UPDATE"
                    + " SET tarea=EXCLUDED.tarea , rutinaria=EXCLUDED.rutinaria, cod_funcion=EXCLUDED.cod_funcion, cod_riesgo=EXCLUDED.cod_riesgo, cod_riesgo_posible=EXCLUDED.cod_riesgo_posible, cod_exposicion=EXCLUDED.cod_exposicion, cod_categoria_riesgo=EXCLUDED.cod_categoria_riesgo,"
                    + " fuente=EXCLUDED.fuente, medio=EXCLUDED.medio, individuo=EXCLUDED.individuo, cod_nivel_def=EXCLUDED.cod_nivel_def, cod_nivel_exp=EXCLUDED.cod_nivel_exp, nivel_probabilidad=EXCLUDED.nivel_probabilidad, interpretacion_prob=EXCLUDED.interpretacion_prob, cod_nivel_consec=EXCLUDED.cod_nivel_consec, "
                    + "nivel_riesgo=EXCLUDED.nivel_riesgo, interpretacion_nr=EXCLUDED.interpretacion_nr, aceptabilidad_riesgo=EXCLUDED.aceptabilidad_riesgo, nro_expuestos=EXCLUDED.nro_expuestos, peor_consecuencia=EXCLUDED.peor_consecuencia, requisito_legal=EXCLUDED.requisito_legal, observaciones=EXCLUDED.observaciones,"
                    + "cod_medida=EXCLUDED.cod_medida,descripcion_medida=EXCLUDED.descripcion_medida, cod_elemento=EXCLUDED.cod_elemento, cod_categoria=EXCLUDED.cod_categoria, cod_categoria_tipo=EXCLUDED.cod_categoria_tipo, descripcion_medida2=EXCLUDED.descripcion_medida2, cod_categoria2=EXCLUDED.cod_categoria2, cod_categoria_tipo2=EXCLUDED.cod_categoria_tipo2"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void updiligenciado(MatrizRiesgos matrizRiesgos) throws SQLException{        
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "UPDATE public.funciones f "
                        + " SET diligenciado='"+true+"'"
                        + " FROM public.cargos car"
                        + " INNER JOIN public.rel_cargos_establecimiento rel on (rel.cod_cargo=car.cod_cargo)"
                        + " WHERE rel.codigo_establecimiento='"+matrizRiesgos.getCodigoEstablecimiento()+"' AND f.cod_cargo='"+matrizRiesgos.getCodCargo()+"' AND cod_funcion='"+matrizRiesgos.getCodFuncion()+"'"                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
                
    }
    
    public MatrizRiesgos cargarMatrizRiesgosCargoActividad(int codCargo, int codFuncion, int codigoEstablecimiento) throws SQLException{
                ResultSet rs = null;
        Consulta consulta = null;        
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_matriz, tarea, rutinaria, fuente, medio, individuo, nivel_probabilidad, interpretacion_prob, "
                    + "nivel_riesgo, interpretacion_nr, aceptabilidad_riesgo, nro_expuestos, peor_consecuencia, requisito_legal,"
                    + "observaciones, descripcion_medida, descripcion_medida2, "
                    + " C.cod_cargo codc, C.nombre nomc, "
                    + " F.cod_funcion codf, F.nombre nomf, F.diligenciado diligenciado, "
                    + " R.cod_riesgo codr, R.nombre nomr, "
                    + " EX.cod_exposicion codex, EX.nombre nomex,"
                    + " CR.cod_categoria_riesgo codcr, CR.nombre nomcr,"
                    + " RP.cod_riesgo_posible codrp, RP.nombre nomrp,"
                    + " ND.cod_nivel_def codnd, ND.nombre nomdf,"
                    + " NE.cod_nivel_exp codnexp, NE.nombre nomexp,"
                    + " NC.cod_nivel_consec codnnc, NC.nombre nomnc,"
                    + " MI.cod_medida codmi, MI.nombre nommi,"
                    + " E.cod_elemento code, E.nombre nome,"
                    + " AC1.cod_categoria codcat, AC1.nombre nomct, ACT1.cod_categoria_tipo codct, ACT1.nombre nomcat,"
                    + " AC2.cod_categoria codcat2, AC2.nombre nomct2, ACT2.cod_categoria_tipo codct2, ACT2.nombre nomcat2"                    
                    + " FROM matriz.matriz_riesgos MR"
                    + " JOIN cargos C USING (cod_cargo)"
                    + " JOIN funciones F using (cod_cargo, cod_funcion)"
                    + " JOIN matriz.riesgo R using (cod_riesgo)"
                    + " JOIN matriz.exposicion EX using (cod_exposicion , cod_riesgo)"
                    + " JOIN matriz.categoria_riesgo CR using (cod_categoria_riesgo)"
                    + " JOIN matriz.riesgo_posible RP using (cod_categoria_riesgo , cod_riesgo_posible)"
                    + " JOIN matriz.nivel_deficiencia ND using (cod_nivel_def)"
                    + " JOIN matriz.nivel_exposicion NE using (cod_nivel_exp)"
                    + " JOIN matriz.nivel_consecuencia NC using (cod_nivel_consec)"
                    + " JOIN matriz.medidas_intervencion MI using (cod_medida)"
                    + " JOIN matriz.elementos_proteccion E using (cod_elemento)"
                    + " INNER JOIN gestor.adjuntos_categoria AC1 on (ac1.cod_categoria=mr.cod_categoria) "
                    + " INNER JOIN gestor.adjuntos_categoria AC2 on (ac2.cod_categoria=mr.cod_categoria2) "
                    + " INNER JOIN gestor.adjuntos_categoria_tipo act1 on (act1.cod_categoria = mr.cod_categoria and act1.cod_categoria_tipo = mr.cod_categoria_tipo)"
                    + " INNER JOIN gestor.adjuntos_categoria_tipo act2 ON (act2.cod_categoria = mr.cod_categoria2 and act2.cod_categoria_tipo = mr.cod_categoria_tipo2)"                            
                    + " WHERE codigo_establecimiento='" + codigoEstablecimiento + "' and mr.cod_cargo='"+codCargo+"' and mr.cod_funcion='"+codFuncion+"'"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                MatrizRiesgos mr= new MatrizRiesgos(rs.getInt("codigo_establecimiento"), rs.getInt("codc"), rs.getInt("cod_matriz"), rs.getInt("codf"), rs.getString("tarea"),
                    rs.getBoolean("rutinaria"), rs.getInt("codr"), rs.getInt("codrp"), rs.getInt("codex"), rs.getInt("codcr"),rs.getBoolean("fuente"), rs.getBoolean("medio"),
                    rs.getBoolean("individuo"), rs.getInt("codnd"), rs.getInt("codnexp"), rs.getInt("codnnc"),rs.getInt("nivel_riesgo"), rs.getString("interpretacion_nr"), 
                    rs.getString("aceptabilidad_riesgo"), rs.getInt("nivel_probabilidad"), rs.getString("interpretacion_prob"),rs.getInt("nro_expuestos"), rs.getString("nro_expuestos"),
                    rs.getBoolean("requisito_legal"), rs.getInt("codmi"), rs.getString("descripcion_medida"), rs.getString("descripcion_medida2"), rs.getInt("code"),
                    rs.getInt("codcat"), rs.getInt("codcat2"), rs.getInt("codct"), rs.getInt("codct2"), rs.getString("observaciones"));                    
                    
                mr.setCargos(new Cargos(rs.getInt("codc"), rs.getString("nomc")));
                mr.setFunciones(new Funciones(rs.getInt("codc"), rs.getInt("codf"), rs.getString("nomf"), rs.getBoolean("diligenciado")));                
                mr.setRiesgo(new Riesgo(rs.getInt("codr"), rs.getString("nomr")));
                mr.setExposicion(new Exposicion(rs.getInt("codex"), rs.getString("nomex"), rs.getInt("codr")));
                mr.setCategoriaRiesgo(new CategoriaRiesgo(rs.getInt("codcr"), rs.getString("nomcr")));
                mr.setRiesgoPosible(new RiesgoPosible(rs.getInt("codcr"), rs.getInt("codrp"), rs.getString("nomrp"), ""));
                mr.setNivelDeficiencia(new NivelDeficiencia(rs.getInt("codnd"), 0, rs.getString("nomdf"),"" ));
                mr.setNivelExposcion(new NivelExposicion(rs.getInt("codnexp"), rs.getString("nomexp"), 0, ""));
                mr.setNivelConsecuencia(new NivelConsecuencia(rs.getInt("codnnc"), rs.getString("nomnc"), 0, ""));
                mr.setMedidasIntervencion(new MedidasIntervencion(rs.getInt("codmi"), rs.getString("nommi")));
                mr.setElementoProteccion(new ElementosProteccion(rs.getInt("code"), rs.getString("nome")));
                mr.setAdjuntosCategoria(new AdjuntosCategoria(rs.getInt("codcat"), rs.getString("nomcat"), 0));
                mr.getAdjuntosCategoria().setAdjuntosCategoriaTipo(new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(rs.getInt("codcat"), rs.getInt("codct")), rs.getString("nomcat")));
                mr.setAdjuntosCategoria2(new AdjuntosCategoria(rs.getInt("codcat2"), rs.getString("nomcat2"), 0));
                mr.getAdjuntosCategoria2().setAdjuntosCategoriaTipo(new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(rs.getInt("codcat2"), rs.getInt("codct2")), rs.getString("nomcat2")));
                return mr;
            }
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
        
    }
            
    
    public List<MatrizRiesgos> cargarListaMatrizCargosEstablecimiento(Integer codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<MatrizRiesgos> listaMatrizCargosEstablecimiento = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_matriz, tarea, rutinaria, fuente, medio, individuo, nivel_probabilidad, interpretacion_prob, "
                    + "nivel_riesgo, interpretacion_nr, aceptabilidad_riesgo, nro_expuestos, peor_consecuencia, requisito_legal,"
                    + "observaciones, descripcion_medida, descripcion_medida2, "
                    + " C.cod_cargo codc, C.nombre nomc, "
                    + " F.cod_funcion codf, F.nombre nomf, F.diligenciado diligenciado, "
                    + " R.cod_riesgo codr, R.nombre nomr, "
                    + " EX.cod_exposicion codex, EX.nombre nomex,"
                    + " CR.cod_categoria_riesgo codcr, CR.nombre nomcr,"
                    + " RP.cod_riesgo_posible codrp, RP.nombre nomrp,"
                    + " ND.cod_nivel_def codnd, ND.nombre nomdf,"
                    + " NE.cod_nivel_exp codnexp, NE.nombre nomexp,"
                    + " NC.cod_nivel_consec codnnc, NC.nombre nomnc,"
                    + " MI.cod_medida codmi, MI.nombre nommi,"
                    + " E.cod_elemento code, E.nombre nome,"
                    + " AC1.cod_categoria codcat, AC1.nombre nomct, ACT1.cod_categoria_tipo codct, ACT1.nombre nomcat,"
                    + " AC2.cod_categoria codcat2, AC2.nombre nomct2, ACT2.cod_categoria_tipo codct2, ACT2.nombre nomcat2"                    
                    + " FROM matriz.matriz_riesgos MR"
                    + " JOIN cargos C USING (cod_cargo)"
                    + " JOIN funciones F using (cod_cargo, cod_funcion)"
                    + " JOIN matriz.riesgo R using (cod_riesgo)"
                    + " JOIN matriz.exposicion EX using (cod_exposicion , cod_riesgo)"
                    + " JOIN matriz.categoria_riesgo CR using (cod_categoria_riesgo)"
                    + " JOIN matriz.riesgo_posible RP using (cod_categoria_riesgo , cod_riesgo_posible)"
                    + " JOIN matriz.nivel_deficiencia ND using (cod_nivel_def)"
                    + " JOIN matriz.nivel_exposicion NE using (cod_nivel_exp)"
                    + " JOIN matriz.nivel_consecuencia NC using (cod_nivel_consec)"
                    + " JOIN matriz.medidas_intervencion MI using (cod_medida)"
                    + " JOIN matriz.elementos_proteccion E using (cod_elemento)"
                    + " INNER JOIN gestor.adjuntos_categoria AC1 on (ac1.cod_categoria=mr.cod_categoria) "
                    + " INNER JOIN gestor.adjuntos_categoria AC2 on (ac2.cod_categoria=mr.cod_categoria2) "
                    + " INNER JOIN gestor.adjuntos_categoria_tipo act1 on (act1.cod_categoria = mr.cod_categoria and act1.cod_categoria_tipo = mr.cod_categoria_tipo)"
                    + " INNER JOIN gestor.adjuntos_categoria_tipo act2 ON (act2.cod_categoria = mr.cod_categoria2 and act2.cod_categoria_tipo = mr.cod_categoria_tipo2)"                            
                    + " WHERE codigo_establecimiento='" + codEstablecimiento + "'"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                MatrizRiesgos mr= new MatrizRiesgos(rs.getInt("codigo_establecimiento"), rs.getInt("codc"), rs.getInt("cod_matriz"), rs.getInt("codf"), rs.getString("tarea"),
                    rs.getBoolean("rutinaria"), rs.getInt("codr"), rs.getInt("codrp"), rs.getInt("codex"), rs.getInt("codcr"),rs.getBoolean("fuente"), rs.getBoolean("medio"),
                    rs.getBoolean("individuo"), rs.getInt("codnd"), rs.getInt("codnexp"), rs.getInt("codnnc"),rs.getInt("nivel_riesgo"), rs.getString("interpretacion_nr"), 
                    rs.getString("aceptabilidad_riesgo"), rs.getInt("nivel_probabilidad"), rs.getString("interpretacion_prob"),rs.getInt("nro_expuestos"), rs.getString("nro_expuestos"),
                    rs.getBoolean("requisito_legal"), rs.getInt("codmi"), rs.getString("descripcion_medida"), rs.getString("descripcion_medida2"), rs.getInt("code"),
                    rs.getInt("codcat"), rs.getInt("codcat2"), rs.getInt("codct"), rs.getInt("codct2"), rs.getString("observaciones"));                    
                    
                mr.setCargos(new Cargos(rs.getInt("codc"), rs.getString("nomc")));
                mr.setFunciones(new Funciones(rs.getInt("codc"), rs.getInt("codf"), rs.getString("nomf"), rs.getBoolean("diligenciado")));                
                mr.setRiesgo(new Riesgo(rs.getInt("codr"), rs.getString("nomr")));
                mr.setExposicion(new Exposicion(rs.getInt("codex"), rs.getString("nomex"), rs.getInt("codr")));
                mr.setCategoriaRiesgo(new CategoriaRiesgo(rs.getInt("codcr"), rs.getString("nomcr")));
                mr.setRiesgoPosible(new RiesgoPosible(rs.getInt("codcr"), rs.getInt("codrp"), rs.getString("nomrp"), ""));
                mr.setNivelDeficiencia(new NivelDeficiencia(rs.getInt("codnd"), 0, rs.getString("nomdf"),"" ));
                mr.setNivelExposcion(new NivelExposicion(rs.getInt("codnexp"), rs.getString("nomexp"), 0, ""));
                mr.setNivelConsecuencia(new NivelConsecuencia(rs.getInt("codnnc"), rs.getString("nomnc"), 0, ""));
                mr.setMedidasIntervencion(new MedidasIntervencion(rs.getInt("codmi"), rs.getString("nommi")));
                mr.setElementoProteccion(new ElementosProteccion(rs.getInt("code"), rs.getString("nome")));
                mr.setAdjuntosCategoria(new AdjuntosCategoria(rs.getInt("codcat"), rs.getString("nomcat"), 0));
                mr.getAdjuntosCategoria().setAdjuntosCategoriaTipo(new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(rs.getInt("codcat"), rs.getInt("codct")), rs.getString("nomcat")));
                mr.setAdjuntosCategoria2(new AdjuntosCategoria(rs.getInt("codcat2"), rs.getString("nomcat2"), 0));
                mr.getAdjuntosCategoria2().setAdjuntosCategoriaTipo(new AdjuntosCategoriaTipo(new AdjuntosCategoriaTipoPK(rs.getInt("codcat2"), rs.getInt("codct2")), rs.getString("nomcat2")));
                listaMatrizCargosEstablecimiento.add(mr);
            }
            return listaMatrizCargosEstablecimiento;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public ArrayList<ElementosProteccion> cargarListaElementosProteccion() throws Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<ElementosProteccion> listaElementosProteccion = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_elemento, nombre "                    
                    + " FROM matriz.elementos_proteccion "                                                     
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                ElementosProteccion ep= new ElementosProteccion(rs.getInt("cod_elemento"), rs.getString("nombre"));
                ep.setCodElemento(rs.getInt("cod_elemento"));
                ep.setNombre(rs.getString("nombre"));
                listaElementosProteccion.add(ep);                    
            }
            return listaElementosProteccion;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public ArrayList<Funciones> cargarListaCargosFuncionesEstablecimiento(Integer codigoEstablecimiento) throws Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<Funciones> listaCargosFuncionesEstablecimiento = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    " SELECT car.cod_cargo codcar, car.nombre nomcar, f.cod_funcion codf, f.nombre nomf ,f.diligenciado diligenciado" +
                        " FROM public.rel_cargos_establecimiento " +
                        " JOIN cargos car using (cod_cargo) " +
                        " JOIN funciones f using (cod_cargo) " +
                        " WHERE codigo_establecimiento= '"+codigoEstablecimiento+"'"                                                     
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Funciones f=new Funciones(rs.getInt("codcar"), rs.getInt("codf"), rs.getString("nomf"), rs.getBoolean("diligenciado"));
                f.setCargos(new Cargos(rs.getInt("codcar"), rs.getString("nomcar")));
                listaCargosFuncionesEstablecimiento.add(f);                          
            }
            return listaCargosFuncionesEstablecimiento;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public ArrayList<MedidasIntervencion> cargarListaMedidasIntervecion() throws Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<MedidasIntervencion> listaMedidasIntervencion = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_medida, nombre "                    
                    + " FROM matriz.medidas_intervencion "                                                     
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                MedidasIntervencion mi= new MedidasIntervencion(rs.getInt("cod_medida"), rs.getString("nombre"));
                mi.setCodMedida(rs.getInt("cod_medida"));
                mi.setNombre(rs.getString("nombre"));
                listaMedidasIntervencion.add(mi);                                    
            }
            return listaMedidasIntervencion;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public ArrayList<NivelDeficiencia> cargarListaNivelDeficiencia() throws Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<NivelDeficiencia> listaNivelDeficiencia = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_nivel_def, nombre, significado, valor "                    
                    + " FROM matriz.nivel_deficiencia "
                    + "ORDER BY cod_nivel_def"                                                     
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                NivelDeficiencia nd= new NivelDeficiencia(rs.getInt("cod_nivel_def"), rs.getInt("valor"), rs.getString("nombre"), rs.getString("significado"));
                nd.setCodNivelDef(rs.getInt("cod_nivel_def"));
                nd.setDescripcion(rs.getString("significado"));
                nd.setNombre(rs.getString("nombre"));
                nd.setValor(rs.getInt("valor"));
                listaNivelDeficiencia.add(nd);                       
            }
            return listaNivelDeficiencia;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
     public ArrayList<NivelConsecuencia> cargarListaNivelConsecuencia() throws Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<NivelConsecuencia> listaNivelConsecuencia = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_nivel_consec, nombre, significado, valor "                    
                    + " FROM matriz.nivel_consecuencia "
                    + "ORDER BY cod_nivel_consec"                                                     
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                NivelConsecuencia ncons= new NivelConsecuencia(rs.getInt("cod_nivel_consec"), rs.getString("nombre"), rs.getInt("valor"), rs.getString("significado"));
                ncons.setCodNivelConsec(rs.getInt("cod_nivel_consec"));
                ncons.setNombre(rs.getString("nombre"));
                ncons.setSignificado(rs.getString("significado"));
                ncons.setValor(rs.getInt("valor"));
                listaNivelConsecuencia.add(ncons);                                   
            }
            return listaNivelConsecuencia;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public ArrayList<NivelExposicion> cargarListaNivelExposicion() throws Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<NivelExposicion> listaNivelExposicion = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_nivel_exp, nombre, significado, valor "                    
                    + " FROM matriz.nivel_exposicion "
                    + "ORDER BY cod_nivel_exp"                                                     
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                NivelExposicion ne= new NivelExposicion(rs.getInt("cod_nivel_exp"), rs.getString("nombre"), rs.getInt("valor"), rs.getString("significado"));
                ne.setCodNivelExp(rs.getInt("cod_nivel_exp"));
                ne.setDescripcion(rs.getString("significado"));
                ne.setNombre(rs.getString("nombre"));
                ne.setValor(rs.getInt("valor"));
                listaNivelExposicion.add(ne);                
            }
            return listaNivelExposicion;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    public ArrayList<CategoriaRiesgo> cargarListaCategoriaRiesgos() throws Exception {
        ResultSet rs = null;
        Consulta consulta = null;
        ArrayList<CategoriaRiesgo> listaCategoriaRiesgos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_categoria_riesgo, nombre "                    
                    + " FROM matriz.categoria_riesgo "                                                     
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                CategoriaRiesgo cri= new CategoriaRiesgo(rs.getInt("cod_categoria_riesgo"), rs.getString("nombre"));
                cri.setCodCategoriaRiesgo(rs.getInt("cod_categoria_riesgo"));
                cri.setNombre(rs.getString("nombre"));                
                listaCategoriaRiesgos.add(cri);                
            }
            return listaCategoriaRiesgos;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.planemergencias.conrolador;

import com.gestor.controller.Gestor;
import com.gestor.planemergencias.Amenaza;
import com.gestor.planemergencias.AnalisisAmenazas;
import com.gestor.planemergencias.AnalisisVulnerabilidad;
import com.gestor.planemergencias.AnalisisVulnerabilidadResultados;
import com.gestor.planemergencias.PlanEmergencia;
import com.gestor.planemergencias.CuestionarioVulnerabilidad;
import com.gestor.planemergencias.DeterminacionNivelRiesgo;
import com.gestor.planemergencias.Gravedad;
import com.gestor.planemergencias.Probabilidad;
import com.gestor.planemergencias.RelDeterminacionNivelRiesgo;
import com.gestor.planemergencias.RelOriegenTipo;
import com.gestor.planemergencias.RelVulnerabilidadTipo;
import com.gestor.planemergencias.TipoOrigen;
import com.gestor.planemergencias.Vulnerabilidad;
import com.gestor.planemergencias.dao.PlanEmergenciasDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Francisco
 */
public class GestorPlanEmergencias extends Gestor implements Serializable{

    public GestorPlanEmergencias() throws Exception {
        super();
    }
    
    public Collection<? extends RelDeterminacionNivelRiesgo> cargarRelDeterminacionNivelRiesgo(Integer codPlanEmergencia, RelOriegenTipo rto) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarRelDeterminacionNivelRiesgo(codPlanEmergencia, rto);                        
        } finally {
            this.cerrarConexion();
        }
    }    
    
    public Collection<? extends TipoOrigen> cargarTiposOrigen() throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaTiposOrigen();                        
        } finally {
            this.cerrarConexion();
        }
    }
    

    public Collection<? extends CuestionarioVulnerabilidad> cargarCuestionarioVulnerabilidadTipo(Integer codVulnerabilidad, Integer codVulnerabilidadTipo) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaCuestionarioTipo(codVulnerabilidad, codVulnerabilidadTipo);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends AnalisisAmenazas> cargarAnalisisAmenazaCentroTrabajo(PlanEmergencia pem) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaAnalisisAmenazasCentroTrabajo(pem);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends Amenaza> cargarPromedioAmenaza(StringBuilder sb, Integer codPlanEmergencia) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaPromedioAmenaza(sb, codPlanEmergencia);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends RelDeterminacionNivelRiesgo> cargarTiposVulnerabilidad(RelDeterminacionNivelRiesgo reldnr) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaTiposVulnerabilidad(reldnr);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends AnalisisVulnerabilidad> cargarAnalisisVulnerabilidadesPersonasCentroTrabajo(PlanEmergencia pem) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaAnalisisVulnerabilidadesPersonasCentroTrabajo(pem);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Integer cargarvulnerabildadSize(PlanEmergencia pe) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarvulnerabildadSize(pe);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Integer cargarvulnerabildadtipoSize(PlanEmergencia pe,Integer i) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarvulnerabildadtipoSize(pe,i);
        } finally {
            this.cerrarConexion();
        }
    } 

    
    public Collection<? extends AnalisisVulnerabilidadResultados> cargarAnalisisVulnerabilidadesResultadosList(PlanEmergencia pem) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarAnalisisVulnerabilidadesResultadosList(pem);
        } finally {
            this.cerrarConexion();
        }
    }
    
    
    public Collection<? extends AnalisisVulnerabilidad> cargarAnalisisVulnerabilidadesRecursosCentroTrabajo(PlanEmergencia pem) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaAnalisisVulnerabilidadesRecursosCentroTrabajo(pem);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends AnalisisVulnerabilidad> cargarAnalisisVulnerabilidadesSistemasCentroTrabajo(PlanEmergencia pem) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaAnalisisVulnerabilidadesSistemasCentroTrabajo(pem);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends AnalisisVulnerabilidad> cargarAnalisisVulnerabilidadesCentroTrabajo(PlanEmergencia pem, Integer codVul, Integer codVultipo) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaAnalisisVulnerabilidadesCentroTrabajo(pem, codVul, codVultipo);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends RelOriegenTipo> cargarOrigenes(Integer codTiopOrigen) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaOrigenes(codTiopOrigen);                        
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends RelOriegenTipo> cargarOrigenesTipoList() throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaRelOrigenTiposList();
        } finally {
            this.cerrarConexion();
        }
    }    

    public Collection<? extends Probabilidad> cargarProbabilidades() throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaProbabilidades();                        
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends Gravedad> cargarGravedades() throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaGravedades();                        
        } finally {
            this.cerrarConexion();
        }
    }
        
    public Collection<? extends Vulnerabilidad> cargarVulnerabilidades() throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaVulnerabilidades();                        
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends Vulnerabilidad> cargarVulnerabilidadesNivelRiesgo(Integer codPlanEmergencia) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaVulnerabilidadesNivelRiesgo(codPlanEmergencia);                        
        } finally {
            this.cerrarConexion();
        }
    }
    

    public Collection<? extends RelVulnerabilidadTipo> cargarVulnerabilidadTipos(Integer codVulnerabilidad) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaVulnerabilidadTipos(codVulnerabilidad);                        
        } finally {
            this.cerrarConexion();
        }
    }
    
    
    public void almacenarPregunta(CuestionarioVulnerabilidad cv) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.insertarPregunta(cv);
            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarAnalisisAmenaza(AnalisisAmenazas aam) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.eliminarAnalisisAmenaza(aam);
            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarAnalisisVulnerabilidad(AnalisisVulnerabilidad av) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.eliminarAnalisisAmenaza(av);
            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarPlanEmergencia(PlanEmergencia pe) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.insertarPlanEmergencia(pe);            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarAnalisisAmenazas(ArrayList<AnalisisAmenazas> analisisAmenazaList ) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.insertarAnalisisAmenazas(analisisAmenazaList);            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarAnalisisVulnerabilidades(ArrayList<AnalisisVulnerabilidad> analisisVulnerabilidadesList ) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.insertarAnalisisVulnerabilidades(analisisVulnerabilidadesList);            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarAnalisisVulnerabilidadesResultado(ArrayList<AnalisisVulnerabilidadResultados> analisisVulnerabilidadesResultadoList ) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.insertarAnalisisVulnerabilidadesResultado(analisisVulnerabilidadesResultadoList);            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarDeterminacionNivelRiesgo(DeterminacionNivelRiesgo determinacionNivelRiesgo ) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.insertarDeterminacionNivelRiesgo(determinacionNivelRiesgo);            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminarDeterminacionNivelRiesgo(Integer codPlanEmergencia ) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.eliminarDeterminacionNivelRiesgo(codPlanEmergencia);            
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void almacenarRelDeterminacionNivelRiesgo(RelDeterminacionNivelRiesgo relDeterminacionNivelRiesgo) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.insertarRelDeterminacionNivelRiesgo(relDeterminacionNivelRiesgo);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void updateRelDeterminacionNivelRiesgo(ArrayList<RelDeterminacionNivelRiesgo> relDeterminacionNivelRiesgoList) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.updateRelDeterminacionNivelRiesgo(relDeterminacionNivelRiesgoList);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void updateDetNivelRiesgo(Integer codDetNivelRiesgo, Float prom, String nivel) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            PlanEmergenciasDAO planEmergenciasDAO = new PlanEmergenciasDAO(conexion);                                    
            planEmergenciasDAO.updateRelDeterminacionNivelRiesgo(codDetNivelRiesgo, prom, nivel);
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends CuestionarioVulnerabilidad> cargarCuestionarioVulnerabilidades() throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarListaCuestionarioVulnerabilidad();                        
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Collection<? extends PlanEmergencia> cargarPlanesEmergencias(Integer codigoEstablecimiento) throws Exception {
        try {
            this.abrirConexion();
            PlanEmergenciasDAO planEmergenciasDAO =new PlanEmergenciasDAO(conexion);
            return planEmergenciasDAO.cargarPlanesEmergencias(codigoEstablecimiento);
        } finally {
            this.cerrarConexion();
        }
    }
    
    
    
    
}

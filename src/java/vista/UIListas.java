/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorEmpleado;
import controlador.GestorEps;
import controlador.GestorCargo;
import controlador.GestorEcivil;
import controlador.GestorMes;
import controlador.GestorSexo;
import controlador.GestorMunicipio;
import controlador.GestorListas;
import controlador.GestorNivelEscolar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import modelo.Empleado;
import modelo.Municipio;
import modelo.Eps;
import modelo.Cargo;
import modelo.Ecivil;
import modelo.Sexo;
import modelo.Mes;
import util.Utilidades;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.AgenteAccidente;
import modelo.AntiguedadCargo;
import modelo.AntiguedadEmpresa;
import modelo.Empresa;
import modelo.SubEmpresa;
import modelo.Año;
import modelo.CausaBasica;
import modelo.CausaInmediata;
import modelo.Clasificacion;
import modelo.ConsumoBebidasAlcoholicas;
import modelo.IncapacidadSi;
import modelo.Mecanismo;
import modelo.NivelEscolar;
import modelo.NumeroPersonasCargo;
import modelo.ParteAfectada;
import modelo.ParticipaActividades;
import modelo.PromedioIngreso;
import modelo.Riesgo;
import modelo.TendenciaVivienda;
import modelo.TipoAccidente;
import modelo.TipoContratacion;
import modelo.TipoEvento;
import modelo.TipoIncapacidad;
import modelo.TipoLesion;
import modelo.UsoTiempoLibre;
/**
 *
 * @author Andres
 */
public class UIListas implements Serializable {

    public String cedula;
    public Empleado empleado;
    public Empresa empresa;
    public SubEmpresa subempresa;
    public Utilidades util = new Utilidades();
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory empl;
    private GestorEmpleado gestorEmpleado; 
    private GestorEps gestorEps;
    private GestorEcivil gestorEcivil;
    private GestorCargo gestorCargo;
    private GestorNivelEscolar gestorNivelEscolar;
    private GestorSexo gestorSexo;
    private GestorMunicipio gestorMunicipio;
    private GestorListas gestorListas;
    private GestorMes gestorMes;
    private ArrayList<SelectItem> itemsConsumeBebidasAlcoholicas = new ArrayList<>();
    private ArrayList<SelectItem> itemsParticipaActividades = new ArrayList<>();
    private ArrayList<SelectItem> itemsTipoContratacion = new ArrayList<>();
    private ArrayList<SelectItem> itemsAntiguedadCargo = new ArrayList<>();
    private ArrayList<SelectItem> itemsAntiguedadEmpresa = new ArrayList<>();
    private ArrayList<SelectItem> itemsPromedioIngreso = new ArrayList<>();
    private ArrayList<SelectItem> itemsTiempoLibre = new ArrayList<>();
    private ArrayList<SelectItem> itemsNumeroPersonasCargo = new ArrayList<>();
    private ArrayList<SelectItem> itemsTenenciaViviendas = new ArrayList<>();
    private ArrayList<SelectItem> itemsNivelEscolar = new ArrayList<>();
    private ArrayList<SelectItem> itemsEpss = new ArrayList<>();    
    private ArrayList<SelectItem> itemsCargos = new ArrayList<>();
    private ArrayList<SelectItem> itemsEciviles = new ArrayList<>();
    private ArrayList<SelectItem> itemsSexos = new ArrayList<>();
    private ArrayList<SelectItem> itemsTipoIncapacidades = new ArrayList<>();
    private ArrayList<SelectItem> itemsTipoEventos = new ArrayList<>();
    private ArrayList<SelectItem> itemsClasificaciones = new ArrayList<>();
    private ArrayList<SelectItem> itemsIncapacidadesSi = new ArrayList<>();
    private ArrayList<SelectItem> itemsParteAfectada = new ArrayList<>();
    private ArrayList<SelectItem> itemsTipoLesiones = new ArrayList<>();
    private ArrayList<SelectItem> itemsRiesgos = new ArrayList<>();
    private ArrayList<SelectItem> itemsMecanismos = new ArrayList<>();
    private ArrayList<SelectItem> itemsAgentesAccidente = new ArrayList<>();
    private ArrayList<SelectItem> itemsSubempresas = new ArrayList<>();
    private ArrayList<SelectItem> itemsCausaBasicas = new ArrayList<>();
    private ArrayList<SelectItem> itemsCausaInmediatas = new ArrayList<>();
    private ArrayList<SelectItem> itemsTipoAccidente = new ArrayList<>();
    private ArrayList<SelectItem> itemsEmpresas = new ArrayList<>();    
    private ArrayList<SelectItem> itemsMeses = new ArrayList<>();
    
    private ExpressionFactory ef;    
    

    public UIListas()  throws Exception {
       contextoJSF = FacesContext.getCurrentInstance();
       contextoEL = contextoJSF.getELContext(); 
       empl = contextoJSF.getApplication().getExpressionFactory();
       empleado = new Empleado();
       gestorMunicipio = new GestorMunicipio();
       gestorListas = new GestorListas();
       empleado.setResidencia(new Municipio()); 
       gestorEmpleado = new GestorEmpleado();
       empresa = new Empresa();
       subempresa = new SubEmpresa();
       itemsTipoIncapacidades=new ArrayList<>();
       itemsSubempresas=new ArrayList<>();
       
    }
  
    public List<String> listarMunicipiosPatron(String query) throws Exception {
        
        ArrayList<Municipio> listaMunicipiosLocal;
        listaMunicipiosLocal = getGestorListas().listarMunicipiosPatron(query);
        List<String> listaMun = new ArrayList<>();        
        for (Municipio m : listaMunicipiosLocal) {
            listaMun.add(m.getNombre());
        }        
        return listaMun;
    }     
       
    public ArrayList<SelectItem> getItemsEpss() throws Exception{
            
            try {
                gestorEps = new GestorEps();
                ArrayList<Eps> listaEpss;
                listaEpss = gestorListas.listarEpss();
                itemsEpss.clear();
                
                for (int i = 0; i < listaEpss.size(); i++) {                    
                        itemsEpss.add(new SelectItem(listaEpss.get(i).getCodigo(), listaEpss.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsEpss;    
    }

    public ArrayList<SelectItem> getItemsCargos() throws Exception{
            
            try {
                gestorCargo = new GestorCargo();
                ArrayList<Cargo> listaCargos;
                listaCargos = gestorListas.listarCargos();
                itemsCargos.clear();
                for (int i = 0; i < listaCargos.size(); i++) {                    
                        itemsCargos.add(new SelectItem(listaCargos.get(i).getCodigo(), listaCargos.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsCargos;    
    }
    
    public ArrayList<SelectItem> getItemsNivelEscolar() throws Exception{
            
        try {                
            ArrayList<NivelEscolar> listaNivelEscolars;
            listaNivelEscolars = gestorListas.listarNivelEscolares();
            itemsNivelEscolar.clear();
            for (int i = 0; i < listaNivelEscolars.size(); i++) {                    
                    itemsNivelEscolar.add(new SelectItem(listaNivelEscolars.get(i).getCodigo(), listaNivelEscolars.get(i).getNombre()));
                }
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }            
            return itemsNivelEscolar;    
    }
    
    public ArrayList<SelectItem> getItemsNumeroPersonasCargo() throws Exception{
            
            try {                
                ArrayList<NumeroPersonasCargo> listaNumeroPersonasCargo;
                listaNumeroPersonasCargo = gestorListas.listarNumeroPersonasCargo();
                itemsNumeroPersonasCargo.clear();
                for (int i = 0; i < listaNumeroPersonasCargo.size(); i++) {                    
                        itemsNumeroPersonasCargo.add(new SelectItem(listaNumeroPersonasCargo.get(i).getCodigo(), listaNumeroPersonasCargo.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }            
                return itemsNumeroPersonasCargo;    
    }

    public ArrayList<SelectItem> getItemsConsumeBebidasAlcoholicas() {
        try {                
                ArrayList<ConsumoBebidasAlcoholicas> listaConsumoBebidasAlcoholicas;
                listaConsumoBebidasAlcoholicas = gestorListas.listarConsumoBebidasAlcoholicas();
                itemsConsumeBebidasAlcoholicas.clear();
                for (int i = 0; i < listaConsumoBebidasAlcoholicas.size(); i++) {                    
                        itemsConsumeBebidasAlcoholicas.add(new SelectItem(listaConsumoBebidasAlcoholicas.get(i).getCodigo(), listaConsumoBebidasAlcoholicas.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }            
                return itemsConsumeBebidasAlcoholicas;  
    }

    public void setItemsConsumeBebidasAlcoholicas(ArrayList<SelectItem> itemsConsumeBebidasAlcoholicas) {
        this.itemsConsumeBebidasAlcoholicas = itemsConsumeBebidasAlcoholicas;
    }
    
    
    
    
    
    public ArrayList<SelectItem> getItemsTenenciaViviendas() throws Exception{
            
            try {                
                ArrayList<TendenciaVivienda> listaTenenciaVivienda;
                listaTenenciaVivienda = gestorListas.listarTendenciaVivienda();
                itemsTenenciaViviendas.clear();
                for (int i = 0; i < listaTenenciaVivienda.size(); i++) {                    
                        itemsTenenciaViviendas.add(new SelectItem(listaTenenciaVivienda.get(i).getCodigo(), listaTenenciaVivienda.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }            
                return itemsTenenciaViviendas;    
    }
    


    
    public ArrayList<SelectItem> getItemsTiempoLibre() throws Exception{
            
            try {                
                ArrayList<UsoTiempoLibre> listaUsoTiempoLibre;
                listaUsoTiempoLibre = gestorListas.listarUsoTiempoLibre();
                itemsTiempoLibre.clear();
                for (int i = 0; i < listaUsoTiempoLibre.size(); i++) {                    
                        itemsTiempoLibre.add(new SelectItem(listaUsoTiempoLibre.get(i).getCodigo(), listaUsoTiempoLibre.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }            
                return itemsTiempoLibre;
    }
    
    
    public ArrayList<SelectItem> getItemsPromedioIngreso() throws Exception{
            
            try {                
                ArrayList<PromedioIngreso> listaPromedioIngreso;
                listaPromedioIngreso = gestorListas.listarPromedioIngresos();
                itemsPromedioIngreso.clear();
                for (int i = 0; i < listaPromedioIngreso.size(); i++) {                    
                        itemsPromedioIngreso.add(new SelectItem(listaPromedioIngreso.get(i).getCodigo(), listaPromedioIngreso.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }            
                return itemsPromedioIngreso;
    }
    
    public ArrayList<SelectItem> getItemsAntiguedadEmpresa() {
        try {                
            ArrayList<AntiguedadEmpresa> listaAntiguedadEmpresa;
            listaAntiguedadEmpresa = gestorListas.listarAntiguedadEmpresa();
            itemsAntiguedadEmpresa.clear();
            for (int i = 0; i < listaAntiguedadEmpresa.size(); i++) {                    
                    itemsAntiguedadEmpresa.add(new SelectItem(listaAntiguedadEmpresa.get(i).getCodigo(), listaAntiguedadEmpresa.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }            
            return itemsAntiguedadEmpresa;
    }
    
    public ArrayList<SelectItem> getItemsAntiguedadCargo() {
        try {                
            ArrayList<AntiguedadCargo> listaAntiguedadCargo;
            listaAntiguedadCargo = gestorListas.listarAntiguedadCargo();
            itemsAntiguedadCargo.clear();
            for (int i = 0; i < listaAntiguedadCargo.size(); i++) {                    
                    itemsAntiguedadCargo.add(new SelectItem(listaAntiguedadCargo.get(i).getCodigo(), listaAntiguedadCargo.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }            
            return itemsAntiguedadCargo;
    }
    
    public ArrayList<SelectItem> getItemsTipoContratacion() {
        try {                
            ArrayList<TipoContratacion> listaTipoContratacion;
            listaTipoContratacion = gestorListas.listarTiposContratacion();
            itemsTipoContratacion.clear();
            for (int i = 0; i < listaTipoContratacion.size(); i++) {
                    itemsTipoContratacion.add(new SelectItem(listaTipoContratacion.get(i).getCodigo(), listaTipoContratacion.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }            
            return itemsTipoContratacion;        
    }    
    
    public ArrayList<SelectItem> getItemsParticipaActividades() {
        try {                
            ArrayList<ParticipaActividades> listaParticipaActividades;
            listaParticipaActividades = gestorListas.listarParticipaActividades();
            itemsParticipaActividades.clear();
            for (int i = 0; i < listaParticipaActividades.size(); i++) {
                    itemsParticipaActividades.add(new SelectItem(listaParticipaActividades.get(i).getCodigo(), listaParticipaActividades.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }            
            return itemsParticipaActividades;        
    }    
    
    public ArrayList<SelectItem> getItemsMeses() throws Exception{
            
            try {
                gestorMes = new GestorMes();
                ArrayList<Mes> listaMeses;
                listaMeses = gestorListas.listarMeses();
                itemsMeses.clear();
                for (int i = 0; i < listaMeses.size(); i++) {                    
                        itemsMeses.add(new SelectItem(listaMeses.get(i).getCodigo(), listaMeses.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsMeses;    
    }
    
    public ArrayList<SelectItem> getItemsAños() throws Exception{
            
            try {
                gestorListas = new GestorListas();
                ArrayList<Año> listaAños;
                listaAños = gestorListas.listarAños();
                itemsMeses.clear();
                for (int i = 0; i < listaAños.size(); i++) {                    
                        itemsMeses.add(new SelectItem(listaAños.get(i).getAño()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsMeses;    
    }



    public ArrayList<SelectItem> getItemsEciviles() throws Exception{
            
            try {
                gestorEcivil = new GestorEcivil();
                ArrayList<Ecivil> listaEciviles;
                listaEciviles = gestorListas.listarEciviles();
                itemsEciviles.clear();
                for (int i = 0; i < listaEciviles.size(); i++) {                    
                        itemsEciviles.add(new SelectItem(listaEciviles.get(i).getCodigo(), listaEciviles.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsEciviles;    
    }

    public ArrayList<SelectItem> getItemsSexos() throws Exception{
            
            try {
                gestorSexo = new GestorSexo();
                ArrayList<Sexo> listaSexos;
                listaSexos = gestorListas.listarSexos();
                itemsSexos.clear();
                for (int i = 0; i < listaSexos.size(); i++) {                    
                        itemsSexos.add(new SelectItem(listaSexos.get(i).getCodigo(), listaSexos.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsSexos;    
    }
    
    public ArrayList<SelectItem> getItemsTipoIncapacidades() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<TipoIncapacidad> listaTipoIncapacidades;
            listaTipoIncapacidades = gestorListas.listarTipoIncapaciadades();
            itemsTipoIncapacidades.clear();
            for (int i = 0; i < listaTipoIncapacidades.size(); i++) {                    
                    itemsTipoIncapacidades.add(new SelectItem(listaTipoIncapacidades.get(i).getCodigo(), listaTipoIncapacidades.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsTipoIncapacidades;    
    }
    
    public ArrayList<SelectItem> getItemsTipoEvento() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<TipoEvento> listaTipoEventos;
            listaTipoEventos = gestorListas.listarTipoEventos();
            itemsTipoEventos.clear();
            for (int i = 0; i < listaTipoEventos.size(); i++) {                    
                    itemsTipoEventos.add(new SelectItem(listaTipoEventos.get(i).getCodigo(), listaTipoEventos.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsTipoEventos;    
    }
    
    public ArrayList<SelectItem> getItemsClasificaiones() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<Clasificacion> listaClasificaciones;
            listaClasificaciones = gestorListas.listarClasificaciones();
            itemsClasificaciones.clear();
            for (int i = 0; i < listaClasificaciones.size(); i++) {                    
                    itemsClasificaciones.add(new SelectItem(listaClasificaciones.get(i).getCodigo(), listaClasificaciones.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsClasificaciones;    
    }
    
    public ArrayList<SelectItem> getItemsIncapacidadesSi() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<IncapacidadSi> listaIncapacidadSis;
            listaIncapacidadSis = gestorListas.listarIncapacidadesSi();
            itemsIncapacidadesSi.clear();
            for (int i = 0; i < listaIncapacidadSis.size(); i++) {                    
                    itemsIncapacidadesSi.add(new SelectItem(listaIncapacidadSis.get(i).getCodigo(), listaIncapacidadSis.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsIncapacidadesSi;    
    }

    public ArrayList<SelectItem> getItemsTipoAccidentes() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<TipoAccidente> listaTipoAccidentes;
            listaTipoAccidentes = gestorListas.listarTipoAccidente();
            itemsTipoAccidente.clear();
            for (int i = 0; i < listaTipoAccidentes.size(); i++) {                    
                    itemsTipoAccidente.add(new SelectItem(listaTipoAccidentes.get(i).getCodigo(), listaTipoAccidentes.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsTipoAccidente;    
    }
    
    public ArrayList<SelectItem> getItemsParteAfectadas() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<ParteAfectada> listaParteAfectadas;
            listaParteAfectadas = gestorListas.listarParteAfectada();
            itemsParteAfectada.clear();
            for (int i = 0; i < listaParteAfectadas.size(); i++) {                    
                    itemsParteAfectada.add(new SelectItem(listaParteAfectadas.get(i).getCodigo(), listaParteAfectadas.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsParteAfectada;    
    }
    
    public ArrayList<SelectItem> getItemsTipoLesiones() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<TipoLesion> listaTipoLesiones;
            listaTipoLesiones = gestorListas.listarTipoLesiones();
            itemsTipoLesiones.clear();
            for (int i = 0; i < listaTipoLesiones.size(); i++) {                    
                    itemsTipoLesiones.add(new SelectItem(listaTipoLesiones.get(i).getCodigo(), listaTipoLesiones.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsTipoLesiones;    
    }
    
    public ArrayList<SelectItem> getItemsRiesgos() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<Riesgo> listaRiesgos;
            listaRiesgos = gestorListas.listarRiesgos();
            itemsRiesgos.clear();
            for (int i = 0; i < listaRiesgos.size(); i++) {                    
                    itemsRiesgos.add(new SelectItem(listaRiesgos.get(i).getCodigo(), listaRiesgos.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsRiesgos;    
    }
    
    public ArrayList<SelectItem> getItemsMecanismos() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<Mecanismo> listaMecanismos;
            listaMecanismos = gestorListas.listarMecanismos();
            itemsMecanismos.clear();
            for (int i = 0; i < listaMecanismos.size(); i++) {                    
                    itemsMecanismos.add(new SelectItem(listaMecanismos.get(i).getCodigo(), listaMecanismos.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsMecanismos;    
    }
    
    public ArrayList<SelectItem> getItemsAgentesAccidente() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<AgenteAccidente> listaAgenteAccidentes;
            listaAgenteAccidentes = gestorListas.listarAgentesAccidente();
            itemsAgentesAccidente.clear();
            for (int i = 0; i < listaAgenteAccidentes.size(); i++) {                    
                    itemsAgentesAccidente.add(new SelectItem(listaAgenteAccidentes.get(i).getCodigo(), listaAgenteAccidentes.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsAgentesAccidente;    
    }
    
    public ArrayList<SelectItem> getItemsCausaBasicas() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<CausaBasica> listaCausaBasicas;
            listaCausaBasicas = gestorListas.listarCausaBasicas();
            itemsCausaBasicas.clear();
            for (int i = 0; i < listaCausaBasicas.size(); i++) {                    
                    itemsCausaBasicas.add(new SelectItem(listaCausaBasicas.get(i).getCodigo(), listaCausaBasicas.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsCausaBasicas;    
    }
    
    public ArrayList<SelectItem> getItemsCausaInmediata() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<CausaInmediata> listaCausaInmediata;
            listaCausaInmediata = gestorListas.listarCausaInmediatas();
            itemsCausaInmediatas.clear();
            for (int i = 0; i < listaCausaInmediata.size(); i++) {                    
                    itemsCausaInmediatas.add(new SelectItem(listaCausaInmediata.get(i).getCodigo(), listaCausaInmediata.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsCausaInmediatas;    
    }

    
    public ArrayList<SelectItem> getItemsEmpresas() throws Exception{
            
            try {
                gestorListas = new GestorListas();
                ArrayList<Empresa> listaEmpresa;
                listaEmpresa = gestorListas.listarEmpresas();
                itemsEmpresas.clear();
                for (int i = 0; i < listaEmpresa.size(); i++) {
                        itemsEmpresas.add(new SelectItem(listaEmpresa.get(i).getNitempresa(), listaEmpresa.get(i).getNombre()));                        
                    }
                }
            catch (Exception ex) {
                        Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsEmpresas;    
    }
    
        public ArrayList<SelectItem> getItemsSubEmpresas() throws Exception{
            itemsSubempresas.clear();
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            
            String nitempresa = empresa.getNitempresa();
            String nomusuario="";
            
            
            if ( nitempresa == null) {
                
                nitempresa = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.empresa.nitempresa}", String.class).getValue(contextoEL);  
                nomusuario = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.nombre}", String.class).getValue(contextoEL);
                try {
                    gestorListas = new GestorListas();
                    ArrayList<SubEmpresa> listaSubEmpresa;
                    listaSubEmpresa = gestorListas.listarSubempresas(nitempresa,nomusuario);
                    itemsSubempresas.clear();
                    for (int i = 0; i < listaSubEmpresa.size(); i++) {                    
                            itemsSubempresas.add(new SelectItem(listaSubEmpresa.get(i).getNitsubempresa(), listaSubEmpresa.get(i).getNombre()));
                        }                        
                    }
                catch (Exception ex) {
                            Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
                    }

            }else{
                
                try {
                    gestorListas = new GestorListas();
                    ArrayList<SubEmpresa> listaSubEmpresa;
                    listaSubEmpresa = gestorListas.listarSubempresas(nitempresa, nomusuario);
                    itemsSubempresas.clear();
                    for (int i = 0; i < listaSubEmpresa.size(); i++) {                    
                            itemsSubempresas.add(new SelectItem(listaSubEmpresa.get(i).getNitsubempresa(), listaSubEmpresa.get(i).getNombre()));
                        }                        
                    }
                catch (Exception ex) {
                            Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            
                return itemsSubempresas;    
    }
        
    public String convertirMeses(String fecha){
        
        String nomfecha = "";
        
                switch (fecha) {
            case "01":
                nomfecha="ENERO";
                break;
            case "02":
                nomfecha="FEBRERO";
                break;
            case "03":
                nomfecha="MARZO";
                break;
            case "04":
                nomfecha="ABRIL";
                break;
            case "05":
                nomfecha="MAYO";
                break;
            case "06":
                nomfecha="JUNIO";    
                break;
            case "07":
                nomfecha="JULIO";
                break;
            case "08":
                nomfecha="AGOSTO";
                break;
            case "09":
                nomfecha="SEPTIEMBRE";
                break;
            case "10":
                nomfecha="OCTUBRE";
                break;
            case "11":
                nomfecha="NOVIEMBRE";
                break;
            case "12":
                nomfecha="DICIEMBRE";                            
                break;
        } 
                
                return nomfecha;
    }  



    public void setItemsTipoContratacion(ArrayList<SelectItem> itemsTipoContratacion) {
        this.itemsTipoContratacion = itemsTipoContratacion;
    }

    public void setItemsAntiguedadCargo(ArrayList<SelectItem> itemsAntiguedadCargo) {
        this.itemsAntiguedadCargo = itemsAntiguedadCargo;
    }

    public void setItemsAntiguedadEmpresa(ArrayList<SelectItem> itemsAntiguedadEmpresa) {
        this.itemsAntiguedadEmpresa = itemsAntiguedadEmpresa;
    }
    
    public void setItemsTenenciaViviendas(ArrayList<SelectItem> itemsTenenciaViviendas) {
        this.itemsTenenciaViviendas = itemsTenenciaViviendas;
    }

    public void setItemsNumeroPersonasCargo(ArrayList<SelectItem> itemsNumeroPersonasCargo) {
        this.itemsNumeroPersonasCargo = itemsNumeroPersonasCargo;
    }

    public GestorNivelEscolar getGestorNivelEscolar() {
        return gestorNivelEscolar;
    }

    public void setGestorNivelEscolar(GestorNivelEscolar gestorNivelEscolar) {
        this.gestorNivelEscolar = gestorNivelEscolar;
    }

    public void setItemsMecanismos(ArrayList<SelectItem> itemsMecanismos) {
        this.itemsMecanismos = itemsMecanismos;
    }   

    public void setItemsTipoIncapacidades(ArrayList<SelectItem> itemsTipoIncapacidades) {
        this.itemsTipoIncapacidades = itemsTipoIncapacidades;
    }

    public GestorMunicipio getGestorMunicipio() {
        return gestorMunicipio;
    }

    public void setGestorMunicipio(GestorMunicipio gestorMunicipio) {
        this.gestorMunicipio = gestorMunicipio;
    }

    public GestorListas getGestorListas() {
        return gestorListas;
    }

    public void setGestorListas(GestorListas gestorListas) {
        this.gestorListas = gestorListas;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }     

    public SubEmpresa getSubempresa() {
        return subempresa;
    }

    public void setSubempresa(SubEmpresa subempresa) {
        this.subempresa = subempresa;
    }   
}
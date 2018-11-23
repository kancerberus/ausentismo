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
import modelo.Empresa;
import modelo.SubEmpresa;
import modelo.Año;
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
    private GestorSexo gestorSexo;
    private GestorMunicipio gestorMunicipio;
    private GestorListas gestorListas;
    private GestorMes gestorMes;
    private ArrayList<SelectItem> itemsEpss = new ArrayList<>();
    private ArrayList<SelectItem> itemsCargos = new ArrayList<>();
    private ArrayList<SelectItem> itemsEciviles = new ArrayList<>();
    private ArrayList<SelectItem> itemsSexos = new ArrayList<>();
    private ArrayList<SelectItem> itemsSubempresas = new ArrayList<>();
    private ArrayList<SelectItem> itemsEmpresas = new ArrayList<>();
    private ArrayList<SelectItem> itemsMeses = new ArrayList<>();
    

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
            
            String nitempresa = empresa.getNitempresa();
            
            if ( nitempresa == null) {
                
                try {
                    gestorListas = new GestorListas();
                    ArrayList<SubEmpresa> listaSubEmpresa;
                    listaSubEmpresa = gestorListas.listarSubempresas();
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
                    listaSubEmpresa = gestorListas.listarSubempresas(nitempresa);
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
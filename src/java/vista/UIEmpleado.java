/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorAusentismo;
import controlador.GestorMunicipio;
import java.io.Serializable;
import controlador.GestorEmpleado;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import modelo.Ausentismo;
import modelo.Empleado;
import modelo.Municipio;
import util.Utilidades;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import modelo.Año;
import modelo.Mes;
import org.primefaces.model.chart.PieChartModel;
/**
 *
 * @author Andres
 */
public class UIEmpleado implements Serializable {

    public Empleado empleado;    
    private Ausentismo ausentismo;
    public Utilidades util = new Utilidades();
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory empl;
    private GestorEmpleado gestorEmpleado; 
    private GestorMunicipio gestorMunicipio;       
    private ExpressionFactory ef;
    private List<Empleado> listaEmpleado;
    private List<Empleado> listaEmpleadoAdmin;    
    private List<Empleado> filteredlistaEmpleado; 
    private String cedula;
    private Boolean todos;
    private Mes mes;
    private Año ano;
    private Integer total;
    private Integer totalCargos;
    private Integer totalNescolar;
    private Integer totalTvivienda;
    private Integer totalUsoTlibre;
    private Integer totalPromedioIngreso;
    private Integer totalAntiguedadEmpresa;
    private Integer totalAntiguedadCargo;
    private Integer totalTipoContrato;
    private Integer totalActivEmpresa;
    private Integer totalFuma;
    private Integer totalDiagnosticado;
    private Integer totalConsumoAlcohol;
    private Integer totalPracticaDeporte;
    
    
    
    private List<Empleado> distribucionEdad=new ArrayList<>();
    private List<Empleado> distribucionECivil=new ArrayList<>();
    private List<Empleado> distribucionSexo=new ArrayList<>();
    private List<Empleado> distribucionNumPerCargo=new ArrayList<>();
    private List<Empleado> distribucionNivelEscolaridad=new ArrayList<>();
    private List<Empleado> distribucionTendenciaVivienda=new ArrayList<>();
    private List<Empleado> distribucionUsoTiempoLibre=new ArrayList<>();    
    private List<Empleado> distribucionPromedioIngreso=new ArrayList<>();    
    private List<Empleado> distribucionAntiguedadEmpresa=new ArrayList<>();    
    private List<Empleado> distribucionAntiguedadCargo=new ArrayList<>();    
    private List<Empleado> distribucionTipoContrato=new ArrayList<>();    
    private List<Empleado> distribucionActivEmpresa=new ArrayList<>();
    private List<Empleado> distribucionFuma=new ArrayList<>();
    private List<Empleado> distribucionDiagnosticadoEnfermedad=new ArrayList<>();
    private List<Empleado> distribucionConsumoAlcohol=new ArrayList<>();
    private List<Empleado> distribucionPracticaDeporte=new ArrayList<>();
    private List<Empleado> perfilSocioList=new ArrayList<>();
    
    private PieChartModel pieEdades=new PieChartModel();
    private PieChartModel pieNumPerCargo=new PieChartModel();
    private PieChartModel pieECivil=new PieChartModel();
    private PieChartModel pieSexo=new PieChartModel();
    private PieChartModel pieNivelEscolaridad=new PieChartModel();
    private PieChartModel pieTendenciaVivienda=new PieChartModel();
    private PieChartModel pieUsoTiempoLibre=new PieChartModel();
    private PieChartModel piePromedioIngreso=new PieChartModel();
    private PieChartModel pieAntiguedadEmpresa=new PieChartModel();
    private PieChartModel pieAntiguedadCargo=new PieChartModel();
    private PieChartModel pieTipoContrato=new PieChartModel();
    private PieChartModel pieActivEmpresa=new PieChartModel();
    private PieChartModel pieFuma=new PieChartModel();
    private PieChartModel pieDiagnosticadoEnfermedad=new PieChartModel();
    private PieChartModel pieConsumoAlcohol=new PieChartModel();
    private PieChartModel piePracticaDeporte=new PieChartModel();
    
    
    

    public UIEmpleado()  throws Exception {
       contextoJSF = FacesContext.getCurrentInstance();
       contextoEL = contextoJSF.getELContext(); 
       empl = contextoJSF.getApplication().getExpressionFactory();
       empleado = new Empleado();
       ausentismo = new Ausentismo();
       gestorMunicipio = new GestorMunicipio();
       empleado.setResidencia(new Municipio()); 
       gestorEmpleado = new GestorEmpleado();
       mes = new Mes();
       ano = new Año();
       total=0;
       
    }
            
    public void guardarEmpleado() throws Exception{    

        Boolean invalido = false;        

        //ingreso de informacion al gestor
        gestorEmpleado = new GestorEmpleado();
        
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);

        try {
            //verificar que todas las cajas este llenas
            
            
            
            if (empleado.getCedula() == null) {
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getNombres() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getApellidos() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getFecha_nac() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getMunicipio() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getEps() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }            
            if (empleado.getEps() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }         
            if (empleado.getCargo() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }       
            if (empleado.getEcivil() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }         
            if (empleado.getSexo() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }            
            if(empleado.getFechaIngreso()==null){
                invalido =true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if(empleado.getNescolar()==null){
                invalido =true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            
            
            Empleado encontradoEn = gestorEmpleado.buscarempleadoAdmin(empleado.getCedula());                    
                    
            if(encontradoEn != null){
                invalido=true;                
                util.mostrarMensaje("!! No se puede crear empleado ya existe en el centro de trabajo '"+encontradoEn.getNitsubempresa()+"'  !!");                        
            }
            
            if (!invalido) {
                    Integer resultado = gestorEmpleado.guardarEmpleado(empleado,nitsesion);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! El empleado fue creado de manera exitosa !!");
                        empleado = new Empleado();                    
                    } else {
                        util.mostrarMensaje("!! El registro no pudo ser almacenado !!");
                    }
            } 
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
            }
    }
    
    public List<Empleado> getListaEmpleado() {

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);          

        try {
            listaEmpleado = gestorEmpleado.listarEmpleados(nitsesion);
        } catch (Exception ex) {
            Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return (listaEmpleado);
    }
    
    public void buscarempleadoAdmin() throws Exception {
    
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        String cedula = empleado.getCedula();

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        
        
        empleado = gestorEmpleado.buscarempleadoAdmin(cedula); 
        
        if(empleado == null){                                                
            util.mostrarMensaje("La cedula no existe");
            empleado = new Empleado();
        }     
    }
    
    public void modificarSubempresaempleado() throws Exception{    

        String msg = null;
        
        //ingreso de informacion al gestor
        //gestorAusentismo = new GestorAusentismo();               

        try {
                    Integer resultado=gestorEmpleado.modificarSubempresaempleado(empleado);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! cambio guardado !!");
                        empleado = new Empleado();                    
                    } else {
                        util.mostrarMensaje("!! El cambio no pudo ser guardado !!");
                    }

            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! El cambio no pudo ser guardado !!");               
            }
    }
    
    public void buscarEmpleado() throws Exception {
    
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        String cedula = empleado.getCedula();

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        
        empleado = gestorEmpleado.buscarEmpleado(cedula, nitsesion); 
        
        if(empleado == null){                                                
            util.mostrarMensaje("La cedula no existe");
            empleado = new Empleado();
        }
    }

    public void generarReportePerfil() throws Exception{
        gestorEmpleado=new GestorEmpleado();        
        total=0;
        try {
            
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitem = (String) ef.createValueExpression(contextoEL, "#{listasBean.empresa.nitempresa}", String.class).getValue(contextoEL);  
            String nitsubem = (String) ef.createValueExpression(contextoEL, "#{listasBean.subempresa.nitsubempresa}", String.class).getValue(contextoEL);  
            //revisar checkbox    
                  
            if(nitsubem == ""){
                nitsubem = null;
            }
            
            distribucionEdad=new ArrayList<>();
            distribucionEdad.addAll(gestorEmpleado.cargarDistEdad(nitem, nitsubem));            
            if(distribucionEdad.size()!=0){
                total=distribucionEdad.get(0).getTotal();
            }
            
            
            
            pieEdades=new PieChartModel();
            for(int i=0;i<=distribucionEdad.size()-1;i++){                
                pieEdades.set(distribucionEdad.get(i).getDescDistribucion(), distribucionEdad.get(i).getCantidad());
                
            }
            pieEdades.isShowDataLabels();
            pieEdades.setLegendPosition("w");   
            pieEdades.setDataFormat("value");
            pieEdades.setShowDataLabels(true);
            
            distribucionECivil=new ArrayList<>();
            distribucionECivil.addAll(gestorEmpleado.cargarDistECivil(nitem,nitsubem));
            
            pieECivil=new PieChartModel();
            for(int i=0;i<=distribucionECivil.size()-1;i++){                
                pieECivil.set(distribucionECivil.get(i).getDescDistribucion(), distribucionECivil.get(i).getCantidad());
                
            }
            pieECivil.isShowDataLabels();
            pieECivil.setLegendPosition("w");   
            pieECivil.setDataFormat("value");
            pieECivil.setShowDataLabels(true);
            
            distribucionSexo=new ArrayList<>();
            distribucionSexo.addAll(gestorEmpleado.cargarDistSexo(nitem, nitsubem));
            
            pieSexo=new PieChartModel();
            for(int i=0;i<=distribucionSexo.size()-1;i++){                
                pieSexo.set(distribucionSexo.get(i).getDescDistribucion(), distribucionSexo.get(i).getCantidad());
                
            }
            pieSexo.isShowDataLabels();
            pieSexo.setLegendPosition("w");   
            pieSexo.setDataFormat("value");
            pieSexo.setShowDataLabels(true);
            
            
            distribucionNumPerCargo=new ArrayList<>();
            distribucionNumPerCargo.addAll(gestorEmpleado.cargarDistNumPerCargo(nitem, nitsubem));
            if(distribucionNumPerCargo.size()!=0){
                totalCargos=distribucionNumPerCargo.get(0).getTotal();
            }
            
            
            
            pieNumPerCargo=new PieChartModel();
            for(int i=0;i<=distribucionNumPerCargo.size()-1;i++){                
                pieNumPerCargo.set(distribucionNumPerCargo.get(i).getDescDistribucion(), distribucionNumPerCargo.get(i).getCantidad());                
            }
            
            pieNumPerCargo.isShowDataLabels();
            pieNumPerCargo.setLegendPosition("w");   
            pieNumPerCargo.setDataFormat("value");
            pieNumPerCargo.setShowDataLabels(true);
            
            distribucionNivelEscolaridad=new ArrayList<>();
            distribucionNivelEscolaridad.addAll(gestorEmpleado.cargarDistNivelEscolaridad(nitem, nitsubem));
            if(distribucionNivelEscolaridad.size()!=0){
                totalNescolar=distribucionNivelEscolaridad.get(0).getTotal();
            }
            
            pieNivelEscolaridad=new PieChartModel();
            for(int i=0;i<=distribucionNivelEscolaridad.size()-1;i++){                
                pieNivelEscolaridad.set(distribucionNivelEscolaridad.get(i).getDescDistribucion(), distribucionNivelEscolaridad.get(i).getCantidad());                
            }
            
            pieNivelEscolaridad.isShowDataLabels();
            pieNivelEscolaridad.setLegendPosition("w");   
            pieNivelEscolaridad.setDataFormat("value");
            pieNivelEscolaridad.setShowDataLabels(true);
            
            distribucionTendenciaVivienda=new ArrayList<>();
            distribucionTendenciaVivienda.addAll(gestorEmpleado.cargarDistTendenciaVivienda(nitem,nitsubem));
            
            if(distribucionTendenciaVivienda.size()!=0){
                totalTvivienda=distribucionTendenciaVivienda.get(0).getTotal();
            }
            
            pieTendenciaVivienda=new PieChartModel();
            for(int i=0;i<=distribucionTendenciaVivienda.size()-1;i++){                
                pieTendenciaVivienda.set(distribucionTendenciaVivienda.get(i).getDescDistribucion(), distribucionTendenciaVivienda.get(i).getCantidad());                
            }     
            
            pieTendenciaVivienda.isShowDataLabels();
            pieTendenciaVivienda.setLegendPosition("w");   
            pieTendenciaVivienda.setDataFormat("value");
            pieTendenciaVivienda.setShowDataLabels(true);
            
            distribucionUsoTiempoLibre=new ArrayList<>();
            distribucionUsoTiempoLibre.addAll(gestorEmpleado.cargarDistUsoTiempoLibre(nitem, nitsubem));
            
            if(distribucionUsoTiempoLibre.size()!=0){
                totalUsoTlibre=distribucionUsoTiempoLibre.get(0).getTotal();
            }
            
            pieUsoTiempoLibre=new PieChartModel();
            for(int i=0;i<=distribucionUsoTiempoLibre.size()-1;i++){                
                pieUsoTiempoLibre.set(distribucionUsoTiempoLibre.get(i).getDescDistribucion(), distribucionUsoTiempoLibre.get(i).getCantidad());                
            }     
            
            pieUsoTiempoLibre.isShowDataLabels();
            pieUsoTiempoLibre.setLegendPosition("w");   
            pieUsoTiempoLibre.setDataFormat("value");
            pieUsoTiempoLibre.setShowDataLabels(true);
            
            distribucionPromedioIngreso=new ArrayList<>();
            distribucionPromedioIngreso.addAll(gestorEmpleado.cargarDistPromedioIngreso(nitem,nitsubem));
            if(distribucionPromedioIngreso.size()!=0){
                totalPromedioIngreso=distribucionPromedioIngreso.get(0).getTotal();
            }
            
            piePromedioIngreso=new PieChartModel();
            for(int i=0;i<=distribucionPromedioIngreso.size()-1;i++){                
                piePromedioIngreso.set(distribucionPromedioIngreso.get(i).getDescDistribucion(), distribucionPromedioIngreso.get(i).getCantidad());                
            }     
            
            piePromedioIngreso.isShowDataLabels();
            piePromedioIngreso.setLegendPosition("w");   
            piePromedioIngreso.setDataFormat("value");
            piePromedioIngreso.setShowDataLabels(true);
            
            distribucionAntiguedadEmpresa=new ArrayList<>();
            distribucionAntiguedadEmpresa.addAll(gestorEmpleado.cargarDistAntiguedadEmpresa(nitem,nitsubem));
            
            if(distribucionAntiguedadEmpresa.size()!=0){
                totalAntiguedadEmpresa=distribucionAntiguedadEmpresa.get(0).getTotal();
            }
            
            pieAntiguedadEmpresa=new PieChartModel();
            for(int i=0;i<=distribucionAntiguedadEmpresa.size()-1;i++){                
                pieAntiguedadEmpresa.set(distribucionAntiguedadEmpresa.get(i).getDescDistribucion(), distribucionAntiguedadEmpresa.get(i).getCantidad());                
            }     
            
            pieAntiguedadEmpresa.isShowDataLabels();
            pieAntiguedadEmpresa.setLegendPosition("w");   
            pieAntiguedadEmpresa.setDataFormat("value");
            pieAntiguedadEmpresa.setShowDataLabels(true);
            
            distribucionAntiguedadCargo=new ArrayList<>();
            distribucionAntiguedadCargo.addAll(gestorEmpleado.cargarDistAntiguedadCargo(nitem,nitsubem));
            
            if(distribucionAntiguedadCargo.size()!=0){
                totalAntiguedadCargo=distribucionAntiguedadCargo.get(0).getTotal();
            }
            
            pieAntiguedadCargo=new PieChartModel();
            for(int i=0;i<=distribucionAntiguedadCargo.size()-1;i++){                
                pieAntiguedadCargo.set(distribucionAntiguedadCargo.get(i).getDescDistribucion(), distribucionAntiguedadCargo.get(i).getCantidad());                
            }     
            
            pieAntiguedadCargo.isShowDataLabels();
            pieAntiguedadCargo.setLegendPosition("w");   
            pieAntiguedadCargo.setDataFormat("value");
            pieAntiguedadCargo.setShowDataLabels(true);
            
            distribucionTipoContrato=new ArrayList<>();
            distribucionTipoContrato.addAll(gestorEmpleado.cargarDistTipoContrato(nitem,nitsubem));
            
            if(distribucionTipoContrato.size()!=0){
                totalTipoContrato=distribucionTipoContrato.get(0).getTotal();
            }
            
            pieTipoContrato=new PieChartModel();
            for(int i=0;i<=distribucionTipoContrato.size()-1;i++){                
                pieTipoContrato.set(distribucionTipoContrato.get(i).getDescDistribucion(), distribucionTipoContrato.get(i).getCantidad());                
            }     
            
            pieTipoContrato.isShowDataLabels();
            pieTipoContrato.setLegendPosition("w");   
            pieTipoContrato.setDataFormat("value");
            pieTipoContrato.setShowDataLabels(true);
            
            distribucionActivEmpresa=new ArrayList<>();
            distribucionActivEmpresa.addAll(gestorEmpleado.cargarDistActivEmpresa(nitem,nitsubem));
            
            
            if(distribucionActivEmpresa.size()!=0){
                totalActivEmpresa=distribucionActivEmpresa.get(0).getTotal();
            }
            
            pieActivEmpresa=new PieChartModel();
            for(int i=0;i<=distribucionActivEmpresa.size()-1;i++){                
                pieActivEmpresa.set(distribucionActivEmpresa.get(i).getDescDistribucion(), distribucionActivEmpresa.get(i).getCantidad());                
            }     
            
            pieActivEmpresa.isShowDataLabels();
            pieActivEmpresa.setLegendPosition("w");   
            pieActivEmpresa.setDataFormat("value");
            pieActivEmpresa.setShowDataLabels(true);
            
            distribucionFuma=new ArrayList<>();
            distribucionFuma.addAll(gestorEmpleado.cargarDistFuma(nitem,nitsubem));
            
            if(distribucionFuma.size()!=0){
                totalFuma=distribucionFuma.get(0).getTotal();
            }
            
            pieFuma=new PieChartModel();
            for(int i=0;i<=distribucionFuma.size()-1;i++){                
                pieFuma.set(distribucionFuma.get(i).getDescDistribucion(), distribucionFuma.get(i).getCantidad());                
            }
            pieFuma.isShowDataLabels();
            pieFuma.setLegendPosition("w");   
            pieFuma.setDataFormat("value");
            pieFuma.setShowDataLabels(true);
            
            distribucionDiagnosticadoEnfermedad=new ArrayList<>();
            distribucionDiagnosticadoEnfermedad.addAll(gestorEmpleado.cargarDistDiagnosticadoEnfermedad(nitem,nitsubem));
            
            if(distribucionDiagnosticadoEnfermedad.size()!=0){
                totalDiagnosticado=distribucionDiagnosticadoEnfermedad.get(0).getTotal();
            }
            
            pieDiagnosticadoEnfermedad=new PieChartModel();
            for(int i=0;i<=distribucionDiagnosticadoEnfermedad.size()-1;i++){                
                pieDiagnosticadoEnfermedad.set(distribucionDiagnosticadoEnfermedad.get(i).getDescDistribucion(), distribucionDiagnosticadoEnfermedad.get(i).getCantidad());                
            }
            pieDiagnosticadoEnfermedad.isShowDataLabels();
            pieDiagnosticadoEnfermedad.setLegendPosition("w");   
            pieDiagnosticadoEnfermedad.setDataFormat("value");
            pieDiagnosticadoEnfermedad.setShowDataLabels(true);
            
            
            
            distribucionConsumoAlcohol=new ArrayList<>();
            distribucionConsumoAlcohol.addAll(gestorEmpleado.cargarDistConsumoAlcohol(nitem,nitsubem));
            
            if(distribucionConsumoAlcohol.size()!=0){
                totalConsumoAlcohol=distribucionConsumoAlcohol.get(0).getTotal();
            }
            
            pieConsumoAlcohol=new PieChartModel();
            for(int i=0;i<=distribucionConsumoAlcohol.size()-1;i++){                
                pieConsumoAlcohol.set(distribucionConsumoAlcohol.get(i).getDescDistribucion(), distribucionConsumoAlcohol.get(i).getCantidad());                
            }
            pieConsumoAlcohol.isShowDataLabels();
            pieConsumoAlcohol.setLegendPosition("w");   
            pieConsumoAlcohol.setDataFormat("value");
            pieConsumoAlcohol.setShowDataLabels(true);
            
            distribucionPracticaDeporte=new ArrayList<>();
            distribucionPracticaDeporte.addAll(gestorEmpleado.cargarDistPracticaDeporte(nitem,nitsubem));
            
            if(distribucionPracticaDeporte.size()!=0){
                totalPracticaDeporte=distribucionPracticaDeporte.get(0).getTotal();
            }
            
            piePracticaDeporte=new PieChartModel();
            for(int i=0;i<=distribucionPracticaDeporte.size()-1;i++){                
                piePracticaDeporte.set(distribucionPracticaDeporte.get(i).getDescDistribucion(), distribucionPracticaDeporte.get(i).getCantidad());                
            }
            piePracticaDeporte.isShowDataLabels();
            piePracticaDeporte.setLegendPosition("w");   
            piePracticaDeporte.setDataFormat("value");
            piePracticaDeporte.setShowDataLabels(true);
            
            
            
            
        } catch (Exception e) {
            Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());            
        }
        
    }
    
    
    public void BuscarEmpleado() throws Exception {
    
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        empleado = gestorEmpleado.validarEmpleado(cedula,nitsesion);

        if(empleado == null){
            util.mostrarMensaje("La Cedula No Corresponde al Establecimiento.");
            
            empleado=new Empleado();
        }                
        
            
        
    }
    
    public void limpiarPerfilsocioDemografico(){
        empleado=new Empleado();
    }
    
    
    public void guardarPerfilSocioDemografico() throws Exception{
        Boolean invalido = false;        

        //ingreso de informacion al gestor
        gestorEmpleado = new GestorEmpleado();
        
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);

        try {
            //verificar que todas las cajas este llenas            
            
            if(empleado.getNescolar()==null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getNumPersonas()== null) {
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getTendenciaVivienda()== null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getUsoTiempoLibre() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getPromedioIngreso() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getAntiguedadEmpresa() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }
            if (empleado.getAntiguedadCargo() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }            
            if (empleado.getTipoContratacion() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }         
            if (empleado.getParticipaActividades() == null){
                invalido = true;
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }                   
            
            
            if (!invalido) {
                empleado.setCedula(cedula);
                Integer resultado = gestorEmpleado.guardarPerfilSocioDemografico(empleado,nitsesion);
                    empleado=new Empleado();
                if (resultado > 0) {
                    util.mostrarMensaje("!! El PERFIL fue creado de manera exitosa !!");
                    empleado = new Empleado();                    
                } else {
                    util.mostrarMensaje("!! El PERFIL no pudo ser almacenado !!");
                }
            }
            
        } catch (Exception e) {
            util.mostrarMensaje(e.getMessage());
        }
    }
    
    
    public void actualizarConsumoBebidas(){
        try {
            
            util.mostrarMensaje("i");
        } catch (Exception e) {
            util.mostrarMensaje(e.getMessage());
        }
    }
    
    public void modificarEmpleado() throws Exception{    

        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        //gestorAusentismo = new GestorAusentismo();               

        try {
            //verificar que todas las cajas este llenas
            if (empleado.getNombres() == null) {
                msg = "El nombre esta vacio!";
                invalido = true;                
            }
            if (empleado.getApellidos() == null){
                msg = "Los apellidos estan vacios!";
                invalido = true;
            }

            if (invalido == false) {
                    Integer resultado=gestorEmpleado.modificarEmpleado(empleado);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! Modificacion guardada !!");
                        empleado = new Empleado();                    
                    } else {
                        util.mostrarMensaje("!! La modificacion no pudo ser guardada !!");
                    }
            } else {
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
                }
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! La modificacion no pudo ser guardada !!");               
            }
    }    

    public List<Empleado> getListaEmpleadoAdmin() {
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();        

        try {
            listaEmpleadoAdmin = gestorEmpleado.listarEmpleadosAdmin();
        } catch (Exception ex) {
            Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return listaEmpleadoAdmin;
    }
    
    public void cargarPerfilSocioDemograficoList(){
        try {
            perfilSocioList=new ArrayList<>();
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitsub = (String) ef.createValueExpression(contextoEL, "#{listasBean.subempresa.nitsubempresa}", String.class).getValue(contextoEL);
            String nitem = (String) ef.createValueExpression(contextoEL, "#{listasBean.empresa.nitempresa}", String.class).getValue(contextoEL);              
            
            

            gestorEmpleado=new GestorEmpleado();
            perfilSocioList.addAll(gestorEmpleado.cargarPerfilSocioList(nitsub, nitem));

        } catch (Exception e) {
            Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<Empleado> getPerfilSocioList() {
        return perfilSocioList;
    }

    public void setPerfilSocioList(List<Empleado> perfilSocioList) {
        this.perfilSocioList = perfilSocioList;
    }

    public Integer getTotalConsumoAlcohol() {
        return totalConsumoAlcohol;
    }

    public Integer getTotalPracticaDeporte() {
        return totalPracticaDeporte;
    }

    public void setTotalPracticaDeporte(Integer totalPracticaDeporte) {
        this.totalPracticaDeporte = totalPracticaDeporte;
    }

    public List<Empleado> getDistribucionPracticaDeporte() {
        return distribucionPracticaDeporte;
    }

    public void setDistribucionPracticaDeporte(List<Empleado> distribucionPracticaDeporte) {
        this.distribucionPracticaDeporte = distribucionPracticaDeporte;
    }

    public PieChartModel getPiePracticaDeporte() {
        return piePracticaDeporte;
    }

    public void setPiePracticaDeporte(PieChartModel piePracticaDeporte) {
        this.piePracticaDeporte = piePracticaDeporte;
    }

    public void setTotalConsumoAlcohol(Integer totalConsumoAlcohol) {
        this.totalConsumoAlcohol = totalConsumoAlcohol;
    }

    public List<Empleado> getDistribucionConsumoAlcohol() {
        return distribucionConsumoAlcohol;
    }

    public void setDistribucionConsumoAlcohol(List<Empleado> distribucionConsumoAlcohol) {
        this.distribucionConsumoAlcohol = distribucionConsumoAlcohol;
    }

    public PieChartModel getPieConsumoAlcohol() {
        return pieConsumoAlcohol;
    }

    public void setPieConsumoAlcohol(PieChartModel pieConsumoAlcohol) {
        this.pieConsumoAlcohol = pieConsumoAlcohol;
    }

    public Integer getTotalDiagnosticado() {
        return totalDiagnosticado;
    }

    public void setTotalDiagnosticado(Integer totalDiagnosticado) {
        this.totalDiagnosticado = totalDiagnosticado;
    }

    public List<Empleado> getDistribucionDiagnosticadoEnfermedad() {
        return distribucionDiagnosticadoEnfermedad;
    }

    public void setDistribucionDiagnosticadoEnfermedad(List<Empleado> distribucionDiagnosticadoEnfermedad) {
        this.distribucionDiagnosticadoEnfermedad = distribucionDiagnosticadoEnfermedad;
    }

    public PieChartModel getPieDiagnosticadoEnfermedad() {
        return pieDiagnosticadoEnfermedad;
    }

    public void setPieDiagnosticadoEnfermedad(PieChartModel pieDiagnosticadoEnfermedad) {
        this.pieDiagnosticadoEnfermedad = pieDiagnosticadoEnfermedad;
    }

    public Integer getTotalFuma() {
        return totalFuma;
    }

    public void setTotalFuma(Integer totalFuma) {
        this.totalFuma = totalFuma;
    }

    public List<Empleado> getDistribucionFuma() {
        return distribucionFuma;
    }

    public void setDistribucionFuma(List<Empleado> distribucionFuma) {
        this.distribucionFuma = distribucionFuma;
    }

    public PieChartModel getPieFuma() {
        return pieFuma;
    }

    public void setPieFuma(PieChartModel pieFuma) {
        this.pieFuma = pieFuma;
    }

    public Integer getTotalActivEmpresa() {
        return totalActivEmpresa;
    }

    public void setTotalActivEmpresa(Integer totalActivEmpresa) {
        this.totalActivEmpresa = totalActivEmpresa;
    }

    public List<Empleado> getDistribucionActivEmpresa() {
        return distribucionActivEmpresa;
    }

    public void setDistribucionActivEmpresa(List<Empleado> distribucionActivEmpresa) {
        this.distribucionActivEmpresa = distribucionActivEmpresa;
    }

    public PieChartModel getPieActivEmpresa() {
        return pieActivEmpresa;
    }

    public void setPieActivEmpresa(PieChartModel pieActivEmpresa) {
        this.pieActivEmpresa = pieActivEmpresa;
    }

    public Integer getTotalTipoContrato() {
        return totalTipoContrato;
    }

    public void setTotalTipoContrato(Integer totalTipoContrato) {
        this.totalTipoContrato = totalTipoContrato;
    }

    public List<Empleado> getDistribucionTipoContrato() {
        return distribucionTipoContrato;
    }

    public void setDistribucionTipoContrato(List<Empleado> distribucionTipoContrato) {
        this.distribucionTipoContrato = distribucionTipoContrato;
    }

    public PieChartModel getPieTipoContrato() {
        return pieTipoContrato;
    }

    public void setPieTipoContrato(PieChartModel pieTipoContrato) {
        this.pieTipoContrato = pieTipoContrato;
    }

    public Integer getTotalAntiguedadCargo() {
        return totalAntiguedadCargo;
    }

    public void setTotalAntiguedadCargo(Integer totalAntiguedadCargo) {
        this.totalAntiguedadCargo = totalAntiguedadCargo;
    }

    public List<Empleado> getDistribucionAntiguedadCargo() {
        return distribucionAntiguedadCargo;
    }

    public void setDistribucionAntiguedadCargo(List<Empleado> distribucionAntiguedadCargo) {
        this.distribucionAntiguedadCargo = distribucionAntiguedadCargo;
    }

    public PieChartModel getPieAntiguedadCargo() {
        return pieAntiguedadCargo;
    }

    public void setPieAntiguedadCargo(PieChartModel pieAntiguedadCargo) {
        this.pieAntiguedadCargo = pieAntiguedadCargo;
    }

    public Integer getTotalAntiguedadEmpresa() {
        return totalAntiguedadEmpresa;
    }

    public void setTotalAntiguedadEmpresa(Integer totalAntiguedadEmpresa) {
        this.totalAntiguedadEmpresa = totalAntiguedadEmpresa;
    }

    public List<Empleado> getDistribucionAntiguedadEmpresa() {
        return distribucionAntiguedadEmpresa;
    }

    public void setDistribucionAntiguedadEmpresa(List<Empleado> distribucionAntiguedadEmpresa) {
        this.distribucionAntiguedadEmpresa = distribucionAntiguedadEmpresa;
    }

    public PieChartModel getPieAntiguedadEmpresa() {
        return pieAntiguedadEmpresa;
    }

    public void setPieAntiguedadEmpresa(PieChartModel pieAntiguedadEmpresa) {
        this.pieAntiguedadEmpresa = pieAntiguedadEmpresa;
    }

    public Integer getTotalPromedioIngreso() {
        return totalPromedioIngreso;
    }

    public void setTotalPromedioIngreso(Integer totalPromedioIngreso) {
        this.totalPromedioIngreso = totalPromedioIngreso;
    }

    public List<Empleado> getDistribucionPromedioIngreso() {
        return distribucionPromedioIngreso;
    }

    public void setDistribucionPromedioIngreso(List<Empleado> distribucionPromedioIngreso) {
        this.distribucionPromedioIngreso = distribucionPromedioIngreso;
    }

    public PieChartModel getPiePromedioIngreso() {
        return piePromedioIngreso;
    }

    public void setPiePromedioIngreso(PieChartModel piePromedioIngreso) {
        this.piePromedioIngreso = piePromedioIngreso;
    }

    public Integer getTotalUsoTlibre() {
        return totalUsoTlibre;
    }

    public void setTotalUsoTlibre(Integer totalUsoTlibre) {
        this.totalUsoTlibre = totalUsoTlibre;
    }

    public List<Empleado> getDistribucionUsoTiempoLibre() {
        return distribucionUsoTiempoLibre;
    }

    public void setDistribucionUsoTiempoLibre(List<Empleado> distribucionUsoTiempoLibre) {
        this.distribucionUsoTiempoLibre = distribucionUsoTiempoLibre;
    }

    public PieChartModel getPieUsoTiempoLibre() {
        return pieUsoTiempoLibre;
    }

    public void setPieUsoTiempoLibre(PieChartModel pieUsoTiempoLibre) {
        this.pieUsoTiempoLibre = pieUsoTiempoLibre;
    }

    public Integer getTotalTvivienda() {
        return totalTvivienda;
    }

    public void setTotalTvivienda(Integer totalTvivienda) {
        this.totalTvivienda = totalTvivienda;
    }

    public List<Empleado> getDistribucionTendenciaVivienda() {
        return distribucionTendenciaVivienda;
    }

    public void setDistribucionTendenciaVivienda(List<Empleado> distribucionTendenciaVivienda) {
        this.distribucionTendenciaVivienda = distribucionTendenciaVivienda;
    }

    public PieChartModel getPieTendenciaVivienda() {
        return pieTendenciaVivienda;
    }

    public void setPieTendenciaVivienda(PieChartModel pieTendenciaVivienda) {
        this.pieTendenciaVivienda = pieTendenciaVivienda;
    }

    public Integer getTotalCargos() {
        return totalCargos;
    }

    public void setTotalCargos(Integer totalCargos) {
        this.totalCargos = totalCargos;
    }

    public Integer getTotalNescolar() {
        return totalNescolar;
    }

    public void setTotalNescolar(Integer totalNescolar) {
        this.totalNescolar = totalNescolar;
    }

    public List<Empleado> getDistribucionNivelEscolaridad() {
        return distribucionNivelEscolaridad;
    }

    public void setDistribucionNivelEscolaridad(List<Empleado> distribucionNivelEscolaridad) {
        this.distribucionNivelEscolaridad = distribucionNivelEscolaridad;
    }

    public PieChartModel getPieNivelEscolaridad() {
        return pieNivelEscolaridad;
    }

    public void setPieNivelEscolaridad(PieChartModel pieNivelEscolaridad) {
        this.pieNivelEscolaridad = pieNivelEscolaridad;
    }

    public List<Empleado> getDistribucionNumPerCargo() {
        return distribucionNumPerCargo;
    }

    public void setDistribucionNumPerCargo(List<Empleado> distribucionNumPerCargo) {
        this.distribucionNumPerCargo = distribucionNumPerCargo;
    }

    public PieChartModel getPieNumPerCargo() {
        return pieNumPerCargo;
    }

    public void setPieNumPerCargo(PieChartModel pieNumPerCargo) {
        this.pieNumPerCargo = pieNumPerCargo;
    }

    public List<Empleado> getDistribucionSexo() {
        return distribucionSexo;
    }

    public void setDistribucionSexo(List<Empleado> distribucionSexo) {
        this.distribucionSexo = distribucionSexo;
    }

    public PieChartModel getPieSexo() {
        return pieSexo;
    }

    public void setPieSexo(PieChartModel pieSexo) {
        this.pieSexo = pieSexo;
    }

    public List<Empleado> getDistribucionECivil() {
        return distribucionECivil;
    }

    public void setDistribucionECivil(List<Empleado> distribucionECivil) {
        this.distribucionECivil = distribucionECivil;
    }

    public PieChartModel getPieECivil() {
        return pieECivil;
    }

    public void setPieECivil(PieChartModel pieECivil) {
        this.pieECivil = pieECivil;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public PieChartModel getPieEdades() {
        return pieEdades;
    }

    public void setPieEdades(PieChartModel pieEdades) {
        this.pieEdades = pieEdades;
    }

    public Mes getMes() {
        return mes;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public Año getAno() {
        return ano;
    }

    public void setAno(Año ano) {
        this.ano = ano;
    }

    public Boolean getTodos() {
        return todos;
    }

    public void setTodos(Boolean todos) {
        this.todos = todos;
    }

    public List<Empleado> getDistribucionEdad() {
        return distribucionEdad;
    }

    public void setDistribucionEdad(List<Empleado> distribucionEdad) {
        this.distribucionEdad = distribucionEdad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setListaEmpleadoAdmin(List<Empleado> listaEmpleadoAdmin) {
        this.listaEmpleadoAdmin = listaEmpleadoAdmin;
    }    
    
    public void limpiarEmpleado() { 
        empleado = new Empleado();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }     

    public List<Empleado> getFilteredlistaEmpleado() {
        return filteredlistaEmpleado;
    }

    public void setFilteredlistaEmpleado(List<Empleado> filteredlistaEmpleado) {
        this.filteredlistaEmpleado = filteredlistaEmpleado;
    }
    
}

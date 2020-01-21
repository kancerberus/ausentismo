/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorAccidente;
import controlador.GestorAusentismo;
import controlador.GestorEmpleado;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import modelo.Ausentismo;
import modelo.Empleado;
import modelo.Motivo;
import util.Utilidades;
import util.UtilJSF;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.Accidente;
import modelo.Año;
import modelo.Incapacidad;
import modelo.Mes;
import modelo.SubEmpresa;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;


/**
 *
 * @author Andres
 */
public class UIAusentismo implements Serializable {

    private String cedula;
    private Empleado empleado;
    private Ausentismo ausentismo; 
    private Mes mes;
    private Año ano;
    private Motivo motivo;
    private SubEmpresa subempresa;
    private Integer[] selectedMotivos=new Integer[13];
    
    
    DecimalFormat formato1 = new DecimalFormat("#,###.00");   
    private float totarl=0;
    private float toteps=0;
    private float totempleador=0;
    private float tottrabajador=0;
    private float total=0;
    private float totalhs=0;
    private float totaleps=0;    
    private float totalarl=0;
    private float totaltrabajador=0;
    private float totalem=0;    
    private float totalsubt=0;
    private String anoActualizador;
    private float totCasos;
    private float totDiasIncapacidad;
    private Integer totCasosGenero;
    private Integer totCasosTipoIncapacidad;
    
    Boolean todos;
    Boolean selec;
    public Utilidades util = new Utilidades();
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory reg;
    private GestorAusentismo gestorAusentismo;
    private GestorAccidente gestorAccidente;
    private ArrayList<SelectItem> itemsMotivos = new ArrayList<>();
    private List<Ausentismo> listaAusentismo;
    private List<Ausentismo> listaAusentismoanomes;
    private List<Ausentismo> listaausentismoEmpresa;
    private List<Ausentismo> listAusentismoEmpleado;
    private List<Ausentismo> listAusentismoEmpleadoTotales;
    private List<Ausentismo> pieausentismoEmpresa;
    private List<Ausentismo> pieausentismoEmpleado;
    private String nitsubempresa="";
    
    
    private List<Ausentismo> distribucionPorOrigen=new ArrayList<>();
    private ArrayList<Incapacidad> distribucionGrupoDiagnostico=new ArrayList<>();    
    private PieChartModel piePorOrigen=new PieChartModel();    
    private PieChartModel piePorDias=new PieChartModel();
    private PieChartModel pieGenero=new PieChartModel();
    private PieChartModel pieTipoIncapacidad=new PieChartModel();
    private HorizontalBarChartModel horizontalBarGrupoDiagnostico=new HorizontalBarChartModel();
    private HorizontalBarChartModel horizontalBarCargos=new HorizontalBarChartModel();
    private HorizontalBarChartModel horizontalBarCentrosTrabajo=new HorizontalBarChartModel();
    
    private List<Ausentismo> distribucionAuLaboralGenero=new ArrayList<>();
    private List<Ausentismo> distribucionTipoIncapacidad=new ArrayList<>();
    private List<Ausentismo> distribucionCargos=new ArrayList<>();
    private List<Ausentismo> distribucionPorCentroTrabajo=new ArrayList<>();
    
    private List<Ausentismo> pieporSubempresa;
    private List<Ausentismo> filteredlistaAusentismo;
    private PieChartModel pieausentismoanomesEmpresa;
    private PieChartModel pieausentismoanomesEmpleado;
    
    private PieChartModel pieSubempresa;
    private ExpressionFactory ef;    
    private String minutos;

    public UIAusentismo()  throws Exception {
       contextoJSF = FacesContext.getCurrentInstance();
       contextoEL = contextoJSF.getELContext(); 
       reg = contextoJSF.getApplication().getExpressionFactory();
       empleado = new Empleado();
       ausentismo = new Ausentismo();
       mes = new Mes();
       ano = new Año();
       gestorAusentismo = new GestorAusentismo();
       pieausentismoanomesEmpresa = new PieChartModel();
       pieSubempresa = new PieChartModel();
       pieausentismoanomesEmpleado = new PieChartModel();       
       subempresa=new SubEmpresa();
    }
    


    public void BuscarEmpleado() throws Exception {
    
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        Empleado em = gestorEmpleado.validarEmpleado(cedula,nitsesion);
        Integer thacum = gestorAusentismo.horasanoAcumuladas(cedula);        
        
        if(em!=null && em.getEstado()== true){
           ausentismo.setEmpleado(em);
           ausentismo.getEmpleado().setThacum(thacum);        
        }else {
            Empleado encontradoEn=gestorEmpleado.buscarempleadoAdmin(cedula);        
        
            if(encontradoEn != null){        
                util.mostrarMensaje("El funcionario no pertenece a este centro de trabajo....");            
            }else{
                util.mostrarMensaje("La cedula no Existe.");                
                empleado=new Empleado();
            }                
        
            
        }
    }
    
    public void buscarEActualizacion() throws Exception {
    
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        Integer resultado = gestorEmpleado.validarEmpleadoActualizacion(cedula,nitsesion,anoActualizador); 
        
        //mensaje si resultado viene con 0 error en la consulta

    }
    
    public void buscarEActualizacionEPS() throws Exception {
    
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        Integer resultado = gestorEmpleado.validarEmpleadoActualizacionEPS();
        //mensaje si resultado viene con 0 error en la consulta

    }
    

        
    public void buscarAusentismo() throws Exception {
    
        GestorAusentismo gestorAusentismo = new GestorAusentismo();
        String cod_registro = ausentismo.getCod_registro();

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        
        ausentismo = gestorAusentismo.buscarAusentismo(cod_registro, nitsesion); 
        
        if(ausentismo == null){                                                
            util.mostrarMensaje("El registro no existe o se encuentra cerrado");
            ausentismo = new Ausentismo();
        }        
    }
    
    public void dialogoIncapacidad(){        
        if(ausentismo.getMotivoausentismo().equals("2") || ausentismo.getMotivoausentismo().equals("1")){
            RequestContext req= RequestContext.getCurrentInstance();
            req.execute("PF('dlg2').show();");
        }
        
    }

    public void guardarRegistro() throws Exception{    

        Boolean invalido = false;
        String msg = null;
        

        //ingreso de informacion al gestor
        gestorAusentismo = new GestorAusentismo();
        try {
            
            String fechaActualizado = gestorAusentismo.cargarFechaActualizadoSalario();
            
            Calendar fecha = Calendar.getInstance();

            
            int año=fecha.get(Calendar.YEAR);
            String añohoy=String.valueOf(año);
            if(fechaActualizado.equals(añohoy)){                
            
            //verificar que todas las cajas este llenas           
            if (ausentismo.getEmpleado().getCedula() == null) {
                msg = "La cédula esta vacía!";
                invalido = true;              
            }
            if (ausentismo.getMotivoausentismo().equals("Seleccione...")) {
                msg = "Seleccione un Motivo";
                invalido = true;
            }            

            if (invalido == false) {                
                    float min=0;
                    min=Float.parseFloat(minutos);
                    float horas= Float.parseFloat(ausentismo.getTiempo_horas());
                    horas+= min;
                    ausentismo.setTiempo_horas(Float.toString(horas));
                    Integer resultado = gestorAusentismo.guardarAusentismo(ausentismo);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! El registro fue realizado de manera exitosa !!");
                        ausentismo = new Ausentismo();                    
                    } else {
                        util.mostrarMensaje("!! El registro no pudo ser almacenado !!");
                    }
            } else {
                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
                }
            
            }else{
                    util.mostrarMensaje("Actualice Salario Basico del Año.");                
            }
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! El registro no pudo ser almacenado !!");               
            }
    }
    
    public void eliminarRegisroAusentismo() throws Exception{
        
        gestorAusentismo=new GestorAusentismo();
        
        try {
            
                ausentismo= (Ausentismo) UtilJSF.getBean("itemAusentismo");
            
                Integer resultado=gestorAusentismo.eliminarRegistro(ausentismo.getCod_registro());

                if (resultado > 0) {
                    util.mostrarMensaje("!! Se elimino el registro correctamente.");                        
                }else{
                    util.mostrarMensaje("!! No se pudo eliminar.");                
                }            
            
        } catch (Exception e) {
            Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    public void modificarAusentismo() throws Exception{    

        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        gestorAusentismo = new GestorAusentismo();               

        try {
            //verificar que todas las cajas este llenas
            if (ausentismo.getEmpleado().getCedula() == null) {
                msg = "La cédula esta vacía!";
                invalido = true;                
            }
            if (ausentismo.getMotivoausentismo().equals("Seleccione...")) {
                msg = "Seleccione un Motivo";
                invalido = true;
            }

            if (invalido == false) {
                    Integer resultado = gestorAusentismo.modificarAusentismo(ausentismo);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! El registro ha sido cerrado !!");
                        ausentismo = new Ausentismo();                    
                    } else {
                        util.mostrarMensaje("!! El registro no pudo ser almacenado !!");
                    }
            } else {
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
                }
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! El registro no pudo ser almacenado !!");               
            }
    }
    
    public void limpiarAusentismo() {       
        ausentismo = new Ausentismo(); 
        empleado = new Empleado();        
        itemsMotivos.clear();
    }
    
    public List<Ausentismo> getListaAusentismo() {

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);          

        try {
            listaAusentismo = gestorAusentismo.listarAusentismos(nitsesion);
        } catch (Exception ex) {
            Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return (listaAusentismo);
    }
  
    
    public void ausentismoAnomes() { 
        
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);
        
        //revisar checkbox
        String selmesano = null;
        String selano = null;
        
        if(todos == true){
           selano = ano.getAño();   
        }else{            
           selmesano = (ano.getAño())+"/"+(mes.getCodigo());            
        }

        try {            
            listaAusentismoanomes = gestorAusentismo.ausentismoAnomes(nitsesion,selmesano,selano);
            setListaAusentismoanomes(listaAusentismoanomes);                                  
            totarl=0; toteps=0; totempleador=0; tottrabajador=0; total=0;
            
            
            //Carga totales ausentismo ano y mes 
            for(int i=0; i < listaAusentismoanomes.size(); i++){
                totarl +=  listaAusentismoanomes.get(i).getArl();                
                toteps += listaAusentismoanomes.get(i).getEps();
                totempleador += listaAusentismoanomes.get(i).getEmpleador();
                tottrabajador += listaAusentismoanomes.get(i).getTrabajador();
                total += listaAusentismoanomes.get(i).getTotal();                               
            }   

            
        } catch (Exception ex) {
            Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    
    public void ausentismoAnomesEmpleadoTotales() {
        listAusentismoEmpleadoTotales=new ArrayList<>();
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitem = nitsubempresa;
        if(nitem.equals("")){
            nitem=subempresa.getNitsubempresa();
        }
        //revisar checkbox
        String selmesdesde = null;
        String selmeshasta = null;
        String selano = null;         
        
        
            
        if(todos == true){
            selano = ano.getAño();   
        }else{
           selmesdesde = (ano.getAño())+"/"+(mes.getDesde());
           selmeshasta = (ano.getAño()+"/"+(mes.getHasta()));           
        }        
        
        String motivos="";
        try {
            
            if(selectedMotivos.length == 13 || selectedMotivos.length==0){
                    motivos="'1','2','3','4','5','6','7','8','9','10','11','12','13',";
                    selectedMotivos=new Integer[13];
            }else{
                for(int i=0; i<selectedMotivos.length;i++){                
                    motivos+="'"+selectedMotivos[i]+"',";                    
                }
                selectedMotivos=new Integer[13];
                    
            }
            
            listAusentismoEmpleadoTotales = gestorAusentismo.ausentismoanomesEmpleadoTotales(cedula, nitem, selmesdesde, selmeshasta, selano, motivos);            
            //pieausentismoEmpleado = gestorAusentismo.pieausentismoanomesEmpleado(cedula, nitem, selmesdesde, selmeshasta, selano, selmotivo);
            setListAusentismoEmpleadoTotales(listAusentismoEmpleadoTotales);
            
            totaleps=0;totalarl=0;totalem=0; totaltrabajador=0; totalsubt=0; totalhs=0;
            //calculo de totales eps,arl,empleador,trabajador y total 
            
            
            for(int i=0; i < listAusentismoEmpleadoTotales.size(); i++){
                totaleps += listAusentismoEmpleadoTotales.get(i).getEps();
                totalarl += listAusentismoEmpleadoTotales.get(i).getArl();
                totalem += listAusentismoEmpleadoTotales.get(i).getEmpleador();
                totaltrabajador += listAusentismoEmpleadoTotales.get(i).getTrabajador();
                totalsubt += listAusentismoEmpleadoTotales.get(i).getTotalsube();
                totalhs+=listAusentismoEmpleadoTotales.get(i).getEmpleado().getThacum();
                
            }
    
        } catch (Exception ex) {
            Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    //muestra reporte de ausentismos por cedula de empleado
    public void ausentismoAnomesEmpleado() {
        
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitem = (String) ef.createValueExpression(contextoEL, "#{listasBean.empresa.nitempresa}", String.class).getValue(contextoEL);          
        //revisar checkbox
        String selmesdesde = null;
        String selmeshasta = null;
        String selano = null; 
        String selmotivo=null;
        
            
        if(todos == true){
            selano = ano.getAño();   
        }else{
           selmesdesde = (ano.getAño())+"/"+(mes.getDesde());
           selmeshasta = (ano.getAño()+"/"+(mes.getHasta()));           
        }        
        selmotivo = ausentismo.getMotivoausentismo();
        
        try {       

            listAusentismoEmpleado = gestorAusentismo.ausentismoanomesEmpleado(cedula, nitem, selmesdesde, selmeshasta, selano, selmotivo);
            pieausentismoEmpleado = gestorAusentismo.pieausentismoanomesEmpleado(cedula, nitem, selmesdesde, selmeshasta, selano, selmotivo);
            setListAusentismoEmpleado(listAusentismoEmpleado);
            
            totaleps=0;totalarl=0;totalem=0; totaltrabajador=0; totalsubt=0; totalhs=0;float ths=0.0f;
            //calculo de totales eps,arl,empleador,trabajador y total 
            
            
            for(int i=0; i < listAusentismoEmpleado.size(); i++){
                totaleps += listAusentismoEmpleado.get(i).getEps();
                totalarl += listAusentismoEmpleado.get(i).getArl();
                totalem += listAusentismoEmpleado.get(i).getEmpleador();
                totaltrabajador += listAusentismoEmpleado.get(i).getTrabajador();
                totalsubt += listAusentismoEmpleado.get(i).getTotalsube();                
                ths=Float.parseFloat(listAusentismoEmpleado.get(i).getTiempo_horas());                
                totalhs += ths;
            }
            
            
            
            //Carga Pie Totales Ausentismo Vs Tipo
            pieausentismoanomesEmpleado.clear();
            pieausentismoanomesEmpleado.set("EPS", pieausentismoEmpleado.get(0).getEps());
            pieausentismoanomesEmpleado.set("ARL", pieausentismoEmpleado.get(0).getArl());
            pieausentismoanomesEmpleado.set("TRABAJADOR", pieausentismoEmpleado.get(0).getTrabajador());
            pieausentismoanomesEmpleado.set("EMPLEADOR", pieausentismoEmpleado.get(0).getEmpleador());
            pieausentismoanomesEmpleado.setTitle(pieausentismoEmpleado.get(0).getEmpleado().getNombres()+" "+ pieausentismoEmpleado.get(0).getEmpleado().getApellidos()+"   TOTAL AUSENTISMOS ");
            pieausentismoanomesEmpleado.setLegendPosition("w");
            pieausentismoanomesEmpleado.setShowDataLabels(true);
            pieausentismoanomesEmpleado.setDiameter(300);
            
    
        } catch (Exception ex) {
            Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    
    public void ausentismoAnomesEmpresa() { 
        
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitem = (String) ef.createValueExpression(contextoEL, "#{listasBean.empresa.nitempresa}", String.class).getValue(contextoEL);  
        String nitsubem = (String) ef.createValueExpression(contextoEL, "#{listasBean.subempresa.nitsubempresa}", String.class).getValue(contextoEL);  
        //revisar checkbox
        String selmesdesde = null;
        String selmeshasta = null;
        String selano = null; 
        DecimalFormat formato2 = new DecimalFormat("#.##");
        String selmotivo=null;
            
        if(todos == true){
            selano = ano.getAño();   
        }else{
           selmesdesde = (ano.getAño())+"/"+(mes.getDesde());
           selmeshasta = (ano.getAño()+"/"+(mes.getHasta()));           
        }        
        if(nitsubem == ""){
         nitsubem = null;         
        }
        
        selmotivo = ausentismo.getMotivoausentismo();
        
        try {       

            listaausentismoEmpresa = gestorAusentismo.ausentismoanomesEmpresa(nitem, nitsubem, selmesdesde, selmeshasta, selano, selmotivo);
            pieausentismoEmpresa = gestorAusentismo.pieausentismoanomesEmpresa(nitem, nitsubem, selmesdesde, selmeshasta, selano, selmotivo);
            pieporSubempresa = gestorAusentismo.pieporSubempresa(nitem, nitsubem, selmesdesde, selmeshasta, selano, selmotivo);
            setListaausentismoEmpresa(listaausentismoEmpresa); 
            
            
            
            totaleps=0;totalarl=0;totalem=0; totaltrabajador=0; totalsubt=0;
            
            for(int i=0; i < listaausentismoEmpresa.size(); i++){                
                totaleps += listaausentismoEmpresa.get(i).getEps();
                totalarl += listaausentismoEmpresa.get(i).getArl();
                totalem += listaausentismoEmpresa.get(i).getEmpleador();
                totaltrabajador += listaausentismoEmpresa.get(i).getTrabajador();
                totalsubt += listaausentismoEmpresa.get(i).getTotalsube();                
                
            }            
            
            //Carga Pie Totales Ausentismo Vs Tipo
            pieausentismoanomesEmpresa.clear();            
            pieausentismoanomesEmpresa.set("EPS", pieausentismoEmpresa.get(0).getEps());
            pieausentismoanomesEmpresa.set("ARL", pieausentismoEmpresa.get(0).getArl());
            pieausentismoanomesEmpresa.set("TRABAJADOR", pieausentismoEmpresa.get(0).getTrabajador());
            pieausentismoanomesEmpresa.set("EMPLEADOR", pieausentismoEmpresa.get(0).getEmpleador());
            pieausentismoanomesEmpresa.setTitle(pieausentismoEmpresa.get(0).getEmpresa().getNombre()+"   TOTALES AUSENTISMO VS TIPO");
            pieausentismoanomesEmpresa.setLegendPosition("w");
            pieausentismoanomesEmpresa.setShowDataLabels(true);
            pieausentismoanomesEmpresa.setDiameter(300);
            
            
            //Carga Totales Ausentismo vs Subemrpesa
                pieSubempresa.clear();
                for(int i=0; i < pieporSubempresa.size(); i++){    

                    pieSubempresa.setTitle(pieporSubempresa.get(i).getEmpresa().getNombre()+"   TOTALES AUSENTISMO VS SUBEMPRESA");
                    pieSubempresa.set(pieporSubempresa.get(i).getSubempresa().getNombre(),pieporSubempresa.get(i).getTotalsube());
                    pieSubempresa.setDiameter(300);
                    pieSubempresa.setLegendPosition("w");
                    pieSubempresa.setShowDataLabels(true);
                }
            
            
        } catch (Exception ex) {
            Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    public ArrayList<SelectItem> getItemsMotivos() throws Exception{
            
            try {
                gestorAusentismo = new GestorAusentismo();
                ArrayList<Motivo> listaMotivos;
                itemsMotivos.clear();
                listaMotivos = gestorAusentismo.listarMotivos();                                
                
                for (int i = 0; i < listaMotivos.size(); i++) {                    
                        itemsMotivos.add(new SelectItem(listaMotivos.get(i).getCodigo(), listaMotivos.get(i).getNombrem(), listaMotivos.get(i).getTipo()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsMotivos;    
    }
    
    public void indicadoresAusentismo(){
        try {
            
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitem = (String) ef.createValueExpression(contextoEL, "#{listasBean.empresa.nitempresa}", String.class).getValue(contextoEL);  
            String nitsubem = (String) ef.createValueExpression(contextoEL, "#{listasBean.subempresa.nitsubempresa}", String.class).getValue(contextoEL);  
            //revisar checkbox
            String selmesdesde = null;
            String selmeshasta = null;
            String selano = null; 
            totCasosGenero=0;
            totCasosTipoIncapacidad=0;
            
            if(todos == true){
                selano = ano.getAño();   
            }else{
               selmesdesde = (ano.getAño())+"/"+(mes.getDesde());
               selmeshasta = (ano.getAño()+"/"+(mes.getHasta()));           
            }        
            if(nitsubem == ""){
                nitsubem = null;
            }
            
            gestorAccidente=new GestorAccidente();
            gestorAusentismo=new GestorAusentismo();
            distribucionPorOrigen=new ArrayList<>();
            
            
            distribucionPorOrigen.addAll(gestorAusentismo.cargarDistribucionPorOrigen(nitem, nitsubem,selmesdesde,selmeshasta,selano));
            
            for(int i=0;i<distribucionPorOrigen.size();i++){
                totCasos += distribucionPorOrigen.get(i).getCasos();
                totDiasIncapacidad += distribucionPorOrigen.get(i).getDiasIncapacidad();
            }
            
            piePorOrigen=new PieChartModel();
            for(int i=0;i<=distribucionPorOrigen.size()-1;i++){                
                piePorOrigen.set(distribucionPorOrigen.get(i).getMotivo().getNombrem(), distribucionPorOrigen.get(i).getCasos());
            }
            piePorOrigen.isShowDataLabels();
            piePorOrigen.setLegendPosition("w"); 
            piePorOrigen.setShowDataLabels(true);
            piePorOrigen.setDataFormat("value");
            
            piePorDias=new PieChartModel();
            for(int i=0;i<=distribucionPorOrigen.size()-1;i++){                
                piePorDias.set(distribucionPorOrigen.get(i).getMotivo().getNombrem(), distribucionPorOrigen.get(i).getDiasIncapacidad());
            }
            piePorDias.isShowDataLabels();
            piePorDias.setLegendPosition("e"); 
            piePorDias.setShowDataLabels(true);
            piePorDias.setDataFormat("value");
            
            
            distribucionAuLaboralGenero=new ArrayList<>();
            distribucionAuLaboralGenero.addAll(gestorAusentismo.cargarDistribucionLabora(nitem, nitsubem,selmesdesde,selmeshasta,selano));
            
            pieGenero=new PieChartModel();
            for(int i=0;i<distribucionAuLaboralGenero.size();i++){
                totCasosGenero+=distribucionAuLaboralGenero.get(i).getCasos();
                pieGenero.set(distribucionAuLaboralGenero.get(i).getSexo().getNombre(), distribucionAuLaboralGenero.get(i).getCasos());
            }
            
            pieGenero.isShowDataLabels();
            pieGenero.setLegendPosition("w");
            pieGenero.setShowDataLabels(true);
            pieGenero.setDataFormat("value");
            
            distribucionAuLaboralGenero.get(0).setPorcentaje((distribucionAuLaboralGenero.get(0).getCasos().floatValue()/totCasosGenero.floatValue())*100);
            distribucionAuLaboralGenero.get(1).setPorcentaje((distribucionAuLaboralGenero.get(1).getCasos().floatValue()/totCasosGenero.floatValue())*100);
            
            distribucionTipoIncapacidad=new ArrayList<>();
            distribucionTipoIncapacidad.addAll(gestorAusentismo.cargarDistribucionTipoIncapacidad(nitem, nitsubem,selmesdesde,selmeshasta,selano));
            
            pieTipoIncapacidad=new PieChartModel();
            for(int i=0;i<distribucionTipoIncapacidad.size();i++){
                totCasosTipoIncapacidad+=distribucionTipoIncapacidad.get(i).getCasos();
                pieTipoIncapacidad.set(distribucionTipoIncapacidad.get(i).getTipoIncapacidad().getNombre(), distribucionTipoIncapacidad.get(i).getCasos());
            }
            
            pieTipoIncapacidad.isShowDataLabels();
            pieTipoIncapacidad.setLegendPosition("w");
            pieTipoIncapacidad.setShowDataLabels(true);
            pieTipoIncapacidad.setDataFormat("value");
            
            distribucionGrupoDiagnostico=new ArrayList<>();
            distribucionGrupoDiagnostico.addAll(gestorAusentismo.cargarDistribucionGrupoDiagnostico(nitem, nitsubem,selmesdesde,selmeshasta,selano));
        
            horizontalBarGrupoDiagnostico=new HorizontalBarChartModel();
            
            for(int i=0;i<=distribucionGrupoDiagnostico.size()-1;i++){
                ChartSeries serie=new ChartSeries();                                
                serie.setLabel(distribucionGrupoDiagnostico.get(i).getGrupoCie10().getNombre());
                serie.set(distribucionGrupoDiagnostico.get(i).getGrupoCie10().getNombre(), distribucionGrupoDiagnostico.get(i).getCasos());                
                horizontalBarGrupoDiagnostico.addSeries(serie);
            }                        
            
            horizontalBarGrupoDiagnostico.setLegendPosition("ne");
            horizontalBarGrupoDiagnostico.setAnimate(true);
            horizontalBarGrupoDiagnostico.setDatatipFormat("%.0f");            
            horizontalBarGrupoDiagnostico.setBarWidth(10);
            horizontalBarGrupoDiagnostico.setShowPointLabels(true);
            
            
            Axis xAxis = horizontalBarGrupoDiagnostico.getAxis(AxisType.X);
            xAxis.setLabel("Cantidad");
            xAxis.setMin(0);
            xAxis.setMax(50);
            xAxis.setTickInterval("5");

            Axis yAxis = horizontalBarGrupoDiagnostico.getAxis(AxisType.Y);
            yAxis.setLabel("Grupo Diagnostico");   
            
            
            distribucionCargos=new ArrayList<>();
            distribucionCargos.addAll(gestorAusentismo.cargarDistribucionCargos(nitem,nitsubem,selmesdesde, selmeshasta,selano));
            
            horizontalBarCargos=new HorizontalBarChartModel();
            
            for(int i=0;i<=distribucionCargos.size()-1;i++){                
                ChartSeries serie=new ChartSeries();                                
                serie.setLabel(distribucionCargos.get(i).getCargos().getNombre());
                serie.set(distribucionCargos.get(i).getCargos().getNombre(), distribucionCargos.get(i).getTotCargos());
                horizontalBarCargos.addSeries(serie);                      
            }
            
            horizontalBarCargos.setTitle("Distribucion Por Cargos");
            horizontalBarCargos.setLegendPosition("se");
            horizontalBarCargos.setAnimate(true);
            horizontalBarCargos.setDatatipFormat("%.0f");            
            horizontalBarCargos.setBarWidth(20);
            horizontalBarCargos.setShowPointLabels(true);
            
            Axis xAxisC = horizontalBarCargos.getAxis(AxisType.X);
            xAxisC.setLabel("Cantidad");
            xAxisC.setMin(0);
            xAxisC.setMax(300);
            xAxisC.setTickInterval("50");

            Axis yAxisC= horizontalBarCargos.getAxis(AxisType.Y);
            yAxisC.setLabel("Cargo");
            
            
            distribucionPorCentroTrabajo=new ArrayList<>();
            horizontalBarCentrosTrabajo=new HorizontalBarChartModel();
            if(nitsubem==null){
                distribucionPorCentroTrabajo.addAll(gestorAusentismo.cargarDistribucionPorCentroTrabajo(nitem, selmesdesde, selmeshasta, selano));



                for(int i=0;i<=distribucionPorCentroTrabajo.size()-1;i++){                
                    ChartSeries serie=new ChartSeries();                                
                    serie.setLabel(distribucionPorCentroTrabajo.get(i).getSubempresa().getNombre());
                    serie.set(distribucionPorCentroTrabajo.get(i).getSubempresa().getNombre(), distribucionPorCentroTrabajo.get(i).getCasos());
                    horizontalBarCentrosTrabajo.addSeries(serie);                      
                }

                horizontalBarCentrosTrabajo.setTitle("Distribucion Por Centro Trabajo");
                horizontalBarCentrosTrabajo.setLegendPosition("ne");
                horizontalBarCentrosTrabajo.setAnimate(true);
                horizontalBarCentrosTrabajo.setDatatipFormat("%.0f");            
                horizontalBarCentrosTrabajo.setBarWidth(20);
                horizontalBarCentrosTrabajo.setShowPointLabels(true);

                Axis xAxisCe = horizontalBarCentrosTrabajo.getAxis(AxisType.X);
                xAxisCe.setLabel("Cantidad");
                xAxisCe.setMin(0);
                xAxisCe.setMax(800);
                xAxisCe.setTickInterval("40");

                Axis yAxisCe=horizontalBarCentrosTrabajo.getAxis(AxisType.Y);
                yAxisCe.setLabel("Centro Trabajo");
            }
            
            
        } catch (Exception e) {
            Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String getNitsubempresa() {
        return nitsubempresa;
    }

    public void setNitsubempresa(String nitsubempresa) {
        this.nitsubempresa = nitsubempresa;
    }

    public Integer[] getSelectedMotivos() {
        return selectedMotivos;
    }

    public void setSelectedMotivos(Integer[] selectedMotivos) {
        this.selectedMotivos = selectedMotivos;
    }

    public HorizontalBarChartModel getHorizontalBarCentrosTrabajo() {
        return horizontalBarCentrosTrabajo;
    }

    public void setHorizontalBarCentrosTrabajo(HorizontalBarChartModel horizontalBarCentrosTrabajo) {
        this.horizontalBarCentrosTrabajo = horizontalBarCentrosTrabajo;
    }

    public List<Ausentismo> getDistribucionPorCentroTrabajo() {
        return distribucionPorCentroTrabajo;
    }

    public void setDistribucionPorCentroTrabajo(List<Ausentismo> distribucionPorCentroTrabajo) {
        this.distribucionPorCentroTrabajo = distribucionPorCentroTrabajo;
    }

    public HorizontalBarChartModel getHorizontalBarCargos() {
        return horizontalBarCargos;
    }

    public void setHorizontalBarCargos(HorizontalBarChartModel horizontalBarCargos) {
        this.horizontalBarCargos = horizontalBarCargos;
    }

    public List<Ausentismo> getDistribucionCargos() {
        return distribucionCargos;
    }

    public void setDistribucionCargos(List<Ausentismo> distribucionCargos) {
        this.distribucionCargos = distribucionCargos;
    }


    public ArrayList<Incapacidad> getDistribucionGrupoDiagnostico() {
        return distribucionGrupoDiagnostico;
    }

    public void setDistribucionGrupoDiagnostico(ArrayList<Incapacidad> distribucionGrupoDiagnostico) {
        this.distribucionGrupoDiagnostico = distribucionGrupoDiagnostico;
    }

    public HorizontalBarChartModel getHorizontalBarGrupoDiagnostico() {
        return horizontalBarGrupoDiagnostico;
    }

    public void setHorizontalBarGrupoDiagnostico(HorizontalBarChartModel horizontalBarGrupoDiagnostico) {
        this.horizontalBarGrupoDiagnostico = horizontalBarGrupoDiagnostico;
    }

    public PieChartModel getPieTipoIncapacidad() {
        return pieTipoIncapacidad;
    }

    public void setPieTipoIncapacidad(PieChartModel pieTipoIncapacidad) {
        this.pieTipoIncapacidad = pieTipoIncapacidad;
    }

    public Integer getTotCasosTipoIncapacidad() {
        return totCasosTipoIncapacidad;
    }

    public void setTotCasosTipoIncapacidad(Integer totCasosTipoIncapacidad) {
        this.totCasosTipoIncapacidad = totCasosTipoIncapacidad;
    }

    public List<Ausentismo> getDistribucionTipoIncapacidad() {
        return distribucionTipoIncapacidad;
    }

    public void setDistribucionTipoIncapacidad(List<Ausentismo> distribucionTipoIncapacidad) {
        this.distribucionTipoIncapacidad = distribucionTipoIncapacidad;
    }

    public PieChartModel getPieGenero() {
        return pieGenero;
    }

    public void setPieGenero(PieChartModel pieGenero) {
        this.pieGenero = pieGenero;
    }

    public Integer getTotCasosGenero() {
        return totCasosGenero;
    }

    public void setTotCasosGenero(Integer totCasosGenero) {
        this.totCasosGenero = totCasosGenero;
    }

    public List<Ausentismo> getDistribucionAuLaboralGenero() {
        return distribucionAuLaboralGenero;
    }

    public void setDistribucionAuLaboralGenero(List<Ausentismo> distribucionAuLaboralGenero) {
        this.distribucionAuLaboralGenero = distribucionAuLaboralGenero;
    }

    public PieChartModel getPiePorDias() {
        return piePorDias;
    }

    public void setPiePorDias(PieChartModel piePorDias) {
        this.piePorDias = piePorDias;
    }

    public PieChartModel getPiePorOrigen() {
        return piePorOrigen;
    }

    public void setPiePorOrigen(PieChartModel piePorOrigen) {
        this.piePorOrigen = piePorOrigen;
    }

    public float getTotCasos() {
        return totCasos;
    }

    public void setTotCasos(float totCasos) {
        this.totCasos = totCasos;
    }

    public float getTotDiasIncapacidad() {
        return totDiasIncapacidad;
    }

    public void setTotDiasIncapacidad(float totDiasIncapacidad) {
        this.totDiasIncapacidad = totDiasIncapacidad;
    }

    public List<Ausentismo> getDistribucionPorOrigen() {
        return distribucionPorOrigen;
    }

    public void setDistribucionPorOrigen(List<Ausentismo> distribucionPorOrigen) {
        this.distribucionPorOrigen = distribucionPorOrigen;
    }

    public String getAnoActualizador() {
        return anoActualizador;
    }

    public void setAnoActualizador(String anoActualizador) {
        this.anoActualizador = anoActualizador;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }    

    public PieChartModel getPieausentismoanomesEmpleado() {
        return pieausentismoanomesEmpleado;
    }

    public void setPieausentismoanomesEmpleado(PieChartModel pieausentismoanomesEmpleado) {
        this.pieausentismoanomesEmpleado = pieausentismoanomesEmpleado;
    }   
    
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public List<Ausentismo> getListAusentismoEmpleadoTotales() {
        return listAusentismoEmpleadoTotales;
    }

    public void setListAusentismoEmpleadoTotales(List<Ausentismo> listAusentismoEmpleadoTotales) {
        this.listAusentismoEmpleadoTotales = listAusentismoEmpleadoTotales;
    }

    public List<Ausentismo> getListAusentismoEmpleado() {
        return listAusentismoEmpleado;
    }

    public void setListAusentismoEmpleado(List<Ausentismo> listAusentismoEmpleado) {
        this.listAusentismoEmpleado = listAusentismoEmpleado;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Utilidades getUtil() {
        return util;
    }

    public void setUtil(Utilidades util) {
        this.util = util;
    }

    public Ausentismo getAusentismo() {
        return ausentismo;
    }

    public void setAusentismo(Ausentismo ausentismo) {
        this.ausentismo = ausentismo;
    }

    public List<Ausentismo> getFilteredlistaAusentismo() {
        return filteredlistaAusentismo;
    }

    public void setFilteredlistaAusentismo(List<Ausentismo> filteredlistaAusentismo) {
        this.filteredlistaAusentismo = filteredlistaAusentismo;
    }
    
    public List<Ausentismo> getListaausentismoEmpresa() {
        return listaausentismoEmpresa;
    }

    public void setListaausentismoEmpresa(List<Ausentismo> listaausentismoEmpresa) {
        this.listaausentismoEmpresa = listaausentismoEmpresa;
    }

    public List<Ausentismo> getPieporSubempresa() {
        return pieporSubempresa;
    }

    public void setPieporSubempresa(List<Ausentismo> pieporSubempresa) {
        this.pieporSubempresa = pieporSubempresa;
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

    public List<Ausentismo> getListaAusentismoanomes() {
        return listaAusentismoanomes;
    }

    public void setListaAusentismoanomes(List<Ausentismo> listaAusentismoanomes) {
        this.listaAusentismoanomes = listaAusentismoanomes;
    }    

    public String getTotarl() {
        return formato1.format(totarl);
    }

    public void setTotarl(float totarl) {
        this.totarl = totarl;
    }

    public String getToteps() {
        return formato1.format(toteps);
    }

    public void setToteps(float toteps) {
        this.toteps = toteps;
    }

    public String getTotempleador() {
        return formato1.format(totempleador);
    }

    public void setTotempleador(float totempleador) {
        this.totempleador = totempleador;
    }

    public String getTottrabajador() {
        return formato1.format(tottrabajador);
    }

    public void setTottrabajador(float tottrabajador) {
        this.tottrabajador = tottrabajador;
    }

    public String getTotal() {
        return formato1.format(total);
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getTotalhs() {
        return formato1.format(totalhs);
    }

    public void setTotalhs(float totalhs) {
        this.totalhs = totalhs;
    }

    public String getTotaleps() {
        return formato1.format(totaleps);
    }

    public void setTotaleps(float totaleps) {
        this.totaleps = totaleps;
    }

    public String getTotalarl() {
        return formato1.format(totalarl);
    }

    public void setTotalarl(float totalarl) {
        this.totalarl = totalarl;
    }

    public String getTotaltrabajador() {
        return formato1.format(totaltrabajador);
    }

    public void setTotaltrabajador(float totaltrabajador) {
        this.totaltrabajador = totaltrabajador;
    }

    public String getTotalem() {
        return formato1.format(totalem);
    }

    public void setTotalem(float totalem) {
        this.totalem = totalem;
    }

    public List<Ausentismo> getPieausentismoEmpresa() {
        return pieausentismoEmpresa;
    }

    public void setPieausentismoEmpresa(List<Ausentismo> pieausentismoEmpresa) {
        this.pieausentismoEmpresa = pieausentismoEmpresa;
    }

    public List<Ausentismo> getPieausentismoEmpleado() {
        return pieausentismoEmpleado;
    }

    public void setPieausentismoEmpleado(List<Ausentismo> pieausentismoEmpleado) {
        this.pieausentismoEmpleado = pieausentismoEmpleado;
    }
    public SubEmpresa getSubempresa() {
        return subempresa;
    }

    public void setSubempresa(SubEmpresa subempresa) {
        this.subempresa = subempresa;
    }
    
    public String getTotalsubt() {        
        
        return formato1.format(totalsubt);
    }

    public void setTotalsubt(float totalsubt) {
        this.totalsubt = totalsubt;
    }
    public PieChartModel getPieausentismoanomesEmpresa() {
        return pieausentismoanomesEmpresa;
    }

    public void setPieausentismoanomesEmpresa(PieChartModel pieausentismoanomesEmpresa) {
        this.pieausentismoanomesEmpresa = pieausentismoanomesEmpresa;
    } 

    public PieChartModel getPieSubempresa() {
        return pieSubempresa;
    }

    public void setPieSubempresa(PieChartModel pieSubempresa) {
        this.pieSubempresa = pieSubempresa;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorAusentismo;
import controlador.GestorEmpleado;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import modelo.Ausentismo;
import modelo.Empleado;
import modelo.Motivo;
import util.Utilidades;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.Año;
import modelo.Mes;
import modelo.SubEmpresa;
import org.primefaces.model.chart.PieChartModel;
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
    
    
    Boolean todos;
    Boolean selec;
    public Utilidades util = new Utilidades();
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory reg;
    private GestorAusentismo gestorAusentismo;
    private ArrayList<SelectItem> itemsMotivos = new ArrayList<>();
    private List<Ausentismo> listaAusentismo;
    private List<Ausentismo> listaAusentismoanomes;
    private List<Ausentismo> listaausentismoEmpresa;
    private List<Ausentismo> listAusentismoEmpleado;
    private List<Ausentismo> pieausentismoEmpresa;
    private List<Ausentismo> pieausentismoEmpleado;
    
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
       
    }

    public void BuscarEmpleado() throws Exception {
    
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        Empleado em = gestorEmpleado.validarEmpleado(cedula,nitsesion);
        Integer thacum = gestorAusentismo.horasanoAcumuladas(cedula);        
        
        if(em!=null && em.isEstado() == true){
           ausentismo.setEmpleado(em);
           ausentismo.getEmpleado().setThacum(thacum);
        }
        else {
            util.mostrarMensaje("La cedula no existe....");
            empleado =new Empleado();
        }
    }
    
    public void buscarEActualizacion() throws Exception {
    
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        Integer resultado = gestorEmpleado.validarEmpleadoActualizacion(cedula,nitsesion); 
        
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

    public void guardarRegistro() throws Exception{    

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
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! El registro no pudo ser almacenado !!");               
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
            
            totaleps=0;totalarl=0;totalem=0; totaltrabajador=0; totalsubt=0;
            //calculo de totales eps,arl,empleador,trabajador y total 
            
            
            for(int i=0; i < listAusentismoEmpleado.size(); i++){
                totaleps += listAusentismoEmpleado.get(i).getEps();
                totalarl += listAusentismoEmpleado.get(i).getArl();
                totalem += listAusentismoEmpleado.get(i).getEmpleador();
                totaltrabajador += listAusentismoEmpleado.get(i).getTrabajador();
                totalsubt += listAusentismoEmpleado.get(i).getTotalsube();
                
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
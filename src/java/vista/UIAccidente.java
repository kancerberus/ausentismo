/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorAccidente;
import controlador.GestorEmpleado;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import modelo.Empleado;
import modelo.Motivo;
import util.Utilidades;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.Accidente;
import modelo.AgenteAccidente;
import modelo.Year;
import modelo.CausaBasica;
import modelo.CausaInmediata;
import modelo.Clasificacion;
import modelo.IncapacidadSi;
import modelo.Mecanismo;
import modelo.Mes;
import modelo.ParteAfectada;
import modelo.Riesgo;
import modelo.SubEmpresa;
import modelo.TipoAccidente;
import modelo.TipoEvento;
import modelo.TipoLesion;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Andres
 */
public class UIAccidente implements Serializable {

    private String cedula;
    private Empleado empleado;
    private Accidente accidente;    
    private Mes mes;
    private Year year;
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
    private Integer totIncidente=0;
    private Integer totAcc=0;
    private Integer totEnf=0;           
    private Integer totIncatel=0;
    private Integer totCbasicaInc=0;
    private Integer totCbasicaAcc=0;
    private Integer totCbasicaEnf=0;
    private Integer totCbasicaTot=0;
    private Integer totCInmInc=0;
    private Integer totTaccidente=0;
    private Integer totClasificaciones=0;
    private Integer totMecInc=0;
    private Integer totMecAcc=0;
    private Integer totMecTots=0;
    private Integer totMecanismos=0;
    private Integer totAgInc=0;
    private Integer totAgAcc=0;
    private Integer totAgTots=0;
    private Integer totPafecInc=0;
    private Integer totPafecAcc=0;
    private Integer totPafecTots=0;
    private Integer totTipoLesionInc=0;
    private Integer totTipoLesionAcc=0;
    private Integer totTipoLesiontots=0;
    private Integer totCInmAcc=0;
    private Integer totCInmEnf=0;
    private Integer totCInmTot=0;
    private Integer totCasos=0;        
    private Integer totInc=0;
    private Integer totInv=0;
    private Float cumpInc=0.0f;
    private Float cumpAcc=0.0f;
    private Float cumpEnf=0.0f;
    private Float totCumpInv=0.0f;
    
    Boolean todos;
    Boolean selec;
    public Utilidades util = new Utilidades();
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory reg;
    private GestorAccidente gestorAccidente;
    private ArrayList<SelectItem> itemsMotivos = new ArrayList<>();
    
    private List<Accidente> distribucionTipoEventos=new ArrayList<>();
    private List<Accidente> distribucionRiesgos=new ArrayList<>();
    private List<Accidente> distribucionCausaBasica=new ArrayList<>();
    private List<Accidente> distribucionCausaInmediata=new ArrayList<>();
    private List<Accidente> distribucionTipoAccion=new ArrayList<>();
    private List<Accidente> distribucionClasificacion=new ArrayList<>();
    private List<Accidente> distribucionMecanismo=new ArrayList<>();
    private List<Accidente> distribucionAgente=new ArrayList<>();
    private List<Accidente> distribucionParteAfectada=new ArrayList<>();
    private List<Accidente> distribucionTipoLesion=new ArrayList<>();
    private List<Accidente> distribucionCargos=new ArrayList<>();
    private HorizontalBarChartModel horizontalBarCausaBasica;
    private HorizontalBarChartModel horizontalBarCausaInmediata;
    private HorizontalBarChartModel horizontalBarMecanismos=new HorizontalBarChartModel();
    private HorizontalBarChartModel horizontalBarAgenteAccidente=new HorizontalBarChartModel();
    private HorizontalBarChartModel horizontalBarTipoLesion=new HorizontalBarChartModel();
    private HorizontalBarChartModel horizontalBarCargos=new HorizontalBarChartModel();
    private PieChartModel pieTipoAccidente=new PieChartModel();
    private PieChartModel pieClasificacion=new PieChartModel();
    private PieChartModel pieParteAfectada=new PieChartModel();
    private PieChartModel pieRiesgos=new PieChartModel();
    private String tipoAccion;
    
    
    
    private PieChartModel pieSubempresa;
    private ExpressionFactory ef;    

    public UIAccidente()  throws Exception {
       contextoJSF = FacesContext.getCurrentInstance();
       contextoEL = contextoJSF.getELContext(); 
       reg = contextoJSF.getApplication().getExpressionFactory();
       empleado = new Empleado();
       accidente = new Accidente();                     
       accidente.setClasificacion(new Clasificacion());
       accidente.setTipoEvento(new TipoEvento());
       accidente.setIncapacidadsi(new IncapacidadSi());
       accidente.setTipoAccidente(new TipoAccidente());
       accidente.setParteAfectada(new ParteAfectada());
       accidente.setTipoLesion(new TipoLesion());
       accidente.setCausaBasica(new CausaBasica());
       accidente.setCausaInmediata(new CausaInmediata());
       accidente.setRiesgo(new Riesgo());   
       accidente.setMecanismo(new Mecanismo());
       accidente.setAgenteAccidente(new AgenteAccidente());
       horizontalBarCausaBasica=new HorizontalBarChartModel();
       horizontalBarCausaInmediata=new HorizontalBarChartModel();
       distribucionRiesgos=new ArrayList<>();
       distribucionTipoEventos=new ArrayList<>();
       distribucionRiesgos=new ArrayList<>();
       distribucionCausaBasica=new ArrayList<>();
       distribucionCausaInmediata=new ArrayList<>();  
       distribucionTipoAccion=new ArrayList<>();
       distribucionClasificacion=new ArrayList<>();
       distribucionMecanismo=new ArrayList<>();
       distribucionAgente=new ArrayList<>();
       distribucionParteAfectada=new ArrayList<>();
       distribucionTipoLesion=new ArrayList<>();
       distribucionCargos=new ArrayList<>();
       mes = new Mes();
       year = new Year();
       gestorAccidente = new GestorAccidente();       
       
    }

    public void BuscarEmpleado() throws Exception {
    
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        Empleado em = gestorEmpleado.validarEmpleado(cedula,nitsesion);        
        
        if(em!=null && em.getEstado()== true){
           accidente.setEmpleado(em);           
        }
        else {
            util.mostrarMensaje("La cedula no existe....");
            empleado =new Empleado();
        }
    }
    
    public void generarAccidentes() throws Exception{
        try {  
            totarl=0;
            toteps=0;
            totempleador=0;
            tottrabajador=0;
            total=0;
            totalhs=0;
            totaleps=0;
            totalarl=0;
            totaltrabajador=0;
            totalem=0;    
            totalsubt=0;
            totIncidente=0;
            totAcc=0;
            totEnf=0;           
            totIncatel=0;
            totCbasicaInc=0;
            totCbasicaAcc=0;
            totCbasicaEnf=0;
            totCbasicaTot=0;
            totTaccidente=0;
            totClasificaciones=0;
            totMecInc=0;
            totMecAcc=0;
            totMecTots=0;
            totMecanismos=0;
            totCInmAcc=0;
            totCInmEnf=0;
            totCInmTot=0;
            totCasos=0;        
            totInc=0;
            totInv=0;
            cumpInc=0.0f;
            cumpAcc=0.0f;
            cumpEnf=0.0f;
            totCasos=0;
            totInc=0;
            totInv=0;   
            totPafecInc=0;
            totPafecAcc=0;
            totPafecTots=0;
            totTipoLesionInc=0;
            totTipoLesionAcc=0;
            totTipoLesiontots=0;
            totAgInc=0;
            totAgAcc=0;
            totAgTots=0;
           
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitem = (String) ef.createValueExpression(contextoEL, "#{listasBean.empresa.nitempresa}", String.class).getValue(contextoEL);  
            String nitsubem = (String) ef.createValueExpression(contextoEL, "#{listasBean.subempresa.nitsubempresa}", String.class).getValue(contextoEL);  
            //revisar checkbox
            String selmesdesde = null;
            String selmeshasta = null;
            String selano = null; 
            gestorAccidente=new GestorAccidente();
            
            pieRiesgos=new PieChartModel();
            
            
            if(todos == true){
                selano = year.getYear();   
            }else{
               selmesdesde = (year.getYear())+"/"+(mes.getDesde());
               selmeshasta = (year.getYear()+"/"+(mes.getHasta()));           
            }        
            if(nitsubem == ""){
                nitsubem = null;
            }            
            
            distribucionTipoEventos=new ArrayList<>();
            distribucionTipoEventos.addAll(gestorAccidente.cargarDistribucionTipoEventos(nitem, nitsubem,selmesdesde,selmeshasta,selano));
            
            
            for(int i=0;i<=distribucionTipoEventos.size()-1;i++){
                totCasos+=distribucionTipoEventos.get(i).getCasos();                
                totInc+=distribucionTipoEventos.get(i).getIncapacitantes();                
                totInv+=distribucionTipoEventos.get(i).getInvestigados();                  
            }
            
            cumpInc=distribucionTipoEventos.get(0).getInvestigados().floatValue()/distribucionTipoEventos.get(0).getCasos().floatValue()*100;
            cumpAcc=distribucionTipoEventos.get(1).getInvestigados().floatValue()/distribucionTipoEventos.get(1).getCasos().floatValue()*100;
            cumpEnf=distribucionTipoEventos.get(2).getInvestigados().floatValue()/distribucionTipoEventos.get(2).getCasos().floatValue()*100;            
            totCumpInv=(totInv.floatValue()/totCasos.floatValue())*100;
            
            ////
            distribucionRiesgos=new ArrayList<>();
            distribucionRiesgos.addAll(gestorAccidente.cargarDistribucionRiesgos(nitem,nitsubem,selmesdesde, selmeshasta,selano));            
            
            for(int k=0;k<=distribucionRiesgos.size()-1;k++){
                totIncidente+=distribucionRiesgos.get(k).getIncidentes();
                totAcc+=distribucionRiesgos.get(k).getAccidentes();
                totEnf+=distribucionRiesgos.get(k).getEnfermedades();
                totIncatel+=distribucionRiesgos.get(k).getIncatels();
            }
            
            pieRiesgos=new PieChartModel();
            
            if(tipoAccion.equals("0")){
                for(int j=0;j<=distribucionRiesgos.size()-1;j++){
                        pieRiesgos.set(distribucionRiesgos.get(j).getRiesgo().getNombre(), distribucionRiesgos.get(j).getIncatels());
                        
                    }
            }
            
            if(tipoAccion!=null){
                if(tipoAccion.equals("11")){
                    for(int j=0;j<=distribucionRiesgos.size()-1;j++){
                        pieRiesgos.set(distribucionRiesgos.get(j).getRiesgo().getNombre(), distribucionRiesgos.get(j).getIncidentes());
                    }
                }
                if(tipoAccion.equals("12")){
                    for(int j=0;j<=distribucionRiesgos.size()-1;j++){
                        pieRiesgos.set(distribucionRiesgos.get(j).getRiesgo().getNombre(), distribucionRiesgos.get(j).getAccidentes());
                    }
                }
                if(tipoAccion.equals("13")){
                    for(int j=0;j<=distribucionRiesgos.size()-1;j++){
                        pieRiesgos.set(distribucionRiesgos.get(j).getRiesgo().getNombre(), distribucionRiesgos.get(j).getEnfermedades());
                    }
                }                
            }
            
            
            
            pieRiesgos.setDataFormat("value");            
            pieRiesgos.setShowDataLabels(true);
            pieRiesgos.setLegendPosition("w");
            
            /////
            
            distribucionCausaBasica=new ArrayList<>();
            distribucionCausaBasica.addAll(gestorAccidente.cargarDistribucionCausaBasica(nitem,nitsubem,selmesdesde, selmeshasta,selano));            
            
            
            for(int k=0;k<=distribucionCausaBasica.size()-1;k++){
                totCbasicaInc+=distribucionCausaBasica.get(k).getIncidentes();
                totCbasicaAcc+=distribucionCausaBasica.get(k).getAccidentes();
                totCbasicaEnf+=distribucionCausaBasica.get(k).getEnfermedades();
                totCbasicaTot+=distribucionCausaBasica.get(k).getTotCbasica();
            }
            
            horizontalBarCausaBasica=new HorizontalBarChartModel();            
            for(int i=0;i<=distribucionCausaBasica.size()-1;i++){
                ChartSeries serie=new ChartSeries();                                
                serie.setLabel(distribucionCausaBasica.get(i).getCausaBasica().getNombre());
                serie.set(distribucionCausaBasica.get(i).getCausaBasica().getNombre(), distribucionCausaBasica.get(i).getTotCbasica());                
                horizontalBarCausaBasica.addSeries(serie);
            }
            
            horizontalBarCausaBasica.setTitle("Distribucion Por Causa Basica");
            horizontalBarCausaBasica.setLegendPosition("ne");
            horizontalBarCausaBasica.setAnimate(true);
            horizontalBarCausaBasica.setDatatipFormat("%.0f");            
            horizontalBarCausaBasica.setBarWidth(40);
            horizontalBarCausaBasica.setShowPointLabels(true);
            
            
            Axis xAxis = horizontalBarCausaBasica.getAxis(AxisType.X);
            xAxis.setLabel("Cantidad");
            xAxis.setMin(0);
            xAxis.setMax(50);
            xAxis.setTickInterval("10");

            Axis yAxis = horizontalBarCausaBasica.getAxis(AxisType.Y);
            yAxis.setLabel("Causa Basica");
            
            distribucionCausaInmediata=new ArrayList<>();
            distribucionCausaInmediata.addAll(gestorAccidente.cargarDistribucionCausaInmediata(nitem,nitsubem,selmesdesde, selmeshasta,selano));
            
            
            
            horizontalBarCausaInmediata=new HorizontalBarChartModel();            
            for(int i=0;i<=distribucionCausaInmediata.size()-1;i++){
                totCInmInc+=distribucionCausaInmediata.get(i).getIncidentes();
                totCInmAcc+=distribucionCausaInmediata.get(i).getAccidentes();
                totCInmEnf+=distribucionCausaInmediata.get(i).getEnfermedades();
                totCInmTot+=distribucionCausaInmediata.get(i).getTotCInmediata();
                ChartSeries serie=new ChartSeries();                                
                serie.setLabel(distribucionCausaInmediata.get(i).getCausaInmediata().getNombre());
                serie.set(distribucionCausaInmediata.get(i).getCausaInmediata().getNombre(), distribucionCausaInmediata.get(i).getTotCInmediata());                
                horizontalBarCausaInmediata.addSeries(serie);
            }
            
            horizontalBarCausaInmediata.setTitle("Distribucion Por Causa Inmediata");
            horizontalBarCausaInmediata.setLegendPosition("ne");
            horizontalBarCausaInmediata.setAnimate(true);
            horizontalBarCausaInmediata.setDatatipFormat("%.0f");            
            horizontalBarCausaInmediata.setBarWidth(40);
            horizontalBarCausaInmediata.setShowPointLabels(true);
            
            Axis xAxisInm = horizontalBarCausaInmediata.getAxis(AxisType.X);
            xAxisInm.setLabel("Cantidad");
            xAxisInm.setMin(0);
            xAxisInm.setMax(50);
            xAxisInm.setTickInterval("10");

            Axis yAxisInm = horizontalBarCausaInmediata.getAxis(AxisType.Y);
            yAxisInm.setLabel("Causa Inmediata");
            
            
            distribucionTipoAccion=new ArrayList<>();
            distribucionTipoAccion.addAll(gestorAccidente.cargarDistribucionTipoAccidente(nitem,nitsubem,selmesdesde, selmeshasta,selano));            
            
            pieTipoAccidente=new PieChartModel();
            for(int i=0;i<=distribucionTipoAccion.size()-1;i++){
                totTaccidente+=distribucionTipoAccion.get(i).getCasos();
                pieTipoAccidente.set(distribucionTipoAccion.get(i).getTipoAccidente().getNombre(), distribucionTipoAccion.get(i).getCasos());
            }
            pieTipoAccidente.isShowDataLabels();
            pieTipoAccidente.setLegendPosition("w");   
            pieTipoAccidente.setDataFormat("value");
            pieTipoAccidente.setShowDataLabels(true);            
            
            distribucionClasificacion=new ArrayList<>();
            distribucionClasificacion.addAll(gestorAccidente.cargarDistribucionClasificaciones(nitem,nitsubem,selmesdesde, selmeshasta,selano));
            

            
            pieClasificacion=new PieChartModel();
            for(int i=0;i<=distribucionClasificacion.size()-1;i++){
                totClasificaciones+=distribucionClasificacion.get(i).getCasos();
                pieClasificacion.set(distribucionClasificacion.get(i).getClasificacion().getNombre(), distribucionClasificacion.get(i).getCasos());
            }
            pieClasificacion.isShowDataLabels();
            pieClasificacion.setLegendPosition("w");   
            pieClasificacion.setDataFormat("value");
            pieClasificacion.setShowDataLabels(true);
            
            
            
            distribucionMecanismo=new ArrayList<>();
            distribucionMecanismo.addAll(gestorAccidente.cargarDistribucionMecanismos(nitem,nitsubem,selmesdesde, selmeshasta,selano));
                                    
            horizontalBarMecanismos=new HorizontalBarChartModel();       
            
            for(int i=0;i<=distribucionMecanismo.size()-1;i++){
                totMecInc+=distribucionMecanismo.get(i).getIncidentes();
                totMecAcc+=distribucionMecanismo.get(i).getAccidentes();
                totMecTots+=distribucionMecanismo.get(i).getTotMecanismo();                
                ChartSeries serie=new ChartSeries();                                
                serie.setLabel(distribucionMecanismo.get(i).getMecanismo().getNombre());
                serie.set(distribucionMecanismo.get(i).getMecanismo().getNombre(), distribucionMecanismo.get(i).getTotMecanismo());
                horizontalBarMecanismos.addSeries(serie);
            }
            
            horizontalBarMecanismos.setTitle("Distribucion Por Mecanismos");
            horizontalBarMecanismos.setLegendPosition("ne");
            horizontalBarMecanismos.setAnimate(true);
            horizontalBarMecanismos.setDatatipFormat("%.0f");            
            horizontalBarMecanismos.setBarWidth(40);
            horizontalBarMecanismos.setShowPointLabels(true);
            
            Axis xAxisMec = horizontalBarMecanismos.getAxis(AxisType.X);
            xAxisMec.setLabel("Cantidad");
            xAxisMec.setMin(0);
            xAxisMec.setMax(50);
            xAxisMec.setTickInterval("10");

            Axis yAxisMec= horizontalBarMecanismos.getAxis(AxisType.Y);
            yAxisMec.setLabel("Mecanismo");
            
            
            distribucionAgente=new ArrayList<>();
            distribucionAgente.addAll(gestorAccidente.cargarDistribucionAgente(nitem,nitsubem,selmesdesde, selmeshasta,selano));
            
            horizontalBarAgenteAccidente=new HorizontalBarChartModel();
            
            for(int i=0;i<=distribucionAgente.size()-1;i++){
                totAgInc+=distribucionAgente.get(i).getIncidentes();
                totAgAcc+=distribucionAgente.get(i).getAccidentes();
                totAgTots+=distribucionAgente.get(i).getTotAgAccidente();
                
                ChartSeries serie=new ChartSeries();                                
                serie.setLabel(distribucionAgente.get(i).getAgenteAccidente().getNombre());
                serie.set(distribucionAgente.get(i).getAgenteAccidente().getNombre(), distribucionAgente.get(i).getTotAgAccidente());
                horizontalBarAgenteAccidente.addSeries(serie);
            }
            
            horizontalBarAgenteAccidente.setTitle("Distribucion Por Agente Accidente");
            horizontalBarAgenteAccidente.setLegendPosition("ne");
            horizontalBarAgenteAccidente.setAnimate(true);
            horizontalBarAgenteAccidente.setDatatipFormat("%.0f");            
            horizontalBarAgenteAccidente.setBarWidth(40);
            horizontalBarAgenteAccidente.setShowPointLabels(true);
            
            Axis xAxisAg = horizontalBarAgenteAccidente.getAxis(AxisType.X);
            xAxisAg.setLabel("Cantidad");
            xAxisAg.setMin(0);
            xAxisAg.setMax(50);
            xAxisAg.setTickInterval("10");

            Axis yAxisAg= horizontalBarAgenteAccidente.getAxis(AxisType.Y);
            yAxisAg.setLabel("Agente Accidente");
            
            
            distribucionParteAfectada=new ArrayList<>();
            distribucionParteAfectada.addAll(gestorAccidente.cargarDistribucionParteAfectada(nitem,nitsubem,selmesdesde, selmeshasta,selano));
            pieParteAfectada=new PieChartModel();
            for(int i=0;i<=distribucionParteAfectada.size()-1;i++){
                totPafecInc+=distribucionParteAfectada.get(i).getIncidentes();
                totPafecAcc+=distribucionParteAfectada.get(i).getAccidentes();
                totPafecTots+=distribucionParteAfectada.get(i).getTotParteAfectada();
                pieParteAfectada.set(distribucionParteAfectada.get(i).getParteAfectada().getNombre(), distribucionParteAfectada.get(i).getTotParteAfectada());
            }
            
            pieParteAfectada.isShowDataLabels();
            pieParteAfectada.setLegendPosition("w");   
            pieParteAfectada.setDataFormat("value");
            pieParteAfectada.setShowDataLabels(true);
            
            
            distribucionTipoLesion=new ArrayList<>();
            distribucionTipoLesion.addAll(gestorAccidente.cargarDistribucionTipoLesion(nitem,nitsubem,selmesdesde, selmeshasta,selano));
            
            
            horizontalBarTipoLesion=new HorizontalBarChartModel();
            for(int i=0;i<=distribucionTipoLesion.size()-1;i++){
                totTipoLesionInc+=distribucionTipoLesion.get(i).getIncidentes();
                totTipoLesionAcc+=distribucionTipoLesion.get(i).getAccidentes();
                totTipoLesiontots+=distribucionTipoLesion.get(i).getTotTipoLesion();
                ChartSeries serie=new ChartSeries();                                
                serie.setLabel(distribucionTipoLesion.get(i).getTipoLesion().getNombre());
                serie.set(distribucionTipoLesion.get(i).getTipoLesion().getNombre(), distribucionTipoLesion.get(i).getTotTipoLesion());
                horizontalBarTipoLesion.addSeries(serie);                      
            }
            
            horizontalBarTipoLesion.setTitle("Distribucion Por Tipo Lesion");
            horizontalBarTipoLesion.setLegendPosition("ne");
            horizontalBarTipoLesion.setAnimate(true);
            horizontalBarTipoLesion.setDatatipFormat("%.0f");            
            horizontalBarTipoLesion.setBarWidth(40);
            horizontalBarTipoLesion.setShowPointLabels(true);
            
            Axis xAxisTl = horizontalBarTipoLesion.getAxis(AxisType.X);
            xAxisTl.setLabel("Cantidad");
            xAxisTl.setMin(0);
            xAxisTl.setMax(50);
            xAxisTl.setTickInterval("10");

            Axis yAxisTl= horizontalBarTipoLesion.getAxis(AxisType.Y);
            yAxisTl.setLabel("Tipo Lesion");
            
            distribucionCargos=new ArrayList<>();
            distribucionCargos.addAll(gestorAccidente.cargarDistribucionCargos(nitem,nitsubem,selmesdesde, selmeshasta,selano));
            
            horizontalBarCargos=new HorizontalBarChartModel();
            
            for(int i=0;i<=distribucionCargos.size()-1;i++){                
                ChartSeries serie=new ChartSeries();                                
                serie.setLabel(distribucionCargos.get(i).getCargo().getNombre());
                serie.set(distribucionCargos.get(i).getCargo().getNombre(), distribucionCargos.get(i).getTotCargos());
                horizontalBarCargos.addSeries(serie);                      
            }
            
            horizontalBarCargos.setTitle("Distribucion Por Cargos");
            horizontalBarCargos.setLegendPosition("ne");
            horizontalBarCargos.setAnimate(true);
            horizontalBarCargos.setDatatipFormat("%.0f");            
            horizontalBarCargos.setBarWidth(40);
            horizontalBarCargos.setShowPointLabels(true);
            
            Axis xAxisC = horizontalBarCargos.getAxis(AxisType.X);
            xAxisC.setLabel("Cantidad");
            xAxisC.setMin(0);
            xAxisC.setMax(50);
            xAxisC.setTickInterval("10");

            Axis yAxisC= horizontalBarCargos.getAxis(AxisType.Y);
            yAxisC.setLabel("Cargo");
            
        } catch (Exception e) {
            Logger.getLogger(UIAccidente.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
        }
    }     

    public void limpiarIncapacidad() {       
        accidente = new Accidente(); 
        empleado = new Empleado();        
        itemsMotivos.clear();
    }
     
    public void guardarRegistro() throws Exception{

        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        gestorAccidente = new GestorAccidente();

        try {
            //verificar que todas las cajas este llenas           
            if (accidente.getEmpleado().getCedula() == null) {
                msg = "La cédula esta vacía!";
                invalido = true;              
            }
         

            if (invalido == false) {                                    
                    Integer resultado = gestorAccidente.guardarAccidente(accidente);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! El registro fue realizado de manera exitosa !!");
                        accidente = new Accidente();
                        accidente.setTipoEvento(new TipoEvento());
                        accidente.setClasificacion(new Clasificacion());
                        accidente.setTipoEvento(new TipoEvento());
                        accidente.setIncapacidadsi(new IncapacidadSi());
                        accidente.setTipoAccidente(new TipoAccidente());
                        accidente.setParteAfectada(new ParteAfectada());
                        accidente.setTipoLesion(new TipoLesion());
                        accidente.setCausaBasica(new CausaBasica());
                        accidente.setCausaInmediata(new CausaInmediata());
                        accidente.setRiesgo(new Riesgo());   
                        accidente.setMecanismo(new Mecanismo());
                        accidente.setAgenteAccidente(new AgenteAccidente());
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

    public Float getCumpInc() {
        return cumpInc;
    }

    public void setCumpInc(Float cumpInc) {
        this.cumpInc = cumpInc;
    }

    public Float getCumpAcc() {
        return cumpAcc;
    }

    public void setCumpAcc(Float cumpAcc) {
        this.cumpAcc = cumpAcc;
    }

    public Float getCumpEnf() {
        return cumpEnf;
    }

    public void setCumpEnf(Float cumpEnf) {
        this.cumpEnf = cumpEnf;
    }

    public Float getTotCumpInv() {
        return totCumpInv;
    }

    public void setTotCumpInv(Float totCumpInv) {
        this.totCumpInv = totCumpInv;
    }



    public HorizontalBarChartModel getHorizontalBarCargos() {
        return horizontalBarCargos;
    }

    public void setHorizontalBarCargos(HorizontalBarChartModel horizontalBarCargos) {
        this.horizontalBarCargos = horizontalBarCargos;
    }

    public List<Accidente> getDistribucionCargos() {
        return distribucionCargos;
    }

    public void setDistribucionCargos(List<Accidente> distribucionCargos) {
        this.distribucionCargos = distribucionCargos;
    }

    public HorizontalBarChartModel getHorizontalBarTipoLesion() {
        return horizontalBarTipoLesion;
    }

    public void setHorizontalBarTipoLesion(HorizontalBarChartModel horizontalBarTipoLesion) {
        this.horizontalBarTipoLesion = horizontalBarTipoLesion;
    }

    public List<Accidente> getDistribucionTipoLesion() {
        return distribucionTipoLesion;
    }

    public void setDistribucionTipoLesion(List<Accidente> distribucionTipoLesion) {
        this.distribucionTipoLesion = distribucionTipoLesion;
    }

    public PieChartModel getPieParteAfectada() {
        return pieParteAfectada;
    }

    public void setPieParteAfectada(PieChartModel pieParteAfectada) {
        this.pieParteAfectada = pieParteAfectada;
    }

    public Integer getTotPafecInc() {
        return totPafecInc;
    }

    public void setTotPafecInc(Integer totPafecInc) {
        this.totPafecInc = totPafecInc;
    }

    public Integer getTotPafecAcc() {
        return totPafecAcc;
    }

    public void setTotPafecAcc(Integer totPafecAcc) {
        this.totPafecAcc = totPafecAcc;
    }

    public Integer getTotPafecTots() {
        return totPafecTots;
    }

    public void setTotPafecTots(Integer totPafecTots) {
        this.totPafecTots = totPafecTots;
    }

    public List<Accidente> getDistribucionParteAfectada() {
        return distribucionParteAfectada;
    }

    public void setDistribucionParteAfectada(List<Accidente> distribucionParteAfectada) {
        this.distribucionParteAfectada = distribucionParteAfectada;
    }

    public Integer getTotAgInc() {
        return totAgInc;
    }

    public void setTotAgInc(Integer totAgInc) {
        this.totAgInc = totAgInc;
    }

    public Integer getTotAgAcc() {
        return totAgAcc;
    }

    public void setTotAgAcc(Integer totAgAcc) {
        this.totAgAcc = totAgAcc;
    }

    public Integer getTotAgTots() {
        return totAgTots;
    }

    public void setTotAgTots(Integer totAgTots) {
        this.totAgTots = totAgTots;
    }

    public HorizontalBarChartModel getHorizontalBarAgenteAccidente() {
        return horizontalBarAgenteAccidente;
    }

    public void setHorizontalBarAgenteAccidente(HorizontalBarChartModel horizontalBarAgenteAccidente) {
        this.horizontalBarAgenteAccidente = horizontalBarAgenteAccidente;
    }

    public List<Accidente> getDistribucionAgente() {
        return distribucionAgente;
    }

    public void setDistribucionAgente(List<Accidente> distribucionAgente) {
        this.distribucionAgente = distribucionAgente;
    }

    public HorizontalBarChartModel getHorizontalBarMecanismos() {
        return horizontalBarMecanismos;
    }

    public void setHorizontalBarMecanismos(HorizontalBarChartModel horizontalBarMecanismos) {
        this.horizontalBarMecanismos = horizontalBarMecanismos;
    }

    public Integer getTotMecInc() {
        return totMecInc;
    }

    public void setTotMecInc(Integer totMecInc) {
        this.totMecInc = totMecInc;
    }

    public Integer getTotMecAcc() {
        return totMecAcc;
    }

    public void setTotMecAcc(Integer totMecAcc) {
        this.totMecAcc = totMecAcc;
    }

    public Integer getTotMecTots() {
        return totMecTots;
    }

    public void setTotMecTots(Integer totMecTots) {
        this.totMecTots = totMecTots;
    }

    public Integer getTotMecanismos() {
        return totMecanismos;
    }

    public void setTotMecanismos(Integer totMecanismos) {
        this.totMecanismos = totMecanismos;
    }

    public List<Accidente> getDistribucionMecanismo() {
        return distribucionMecanismo;
    }

    public void setDistribucionMecanismo(List<Accidente> distribucionMecanismo) {
        this.distribucionMecanismo = distribucionMecanismo;
    }

    public Integer getTotClasificaciones() {
        return totClasificaciones;
    }

    public void setTotClasificaciones(Integer totClasificaciones) {
        this.totClasificaciones = totClasificaciones;
    }

    public PieChartModel getPieClasificacion() {
        return pieClasificacion;
    }

    public void setPieClasificacion(PieChartModel pieClasificacion) {
        this.pieClasificacion = pieClasificacion;
    }

    public List<Accidente> getDistribucionClasificacion() {
        return distribucionClasificacion;
    }

    public void setDistribucionClasificacion(List<Accidente> distribucionClasificacion) {
        this.distribucionClasificacion = distribucionClasificacion;
    }

    public PieChartModel getPieTipoAccidente() {
        return pieTipoAccidente;
    }

    public void setPieTipoAccidente(PieChartModel pieTipoAccidente) {
        this.pieTipoAccidente = pieTipoAccidente;
    }

    public Integer getTotTaccidente() {
        return totTaccidente;
    }

    public void setTotTaccidente(Integer totTaccidente) {
        this.totTaccidente = totTaccidente;
    }

    public Integer getTotTipoLesionInc() {
        return totTipoLesionInc;
    }

    public void setTotTipoLesionInc(Integer totTipoLesionInc) {
        this.totTipoLesionInc = totTipoLesionInc;
    }

    public Integer getTotTipoLesionAcc() {
        return totTipoLesionAcc;
    }

    public void setTotTipoLesionAcc(Integer totTipoLesionAcc) {
        this.totTipoLesionAcc = totTipoLesionAcc;
    }

    public Integer getTotTipoLesiontots() {
        return totTipoLesiontots;
    }

    public void setTotTipoLesiontots(Integer totTipoLesiontots) {
        this.totTipoLesiontots = totTipoLesiontots;
    }

    public List<Accidente> getDistribucionTipoAccion() {
        return distribucionTipoAccion;
    }

    public void setDistribucionTipoAccion(List<Accidente> distribucionTipoAccion) {
        this.distribucionTipoAccion = distribucionTipoAccion;
    }

    public Integer getTotCInmInc() {
        return totCInmInc;
    }

    public void setTotCInmInc(Integer totCInmInc) {
        this.totCInmInc = totCInmInc;
    }

    public Integer getTotCInmAcc() {
        return totCInmAcc;
    }

    public void setTotCInmAcc(Integer totCInmAcc) {
        this.totCInmAcc = totCInmAcc;
    }

    public Integer getTotCInmEnf() {
        return totCInmEnf;
    }

    public void setTotCInmEnf(Integer totCInmEnf) {
        this.totCInmEnf = totCInmEnf;
    }

    public Integer getTotCInmTot() {
        return totCInmTot;
    }

    public void setTotCInmTot(Integer totCInmTot) {
        this.totCInmTot = totCInmTot;
    }

    public List<Accidente> getDistribucionCausaInmediata() {
        return distribucionCausaInmediata;
    }

    public void setDistribucionCausaInmediata(List<Accidente> distribucionCausaInmediata) {
        this.distribucionCausaInmediata = distribucionCausaInmediata;
    }

    public HorizontalBarChartModel getHorizontalBarCausaBasica() {
        return horizontalBarCausaBasica;
    }

    public void setHorizontalBarCausaBasica(HorizontalBarChartModel horizontalBarCausaBasica) {
        this.horizontalBarCausaBasica = horizontalBarCausaBasica;
    }


    public Integer getTotCbasicaInc() {
        return totCbasicaInc;
    }

    public void setTotCbasicaInc(Integer totCbasicaInc) {
        this.totCbasicaInc = totCbasicaInc;
    }

    public Integer getTotCbasicaAcc() {
        return totCbasicaAcc;
    }

    public void setTotCbasicaAcc(Integer totCbasicaAcc) {
        this.totCbasicaAcc = totCbasicaAcc;
    }

    public Integer getTotCbasicaEnf() {
        return totCbasicaEnf;
    }

    public void setTotCbasicaEnf(Integer totCbasicaEnf) {
        this.totCbasicaEnf = totCbasicaEnf;
    }

    public Integer getTotCbasicaTot() {
        return totCbasicaTot;
    }

    public void setTotCbasicaTot(Integer totCbasicaTot) {
        this.totCbasicaTot = totCbasicaTot;
    }

    public Integer getTotIncatel() {
        return totIncatel;
    }

    public void setTotIncatel(Integer totIncatel) {
        this.totIncatel = totIncatel;
    }

    public Integer getTotIncidente() {
        return totIncidente;
    }

    public void setTotIncidente(Integer totIncidente) {
        this.totIncidente = totIncidente;
    }

    public Integer getTotAcc() {
        return totAcc;
    }

    public void setTotAcc(Integer totAcc) {
        this.totAcc = totAcc;
    }

    public Integer getTotEnf() {
        return totEnf;
    }

    public void setTotEnf(Integer totEnf) {
        this.totEnf = totEnf;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public PieChartModel getPieRiesgos() {
        return pieRiesgos;
    }

    public void setPieRiesgos(PieChartModel pieRiesgos) {
        this.pieRiesgos = pieRiesgos;
    }

    public List<Accidente> getDistribucionRiesgos() {
        return distribucionRiesgos;
    }

    public void setDistribucionRiesgos(List<Accidente> distribucionRiesgos) {
        this.distribucionRiesgos = distribucionRiesgos;
    }

    public List<Accidente> getDistribucionCausaBasica() {
        return distribucionCausaBasica;
    }

    public void setDistribucionCausaBasica(List<Accidente> distribucionCausaBasica) {
        this.distribucionCausaBasica = distribucionCausaBasica;
    }

    

    public Integer getTotCasos() {
        return totCasos;
    }

    public void setTotCasos(Integer totCasos) {
        this.totCasos = totCasos;
    }

    public Integer getTotInc() {
        return totInc;
    }

    public void setTotInc(Integer totInc) {
        this.totInc = totInc;
    }

    public Integer getTotInv() {
        return totInv;
    }

    public void setTotInv(Integer totInv) {
        this.totInv = totInv;
    }
    
    public void limpiarAccidente() {       
        accidente = new Accidente();
        empleado = new Empleado();                
    }

    public List<Accidente> getDistribucionTipoEventos() {
        return distribucionTipoEventos;
    }

    public void setDistribucionTipoEventos(List<Accidente> distribucionTipoEventos) {
        this.distribucionTipoEventos = distribucionTipoEventos;
    }    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Accidente getAccidente() {
        return accidente;
    }

    public void setAccidente(Accidente accidente) {
        this.accidente = accidente;
    }

    public Mes getMes() {
        return mes;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    public SubEmpresa getSubempresa() {
        return subempresa;
    }

    public void setSubempresa(SubEmpresa subempresa) {
        this.subempresa = subempresa;
    }


    public DecimalFormat getFormato1() {
        return formato1;
    }

    public void setFormato1(DecimalFormat formato1) {
        this.formato1 = formato1;
    }

    public float getTotarl() {
        return totarl;
    }

    public void setTotarl(float totarl) {
        this.totarl = totarl;
    }

    public float getToteps() {
        return toteps;
    }

    public void setToteps(float toteps) {
        this.toteps = toteps;
    }

    public float getTotempleador() {
        return totempleador;
    }

    public void setTotempleador(float totempleador) {
        this.totempleador = totempleador;
    }

    public float getTottrabajador() {
        return tottrabajador;
    }

    public void setTottrabajador(float tottrabajador) {
        this.tottrabajador = tottrabajador;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTotalhs() {
        return totalhs;
    }

    public void setTotalhs(float totalhs) {
        this.totalhs = totalhs;
    }

    public float getTotaleps() {
        return totaleps;
    }

    public void setTotaleps(float totaleps) {
        this.totaleps = totaleps;
    }

    public float getTotalarl() {
        return totalarl;
    }

    public void setTotalarl(float totalarl) {
        this.totalarl = totalarl;
    }

    public float getTotaltrabajador() {
        return totaltrabajador;
    }

    public void setTotaltrabajador(float totaltrabajador) {
        this.totaltrabajador = totaltrabajador;
    }

    public float getTotalem() {
        return totalem;
    }

    public void setTotalem(float totalem) {
        this.totalem = totalem;
    }

    public float getTotalsubt() {
        return totalsubt;
    }

    public void setTotalsubt(float totalsubt) {
        this.totalsubt = totalsubt;
    }

    public Boolean getTodos() {
        return todos;
    }

    public void setTodos(Boolean todos) {
        this.todos = todos;
    }

    public Boolean getSelec() {
        return selec;
    }

    public void setSelec(Boolean selec) {
        this.selec = selec;
    }

    public Utilidades getUtil() {
        return util;
    }

    public void setUtil(Utilidades util) {
        this.util = util;
    }

    public FacesContext getContextoJSF() {
        return contextoJSF;
    }

    public void setContextoJSF(FacesContext contextoJSF) {
        this.contextoJSF = contextoJSF;
    }

    public ELContext getContextoEL() {
        return contextoEL;
    }

    public void setContextoEL(ELContext contextoEL) {
        this.contextoEL = contextoEL;
    }

    public ExpressionFactory getReg() {
        return reg;
    }

    public void setReg(ExpressionFactory reg) {
        this.reg = reg;
    }

    public GestorAccidente getGestorAccidente() {
        return gestorAccidente;
    }

    public void setGestorAccidente(GestorAccidente gestorAccidente) {
        this.gestorAccidente = gestorAccidente;
    }    
    

    public void setItemsMotivos(ArrayList<SelectItem> itemsMotivos) {
        this.itemsMotivos = itemsMotivos;
    }   

    public PieChartModel getPieSubempresa() {
        return pieSubempresa;
    }

    public void setPieSubempresa(PieChartModel pieSubempresa) {
        this.pieSubempresa = pieSubempresa;
    }

    public ExpressionFactory getEf() {
        return ef;
    }

    public void setEf(ExpressionFactory ef) {
        this.ef = ef;
    }

    public HorizontalBarChartModel getHorizontalBarCausaInmediata() {
        return horizontalBarCausaInmediata;
    }

    public void setHorizontalBarCausaInmediata(HorizontalBarChartModel horizontalBarCausaInmediata) {
        this.horizontalBarCausaInmediata = horizontalBarCausaInmediata;
    }
    
}
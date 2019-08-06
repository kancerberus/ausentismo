/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;


import bd.UtilJSF;
import controlador.GestorConcurso;
import controlador.GestorEmpleado;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.Actividad;
import modelo.AdjuntosActividad;
import util.Utilidades;
import modelo.Concurso;
import modelo.Empleado;
import modelo.Empresa;
import modelo.GrupoConcurso;
import modelo.GrupoConcursoParticipantes;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.MoveEvent;
import org.primefaces.model.UploadedFile;
import util.UtilArchivo;


/**
 *
 * @author Andres
 */
@ManagedBean(name = "concursoBean")
@SessionScoped
public class UIConcurso implements Serializable {
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private String cedula;
    private Concurso concurso= new Concurso();
    private AdjuntosActividad adjuntoActividad1=new AdjuntosActividad();
    private AdjuntosActividad adjuntoActividad2=new AdjuntosActividad();
    private AdjuntosActividad adjuntoActividad3=new AdjuntosActividad();
    private AdjuntosActividad adjuntoActividad4=new AdjuntosActividad();
    private AdjuntosActividad adjuntoActividad5=new AdjuntosActividad();
    private GrupoConcurso grupoConcurso=new GrupoConcurso();    
    private GrupoConcursoParticipantes grupoParticipantes=new GrupoConcursoParticipantes();
    private String codGrupo;    
    private Empleado empleado=new Empleado();
    private Actividad actividad;
    private Empresa empresa=new Empresa();  
    private UploadedFile file;
    private Boolean captain;    
    
    
    private List<Actividad> listActividades=new ArrayList<>();
    private List<Concurso> listaConcurso=new ArrayList<>();
    private List<Concurso> listaConcursosGerente=new ArrayList<>();
    private ArrayList<SelectItem> listaConcursosEmpresas=new ArrayList<>();
    private ArrayList<SelectItem> listGruposConcurso=new ArrayList<>();
    private List<GrupoConcurso> listaGruposConcursos=new ArrayList<>();
    private List<GrupoConcursoParticipantes> listGruposParticipantes=new ArrayList<>();        
    private List<GrupoConcurso> listaEquipos=new ArrayList<>();
    
    private GestorConcurso gestorConcurso;
    
    public Utilidades util = new Utilidades();    
    private ExpressionFactory ef;
    public UIConcurso()  throws Exception {       
       concurso=new Concurso();
       concurso.setEmpresa(new Empresa());      
       grupoConcurso=new GrupoConcurso();
       grupoParticipantes.setEmpleado(new Empleado());
       grupoParticipantes.setGrupoConcurso(new GrupoConcurso());
       actividad=new Actividad();                 
       listaConcursosEmpresas=new ArrayList<>();
       contextoJSF = FacesContext.getCurrentInstance();
       contextoEL = contextoJSF.getELContext(); 
    }  
    
    @PostConstruct
    public void init() {
        this.cargarConcursos();
        this.cargarConcursosGerente();           
    }
    
    public void handleClose(CloseEvent event) {                
    }
     
    public void handleMove(MoveEvent event) {        
    }
    
    public void crearConcurso() throws Exception{        
        Boolean invalido = false;
        String msg = null;
        File carpeta;

        //ingreso de informacion al gestor
        gestorConcurso = new GestorConcurso();
        concurso.setEmpresa(empresa);
        try {
            //verificar que todas las cajas este llenas           
            if (concurso.getNombre().equals("") ) {
                msg = "Concurso sin nombre!";
                invalido = true;              
            }
         

            if (invalido == false) {       
                    Long codConcurso=gestorConcurso.nextval(GestorConcurso.CAMPAÑA_CONCURSO_COD_CONCURSO_SEQ);
                    concurso.setCodConcurso(codConcurso.toString());
                    Integer resultado = gestorConcurso.guardarConcurso(concurso);
                    carpeta=new File("C:/Concursos");
                    carpeta.mkdir();
                    
                    carpeta=new File("C:/Concursos/"+concurso.getEmpresa().getNitempresa());
                    carpeta.mkdir();
                    
                    carpeta=new File("C:/Concursos/"+concurso.getNombre());
                    carpeta.mkdir();

                    if (resultado > 0) {
                        util.mostrarMensaje("!! Concurso guardado !!");
                        concurso = new Concurso();
                        this.cargarConcursos();
                    } else {
                        util.mostrarMensaje("!! El concurso no pudo ser almacenado !!");
                    }
            } else {                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! El concurso no pudo ser almacenado !!");               
        }
    }
    
    public void crearGrupoConcurso() throws Exception{        
        Boolean invalido = false;
        String msg = null;
        File carpeta;

        //ingreso de informacion al gestor
        gestorConcurso = new GestorConcurso();
         
        Date hoy =new Date();
        try {
            grupoConcurso.setConcurso(concurso);
            //verificar que todas las cajas este llenas           
            if (grupoConcurso.getNombre().equals("") || grupoConcurso.getConcurso().getCodConcurso().equals("")) {
                util.mostrarMensaje("Grupo sin nombre!");
                invalido = true;              
            }
            concurso.getEmpresa();
            if (hoy.after(concurso.getFecha_limite_insc())) {
                util.mostrarMensaje("Fecha Limite De inscripcion Vencida!");
                msg = "Fecha Limite De inscripcion Vencida!"; 
                concurso=new Concurso();
                this.getListaConcursosEmpresas();                       
                invalido = true;              
            }
         

            if (invalido == false) {  
                    Long codGrupoSeq= gestorConcurso.nextval(GestorConcurso.CAMPAÑA_GRUPO_CONCURSO_COD_GRUPO_SEQ);
                    grupoConcurso.setCodGrupo(codGrupoSeq.toString());
                    Integer resultado = gestorConcurso.guardarGrupoConcurso(grupoConcurso);
                                        
                    carpeta=new File("C:/Concursos/"+concurso.getEmpresa().getNitempresa()+"/"+concurso.getNombre()+"/"+grupoConcurso.getNombre());
                    carpeta.mkdir();
                    if (resultado > 0) {
                        util.mostrarMensaje("!! Equipo guardado !!");
                        grupoConcurso = new GrupoConcurso();
                        this.cargarGruposConcursos();
                        this.cargarConcursosGerente();
                        concurso=new Concurso();
                        this.getListaConcursosEmpresas();
                    } else {
                        util.mostrarMensaje("!! El Equipo no pudo ser almacenado !!");
                    }
            } else {                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                     
            }            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! El Equipo no pudo ser almacenado !!");               
        }
    }
    
    
    public void subirItemConcurso() {         
        concurso = (Concurso) UtilJSF.getBean("varConcursos");            
        this.cargarActividades();
    }
    
    public void subirItemGrupoConcurso() {         
        grupoParticipantes.setGrupoConcurso((GrupoConcurso) UtilJSF.getBean("varEquipos")); 
        codGrupo=grupoParticipantes.getGrupoConcurso().getCodGrupo();
        concurso=grupoParticipantes.getGrupoConcurso().getConcurso();
        this.cargarListaGrupoParticipantes();
        
    }    
    
    public void subirItemActividad() {         
        actividad= (Actividad) UtilJSF.getBean("varActividades");       
    }
    
    public void agregarEmpleado() throws Exception{        
        Boolean invalido = false;
        String msg = null;        
        Date hoy=new Date();
        try {            
            
            //verificar que todas las cajas este llenas           
            if (empleado.getCedula() == null || codGrupo==null || empleado.getNombres().equals("")) {
                msg = "Ingrese Cedula!";
                invalido = true;
            }           
            
            
            this.grupoParticipantes.setEmpleado(empleado); 
            this.grupoParticipantes.getGrupoConcurso().setCodGrupo(codGrupo);            
            
            if(listGruposParticipantes.size()==(concurso.getParticipantes())){
                msg = "Cupo completo";                
                invalido = true;
            }
            
            if (invalido == false) {
                    Integer resultado = gestorConcurso.agregarEmpleado(grupoParticipantes);
                    if(grupoParticipantes.isCapitan()==true){
                        grupoParticipantes.setCapitan(false);
                        captain=false;
                    }
                    if (resultado > 0) {
                        util.mostrarMensaje("!! Participante agregado !!");
                        empleado = new Empleado();
                        this.cargarListaGrupoParticipantes();                        
                    } else {
                        util.mostrarMensaje("!! El participante no se pudo agregar !!");
                    }
            } else {
                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }
    }   
    
    public void cargarConcursos(){
        try {
            
            listaConcurso=new ArrayList<>();
            gestorConcurso=new GestorConcurso();
            
            listaConcurso.addAll(gestorConcurso.cargarConcursos());            
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }   
    }
    
    public void cargarGruposConcursos(){
        try {
            listaGruposConcursos=new ArrayList<>();
            gestorConcurso=new GestorConcurso();            
            
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
            listaGruposConcursos.addAll(gestorConcurso.cargarGruposConcursos(nitsesion));
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }   
    } 
    
    
    
    
    public void cargarConcursosGerente(){
        try {
            
            listaConcursosGerente=new ArrayList<>();
            gestorConcurso=new GestorConcurso();
            
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
            listaConcursosGerente.addAll(gestorConcurso.cargarConcursosGerente(nitsesion));
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }   
    }
    
    public void cargarActividades(){
        try {
            
            listActividades=new ArrayList<>();
            gestorConcurso=new GestorConcurso();            
            
            listActividades.addAll(gestorConcurso.cargarActividades(concurso.getCodConcurso()));                        
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }   
    }
    
    public void cargarListaGrupoParticipantes(){
        try {
            captain=false;
            listGruposParticipantes=new ArrayList<>();
            gestorConcurso=new GestorConcurso();            
            grupoConcurso.setCodGrupo(codGrupo);
            listGruposParticipantes.addAll(gestorConcurso.cargarListaGrupoParticipantes(grupoConcurso.getCodGrupo()));     
            
            for(int i=0;i<=listGruposParticipantes.size()-1;i++){
                if(listGruposParticipantes.get(i).isCapitan()==true){
                    captain=true;
                }
                        
            }
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }   
    }
    
    public void addMessage() {
        String summary = concurso.isEstado() ? "Activo" : "Inactivo";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
    
    public void crearActividad() throws Exception{        
        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        gestorConcurso = new GestorConcurso();

        try {
            actividad.setConcurso(new Concurso(concurso.getCodConcurso(), "", null, 0,false,null));
            //verificar que todas las cajas este llenas           
            if (actividad.getNombre().equals("")) {
                msg = "Actividad sin nombre!";
                invalido = true;              
            }
            if(actividad.getConcurso().getCodConcurso()==null){
                msg = "Cree Concurso!";
                invalido = true;              
            }
         

            if (invalido == false) {                                    
                    Integer resultado = gestorConcurso.guardarActividad(actividad);
                    this.cargarActividades();
                    if (resultado > 0) {
                        util.mostrarMensaje("!! Activdad guardada !!");
                        actividad = new Actividad();                        
                    } else {
                        util.mostrarMensaje("!! La actividad no pudo ser almacenada !!");
                    }
            } else {
                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }
    }
    
    
    public void buscarEmpleado() throws Exception{
        try {
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
            GestorEmpleado gestorEmpleado = new GestorEmpleado();
            Empleado em = gestorEmpleado.validarEmpleado(cedula,nitsesion);


            if(em!=null && em.isEstado() == true){
                empleado=em; 
                grupoParticipantes.setGrupoConcurso(grupoConcurso);
            }
            else {
                util.mostrarMensaje("La cedula no existe....");
                empleado =new Empleado();
            }
            this.cargarListaGrupoParticipantes();
        } catch (Exception ex) {
        Logger.getLogger(UIIncapacidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    public ArrayList<SelectItem> getListaConcursosEmpresas() throws Exception{
            
            try {
                
                gestorConcurso = new GestorConcurso();
                contextoJSF = FacesContext.getCurrentInstance();
                contextoEL = contextoJSF.getELContext();
                ef = contextoJSF.getApplication().getExpressionFactory();
                String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);                        
                
                
                ArrayList<Concurso> listaConcursos;
                listaConcursos = gestorConcurso.listarConcursos(nitsesion);
                listaConcursosEmpresas.clear();                
                
                for (int i = 0; i < listaConcursos.size(); i++) {
                        listaConcursosEmpresas.add(new SelectItem(listaConcursos.get(i).getCodConcurso(), listaConcursos.get(i).getNombre(), listaConcursos.get(i).getFecha_limite_insc().toString()));                        
                    }
                
                }
            catch (Exception ex) {
                        Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
                        util.mostrarMensaje(ex.getMessage()); 
                }  
            
                if(concurso.getCodConcurso()!=null){
                    for(int i=0;i<=listaConcurso.size()-1;i++){
                        if(concurso.getCodConcurso().equals(listaConcurso.get(i).getCodConcurso())){                    
                        concurso.setCodConcurso(listaConcurso.get(i).getCodConcurso());
                        concurso.setEmpresa(listaConcurso.get(i).getEmpresa());
                        concurso.setFecha_limite_insc(listaConcurso.get(i).getFecha_limite_insc());
                        concurso.setParticipantes(listaConcurso.get(i).getParticipantes());
                        concurso.setNombre(listaConcurso.get(i).getNombre());
                        this.getListaGrupoConcurso();
                        }
                    }
                }            
                return listaConcursosEmpresas;                
                
    }
    
    public ArrayList<SelectItem> getListaGrupoConcurso() throws Exception{
            
            try {
                gestorConcurso = new GestorConcurso();
                contextoJSF = FacesContext.getCurrentInstance();
                contextoEL = contextoJSF.getELContext();
                ef = contextoJSF.getApplication().getExpressionFactory();                
                
                ArrayList<GrupoConcurso> listaGrupoConcursos;
                listaGrupoConcursos = gestorConcurso.listarGrupoConcursos(concurso.getCodConcurso());
                listGruposConcurso.clear();
                for (int i = 0; i < listaGrupoConcursos.size(); i++) {                    
                        listGruposConcurso.add(new SelectItem(listaGrupoConcursos.get(i).getCodGrupo(), listaGrupoConcursos.get(i).getNombre()));
                    }
                }
            catch (Exception ex) {
                        Logger.getLogger(UIAusentismo.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return listGruposConcurso;
                
    }
    
    
    public void cargarLogo(FileUploadEvent event) {
        try {
            String ruta="C:/Concursos/"+actividad.getConcurso().getEmpresa().getNitempresa()+"/"+actividad.getConcurso().getNombre()+"/"+actividad.getGrupoConcurso().getNombre()+"/"+actividad.getNombre();                                    

            UtilArchivo.guardarStream(ruta + File.separator + event.getFile().getFileName(), event.getFile().getInputstream());
            this.file = event.getFile();
        } catch (IOException ex) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, ex);
           util.mostrarMensaje(ex.getMessage()); 
        }

    }

    public Boolean getCaptain() {
        return captain;
    }

    public void setCaptain(Boolean captain) {
        this.captain = captain;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public AdjuntosActividad getAdjuntoActividad1() {
        return adjuntoActividad1;
    }

    public void setAdjuntoActividad1(AdjuntosActividad adjuntoActividad1) {
        this.adjuntoActividad1 = adjuntoActividad1;
    }

    public AdjuntosActividad getAdjuntoActividad2() {
        return adjuntoActividad2;
    }

    public void setAdjuntoActividad2(AdjuntosActividad adjuntoActividad2) {
        this.adjuntoActividad2 = adjuntoActividad2;
    }

    public AdjuntosActividad getAdjuntoActividad3() {
        return adjuntoActividad3;
    }

    public void setAdjuntoActividad3(AdjuntosActividad adjuntoActividad3) {
        this.adjuntoActividad3 = adjuntoActividad3;
    }

    public AdjuntosActividad getAdjuntoActividad4() {
        return adjuntoActividad4;
    }

    public void setAdjuntoActividad4(AdjuntosActividad adjuntoActividad4) {
        this.adjuntoActividad4 = adjuntoActividad4;
    }

    public AdjuntosActividad getAdjuntoActividad5() {
        return adjuntoActividad5;
    }

    public void setAdjuntoActividad5(AdjuntosActividad adjuntoActividad5) {
        this.adjuntoActividad5 = adjuntoActividad5;
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

    public Utilidades getUtil() {
        return util;
    }

    public void setUtil(Utilidades util) {
        this.util = util;
    }

    public ExpressionFactory getEf() {
        return ef;
    }

    public void setEf(ExpressionFactory ef) {
        this.ef = ef;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setListaConcursosEmpresas(ArrayList<SelectItem> listaConcursosEmpresas) {
        this.listaConcursosEmpresas = listaConcursosEmpresas;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<GrupoConcursoParticipantes> getListGruposParticipantes() {
        return listGruposParticipantes;
    }

    public void setListGruposParticipantes(List<GrupoConcursoParticipantes> listGruposParticipantes) {
        this.listGruposParticipantes = listGruposParticipantes;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<GrupoConcurso> getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(List<GrupoConcurso> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    public ArrayList<SelectItem> getListGruposConcurso() {
        return listGruposConcurso;
    }

    public void setListGruposConcurso(ArrayList<SelectItem> listGruposConcurso) {
        this.listGruposConcurso = listGruposConcurso;
    }

    public List<GrupoConcurso> getListaGruposConcursos() {
        return listaGruposConcursos;
    }

    public void setListaGruposConcursos(List<GrupoConcurso> listaGruposConcursos) {
        this.listaGruposConcursos = listaGruposConcursos;
    }


    public GrupoConcursoParticipantes getGrupoParticipantes() {
        return grupoParticipantes;
    }

    public void setGrupoParticipantes(GrupoConcursoParticipantes grupoParticipantes) {
        this.grupoParticipantes = grupoParticipantes;
    }
    
    public GrupoConcurso getGrupoConcurso() {
        return grupoConcurso;
    }

    public void setGrupoConcurso(GrupoConcurso grupoConcurso) {
        this.grupoConcurso = grupoConcurso;
    }

    public List<Concurso> getListaConcursosGerente() {
        return listaConcursosGerente;
    }

    public void setListaConcursosGerente(List<Concurso> listaConcursosGerente) {
        this.listaConcursosGerente = listaConcursosGerente;
    }

    public List<Concurso> getListaConcurso() {
        return listaConcurso;
    }

    public void setListaConcurso(List<Concurso> listaConcurso) {
        this.listaConcurso = listaConcurso;
    }

    public GestorConcurso getGestorConcurso() {
        return gestorConcurso;
    }

    public void setGestorConcurso(GestorConcurso gestorConcurso) {
        this.gestorConcurso = gestorConcurso;
    }

    public List<Actividad> getListActividades() {
        return listActividades;
    }

    public void setListActividades(List<Actividad> listActividades) {
        this.listActividades = listActividades;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
/**
 *
 * @author Andres
 */
public class GestorPrincipal implements Serializable {

    private boolean regAusentismo = false;
    private boolean NuevoEmpleado = false;  
    private boolean ListarEmpleado = false;
    private boolean NuevaEmpresa = false;
    private boolean NuevaSubEmpresa = false;
    private boolean NuevoUsuario = false;
    private boolean CargosyEpsbd = false;
    private boolean consultaAusentismo = false;
    private boolean finAusentismo = false;
    private boolean informeAusentismo = false;
    private boolean informeausentismoEmpresa = false;
    private boolean reporteEmpleado = false;
    private boolean modificarEmpleado = false;    
    private boolean moverEmpleado=false;
    private boolean reporteGrafica=false;
    private boolean actualizarRegistros=false;
    private boolean ausentismoEmpleado=false;
    private boolean modificarUsuario=false;
    private boolean regIncapacidad=false;
    private boolean regAccidente=false;
    private boolean indAccidente=false;
    private boolean crearConcurso=false;
    private boolean concursos=false;
    private boolean equipos=false;
    private boolean jueces=false;
    private boolean indAusentismo=false;
    private boolean modificarMinimo=false;
    private boolean reporteEmpleadoAdmin=false;
    private boolean perfilSociodemografico=false;
    private boolean reportePerfilSocioDemografico=false;
    
    
    
    

    public String selGeneral(String opcion) {
        switch (opcion) {
            case "reg_ausentismo":
                setRegAusentismo(true);
                setConsultaAusentismo(false);
                setFinAusentismo(false);
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "consultar_ausentismo":
                setRegAusentismo(false);
                setConsultaAusentismo(true);
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;                

            case "finalizar_reg":
                setRegAusentismo(false);
                setConsultaAusentismo(false);
                setFinAusentismo(true);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;                 
            
            case "crear_empleado":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(true);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;  
                
            case "listar_empleado":
                setRegAusentismo(false);
                setConsultaAusentismo(false);
                setFinAusentismo(false);
                setNuevoEmpleado(false);
                setListarEmpleado(true);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;  
                
            case "crear_empresa":
                setRegAusentismo(false);
                setConsultaAusentismo(false);
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(true);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;  
                
            case "crear_usuario":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(true);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;  
                
            case "cargosyepsbd":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(true);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false); 
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;  
                
            case "crear_subempresa":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(true);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "informe_ausentismo":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(true);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "informe_por_empresa":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(true);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "reporte_empleado":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(true);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "modificar_empleado":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(true);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;                

                
            case "mover_empleado":                
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(true);
                setReporteGrafica(false);
                setActualizarRegistros(false); 
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
                
            case "reporte_grafica":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(true);
                setActualizarRegistros(false);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
                
            case "actualizar_registros":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(true);
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "ausentismo_empleado":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(true);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "modificar_usuario":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(true);
                setRegIncapacidad(false);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "reg_incapacidad":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(true);
                setRegAccidente(false);
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "reg_accidente":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(true);   
                setIndAccidente(false);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "indicadores_accidente":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(true);
                setCrearConcurso(false);
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                
                break;
                
            case "crear_concurso":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(false);
                setCrearConcurso(true);                
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "concursos":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(false);
                setCrearConcurso(false); 
                setConcursos(true);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "equipos":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(false);
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(true);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "jueces":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(false);
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(false);
                setJueces(true);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "indicadores_ausentismo":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(false);
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(true);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "modificar_minimo":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(false);
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(true);
                setReporteEmpleadoAdmin(false);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
                
            case "reporte_empleado_admin":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(false);
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(true);
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(false);
                break;
                
            case "perfil_sociodemografico":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(false);
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);         
                setPerfilSociodemografico(true);
                setReportePerfilSocioDemografico(false);
                break;
                
                case "reporte_perfil_socio":
                setRegAusentismo(false);
                setConsultaAusentismo(false); 
                setFinAusentismo(false);                
                setNuevoEmpleado(false);
                setListarEmpleado(false);
                setNuevaEmpresa(false);
                setNuevoUsuario(false);
                setCargosyEpsbd(false);
                setNuevaSubEmpresa(false);
                setInformeAusentismo(false);
                setInformeausentismoEmpresa(false);
                setReporteEmpleado(false);
                setModificarEmpleado(false);                
                setMoverEmpleado(false);
                setReporteGrafica(false);
                setActualizarRegistros(false);                
                setAusentismoEmpleado(false);
                setModificarUsuario(false);
                setRegIncapacidad(false);
                setRegAccidente(false);                
                setIndAccidente(false);
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setIndAusentismo(false);
                setModificarMinimo(false);
                setReporteEmpleadoAdmin(false);         
                setPerfilSociodemografico(false);
                setReportePerfilSocioDemografico(true);
                break;
                
                
        }
        return "";
    }

    public boolean isReportePerfilSocioDemografico() {
        return reportePerfilSocioDemografico;
    }

    public void setReportePerfilSocioDemografico(boolean reportePerfilSocioDemografico) {
        this.reportePerfilSocioDemografico = reportePerfilSocioDemografico;
    }

    public boolean isPerfilSociodemografico() {
        return perfilSociodemografico;
    }

    public void setPerfilSociodemografico(boolean perfilSociodemografico) {
        this.perfilSociodemografico = perfilSociodemografico;
    }

    public boolean isReporteEmpleadoAdmin() {
        return reporteEmpleadoAdmin;
    }

    public void setReporteEmpleadoAdmin(boolean reporteEmpleadoAdmin) {
        this.reporteEmpleadoAdmin = reporteEmpleadoAdmin;
    }

    public boolean isModificarMinimo() {
        return modificarMinimo;
    }

    public void setModificarMinimo(boolean modificarMinimo) {
        this.modificarMinimo = modificarMinimo;
    }

    public boolean isIndAusentismo() {
        return indAusentismo;
    }

    public void setIndAusentismo(boolean indAusentismo) {
        this.indAusentismo = indAusentismo;
    }

    public boolean isJueces() {
        return jueces;
    }

    public void setJueces(boolean jueces) {
        this.jueces = jueces;
    }

    public boolean isEquipos() {
        return equipos;
    }

    public void setEquipos(boolean equipos) {
        this.equipos = equipos;
    }

    public boolean isConcursos() {
        return concursos;
    }

    public void setConcursos(boolean concursos) {
        this.concursos = concursos;
    }

    public boolean isCrearConcurso() {
        return crearConcurso;
    }

    public void setCrearConcurso(boolean crearConcurso) {
        this.crearConcurso = crearConcurso;
    }

    public boolean isIndAccidente() {
        return indAccidente;
    }

    public void setIndAccidente(boolean indAccidente) {
        this.indAccidente = indAccidente;
    }

    public boolean isRegAccidente() {
        return regAccidente;
    }

    public void setRegAccidente(boolean regAccidente) {
        this.regAccidente = regAccidente;
    }

    public boolean isRegIncapacidad() {
        return regIncapacidad;
    }

    public void setRegIncapacidad(boolean regIncapacidad) {
        this.regIncapacidad = regIncapacidad;
    }

    public boolean isModificarUsuario() {
        return modificarUsuario;
    }

    public void setModificarUsuario(boolean modificarUsuario) {
        this.modificarUsuario = modificarUsuario;
    }

    public boolean isAusentismoEmpleado() {
        return ausentismoEmpleado;
    }

    public void setAusentismoEmpleado(boolean ausentismoEmpleado) {
        this.ausentismoEmpleado = ausentismoEmpleado;
    }
    
    public boolean isActualizarRegistros() {
        return actualizarRegistros;
    }

    public void setActualizarRegistros(boolean actualizarRegistros) {
        this.actualizarRegistros = actualizarRegistros;
    } 

    public boolean isCargosyEpsbd() {
        return CargosyEpsbd;
    }

    public void setCargosyEpsbd(boolean CargosyEpsbd) {
        this.CargosyEpsbd = CargosyEpsbd;
    }
    
    public boolean isNuevoUsuario() {
        return NuevoUsuario;
    }

    public void setNuevoUsuario(boolean NuevoUsuario) {
        this.NuevoUsuario = NuevoUsuario;
    }
    
    public boolean isNuevaEmpresa() {
        return NuevaEmpresa;
    }

    public void setNuevaEmpresa(boolean NuevaEmpresa) {
        this.NuevaEmpresa = NuevaEmpresa;
    }    

    public boolean isRegAusentismo() {
        return regAusentismo;
    }

    public boolean isReporteGrafica() {
        return reporteGrafica;
    }

    public void setReporteGrafica(boolean reporteGrafica) {
        this.reporteGrafica = reporteGrafica;
    }    

    public void setRegAusentismo(boolean regAusentismo) {
        this.regAusentismo = regAusentismo;
    }

    public boolean isNuevoEmpleado() {
        return NuevoEmpleado;
    }   

    public void setNuevoEmpleado(boolean NuevoEmpleado) {
        this.NuevoEmpleado = NuevoEmpleado;
    }

    public boolean isListarEmpleado() {
        return ListarEmpleado;
    }

    public void setListarEmpleado(boolean ListarEmpleado) {
        this.ListarEmpleado = ListarEmpleado;
    }

    public boolean isConsultaAusentismo() {
        return consultaAusentismo;
    }

    public void setConsultaAusentismo(boolean consultaAusentismo) {
        this.consultaAusentismo = consultaAusentismo;
    } 

    public boolean isFinAusentismo() {
        return finAusentismo;
    }

    public void setFinAusentismo(boolean finAusentismo) {
        this.finAusentismo = finAusentismo;
    }   

    public boolean isNuevaSubEmpresa() {
        return NuevaSubEmpresa;
    }

    public void setNuevaSubEmpresa(boolean NuevaSubEmpresa) {
        this.NuevaSubEmpresa = NuevaSubEmpresa;
    }

    public boolean isInformeAusentismo() {
        return informeAusentismo;
    }

    public void setInformeAusentismo(boolean informeAusentismo) {
        this.informeAusentismo = informeAusentismo;
    }

    public boolean isInformeausentismoEmpresa() {
        return informeausentismoEmpresa;
    }

    public void setInformeausentismoEmpresa(boolean informeausentismoEmpresa) {
        this.informeausentismoEmpresa = informeausentismoEmpresa;
    } 

    public boolean isReporteEmpleado() {
        return reporteEmpleado;
    }

    public void setReporteEmpleado(boolean reporteEmpleado) {
        this.reporteEmpleado = reporteEmpleado;
    }

    public boolean isModificarEmpleado() {
        return modificarEmpleado;
    }

    public void setModificarEmpleado(boolean modificarEmpleado) {
        this.modificarEmpleado = modificarEmpleado;
    }
    public boolean isMoverEmpleado() {
        return moverEmpleado;
    }

    public void setMoverEmpleado(boolean moverEmpleado) {
        this.moverEmpleado = moverEmpleado;
    }
}

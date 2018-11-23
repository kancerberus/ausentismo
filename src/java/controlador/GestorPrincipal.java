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
                break;
        }

        return "";
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

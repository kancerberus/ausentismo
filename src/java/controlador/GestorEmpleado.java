/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.AusentismoDAO;
import bd.EmpleadoDAO;
import java.util.Collection;
import java.util.List;
import modelo.Empleado;


/**
 *
 * @author Andres
 */
public class GestorEmpleado extends Gestor {

    public GestorEmpleado() throws Exception {
        super();
    }
    public List<Empleado> listarEmpleados(String nitsesion) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.listarEmpleados(nitsesion);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Empleado> listarEmpleadosAdmin() throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.listarEmpleadosAdmin();
        } finally {
            cerrarConexion();
        }
    }

    
    public Empleado buscarEmpleado(String cedula, String nitsesion, Integer codPerfil) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.buscarEmpleado(cedula, nitsesion, codPerfil);
            } finally {
            cerrarConexion();
        }
    }    
    
    public Empleado buscarempleadoAdmin(String cedula) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.buscarempleadoAdmin(cedula);
            } finally {
            cerrarConexion();
        }
    }
    
    public Integer modificarSubempresaempleado(Empleado empleado) throws Exception{
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.modificarSubempresaempleado(empleado);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistEdad(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistEdad(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistECivil(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistECivil(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistSexo(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistSexo(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistNumPerCargo(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistNumPerCargo(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    
    public Collection<? extends Empleado> cargarDistNivelEscolaridad(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistNivelEscolaridad(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistTendenciaVivienda(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistTendenciaVivienda(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistUsoTiempoLibre(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistUsoTiempoLibre(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistPromedioIngreso(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistPromedioIngreso(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistAntiguedadEmpresa(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistAntiguedadEmpresa(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistAntiguedadCargo(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistAntiguedadCargo(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistTipoContrato(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistTipoContrato(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistActivEmpresa(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistActivEmpresa(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistFuma(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistFuma(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistDiagnosticadoEnfermedad(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarDistDiagnosticadoEnfermedad(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistConsumoAlcohol(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarConsumoAlcohol(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Empleado> cargarDistPracticaDeporte(String nitem, String nitsubem) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarPracticaDeporte(nitem, nitsubem);
        } finally {
            cerrarConexion();
        }
    }


    public Empleado validarEmpleado(String cedula, String nitsesion) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.validarEmpleado(cedula,nitsesion);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer validarEmpleadoActualizacion(String cedula, String nitsesion, String anoActualizador) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.validarEmpleadoActualizacion(cedula,nitsesion,anoActualizador);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer validarEmpleadoActualizacionEPS() throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.validarEmpleadoActualizacionEPS();
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarEmpleado(Empleado empleado, String nitsesion) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.guardarEmpleado(empleado, nitsesion);
           
        } finally {
            cerrarConexion();
        }       
    }
    
    public Integer guardarPerfilSocioDemografico(Empleado empleado, String nitsesion) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.guardarPerfilSocioDemografico(empleado, nitsesion);
           
        } finally {
            cerrarConexion();
        }       
    }
    
    public Integer modificarEmpleado(Empleado empleado) throws Exception{
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.modificarEmpleado(empleado);
        } finally {
            cerrarConexion();
        }
    }

    public Collection<? extends Empleado> cargarPerfilSocioList(String nitsub,String nitem) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.cargarPerfilSocioList(nitsub, nitem);
        } finally {
            cerrarConexion();
        }
    }
}

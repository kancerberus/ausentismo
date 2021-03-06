/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import bd.AusentismoDAO;
import bd.EmpleadoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.scene.chart.PieChart;
import modelo.Ausentismo;
import modelo.Empleado;
import modelo.Incapacidad;
import modelo.Mes;
import modelo.Motivo;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author diego
 */
public class GestorAusentismo extends Gestor implements Serializable{
    
    private AusentismoDAO ausentismoDAO;
    
    public GestorAusentismo() throws Exception{
        super();
    }
    
    public Collection<? extends Ausentismo> cargarDistribucionCargos(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AusentismoDAO ausentismoDAO= new AusentismoDAO(conexion);
            return ausentismoDAO.cargarDistribucionCargos(nitem, nitsubem, mesdesde, meshasta, ano);
        } finally {
            cerrarConexion();
        }
    }
    
    public String cargarFechaActualizadoSalario() throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.cargarFechaActualizadoSalario();
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> cargarDistribucionPorOrigen(String nitem,String nitsubem,String selmesdesde,String selmeshasta,String selano) throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.cargarDistribucionPorOrigen(nitem, nitsubem,selmesdesde,selmeshasta,selano);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> cargarDistribucionLabora(String nitem,String nitsubem,String selmesdesde,String selmeshasta,String selano) throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.cargarDistribucionLabora(nitem, nitsubem,selmesdesde,selmeshasta,selano);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> cargarDistribucionTipoIncapacidad(String nitem,String nitsubem,String selmesdesde,String selmeshasta,String selano) throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.cargarDistribucionTipoIncapacidad(nitem, nitsubem,selmesdesde,selmeshasta,selano);
        } finally {
            cerrarConexion();
        }
    }
    
    public ArrayList<Incapacidad> cargarDistribucionGrupoDiagnostico(String nitem,String nitsubem,String selmesdesde,String selmeshasta,String selano) throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.cargarDistribucionGrupoDiagnostico(nitem, nitsubem,selmesdesde,selmeshasta,selano);
        } finally {
            cerrarConexion();
        }
    }
    
    public ArrayList<Ausentismo> cargarDistribucionPorCentroTrabajo(String nitem,String selmesdesde,String selmeshasta,String selano) throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.cargarDistribucionPorCentroTrabajo(nitem,selmesdesde,selmeshasta,selano);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> listarAusentismos(String nitsesion) throws Exception {
       try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.listarAusentismos(nitsesion);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> ausentismoAnomes(String nitsesion,String selmesano,String selano) throws Exception {
       try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.ausentismoAnomes(nitsesion, selmesano, selano);
        } finally {
            cerrarConexion();
        }
    }    

    
    public List<Ausentismo> ausentismoanomesEmpresa(String nitem,String nitsubem, String selmesdesde, String selmeshasta,String selano,String selmotivo) throws Exception {
       try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.ausentismoanomesEmpresa(nitem, nitsubem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> ausentismoanomesEmpleado(String cedula,String nitem, String selmesdesde, String selmeshasta,String selano, String selmotivo) throws Exception {
       try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.ausentismoanomesEmpleado(cedula, nitem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    } 
    
    public List<Ausentismo> ausentismoanomesEmpleadoTotales(String cedula,String nitem, String selmesdesde, String selmeshasta,String selano, String motivos) throws Exception {
       try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.ausentismoanomesEmpleadoTotales(cedula, nitem,selmesdesde, selmeshasta, selano, motivos);
        } finally {
            cerrarConexion();
        }
    } 
    
    public List<Ausentismo> pieausentismoanomesEmpresa(String nitem,String nitsubem, String selmesdesde, String selmeshasta,String selano,String selmotivo) throws Exception {
       try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.pieausentismoanomesEmpresa(nitem, nitsubem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> pieausentismoanomesEmpleado(String cedula, String nitem, String selmesdesde, String selmeshasta,String selano, String selmotivo) throws Exception {
       try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.pieanomesEmpleado(cedula, nitem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    }   
    
    
    
    public List<Ausentismo> pieporSubempresa(String nitem,String nitsubem, String selmesdesde, String selmeshasta,String selano, String selmotivo) throws Exception {
       try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.pieporSubempresa(nitem, nitsubem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    } 
    
    public Ausentismo buscarAusentismo(String cod_registro, String nitsesion) throws Exception {
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.buscarAusentismo(cod_registro, nitsesion );
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarAusentismo(Ausentismo ausentismo) throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.guardarAusentismo(ausentismo);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer eliminarRegistro(String codReg)throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.eliminarRegistro(codReg);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer horasanoAcumuladas(String cedula) throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.horasAcumuladas(cedula);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer modificarAusentismo(Ausentismo ausentismo) throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.modificarAusentismo(ausentismo);
        } finally {
            cerrarConexion();
        }
    }    
    
        
    /*public Integer actualizarAusentismo(Ausentismo ausentismo) throws Exception{
        try {
            abrirConexion();
            AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
            return ausentismoDAO.actualizarAusentismo(ausentismo);
        } finally {
            cerrarConexion();
        }
    }*/
    
    public ArrayList<Motivo> listarMotivos() throws Exception {
        try {
                abrirConexion();
                AusentismoDAO ausentismoDAO = new AusentismoDAO(conexion);
                return ausentismoDAO.listarMotivos();
            } finally {
                cerrarConexion();
            }
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import bd.ConcursoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import modelo.Actividad;
import modelo.Concurso;
import modelo.GrupoConcurso;
import modelo.GrupoConcursoParticipantes;

/**
 *
 * @author diego
 */
public class GestorConcurso extends Gestor implements Serializable{
    
    public static String CAMPAÑA_CONCURSO_COD_CONCURSO_SEQ = "campaña.campaña_concurso_cod_concurso_seq";
    public static String CAMPAÑA_GRUPO_CONCURSO_COD_GRUPO_SEQ = "campaña.campaña_grupo_concurso_cod_grupo_seq";
    
    
    private ConcursoDAO concursoDAO;
    
    public GestorConcurso() throws Exception{
        super();
    }   
    
    public Long nextval(String secuencia) throws Exception {
        try {
            this.abrirConexion();
            return new ConcursoDAO(this.conexion).nextval(secuencia);
        } finally {
            this.cerrarConexion();
        }
    }
        
    public Integer guardarActividad(Actividad actividad) throws Exception{
        try {
            abrirConexion();
            ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
            return concursoDAO.guardarActividad(actividad);
        } finally {
            cerrarConexion();
        }
    }
    
    
    public Integer agregarEmpleado(GrupoConcursoParticipantes grupoParticipantes) throws Exception{
        try {
            abrirConexion();
            ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
            return concursoDAO.agregarEmpleado(grupoParticipantes);
        } finally {
            cerrarConexion();
        }
    }

    public Integer guardarConcurso(Concurso concurso) throws Exception {            
        try {
            abrirConexion();
            ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
            return concursoDAO.guardarConcurso(concurso);
        } finally {
            cerrarConexion();
        }    
    }
    
    public Integer guardarGrupoConcurso(GrupoConcurso grupoConcurso) throws Exception {            
        try {
            abrirConexion();
            ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
            return concursoDAO.guardarGrupoConcurso(grupoConcurso);
        } finally {
            cerrarConexion();
        }    
    }
    
    public ArrayList<Concurso> cargarConcursos() throws Exception {
        try {
                abrirConexion();
                ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
                return concursoDAO.cargarConcursos();
            } finally {
                cerrarConexion();
            }
    }
    
    public ArrayList<GrupoConcurso> cargarGruposConcursos(String nitsesion) throws Exception {
        try {
                abrirConexion();
                ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
                return concursoDAO.cargarGruposConcursos(nitsesion);
            } finally {
                cerrarConexion();
            }
    }
    
    public ArrayList<Concurso> listarConcursos(String nitsesion) throws Exception {
        try {
            abrirConexion();
            ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
            return concursoDAO.listarConcursos(nitsesion);
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<GrupoConcurso> listarGrupoConcursos(String codConcurso) throws Exception {
        try {
            abrirConexion();
            ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
            return concursoDAO.listarGruposConcursos(codConcurso);
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<Concurso> cargarConcursosGerente(String nitsesion) throws Exception {
        try {
                abrirConexion();
                ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
                return concursoDAO.cargarConcursosGerente(nitsesion);
            } finally {
                cerrarConexion();
            }
    }


    public ArrayList<Actividad> cargarActividades(String codConcurso) throws Exception {
        try {
                abrirConexion();
                ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
                return concursoDAO.cargarActividades(codConcurso);
            } finally {
                cerrarConexion();
            }
    }
    
    public ArrayList<GrupoConcursoParticipantes> cargarListaGrupoParticipantes(String codConcurso) throws Exception {
        try {
                abrirConexion();
                ConcursoDAO concursoDAO = new ConcursoDAO(conexion);
                return concursoDAO.cargarListaGrupoParticipantes(codConcurso);
            } finally {
                cerrarConexion();
            }
    }

    public static String getCAMPAÑA_GRUPO_CONCURSO_COD_GRUPO_SEQ() {
        return CAMPAÑA_GRUPO_CONCURSO_COD_GRUPO_SEQ;
    }

    public static void setCAMPAÑA_GRUPO_CONCURSO_COD_GRUPO_SEQ(String CAMPAÑA_GRUPO_CONCURSO_COD_GRUPO_SEQ) {
        GestorConcurso.CAMPAÑA_GRUPO_CONCURSO_COD_GRUPO_SEQ = CAMPAÑA_GRUPO_CONCURSO_COD_GRUPO_SEQ;
    }

    public static String getCAMPAÑA_CONCURSO_COD_CONCURSO_SEQ() {
        return CAMPAÑA_CONCURSO_COD_CONCURSO_SEQ;
    }

    public static void setCAMPAÑA_CONCURSO_COD_CONCURSO_SEQ(String CAMPAÑA_CONCURSO_COD_CONCURSO_SEQ) {
        GestorConcurso.CAMPAÑA_CONCURSO_COD_CONCURSO_SEQ = CAMPAÑA_CONCURSO_COD_CONCURSO_SEQ;
    }
    
}

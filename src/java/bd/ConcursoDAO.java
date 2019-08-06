/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Actividad;
import modelo.Concurso;
import modelo.Empleado;
import modelo.Empresa;
import modelo.GrupoConcurso;
import modelo.GrupoConcursoParticipantes;
/**
 *
 * @author Andres
 */
public class ConcursoDAO {
    private Connection conexion;

    public ConcursoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    

    /**
     * @return the conexion
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * @param conexion the conexion to set
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Integer guardarActividad(Actividad actividad) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO campaña.actividad ("
                        + " nombre, observacion, fecha_limite, fk_cod_concurso) "                        
                        + "VALUES ("
                        + "'" + actividad.getNombre() + "',"
                        + "'" + actividad.getObservacion() + "',"
                        + "'" + actividad.getFechaLimite() + "',"
                        + "'" + actividad.getConcurso().getCodConcurso() + "')";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer agregarEmpleado(GrupoConcursoParticipantes grupoParticipantes) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO campaña.grupo_concurso_participantes ("
                        + "     cod_grupo, fk_cedula, capitan ) "                        
                        + "VALUES ("
                        + "'" + grupoParticipantes.getGrupoConcurso().getCodGrupo()+ "',"
                        + "'" + grupoParticipantes.getEmpleado().getCedula()+ "',"
                        + "'" +grupoParticipantes.isCapitan()+ "')";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    
    public Integer guardarConcurso(Concurso concurso) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO campaña.concurso ("
                        + " cod_concurso,fk_nitempresa, nombre, participantes, estado, fecha_limite_insc) "                        
                        + "VALUES ("
                        + "'" + concurso.getCodConcurso() +"',"
                        + "'" + concurso.getEmpresa().getNitempresa()+ "',"
                        + "'" + concurso.getNombre() + "',"
                        + "'" + concurso.getParticipantes() + "',"
                        + "'" + concurso.isEstado() + "',"
                        + "'" + concurso.getFecha_limite_insc() + "')";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public Long nextval(String secuencia) throws SQLException {

        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder("select nextval('" + secuencia + "')");
            rs = consulta.ejecutar(sql);
            rs.next();
            return rs.getLong(1);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public Integer guardarGrupoConcurso(GrupoConcurso grupoConcurso) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO campaña.grupo_concurso ("
                        + " cod_grupo, cod_concurso, nombre) "                        
                        + "VALUES ("
                        + "'" + grupoConcurso.getCodGrupo() + "', "
                        + "'" + grupoConcurso.getConcurso().getCodConcurso()+ "',"
                        + "'" + grupoConcurso.getNombre() + "' )";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<Concurso> cargarConcursos() throws SQLException {
        Concurso concurso;
        ArrayList<Concurso> listaConcursos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_concurso, conc.nombre nomconc, em.nombre nombre,fk_nitempresa, participantes, estado, fecha_limite_insc " +
                        "FROM campaña.concurso conc " +
                        "JOIN empresa em on(em.nitempresa=conc.fk_nitempresa) " +
                        "ORDER BY cod_concurso";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                concurso =new Concurso();    
                concurso.setCodConcurso(rs.getString("cod_concurso"));
                concurso.setNombre(rs.getString("nomconc"));
                concurso.setParticipantes(rs.getInt("participantes"));
                concurso.setEstado(rs.getBoolean("estado"));
                concurso.setFecha_limite_insc(rs.getDate("fecha_limite_insc"));
                concurso.setEmpresa(new Empresa(rs.getString("fk_nitempresa"), rs.getString("nombre")));
                listaConcursos.add(concurso);
            }
            return listaConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<GrupoConcurso> cargarGruposConcursos(String nitsesion) throws SQLException {
        GrupoConcurso  grupoConcurso;
        ArrayList<GrupoConcurso> listaGrupoConcursos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_grupo, gconc.cod_concurso codgconc, conc.nombre nomconc, gconc.nombre nombregconc" +
                        " FROM campaña.grupo_concurso gconc " +
                        " JOIN campaña.concurso conc on(conc.cod_concurso=gconc.cod_concurso) "+
                        " WHERE conc.fk_nitempresa='"+nitsesion+"'" +
                        " ORDER BY conc.cod_concurso";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                grupoConcurso =new GrupoConcurso();
                grupoConcurso.setCodGrupo(rs.getString("cod_grupo"));
                grupoConcurso.setConcurso(new Concurso(rs.getString("codgconc"), rs.getString("nomconc")));                
                grupoConcurso.setNombre(rs.getString("nombregconc"));
                listaGrupoConcursos.add(grupoConcurso);
            }
            return listaGrupoConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<Concurso> listarConcursos(String nitsesion) throws SQLException {
        Concurso concurso;        
        ArrayList<Concurso> listaConcursos = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_concurso, nombre, fk_nitempresa, participantes, estado, fecha_limite_insc "
                    + " FROM campaña.concurso "
                    + " WHERE fk_nitempresa='"+nitsesion+"' ";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                concurso=new Concurso();
                concurso.setCodConcurso(dt.getString("cod_concurso"));
                concurso.setNombre(dt.getString("nombre"));
                concurso.setParticipantes(dt.getInt("participantes"));
                concurso.setEstado(dt.getBoolean("estado"));
                concurso.setFecha_limite_insc(dt.getDate("fecha_limite_insc"));
                
                listaConcursos.add(concurso);
            }
            return listaConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    
    public ArrayList<GrupoConcurso> listarGruposConcursos(String codConcurso) throws SQLException {
        GrupoConcurso grupoConcurso;
        ArrayList<GrupoConcurso> listaGrupoConcursos = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_grupo, nombre "
                    + " FROM campaña.grupo_concurso "
                    + " WHERE cod_concurso='"+codConcurso+"' ";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                grupoConcurso=new GrupoConcurso();
                grupoConcurso.setCodGrupo(dt.getString("cod_grupo"));
                grupoConcurso.setNombre(dt.getString("nombre"));  
                grupoConcurso.setConcurso(new Concurso(codConcurso, ""));
                
                listaGrupoConcursos.add(grupoConcurso);
            }
            return listaGrupoConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<Concurso> cargarConcursosGerente(String nitsesion) throws SQLException {
        Concurso concurso;
        ArrayList<Concurso> listaConcursos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_concurso, conc.nombre nomconc, em.nombre nombre,fk_nitempresa, participantes, estado, fecha_limite_insc " +
                        "FROM campaña.concurso conc " +
                        "JOIN empresa em on(em.nitempresa=conc.fk_nitempresa) "+
                        " WHERE fk_nitempresa='"+nitsesion+"' " +                    
                        "ORDER BY cod_concurso";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                concurso =new Concurso();    
                concurso.setCodConcurso(rs.getString("cod_concurso"));
                concurso.setNombre(rs.getString("nomconc"));
                concurso.setParticipantes(rs.getInt("participantes"));
                concurso.setEstado(rs.getBoolean("estado"));
                concurso.setFecha_limite_insc(rs.getDate("fecha_limite_insc"));
                concurso.setEmpresa(new Empresa(rs.getString("fk_nitempresa"), rs.getString("nombre")));
                listaConcursos.add(concurso);
            }
            return listaConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<Actividad> cargarActividades(String codConcurso) throws SQLException {
        Actividad actividad;
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_actividad, act.nombre nomact , observacion,fecha_limite, con.cod_concurso codcon, con.nombre nomcon " +
                        "FROM campaña.actividad act " +
                        "JOIN campaña.concurso con on(con.cod_concurso=act.fk_cod_concurso) "+
                        " WHERE cod_concurso='"+codConcurso+"'" +
                        "ORDER BY cod_actividad";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                actividad =new Actividad();
                actividad.setCodActividad(rs.getString("cod_actividad"));
                actividad.setNombre(rs.getString("nomact"));
                actividad.setObservacion(rs.getString("observacion"));
                actividad.setFechaLimite(rs.getDate("fecha_limite"));
                actividad.setConcurso(new Concurso(rs.getString("codcon"), rs.getString("nomcon"), null, 0, false, null));
                listaActividades.add(actividad);
            }
            return listaActividades;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<GrupoConcursoParticipantes> cargarListaGrupoParticipantes(String codConcurso) throws SQLException {
        GrupoConcursoParticipantes grupoParticipantes;
        ArrayList<GrupoConcursoParticipantes> listaGrupoParticipantes = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT fk_cedula, e.nombres nome, e.apellidos apee, capitan " +
                        " FROM  campaña.grupo_concurso_participantes gp " +
                        " JOIN empleado e on(e.cedula=gp.fk_cedula) "+
                        "  WHERE cod_grupo='"+codConcurso+"' " +
                        " ORDER BY cod_grupo_participantes ";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                grupoParticipantes =new GrupoConcursoParticipantes();
                grupoParticipantes.setEmpleado(new Empleado(rs.getString("fk_cedula"), rs.getString("nome"), rs.getString("apee")));
                grupoParticipantes.setCapitan(rs.getBoolean("capitan"));
                
                listaGrupoParticipantes.add(grupoParticipantes);
            }
            return listaGrupoParticipantes;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
}


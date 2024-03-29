/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Ausentismo;
import modelo.InformeAusentismo;
import modelo.Motivo;
import modelo.Configuracion;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import modelo.Cargo;
import modelo.Empleado;
import modelo.Empresa;
import modelo.GrupoCie10;
import modelo.Sexo;
import modelo.TipoIncapacidad;
import modelo.Incapacidad;
import modelo.SubEmpresa;
import org.primefaces.model.chart.PieChartModel;
import vista.UIListas;

/**
 *
 * @author diego
 */
public class AusentismoDAO {    
    private Connection conexion;
    private UIListas uilistas;
    
    
    public AusentismoDAO(Connection conexion) throws Exception{
        this.conexion = conexion;
        this.uilistas = new UIListas();
    }
    
    public ArrayList<Ausentismo> cargarDistribucionLabora(String nitem,String nitsubem,String selmesdesde,String selmeshasta,String selano) throws Exception{
        Ausentismo au;
        ArrayList<Ausentismo> listaAusentismo = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;        

        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')";             
            queryfechainc = "to_char(fecha_inicial,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
            queryfechainc = "to_char(fecha_inicial,'yyyy/mm')"; 
        }
        
        try {
            
            //nitempresa y año 
            if(nitem!=null && selano!=null && nitsubem==null){
                
                consulta = new Consulta(getConexion());
                String sql
                        = " select e.cod_det_lista_sexo codsexo, dl.nombre nomsexo,  count(ra.cod_regausentismo) casos " +
                            " from registro_ausentismo ra  " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join det_lista dl on (dl.cod_det_lista=e.cod_det_lista_sexo) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+                            
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa) " +                               
                            " where em.nitempresa='"+nitem+"' and " + queryfecha + " = '" + selfecha + "' " +
                            " group by e.cod_det_lista_sexo, dl.nombre";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(rs.getInt("casos"), new Sexo(rs.getString("codsexo"), rs.getString("nomsexo")), 0);                                                                     
                    listaAusentismo.add(au);
                }            
            }
            
            if (selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null ) {                                        
                
                consulta = new Consulta(getConexion());
                String sql
                        = " select e.cod_det_lista_sexo codsexo, dl.nombre nomsexo,  count(ra.cod_regausentismo) casos " +
                            " from registro_ausentismo ra  " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join det_lista dl on (dl.cod_det_lista=e.cod_det_lista_sexo) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+                            
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa) " +                               
                            " where em.nitempresa='"+nitem+"' "+
                            " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+ 
                            " group by e.cod_det_lista_sexo, dl.nombre";                            

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(rs.getInt("casos"), new Sexo(rs.getString("codsexo"), rs.getString("nomsexo")), 0);                                                                     
                    listaAusentismo.add(au);
                }            
                
                
            }
            
            if (nitem != null && nitsubem!=null && selano!=null) {
                
                consulta = new Consulta(getConexion());
                String sql
                        = " select e.cod_det_lista_sexo codsexo, dl.nombre nomsexo,  count(ra.cod_regausentismo) casos " +
                            " from registro_ausentismo ra  " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join det_lista dl on (dl.cod_det_lista=e.cod_det_lista_sexo) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+                            
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa) " +                               
                            " where e.nitsubempresa = '"+nitsubem+"' and " + queryfecha + " = '" + selfecha + "' " +
                            " group by e.cod_det_lista_sexo, dl.nombre";                        

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(rs.getInt("casos"), new Sexo(rs.getString("codsexo"), rs.getString("nomsexo")), 0);                                                                     
                    listaAusentismo.add(au);
                }            
                
                
            }
            
            if (nitem != null && nitsubem!=null && selano==null && selmesdesde != null && selmeshasta != null) {
                
                consulta = new Consulta(getConexion());
                String sql
                       = " select e.cod_det_lista_sexo codsexo, dl.nombre nomsexo,  count(ra.cod_regausentismo) casos " +
                            " from registro_ausentismo ra  " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join det_lista dl on (dl.cod_det_lista=e.cod_det_lista_sexo) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+                            
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa) " +                               
                            " where e.nitsubempresa = '"+nitsubem+"' "+
                            " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+ 
                            " group by e.cod_det_lista_sexo, dl.nombre";                        

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(rs.getInt("casos"), new Sexo(rs.getString("codsexo"), rs.getString("nomsexo")), 0);                                                                     
                    listaAusentismo.add(au);
                }            
                
            }
            
            return listaAusentismo;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
            
    }
    
    
    public ArrayList<Ausentismo> cargarDistribucionTipoIncapacidad(String nitem,String nitsubem,String selmesdesde,String selmeshasta,String selano) throws Exception{
        Ausentismo au;
        ArrayList<Ausentismo> listaAusentismo = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;    
        Integer totCasos=0;

        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";             
            queryfechainc = "to_char(fecha_inicial,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";
            queryfechainc = "to_char(fecha_inicial,'yyyy/mm')"; 
        }
        
        try {
            
            //nitempresa y año 
            if(nitem!=null && selano!=null && nitsubem==null){
                
                consulta = new Consulta(getConexion());
                String sql
                        = "  select dl.nombre nombre, count(ri.cod_reg_incapacidad) casos  " +
                            " from registro_incapacidad ri  " +
                            " inner join det_lista dl on(dl.cod_det_lista=ri.cod_det_lista_tipo_incapacidad)  " +
                            " inner join empleado e on (e.cedula=fk_cedula)   " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa)  " +
                            " where cod_det_lista_tipo_incapacidad in ('8','9','10') and em.nitempresa='"+nitem+"' and to_char(fecha_registro,'yyyy') = '"+selfecha+"'  " +
                            " group by dl.nombre  ";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(new TipoIncapacidad("", rs.getString("nombre")), rs.getInt("casos"),0);
                    totCasos+=au.getCasos();
                    au.setTotCasos(totCasos);
                    au.setPorcentaje((au.getCasos()/totCasos)*100);
                    listaAusentismo.add(au);                    
                }                   
                
                
            }
            
            if (selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null ) {                                        
                
                consulta = new Consulta(getConexion());
                String sql
                        = "  select dl.nombre nombre, count(ri.cod_reg_incapacidad) casos  " +
                            " from registro_incapacidad ri  " +
                            " inner join det_lista dl on(dl.cod_det_lista=ri.cod_det_lista_tipo_incapacidad)  " +
                            " inner join empleado e on (e.cedula=fk_cedula)   " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa)  " +
                            " where cod_det_lista_tipo_incapacidad in ('8','9','10') and em.nitempresa='"+nitem+"' "+
                            " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+ 
                            " group by dl.nombre  ";
                        
                        
                                                    

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(new TipoIncapacidad("", rs.getString("nombre")), rs.getInt("casos"),0);
                    totCasos+=au.getCasos();
                    au.setTotCasos(totCasos);
                    au.setPorcentaje((au.getCasos()/totCasos)*100);
                    listaAusentismo.add(au);                    
                }                   
                
                
            }
            
            if (nitem != null && nitsubem!=null && selano!=null) {
                
                consulta = new Consulta(getConexion());
                String sql
                        = "  select dl.nombre nombre, count(ri.cod_reg_incapacidad) casos  " +
                            " from registro_incapacidad ri  " +
                            " inner join det_lista dl on(dl.cod_det_lista=ri.cod_det_lista_tipo_incapacidad)  " +
                            " inner join empleado e on (e.cedula=fk_cedula)   " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa)  " +
                            " where cod_det_lista_tipo_incapacidad in ('8','9','10') and e.nitsubempresa = '"+nitsubem+"' and " + queryfecha + " = '" + selfecha + "' " +
                            " group by dl.nombre  ";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(new TipoIncapacidad("", rs.getString("nombre")), rs.getInt("casos"),0);
                    totCasos+=au.getCasos();
                    au.setTotCasos(totCasos);
                    au.setPorcentaje((au.getCasos()/totCasos)*100);
                    listaAusentismo.add(au);                    
                }                   
                
                
            }
            
            if (nitem != null && nitsubem!=null && selano==null && selmesdesde != null && selmeshasta != null) {
                
                consulta = new Consulta(getConexion());
                String sql
                       = "  select dl.nombre nombre, count(ri.cod_reg_incapacidad) casos  " +
                            " from registro_incapacidad ri  " +
                            " inner join det_lista dl on(dl.cod_det_lista=ri.cod_det_lista_tipo_incapacidad)  " +
                            " inner join empleado e on (e.cedula=fk_cedula)   " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa)  " +
                            " where cod_det_lista_tipo_incapacidad in ('8','9','10') and e.nitsubempresa = '"+nitsubem+"' " +
                            " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+ 
                            " group by dl.nombre  ";                       
                        
                                              

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(new TipoIncapacidad("", rs.getString("nombre")), rs.getInt("casos"),0);
                    totCasos+=au.getCasos();
                    au.setTotCasos(totCasos);
                    au.setPorcentaje((au.getCasos()/totCasos)*100);
                    listaAusentismo.add(au);
                }            
                
            }
            
            return listaAusentismo;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
            
    }
    
    public ArrayList<Incapacidad> cargarDistribucionGrupoDiagnostico(String nitem,String nitsubem,String selmesdesde,String selmeshasta,String selano) throws SQLException{
        Incapacidad in;
        ArrayList<Incapacidad> listaIncapacidad = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;    
        Integer totCasos=0;

        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";             
            queryfechainc = "to_char(fecha_inicial,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";
            queryfechainc = "to_char(fecha_inicial,'yyyy/mm')"; 
        }
        
        try {
            
            //nitempresa y año 
            if(nitem!=null && selano!=null && nitsubem==null){
                
                consulta = new Consulta(getConexion());
                String sql
                        = "select count (ri.cod_reg_incapacidad) casos, gcie10.cod_grupo_cie10 codcie10 , gcie10.nombre nom" +
                            " from registro_incapacidad ri " +
                            " inner join cie10 c10 on (c10.cod_cie10=ri.cod_cie10) " +
                            " inner join cat_cie10 catc10 on (catc10.cod_cat_cie10=c10.cod_cat_cie10) " +
                            " inner join grupo_cie10 gcie10 on (gcie10.cod_grupo_cie10=c10.cod_grupo_cie10) " +
                            " inner join empleado e on (e.cedula=fk_cedula)   " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa)  " +
                            " where em.nitempresa='"+nitem+"' and to_char(fecha_registro,'yyyy') = '"+selfecha+"'  " +
                            " group by gcie10.nombre, gcie10.cod_grupo_cie10, c10.cod_cie10"+
                            " order by c10.cod_cie10 ";                        

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    in = new Incapacidad();
                    in.setGrupoCie10(new GrupoCie10(rs.getString("codcie10"), rs.getString("nom")));
                    in.setCasos(rs.getInt("casos"));
                    listaIncapacidad.add(in);
                }                                   
            }
            
            if (selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null ) {                                        
                
                consulta = new Consulta(getConexion());
                String sql
                        = "select count (ri.cod_reg_incapacidad) casos, gcie10.cod_grupo_cie10 codcie10 , gcie10.nombre nom" +
                            " from registro_incapacidad ri " +
                            " inner join cie10 c10 on (c10.cod_cie10=ri.cod_cie10) " +
                            " inner join cat_cie10 catc10 on (catc10.cod_cat_cie10=c10.cod_cat_cie10) " +
                            " inner join grupo_cie10 gcie10 on (gcie10.cod_grupo_cie10=c10.cod_grupo_cie10) " +
                            " inner join empleado e on (e.cedula=fk_cedula)   " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa)  " +
                            " where cod_det_lista_tipo_incapacidad in ('8','9','10') and em.nitempresa='"+nitem+"' "+
                            " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+ 
                            " group by gcie10.nombre, gcie10.cod_grupo_cie10, c10.cod_cie10"+
                            " order by c10.cod_cie10 "; 

                rs = consulta.ejecutar(sql);

                 while (rs.next()) {
                    in = new Incapacidad();
                    in.setGrupoCie10(new GrupoCie10(rs.getString("codcie10"), rs.getString("nom")));
                    in.setCasos(rs.getInt("casos"));
                    listaIncapacidad.add(in);
                }                   
                
                
            }
            
            if (nitem != null && nitsubem!=null && selano!=null) {
                
                consulta = new Consulta(getConexion());
                String sql
                        = "select count (ri.cod_reg_incapacidad) casos, gcie10.cod_grupo_cie10 codcie10 , gcie10.nombre nom" +
                            " from registro_incapacidad ri " +
                            " inner join cie10 c10 on (c10.cod_cie10=ri.cod_cie10) " +
                            " inner join cat_cie10 catc10 on (catc10.cod_cat_cie10=c10.cod_cat_cie10) " +
                            " inner join grupo_cie10 gcie10 on (gcie10.cod_grupo_cie10=c10.cod_grupo_cie10) " +
                            " inner join empleado e on (e.cedula=fk_cedula)   " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa)  " +
                            " where e.nitsubempresa = '"+nitsubem+"' and " + queryfecha + " = '" + selfecha + "' " +                            
                            " group by gcie10.nombre, gcie10.cod_grupo_cie10, c10.cod_cie10"+
                            " order by c10.cod_cie10 ";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    in = new Incapacidad();
                    in.setGrupoCie10(new GrupoCie10(rs.getString("codcie10"), rs.getString("nom")));
                    in.setCasos(rs.getInt("casos"));
                    listaIncapacidad.add(in);
                }                   
                
                
            }
            
            if (nitem != null && nitsubem!=null && selano==null && selmesdesde != null && selmeshasta != null) {
                
                consulta = new Consulta(getConexion());
                String sql
                       = "select count (ri.cod_reg_incapacidad) casos, gcie10.cod_grupo_cie10 codcie10 , gcie10.nombre nom" +
                            " from registro_incapacidad ri " +
                            " inner join cie10 c10 on (c10.cod_cie10=ri.cod_cie10) " +
                            " inner join cat_cie10 catc10 on (catc10.cod_cat_cie10=c10.cod_cat_cie10) " +
                            " inner join grupo_cie10 gcie10 on (gcie10.cod_grupo_cie10=c10.cod_grupo_cie10) " +
                            " inner join empleado e on (e.cedula=fk_cedula)   " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa)  " +
                            " where e.nitsubempresa = '"+nitsubem+"' " +
                            " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+                             
                            " group by gcie10.nombre, gcie10.cod_grupo_cie10, c10.cod_cie10"+
                            " order by c10.cod_cie10 ";                                              

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    in = new Incapacidad();
                    in.setGrupoCie10(new GrupoCie10(rs.getString("codcie10"), rs.getString("nom")));
                    in.setCasos(rs.getInt("casos"));
                    listaIncapacidad.add(in);
                }            
                
            }
            
            return listaIncapacidad;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Collection<? extends Ausentismo> cargarDistribucionCargos(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Ausentismo> listaDistribucionCargos = new ArrayList<>();        
        
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(ra.fechapermiso,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(ra.fechapermiso,'yyyy/mm')";            
        }
        
        try {                
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " SELECT count(cod_regausentismo) ausentismos, e.cargo cargo, car.cargo nomcargo " +
                    " FROM registro_ausentismo ra " +                    
                    " JOIN empleado e ON (e.cedula=ra.fk_cedula) " +
                    " JOIN cargo car ON (car.cod_cargo=e.cargo) " +
                    " WHERE e.cargo<>'0' " +
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                    " and " + queryfecha + " = '" + selfecha + "'"+
                    " GROUP BY e.cargo, car.cargo "
                    
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {                   
                    Ausentismo ausentismo=new Ausentismo(new Cargo(rs.getString("cargo"), rs.getString("nomcargo")), rs.getInt("ausentismos"));
                    listaDistribucionCargos.add(ausentismo);
                }
            }
            
            if(nitem != null && selano != null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " SELECT count(cod_regausentismo) ausentismos, e.cargo cargo, car.cargo nomcargo " +
                    " FROM registro_ausentismo ra " +                    
                    " JOIN empleado e ON (e.cedula=ra.fk_cedula) " +
                    " JOIN cargo car ON (car.cod_cargo=e.cargo) " +
                    " WHERE e.cargo<>'0' " +
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                    " GROUP BY e.cargo, car.cargo "
                    
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {                   
                    Ausentismo ausentismo=new Ausentismo(new Cargo(rs.getString("cargo"), rs.getString("nomcargo")), rs.getInt("ausentismos"));
                    listaDistribucionCargos.add(ausentismo);
                }
            
            }            
            
            if(selmesdesde!=null && selmeshasta!=null){
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " SELECT count(cod_regausentismo) ausentismos, e.cargo cargo, car.cargo nomcargo " +
                    " FROM registro_ausentismo ra " +                    
                    " JOIN empleado e ON (e.cedula=ra.fk_cedula) " +
                    " JOIN cargo car ON (car.cod_cargo=e.cargo) " +
                    " WHERE e.cargo<>'0' " +
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+
                    " GROUP BY e.cargo, car.cargo ");
                    
                rs = consulta.ejecutar(sql);
                while (rs.next()) {                   
                    Ausentismo ausentismo=new Ausentismo(new Cargo(rs.getString("cargo"), rs.getString("nomcargo")), rs.getInt("ausentismos"));
                    listaDistribucionCargos.add(ausentismo);
                }
            }           
            
            return listaDistribucionCargos;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<Ausentismo> cargarDistribucionPorCentroTrabajo(String nitem,String selmesdesde,String selmeshasta,String selano) throws SQLException{
        Ausentismo au;
        ArrayList<Ausentismo> listaAusentismo = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;    
        Integer totCasos=0;

        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')";             
            queryfechainc = "to_char(fecha_inicial,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
            queryfechainc = "to_char(fecha_inicial,'yyyy/mm')"; 
        }
        
        try {
            
            //nitempresa y año 
            if(nitem!=null && selano!=null ){
                
                consulta = new Consulta(getConexion());
                String sql
                        = " select count(cod_regausentismo) casos, se.nombre nom, se.nitsubempresa nitsub " +
                            " from registro_ausentismo " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa) " +
                            " where em.nitempresa='"+nitem+"' and to_char(fechapermiso,'yyyy') = '"+selfecha+"'  " +
                            " group by se.nombre,se.nitsubempresa ";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au=new Ausentismo();
                    au.setCasos(rs.getInt("casos"));
                    au.setSubempresa(new SubEmpresa(rs.getString("nitsub"),rs.getString("nom")));
                    listaAusentismo.add(au);                    
                }                                   
            }
            
            if (selmesdesde != null && selmeshasta != null && nitem != null ) {                                        
                
                consulta = new Consulta(getConexion());
                String sql
                        = " select count(cod_regausentismo) casos, se.nombre nom, se.nitsubempresa nitsub " +
                            " from registro_ausentismo " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa) " +                            
                            " where em.nitempresa='"+nitem+"' and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+ 
                            " group by se.nombre,se.nitsubempresa ";

                rs = consulta.ejecutar(sql);

                 while (rs.next()) {
                    au=new Ausentismo();
                    au.setCasos(rs.getInt("casos"));
                    au.setSubempresa(new SubEmpresa(rs.getString("nitsub"),rs.getString("nom")));
                    listaAusentismo.add(au);                    
                }                                   
                
            }
                
            
            
            return listaAusentismo;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public Integer eliminarRegistro(String codReg) throws Exception{
       Consulta consulta = null;
        Integer resultado;  
        Date hoy =new Date();
        //Sentencia SQL para guardar el registro
        String sql ;    
        
   
        try {
            consulta = new Consulta(getConexion());
            
                sql = " DELETE "
                        + " from registro_ausentismo "
                        + " where cod_regausentismo='"+codReg+"'";
                
            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }            
                
    }
    
    
    public String cargarFechaActualizadoSalario()throws Exception{
        ResultSet rs;
        Consulta consulta = null;
        String ano="";
        try {
            consulta = new Consulta(getConexion());
                String sql
                       = "select to_char(fecha_actualizado,'yyyy') as fecActualizado"
                        + " from configuracion ";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                 ano=rs.getString("fecActualizado");
                }          
                return ano;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
        
    }
    
    
    
    
    
    public ArrayList<Ausentismo> cargarDistribucionPorOrigen(String nitem,String nitsubem,String selmesdesde,String selmeshasta,String selano) throws Exception{
        Ausentismo au;
        ArrayList<Ausentismo> listaAusentismo = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;        

        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')";             
            queryfechainc = "to_char(fecha_inicial,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
            queryfechainc = "to_char(fecha_inicial,'yyyy/mm')"; 
        }
        
        try {
            
            //nitempresa y año 
            if(nitem!=null && selano!=null && nitsubem==null){
                consulta = new Consulta(getConexion());
                String sql
                        = " select ra.fk_cod_motivo codmotivo, mp.nombre_motivo nommotivo, count (ra.cod_regausentismo) casos,  sum(cast(ra.tiempohoras as float)) dias " +
                            " from registro_ausentismo ra "+
                            "inner join motivopermiso mp on(mp.cod_motivo=ra.fk_cod_motivo) " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa) " +                            
                            " where fk_cod_motivo in ('1','5','2')  and  em.nitempresa='"+nitem+"' and " + queryfecha + " = '" + selfecha + "' " +
                            " group by ra.fk_cod_motivo, mp.nombre_motivo " +
                            " order by ra.fk_cod_motivo ";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(new Motivo(rs.getString("codmotivo"), rs.getString("nommotivo")), rs.getInt("casos"), rs.getFloat("dias"));                        

                    float dias=au.getDiasIncapacidad()/24;                                

                    listaAusentismo.add(new Ausentismo(au.getMotivo(), au.getCasos(), dias));
                }            
            }
            
            if (selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null ) {                                        
                
                consulta = new Consulta(getConexion());
                String sql
                        = " select ra.fk_cod_motivo codmotivo, mp.nombre_motivo nommotivo, count (ra.cod_regausentismo) casos,  sum(cast(ra.tiempohoras as float)) dias " +
                            " from registro_ausentismo ra " +
                            " inner join motivopermiso mp on(mp.cod_motivo=ra.fk_cod_motivo) " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " +
                            " inner join empresa em on(em.nitempresa=se.fk_nitempresa) " +                            
                            " where fk_cod_motivo in ('1','5','2')  and  em.nitempresa='"+nitem+"' "+
                            " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+ 
                            " group by ra.fk_cod_motivo, mp.nombre_motivo " +
                            " order by ra.fk_cod_motivo ";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(new Motivo(rs.getString("codmotivo"), rs.getString("nommotivo")), rs.getInt("casos"), rs.getFloat("dias"));                        

                    float dias=au.getDiasIncapacidad()/24;                                

                    listaAusentismo.add(new Ausentismo(au.getMotivo(), au.getCasos(), dias));
                }
                
                
            }
            
            if (nitem != null && nitsubem!=null && selano!=null) {
                
                consulta = new Consulta(getConexion());
                String sql
                        = " select ra.fk_cod_motivo codmotivo, mp.nombre_motivo nommotivo, count (ra.cod_regausentismo) casos,  sum(cast(ra.tiempohoras as float)) dias " +
                            " from registro_ausentismo ra "+
                            " inner join motivopermiso mp on(mp.cod_motivo=ra.fk_cod_motivo) " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+                            
                            " where fk_cod_motivo in ('1','5','2')  and e.nitsubempresa = '"+nitsubem+"' and " + queryfecha + " = '" + selfecha + "' " +
                            " group by ra.fk_cod_motivo, mp.nombre_motivo " +
                            " order by ra.fk_cod_motivo ";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(new Motivo(rs.getString("codmotivo"), rs.getString("nommotivo")), rs.getInt("casos"), rs.getFloat("dias"));                        

                    float dias=au.getDiasIncapacidad()/24;                                

                    listaAusentismo.add(new Ausentismo(au.getMotivo(), au.getCasos(), dias));
                }            
                
                
            }
            
            if (nitem != null && nitsubem!=null && selano==null && selmesdesde != null && selmeshasta != null) {
                
                consulta = new Consulta(getConexion());
                String sql
                        = " select ra.fk_cod_motivo codmotivo, mp.nombre_motivo nommotivo, count (ra.cod_regausentismo) casos,  sum(cast(ra.tiempohoras as float)) dias " +
                            " from registro_ausentismo ra " +
                            " inner join motivopermiso mp on(mp.cod_motivo=ra.fk_cod_motivo) " +
                            " inner join empleado e on (e.cedula=fk_cedula) " +
                            " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " +                            
                            " where fk_cod_motivo in ('1','5','2')  and e.nitsubempresa = '"+nitsubem+"' "+
                            " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+ 
                            " group by ra.fk_cod_motivo, mp.nombre_motivo " +
                            " order by ra.fk_cod_motivo ";

                rs = consulta.ejecutar(sql);

                while (rs.next()) {
                    au = new Ausentismo(new Motivo(rs.getString("codmotivo"), rs.getString("nommotivo")), rs.getInt("casos"), rs.getFloat("dias"));                        

                    float dias=au.getDiasIncapacidad()/24;                                

                    listaAusentismo.add(new Ausentismo(au.getMotivo(), au.getCasos(), dias));
                }
                
            }
            
            return listaAusentismo;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
            
    }
    
    public ArrayList<Ausentismo> listarAusentismos(String nitsesion) throws SQLException {
        Ausentismo au;
        ArrayList<Ausentismo> listaAusentismo = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;           
        
        
        
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = "select e.nitsubempresa, ra.cod_regausentismo codigo, ra.tiempohoras, ra.fk_cedula cedula,e.nombres nombres,e.apellidos apellidos,ra.fechapermiso fechaper,mo.nombre_motivo motivo " 
                    + "from registro_ausentismo ra " 
                    + "inner join empleado e on (cedula=fk_cedula) " 
                    + "inner join motivopermiso mo on (cod_motivo=fk_cod_motivo) "                    
                    + "where ra.estado = 'A' and e.nitsubempresa ='" + nitsesion + "' "
                    + "order by cod_regausentismo";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                au = new Ausentismo();
                au.setCod_registro(rs.getString("codigo"));
                au.getEmpleado().setCedula(rs.getString("cedula"));
                au.getEmpleado().setNombres(rs.getString("nombres"));
                au.getEmpleado().setApellidos(rs.getString("apellidos"));
                au.setFecha_permiso(rs.getDate("fechaper"));
                au.setMotivoausentismo(rs.getString("motivo"));
                au.setTiempo_horas(rs.getString("tiempohoras"));

                listaAusentismo.add(au);
            }
            return listaAusentismo;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<Ausentismo> ausentismoAnomes(String nitsesion,String selmesano, String selano) throws SQLException {

        Ausentismo au;            
        ArrayList<Ausentismo> listaAusentismo = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String queryfecha = null;
        String queryfechainc = null;
        Double totaleps = 0.00;

        if (selmesano == null){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')";
            queryfechainc="to_char(fecha_inicial,'yyyy')";
        }else{
            selfecha = selmesano;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
            queryfechainc="to_char(fecha_inicial,'yyyy/mm')";
        }


        try {
            consulta = new Consulta(getConexion());
            String sql
                    = "SELECT fechapermiso, e.nombres nombres, e.apellidos apellidos, e.sueldo_mes sueldo, " +
                    " e.nitsubempresa,tiempohoras, m.nombre_motivo nom_motivo, m.tipo tipo,ra.eps eps, " +
                    " ra.arl arl, ra.empleador empleador, ra.trabajador trabajador, ra.total total " +
                    " FROM registro_ausentismo ra " +
                    " INNER JOIN empleado e on (cedula=fk_cedula) " +
                    " INNER JOIN motivopermiso m on (cod_motivo=fk_cod_motivo) " +
                    " WHERE e.nitsubempresa ='" + nitsesion + "' AND " + queryfecha + "='" + selfecha + "' "+
                    " UNION ALL " +
                    " SELECT ri.fecha_inicial, e.nombres nombres, e.apellidos apellidos, e.sueldo_mes sueldo, " +
                    " e.nitsubempresa,tiempohoras, m.nombre_motivo nom_motivo, m.tipo tipo,ri.eps eps, " +
                    " ri.arl arl, ri.empleador empleador, ri.trabajador trabajador, ri.total total " +
                    " FROM registro_incapacidad ri " +
                    " INNER JOIN empleado e on (cedula=fk_cedula) " +
                    " INNER JOIN motivopermiso m on (cod_motivo=fk_cod_motivo) " +                    
                    " WHERE e.nitsubempresa ='" + nitsesion + "' AND " + queryfechainc + "='" + selfecha + "' "+
                    " ORDER BY fechapermiso";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                au = new Ausentismo();


                String nomfecha = "";
                String formato = "MM";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
                String fecha = simpleDateFormat.format(rs.getDate("fechapermiso"));

                //trae el nombre del mes
                nomfecha = uilistas.convertirMeses(fecha);
                au.setFecha_permiso(rs.getDate("fechapermiso"));
                au.getEmpleado().setNombres(rs.getString("nombres"));
                au.getEmpleado().setApellidos(rs.getString("apellidos"));
                au.getEmpleado().setSueldo_mes(rs.getInt("sueldo"));
                au.getEmpleado().setNitsubempresa(rs.getString("nitsubempresa"));
                au.setTiempo_horas(rs.getString("tiempohoras"));
                au.getMotivo().setNombrem(rs.getString("nom_motivo"));
                au.setMes(nomfecha);
                au.getMotivo().setTipo(rs.getString("tipo"));
                au.setEps(rs.getDouble("eps"));
                au.setArl(rs.getDouble("arl"));
                au.setEmpleador(rs.getDouble("empleador"));
                au.setTrabajador(rs.getDouble("trabajador"));
                au.setTotal(rs.getDouble("total"));                                         

                listaAusentismo.add(au);
            }      
            return listaAusentismo;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    } 
    
    public ArrayList<Ausentismo> ausentismoanomesEmpresa(String nitem,String nitsubem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {

        Ausentismo au;        
        String thconv;
        ArrayList<Ausentismo> listaausentismoEmpresa = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;
        Double totaleps = 0.00;
        String sql="";
        

        
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')";             
            queryfechainc = "to_char(fecha_inicial,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
            queryfechainc = "to_char(fecha_inicial,'yyyy/mm')"; 
        }
                            
                //Valida que llegue año y empresa
                if (nitem != null && selano != null) {
                    sql = "select  se.nombre as nombre, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) "                             
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                            +"and " + queryfecha + " = '" + selfecha + "' " 
                            +"group by se.nombre, to_char(fechapermiso,'mm') "
                            + " UNION ALL "+
                            "SELECT se.nombre as nombre, to_char(fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " +
                            "from registro_incapacidad " +
                            "inner join empleado e on (e.cedula=fk_cedula) " +
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " +
                            "where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') " +
                            "and " + queryfechainc + " = '"+selfecha+"' "+
                            "group by se.nombre, to_char(fecha_inicial,'mm')";                    
                }
                //Valida que llegue año y empresa y motivopermiso
                if (nitem != null && selano != null && selmotivo!=null) {
                    sql = "select se.nombre as nombre, to_char(fechapermiso,'mm') as fechaper, "
                            +"sum(cast(tiempohoras as float)) as tothoras, sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') and fk_cod_motivo='"+selmotivo+"' "
                            +"and " + queryfecha + " = '" + selfecha + "' "
                            +"group by se.nombre, to_char(fechapermiso,'mm'), fk_cod_motivo "
                            + " UNION ALL "
                            +"select se.nombre as nombre, to_char(fecha_inicial,'mm') as fechaper, "
                            +"sum(cast(tiempohoras as float)) as tothoras, sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula) inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') and fk_cod_motivo='"+selmotivo+"' "
                            +"and " + queryfechainc + " = '" + selfecha + "' "
                            +"group by se.nombre, to_char(fecha_inicial,'mm'), fk_cod_motivo";
                }
                
                //Valida que llegue nitsubempresa nitempresa y año
                if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                    sql= "select se.nombre,to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "+
                            "sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "+
                            "round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "+
                            "from registro_ausentismo "+
                            "inner join empleado e on (e.cedula=fk_cedula) "+
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                            "where e.nitsubempresa = '"+nitsubem+"'"+
                            "and " + queryfecha + " = '" + selfecha + "'"+
                            "group by se.nombre, to_char(fechapermiso,'mm') "+
                            " UNION ALL "+
                            "select se.nombre,to_char(fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "+
                            "sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "+
                            "round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "+
                            "from registro_incapacidad "+
                            "inner join empleado e on (e.cedula=fk_cedula) "+
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                            "where e.nitsubempresa = '"+nitsubem+"'"+
                            "and " + queryfechainc + " = '" + selfecha + "'"+
                            "group by se.nombre, to_char(fecha_inicial,'mm')";
                    
                }
                //Valida que llegue nitsubempresa nitempresa año y motivo
                if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null && selmotivo!=null) {
                    sql= "select se.nombre,to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  "
                            +"where e.nitsubempresa = '"+nitsubem+"' and fk_cod_motivo='"+selmotivo+"' "
                            +" and " + queryfecha + " = '" + selfecha + "'"
                            +" group by se.nombre, to_char(fechapermiso,'mm'), fk_cod_motivo "
                            +" UNION ALL "
                            +"select se.nombre,to_char(fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  "
                            +"where e.nitsubempresa = '"+nitsubem+"' and fk_cod_motivo='"+selmotivo+"' "
                            + "and " + queryfechainc + " = '" + selfecha + "'"
                            +"group by se.nombre, to_char(fecha_inicial,'mm'), fk_cod_motivo";
                            
                    
                }
                
                //Valida que llegue nitsubempresa, mes desde, mes hasta y año
                if (selmesdesde != null && selmeshasta != null && nitsubem != null) {                                        
                    sql= "select se.nombre,to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "+
                            "sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "+
                            "round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "+
                            "from registro_ausentismo "+
                            "inner join empleado e on (e.cedula=fk_cedula) "+
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                            "where e.nitsubempresa = '"+nitsubem+"'" +
                            "and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " +
                            "group by se.nombre, to_char(fechapermiso,'mm') "+
                            " UNION ALL "+
                            "select se.nombre,to_char(fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "+
                            "sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "+
                            "round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "+
                            "from registro_incapacidad "+
                            "inner join empleado e on (e.cedula=fk_cedula) "+
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                            "where e.nitsubempresa = '"+nitsubem+"'" +
                            "and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' " +
                            "group by se.nombre, to_char(fecha_inicial,'mm')";
                                         
                }
                
                //Valida que llegue nitsubempresa, mes desde, mes hasta y año y motivo
                if (selmesdesde != null && selmeshasta != null && nitsubem != null && selmotivo!=null) {
                    sql= "select se.nombre,to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "+
                            "sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "+
                            "round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "+
                            "from registro_ausentismo "+
                            "inner join empleado e on (e.cedula=fk_cedula) "+
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                            "where e.nitsubempresa = '"+nitsubem+"'" +
                            "and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " +
                            "and fk_cod_motivo='"+selmotivo+"'"+
                            "group by se.nombre, to_char(fechapermiso,'mm'), fk_cod_motivo "+
                            " UNION ALL "+
                            "select se.nombre,to_char(fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "+
                            "sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "+
                            "round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "+
                            "from registro_incapacidad "+
                            "inner join empleado e on (e.cedula=fk_cedula) "+
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                            "where e.nitsubempresa = '"+nitsubem+"'" +
                            "and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' " +
                            "and fk_cod_motivo='"+selmotivo+"'"+
                            "group by se.nombre, to_char(fecha_inicial,'mm'), fk_cod_motivo";
                            
                            
                }
                //valida que llegue nitempresa mes desde y mes hasta
                if(selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null){
                    sql = "select se.nombre as nombre, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"group by se.nombre, to_char(fechapermiso,'mm') "+
                            " UNION ALL "+
                            " select se.nombre as nombre, to_char(fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                            +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"group by se.nombre, to_char(fecha_inicial,'mm')";
                            

                }
                //Valida que llegue nitempresa mes desde mes hasta y motivo
                if(selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null && selmotivo!=null){
                    sql = "select se.nombre as nombre, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"and fk_cod_motivo= '"+selmotivo+"'"
                            +"group by se.nombre, to_char(fechapermiso,'mm'), fk_cod_motivo"
                            + " UNION ALL "+
                            " select se.nombre as nombre, to_char(fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                            +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"and fk_cod_motivo= '"+selmotivo+"'"
                            +"group by se.nombre, to_char(fecha_inicial,'mm'), fk_cod_motivo";
                            
                            
                }
                
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);                              

                    while (rs.next()) {
                        String fecha = rs.getString("fechaper");
                        String mes = uilistas.convertirMeses(fecha);                        
                        //convertir tiempo horas en float
                        float thcon = Float.parseFloat(rs.getString("tothoras"));                        
                        String threg = Float.toString(thcon);                        
                        au = new Ausentismo();
                        au.getSubempresa().setNombre(rs.getString("nombre"));
                        au.setMes(mes);       
                        
                        au.setTiempo_horas(threg);                        
                        au.setEps(rs.getDouble("toteps"));
                        au.setEmpleador(rs.getDouble("totempleador"));
                        au.setArl(rs.getDouble("totarl"));
                        au.setTrabajador(rs.getDouble("tottrabajador"));
                        au.setTotalsube(rs.getDouble("totalsube"));

                        listaausentismoEmpresa.add(au);
                    }                             

            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }

        return listaausentismoEmpresa;
    }
    
    public ArrayList<Ausentismo> ausentismoanomesEmpleado(String cedula,String nitem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {

        Ausentismo au;            
        ArrayList<Ausentismo> listausentismoEmpleado = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;
        Double totaleps = 0.00;
        String sql="";

        //formato fecha recibida
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')";
            queryfechainc = "to_char(ri.fecha_inicial,'yyyy')";
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
            queryfechainc = "to_char(ri.fecha_inicial,'yyyy/mm')";
        }
        
        //Valida y muestra la seleccion de empresa año y motivo de la ausencia
        if (nitem != null && selano != null && selmotivo != null) {
                sql = "select cedula, observaciones, e.nombres as nombre, apellidos, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_ausentismo re " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and "+queryfecha+" = '"+selfecha+"' and fk_cod_motivo='"+selmotivo+"' "
                        +"group by e.nombres, observaciones, e.cedula, e.apellidos, to_char(fechapermiso,'mm'), m.nombre_motivo "
                        + " UNION ALL "+
                        " select cedula, observaciones, e.nombres as nombre, apellidos, to_char(ri.fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_incapacidad ri " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=ri.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and "+queryfechainc+" = '"+selfecha+"' and fk_cod_motivo='"+selmotivo+"' "
                        +"group by e.nombres, observaciones, e.cedula, e.apellidos, to_char(ri.fecha_inicial,'mm'), m.nombre_motivo";
                        
        }
        //Valida y muestra la seleccion de empresa y año
        if (nitem != null && selano != null && selmotivo == null) {
                sql = "select cedula, observaciones, e.nombres as nombre, apellidos, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_ausentismo re " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and "+queryfecha+" = '"+selfecha+"' "
                        +"group by e.nombres, observaciones, e.cedula, e.apellidos, to_char(fechapermiso,'mm'), m.nombre_motivo "
                        + " UNION ALL "+
                        "select cedula, observaciones, e.nombres as nombre, apellidos, to_char(ri.fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_incapacidad ri " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=ri.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and "+queryfechainc+" = '"+selfecha+"' "
                        +"group by e.nombres, observaciones, e.cedula, e.apellidos, to_char(ri.fecha_inicial,'mm'), m.nombre_motivo ";
        }
        //valida y muestra la seleccion de empresa mesdesde y meshasta y año correspondiente
        if (nitem != null &&  selmesdesde != null && selmeshasta != null && queryfecha!=null ) {
                sql = "select cedula, observaciones, e.nombres as nombre, apellidos, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_ausentismo re " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "
                        +"group by e.nombres, observaciones, e.cedula, e.apellidos, to_char(fechapermiso,'mm'), m.nombre_motivo"
                        + " UNION ALL "+
                        "select cedula, observaciones, e.nombres as nombre, apellidos, to_char(ri.fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_incapacidad ri " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=ri.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' "
                        +"group by e.nombres, observaciones, e.cedula, e.apellidos, to_char(ri.fecha_inicial,'mm'), m.nombre_motivo";
        }
        //valida y muestra la seleccion de empresa mesdesde meshasta motivo y año
        if (nitem != null &&  selmesdesde != null && selmeshasta != null && queryfecha!=null && selmotivo!=null ) {
                sql = "select cedula, observaciones, e.nombres as nombre, apellidos, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_ausentismo re " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' and fk_cod_motivo='"+selmotivo+"' "
                        +"group by e.nombres, observaciones, e.cedula, e.apellidos, to_char(fechapermiso,'mm'), m.nombre_motivo "
                        + " UNION ALL "+
                        " select cedula, observaciones, e.nombres as nombre, apellidos, to_char(ri.fecha_inicial,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_incapacidad ri " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=ri.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' and fk_cod_motivo='"+selmotivo+"' "
                        +"group by e.nombres, observaciones, e.cedula, e.apellidos, to_char(ri.fecha_inicial,'mm'), m.nombre_motivo";
                                     
        }
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);

                    while (rs.next()) {
                        String fecha = rs.getString("fechaper");
                        String mes = uilistas.convertirMeses(fecha);
                        au = new Ausentismo();
                        au.getEmpleado().setCedula(rs.getString("cedula"));
                        au.getEmpleado().setNombres(rs.getString("nombre"));
                        au.getEmpleado().setApellidos(rs.getString("apellidos"));
                        au.setMes(mes);  
                        au.setTiempo_horas(rs.getString("tothoras"));
                        au.setMotivoausentismo(rs.getString("motivo"));
                        au.setEps(rs.getDouble("toteps"));
                        au.setEmpleador(rs.getDouble("totempleador"));
                        au.setArl(rs.getDouble("totarl"));
                        au.setTrabajador(rs.getDouble("tottrabajador"));
                        au.setTotalsube(rs.getDouble("totalsube"));  
                        au.setObservaciones(rs.getString("observaciones"));

                        listausentismoEmpleado.add(au);
                    }                             

            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }
        return listausentismoEmpleado;
    }
    
    public ArrayList<Ausentismo> ausentismoanomesEmpleadoTotales(String cedula,String nitem,String selmesdesde, String selmeshasta, String selano, String motivos) throws SQLException, Exception {

        Ausentismo au;            
        ArrayList<Ausentismo> listausentismoEmpleado = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;
        Double totaleps = 0.00;
        String sql="";

        //formato fecha recibida
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')";
            queryfechainc = "to_char(ri.fecha_inicial,'yyyy')";
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
            queryfechainc = "to_char(ri.fecha_inicial,'yyyy/mm')";
        }
        
        //Valida y muestra la seleccion de empresa año y motivo de la aucensia
        if (nitem != null && selano != null && selmesdesde==null && selmeshasta==null) {
                sql = "select cedula, e.nombres as nombre, apellidos,round(sum(cast(tiempohoras as numeric)),'2') as tothoras, sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " +
                    " from registro_ausentismo re " +
                    " inner join motivopermiso m on (cod_motivo=fk_cod_motivo) " +
                    " inner join empleado e on (e.cedula=re.fk_cedula) " +
                    " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                    " where e.nitsubempresa in ('"+nitem+"') and "+
                    " "+queryfecha+"='"+selfecha+"'"+
                    " and m.cod_motivo in ("+motivos+"'0') " +                        
                    " group by e.nombres, e.cedula, e.apellidos " +                        
                    " union all " +
                    " select cedula, e.nombres as nombre, apellidos,round(sum(cast(tiempohoras as numeric)),'2') as tothoras, sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " +
                    " from registro_incapacidad re " +
                    " inner join motivopermiso m on (cod_motivo=fk_cod_motivo) " +
                    " inner join empleado e on (e.cedula=re.fk_cedula) " +
                    " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " +
                    " where e.nitsubempresa in ('"+nitem+"') and"+
                    " to_char(fecha_registro,'yyyy')='"+selfecha+"'" +                        
                    " and m.cod_motivo in ("+motivos+"'0') " +
                    " group by e.nombres, e.cedula, e.apellidos ";
                        
        }
        
        
        
        if (nitem != null && selano == null && selmesdesde!=null && selmeshasta!=null) {
                sql = "select cedula, e.nombres as nombre, apellidos,round(sum(cast(tiempohoras as numeric)),'2') as tothoras, sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " +
                    " from registro_ausentismo re " +
                    " inner join motivopermiso m on (cod_motivo=fk_cod_motivo) " +
                    " inner join empleado e on (e.cedula=re.fk_cedula) " +
                    " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                    " where e.nitsubempresa in ('"+nitem+"') "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "'"+
                    " and m.cod_motivo in ("+motivos+"'0') " +                        
                    " group by e.nombres, e.cedula, e.apellidos " +                        
                    " union all " +
                    " select cedula, e.nombres as nombre, apellidos,round(sum(cast(tiempohoras as numeric)),'2') as tothoras, sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " +
                    " from registro_incapacidad re " +
                    " inner join motivopermiso m on (cod_motivo=fk_cod_motivo) " +
                    " inner join empleado e on (e.cedula=re.fk_cedula) " +
                    " inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " +
                    " where e.nitsubempresa in ('"+nitem+"') "+
                    " and to_char(fecha_registro,'yyyy/mm') between '" + selfecha + "' and '" + selfecha2 + "'"+                    
                    " and m.cod_motivo in ("+motivos+"'0') " +
                    " group by e.nombres, e.cedula, e.apellidos ";
                        
        }
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);

                    while (rs.next()) {                                                
                        au = new Ausentismo();
                        au.getEmpleado().setCedula(rs.getString("cedula"));
                        au.getEmpleado().setNombres(rs.getString("nombre"));
                        au.getEmpleado().setApellidos(rs.getString("apellidos"));                        
                        au.getEmpleado().setThacum(rs.getInt("tothoras"));
                        au.setEps(rs.getDouble("toteps"));
                        au.setEmpleador(rs.getDouble("totempleador"));
                        au.setArl(rs.getDouble("totarl"));
                        au.setTrabajador(rs.getDouble("tottrabajador"));
                        au.setTotalsube(rs.getDouble("totalsube"));                                 
                        
                        
                        
                        listausentismoEmpleado.add(au);
                    }                             

            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }
        return listausentismoEmpleado;
    }
    
    
    
    public ArrayList<Ausentismo> pieanomesEmpleado(String cedula,String nitem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {
        
        Ausentismo au;       
        ArrayList<Ausentismo> pieausentismoEmpleado = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        Double totaleps = 0.00;
        String sql="";

        
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
        }
                            
                //Valida que llegue año cedula y empresa
                if (nitem != null && selano != null) {
                    sql = "select nombres , apellidos, sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador " 
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +"where fk_cedula= '"+cedula+"' and fk_nitempresa = '"+nitem+"' and "+queryfecha+" = '"+selfecha+"' " 
                            +"group by nombres,apellidos";                 
                }
                
                //valida y muestra la seleccion de empresa mesdesde y meshasta y año correspondiente
                 if (nitem != null &&  selmesdesde != null && selmeshasta != null && queryfecha!=null ) {
                    sql = "select nombres , apellidos, sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador " 
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +"where fk_cedula= '"+cedula+"' and fk_nitempresa = '"+nitem+"' and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "'  " 
                            +"group by nombres,apellidos";  
                 }
                 
                 if (nitem != null &&  selmesdesde != null && selmeshasta != null && queryfecha!=null && selmotivo!=null ) {
                    sql = "select nombres , apellidos, sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador " 
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +"where fk_cedula= '"+cedula+"' and fk_nitempresa = '"+nitem+"' "
                            + "and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "'  " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by nombres, apellidos, m.nombre_motivo";                                            
                 }
                 
                 if (nitem != null && selano != null && selmotivo != null) {
                    sql = "select nombres , apellidos, sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador " 
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +"where fk_cedula= '"+cedula+"' and fk_nitempresa = '"+nitem+"' "
                            + "and "+queryfecha+" = '"+selfecha+"' " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by nombres, apellidos, m.nombre_motivo";                     
                 }
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);                              

                    while (rs.next()) {                     
                        au = new Ausentismo();
                        au.getEmpleado().setNombres(rs.getString("nombres"));
                        au.getEmpleado().setApellidos(rs.getString("apellidos"));
                        au.setEps(rs.getDouble("toteps"));
                        au.setEmpleador(rs.getDouble("totempleador"));
                        au.setArl(rs.getDouble("totarl"));
                        au.setTrabajador(rs.getDouble("tottrabajador"));
                        pieausentismoEmpleado.add(au);
                    }                             

            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }

        return pieausentismoEmpleado;
    }
    
    
    public ArrayList<Ausentismo> pieausentismoanomesEmpresa(String nitem,String nitsubem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {

        Ausentismo au;       
        ArrayList<Ausentismo> pieausentismoEmpresa = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;
        Double totaleps = 0.00;
        String sql="";

        
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')"; 
            queryfechainc = "to_char(fecha_inicial,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
            queryfechainc = "to_char(fecha_inicial,'yyyy/mm')"; 
        }
                            
                //Valida que llegue año y empresa
                if (nitem != null && selano != null) {
                    sql = " select nombre, sum(toteps) as toteps, sum(totempleador) as totempleador, sum(totarl) as totarl, sum(tottrabajador) as tottrabajador "
                            + "from( select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"' and " + queryfecha + " = '" + selfecha + "' " 
                            +"group by em.nombre " 
                            + " UNION ALL "+
                            " select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"' and " + queryfechainc + " = '" + selfecha + "' " 
                            +"group by em.nombre) as uniontotalesincapacidad "
                            + " group by nombre ";
                }
                
                //Valida que llegue año y empresa y motivo
                if (nitem != null && selano != null && selmotivo !=null) {
                    sql = " select nombre, sum(toteps) as toteps, sum(totempleador) as totempleador, sum(totarl) as totarl, sum(tottrabajador) as tottrabajador "
                            + " from (select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"' and " + queryfecha + " = '" + selfecha + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by em.nombre, fk_cod_motivo "
                            + " UNION ALL "+
                            " select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"' and " + queryfechainc + " = '" + selfecha + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +" group by em.nombre, fk_cod_motivo)  as totalesunionausentismoincapacidad "
                            +" group by nombre ";
                          
                }
                //valida que llegue nitsubempresa y fecha
                if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                    sql = " select nombre, sum(toteps) as toteps, sum(totempleador) as totempleador, sum(totarl) as totarl, sum(tottrabajador) as tottrabajador "
                            + " from ("                            
                            + "select se.nombre as nombre,sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) "
                            +" as totarl, sum(trabajador) as tottrabajador " 
                            +" from registro_ausentismo "
                            +" inner join empleado e on (e.cedula=fk_cedula)"
                            +" inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +" inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +" where se.nitsubempresa = '"+nitsubem+"' and  " + queryfecha + " = '"+ selfecha + "' " 
                            +" group by se.nombre "
                            + " UNION ALL "+
                            " select se.nombre as nombre,sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) "
                            +" as totarl, sum(trabajador) as tottrabajador " 
                            +" from registro_incapacidad "
                            +" inner join empleado e on (e.cedula=fk_cedula)"
                            +" inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +" inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +" where se.nitsubempresa = '"+nitsubem+"' and  " + queryfechainc + " = '"+ selfecha + "' " 
                            +" group by se.nombre ) as totalesunionausentismoincapacidad "
                            + " group by nombre";
                 
                }
                
                //valida que llegue nitsubempresa fecha y motivo
                if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null && selmotivo!=null) {
                    sql = " select nombre, sum(toteps) as toteps, sum(totempleador) as totempleador, sum(totarl) as totarl, sum(tottrabajador) as tottrabajador "
                            + " from ("
                            + "select se.nombre as nombre,sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) "
                            +" as totarl, sum(trabajador) as tottrabajador " 
                            +" from registro_ausentismo "
                            +" inner join empleado e on (e.cedula=fk_cedula)"
                            +" inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +" inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +" where se.nitsubempresa = '"+nitsubem+"' and  " + queryfecha + " = '"+ selfecha + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"'"
                            +" group by se.nombre, fk_cod_motivo "
                            + " UNION ALL "+
                            " select se.nombre as nombre,sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) "
                            +" as totarl, sum(trabajador) as tottrabajador " 
                            +" from registro_incapacidad "
                            +" inner join empleado e on (e.cedula=fk_cedula)"
                            +" inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +" inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +" where se.nitsubempresa = '"+nitsubem+"' and  " + queryfechainc + " = '"+ selfecha + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"'"
                            +" group by se.nombre, fk_cod_motivo) as totalesunionausentismoincapacidad "
                            + " group by nombre ";
                 
                }
                
                //Valida que llegue nitsubempresa, mes desde, mes hasta y año
                if (selmesdesde != null && selmeshasta != null && nitsubem != null && selmotivo ==null) {                                        
                    sql = " select nombre, sum(toteps) as toteps, sum(totempleador) as totempleador, sum(totarl) as totarl, sum(tottrabajador) as tottrabajador "
                            + " from ("
                            + "select se.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "
                            +"where se.nitsubempresa = '"+nitsubem+"' "
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "
                            +"group by se.nombre "
                            + " UNION ALL "+
                            " select se.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "
                            +"where se.nitsubempresa = '"+nitsubem+"' "
                            +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' "
                            +"group by se.nombre) as totalesunionausentismoincapacidad "
                            + " group by nombre";
                    
                   
                }
                
                //Valida que llegue nitsubempresa, mes desde, mes hasta año y motivo
                if (selmesdesde != null && selmeshasta != null && nitsubem != null && selmotivo !=null) {                                        
                    sql = " select nombre, sum(toteps) as toteps, sum(totempleador) as totempleador, sum(totarl) as totarl, sum(tottrabajador) as tottrabajador "
                            + " from ("
                            +" select se.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "
                            +"where se.nitsubempresa = '"+nitsubem+"' "
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by se.nombre, fk_cod_motivo "
                            + " UNION ALL "+
                            " select se.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "
                            +"where se.nitsubempresa = '"+nitsubem+"' "
                            +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' "
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by se.nombre, fk_cod_motivo) as totalesunionausentismoincapacidad "
                            + " group by nombre ";
                            
                    
                   
                }
                //valida que llegue nitempresa mes desde y mes hasta
                if(selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null){
                    sql = " select nombre, sum(toteps) as toteps, sum(totempleador) as totempleador, sum(totarl) as totarl, sum(tottrabajador) as tottrabajador "
                            + " from ( " 
                            + "select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"'" 
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"group by em.nombre"
                            + " UNION ALL "+
                            " select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"'" 
                            +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"group by em.nombre ) as totalesunionausentismoincapacidad "
                            + " group by nombre ";
                }
                
                //valida que llegue nitempresa mes desde  mes hasta y motivo
                if(selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null && selmotivo!=null){
                    sql = " select nombre, sum(toteps) as toteps, sum(totempleador) as totempleador, sum(totarl) as totarl, sum(tottrabajador) as tottrabajador "
                            + " from( "
                            + "select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"'" 
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by em.nombre, fk_cod_motivo "
                            + " UNION ALL "+
                            " select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_incapacidad "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"'" 
                            +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by em.nombre, fk_cod_motivo) as totalesunionausentismoincapacidad"
                            + " group by nombre ";
                }
                
                
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);                              

                    while (rs.next()) {                     
                        au = new Ausentismo();
                        au.getEmpresa().setNombre(rs.getString("nombre"));
                        au.setEps(rs.getDouble("toteps"));
                        au.setEmpleador(rs.getDouble("totempleador"));
                        au.setArl(rs.getDouble("totarl"));
                        au.setTrabajador(rs.getDouble("tottrabajador"));
                        pieausentismoEmpresa.add(au);
                    }                             

            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }

        return pieausentismoEmpresa;
        
    }
        
    public ArrayList<Ausentismo> pieporSubempresa(String nitem,String nitsubem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {

        Ausentismo au;       
        ArrayList<Ausentismo> pieporSubempresa = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        String queryfechainc = null;
        Double totaleps = 0.00;
        String sql="";

        
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')"; 
            queryfechainc = "to_char(fecha_inicial,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
            queryfechainc = "to_char(fecha_inicial,'yyyy/mm')";
        }
                            
                //Valida que llegue año y empresa
                if (nitem != null && selano != null) {
                    sql = "Select em.nombre as nombreem, se.nombre as nombre, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " 
                        +"from registro_ausentismo " 
                        +"inner join empleado e on (e.cedula=fk_cedula) " 
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                        +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "    
                        +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') " 
                        +"and " + queryfecha + " = '" + selfecha + "' " 
                        +"group by se.nombre, em.nombre "
                        + " UNION ALL "+
                        " Select em.nombre as nombreem, se.nombre as nombre, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " 
                        +"from registro_incapacidad " 
                        +"inner join empleado e on (e.cedula=fk_cedula) " 
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                        +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "    
                        +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') " 
                        +"and " + queryfechainc + " = '" + selfecha + "' " 
                        +"group by se.nombre, em.nombre";
                }
                
                //Valida que llegue año empresa y motivo
                if (nitem != null && selano != null && selmotivo!=null) {
                    sql = "Select em.nombre as nombreem, se.nombre as nombre, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " 
                        +"from registro_ausentismo " 
                        +"inner join empleado e on (e.cedula=fk_cedula) " 
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                        +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "    
                        +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') " 
                        +"and " + queryfecha + " = '" + selfecha + "' " 
                        +"and fk_cod_motivo='"+selmotivo+"' "  
                        +"group by se.nombre, em.nombre, fk_cod_motivo "
                        + " UNION ALL "+
                        " Select em.nombre as nombreem, se.nombre as nombre, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " 
                        +"from registro_incapacidad " 
                        +"inner join empleado e on (e.cedula=fk_cedula) " 
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                        +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "    
                        +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') " 
                        +"and " + queryfechainc + " = '" + selfecha + "' " 
                        +"and fk_cod_motivo='"+selmotivo+"' "  
                        +"group by se.nombre, em.nombre, fk_cod_motivo";
                }
                
                if(selmesdesde != null && selmeshasta != null && nitem != null){
                    sql = " Select em.nombre as nombreem, se.nombre as nombre, " 
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) " 
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '" +nitem+ "') " 
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"group by se.nombre, em.nombre "
                            + " UNION ALL "+
                            " Select em.nombre as nombreem, se.nombre as nombre, " 
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_incapacidad " 
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) " 
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '" +nitem+ "') " 
                            +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"group by se.nombre, em.nombre ";
                            
                }
                
                if(selmesdesde != null && selmeshasta != null && nitem != null && selmotivo!=null){
                    sql = " Select em.nombre as nombreem, se.nombre as nombre, " 
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) " 
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '" +nitem+ "') " 
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"'"
                            +"group by se.nombre, em.nombre, fk_cod_motivo "
                            + " UNION ALL "+
                            " Select em.nombre as nombreem, se.nombre as nombre, " 
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_incapacidad " 
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) " 
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '" +nitem+ "') " 
                            +"and " + queryfechainc + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"'"
                            +"group by se.nombre, em.nombre, fk_cod_motivo";
                }
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);                              

                    while (rs.next()) {                     
                        au = new Ausentismo();
                        au.getEmpresa().setNombre(rs.getString("nombreem"));
                        au.getSubempresa().setNombre(rs.getString("nombre"));                        
                        au.setTotalsube(rs.getDouble("totalsube"));
                        pieporSubempresa.add(au);
                    }                  
            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }

        return pieporSubempresa;
        
    }
    
    
    
    public Ausentismo buscarAusentismo(String cod_registro, String nitsesion) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Ausentismo au = null;
        DateFormat formatoHora = new SimpleDateFormat("HH:mm");
        String hora_salida = null;
        
        try {
            consulta = new Consulta(getConexion());
            
            sql = "select re.*, e.nombres,e.apellidos,e.fecha_nacimiento,e.cargo,cargo.cargo ncargo, "
                    + "e.cod_det_lista_ecivil,civil.nombre necivil, " 
                    + "cod_det_lista_sexo,sexo.nombre nsexo, "
                    + "(SELECT date_part('year',age(e.fecha_nacimiento)) AS edad) " 
                    + "from registro_ausentismo re " 
                    + "inner join empleado e on (fk_cedula=cedula) " 
                    + "inner join det_lista civil on (civil.cod_det_lista=e.cod_det_lista_ecivil) " 
                    + "inner join det_lista sexo on (sexo.cod_det_lista=e.cod_det_lista_sexo) " 
                    + "inner join cargo on (cargo.cod_cargo=e.cargo) " 
                    + " where cod_regausentismo='" + cod_registro.trim() +"'";
                      
            rs = consulta.ejecutar(sql);
            
            if (rs.next()) {
                String estado = rs.getString("estado");
                if (estado.equals("A")) {                                                                       
                        au = new Ausentismo(); 
                        au.setCod_registro(cod_registro);
                        au.setMotivoausentismo(rs.getString("fk_cod_motivo"));                                
                        au.setHora_salida(rs.getTime("horasalida"));                                                               
                        au.setHora_llegada(rs.getTime("horallegada"));                                                
                        au.setTiempo_horas(rs.getString("tiempohoras"));                        
                        au.setObservaciones(rs.getString("observaciones"));
                        au.getEmpleado().setCedula(rs.getString("fk_cedula"));
                        au.setFecha_permiso(rs.getDate("fechapermiso"));
                        au.getEmpleado().setNombres(rs.getString("nombres"));
                        au.getEmpleado().setApellidos(rs.getString("apellidos"));
                        au.getEmpleado().getCargo().setCodigo(rs.getString("cargo"));
                        au.getEmpleado().getCargo().setNombre(rs.getString("ncargo"));
                        au.getEmpleado().getSexo().setCodigo(rs.getString("cod_det_lista_sexo"));
                        au.getEmpleado().getSexo().setNombre(rs.getString("nsexo"));
                        au.getEmpleado().getEcivil().setCodigo(rs.getString("cod_det_lista_ecivil"));
                        au.getEmpleado().getEcivil().setNombre(rs.getString("necivil"));
                        au.getEmpleado().setEdad(rs.getInt("edad"));                
                }
            }                                    
            return au;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer modificarAusentismo(Ausentismo ausentismo) throws SQLException{
        Consulta consulta = null;
        Integer resultado;   
        ResultSet rs;
        String sql = "";
        String tipo = "";       
        double salmin = 0.00;
        float th = 0;        
        double salhoras = 0.00;
        double thd = 0;
        double tha = 0;
        

        //Dar formato a la fecha y a la hora
        DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");    
        DateFormat formatoHora = new SimpleDateFormat("HH:mm");   
        

        

        //Sentencia SQL para guardar el registro        
        try {
            consulta = new Consulta(getConexion());            
                
            
            sql = "SELECT tipo from motivopermiso where cod_motivo = '" + ausentismo.getMotivoausentismo() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {                
                ausentismo.setTipo_permiso(rs.getString("tipo"));    
                tipo = ausentismo.getTipo_permiso();                
                    
            }
            
            //Búsqueda del sueldo de Empleado
            sql = "SELECT sueldo_mes from empleado where cedula = '" + ausentismo.getEmpleado().getCedula() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
                ausentismo.getEmpleado().setSueldo_mes(rs.getInt("sueldo_mes"));

            }
            
            sql = "SELECT sal_min from configuracion";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
               salmin = rs.getDouble("sal_min");
            }

            salhoras = (ausentismo.getEmpleado().getSueldo_mes()/240.00);
            th = Float.parseFloat(ausentismo.getTiempo_horas());
            double minhoras = (salmin/240);
            tha = 16;
            thd = th - tha;
           
            
            double empleador=0;
            double arl=0;
            double eps=0;
            double trabajador=0;
            double total=0;
            double porcent=0.6667;
            double normal=0;
            
            DecimalFormat formato1= new DecimalFormat("#.00");
            formato1.format(arl);
            formato1.format(eps);
            formato1.format(empleador);
            formato1.format(total);
            List thorasHibrido = new ArrayList();

            //Revisar cada tipo
            switch(tipo){
                
                case "EMPLEADOR":
                        empleador = salhoras * th;
                    break;
                    
                case "EPS":
                       //Calculo en valor del mínimo
                       double minimo = (th-16) * minhoras;
                       
                       //Calculo de los primeros dias
                      if(th <= 16 ){
                           empleador = tha * salhoras;    
                      }else{
                           //Calculo de los primeros dias                         
                           empleador = tha * salhoras; 
                           //Calculo de los días restantes
                           double epsxemp = ((salhoras * thd) * porcent);
                           
                           //Se compara con el mínimo el valor adquirido                           
                           if (epsxemp > minimo) {
                               eps = epsxemp;
                           }else{
                               eps = minimo;
                           }
                       }  
                    break; 
                    
                case "ARL":                
                    arl = salhoras*th;  
                    break;
                    
                case "TRABAJADOR":
                    trabajador = salhoras*th;
                    break;
                    
                case "EPSN":
                    eps = salhoras*th;                    
                    break;
                    
                case "EM-TR":
                    //Calcula sumatoria para tipo EM-TR con acumulador de horas de empleado y tiempo horas
                    int thacumA= ausentismo.getEmpleado().getThacum();                   
                    float thacumD = thacumA + th;
                    
                    if (thacumA > 24){
                        trabajador= th*salhoras;
                    }else if(thacumA <= 24 && thacumD>24){
                        double  thdes=thacumD-24;
                        double  thant=24-thacumA;
                        trabajador=thdes*salhoras;
                        empleador=thant*salhoras;
                    }else{
                        empleador=th*salhoras;
                    }
                    break;
            }  
            total = empleador + eps + arl + trabajador;

            
                sql = "UPDATE registro_ausentismo "
                        + "set horasalida = '" + formatoHora.format(ausentismo.getHora_salida()) + "', horallegada = '" + formatoHora.format(ausentismo.getHora_llegada()) + "', "
                        + "fk_cod_motivo = '" + ausentismo.getMotivoausentismo() + "', tiempohoras = '" +ausentismo.getTiempo_horas()+ "', "
                        + "observaciones = '" + ausentismo.getObservaciones() + "', "
                        + "eps = '"+eps+"', arl='"+arl+"',trabajador = '"+trabajador+"', empleador = '"+empleador+"', total = '"+total+"', "
                        + "fechapermiso = '" + formatoFecha.format(ausentismo.getFecha_permiso()) + "', estado = 'F' "
                        + "where cod_regausentismo = '" + ausentismo.getCod_registro() + "' ";                        

            resultado = consulta.actualizar(sql);
            return resultado;
            
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    } 
    
    public Integer guardarAusentismo(Ausentismo ausentismo) throws SQLException{
        Consulta consulta = null;        
        Integer resultado;
        ResultSet rs;
        String sql = "";
        String tipo = "";       
        double salmin = 0.00;
        float th = 0;        
        double salhoras = 0.00;
        double thd = 0;
        double tha = 0;
        
        
        //Adquirir la fecha de hoy y darle formato
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate ahora = LocalDate.now();
        //Dar formato a la fecha y a la hora
        DateFormat formatoFecha1 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        
        
              
        try {
            consulta = new Consulta(getConexion());          
            
            //Búsqueda del tipo de motivo
            sql = "SELECT tipo from motivopermiso where cod_motivo = '" + ausentismo.getMotivoausentismo() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {                
                ausentismo.setTipo_permiso(rs.getString("tipo"));    
                tipo = ausentismo.getTipo_permiso();                
                    
            }
            
            //Búsqueda del sueldo de Empleado
            sql = "SELECT sueldo_mes from empleado where cedula = '" + ausentismo.getEmpleado().getCedula() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
                ausentismo.getEmpleado().setSueldo_mes(rs.getInt("sueldo_mes"));

            }
            
            sql = "SELECT sal_min from configuracion";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
               salmin = rs.getDouble("sal_min");
            }

            salhoras = (ausentismo.getEmpleado().getSueldo_mes()/240.00);
            th = Float.parseFloat(ausentismo.getTiempo_horas());
            double minhoras = (salmin/240);
            tha = 16;
            thd = th - tha;
           
            boolean finalizado=false;
            double empleador=0;
            double arl=0;
            double eps=0;
            double trabajador=0;
            double total=0;
            double porcent=0.6667;
            double normal=0;
            
            DecimalFormat formato1= new DecimalFormat("#.00");
            formato1.format(arl);
            formato1.format(eps);
            formato1.format(empleador);
            formato1.format(total);
            List thorasHibrido = new ArrayList();

            //Revisar cada tipo
            switch(tipo){
                
                case "EMPLEADOR":
                        empleador = salhoras * th;
                    break;
                    
                case "EPS":
                       //Calculo en valor del mínimo
                       double minimo = (th-16) * minhoras;
                       
                       //Calculo de los primeros dias
                      if(th <= 16 ){
                           empleador = tha * salhoras;    
                      }else{
                           //Calculo de los primeros dias                         
                           empleador = tha * salhoras; 
                           //Calculo de los días restantes
                           double epsxemp = ((salhoras * thd) * porcent);
                           
                           //Se compara con el mínimo el valor adquirido                           
                           if (epsxemp > minimo) {
                               eps = epsxemp;
                           }else{
                               eps = minimo;
                           }
                       }  
                    break; 
                    
                case "ARL":                
                    arl = salhoras*th;  
                    break;
                    
                case "TRABAJADOR":
                    trabajador = salhoras*th;
                    break;
                    
                case "EPSN":
                    eps = salhoras*th;                    
                    break;
                    
                case "EM-TR":
                    //Calcula sumatoria para tipo EM-TR con acumulador de horas de empleado y tiempo horas
                    int thacumA= ausentismo.getEmpleado().getThacum();                   
                    float thacumD = thacumA + th;
                    
                    if (thacumA > 24){
                        trabajador= th*salhoras;
                    }else if(thacumA <= 24 && thacumD>24){
                        double  thdes=thacumD-24;
                        double  thant=24-thacumA;
                        trabajador=thdes*salhoras;
                        empleador=thant*salhoras;
                    }else{
                        empleador=th*salhoras;
                    }
                    break;
            }  
            total = empleador + eps + arl + trabajador;

            
            
            //Sentencia SQL para guardar el registro
                sql = "INSERT INTO registro_ausentismo ("
                        + "fecharegistro, fk_cod_motivo, horasalida, "
                        + "horallegada, tiempohoras, observaciones, "
                        + "fk_cedula, estado, fechapermiso, empleador, eps, arl,trabajador, total )"
                        + "VALUES ("
                        + "'" + formatoFecha.format(ahora) + "',"                                                                                                    
                        + "'" + ausentismo.getMotivoausentismo() + "',"
                        + "'" + formatoHora.format(ausentismo.getHora_salida()) + "',"
                        + "'" + formatoHora.format(ausentismo.getHora_llegada()) + "',"
                        + "'" + ausentismo.getTiempo_horas() + "',"                      
                        + "'" + ausentismo.getObservaciones() + "',"
                        + "'" + ausentismo.getEmpleado().getCedula() + "',"
                        + "'A',"
                        + "'" + formatoFecha1.format(ausentismo.getFecha_permiso()) + "',"
                        + " round(CAST(('"+empleador+"') as numeric), '3'), "
                        + " round(CAST(('"+eps+"') as numeric), '3'), "
                        + " round(CAST(('"+arl+"') as numeric), '3'), "
                        + " round(CAST(('"+trabajador+"') as numeric), '3'), "
                        + " round(CAST(('"+total+"') as numeric), '3'))";

                resultado = consulta.actualizar(sql);
                return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }
    

    
    
    public ArrayList<Motivo> listarMotivos() throws SQLException {
        Motivo motivo;
        ArrayList<Motivo> listaMotivos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_motivo,nombre_motivo,tipo "
                    + " FROM motivopermiso "
                    + " where cod_motivo not in ('1','2','5')"
                    + " order by cod_motivo ";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                motivo = new Motivo();
                motivo.setCodigo(rs.getString("cod_motivo"));
                motivo.setNombrem(rs.getString("nombre_motivo"));
                motivo.setTipo(rs.getString("tipo"));
                listaMotivos.add(motivo);
            }
            return listaMotivos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    //Acumulador de horas de ausentismo tipo EM-TR
    public Integer horasAcumuladas(String cedula) throws SQLException {
        int thacum =0 ;         
        ResultSet rs;
        Consulta consulta = null;  
        
        
        LocalDate ahora = LocalDate.now();
        String ano = ahora.format(DateTimeFormatter.ofPattern("yyyy"));
        
        try {
            String sql;
            
            consulta = new Consulta(getConexion());
            sql = "select sum(cast(tiempohoras as double precision)) as thacum "
                   +"from registro_ausentismo re "
                   +"inner join motivopermiso mo on (mo.cod_motivo=re.fk_cod_motivo) " 
                   +"where fk_cedula='"+cedula+"' "
                   +"and mo.tipo = 'EM-TR' and to_char (fechapermiso,'yyyy')= '"+ano+"'";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                thacum=rs.getInt("thacum");
            }
            
            return thacum;
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    private void setNormal(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   

    public Connection getConexion() {
        return conexion;
    }
}

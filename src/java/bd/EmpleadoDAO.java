/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import modelo.Ausentismo;
import modelo.Empleado;
import modelo.Municipio;
import modelo.SubEmpresa;
/**
 *
 * @author Andres
 */
public class EmpleadoDAO {

    private Connection conexion;

    public EmpleadoDAO(Connection conexion) {
        this.conexion = conexion;        
    }

    public Empleado validarEmpleado(String cedula, String nitsesion) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Date fechaNac;
        Empleado em = null;
        Municipio mu = null;
        
        try {
            
            String nit="";
            consulta = new Consulta(getConexion());
            String sql1
                    = "  select fk_nitempresa " +
                    " from subempresa " +
                    " where nitsubempresa='"+nitsesion+"'";

            rs = consulta.ejecutar(sql1);
            
            while(rs.next()){
                nit=rs.getString("fk_nitempresa");
            }
            
            consulta = new Consulta(getConexion());
            
            sql = "SELECT em.nombres nombres,em.apellidos apellidos,em.fecha_nacimiento fecha_nac, "
                    + "m.municipio municipio,c.cargo cargo,det1.nombre sexo,det2.nombre e_civil,estado, eps.eps as nomeps "
                    + "FROM empleado em "
                    + "INNER JOIN municipio m using (cod_municipio) "
                    + "INNER JOIN cargo c on (c.cod_cargo=em.cargo)"
                    + "INNER JOIN eps eps using (cod_eps) "
                    + "INNER JOIN det_lista det1 on (det1.cod_det_lista=em.cod_det_lista_sexo) "
                    + "INNER JOIN det_lista det2 on (det2.cod_det_lista=em.cod_det_lista_ecivil) "
                    + " WHERE nitsubempresa ='" + nitsesion +"' and cedula='" + cedula.trim() +"'";

            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                em = new Empleado();  
                em.setCedula(cedula);
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));                
                em.getEcivil().setNombre(rs.getString("e_civil"));               
                em.getSexo().setNombre(rs.getString("sexo"));
                em.getCargo().setNombre(rs.getString("cargo"));                 
                em.setEstado(rs.getBoolean("estado"));
                em.getEps().setNombre(rs.getString("nomeps"));

                //calculo de la edad
                fechaNac = rs.getDate("fecha_nac");
                
                Calendar fechaNacimiento = Calendar.getInstance();
                //Se crea un objeto con la fecha actual
                Calendar fechaActual = Calendar.getInstance();
                //Se asigna la fecha recibida a la fecha de nacimiento.
                fechaNacimiento.setTime(fechaNac);
                //Se restan la fecha actual y la fecha de nacimiento
                int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
                //int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
                //int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);  
                
                //Asignamos la edad al atributo de la clase Empleados
                em.setEdad(año);   
                
                mu = new Municipio();
                mu.setNombre(rs.getString("municipio"));            
            }            
            return em;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer validarEmpleadoActualizacion(String cedula, String nitsesion, String anoActualizador) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Date fechaNac;
        Empleado em = null;
        Municipio mu = null;
        Ausentismo au=null;  
        String cod_registro="";
        
        try {
            consulta = new Consulta(getConexion());            
            
                double thoras=0;
                
                double trabajador=0;
                double total=0;
                boolean finalizado=false;
                float salhoras=0;                
                Integer resultado=0;               
                double thacum=0;
                double tp=24;
                String selfecha;
                String queryfecha;
                selfecha = anoActualizador;
                queryfecha = "to_char(fechapermiso,'yyyy')";
                
                                               
            
                //Consulta la información a actualizar item a item
                sql= "select cod_regausentismo, re.tiempohoras as thoras,round(CAST((e.sueldo_mes/240.000) as numeric), '3') as sueldohora  "                        
                        +"from registro_ausentismo re "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join motivopermiso mo on (mo.cod_motivo=re.fk_cod_motivo) "
                        +"where fk_cedula = '"+cedula.trim()+"' and mo.tipo = 'EM-TR'  "
                        +" and "+queryfecha+" = '"+selfecha+"'"
                        + " order by cod_regausentismo ";
                
                rs = consulta.ejecutar(sql);
                                
                while (rs.next()) {            
                    double empleador=0; 
                    trabajador=0;
                    thoras = rs.getDouble("thoras");
                    thacum= thoras+thacum;
                    salhoras= rs.getFloat("sueldohora");
                    cod_registro = rs.getString("cod_regausentismo");                                           



                        if(thacum <= tp){
                            empleador=thoras*salhoras;                            
                        }
                        total=trabajador+empleador;
                        if(thacum > tp){                             
                            if(finalizado==false){                                
                                double thorastrabajador= thacum-tp;
                                trabajador=thorastrabajador*salhoras;
                                double  thorasempleador= thoras-thorastrabajador;
                                empleador=thorasempleador*salhoras;
                                finalizado=true;                                      
                                  
                            }                               
                            else{
                                empleador=0;                                
                                trabajador = thoras * salhoras;                        
                                                               
                            }
                            total=trabajador+empleador;
                            
                        }                        
                        //Ingresa la actualización en BD  
                        consulta = new Consulta(getConexion());
                        
                        
                        sql = "UPDATE registro_ausentismo re "
                                +"set trabajador =  round(CAST(('"+trabajador+"') as numeric), '3')  , empleador = round(CAST(('"+empleador+"') as numeric),'3') , total= round(CAST(('"+total+"') as numeric),'3') " 
                                +"FROM motivopermiso mo "                                                
                                +"where re.fk_cedula= '"+cedula.trim()+"' and mo.tipo = 'EM-TR' and cod_regausentismo ='" + cod_registro + "' ";

                        resultado = consulta.actualizar(sql);
                
                    }//termina while                 
                
                return resultado;
                
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public Collection<? extends Empleado> cargarDistEdad(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionEmpleados = new ArrayList<>();        
        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(*), (select count(cedula) from empleado WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total," +
                    " case " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 0 and 17 " +
                    " then '1-. MENOR DE 18 AÑOS (0-18)' " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 18 and 27 " +
                    " then '2-. 18 a 27 AÑOS (18-27)' " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 28 and 37 " +
                    " then '3-. 28 a 37 AÑOS (28-37)' " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 38 and 47 " +
                    " then '4-. 38 a 47 AÑOS (38-47)' " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 48 and 200 " +
                    " then '5-. MAYOR de 48 (>47)' " +
                    " end AS grupo_etario " +
                    " from empleado " +
                    " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                    " group by grupo_etario " +
                    " order by grupo_etario "
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("grupo_etario"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionEmpleados.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(*), (select count(cedula) from empleado WHERE nitsubempresa = '"+nitsubem+"') total," +
                    " case " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 0 and 17 " +
                    " then '1-. MENOR DE 18 AÑOS (0-18)' " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 18 and 27 " +
                    " then '2-. 18 a 27 AÑOS (18-27)' " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 28 and 37 " +
                    " then '3-. 28 a 37 AÑOS (28-37)' " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 38 and 47 " +
                    " then '4-. 38 a 47 AÑOS (38-47)' " +
                    " when substring(age(now(),fecha_nacimiento)::text from 1 for 2)::int between 48 and 200 " +
                    " then '5-. MAYOR de 48 (>47)' " +
                    " end AS grupo_etario " +
                    " from empleado " +
                    " WHERE nitsubempresa = '"+nitsubem+"'"+
                    " group by grupo_etario " +
                    " order by grupo_etario "
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("grupo_etario"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionEmpleados.add(empleado);
                }
            }
            
                        
            
            return listaDistribucionEmpleados;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }


    public Collection<? extends Empleado> cargarDistECivil(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionEciviles = new ArrayList<>();        
        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_ecivil),  dl.nombre descripcion, (select count (cod_det_lista_ecivil) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                    " from empleado e " +
                    " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_ecivil)" +
                    " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                    " group by dl.nombre "
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionEciviles.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_ecivil),  dl.nombre descripcion, (select count (cod_det_lista_ecivil) from empleado e WHERE nitsubempresa = '"+nitsubem+"') total " +
                    " from empleado e " +
                    " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_ecivil)" +
                    " WHERE nitsubempresa = '"+nitsubem+"'"+
                    " group by dl.nombre "
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionEciviles.add(empleado);
                }
            }           
            
            return listaDistribucionEciviles;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    
    public Collection<? extends Empleado> cargarDistSexo(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionSexos = new ArrayList<>();        
        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_sexo),  dl.nombre descripcion, (select count (cod_det_lista_sexo) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_sexo) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre ");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionSexos.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_sexo),  dl.nombre descripcion, (select count (cod_det_lista_sexo) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_sexo) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre "
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionSexos.add(empleado);
                }
            }           
            
            return listaDistribucionSexos;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    
    public Collection<? extends Empleado> cargarDistNumPerCargo(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionNumPerCargos = new ArrayList<>();        
        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_num_personas_cargo),  dl.nombre descripcion, (select count (cod_det_lista_num_personas_cargo) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_num_personas_cargo) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre,e.cod_det_lista_num_personas_cargo "+                        
                        "order by e.cod_det_lista_num_personas_cargo");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionNumPerCargos.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_num_personas_cargo),  dl.nombre descripcion, (select count (cod_det_lista_num_personas_cargo) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_num_personas_cargo) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre,e.cod_det_lista_num_personas_cargo "+
                        " order by e.cod_det_lista_num_personas_cargo"
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionNumPerCargos.add(empleado);
                }
            }           
            
            return listaDistribucionNumPerCargos;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    
    public Collection<? extends Empleado> cargarDistNivelEscolaridad(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionNivelEscolaridad = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_nescolar),  dl.nombre descripcion, (select count (cod_det_lista_nescolar) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_nescolar) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre,e.cod_det_lista_nescolar "+                        
                        " order by e.cod_det_lista_nescolar");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionNivelEscolaridad.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_nescolar),  dl.nombre descripcion, (select count (cod_det_lista_nescolar) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_nescolar) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre,e.cod_det_lista_nescolar "+
                        " order by e.cod_det_lista_nescolar"
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionNivelEscolaridad.add(empleado);
                }
            }           
            
            return listaDistribucionNivelEscolaridad;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    
    public Collection<? extends Empleado> cargarDistTendenciaVivienda(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionTendenciaVivienda = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_tendencia_vivienda),  dl.nombre descripcion, (select count (cod_det_lista_tendencia_vivienda) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_tendencia_vivienda) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre,e.cod_det_lista_tendencia_vivienda "+                        
                        "order by e.cod_det_lista_tendencia_vivienda");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionTendenciaVivienda.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_tendencia_vivienda),  dl.nombre descripcion, (select count (cod_det_lista_tendencia_vivienda) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_tendencia_vivienda) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre,e.cod_det_lista_tendencia_vivienda "+
                        " order by e.cod_det_lista_tendencia_vivienda"
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionTendenciaVivienda.add(empleado);
                }
            }           
            
            return listaDistribucionTendenciaVivienda;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    
    public Collection<? extends Empleado> cargarDistUsoTiempoLibre(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionUsoTiempoLibre = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_uso_tiempo_libre),  dl.nombre descripcion, (select count (cod_det_lista_uso_tiempo_libre) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_uso_tiempo_libre) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre,e.cod_det_lista_uso_tiempo_libre "+                        
                        " order by e.cod_det_lista_uso_tiempo_libre");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionUsoTiempoLibre.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_uso_tiempo_libre),  dl.nombre descripcion, (select count (cod_det_lista_uso_tiempo_libre) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_uso_tiempo_libre) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre,e.cod_det_lista_uso_tiempo_libre "+
                        " order by e.cod_det_lista_uso_tiempo_libre"
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionUsoTiempoLibre.add(empleado);
                }
            }           
            
            return listaDistribucionUsoTiempoLibre;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    public Collection<? extends Empleado> cargarDistPromedioIngreso(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionPromedioIngreso = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_promedio_ingresos),  dl.nombre descripcion, (select count (cod_det_lista_promedio_ingresos) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_promedio_ingresos) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre,e.cod_det_lista_promedio_ingresos "+                        
                        " order by e.cod_det_lista_promedio_ingresos");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionPromedioIngreso.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_promedio_ingresos),  dl.nombre descripcion, (select count (cod_det_lista_promedio_ingresos) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_promedio_ingresos) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre,e.cod_det_lista_promedio_ingresos "+
                        " order by e.cod_det_lista_promedio_ingresos"
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionPromedioIngreso.add(empleado);
                }
            }           
            
            return listaDistribucionPromedioIngreso;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    public Collection<? extends Empleado> cargarDistAntiguedadEmpresa(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionAntiguedadEmpresa = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_antiguedad_empresa),  dl.nombre descripcion, (select count (cod_det_lista_antiguedad_empresa) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_antiguedad_empresa) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre,e.cod_det_lista_antiguedad_empresa "+                        
                        " order by e.cod_det_lista_antiguedad_empresa");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionAntiguedadEmpresa.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_antiguedad_empresa),  dl.nombre descripcion, (select count (cod_det_lista_antiguedad_empresa) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_antiguedad_empresa) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre,e.cod_det_lista_antiguedad_empresa "+
                        " order by e.cod_det_lista_antiguedad_empresa"
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionAntiguedadEmpresa.add(empleado);
                }
            }           
            
            return listaDistribucionAntiguedadEmpresa;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    
    public Collection<? extends Empleado> cargarDistAntiguedadCargo(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionAntiguedadCargo = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_antiguedad_cargo),  dl.nombre descripcion, (select count (cod_det_lista_antiguedad_cargo) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_antiguedad_cargo) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre,e.cod_det_lista_antiguedad_cargo "+                        
                        " order by e.cod_det_lista_antiguedad_cargo");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionAntiguedadCargo.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_antiguedad_cargo),  dl.nombre descripcion, (select count (cod_det_lista_antiguedad_cargo) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_antiguedad_cargo) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre,e.cod_det_lista_antiguedad_cargo "+
                        " order by e.cod_det_lista_antiguedad_cargo"
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionAntiguedadCargo.add(empleado);
                }
            }           
            
            return listaDistribucionAntiguedadCargo;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    public Collection<? extends Empleado> cargarDistTipoContrato(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionTipoContrato = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_tipo_contrato),  dl.nombre descripcion, (select count (cod_det_lista_tipo_contrato) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_tipo_contrato) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre,e.cod_det_lista_tipo_contrato "+                        
                        " order by e.cod_det_lista_tipo_contrato");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionTipoContrato.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_tipo_contrato),  dl.nombre descripcion, (select count (cod_det_lista_tipo_contrato) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_tipo_contrato) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre,e.cod_det_lista_tipo_contrato "+
                        " order by e.cod_det_lista_tipo_contrato"
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionTipoContrato.add(empleado);
                }
            }           
            
            return listaDistribucionTipoContrato;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    public Collection<? extends Empleado> cargarDistActivEmpresa(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionActivEmpresa = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select count(cod_det_lista_participacion_actividades),  dl.nombre descripcion, (select count (cod_det_lista_participacion_actividades) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_participacion_actividades) " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by dl.nombre,e.cod_det_lista_participacion_actividades "+                        
                        " order by e.cod_det_lista_participacion_actividades");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionActivEmpresa.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select count(cod_det_lista_participacion_actividades),  dl.nombre descripcion, (select count (cod_det_lista_participacion_actividades) from empleado e WHERE nitsubempresa in (select nitsubempresa from subempresa WHERE nitsubempresa = '"+nitsubem+"')) total " +
                        " from empleado e " +                        
                        " join public.det_lista dl on (dl.cod_det_lista=e.cod_det_lista_participacion_actividades) " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by dl.nombre,e.cod_det_lista_participacion_actividades "+
                        " order by e.cod_det_lista_participacion_actividades"
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("descripcion"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionActivEmpresa.add(empleado);
                }
            }           
            
            return listaDistribucionActivEmpresa;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    public Collection<? extends Empleado> cargarDistFuma(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionFuma = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select case		" +
                        " when fuma=true " +
                        " then 'SI' " +
                        " when fuma=false " +
                        " then 'NO' end " +
                        " fuma, count(*), (select count(fuma)from empleado) as total " +
                        " from empleado " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by fuma ");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("fuma"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionFuma.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select case " +
                        " when fuma=true " +
                        " then 'SI' " +
                        " when fuma=false " +
                        " then 'NO' end " +
                        " fuma, count(*), (select count(fuma)from empleado) as total " +
                        " from empleado " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by fuma "
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("fuma"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionFuma.add(empleado);
                }
            }           
            
            return listaDistribucionFuma;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    public Collection<? extends Empleado> cargarDistDiagnosticadoEnfermedad(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionDiagnosticadoEnfermedad = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select case " +
                        " when diagonosticado_enfermedad=true " +
                        " then 'SI' " +
                        " when diagonosticado_enfermedad=false " +
                        " then 'NO' end " +
                        " diagonosticado_enfermedad, count(*), (select count(diagonosticado_enfermedad)from empleado) as total " +
                        " from empleado " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by diagonosticado_enfermedad ");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("diagonosticado_enfermedad"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionDiagnosticadoEnfermedad.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select case " +
                        " when diagonosticado_enfermedad=true " +
                        " then 'SI' " +
                        " when diagonosticado_enfermedad=false " +
                        " then 'NO' end " +
                        " diagonosticado_enfermedad, count(*), (select count(diagonosticado_enfermedad)from empleado) as total " +
                        " from empleado " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by diagonosticado_enfermedad "
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("diagonosticado_enfermedad"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionDiagnosticadoEnfermedad.add(empleado);
                }
            }           
            
            return listaDistribucionDiagnosticadoEnfermedad;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    public Collection<? extends Empleado> cargarConsumoAlcohol(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionConsumoAlcohol = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select case " +
                        " when consume_alcohol=true " +
                        " then 'SI' " +
                        " when consume_alcohol=false " +
                        " then 'NO' end " +
                        " consume_alcohol, count(*), (select count(consume_alcohol)from empleado) as total " +
                        " from empleado " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by consume_alcohol ");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("consume_alcohol"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionConsumoAlcohol.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select case " +
                        " when consume_alcohol=true " +
                        " then 'SI' " +
                        " when consume_alcohol=false " +
                        " then 'NO' end " +
                        " consume_alcohol, count(*), (select count(consume_alcohol)from empleado) as total " +
                        " from empleado " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by consume_alcohol "
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("consume_alcohol"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionConsumoAlcohol.add(empleado);
                }
            }           
            
            return listaDistribucionConsumoAlcohol;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }
    
    public Collection<? extends Empleado> cargarPracticaDeporte(String nitem, String nitsubem) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        
        List<Empleado> listaDistribucionPracticaDeporte = new ArrayList<>();        
 
        try {
            
            if(nitem!=null && nitsubem==null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                        " select case " +
                        " when practica_deporte=true " +
                        " then 'SI' " +
                        " when practica_deporte=false " +
                        " then 'NO' end " +
                        " practica_deporte, count(*), (select count(practica_deporte)from empleado) as total " +
                        " from empleado " +
                        " WHERE nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                        " group by practica_deporte ");
                    
                ;
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("practica_deporte"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionPracticaDeporte.add(empleado);
                }
            }
            
            if(nitem!=null && nitsubem!=null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " select case " +
                        " when practica_deporte=true " +
                        " then 'SI' " +
                        " when practica_deporte=false " +
                        " then 'NO' end " +
                        " practica_deporte, count(*), (select count(practica_deporte)from empleado) as total " +
                        " from empleado " +
                        " WHERE nitsubempresa = '"+nitsubem+"'"+
                        " group by practica_deporte "
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    Empleado empleado=new Empleado(rs.getInt("count"), 0.0f, rs.getString("practica_deporte"),rs.getInt("total"));
                    empleado.setPorcentaje((empleado.getCantidad()/rs.getFloat("total"))*100);
                    listaDistribucionPracticaDeporte.add(empleado);
                }
            }           
            
            return listaDistribucionPracticaDeporte;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
         
    }


    
    
    public Integer validarEmpleadoActualizacionEPS() throws SQLException {
        Consulta consulta = null;
        ArrayList<Ausentismo> listaAusentismo = new ArrayList<>();        
        Ausentismo au;
        ResultSet rs;
        String sql;
        
        
        try {
            consulta = new Consulta(getConexion());                        
        
                Integer resultado=0;                                                              
                double minhoras = (828116/240);
                double empleador;
                double arl;
                double eps;
                double trabajador;
                double total;
                double porcent=0.6667;
                double normal;
                double thd;
                double tha;
                double salhoras;
                
                
                
                //Consulta la información a actualizar item a item
                sql= " SELECT ra.cod_regausentismo cod_regausentismo,ra.fk_cedula, ra.tiempohoras tiempohoras, ra.eps, ra.empleador,ra.arl,ra.trabajador, ra.total, e.sueldo_mes sueldo_mes " +
                        " FROM public.registro_ausentismo ra " +
                        " inner join empleado e on (e.cedula=ra.fk_cedula) " +
                        " where to_char(fechapermiso,'yyyy')='2019' and fk_cod_motivo='1' ";
                
                rs = consulta.ejecutar(sql);
                                
                while (rs.next()) {
                    
                    empleador=0;
                    arl=0;
                    eps=0;
                    trabajador=0;
                    total=0;
                    porcent=0.6667;
                    normal=0;
                    thd=0;
                    tha=0;
                    salhoras=0.00;
                    
                    
                    
                    //au=new Ausentismo(rs.getString("cod_regausentismo"), new Empleado(rs.getString("fk_cedula"), "", ""), rs.getString("tiempohoras"));                  
                    String cod_regausentismo = rs.getString("cod_regausentismo");                    
                    float th=Float.parseFloat(rs.getString("tiempohoras"));
                    Integer sueldo_mes = rs.getInt("sueldo_mes");
                    salhoras=sueldo_mes/240.00;                        
                        
                    //listaAusentismo.add(au);
                    //}//termina while 

                //for(int i=0;i<1;i++){
                    
                    //float th=Float.parseFloat(listaAusentismo.get(i).getTiempo_horas());                            
                    
                    double minimo = (th-16) * minhoras;
                    
                    tha = 16;
                    thd = th - tha;
                    
                    
                    
                    //consulta = new Consulta(getConexion());  
                    //Consulta la información a actualizar item a item
                    //sql= "SELECT sueldo_mes "
                      //      + " from empleado "
                        //    + " where cedula='"+listaAusentismo.get(i).getEmpleado().getCedula()+"' ";

                    //rs = consulta.ejecutar(sql);

                    //while (rs.next()) {
                        //Integer salarioEmpleado=rs.getInt("sueldo_mes");
                        
                    //}                    
                    
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
                    total = empleador + eps + arl + trabajador;
                    
                    //Ingresa la actualización en BD  
                    consulta = new Consulta(getConexion());


                    sql = " UPDATE registro_ausentismo re "
                            +" set trabajador =  round(CAST(('"+trabajador+"') as numeric), '3'), eps =  round(CAST(('"+eps+"') as numeric), '3')  , empleador = round(CAST(('"+empleador+"') as numeric),'3') , total= round(CAST(('"+total+"') as numeric),'3') "
                            +" where re.cod_regausentismo= '"+cod_regausentismo+"' ";

                    resultado = consulta.actualizar(sql);
                    
                }                
                
                return resultado;
                
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer modificarEmpleado(Empleado empleado) throws SQLException{
        Consulta consulta = null;
        Integer resultado;    

        //Dar formato a la fecha y a la hora
        DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");    
        DateFormat formatoHora = new SimpleDateFormat("HH:mm");    

        //Sentencia SQL para guardar el registro
        String sql = "";
        ResultSet rs;    
   
        try {
            consulta = new Consulta(getConexion());
            
            sql = "SELECT cod_municipio from municipio where municipio = '" + empleado.getResidencia().getNombre() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
                empleado.getResidencia().setCodigo(rs.getString("cod_municipio"));                   
            }                        
            
                sql = "UPDATE empleado "
                        +" set nombres = '" + empleado.getNombres() + "', apellidos = '" + empleado.getApellidos() + "', cod_municipio = '" + empleado.getResidencia().getCodigo() + "', cod_eps = '" + empleado.getEps().getCodigo() + "',"
                        +" cod_det_lista_sexo='" + empleado.getSexo().getCodigo() + "',cod_det_lista_ecivil='" + empleado.getEcivil().getCodigo() + "',fecha_nacimiento='" + empleado.getFecha_nac() + "',"
                        +" cargo = '" + empleado.getCargo().getCodigo() + "', sueldo_mes = '" + empleado.getSueldo_mes() + "',aux_transporte='" + empleado.getAux_transporte() + "', estado = "+empleado.isEstado()+", fecha_ingreso='"+empleado.getFechaIngreso()+"'  "
                        +" where cedula = '" + empleado.getCedula() + "'";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }    
    
    public Empleado buscarEmpleado(String cedula, String nitsesion) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Empleado em = null;

        
        try {
            consulta = new Consulta(getConexion());
            
            sql = "select e.nombres,e.apellidos,m.municipio nom_municipio,ep.cod_eps eps,\n" +
                    "e.cod_det_lista_ecivil,cod_det_lista_sexo,\n" +
                    "e.fecha_nacimiento, c.cod_cargo ,e.sueldo_mes, e.aux_transporte, e.estado, fecha_ingreso\n" +
                    "from empleado e " +
                    "inner join municipio m on (m.cod_municipio=e.cod_municipio)\n" +
                    "inner join eps ep on(ep.cod_eps=e.cod_eps)\n" +
                    "inner join det_lista s on (s.cod_det_lista=e.cod_det_lista_sexo)\n" +
                    "inner join det_lista ci on (ci.cod_det_lista=e.cod_det_lista_ecivil)\n" +
                    "inner join cargo c on (c.cod_cargo=e.cargo)\n"+
                    "where e.nitsubempresa='" + nitsesion +"' and cedula='" + cedula.trim() +"'";
                    
                      
            rs = consulta.ejecutar(sql);
            
            if (rs.next()) {               
                    
                em=new Empleado();      

                
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));
                
                em.getResidencia().setNombre(rs.getString("nom_municipio"));
                
                
                em.getEps().setCodigo(rs.getString("eps"));
                em.getEcivil().setCodigo(rs.getString("cod_det_lista_ecivil"));
                 
                
                em.getCargo().setCodigo(rs.getString("cod_cargo"));
                em.setSueldo_mes(rs.getInt("sueldo_mes"));
                em.setAux_transporte(rs.getInt("aux_transporte")); 
                em.setFechaIngreso(rs.getDate("fecha_ingreso"));
                em.getSexo().setCodigo(rs.getString("cod_det_lista_sexo")); 
                em.setFecha_nac(rs.getDate("fecha_nacimiento"));
                em.setEstado(rs.getBoolean("estado"));
            }                                    
            return em;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
            //Modifica la subempresa en la que esta registrado el empleado
    public Integer modificarSubempresaempleado(Empleado empleado) throws SQLException{
        Consulta consulta = null;
        Integer resultado;    
        //Sentencia SQL para guardar el registro
        String sql = "";
        ResultSet rs;    
        
        try {
            consulta = new Consulta(getConexion());
                sql = "UPDATE empleado "
                        +" set nitsubempresa = '"+ empleado.getNitsubempresa()+"'"
                        +" where cedula = '" + empleado.getCedula() + "'";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }
    
    
    //busca empleado en todas las empresas solo admin
    public Empleado buscarempleadoAdmin(String cedula) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Date fechaNac;
        Empleado em = null;
        Municipio mu = null;
        
        try {
            consulta = new Consulta(getConexion());
            
            sql = "select em.nombres nombres,em.apellidos apellidos,em.fecha_nacimiento fecha_nac, "
                    + "m.municipio municipio,c.cargo cargo,det1.nombre sexo,det2.nombre e_civil, "
                    + "subempresa.nombre subempresa "
                    + "from empleado em "
                    + "inner join municipio m using (cod_municipio) "
                    + "inner join cargo c on (c.cod_cargo=em.cargo) "
                    + "inner join det_lista det1 on (det1.cod_det_lista=em.cod_det_lista_sexo) "
                    + "inner join det_lista det2 on (det2.cod_det_lista=em.cod_det_lista_ecivil) "
                    + "inner join subempresa on (subempresa.nitsubempresa=em.nitsubempresa) "
                    + "where cedula='" + cedula.trim() + "'";

            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                em = new Empleado();                 
                em.setCedula(cedula);
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));                
                em.getEcivil().setNombre(rs.getString("e_civil"));               
                em.getSexo().setNombre(rs.getString("sexo"));
                em.getCargo().setNombre(rs.getString("cargo")); 
                em.setNitsubempresa(rs.getString("subempresa"));

                //calculo de la edad
                fechaNac = rs.getDate("fecha_nac");
                
                Calendar fechaNacimiento = Calendar.getInstance();
                //Se crea un objeto con la fecha actual
                Calendar fechaActual = Calendar.getInstance();
                //Se asigna la fecha recibida a la fecha de nacimiento.
                fechaNacimiento.setTime(fechaNac);
                //Se restan la fecha actual y la fecha de nacimiento
                int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
                //int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
                //int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);  
                
                //Asignamos la edad al atributo de la clase Empleados
                em.setEdad(año); 
            }
            return em;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    
    public ArrayList<Empleado> listarEmpleados(String nitsesion) throws SQLException {
        Empleado em;
        ArrayList<Empleado> listaEmpleado = new ArrayList<>();
        ResultSet rs;
        String sql;
        Consulta consulta = null;           
        
        try {
            consulta = new Consulta(getConexion());
                    sql = "select e.cedula, e.nombres, e.apellidos,c.cargo cargo,ep.eps eps , e.sueldo_mes " +
                      "from empleado e " +
                      "inner join eps ep on (ep.cod_eps=e.cod_eps)"+
                      "inner join cargo c on (c.cod_cargo=e.cargo)"+
                      "where e.nitsubempresa = '" + nitsesion + "'";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                em = new Empleado();
                em.setCedula(rs.getString("cedula"));
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));
                em.getCargo().setNombre(rs.getString("cargo"));
                em.getEps().setNombre(rs.getString("eps"));
                em.setSueldo_mes(rs.getInt("sueldo_mes"));     

                listaEmpleado.add(em);
            }
            return listaEmpleado;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<Empleado> listarEmpleadosAdmin() throws SQLException {
        Empleado em;
        ArrayList<Empleado> listaEmpleado = new ArrayList<>();
        ResultSet rs;
        String sql;
        Consulta consulta = null;           
        
        try {
            consulta = new Consulta(getConexion());
                    sql = "select e.cedula, e.nombres, e.apellidos,c.cargo cargo,ep.eps eps , e.sueldo_mes, sub.nombre nomsub " +
                      " from empleado e " +
                      " inner join eps ep on (ep.cod_eps=e.cod_eps) "+
                      " inner join cargo c on (c.cod_cargo=e.cargo) "+
                      " inner join subempresa sub on (sub.nitsubempresa=e.nitsubempresa) ";                      

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                em = new Empleado();
                em.setCedula(rs.getString("cedula"));
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));
                em.getCargo().setNombre(rs.getString("cargo"));
                em.getEps().setNombre(rs.getString("eps"));
                em.setSueldo_mes(rs.getInt("sueldo_mes"));
                em.setNitsubempresa(rs.getString("nomsub"));
                listaEmpleado.add(em);
            }
            return listaEmpleado;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer guardarPerfilSocioDemografico(Empleado empleado, String nitsesion) throws SQLException{
        Consulta consulta = null;
        Integer resultado;    

        //Sentencia SQL para guardar el registro
        String sql = "";
        try {
            consulta = new Consulta(getConexion());        

                sql = "UPDATE empleado "
                        + " SET cod_det_lista_nescolar='"+empleado.getNescolar().getCodigo()+"', cod_det_lista_num_personas_cargo='"+empleado.getNumPersonas().getCodigo()+"', cod_det_lista_tendencia_vivienda='"+empleado.getTendenciaVivienda().getCodigo()+"', cod_det_lista_uso_tiempo_libre='"+empleado.getUsoTiempoLibre().getCodigo()+"', "
                        + " cod_det_lista_promedio_ingresos='"+empleado.getPromedioIngreso().getCodigo()+"', cod_det_lista_antiguedad_empresa='"+empleado.getAntiguedadEmpresa().getCodigo()+"', cod_det_lista_antiguedad_cargo='"+empleado.getAntiguedadCargo().getCodigo()+"', cod_det_lista_tipo_contrato='"+empleado.getTipoContratacion().getCodigo()+"', "
                        + " cod_det_lista_participacion_actividades='"+empleado.getParticipaActividades().getCodigo()+"', fuma='"+empleado.getFuma()+"', promedio_fuma_diario='"+empleado.getPromedioFuma()+"', diagonosticado_enfermedad='"+empleado.getDiagnosticadoEnfermidad()+"', enfermedad='"+empleado.getEnfermedad()+"', practica_deporte='"+empleado.getPracticaAlgunDeporte()+"', "
                        + " deporte='"+empleado.getDeportePractica()+"',consume_alcohol='"+empleado.isConsBebidasAlcoholicas()+"',cod_det_lista_frecuencia_alcohol='"+empleado.getConsumoBebidasAlcoholicas().getCodigo()+"', frecuencia_deporte='"+empleado.getFrecuenciaDeportePractica()+"' "
                        + " WHERE cedula='"+empleado.getCedula()+"'";
            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }
    
public Integer guardarEmpleado(Empleado empleado, String nitsesion) throws SQLException{
    Consulta consulta = null;
    Integer resultado;    
    
    //Dar formato a la fecha 
    DateFormat formatoFecha1 = new SimpleDateFormat("yyyy/MM/dd");   

    //Sentencia SQL para guardar el registro
    String sql = "";
    try {
        consulta = new Consulta(getConexion());
        ResultSet rs;
        
            sql = "SELECT cod_municipio from municipio where municipio = '" + empleado.getResidencia().getNombre() + "'";
                rs = consulta.ejecutar(sql); 
                if (rs.next()) {
                    empleado.getResidencia().setCodigo(rs.getString("cod_municipio"));                   
                }                                                            
        
            sql = "INSERT INTO empleado ("
                    + "cedula, nombres, apellidos, cod_municipio, "
                    + "cod_eps, cod_det_lista_sexo, cod_det_lista_ecivil, "
                    + "nitsubempresa, fecha_nacimiento, sueldo_mes, aux_transporte, cargo, estado, fecha_ingreso)"
                    + "VALUES ("                                                                                                    
                    + "'" + empleado.getCedula()+ "',"
                    + "'" + empleado.getNombres()+ "',"
                    + "'" + empleado.getApellidos()+ "',"
                    + "'" + empleado.getResidencia().getCodigo()+ "',"
                    + "'" + empleado.getEps().getCodigo()+ "',"
                    + "'" + empleado.getSexo().getCodigo()+ "',"
                    + "'" + empleado.getEcivil().getCodigo()+ "',"                    
                    + "'" + nitsesion + "',"
                    + "'" + formatoFecha1.format(empleado.getFecha_nac()) +"',"
                    + "'" + empleado.getSueldo_mes() +"',"
                    + "'" + empleado.getAux_transporte() +"',"
                    + "'" + empleado.getCargo().getCodigo()+"', true, '"+empleado.getFechaIngreso()+"')";
        resultado = consulta.actualizar(sql);
        return resultado;
    } catch (SQLException ex) {
        throw ex;
    } finally {
        consulta.desconectar();
    }     
}  /**
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

    public Collection<? extends Empleado> cargarPerfilSocioList(String nitsub,String nitem) throws SQLException {
        Empleado em;
        ArrayList<Empleado> listaEmpleado = new ArrayList<>();
        ResultSet rs;
        String sql="";
        Consulta consulta = null;           
        
        try {
            
            if(nitem!=null && nitsub.equals("")){
                
                consulta = new Consulta(getConexion());
                    sql = "select cedula, nombres, apellidos, dlnes.nombre nomnes, dlnpc.nombre nomnpc, dltv.nombre nomtv,  dlutl.nombre nomtl, dlpi.nombre nompi,"
                    + " dlac.nombre nomac, dltc.nombre nomtc,pact.nombre nompact,  fuma, promedio_fuma_diario, diagonosticado_enfermedad, "
                    + "enfermedad, practica_deporte, deporte,  consume_alcohol, dlah.nombre nomah, frecuencia_deporte, dlae.nombre nomae, sem.nombre nomsubem " +
                    "from empleado emp  " +
                    "join det_lista dltc on(dltc.cod_det_lista=emp.cod_det_lista_tipo_contrato)  " +
                    "join det_lista dlae on(dlae.cod_det_lista=emp.cod_det_lista_antiguedad_empresa)  " +
                    "join det_lista dlac on(dlac.cod_det_lista=emp.cod_det_lista_antiguedad_cargo)  " +
                    "join det_lista dlpi on(dlpi.cod_det_lista=emp.cod_det_lista_promedio_ingresos)  " +
                    "join det_lista dlutl on(dlutl.cod_det_lista=emp.cod_det_lista_uso_tiempo_libre)  " +
                    "join det_lista dltv on(dltv.cod_det_lista=emp.cod_det_lista_tendencia_vivienda) " +
                    "join det_lista dlnpc on(dlnpc.cod_det_lista=emp.cod_det_lista_num_personas_cargo)  " +
                    "join det_lista dlnes on(dlnes.cod_det_lista=emp.cod_det_lista_nescolar)  " +
                    "join det_lista dlah on(dlah.cod_det_lista=emp.cod_det_lista_frecuencia_alcohol)  " +
                    "join det_lista pact on(pact.cod_det_lista=emp.cod_det_lista_participacion_actividades) " +
                    "join subempresa sem on (sem.nitsubempresa=emp.nitsubempresa) " +
                    "where emp.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')";      
            }
            
            if(!nitsub.equals("")){
                
                consulta = new Consulta(getConexion());
                    sql = "select cedula, nombres, apellidos, dlnes.nombre nomnes, dlnpc.nombre nomnpc, dltv.nombre nomtv, " +
                    " dlutl.nombre nomtl, dlpi.nombre nompi, dlac.nombre nomac, dltc.nombre nomtc,pact.nombre nompact, " +
                    " fuma, promedio_fuma_diario, diagonosticado_enfermedad, enfermedad, practica_deporte, deporte, " +
                    " consume_alcohol, dlah.nombre nomah, frecuencia_deporte, dlae.nombre nomae,sem.nombre nomsubem " +
                    " from empleado emp " +
                    " join det_lista dltc on(dltc.cod_det_lista=emp.cod_det_lista_tipo_contrato) "+
                    " join det_lista dlae on(dlae.cod_det_lista=emp.cod_det_lista_antiguedad_empresa) " +
                    " join det_lista dlac on(dlac.cod_det_lista=emp.cod_det_lista_antiguedad_cargo) " +
                    " join det_lista dlpi on(dlpi.cod_det_lista=emp.cod_det_lista_promedio_ingresos) " +
                    " join det_lista dlutl on(dlutl.cod_det_lista=emp.cod_det_lista_uso_tiempo_libre) " +
                    " join det_lista dltv on(dltv.cod_det_lista=emp.cod_det_lista_tendencia_vivienda) " +
                    " join det_lista dlnpc on(dlnpc.cod_det_lista=emp.cod_det_lista_num_personas_cargo) " +
                    " join det_lista dlnes on(dlnes.cod_det_lista=emp.cod_det_lista_nescolar) " +
                    " join det_lista dlah on(dlah.cod_det_lista=emp.cod_det_lista_frecuencia_alcohol) " +
                    " join det_lista pact on(pact.cod_det_lista=emp.cod_det_lista_participacion_actividades)"+
                    " join subempresa sem on (sem.nitsubempresa=emp.nitsubempresa) " +
                    " where sem.nitsubempresa='"+nitsub+"'";  
            }                   

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                em = new Empleado();
                em.setCedula(rs.getString("cedula"));
                em.setSubempresa(new SubEmpresa(nitsub, rs.getString("nomsubem")));
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));
                em.getNescolar().setNombre(rs.getString("nomnes"));
                em.getNumPersonas().setNombre(rs.getString("nomnpc"));
                em.getTendenciaVivienda().setNombre(rs.getString("nomtv"));
                em.getUsoTiempoLibre().setNombre(rs.getString("nomtl"));
                em.getPromedioIngreso().setNombre(rs.getString("nompi"));
                em.getAntiguedadCargo().setNombre(rs.getString("nomac"));
                em.getAntiguedadEmpresa().setNombre(rs.getString("nomae"));
                em.getTipoContratacion().setNombre(rs.getString("nomtc"));
                em.getParticipaActividades().setNombre(rs.getString("nompact"));
                em.setFuma(rs.getBoolean("fuma"));
                em.setPromedioFuma(rs.getString("promedio_fuma_diario"));
                em.setDiagnosticadoEnfermidad(rs.getBoolean("diagonosticado_enfermedad"));
                em.setEnfermedad(rs.getString("enfermedad"));
                em.setPracticaAlgunDeporte(rs.getBoolean("practica_deporte"));
                em.setDeportePractica(rs.getString("deporte"));
                em.setConsBebidasAlcoholicas(rs.getBoolean("consume_alcohol"));
                em.getConsumoBebidasAlcoholicas().setNombre("nomah");
                em.setFrecuenciaDeportePractica(rs.getString("frecuencia_deporte"));                
                
                listaEmpleado.add(em);
            }
            return listaEmpleado;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
}

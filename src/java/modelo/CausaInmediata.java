/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Andres
 */
public class CausaInmediata {

    private String codigo;
    private String nombre;
    
    
    public CausaInmediata(String codigo, String nombre) {
      
        this.codigo = codigo;
        this.nombre = nombre;        
    }

    public CausaInmediata() {

    }  

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }        
}

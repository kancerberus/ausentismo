/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Andres
 */
public class GrupoConcurso implements Serializable{

    private String codGrupo;
    private Concurso concurso;
    private String nombre;    

    public GrupoConcurso(String codGrupo, Concurso concurso, String nombre) {
        this.codGrupo = codGrupo;
        this.concurso = concurso;
        this.nombre = nombre;        
    }      

    public GrupoConcurso() {

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

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }    
    
}

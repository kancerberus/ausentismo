/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.util.Date;


public class Empleado {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String nitsubempresa;
    private Date fecha_nac;
    private int edad;
    private Cargo cargo;
    private NivelEscolar nescolar;
    private NumeroPersonasCargo numPersonas;
    private TendenciaVivienda tendenciaVivienda;
    private UsoTiempoLibre usoTiempoLibre;
    private PromedioIngreso promedioIngreso;
    private AntiguedadEmpresa antiguedadEmpresa;
    private AntiguedadCargo antiguedadCargo;
    private TipoContratacion tipoContratacion;
    private ParticipaActividades participaActividades;
    private Boolean fuma;
    private String promedioFuma;
    private Boolean diagnosticadoEnfermidad;
    private String enfermedad;    
    private ConsumoBebidasAlcoholicas consumoBebidasAlcoholicas;
    private Boolean consBebidasAlcoholicas;
    private Boolean practicaAlgunDeporte;
    private String deportePractica;
    private String frecuenciaDeportePractica;
    private int sueldo_mes;
    private int aux_transporte;
    private Ecivil ecivil;    
    private Municipio municipio; 
    private Sexo sexo; 
    private int thacum;
    private String anoacum;
    private Eps eps;    
    private Municipio Residencia;
    private Boolean estado;
    private Date fechaIngreso;    
    
    private Integer cantidad;
    private Float porcentaje;
    private String descDistribucion;
    private Integer total;
    private SubEmpresa subempresa;
    
    public Empleado() {
        this.municipio = new Municipio();
        this.fecha_nac = new Date();
        this.Residencia = new Municipio();
        this.cargo = new Cargo();
        this.ecivil = new Ecivil();
        this.sexo = new Sexo();
        this.eps = new Eps();      
        this.nescolar=new NivelEscolar();        
        this.tendenciaVivienda=new TendenciaVivienda();        
        this.numPersonas=new NumeroPersonasCargo();
        this.tendenciaVivienda=new TendenciaVivienda();
        this.usoTiempoLibre=new UsoTiempoLibre();
        this.promedioIngreso=new PromedioIngreso();
        this.antiguedadEmpresa=new AntiguedadEmpresa();
        this.antiguedadCargo=new AntiguedadCargo();
        this.tipoContratacion=new TipoContratacion();
        this.participaActividades=new ParticipaActividades();
        this.consumoBebidasAlcoholicas=new ConsumoBebidasAlcoholicas();        
    }

    public Empleado(String cedula, String nombres, String apellidos) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Empleado(Integer cantidad, Float porcentaje, String descDistribucion, Integer total) {
        this.cantidad = cantidad;
        this.porcentaje = porcentaje;
        this.descDistribucion = descDistribucion;
        this.total=total;
    }

    public SubEmpresa getSubempresa() {
        return subempresa;
    }

    public void setSubempresa(SubEmpresa subempresa) {
        this.subempresa = subempresa;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    
    
    public String getDescDistribucion() {
        return descDistribucion;
    }

    public void setDescDistribucion(String descDistribucion) {
        this.descDistribucion = descDistribucion;
    }

    
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Boolean getConsBebidasAlcoholicas() {
        return consBebidasAlcoholicas;
    }

    public void setConsBebidasAlcoholicas(Boolean consBebidasAlcoholicas) {
        this.consBebidasAlcoholicas = consBebidasAlcoholicas;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    

    public String getFrecuenciaDeportePractica() {
        return frecuenciaDeportePractica;
    }

    public void setFrecuenciaDeportePractica(String frecuenciaDeportePractica) {
        this.frecuenciaDeportePractica = frecuenciaDeportePractica;
    }

    public String getPromedioFuma() {
        return promedioFuma;
    }

    public void setPromedioFuma(String promedioFuma) {
        this.promedioFuma = promedioFuma;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getDeportePractica() {
        return deportePractica;
    }

    public void setDeportePractica(String deportePractica) {
        this.deportePractica = deportePractica;
    }

    public Boolean getFuma() {
        return fuma;
    }

    public void setFuma(Boolean fuma) {
        this.fuma = fuma;
    }

    public Boolean getDiagnosticadoEnfermidad() {
        return diagnosticadoEnfermidad;
    }

    public void setDiagnosticadoEnfermidad(Boolean diagnosticadoEnfermidad) {
        this.diagnosticadoEnfermidad = diagnosticadoEnfermidad;
    }

    public ConsumoBebidasAlcoholicas getConsumoBebidasAlcoholicas() {
        return consumoBebidasAlcoholicas;
    }

    public void setConsumoBebidasAlcoholicas(ConsumoBebidasAlcoholicas consumoBebidasAlcoholicas) {
        this.consumoBebidasAlcoholicas = consumoBebidasAlcoholicas;
    }

    public Boolean getPracticaAlgunDeporte() {
        return practicaAlgunDeporte;
    }

    public void setPracticaAlgunDeporte(Boolean practicaAlgunDeporte) {
        this.practicaAlgunDeporte = practicaAlgunDeporte;
    }

    public ParticipaActividades getParticipaActividades() {
        return participaActividades;
    }

    public void setParticipaActividades(ParticipaActividades participaActividades) {
        this.participaActividades = participaActividades;
    }

    public TipoContratacion getTipoContratacion() {
        return tipoContratacion;
    }

    public void setTipoContratacion(TipoContratacion tipoContratacion) {
        this.tipoContratacion = tipoContratacion;
    }

    public AntiguedadEmpresa getAntiguedadEmpresa() {
        return antiguedadEmpresa;
    }

    public void setAntiguedadEmpresa(AntiguedadEmpresa antiguedadEmpresa) {
        this.antiguedadEmpresa = antiguedadEmpresa;
    }

    public AntiguedadCargo getAntiguedadCargo() {
        return antiguedadCargo;
    }

    public void setAntiguedadCargo(AntiguedadCargo antiguedadCargo) {
        this.antiguedadCargo = antiguedadCargo;
    }

    public PromedioIngreso getPromedioIngreso() {
        return promedioIngreso;
    }

    public void setPromedioIngreso(PromedioIngreso promedioIngreso) {
        this.promedioIngreso = promedioIngreso;
    }

    public UsoTiempoLibre getUsoTiempoLibre() {
        return usoTiempoLibre;
    }

    public void setUsoTiempoLibre(UsoTiempoLibre usoTiempoLibre) {
        this.usoTiempoLibre = usoTiempoLibre;
    }

    public TendenciaVivienda getTendenciaVivienda() {
        return tendenciaVivienda;
    }

    public void setTendenciaVivienda(TendenciaVivienda tendenciaVivienda) {
        this.tendenciaVivienda = tendenciaVivienda;
    }

    public NumeroPersonasCargo getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(NumeroPersonasCargo numPersonas) {
        this.numPersonas = numPersonas;
    }

    public NivelEscolar getNescolar() {
        return nescolar;
    }

    public void setNescolar(NivelEscolar nescolar) {
        this.nescolar = nescolar;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getAnoacum() {
        return anoacum;
    }


    public void setAnoacum(String anoacum) {
        this.anoacum = anoacum;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio() {
        this.municipio = municipio;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }   

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Municipio getResidencia() {
        return Residencia;
    }

    public void setResidencia(Municipio Residencia) {
        this.Residencia = Residencia;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Ecivil getEcivil() {
        return ecivil;
    }

    public void setEcivil(Ecivil ecivil) {
        this.ecivil = ecivil;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Eps getEps() {
        return eps;
    }

    public void setEps(Eps eps) {
        this.eps = eps;
    }      

    public int getSueldo_mes() {
        return sueldo_mes;
    }

    public void setSueldo_mes(int sueldo_mes) {
        this.sueldo_mes = sueldo_mes;
    } 

    public int getAux_transporte() {
        return aux_transporte;
    }

    public void setAux_transporte(int aux_transporte) {
        this.aux_transporte = aux_transporte;
    }  

    public String getNitsubempresa() {
        return nitsubempresa;
    }

    public void setNitsubempresa(String nitsubempresa) {
        this.nitsubempresa = nitsubempresa;
    } 

    public int getThacum() {
        return thacum;
    }

    public void setThacum(int thacum) {
        this.thacum = thacum;
    }
    
}

package mi.paquete.progra;

//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;

public class Curso {
	private String codigo;
	private String nombre;
	private String seccion;
	//private Date date;
	
	@SuppressWarnings("deprecation")
	public Curso(String line)  {
		String[] cursoData = line.split(",");
		
		if (cursoData.length == 1) {
			Frames.ta.setText("Exception: Seleccione un archivo válido.");
		} else {			
			this.codigo  = cursoData[0].trim();
			this.nombre  = cursoData[1].trim();
			this.seccion = cursoData[2].trim();
			
		}
		
	}
	
	public String getConcatenatedCurso() {
		return this.getCodigo() + "," + this.getNombre() + "," + this.getSeccion();
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String valor) {
		codigo = valor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String valor) {
		this.nombre = valor;
	}
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String valor) {
		this.seccion = valor;
	}
}

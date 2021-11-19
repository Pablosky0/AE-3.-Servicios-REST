package serviciorest.cliente.entidad;

import org.springframework.stereotype.Component;

@Component
public class Videojuego{
	
	private int id;
	private String nombre;
	private String compañia;
	private Float nota;
	
	
	public Videojuego() {
		super();
	}


	public Videojuego(int iD, String nombre, String compañia, Float nota) {
		super();
		this.id = iD;
		this.nombre = nombre;
		this.compañia = compañia;
		this.nota = nota;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCompañia() {
		return compañia;
	}


	public void setCompañía(String compañía) {
		this.compañia = compañía;
	}


	public Float getNota() {
		return nota;
	}


	public void setNota(Float nota) {
		this.nota = nota;
	}


	@Override
	public String toString() {
		return "id=" + id + ", nombre=" + nombre + ", compañía=" + compañia + ", nota=" + nota;
	}
	

}

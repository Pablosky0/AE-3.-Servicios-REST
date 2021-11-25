package serviciorest.persistencia;

import java.util.ArrayList;

import java.util.List;
import org.springframework.stereotype.Component;

import serviciorest.entidad.Videojuego;

@Component
public class DaoVideojuego {
	public List<Videojuego> listaVideojuegos;
	public int contador;
	
	public DaoVideojuego() {
		System.out.println("DaoVideojuego: Creando lista de videojuegos");
		listaVideojuegos = new ArrayList<Videojuego>();
		Videojuego v1 = new Videojuego(contador++, "Pokemon rojo", "Nintendo", 90);
		Videojuego v2 = new Videojuego(contador++, "The Witcher 3", "CD Projeckt Red", 100);
		Videojuego v3 = new Videojuego(contador++, "Cyberpunk 2077", "CD Projeckt Red", 80);
		Videojuego v4 = new Videojuego(contador++, "Bioshock", "2K Games", 80);
		Videojuego v5 = new Videojuego(contador++, "Super Smash Bros Brawl", "Nintendo", 100);
		listaVideojuegos.add(v1);
		listaVideojuegos.add(v2);
		listaVideojuegos.add(v3);
		listaVideojuegos.add(v4);
		listaVideojuegos.add(v5);
	}
	
	//Metodo que devuelve un videojuego a partir de su id
	public Videojuego get(int posicion) {
		try {
			return listaVideojuegos.get(posicion);
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Videojuego fuera de rango");
			return null;
		}
	}
	
	//Metodo que devuelve la lista de videojuegos
	public List<Videojuego> list() {
		return listaVideojuegos;
	}
	
	//Metodo que introduce un videojuego a la lista
	public void add(Videojuego v) {
		int cont= 0;
		for (int i = 0; i< listaVideojuegos.size(); i++) {
			if (v.getNombre() == listaVideojuegos.get(i).getNombre()) {
				cont++;
				break;
			}
		}	
		if (cont >0) {
			System.out.println("Ese nombre ya esta");
		}else {
		v.setId(contador++);
		listaVideojuegos.add(v);
		}
	}
	
	//Metodo que borra un videojuego por id
	public Videojuego delete(int posicion) {
		try {
			return listaVideojuegos.remove(posicion);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Videojuego fuera de rango");
			return null;
		}
	}
	
	//Metodo que modifica un videojuego por id
	public Videojuego update(Videojuego v) {
		try {
			Videojuego vAux = listaVideojuegos.get(v.getId());
			vAux.setNombre(v.getNombre());
			vAux.setCompañia(v.getCompañia());
			vAux.setNota(v.getNota());
			
			return vAux;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Videojuego fuera de rango");
			return null;
		}
	}
}

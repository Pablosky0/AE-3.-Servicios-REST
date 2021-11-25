package serviciorest.controlador;

import org.springframework.http.MediaType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import serviciorest.entidad.Videojuego;
import serviciorest.persistencia.DaoVideojuego;

@RestController
public class ControladorVideojuego {
	@Autowired
	private DaoVideojuego daoVideojuego;
	
	//Mostramos un videojuego por id
	@GetMapping(path="videojuegos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> getVideojuego(@PathVariable("id") int id) {
		System.out.println("Buscando videojuego por id: " + id);
		Videojuego v = daoVideojuego.get(id);
		if (v != null) {
			return new ResponseEntity<Videojuego>(v,HttpStatus.OK);
		}else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Damos de alta un videojuego
	@PostMapping(path="videojuegos", consumes= MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> altaVideojuego(@RequestBody Videojuego v) {
		int cont = 0;
		for (int i = 0; i< daoVideojuego.listaVideojuegos.size(); i++) {
			if (v.getNombre().equalsIgnoreCase(daoVideojuego.listaVideojuegos.get(i).getNombre())) {
				cont++;
			}
		}	
		if (cont == 0) {
			System.out.println("Alta Videojuego: objeto videojuego: " + v);
			daoVideojuego.add(v);
			return new ResponseEntity<Videojuego>(v,HttpStatus.CREATED);
			
		}else {
			return new ResponseEntity<Videojuego>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//Mostramos la lista de videojuegos
	@GetMapping(path="videojuegos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Videojuego>> listarVideojuegos() {
		List<Videojuego> listaVideojuegos = null;
		System.out.println("Listando los videojuegos");
		listaVideojuegos = daoVideojuego.list();
		System.out.println(listaVideojuegos);
		return new ResponseEntity<List<Videojuego>>(listaVideojuegos,HttpStatus.OK);
	}
	
	//Modificamos un videojuego por id
	@PutMapping(path = "videojuegos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> modificarVideojuego(
			@PathVariable("id") int id,
			@RequestBody Videojuego v) {
		int cont = 0;
		for (int i = 0; i< daoVideojuego.listaVideojuegos.size(); i++) {
			if (v.getNombre().equalsIgnoreCase(daoVideojuego.listaVideojuegos.get(i).getNombre())) {
				cont++;
			}
		}	
		if (cont == 0) {
			v.setId(id);
			Videojuego vUpdate = daoVideojuego.update(v);
			if (vUpdate != null) {
				return new ResponseEntity<Videojuego>(HttpStatus.OK);
			}else {
				return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<Videojuego>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	//Borramos un videojuego por id
	@DeleteMapping(path = "videojuegos/{id}")
	public ResponseEntity<Videojuego> borrarVideojuego(@PathVariable int id) {
		System.out.println("IDa borrar: " + id);
		Videojuego v = daoVideojuego.delete(id);
		if (v != null) {
			return new ResponseEntity<Videojuego>(v,HttpStatus.OK);
		}else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);
		}
	}
}

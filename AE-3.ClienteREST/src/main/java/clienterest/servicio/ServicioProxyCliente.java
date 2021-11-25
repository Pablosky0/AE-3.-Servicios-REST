package clienterest.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import clienterest.entidad.Videojuego;

//Damos de alta el servicio
@Service
public class ServicioProxyCliente {
	//La URL del servicio REST
	public static final String URL = "http://localhost:2018/videojuegos/";
	
	//Inyectamos el objeto RestTemplate para hacer las peticiones HTTP
	@Autowired
	private RestTemplate restTemplate;
	
	//Metodo que obtiene un videojuego del servidor REST a partir de su id
	public Videojuego obtener(int id) {
		try {
			ResponseEntity<Videojuego> re = restTemplate.getForEntity(URL + id, Videojuego.class);
			HttpStatus hs = re.getStatusCode();
			if (hs == HttpStatus.OK) {
				return re.getBody();
			}else {
				System.out.println("Respuesta no contemplada");
				return null;
			}
		} catch (HttpClientErrorException e) {
			System.out.println("Obtener: el videojuego No se ha encontrado, id: " + id);
			System.out.println("Obtener: Codigo de respuesta: " + e.getStatusCode());
			return null;
		}
	}
	
	//Metodo que da de alta un videojuego en servidor REST
	public Videojuego alta(Videojuego v) {
		try {
			ResponseEntity<Videojuego> re = restTemplate.postForEntity(URL, v, Videojuego.class);
			System.out.println("Alta: Codigo de respuesta " + re.getStatusCodeValue());
			return re.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("Alta: el videojuego NO se ha dado de alta, id: " + v);
			System.out.println("Alta: Codigo de respuesta: " + e.getStatusCode());
			return null;
		}
	}
	
	//Modificar un videojuego del servidor REST a partir de su id
	public boolean modificar(Videojuego v){
		try {
			//El metodo put de Spring no devuelve nada
			//si no da error se ha dado de alta y si no daria una 
			//excepcion
			restTemplate.put(URL + v.getId(), v, Videojuego.class);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("modificar -> el videojuego NO se ha modificado, id: " + v.getId());
		    System.out.println("modificar -> Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
	}
	
	//Metodo que borra una persona del servidor REST a partir de su id
	public boolean borrar(int id) {
		try {
			restTemplate.delete(URL + id);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("Borrar: El videojuego NO se ha de borrado, id: " + id);
			System.out.println("Borrar: Codigo de respuesta: " + e.getStatusCode());
			return false;
		}
	}
	
	//Metodo que devuelve todos los videojuegos del servidor REST
	public List<Videojuego> listar() {
		try {
			ResponseEntity<Videojuego[]> response = 
					restTemplate.getForEntity(URL, Videojuego[].class);
			Videojuego[] arrayVideojuegos = response.getBody();
			return Arrays.asList(arrayVideojuegos);
		} catch (HttpClientErrorException e) {
			System.out.println("Listar: Error al obtener la lista de videojuegos");
			System.out.println("Listar: Codigo de respuesta: " + e.getStatusCode());
			return null;
		}
	}
}

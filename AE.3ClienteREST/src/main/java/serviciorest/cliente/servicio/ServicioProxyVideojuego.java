package serviciorest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Videojuego;


//Con esta anotacion damos de alta un objeto de tipo
//ServicioProxyPersona dentro del contexto de Spring
@Service
public class ServicioProxyVideojuego {

	public static final String URL = "http://localhost:8080/videojuegos/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	private HttpStatus codigoHTTP;
	
	public HttpStatus getCodigoHTTP() {
		return codigoHTTP;
	}
	
	public Videojuego obtener(int id){
		try {
			ResponseEntity<Videojuego> re = restTemplate.getForEntity(URL + id, Videojuego.class);
			
			codigoHTTP = re.getStatusCode();;
			
			if(codigoHTTP == HttpStatus.OK) {				
				return re.getBody();
			}else {
				return null;
			}
		}catch (HttpClientErrorException e) {
		    return null;
		}
	}
	
	public Videojuego alta(Videojuego p){
		try {
			ResponseEntity<Videojuego> re = restTemplate.postForEntity(URL, p, Videojuego.class);
			codigoHTTP = re.getStatusCode();
			return re.getBody();
		} catch (HttpClientErrorException e) {
			codigoHTTP = e.getStatusCode();
		    return null;
		}
	}
	
	public boolean modificar(int idAModificar, Videojuego p){
		try {
			restTemplate.put(URL + idAModificar, p, Videojuego.class);
			codigoHTTP =  HttpStatus.OK;
			return true;
		} catch (HttpClientErrorException e) {
			codigoHTTP = e.getStatusCode();
		    return false;
		}
	}
	
	public boolean borrar(int id){
		try {
			restTemplate.delete(URL + id);
			codigoHTTP =  HttpStatus.OK;
			return true;
		} catch (HttpClientErrorException e) {
			codigoHTTP = e.getStatusCode();
		    return false;
		}
	}
	
	public List<Videojuego> listar(){
		
		try {
			ResponseEntity<Videojuego[]> response = restTemplate.getForEntity(URL,Videojuego[].class);
			Videojuego[] arrayPersonas = response.getBody();
			codigoHTTP = response.getStatusCode();
			return Arrays.asList(arrayPersonas);
		} catch (HttpClientErrorException e) {
			codigoHTTP = e.getStatusCode();
		    return null;
		}
	}
}

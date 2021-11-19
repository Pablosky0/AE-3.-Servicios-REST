package serviciorest.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Videojuego;
import serviciorest.cliente.servicio.ServicioProxyVideojuego;

@SpringBootApplication
public class Application implements CommandLineRunner{

	Scanner lector = new Scanner(System.in);
	
	@Autowired
	private ServicioProxyVideojuego spv;
	
	
	@Autowired
	private ApplicationContext context;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(Application.class, args);

	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Cliente -> Ejecutando programa");
		
		boolean continuar = true;
		String opcion;
		String resultado;

		while (continuar == true) {
			System.out.println("1- Dar de alta un videojuego");
			System.out.println("2- Dar de baja un videojuego por ID");
			System.out.println("3- Modificar un videojuego por ID");
			System.out.println("4- Obtener un videojuego por ID");
			System.out.println("5- Listar todos los videojuegos");
			System.out.println("6- Salir");
			opcion = lector.nextLine();
			switch (opcion) {
			case "1": //alta
				resultado = alta();
				break;
			case "2": //baja implementado
				resultado = baja();
				break;
			case "3": //modificar
				resultado = modificar();
				break;
			case "4": //Obtener implementado
				resultado = Obtener();
				break;
			case "5": //lista implementado
				resultado = lista();
				break;
			case "6":
				continuar = false;
				resultado = "Saliendo del programa";
				break;
			default:
				resultado = "\nOpción no contemplada\n";
			}
			System.out.println(resultado);
		}
		lector.close();
		
		SpringApplication.exit(context, () -> 0);
	}

	private String alta() {
		String resultado = "";
		int id;
		String nombre;
		String compañia;
		Float nota;
		Videojuego vj;
		Videojuego vjPedido;

		HttpStatus codigoHTTP;


		System.out.println("\n\nalta por ID :");
		System.out.println("Introduce Id ");
		id = Integer.parseInt(lector.nextLine());
		System.out.println("Introduce nombre ");
		nombre = lector.nextLine();
		System.out.println("Introduce compañia ");
		compañia = lector.nextLine();
		System.out.println("Introduce nota ");
		nota = Float.parseFloat(lector.nextLine());
		vj = new Videojuego(id, nombre, compañia, nota);
		
		
		spv.alta(vj);
		
		codigoHTTP = spv.getCodigoHTTP();
		
		switch (codigoHTTP) {
		case CREATED:
			resultado = "El servidor ha creado el videojuego\n";
			resultado += vj.toString();
			break;
		case INTERNAL_SERVER_ERROR:
			resultado = "El servidor no ha podido responder la petición.";
			break;
		case BAD_REQUEST:
			vjPedido = spv.obtener(id);			
			if (vjPedido == null) {
				resultado = "El id del videojuego a crear ya exite.";	
			} else {
				resultado = "El nombre del videojuego a crear ya exite.";	
			}
			break;
		default:
			resultado = "Respuesta del servidor no contemplada.";
			break;
		}
				
		return resultado;
	}

	private String baja() {
		String resultado = "";
		int id;
		System.out.println("\n\nbaja por ID :");
		System.out.println("Introduce Id ");
		id = Integer.parseInt(lector.nextLine());
		HttpStatus codigoHTTP;

	
		spv.borrar(id);	
		codigoHTTP = spv.getCodigoHTTP();
		
		switch (codigoHTTP) {
		case OK:
			resultado = "El servidor ha eliminado el videojuego con ID=" + id +".";
			break;
		case NOT_FOUND:
			resultado = "El servidor no ha encontrado un Videojuego con ID=" + id +".";
			break;
		case INTERNAL_SERVER_ERROR:
			resultado = "El servidor no ha podido responder la petición.";
			break;
		default:
			resultado = "Respuesta del servidor no contemplada.";
			break;
		}
		
		return resultado;
	}

	private String modificar() {
		String resultado = "";
		HttpStatus codigoHTTP;

		int id;
		int idAmodificar;
		String nombre;
		String compañia;
		Float nota;
		Videojuego vj;
		Videojuego vjPedido;


		System.out.println("\n\nalta por ID :");
		System.out.println("Introduce Id del videojuego a modificar");
		idAmodificar = Integer.parseInt(lector.nextLine());
		System.out.println("Introduce Id ");
		id = lector.nextInt();
		System.out.println("Introduce nombre ");
		nombre = lector.nextLine();
		System.out.println("Introduce compañia ");
		compañia = lector.nextLine();
		System.out.println("Introduce nota ");
		nota = Float.parseFloat(lector.nextLine());
		
		vj = new Videojuego(id, nombre, compañia, nota);

		spv.modificar(idAmodificar, vj);
		
		codigoHTTP = spv.getCodigoHTTP();
		switch (codigoHTTP) {
		case OK:
			resultado = "El servidor ha modificado el videojuego seleccionado.";
			break;
		case NOT_FOUND:
			resultado = "El servidor no ha encontrado el videojuego que quiere modificar.";
			break;
		case INTERNAL_SERVER_ERROR:
			resultado = "El servidor no ha podido responder la petición.";
			break;
		case BAD_REQUEST:
			vjPedido = spv.obtener(vj.getId());			
			if (vjPedido == null) {
				resultado = "El id del videojuego a crear ya exite.";	
			} else {
				resultado = "El nombre del videojuego a crear ya exite.";	
			}

			break;
		default:
			resultado = "Respuesta del servidor no contemplada.";
			break;
		}

		return resultado;
	}
	
	private String Obtener() {
		String resultado = "";
		Videojuego vjObtenido = new Videojuego();
		int id;
		HttpStatus codigoHTTP;
		
		System.out.println("\n\nConsulta por ID :");
		System.out.println("Introduce Id ");
		id =Integer.parseInt(lector.nextLine());

		vjObtenido = spv.obtener(id);
		
		codigoHTTP = spv.getCodigoHTTP();
		switch (codigoHTTP) {
		case OK:
			resultado = "El servidor ha devuelto el videojuego:\n";
			resultado += vjObtenido.toString();
			break;
		case NOT_FOUND:
			resultado = "El servidor no ha encontrado un Videojuego con ID=" + id +".";
			break;
		case INTERNAL_SERVER_ERROR:
			resultado = "El servidor no ha podido responder la petición.";
			break;
		default:
			resultado = "Respuesta del servidor no contemplada.";
			break;
		}

		return resultado;
	}
	
	private String lista() {
		String resultado = "";
		List<Videojuego> listaResultado = new ArrayList<Videojuego>();
		HttpStatus codigoHTTP;

		listaResultado = spv.listar();

		codigoHTTP = spv.getCodigoHTTP();
		switch (codigoHTTP) {
		case OK:
			resultado = "El servidor ha devuelto " + listaResultado.size() +" videojuego:\n";
			for (Videojuego elemento: listaResultado) {
				resultado += elemento.toString() + "\n";
			}
			break;
		case NOT_FOUND:
			resultado = "El servidor no ha devuelto ningún videojuego.";
			break;
		case INTERNAL_SERVER_ERROR:
			resultado = "El servidor no ha podido responder la petición.";
			break;
		default:
			resultado = "Respuesta del servidor no contemplada.";
			break;
		}
		
		
		return resultado;
	}

	
	
}

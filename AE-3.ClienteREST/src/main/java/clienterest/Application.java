package clienterest;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import clienterest.entidad.Videojuego;
import clienterest.servicio.ServicioProxyCliente;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	//inyectamos el objeto
	@Autowired
	private ServicioProxyCliente spc;
	
	//Inyectamos el contexto de Spring
	@Autowired
	private ApplicationContext context;
	
	//Damos de alta el objeto RestTemplate que servira para hacer las peticiones HTTP
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	
	public static void main(String[] args) {
		System.out.println("Cliente: Cargando el contexto de SPring");
		SpringApplication.run(Application.class, args);
	}

	//Metodo dinamico para acceder a los atributos dinamicos
	@Override
	public void run(String... args) throws Exception {
		Scanner leer = new Scanner(System.in);
		boolean continuar = true;
		//Hacemos un bucle infinito de el que se saldra con una opcion del menu
		do {
			System.out.println("Cliente: Elige la opcion de lo que quieras  hacer: \n 1) Dar de alta un videojuego \n 2) Dar de baja un videojuego por ID \n 3) Modificar un videojuego por ID \n 4) Obtener un videojuego por ID \n 5) Listar todos los videojuegos \n 6) Salir");
			String respuesta = leer.next();
			
			switch (respuesta) {
			//Dar de alta un videojuego
			case "1":
				Videojuego video = new Videojuego();
				System.out.println("Introduzca el nombre del videojuego: ");
				respuesta = leer.next();
				video.setNombre(respuesta);
				System.out.println("Introduzca el nombre de la compañia: ");
				respuesta = leer.next();
				video.setCompañia(respuesta);
				System.out.println("Introduzca la nota del videojuego: ");
				respuesta = leer.next();
				try {
				video.setNota(Integer.parseInt(respuesta));
				} catch (NumberFormatException e) {
					System.out.println("La nota debe ser un numero, se puso 0 por defecto");
				}
				Videojuego vAlta = spc.alta(video);
				System.out.println("run -> Videojuego dada de alta " + vAlta);
				break;
				
			//Dar de baja un videjuego por el id
			case "2":
				System.out.println("Introduzca el id del videojuego que quiere borrar: ");
				respuesta = leer.next();
				boolean borrada = spc.borrar(Integer.parseInt(respuesta));
				System.out.println("run -> Videojuego con id " + respuesta + " borrado? " + borrada);	
				break;
				
			//Modificar un videojuego por el id
			case "3":
				Videojuego vModificar = new Videojuego();
				System.out.println("Introduzca el id del videojuego que quiere modificar: ");
				respuesta = leer.next();
				vModificar.setId(Integer.parseInt(respuesta));
				System.out.println("Introduzca el nombre del videojuego: ");
				respuesta = leer.next();
				vModificar.setNombre(respuesta);
				System.out.println("Introduzca el nombre de la compañia: ");
				respuesta = leer.next();
				vModificar.setCompañia(respuesta);
				System.out.println("Introduzca la nota del videojuego: ");
				respuesta = leer.next();
				try {
				vModificar.setNota(Integer.parseInt(respuesta));
				} catch (NumberFormatException e) {
					System.out.println("La nota debe ser un numero");
				}
				boolean modificada = spc.modificar(vModificar);
				System.out.println("run -> videojuego modificad0? " + modificada);
				break;
				
			//Mostrar un videojuego por el id
			case "4":
				System.out.println("Introduzca el id del videojuego que quiere: ");
				respuesta = leer.next();
				video = spc.obtener(Integer.parseInt(respuesta));
				System.out.println("run ->a" + video);
				break;
				
			//Listar todos los videojuegos
			case "5":
				List<Videojuego> listaVideojuegos = spc.listar();
				listaVideojuegos.forEach((v) -> System.out.println(v));
				System.out.println("");
				break;
				
			//Salir de la aplicacion
			case "6":
				continuar = false;	
				System.out.println("Adios");	
				pararAplicacion();
				break;
			default:
				System.out.println("Escriba el numero de la opcion que desea");
				break;
			}
			
		} while (continuar);
	}
	
	public void pararAplicacion() {
		SpringApplication.exit(context, () -> 0);
	}

}

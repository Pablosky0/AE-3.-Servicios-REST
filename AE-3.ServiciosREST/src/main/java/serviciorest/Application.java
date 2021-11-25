package serviciorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//Arrancamos el contexto de Spring y damos de alta todos los objetos
		//y sus dependencias con otros objetos
		SpringApplication.run(Application.class, args);
		System.out.println("Servidor Rest: Contexto de Spring cargado");
	}

}

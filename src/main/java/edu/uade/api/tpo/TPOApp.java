package edu.uade.api.tpo;

import edu.uade.api.tpo.services.implemented.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TPOApp {

	public static void main(String[] args) {
		SpringApplication.run(TPOApp.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner(EdificioServiceImpl edificioService, UnidadServiceImpl unidadService, PersonaServiceImpl personaService, ReclamoServiceImpl reclamoService, ImagenServiceImpl imagenService) {
		return args->{
			/*//Traer unidad
			Optional<Unidad> unidad= unidadService.findById(15);
			System.err.println(unidad.get().toString());
			
			// Sacar su edificio
			Edificio edificioUnidad= unidad.get().getEdificio();
			System.err.println(edificioUnidad.getDireccion());
			
			//Sacar dueños e inquilinos
			List<Persona> inquilinos= unidad.get().getInquilinos();
			inquilinos.forEach(i -> System.err.println("    "  + i.getNombre()));
			/*List<Persona> duenios= unidad.get().getDuenios();
			duenios.forEach(d -> System.out.println("    "  + d.getNombre()));
			
			//Crear reclamo
			
			Reclamo reclamo= new Reclamo(inquilinos.get(0),edificioUnidad,"Techo cocina","Gotera",unidad.get());
			reclamo= reclamoService.save(reclamo);
			System.err.println(reclamo.getNumero());
			Imagen imagen= new Imagen("urlPrueba","jpg");
			//imagenService.save(imagen, reclamo.getNumero());
			//reclamo.agregarImagen("urlPrueba","jpg");

			//Crear imagen


			//imagenService.save(imagen);
			
			// Traer edificio
			Optional<Edificio> edificio= edificioService.findById(2);
			System.err.println(edificio.get().getDireccion());
			
			//Traer persona
			
			Optional<Persona> persona= personaService.findById("DNI29988738");
			System.err.println(persona.get().getNombre());*/
		};
			
		}
		

}

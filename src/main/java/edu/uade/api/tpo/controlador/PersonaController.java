package edu.uade.api.tpo.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uade.api.tpo.modelo.Persona;
import edu.uade.api.tpo.modelo.Unidad;
import edu.uade.api.tpo.services.implemented.PersonaServiceImpl;
import edu.uade.api.tpo.services.implemented.UnidadServiceImpl;
import edu.uade.api.tpo.views.PersonaView;
import edu.uade.api.tpo.views.UnidadView;

@RestController
@RequestMapping("/api/persona")
public class PersonaController {
	@Autowired
	PersonaServiceImpl personaService;
	
	@GetMapping()
	public List<PersonaView> obtenerPersonas(){
		List<PersonaView> personas = new ArrayList<PersonaView>();
		Iterable<Persona> iterable =this.personaService.findAll();
		for(Persona u : iterable) {
			personas.add(u.toView());
		}
		return personas;
	} 
	
	@PostMapping()
	public ResponseEntity<?> guardarPersona(@RequestBody Persona persona){
		return ResponseEntity.status(HttpStatus.CREATED).body(personaService.save(persona));
	}
	
	@GetMapping("/{documento}")
	public ResponseEntity<?> obtenerPersonaPorId(@PathVariable String documento){
		Optional<Persona> persona = personaService.findById(documento);
		if(!persona.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(persona.get().toView());
	}
	
	@PutMapping("/{documento}")
	public ResponseEntity<?> updatePersonaPorId(@RequestBody Persona persona, @PathVariable(value = "documento") String documento){
		Optional<Persona> oPersona = personaService.findById(documento);
		if(!oPersona.isPresent())
			return ResponseEntity.notFound().build();
		
		oPersona.get().setNombre(persona.getNombre());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(personaService.save(oPersona.get()));
	}
	
	@DeleteMapping("/{documento}")
	public ResponseEntity<?> eliminarPersonaPorId(@PathVariable(value = "documento") String documento){
		if(!personaService.findById(documento).isPresent())
			return ResponseEntity.notFound().build();
		
		personaService.deleteById(documento);
		return ResponseEntity.ok().build();
	}
}
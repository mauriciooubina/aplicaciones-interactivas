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

import edu.uade.api.tpo.modelo.Edificio;
import edu.uade.api.tpo.modelo.Unidad;
import edu.uade.api.tpo.services.implemented.EdificioServiceImpl;
import edu.uade.api.tpo.services.implemented.UnidadServiceImpl;
import edu.uade.api.tpo.views.EdificioView;
import edu.uade.api.tpo.views.UnidadView;

@RestController
@RequestMapping("/api/edificio")
public class EdificioController {
	@Autowired
	EdificioServiceImpl edificioService;
	
	@GetMapping()
	public List<EdificioView> obtenerEdificios(){
		List<EdificioView> edificios = new ArrayList<EdificioView>();
		Iterable<Edificio> iterable =this.edificioService.findAll();
		for(Edificio u : iterable) {
			edificios.add(u.toView());
		}
		return edificios;
	} 
	
	@PostMapping()
	public ResponseEntity<?> guardarEdificio(@RequestBody Edificio edificio){
		return ResponseEntity.status(HttpStatus.CREATED).body(edificioService.save(edificio));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerEdificioPorId(@PathVariable Integer id){
		Optional<Edificio> edificio = edificioService.findById(id);
		if(!edificio.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(edificio.get().toView());
	}
	/*
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEdificioPorId(@RequestBody Edificio edificio, @PathVariable(value = "id") Integer id){
		Optional<Edificio> oEdificio = edificioService.findById(id);
		if(!oUnidad.isPresent())
			return ResponseEntity.notFound().build();
		
		oEdificio.get().
		oEdificio.get().setDireccion(unidad.getDireccion());
		oEdificio.get().setEstado(unidad.getEstado());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(edificioService.save(oEdificio.get()));
	}*/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarEdificioPorId(@PathVariable(value = "id") Integer edificio){
		if(!edificioService.findById(edificio).isPresent())
			return ResponseEntity.notFound().build();
		
		edificioService.deleteById(edificio);
		return ResponseEntity.ok().build();
	}
}
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
import edu.uade.api.tpo.modelo.Unidad;
import edu.uade.api.tpo.services.implemented.UnidadServiceImpl;
import edu.uade.api.tpo.views.UnidadView;

@RestController
@RequestMapping("/api/unidad")
public class UnidadController {
	@Autowired
	UnidadServiceImpl unidadService;
	
	@GetMapping()
	public List<UnidadView> obtenerUnidades(){
		List<UnidadView> unidades = new ArrayList<UnidadView>();
		Iterable<Unidad> iterable =this.unidadService.findAll();
		for(Unidad u : iterable) {
			unidades.add(u.toView());
		}
		return unidades;
	} 
	
	@PostMapping()
	public ResponseEntity<?> guardarUnidad(@RequestBody Unidad unidad){
		return ResponseEntity.status(HttpStatus.CREATED).body(unidadService.save(unidad));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerUnidadPorId(@PathVariable Integer id){
		Optional<Unidad> unidad = unidadService.findById(id);
		if(!unidad.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(unidad.get().toView());
	}
	/*
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUnidadPorId(@RequestBody Unidad unidad, @PathVariable(value = "id") Integer id){
		Optional<Unidad> oUnidad = unidadService.findById(id);
		if(!oUnidad.isPresent())
			return ResponseEntity.notFound().build();
		
		oUnidad.get().
		oUnidad.get().setDireccion(unidad.getDireccion());
		oUnidad.get().setEstado(unidad.getEstado());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(unidadService.save(oUnidad.get()));
	}*/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarUnidadPorId(@PathVariable(value = "id") Integer unidad){
		if(!unidadService.findById(unidad).isPresent())
			return ResponseEntity.notFound().build();
		
		unidadService.deleteById(unidad);
		return ResponseEntity.ok().build();
	}
}
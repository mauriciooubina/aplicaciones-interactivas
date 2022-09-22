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
import edu.uade.api.tpo.modelo.Reclamo;
import edu.uade.api.tpo.modelo.Unidad;
import edu.uade.api.tpo.services.implemented.ReclamoServiceImpl;
import edu.uade.api.tpo.services.implemented.UnidadServiceImpl;
import edu.uade.api.tpo.views.ReclamoView;
import edu.uade.api.tpo.views.UnidadView;

@RestController
@RequestMapping("/api/reclamo")
public class ReclamoController {
	@Autowired
	ReclamoServiceImpl reclamoService;
	
	@GetMapping()
	public List<ReclamoView> obtenerReclamos(){
		List<ReclamoView> reclamos = new ArrayList<ReclamoView>();
		Iterable<Reclamo> iterable =this.reclamoService.findAll();
		for(Reclamo u : iterable) {
			reclamos.add(u.toView());
		}
		return reclamos;
	} 
	
	@PostMapping()
	public ResponseEntity<?> guardarReclamo(@RequestBody Reclamo reclamo){
		return ResponseEntity.status(HttpStatus.CREATED).body(reclamoService.save(reclamo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerReclamoPorId(@PathVariable Integer id){
		Optional<Reclamo> unidad = reclamoService.findById(id);
		if(!unidad.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(unidad.get().toView());
	}
	/*
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUnidadPorId(@RequestBody Reclamo reclamo, @PathVariable(value = "id") Integer id){
		Optional<Reclamo> oReclamo = reclamoService.findById(id);
		if(!oReclamo.isPresent())
			return ResponseEntity.notFound().build();
		
		oReclamo.get().
		oReclamo.get().setDireccion(unidad.getDireccion());
		oReclamo.get().setEstado(unidad.getEstado());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(reclamoService.save(oReclamo.get()));
	}*/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarReclamoPorId(@PathVariable(value = "id") Integer id){
		if(!reclamoService.findById(id).isPresent())
			return ResponseEntity.notFound().build();
		
		reclamoService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
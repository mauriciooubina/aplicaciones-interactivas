package edu.uade.api.tpo.controlador;

import edu.uade.api.tpo.exceptions.ReclamoException;
import edu.uade.api.tpo.modelo.EstadoMedidas;
import edu.uade.api.tpo.modelo.Reclamo;
import edu.uade.api.tpo.views.ReclamoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reclamo")
public class ReclamoController {
	private final Controlador controlador;

	@Autowired
	public ReclamoController(Controlador controlador){
		this.controlador=controlador;
	}
	@GetMapping("/edificio/{id}")
	public ResponseEntity<List<ReclamoView>> obtenerReclamosPorEdificio(@PathVariable("id") int codigoEdificio){
		return ResponseEntity.ok(controlador.reclamosPorEdificio(codigoEdificio));
	}

	@GetMapping("/unidad/{id}")
	public ResponseEntity<List<ReclamoView>> obtenerReclamosPorUnidad(@PathVariable("id") int codigoUnidad){
		return ResponseEntity.ok(controlador.reclamosPorUnidad(codigoUnidad));
	}

	@PostMapping()
	public ResponseEntity<?> guardarReclamo(@RequestBody Reclamo reclamo) throws ReclamoException {
		return ResponseEntity.status(HttpStatus.CREATED).body(controlador.agregarReclamo(reclamo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerReclamoPorId(@PathVariable Integer id) throws ReclamoException {
		return ResponseEntity.ok(controlador.reclamosPorNumero(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarReclamoPorId(@PathVariable(value = "id") Integer id){
		return ResponseEntity.accepted().body(eliminarReclamoPorId(id));
	}

	@GetMapping("/personas/DNI/{documento}")
	public ResponseEntity<?> reclamosPorDni(@PathVariable String documento){
		return ResponseEntity.ok(controlador.reclamosPorPersona("DNI".concat(documento)));
	}

	@GetMapping("/personas/CI/{documento}")
	public ResponseEntity<?> reclamosPorCi(@PathVariable String documento){
		return ResponseEntity.ok(controlador.reclamosPorPersona("CI ".concat(documento)));
	}

	@GetMapping("/personas/CPA/{documento}")
	public ResponseEntity<?> reclamosPorCpa(@PathVariable String documento){
		return ResponseEntity.ok(controlador.reclamosPorPersona("CPA".concat(documento)));
	}

	@PutMapping("/agregar-imagen/{reclamo}")
	public ResponseEntity<?> agregarImagen(@PathVariable("reclamo") int idReclamo, @RequestParam("url") String url, @RequestParam("tipo") String tipo) throws ReclamoException {
		controlador.agregarImagenAReclamo(idReclamo,url,tipo);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/cambiar-estado/{reclamo}")
	public ResponseEntity<?> cambiarEstado(@PathVariable("reclamo")int idReclamo, @RequestBody EstadoMedidas estadoMedidas) throws ReclamoException {
		controlador.cambiarEstado(idReclamo, estadoMedidas);
		return ResponseEntity.ok().build();
	}
}
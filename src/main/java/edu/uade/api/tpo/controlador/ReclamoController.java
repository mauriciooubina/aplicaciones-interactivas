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

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/reclamo")
public class ReclamoController {
	private final Controlador controlador;

	@Autowired
	public ReclamoController(Controlador controlador){
		this.controlador=controlador;
	}
	@GetMapping("/edificio")
	public ResponseEntity<List<ReclamoView>> obtenerReclamosPorEdificio(@PathParam("edificio") int codigoEdificio){
		return ResponseEntity.ok(controlador.reclamosPorEdificio(codigoEdificio));
	}

	@GetMapping("/unidad")
	public ResponseEntity<List<ReclamoView>> obtenerReclamosPorUnidad(@PathParam("unidad") int codigoUnidad){
		return ResponseEntity.ok(controlador.reclamosPorUnidad(codigoUnidad));
	}

	@PostMapping()
	public ResponseEntity<?> guardarReclamo(@RequestBody Reclamo reclamo){
		return ResponseEntity.status(HttpStatus.CREATED).body(controlador.agregarReclamo(reclamo));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerReclamoPorId(@PathVariable Integer id){
		return ResponseEntity.ok(controlador.reclamosPorNumero(id));
	}
	
	@PutMapping("/estado/{id}")
	public ResponseEntity<?> updateReclamoPorId(@RequestBody Reclamo reclamo, @PathVariable(value = "id") Integer id){
		return ResponseEntity.status(HttpStatus.CREATED).body(controlador.actualizarReclamo(reclamo,id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarReclamoPorId(@PathVariable(value = "id") Integer id){
		return ResponseEntity.accepted().body(eliminarReclamoPorId(id));
	}
}
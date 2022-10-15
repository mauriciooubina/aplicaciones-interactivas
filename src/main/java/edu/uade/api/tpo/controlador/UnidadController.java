package edu.uade.api.tpo.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import edu.uade.api.tpo.exceptions.UnidadException;
import edu.uade.api.tpo.views.UnidadSinEdificioView;
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

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/unidad")
public class UnidadController {
	private final Controlador controlador;

	@Autowired
	public UnidadController(Controlador controlador){
		this.controlador=controlador;
	}
	@GetMapping("/edificio")
	public ResponseEntity<List<UnidadSinEdificioView>> obtenerUnidadesSinEdificios(@PathParam("codigo") int codigo){
		return ResponseEntity.ok(controlador.getUnidadesPorEdificio(codigo));
	}

	@GetMapping()
	public ResponseEntity<List<UnidadView>> obtenerUnidadesConEdificios(){
		return ResponseEntity.ok(controlador.obtenerUnidadesConEdificios());
	}

	@PostMapping()
	public ResponseEntity<?> guardarUnidad(@RequestBody Unidad unidad){
		return ResponseEntity.status(HttpStatus.CREATED).body(controlador.agregarUnidad(unidad));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerUnidadPorId(@PathVariable Integer id) throws UnidadException {
		return ResponseEntity.ok(controlador.buscarUnidadPorCodigo(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUnidadPorId(@RequestBody Unidad unidad, @PathVariable(value = "id") Integer id){
		return ResponseEntity.status(HttpStatus.CREATED).body(controlador.actualizarUnidad(unidad,id));
	}
	
	/*@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarUnidadPorId(@PathVariable(value = "id") Integer unidad){
		if(!unidadService.findById(unidad).isPresent())
			return ResponseEntity.notFound().build();
		
		unidadService.deleteById(unidad);
		return ResponseEntity.ok().build();
	}*/
}
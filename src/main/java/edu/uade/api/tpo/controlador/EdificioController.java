package edu.uade.api.tpo.controlador;

import edu.uade.api.tpo.exceptions.EdificioException;
import edu.uade.api.tpo.exceptions.UnidadException;
import edu.uade.api.tpo.modelo.Edificio;
import edu.uade.api.tpo.views.EdificioConUnidadesView;
import edu.uade.api.tpo.views.EdificioView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/edificio")
@Slf4j
public class EdificioController {
	private final Controlador controlador;

	@Autowired
	public EdificioController(Controlador controlador){
		log.debug("Calling controller");
		this.controlador=controlador;
	}
	@GetMapping()
	public ResponseEntity<List<EdificioView>> obtenerEdificios(){
		return ResponseEntity.ok(controlador.getEdificios());
	}

	@GetMapping(path="/unidades")
	public ResponseEntity<List<EdificioConUnidadesView>> obtenerEdificiosConUnidades(){
		return ResponseEntity.ok(controlador.getEdificiosConUnidades());
	}

	@PostMapping()
	public ResponseEntity<?> guardarEdificio(@RequestBody Edificio edificio){
		return ResponseEntity.status(HttpStatus.CREATED).body(controlador.guardarEdificio(edificio));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerEdificioPorId(@PathVariable Integer id) throws EdificioException {
		return ResponseEntity.ok(controlador.buscarEdificio(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEdificio(@PathVariable int id, @RequestBody Edificio edificio){
		return ResponseEntity.status(HttpStatus.CREATED).body(controlador.actualizarEdificio(edificio,id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarEdificioPorId(@PathVariable(value = "id") Integer edificio){
		controlador.eliminarEdificio(edificio);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/obtener-habilitados/{id}")
	public ResponseEntity<?> obtenerHabilitadosPorEdificio(@PathVariable("id") int codigo) throws EdificioException {
		return ResponseEntity.ok(controlador.habilitadosPorEdificio(codigo));
	}

	@GetMapping("/obtener-habitantes/{id}")
	public ResponseEntity<?> obtenerHabitantesPorEdificio(@PathVariable("id") int codigo) throws EdificioException {
		return ResponseEntity.ok(controlador.habitantesPorEdificio(codigo));
	}

	@GetMapping("/obtener-duenios/{id}")
	public ResponseEntity<?> obtenerDueniosPorEdificio(@PathVariable("id") int codigo) throws EdificioException {
		return ResponseEntity.ok(controlador.dueniosPorEdificio(codigo));
	}

	@GetMapping("/unidades/{id}")
	public ResponseEntity<?> obtenerUnidadesPorEdificio(@PathVariable("id")int codigo){
		return ResponseEntity.ok(controlador.obtenerUnidadesPorEdificio(codigo));
	}
}
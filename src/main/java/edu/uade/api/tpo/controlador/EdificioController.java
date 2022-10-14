package edu.uade.api.tpo.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import edu.uade.api.tpo.exceptions.EdificioException;
import edu.uade.api.tpo.views.EdificioConUnidadesView;
import lombok.extern.slf4j.Slf4j;
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
}
package edu.uade.api.tpo.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.uade.api.tpo.modelo.Unidad;
import edu.uade.api.tpo.services.implemented.UnidadServiceImpl;
import edu.uade.api.tpo.views.UnidadView;

@RestController
@RequestMapping("/unidad")
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
	public UnidadView guardarUnidad(@RequestBody Unidad unidad){
		return this.unidadService.save(unidad).toView();
	}
	
	@GetMapping(path="/{id}")
	public UnidadView obtenerUnidadPorId(@PathVariable("id") Integer id){
		return this.unidadService.findById(id).get().toView();
	}
	
	@DeleteMapping(path="/{id}")
	public String eliminarUnidadPorId(@PathVariable("id") Integer id){
		try {
			this.unidadService.deleteById(id);
			return "Se elimino el usuario con id: " + id;
		}catch(Exception e){
			return "No se pudo eliminar el usuario con id: " + id;
		}
	}
}
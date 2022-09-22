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

import edu.uade.api.tpo.modelo.Imagen;
import edu.uade.api.tpo.modelo.Reclamo;
import edu.uade.api.tpo.modelo.Unidad;
import edu.uade.api.tpo.services.implemented.ImagenServiceImpl;
import edu.uade.api.tpo.services.implemented.UnidadServiceImpl;
import edu.uade.api.tpo.views.ImagenView;
import edu.uade.api.tpo.views.UnidadView;

@RestController
@RequestMapping("/api/imagen")
public class ImagenController {
	@Autowired
	ImagenServiceImpl imagenService;
	
	@GetMapping()
	public List<ImagenView> obtenerImagenes(){
		List<ImagenView> imagenes = new ArrayList<ImagenView>();
		Iterable<Imagen> iterable =this.imagenService.findAll();
		for(Imagen u : iterable) {
			imagenes.add(u.toView());
		}
		return imagenes;
	} 
	
	@PostMapping()
	public ResponseEntity<?> guardarImagen(@RequestBody Imagen imagen){
		return ResponseEntity.status(HttpStatus.CREATED).body(imagenService.save(imagen));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerImagenPorId(@PathVariable Integer id){
		Optional<Imagen> imagen = imagenService.findById(id);
		if(!imagen.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(imagen.get().toView());
	}
	/*
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUnidadPorId(@RequestBody Imagen imagen, @PathVariable(value = "id") Integer id){
		Optional<Imagen> oImagen = unidadService.findById(id);
		if(!oUnidad.isPresent())
			return ResponseEntity.notFound().build();
		
		oImagen.get().
		oImagen.get().setDireccion(imagen.getDireccion());
		oImagen.get().setEstado(imagen.getEstado());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(imagenService.save(oImagen.get()));
	}*/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarImagenPorId(@PathVariable(value = "id") Integer imagen){
		if(!imagenService.findById(imagen).isPresent())
			return ResponseEntity.notFound().build();
		
		imagenService.deleteById(imagen);
		return ResponseEntity.ok().build();
	}
}
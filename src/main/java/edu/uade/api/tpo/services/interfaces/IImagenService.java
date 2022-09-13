package edu.uade.api.tpo.services.interfaces;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;

import edu.uade.api.tpo.modelo.Imagen;

public interface IImagenService {
	
	Iterable<Imagen> findAll();
	
	Page<Imagen> findAll(Pageable pageable);
	
	Optional<Imagen> findById(Integer id);
	
	Imagen save(Imagen imagen);
	
	void deleteById(Integer id);

}

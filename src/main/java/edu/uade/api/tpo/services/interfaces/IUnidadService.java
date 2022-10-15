package edu.uade.api.tpo.services.interfaces;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import edu.uade.api.tpo.modelo.Unidad;

public interface IUnidadService {
	
	List<Unidad> findAll();
	
	Page<Unidad> findAll(Pageable pageable);
	
	Optional<Unidad> findById(Integer id);
	
	Unidad save(Unidad unidad);

	Unidad update(Unidad unidad, int id);
	
	void deleteById(Integer id);

}

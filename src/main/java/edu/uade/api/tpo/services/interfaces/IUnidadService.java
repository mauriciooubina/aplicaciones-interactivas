package edu.uade.api.tpo.services.interfaces;

import edu.uade.api.tpo.modelo.Unidad;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface IUnidadService {
	
	List<Unidad> findAll();
	
	Page<Unidad> findAll(Pageable pageable);
	
	Optional<Unidad> findById(Integer id);
	
	Unidad save(Unidad unidad);

	Unidad update(Unidad unidad, int id);
	
	void deleteById(Integer id);

}

package edu.uade.api.tpo.services.interfaces;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;

import edu.uade.api.tpo.modelo.Reclamo;

public interface IReclamoService {
	
	Iterable<Reclamo> findAll();
	
	Page<Reclamo> findAll(Pageable pageable);
	
	Optional<Reclamo> findById(Integer id);
	
	Reclamo save(Reclamo reclamo);
	
	void deleteById(Integer id);

}

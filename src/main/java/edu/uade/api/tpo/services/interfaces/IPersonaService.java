package edu.uade.api.tpo.services.interfaces;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;

import edu.uade.api.tpo.modelo.Persona;

public interface IPersonaService {
	
	Iterable<Persona> findAll();
	
	Page<Persona> findAll(Pageable pageable);
	
	Optional<Persona> findById(String documento);
	
	Persona save(Persona persona);
	
	void deleteById(String documento);

}

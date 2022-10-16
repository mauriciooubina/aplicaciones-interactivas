package edu.uade.api.tpo.services.interfaces;

import edu.uade.api.tpo.modelo.Persona;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface IPersonaService {
	
	List<Persona> findAll();
	
	Page<Persona> findAll(Pageable pageable);
	
	Optional<Persona> findById(String documento);
	
	Persona save(Persona persona);

	Persona update(Persona persona, String documento);
	
	void deleteById(String documento);

}

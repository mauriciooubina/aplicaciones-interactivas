package edu.uade.api.tpo.services.interfaces;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;

import edu.uade.api.tpo.modelo.Edificio;

public interface IEdificioService {
	
	Iterable<Edificio> findAll();
	
	Page<Edificio> findAll(Pageable pageable);
	
	Optional<Edificio> findById(Integer id);
	
	Edificio save(Edificio edificio);
	
	void deleteById(Integer id);
	
	

}

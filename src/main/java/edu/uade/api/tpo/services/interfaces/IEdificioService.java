package edu.uade.api.tpo.services.interfaces;

import edu.uade.api.tpo.modelo.Edificio;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface IEdificioService {
	
	List<Edificio> findAll();
	
	Page<Edificio> findAll(Pageable pageable);
	
	Optional<Edificio> findById(Integer id);
	
	Edificio save(Edificio edificio);
	
	void deleteById(Integer id);


	Edificio update(Edificio edificio, int codigo);
}

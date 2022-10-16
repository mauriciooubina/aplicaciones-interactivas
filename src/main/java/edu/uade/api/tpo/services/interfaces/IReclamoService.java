package edu.uade.api.tpo.services.interfaces;

import edu.uade.api.tpo.modelo.Reclamo;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface IReclamoService {
	
	List<Reclamo> findAll();
	
	Page<Reclamo> findAll(Pageable pageable);
	
	Optional<Reclamo> findById(Integer id);
	
	Reclamo save(Reclamo reclamo);

	Reclamo update(Reclamo reclamo, int id);
	
	void deleteById(Integer id);

}

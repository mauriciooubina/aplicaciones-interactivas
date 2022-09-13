package edu.uade.api.tpo.services.implemented;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.uade.api.tpo.modelo.Reclamo;
import edu.uade.api.tpo.repositorios.ReclamoRepositorio;
import edu.uade.api.tpo.services.interfaces.IReclamoService;

@Service
public class ReclamoServiceImpl implements IReclamoService {

	@Autowired
	private ReclamoRepositorio repositorio;
	@Override
	public Iterable<Reclamo> findAll() {
		// TODO Auto-generated method stub
		return repositorio.findAll();
	}

	@Override
	public Page<Reclamo> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return (Page<Reclamo>) repositorio.findAll((Sort) pageable);
	}

	@Override
	public Optional<Reclamo> findById(Integer id) {
		// TODO Auto-generated method stub
		return repositorio.findById(id);
	}

	@Override
	public Reclamo save(Reclamo reclamo) {
		// TODO Auto-generated method stub
		return repositorio.save(reclamo);
	}

	@Override
	public void deleteById(Integer id) {
		repositorio.deleteById(id);

	}

}

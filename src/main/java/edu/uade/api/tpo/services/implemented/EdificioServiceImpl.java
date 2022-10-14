package edu.uade.api.tpo.services.implemented;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.uade.api.tpo.modelo.Edificio;
import edu.uade.api.tpo.repositorios.EdificioRepositorio;
import edu.uade.api.tpo.services.interfaces.IEdificioService;

@Service
public class EdificioServiceImpl implements IEdificioService {

	

	private final EdificioRepositorio repositorio;

	@Autowired
	public EdificioServiceImpl(EdificioRepositorio repositorio){
		this.repositorio=repositorio;
	}
	@Override
	public List<Edificio> findAll() {
		return repositorio.findAll();
	}

	
	@Override
	public Page<Edificio> findAll(Pageable pageable) {
		return (Page<Edificio>) repositorio.findAll((Sort) pageable);
	}

	@Override
	public Optional<Edificio> findById(Integer id) {
		return repositorio.findById(id);
	}

	@Override
	public Edificio save(Edificio edificio) {
		return repositorio.save(edificio);
	}

	@Override
	public void deleteById(Integer id) {
		repositorio.deleteById(id);

	}

}

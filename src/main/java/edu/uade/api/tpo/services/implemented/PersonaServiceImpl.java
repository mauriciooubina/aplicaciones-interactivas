package edu.uade.api.tpo.services.implemented;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.uade.api.tpo.modelo.Persona;
import edu.uade.api.tpo.repositorios.PersonaRepositorio;
import edu.uade.api.tpo.services.interfaces.IPersonaService;

@Service
public class PersonaServiceImpl implements IPersonaService {

	
	@Autowired
	private PersonaRepositorio repositorio;
	@Override
	public List<Persona> findAll() {
		// TODO Auto-generated method stub
		return repositorio.findAll();
	}

	@Override
	public Page<Persona> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return (Page<Persona>) repositorio.findAll((Sort) pageable);
	}

	@Override
	public Optional<Persona> findById(String documento) {
		// TODO Auto-generated method stub
		return repositorio.findById(documento);
	}

	@Override
	public Persona save(Persona persona) {
		// TODO Auto-generated method stub
		return repositorio.save(persona);
	}

	@Override
	public Persona update(Persona personaActualizada, String documento) {
		Persona personaPorActualizar= repositorio.getReferenceById(documento);
		personaPorActualizar.setNombre(personaActualizada.getNombre());

		return repositorio.save(personaPorActualizar);
	}

	@Override
	public void deleteById(String documento) {
		repositorio.deleteById(documento);

	}

}

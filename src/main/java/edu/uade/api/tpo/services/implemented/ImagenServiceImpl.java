package edu.uade.api.tpo.services.implemented;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.uade.api.tpo.modelo.Imagen;
import edu.uade.api.tpo.repositorios.ImagenRepositorio;
import edu.uade.api.tpo.services.interfaces.IImagenService;

@Service
public class ImagenServiceImpl implements IImagenService {

	private final ImagenRepositorio repositorio;

	@Autowired
	public ImagenServiceImpl(ImagenRepositorio repositorio){
		this.repositorio=repositorio;
	}

	@Override
	public Iterable<Imagen> findAll() {
		// TODO Auto-generated method stub
		return repositorio.findAll();
	}

	@Override
	public Page<Imagen> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return (Page<Imagen>) repositorio.findAll((Sort) pageable);
	}

	@Override
	public Optional<Imagen> findById(Integer id) {
		// TODO Auto-generated method stub
		return repositorio.findById(id);
	}

	@Override
	public Imagen save(Imagen imagen) {
		// TODO Auto-generated method stub
		return repositorio.save(imagen);
	}

	@Override
	public void deleteById(Integer id) {
		repositorio.deleteById(id);

	}

	public void save(Imagen imagen, Integer idReclamo){
		repositorio.save(imagen.getDireccion(), imagen.getTipo(), idReclamo);
	}

}

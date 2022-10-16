package edu.uade.api.tpo.repositorios;

import edu.uade.api.tpo.modelo.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepositorio extends JpaRepository<Unidad,Integer>{

}

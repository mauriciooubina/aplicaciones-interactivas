package edu.uade.api.tpo.repositorios;

import edu.uade.api.tpo.modelo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona,String>{

}

package edu.uade.api.tpo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uade.api.tpo.modelo.Persona;

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona,String>{

}

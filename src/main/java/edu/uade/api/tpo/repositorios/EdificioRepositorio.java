package edu.uade.api.tpo.repositorios;

import edu.uade.api.tpo.modelo.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdificioRepositorio extends JpaRepository<Edificio,Integer>{

}

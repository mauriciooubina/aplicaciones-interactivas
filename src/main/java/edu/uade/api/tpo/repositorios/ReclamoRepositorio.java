package edu.uade.api.tpo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uade.api.tpo.modelo.Reclamo;

@Repository
public interface ReclamoRepositorio extends JpaRepository<Reclamo,Integer>{

}

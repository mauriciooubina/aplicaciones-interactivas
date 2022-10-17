package edu.uade.api.tpo.repositorios;

import edu.uade.api.tpo.modelo.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamoRepositorio extends JpaRepository<Reclamo,Integer>{

}

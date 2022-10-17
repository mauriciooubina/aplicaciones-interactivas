package edu.uade.api.tpo.repositorios;

import edu.uade.api.tpo.modelo.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen,Integer>{

  @Modifying
  @Transactional
  @Query(value= "insert into imagenes (path, tipo, idReclamo) values (:direccion,:tipo,:idReclamo)",nativeQuery = true)
  void save(@Param("direccion") String direccion, @Param("tipo")String tipo, @Param("idReclamo")Integer idReclamo);
}

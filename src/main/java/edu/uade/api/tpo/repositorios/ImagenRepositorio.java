package edu.uade.api.tpo.repositorios;

import javax.persistence.ColumnResult;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.uade.api.tpo.modelo.Imagen;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen,Integer>{

  @Modifying
  @Transactional
  @Query(value= "insert into imagenes (path, tipo, idReclamo) values (:direccion,:tipo,:idReclamo)",nativeQuery = true)
  void save(@Param("direccion") String direccion, @Param("tipo")String tipo, @Param("idReclamo")Integer idReclamo);
}

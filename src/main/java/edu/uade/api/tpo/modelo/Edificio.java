package edu.uade.api.tpo.modelo;

import edu.uade.api.tpo.views.EdificioConUnidadesView;
import edu.uade.api.tpo.views.EdificioView;
import edu.uade.api.tpo.views.UnidadSinEdificioView;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name= "edificios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Edificio{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int codigo;
	private String nombre;
	private String direccion;
	@OneToMany(fetch= FetchType.EAGER)
	@JoinColumn(name="codigoEdificio")
	private List<Unidad> unidades;


	public Edificio(String nombre, String direccion, List<Unidad> unidades) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.unidades = unidades;
	}

	public void agregarUnidad(Unidad unidad) {
		unidades.add(unidad);
	}
	
	public Set<Persona> habilitados(){
		Set<Persona> habilitados = new HashSet<Persona>();
		for(Unidad unidad : unidades) {
			List<Persona> duenios = unidad.getDuenios();
			for(Persona p : duenios)
				habilitados.add(p);
			List<Persona> inquilinos = unidad.getInquilinos();
			for(Persona p : inquilinos)
				habilitados.add(p);
		}
		return habilitados;
	}

	public Set<Persona> duenios() {
		Set<Persona> resultado = new HashSet<Persona>();
		for(Unidad unidad : unidades) {
			List<Persona> duenios = unidad.getDuenios();
			for(Persona p : duenios)
				resultado.add(p);
		}
		return resultado;
	}

	public Set<Persona> habitantes() {
		Set<Persona> resultado = new HashSet<Persona>();
		for(Unidad unidad : unidades) {
			if(unidad.estaHabitado()) {
				List<Persona> inquilinos = unidad.getInquilinos();
				if(inquilinos.size() > 0) 
					for(Persona p : inquilinos)
						resultado.add(p);
				else {
					List<Persona> duenios = unidad.getDuenios();
					for(Persona p : duenios)
						resultado.add(p);
				}
			}
		}
		return resultado;
	}


	public EdificioView toView() {
		return new EdificioView(codigo, nombre, direccion);
	}

	public EdificioConUnidadesView toViewConUnidades() {
		List<UnidadSinEdificioView> unidadesSinEdificio= unidades.stream().map(Unidad::toViewSinEdificios).toList();
		return new EdificioConUnidadesView(codigo, nombre, direccion,unidadesSinEdificio);
	}
}

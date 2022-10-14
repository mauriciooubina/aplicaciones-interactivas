package edu.uade.api.tpo.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.uade.api.tpo.views.EdificioConUnidadesView;
import edu.uade.api.tpo.views.EdificioView;
import edu.uade.api.tpo.views.UnidadSinEdificioView;
import lombok.ToString;


@Entity
@Table(name= "edificios")
public class Edificio{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int codigo;
	private String nombre;
	private String direccion;
	@OneToMany(fetch= FetchType.EAGER)
	@JoinColumn(name="codigoEdificio")
	private List<Unidad> unidades;
	
	public Edificio() {}
	
	public Edificio(int codigo, String nombre, String direccion) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.direccion = direccion;
		unidades = new ArrayList<Unidad>();
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

	public int getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public Set<Persona> duenios() {
		Set<Persona> resultado = new HashSet<Persona>();
		for(Unidad unidad : unidades) {
			List<Persona> duenios = unidad.getDuenios();
			for(Persona p : duenios)
				duenios.add(p);
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

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	public EdificioView toView() {
		return new EdificioView(codigo, nombre, direccion);
	}

	public EdificioConUnidadesView toViewConUnidades() {
		List<UnidadSinEdificioView> unidadesSinEdificio= unidades.stream().map(Unidad::toViewSinEdificios).toList();
		return new EdificioConUnidadesView(codigo, nombre, direccion,unidadesSinEdificio);
	}
}

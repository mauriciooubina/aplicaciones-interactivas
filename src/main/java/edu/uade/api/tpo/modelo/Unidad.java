package edu.uade.api.tpo.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.uade.api.tpo.exceptions.UnidadException;
import edu.uade.api.tpo.views.EdificioView;
import edu.uade.api.tpo.views.UnidadView;
import lombok.ToString;

@Entity
@Table(name="unidades")
public class Unidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="identificador")
	private int id;
	private String piso;
	private String numero;
	private boolean habitado;
	@ManyToOne
	@JoinColumn(name="codigoEdificio")
	private Edificio edificio;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="duenios", joinColumns= {
			@JoinColumn(name="identificador"),	
	}, inverseJoinColumns= {
			@JoinColumn(name="documento")
	})
	private List<Persona> duenios;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name="inquilinos", joinColumns= {
			@JoinColumn(name="identificador"),	
	}, inverseJoinColumns= {
			@JoinColumn(name="documento")
	})
	private List<Persona> inquilinos;
	
	
	public Unidad() {}
	
	public Unidad(int id, String piso, String numero, Edificio edificio) {
		this.id = id;
		this.piso = piso;
		this.numero = numero;
		this.habitado = false;
		this.edificio = edificio;
		this.duenios = new ArrayList<Persona>();
		this.inquilinos = new ArrayList<Persona>();
	}

	public void transferir(Persona nuevoDuenio) {
		duenios = new ArrayList<Persona>();
		duenios.add(nuevoDuenio);
	}
	
	public void agregarDuenio(Persona duenio) {
		duenios.add(duenio);
	}
	
	public void alquilar(Persona inquilino) throws UnidadException {
		if(!this.habitado) {
			this.habitado = true;
			inquilinos = new ArrayList<Persona>();
			inquilinos.add(inquilino);
		}
		else
			throw new UnidadException("La unidad esta ocupada");
	}

	public void agregarInquilino(Persona inquilino) {
		inquilinos.add(inquilino);
	}
	
	public boolean estaHabitado() {
		return habitado;
	}
	
	public void liberar() {
		this.inquilinos = new ArrayList<Persona>();
		this.habitado = false;
	}
	
	public void habitar() throws UnidadException {
		if(this.habitado)
			throw new UnidadException("La unidad ya esta habitada");
		else
			this.habitado = true;
	}
	
	public int getId() {
		return id;
	}

	public String getPiso() {
		return piso;
	}
	
	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public void setHabitado(boolean habitado) {
		this.habitado = habitado;
	}

	
	public Edificio getEdificio() {
		return edificio;
	}

	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}
	
	public List<Persona> getDuenios() {
		return duenios;
	}

	public List<Persona> getInquilinos() {
		return inquilinos;
	}

	public UnidadView toView() {
		EdificioView auxEdificio = edificio.toView();
		return new UnidadView(id, piso, numero, habitado, auxEdificio);
	}

	public String toString(){
		return "Unidad= {numero= " + this.numero +", piso= " + this.piso + ", edificio= " + this.edificio.getDireccion() +"}";
	}
}

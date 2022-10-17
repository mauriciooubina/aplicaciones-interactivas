package edu.uade.api.tpo.modelo;

import edu.uade.api.tpo.exceptions.UnidadException;
import edu.uade.api.tpo.views.EdificioView;
import edu.uade.api.tpo.views.UnidadSinEdificioView;
import edu.uade.api.tpo.views.UnidadView;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Entity
@Table(name="unidades")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

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
	@Cascade(CascadeType.LOCK)
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


	public Unidad(String piso, String numero, Edificio edificio) {
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

	public boolean esDuenio(Persona persona){
		AtomicBoolean esDuenio= new AtomicBoolean(false);
		duenios.forEach(duenio -> {
			if(duenio.getDocumento().equals(persona.getDocumento())){
				esDuenio.set(true);
			}
		});
		return esDuenio.get();
	}

	public boolean esInquilino(Persona persona){
		AtomicBoolean esInquilino= new AtomicBoolean(false);
		inquilinos.forEach(inquilino -> {
			if(inquilino.getDocumento().equals(persona.getDocumento())){
				esInquilino.set(true);
			}
		});
		return esInquilino.get();
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
	public UnidadView toView() {
		EdificioView auxEdificio = edificio.toView();
		return new UnidadView(id, piso, numero, habitado, auxEdificio);
	}

	public UnidadSinEdificioView toViewSinEdificios(){
		return new UnidadSinEdificioView(id,piso,numero,habitado);
	}
}

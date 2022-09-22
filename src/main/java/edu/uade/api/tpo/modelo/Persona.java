package edu.uade.api.tpo.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.uade.api.tpo.views.PersonaView;

@Entity
@Table(name="personas")
public class Persona {

	@Id
	private String documento;
	private String nombre;
	
	public Persona() {}
	
	public Persona(String documento, String nombre) {
		this.documento = documento;
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}
	
	public void setDocumento(String documento) {
		this.documento = documento;
	}


	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public PersonaView toView() {
		return new PersonaView(documento, nombre);
	}

	public void save() {
		
	}

	public void delete() {
		
	}	
}

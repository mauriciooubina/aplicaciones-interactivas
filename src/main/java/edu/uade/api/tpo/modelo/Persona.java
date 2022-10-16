package edu.uade.api.tpo.modelo;

import edu.uade.api.tpo.views.PersonaSinRolView;
import edu.uade.api.tpo.views.PersonaView;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="personas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Persona {

	@Id
	private String documento;
	private String nombre;

	private String email;

	@Column(name="pass")
	private String password;

	@Enumerated(EnumType.ORDINAL)
	private Rol rol;
	
	public Persona(String nombre, String email, String password,Rol rol) {
		this.documento = documento;
		this.nombre = nombre;
		this.email=email;
		this.password=password;
		this.rol=rol;
	}

	public PersonaView toView() {
		return new PersonaView(documento, nombre,email, rol);
	}

	public PersonaSinRolView toViewSinRol() {
		return new PersonaSinRolView(documento,nombre,email);
	}
}

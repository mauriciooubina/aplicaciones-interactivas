package edu.uade.api.tpo.views;

import edu.uade.api.tpo.modelo.Rol;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonaView {
	
	private String documento;
	private String nombre;
	private String email;
	private Rol rol;

}

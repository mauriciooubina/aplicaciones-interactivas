package edu.uade.api.tpo.modelo;

import edu.uade.api.tpo.views.ImagenView;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="imagenes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Imagen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int numero;
	@Column(name = "path")
	private String direccion;
	private String tipo;

	public Imagen(String direccion, String tipo) {
		this.direccion = direccion;
		this.tipo = tipo;
	}

	public ImagenView toView() {
		return new ImagenView(numero, direccion ,tipo);
	}

}

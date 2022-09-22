package edu.uade.api.tpo.modelo;

import edu.uade.api.tpo.services.implemented.ImagenServiceImpl;
import edu.uade.api.tpo.views.EdificioView;
import edu.uade.api.tpo.views.ImagenView;
import edu.uade.api.tpo.views.UnidadView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="imagenes")
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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void save(int numeroReclamo) {
		/*ImagenServiceImpl service= new ImagenServiceImpl();
		service.save(this, numeroReclamo);*/
	}
	
	public ImagenView toView() {
		return new ImagenView(numero, direccion ,tipo);
	}

}

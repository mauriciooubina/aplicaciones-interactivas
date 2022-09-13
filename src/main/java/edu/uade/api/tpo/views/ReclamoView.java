package edu.uade.api.tpo.views;

import java.util.List;

import edu.uade.api.tpo.modelo.Edificio;
import edu.uade.api.tpo.modelo.Imagen;
import edu.uade.api.tpo.modelo.Persona;
import edu.uade.api.tpo.modelo.Unidad;

public class ReclamoView {

	private int numero;
	private PersonaView usuario;
	private EdificioView edificio;
	private String ubicacion;
	private String descripcion;
	private UnidadView unidad;
	private Estado estado;
	private List<ImagenView> imagenes;
}

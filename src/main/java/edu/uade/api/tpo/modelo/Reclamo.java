package edu.uade.api.tpo.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.uade.api.tpo.views.EdificioView;
import edu.uade.api.tpo.views.Estado;
import edu.uade.api.tpo.views.ImagenView;
import edu.uade.api.tpo.views.PersonaView;
import edu.uade.api.tpo.views.ReclamoView;
import edu.uade.api.tpo.views.UnidadView;

import javax.persistence.Transient;

@Entity
@Table(name="reclamos")
public class Reclamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idReclamo")
	private int numero;
	
	@ManyToOne
	@JoinColumn(name="documento")
	private Persona usuario;
	
	@ManyToOne
	@JoinColumn(name="codigo")
	private Edificio edificio;
	private String ubicacion;
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name="identificador")
	private Unidad unidad;

	@Transient
	private Estado estado;
	
	@OneToMany
	@JoinColumn(name="idReclamo")
	private List<Imagen> imagenes;

	public Reclamo(){}
	public Reclamo(Persona usuario, Edificio edificio, String ubicacion, String descripcion, Unidad unidad) {
		this.usuario = usuario;
		this.edificio = edificio;
		this.ubicacion = ubicacion;
		this.descripcion = descripcion;
		this.unidad = unidad;
		this.estado = Estado.nuevo;
		imagenes = new ArrayList<Imagen>();
	}

	public void agregarImagen(String direccion, String tipo) {
		Imagen imagen = new Imagen(direccion, tipo);
		imagenes.add(imagen);
		imagen.save(numero);
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Persona getUsuario() {
		return usuario;
	}

	public Edificio getEdificio() {
		return edificio;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public Estado getEstado() {
		return estado;
	}
	
	public List<Imagen> getImagenes(){
		return this.imagenes;
	}
	
	public void setUsuario(Persona usuario) {
		this.usuario = usuario;
	}
	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}
	
	public void cambiarEstado(Estado estado) {
		this.estado = estado;
	}

	public void save() {
		
	}
	
	public void update() {
		
	}
	
	public ReclamoView toView() {
		PersonaView auxPersona = usuario.toView();
		EdificioView auxEdificio = edificio.toView();
		UnidadView auxUnidad = unidad.toView();
		List<ImagenView> auxImagen = new ArrayList<ImagenView>();
		for(Imagen img : imagenes) {
			auxImagen.add(img.toView());
		}
		return new ReclamoView(numero, auxPersona, auxEdificio, ubicacion, descripcion, auxUnidad, estado, auxImagen);
	}
}

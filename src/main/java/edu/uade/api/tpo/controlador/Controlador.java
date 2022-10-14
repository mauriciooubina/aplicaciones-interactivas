package edu.uade.api.tpo.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import edu.uade.api.tpo.exceptions.EdificioException;
import edu.uade.api.tpo.exceptions.PersonaException;
import edu.uade.api.tpo.exceptions.ReclamoException;
import edu.uade.api.tpo.exceptions.UnidadException;
import edu.uade.api.tpo.modelo.Edificio;
import edu.uade.api.tpo.modelo.Persona;
import edu.uade.api.tpo.modelo.Reclamo;
import edu.uade.api.tpo.modelo.Unidad;
import edu.uade.api.tpo.services.implemented.EdificioServiceImpl;
import edu.uade.api.tpo.services.interfaces.IEdificioService;
import edu.uade.api.tpo.views.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@Slf4j
public class Controlador {

	private final IEdificioService edificioService;

	@Autowired
	private Controlador(IEdificioService edificioService) {
		this.edificioService=edificioService;
	}

	
	public List<EdificioView> getEdificios(){
		log.debug("Called getEdificios from Controller");
		return edificioService.findAll().stream().map(Edificio::toView).toList();
	}

	public List<EdificioConUnidadesView> getEdificiosConUnidades(){
		log.debug("Called getEdificiosConUnidades from Controller");
		return edificioService.findAll().stream().map(Edificio::toViewConUnidades).toList();
	}

	public Edificio guardarEdificio(Edificio edificio){
		return edificioService.save(edificio);
	}

	public Edificio actualizarEdificio(Edificio edificio, int codigo){
		Optional<Edificio> edificioBuscado= edificioService.findById(codigo);
		if(edificioBuscado.isPresent()){
			Edificio edificioPorActualizar= edificioBuscado.get();
			edificioPorActualizar.setDireccion(edificio.getDireccion());
			edificioPorActualizar.setNombre(edificio.getNombre());
			edificioPorActualizar.setUnidades(edificio.getUnidades());
			log.info(String.valueOf(edificioPorActualizar));
		}
		return edificioService.save(edificio);
	}

	public void eliminarEdificio(int codigo){
		edificioService.deleteById(codigo);
	}
	
	/*public List<UnidadView> getUnidadesPorEdificio(int codigo) throws EdificioException{
		List<UnidadView> resultado = new ArrayList<UnidadView>();
		Edificio edificio = buscarEdificio(codigo);
		List<Unidad> unidades = edificio.getUnidades();
		for(Unidad unidad : unidades)
			resultado.add(unidad.toView());
		return resultado;
	}
	
	public List<PersonaView> habilitadosPorEdificio(int codigo) throws EdificioException{
		List<PersonaView> resultado = new ArrayList<PersonaView>();
		Edificio edificio = buscarEdificio(codigo);
		Set<Persona> habilitados = edificio.habilitados();
		for(Persona persona : habilitados)
			resultado.add(persona.toView());
		return resultado;
	}*/

	/*public List<PersonaView> dueniosPorEdificio(int codigo) throws EdificioException{
		List<PersonaView> resultado = new ArrayList<PersonaView>();
		Edificio edificio = buscarEdificio(codigo);
		Set<Persona> duenios = edificio.duenios();
		for(Persona persona : duenios)
			resultado.add(persona.toView());
		return resultado;
	}

	public List<PersonaView> habitantesPorEdificio(int codigo) throws EdificioException{
		List<PersonaView> resultado = new ArrayList<PersonaView>();
		Edificio edificio = buscarEdificio(codigo);
		Set<Persona> habitantes = edificio.duenios();
		for(Persona persona : habitantes)
			resultado.add(persona.toView());
		return resultado;
	}*/

	public List<PersonaView> dueniosPorUnidad(int codigo, String piso, String numero) throws UnidadException{
		List<PersonaView> resultado = new ArrayList<PersonaView>();
		Unidad unidad = buscarUnidad(codigo, piso, numero);
		List<Persona> duenios = unidad.getDuenios();
		for(Persona persona : duenios)
			resultado.add(persona.toView());
		return resultado;
	}

	public List<PersonaView> inquilinosPorUnidad(int codigo, String piso, String numero) throws UnidadException{
		List<PersonaView> resultado = new ArrayList<PersonaView>();
		Unidad unidad = buscarUnidad(codigo, piso, numero);
		List<Persona> inquilinos = unidad.getInquilinos();
		for(Persona persona : inquilinos)
			resultado.add(persona.toView());
		return resultado;
	}
	
	public void transferirUnidad(int codigo, String piso, String numero, String documento) throws UnidadException, PersonaException {
		Unidad unidad = buscarUnidad(codigo, piso, numero);
		Persona persona = buscarPersona(documento);
		unidad.transferir(persona);
	}

	public void agregarDuenioUnidad(int codigo, String piso, String numero, String documento) throws UnidadException, PersonaException {
		Unidad unidad = buscarUnidad(codigo, piso, numero);
		Persona persona = buscarPersona(documento);
		unidad.agregarDuenio(persona);
	}

	public void alquilarUnidad(int codigo, String piso, String numero, String documento) throws UnidadException, PersonaException{
		Unidad unidad = buscarUnidad(codigo, piso, numero);
		Persona persona = buscarPersona(documento);
		unidad.alquilar(persona);
	}

	public void agregarInquilinoUnidad(int codigo, String piso, String numero, String documento) throws UnidadException, PersonaException{
		Unidad unidad = buscarUnidad(codigo, piso, numero);
		Persona persona = buscarPersona(documento);
		unidad.agregarInquilino(persona);
	}

	public void liberarUnidad(int codigo, String piso, String numero) throws UnidadException {
		Unidad unidad = buscarUnidad(codigo, piso, numero);
		unidad.liberar();
	}
	
	public void habitarUnidad(int codigo, String piso, String numero) throws UnidadException {
		Unidad unidad = buscarUnidad(codigo, piso, numero);
		unidad.habitar();;
	}
	
	public void agregarPersona(String documento, String nombre) {
		Persona persona = new Persona(documento, nombre);
		persona.save();
	}
	
	public void eliminarPersona(String documento) throws PersonaException {
		Persona persona = buscarPersona(documento);
		persona.delete();
	}
	
	public List<ReclamoView> reclamosPorEdificio(int codigo){
		List<ReclamoView> resultado = new ArrayList<ReclamoView>();
		return resultado;
	}
	
	public List<ReclamoView> reclamosPorUnidad(int codigo, String piso, String numero) {
		List<ReclamoView> resultado = new ArrayList<ReclamoView>();
		return resultado;
	}
	
	public ReclamoView reclamosPorNumero(int numero) {
		ReclamoView resultado = null;
		return resultado;
	}
	
	public List<ReclamoView> reclamosPorPersona(String documento) {
		List<ReclamoView> resultado = new ArrayList<ReclamoView>();
		return resultado;
	}
 
	/**public int agregarReclamo(int codigo, String piso, String numero, String documento, String ubicacion, String descripcion) throws EdificioException, UnidadException, PersonaException {
		Edificio edificio = buscarEdificio(codigo);
		Unidad unidad = buscarUnidad(codigo, piso, numero);
		Persona persona = buscarPersona(documento);
		Reclamo reclamo = new Reclamo(persona, edificio, ubicacion, descripcion, unidad);
		reclamo.save();
		return reclamo.getNumero();
	}*/
	
	public void agregarImagenAReclamo(int numero, String direccion, String tipo) throws ReclamoException {
		Reclamo reclamo = buscarReclamo(numero);
		reclamo.agregarImagen(direccion, tipo);
	}
	
	public void cambiarEstado(int numero, Estado estado) throws ReclamoException {
		Reclamo reclamo = buscarReclamo(numero);
		reclamo.cambiarEstado(estado);
		reclamo.update();
	}
	
	public EdificioView buscarEdificio(int codigo) throws EdificioException {
		return edificioService.findById(codigo).orElse(null).toView();
	}

	private Unidad buscarUnidad(int codigo, String piso, String numero) throws UnidadException{
		return null;
	}	
	
	private Persona buscarPersona(String documento) throws PersonaException {
		return null;
	}
	
	private Reclamo buscarReclamo(int numero) throws ReclamoException {
		return null;
	}
}

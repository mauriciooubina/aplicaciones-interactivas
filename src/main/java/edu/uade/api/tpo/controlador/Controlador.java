package edu.uade.api.tpo.controlador;

import java.util.*;

import edu.uade.api.tpo.exceptions.EdificioException;
import edu.uade.api.tpo.exceptions.PersonaException;
import edu.uade.api.tpo.exceptions.ReclamoException;
import edu.uade.api.tpo.exceptions.UnidadException;
import edu.uade.api.tpo.modelo.Edificio;
import edu.uade.api.tpo.modelo.Persona;
import edu.uade.api.tpo.modelo.Reclamo;
import edu.uade.api.tpo.modelo.Unidad;
import edu.uade.api.tpo.services.implemented.EdificioServiceImpl;
import edu.uade.api.tpo.services.interfaces.*;
import edu.uade.api.tpo.views.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@Slf4j
public class Controlador {

	private final IEdificioService edificioService;

	private final IUnidadService unidadService;

	private final IPersonaService personaService;

	private final IReclamoService reclamoService;

	private final IImagenService imagenService;
	@Autowired
	private Controlador(IEdificioService edificioService, IUnidadService unidadService,IPersonaService personaService, IReclamoService reclamoService, IImagenService imagenService) {
		this.edificioService=edificioService;
		this.unidadService=unidadService;
		this.personaService=personaService;
		this.reclamoService= reclamoService;
		this.imagenService= imagenService;
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
		return edificioService.update(edificio, codigo);
	}

	public void eliminarEdificio(int codigo){
		edificioService.deleteById(codigo);
	}

	public List<UnidadView> obtenerUnidadesConEdificios(){
		return unidadService.findAll().stream().map(Unidad::toView).toList();
	}

	public List<UnidadSinEdificioView> getUnidadesPorEdificio(int codigoEdificio){
		return unidadService.findAll().stream().filter(u -> u.getEdificio().getCodigo()==codigoEdificio).map(Unidad::toViewSinEdificios).toList();
	}

	public UnidadView agregarUnidad(Unidad unidad){
		return unidadService.save(unidad).toView();
	}

	public UnidadView actualizarUnidad(Unidad unidad, int id){
		return unidadService.update(unidad,id).toView();
	}
	
	public List<PersonaView> habilitadosPorEdificio(int codigo) throws EdificioException{
		List<PersonaView> resultado = new ArrayList<PersonaView>();
		Edificio edificio = buscarEdificioCompleto(codigo);
		Set<Persona> habilitados = edificio.habilitados();
		for(Persona persona : habilitados)
			resultado.add(persona.toView());
		return resultado;
	}

	private Edificio buscarEdificioCompleto(int codigo){
		return edificioService.findById(codigo).orElseGet(null);
	}

	public List<PersonaView> dueniosPorEdificio(int codigo) throws EdificioException{
		Edificio edificio = buscarEdificioCompleto(codigo);
		return edificio.duenios().stream().map(Persona::toView).toList();
	}

	public List<PersonaView> habitantesPorEdificio(int codigo) throws EdificioException{
		Edificio edificio= buscarEdificioCompleto(codigo);
		return edificio.habitantes().stream().map(Persona::toView).toList();
	}

	public List<PersonaView> dueniosPorUnidad(int codigo) throws UnidadException{
		Unidad unidad =  buscarUnidadCompleto(codigo);
		return unidad.getDuenios().stream().map(Persona::toView).toList();
	}

	public List<PersonaView> inquilinosPorUnidad(int codigo, String piso, String numero) throws UnidadException{
		Unidad unidad =  buscarUnidadCompleto(codigo);
		return unidad.getInquilinos().stream().map(Persona::toView).toList();
	}
	
	public void transferirUnidad(int codigo, String documento) throws UnidadException, PersonaException {
		Unidad unidad = buscarUnidadCompleto(codigo);
		Persona persona = buscarPersona(documento);
		unidad.transferir(persona);
		unidadService.update(unidad,unidad.getId());
	}

	public void agregarDuenioUnidad(int codigo, String documento) throws UnidadException, PersonaException {
		Unidad unidad = buscarUnidadCompleto(codigo);
		Persona persona = buscarPersona(documento);
		unidad.agregarDuenio(persona);
		unidadService.update(unidad, unidad.getId());
	}

	public void alquilarUnidad(int codigo, String documento) throws UnidadException, PersonaException{
		Unidad unidad = buscarUnidadCompleto(codigo);
		Persona persona = buscarPersona(documento);
		unidad.alquilar(persona);
		unidadService.update(unidad, unidad.getId());
	}

	public void agregarInquilinoUnidad(int codigo, String piso, String numero, String documento) throws UnidadException, PersonaException{
		Unidad unidad = buscarUnidadCompleto(codigo);
		Persona persona = buscarPersona(documento);
		unidad.agregarInquilino(persona);
		unidadService.update(unidad, unidad.getId());
	}

	public void liberarUnidad(int codigo, String piso, String numero) throws UnidadException {
		Unidad unidad = buscarUnidadCompleto(codigo);
		unidad.liberar();
		unidadService.update(unidad, unidad.getId());
	}
	
	public void habitarUnidad(int codigo, String piso, String numero) throws UnidadException {
		Unidad unidad = buscarUnidadCompleto(codigo);
		unidad.habitar();
		unidadService.update(unidad, unidad.getId());
	}
	
	public void agregarPersona(String documento, String nombre) {
		personaService.save(new Persona(documento, nombre));
	}
	
	public void eliminarPersona(String documento) throws PersonaException {
		Persona persona = buscarPersona(documento);
		personaService.deleteById(documento);
	}
	
	public List<ReclamoView> reclamosPorEdificio(int codigo){
		return reclamoService.findAll().stream().filter(r -> r.getEdificio().getCodigo()==codigo).map(Reclamo::toView).toList();
	}
	
	public List<ReclamoView> reclamosPorUnidad(int codigo) {
		return reclamoService.findAll().stream().filter(r -> r.getUnidad().getId()==codigo).map(Reclamo::toView).toList();
	}
	
	public ReclamoView reclamosPorNumero(int numero) {
		return reclamoService.findById(numero).orElseGet(null).toView();
	}
	
	public List<ReclamoView> reclamosPorPersona(String documento) {
		List<ReclamoView> resultado = new ArrayList<ReclamoView>();
		return resultado;
	}
 
	public int agregarReclamo(int codigoEdificio, int codigoUnidad, String documento, String ubicacion, String descripcion) throws EdificioException, UnidadException, PersonaException {
		Edificio edificio = buscarEdificioCompleto(codigoEdificio);
		Unidad unidad = buscarUnidadCompleto(codigoUnidad);
		Persona persona = buscarPersona(documento);
		Reclamo reclamo = new Reclamo(persona, edificio, ubicacion, descripcion, unidad);
		reclamoService.save(reclamo);
		return reclamo.getNumero();
	}
	public Reclamo agregarReclamo(Reclamo reclamo){
		return reclamoService.save(reclamo);
	}
	
	public void agregarImagenAReclamo(int numero, String direccion, String tipo) throws ReclamoException {
		Reclamo reclamo = buscarReclamo(numero);
		reclamo.agregarImagen(direccion, tipo);
		reclamoService.update(reclamo,reclamo.getNumero());
	}
	
	public void cambiarEstado(int numero, Estado estado) throws ReclamoException {
		Reclamo reclamo = buscarReclamo(numero);
		reclamo.cambiarEstado(estado);
		reclamo.update();
	}
	
	public EdificioView buscarEdificio(int codigo) throws EdificioException {
		return edificioService.findById(codigo).orElse(null).toView();
	}

	public UnidadView buscarUnidadPorCodigo(int codigo) throws UnidadException{
		return unidadService.findById(codigo).orElse(null).toView();
	}
	
	private Persona buscarPersona(String documento) throws PersonaException {
		return personaService.findById(documento).orElseGet(null);
	}
	
	private Reclamo buscarReclamo(int numero) throws ReclamoException {
		return null;
	}

	private Unidad buscarUnidadCompleto(int id){
		return unidadService.findById(id).orElseGet(null);
	}

	public ReclamoView actualizarReclamo(Reclamo reclamo,int id){
		return reclamoService.update(reclamo,id).toView();
	}
}

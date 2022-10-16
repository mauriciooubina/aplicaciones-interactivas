package edu.uade.api.tpo.controlador;

import edu.uade.api.tpo.exceptions.EdificioException;
import edu.uade.api.tpo.exceptions.PersonaException;
import edu.uade.api.tpo.exceptions.ReclamoException;
import edu.uade.api.tpo.exceptions.UnidadException;
import edu.uade.api.tpo.modelo.*;
import edu.uade.api.tpo.services.interfaces.*;
import edu.uade.api.tpo.views.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
		Edificio edificio = buscarEdificioCompleto(codigo);
		return edificio.habilitados().stream().map(Persona::toView).toList();
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

	public List<PersonaView> inquilinosPorUnidad(int codigo) throws UnidadException{
		Unidad unidad =  buscarUnidadCompleto(codigo);
		return unidad.getInquilinos().stream().map(Persona::toView).toList();
	}
	
	public void transferirUnidad(UnidadPersona unidadPersona) throws UnidadException, PersonaException {
		Unidad unidad = buscarUnidadCompleto(unidadPersona.getCodigoUnidad());
		Persona persona = buscarPersona(unidadPersona.getDocumento());
		unidad.transferir(persona);
		unidadService.update(unidad,unidad.getId());
	}

	public void agregarDuenioUnidad(UnidadPersona unidadPersona) throws UnidadException, PersonaException {
		Unidad unidad = buscarUnidadCompleto(unidadPersona.getCodigoUnidad());
		Persona persona = buscarPersona(unidadPersona.getDocumento());
		unidad.agregarDuenio(persona);
		unidadService.update(unidad, unidad.getId());
	}

	public void agregarInquilinoUnidad(UnidadPersona unidadPersona) throws UnidadException, PersonaException{
		Unidad unidad = buscarUnidadCompleto(unidadPersona.getCodigoUnidad());
		Persona persona = buscarPersona(unidadPersona.getDocumento());
		if(unidad.getInquilinos().isEmpty()){
			unidad.alquilar(persona);
		}else{
			unidad.agregarInquilino(persona);
		}
		unidadService.update(unidad, unidad.getId());
	}

	public void liberarUnidad(int codigo) throws UnidadException {
		Unidad unidad = buscarUnidadCompleto(codigo);
		unidad.liberar();
		unidadService.update(unidad, unidad.getId());
	}
	
	public void habitarUnidad(int codigo) throws UnidadException {
		Unidad unidad = buscarUnidadCompleto(codigo);
		unidad.habitar();
		unidadService.update(unidad, unidad.getId());
	}
	
	public PersonaView agregarPersona(Persona persona) {
		return personaService.save(persona).toView();
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
		reclamo.setEstado(estado);
	}
	
	public EdificioView buscarEdificio(int codigo) throws EdificioException {
		return edificioService.findById(codigo).orElse(null).toView();
	}

	public UnidadView buscarUnidadPorCodigo(int codigo) throws UnidadException{
		return unidadService.findById(codigo).orElse(null).toView();
	}
	
	public Persona buscarPersona(String documento) throws PersonaException {
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

	public PersonaView validarPassword(Persona persona) throws PersonaException {
		Persona usuarioValidado= buscarPersona(persona.getDocumento());
		if(usuarioValidado==null || !(usuarioValidado.getPassword().equals(persona.getPassword()))){
			return new PersonaView();
		}else{
			return usuarioValidado.toView();
		}
	}

	public PersonaView actualizarPersona(Persona persona, String documento) {
		return personaService.update(persona, documento).toView();
	}

	public PersonaConUnidadesView obtenerPersonaConUnidades(String documento){
		List<UnidadView> duenioDe= new ArrayList<>();
		List<UnidadView> inquilindoDe= new ArrayList<>();
		List<Unidad> unidades= unidadService.findAll();
		Persona persona= personaService.findById(documento).orElse(null);
		unidades.forEach(unidad-> {
			if(unidad.getDuenios().stream().filter(d -> d.getDocumento().equals(persona.getDocumento())).count()==1){
				duenioDe.add(unidad.toView());
			}else if(unidad.getInquilinos().stream().filter(i -> i.getDocumento().equals(persona.getDocumento())).count()==1){
				inquilindoDe.add(unidad.toView());
			}
		});
		return new PersonaConUnidadesView(persona,duenioDe,inquilindoDe);
	}
}

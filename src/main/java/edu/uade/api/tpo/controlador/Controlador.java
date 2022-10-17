package edu.uade.api.tpo.controlador;

import edu.uade.api.tpo.exceptions.EdificioException;
import edu.uade.api.tpo.exceptions.PersonaException;
import edu.uade.api.tpo.exceptions.ReclamoException;
import edu.uade.api.tpo.exceptions.UnidadException;
import edu.uade.api.tpo.modelo.*;
import edu.uade.api.tpo.services.interfaces.IEdificioService;
import edu.uade.api.tpo.services.interfaces.IPersonaService;
import edu.uade.api.tpo.services.interfaces.IReclamoService;
import edu.uade.api.tpo.services.interfaces.IUnidadService;
import edu.uade.api.tpo.views.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Controller
@Slf4j
public class Controlador {

	private final IEdificioService edificioService;

	private final IUnidadService unidadService;

	private final IPersonaService personaService;

	private final IReclamoService reclamoService;
	@Autowired
	private Controlador(IEdificioService edificioService, IUnidadService unidadService,IPersonaService personaService, IReclamoService reclamoService) {
		this.edificioService=edificioService;
		this.unidadService=unidadService;
		this.personaService=personaService;
		this.reclamoService= reclamoService;
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

	private Edificio buscarEdificioCompleto(int codigo) throws EdificioException{
		return edificioService.findById(codigo).orElseThrow(() -> new EdificioException("Edificio no encontrado"));
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
			if(!unidad.getInquilinos().contains(persona)) {
				unidad.agregarInquilino(persona);
			}else{
				throw new UnidadException("Inquilino previamente agregado");
			}
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
	
	public ReclamoView reclamosPorNumero(int numero) throws ReclamoException {
		return reclamoService.findById(numero).orElseThrow(() -> new ReclamoException("Reclamo no encontrado")).toView();
	}
	
	public List<ReclamoView> reclamosPorPersona(String documento) {
		return reclamoService.findAll().stream().filter(r -> r.getUsuario().getDocumento().equals(documento)).map(Reclamo::toView).toList();
	}
	public Reclamo agregarReclamo(Reclamo reclamo) throws ReclamoException {
		if(reclamo.getUnidad()==null){
			AtomicBoolean habitanteEnEdificio= new AtomicBoolean(false);
			Edificio edificio= buscarEdificioCompleto(reclamo.getEdificio().getCodigo());
			edificio.getUnidades().forEach(unidad -> {
				if(personaEnUnidad(unidad,reclamo.getUsuario())){
					habitanteEnEdificio.set(true);
				}
			});

			if(!habitanteEnEdificio.get()){
				throw new ReclamoException("Usuario no perteneciente al edificio");
			}
		}else{
			Unidad unidad= buscarUnidadCompleto(reclamo.getUnidad().getId());
			if(unidad.getInquilinos().size()>0 && !(unidad.esInquilino(reclamo.getUsuario()))){
				throw new ReclamoException("Solo puede generar el reclamo un inquilino");
			}else if(unidad.getInquilinos().size()==0 && !(unidad.esDuenio(reclamo.getUsuario()))){
				throw new ReclamoException("Persona no relacionada a la unidad adjunta");
			}
		}
		return reclamoService.save(reclamo);
	}
	
	public void agregarImagenAReclamo(int numero, List<Imagen> imagenes) throws ReclamoException {
		Reclamo reclamo = buscarReclamo(numero);
		imagenes.forEach(imagen -> reclamo.agregarImagen(imagen.getDireccion(),imagen.getTipo()));
		reclamoService.update(reclamo,reclamo.getNumero());
	}
	
	public void cambiarEstado(int numero, EstadoMedidas estadoMedidas) throws ReclamoException {
		Reclamo reclamo = buscarReclamo(numero);
		reclamo.setEstado(Estado.values()[estadoMedidas.getEstado()]);
		if(estadoMedidas.getMedidas()!=null && estadoMedidas.getMedidas().length()>0){
			reclamo.setDescripcion(estadoMedidas.getMedidas());
		}
		reclamoService.update(reclamo,numero);
	}
	
	public EdificioView buscarEdificio(int codigo) throws EdificioException {
		return edificioService.findById(codigo).orElseThrow(() -> new EdificioException("Edificio no encontrado")).toView();
	}

	public UnidadView buscarUnidadPorCodigo(int codigo) throws UnidadException{
		return unidadService.findById(codigo).orElseThrow(() -> new UnidadException("Unidad no encontrada")).toView();
	}

	public Persona buscarPersona(String documento) throws PersonaException {
		return personaService.findById(documento).orElseThrow(() -> new PersonaException("Persona no encontrada"));
	}
	
	public Reclamo buscarReclamo(int numero) throws ReclamoException {
		return reclamoService.findById(numero).orElseThrow(() -> new ReclamoException("Reclamo no encontrado"));
	}

	public Unidad buscarUnidadCompleto(int id){
		return unidadService.findById(id).orElseThrow(() -> new UnidadException("Unidad no encontrada"));
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

	public PersonaConUnidadesView obtenerPersonaConUnidades(String documento) throws PersonaException {
		List<UnidadView> duenioDe= new ArrayList<>();
		List<UnidadView> inquilindoDe= new ArrayList<>();
		List<Unidad> unidades= unidadService.findAll();
		Persona persona= buscarPersona(documento);
		unidades.forEach(unidad-> {
			if(unidad.getDuenios().stream().filter(d -> d.getDocumento().equals(persona.getDocumento())).count()==1){
				duenioDe.add(unidad.toView());
			}else if(unidad.getInquilinos().stream().filter(i -> i.getDocumento().equals(persona.getDocumento())).count()==1){
				inquilindoDe.add(unidad.toView());
			}
		});
		return new PersonaConUnidadesView(persona,duenioDe,inquilindoDe);
	}

	public List<UnidadSinEdificioView> obtenerUnidadesPorEdificio(int codigoEdificio){
		Edificio edificio= buscarEdificioCompleto(codigoEdificio);

		return edificio.getUnidades().stream().map(Unidad::toViewSinEdificios).toList();
	}

	private boolean personaEnUnidad(Unidad unidad, Persona persona){
		return (unidad.getInquilinos().contains(persona) || unidad.getDuenios().contains(persona));
	}
}

package com.solusoft.springboot.backend.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.solusoft.springboot.backend.apirest.models.entity.Cliente;
import com.solusoft.springboot.backend.apirest.models.services.IClienteService;
import com.solusoft.springboot.backend.apirest.models.services.IUploadFileService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileService uploadService;
	
	private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);
	
	
	//Metodo para listar los clientes
	@GetMapping("/clientes")
	public List<Cliente> index(){
		
		return clienteService.findAll();
	}
	
	//Paginacion
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page){	
		Pageable pageable = PageRequest.of(page, 10);
		return clienteService.findAll(pageable);
	}
	
	
	//Mostrar clientes por su id, y manejo de errores	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Cliente cliente = null;
		//Map que almacene objetos o valores asociados a un nombre 
		Map<String, Object> response = new HashMap<>();//Map es la interfaz HashMap es la implementacion 
			 	
		try {
			cliente = clienteService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(cliente == null) {
			response.put("mensaje", "El cliente con el ID: ".concat(id.toString().concat(" no existe en la base de datos...")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);//codigo de error 404
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);//El segundo argumento es el estado de la respuesta 
	}
	
	//Crear clientes
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
		
		//Validar si vienen errores
		if(result.hasErrors()){//Si hay errores
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());	
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		try {
			clienteNew = clienteService.save(cliente);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado con exito...");
		response.put("cliente", clienteNew);
		
		return new ResponseEntity<Map<String, Object>> (response , HttpStatus.CREATED);
	}
	
	//Actualizar un cliente 
	@PutMapping("/clientes/{id}")
	public  ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		//Validar si vienen errores 
		if(result.hasErrors()){//Si hay errores
			
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());	
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		if(clienteActual == null) {//Si no existe el cliente
			response.put("mensaje", "Error: no se pudo editar, el cliente con el ID: ".concat(id.toString().concat(" no existe en la base de datos...")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {			
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNit(cliente.getNit());
			clienteActual.setTelefono(cliente.getTelefono());
			clienteActual.setCreateAt(cliente.getCreateAt());
			
			clienteUpdate = clienteService.save(clienteActual);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.put("mensaje", "El cliente ha sido actualizado con exito...");
		response.put("cliente", clienteUpdate);
		
		return new ResponseEntity<Map<String, Object>> (response , HttpStatus.CREATED); 
	}
	
	//Eliminar un cliente
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			
			//Si el cliente tiene que foto que se elimina 
			Cliente cliente = clienteService.findById(id);
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);
			
			clienteService.delete(id);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido eliminado con exito...");
		
		return new ResponseEntity<Map<String, Object>> (response , HttpStatus.OK);		
	}
	
	
	//Agregar imagen al cliente
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload (@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
		//Actualizando la tabla del cliente con la imagen 
		Map<String, Object> response = new HashMap<>();
		
		Cliente cliente = clienteService.findById(id);
		
		if(!archivo.isEmpty()) {//si existe la imagen
			
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
				
			}catch(IOException e) {
				response.put("mensaje", "Error al subir la imagen del cliente");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//Cuando se sube una nueva foto que se borre la anterior
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);
			
			cliente.setFoto(nombreArchivo);
			
			//actualizar el cliente
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje", "Has subido correctamente la imagen: "+nombreArchivo);
			
		}
		 
		return new ResponseEntity<Map<String, Object>> (response , HttpStatus.CREATED); 
	}
	
	//Mostrar la imagen en el navegador 
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		
		Resource recurso = null;
		
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		//Forzar la descarga de la img
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
	}
}




















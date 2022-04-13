package com.solusoft.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.solusoft.springboot.backend.apirest.models.entity.Cliente;
import com.solusoft.springboot.backend.apirest.models.services.IClienteService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	
	@Autowired
	private IClienteService clienteService;
	
	//Metodo para listar los clientes
	@GetMapping("/clientes")
	public List<Cliente> index(){
		
		return clienteService.findAll();
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
			response.put("Mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(cliente == null) {
			response.put("Mensaje", "El cliente con el ID: ".concat(id.toString().concat(" no existe en la base de datos...")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);//codigo de error 404
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);//El segundo argumento es el estado de la respuesta 
	}
	
	//crear clientes
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
		
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
	public  ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if(clienteActual == null) {//Si no existe el cliente
			response.put("Mensaje", "Error: no se pudo editar, el cliente con el ID: ".concat(id.toString().concat(" no existe en la base de datos...")));
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
			clienteService.delete(id);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido eliminado con exito...");
		
		return new ResponseEntity<Map<String, Object>> (response , HttpStatus.OK);
		
	}
	
}




















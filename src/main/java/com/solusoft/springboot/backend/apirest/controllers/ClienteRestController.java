package com.solusoft.springboot.backend.apirest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	//Mostrar por id
	@GetMapping("/clientes/{id}")
	public Cliente show(@PathVariable Long id) {
		return clienteService.findById(id);
	}
	
	//crear clientes
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)//201 se ha creado contenido
	public Cliente create(@RequestBody Cliente cliente) {
		return clienteService.save(cliente);
	}
	
	//Actualizar un cliente 
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)//201 se ha actualizado 
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente clienteActual = clienteService.findById(id);
		
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNit(cliente.getNit());
		clienteActual.setTelefono(cliente.getTelefono());
		
		return clienteService.save(clienteActual);
	}
	
	
	//Eliminar un cliente
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) //204
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}
	
}




















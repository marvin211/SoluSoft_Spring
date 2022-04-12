package com.solusoft.springboot.backend.apirest.models.services;

import java.util.List;

import com.solusoft.springboot.backend.apirest.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Cliente findById(Long id);//Buscar por id
	
	public Cliente save(Cliente cliente);//Recibe el cliente y va retornar el cliente guardado
	
	public void delete(Long id);//Se le pasa el id del cliente a eliminar
	
	
}

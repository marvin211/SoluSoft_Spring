package com.solusoft.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.solusoft.springboot.backend.apirest.models.entity.Cliente;
import com.solusoft.springboot.backend.apirest.models.entity.Factura;
import com.solusoft.springboot.backend.apirest.models.entity.Producto;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);//paginacion
	
	public Cliente findById(Long id);//Buscar por id
	
	public Cliente save(Cliente cliente);//Recibe el cliente y va retornar el cliente guardado
	
	public void delete(Long id);//Se le pasa el id del cliente a eliminar
	
	
	
	
	public Factura findFacturaById(Long id);
	
	public Factura saveFactura(Factura factura);
	
	public void deleteFacturaById(Long id);
	
	public List<Producto> findProductoByNombre(String term);
	
	
}

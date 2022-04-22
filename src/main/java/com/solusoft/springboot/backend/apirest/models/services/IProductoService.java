package com.solusoft.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.solusoft.springboot.backend.apirest.models.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	
	public Page<Producto> findAll(Pageable pageable);//paginacion
	
	public Producto findById(Long id);//Buscar por id
	
	public Producto save(Producto producto);//Guardar productos
	
	public void delete(Long id);//Se le pasa el id del producto a eliminar
		
}

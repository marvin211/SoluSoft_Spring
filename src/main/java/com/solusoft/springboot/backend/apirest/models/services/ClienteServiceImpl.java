package com.solusoft.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solusoft.springboot.backend.apirest.models.dao.IClienteDao;
import com.solusoft.springboot.backend.apirest.models.dao.IFacturaDao;
import com.solusoft.springboot.backend.apirest.models.dao.IProductoDao;
import com.solusoft.springboot.backend.apirest.models.entity.Cliente;
import com.solusoft.springboot.backend.apirest.models.entity.Factura;
import com.solusoft.springboot.backend.apirest.models.entity.Producto;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Override
	@Transactional(readOnly = true)//lectura
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll(); //accediendo a la lista de clientes 
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)//lectura
	public Cliente findById(Long id) {
		
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional //Transactional completo
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}
	
	@Override
	@Transactional //Transactional completo
	public void delete(Long id) {
		 clienteDao.deleteById(id);
	}

	
	
	
	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Factura saveFactura(Factura factura) {
		return facturaDao.save(factura);
	}

	@Override
	@Transactional
	public void deleteFacturaById(Long id) {
		facturaDao.deleteById(id);	
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductoByNombre(String term) {
		return productoDao.findByNombre(term);
	}
}

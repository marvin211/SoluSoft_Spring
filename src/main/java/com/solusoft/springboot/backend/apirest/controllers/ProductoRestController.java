package com.solusoft.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;

import com.solusoft.springboot.backend.apirest.models.entity.Producto;
import com.solusoft.springboot.backend.apirest.models.services.IProductoService;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ProductoRestController {
	
	@Autowired
	private IProductoService productoService;

	
	//Metodo para listar los productos
	@GetMapping("/productos")
	public List<Producto> index(){
		
		return productoService.findAll();
	}
	
	//Paginacion
	@GetMapping("/productos/page/{page}")
	public Page<Producto> index(@PathVariable Integer page){	
		Pageable pageable = PageRequest.of(page, 4);
		return productoService.findAll(pageable);
	}

	//Mostrar productos por su id, y manejo de errores	
		@GetMapping("/productos/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			Producto producto = null;

			Map<String, Object> response = new HashMap<>();//Map es la interfaz HashMap es la implementacion 
				 	
			try {
				producto = productoService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(producto == null) {
				response.put("mensaje", "El producto con el ID: ".concat(id.toString().concat(" no existe en la base de datos...")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);//codigo de error 404
			}
			
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}
	
		//Crear productos
		@PostMapping("/productos")
		public ResponseEntity<?> create(@Valid @RequestBody Producto producto, BindingResult result) {
			Producto productoNew = null;  
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
				productoNew = productoService.save(producto);
				
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			response.put("mensaje", "El producto ha sido creado con exito...");
			response.put("producto", productoNew);
			
			return new ResponseEntity<Map<String, Object>> (response , HttpStatus.CREATED);
		}
		
		//Actualizar producto
		@PutMapping("/productos/{id}")
		public  ResponseEntity<?> update(@Valid @RequestBody Producto producto, BindingResult result, @PathVariable Long id) {
			Producto productoActual = productoService.findById(id);
			Producto productoUpdate = null;
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
			
			
			if(productoActual == null) {//Si no existe el producto
				response.put("mensaje", "Error: no se pudo editar, el producto con el ID: ".concat(id.toString().concat(" no existe en la base de datos...")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try {			
				productoActual.setNombre(producto.getNombre());
				productoActual.setDescripcion(producto.getDescripcion());
				productoActual.setPrecio(producto.getPrecio());
				productoActual.setCreateAt(producto.getCreateAt());
				
				productoUpdate = productoService.save(productoActual);
				
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el producto en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			response.put("mensaje", "El producto ha sido actualizado con exito...");
			response.put("producto", productoUpdate);
			
			return new ResponseEntity<Map<String, Object>> (response , HttpStatus.CREATED); 
		}
		
		
		//Eliminar producto 
		@DeleteMapping("/productos/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			Map<String, Object> response = new HashMap<>();
			try {
				
				productoService.delete(id);
				
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el producto en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			response.put("mensaje", "El producto ha sido eliminado con exito...");
			
			return new ResponseEntity<Map<String, Object>> (response , HttpStatus.OK);		
		}
	
}





















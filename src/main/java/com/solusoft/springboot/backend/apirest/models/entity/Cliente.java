package com.solusoft.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 4, max = 25, message = "el tamaño tiene que estar entre 4 y 12 caracteres")
	@Column(nullable = false)//el nombre no puede ser nulo
	private String nombre;
	
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 4, max = 25, message = "el tamaño tiene que estar entre 4 y 12 caracteres")
	private String apellido;
	private String nit;
	private String telefono;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	//Antes de que se haga un persist en la base de datos que incluya la fecha
	@PrePersist
	public void prePersist() { //de forma automatica se va crear la fecha
		createAt = new Date(); 
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
	private static final long serialVersionUID = 1L;
	
}












package com.solusoft.springboot.backend.apirest.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solusoft.springboot.backend.apirest.security.entity.Rol;
import com.solusoft.springboot.backend.apirest.security.enums.RolNombre;
import com.solusoft.springboot.backend.apirest.security.repository.RolRepository;

@Service
@Transactional
public class RolService {

	@Autowired
	RolRepository rolRepository;
	
	public Optional<Rol> getByRolNombre(RolNombre rolNombre){
		return rolRepository.findByRolNombre(rolNombre);
	}
	
	public void save(Rol rol) {
		rolRepository.save(rol);
	}
}























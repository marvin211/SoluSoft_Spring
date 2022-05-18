package com.solusoft.springboot.backend.apirest.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solusoft.springboot.backend.apirest.security.entity.Rol;
import com.solusoft.springboot.backend.apirest.security.enums.RolNombre;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
	Optional<Rol> findByRolNombre(RolNombre rolNombre);
	
}

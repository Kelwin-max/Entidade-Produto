package com.login.loginn;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	

	Usuario findByEmail(String email);

	Integer findRandomId();

	Usuario findByName(String name);

}

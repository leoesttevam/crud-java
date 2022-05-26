package br.com.springboot.curso_jdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.springboot.curso_jdev.model.Usuario;

@Repository
public interface UsuarioRpository extends JpaRepository<Usuario, Long>{

}

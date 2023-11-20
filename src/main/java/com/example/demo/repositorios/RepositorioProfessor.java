package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Professor;

@Repository
public interface RepositorioProfessor extends JpaRepository<Professor, Long> {

}

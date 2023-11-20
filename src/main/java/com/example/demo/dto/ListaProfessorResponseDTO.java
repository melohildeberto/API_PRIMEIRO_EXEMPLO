package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entidades.Professor;

public class ListaProfessorResponseDTO extends BasicDTO{
	public int quantidadeTotal;
	public List<Professor> professores;
	public ListaProfessorResponseDTO() {
		super.setMensagem(new ArrayList<>());
	}
}

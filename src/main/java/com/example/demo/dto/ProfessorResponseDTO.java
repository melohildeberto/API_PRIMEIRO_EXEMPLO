package com.example.demo.dto;

import java.util.ArrayList;

import com.example.demo.entidades.Professor;

public class ProfessorResponseDTO extends BasicDTO{
	public Professor professor;
	public ProfessorResponseDTO() {
		super.setMensagem(new ArrayList<>());
	}
}

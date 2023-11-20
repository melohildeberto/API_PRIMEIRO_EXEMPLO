package com.example.demo.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProfessorResponseDTO;
import com.example.demo.entidades.Professor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("meucontroler")
public class Controler {
	@GetMapping("/")
	public String teste() {
		return "Primeira API";
	}
	//@CrossOrigin(origins = "*")
	@PostMapping("/cadastrar")
	ResponseEntity<ProfessorResponseDTO> cadastrar(@Valid @RequestBody Professor dados, BindingResult bindingResult) {
		ProfessorResponseDTO response = new ProfessorResponseDTO();
		response.setStatusCode("200");
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.getMensagem().add("Professor validado");
			response.professor = dados;
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
}

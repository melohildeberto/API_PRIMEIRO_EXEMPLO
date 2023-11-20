package com.example.demo.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ListaProfessorResponseDTO;
import com.example.demo.dto.ProfessorResponseDTO;
import com.example.demo.entidades.Professor;
import com.example.demo.repositorios.RepositorioProfessor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("professor")
@Api(value="API REST Professor")
public class ControladorProfessor {
	@Autowired
	private RepositorioProfessor repositorioProfessor;

	@GetMapping("/listar")
	public ResponseEntity<ListaProfessorResponseDTO> listar() {
		ListaProfessorResponseDTO response = new ListaProfessorResponseDTO();
		response.setStatusCode("200");
		List<Professor> lista = (List<Professor>) repositorioProfessor.findAll();
		response.quantidadeTotal = lista.size();
		if(lista.size() == 0) {
			response.getMensagem().add("Consulta sem Resultados");
		} else {
			response.professores = lista; 
		}
		return new ResponseEntity<>(
				
				response, HttpStatus.OK);
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<ProfessorResponseDTO> cadastrar(@Valid @RequestBody Professor dados, BindingResult bindingResult) {
		ProfessorResponseDTO response = new ProfessorResponseDTO();
		response.setStatusCode("200");
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			try {
				dados = repositorioProfessor.save(dados);
				response.professor = dados;
				response.getMensagem().add("Professor cadastrado com sucesso");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (DataIntegrityViolationException e) {
				response.professor = dados;
				response.getMensagem().add(e.getLocalizedMessage());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		}
	}

	@GetMapping("/getProfessor/{id}")
	public ResponseEntity<ProfessorResponseDTO> getProfessor(@PathVariable Long id) {
		ProfessorResponseDTO response = new ProfessorResponseDTO();
		response.setStatusCode("200");
		Optional<Professor> buscarProfessor = repositorioProfessor.findById(id);
		if (buscarProfessor.isPresent() == false) {
			response.setStatusCode("199");
			response.getMensagem().add("Professor não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.getMensagem().add("Professor encontrado");
			response.professor = buscarProfessor.get();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PutMapping("/atualizar/{id}")
	public ResponseEntity<ProfessorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody Professor dados,
			BindingResult bindingResult) {
		ProfessorResponseDTO response = new ProfessorResponseDTO();
		response.setStatusCode("200");
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			Optional<Professor> buscarProfessor = repositorioProfessor.findById(id);
			if (buscarProfessor.isPresent() == false) {
				response.setStatusCode("199");
				response.getMensagem().add("Professor não encontrado");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				response.getMensagem().add("Professor atualizado");
				dados.id = buscarProfessor.get().id;
				response.professor = repositorioProfessor.save(dados);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ProfessorResponseDTO> delete(@PathVariable Long id) {
		ProfessorResponseDTO response = new ProfessorResponseDTO();
		response.setStatusCode("200");
		Optional<Professor> buscarProfessor = repositorioProfessor.findById(id);
		if (buscarProfessor.isPresent() == false) {
			response.setStatusCode("199");
			response.getMensagem().add("Professor não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.getMensagem().add("Professor removido");
			repositorioProfessor.deleteById(id);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
}

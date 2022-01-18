package com.nutritec.backend.Nutri.Controller;

import java.util.List;
import java.util.Optional;

import com.nutritec.backend.Nutri.Model.Paciente;
import com.nutritec.backend.Nutri.Repository.PacienteRepository;
import com.nutritec.backend.Nutri.Service.PacienteService;
import com.nutritec.backend.Util.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/paciente")
@AllArgsConstructor
public class PacienteController {

    PacienteService service;
    PacienteRepository repository;


    @PostMapping
    public ResponseEntity<?> cadastrarPaciente(@RequestBody Paciente paciente, UriComponentsBuilder ucBuilder) {

        int controle = service.verificarPaciente(paciente);
        if (controle == 1) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! cpf nulo ou invalido"), HttpStatus.CONFLICT);
        }else if(controle == 2){
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Os dados não podem ser vazios ou nulos"), HttpStatus.CONFLICT);
        }

       List<Paciente> pessoas = repository.findByCpf(paciente.getCpf());

        if (!pessoas.isEmpty()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Paciente já cadastrado"), HttpStatus.CONFLICT);
        }

        repository.save(paciente);
        return new ResponseEntity<Paciente>(paciente, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
	public ResponseEntity<?> atualizarPaciente(@RequestBody Paciente paciente) {

        List<Paciente> pessoas = repository.findByCpf(paciente.getCpf());

        if (pessoas.isEmpty()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Paciente não cadastrado"), HttpStatus.CONFLICT);
        }

        Paciente p = service.update(paciente);
        return new ResponseEntity<Paciente>(p, HttpStatus.OK);
    } 

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletarPaciente(@PathVariable long id) {

        Optional<Paciente> p = repository.findById(id);

        if (!p.isPresent()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Paciente não encontrado"), HttpStatus.CONFLICT);
        }

        repository.deleteById(id);
        return new ResponseEntity<Paciente>(p.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
	public ResponseEntity<?> consultarPaciente(@PathVariable long id){

        Optional<Paciente> p = repository.findById(id);

        if (p.isPresent()) {
            return new ResponseEntity<>(p.get(), HttpStatus.OK);
        }else{
           return new ResponseEntity<CustomError>(new CustomError("ERRO! Paciente não encontrado"), HttpStatus.CONFLICT);
       }
    }

    
    @GetMapping
    public ResponseEntity<?> consultarPacientes(){
        List<Paciente> pessoas = repository.findAll();
        return new ResponseEntity<List<Paciente>>(pessoas, HttpStatus.OK);
    }

}

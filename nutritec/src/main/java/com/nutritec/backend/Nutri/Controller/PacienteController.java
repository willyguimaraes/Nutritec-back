package com.nutritec.backend.Nutri.Controller;

import java.util.List;
import com.nutritec.backend.Nutri.Model.Paciente;
import com.nutritec.backend.Nutri.Repository.PacienteRepository;
import com.nutritec.backend.Nutri.Service.PacienteService;
import com.nutritec.backend.Util.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

       List<Paciente> pessoas = repository.findByCpf(paciente.getCpf());

        if (!pessoas.isEmpty()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Paciente já cadastrado"), HttpStatus.CONFLICT);
        }

        repository.save(paciente);
        return new ResponseEntity<Paciente>(paciente, HttpStatus.CREATED);
    }
    
}

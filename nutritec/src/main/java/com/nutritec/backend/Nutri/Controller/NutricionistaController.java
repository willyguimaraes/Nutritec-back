package com.nutritec.backend.Nutri.Controller;

import java.util.List;

import com.nutritec.backend.Nutri.Model.Nutricionista;
import com.nutritec.backend.Nutri.Repository.NutricionistaRepository;
import com.nutritec.backend.Nutri.Service.NutricionistaService;
import com.nutritec.backend.Util.CustomError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/Nutricionista")
@AllArgsConstructor
public class NutricionistaController {
    
    NutricionistaService service;
    NutricionistaRepository repository;

    @PostMapping
    public ResponseEntity<?> cadastrarNutri(@RequestBody Nutricionista nutricionista, UriComponentsBuilder ucBuilder) {

       List<Nutricionista> nutris = repository.findByMatricula(nutricionista.getMatricula());

        if (!nutris.isEmpty()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Nutricionista já cadastrado"), HttpStatus.CONFLICT);
        }

        repository.save(nutricionista);
        return new ResponseEntity<Nutricionista>(nutricionista, HttpStatus.CREATED);
    }

}

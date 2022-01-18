package com.nutritec.backend.Nutri.Controller;

import java.util.List;
import java.util.Optional;

import com.nutritec.backend.Nutri.Model.Nutricionista;
import com.nutritec.backend.Nutri.Repository.NutricionistaRepository;
import com.nutritec.backend.Nutri.Service.NutricionistaService;
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
@RequestMapping("/api/Nutricionista")
@AllArgsConstructor
public class NutricionistaController {
    
    NutricionistaService service;
    NutricionistaRepository repository;

    @PostMapping
    public ResponseEntity<?> cadastrarNutri(@RequestBody Nutricionista nutricionista, UriComponentsBuilder ucBuilder) {

        int controle = service.verificarNutricionista(nutricionista);
        if (controle == 1) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Matricula nula ou invalida"), HttpStatus.CONFLICT);
        }else if(controle == 2){
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Os dados não podem ser vazios ou nulos"), HttpStatus.CONFLICT);
        }

        List<Nutricionista> pessoas = repository.findByMatricula(nutricionista.getMatricula());

        if (!pessoas.isEmpty()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Nutricionista já cadastrado"), HttpStatus.CONFLICT);
        }

        repository.save(nutricionista);
        return new ResponseEntity<Nutricionista>(nutricionista, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
	public ResponseEntity<?> AtualizarNutri(@RequestBody Nutricionista nutricionista) {

        int controle = service.verificarNutricionista(nutricionista);
        if (controle == 1) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Nova matricula não pode ser nula ou invalida"), HttpStatus.CONFLICT);
        }else if(controle == 2){
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Os novos dados não podem ser vazios ou nulos"), HttpStatus.CONFLICT);
        }

        List<Nutricionista> pessoas = repository.findByMatricula(nutricionista.getMatricula());

        if (pessoas.isEmpty()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Nutricionista não cadastrado"), HttpStatus.CONFLICT);
        }

        Nutricionista n = service.update(nutricionista);

        return new ResponseEntity<Nutricionista>(n, HttpStatus.OK);

    } 

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletarNutricionista(@PathVariable long id) {

        Optional<Nutricionista> n = repository.findById(id);

        if (!n.isPresent()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Nutricionista não encontrado"), HttpStatus.CONFLICT);
        }

        repository.deleteById(id);
        return new ResponseEntity<Nutricionista>(n.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
	public ResponseEntity<?> consultarNutricionista(@PathVariable long id){

        Optional<Nutricionista> n = repository.findById(id);

        if (n.isPresent()) {
            return new ResponseEntity<>(n.get(), HttpStatus.OK);
        }else{
           return new ResponseEntity<CustomError>(new CustomError("ERRO! Nutricionista não encontrado"), HttpStatus.CONFLICT);
       }
    }

    @GetMapping
    public ResponseEntity<?> consultarNutricionistas(){
        List<Nutricionista> nutris = repository.findAll();
        return new ResponseEntity<List<Nutricionista>>(nutris, HttpStatus.OK);
    }

}

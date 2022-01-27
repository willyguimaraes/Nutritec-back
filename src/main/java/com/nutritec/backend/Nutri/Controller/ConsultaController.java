package com.nutritec.backend.Nutri.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.nutritec.backend.Nutri.Model.Consulta;
import com.nutritec.backend.Nutri.Model.Nutricionista;
import com.nutritec.backend.Nutri.Model.Paciente;
import com.nutritec.backend.Nutri.Repository.ConsultaRepository;
import com.nutritec.backend.Nutri.Service.ConsultaService;
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
@RequestMapping("/api/consulta")
@AllArgsConstructor
public class ConsultaController {

    ConsultaService service;

    @PostMapping
    public ResponseEntity<?> cadastrarConsulta(@RequestBody Consulta c, UriComponentsBuilder ucBuilder) {

        int controle = service.verificarConsulta(c);

        if (controle == 1) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Nutricionista não encontrado"),
                    HttpStatus.CONFLICT);
        } else if (controle == 2) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Paciente não encontrado"),
                    HttpStatus.CONFLICT);
        }

        Consulta consulta = service.novaConsulta(c.getPacienteId(), c.getNutriId(), c.getDataConsulta(), c.getPlano());

        return new ResponseEntity<Consulta>(consulta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verConsulta(@PathVariable long id) {

        Optional<Consulta> n = service.opConsulta(id);

        if (n.isPresent()) {
            String info = service.infoConsulta(n.get());
            return new ResponseEntity<>(info, HttpStatus.OK);
        } else {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Consulta não encontrada"),
                    HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/{id}")
	public ResponseEntity<?> atualizarConsulta(@PathVariable Long id, @RequestBody Consulta consulta) {

        int controle = service.verificarConsulta(consulta);
        if (controle == 1) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Nutricionista não encontrado"),
                    HttpStatus.CONFLICT);
        } else if (controle == 2) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Paciente não encontrado"),
                    HttpStatus.CONFLICT);
        }

        Optional<Consulta> con = service.opConsulta(id);
 
        if (!con.isPresent()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Consulta não existe."), HttpStatus.CONFLICT);
        }

        Consulta c = service.updateConsulta(consulta,id);

        return new ResponseEntity<>(service.infoConsulta(c), HttpStatus.OK);
    } 


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletarConsulta(@PathVariable long id) {

        Optional<Consulta> c = service.opConsulta(id);

        if (!c.isPresent()) {
            return new ResponseEntity<CustomError>(new CustomError("ERRO! Consulta não encontrada"), HttpStatus.CONFLICT);
        }

        String info = service.infoConsulta(c.get()) + "\n\n     ***CONSULTA DELETADA***";
        service.deletar(id);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listarConsultas(){
        List<String> consultas = service.listarConsultas();
        return new ResponseEntity<List<String>>(consultas, HttpStatus.OK);
    }


}

package com.nutritec.backend.Nutri.Service;

import com.nutritec.backend.Nutri.Model.Paciente;
import com.nutritec.backend.Nutri.Repository.PacienteRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PacienteService {

    PacienteRepository repository;

    public Paciente update(Paciente paciente){
        Paciente p = repository.getById(paciente.getId());
        p.setAltura(paciente.getAltura());
        p.setCpf(paciente.getCpf());
        p.setIdade(paciente.getIdade());
        p.setNome(paciente.getNome());
        p.setPeso(paciente.getPeso());
        p.setPlano(paciente.getPlano());
        return p;
    }
  
}

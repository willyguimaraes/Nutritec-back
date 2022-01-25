package com.nutritec.backend.Nutri.Service;

import java.util.ArrayList;
import java.util.List;

import com.nutritec.backend.Nutri.Model.Paciente;
import com.nutritec.backend.Nutri.Repository.PacienteRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice.This;

@AllArgsConstructor
@Service
public class PacienteService {

    PacienteRepository repository;

    public Paciente update(Paciente paciente){
        Paciente p = repository.findByCpf(paciente.getCpf()).get(0);
        p.setAltura(paciente.getAltura());
        p.setCpf(paciente.getCpf());
        p.setIdade(paciente.getIdade());
        p.setNome(paciente.getNome());
        p.setPeso(paciente.getPeso());
        p.setPlano(paciente.getPlano());
        repository.save(p);
        return p;
    }

    public int verificarPaciente(Paciente p) {
        boolean nome = (p.getNome() == null || p.getNome().isEmpty());
        boolean numericos = (p.getAltura() == 0 || p.getIdade() == 0 || p.getPeso() == 0);
        boolean cpf = p.getCpf() == null || p.getCpf().length() != 11 || !numerico(p.getCpf());
        boolean plano = verificaPlano(p.getPlano());
        if (cpf) {
            return 1;
        } else if ( nome || numericos) {
            return 2;
        } else if ( plano) {
            return 3;
        }
        return 0;
    }

    public boolean numerico(String cpf){
        for(int i = 0; i < cpf.length(); i++) {
            if(!cpf.substring(i).matches("[0-9]*")){
                return false;
            }
    }
        return true;
    }

    public boolean verificaPlano(String plano){
        ArrayList<String> planos = new ArrayList<>();
        planos.add("PLANO_VIDA");
        planos.add("HAP_SAUDE");
        planos.add("UT_MAIS");

        if (planos.contains(plano)){
            return true;
        }
        return false;
    }
  
}

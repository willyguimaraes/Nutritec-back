package com.nutritec.backend.Nutri.Service;

import java.util.List;
import java.util.Optional;

import com.nutritec.backend.Nutri.Model.Nutricionista;
import com.nutritec.backend.Nutri.Repository.NutricionistaRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class NutricionistaService {

    NutricionistaRepository repository;


    public Nutricionista update(Nutricionista nutri){
       Nutricionista n = repository.findByMatricula(nutri.getMatricula()).get(0);

       n.setEspecialidade(nutri.getEspecialidade());
       n.setIdade(nutri.getIdade());
       n.setNome(nutri.getNome());
       n.setPlanosAceitos(nutri.getPlanosAceitos());
       repository.save(n);
       return n;
    }

    public int verificarNutricionista(Nutricionista n) {
        boolean nome = (n.getNome() == null || n.getNome().isEmpty());
        boolean idade = (n.getIdade() == 0);
        boolean cpf = n.getMatricula() == null || !numerico(n.getMatricula());

        if (cpf) {
            return 1;
        } else if ( nome || idade) {
            return 2;
        }
        return 0;
    }

    public boolean numerico(String matricula){
        for(int i = 0; i < matricula.length(); i++) {
            if(!matricula.substring(i).matches("[0-9]*")){
                return false;
            }
    }
        return true;
    }

    public List<Nutricionista> getNutris(){
       return repository.findAll();
        

    }

    public Optional<Nutricionista> opNutri(Long id){
        return repository.findById(id);
    }


    
}

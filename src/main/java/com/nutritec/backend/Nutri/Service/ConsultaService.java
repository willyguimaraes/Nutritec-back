package com.nutritec.backend.Nutri.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.nutritec.backend.Nutri.Model.Consulta;
import com.nutritec.backend.Nutri.Model.Nutricionista;
import com.nutritec.backend.Nutri.Model.Paciente;
import com.nutritec.backend.Nutri.Repository.ConsultaRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ConsultaService {

    NutricionistaService serviceNutri;
    PacienteService servicePac;
    ConsultaRepository repository;

    public List<Nutricionista> nutriPorPlano(String plano) {

        List<Nutricionista> nutris = serviceNutri.getNutris();

        if (plano.isEmpty()) {
            return nutris;
        }

        List<Nutricionista> comPlano = new ArrayList<Nutricionista>();

        for (int i = 0; i < nutris.size(); i++) {
            if (nutris.get(i).getPlanosAceitos().contains(plano)) {
                comPlano.add(nutris.get(i));
            }

        }
        return comPlano;
    }

    public Optional<Nutricionista> getNutri(Long id) {
        return serviceNutri.opNutri(id);
    }

    public Optional<Paciente> getPaciente(Long id) {
        return servicePac.opPac(id);
    }

    public Consulta novaConsulta(Long p, Long n, String data, String plano) {
        Consulta nova = new Consulta();
        nova.setPacienteId(p);
        nova.setNutriId(n);
        nova.setPlano(plano);
        nova.setDataConsulta(data);
        repository.save(nova);
        return nova;
    }

    public Optional<Consulta> opConsulta(Long id) {
        return repository.findById(id);
    }

    public int verificarConsulta(Consulta consulta) {

        Optional<Nutricionista> n = getNutri(consulta.getNutriId());
        Optional<Paciente> p = getPaciente(consulta.getPacienteId());

        if (!n.isPresent()) {
            return 1;
        } else if (!p.isPresent()) {
            return 2;
        }
        return 0;
    }

    public String infoConsulta(Consulta c){
        String paciente = servicePac.opPac(c.getPacienteId()).get().getNome();
        String nutricionista = serviceNutri.opNutri(c.getNutriId()).get().getNome();

        String info = "ID da consulta = " + c.getId() + 
                    "\n Paciente : " + paciente +
                    "\n Nutricionista : " + nutricionista +
                    "\n Plano de saude : " + c.getPlano() +
                    "\n Data da consulta : " + c.getDataConsulta() + "h";
        
        return info;
    } 

    public Consulta updateConsulta(Consulta con, Long id){
        Consulta c = repository.getById(id);

        c.setPacienteId(con.getPacienteId());
        c.setNutriId(con.getNutriId());
        c.setPlano(con.getPlano());
        c.setDataConsulta(con.getDataConsulta());
        repository.save(c);
        return c;
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }

    public List<String> listarConsultas(){
        List<Consulta> consultas = repository.findAll();
        List<String> infoConsultas = new ArrayList<>();
        for(int i = 0; i < consultas.size(); i++){
            String str = infoConsulta(consultas.get(i));
            infoConsultas.add(str);
        }

        return infoConsultas;

    }

}

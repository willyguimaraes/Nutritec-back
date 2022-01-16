package com.nutritec.backend.Nutri.Repository;

import java.util.List;
import com.nutritec.backend.Nutri.Model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByCpf(String cpf);
}

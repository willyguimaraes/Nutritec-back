package com.nutritec.backend.Nutri.Repository;

import java.util.List;
import com.nutritec.backend.Nutri.Model.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long> {
    List<Nutricionista> findByMatricula(String matricula);
}
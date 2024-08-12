package br.com.projetoESOF.length_service.repository;

import br.com.projetoESOF.length_service.model.Length;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LengthRepository extends JpaRepository<Length, Long> {

    Length findByFromAndTo(String from, String to);
}

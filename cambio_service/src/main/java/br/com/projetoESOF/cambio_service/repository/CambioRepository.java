package br.com.projetoESOF.cambio_service.repository;

import br.com.projetoESOF.cambio_service.model.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;
                                                                //id
public interface CambioRepository extends JpaRepository<Cambio, Long> {

    Cambio findByFromAndTo(String from, String to);
}

//this permisses to create a repository to access the database

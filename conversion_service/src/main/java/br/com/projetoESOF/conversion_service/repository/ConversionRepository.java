package br.com.projetoESOF.conversion_service.repository;

import br.com.projetoESOF.conversion_service.model.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {}

package br.com.projetoESOF.cambio_service.repository;

import br.com.projetoESOF.cambio_service.model.Cambio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CambioRepositoryTest {

    @Autowired
    private CambioRepository repository;

    @Test
    public void testFindByFromAndTo() {
        Cambio cambio = new Cambio(null, "USD", "BRL", BigDecimal.valueOf(5.42), null, "8000");
        repository.save(cambio);

        Cambio foundCambio = repository.findByFromAndTo("USD", "BRL");

        assertNotNull(foundCambio);
        assertEquals("USD", foundCambio.getFrom());
        assertEquals("BRL", foundCambio.getTo());
    }
}

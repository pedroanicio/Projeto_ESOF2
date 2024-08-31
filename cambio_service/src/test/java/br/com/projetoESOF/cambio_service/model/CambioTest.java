package br.com.projetoESOF.cambio_service.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CambioTest {

    @Test
    public void testCambioEqualsAndHashcode() {
        Cambio cambio1 = new Cambio(1L, "USD", "BRL", BigDecimal.valueOf(5.42), null, "8000");
        Cambio cambio2 = new Cambio(1L, "USD", "BRL", BigDecimal.valueOf(5.42), null, "8000");

        assertEquals(cambio1, cambio2);
        assertEquals(cambio1.hashCode(), cambio2.hashCode());

        cambio2.setId(2L);

        assertNotEquals(cambio1, cambio2);
        assertNotEquals(cambio1.hashCode(), cambio2.hashCode());
    }
}
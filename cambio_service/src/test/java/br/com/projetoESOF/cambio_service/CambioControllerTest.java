package br.com.projetoESOF.cambio_service;

import br.com.projetoESOF.cambio_service.controller.CambioController;
import br.com.projetoESOF.cambio_service.model.Cambio;
import br.com.projetoESOF.cambio_service.repository.CambioRepository;
import br.com.projetoESOF.cambio_service.service.CambioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class CambioControllerTest {

    @Mock
    private CambioRepository repository;

    @Mock
    private Environment environment;

    @Mock
    private CambioService cambioService; // Mockar o CambioService

    @InjectMocks
    private CambioController cambioController;

    public CambioControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCambio() {
        // Dados de entrada
        BigDecimal amount = BigDecimal.valueOf(100);
        String from = "USD";
        String to = "BRL";
        BigDecimal conversionFactor = BigDecimal.valueOf(5);
        Cambio cambio = new Cambio(1L, from, to, conversionFactor, null, null);

        // Simular o comportamento do repositório
        when(repository.findByFromAndTo(from, to)).thenReturn(cambio);

        // Simular o comportamento do Environment
        when(environment.getProperty("local.server.port")).thenReturn("8000");

        // Simular o cálculo da conversão
        when(cambioService.calculateConvertedValue(amount, conversionFactor)).thenReturn(conversionFactor.multiply(amount).setScale(2));

        // Resultado esperado
        Cambio result = cambioController.getCambio(amount, from, to);

        BigDecimal expectedConvertedValue = conversionFactor.multiply(amount).setScale(2); // 5 * 100 = 500

        assertEquals(expectedConvertedValue, result.getConvertedValue());
        assertEquals("8000", result.getEnviroment());
    }

    @Test
    public void testGetCambioCurrencyUnsupported() {
        // Dados de entrada
        BigDecimal amount = BigDecimal.valueOf(100);
        String from = "AAA";
        String to = "BRL";

        // Espera-se uma RuntimeException
        try {
            cambioController.getCambio(amount, from, to);
        } catch (RuntimeException e) {
            assertEquals("Currency conversion from AAA to BRL is unsupported.", e.getMessage());
        }
    }
}

package br.com.projetoESOF.length_service;

import br.com.projetoESOF.length_service.controller.LengthController;
import br.com.projetoESOF.length_service.model.Length;
import br.com.projetoESOF.length_service.repository.LengthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
public class LengthControllerTest {

	@Mock
	private LengthRepository repository;

	@Mock
	private Environment environment;

	@InjectMocks
	private LengthController lengthController;

	public LengthControllerTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetLentgh() {
		// Dados de entrada
		BigDecimal amount = BigDecimal.valueOf(100);
		String from = "M";
		String to = "CM";
		BigDecimal conversionFactor = BigDecimal.valueOf(100);
		Length length = new Length(1L, from, to, conversionFactor, null, null);
		when(repository.findByFromAndTo(from, to)).thenReturn(length);
		when(environment.getProperty("local.server.port")).thenReturn("8200");

		// Resultado esperado
		Length result = lengthController.getLength(amount, from, to);
		BigDecimal expectedConvertedValue = conversionFactor.multiply(amount).setScale(2);

		assertEquals(expectedConvertedValue, result.getConvertedValue());
		assertEquals("8200", result.getEnviroment());
	}

	@Test
	void testGetLengthUnitUnsupported() throws Exception {
		// Dados de entrada
		BigDecimal amount = BigDecimal.valueOf(100);
		String from = "M";
		String to = "KM"; // Unidade não suportada

		// Espera-se uma RuntimeException
		try {
			lengthController.getLength(amount, from, to);
		} catch (RuntimeException e) {
			assertEquals("Unit Unsuported.", e.getMessage());
		}
	}
}

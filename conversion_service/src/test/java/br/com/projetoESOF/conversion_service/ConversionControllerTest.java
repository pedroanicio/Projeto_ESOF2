package br.com.projetoESOF.conversion_service;

import br.com.projetoESOF.conversion_service.controller.ConversionController;
import br.com.projetoESOF.conversion_service.model.Conversion;
import br.com.projetoESOF.conversion_service.proxy.CambioProxy;
import br.com.projetoESOF.conversion_service.proxy.LengthProxy;
import br.com.projetoESOF.conversion_service.repository.ConversionRepository;
import br.com.projetoESOF.conversion_service.response.Cambio;
import br.com.projetoESOF.conversion_service.response.Length;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ConversionControllerTest {

	@Mock
	private ConversionRepository repository;

	@Mock
	private CambioProxy cambioProxy;

	@Mock
	private LengthProxy lengthProxy;

	@Mock
	private Environment environment;

	@InjectMocks
	private ConversionController conversionController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(conversionController).build();
	}

	@Test
	public void testFindConversionWithCambio() throws Exception {
		// Dados de entrada
		Long id = 1L;
		String value = "10";
		String from = "BRL";
		String to = "USD";

		Conversion conversion = new Conversion(id, "Cambio", null, from, to, null);
		Cambio cambio = new Cambio(1L, from, to, 5.0, 50.0, "CambioEnv");

		when(repository.getById(id)).thenReturn(conversion);
		when(cambioProxy.getCambio(Double.parseDouble(value), from, to)).thenReturn(cambio);
		when(environment.getProperty("local.server.port")).thenReturn("8100");

		mockMvc.perform(MockMvcRequestBuilders.get("/conversion-service/{id}/{value}/{from}/{to}", id, value, from, to)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value(cambio.getConvertedValue()))
				.andExpect(jsonPath("$.from").value(from))
				.andExpect(jsonPath("$.to").value(to))
				.andExpect(jsonPath("$.environment").value("Conversion port: 8100 Cambio port: CambioEnv"));
	}

	@Test
	public void testFindConversionWithLength() throws Exception {
		// Dados de entrada
		Long id = 2L;
		String value = "10";
		String from = "M";
		String to = "CM";

		Conversion conversion = new Conversion(id, "Length", null, from, to, null);
		Length length = new Length(1L, from, to, 100.0, 1000.0, "LengthEnv");

		when(repository.getById(id)).thenReturn(conversion);
		when(lengthProxy.getLength(Double.parseDouble(value), from, to)).thenReturn(length);
		when(environment.getProperty("local.server.port")).thenReturn("8100");

		mockMvc.perform(MockMvcRequestBuilders.get("/conversion-service/{id}/{value}/{from}/{to}", id, value, from, to)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value(length.getConvertedValue()))
				.andExpect(jsonPath("$.from").value(from))
				.andExpect(jsonPath("$.to").value(to))
				.andExpect(jsonPath("$.environment").value("Conversion port: 8100 Length port: LengthEnv"));
	}

}

package br.com.projetoESOF.cambio_service.controller;

import br.com.projetoESOF.cambio_service.model.Cambio;
import br.com.projetoESOF.cambio_service.repository.CambioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CambioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CambioRepository repository;

    @Test
    public void testGetCambio() throws Exception {
        Cambio cambio = new Cambio(null, "USD", "BRL", BigDecimal.valueOf(5.42), null, "8000");
        repository.save(cambio);

        mockMvc.perform(get("/cambio-service/10/USD/BRL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value("USD"))
                .andExpect(jsonPath("$.to").value("BRL"))
                .andExpect(jsonPath("$.convertedValue").value("54.20"));
    }
}

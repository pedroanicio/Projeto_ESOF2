package br.com.projetoESOF.cambio_service.controller;

import br.com.projetoESOF.cambio_service.exception.UnsupportedCurrencyException;
import br.com.projetoESOF.cambio_service.model.Cambio;
import br.com.projetoESOF.cambio_service.repository.CambioRepository;
import br.com.projetoESOF.cambio_service.service.CambioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Tag(name = "Cambio Service API")
@RestController
@RequestMapping("cambio-service")
public class CambioController {
    private Logger logger = LoggerFactory.getLogger(CambioController.class);

    @Autowired //injeção de dependencia
    private Environment environment;

    @Autowired // injeção de dependencia
    private CambioRepository repository;

    @Autowired // injeção de dependencia
    private CambioService cambioService;

    // http://localhost:8000/cambio-service/5/USD/BRL
    @Operation(description = "Get cambio from currency")
    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from")String from,
            @PathVariable("to")String to
    ){

        logger.info("getCambio called with amount={}, from={}, to={}", amount, from, to);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        if (from.length() != 3 || to.length() != 3) {
            throw new IllegalArgumentException("Currency codes must be exactly 3 characters.");
        }

        Cambio cambio = repository.findByFromAndTo(from, to);
        if (cambio == null) {
            throw new UnsupportedCurrencyException("Currency conversion from " + from + " to " + to + " is unsupported.");
        }

        BigDecimal convertedValue = cambioService.calculateConvertedValue(amount, cambio.getConversionFactor());
        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));

        cambio.setEnviroment(environment.getProperty("local.server.port"));

        return cambio;

    }
}

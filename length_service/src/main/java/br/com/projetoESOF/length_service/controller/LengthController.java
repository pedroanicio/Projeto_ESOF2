package br.com.projetoESOF.length_service.controller;

import br.com.projetoESOF.length_service.model.Length;
import br.com.projetoESOF.length_service.repository.LengthRepository;
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

@Tag(name = "Length Service API")
@RestController
@RequestMapping("length-service")
public class LengthController {
    private Logger logger = LoggerFactory.getLogger(LengthController.class);

    @Autowired //injeção de dependencia
    private Environment environment;

    @Autowired // injeção de dependencia
    private LengthRepository repository;


    // http://localhost:8200/length-service/5/M/CM
    @Operation(description = "Get length from unit")
    @GetMapping(value = "/{value}/{from}/{to}")
    public Length getLength(
            @PathVariable("value") BigDecimal value,
            @PathVariable("from")String from,
            @PathVariable("to")String to
    ){

        logger.info("getLength is caller with -> {}, {} and {}", value, from, to);
        var length = repository.findByFromAndTo(from, to);
        if (length == null) throw new RuntimeException("Unit Unsuported."); //unidade nao suportada

        var port = environment.getProperty("local.server.port");

        BigDecimal conversionFactor = length.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(value);

        length.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        length.setEnviroment(port);
        return length;
    }



}

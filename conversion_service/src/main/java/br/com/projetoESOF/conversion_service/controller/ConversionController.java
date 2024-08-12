package br.com.projetoESOF.conversion_service.controller;

import br.com.projetoESOF.conversion_service.model.Conversion;
import br.com.projetoESOF.conversion_service.proxy.CambioProxy;
import br.com.projetoESOF.conversion_service.proxy.LengthProxy;
import br.com.projetoESOF.conversion_service.repository.ConversionRepository;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Conversion endpoint")
@RestController
@RequestMapping("conversion-service")
public class ConversionController {

    @Autowired
    private Environment environment;

    @Autowired
    private ConversionRepository repository;

    @Autowired
    private CambioProxy cambioProxy;

    @Autowired
    private LengthProxy lengthProxy;

    //http://localhost:8100/conversion-service/1/10/BRL/USD
    //http://localhost:8100/conversion-service/2/10/M/CM
    @Operation(summary = "Find a specific conversion by your id")
    @GetMapping(value="/{id}/{value}/{from}/{to}")
    @Retry(name = "default") // 3 requests
    public Conversion findConversion(
            @PathVariable("id") Long id,
            @PathVariable("value") String value,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {
        var conversion = repository.getById(id);
        if (conversion == null ) throw  new RuntimeException("Not found");

        if (id == 1){
            var cambio = cambioProxy.getCambio(Double.parseDouble(value), from, to);

            var port = environment.getProperty("local.server.port");
            conversion.setEnvironment("Conversion port: "+port +
                    " Cambio port: " + cambio.getEnviroment());

            conversion.setValue(cambio.getConvertedValue());
            conversion.setFrom(cambio.getFrom());
            conversion.setTo(cambio.getTo());
        }
        if (id == 2){
            var length = lengthProxy.getLength(Double.parseDouble(value), from, to);

            var port = environment.getProperty("local.server.port");
            conversion.setEnvironment("Conversion port: "+port +
                    " Length port: " + length.getEnviroment());

            conversion.setValue(length.getConvertedValue());
            conversion.setFrom(length.getFrom());
            conversion.setTo(length.getTo());
        }


        return conversion;

     }
}

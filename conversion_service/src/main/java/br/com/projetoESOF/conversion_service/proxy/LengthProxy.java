package br.com.projetoESOF.conversion_service.proxy;

import br.com.projetoESOF.conversion_service.response.Length;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "length-service", url = "localhost:8200")
public interface LengthProxy {

    @GetMapping(value = "/length-service/{value}/{from}/{to}")
    public Length getLength(
            @PathVariable("value") Double value,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    );
}

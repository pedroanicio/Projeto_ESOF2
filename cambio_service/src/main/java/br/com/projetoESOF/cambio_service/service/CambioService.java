package br.com.projetoESOF.cambio_service.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CambioService {

    public BigDecimal calculateConvertedValue(BigDecimal amount, BigDecimal conversionFactor) {
        return conversionFactor.multiply(amount).setScale(2, RoundingMode.CEILING);
    }
}

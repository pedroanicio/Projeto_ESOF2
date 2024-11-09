package br.com.projetoESOF.length_service.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class LengthService {

    public BigDecimal calculateConvertedValue(BigDecimal value, BigDecimal conversionFactor) {
        return conversionFactor.multiply(value).setScale(2, RoundingMode.CEILING);
    }
}

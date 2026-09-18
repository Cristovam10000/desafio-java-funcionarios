package com.desafio.funcionarios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class PrincipalTest {

    @Test
    void deveFormatarDataNoPadraoBrasileiro() {
        assertEquals("18/10/2000", Principal.formatarData(LocalDate.of(2000, 10, 18)));
    }

    @Test
    void deveFormatarValorComPontoDeMilharEVirgulaDecimal() {
        assertEquals("3.319,20", Principal.formatarValor(new BigDecimal("3319.20")));
        assertEquals("19.119,88", Principal.formatarValor(new BigDecimal("19119.88")));
    }
}

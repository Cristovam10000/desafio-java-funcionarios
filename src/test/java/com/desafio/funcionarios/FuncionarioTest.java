package com.desafio.funcionarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class FuncionarioTest {

    private Funcionario criarMaria() {
        return new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
    }

    @Test
    void deveHerdarNomeEDataNascimentoDePessoa() {
        Funcionario maria = criarMaria();
        assertEquals("Maria", maria.getNome());
        assertEquals(LocalDate.of(2000, 10, 18), maria.getDataNascimento());
    }

    @Test
    void deveReajustarSalarioEmDezPorCento() {
        Funcionario maria = criarMaria();
        maria.reajustarSalario(new BigDecimal("0.10"));
        assertEquals(new BigDecimal("2210.38"), maria.getSalario());
    }

    @Test
    void deveArredondarMeioCentavoParaCima() {
        Funcionario laura = new Funcionario("Laura", LocalDate.of(1994, 7, 8),
                new BigDecimal("3017.45"), "Gerente");
        laura.reajustarSalario(new BigDecimal("0.10"));
        assertEquals(new BigDecimal("3319.20"), laura.getSalario());
    }

    @Test
    void deveGuardarSalarioComDuasCasasDecimais() {
        Funcionario funcionario = new Funcionario("Teste", LocalDate.of(1990, 1, 1),
                new BigDecimal("1000"), "Operador");
        assertEquals(new BigDecimal("1000.00"), funcionario.getSalario());
    }

    @Test
    void naoDeveCriarFuncionarioComNomeEmBranco() {
        assertThrows(IllegalArgumentException.class,
                () -> new Funcionario("   ", LocalDate.of(1990, 1, 1), new BigDecimal("1000.00"), "Operador"));
    }

    @Test
    void naoDeveCriarFuncionarioComSalarioNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Funcionario("Teste", LocalDate.of(1990, 1, 1), new BigDecimal("-1.00"), "Operador"));
    }

    @Test
    void naoDeveCriarFuncionarioComDataNula() {
        assertThrows(NullPointerException.class,
                () -> new Funcionario("Teste", null, new BigDecimal("1000.00"), "Operador"));
    }

    @Test
    void naoDeveReajustarComPercentualNegativo() {
        Funcionario maria = criarMaria();
        assertThrows(IllegalArgumentException.class,
                () -> maria.reajustarSalario(new BigDecimal("-0.10")));
    }

    @Test
    void deveCalcularIdadeConsiderandoSeOAniversarioJaPassou() {
        Funcionario maria = criarMaria();
        assertEquals(25, maria.calcularIdade(LocalDate.of(2026, 10, 17)));
        assertEquals(26, maria.calcularIdade(LocalDate.of(2026, 10, 18)));
    }
}

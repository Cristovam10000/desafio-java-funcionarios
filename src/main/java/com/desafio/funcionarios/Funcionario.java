package com.desafio.funcionarios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private final String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        Objects.requireNonNull(salario, "salario não pode ser nulo");
        Objects.requireNonNull(funcao, "funcao não pode ser nula");
        if (salario.signum() < 0) {
            throw new IllegalArgumentException("salario não pode ser negativo");
        }
        if (funcao.isBlank()) {
            throw new IllegalArgumentException("funcao não pode ser vazia");
        }
        this.salario = salario.setScale(2, RoundingMode.HALF_UP);
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }
    
    public String getFuncao() {
        return funcao;
    }

    public void reajustarSalario(BigDecimal percentual) {
        Objects.requireNonNull(percentual, "percentual não pode ser nulo");
        if (percentual.signum() < 0) {
            throw new IllegalArgumentException("percentual não pode ser negativo");
        }
        BigDecimal fator = BigDecimal.ONE.add(percentual);
        this.salario = salario.multiply(fator).setScale(2, RoundingMode.HALF_UP);

    }
}

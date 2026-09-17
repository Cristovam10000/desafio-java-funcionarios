package com.desafio.funcionarios;

import java.time.LocalDate;
import java.util.Objects;
import java.time.Period;

public class Pessoa {
    private final String nome;
    private final LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        Objects.requireNonNull(nome, "nome não pode ser nulo");
        Objects.requireNonNull(dataNascimento, "data não pode ser nulo");
        if (nome.isBlank()) {
            throw new IllegalArgumentException("nome não pode ser vazio");
        }
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int calcularIdade(LocalDate dataReferencia) {
        Objects.requireNonNull(dataReferencia, "dataReferencia não pode ser nula");
        if (dataReferencia.isBefore(dataNascimento)) {
            throw new IllegalArgumentException("dataReferencia não pode ser anterior ao nascimento");
        }
        return Period.between(dataNascimento, dataReferencia).getYears();
    }

}
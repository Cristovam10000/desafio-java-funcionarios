package com.desafio.funcionarios;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.desafio.funcionarios.Funcionario;

public class Principal {
    private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BigDecimal PERCENTUAL_REAJUSTE = new BigDecimal("0.10");

    public static void main(String[] args) {
        List<Funcionario> funcionarios = criarFuncionarios();

        removerFuncionarioPorNome(funcionarios, "João");

        System.out.println("=== Funcionários ===");
        imprimirFuncionarios((funcionarios));

        aplicarReajuste(funcionarios, PERCENTUAL_REAJUSTE);
        System.out.println();
        System.out.println("=== Funcionarios com reajuste de 10% ===");
        imprimirFuncionarios(funcionarios);

        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);

        System.out.println();
        System.out.println("=== Funcionários por função ===");
        imprimirAgrupadoresPorFuncao(funcionariosPorFuncao);
    }    

    static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }

    static void removerFuncionarioPorNome(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    static String formatarData(LocalDate data) {
        return data.format(FORMATO_DATA);
    }

    static String formatarValor(BigDecimal valor) {
        DecimalFormat formato = new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(LOCALE_BR));
        formato.setRoundingMode(RoundingMode.HALF_UP);
        return formato.format(valor);
    }

    static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            System.out.printf("%-10s %s %12s %s%n",
                funcionario.getNome(),
                formatarData(funcionario.getDataNascimento()),
                formatarValor(funcionario.getSalario()),
                funcionario.getFuncao()
            );
        }
        
    }

    static void aplicarReajuste(List<Funcionario> funcionarios, BigDecimal percentual) {
        for (Funcionario funcionario : funcionarios) {
            funcionario.reajustarSalario(percentual);
        }
    }

    static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao, LinkedHashMap::new, Collectors.toList()));
    }

    static void imprimirAgrupadoresPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        for (Map.Entry<String, List<Funcionario>> grupo : funcionariosPorFuncao.entrySet()) {
            System.out.println("-- " + grupo.getKey() + " --");
            imprimirFuncionarios(grupo.getValue());
        }
    }
}

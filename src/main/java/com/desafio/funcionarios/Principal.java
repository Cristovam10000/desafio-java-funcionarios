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

import java.time.Month;
import java.util.Comparator;
import java.util.Set;



public class Principal {
    private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BigDecimal PERCENTUAL_REAJUSTE = new BigDecimal("0.10");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public static void main(String[] args) {
        // 3.1 - inserir todos os funcionários, na mesma ordem da tabela
        List<Funcionario> funcionarios = criarFuncionarios();

        // 3.2 - remover o funcionário "João" da lista
        removerFuncionarioPorNome(funcionarios,"João");

        // 3.3 - imprimir todos os funcionários com suas informações
        System.out.println("=== Funcionários ===");
        imprimirFuncionarios((funcionarios));

        // 3.4 - os funcionários receberam 10% de aumento de salário
        aplicarReajuste(funcionarios, PERCENTUAL_REAJUSTE);
        System.out.println();
        System.out.println("=== Funcionarios com reajuste de 10% ===");
        imprimirFuncionarios(funcionarios);

        // 3.5 - agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);

        // 3.6 - imprimir os funcionários agrupados por função
        System.out.println();
        System.out.println("=== Funcionários por função ===");
        imprimirAgrupadoresPorFuncao(funcionariosPorFuncao);

        // 3.8 - imprimir os funcionários que fazem aniversário nos meses 10 e 12
        System.out.println();
        System.out.println("=== Aniversariantes dos meses 10 e 12 ===");
        imprimirFuncionarios(filtrarAniversariantes(funcionarios, Set.of(Month.OCTOBER, Month.DECEMBER)));

        // 3.9 - imprimir o funcionário com a maior idade (nome e idade)
        Funcionario maisVelho = encontrarMaisVelho(funcionarios);
        System.out.println();
        System.out.println("=== Funcionários com a maior idade ===");
        System.out.println(maisVelho.getNome() + ", " + maisVelho.calcularIdade(LocalDate.now()) + "anos");

        // 3.10 - imprimir a lista de funcionários por ordem alfabética
        System.out.println();
        System.out.println("=== Funcionários em ordem alfabética ===");
        imprimirFuncionarios(ordenarPorNome(funcionarios));

        // 3.11 - imprimir o total dos salários dos funcionários
        System.out.println();
        System.out.println("=== Total dos salários ===");
        System.out.println(formatarValor(calcularTotalSalarios(funcionarios)));

        // 3.12 - imprimir quantos salários mínimos ganha cada funcionário
        System.out.println();
        System.out.println("=== Salários mínimos por funcionário ===");
        for (Funcionario funcionario : funcionarios) {
            System.out.printf("%-10s %s%n", funcionario.getNome(),
            formatarValor(calcularQuantidadeSalariosMinimos(funcionario)));
        }
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

    static List<Funcionario> filtrarAniversariantes(List<Funcionario> funcionarios, Set<Month> meses) {
        return funcionarios.stream().filter(funcionario -> meses.contains(funcionario.getDataNascimento().getMonth())).toList();
    }

    static Funcionario encontrarMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream().min(Comparator.comparing(Funcionario::getDataNascimento)).orElseThrow();
    }

    static List<Funcionario> ordenarPorNome(List<Funcionario> funcionarios) {
        return funcionarios.stream().sorted(Comparator.comparing(Funcionario::getNome)).toList();
    }

    static BigDecimal calcularTotalSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    static BigDecimal calcularQuantidadeSalariosMinimos(Funcionario funcionario) {
        return funcionario.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
    }

}

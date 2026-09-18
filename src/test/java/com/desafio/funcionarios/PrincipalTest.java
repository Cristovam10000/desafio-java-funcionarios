package com.desafio.funcionarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PrincipalTest {

    private static final BigDecimal DEZ_POR_CENTO = new BigDecimal("0.10");

    private List<Funcionario> criarListaSemJoao() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();
        Principal.removerFuncionarioPorNome(funcionarios, "João");
        return funcionarios;
    }

    private List<Funcionario> criarListaReajustada() {
        List<Funcionario> funcionarios = criarListaSemJoao();
        Principal.aplicarReajuste(funcionarios, DEZ_POR_CENTO);
        return funcionarios;
    }

    private List<String> nomes(List<Funcionario> funcionarios) {
        return funcionarios.stream().map(Funcionario::getNome).toList();
    }

    @Test
    void deveCriarOsDezFuncionariosNaOrdemDaTabela() {
        List<Funcionario> funcionarios = Principal.criarFuncionarios();

        assertEquals(List.of("Maria", "João", "Caio", "Miguel", "Alice",
                "Heitor", "Arthur", "Laura", "Heloísa", "Helena"), nomes(funcionarios));
    }

    @Test
    void deveRemoverApenasOFuncionarioJoao() {
        List<Funcionario> funcionarios = criarListaSemJoao();

        assertEquals(9, funcionarios.size());
        assertTrue(funcionarios.stream().noneMatch(funcionario -> funcionario.getNome().equals("João")));
        assertEquals("Maria", funcionarios.get(0).getNome());
        assertEquals("Caio", funcionarios.get(1).getNome());
    }

    @Test
    void deveAplicarDezPorCentoDeReajusteATodos() {
        List<Funcionario> funcionarios = criarListaReajustada();

        assertEquals(new BigDecimal("2210.38"), funcionarios.get(0).getSalario());
        assertEquals(new BigDecimal("21031.87"), funcionarios.get(2).getSalario());
        assertEquals(new BigDecimal("3319.20"), funcionarios.get(6).getSalario());
    }

    @Test
    void deveAgruparPorFuncaoNaOrdemEmQueAsFuncoesAparecem() {
        Map<String, List<Funcionario>> porFuncao = Principal.agruparPorFuncao(criarListaSemJoao());

        assertEquals(List.of("Operador", "Coordenador", "Diretor", "Recepcionista",
                "Contador", "Gerente", "Eletricista"), List.copyOf(porFuncao.keySet()));
        assertEquals(List.of("Maria", "Heitor"), nomes(porFuncao.get("Operador")));
        assertEquals(List.of("Laura", "Helena"), nomes(porFuncao.get("Gerente")));
    }

    @Test
    void deveFiltrarAniversariantesDeOutubroEDezembro() {
        List<Funcionario> aniversariantes = Principal.filtrarAniversariantes(
                criarListaSemJoao(), Set.of(Month.OCTOBER, Month.DECEMBER));

        assertEquals(List.of("Maria", "Miguel"), nomes(aniversariantes));
    }

    @Test
    void deveEncontrarCaioComoFuncionarioMaisVelho() {
        Funcionario maisVelho = Principal.encontrarMaisVelho(criarListaSemJoao());

        assertEquals("Caio", maisVelho.getNome());
        assertEquals(65, maisVelho.calcularIdade(LocalDate.of(2026, 9, 18)));
    }

    @Test
    void deveOrdenarPorNomeSemAlterarAListaOriginal() {
        List<Funcionario> funcionarios = criarListaSemJoao();

        List<Funcionario> ordenados = Principal.ordenarPorNome(funcionarios);

        assertEquals(List.of("Alice", "Arthur", "Caio", "Heitor", "Helena",
                "Heloísa", "Laura", "Maria", "Miguel"), nomes(ordenados));
        assertEquals("Maria", funcionarios.get(0).getNome());
    }

    @Test
    void deveSomarOsSalariosReajustados() {
        assertEquals(new BigDecimal("50906.82"), Principal.calcularTotalSalarios(criarListaReajustada()));
    }

    @Test
    void deveCalcularQuantosSalariosMinimosCadaUmGanha() {
        List<BigDecimal> quantidades = criarListaReajustada().stream()
                .map(Principal::calcularQuantidadeSalariosMinimos)
                .toList();

        assertEquals(List.of(new BigDecimal("1.82"), new BigDecimal("8.93"), new BigDecimal("17.35"),
                new BigDecimal("2.03"), new BigDecimal("1.44"), new BigDecimal("3.70"),
                new BigDecimal("2.74"), new BigDecimal("1.46"), new BigDecimal("2.54")), quantidades);
    }

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

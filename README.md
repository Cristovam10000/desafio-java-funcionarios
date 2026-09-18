# Desafio Java: funcionários

Programa de console em Java que resolve o teste prático de programação. Ele cadastra os funcionários da tabela do enunciado e executa os itens 3.1 a 3.12 em ordem.

## Requisitos

- JDK 17 ou mais novo.
- Não é preciso instalar o Maven. O projeto traz o Maven Wrapper, que baixa a versão 3.9.16 na primeira execução.

## Como executar

No Windows:

```powershell
.\mvnw.cmd clean package
java -jar target\funcionarios-1.0.0.jar
```

No Linux ou macOS:

```bash
./mvnw clean package
java -jar target/funcionarios-1.0.0.jar
```

O `package` compila o código, roda os testes e gera o JAR. Também é possível importar a pasta como projeto Maven no Eclipse, IntelliJ, NetBeans ou VS Code e executar a classe `Principal`.

Se os acentos aparecerem trocados no terminal do Windows, rode `chcp 65001` antes de executar o JAR.

## Testes

```powershell
.\mvnw.cmd test
```

São 20 testes com JUnit 5. `FuncionarioTest` cobre as validações, o cálculo de idade e o reajuste. `PrincipalTest` cobre cada requisito com os dados da tabela: remoção do João, reajuste, agrupamento, aniversariantes, funcionário mais velho, ordenação, total dos salários, salários mínimos e formatação.

## Estrutura

```
src/main/java/com/desafio/funcionarios/
    Pessoa.java        nome e data de nascimento, cálculo de idade
    Funcionario.java   herda de Pessoa; salário, função e reajuste
    Principal.java     executa os requisitos 3.1 a 3.12
src/test/java/com/desafio/funcionarios/
    FuncionarioTest.java
    PrincipalTest.java
```

O projeto tem só as três classes que o enunciado pede. As regras de cada requisito ficam em métodos da `Principal`, e o `main` chama esses métodos na ordem do enunciado, com um comentário indicando o número de cada item.

## Decisões técnicas

Os salários usam `BigDecimal` criado a partir de texto, como `new BigDecimal("2009.44")`. Com `double`, valores como 0,10 não têm representação exata e os centavos poderiam sair errados. O salário fica sempre com duas casas decimais, arredondado com `HALF_UP`, e a divisão pelo salário mínimo informa a escala e o arredondamento para não gerar dízima.

`Pessoa` e `Funcionario` validam os dados no construtor, então nenhum objeto é criado com nome vazio, data nula ou salário negativo. O salário não tem setter. Ele só muda pelo método `reajustarSalario`, que concentra o cálculo e o arredondamento.

`calcularIdade` recebe a data de referência como parâmetro. O programa passa a data do dia e os testes passam uma data fixa, o que mantém o resultado dos testes igual em qualquer dia.

A lista de funcionários é um `ArrayList`, porque o item 3.2 exige remover um elemento. O agrupamento por função usa `LinkedHashMap` para os grupos aparecerem na ordem da tabela em todas as execuções.

Laços `for` fazem as operações que alteram dados (reajuste) ou imprimem. Streams fazem as consultas: filtro, agrupamento, ordenação e soma. A ordenação cria uma lista nova e não altera a original.

A formatação usa o locale `pt-BR` de forma explícita, com o padrão `#,##0.00` para valores e `dd/MM/yyyy` para datas. Assim a saída é a mesma em qualquer máquina. O formato numérico padrão do Java cortaria o zero final e mostraria 3.319,2 em vez de 3.319,20.

O código é compilado com `release 17`, então roda em qualquer Java a partir do 17, mesmo sendo desenvolvido com o JDK 21.

## Observações

- O enunciado não tem o item 3.7. A numeração do código segue a do enunciado.
- A idade do item 3.9 é calculada na data da execução. Até 01/05/2027, Caio aparece com 65 anos.

## Saída esperada

```
=== Funcionários ===
Maria      18/10/2000     2.009,44 Operador
Caio       02/05/1961     9.836,14 Coordenador
Miguel     14/10/1988    19.119,88 Diretor
Alice      05/01/1995     2.234,68 Recepcionista
Heitor     19/11/1999     1.582,72 Operador
Arthur     31/03/1993     4.071,84 Contador
Laura      08/07/1994     3.017,45 Gerente
Heloísa    24/05/2003     1.606,85 Eletricista
Helena     02/09/1996     2.799,93 Gerente

=== Funcionários com reajuste de 10% ===
Maria      18/10/2000     2.210,38 Operador
Caio       02/05/1961    10.819,75 Coordenador
Miguel     14/10/1988    21.031,87 Diretor
Alice      05/01/1995     2.458,15 Recepcionista
Heitor     19/11/1999     1.740,99 Operador
Arthur     31/03/1993     4.479,02 Contador
Laura      08/07/1994     3.319,20 Gerente
Heloísa    24/05/2003     1.767,54 Eletricista
Helena     02/09/1996     3.079,92 Gerente

=== Funcionários por função ===
-- Operador --
Maria      18/10/2000     2.210,38 Operador
Heitor     19/11/1999     1.740,99 Operador
-- Coordenador --
Caio       02/05/1961    10.819,75 Coordenador
-- Diretor --
Miguel     14/10/1988    21.031,87 Diretor
-- Recepcionista --
Alice      05/01/1995     2.458,15 Recepcionista
-- Contador --
Arthur     31/03/1993     4.479,02 Contador
-- Gerente --
Laura      08/07/1994     3.319,20 Gerente
Helena     02/09/1996     3.079,92 Gerente
-- Eletricista --
Heloísa    24/05/2003     1.767,54 Eletricista

=== Aniversariantes dos meses 10 e 12 ===
Maria      18/10/2000     2.210,38 Operador
Miguel     14/10/1988    21.031,87 Diretor

=== Funcionário com a maior idade ===
Caio, 65 anos

=== Funcionários em ordem alfabética ===
Alice      05/01/1995     2.458,15 Recepcionista
Arthur     31/03/1993     4.479,02 Contador
Caio       02/05/1961    10.819,75 Coordenador
Heitor     19/11/1999     1.740,99 Operador
Helena     02/09/1996     3.079,92 Gerente
Heloísa    24/05/2003     1.767,54 Eletricista
Laura      08/07/1994     3.319,20 Gerente
Maria      18/10/2000     2.210,38 Operador
Miguel     14/10/1988    21.031,87 Diretor

=== Total dos salários ===
50.906,82

=== Salários mínimos por funcionário ===
Maria      1,82
Caio       8,93
Miguel     17,35
Alice      2,03
Heitor     1,44
Arthur     3,70
Laura      2,74
Heloísa    1,46
Helena     2,54
```

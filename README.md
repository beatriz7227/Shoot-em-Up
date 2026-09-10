# Shoot-em-Up

> Exercício Programa (EP) desenvolvido para a disciplina de Computação Orientada a Objetos (ACH2003) do curso de Sistemas de Informação da Escola de Artes, Ciências e Humanidades da Universidade de São Paulo (EACH-USP).

Este projeto implementa a refatoração de um jogo do tipo *Shoot'em Up* em Java, desenvolvido originalmente de forma procedural. Assim, o objetivo principal foi reestruturar a arquitetura aplicando os principais conceitos da Programação Orientada a Objetos, para tornar a aplicação modular, legível e de fácil manutenção.

---

## 📑 Sumário

* [Principais Impactos](#-principais-impactos)
* [Conceitos de POO Aplicados](#-conceitos-de-poo-aplicados)
* [Estrutura e Arquitetura](#-estrutura-e-arquitetura)
* [Membros do Grupo](#-membros-do-grupo)
* [Como Compilar e Executar](#-como-compilar-e-executar)
* [Como Jogar (Controles)](#-como-jogar-controles)

---

## Principais Impactos

A análise do código original revelou uma alta centralização de lógica na classe `Main` e o uso de vetores estáticos para representar entidades. Assim, a refatoração lidou com esse problema ao entregar as seguintes melhorias:

* **Eliminação de Vetores Paralelos:** Transição para coleções dinâmicas (`ArrayList`), permitindo inserção direta e remoção flexível.
* **Encapsulamento de Estado:** As entidades deixaram de ser apenas posições correspondentes em vetores; cada objeto agora conhece e gerencia o seu próprio estado e comportamento.
* **Reaproveitamento de Código:** Centralização da matemática de colisões e lógicas de explosão nas superclasses, reduzindo a duplicação de funções.
* **Inclusão Dinâmica de Inimigos:** A implementação do novo `Enemy3` provou que a hierarquia permite adicionar novos adversários reaproveitando a estrutura e as coleções genéricas já existentes.

---

## Conceitos de POO Aplicados

A arquitetura do jogo foi redesenhada utilizando os pilares fundamentais da Orientação a Objetos, refletindo diretamente na modelagem do domínio:

* **Abstração:** Criação das classes abstratas `GameObject`, `Inimigo` e `Projeteis`, que definem as regras mínimas de atualização e estrutura para todas as entidades.
* **Encapsulamento:** Agrupamento de estados e comportamentos; uso de membros privados (como as velocidades em `Jogador`) e protegidos (como as propriedades base nas superclasses), além da implementação de métodos de consulta explícitos.
* **Herança:** Estruturação em três hierarquias principais (`GameObject` → `Inimigo`, `GameObject` → `Projeteis`, `GameObject` → `Estrela`), evitando a repetição de atributos básicos.
* **Polimorfismo:** Utilização de coleções tipadas por supertipos (ex: `List<Inimigo>`) e uso dinâmico das chamadas dos métodos sobrescritos `update()` e `draw()`, dispensando o uso de grandes estruturas condicionaiss.
* **Coleções Dinâmicas:** Uso de listas dinâmicas (`ArrayList`) substituindo matrizes fixas de posições reutilizáveis.

---

## Estrutura e Arquitetura

O sistema foi dividido em doze classes, garantindo coesão e facilitando a modularidade:

* **GameObject:** Superclasse abstrata que encapsula atributos fundamentais (`x`, `y`, `radius`) e os estados do ciclo de vida (`ACTIVE`, `INACTIVE`, `EXPLODING`), centralizando a matemática de colisão.
* **Inimigo e suas Subclasses:** A classe abstrata `Inimigo` herda de `GameObject` e gerencia velocidades e explosões. Possui três subclasses:
  * **`Enemy1`**: Movimenta-se para baixo e atira em direção ao jogador.
  * **`Enemy2`**: Voa lateralmente ao atingir um limiar e dispara projéteis em três ângulos simultâneos.
  * **`Enemy3`**: Inimigo exclusivo do projeto, realiza um percurso retangular intercalado com fases de disparos radiais.
* **Jogador:** Isola a lógica da nave principal, gerenciando restrições de movimento nas bordas, ressurreição após colisões e o *cooldown* dos disparos.
* **Projeteis:** Classe abstrata de movimento padrão. Especializada por `TiroJogador` e `TiroInimigo`, que implementam velocidades vetoriais opostas e desenhos diferentes.
* **Estrela e suas Subclasses:** A classe `Estrela` recicla a posição Y ao atingir a base da tela. Suas especializações, `Estrela1` e `Estrela2`, simulam profundidade de cenário através de tamanhos e velocidades diferentes.
* **GameLib:** Biblioteca externa consumida exclusivamente para a inicialização do modo gráfico e captura da entrada de teclado.
* **Main:** Classe que orienta o loop do jogo e responsável por instanciar os objetos e despachar as atualizações para as listas dinâmicas.

---
  
## Membros do Grupo

* [**Arthur Brandão da Mata**](https://github.com/ArthurBrandaoM)
* [**Beatriz dos Santos Bento**](https://github.com/beatriz7227)
* [**Caio Gaspar Paula**](https://github.com/Caio-Gaspar)
* [**Fernanda Yumi Taira**](https://github.com/feyumiii)
* [**Marco Lima Maia**](https://github.com/MarcoLimaMaia)

---

## Como Compilar e Executar

### Pré-requisitos
* É indispensável possuir o **Java Development Kit (JDK)** instalado em sua máquina.

### Opção 1: IntelliJ IDEA
1. Abra o IntelliJ IDEA e selecione a opção **Open**.
2. Navegue até a pasta raiz do repositório e clique em **OK**.
3. No painel de exploração à esquerda, expanda a pasta `src`.
4. Rode o arquivo `Main.java` selecionando **Run 'Main.main()'**.
5. A IDE compilará todas as entidades e a interface gráfica gerada pela `GameLib.java` automaticamente.

### Opção 2: Terminal (Linha de Comando)
Devido à arquitetura modular, o jogo agora é composto por múltiplos arquivos iterativos. É necessário compilar todos simultaneamente.
1. Abra o terminal de sua preferência e navegue até a pasta `src` do projeto.
2. Execute o comando abaixo para compilar todos os arquivos `.java` do diretório:

```bash
javac *.java
```

### Após a compilação bem-sucedida, inicie a aplicação com o comando:

```bash
java Main
```

## Como Jogar (Controles)
* Setas Direcionais (Cima, Baixo, Esquerda, Direita): Movimentação da nave do jogador.
* Tecla Control (CTRL): Disparo de projéteis.
* Tecla ESC: Encerra a execução e sai do jogo.
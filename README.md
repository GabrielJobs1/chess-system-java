# Documentação do Sistema de Xadrez em Java

**Autores:** Isabela Andrade De Morais e Gabriel Tavares Ferreira  
**Data:** 20 de junho de 2026(sujeito a alterações futuras)

## Resumo
Este documento detalha a modelagem orientada a objetos do projeto de Xadrez. O sistema foi projetado com uma forte separação de responsabilidades, dividido em três camadas principais: a fundação do tabuleiro, o motor de regras do xadrez e a interface com o usuário.

---

## Links para o Código Fonte

* [Repositório Principal](https://github.com/GabrielJobs1/chess-system-java.git)
* [Camada de Tabuleiro (`boardlayer`)](https://github.com/GabrielJobs1/chess-system-java/tree/main/src/BoardLayer)
* [Camada de Domínio (`chesslayer`)](https://github.com/GabrielJobs1/chess-system-java/tree/main/src/ChessLayer)
* [Camada de Aplicação (`application`)](https://github.com/GabrielJobs1/chess-system-java/tree/main/src/Application)

---

## 1. Camada de Tabuleiro (`boardlayer`)
Esta é a camada mais genérica do sistema. Ela não conhece as regras do xadrez, servindo apenas como uma matriz lógica bidimensional que pode armazenar qualquer tipo de peça.

* **`Board` (O Tabuleiro):** É a estrutura central desta camada. Ele gerencia as dimensões (linhas e colunas) e mantém uma matriz de peças. Suas principais responsabilidades incluem colocar (`placePiece`), remover (`removePiece`) e consultar peças, além de verificar se uma determinada posição existe ou está ocupada.
* **`Piece` (A Peça Genérica):** Uma classe abstrata que representa um elemento genérico no tabuleiro. Ela conhece a sua posição atual e o tabuleiro ao qual pertence. Exige que qualquer classe filha implemente o método `possibleMoves()`, garantindo que toda peça saiba calcular seus próprios movimentos. Também possui uma verificação genérica (`isThereAnyPossibleMove`) para saber se a peça está travada.
* **`Position` (A Coordenada Interna):** Representa posições matemáticas estritas usando índices baseados em zero (linha e coluna). O formato de exibição desta classe (`toString`) imprime os valores formatados, como "0, 0".
* **`BoardException`:** Trata erros estruturais de domínio, como tentar acessar uma posição fora dos limites do tabuleiro.

---

## 2. Camada de Domínio do Xadrez (`chesslayer`)
É aqui que as regras específicas do jogo de xadrez ganham vida. Esta camada utiliza a estrutura genérica do `boardlayer` e aplica a lógica de negócios da partida.

* **`ChessMatch` (O Motor do Jogo):** A classe mais importante do sistema. Ela orquestra toda a partida.
    * *Estado da Partida:* Controla de quem é a vez (`currentPlayer`), em qual turno o jogo está (`turn`), e gerencia listas de peças que estão vivas no tabuleiro e as que já foram capturadas.
    * *Regras Especiais:* Monitora ativamente estados críticos como Xeque (`check`) e Xeque-Mate (`checkMate`). Também guarda referências para peças que sofreram ações especiais, como o peão vulnerável ao En Passant ou a peça aguardando Promoção.
    * *Motor de Movimento:* O método `performChessMove()` é o coração da classe. Ele executa o movimento, processa capturas, roques, promoções e valida se a jogada deixou o próprio rei em xeque (desfazendo a jogada caso isso ocorra). Operações internas como `makeMove` e `unmakeMove` auxiliam nesse processo físico.
* **`ChessPiece` (A Peça de Xadrez):** Estende a peça genérica. Ela adiciona as características vitais do xadrez: a Cor (`Color` enum com `WHITE` e `BLACK`) e o contador de movimentos (`moveCount`), que é essencial para validar regras de Roque e peões.
* **`ChessPosition` (A Coordenada de Xadrez):** Diferente da posição matemática, esta classe entende a notação universal do xadrez (colunas de 'a' a 'h' e linhas de 1 a 8). Ela faz a ponte, convertendo silenciosamente o input do usuário para a matriz interna baseada em zero da classe `Position`.
* **`ChessException`:** Disparada quando uma regra do xadrez é violada (ex: movimento ilegal).

### 2.1 O Arsenal: Subpacote `pieces`
Todas as peças reais herdam de `ChessPiece` e implementam a lógica de seus movimentos na matriz booleana retornada por `possibleMoves()`. A notação visual de cada uma é definida nos seus métodos `toString()`.

* **Movimentos Contínuos:** `Rook` (Torre, move em cruz), `Bishop` (Bispo, move em X) e `Queen` (Rainha/Dama, combina os movimentos da Torre e do Bispo).
* **Movimentos Especiais:** `Knight` (Cavalo, move em L e pode pular outras peças).
* **Peças com Regras Acopladas:** O `King` (Rei) e o `Pawn` (Peão) são casos à parte. Como o Rei pode fazer Roque e o Peão faz En Passant e Promoção, ambos precisam conhecer o estado global do jogo. Por isso, eles recebem a própria `ChessMatch` como injeção de dependência em seus construtores.

---

## 3. Camada de Aplicação (`application`)
Responsável por interagir com o jogador e dar forma visual ao jogo no terminal.

* **`UI` (User Interface):** Um utilitário de entrada e saída. Embeleza o terminal usando códigos ANSI para colorir as peças (separando brancas e pretas) e limpa a tela a cada turno. Imprime o tabuleiro matricial, destaca os movimentos possíveis quando uma peça é selecionada, exibe as listas de peças capturadas e lê com precisão as coordenadas digitadas.
* **`Program` (O Ponto de Entrada):** Contém o método `main`. Ele funciona como um Game Loop contínuo. Instancia a partida (`ChessMatch`), limpa a tela, imprime o tabuleiro via `UI`, solicita a posição de origem (`source`) e destino (`target`) do usuário, aciona a execução do movimento, trata imediatamente eventuais promoções e captura possíveis exceções. O ciclo se repete até que a partida termine em xeque-mate.

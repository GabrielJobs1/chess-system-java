package BoardLayer;

public class Board {
    private int linhas;
    private int colunas;
    private Piece[][] pieces;

    public Board(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1) {
            throw new BoardException("Erro ao criar tabuleiro: é necessário pelo menos 1 linha e 1 coluna.");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pieces = new Piece[linhas][colunas];
    }

    public int getLinha() {
        return linhas;
    }

    public int getColuna() {
        return colunas;
    }

    public Piece piece(int linha, int coluna) {
        if (!positionExists(linha, coluna)) {
            throw new BoardException("Erro: Posição não encontrada!");
        }
        return pieces[linha][coluna];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Erro: Posição não encontrada!");
        }
        return pieces[position.getLinha()][position.getColuna()];
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("Erro: Existe uma peça na posição " + position);
        }
        pieces[position.getLinha()][position.getColuna()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Posição inválida!");
        }
        if (piece(position) == null) {
            return null;
        }

        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getLinha()][position.getColuna()] = null;
        return aux;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getLinha(), position.getColuna());
    }

    public boolean PositionExists(Position position) {
        return positionExists(position);
    }

    public boolean positionExists(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Posição não existe.");
        }
        return piece(position) != null;
    }
}

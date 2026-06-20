package ChessLayer.Pieces;

import BoardLayer.Board;
import BoardLayer.Piece;
import BoardLayer.Position;
import ChessLayer.ChessMatch;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

import java.util.List;
import java.util.stream.Collectors;

public class King extends ChessPiece {
    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean moveAlowed(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return (piece == null || piece.getColor() != getColor()); // Se não tiver peça, ou se for uma peça rival, ele pode se mexer
    }

    private boolean testCastlingForRook(Position position) {
        ChessPiece temp = (ChessPiece) getBoard().piece(position);
        return (temp instanceof Rook && temp.getMoveCount() == 0 && temp.getColor() == getColor()); // Verifica se é ua torre, não se mexeu e é da mesma cor
    }

    private boolean underAttack(Position pos, Color color) {
        List<Piece> enemiesPieces = chessMatch.getPiecesOnBoard().stream().filter(x -> ((ChessPiece)x).getColor() != color).collect(Collectors.toList());
        for (Piece piece : enemiesPieces) {
            if (piece instanceof King enemyKing) { // Teste diferente para Rei
                Position kingPos = enemyKing.position;

                int rowDif = Math.abs(kingPos.getLinha() - pos.getLinha());
                int colDif = Math.abs(kingPos.getColuna() - pos.getColuna());

                if (rowDif <= 1 && colDif <= 1) {
                    return true;
                }

                continue;
            }
            boolean[][] aux = piece.possibleMoves();
            if (aux[pos.getLinha()][pos.getColuna()]) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] aux = new boolean[getBoard().getLinha()][getBoard().getColuna()];

        Position pos = new Position(0, 0);

        // Cima
        pos.setValue(position.getLinha() - 1, position.getColuna());
        if (getBoard().positionExists(pos) && moveAlowed(pos))
            aux[pos.getLinha()][pos.getColuna()] = true;

        // Baixo
        pos.setValue(position.getLinha() + 1, position.getColuna());
        if (getBoard().positionExists(pos) && moveAlowed(pos))
            aux[pos.getLinha()][pos.getColuna()] = true;

        // Esquerda
        pos.setValue(position.getLinha(), position.getColuna() - 1);
        if (getBoard().positionExists(pos) && moveAlowed(pos))
            aux[pos.getLinha()][pos.getColuna()] = true;

        // Direita
        pos.setValue(position.getLinha(), position.getColuna() + 1);
        if (getBoard().positionExists(pos) && moveAlowed(pos))
            aux[pos.getLinha()][pos.getColuna()] = true;

        // Diagonal sup esquerda
        pos.setValue(position.getLinha() - 1, position.getColuna() - 1);
        if (getBoard().positionExists(pos) && moveAlowed(pos))
            aux[pos.getLinha()][pos.getColuna()] = true;

        // Diagonal sup direita
        pos.setValue(position.getLinha() - 1, position.getColuna() + 1);
        if (getBoard().positionExists(pos) && moveAlowed(pos))
            aux[pos.getLinha()][pos.getColuna()] = true;

        // Diagonal inf esquerda
        pos.setValue(position.getLinha() + 1, position.getColuna() - 1);
        if (getBoard().positionExists(pos) && moveAlowed(pos))
            aux[pos.getLinha()][pos.getColuna()] = true;

        // Diagonal inf direita
        pos.setValue(position.getLinha() + 1, position.getColuna() + 1);
        if (getBoard().positionExists(pos) && moveAlowed(pos))
            aux[pos.getLinha()][pos.getColuna()] = true;

        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // Roque pra direita
            Position posRook = new Position(position.getLinha(), position.getColuna() + 3);
            if (testCastlingForRook(posRook)) {
                Position pos1 = new Position(position.getLinha(), position.getColuna() + 1);
                Position pos2 = new Position(position.getLinha(), position.getColuna() + 2);
                if (getBoard().piece(pos1) == null && getBoard().piece(pos2) == null && !underAttack(pos1, getColor()) && !underAttack(pos2, getColor()))
                    aux[position.getLinha()][position.getColuna() + 2] = true;
            }

            Position posRook2 = new Position(position.getLinha(), position.getColuna() - 4);
            if (testCastlingForRook(posRook2)) {
                Position pos1 = new Position(position.getLinha(), position.getColuna() - 1);
                Position pos2 = new Position(position.getLinha(), position.getColuna() - 2);
                Position pos3 = new Position(position.getLinha(), position.getColuna() - 3);
                if (getBoard().piece(pos1) == null && getBoard().piece(pos2) == null && getBoard().piece(pos3) == null && !underAttack(pos1, getColor()) && !underAttack(pos2, getColor()))
                    aux[position.getLinha()][position.getColuna() - 2] = true;
            }
        }

        return aux;
    }

    @Override
    public String toString() {
        return "K";
    }
}

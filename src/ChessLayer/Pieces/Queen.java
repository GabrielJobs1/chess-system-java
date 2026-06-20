package ChessLayer.Pieces;


import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Queen extends ChessPiece {

    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] aux = new boolean[getBoard().getLinha()][getBoard().getColuna()];

        Position pos = new Position(0, 0);

        // Pra cima
        pos.setValue(position.getLinha() - 1, position.getColuna());
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setLinha(pos.getLinha() - 1);
        }
        if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        // Baixo
        pos.setValue(position.getLinha() + 1, position.getColuna());
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setLinha(pos.getLinha() + 1);
        }
        if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        // Esquerda
        pos.setValue(position.getLinha(), position.getColuna() - 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setColuna(pos.getColuna() - 1);
        }
        if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        // Direita
        pos.setValue(position.getLinha(), position.getColuna() + 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setColuna(pos.getColuna() + 1);
        }
        if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        // Diagonal sup esquerda
        pos.setValue(position.getLinha() - 1, position.getColuna() - 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setValue(pos.getLinha() - 1, pos.getColuna() - 1);
        }
        if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        // Diagonal sup direita
        pos.setValue(position.getLinha() - 1, position.getColuna() + 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setValue(pos.getLinha() - 1, pos.getColuna() + 1);
        }
        if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        // Diagonal inf direita
        pos.setValue(position.getLinha() + 1, position.getColuna() + 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setValue(pos.getLinha() + 1, pos.getColuna() + 1);
        }
        if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        // Diagonal inf esquerda
        pos.setValue(position.getLinha() + 1, position.getColuna() - 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setValue(pos.getLinha() + 1, pos.getColuna() - 1);
        }
        if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        return aux;
    }

    @Override
    public String toString() {
        return "Q";
    }
}
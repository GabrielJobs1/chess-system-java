package ChessLayer.Pieces;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] aux = new boolean[getBoard().getLinha()][getBoard().getColuna()];

        Position pos = new Position(0, 0);

        //moves

        //cima - direita
        pos.setValue(position.getLinha() - 1, position.getColuna() + 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setLinha(pos.getLinha() - 1);
            pos.setColuna(pos.getColuna() + 1);
        }
        if (getBoard().positionExists(pos) && getBoard().thereIsAPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }  //capturar peça

        //cima - esquerda
        pos.setValue(position.getLinha() - 1, position.getColuna() - 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setLinha(pos.getLinha() - 1);
            pos.setColuna(pos.getColuna() - 1);
        }
        if (getBoard().positionExists(pos) && getBoard().thereIsAPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }  //capturar peça

        //baixo - direita
        pos.setValue(position.getLinha() + 1, position.getColuna() + 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setLinha(pos.getLinha() + 1);
            pos.setColuna(pos.getColuna() + 1);
        }
        if (getBoard().positionExists(pos) && getBoard().thereIsAPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }  //capturar peça

        //baixo - esquerda
        pos.setValue(position.getLinha() + 1, position.getColuna() - 1);
        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
            pos.setLinha(pos.getLinha() + 1);
            pos.setColuna(pos.getColuna() - 1);
        }
        if (getBoard().positionExists(pos) && getBoard().thereIsAPiece(pos)) {
            aux[pos.getLinha()][pos.getColuna()] = true;
        }  //capturar peça

        return aux;
    }
}

package ChessLayer.Pieces;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves(){
        boolean[][] aux = new boolean[getBoard().getLinha()][getBoard().getColuna()];

        Position pos = new  Position(0,0);

        pos.setValue(position.getLinha() - 1 , position.getColuna() - 2);
        if(getBoard().positionExists(pos) && canMove(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        pos.setValue(position.getLinha() - 2 , position.getColuna() - 1);
        if(getBoard().positionExists(pos) && canMove(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        pos.setValue(position.getLinha() - 2 , position.getColuna() + 1);
        if(getBoard().positionExists(pos) && canMove(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        pos.setValue(position.getLinha() - 1 , position.getColuna() + 2);
        if(getBoard().positionExists(pos) && canMove(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        pos.setValue(position.getLinha() + 1 , position.getColuna() + 2);
        if(getBoard().positionExists(pos) && canMove(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        pos.setValue(position.getLinha() + 2 , position.getColuna() + 1);
        if(getBoard().positionExists(pos) && canMove(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        pos.setValue(position.getLinha() + 2 , position.getColuna() - 1);
        if(getBoard().positionExists(pos) && canMove(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        pos.setValue(position.getLinha() + 1, position.getColuna() - 2);
        if(getBoard().positionExists(pos) && canMove(pos)){
            aux[pos.getLinha()][pos.getColuna()] = true;
        }

        return aux;
    }
}

package ChessLayer.Pieces;


import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getLinha()][getBoard().getColuna()];

        Position p = new Position(0, 0);

        // above
        p.setValue(position.getLinha() - 1, position.getColuna());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // left
        p.setValue(position.getLinha(), position.getColuna() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // right
        p.setValue(position.getLinha(), position.getColuna() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // below
        p.setValue(position.getLinha() + 1, position.getColuna());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}

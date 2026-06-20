package ChessLayer.Pieces;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessMatch;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Pawn extends ChessPiece {
    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] aux = new boolean[getBoard().getLinha()][getBoard().getColuna()];

        Position pos = new Position(0, 0);

        if (getColor() == Color.WHITE) { // branca
            pos.setValue(position.getLinha() - 1, position.getColuna());
            if (getBoard().PositionExists(pos) && getBoard().thereIsAPiece(pos)) {
                aux[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.setValue(position.getLinha() - 2, position.getColuna());
            Position pos2 = new Position(position.getLinha() - 1, position.getColuna());

            if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos) && !getBoard().thereIsAPiece(pos2) && getMoveCount() == 0) {
                aux[pos.getLinha()][pos.getColuna()] = true;
                // avançar duas casas

                pos.setValue(position.getLinha() - 1, position.getColuna() + 1);
                if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) // Movimento de captura direita
                    aux[pos.getLinha()][pos.getColuna()] = true;
                //captura direita

                pos.setValue(position.getLinha() - 1, position.getColuna() - 1);
                if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) // Movimento de captura direita
                    aux[pos.getLinha()][pos.getColuna()] = true;

                if (position.getLinha() == 3) {
                    Position posLeft = new Position(position.getLinha(), position.getColuna() - 1);
                    if (getBoard().positionExists(posLeft) && isThereOpponentPiece(posLeft) && getBoard().piece(posLeft) == chessMatch.getEnPassantVulnerable())
                        aux[posLeft.getLinha() - 1][posLeft.getColuna()] = true;

                    Position posRight = new Position(position.getLinha(), position.getColuna() + 1);
                    if (getBoard().positionExists(posRight) && isThereOpponentPiece(posRight) && getBoard().piece(posRight) == chessMatch.getEnPassantVulnerable())
                        aux[posRight.getLinha() - 1][posRight.getColuna()] = true;

                }
            }
        } else { //pretas
            pos.setValue(position.getLinha() + 1, position.getColuna());

            if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) // Movimento padrão pra frente
                aux[pos.getLinha()][pos.getColuna()] = true;

            pos.setValue(position.getLinha() + 2, position.getColuna());
            Position pos2 = new Position(position.getLinha() + 1, position.getColuna());
            if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos) && !getBoard().thereIsAPiece(pos2) && getMoveCount() == 0) // Movimento de duas casas
                aux[pos.getLinha()][pos.getColuna()] = true;

            pos.setValue(position.getLinha() + 1, position.getColuna() + 1);
            if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) // Movimento de captura direita
                aux[pos.getLinha()][pos.getColuna()] = true;

            pos.setValue(position.getLinha() + 1, position.getColuna() - 1);
            if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) // Movimento de captura esquerda
                aux[pos.getLinha()][pos.getColuna()] = true;

            // En passant

            if (position.getLinha() == 4) {
                Position posLeft = new Position(position.getLinha(), position.getColuna() - 1);
                if (getBoard().positionExists(posLeft) && isThereOpponentPiece(posLeft) && getBoard().piece(posLeft) == chessMatch.getEnPassantVulnerable())
                    aux[posLeft.getLinha() + 1][posLeft.getColuna()] = true;

                Position posRight = new Position(position.getLinha(), position.getColuna() + 1);
                if (getBoard().positionExists(posRight) && isThereOpponentPiece(posRight) && getBoard().piece(posRight) == chessMatch.getEnPassantVulnerable())
                    aux[posRight.getLinha() + 1][posRight.getColuna()] = true;
            }
        }
        return aux;
    }

    @Override
    public String toString() {
        return "P";
    }
}
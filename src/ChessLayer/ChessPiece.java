package ChessLayer;

import BoardLayer.Board;
import BoardLayer.Piece;
import BoardLayer.Position;

public abstract class ChessPiece extends Piece {
    private Color color;
    private int moveCount;

    public ChessPiece (Board board, Color color){
        super(board);
        this.color = color;
    }
    public Color getColor(){
        return color;
    }

    public int getMoveCount(){
        return moveCount;
    }

    public boolean isThereOpponentPiece(Position position){
        ChessPiece piece = (ChessPiece)getBoard().piece(position);
        return piece != null && piece.getColor() != color;
    }

    protected void increasesMoveCount(){
        moveCount++;
    }

    protected void decreaseMoveCount(){
        moveCount--;
    }

    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);
    }
}

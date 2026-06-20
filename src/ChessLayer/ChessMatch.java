package ChessLayer;

import BoardLayer.Board;
import BoardLayer.Piece;
import BoardLayer.Position;
import ChessLayer.Pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    private Board board;

    private List<Piece> piecesOnBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public List<Piece> getPiecesOnBoard() {
        return new ArrayList<>(piecesOnBoard);
    }


    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] aux = new ChessPiece[board.getLinha()][board.getColuna()];
        for (int i = 0; i < board.getLinha(); i++) {
            for (int j = 0; j < board.getColuna(); j++) {
                aux[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return aux;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position pos = sourcePosition.toPosition();
        validateSourcePosition(pos);
        return board.piece(pos).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);

        Piece capturedPiece = makeMove(source, target);

        if (isCheck(currentPlayer)) { // Verifica se o movimento colocou o jogador em xeque
            unmakeMove(source, target, capturedPiece);
            throw new ChessExceptions("This move put yourself in check [PRESS ENTER TO CONTINUE].");
        }

        ChessPiece pieceMoved = (ChessPiece)board.piece(target);

        // Promoção
        promoted = null;
        if (pieceMoved instanceof Pawn) {
            if ((pieceMoved.getColor() == Color.WHITE && target.getLinha() == 0) || (pieceMoved.getColor() == Color.BLACK && target.getLinha() == 7)) {
                promoted = (ChessPiece)board.piece(target);
            }
        }

        check = isCheck(opponent(currentPlayer));

        if (isCheckMate(opponent(currentPlayer)))
            checkMate = true;
        else
            next();

        if (pieceMoved instanceof Pawn && (target.getLinha() == source.getLinha() + 2 || target.getLinha() == source.getLinha() - 2))
            enPassantVulnerable = pieceMoved;
        else
            enPassantVulnerable = null;

        return (ChessPiece)capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("No piece to be promoted.");
        }
        if (!type.equals("B") && !type.equals("N") && !type.equals("Q") && !type.equals("R")) // Se não escolher uma das opções válidas
            return promoted;

        Position pos = promoted.getChessPosition().toPosition();
        Piece piece = board.removePiece(pos);
        piecesOnBoard.remove(piece);

        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, pos);
        piecesOnBoard.add(newPiece);

        return newPiece;
    }

    private void next() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // Troca de jogador
    }

    private ChessPiece newPiece(String type, Color color) {
        if (type.equals("B")) return new Bishop(board, color);
        if (type.equals("N")) return new Knight(board, color);
        if (type.equals("Q")) return new Queen(board, color);
        return new Rook(board, color);
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece piece = (ChessPiece)board.removePiece(source);
        piece.increasesMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);

        if (capturedPiece != null) {
            piecesOnBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // Roque pra direita
        if (piece instanceof King && target.getColuna() == source.getColuna() + 2) {
            Position sourceRook = new Position(source.getLinha(), source.getColuna() + 3);
            Position targetRook = new Position(source.getLinha(), source.getColuna() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increasesMoveCount();
        }

        // Roque pra esquerda
        if (piece instanceof King && target.getColuna() == source.getColuna() - 2) {
            Position sourceRook = new Position(source.getLinha(), source.getColuna() - 4);
            Position targetRook = new Position(source.getLinha(), source.getColuna() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(sourceRook);
            board.placePiece(rook, targetRook);
            rook.increasesMoveCount();
        }

        // En passant
        if (piece instanceof Pawn) {
            if (source.getColuna() != target.getColuna() && capturedPiece == null) { // Verifica se o movimento foi na diagonal e não pegou peça nenhuma
                Position positionPawn;
                if (piece.getColor() == Color.WHITE)
                    positionPawn = new Position(target.getLinha() + 1, target.getColuna());
                else
                    positionPawn = new Position(target.getLinha() - 1, target.getColuna());
                capturedPiece = board.removePiece(positionPawn);
                capturedPieces.add(capturedPiece);
                piecesOnBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void unmakeMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece piece = (ChessPiece)board.removePiece(target);
        piece.decreaseMoveCount();
        board.placePiece(piece, source);

        if (capturedPiece != null) {
            capturedPieces.remove(capturedPiece);
            piecesOnBoard.add(capturedPiece);
        }

        // Desfazer roque pra direita
        if (piece instanceof King && target.getColuna() == source.getColuna() + 2) {
            Position sourceRook = new Position(source.getLinha(), source.getColuna() + 3);
            Position targetRook = new Position(source.getLinha(), source.getColuna() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(targetRook);
            board.placePiece(rook, sourceRook);
            rook.decreaseMoveCount();
        }

        // Desfazer roque pra esquerda
        if (piece instanceof King && target.getColuna() == source.getColuna() - 2) {
            Position sourceRook = new Position(source.getLinha(), source.getColuna() - 4);
            Position targetRook = new Position(source.getLinha(), source.getColuna() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(targetRook);
            board.placePiece(rook, sourceRook);
            rook.decreaseMoveCount();
        }

        // Desfazer en passant
        if (piece instanceof Pawn) {
            if (source.getColuna() != target.getColuna() && capturedPiece == enPassantVulnerable) {
                Position positionPawn;
                if (piece.getColor() == Color.WHITE)
                    positionPawn = new Position(3, target.getColuna());
                else
                    positionPawn = new Position(4, target.getColuna());
                board.placePiece(capturedPiece, positionPawn);
            }
            else if (capturedPiece != null) {
                board.placePiece(capturedPiece, target);
            }
        }
        else if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
        }
    }

    private void newPieceOnBoard(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnBoard.add(piece);
    }

    private Color opponent(Color color) {
        return color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece locateKing(Color color) {
        List<Piece> pieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece piece : pieces) {
            if (piece instanceof King)
                return (ChessPiece)piece;
        }
        throw new IllegalStateException(String.format("No %s king.", color));
    }

    private boolean isCheck(Color color) {
        Position positionKing = locateKing(color).getChessPosition().toPosition();
        List<Piece> enemiesPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList()); // Filtra apenas as peças inimigas
        for (Piece piece : enemiesPieces) {
            boolean[][] aux = piece.possibleMoves();
            if (aux[positionKing.getLinha()][positionKing.getColuna()]) // Se a posição do rei for um dos movimentos possíveis pras peças inimigas, então é xeque.
                return true;
        }
        return false;
    }

    private boolean isCheckMate(Color color) {
        if (!isCheck(color))
            return false;

        List<Piece> pieces = piecesOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); // Cria uma lista com as peças da mesma cor
        for (Piece piece : pieces) {
            boolean[][] aux = piece.possibleMoves();
            for (int i = 0; i < board.getLinha(); i++) {
                for (int j = 0; j < board.getColuna(); j++) {
                    if (aux[i][j]) {
                        Position source = ((ChessPiece)piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean isCheck = isCheck(color);
                        unmakeMove(source, target, capturedPiece);
                        if (!isCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void initialSetup(){
        // Brancas
        newPieceOnBoard('e', 1, new King(board, Color.WHITE, this)); // Rei
        newPieceOnBoard('a', 1, new Rook(board, Color.WHITE)); // Torre 1
        newPieceOnBoard('h', 1, new Rook(board, Color.WHITE)); // Torre 2
        newPieceOnBoard('b', 1, new Knight(board, Color.WHITE)); // Cavalo 1
        newPieceOnBoard('g', 1, new Knight(board, Color.WHITE)); // Cavalo 2
        newPieceOnBoard('c', 1, new Bishop(board, Color.WHITE)); // Bispo 1
        newPieceOnBoard('f', 1, new Bishop(board, Color.WHITE)); // Bispo 2
        newPieceOnBoard('d', 1, new Queen(board, Color.WHITE)); // Rainha 1
        newPieceOnBoard('a', 2, new Pawn(board, Color.WHITE, this));
        newPieceOnBoard('b', 2, new Pawn(board, Color.WHITE, this));
        newPieceOnBoard('c', 2, new Pawn(board, Color.WHITE, this));
        newPieceOnBoard('d', 2, new Pawn(board, Color.WHITE, this));
        newPieceOnBoard('e', 2, new Pawn(board, Color.WHITE, this));
        newPieceOnBoard('f', 2, new Pawn(board, Color.WHITE, this));
        newPieceOnBoard('g', 2, new Pawn(board, Color.WHITE, this));
        newPieceOnBoard('h', 2, new Pawn(board, Color.WHITE, this));

        // Pretas
        newPieceOnBoard('e', 8, new King(board, Color.BLACK, this)); // Rei
        newPieceOnBoard('a', 8, new Rook(board, Color.BLACK)); // Torre 1
        newPieceOnBoard('h', 8, new Rook(board, Color.BLACK)); // Torre 2
        newPieceOnBoard('b', 8, new Knight(board, Color.BLACK)); // Cavalo 1
        newPieceOnBoard('g', 8, new Knight(board, Color.BLACK)); // Cavalo 2
        newPieceOnBoard('c', 8, new Bishop(board, Color.BLACK)); // Bispo 1
        newPieceOnBoard('f', 8, new Bishop(board, Color.BLACK)); // Bispo 2
        newPieceOnBoard('d', 8, new Queen(board, Color.BLACK)); // Rainha 1
        newPieceOnBoard('a', 7, new Pawn(board, Color.BLACK, this));
        newPieceOnBoard('b', 7, new Pawn(board, Color.BLACK, this));
        newPieceOnBoard('c', 7, new Pawn(board, Color.BLACK, this));
        newPieceOnBoard('d', 7, new Pawn(board, Color.BLACK, this));
        newPieceOnBoard('e', 7, new Pawn(board, Color.BLACK, this));
        newPieceOnBoard('f', 7, new Pawn(board, Color.BLACK, this));
        newPieceOnBoard('g', 7, new Pawn(board, Color.BLACK, this));
        newPieceOnBoard('h', 7, new Pawn(board, Color.BLACK, this));

    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position))
            throw new ChessExceptions("No piece on source position [PRESS ENTER TO CONTINUE].");
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor())
            throw new ChessExceptions("Piece is not yours [PRESS ENTER TO CONTINUE].");
        if (!board.piece(position).isThereAnyPossibleMove())
            throw new ChessExceptions("No possible moves for the piece [PRESS ENTER TO CONTINUE].");
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target))
            throw new ChessExceptions("The piece can't move to target [PRESS ENTER TO CONTINUE].");
    }

}
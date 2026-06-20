package Application;

import ChessLayer.ChessMatch;
import ChessLayer.ChessPiece;
import ChessLayer.ChessPosition;
import ChessLayer.Color;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    public static final String RESET = "\u001B[0m";
    public static final String WHITE = "\u001B[37m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLACK = "\u001B[30m";

    public static final String BLUE_BACKGROUND = "\u001B[44m";

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String str = sc.nextLine();
            char column = str.charAt(0);
            int row = Integer.parseInt(str.substring(1));
            return new ChessPosition(column, row);
        }
        catch (RuntimeException exception) {
            throw new InputMismatchException("Invalid position. Insert values from a1 to h8 [PRESS ENTER TO CONTINUE].");
        }
    }

    public static void printPiece(ChessPiece piece, boolean background) {
        if (background)
            System.out.print(BLUE_BACKGROUND);
        if (piece == null) {
            System.out.print("-" + RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE)
                System.out.print(GREEN + piece + RESET);
            else
                System.out.print(YELLOW + piece + RESET);
        }
        System.out.print(" ");
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8-i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8-i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printMatch(ChessMatch match, List<ChessPiece> capturedPieces){
        System.out.println("Black pieces are " + YELLOW + "yellow." + RESET);
        System.out.println("White pieces are " + GREEN + "green." + RESET);
        printBoard(match.getPieces());
        System.out.println();
        printCapturedPieces(capturedPieces);
        System.out.println();
        System.out.println(String.format("Turn: %d", match.getTurn()));
        if (!match.getCheckMate()) {
            System.out.printf("Player round: %s\n", match.getCurrentPlayer());
            if (match.getCheck())
                System.out.println("Check.");
        }
        else {
            System.out.println("Checkmate.");
            System.out.println("Winner: " + match.getCurrentPlayer());
        }
    }

    private static void printCapturedPieces(List<ChessPiece> capturedPieces) {
        List<ChessPiece> white = capturedPieces.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList()); // Lista com as brancas
        List<ChessPiece> black = capturedPieces.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList()); // Lista com as pretas
        System.out.println("Captured pieces:");
        System.out.print("White: ");
        System.out.print(GREEN);
        for (ChessPiece piece : white) {
            System.out.print(piece.toString() + " ");
        }
        System.out.println(RESET);
        System.out.print("Black: ");
        System.out.print(YELLOW);
        for (ChessPiece piece : black) {
            System.out.print(piece.toString() + " ");
        }
        System.out.println(RESET);
    }
}
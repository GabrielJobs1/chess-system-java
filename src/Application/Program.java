package Application;

import ChessLayer.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();

        while (!match.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(match, capturedPieces);
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = match.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(match.getPieces(), possibleMoves);
                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = match.performChessMove(source, target);

                if (capturedPiece != null)
                    capturedPieces.add(capturedPiece);

                if (match.getPromoted() != null) {
                    System.out.print("To wich piece you want to promote (B/N/Q/R)? ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("Q") && !type.equals("R")) {
                        System.out.print("Invalid choice. Enter piece for promotion (B/N/Q/R): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    match.replacePromotedPiece(type);
                }
            }
            catch (ChessExceptions exception) {
                System.out.println(exception.getMessage());
                if (sc.hasNextLine())
                    sc.nextLine();
            }
            catch (InputMismatchException exception) {
                System.out.println(exception.getMessage());
                if (sc.hasNextLine())
                    sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(match, capturedPieces);

        sc.close();
    }
}
